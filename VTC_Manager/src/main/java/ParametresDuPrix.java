import java.util.ArrayList;
import java.util.List;

public class ParametresDuPrix {
    private Majorations majorations;
    private Ristournes ristournes;
    private PrixDuKm prixDuKm;
    private List<Pays> listeDesPays;

    public ParametresDuPrix(Majorations majorations, Ristournes ristournes, PrixDuKm prixDuKm) {
        this.majorations = majorations;
        this.ristournes = ristournes;
        this.prixDuKm = prixDuKm;
        this.listeDesPays = new ArrayList<>();
    }

    public Majorations getMajorations() {
        return majorations;
    }

    public Ristournes getRistournes() {
        return ristournes;
    }

    public PrixDuKm getPrixDuKm() {
        return prixDuKm;
    }

    public List<Pays> getListeDesPays() {
        return listeDesPays;
    }
}
