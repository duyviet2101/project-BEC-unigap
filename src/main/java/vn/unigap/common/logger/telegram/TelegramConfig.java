package vn.unigap.common.logger.telegram;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "telegram")
public class TelegramConfig {
    private String botToken = "";
    private String chatId = "";
}
