package icu.chiou.qvideo.utils;

import java.util.Random;

/**
 * <p>
 * 短信工具类
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
public class SMSUtils {
    /**
     * 生成随机验证码-6位数
     *
     * @return
     */
    public static String generateVerification6Code() {
        // 生成随机的6位数验证码
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // 生成 0-9 之间的随机数字
            sb.append(digit);
        }
        return sb.toString();
    }
}
