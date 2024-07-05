import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class Pays {
    private String nom;
    private double tauxDeNivauxDeVie;
    private double tauxDeConversion;

    public Pays(String nom, double tauxDeNivauxDeVie) {
        this.nom = nom;
        this.tauxDeNivauxDeVie = tauxDeNivauxDeVie;
        this.tauxDeConversion = obtenirTauxConversionEnTempsReel();
    }

    private double obtenirTauxConversionEnTempsReel() {
        String url = "https://v6.exchangerate-api.com/v6/e1db3e46149a33e8e1160ec2/latest/EUR";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            if (jsonResponse.get("result").equals("success")) {
                JSONObject conversion_rates = jsonResponse.getJSONObject("conversion_rates");
                String devise = obtenirCodeDevise();
                Object ActuelTauxDeConversion = conversion_rates.get(devise);
                if (ActuelTauxDeConversion instanceof Integer) {
                    return ((Integer) ActuelTauxDeConversion).doubleValue();
                } else if (ActuelTauxDeConversion instanceof BigDecimal) {
                    return  ((BigDecimal) ActuelTauxDeConversion).doubleValue();
                } else {
                    throw new IllegalArgumentException("Le type de la valeur n'est pas pris en charge : " + ActuelTauxDeConversion.getClass().getName());
                }
            } else {
                throw new RuntimeException("Erreur dans la réponse de l'API : " + jsonResponse.getString("error"));
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erreur lors de l'obtention du taux de conversion : " + e.getMessage(), e);
        }
    }

    public String obtenirCodeDevise() {
        switch (nom.toLowerCase()) {
            case "france":
                return "EUR";
            case "espagne":
                return "EUR";
            case "roumanie":
                return "RON";
            case "royaume-uni":
                return "GBP";
            default:
                return null;
        }
    }

    public String getNom() {
        return nom;
    }

    public double getTauxDeNivauxDeVie() {
        return tauxDeNivauxDeVie;
    }

    public double getTauxDeConversion() {
        return tauxDeConversion;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTauxDeNivauxDeVie(double tauxDeNivauxDeVie) {
        this.tauxDeNivauxDeVie = tauxDeNivauxDeVie;
    }
}
