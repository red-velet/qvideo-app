package icu.chiou.qvideo.utils;

import java.util.UUID;

/**
 * <p>
 * 字符串工具类
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
public class MyStringUtils {

    /**
     * 生成唯一图片名称
     *
     * @param fileName 文件名+后缀
     * @return 云服务器fileName
     */
    public static String getRandomImgName(String fileName) {

        int index = fileName.lastIndexOf(".");

        if (fileName.isEmpty() || index == -1) {
            throw new IllegalArgumentException();
        }
        // 获取文件后缀
        String suffix = fileName.substring(index).toLowerCase();
        // 生成UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 生成上传至云服务器的路径
        return "userAvatar/" + uuid + suffix;
    }
}
