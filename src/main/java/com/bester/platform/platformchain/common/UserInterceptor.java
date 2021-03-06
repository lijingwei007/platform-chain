package com.bester.platform.platformchain.common;

import com.auth0.jwt.interfaces.Claim;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.util.TokenUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author liuwen
 * @date 2018/11/6
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    try {
                        Map<String, Claim> claimMap = TokenUtil.verifyToken(token);
                        String userId = claimMap.get("userId").asString();
                        request.setAttribute("userId", userId);
                        Map<String, String> data = Maps.newHashMap();
                        data.put("userId", userId);
                        TokenUtil.updateToken2Cookie(response, data);
                        return true;
                    } catch (Exception e) {
                        LOGGER.error("token验证失败！", e);
                    }
                }
            }
        }
        response.sendError(HttpStatus.UNAUTHORIZED.value, HttpStatus.UNAUTHORIZED.message);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex) {
    }

}
