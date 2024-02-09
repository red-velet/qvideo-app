package icu.chiou.qvideo.interceptor;

import icu.chiou.qvideo.constants.RedisPrefix;
import icu.chiou.qvideo.constants.ResponseStatusEnum;
import icu.chiou.qvideo.exception.UnAuthorizeException;
import icu.chiou.qvideo.utils.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户会话信息拦截器
 * </p>
 *
 * @author red-velvet
 * @since 2024/2/8
 */

@Slf4j
public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //校验用户是否认证
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");

        String storeToken = redisService.get(RedisPrefix.TOKEN + userId);
        if (StringUtils.isBlank(storeToken)) {
            log.debug("UserTokenInterceptor: id={} token={} state={}", userId, userToken, "未登录");
            throw new UnAuthorizeException(ResponseStatusEnum.UN_LOGIN.status(), "请先进行登录再操作");
        } else {
            if (!storeToken.equals(userToken)) {
                log.debug("UserTokenInterceptor: id={} token={} state={}", userId, userToken, "会话过期");
                throw new UnAuthorizeException(ResponseStatusEnum.TICKET_INVALID.status(), "会话已过期,请重新登录");
            }
        }
        log.debug("UserTokenInterceptor: id={} token={} state={}", userId, userToken, "认证成功");
        return true;
    }

    public static Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();

        // 获取所有请求头的名称
        Enumeration<String> headerNames = request.getHeaderNames();

        // 遍历请求头名称并获取对应的值
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            // 将请求头名称和值存入HashMap
            headers.put(headerName, headerValue);
        }

        return headers;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
