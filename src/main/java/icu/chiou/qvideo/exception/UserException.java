package icu.chiou.qvideo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户接口内的异常
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserException extends RuntimeException {
    private static final long serialVersionUID = 8613794414894177119L;
    private Integer status;

    private String msg;
}
