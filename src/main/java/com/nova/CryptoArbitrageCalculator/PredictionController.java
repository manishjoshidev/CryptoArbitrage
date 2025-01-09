package com.nova.CryptoArbitrageCalculator;

import com.nova.CryptoArbitrageCalculator.AIModelTrainer;
import com.nova.CryptoArbitrageCalculator.BinanceDataFetcher;
import com.nova.CryptoArbitrageCalculator.DataPreprocessor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PredictionController {
    private final BinanceDataFetcher fetcher = new BinanceDataFetcher();
    private final DataPreprocessor preprocessor = new DataPreprocessor();
    private final AIModelTrainer trainer = new AIModelTrainer();

    @GetMapping("/predictions")
    public String showPredictions(Model model) {
        String rawData = fetcher.fetchHistoricalData();
        List<Double> normalizedData = preprocessor.normalize(parsePrices(rawData));
        double prediction = trainer.predict(normalizedData);

        model.addAttribute("prediction", prediction);
        return "predictions";
    }

    private List<Double> parsePrices(String rawData) {
        // Implement JSON parsing logic to extract prices
        return new ArrayList<>();
    }
}
