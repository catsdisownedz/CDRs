package org.example.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.CDR;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONFormatter implements BaseFormatter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter;

    public JSONFormatter() {
        // i set pretty print for better readability
        objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        // enable writing dates as strings
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public void write(String fileName, List<CDR> records) {
        try (FileWriter jsonWriter = new FileWriter(fileName)) {
            // wrap the records in an array because json couldnt handle each one being its own object, had to do it all in an array
            objectWriter.writeValue(jsonWriter, records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
