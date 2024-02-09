package icu.chiou.qvideo.entity;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/9
 */
@Data
public class PageVO {
    private int page;            // 当前页数
    private long total;            // 总页数
    private long records;        // 总记录数
    private List<?> rows;        // 每行显示的内容
}
