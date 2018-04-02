package productions.ranuskin.meow.duotorial;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ran on 4/1/2018.
 */

public class HttpIO {
    private static OkHttpClient client = new OkHttpClient();

    public static String getJson(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
