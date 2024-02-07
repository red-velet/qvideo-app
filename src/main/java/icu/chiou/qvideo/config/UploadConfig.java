package icu.chiou.qvideo.config;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * 七牛云文件上传配置类
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
@org.springframework.context.annotation.Configuration
public class UploadConfig {
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Bean
    public Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public UploadManager getUploadManager() {
        return new UploadManager(new Configuration());
    }
}
