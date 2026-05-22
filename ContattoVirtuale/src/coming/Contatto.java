package coming;

public class Contatto {
    protected String nome;
    protected String cognome;
    protected String telefono;
    
    public Contatto(String nome, String cognome, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
    }
    
    public String getTipo() {
        return "Base";
    }
    
    public String toCSV() {
        return getTipo() + "," + nome + "," + cognome + "," + telefono;
    }
    
    @Override
    public String toString() {
        return nome + " " + cognome;
    }
}