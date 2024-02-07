package icu.chiou.qvideo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 通行证接口内的异常
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportException extends RuntimeException {

    private static final long serialVersionUID = 2068512265475357432L;
    private Integer status;

    private String msg;

}
