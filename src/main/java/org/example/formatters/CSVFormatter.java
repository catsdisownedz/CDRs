package org.example.formatters;

import org.example.CDR;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVFormatter implements BaseFormatter {
    @Override
    public void write(String fileName, List<CDR> records) {
        try (FileWriter csvWriter = new FileWriter(fileName)) {
            // Write CSV header
            csvWriter.append("ANUM,BNUM,SERVICE_TYPE,USAGE,START_DATE_TIME\n\n");

            // Write records
            for (CDR record : records) {
                csvWriter.append(record.getAnum()).append(",")
                        .append(record.getBnum() == null ? "null" : record.getBnum()).append(",")
                        .append(record.getServiceType()).append(",")
                        .append(String.valueOf(record.getUsage())).append(",")
                        .append(record.getStartDateTime()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
