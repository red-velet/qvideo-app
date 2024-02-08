package icu.chiou.qvideo.controller;

import icu.chiou.qvideo.constants.BaseInfoProperties;
import icu.chiou.qvideo.constants.ResponseStatusEnum;
import icu.chiou.qvideo.entity.RegisterLoginBO;
import icu.chiou.qvideo.entity.User;
import icu.chiou.qvideo.entity.UserVO;
import icu.chiou.qvideo.exception.PassportException;
import icu.chiou.qvideo.service.UserService;
import icu.chiou.qvideo.utils.IPUtil;
import icu.chiou.qvideo.utils.R;
import icu.chiou.qvideo.utils.RedisService;
import icu.chiou.qvideo.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 通行证接口：登录、注册、验证码发送等等
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
@Slf4j
@RestController
@RequestMapping("passport")
public class PassportController extends BaseInfoProperties {
    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    /**
     * 短信验证码发送
     *
     * @param mobile 手机号
     * @return 发送结果
     */
    @PostMapping("/getSMSCode")
    public R getSMSCode(@RequestParam String mobile, HttpServletRequest request) {
        log.debug("进入-{}  参数-{}", "passport/getSMSCode/", mobile);
        if (StringUtils.isBlank(mobile)) {
            throw new PassportException(ResponseStatusEnum.MOBILE_ERROR.status(), ResponseStatusEnum.MOBILE_ERROR.msg());
        }

        String code = SMSUtils.generateVerification6Code();
        log.debug("发送验证码：{} --> {}", mobile, code);
        redisService.set(CODE_MOBILE + mobile, code, FIVE_MINTERS);

        //防止重复发送,对用户ip进行限流
        String userIp = IPUtil.getRequestIp(request);
        //根据用户ip进行限制，限制用户在60秒之内只能获得一次验证码
        redisService.set(LIMIT_IP + userIp, mobile, MINTERS);

        return R.ok();
    }

    /**
     * 用户登录/注册
     *
     * @param registerLoginBO 用户信息：手机号、验证码
     * @return 登录结果
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated RegisterLoginBO registerLoginBO) {
        //验证码校验
        String code = redisService.get(CODE_MOBILE + registerLoginBO.getMobile());
        if (!Objects.equals(code, registerLoginBO.getSmsCode())) {
            throw new PassportException(500, "验证码错误");
        }
        User user = userService.login(registerLoginBO.getMobile());

        if (Objects.isNull(user)) {
            //注册账号
            user = userService.register(registerLoginBO.getMobile());
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        String token = UUID.randomUUID().toString();
        userVO.setUserToken(token);
        redisService.set(TOKEN + user.getId(), token);

        // 4. 用户登录注册成功以后，删除redis中的短信验证码
        redisService.del(CODE_MOBILE + ":" + registerLoginBO.getMobile());
        return R.ok().msg("登陆成功").data(userVO);
    }

    /**
     * 用户登出
     *
     * @param userId 用户id
     * @return 登出结果
     */
    @PostMapping("/logout")
    public R logout(String userId) {
        //清除保存用户会话信息
        redisService.del(TOKEN + userId);
        return R.ok().msg("登出成功");
    }
}