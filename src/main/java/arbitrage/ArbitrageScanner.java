package arbitrage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;



import java.util.HashMap;
import java.util.Map;

public class ArbitrageScanner {

    private static final String BINANCE_API_URL = "https://api.binance.com/api/v3/ticker/price";
    private static final String SOLANA_API_URL = "https://public-api.solscan.io/market/token";
    private static final String SOLANA_API_TOKEN = "your-solana-api-token"; // Replace with your token

    // Binance Fees
    private static final double BINANCE_FEE = 0.001; // 0.1%
    private static final double SOLANA_FEE = 0.003; // 0.3%
    private static final double SOLANA_TRANSACTION_COST = 0.0001; // Example transaction cost

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        try {
            Map<String, Double> binancePrices = fetchBinancePrices();
            Map<String, Double> solanaPrices = fetchSolanaPrices();

            findArbitrageOpportunities(binancePrices, solanaPrices);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Double> fetchBinancePrices() throws Exception {
        Map<String, Double> prices = new HashMap<>();
        Request request = new Request.Builder()
                .url(BINANCE_API_URL)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            for (JsonNode node : rootNode) {
                String symbol = node.get("symbol").asText();
                if (symbol.endsWith("USDC")) {
                    double price = node.get("price").asDouble();
                    prices.put(symbol.replace("USDC", ""), price);
                }
            }
        } else {
            throw new Exception("Failed to fetch Binance prices: " + response.code());
        }
        return prices;
    }

    private static Map<String, Double> fetchSolanaPrices() throws Exception {
        Map<String, Double> prices = new HashMap<>();
        Request request = new Request.Builder()
                .url(SOLANA_API_URL)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("token", SOLANA_API_TOKEN)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            for (JsonNode tokenData : rootNode) {
                String symbol = tokenData.get("symbol").asText();
                double price = tokenData.get("price").asDouble();
                prices.put(symbol, price);
            }
        } else {
            throw new Exception("Failed to fetch Solana prices: " + response.code());
        }
        return prices;
    }

    public static void findArbitrageOpportunities(Map<String, Double> binancePrices, Map<String, Double> solanaPrices) {
        for (String token : binancePrices.keySet()) {
            if (solanaPrices.containsKey(token)) {
                double binancePrice = binancePrices.get(token);
                double solanaPrice = solanaPrices.get(token);

                // Calculate fees
                double binanceNetPrice = binancePrice * (1 - BINANCE_FEE);
                double solanaNetPrice = solanaPrice * (1 - SOLANA_FEE) - SOLANA_TRANSACTION_COST;

                // Check for arbitrage
                if (binanceNetPrice > solanaNetPrice) {
                    double profit = binanceNetPrice - solanaNetPrice;
                    System.out.printf("Arbitrage Opportunity: %s | Binance: %.6f | Solana: %.6f | Profit: %.6f%n",
                            token, binancePrice, solanaPrice, profit);
                }
            }
        }
    }
}
