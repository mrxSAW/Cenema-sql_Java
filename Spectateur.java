public class Spectateur {
    private int id;
    private String nom;
    private String email;

    public Spectateur(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return id + " | " + nom + " | " + email;
    }
}

