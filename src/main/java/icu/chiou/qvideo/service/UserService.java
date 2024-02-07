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

    User login(String mobile);

    User register(String mobile);

    User updateUserInfo(UpdatedUserBO updatedUserBO, Integer type);
}
