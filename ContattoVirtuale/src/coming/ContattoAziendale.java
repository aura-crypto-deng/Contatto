package coming;

public class ContattoAziendale extends Contatto {
    String azienda;
    String sede;
    String ruolo;

    public ContattoAziendale(String nome, String cognome, String telefono, String azienda, String sede, String ruolo) {
        super(nome, cognome, telefono);
        this.azienda = azienda;
        this.sede = sede;
        this.ruolo = ruolo;
    }

    public String getTipo() {
        return "Aziendale";
    }

    public String toString() {
        return getTipo() + "," + nome + "," + cognome + "," + telefono + "," + azienda + "," + sede + "," + ruolo;
    }
}