package arbitrage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BinanceAPI {

    private static final String API_URL = "https://api.binance.com/api/v3/ticker/price\n";

    // Method to fetch raw market data as a String
    public String fetchMarketData() {
        String apiUrl = "https://public-api.solscan.io/market/token"; // Replace with your correct API endpoint
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true); // Enable automatic redirect following
            connection.setRequestProperty("accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } else if (responseCode == 308) {
                String newUrl = connection.getHeaderField("Location");
                System.out.println("Redirected to: " + newUrl);
                if (newUrl != null) {
                    // Make a new request to the redirected URL
                    return fetchMarketData(newUrl);
                } else {
                    System.err.println("No 'Location' header found for redirect.");
                }
            } else {
                System.err.println("Error: Received HTTP " + responseCode + " from API");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Overloaded method to handle redirected URLs
    public String fetchMarketData(String redirectedUrl) {
        try {
            URL url = new URL(redirectedUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } else {
                System.err.println("Error: Received HTTP " + responseCode + " from redirected API");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Method to fetch prices as a Map (processed from the raw data)
    public Map<String, Double> fetchPrices() {
        String marketData = fetchMarketData();
        if (marketData == null) {
            System.err.println("Failed to fetch market data.");
            return new HashMap<>();
        }

        try {
            // Parse the market data (adjust based on API response format)
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(marketData);

            // Extract prices
            Map<String, Double> prices = new HashMap<>();
            for (JsonNode tokenData : rootNode) {
                if (tokenData.has("symbol") && tokenData.has("price")) { // Check if fields exist
                    String symbol = tokenData.get("symbol").asText();
                    double price = tokenData.get("price").asDouble();
                    prices.put(symbol, price);
                } else {
                    System.err.println("Expected fields 'symbol' or 'price' not found in response.");
                }
            }
            return prices;
        } catch (Exception e) {
            System.err.println("Exception while parsing market data: " + e.getMessage());
            e.printStackTrace();
        }
        return new HashMap<>();
    }

}

