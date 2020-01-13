package com.example.fuellog;


import java.text.DecimalFormat;

public class Calculator {
    public DecimalFormat results = new DecimalFormat("###,###.##");
    // Available operations
    public enum Operator {CostPerGallon, CostPerMile, MilesPerGallon, KMPerLitre, APIKPL, CostPerLitre, CostPerKM, MilesPerLitre, KMPerGallon, KM, Litre}


    public String CostPerGallon(double firstOperand, double secondOperand) {
        return "£" + " " + (results.format(firstOperand / secondOperand));
    }
    public String CostPerLitre(double firstOperand, double secondOperand) {
        return "£" + " " + (results.format(firstOperand / (secondOperand * 4.54)));
    }

    public String CostPerMile(double firstOperand, double secondOperand) {
        return "£" + " " +(results.format(firstOperand / secondOperand));
    }
    public String CostPerKM(double firstOperand, double secondOperand) {
        return "£" + " " +(results.format(firstOperand / (secondOperand * 1.6)));
    }

    public String MilesPerGallon(double firstOperand, double secondOperand) {
        return (results.format(firstOperand / secondOperand))+ " " +"Miles/Gallon";
    }
    public String MilesPerLitre(double firstOperand, double secondOperand) {
        return (results.format(firstOperand / (secondOperand * 4.54)))+ " " +"Miles/Gallon";
    }

    public String KMPerLitre(double firstOperand, double secondOperand) {
        return (results.format((firstOperand * 1.6) / (secondOperand * 4.54)))+ " " +"KM/Litre";
    }
    public String KMPerGallon(double firstOperand, double secondOperand) {
        return (results.format((firstOperand * 1.6) / (secondOperand)))+ " " +"KM/Gallon";
    }
    public String APIKPL (double firstOperand) {
        return (results.format(firstOperand / 2.352))+ " " +"KM/Litre";
    }

    public String KM (double firstOperand) {
        return (results.format(firstOperand *1.6))+ " " +"KM";
    }
    public String Litre (double firstOperand) {
        return (results.format(firstOperand *3.7))+ " " +"Litres";
    }



}

