package icu.chiou.qvideo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.chiou.qvideo.entity.PageVO;
import icu.chiou.qvideo.entity.Vlog;
import icu.chiou.qvideo.entity.VlogBO;
import icu.chiou.qvideo.entity.VlogVO;
import icu.chiou.qvideo.mapper.VlogMapper;
import icu.chiou.qvideo.service.VlogService;
import icu.chiou.qvideo.utils.IDGeneratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 短视频表 服务实现类
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
@Service
public class VlogServiceImpl extends ServiceImpl<VlogMapper, Vlog> implements VlogService {

    @Autowired
    @Lazy
    VlogService vlogService;

    @Autowired
    VlogMapper vlogMapper;

    @Override
    public void publishVideo(VlogBO vlogBO) {
        //校验BO
        Vlog vlog = new Vlog();
        BeanUtils.copyProperties(vlogBO, vlog);
        vlog.setId(IDGeneratorUtils.getID() + "");
        vlog.setCommentsCounts(0);
        vlog.setLikeCounts(0);
        vlog.setIsPrivate(0);
        Date date = new Date();
        vlog.setCreatedTime(date);
        vlog.setUpdatedTime(date);

        //保存
        vlogService.save(vlog);
    }

    @Override
    public PageVO getVideoList(Integer pageStart, Integer pageSize, String userId, String search) {
        IPage<VlogVO> page = new Page<>(pageStart, pageSize);

        Map<String, Object> paramMap = new HashMap<>();
        if (!StringUtils.isBlank(search)) {
            paramMap.put("search", search);
        }

        IPage<VlogVO> videoList = vlogMapper.getVideoList(page, paramMap);

        PageVO pageVO = new PageVO();
        pageVO.setPage(pageStart);
        pageVO.setRecords(videoList.getTotal());//查询到的数据列表
        pageVO.setTotal(videoList.getPages());//总页数
        pageVO.setRows(videoList.getRecords());//总数据条数
        return pageVO;
    }

    @Override
    public VlogVO getVlogDetailByCondition(String userId, String vlogId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("vlogId", vlogId);

        return vlogMapper.queryVlogDetail(paramMap);
    }

    @Override
    public void changeToPrivateOrPublic(String userId, String vlogId, int type) {
        Vlog vlog = new Vlog();
        vlog.setId(vlogId);
        vlog.setVlogerId(userId);
        vlog.setIsPrivate(type);
        vlogMapper.updateVlogById(vlog);
    }
}
