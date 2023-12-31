package com.timely.demo.common.config.security.filter;

import com.timely.demo.common.config.properties.AppProperties;
import com.timely.demo.common.config.redis.RedisService;
import com.timely.demo.common.config.security.AuthTokenProvider;
import com.timely.demo.common.config.security.model.AuthToken;
import com.timely.demo.common.utils.MyHeaderUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
form 인증이 아닐 때 인증을 시도하는 필터이다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final MyHeaderUtils headerUtils;
    private final AuthTokenProvider tokenProvider;
    private final RedisService redisService;
    private final AppProperties appProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        String token = headerUtils.getAccessToken(req);
        log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 추출 token: {}", token);

        log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 유효성 체크 시작");
        if(StringUtils.hasText(token)) { /* 요청 헤더에 토큰이 있으면 로그인 처리를 한다 */
            AuthToken authToken = tokenProvider.convertAuthToken(token, appProperties.getAccessTokenKey());

            if(authToken.validate()) { /* 토큰 유효성 검사 */
                String blackAccessTokenKey = String.format("%s:%s", appProperties.getAuth().getRedisAccessBlackKey(), token);
                String isLogout = redisService.getValues(blackAccessTokenKey);

                /* 사용자 인증정보 설정 */
                if(ObjectUtils.isEmpty(isLogout)) {
                    Authentication authentication = tokenProvider.getAuthentication(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 유효성 체크 완료");
                }
            }
        }
        filterChain.doFilter(req, res); /* 요청 헤더에 토큰이 없으면 그냥 지나간다 */
    }
}