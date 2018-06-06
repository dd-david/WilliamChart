package com.db.williamchartdemo.data;

public class DataGenerator {

    // return pdf(x) = standard Gaussian pdf
    public static double pdf(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    // return pdf(x, mu, signma) = Gaussian pdf with mean mu and stddev sigma
    public static double pdf(double x, double mu, double sigma) {
        return pdf((x - mu) / sigma) / sigma;
    }

    public static float[] randomGaussianData(int count) {
        float[] arr = new float[count * 2 + 1];

        int index = 0;
        for (int i = -count; i <= count; i++) {
            double val = pdf(i, 0, 30) * 100000000;
            arr[index++] = (float) val;
        }
        return arr;
    }

    public static String[] labels(int count) {
        String[] labels = new String[count * 2 + 1];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = "";
        }
        return labels;
    }

}
