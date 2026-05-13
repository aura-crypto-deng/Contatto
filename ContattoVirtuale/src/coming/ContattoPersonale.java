package coming;


public class ContattoPersonale extends Contatto {
    String email;

    public ContattoPersonale(String nome, String cognome, String telefono, String email) {
        super(nome, cognome, telefono);
        this.email = email;
    }

    public String getTipo() {
        return "Personale";
    }

    public String toCSV() {
        return getTipo() + "," + nome + "," + cognome + "," + telefono + "," + email;
    }

    public String toString() {
        return "[Personale] " + nome + " " + cognome + " - " + telefono + " - " + email;
    }
}