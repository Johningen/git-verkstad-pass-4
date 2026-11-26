package se.fjax26.maskinpark;

import se.fjax26.maskinpark.records.Tillagg;

import java.util.List;

public abstract class Anlaggningsmaskin {

    private String id;
    private String modell;
    private double timKostnad;
    private double drifttimmar;
    private double bransleNiva; // Procent eller Liter (här liter, max 200L)
    private String status;

    public Anlaggningsmaskin(String id, String modell, double timKostnad) {
        this.id = id;
        this.modell = modell;
        this.timKostnad = timKostnad;
        this.drifttimmar = 0.0;
        this.bransleNiva = 150.0; // Startar med 150L
        this.status = "REDO";
    }

    protected abstract List<Tillagg> tillagg();

    public double beraknaBasPris(double timmar) {
        double tillaggsKostnad = tillagg().stream()
                .filter(Tillagg::galler)
                .mapToDouble(t -> t.kostnad(timmar))
                .sum();
        return timmar * timKostnad + tillaggsKostnad;
    }

    public void tanka(double liter) {
        this.bransleNiva = Math.min(200.0, this.bransleNiva + liter);
        if (this.bransleNiva > 20.0 && this.status.equals("BRÄNSLE_LÅGT")) {
            this.status = "REDO";
        }
    }

    // Getters och Setters
    public String getId() {
        return id;
    }

    public String getModell() {
        return modell;
    }

    public double getTimKostnad() {
        return timKostnad;
    }

    public void setTimKostnad(double timKostnad) {
        this.timKostnad = timKostnad;
    }

    public double getDrifttimmar() {
        return drifttimmar;
    }

    protected void setDrifttimmar(double drifttimmar) {
        this.drifttimmar = drifttimmar;
    }

    public double getBransleNiva() {
        return bransleNiva;
    }

    protected void setBransleNiva(double bransleNiva) {
        this.bransleNiva = bransleNiva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - Timkostnad: %.2f kr/h | Timmar: %.1fh | Bränsle: %.1fL | Status: %s",
                id, modell, getClass().getSimpleName(), timKostnad, drifttimmar, bransleNiva, status);
    }
}
