package DataBase;

import java.sql.*;


public class EtudiantImp implements EtudiantDAO{
    Connection con=null;
    public EtudiantImp(){

        try{
            con=DataBaseConnection.makeConnection();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int insertEtudiant(int cin, String nom, String prenom, double moyenne) {
        Statement st = null;
        int a = 0;
        String req = "INSERT INTO etudiant(cin, nom, prenom, moyenne) VALUES ("+cin+", '"+nom+"', '"+prenom+"', "+moyenne+")";


        try {
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (st != null) {
            try {
                a = st.executeUpdate(req);
            } catch (SQLException e) {
                System.out.println("erreur:" + e.getMessage());
                return -1;
            }
        }
        return a;
    }

    @Override
    public int deleteEtudiant(int cin) {
        Statement st = null;
        int a = 0;
        String req = "DELETE FROM etudiant WHERE cin ="+cin;

        try{
            st= con.createStatement();
            a=st.executeUpdate(req);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return -1;
        }


        return a;
    }

    @Override
    public int deleteAllEtudiants() {
        Statement st = null;
        int a = 0;
        String req = "DELETE FROM etudiant";

        try {
            st = con.createStatement();
            a = st.executeUpdate(req);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }

        return a;
    }

    @Override
    public int updateEtudiant(int cin, String nom, String prenom, double moyenne) {
        Statement st = null;
        int a = 0;
        String req = "UPDATE etudiant SET nom='"+nom+"',prenom='"+prenom+"',moyenne='"+moyenne+"' WHERE cin="+cin;

        try{
            st= con.createStatement();
            a=st.executeUpdate(req);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return -1;
        }

        return a;
    }

    @Override
    public ResultSet selectEtudiant(String req) {
        ResultSet rs = null;
        Statement st = null;

        try {
            st = con.createStatement();

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }
        if (st != null) {

            try {
                rs = st.executeQuery(req);




            } catch (SQLException e) {
                System.out.println("erreur:" + e.getMessage());
            }
        }
        return rs;
    }

    @Override
    public void afficherEtudiant(ResultSet rs) {

        try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int nbCol = rsmd.getColumnCount();
                    while (rs.next()) {
                        for(int i=1;i<=nbCol;i++) System.out.println(rs.getObject(i));
                    }


                } catch (SQLException e) {
                    System.out.println("erreur:" + e.getMessage());
                }

    }
}

