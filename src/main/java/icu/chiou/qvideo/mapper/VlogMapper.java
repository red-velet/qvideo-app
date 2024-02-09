package icu.chiou.qvideo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.chiou.qvideo.entity.Vlog;
import icu.chiou.qvideo.entity.VlogVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 短视频表 Mapper 接口
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
public interface VlogMapper extends BaseMapper<Vlog> {

    /**
     * 分页查询视频列表
     */
    IPage<VlogVO> getVideoList(IPage<VlogVO> page, @Param("paramMap") Map<String, Object> paramMap);

    /**
     * 查询视频详细信息
     */
    VlogVO queryVlogDetail(@Param("paramMap") Map<String, Object> paramMap);

    /**
     * 修改视频信息
     */
    Integer updateVlogById(@Param("vlog") Vlog vlog);
}
