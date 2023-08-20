package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Fraction {
    protected int numerator;
    protected int denominator;

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        if (denominator != 0) {
            this.denominator = denominator;
        } else {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
    }

    public double toDecimal() {
        return (double) numerator / denominator;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}

class RepeatingDecimalFraction extends Fraction {
    private int repeatingPart;

    public RepeatingDecimalFraction(int integerPart, int nonRepeatingPart, int repeatingPart, int denominator) {
        super(integerPart * denominator + nonRepeatingPart, denominator);
        this.repeatingPart = repeatingPart;
    }

    public double toDecimal(int decimalPlaces) {
        BigDecimal bdNumerator = BigDecimal.valueOf(numerator);
        BigDecimal bdDenominator = BigDecimal.valueOf(denominator);
        BigDecimal result = bdNumerator.divide(bdDenominator, decimalPlaces, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    @Override
    public String toString() {
        return numerator + "." + repeatingPart + " (" + numerator + "/" + denominator + ")";
    }
}

class CompoundRepeatingDecimalFraction extends Fraction {
    private int nonRepeatingPart;
    private int repeatingPart;

    public CompoundRepeatingDecimalFraction(int integerPart, int nonRepeatingPart, int repeatingPart, int denominator) {
        super(integerPart * denominator + nonRepeatingPart, denominator);
        this.nonRepeatingPart = nonRepeatingPart;
        this.repeatingPart = repeatingPart;
    }

    public double toDecimal(int decimalPlaces) {
        BigDecimal bdIntegerPart = BigDecimal.valueOf(numerator / denominator);
        BigDecimal bdNonRepeatingPart = BigDecimal.valueOf(nonRepeatingPart);
        BigDecimal bdRepeatingPart = BigDecimal.valueOf(repeatingPart);

        BigDecimal result = bdIntegerPart
                .add(bdNonRepeatingPart.divide(BigDecimal.valueOf(denominator), decimalPlaces, RoundingMode.HALF_UP))
                .add(bdRepeatingPart.divide(BigDecimal.valueOf(denominator), decimalPlaces, RoundingMode.HALF_UP));

        return result.doubleValue();
    }

    @Override
    public String toString() {
        return numerator + "." + nonRepeatingPart + "(" + repeatingPart + ")" + " (" + numerator + "/" + denominator + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        // Exemplo de fração geratriz de dízima periódica simples
        RepeatingDecimalFraction simpleRepeatingFraction = new RepeatingDecimalFraction(0, 2, 3, 9);
        System.out.println("Fração geratriz de dízima periódica simples:");
        System.out.println(simpleRepeatingFraction);
        System.out.println("Valor decimal (3 casas decimais): " + simpleRepeatingFraction.toDecimal(3));
        System.out.println();

        // Exemplo de fração geratriz de dízima periódica composta
        CompoundRepeatingDecimalFraction compoundRepeatingFraction = new CompoundRepeatingDecimalFraction(3, 1, 4, 11);
        System.out.println("Fração geratriz de dízima periódica composta:");
        System.out.println(compoundRepeatingFraction);
        System.out.println("Valor decimal (4 casas decimais): " + compoundRepeatingFraction.toDecimal(4));
    }
}

