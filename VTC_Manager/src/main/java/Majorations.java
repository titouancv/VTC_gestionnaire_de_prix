public class Majorations {
    private double majorationDuDimanche;
    private double majorationDeLaNuit;

    public Majorations() {
        this.majorationDuDimanche = 1.32;
        this.majorationDeLaNuit = 1.49;
    }

    public double getMajorationDuDimanche() {
        return majorationDuDimanche;
    }

    public void setMajorationDuDimanche(double majorationDuDimanche) {
        this.majorationDuDimanche = majorationDuDimanche;
    }

    public double getMajorationDeLaNuit() {
        return majorationDeLaNuit;
    }

    public void setMajorationDeLaNuit(double majorationDeLaNuit) {
        this.majorationDeLaNuit = majorationDeLaNuit;
    }
}
