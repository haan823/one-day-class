package com.one.domain.sms.infrastructure;

import com.one.domain.sms.exception.SmsSendFailedException;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SmsSender {

    private final String apiKey;
    private final String apiSecret;
    private final String env;
    private final String fromNumber;

    public SmsSender(@Value("${sms.api-key}") final String apiKey,
                     @Value("${sms.api-secret}") final String apiSecret,
                     @Value("${spring.config.activate.on-profile}") final String env,
                     @Value("${sms.from}") final String fromNumber) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.env = env;
        this.fromNumber = fromNumber;
    }

    private final String TYPE = "SMS";

    public void send(final String phoneNumber, final String authenticationNumber) {
        final Message message = new Message(this.apiKey, this.apiSecret);
        try {
            if ("local".equals(env)) {
                return;
            }
            message.send(generateSmsInfo(phoneNumber, generateSmsContent(authenticationNumber)));
        } catch (CoolsmsException e) {
            throw new SmsSendFailedException();
        }
    }

    public String generateSmsContent(final String authenticationNumber) {
        final StringBuilder sb = new StringBuilder();
        return sb.append("인증번호[").append(authenticationNumber).append("]를 입력해주세요.").toString();
    }

    public HashMap<String, String> generateSmsInfo(final String phoneNumber, final String content) {
        final HashMap<String, String> smsInfo = new HashMap<>();
        smsInfo.put("to", phoneNumber);
        smsInfo.put("from", this.fromNumber);
        smsInfo.put("type", this.TYPE);
        smsInfo.put("text", content);
        return smsInfo;
    }


}
