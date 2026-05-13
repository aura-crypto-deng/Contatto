package coming;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
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
    @FXML Label labelExtra1;
    @FXML Label labelExtra2;

    ObservableList<Contatto> rubrica;
    ObservableList<Contatto> listaFiltrata;
    String nomeFile = "rubrica.csv";

    public void start(Stage finestra) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rubrica.fxml"));
        loader.setController(this);
        VBox radice = loader.load();
        Scene scena = new Scene(radice, 520, 650);
        finestra.setTitle("Rubrica Telefonica");
        finestra.setScene(scena);
        finestra.show();
    }

    @FXML
    public void initialize() {
        rubrica = FXCollections.observableArrayList(caricaDaFile());
        listaFiltrata = FXCollections.observableArrayList(rubrica);
        listaContatti.setItems(listaFiltrata);

        campoRicerca.textProperty().addListener((obs, vecchio, nuovo) -> {
            filtraLista();
        });

        comboTipo.valueProperty().addListener((obs, vecchio, nuovo) -> {
            if (nuovo.equals("Personale")) {
                labelExtra1.setText("Email:");
                labelExtra2.setText("Compleanno:");
                campoExtra1.setPromptText("email@esempio.it");
                campoExtra2.setPromptText("gg/mm/aaaa");
            } else {
                labelExtra1.setText("Azienda:");
                labelExtra2.setText("Ruolo:");
                campoExtra1.setPromptText("Nome azienda");
                campoExtra2.setPromptText("Ruolo lavorativo");
            }
        });

        comboTipo.setValue("Personale");
    }

    ArrayList<Contatto> caricaDaFile() {
        ArrayList<Contatto> rubrica = new ArrayList<Contatto>();
        File file = new File(nomeFile);

        if (!file.exists()) {
            return rubrica;
        }

        try {
            BufferedReader lettore = new BufferedReader(new FileReader(file));
            String linea;
            while ((linea = lettore.readLine()) != null) {
                String[] parti = linea.split(",");
                if (parti[0].equals("Personale") && parti.length == 5) {
                    ContattoPersonale c = new ContattoPersonale(parti[1], parti[2], parti[3], parti[4]);
                    rubrica.add(c);
                }
                else if (parti[0].equals("Aziendale") && parti.length == 6) {
                    ContattoAziendale c = new ContattoAziendale(parti[1], parti[2], parti[3], parti[4], parti[5]);
                    rubrica.add(c);
                }
                else if (parti[0].equals("Base") && parti.length == 4) {
                    Contatto c = new Contatto(parti[1], parti[2], parti[3]);
                    rubrica.add(c);
                }
            }
            lettore.close();
        } catch (IOException e) {
            System.err.println("Errore lettura: " + e.getMessage());
        }
        return rubrica;
    }

    void salvaSuFile() {
        try {
            PrintWriter scrittore = new PrintWriter(new FileWriter(nomeFile));
            for (int i = 0; i < rubrica.size(); i++) {
                scrittore.println(rubrica.get(i).toCSV());
            }
            scrittore.close();
        } catch (IOException e) {
            System.err.println("Errore salvataggio: " + e.getMessage());
        }
    }

    void filtraLista() {
        String testo = campoRicerca.getText().toLowerCase();
        listaFiltrata.clear();

        if (testo.isEmpty()) {
            listaFiltrata.addAll(rubrica);
        } else {
            for (int i = 0; i < rubrica.size(); i++) {
                Contatto c = rubrica.get(i);
                if (c.nome.toLowerCase().contains(testo) || c.cognome.toLowerCase().contains(testo)) {
                    listaFiltrata.add(c);
                }
            }
        }
    }

    @FXML
    void aggiungiContatto() {
        if (campoNome.getText().isEmpty() || campoCognome.getText().isEmpty() || campoTelefono.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nome, cognome e telefono sono obbligatori!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        String tipo = comboTipo.getValue();
        String nome = campoNome.getText();
        String cognome = campoCognome.getText();
        String telefono = campoTelefono.getText();
        String extra1 = campoExtra1.getText();
        String extra2 = campoExtra2.getText();

        Contatto nuovo;

        if (tipo.equals("Personale")) {
            nuovo = new ContattoPersonale(nome, cognome, telefono, extra1);
        } else {
            if (extra1.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "L'azienda è obbligatoria!", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            nuovo = new ContattoAziendale(nome, cognome, telefono, extra1, extra2);
        }

        rubrica.add(nuovo);
        salvaSuFile();

        campoNome.clear();
        campoCognome.clear();
        campoTelefono.clear();
        campoExtra1.clear();
        campoExtra2.clear();

        filtraLista();
    }

    @FXML
    void eliminaContatto() {
        Contatto selezionato = listaContatti.getSelectionModel().getSelectedItem();
        if (selezionato != null) {
            rubrica.remove(selezionato);
            salvaSuFile();
            filtraLista();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seleziona un contatto dalla lista!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void pulisciRicerca() {
        campoRicerca.clear();
        filtraLista();
    }

    public static void main(String[] args) {
        launch(args);
    }
}