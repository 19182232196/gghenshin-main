package com.gghenshinn.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {
String adminSecretKey;

    private long adminTtl;
    private String adminTokenName;

    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
