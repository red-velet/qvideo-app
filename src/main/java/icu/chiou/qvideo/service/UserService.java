package icu.chiou.qvideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.chiou.qvideo.entity.UpdatedUserBO;
import icu.chiou.qvideo.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     */
    User login(String mobile);

    /**
     * 用户注册
     */
    User register(String mobile);

    /**
     * 修改用户信息
     */
    User updateUserInfo(UpdatedUserBO updatedUserBO, Integer type);
}
