package icu.chiou.qvideo.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 粉丝表


 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Fans implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 作家用户id
     */
    private String vlogerId;

    /**
     * 粉丝用户id
     */
    private String fanId;

    /**
     * 粉丝是否是vloger的朋友，如果成为朋友，则本表的双方此字段都需要设置为1，如果有一人取关，则两边都需要设置为0
     */
    private Integer isFanFriendOfMine;


}
