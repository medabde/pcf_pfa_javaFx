package app.models;

public class Commande {
    private int id;
    private String client;
    private String produits;
    private String status;
    private String date;


    public Commande(int id, String client, String produits, String status, String date) {
        this.id = id;
        this.client = client;
        this.produits = produits;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public String getProduits() {
        return produits;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
