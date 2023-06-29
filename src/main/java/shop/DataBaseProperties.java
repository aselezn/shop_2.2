package shop;

import java.io.*;
import java.util.Properties;

public class DataBaseProperties extends Properties {

    private static DataBaseProperties dbp;
    private DataBaseProperties(){}

    public static DataBaseProperties get(){
        if(dbp==null) {
            dbp = new DataBaseProperties();
            File file = new File("properties");
            try {
                if (!file.exists()) {
                    throw new FileNotFoundException("File 'properties' not found");
                }
                dbp.load(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dbp;
    }
}