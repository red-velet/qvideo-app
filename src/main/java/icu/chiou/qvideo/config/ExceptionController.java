package icu.chiou.qvideo.config;

import icu.chiou.qvideo.exception.PassportException;
import icu.chiou.qvideo.exception.UserException;
import icu.chiou.qvideo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 通行证异常-异常处理器
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
@Slf4j
@RestControllerAdvice(basePackages = "icu.chiou.qvideo")
public class ExceptionController {

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(PassportException.class)
    public R exceptionHandleAll(PassportException e) {
        log.error("code:{} msg:{}", e.getStatus(), e.getMsg());
        return R.error().status(e.getStatus()).msg(e.getMsg());
    }

    /**
     * 数据校验异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R exception(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        // 收集数据校验失败后的信息
        Map<String, String> data = new HashMap<>();
        bindingResult.getFieldErrors().stream()
                .forEach(
                        fieldError -> {
                            data.put(fieldError.getField(), fieldError.getDefaultMessage());
                        }
                );
        return R.error().data(data);
    }

    /**
     * 用户信息异常
     */
    @ExceptionHandler(UserException.class)
    public R exception(UserException e) {
        // 收集数据校验失败后的信息
        return R.error().status(e.getStatus()).msg(e.getMsg());
    }

    /**
     * 文件上传异常
     */
//    @ExceptionHandler(FileUploadBase.SizeLimitExceededException.class)
//    public R exception(FileUploadBase.SizeLimitExceededException e) {
//        // 收集文件上传大小超过限制异常
//        log.error("code:{} msg:{}", 500, e.getMessage());
//        return R.error().msg("请上传大小2MB大小的图片");
//    }
}
