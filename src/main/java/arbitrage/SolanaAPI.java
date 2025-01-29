package arbitrage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SolanaAPI {

    // Use a valid endpoint for fetching market data
    private static final String API_URL = "https://api.mainnet-beta.solana.com"; // Mainnet endpoint

    // Method to fetch raw market data as a String
    public String fetchMarketData() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); // POST request for JSON-RPC
            connection.setRequestProperty("Content-Type", "application/json");

            // Create JSON-RPC request body for fetching token accounts
            String jsonRpcRequest = "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"getTokenAccountsByOwner\",\"params\":[\"YourPublicKeyHere\",{\"encoding\":\"jsonParsed\"}]}";

            connection.setDoOutput(true);
            connection.getOutputStream().write(jsonRpcRequest.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } else {
                System.err.println("Error: Received HTTP " + responseCode + " from API");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
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
            JsonNode accountsNode = rootNode.path("result"); // Adjust based on actual response structure

            if (accountsNode.isArray()) {
                for (JsonNode accountData : accountsNode) {
                    JsonNode tokenInfo = accountData.path("account").path("data").path("parsed").path("info");
                    if (tokenInfo.has("symbol") && tokenInfo.has("price")) { // Check if fields exist
                        String symbol = tokenInfo.get("symbol").asText();
                        double price = tokenInfo.get("price").asDouble();
                        prices.put(symbol, price);
                    } else {
                        System.err.println("Expected fields 'symbol' or 'price' not found in response for accountData: " + accountData);
                    }
                }
            } else {
                System.err.println("Expected 'result' to be an array but got: " + accountsNode);
            }

            return prices;
        } catch (Exception e) {
            System.err.println("Exception while parsing market data: " + e.getMessage());
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
