package se.fjax26.maskinpark;

import se.fjax26.maskinpark.enums.KorkortKlass;
import se.fjax26.maskinpark.enums.Rabatt;
import se.fjax26.maskinpark.enums.Terrang;

import java.time.LocalDateTime;
//här är det ändrat
public class Korning {

    private static final double KVALL_OB = 1.25;
    private static final double HELG_OB = 1.30;
    private static final double HELG_KVALL_OB = 1.50;
    private static final double MOMS = 1.25;

    private final String korningId;
    private final Anlaggningsmaskin maskin;
    private final String forarNamn;
    private final KorkortKlass forarKorkortKlass;
    private final double timmar;
    private final Terrang terrang;
    private final LocalDateTime startTid;
    private final boolean kvallsPass;
    private Rabatt rabatt = Rabatt.INGEN;

    public Korning(String korningId, Anlaggningsmaskin maskin, String forarNamn,
                   String forarKorkortKlass, double timmar, String terrang, boolean kvallsPass) {
        if (maskin == null) {
            throw new IllegalArgumentException("Maskin saknas för körning");
        }
        if (timmar < 0) {
            throw new IllegalArgumentException("Timmar kan inte vara negativt: " + timmar);
        }
        this.korningId = korningId;
        this.maskin = maskin;
        this.forarNamn = forarNamn;
        this.forarKorkortKlass = KorkortKlass.fran(forarKorkortKlass);
        this.timmar = timmar;
        this.terrang = Terrang.fran(terrang);
        this.startTid = LocalDateTime.now();
        this.kvallsPass = kvallsPass;
    }

    public double beraknaSlutpris(boolean inkluderaMoms, boolean arHelg) {
        double pris = maskin.beraknaBasPris(timmar) * obFaktor(arHelg);
        pris += terrang.kostnad(timmar);
        pris = rabatt.tillampa(pris);
        if (inkluderaMoms) {
            pris *= MOMS;
        }
        return Math.max(0, pris);
    }

    private double obFaktor(boolean arHelg) {
        if (kvallsPass && arHelg) return HELG_KVALL_OB;
        if (kvallsPass) return KVALL_OB;
        if (arHelg) return HELG_OB;
        return 1.0;
    }

    public String getKorningId() { return korningId; }
    public Anlaggningsmaskin getMaskin() { return maskin; }
    public String getForarNamn() { return forarNamn; }
    public KorkortKlass getForarKorkortKlass() { return forarKorkortKlass; }
    public double getTimmar() { return timmar; }
    public Terrang getTerrang() { return terrang; }
    public LocalDateTime getStartTid() { return startTid; }
    public boolean isKvallsPass() { return kvallsPass; }
    public Rabatt getRabatt() { return rabatt; }
    public void setRabattKod(String rabattKod) { this.rabatt = Rabatt.fran(rabattKod); }
}