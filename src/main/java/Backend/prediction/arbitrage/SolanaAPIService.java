package Backend.prediction.arbitrage;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class SolanaAPIService {

    private static final String SOLANA_RPC_URL = "https://solana.drpc.org"; // Solana RPC URL

    public String fetchBalance(String publicKey) {
        // Create JSON-RPC request body
        String jsonRpcRequest = String.format("{\"jsonrpc\":\"2.0\",\"method\":\"getBalance\",\"params\":[\"%s\"],\"id\":1}", publicKey);

        // Make the HTTP request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(jsonRpcRequest, headers);

        // Send POST request
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                SOLANA_RPC_URL, HttpMethod.POST, entity, JsonNode.class);

        // Extract balance data
        if (response.getStatusCode().is2xxSuccessful()) {
            JsonNode responseBody = response.getBody();
            if (responseBody != null && responseBody.has("result")) {
                JsonNode result = responseBody.get("result");
                return result.path("value").asText(); // Balance in Lamports (1 Sol = 10^9 Lamports)
            }
        }

        return "Failed to fetch balance.";
    }
}
