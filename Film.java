public class Film {
    private int id;
    private String titre;
    private int duree; // minutes
    private String categorie;

    public Film(int id, String titre, int duree, String categorie) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public String getTitre() { return titre; }
    public int getDuree() { return duree; }
    public String getCategorie() { return categorie; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return id + " | " + titre + " (" + duree + " min) [" + categorie + "]";
    }
}

