package coming;

public class ContattoAziendale extends Contatto {
    String azienda;
    String ruolo;

    public ContattoAziendale(String nome, String cognome, String telefono, String azienda, String ruolo) {
        super(nome, cognome, telefono);
        this.azienda = azienda;
        this.ruolo = ruolo;
    }

    public String getTipo() {
        return "Aziendale";
    }

    public String toCSV() {
        return getTipo() + "," + nome + "," + cognome + "," + telefono + "," + azienda + "," + ruolo;
    }

    public String toString() {
        return "[Aziendale] " + nome + " " + cognome + " - " + telefono + " - " + azienda + " (" + ruolo + ")";
    }
}