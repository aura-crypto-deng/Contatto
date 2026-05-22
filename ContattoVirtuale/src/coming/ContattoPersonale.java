package coming;

public class ContattoPersonale extends Contatto {
    String email;
    String compleanno;
    public ContattoPersonale(String nome, String cognome, String telefono, String email) {
        super(nome, cognome, telefono);
        this.email = email;
        this.compleanno = "";
    }
    public ContattoPersonale(String nome, String cognome, String telefono, String email, String compleanno) {
        super(nome, cognome, telefono);
        this.email = email;
        this.compleanno = compleanno;
    }
    @Override
    public String getTipo() {
        return "Personale";
    }
    @Override
    public String toCSV() {
        if (compleanno != null && !compleanno.isEmpty()) {
            return getTipo() + "," + nome + "," + cognome + "," + telefono + "," + email + "," + compleanno;
        }
        return getTipo() + "," + nome + "," + cognome + "," + telefono + "," + email;
    }
    @Override
    public String toString() {
        return "👤" + nome + " " + cognome;
    }
}