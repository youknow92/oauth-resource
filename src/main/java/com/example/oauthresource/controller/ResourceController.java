package com.example.oauthresource.controller;

import com.example.oauthresource.util.CommonUtils;
import com.example.oauthresource.vo.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class ResourceController {

    @Autowired
    private CommonUtils commonUtils;

    @RequestMapping("/main")
    public Object main(HttpServletRequest request) {
        TokenInfo tokenInfo = commonUtils.getAccessTokenInfo(request);
        return tokenInfo;
    }
}
