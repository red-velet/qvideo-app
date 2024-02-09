package icu.chiou.qvideo.constants;

/**
 * <p>
 * redis前缀 - 常量
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/9
 */
public class RedisPrefix {
    private static final String PREFIX = "qvideo:";
    public static final String CODE_MOBILE = PREFIX + "code:mobile:";
    public static final String CODE_EMAIL = PREFIX + "code:email:";
    public static final String LIMIT_IP = PREFIX + "limit:ip:";
    public static final String TOKEN = PREFIX + "token:";
    public static final String MY_FOLLOW_COUNT = PREFIX + "myfollw-count:";
    public static final String MY_FANS_COUNT = PREFIX + "myfans-count:";
    public static final String ALL_COUNT = PREFIX + "all-count:";
    public static final Integer SECOND = 1;
    public static final Integer MINTER = 60 * SECOND;
    public static final Integer HOUR = 60 * MINTER;

    public static final Integer FIVE_MINTERS = 5 * MINTER;
    

    private RedisPrefix() {

    }
}
