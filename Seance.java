import java.time.LocalDateTime;

public class Seance {
    private int id;
    private Film film;
    private LocalDateTime horaire;
    private String salle;
    private int capaciteMax;

    public Seance(int id, Film film, LocalDateTime horaire, String salle, int capaciteMax) {
        this.id = id;
        this.film = film;
        this.horaire = horaire;
        this.salle = salle;
        this.capaciteMax = capaciteMax;
    }

    public int getId() { return id; }
    public Film getFilm() { return film; }
    public LocalDateTime getHoraire() { return horaire; }
    public String getSalle() { return salle; }
    public int getCapaciteMax() { return capaciteMax; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        String h = (horaire == null) ? "null" : horaire.toString().replace('T',' ');
        return id + " | " + film.getTitre() + " | " + h + " | " + salle + " | cap:" + capaciteMax;
    }
}

