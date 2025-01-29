package web;

import prediction.AIModelTrainer;
import prediction.BinanceDataFetcher;
import prediction.DataPreprocessor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PredictionController {
    private final BinanceDataFetcher fetcher = new BinanceDataFetcher("BTCUSDT", "1h");
    private final DataPreprocessor preprocessor = new DataPreprocessor();
    private final AIModelTrainer trainer = new AIModelTrainer();

    @GetMapping("/predictions")
    public String getPredictions(Model model) {
        String rawData = fetcher.fetchHistoricalData();
        // Parse rawData, normalize, train, and predict
        // Placeholder logic
        double prediction = Math.random();
        model.addAttribute("prediction", prediction);
        return "predictions";
    }
}
