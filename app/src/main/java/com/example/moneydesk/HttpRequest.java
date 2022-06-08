package com.example.moneydesk;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequest {
    private String[] result = new String[1];
    private String host = "194.87.197.6";
    private int port = 3000;
    private String baseUrl;

    @SuppressLint("DefaultLocale")
    public HttpRequest() {
        baseUrl = String.format("http://%s:%d/rpc/", host, port);
    }

    public void post(String endUrl, String payload) {
        Log.e(TAG, "endUrl: " + endUrl);

        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(baseUrl + endUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setDoOutput(true);

                OutputStream out_stream = connection.getOutputStream();

                byte[] out = payload.getBytes(StandardCharsets.UTF_8);
                out_stream.write(out);

                InputStream in_stream = connection.getInputStream();
                byte[] in = new byte[1024];

                String data = "";
                while (true) {
                    int len = in_stream.read(in);
                    if(len < 0) break;

                    data += new String(in,0,len);
                }
                //Log.i(TAG, "post: " + data);
                this.result[0] = data;
                out_stream.close();
                in_stream.close();
                connection.disconnect();
            }
            catch (Exception ex) {
                Log.e("Error", ex.toString());
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Log.e(TAG, "post: " + ex.getMessage());
        }
    }

    public String getData(){
        Log.i(TAG, "getData: " + result[0]);
        return result[0];
    }
}
