package com.company.LoggerPcg;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static Logger logger;
    private static String logString = "";

    public static synchronized Logger getLogger(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    private Logger(){}

    public void addLogInfo(String logStringS){
        logString = logStringS;
        try (
                FileWriter writer = new FileWriter("logs.txt", true)) {
            writer.write(logString + "\n");
            writer.flush();
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

}
