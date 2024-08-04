package org.example.formatters;

import org.example.CDR;

import java.util.List;

public interface BaseFormatter {
    void write(String fileName, List<CDR> records);
}
