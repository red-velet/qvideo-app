package icu.chiou.qvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 业务对象-被修改的用户信息
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatedUserBO {
    private String id;
    private String nickname;
    private String imoocNum;
    private String face;
    private Integer sex;
    private String birthday;
    private String country;
    private String province;
    private String city;
    private String district;
    private String description;
    private String bgImg;
    private Integer canImoocNumBeUpdated;
}
