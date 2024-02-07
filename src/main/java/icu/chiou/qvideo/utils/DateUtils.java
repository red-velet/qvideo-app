package icu.chiou.qvideo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 日期工具类
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
public class DateUtils {
    public static Date get(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
