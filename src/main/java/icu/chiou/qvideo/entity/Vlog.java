package icu.chiou.qvideo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 短视频表
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Vlog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 对应用户表id，vlog视频发布者
     */
    private String vlogerId;

    /**
     * 视频播放地址
     */
    private String url;

    /**
     * 视频封面
     */
    private String cover;

    /**
     * 视频标题，可以为空
     */
    private String title;

    /**
     * 视频width
     */
    private Integer width;

    /**
     * 视频height
     */
    private Integer height;

    /**
     * 点赞总数
     */
    private Integer likeCounts;

    /**
     * 评论总数
     */
    private Integer commentsCounts;

    /**
     * 是否私密，用户可以设置私密，如此可以不公开给比人看
     */
    private Integer isPrivate;

    /**
     * 创建时间 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 更新时间 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


}
