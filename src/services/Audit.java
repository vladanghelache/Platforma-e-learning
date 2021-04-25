package services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Audit {
    private static Audit instance;

    private Audit(){

    }

    public static Audit getInstance(){
        if (instance == null){
            instance = new Audit();
        }

        return instance;
    }

    public void writeAudit(String nume_actiune, LocalDateTime timestamp) throws IOException {
        FileWriter fileWriter =  new FileWriter("Data/Actiuni.csv", true);
        fileWriter.write("\n");
        fileWriter.write(nume_actiune);
        fileWriter.write(",");
        fileWriter.write(timestamp.toString());


        fileWriter.close();


    }




}
