package icu.chiou.qvideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.chiou.qvideo.entity.PageVO;
import icu.chiou.qvideo.entity.Vlog;
import icu.chiou.qvideo.entity.VlogBO;
import icu.chiou.qvideo.entity.VlogVO;

/**
 * <p>
 * 短视频表 服务类
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
public interface VlogService extends IService<Vlog> {

    /**
     * 保存视频信息
     */
    void publishVideo(VlogBO vlogBO);

    /**
     * 获取视频列表
     */
    PageVO getVideoList(Integer page, Integer pageSize, String userId, String search);

    /**
     * 获取视频详细详细
     */
    VlogVO getVlogDetailByCondition(String userId, String vlogId);

    /**
     * 修改视频状态
     */
    void changeToPrivateOrPublic(String userId, String vlogId, int type);
}
