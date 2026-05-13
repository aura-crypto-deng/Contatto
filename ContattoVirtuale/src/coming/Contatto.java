package coming;

public abstract class Contatto {
    protected String nome;
    protected String cognome;
    protected String telefono;
    
    public Contatto(String nome, String cognome, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
    }
    
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getTelefono() { return telefono; }
    
    public abstract String getTipo();
    public abstract String toCSV();
    
    @Override
    public String toString() {
        return nome + " " + cognome + " - " + telefono;
    }
}