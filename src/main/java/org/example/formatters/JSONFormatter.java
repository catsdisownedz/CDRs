package org.example.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.CDR;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONFormatter implements BaseFormatter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void write(String fileName, List<CDR> records) {
        try (FileWriter jsonWriter = new FileWriter(fileName)) {
            objectMapper.writeValue(jsonWriter, records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
