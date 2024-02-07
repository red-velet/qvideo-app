package icu.chiou.qvideo.constants;

import icu.chiou.qvideo.exception.UserException;

/**
 * <p>
 * 修改用户信息类型
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
public enum UserInfoModifyEnum {
    NICKNAME(1, "昵称"),
    IMOOCNUM(2, "慕课号"),
    SEX(3, "性别"),
    BIRTHDAY(4, "生日"),
    LOCATION(5, "所在地"),
    DESC(6, "简介");

    public final Integer type;
    public final String value;

    UserInfoModifyEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public static void checkUserInfoTypeIsRight(Integer type) {
        if (type != UserInfoModifyEnum.NICKNAME.type &&
                type != UserInfoModifyEnum.IMOOCNUM.type &&
                type != UserInfoModifyEnum.SEX.type &&
                type != UserInfoModifyEnum.BIRTHDAY.type &&
                type != UserInfoModifyEnum.LOCATION.type &&
                type != UserInfoModifyEnum.DESC.type) {
            throw new UserException(ResponseStatusEnum.FAILED.status(), "违法操作");
        }
    }
}
