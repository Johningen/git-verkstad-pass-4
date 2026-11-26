package se.fjax26.maskinpark.enums;

import java.util.Locale;

public enum Terrang {
    NORMAL(0.0),
    BERG(110.0),
    LERA(65.0);

    private final double kostnadPerTimme;

    Terrang(double kostnadPerTimme) {
        this.kostnadPerTimme = kostnadPerTimme;
    }

    public double kostnad(double timmar) {
        return kostnadPerTimme * timmar;
    }

    public static Terrang fran(String text) {
        if (text == null) return NORMAL;
        return switch (text.trim().toUpperCase(Locale.ROOT)) {
            case "BERG" -> BERG;
            case "LERA", "LERMARK" -> LERA;
            default -> NORMAL;
        };
    }
}