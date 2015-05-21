package org.finance.bank.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscar
 */
public class FileManager {

    public void FileManager() {
    }

    /**
     * El metodo readFile lee un archivo de texto y retorna su contenido en
     * formato de StringBuffer
     * @param filename String
     * @return StringBuffer
     */
    public StringBuffer readFile(String filename) {
        StringBuffer sb = new StringBuffer();
        try {
            File file = new File(filename);
            String line = null;
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (FileNotFoundException fnfe) {
            Logger.getLogger(FileManager.class.getName()).log(Level.INFO, "No ha sido posible encontrar el archivo " + filename);
        } catch (IOException ioe) {
            Logger.getLogger(FileManager.class.getName()).log(Level.INFO, "Se ha producido un error durante la lectura del archivo " + filename);
        }
        return sb;
    }

    /**
     * Este metodo permite, dada una cadena de caracteres determinada,
     * salvar la misma como un archivo de texto, o agregarla a un archivo ya existente
     *
     * @param filename String
     * @param dataToWrite String
     * @param append boolean
     */
    public void saveFile(String filename, String dataToWrite, boolean append) {
        try {
            FileWriter fw = new FileWriter(filename, append);
            fw.write(dataToWrite);
            fw.close();
        } catch (IOException ioe) {
            Logger.getLogger(FileManager.class.getName()).log(Level.INFO, "Se ha producido un error durante la lectura del archivo " + filename);
        }
    }

    /**
     * Esta funcion permite, dado un archivo en particular, buscar dentro el mismo
     * determinados valures y cambiarlos por una serie de nuevos valores dados,
     * generando un objeto de tipo String con el resultado
     *
     * @param path String
     * @param valuesToSearch String[] Ejemplo {"NOMRE", "APELLIDO"}
     * @param valuesToReplace String[] Ejemplo {"Fernando Augusto", "Arturi"}
     * @return String
     */
    public String replaceValues(String path, String[] valuesToSearch, String[] valuesToReplace) {
        String line;
        StringBuffer textComplete = new StringBuffer();
        String tempText = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                textComplete.append(line);
            }
            br.close();
        } catch (FileNotFoundException fnfe) {
            Logger.getLogger(FileManager.class.getName()).log(Level.INFO, "No ha sido posible encontrar el archivo " + path);
        } catch (IOException ioe) {
            Logger.getLogger(FileManager.class.getName()).log(Level.INFO, "Se ha producido un error durante la lectura del archivo " + path);
        }
        for (int i = 0; i < valuesToSearch.length; i++) {
            int position = textComplete.indexOf(valuesToSearch[i]);
            if (position > 0) {
                tempText = textComplete.substring(0, position);
                tempText = tempText + valuesToReplace[i] + textComplete.substring(position + valuesToSearch[i].length(), textComplete.length());
                textComplete = new StringBuffer(tempText);
            }
        }
        return tempText;
    }
}
