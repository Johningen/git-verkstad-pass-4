package se.fjax26.maskinpark.records;

public record Tillagg(boolean galler, double perTimme, double fastAvgift) {

    public static Tillagg perTimme(boolean galler, double belopp) {
        return new Tillagg(galler, belopp, 0);
    }

    public static Tillagg fast(boolean galler, double belopp) {
        return new Tillagg(galler, 0, belopp);
    }

    public double kostnad(double timmar) {
        return perTimme * timmar + fastAvgift;
    }
}