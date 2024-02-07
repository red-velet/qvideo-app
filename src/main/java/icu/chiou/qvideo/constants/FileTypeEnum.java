package icu.chiou.qvideo.constants;

/**
 * <p>
 *
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
public enum FileTypeEnum {
    BGIMG(1, "用户背景图"),
    FACE(2, "用户头像");

    public final Integer type;
    public final String value;

    FileTypeEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}