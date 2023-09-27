package it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.controllers.macchina;

import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.dbControls.InterazioniDB;
import it.unipa.dinfo.lm32.fardella.vincenzo02.fardella_svm.model.Macchina;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@WebServlet(name = "ServletPagamento", value = "/Pagamento")
public class ServletPagamento extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String metodoPagamento = request.getParameter("metodo");
        String cliente = request.getParameter("cliente");
        Macchina macchina = (Macchina) request.getSession(false).getAttribute("utente");
        int idProdotto = Integer.parseInt(request.getParameter("prodotto"));
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String dataAcquisto = request.getParameter("dataAcquisto");


        if(metodoPagamento.equals("contanti")) {
            double cifraInserita = Double.parseDouble(request.getParameter("cifraInserita"));
            double resto = (double) Math.round((cifraInserita - prezzo)*100)/100;

            String responseContent;
            if(resto < 0.0){

                responseContent = "{" +
                        "\"titolo\": \"Impossibile ordinare\"," +
                        "\"corpo\": \"L'importo inserito è minore di € " + prezzo + ". È pregato/a di inserire altro credito.\"" +
                        "}";

            } else {
                InterazioniDB idb = new InterazioniDB();
                idb.insertTransazione(dataAcquisto, cliente, macchina.getId(), idProdotto, prezzo);
                responseContent = "{" +
                        "\"titolo\": \"Ordine effettuato\"," +
                        "\"corpo\": \"Sarà erogato un resto di € " + resto + ". Grazie per averci scelto!\"" +
                        "}";
            }


            response.setContentType("text/plain");
            response.getWriter().write(responseContent);
            response.getWriter().flush();
        } else if(metodoPagamento.equals("carta")){
            // Ulteriori parametri recuperati dalla richiesta
            String numeroCarta = request.getParameter("carta");
            String codiceFiscaleCliente = request.getParameter("cliente");

            // Proprietà necessarie per interagire con il database
            InterazioniDB idb = new InterazioniDB();
            double saldo = idb.selectSaldoCarta(codiceFiscaleCliente, numeroCarta);
            String responseContent;

            if(saldo < prezzo){
                responseContent = "{\n" +
                        "\"titolo\": \"Impossibile ordinare\",\n" +
                        "\"corpo\": \"Il saldo sulla carta è minore di € " + prezzo + ". È pregato/a " +
                        "di selezionare un'altra carta.\"\n" +
                        "}";
            } else {
                double saldoAggiornato = saldo - prezzo;
                if(idb.updateSaldoCarta(saldoAggiornato, numeroCarta)) {
                    idb.insertTransazione(dataAcquisto, cliente, macchina.getId(), idProdotto, prezzo);
                    responseContent = "{\n" +
                            "\"titolo\": \"Ordine effettuato con successo\",\n" +
                            "\"corpo\": \"€ " + prezzo + " Sono stati scalati dalla sua carta. Grazie di " +
                            "averci scelto.\"\n" +
                            "}";

                } else {
                    responseContent = "{\n" +
                            "\"titolo\": \"Ordine non completato\",\n" +
                            "\"corpo\": \"È avvenuto un errore imprevisto. Se il problema dovesse verificarsi " +
                            "di nuovo, contatti l'amministratore.\"\n" +
                            "}";
                }
            }
            response.setContentType("text/html");
            response.getWriter().write(responseContent);
            response.getWriter().flush();
        }
    }
}
