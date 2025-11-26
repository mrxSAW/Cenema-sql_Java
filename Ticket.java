public class Ticket {

    private int id_tickit  ;
    private  double  prix;

    public Ticket(int id_tickit,double prix) {
        this.id_tickit=id_tickit;
        this.prix=prix;
    }

    public int getId_tickit() {
        return id_tickit;
    }

    public double getPrix() {
        return prix;
    }

    public void setId_tickit(int id_tickit) {
        this.id_tickit = id_tickit;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
