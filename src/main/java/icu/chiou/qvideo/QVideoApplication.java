package icu.chiou.qvideo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 *
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "icu.chiou.qvideo.mapper")
public class QVideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(QVideoApplication.class, args);
    }
}
