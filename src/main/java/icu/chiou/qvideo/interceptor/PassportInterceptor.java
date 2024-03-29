package icu.chiou.qvideo.interceptor;

import icu.chiou.qvideo.constants.RedisPrefix;
import icu.chiou.qvideo.constants.ResponseStatusEnum;
import icu.chiou.qvideo.exception.PassportException;
import icu.chiou.qvideo.utils.IPUtil;
import icu.chiou.qvideo.utils.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 通行证拦截器
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
@Slf4j
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //对获取验证码的ip进行限流
        // 获得用户的ip
        String userIp = IPUtil.getRequestIp(request);

        // 得到是否存在的判断
        boolean flag = redisService.keyIsExist(RedisPrefix.LIMIT_IP + userIp);
        if (flag) {
            log.debug("PassportInterceptor/请求验证码: ip={} {}", userIp, "短信发送太过频繁");
            throw new PassportException(ResponseStatusEnum.MOBILE_ERROR.status(), "短信发送太过频繁");
        }
        log.debug("PassportInterceptor/请求验证码: ip={} {}", userIp, "放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
