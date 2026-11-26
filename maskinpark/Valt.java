package se.fjax26.maskinpark;

import se.fjax26.maskinpark.records.Tillagg;

import java.util.List;

public class Valt extends Anlaggningsmaskin {

    private double viktITon;
    private double trummBreddMeter;
    private boolean vibrationsLägeAktivt;

    public Valt(String id, String modell, double timKostnad, double viktITon, double trummBreddMeter) {
        super(id, modell, timKostnad);
        this.viktITon = viktITon;
        this.trummBreddMeter = trummBreddMeter;
        this.vibrationsLägeAktivt = false;
    }

    public void aktiveraVibration() {
        this.vibrationsLägeAktivt = true;
    }

    public void inaktiveraVibration() {
        this.vibrationsLägeAktivt = false;
    }

    public String utforVältning(double timmar, String underlag) {
        if (timmar <= 0) return "FEL: Ogiltig tid";

        double bransleForbrukning = timmar * (vibrationsLägeAktivt ? 16.5 : 11.0);
        setDrifttimmar(getDrifttimmar() + timmar);
        setBransleNiva(Math.max(0, getBransleNiva() - bransleForbrukning));

        return "VÄLTNING UTFÖRD: " + getId() + " (" + getModell() + ") packade " + underlag + " i " + timmar + "h. Vibration: " + (vibrationsLägeAktivt ? "JA" : "NEJ");
    }

    // Getters och Setters
    public double getViktITon() {
        return viktITon;
    }

    public double getTrummBreddMeter() {
        return trummBreddMeter;
    }

    public boolean isVibrationsLägeAktivt() {
        return vibrationsLägeAktivt;
    }


    @Override
    protected List<Tillagg> tillagg() {
        return List.of(
                Tillagg.perTimme(isVibrationsLägeAktivt(), 45.0),
                Tillagg.fast(getViktITon() > 10.0, 300.0)
        );
    }


}