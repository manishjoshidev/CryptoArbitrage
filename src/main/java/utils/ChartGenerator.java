package com.nova.CryptoArbitrageCalculator.prediction.utils;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.List;

public class ChartGenerator {

    public static void generateLineChart(String fileName, List<Double> xData, List<Double> yData, String title, String xLabel, String yLabel) {
        // Create Chart
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(title)
                .xAxisTitle(xLabel)
                .yAxisTitle(yLabel)
                .theme(Styler.ChartTheme.Matlab)
                .build();

        chart.addSeries("Price", xData, yData);

        // Save chart as an image
        try {
            BitmapEncoder.saveBitmap(chart, fileName, BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }
}
