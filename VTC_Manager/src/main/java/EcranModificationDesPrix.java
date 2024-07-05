import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EcranModificationDesPrix {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            final Majorations majorations = new Majorations();
            final Ristournes ristournes = new Ristournes();
            final PrixDuKm prixDuKm = new PrixDuKm(10);
            final Pays france = new Pays("France", 1);
            final Pays espagne = new Pays("Espagne", 0.95);
            final Pays roumanie = new Pays("Roumanie", 0.91);
            final Pays royaumeUni = new Pays("Royaume-uni", 1.07);
            final ParametresDuPrix parametresDuPrix = new ParametresDuPrix(majorations, ristournes, prixDuKm);
            public void run() {
                parametresDuPrix.getListeDesPays().add(france);
                parametresDuPrix.getListeDesPays().add(espagne);
                parametresDuPrix.getListeDesPays().add(roumanie);
                parametresDuPrix.getListeDesPays().add(royaumeUni);
                creerEtAfficherGUI(parametresDuPrix);
            }
        });
    }

    private static void creerEtAfficherGUI(ParametresDuPrix parametresDuPrix) {
        JFrame fenetre = new JFrame("Modification des Prix VTC");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(500, 600);

        JPanel panneau = new JPanel();
        fenetre.add(panneau);

        placerComposants(panneau, parametresDuPrix);

        fenetre.setVisible(true);
    }

    private static String nouvelleRistourne(String ristourneTexte, String ristourneType, ParametresDuPrix parametresDuPrix){
        if (!ristourneTexte.equals("")){
            double ristourne = Double.parseDouble(ristourneTexte);
            ristourne = 1 + ristourne/100;
            if(ristourne <= 1) {
                if (ristourneType.equals("etudiant")){
                    parametresDuPrix.getRistournes().setRistourneEtudiante(ristourne);
                    return "\nNouvelle ristourne étudiante: " + ristourne;
                }
                else {
                    parametresDuPrix.getRistournes().setRistourneSenior(ristourne);
                    return "\nNouvelle ristourne sénior: " + ristourne;
                }
            }
                return "\nERREUR: La ristourne étudiante doit être comprise entre 0 et 1, ristourne non appliquée.";
        }
        return "";
    }

    private static String nouvelleMajoration(String majorationTexte, String majorationType, ParametresDuPrix parametresDuPrix, double majorationMinimum){
        if (!majorationTexte.equals("")){
            double majoration = Double.parseDouble(majorationTexte);
            majoration = 1 + majoration/100;
            if(majoration >= majorationMinimum) {
                switch (majorationType.toLowerCase()) {
                    case "dimanche":
                        parametresDuPrix.getMajorations().setMajorationDuDimanche(majoration);
                        return "\nNouvelle majoration du dimanche: " + majoration;
                    case "nuit":
                        parametresDuPrix.getMajorations().setMajorationDeLaNuit(majoration);
                        return "\nNouvelle majoration de la nuit: " + majoration;
                    case "prixkm":
                        parametresDuPrix.getPrixDuKm().setPrixDuKm(majoration);
                        return "\nNouvelle majoration du dimanche: " + majoration;
                    case "france":
                        setLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(),"France",majoration);
                        return "\nNouveaux taux de la France: " + majoration;
                    case "espagne":
                        setLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(),"Espagne",majoration);
                        return "\nNouveaux taux de l'Espagne: " + majoration;
                    case "roumanie":
                        setLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(),"Roumanie",majoration);
                        return "\nNouveaux taux de la Roumanie: " + majoration;
                    case "royaume-uni":
                        setLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(),"Royaume-uni",majoration);
                        return "\nNouveaux taux du Royaume-uni: " + majoration;
                    default:
                        return "";
                }
            }
            return "\nERREUR: La valeur "+majorationType+" doit être supérieur à "+majorationMinimum+", aucun changement effectué";
        }
        return "";
    }

    private static void modifierPrix(String prixKmTexte, String reductionEtudiantTexte, String reductionSeniorTexte, String majorationDimancheTexte, String majorationNuitTexte, String tauxFrance, String tauxEspagne, String tauxRoumanie, String tauxUK, JPanel panneau, ParametresDuPrix parametresDuPrix) {

        String message = "";

        message += nouvelleMajoration(prixKmTexte, "prixKm", parametresDuPrix, 0);
        message += nouvelleRistourne(reductionEtudiantTexte, "etudiant", parametresDuPrix);
        message += nouvelleRistourne(reductionSeniorTexte, "senior", parametresDuPrix);
        message += nouvelleMajoration(majorationDimancheTexte, "dimanche", parametresDuPrix, 1);
        message += nouvelleMajoration(majorationNuitTexte, "nuit", parametresDuPrix, 1);
        message += nouvelleMajoration(tauxFrance, "France", parametresDuPrix, 0);
        message += nouvelleMajoration(tauxEspagne, "Espagne", parametresDuPrix, 0);
        message += nouvelleMajoration(tauxRoumanie, "Roumanie", parametresDuPrix, 0);
        message += nouvelleMajoration(tauxUK, "Royaume-uni", parametresDuPrix, 0);

        JOptionPane.showMessageDialog(panneau, message);
    }

    private static double trouveLeTauxDeNiveauDeVieDUnPays(List<Pays> listeDesPays, String nomPaysRecherche){
        for (Pays pays: listeDesPays) {
            if (nomPaysRecherche.toLowerCase().equals(pays.getNom().toLowerCase())){
                return pays.getTauxDeNivauxDeVie();
            }
        }
        return -1;
    }

    private static void setLeTauxDeNiveauDeVieDUnPays(List<Pays> listeDesPays, String nomPaysRecherche, double tauxNiveauDeVie){
        for (Pays pays: listeDesPays) {
            if (nomPaysRecherche.toLowerCase().equals(pays.getNom().toLowerCase())){
                pays.setTauxDeNivauxDeVie(tauxNiveauDeVie);
            }
        }
    }

    private static void placerComposants(JPanel panneau, ParametresDuPrix parametresDuPrix) {
        panneau.setLayout(null);

        JLabel valeurActuelle = new JLabel("Valeur Actuelle");
        valeurActuelle.setBounds(120, 20, 150, 25);
        panneau.add(valeurActuelle);

        JLabel NouvelleValeur = new JLabel("Nouvelle Valeur");
        NouvelleValeur.setBounds(280, 20, 150, 25);
        panneau.add(NouvelleValeur);



        JLabel etiquettePrixKm = new JLabel("Prix par Km:");
        etiquettePrixKm.setBounds(10, 50, 150, 25);
        panneau.add(etiquettePrixKm);

        JLabel etiquetteActuelPrixKm = new JLabel(String.valueOf(parametresDuPrix.getPrixDuKm().getPrixDuKm())+" EUR");
        etiquetteActuelPrixKm.setBounds(120, 50, 150, 25);
        panneau.add(etiquetteActuelPrixKm);

        JTextField champTextePrixKm = new JTextField(20);
        champTextePrixKm.setBounds(280, 50, 165, 25);
        panneau.add(champTextePrixKm);



        JLabel etiquetteReductions = new JLabel("REDUCTIONS");
        etiquetteReductions.setBounds(10, 90, 450, 20);
        panneau.add(etiquetteReductions);

        JLabel etiquetteReductionEtudiant = new JLabel("Etudiant:");
        etiquetteReductionEtudiant.setBounds(10, 110, 150, 25);
        panneau.add(etiquetteReductionEtudiant);

        JLabel etiquetteActuelleReductionEtudiant = new JLabel(String.valueOf(parametresDuPrix.getRistournes().getRistourneEtudiante()*100 -100)+"%");
        etiquetteActuelleReductionEtudiant.setBounds(120, 110, 150, 25);
        panneau.add(etiquetteActuelleReductionEtudiant);

        JTextField champTexteReductionEtudiant = new JTextField(20);
        champTexteReductionEtudiant.setBounds(280, 110, 165, 25);
        panneau.add(champTexteReductionEtudiant);

        JLabel etiquetteReductionSenior = new JLabel("Senior:");
        etiquetteReductionSenior.setBounds(10, 140, 150, 25);
        panneau.add(etiquetteReductionSenior);

        JLabel etiquetteActuelleReductionSenior = new JLabel(String.valueOf(parametresDuPrix.getRistournes().getRistourneSenior()*100 -100)+"%");
        etiquetteActuelleReductionSenior.setBounds(120, 140, 150, 25);
        panneau.add(etiquetteActuelleReductionSenior);

        JTextField champTexteReductionSenior = new JTextField(20);
        champTexteReductionSenior.setBounds(280, 140, 165, 25);
        panneau.add(champTexteReductionSenior);



        JLabel etiquetteMajorations = new JLabel("MAJORATIONS");
        etiquetteMajorations.setBounds(10, 180, 450, 20);
        panneau.add(etiquetteMajorations);

        JLabel etiquetteMajorationDimanche = new JLabel("Dimanche:");
        etiquetteMajorationDimanche.setBounds(10, 200, 150, 25);
        panneau.add(etiquetteMajorationDimanche);

        JLabel etiquetteActuelleMajorationDimanche = new JLabel(String.valueOf(parametresDuPrix.getMajorations().getMajorationDuDimanche()*100 -100)+"%");
        etiquetteActuelleMajorationDimanche.setBounds(120, 200, 150, 25);
        panneau.add(etiquetteActuelleMajorationDimanche);

        JTextField champTexteMajorationDimanche = new JTextField(20);
        champTexteMajorationDimanche.setBounds(280, 200, 165, 25);
        panneau.add(champTexteMajorationDimanche);

        JLabel etiquetteMajorationsNuit = new JLabel("Nuit:");
        etiquetteMajorationsNuit.setBounds(10, 230, 150, 25);
        panneau.add(etiquetteMajorationsNuit);

        JLabel etiquetteActuelleMajorationsNuit = new JLabel(String.valueOf(parametresDuPrix.getMajorations().getMajorationDeLaNuit()*100 -100)+"%");
        etiquetteActuelleMajorationsNuit.setBounds(120, 230, 150, 25);
        panneau.add(etiquetteActuelleMajorationsNuit);

        JTextField champTexteMajorationsNuit = new JTextField(20);
        champTexteMajorationsNuit.setBounds(280, 230, 165, 25);
        panneau.add(champTexteMajorationsNuit);



        JLabel etiquettePAys = new JLabel("PAYS");
        etiquettePAys.setBounds(10, 265, 450, 20);
        panneau.add(etiquettePAys);

        JLabel etiquetteFrance = new JLabel("France:");
        etiquetteFrance.setBounds(10, 280, 150, 25);
        panneau.add(etiquetteFrance);

        JLabel etiquetteActuelleTauxFrance = new JLabel(String.valueOf(trouveLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(), "France")*100 -100)+"%");
        etiquetteActuelleTauxFrance.setBounds(120, 280, 150, 25);
        panneau.add(etiquetteActuelleTauxFrance);

        JTextField champTexteTauxFrance = new JTextField(20);
        champTexteTauxFrance.setBounds(280, 280, 165, 25);
        panneau.add(champTexteTauxFrance);

        JLabel etiquetteEspagne = new JLabel("Espagne:");
        etiquetteEspagne.setBounds(10, 310, 150, 25);
        panneau.add(etiquetteEspagne);

        JLabel etiquetteActuelleTauxEspagne = new JLabel(String.valueOf(trouveLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(), "Espagne")*100 -100)+"%");
        etiquetteActuelleTauxEspagne.setBounds(120, 310, 150, 25);
        panneau.add(etiquetteActuelleTauxEspagne);

        JTextField champTexteTauxEspagne = new JTextField(20);
        champTexteTauxEspagne.setBounds(280, 310, 165, 25);
        panneau.add(champTexteTauxEspagne);

        JLabel etiquetteRoumanie = new JLabel("Roumanie:");
        etiquetteRoumanie.setBounds(10, 340, 150, 25);
        panneau.add(etiquetteRoumanie);

        JLabel etiquetteActuelleTauxRoumanie = new JLabel(String.valueOf(trouveLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(), "Roumanie")*100 -100)+"%");
        etiquetteActuelleTauxRoumanie.setBounds(120, 340, 150, 25);
        panneau.add(etiquetteActuelleTauxRoumanie);

        JTextField champTexteTauxRoumanie = new JTextField(20);
        champTexteTauxRoumanie.setBounds(280, 340, 165, 25);
        panneau.add(champTexteTauxRoumanie);

        JLabel etiquetteUK = new JLabel("Royaume-uni:");
        etiquetteUK.setBounds(10, 370, 150, 25);
        panneau.add(etiquetteUK);

        JLabel etiquetteActuelleTauxUK = new JLabel(String.valueOf(trouveLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(), "Royaume-uni")*100 -100)+"%");
        etiquetteActuelleTauxUK.setBounds(120, 370, 150, 25);
        panneau.add(etiquetteActuelleTauxUK);

        JTextField champTexteTauxUK = new JTextField(20);
        champTexteTauxUK.setBounds(280, 370, 165, 25);
        panneau.add(champTexteTauxUK);



        JButton boutonSoumettre = new JButton("Modifier les Prix");
        boutonSoumettre.setBounds(10, 410, 435, 25);
        panneau.add(boutonSoumettre);

        JButton boutonCalcul = new JButton("Calcul des Prix");
        boutonCalcul.setBounds(10, 440, 435, 25);
        panneau.add(boutonCalcul);



        boutonSoumettre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prixKm = champTextePrixKm.getText();
                String reductionEtudiant = champTexteReductionEtudiant.getText();
                String reductionSenior = champTexteReductionSenior.getText();
                String majorationDimanche = champTexteMajorationDimanche.getText();
                String majorationNuit = champTexteMajorationsNuit.getText();
                String tauxFrance = champTexteTauxFrance.getText();
                String tauxEspagne = champTexteTauxEspagne.getText();
                String tauxRoumanie = champTexteTauxRoumanie.getText();
                String tauxUK = champTexteTauxUK.getText();

                modifierPrix(prixKm, reductionEtudiant, reductionSenior, majorationDimanche, majorationNuit, tauxFrance, tauxEspagne, tauxRoumanie, tauxUK, panneau, parametresDuPrix);

                etiquetteActuelPrixKm.setText(String.valueOf(parametresDuPrix.getPrixDuKm().getPrixDuKm())+" EUR");
                etiquetteActuelleReductionEtudiant.setText(String.valueOf(parametresDuPrix.getRistournes().getRistourneEtudiante()*100 -100)+"%");
                etiquetteActuelleReductionSenior.setText(String.valueOf(parametresDuPrix.getRistournes().getRistourneSenior()*100 -100)+"%");
                etiquetteActuelleMajorationDimanche.setText(String.valueOf(parametresDuPrix.getMajorations().getMajorationDuDimanche()*100 -100)+"%");
                etiquetteActuelleMajorationsNuit.setText(String.valueOf(parametresDuPrix.getMajorations().getMajorationDeLaNuit()*100 -100)+"%");
                etiquetteActuelleTauxFrance.setText(String.valueOf(trouveLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(), "France")*100 -100)+"%");
                etiquetteActuelleTauxEspagne.setText(String.valueOf(trouveLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(), "Espagne")*100 -100)+"%");
                etiquetteActuelleTauxRoumanie.setText(String.valueOf(trouveLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(), "Roumanie")*100 -100)+"%");
                etiquetteActuelleTauxUK.setText(String.valueOf(trouveLeTauxDeNiveauDeVieDUnPays(parametresDuPrix.getListeDesPays(), "Royaume-uni")*100 -100)+"%");
            }
        });

        boutonCalcul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EcranCalculDesPrix.creerEtAfficherGUICalculDesPrix(parametresDuPrix);
            }
        });
    }
}
