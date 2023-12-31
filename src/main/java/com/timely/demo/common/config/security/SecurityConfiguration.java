package com.timely.demo.common.config.security;

import com.timely.demo.common.config.properties.CorsProperties;
import com.timely.demo.common.config.security.filter.JwtAuthenticationFilter;
import com.timely.demo.common.config.security.handler.OAuth2AuthenticationFailureHandler;
import com.timely.demo.common.config.security.handler.OAuth2AuthenticationSuccessHandler;
import com.timely.demo.common.config.security.handler.TokenAccessDeniedHandler;
import com.timely.demo.common.config.security.oauth.CustomOAuth2UserService;
import com.timely.demo.common.config.security.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CorsProperties corsProperties;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository;

    /** 요청 권한 설정 **/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(authz ->
                                authz.requestMatchers(
                                                "/index.html"
                                                , "/"
                                                , "/swagger.html"
                                                , "/swagger-ui/**"
                                                , "/v3/api-docs/**"
                                                , "/**/oauth2/code/**"
                                                , "/oauth2/**"
                                                , "/oauth/**"
                                        ).permitAll()
                                        .requestMatchers("**exception**").permitAll()
                                        .anyRequest().permitAll()
                )
                /* 세션 사용 X */
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                /* HTTP 기본인증 비활성화 */
                .httpBasic(http -> http.disable())
                /* 폼기반 로그인 비활성화 */
                .formLogin(http -> http.disable())

                /* CSRF 보안이 필요 X, 쿠키와 세션을 이용해서 인증을 하고 있기 때문에 발생하는 일, https://kchanguk.tistory.com/197 */
                .csrf(csrf -> csrf.disable())

                /* 사용자 인증 및 접근 권한 예외처리 */
                .exceptionHandling(except -> {
                    except.accessDeniedHandler(tokenAccessDeniedHandler);
                    except.authenticationEntryPoint(new RestAuthenticationEntryPoint());
                })


                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization -> authorization.baseUri("/oauth2/authorization")
                                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/*/oauth2/code/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /** 사용자 정보 로그찍기**/
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            if (authentication instanceof OAuth2AuthenticationToken) {
                OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
                OAuth2User auth = oauth2Token.getPrincipal();
                log.info("사용자 정보 : {}", auth.getAttributes());
                System.out.println("사용자 정보: " + auth.getAttributes());
            }
            response.sendRedirect("/");
        };
    }

    /** 비밀번호 encoder**/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /** Oauth 인증 실패 핸들러 **/
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository);
    }

    /** Cors 설정 **/
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        /* 요청 헤더 설정*/
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        /* 허용된 HTTP메소드 설정*/
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        /* 허용된 출차(Origin) 설정*/
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        /* 자격 증명 허용*/
        corsConfig.setAllowCredentials(true);
        /* CORS 요청 유효기간 설정 */
        corsConfig.setMaxAge(corsConfig.getMaxAge());
        /* 모든 URL CORS 설정*/
        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
