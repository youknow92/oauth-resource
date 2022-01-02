package com.example.oauthresource.util;

import com.example.oauthresource.vo.TokenInfo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class CommonUtils {
    public TokenInfo getAccessTokenInfo(HttpServletRequest requset) {
        TokenInfo tokenInfo = null;
        String authorization = requset.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.startsWithIgnoreCase(authorization, "Bearer")) {
            Jwt jwt = JwtHelper.decode(StringUtils.replace(authorization, "Bearer ", ""));
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            tokenInfo = gson.fromJson(jwt.getClaims(), TokenInfo.class);
        }

        return tokenInfo;
    }
}
