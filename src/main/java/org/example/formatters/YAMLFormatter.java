package org.example.formatters;

import org.example.database.entity.CDR;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class YAMLFormatter implements BaseFormatter {

    private final Yaml yaml;

    public YAMLFormatter() {
        DumperOptions options = new DumperOptions();

        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        this.yaml = new Yaml(options);
    }

    @Override
    public void write(String fileName, List<CDR> records) {



        try (FileWriter yamlWriter = new FileWriter(fileName)) {
            for (CDR record : records) {
                yaml.dump(List.of(record), yamlWriter);
                yamlWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}