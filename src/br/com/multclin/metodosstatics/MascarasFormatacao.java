package br.com.multclin.metodosstatics;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

public class MascarasFormatacao {

    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

    public static String getDataAtualSimples() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hoje = LocalDate.now();
        return dtf.format(hoje);
    }

    

    public static String getDataAtualCompleta() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd 'de' MMMMM 'de' yyyy", new Locale("pt", "BR"));
        LocalDate hoje = LocalDate.now();
        return dtf.format(hoje);
    }

    public static String getCNPJ(String cnpj) {
        try {
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(cnpj);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return "erro gerar cnpj";
        }

    }

    //curso de relatorio mascaras customizadas pratica 
    public static String getValorMonetario(BigDecimal valor, boolean usaSimboloMoeda) {
        NumberFormat formate = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        DecimalFormatSymbols simbol = ((DecimalFormat) formate).getDecimalFormatSymbols();
        if (!usaSimboloMoeda) {
            simbol.setCurrencySymbol("");
        }
        ((DecimalFormat) formate).setDecimalFormatSymbols(simbol);
        return formate.format(valor);
    }

}
