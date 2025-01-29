package com.nova.CryptoArbitrageCalculator;

import arbitrage.ArbitrageScanner;
import arbitrage.BinanceAPI;
import arbitrage.SolanaAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class CryptoArbitrageCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoArbitrageCalculatorApplication.class, args);

		// Create instances of necessary components
		BinanceAPI binanceAPI = new BinanceAPI();
		SolanaAPI solanaAPI = new SolanaAPI();
		ArbitrageScanner scanner = new ArbitrageScanner();

		// Fetch prices
		Map<String, Double> binancePrices = binanceAPI.fetchPrices(); // Ensure this method exists
		Map<String, Double> solanaPrices = solanaAPI.fetchPrices();   // Uses the fetchMarketData() internally

		// Find arbitrage opportunities
		scanner.findArbitrageOpportunities(binancePrices, solanaPrices);
	}
}
