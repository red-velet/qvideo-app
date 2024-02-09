package icu.chiou.qvideo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.chiou.qvideo.entity.PageVO;
import icu.chiou.qvideo.entity.Vlog;
import icu.chiou.qvideo.entity.VlogBO;
import icu.chiou.qvideo.entity.VlogVO;
import icu.chiou.qvideo.service.VlogService;
import icu.chiou.qvideo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 短视频表 前端控制器
 * </p>
 *
 * @author qxy
 * @since 2024-02-07
 */
@Slf4j
@RestController
@RequestMapping("/vlog")
public class VlogController {
    @Autowired
    private VlogService vlogService;

    /**
     * 保存上传视频的信息到库
     *
     * @param vlogBO 上传视频的信息
     * @return 保存结果
     */
    @PostMapping("/publish")
    public R publish(@RequestBody VlogBO vlogBO) {
        log.debug("path:{} -> params{}", "/vlog/publish", vlogBO);
        vlogService.publishVideo(vlogBO);
        return R.ok();
    }

    /**
     * 首页视频列表展示
     *
     * @param userId   用户id
     * @param search   搜索条件
     * @param page     分页起始
     * @param pageSize 页条数限制
     * @return 查询结果
     */
    @GetMapping("indexList")
    public R indexList(@RequestParam(required = false, defaultValue = "") String userId,
                       @RequestParam(required = false, defaultValue = "") String search,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer pageSize) {

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        PageVO data = vlogService.getVideoList(page, pageSize, userId, search);

        return R.ok(data);
    }

    /**
     * 获取视频详细详信息
     *
     * @param userId 用户id
     * @param vlogId 视频id
     * @return 查询结果
     */
    @GetMapping("detail")
    public R vlogDetail(@RequestParam(required = false) String userId, String vlogId) {
        VlogVO data = vlogService.getVlogDetailByCondition(userId, vlogId);
        return R.ok(data);
    }

    /**
     * 更改作品状态: 公开->私密
     *
     * @param userId 用户id
     * @param vlogId 视频id
     * @return 查询结果
     */
    @PostMapping("changeToPrivate")
    public R changeToPrivate(@RequestParam String userId,
                             @RequestParam String vlogId) {
        vlogService.changeToPrivateOrPublic(userId, vlogId, 1);
        return R.ok();
    }

    /**
     * 更改作品状态: 私密->公开
     *
     * @param userId 用户id
     * @param vlogId 视频id
     * @return 查询结果
     */
    @PostMapping("changeToPublic")
    public R changeToPublic(@RequestParam String userId,
                            @RequestParam String vlogId) {
        vlogService.changeToPrivateOrPublic(userId, vlogId, 0);
        return R.ok();
    }

    /**
     * 获取公开作品列表
     *
     * @param userId   用户id
     * @param page     分页起始
     * @param pageSize 页条数限制
     * @return 查询结果
     */
    @GetMapping("myPublicList")
    public R myPublicList(@RequestParam String userId,
                          @RequestParam Integer page,
                          @RequestParam Integer pageSize) {

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        IPage<Vlog> iPage = new Page<>(page, pageSize);
        vlogService.page(
                iPage,
                new QueryWrapper<Vlog>()
                        .eq("vloger_id", userId)
                        .eq("is_private", 0)
                        .orderByDesc("created_time"));

        PageVO data = new PageVO();
        data.setPage(page);
        data.setTotal(iPage.getPages());
        data.setRecords(iPage.getTotal());
        data.setRows(iPage.getRecords());
        return R.ok(data);
    }

    /**
     * 获取私密作品列表
     *
     * @param userId   用户id
     * @param page     分页起始
     * @param pageSize 页条数限制
     * @return 查询结果
     */
    @GetMapping("myPrivateList")
    public R myPrivateList(@RequestParam String userId,
                           @RequestParam Integer page,
                           @RequestParam Integer pageSize) {

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        IPage<Vlog> iPage = new Page<>(page, pageSize);
        vlogService.page(
                iPage,
                new QueryWrapper<Vlog>()
                        .eq("vloger_id", userId)
                        .eq("is_private", 1)
                        .orderByDesc("created_time"));

        PageVO data = new PageVO();
        data.setPage(page);
        data.setTotal(iPage.getPages());
        data.setRecords(iPage.getTotal());
        data.setRows(iPage.getRecords());
        return R.ok(data);
    }
}
