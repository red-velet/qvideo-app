package icu.chiou.qvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 登陆注册的业务对象
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterLoginBO {
    
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机长度不正确")
    private String mobile;


    @NotBlank(message = "验证码不能为空")
    private String smsCode;
}
