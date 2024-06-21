package com.resolute.zero.whatsapp;

import com.whatsapp.api.WhatsappApiFactory;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WhConfig {

    @Value("${whatsapp.key}")
    private String apiKey;
    @Bean
    public WhatsappBusinessCloudApi api(){
        WhatsappApiFactory factory = WhatsappApiFactory.newInstance(apiKey);
        return factory.newBusinessCloudApi();
    }

}
