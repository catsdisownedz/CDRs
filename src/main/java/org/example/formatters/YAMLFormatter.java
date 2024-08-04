package org.example.formatters;

import org.example.CDR;
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
        this.yaml = new Yaml(options);
    }

    @Override
    public void write(String fileName, List<CDR> records) {
        try (FileWriter yamlWriter = new FileWriter(fileName)) {
            yaml.dump(records, yamlWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
