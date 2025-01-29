package Backend.prediction;

import java.util.List;

public class BacktestingEngine {
    public double calculateAccuracy(List<Double> actual, List<Double> predicted) {
        int correct = 0;
        for (int i = 0; i < actual.size(); i++) {
            if ((actual.get(i) > 0 && predicted.get(i) > 0) ||
                    (actual.get(i) < 0 && predicted.get(i) < 0)) {
                correct++;
            }
        }
        return (double) correct / actual.size();
    }
}
