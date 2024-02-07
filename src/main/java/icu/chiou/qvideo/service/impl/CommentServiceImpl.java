package icu.chiou.qvideo.service.impl;

import icu.chiou.qvideo.entity.Comment;
import icu.chiou.qvideo.mapper.CommentMapper;
import icu.chiou.qvideo.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
