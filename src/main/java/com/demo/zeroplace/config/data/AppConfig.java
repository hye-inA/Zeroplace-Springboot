package com.demo.zeroplace.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "zerospace")
public class AppConfig {

    public String jwtKey;

}
