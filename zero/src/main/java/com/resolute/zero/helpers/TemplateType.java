package com.resolute.zero.helpers;

public enum TemplateType {
    EMAIL("email"), WHATSAPP("whatsapp"),SMS("sms");
    final String type;

    TemplateType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
