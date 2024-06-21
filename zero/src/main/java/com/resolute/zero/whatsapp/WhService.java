package com.resolute.zero.whatsapp;

import com.whatsapp.api.domain.messages.Language;
import com.whatsapp.api.domain.messages.Message;
import com.whatsapp.api.domain.messages.TemplateMessage;
import com.whatsapp.api.domain.templates.type.LanguageType;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhService {
    @Autowired
    private WhatsappBusinessCloudApi whatsappBusinessCloudApi;
    public void sendHelloWorldMessage(String toNumber){
        var message = Message.MessageBuilder.builder()
                .setTo(toNumber)
                .buildTemplateMessage(
                        new TemplateMessage()
                                .setLanguage(new Language(LanguageType.EN_US))
                                .setName(WhConstants.HELLO_WORLD_TEMPLATE)
                );

        whatsappBusinessCloudApi.sendMessage(WhConstants.PHONE_NO_ID, message);
    }
}
