package org.example.formatters;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.example.database.entity.CDR;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XMLFormatter implements BaseFormatter {
    @Override
    public void write(String fileName, List<CDR> records) {
        try {
            JAXBContext context = JAXBContext.newInstance(CDR.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            try (FileWriter xmlWriter = new FileWriter(fileName)) {
                for (CDR record : records) {
                    marshaller.marshal(record, xmlWriter);
                }
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

}
