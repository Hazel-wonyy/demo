package com.timely.demo.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class OauthController {
    private final OauthService SERVICE;

    @GetMapping
    public String test() {
        return "test";
    }

    @GetMapping("/keke")
    public String test2() {
        return "이것도 테스트";
    }
}
