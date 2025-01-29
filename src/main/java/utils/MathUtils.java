package com.nova.CryptoArbitrageCalculator.prediction.utils;

import java.util.List;

public class MathUtils {

    public static double calculateAverage(List<Double> data) {
        return data.stream().mapToDouble(v -> v).average().orElse(0.0);
    }

    public static double calculateStandardDeviation(List<Double> data) {
        double mean = calculateAverage(data);
        double variance = data.stream()
                .mapToDouble(v -> Math.pow(v - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    public static double calculatePercentageChange(double oldPrice, double newPrice) {
        return ((newPrice - oldPrice) / oldPrice) * 100;
    }
}
