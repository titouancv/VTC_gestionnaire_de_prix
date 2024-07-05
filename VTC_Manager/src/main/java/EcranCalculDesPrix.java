import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranCalculDesPrix {

    public static void creerEtAfficherGUICalculDesPrix(ParametresDuPrix parametresDuPrix) {
        JFrame fenetre = new JFrame("Calcul des Prix VTC");
        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetre.setSize(400, 300);

        JPanel panneau = new JPanel();
        fenetre.add(panneau);

        placerComposants(panneau, parametresDuPrix);

        fenetre.setVisible(true);
    }

    private static void calculerPrix(String km, String ageTexte, String paysName, JPanel panneau, ParametresDuPrix parametresDuPrixs) {
        if (km.equals("") || ageTexte.equals("")){
            String message = "Les champs 'nombre de Km' et 'age' ne peuvent pas être vides";
            JOptionPane.showMessageDialog(panneau, message);
            return;
        }
        double distanceCourse = Double.parseDouble(km);
        double age = Double.parseDouble(ageTexte);
        if (distanceCourse < 0 || age < 0){
            String message = "Les champs 'nombre de Km' et 'age' ne peuvent pas être négatifs";
            JOptionPane.showMessageDialog(panneau, message);
            return;
        }

        LongeurDeLaCourse longeurDeLaCourse = new LongeurDeLaCourse(distanceCourse);
        Client client = new Client(age);
        Pays pays = new Pays(paysName, 1);

        CaculDuPrixDeLaCourse caculDuPrixDeLaCourse = new CaculDuPrixDeLaCourse(longeurDeLaCourse, client, pays, parametresDuPrixs);

        String message = "Prix course Basique: " + caculDuPrixDeLaCourse.getPrixDUneCourseBasique() + " " + pays.obtenirCodeDevise() +
                "\nPrix course Dimanche: " + caculDuPrixDeLaCourse.getPrixDUneCourseDuDimanche() + " " + pays.obtenirCodeDevise() +
                "\nPrix course Nuit: " + caculDuPrixDeLaCourse.getPrixDUneCourseDeNuit() + " " + pays.obtenirCodeDevise();

        JOptionPane.showMessageDialog(panneau, message);
    }

    private static void placerComposants(JPanel panneau, ParametresDuPrix parametresDuPrix) {
        panneau.setLayout(null);

        JLabel etiquetteKm = new JLabel("Nombre de Km:");
        etiquetteKm.setBounds(10, 20, 150, 25);
        panneau.add(etiquetteKm);

        JTextField champTexteKm = new JTextField(20);
        champTexteKm.setBounds(120, 20, 165, 25);
        panneau.add(champTexteKm);

        JLabel etiquetteAgeInformations = new JLabel("Réduction pour les étudiants (<=26 ans) et les seniors (>=65 ans)");
        etiquetteAgeInformations.setBounds(10, 50, 450, 20);
        panneau.add(etiquetteAgeInformations);

        JLabel etiquetteAge = new JLabel("Âge du client:");
        etiquetteAge.setBounds(10, 70, 150, 25);
        panneau.add(etiquetteAge);

        JTextField champTexteAge = new JTextField(20);
        champTexteAge.setBounds(120, 70, 165, 25);
        panneau.add(champTexteAge);

        JLabel etiquettePays = new JLabel("Pays:");
        etiquettePays.setBounds(10, 100, 100, 25);
        panneau.add(etiquettePays);

        String[] pays = {};
        JComboBox<String> comboBoxPays = new JComboBox<>(pays);
        comboBoxPays.setBounds(120, 100, 165, 25);
        panneau.add(comboBoxPays);

        for (Pays p:parametresDuPrix.getListeDesPays()) {
            comboBoxPays.addItem(p.getNom());
        }

        JButton boutonSoumettre = new JButton("Calcul du nouveau Prix");
        boutonSoumettre.setBounds(10, 150, 275, 25);
        panneau.add(boutonSoumettre);

        boutonSoumettre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String km = champTexteKm.getText();
                String ageTexte = champTexteAge.getText();
                String paysName = (String) comboBoxPays.getSelectedItem();
                calculerPrix(km, ageTexte, paysName, panneau, parametresDuPrix);
            }
        });
    }
}
