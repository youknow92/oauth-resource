package com.example.oauthresource.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {

    // API 별 필요한 인증정보 설정
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/main")
                .access("#oauth2.hasAnyScope('read')")
                .anyRequest()
                .authenticated();
    }

    // 토큰 유효성 체크
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setClientId("clientId");
        remoteTokenServices.setClientSecret("secretKey");
        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:8081/oauth/check_token");
        resources.tokenServices(remoteTokenServices);
    }
}
