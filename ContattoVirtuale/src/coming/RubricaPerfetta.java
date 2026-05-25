package coming;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class RubricaPerfetta extends Application {
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
    @FXML Button buttonAggiungi;
    boolean elimina=false;
    String nomeFile = "/Users/deng/Desktop/rubrica.csv";
    ArrayList<Contatto> rubrica = new ArrayList<>();
    Contatto contattoInModifica = null;
    @Override
    public void start(Stage finestra) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rubrica.fxml"));
        Parent root = loader.load();
        Scene scena = new Scene(root);
        scena.getStylesheets().add(getClass().getResource("RubricaPerfetta.css").toExternalForm());
        finestra.setTitle("Rubrica Telefonica");
        finestra.setScene(scena);
        finestra.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    @FXML
    public void initialize() throws FileNotFoundException, IOException {
        comboTipo.getItems().addAll("Personale", "Aziendale");
        comboTipo.setValue("Personale");

        campoRicerca.textProperty().addListener((obs, vecchio, nuovo) -> {
            aggiornaList();
        });

        comboTipo.valueProperty().addListener((obs, vecchio, nuovo) -> {
            if (nuovo.equals("Personale")) {
                labelExtra1.setText("Email:");
                labelExtra2.setText("Compleanno:");
                labelRuolo.setVisible(false);
                campoRuolo.setVisible(false);
                campoExtra1.setPromptText("email@esempio.it");
                campoExtra2.setPromptText("gg/mm/aaaa (opzionale)");
            } else {
                labelExtra1.setText("Azienda:");
                labelExtra2.setText("Sede:");
                labelRuolo.setVisible(true);
                campoRuolo.setVisible(true);
                campoExtra1.setPromptText("Nome azienda");
                campoExtra2.setPromptText("Sede");
                campoRuolo.setPromptText("Ruolo lavorativo");
            }
            campoExtra1.setVisible(true);
            campoExtra2.setVisible(true);
        });
        labelExtra1.setText("Email:");
        labelExtra2.setText("Compleanno:");
        labelRuolo.setVisible(false);
        campoRuolo.setVisible(false);
        campoExtra1.setPromptText("email@esempio.it");
        campoExtra2.setPromptText("gg/mm/aaaa (opzionale)");
        campoExtra1.setVisible(true);
        campoExtra2.setVisible(true);
        caricaDaFile();
        aggiornaList();
    }
    void caricaDaFile() throws FileNotFoundException, IOException {
        File mioFile = new File(nomeFile);

        if (!mioFile.exists()) {
            campoRicerca.setText("File non trovato, rubrica vuota");
            return;
        }
        FileReader leggo = new FileReader(mioFile);
        BufferedReader lettoreDiRighe = new BufferedReader(leggo);
        String rigaletta;
        while ((rigaletta = lettoreDiRighe.readLine()) != null) {
            String[] parti = rigaletta.split(",");
            if (parti.length >= 4) {
                if (parti[0].equalsIgnoreCase("Personale")) {
                    if (parti.length >= 5) {
                        String email = parti[4];
                        String compleanno = "";
                        if (parti.length > 5) {
                            compleanno = parti[5];
                        }
                        ContattoPersonale icPersonali = new ContattoPersonale(parti[1], parti[2], parti[3], email, compleanno);
                        rubrica.add(icPersonali);
                    }
                }
                else if (parti[0].equals("Aziendale")) {
                    if (parti.length >= 7) {
                        String azienda = parti[4];
                        String sede = parti[5];
                        String ruolo = parti[6];
                        ContattoAziendale c = new ContattoAziendale(parti[1], parti[2], parti[3], azienda, sede, ruolo);
                        rubrica.add(c);
                    }
                }
            }
        }
        lettoreDiRighe.close();
    }

    void salvaSuFile() {
        try {
            FileWriter flussoCaratteri = new FileWriter(nomeFile);
            for (int i = 0; i < rubrica.size(); i++) {
                Contatto c = rubrica.get(i);
                flussoCaratteri.write(c.toCSV() + "\n");
            }
            flussoCaratteri.close();
            System.out.println("Salvati " + rubrica.size() + " contatti");
        } catch (IOException e) {
            System.err.println("Errore salvataggio: " + e.getMessage());
        }
    }

    void aggiornaList() {
        listaContatti.getItems().clear();
        String testoRicerca = "";
        if (campoRicerca != null) {
            testoRicerca = campoRicerca.getText().toLowerCase();
        }
        for (int i = 0; i < rubrica.size(); i++) {
            Contatto c = rubrica.get(i);
            if (testoRicerca.isEmpty()) {
                listaContatti.getItems().add(c);
            } else {
                boolean nomeTrovato = c.nome.toLowerCase().contains(testoRicerca);
                boolean cognomeTrovato = c.cognome.toLowerCase().contains(testoRicerca);
                if (nomeTrovato || cognomeTrovato) {
                    listaContatti.getItems().add(c);
                }
            }
        }
    }

    boolean campiPersonaleValid() {
        if (campoNome.getText().isEmpty()) {
            mostraErrore("Il nome è obbligatorio!");
            return false;
        }
        if (campoCognome.getText().isEmpty()) {
            mostraErrore("Il cognome è obbligatorio!");
            return false;
        }
        if (campoTelefono.getText().isEmpty()) {
            mostraErrore("Il telefono è obbligatorio!");
            return false;
        }
        if (campoExtra1.getText().isEmpty()) {
            mostraErrore("L'email è obbligatoria!");
            return false;
        }
        return true;
    }

    boolean campiAziendaleValid() {
        if (campoNome.getText().isEmpty()) {
            mostraErrore("Il nome è obbligatorio!");
            return false;
        }
        if (campoCognome.getText().isEmpty()) {
            mostraErrore("Il cognome è obbligatorio!");
            return false;
        }
        if (campoTelefono.getText().isEmpty()) {
            mostraErrore("Il telefono è obbligatorio!");
            return false;
        }
        if (campoExtra1.getText().isEmpty()) {
            mostraErrore("L'azienda è obbligatoria!");
            return false;
        }
        if (campoExtra2.getText().isEmpty()) {
            mostraErrore("La sede è obbligatoria!");
            return false;
        }
        if (campoRuolo.getText().isEmpty()) {
            mostraErrore("Il ruolo è obbligatorio!");
            return false;
        }
        return true;
    }
    @FXML
    public void aggiungiContatto() {
        String tipo = comboTipo.getValue();
        Contatto nuovo = null;
        if (contattoInModifica != null) {
            if (tipo.equals("Personale")) {
                if (campiPersonaleValid()) {
                    nuovo = new ContattoPersonale(
                        campoNome.getText(),
                        campoCognome.getText(),
                        campoTelefono.getText(),
                        campoExtra1.getText(),
                        campoExtra2.getText()
                    );
                }
            } else {
                if (campiAziendaleValid()) {
                    nuovo = new ContattoAziendale(
                        campoNome.getText(),
                        campoCognome.getText(),
                        campoTelefono.getText(),
                        campoExtra1.getText(),
                        campoExtra2.getText(),
                        campoRuolo.getText()
                    );
                }
            }
            if (nuovo != null) {
                rubrica.remove(contattoInModifica);
                rubrica.add(nuovo);
                contattoInModifica = null; 
                buttonAggiungi.setStyle("");
                buttonAggiungi.setText("Aggiungi Contatto");
                listaContatti.setStyle("");
                salvaSuFile();
                aggiornaList();
                System.out.println("Contatto modificato e salvato!");
            }
            return;
        }
        if (tipo.equals("Personale")) {
            if (campiPersonaleValid()) {
                nuovo = new ContattoPersonale(
                    campoNome.getText(),
                    campoCognome.getText(),
                    campoTelefono.getText(),
                    campoExtra1.getText(),
                    campoExtra2.getText()
                );
            }
        } else {
            if (campiAziendaleValid()) {
                nuovo = new ContattoAziendale(
                    campoNome.getText(),
                    campoCognome.getText(),
                    campoTelefono.getText(),
                    campoExtra1.getText(),
                    campoExtra2.getText(),
                    campoRuolo.getText()
                );
            }
        }

        if (nuovo != null) {
            rubrica.add(nuovo);
            campoNome.clear();
            campoCognome.clear();
            campoTelefono.clear();
            campoExtra1.clear();
            campoExtra2.clear();
            campoRuolo.clear();
            salvaSuFile();
            aggiornaList();
            System.out.println("Contatto aggiunto");
        }
    }
    public void eliminaContatto() {
        Contatto selezionato = listaContatti.getSelectionModel().getSelectedItem();
        if (selezionato != null) {
            rubrica.remove(selezionato);
            salvaSuFile();
            aggiornaList();
            elimina=true;
            mostraErrore(selezionato.toString()+"   Eliminato!!!");
        } else {
            mostraErrore("Seleziona un contatto dalla lista!");
        }
    }
    @FXML
    public void pulisciRicerca() {
        campoRicerca.clear();
        aggiornaList();
    }
    void mostraErrore(String messaggio) {
    	if(elimina) {
    		Alert alert = new Alert(Alert.AlertType.WARNING, messaggio, ButtonType.OK);
            alert.showAndWait();
            elimina=false;
    	}else {
    		Alert alert = new Alert(Alert.AlertType.ERROR, messaggio, ButtonType.OK);
            alert.showAndWait();
    	}
    }
    @FXML
    public void modificaContatto(MouseEvent event) {
        Contatto selezionato = listaContatti.getSelectionModel().getSelectedItem();
        if (selezionato == null) {
            return;
        }
        if (event.getClickCount() == 1) {
            mostraDettagli(selezionato);
        }
    }
    public void modificaContatto(Contatto selezionato) {
        contattoInModifica = selezionato;
        buttonAggiungi.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white;");
        buttonAggiungi.setText("SALVA MODIFICHE");
        campoNome.setText(selezionato.nome);
        campoCognome.setText(selezionato.cognome);
        campoTelefono.setText(selezionato.telefono);
        
        if (selezionato.getTipo().equalsIgnoreCase("Personale")) {
            comboTipo.setValue("Personale");
            ContattoPersonale persona = (ContattoPersonale) selezionato;
            campoExtra1.setText(persona.email);
            campoExtra2.setText(persona.compleanno);
            campoRuolo.clear();
            campoRuolo.setVisible(false);
            labelRuolo.setVisible(false);
        } else if (selezionato.getTipo().equalsIgnoreCase("Aziendale")) {
            comboTipo.setValue("Aziendale");
            ContattoAziendale azienda = (ContattoAziendale) selezionato;
            campoExtra1.setText(azienda.azienda);
            campoExtra2.setText(azienda.sede);
            campoRuolo.setText(azienda.ruolo);
            campoRuolo.setVisible(true);
            labelRuolo.setVisible(true);
        }
    }
    void mostraDettagli(Contatto contatto) {
        Stage finestraDettagli = new Stage();
        finestraDettagli.setTitle("Dettagli Contatto");
        GridPane griglia = new GridPane();
        griglia.setHgap(10);
        griglia.setVgap(10);
        griglia.setStyle("-fx-padding: 20; -fx-background-color: #f5f5f5;");
        int riga = 0;
        griglia.add(new Label("Tipo:"), 0, riga);
        griglia.add(new Label(contatto.getTipo()), 1, riga);
        riga++;
        griglia.add(new Label("Nome:"), 0, riga);
        griglia.add(new Label(contatto.nome), 1, riga);
        riga++;
        griglia.add(new Label("Cognome:"), 0, riga);
        griglia.add(new Label(contatto.cognome), 1, riga);
        riga++;
        griglia.add(new Label("Telefono:"), 0, riga);
        griglia.add(new Label(contatto.telefono), 1, riga);
        if (contatto.getTipo().equalsIgnoreCase("Personale")) {
            ContattoPersonale personale = (ContattoPersonale) contatto;
            riga++;
            griglia.add(new Label("Email:"), 0, riga);
            griglia.add(new Label(personale.email), 1, riga);
            riga++;
            griglia.add(new Label("Compleanno:"), 0, riga);
            if(personale.compleanno.isEmpty()) {
            	griglia.add(new Label ("non inserito"),1,riga);
            }else {
            	griglia.add(new Label(personale.compleanno), 1, riga);
            }
        }
        else if (contatto.getTipo().equalsIgnoreCase("Aziendale")) {
            ContattoAziendale aziendale = (ContattoAziendale) contatto;
            riga++;
            griglia.add(new Label("Azienda:"), 0, riga);
            griglia.add(new Label(aziendale.azienda), 1, riga);
            riga++;
            griglia.add(new Label("Sede:"), 0, riga);
            griglia.add(new Label(aziendale.sede), 1, riga);
            riga++;
            griglia.add(new Label("Ruolo:"), 0, riga);
            griglia.add(new Label(aziendale.ruolo), 1, riga);
        }
        Button bModifica = new Button("modifica");
        Button btnChiudi = new Button("Chiudi");
        Button bElimina=new Button("elimina");
        
        btnChiudi.setOnAction(e -> finestraDettagli.close());
        bElimina.setOnAction(e-> eliminaContatto());
        bModifica.setOnAction(e -> {finestraDettagli.close();modificaContatto(contatto);});
        riga++;
        griglia.add(bModifica, 2, riga,2,1);
        griglia.add(btnChiudi, 0, riga, 2, 1);
        griglia.add(bElimina, 1, riga,2,1);
        Scene scena = new Scene(griglia, 450, 450);
        finestraDettagli.setScene(scena);
        finestraDettagli.show();
    }
}

  