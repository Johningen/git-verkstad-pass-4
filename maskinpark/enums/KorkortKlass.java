package se.fjax26.maskinpark.enums;

import java.util.Arrays;

public enum KorkortKlass {
    C,
    CE,
    TRAKTOR;

    public static KorkortKlass fran(String text){
        return Arrays.stream(values())
                .filter(k -> k.name().equalsIgnoreCase(text == null ? "" : text.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Okänd korkortsklass: " + text));
    }
}
