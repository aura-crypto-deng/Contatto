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
    public Contatto(String riga) {
    	String [] parti= riga.split(",");
    	nome =parti[0];
    	cognome= parti[1];
    	telefono=parti[2];
    }
    public void 

    
    @Override
    public String toString() {
        return nome + " " + cognome + " - " + telefono;
    }
}