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

    public String getTipo() {
        return "Personale";
    }

    public String toString() {
        if (compleanno != null && !compleanno.isEmpty()) {
            return getTipo() + "," + nome + "," + cognome + "," + telefono + "," + email + "," + compleanno;
        }
        return getTipo() + "," + nome + "," + cognome + "," + telefono + "," + email;
    }
}