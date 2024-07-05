public class Client {
    private double age;
    private boolean etudiant;
    private boolean senior;

    public Client(double age) {
        this.age = age;
        etudiant = age <= 26;
        senior = age >= 65;
    }

    public double getAge() {
        return age;
    }

    public boolean isEtudiant() {
        return etudiant;
    }

    public boolean isSenior() {
        return senior;
    }
}
