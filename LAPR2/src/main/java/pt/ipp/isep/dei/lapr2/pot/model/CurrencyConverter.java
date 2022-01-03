package pt.ipp.isep.dei.lapr2.pot.model;

public interface CurrencyConverter{
    /**
     * Interface of the Currency converter
     *
     * @author RodrigoRodrigues
     */
    //The API to convert between currencies should, at least, convert euros to:  dolar (USA), libra (UK), Real (Brasil), Yen (Japan), Yuan (China)

    /**
     * Convertion rates for the different currencies to Euro (€).
     */

    double EURtoUSD = 1.13;
    double EURtoGBP = 0.90;
    double EURtoBRL = 5.62;
    double EURtoJPY = 120.66;
    double EURtoCNY = 7.98;
    double EURtoCAD = 1.54;
    double EURtoCHF = 1.07;
    double EURtoAUD = 1.65;
    double EURtoINR = 85.98;
    double EURtoHKD = 8.75;
    double EURtoSEK = 10.54;
    double EURtoTRY = 7.73;
    double EURtoMXN = 25.63;
    double EURtoNOK = 10.87;
    double EURtoRUB = 79.17;
    
    double convertEURto(String country, double amount);
    }