package app.utils;

import app.models.Client;
import app.models.Commande;
import app.models.CommandeUpdate;
import app.models.Produit;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Connextion {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/pfejavafx";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Connextion() throws SQLException {
    }


    public ArrayList<Client> getClients() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "SELECT * FROM clients";
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);

            while (rs.next()){
                clients.add(new Client(rs.getInt("id"),rs.getString("name"),rs.getString("sex"),rs.getString("phone"),rs.getString("dateOfBirth")));
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clients;
    }
    public Client getClient(String telephone){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT * FROM clients WHERE phone='"+telephone+"'");

            if (res.next())return new Client(res.getInt("id"),res.getString("name"),res.getString("sex"),res.getString("phone"),res.getString("dateOfBirth"));

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




        return null;
    }
    public Client getClient(int id){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT * FROM clients WHERE id='"+id+"'");

            if (res.next())return new Client(res.getInt("id"),res.getString("name"),res.getString("sex"),res.getString("phone"),res.getString("dateOfBirth"));

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




        return null;
    }

    public ArrayList<Produit> getProduits() throws SQLException {
        ArrayList<Produit> produits = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "SELECT * FROM produits";
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);

            while (rs.next()){
                produits.add(new Produit(rs.getInt("id"),rs.getString("name"),rs.getDouble("price"),rs.getInt("quantity")));
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return produits;
    }
    public Produit getProduit(int id){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT * FROM produits WHERE id='"+id+"'");

            if (res.next())return new Produit(res.getInt("id"),res.getString("name"),res.getDouble("price"),res.getInt("quantity"));

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




        return null;
    }
    public void modifyClient(int id, String name, String sexe, String telephone, String dateNaissance){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "UPDATE clients " +
                    "SET name = '"+name+"', sex = '"+sexe+"', phone = '"+telephone+"',dateOfBirth= '"+dateNaissance+"'" +
                    " WHERE id= "+id;
            stmt.executeUpdate(sql);
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void modifyProduit(int id, String name, String price, String quantity){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "UPDATE produits " +
                    "SET name = '"+name+"', price = "+price+", quantity = "+quantity+"" +
                    " WHERE id= "+id;
            stmt.executeUpdate(sql);
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void reduceFromQuantity(int id,int m){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT quantity FROM produits WHERE id='"+id+"'");
            res.next();

            int preQuantity = res.getInt("quantity");

            int finalQuantity = preQuantity - m;

            stmt.executeUpdate("UPDATE produits " +
                    "SET quantity = "+finalQuantity+"" +
                    " WHERE id= "+id);


            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void addToQuantity(int id,int m){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT quantity FROM produits WHERE id='"+id+"'");
            res.next();

            int preQuantity = res.getInt("quantity");

            int finalQuantity = preQuantity + m;

            stmt.executeUpdate("UPDATE produits " +
                    "SET quantity = "+finalQuantity+"" +
                    " WHERE id= "+id);


            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }



    public int getOldQuantity(int cmdId,int produitId){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT quantity FROM passecommande WHERE commandeId='"+cmdId+"' AND produitId='"+produitId+"'");
            res.next();
            int ret =res.getInt("quantity");
            conn.close();

            return ret;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




        return 0;
    }

    public boolean productIsInCmd(int cmdId,int produitId){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT quantity FROM passecommande WHERE commandeId='"+cmdId+"' AND produitId='"+produitId+"'");
            if (res.next()) return true;
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




        return false;
    }


    public ArrayList<CommandeUpdate> productsInCmd(int cmdId){
        ArrayList<CommandeUpdate> cmds = new ArrayList<>();
        String name="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT quantity,produitId FROM passecommande WHERE commandeId='"+cmdId+"'");
            while(res.next()){
                name = getProduit(res.getInt("produitId")).getId()+" "+getProduit(res.getInt("produitId")).getName();
                cmds.add(new CommandeUpdate(name,res.getInt("quantity")));
            }

            conn.close();
            return cmds;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




        return null;
    }




    public void deleteClient(int id){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT id FROM commandes WHERE clientId="+id);

            if (res.next()) {
                int cmdId = res.getInt("id");
                stmt.execute("DELETE FROM passecommande WHERE commandeId="+cmdId);
                stmt.execute("DELETE FROM commandes WHERE id="+cmdId);
            }


            String sql = "DELETE FROM clients WHERE id="+id;
            stmt.execute(sql);


            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduit(int id){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            stmt.execute("DELETE FROM passecommande WHERE produitId="+id);

            String sql = "DELETE FROM produits WHERE id="+id;
            stmt.execute(sql);


            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduit(String name, String price, String quantity){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "INSERT INTO `produits` (`id`, `name`, `price`, `quantity`) VALUES (NULL, '"+name+"', '"+price+"', '"+quantity+"'); ";
            stmt.execute(sql);


            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void addClient(String name, String phone, String sex,String dateOfBirth){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "INSERT INTO `clients` (`id`, `name`, `sex`, `phone`, `dateOfBirth`) VALUES (NULL, '"+name+"', '"+sex+"', '"+phone+"', '"+dateOfBirth+"'); ";
            stmt.execute(sql);


            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCommande(String name,String phone,String sex,String dateOfBirth,int[] p , int[] q,String status){
        addClient(name,phone,sex,dateOfBirth);


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT id FROM clients WHERE name='"+name+"' AND sex='"+sex+"' AND dateOfBirth='"+dateOfBirth+"' AND phone='"+phone+"' ");

            res.next();
            int idClient = res.getInt("id");
            String date =simpleDateFormat.format(new Date());

            stmt.execute("INSERT INTO `commandes` (`id`, `clientId`, `date`, `status`) VALUES (NULL, '"+idClient+"', '"+date+"', '"+status+"'); ");

            ResultSet res2 = (ResultSet) stmt.executeQuery("SELECT id FROM commandes WHERE clientId='"+idClient+"' AND date='"+date+"' AND status='"+status+"'");
            res2.next();
            int idCmd = res2.getInt("id");



            for (int i = 0; i <p.length ; i++) {
                if (p[i]!=0){
                    stmt.execute("INSERT INTO `passecommande` (`id`, `commandeId`, `produitId`,`quantity`) VALUES (NULL, '"+idCmd+"', '"+p[i]+"', '"+q[i]+"'); ");

                }
            }







            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    public void addCommande(int clientId,int[] p , int[] q,String status){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();



            String date = simpleDateFormat.format(new Date());

            stmt.execute("INSERT INTO `commandes` (`id`, `clientId`, `date`, `status`) VALUES (NULL, '"+clientId+"', '"+date+"', '"+status+"'); ");

            ResultSet res2 = (ResultSet) stmt.executeQuery("SELECT id FROM commandes WHERE clientId='"+clientId+"' AND date='"+date+"' AND status='"+status+"'");
            res2.next();
            int idCmd = res2.getInt("id");



            for (int i = 0; i <p.length ; i++) {
                if (p[i]!=0){
                    stmt.execute("INSERT INTO `passecommande` (`id`, `commandeId`, `produitId`,`quantity`) VALUES (NULL, '"+idCmd+"', '"+p[i]+"', '"+q[i]+"'); ");

                }
            }







            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }



    public ArrayList<Commande> getCommandes() throws SQLException {
        ArrayList<Commande> commandes = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "SELECT * FROM commandes";
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);


            ArrayList<Integer> ids = new ArrayList<>();
            ArrayList<String> clientNames = new ArrayList<>();
            ArrayList<String> statuss = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();

            while (rs.next()){
                ids.add(rs.getInt("id"));
                clientNames.add(getClient(rs.getInt("clientId")).getName());
                statuss.add(rs.getString("status"));
                dates.add(rs.getString("date"));




            }

            for (int i = 0; i <ids.size() ; i++) {
                ResultSet res = (ResultSet) stmt.executeQuery("SELECT * FROM passecommande WHERE commandeId='"+ids.get(i)+"'");
                ArrayList<Produit> produits = new ArrayList<>();
                ArrayList<Integer> quantities = new ArrayList<>();

                while(res.next()){
                    produits.add(getProduit(res.getInt("produitId")));
                    quantities.add(res.getInt("quantity"));
                }




                String pros="";
                for (int k = 0; k < produits.size() ; k++) {
                    pros+=produits.get(k).getName()+"("+quantities.get(k)+")"+"\n";
                }

                commandes.add(new Commande(ids.get(i),clientNames.get(i),pros,statuss.get(i),dates.get(i)));
            }





            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        return commandes;

    }




    public ArrayList<Client> searchClient(String name,String gender) throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "SELECT * FROM clients WHERE name LIKE '%"+name+"%' AND sex='"+gender+"'";
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);

            while (rs.next()){
                clients.add(new Client(rs.getInt("id"),rs.getString("name"),rs.getString("sex"),rs.getString("phone"),rs.getString("dateOfBirth")));
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public ArrayList<Produit> searchProduit(String name) throws SQLException {
        ArrayList<Produit> produits = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "SELECT * FROM produits WHERE name LIKE '%"+name+"%'";
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);

            while (rs.next()){
                produits.add(new Produit(rs.getInt("id"),rs.getString("name"),rs.getDouble("price"),rs.getInt("quantity")));
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return produits;
    }

    

    public boolean checkUsernamePass(String username,String password){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "SELECT * FROM admin";
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);

            if (rs.next()){
                if (rs.getString("username").equals(username) && rs.getString("password").equals(password) ){
                    return true;
                }
            }


            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




        return false;
    }

    public boolean isLogedIn(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String sql = "SELECT * FROM admin";
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);

            if (rs.next()){
                if (rs.getString("isLogedIn").equals("1")){
                    return true;
                }
            }


            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

       return false;
    }
    public void setIsLogedIn(boolean bool){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            String is = (bool)?"1":"0";

            String sql = "UPDATE admin " +
                    "SET isLogedIn = '"+is+"'" +
                    " WHERE id= 1";
            stmt.executeUpdate(sql);
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    public void modifyCommande(int id,int[] p,int[] q,String status){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();


            stmt.execute("UPDATE commandes SET status ='"+status+"'  WHERE id="+id);


            stmt.execute("DELETE FROM passecommande WHERE commandeId="+id);


            for (int i = 0; i <p.length ; i++) {
                if (p[i]!=0){
                    stmt.execute("INSERT INTO `passecommande` (`id`, `commandeId`, `produitId`,`quantity`) VALUES (NULL, '"+id+"', '"+p[i]+"', '"+q[i]+"'); ");

                }
            }







            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }


    public void deleteCommande(int id){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            Statement stmt = (Statement) conn.createStatement();

            ResultSet res = (ResultSet) stmt.executeQuery("SELECT id FROM commandes WHERE clientId="+id);

            stmt.execute("DELETE FROM passecommande WHERE commandeId="+id);
            stmt.execute("DELETE FROM commandes WHERE id="+id);


            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }




}
