public class CaculDuPrixDeLaCourse {
    private LongeurDeLaCourse longeurDeLaCourse;
    private PrixDuKm prixDuKm;
    private PrixDUneCourse prixDUneCourse;
    private Majorations majorations;
    private Ristournes ristournes;
    private Client client;
    private Pays pays;

    public CaculDuPrixDeLaCourse(LongeurDeLaCourse longeurDeLaCourse, Client client, Pays pays, ParametresDuPrix parametresDuPrixs) {
        this.longeurDeLaCourse = longeurDeLaCourse;
        this.prixDuKm = parametresDuPrixs.getPrixDuKm();
        this.client = client;
        this.pays = pays;
        this.majorations = parametresDuPrixs.getMajorations();
        this.ristournes = parametresDuPrixs.getRistournes();
        calculDUneCourseBasique();
    }

    private double applicationDeRistourne(double prixBasique){
        double prixRistourne = prixBasique;
        if (client.isEtudiant()){
            prixRistourne = prixBasique * ristournes.getRistourneEtudiante();
        }
        else if (client.isSenior()){
            prixRistourne = prixBasique * ristournes.getRistourneSenior();
        }
        return prixRistourne;
    }

    public void calculDUneCourseBasique(){
        double prixBasique = longeurDeLaCourse.getKm() * prixDuKm.getPrixDuKm();
        prixBasique = applicationDeRistourne(prixBasique);
        prixBasique = prixBasique * pays.getTauxDeNivauxDeVie() * pays.getTauxDeConversion();

        PrixDUneCourse prixCourseBasique = new PrixDUneCourse(prixBasique, majorations);
        prixDUneCourse = prixCourseBasique;
    }

    public double getPrixDUneCourseBasique() {
        return prixDUneCourse.getPrixBasique();
    }

    public double getPrixDUneCourseDuDimanche() {
        return prixDUneCourse.getPrixDimanche();
    }

    public double getPrixDUneCourseDeNuit() {
        return prixDUneCourse.getPrixNuit();
    }
}
