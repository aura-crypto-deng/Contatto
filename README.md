# Rubrica Telefonica - Yuhao Deng

## File del progetto

| File | Descrizione |
|------|-------------|
| `RubricaPerfetta.java` | Classe principale, interfaccia e controller |
| `Contatto.java` | Classe base astratta (nome, cognome, telefono) |
| `ContattoPersonale.java` | Estende Contatto, aggiunge email e compleanno |
| `ContattoAziendale.java` | Estende Contatto, aggiunge azienda, sede e ruolo |
| `rubrica.fxml` | Layout dell'interfaccia con GridPane |
| `rubrica.csv` | File di salvataggio dei contatti |

## Funzionalita

- Aggiungi contatto (Personale o Aziendale)
- Ricerca in tempo reale per nome o cognome
- Elimina contatto selezionato
- Modifica con doppio clic
- Salvataggio automatico su CSV
- Caricamento automatico all'avvio

## Come funziona

### 1. Avvio del programma
- JavaFX carica il file `rubrica.fxml`
- Viene chiamato il metodo `initialize()`
- Viene letto il file `rubrica.csv` (se esiste)
- I contatti vengono mostrati nella ListView

### 2. Ricerca
- L'utente digita nel campo "Cerca"
- Un listener cattura ogni carattere digitato
- Viene chiamato `aggiornaList()`
- Il metodo filtra i contatti in base a nome o cognome
- La ListView mostra solo i contatti trovati

### 3. Aggiunta contatto
- L'utente sceglie Personale o Aziendale
- I campi del form cambiano dinamicamente
- Dopo aver riempito i campi, si clicca "Aggiungi"
- Il contatto viene salvato su CSV e mostrato nella lista

### 4. Modifica contatto
- Doppio clic su un contatto nella lista
- Il form viene riempito con i dati del contatto
- Il contatto vecchio viene rimosso
- Dopo la modifica, si aggiunge come nuovo contatto

### 5. Elimina contatto
- Si seleziona un contatto dalla lista
- Si clicca "Elimina selezionato"
- Il contatto viene rimosso dalla rubrica e dal file CSV

## Formato CSV

Separatore: virgola (`,`)

**Personale (5 o 6 campi):**
