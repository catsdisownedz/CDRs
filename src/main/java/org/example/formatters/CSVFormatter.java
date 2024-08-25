package org.example.formatters;

import org.example.database.entity.CDR;
import org.example.display.LoginMenu;
import org.example.display.Menu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CSVFormatter implements BaseFormatter {
    private static List<CDR> list = new ArrayList<>();
    static Map<String, Integer> freq = new HashMap<>();
    @Override
    public void write(String fileName, List<CDR> records) {
        list = records;
        try (FileWriter csvWriter = new FileWriter(fileName)) {
            csvWriter.append("ANUM , BNUM , SERVICE_TYPE , USAGE , START_DATE_TIME\n\n");

            for (CDR record : records) {
                String serviceType = record.getServiceType().toLowerCase();
                freq.put(serviceType, freq.getOrDefault(serviceType, 0) + 1);
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

    public static List<CDR> normalList(){
        return list;
    }

    public static String getMostRepeatedServiceType(){
        return freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(()->new RuntimeException("Can't find most repeated type."))
                .getKey();
    }

    public static List<CDR> getServiceTypeList(){
        String service = getMostRepeatedServiceType();
        List<CDR> typeList = new ArrayList<>();

        for(CDR record : list){
            if(record.getServiceType().toLowerCase().equals(service)){
                typeList.add(record);
            }
        }
            return typeList;
    }

    public static List<CDR> sortByAnum() {
        list.sort(Comparator.comparing(CDR::getAnum));
        return list;
    }

    public static List<CDR> sortByBnum() {
        list.sort(Comparator.comparing(CDR::getBnum));
        return list;
    }

    public static List<CDR> filterByServiceType(int choice) {
        List<CDR> filteredList = new ArrayList<>();
        switch (choice) {
            case 1:
                for (CDR record : list) {
                    if (record.getServiceType().equalsIgnoreCase("CALL")) {
                        filteredList.add(record);
                    }
                }
                break;
            case 2:
                for (CDR record : list) {
                    if (record.getServiceType().equalsIgnoreCase("SMS")) {
                        filteredList.add(record);
                    }
                }
                break;
            case 3:
                for (CDR record : list) {
                    if (record.getServiceType().equalsIgnoreCase("DATA")) {
                        filteredList.add(record);
                    }
                }
                break;
            default:
                System.out.println("Invalid choice. Please choose from SMS, CALL, or DATA.");
                LoginMenu.displayRedirectingMessage();
                Menu.display();
                break;
        }
        return filteredList;
    }

    public static List<CDR> sortByUsage() {
        list.sort(Comparator.comparingDouble(CDR::getUsage));
        return list;
    }

}
