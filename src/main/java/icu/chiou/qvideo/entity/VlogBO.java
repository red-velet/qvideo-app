package icu.chiou.qvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 业务对象接收视频详细信息
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VlogBO {
    private String id;
    private String vlogerId;
    private String url;
    private String cover;
    private String title;
    private Integer width;
    private Integer height;
    private Integer likeCounts;
    private Integer commentsCounts;
}
