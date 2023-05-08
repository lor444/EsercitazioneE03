package it.corsojava.jdbc.autori;

import it.corsojava.ui.terminal.TerminalUi;

import java.sql.*;
import java.util.List;

public class UserInterface {

    private TerminalUi ui;
    private String connectionString="";
    private String userName="";
    private String userPass="";

    public UserInterface(TerminalUi ui){
        this.ui=ui;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public void run(){
        ui.writeln("Archivio autori =======================");
        boolean goAhead=true;
        while(goAhead) {
            ui.write("[1] Elenco autori  [2] Nuovo autore [3] Esci");
            String comando = ui.read();
            if (comando.equals("1") || comando.equals("2") || comando.equals("3") ) {
                switch (comando){
                    case "1":
                        elencoAutori();
                        break;
                    case "2":
                        aggiungiAutore();
                        break;
                    case "3":
                        goAhead=false;
                        break;
                }
            }else{
                ui.write("Comando non riconosciuto");
            }
        }
    }

    private void elencoAutori(){
        try(Connection cn = DriverManager.getConnection(connectionString,userName,userPass)){
            ResultSet rs= cn.createStatement().executeQuery("SELECT * FROM autori");
            while(rs.next()){
                StringBuilder line=new StringBuilder();
                line.append(rs.getInt("idAutore"));
                line.append(" - ");
                line.append(rs.getString("nome"));
                line.append(" ");
                line.append(rs.getString("cognome"));
                ui.writeln(line.toString());
            }
        }catch (SQLException ex){
            ui.write(ex.getMessage());
        }
    }

    private void aggiungiAutore(){
        ui.write("Cognome: ");
        String cognome=ui.read();
        ui.write("Nome   : ");
        String nome =ui.read();
        // ui.writeln("FUNZIONE NON IMPLEMENTATA");

        try(Connection cn = DriverManager.getConnection(connectionString,userName,userPass)){
            ResultSet rs=cn.createStatement().executeQuery("SELECT MAX(idAutore) FROM autori");
            int nextId=-1;
            nextId=rs.getInt(1);
            nextId++;
            rs.close();

            PreparedStatement ps = cn.prepareStatement("INSERT INTO autori (idAutore,cognome,nome) values (?,?,?)");
            ps.setInt(1, nextId);
            ps.setString(2,cognome);
            ps.setString(3,nome);
            int result=ps.executeUpdate();
            if(result>0){
                ui.writeln("Autore registrato");
            }else{
                ui.writeln("Autore NON registrato");
            }
        }catch (SQLException ex){
            ui.write(ex.getMessage());
        }
    }

}
