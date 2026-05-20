

public class Snippet {
	@FXML
	public void aggiungiContatto() {
	    if (campoNome.getText().isEmpty() || campoCognome.getText().isEmpty() || campoTelefono.getText().isEmpty()) {
	        mostraErrore("Nome, cognome e telefono sono obbligatori!");
	        return;
	    }
	    
	    String tipo = comboTipo.getValue();  // Ora è solo "Personale" o "Aziendale"
	    String nome = campoNome.getText();
	    String cognome = campoCognome.getText();
	    String telefono = campoTelefono.getText();
	    String extra1 = campoExtra1.getText();
	    String extra2 = campoExtra2.getText();
	    String ruolo = campoRuolo.getText();
	    
	    Contatto nuovo;
	    
	    if (tipo.equals("Personale")) {
	        nuovo = new ContattoPersonale(nome, cognome, telefono, extra1, extra2);
	    } else {
	        if (extra1.isEmpty()) {
	            mostraErrore("L'azienda è obbligatoria!");
	            return;
	        }
	        nuovo = new ContattoAziendale(nome, cognome, telefono, extra1, ruolo);
	    }
	    
	    rubrica.add(nuovo);
	    
	    // Pulisci form
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