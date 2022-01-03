package pt.ipp.isep.dei.lapr2.pot.ui.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Utils
{
    static public String readLineFromConsole(String strPrompt)
    {
        try
        {
            System.out.println("\n" + strPrompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    static public int readIntegerFromConsole(String strPrompt)
    {
        do
        {
            try
            {
                String strInt = readLineFromConsole(strPrompt);

                int iValor = Integer.parseInt(strInt);

                return iValor;
            } catch (NumberFormatException ex)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public double readDoubleFromConsole(String strPrompt)
    {
        do
        {
            try
            {
                String strDouble = readLineFromConsole(strPrompt);

                double dValor = Double.parseDouble(strDouble);

                return dValor;
            } catch (NumberFormatException ex)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public Date readDateFromConsole(String strPrompt)
    {
        do
        {
            try
            {
                String strDate = readLineFromConsole(strPrompt);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                Date date = df.parse(strDate);

                return date;
            } catch (ParseException ex)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public boolean confirm(String sMessage) {
        String strConfirm;
        do {
            strConfirm = Utils.readLineFromConsole("\n" + sMessage + "\n");
        } while (!strConfirm.equalsIgnoreCase("y") && !strConfirm.equalsIgnoreCase("n"));

        return strConfirm.equalsIgnoreCase("y");
    }
    static public Object showAndChoose(List list,String sHeader)
    {
        showList(list,sHeader);
        return chooseObject(list);
    }
    static public int showAndChooseIndex(List list,String sHeader)
    {
        showList(list,sHeader);
        return chooseIndex(list);
    }
    static public void showList(List list,String sHeader)
    {
        System.out.println(sHeader);

        int index = 0;
        for (Object o : list)
        {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        System.out.println("");
        System.out.println("0 - Cancel");
    }

    static public Object chooseObject(List list)
    {
        String option;
        int nOption;
        do
        {
            option = Utils.readLineFromConsole("Introduce the option: ");
            nOption = new Integer(option);
        } while (nOption < 0 || nOption > list.size());

        if (nOption == 0)
        {
            return null;
        } else
        {
            return list.get(nOption - 1);
        }
    }

    static public int chooseIndex(List list)
    {
        String option;
        int nOption;
        do
        {
            option = Utils.readLineFromConsole("Introduce the option: ");
            nOption = new Integer(option);
        } while (nOption < 0 || nOption > list.size());

        return nOption - 1;
    }
}
