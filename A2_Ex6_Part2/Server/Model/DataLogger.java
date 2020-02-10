package Model;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class supports all the functions required to write a custom data log
 */
public class DataLogger {
    private FileWriter messageLog;
    private DateTimeFormatter dateTimeFormatter;

    public DataLogger(String logFileName, String dateTimeFormatPattern) {
        // Specify the Date Time format to use
        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatPattern);
        // Start the message log
        try {
            messageLog = new FileWriter(logFileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes to the server message log
     *
     * @param s message to write
     */
    public void writeToMessageLog(String s) {
        try {
            messageLog.append(dateTimeFormatter.format(LocalDateTime.now()) + " - " + s + "\n");
            messageLog.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
