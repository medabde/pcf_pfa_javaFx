package app.models;

public class Client {

    private int id;
    private String name;
    private String sexe;
    private String telephone;
    private String dateNaissance;


    public Client(int id, String name, String sexe, String telephone, String dateNaissance) {
        this.id = id;
        this.name = name;
        this.sexe = sexe;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
    }
    public Client() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getSexe() {
        return sexe;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }
}