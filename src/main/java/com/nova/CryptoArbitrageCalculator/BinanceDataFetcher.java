package com.nova.CryptoArbitrageCalculator;

import okhttp3.*;
import java.io.IOException;

public class BinanceDataFetcher {
    private static final String API_URL = "https://api.binance.com/api/v3/klines";
    private static final String SYMBOL = "BTCUSDT";
    private static final String INTERVAL = "1h";

    public String fetchHistoricalData() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = HttpUrl.parse(API_URL).newBuilder()
                .addQueryParameter("symbol", SYMBOL)
                .addQueryParameter("interval", INTERVAL)
                .addQueryParameter("limit", "1000")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new RuntimeException("Error fetching Binance data: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch Binance data", e);
        }
    }
}
