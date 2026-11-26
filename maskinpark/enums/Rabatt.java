package se.fjax26.maskinpark.enums;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;


public enum Rabatt {
    INGEN(pris -> pris),
    SOMMAR2026(pris -> pris * 0.9),
    PARTNER_FREE_SHIPPING(pris -> pris - 200);

    private final DoubleUnaryOperator berakning;

    Rabatt(DoubleUnaryOperator berakning){
        this.berakning = berakning;
    }

    public double tillampa(double pris){
        return berakning.applyAsDouble(pris);
    }

    public static Rabatt fran(String kod){
        if (kod == null || kod.isBlank()) return INGEN;
        return Arrays.stream(values())
                .filter(r -> r.name().equalsIgnoreCase(kod.trim()))
                .findFirst()
                .orElse(INGEN);
    }
}