package org.finance.bank.util;

//import java.util.Date;

/**
 *
 * @author roger
 */
public class Correr {

    /**
     * @param args the command line arguments
     */

//    public static void main(String Args[]) {
//        String ff="2011/06/15 23:00:00";
//        String df="yyyy/MM/dd";
//        Date d1=DateUtil.convertStringToDate(df,ff);
//        Date d2=new Date();
//        System.out.println("d1 = " + d1);
//        System.out.println("d2 = " + d2);
//        System.out.println("dx = " + DateUtil.daysDifBetween(d1, d2));
//    }

    public static String formatTasa(String t) {
        String tasa = t;
        tasa = tasa.replace(",", "");
        tasa = tasa.replace(".", "-");
        String[] array = tasa.split("-");
        String decimal = "00";
        if (array.length > 1) {
            decimal = array[1];
        }
        for (int i = 2; i > decimal.length(); i--) {
            decimal = decimal + "0";
        }
        CnvNumsLets nl = new CnvNumsLets();
        int i = Integer.parseInt(array[0].toString());
        return nl.toLetras(i++).toUpperCase() + "CON " + decimal + "/100";
    }
}