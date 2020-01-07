package com.example.fuellog;


import java.text.DecimalFormat;

public class Calculator {
    public DecimalFormat results = new DecimalFormat("###,###.##");
    // Available operations
    public enum Operator {CostPerGallon, CostPerMile, MilesPerGallon, KMPerLitre, APIKPL}

     // CostPerGallon operation

    public String CostPerGallon(double firstOperand, double secondOperand) {
        return "£" + " " + (results.format(firstOperand / secondOperand));
    }

     // CostPerMile operation

    public String CostPerMile(double firstOperand, double secondOperand) {
        return "£" + " " +(results.format(firstOperand / secondOperand));
    }

     // MilesPerGallon operation

    public String MilesPerGallon(double firstOperand, double secondOperand) {
        return (results.format(firstOperand / secondOperand))+ " " +"miles";
    }

     // KMPerLitre operation

    public String KMPerLitre(double firstOperand, double secondOperand) {
        return (results.format((firstOperand * 1.6) / (secondOperand * 4.54)))+ " " +"KM";
    }
    public String APIKPL (double firstOperand) {
        return (results.format(firstOperand / 2.352))+ " " +"KM";
    }


}

