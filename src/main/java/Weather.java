import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    // 4d13927ca3671dd2f2d5d3fca66f490a
    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=4d13927ca3671dd2f2d5d3fca66f490a");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";

        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray jsonArray = object.getJSONArray("weather");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setName(obj.getString("main"));
        }


        return "Небо: " + model.getName() + "\n" +
                "Температура: " + String.valueOf(model.getTemp()).substring(0, 2) + " °C" + "\n" +
                "Влажность: " + model.getHumidity() + "%" + "\n" +
                "http://openweathermap.org/img/wn/" + model.getIcon() + ".png";
    }
}
