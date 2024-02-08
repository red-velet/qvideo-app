package icu.chiou.qvideo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.chiou.qvideo.constants.BaseInfoProperties;
import icu.chiou.qvideo.constants.FileTypeEnum;
import icu.chiou.qvideo.constants.ResponseStatusEnum;
import icu.chiou.qvideo.constants.UserInfoModifyEnum;
import icu.chiou.qvideo.entity.UpdatedUserBO;
import icu.chiou.qvideo.entity.User;
import icu.chiou.qvideo.entity.UserVO;
import icu.chiou.qvideo.service.UserService;
import icu.chiou.qvideo.utils.MyStringUtils;
import icu.chiou.qvideo.utils.QiniuUtils;
import icu.chiou.qvideo.utils.R;
import icu.chiou.qvideo.utils.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.Objects;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
@Slf4j
@RestController
@RequestMapping("/userInfo")
public class UserController extends BaseInfoProperties {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    /**
     * 查询用户主页个人信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @GetMapping("/query")
    public R getUserInfo(String userId) {
        User userInfo = userService.getOne(new QueryWrapper<User>().eq("id", userId));

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userInfo, userVO);

        // 我的关注博主总数量
        String myFollowsCountsStr = redisService.get(MY_FOLLOW_COUNT + userId);
        // 我的粉丝总数
        String myFansCountsStr = redisService.get(MY_FANS_COUNT + ":" + userId);
        // 用户获赞总数，视频博主（点赞/喜欢）总和
        String allCountsStr = redisService.get(ALL_COUNT + ":" + userId);

        Integer myFollowsCounts = 0;
        Integer myFansCounts = 0;
        Integer totalLikeMeCounts = 0;

        if (StringUtils.isNotBlank(myFollowsCountsStr)) {
            myFollowsCounts = Integer.valueOf(myFollowsCountsStr);
        }
        if (StringUtils.isNotBlank(myFansCountsStr)) {
            myFansCounts = Integer.valueOf(myFansCountsStr);
        }
        if (StringUtils.isNotBlank(allCountsStr)) {
            totalLikeMeCounts = Integer.valueOf(allCountsStr);
        }

        userVO.setMyFollowsCounts(myFollowsCounts);
        userVO.setMyFansCounts(myFansCounts);
        userVO.setTotalLikeMeCounts(totalLikeMeCounts);

        return R.ok().data(userVO);
    }

    /**
     * 修改用户信息
     *
     * @param updatedUserBO 被修改的信息
     * @param type          修改类型
     * @return 修改状态
     */
    @PostMapping("modifyUserInfo")
    public R modifyUserInfo(@RequestBody UpdatedUserBO updatedUserBO,
                            @RequestParam Integer type) {
        //校验是否符合规范
        UserInfoModifyEnum.checkUserInfoTypeIsRight(type);

        //修改用户信息
        User newUserInfo = userService.updateUserInfo(updatedUserBO, type);

        //返回更新后的信息
        return R.ok().data(newUserInfo);
    }


    @Autowired
    QiniuUtils qiniuUtils;

    /**
     * 修改图像
     *
     * @param userId 用户id
     * @param type   图像类型
     * @param file   上传图像文件流
     * @return 修改结果
     */
    @PostMapping("/modifyImage")
    @Transactional
    public R modifyImage(@RequestParam String userId,
                         @RequestParam Integer type,
                         MultipartFile file) throws Exception {


        if (!Objects.equals(type, FileTypeEnum.BGIMG.type) && !Objects.equals(type, FileTypeEnum.FACE.type)) {
            return R.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        long size = file.getSize();
        if (size > 2097152) {
            log.error("图片上传失败: {}", file.getOriginalFilename());
            User user = userService.getById(userId);
            return R.error().status(500).msg("请上传2MB大小的图片").data(user);
        }

        //上传新图片
        String fileName = file.getOriginalFilename();
        String newFileName = MyStringUtils.getRandomImgName(fileName);
        String fileUrl = qiniuUtils.upload((FileInputStream) file.getInputStream(), newFileName);

        //修改数据库信息
        UpdatedUserBO updatedUserBO = new UpdatedUserBO();
        updatedUserBO.setId(userId);
        updatedUserBO.setFace(fileUrl);
        User user = userService.updateUserInfo(updatedUserBO, type);
        log.error("上传成功");
        return R.ok().data(user);
    }
}
