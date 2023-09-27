package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class InterazioniDB {
    Connection connection;

    private static final String DEFAULT_HOST = "jdbc:mysql://localhost:3306/fardella",
                          DEFAULT_USER = "root",
                          DEFAULT_PSW = "Vf19ITmdv96Vf19ITmdv96";

    // INSERT
    private static final String insertUtente = "insert into utenti(tipo_utente) values (?)",
                          insertCredenziali = "insert into credenziali select ?,?,SHA2(?, 256)",
                          insertCliente = "insert into clienti values(?,?,?,?,?)",
                          insertCarta = "insert into carte values (?,?,?,?,?)",
                          insertTransazione = "insert into transazioni " +
                                  "(data_transazione, ref_cod_fis, ref_id_macchina, ref_id_prodotto, importo)" +
                                  " values (?,?,?,?,?)";

    // SELECT per UTENTE
    private static final String selectUltimoUtente = "select id from utenti order by id desc limit 0,1",
                          selectUtenteCredenziali = "select * from utenti u, credenziali cr" +
                                  " where u.id = cr.ref_id and cr.email=? and cr.keyword=?";

    // SELECT per CLIENTE
    private static final String selectClienteFromID = "select * from utenti u, clienti cl" +
                                  " where u.id = cl.ref_id and u.id=?",
                          selectCarteUtente = "select * from clienti cl, carte ca " +
                                  "where cl.codice_fiscale = ca.ref_codice_fiscale and cl.codice_fiscale=?";

    // SELECT per CARTE
    private static final String selectSaldoCarta = "select saldo from carte where ref_codice_fiscale=? and numero=?";

    // SELECT per MACCHINE
    private static final String selectStatoMacchina = "select stato from macchine where ref_id=?",
                          selectMacchina = "select * from utenti u, macchine m" +
                                  " where u.id = m.ref_id and u.id=?",
                          selectProdotti = "select * from prodotti",
                          selectMacchineLibere = "select * from macchine where stato=1";

    // SELECT per CONNESSIONI
    private static final String selectConnessioneCliente = "select * from connessioni where ref_cf_cliente=?",
                                selectConnessioneMacchina = "select * from connessioni where ref_id_macchina=?";

    // SELECT per TRANSAZIONI
    private static final String selectTransazioniCliente = "select * from transazioni where ref_cod_fis=? " +
            "order by id_transazione desc limit 3";

    // DELETE
    private static final String deleteCartCF = "delete from carte where ref_codice_fiscale=? and numero=?";

    // UPDATE
    private static final String updateStatoMacchina = "update macchine set stato=? where (ref_id=?)",
                               updateConnessioni = "update connessioni set ref_cf_cliente=? " +
                                       "where (ref_id_macchina=?)",
                               updateSaldoCarte = "update carte set saldo=? where numero=?";

    // COSTRUTTORE

    public InterazioniDB() {
        this.setConnection(DEFAULT_HOST, DEFAULT_USER, DEFAULT_PSW);
    }

    // SEZIONE UTENTI

    public boolean insertUtente(String tipoUtente){
        try {
            PreparedStatement insertUtenteStmt = this.getConnection().prepareStatement(insertUtente);

            insertUtenteStmt.setString(1, tipoUtente);

            insertUtenteStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public int getUltimoUtente(){
        try {
            Statement selectUltimoUtenteQuery = this.getConnection().createStatement();

            ResultSet ultimoUtente = selectUltimoUtenteQuery.executeQuery(selectUltimoUtente);
            if(ultimoUtente.next())
                return ultimoUtente.getInt(1);
            else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet selectCredenzialiUtente(String email, String password){
        try {
            PreparedStatement selectUtenteStmt = this.getConnection().prepareStatement(selectUtenteCredenziali);
            selectUtenteStmt.setString(1, email);
            selectUtenteStmt.setString(2, password);

            return selectUtenteStmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // SEZIONE CLIENTI

    public boolean insertCredenziali(int id, String email, String password){
        try{
        PreparedStatement insertCredenzialiStmt = this.getConnection().prepareStatement(insertCredenziali);

        insertCredenzialiStmt.setInt(1, id);
        insertCredenzialiStmt.setString(2, email);
        insertCredenzialiStmt.setString(3, password);

        insertCredenzialiStmt.executeUpdate();

        return true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean insertCliente(int id, String nome, String cognome, String dataNascita, String codiceFiscale){
        try {
            PreparedStatement insertClienteStmt = this.getConnection().prepareStatement(insertCliente);

            insertClienteStmt.setInt(1, id);
            insertClienteStmt.setString(2, nome.toUpperCase());
            insertClienteStmt.setString(3, cognome.toUpperCase());
            insertClienteStmt.setDate(4, stringa2SQLDate(dataNascita));
            insertClienteStmt.setString(5, codiceFiscale.toUpperCase());

            insertClienteStmt.executeUpdate();

            return true;

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cliente selectCliente(int id){
        try {
            PreparedStatement selectClienteStmt = this.getConnection().prepareStatement(selectClienteFromID);
            selectClienteStmt.setInt(1, id);

            ResultSet clienti = selectClienteStmt.executeQuery();

            if(clienti.next()){
                //Creo un oggetto cliente e un oggetto credenziali, secondo il modello dei dati
                Cliente cliente = new Cliente();

                //Imposto le proprietà ereditate da Utente
                cliente.setId(id);
                cliente.setTipoUtente("cliente");

                //Imposto le proprietà base dell'oggetto Cliente
                cliente.setNome(clienti.getString(4));
                cliente.setCognome(clienti.getString(5));
                cliente.setDataDiNascita(clienti.getDate(6));
                cliente.setCodiceFiscale(clienti.getString(7));

                //Imposto eventuali carte associate
                cliente.setCarteList(selectCarteUtente(cliente.getCodiceFiscale()));

                return cliente;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // SEZIONE CARTE

    public List<Carta> selectCarteUtente(String codiceFiscale){
        List<Carta> lista = new LinkedList<>();

        try {
            PreparedStatement selectCarteUtenteStmt = this.connection.prepareStatement(selectCarteUtente);
            selectCarteUtenteStmt.setString(1, codiceFiscale);

            ResultSet result = selectCarteUtenteStmt.executeQuery();

            while(result.next()){
                Carta carta = new Carta();

                carta.setCodiceFiscale(codiceFiscale);
                carta.setNumero(result.getString(7));
                carta.setDataScadenza(result.getDate(8));
                carta.setCvv(result.getString(9));
                carta.setSaldo(result.getDouble(10));

                lista.add(carta);
            }
            return lista;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public double selectSaldoCarta(String codiceFiscale, String numero){
        try{
            PreparedStatement selectSaldo = this.getConnection().prepareStatement(selectSaldoCarta);

            selectSaldo.setString(1, codiceFiscale);
            selectSaldo.setString(2, numero);

            ResultSet resultSet = selectSaldo.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean updateSaldoCarta(double nuovoSaldo, String numero){
        try{
            PreparedStatement updateSaldo = this.getConnection().prepareStatement(updateSaldoCarte);

            updateSaldo.setDouble(1, nuovoSaldo);
            updateSaldo.setString(2, numero);

            updateSaldo.executeUpdate();
            this.getConnection().commit();

            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // SEZIONE MACCHINE

    public boolean selectStatoMacchina(int id){
        try{
            PreparedStatement selectStatoMStmt = this.getConnection().prepareStatement(selectStatoMacchina);

            selectStatoMStmt.setInt(1, id);
            ResultSet macchine = selectStatoMStmt.executeQuery();

            if(macchine.next()){
                return macchine.getBoolean(1);

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Macchina selectMacchinaID(int id){
        try {
            PreparedStatement selectMacchinaStmt = this.getConnection().prepareStatement(selectMacchina);
            selectMacchinaStmt.setInt(1, id);

            ResultSet macchine = selectMacchinaStmt.executeQuery();
            if (macchine.next()) {

                //Creo un oggetto macchina e un oggetto credenziali, secondo il modello dei dati
                Macchina m = new Macchina();

                //Imposto le proprietà base dell'oggetto Macchina
                m.setIndirizzo(macchine.getString(4));
                m.setStato(macchine.getBoolean(5));
                m.setListaProdotti(this.selectProdotti());

                return m;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Prodotto> selectProdotti(){
        try{
            List<Prodotto> listaProdotti = new LinkedList<>();
            PreparedStatement selectProdottiStmt = this.getConnection().prepareStatement(selectProdotti);

            ResultSet result = selectProdottiStmt.executeQuery();
            while(result.next()){
                Prodotto prodotto = new Prodotto();

                prodotto.setId(result.getInt(1));
                prodotto.setNome(result.getString(2));
                prodotto.setPrezzo(result.getDouble(3));

                listaProdotti.add(prodotto);
            }
            return listaProdotti;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStatoMacchina(boolean stato, int id){
        try{
            PreparedStatement updateStatoMacchinaStmt = this.getConnection().prepareStatement(updateStatoMacchina);

            updateStatoMacchinaStmt.setBoolean(1, stato);
            updateStatoMacchinaStmt.setInt(2, id);

            updateStatoMacchinaStmt.executeUpdate();

            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // SEZIONE CONNESSIONI CLIENTE-MACCHINA

    public Connessione selectConnessione(String codiceFiscale){
        PreparedStatement preparedStatement = null;
        Connessione connessione = new Connessione();
        try {
            preparedStatement = this.getConnection().prepareStatement(selectConnessioneCliente);
            preparedStatement.setString(1, codiceFiscale);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                connessione.setIdMacchina(resultSet.getInt(1));
                connessione.setCodiceFiscaleCliente(codiceFiscale);

                return connessione;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connessione.setIdMacchina(0);
        connessione.setCodiceFiscaleCliente(codiceFiscale);

        return connessione;
    }

    public Connessione selectConnessione(int idMacchina){
        try{
            PreparedStatement selectConnessione = this.getConnection().prepareStatement(selectConnessioneMacchina);

            selectConnessione.setInt(1, idMacchina);

            ResultSet result = selectConnessione.executeQuery();
            if(result.next()){
                Connessione connessione = new Connessione();

                connessione.setIdMacchina(idMacchina);
                connessione.setCodiceFiscaleCliente(result.getString(2));

                return connessione;
            }
        } catch (SQLException e){
            e.printStackTrace();
            Connessione connessione = new Connessione();
            connessione.setIdMacchina(idMacchina);
            connessione.setCodiceFiscaleCliente("");

            return connessione;
        }
        Connessione connessione = new Connessione();
        connessione.setIdMacchina(idMacchina);
        connessione.setCodiceFiscaleCliente("");

        return connessione;
    }

    // SEZIONE TRANSAZIONI (DOVE SI CHIUDE LA CONNESSIONE)

    public boolean iscriviCliente(String... args){
        String nome = args[0],
                cognome = args[1],
                codiceFiscale = args[2],
                dataNascita = args[3],
                email = args[4],
                password = args[5];

        try {
            if(insertUtente("cliente")) {
                int id = getUltimoUtente();
                if (!(insertCredenziali(id, email, password) && insertCliente(id, nome, cognome, codiceFiscale, dataNascita)))
                    throw new SQLException();

                this.getConnection().commit();
                this.getConnection().close();

                return true;
            } else throw new SQLException();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                this.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    public Utente accedi(String email, String password){
        try{
            ResultSet result = selectCredenzialiUtente(email, password);

            //Se la query ha successo dovrebbe restituirmi solo una riga
            if(result.next()){
                //Salvo l'id dell'utente in una variabile
                int id = result.getInt(1);

                //Controllo il tipo dell'utente, dato che il percorso sarà diverso per clienti e macchine
                String tipoUtente = result.getString(2);

                if(tipoUtente.equals("cliente")){
                    Cliente cliente = selectCliente(id);
                    // Imposto le caratteristiche derivanti dall'essere utente

                    // ID
                    cliente.setId(id);

                    // Tipo di utente
                    cliente.setTipoUtente(tipoUtente);

                    // Credenziali
                    Credenziali credenziali = new Credenziali();

                    credenziali.setEmail(email);
                    credenziali.setPassword(password);

                    cliente.setCredenziali(credenziali);
                    // Stato connessione (con macchina)
                    Connessione connessione = this.selectConnessione(cliente.getCodiceFiscale());

                    cliente.setConnessione(connessione);

                    // Chiudo la connessione con il DB

                    this.getConnection().commit();
                    this.getConnection().close();

                    return cliente;
                } else if(tipoUtente.equals("macchina")) {
                    Macchina macchina = selectMacchinaID(id);

                    // Imposto le caratteristiche derivanti dall'essere utente

                    // ID
                    macchina.setId(id);

                    // Tipo di utente
                    macchina.setTipoUtente(tipoUtente);

                    // Credenziali
                    Credenziali credenziali = new Credenziali();

                    credenziali.setEmail(email);
                    credenziali.setPassword(password);

                    // Stato connessione (con cliente)
                    Connessione connessione = this.selectConnessione(id);

                    macchina.setConnessione(connessione);

                    // Chiudo la connessione con il DB

                    this.getConnection().commit();
                    this.getConnection().close();

                    return macchina;
                }

            }
        } catch (SQLException e){
            e.printStackTrace();
            try {
                this.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public boolean aggiungiCarta(double saldo, String ...args){
        try {
            PreparedStatement aggiungiCartaStmt = this.getConnection().prepareStatement(insertCarta);

            for(int i=0; i< args.length; i++){
                if(i == 2) {
                    java.sql.Date dataScadenza = this.stringa2SQLDate(args[i]+"-01");
                    aggiungiCartaStmt.setDate(3, dataScadenza);

                    continue;
                }
                aggiungiCartaStmt.setString(i+1, args[i]);
            }
            //Aggiungo un saldo casuale.
            aggiungiCartaStmt.setDouble(5, saldo);

            aggiungiCartaStmt.executeUpdate();

            //Chiudo la connessione
            this.getConnection().commit();
            this.getConnection().close();

            return true;

        } catch (SQLException | ParseException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean rimuoviCarta(String codiceFiscale, String numero){
        try{
            PreparedStatement deleteCartaStmt = this.getConnection().prepareStatement(deleteCartCF);
            deleteCartaStmt.setString(1, codiceFiscale);
            deleteCartaStmt.setString(2, numero);

            deleteCartaStmt.executeUpdate();

            this.getConnection().commit();
            this.getConnection().close();

            return true;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Macchina> selectMacchineLibere(){

        try{
            List<Macchina> list = new LinkedList<>();
            PreparedStatement selectMacchineStmt = this.getConnection().prepareStatement(selectMacchineLibere);

            ResultSet macchine = selectMacchineStmt.executeQuery();
            while(macchine.next()){
                Macchina macchina = new Macchina();

                macchina.setId(macchine.getInt(1));
                macchina.setIndirizzo(macchine.getString(2));
                macchina.setStato(macchine.getBoolean(3));
                macchina.setListaProdotti(selectProdotti());

                list.add(macchina);
            }
            this.getConnection().close();

            return list;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    public boolean setConnessione(int idMacchina, String codiceFiscale){
        try{
            if(updateStatoMacchina(false, idMacchina)) {

                PreparedStatement updateConnStmt = this.getConnection().prepareStatement(updateConnessioni);

                updateConnStmt.setString(1, codiceFiscale);
                updateConnStmt.setInt(2, idMacchina);

                updateConnStmt.executeUpdate();

                this.getConnection().commit();
                this.getConnection().close();

                return true;

            }

        } catch (SQLException e){
            e.printStackTrace();
            try {
                this.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean resetConnessione(int idMacchina){
        try{
            if(updateStatoMacchina(true, idMacchina)){
                PreparedStatement updateConnStmt = this.getConnection().prepareStatement(updateConnessioni);

                updateConnStmt.setString(1, "");
                updateConnStmt.setInt(2, idMacchina);

                updateConnStmt.executeUpdate();

                this.getConnection().commit();
                this.getConnection().close();

                return true;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void insertTransazione(String data, String codiceFiscale,
                                  int idMacchina, int idProdotto, double importo){
        try {
            PreparedStatement statement = this.getConnection().prepareStatement(insertTransazione);

            statement.setDate(1, stringa2SQLDate(data));
            statement.setString(2, codiceFiscale);
            statement.setInt(3, idMacchina);
            statement.setInt(4, idProdotto);
            statement.setDouble(5, importo);

            statement.executeUpdate();

            this.getConnection().commit();
            this.getConnection().close();

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

    }

    public List<Transazione> selectTransazioni(String codiceFiscale){
        List<Transazione> lista = new LinkedList<>();
        try{
            PreparedStatement statement = this.getConnection().prepareStatement(selectTransazioniCliente);

            statement.setString(1, codiceFiscale);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transazione transazione = new Transazione();

                transazione.setId(resultSet.getInt(1));
                transazione.setData(resultSet.getDate(2));
                transazione.setCliente(resultSet.getString(3));
                transazione.setIdMacchina(resultSet.getInt(4));
                transazione.setIdProdotto(resultSet.getInt(5));
                transazione.setPrezzo(resultSet.getDouble(6));

                lista.add(transazione);
            }
            this.getConnection().close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return lista;
    }

    public java.sql.Date stringa2SQLDate(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dataNascitaJava = sdf.parse(data);
        return new java.sql.Date(dataNascitaJava.getTime());
    }

    public void setConnection(String host, String user, String password){
        try {
            Driver driver =(Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
            this.connection = DriverManager.getConnection(host+"?user="+user+"&password="+password);

            this.connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return this.connection;
    }
}
