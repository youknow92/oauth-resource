package com.example.oauthresource.configuration;

import com.example.oauthresource.vo.JWTKey;
import com.google.gson.Gson;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.StringUtils;

@EnableResourceServer
@Configuration
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String publicKeyUri;

    // API 별 필요한 인증정보 설정
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/main")
                .access("#oauth2.hasAnyScope('read')")
                .anyRequest()
                .authenticated();
    }

//    // 토큰 유효성 체크
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setClientId("clientId");
//        remoteTokenServices.setClientSecret("secretKey");
//        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:8081/oauth/check_token");
//        resources.tokenServices(remoteTokenServices);
//    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        try {
            /**
             * 공개키를 직접 파일로 만들어 읽어서 jwt 디코드 키 등록
             */
//            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//            Resource resource = new ClassPathResource("key.txt");
//            converter.setVerifierKey(IOUtils.toString(resource.getInputStream()));
//            return converter;

            /**
             * 직접 oauth 서버를 호출하여 공개키 읽어서 jwt 디코드 키 등록
             */
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setVerifierKey(getPublicKeyValue(publicKeyUri));
            return converter;

        } catch (Exception e) {
            return new JwtAccessTokenConverter();
        }
    }

    private String getPublicKeyValue(String uriKey) {
        JsonNode response = Unirest.get(uriKey)
                .asJson().getBody();
        return StringUtils.isEmpty(response.toString()) ? "" : new Gson().fromJson(response.toString(), JWTKey.class).getValue();
    }
}
