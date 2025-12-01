import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DAOService.FilmDAO filmDAO = new DAOService.FilmDAO();
    private static final DAOService.SeanceDAO seanceDAO = new DAOService.SeanceDAO();
    private static final DAOService.SpectateurDAO spectateurDAO = new DAOService.SpectateurDAO();
    private static final DAOService.TicketDAO ticketDAO = new DAOService.TicketDAO();
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        System.out.println("Cinema Console App");
        while (true) {
            printMenu();
            int choice = readInt();
            try {
                switch (choice) {
                    case 1 -> listFilms();
                    case 2 -> addFilm();
                    case 3 -> listSeances();
                    case 4 -> addSeance();
                    case 5 -> listSpectateurs();
                    case 6 -> addSpectateur();
                    case 7 -> reserveTicket();
                    case 0 -> {
                        System.out.println("Bye");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Choix invalide");
                }
            } catch (SQLException e) {
                System.err.println("DB error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1  - List films");
        System.out.println("2  - Add film");
        System.out.println("3  - List seances");
        System.out.println("4  - Add seance");
        System.out.println("5  - List spectateurs");
        System.out.println("6  - Add spectateur");
        System.out.println("7  - Reserve ticket");
        System.out.println("0  - Quit");
        System.out.print("Choice: ");
    }

    // Reads an int and consumes the newline so following nextLine() works
    private static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrée invalide. Réessayez: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return val;
    }

    private static double readDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Entrée invalide. Réessayez: ");
            scanner.next();
        }
        double v = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return v;
    }

    private static void listFilms() throws SQLException {
        List<Film> films = filmDAO.findAll();
        System.out.println("\n--- Films ---");
        if (films.isEmpty()) System.out.println(" (aucun film)");
        for (Film f : films) System.out.println(f);
    }

    private static void addFilm() throws SQLException {
        System.out.print("Titre: ");
        String titre = scanner.nextLine().trim();
        if (titre.isEmpty()) { System.out.println("Titre vide — annulé."); return; }

        System.out.print("Duree minutes: ");
        int duree = readInt();

        System.out.print("Categorie: ");
        String cat = scanner.nextLine().trim();

        Film f = new Film(0, titre, duree, cat);
        filmDAO.create(f);
        System.out.println("Ajouté: " + f);
    }

    private static void listSeances() throws SQLException {
        List<Seance> seances = seanceDAO.findAll();
        System.out.println("\n--- Seances ---");
        if (seances.isEmpty()) System.out.println(" (aucune séance)");
        for (Seance s : seances) System.out.println(s);
    }

    private static void addSeance() throws SQLException {
        // show films so user can pick film id
        listFilms();
        System.out.print("Film ID: ");
        int filmId = readInt();

        Film film = filmDAO.findById(filmId);
        if (film == null) {
            System.out.println("Film introuvable (id=" + filmId + "). Annulé.");
            return;
        }

        System.out.print("Salle: ");
        String salle = scanner.nextLine().trim();
        System.out.print("Capacite: ");
        int cap = readInt();

        System.out.print("Horaire (yyyy-MM-dd HH:mm): ");
        String horaireStr = scanner.nextLine().trim();
        LocalDateTime horaire;
        try {
            horaire = LocalDateTime.parse(horaireStr, DF);
        } catch (Exception ex) {
            System.out.println("Format horaire invalide. Attendu: yyyy-MM-dd HH:mm. Annulé.");
            return;
        }

        Seance s = new Seance(0, film, horaire, salle, cap);
        seanceDAO.create(s);
        System.out.println("Seance créée: " + s);
    }

    private static void listSpectateurs() throws SQLException {
        List<Spectateur> list = spectateurDAO.findAll();
        System.out.println("\n--- Spectateurs ---");
        if (list.isEmpty()) System.out.println(" (aucun spectateur)");
        for (Spectateur sp : list) System.out.println(sp);
    }

    private static void addSpectateur() throws SQLException {
        System.out.print("Nom: ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) { System.out.println("Nom vide — annulé."); return; }

        System.out.print("Email: ");
        String mail = scanner.nextLine().trim();
        Spectateur s = new Spectateur(0, nom, mail);
        spectateurDAO.create(s);
        System.out.println("Ajouté: " + s);
    }

    private static void reserveTicket() throws SQLException {
        listSeances();
        System.out.print("Seance ID: ");
        int sid = readInt();

        Seance seance = seanceDAO.findById(sid);
        if (seance == null) {
            System.out.println("Séance introuvable (id=" + sid + "). Annulé.");
            return;
        }

        listSpectateurs();
        System.out.print("Spectateur ID: ");
        int spid = readInt();

        Spectateur spect = spectateurDAO.findById(spid);
        if (spect == null) {
            System.out.println("Spectateur introuvable (id=" + spid + "). Annulé.");
            return;
        }

        System.out.print("Prix (ex: 45.0): ");
        double prix = readDouble();

        try {
            Ticket t = ticketDAO.reserve(sid, spid, prix);
            if (t != null) {
                System.out.println("Ticket réservé: id=" + t.getId() + " prix=" + t.getPrix());
            } else {
                System.out.println("Échec réservation (ticket null retourné).");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur réservation: " + ex.getMessage());
        }
    }
}
