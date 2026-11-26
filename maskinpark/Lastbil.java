package se.fjax26.maskinpark;

import se.fjax26.maskinpark.records.Tillagg;

import java.util.List;

public class Lastbil extends Anlaggningsmaskin {

    private double lasteKapacitetTon;
    private int antalAxlar;
    private boolean harSlap;

    public Lastbil(String id, String modell, double timKostnad, double lasteKapacitetTon, int antalAxlar, boolean harSlap) {
        super(id, modell, timKostnad);
        this.lasteKapacitetTon = lasteKapacitetTon;
        this.antalAxlar = antalAxlar;
        this.harSlap = harSlap;
    }

    public String utforTransport(double strackaKm, double lastViktTon) {
        if (lastViktTon > (harSlap ? lasteKapacitetTon * 2.0 : lasteKapacitetTon)) {
            return "FEL: Lastbil överlastad! Max kapacitet: " + lasteKapacitetTon + "t (Släp: " + harSlap + ")";
        }

        double forverkadBranslePerMil = 3.2 + (lastViktTon * 0.15) + (harSlap ? 1.2 : 0.0);
        double totalBransle = (strackaKm / 10.0) * forverkadBranslePerMil;

        setDrifttimmar(getDrifttimmar() + (strackaKm / 60.0)); // Antar 60 km/h medelhasighet
        setBransleNiva(Math.max(0, getBransleNiva() - totalBransle));

        return "TRANSPORT FULLFÖRD: " + getId() + " kört " + strackaKm + " km med " + lastViktTon + " ton last. Förbrukning: " + String.format("%.1f", totalBransle) + "L.";
    }

    public double getLasteKapacitetTon() {
        return lasteKapacitetTon;
    }

    public int getAntalAxlar() {
        return antalAxlar;
    }

    public boolean isHarSlap() {
        return harSlap;
    }

    public void setHarSlap(boolean harSlap) {
        this.harSlap = harSlap;
    }

    @Override
    protected List<Tillagg> tillagg() {
        return List.of(
                Tillagg.perTimme(isHarSlap(), 120.0),
                Tillagg.fast(getAntalAxlar() >= 4, 250.0)
        );
    }
}