package com.example.fuellog;


import java.text.DecimalFormat;

public class Calculator {
 ;
    // Available operations
    public enum Operator {CostPerGallon, CostPerMile, MilesPerGallon, KMPerLitre, APIKPL}

     // CostPerGallon operation

    public double CostPerGallon(double firstOperand, double secondOperand) {
        return firstOperand / secondOperand;
    }

     // CostPerMile operation

    public double CostPerMile(double firstOperand, double secondOperand) {
        return  firstOperand / secondOperand;
    }

     // MilesPerGallon operation

    public double MilesPerGallon(double firstOperand, double secondOperand) {
        return firstOperand / secondOperand;
    }

     // KMPerLitre operation

    public double KMPerLitre(double firstOperand, double secondOperand) {
        return (firstOperand * 1.6) / (secondOperand * 4.54);
    }
    public double APIKPL (double firstOperand) {
        return (firstOperand / 2.352);
    }


}

