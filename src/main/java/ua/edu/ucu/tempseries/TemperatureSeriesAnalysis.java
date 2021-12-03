package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private static final int MIN_TEMP = -273;
    private double[] tempArr;
    private int tempCounter;

    public TemperatureSeriesAnalysis() {
        tempArr = new double[]{};
        tempCounter = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this();
        addTemps(temperatureSeries);
    }

    public double average() {
        doNotAllowEmpty();
        double total = tempArr[0];
        for (int i = 1; i < tempCounter; i++) {
            total += tempArr[i];
        }
        return total / tempCounter;
    }

    public double deviation() {
        doNotAllowEmpty();
        double total = 0;
        double mean = average();
        for (int i = 0; i < tempCounter; i++) {
            total += Math.pow(tempArr[i] - mean, 2);
        }
        return Math.sqrt(total / tempCounter);
    }

    public double min() {
        doNotAllowEmpty();
        double minTemp = tempArr[0];
        for (int i = 1; i < tempCounter; i++) {
            if (minTemp > tempArr[i]) {
                minTemp = tempArr[i];
            }
        }
        return minTemp;
    }

    public double max() {
        doNotAllowEmpty();
        double maxTemp = tempArr[0];
        for (int i = 1; i < tempCounter; i++) {
            if (maxTemp < tempArr[i]) {
                maxTemp = tempArr[i];
            }
        }
        return maxTemp;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        doNotAllowEmpty();
        double DELTA = .0000001;
        double closestTemp = tempArr[0];
        for (int i = 1; i < tempCounter; i++) {
            if (Math.abs(closestTemp - tempValue) > Math.abs(tempArr[i]
                    - tempValue) || Math.abs(Math.abs(closestTemp - tempValue)
                    - Math.abs(tempArr[i] - tempValue)) < DELTA && closestTemp <
                    tempArr[i]) {
                closestTemp = tempArr[i];
            }
        }
        return closestTemp;
    }

    public double[] findTempsLessThan(final double tempValue) {
        double[] actualTempArr = Arrays.copyOf(tempArr, tempCounter);
        return Arrays.stream(actualTempArr).filter(temp -> temp < tempValue
        ).toArray();
    }

    public double[] findTempsGreaterThan(double tempValue) {
        double[] actualTempArr = Arrays.copyOf(tempArr, tempCounter);
        return Arrays.stream(actualTempArr).filter(temp -> temp >= tempValue
        ).toArray();
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        for (double temp : temps) {
            if (temp < MIN_TEMP) {
                throw new InputMismatchException();
            }
        }
        if (tempArr.length == 0) {
            tempArr = temps.clone();
            tempCounter = tempArr.length;
            return tempCounter;
        }
        for (double temp : temps) {
            if (tempCounter == tempArr.length) {
                double[] newTempArr = new double[2 * tempCounter];
                System.arraycopy(tempArr, 0, newTempArr, 0, tempCounter);
                tempArr = newTempArr;
            }
            tempArr[tempCounter++] = temp;
        }
        return tempCounter;
    }

    public double[] getTempArr() {
        return Arrays.copyOf(tempArr, tempCounter);
    }

    private void doNotAllowEmpty() {
        if (tempCounter == 0) {
            throw new IllegalArgumentException();
        }
    }
}
