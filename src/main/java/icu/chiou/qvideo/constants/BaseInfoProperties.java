package icu.chiou.qvideo.constants;

/**
 * <p>
 * redis常量前缀
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
public class BaseInfoProperties {
    private static final String PREFIX = "qvideo:";
    public static final String CODE_MOBILE = PREFIX + "code:mobile:";
    public static final String CODE_EMAIL = PREFIX + "code:email:";
    public static final String LIMIT_IP = PREFIX + "limit:ip:";
    public static final String TOKEN = PREFIX + "token:";
    public static final String MY_FOLLOW_COUNT = PREFIX + "myfollw-count:";
    public static final String MY_FANS_COUNT = PREFIX + "myfans-count:";
    public static final String ALL_COUNT = PREFIX + "all-count:";

    public static final Integer MINTERS = 60;
    public static final Integer FIVE_MINTERS = 5 * MINTERS;
}
