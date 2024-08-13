package vn.unigap.common.logger.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class LoggerTelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final TelegramConfig telegramConfig;

    @Autowired
    public LoggerTelegramBot(TelegramConfig telegramConfig) {
        this.telegramConfig = telegramConfig;
        telegramClient = new OkHttpTelegramClient(telegramConfig.getBotToken());
    }

    @Override
    public String getBotToken() {
        assert telegramConfig != null;
        return telegramConfig.getBotToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        return;
    }

    public void log(String message) {
        String chatId = telegramConfig.getChatId();
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(message).build();
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
