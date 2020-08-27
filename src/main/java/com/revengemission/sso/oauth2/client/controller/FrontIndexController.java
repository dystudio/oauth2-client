package com.revengemission.sso.oauth2.client.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FrontIndexController {

    RestTemplate restTemplate;

    public FrontIndexController(RestTemplateBuilder restTemplateBuilder) {
        super();
        //可以全局配置converter等
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping(value = {"/", "/index"})
    public String index(HttpServletRequest request,
                        Authentication authentication,
                        Model model) {
        return "index";
    }

    @GetMapping(value = "/user")
    public String user(HttpServletRequest request,
                       OAuth2AuthenticationToken oAuth2AuthenticationToken,
                       @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                       Model model) {
        return "securedPage";
    }

    @ResponseBody
    @GetMapping(value = "/resource")
    public Object resource(HttpServletRequest request,
                           OAuth2AuthenticationToken oAuth2AuthenticationToken,
                           @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                           Model model) {
        String url = "https://resource-server.apps.dhccloud.com.cn/coupon/list";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authorizedClient.getAccessToken().getTokenValue());
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class);
        return response.getBody();
    }

}
