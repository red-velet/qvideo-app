package icu.chiou.qvideo.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 评论的视频是哪个作者（vloger）的关联id
     */
    private String vlogerId;

    /**
     * 如果是回复留言，则本条为子留言，需要关联查询
     */
    private String fatherCommentId;

    /**
     * 回复的那个视频id
     */
    private String vlogId;

    /**
     * 发布留言的用户id
     */
    private String commentUserId;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言的点赞总数
     */
    private Integer likeCounts;


}
