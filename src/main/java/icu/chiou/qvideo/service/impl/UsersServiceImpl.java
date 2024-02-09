package icu.chiou.qvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.chiou.qvideo.constants.ResponseStatusEnum;
import icu.chiou.qvideo.constants.UserInfoModifyEnum;
import icu.chiou.qvideo.entity.UpdatedUserBO;
import icu.chiou.qvideo.entity.User;
import icu.chiou.qvideo.exception.UserException;
import icu.chiou.qvideo.mapper.UserMapper;
import icu.chiou.qvideo.service.UserService;
import icu.chiou.qvideo.utils.IDGeneratorUtils;
import icu.chiou.qvideo.utils.RedisService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    @Lazy
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String mobile) {
        return userService.getOne(new QueryWrapper<User>().eq("mobile", mobile));
    }

    @Override
    public User register(String mobile) {
        long id = IDGeneratorUtils.getID();
        String userId = (id + "").substring(0, 8);
        User user = User.builder()
                .id(userId)
                .mobile(mobile)
                .nickname("user_" + userId.substring(0, 4))
                .imoocNum(userId)
                .face("http://sevencowcloud.chiou.icu/pjx.jpg")
                .sex(1)
                .birthday(new Date())
                .country("中国")
                .province("江西")
                .city("新余")
                .district("渝水区")
                .description("这家伙很懒，什么也没有留下~~")
                .canImoocNumBeUpdated(1)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
        userService.save(user);

        return user;
    }

    @Override
    public User updateUserInfo(UpdatedUserBO updatedUserBO, Integer type) {
        User user = new User();
        if (Objects.equals(type, UserInfoModifyEnum.NICKNAME.type)) {
            //昵称是否已被使用
            int count = userService.count(new QueryWrapper<User>().eq("nickname", updatedUserBO.getNickname()));
            if (count >= 1) {
                throw new UserException(ResponseStatusEnum.NICKNAME_HAS_EXIST.status(), ResponseStatusEnum.NICKNAME_HAS_EXIST.msg());
            }
        } else if (Objects.equals(type, UserInfoModifyEnum.IMOOCNUM.type)) {
            //是否可以修改个性号
            int flag = userService.count(new QueryWrapper<User>().eq("can_imooc_num_be_updated", updatedUserBO.getImoocNum()));
            if (flag == 0) {
                throw new UserException(ResponseStatusEnum.USER_INFO_CANT_UPDATED_IMOOCNUM_ERROR.status(), ResponseStatusEnum.USER_INFO_CANT_UPDATED_IMOOCNUM_ERROR.msg());
            } else {
                //个性号是否已被使用
                int count = userService.count(new QueryWrapper<User>().eq("imooc_num", updatedUserBO.getImoocNum()));
                if (count >= 1) {
                    throw new UserException(ResponseStatusEnum.IMOOCNUM_HAS_EXIST.status(), ResponseStatusEnum.IMOOCNUM_HAS_EXIST.msg());
                }
                updatedUserBO.setCanImoocNumBeUpdated(0);
            }

        }
        BeanUtils.copyProperties(updatedUserBO, user);

        userMapper.updateById(user);
        return userMapper.selectById(user.getId());
    }
}
