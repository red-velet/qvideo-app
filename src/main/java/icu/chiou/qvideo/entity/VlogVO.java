package icu.chiou.qvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 前端展示视频
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VlogVO {
    private String vlogId;
    private String vlogerId;
    private String vlogerFace;
    private String vlogerName;
    private String content;
    private String url;
    private String cover;
    private Integer width;
    private Integer height;
    private Integer likeCounts;
    private Integer commentsCounts;
    private Integer isPrivate;
    private boolean isPlay = false;
    private boolean doIFollowVloger = false;
    private boolean doILikeThisVlog = false;
}
