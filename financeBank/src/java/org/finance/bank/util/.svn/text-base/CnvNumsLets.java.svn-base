package org.finance.bank.util;

import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class CnvNumsLets {

    private String Unidad[] = {"Cero", "Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve", "Diez", "Once", "Doce", "Trece", "Catorce", "Quince", "DieciSeis", "DieciSiete", "DieciOcho", "DieciNueve", "Veinte"};
    private String Decena[] = {"Venti", "Treinta", "Cuarenta", "Cincuenta", "Sesenta", "Setenta", "Ochenta", "Noventa"};
    private String Centena[] = {"Cien", "DosCientos", "TresCientos", "CuatroCientos", "Quinientos", "SeisCientos", "SeteCientos", "OchoCientos", "Novecientos", "Mil", "Un Millón", "Millones", "Un Billón", "Billones"};

    public CnvNumsLets() {
    }

    private String getUnidad(long Numero) {
        String aux = "";
        for (int p = 0; p <= 20; p++) {
            if (Numero == p) {
                aux = Unidad[p] + " ";
                return aux;
            }
        }
        return "";
    }

    private String getDecena(long Numero) {
        String aux = "";
        long pf = Numero % 10;
        long pi = (Numero / 10);
        int p = 0;
        boolean sal = false;
        while (p <= 8 & sal == false) {
            if (pi == p + 2) {
                aux = Decena[p];
                sal = true;
            }
            p++;
        }
        if (pf == 0) {
            return aux + " ";
        }
        if (Numero > 20 & Numero < 30) {
            return (aux + getUnidad(pf) + " ");
        }
        return aux + " y " + getUnidad(pf) + " ";
    }

    private String getCentena(long Numero) {
        String aux = "", aux2 = "";
        long pf = Numero % 100;
        long pi = (Numero / 100);
        int p = 0;
        boolean sal = false;
        while (p <= 10 & sal == false) {
            if (pi == p + 1) {
                aux = Centena[p];
                sal = true;
            }
            p++;
        }
        if (pf == 0) {
            return aux;
        }
        if (pf < 21) {
            aux2 = getUnidad(pf);
        } else {
            aux2 = getDecena(pf);
        }
        if (Numero < 200) {
            return (aux + "to " + aux2 + " ");
        } else {
            return (aux + " " + aux2 + " ");
        }
    }

    private String getMil(long Numero) {
        String aux = "";
        long pf = Numero % 1000;
        long pi = (Numero / 1000);
        if (Numero == 1000) {
            return "MIL";
        }
        if (Numero > 1000 & Numero < 1999) {
            aux = Centena[9] + " ";
        } else {
            aux = resolverIntervalo(pi) + Centena[9] + " ";
        }
        if (pf != 0) {
            return (aux + resolverIntervalo(pf) + " ");
        }
        return aux;
    }

    private String getMillon(long Numero) {
        String aux = "";
        long pf = Numero % 1000000;
        long pi = (Numero / 1000000);
        if (Numero > 1000000 & Numero < 1999999) {
            aux = Centena[10] + " ";
        } else {
            aux = resolverIntervalo(pi) + Centena[11] + " ";
        }
        if (pf != 0) {
            return (aux + resolverIntervalo(pf) + " ");
        }
        return aux;
    }

    private String getBillon(long Numero) {
        String aux = "";
        long pf = Numero % 1000000000;
        long pi = (Numero / 1000000000);
        if (Numero > 1000000000 & Numero < 1999999999) {
            aux = Centena[12] + " ";
        } else {
            aux = resolverIntervalo(pi) + Centena[13] + " ";
        }
        if (pf != 0) {
            return (aux + resolverIntervalo(pf) + " ");
        }
        return aux;
    }

    private String resolverIntervalo(long Numero) {
        if (Numero >= 0 & Numero <= 20) {
            return getUnidad(Numero);
        }
        if (Numero >= 21 & Numero <= 99) {
            return getDecena(Numero);
        }
        if (Numero >= 100 & Numero <= 999) {
            return getCentena(Numero);
        }
        if (Numero >= 1000 & Numero <= 999999) {
            return getMil(Numero);
        }
        if (Numero >= 1000000 & Numero <= 999999999) {
            return getMillon(Numero);
        }
        if (Numero >= 1000000000 & Numero <= 2000000000) {
            return getBillon(Numero);
        }
        return "<<El numero esta fuera del rango>>";
    }

    public String toLetras(long Numero) {
        if (Numero >= 0) {
            return (resolverIntervalo(Numero));
        } else {
            return (" Menos " + resolverIntervalo(Numero * -1));
        }
    }

    public boolean pausa(long p) {
        try {
            Thread.sleep(p);
        } catch (Exception e) {
        }
        return true;
    }
}
