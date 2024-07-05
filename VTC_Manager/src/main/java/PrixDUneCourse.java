import java.math.BigDecimal;
import java.math.RoundingMode;

public class PrixDUneCourse {
    private double prixBasique;
    private double prixDimanche;
    private double prixNuit;

    public PrixDUneCourse(double prixBasique, Majorations majorations) {
        BigDecimal prixArrondisBasique = new BigDecimal(prixBasique).setScale(2, RoundingMode.HALF_UP);
        BigDecimal prixArrondisDimanche = new BigDecimal(prixBasique * majorations.getMajorationDuDimanche()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal prixArrondisNuit = new BigDecimal(prixBasique * majorations.getMajorationDeLaNuit()).setScale(2, RoundingMode.HALF_UP);
        this.prixBasique = prixArrondisBasique.doubleValue();
        prixDimanche = prixArrondisDimanche.doubleValue();
        prixNuit = prixArrondisNuit.doubleValue();
    }

    public double getPrixBasique() {
        return prixBasique;
    }

    public double getPrixDimanche() {
        return prixDimanche;
    }

    public double getPrixNuit() {
        return prixNuit;
    }
}
