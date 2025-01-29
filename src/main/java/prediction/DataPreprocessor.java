package prediction;

import java.util.ArrayList;
import java.util.List;

public class DataPreprocessor {
    public List<Double> normalize(List<Double> prices) {
        double max = prices.stream().mapToDouble(v -> v).max().orElse(1);
        double min = prices.stream().mapToDouble(v -> v).min().orElse(0);

        List<Double> normalized = new ArrayList<>();
        for (double price : prices) {
            normalized.add((price - min) / (max - min));
        }
        return normalized;
    }
}
