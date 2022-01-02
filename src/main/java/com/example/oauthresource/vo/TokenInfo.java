package com.example.oauthresource.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TokenInfo {
    private String username;
    private String corinNickName;
    private String corinId;
    private String clientId;
    private List<String> authorities;
}
