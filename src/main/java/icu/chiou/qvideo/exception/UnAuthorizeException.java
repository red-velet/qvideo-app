package icu.chiou.qvideo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 自定义未认证异常
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnAuthorizeException extends RuntimeException {
    private static final long serialVersionUID = -7490373545048540073L;
    private Integer status;

    private String msg;
}
