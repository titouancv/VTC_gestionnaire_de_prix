import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalculDuPrixDeLaCourseTest {
    private LongeurDeLaCourse longeurDeLaCourse = new LongeurDeLaCourse(10);
    private PrixDuKm prixDuKm = new PrixDuKm(10);
    Majorations majorations = new Majorations();
    Ristournes ristournes = new Ristournes();
    private Client clientBasique = new Client(30);
    private Client clientEtudiant = new Client(26);
    private Client clientSenior = new Client(65);
    Pays france = new Pays("France", 1);
    Pays espagne = new Pays("Espagne", 0.95);
    Pays roumanie = new Pays("Roumanie", 0.91);
    Pays royaumeUni = new Pays("Royaume-uni", 1.07);
    ParametresDuPrix parametresDuPrix = new ParametresDuPrix(majorations, ristournes, prixDuKm);

    @BeforeAll
    public void ajoutDesPays() {
        parametresDuPrix.getListeDesPays().add(france);
        parametresDuPrix.getListeDesPays().add(espagne);
        parametresDuPrix.getListeDesPays().add(roumanie);
        parametresDuPrix.getListeDesPays().add(royaumeUni);
    }

    // Client lambda -------------------------------------------
    @Test
    public void calculDUneCourseBasiqueTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientBasique, france, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(100, caculDuPrixDeLaCourse.getPrixDUneCourseBasique(),  0.01);
    }

    @Test
    public void calculDUneCourseDuDimancheTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientBasique, france, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(132, caculDuPrixDeLaCourse.getPrixDUneCourseDuDimanche(),  0.01);
    }

    @Test
    public void calculDUneCourseDeNuitTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientBasique, france, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(149, caculDuPrixDeLaCourse.getPrixDUneCourseDeNuit(),  0.01);
    }

    // Client Etudiant -------------------------------------------

    @Test
    public void calculDUneCourseBasiquePourEtudiantTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientEtudiant, france, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(89, caculDuPrixDeLaCourse.getPrixDUneCourseBasique(),  0.01);
    }

    @Test
    public void calculDUneCourseDuDimanchePourEtudiantTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientEtudiant, france, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(117.48, caculDuPrixDeLaCourse.getPrixDUneCourseDuDimanche(),  0.01);
    }

    @Test
    public void calculDUneCourseDeNuitPourEtudiantTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientEtudiant, france, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(132.61, caculDuPrixDeLaCourse.getPrixDUneCourseDeNuit(),  0.01);
    }

    // Client Senior -------------------------------------------

    @Test
    public void calculDUneCourseBasiquePourSeniorTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientSenior, france, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(92, caculDuPrixDeLaCourse.getPrixDUneCourseBasique(),  0.01);
    }

    @Test
    public void calculDUneCourseDuDimanchePourSeniorTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientSenior, france, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(121.44, caculDuPrixDeLaCourse.getPrixDUneCourseDuDimanche(),  0.01);
    }

    @Test
    public void calculDUneCourseDeNuitPourSeniorTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientSenior, france, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(137.08, caculDuPrixDeLaCourse.getPrixDUneCourseDeNuit(),  0.01);
    }

    // Changement de pays -------------------------------------------
    @Test
    public void calculDUneCourseBasiqueEnEspagneTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientBasique, espagne, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(95 * espagne.getTauxDeConversion(), caculDuPrixDeLaCourse.getPrixDUneCourseBasique(),  0.01);
    }

    @Test
    public void calculDUneCourseBasiqueEnRoumanieTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientBasique, roumanie, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(91 * roumanie.getTauxDeConversion(), caculDuPrixDeLaCourse.getPrixDUneCourseBasique(),  0.01);
    }

    @Test
    public void calculDUneCourseBasiquePourSeniorAuRoyaumeUniTest(){
        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, clientSenior, royaumeUni, parametresDuPrix);

        caculDuPrixDeLaCourse.calculDUneCourseBasique();

        assertEquals(98.44 * royaumeUni.getTauxDeConversion(), caculDuPrixDeLaCourse.getPrixDUneCourseBasique(),  0.01);
    }

}
