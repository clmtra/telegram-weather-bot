import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "Введите город, чтобы узнать погоду");
                    break;
                case "/settings":
                    sendMsg(message, "Введите город, чтобы узнать погоду");
                    break;
                    default:
                        try{
                            sendMsg(message, Weather.getWeather(message.getText(), model));
                        } catch (IOException e){
                            sendMsg(message, "Город не найден :(");
                        }
            }
        }
    }



    public void sendMsg(Message message, String text) { // reply method
        SendMessage sendMessage = new SendMessage(); // создание объекта ответа
        sendMessage.enableMarkdown(true); // создание разметки
        sendMessage.enableHtml(true);
        sendMessage.setChatId(message.getChatId().toString()); // определение ID написавшего пользователя
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(keyboardMarkup); // связь клавиатуры и сообщения. размещение под окном ввода
        keyboardMarkup.setSelective(true); // каким именно пользователям показывать клавиатуру?
        keyboardMarkup.setResizeKeyboard(true); // подгонять клавиатуру под размер
        keyboardMarkup.setOneTimeKeyboard(false); // не скрывать клавиатуру

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();

        firstRow.add(new KeyboardButton("/help"));
        firstRow.add(new KeyboardButton("/settings"));

        keyboardRowList.add(firstRow);
        keyboardMarkup.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return "dima_sic_bot";
    }

    public String getBotToken() {
        return "1256858454:AAEwkqYj7JKqsqR7RgU4lUd0lvGVBjsyO5A";
    }
}
