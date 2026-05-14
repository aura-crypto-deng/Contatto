package coming;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Main extends Application {
	@FXML TextField campoRicerca;
    @FXML ListView<Contatto> listaContatti;
    @FXML ComboBox<String> comboTipo;
    @FXML TextField campoNome;
    @FXML TextField campoCognome;
    @FXML TextField campoTelefono;
    @FXML TextField campoExtra1;
    @FXML TextField campoExtra2;
    @FXML TextField campoRuolo;
    @FXML Label labelExtra1;
    @FXML Label labelExtra2;
    @FXML Label labelRuolo;
    String nomeFile=("/Users/deng/Desktop/rubrica.csv");
    ArrayList<Contatto> rubrica= new ArrayList();
    @Override
    public void start(Stage finestra) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rubrica.fxml"));
        Parent root = loader.load();
        Scene scena = new Scene(root, 520, 650);
        finestra.setTitle("Rubrica Telefonica");
        finestra.setScene(scena);
        finestra.show();           
                    
    }
    public static void main(String[] args) {
        launch(args);
    }
    @FXML
    public void initialize() throws FileNotFoundException, IOException {
    	System.out.println("metodo chimato");
    	comboTipo.getItems().addAll("Personale", "Aziendale");
        comboTipo.setValue("Personale");
        
        comboTipo.valueProperty().addListener((obs, vecchio, nuovo) -> {
            if (nuovo.equals("Personale")) {
                labelExtra1.setText("Email:");
                labelExtra2.setText("Compleanno:");
                labelRuolo.setVisible(false);
                campoRuolo.setVisible(false);
                campoExtra1.setPromptText("email@esempio.it");
                campoExtra2.setPromptText("gg/mm/aaaa");
            } else {
                labelExtra1.setText("Azienda:");
                labelExtra2.setText("Sede:");
                labelRuolo.setVisible(true);
                campoRuolo.setVisible(true);
                campoExtra1.setPromptText("Nome azienda");
                campoExtra2.setPromptText("Sede");
                campoRuolo.setPromptText("Ruolo lavorativo");
            }
        });
        
        labelRuolo.setVisible(false);
        campoRuolo.setVisible(false);
        caricaDaFile();
    
    }
    void caricaDaFile() throws FileNotFoundException, IOException{
    	File mioFile= new File(nomeFile);
    	String rigaletta;
    	if (!mioFile.exists()) {
            campoRicerca.setText("File non trovato, rubrica vuota");
        }
    	 try (
                 FileReader leggo = new FileReader(mioFile);
                 BufferedReader lettoreDiRighe = new BufferedReader(leggo);
             ){
                 while((rigaletta = lettoreDiRighe.readLine()) != null) {
                	 String [] parti=rigaletta.split(",");
                	 if(parti[0].equalsIgnoreCase("Personale")) {
                	 String email = parti[4];
                     String compleanno = parti[5];
                     ContattoPersonale icPersonali = new ContattoPersonale(parti[1], parti[2], parti[3], email, compleanno);
                     rubrica.add(icPersonali);
                	 }else if (parti[0].equals("Aziendale") && parti.length >= 6) {
                         String azienda = parti[4];
                         String ruolo = parti[5];
                         ContattoAziendale c = new ContattoAziendale(parti[1], parti[2], parti[3], azienda, ruolo);
                         rubrica.add(c);
                	 }
                 }
    	 	}
    }
    @FXML
    public void aggiungiContatto() {
        System.out.println("Contatto aggiunto");
    }
    @FXML
    public void eliminaContatto() {
        System.out.println("Contatto eliminato");
    }
    @FXML
    public void pulisciRicerca() {
        campoRicerca.clear();
    }
}

  