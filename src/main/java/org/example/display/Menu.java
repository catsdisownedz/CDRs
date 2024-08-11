package org.example.display;

import org.example.CDR;
import org.example.formatters.BaseFormatter;
import org.example.formatters.CSVFormatter;
import org.example.utils.*;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.example.Main.OUTPUT_DIR;

public class Menu {
    private static BaseFormatter[] formatters = new BaseFormatter[0];
    private static String username;
    public static LoginMenu mn = new LoginMenu(formatters);
    private static Random rd = new Random();

    public Menu(String username, BaseFormatter[] formatters) {
        this.username = username;
        this.formatters = formatters;
    }

    public static void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(Color.colorText("Hello", Color.underline ) + ", " + Color.colorText(username,Color.blue) + "!");
            System.out.println(Color.colorText("1)", Color.baby_blue) +" View Data files");
            System.out.println(Color.colorText("2)", Color.green) + " Filter Results By");
            System.out.println(Color.colorText("3)", Color.orange) + " View Service Type Volume");
            System.out.println(Color.colorText("4)", Color.lavender) + " Revenue calculator");
            System.out.println(Color.colorText("5)", Color.red) + " Logout");
            System.out.print("Choose an option: ");
            int choice = 0;
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (IllegalArgumentException ex) {
                System.out.println(Color.colorText("You need to enter a Number. Choose from numbers 1-5", Color.red));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                display();
            }

            switch (choice) {
                case 1:
                    viewDataFiles(scanner);
                    break;
                case 2:
                    filterResultsBy(scanner);
                    break;
                case 3:
                    System.out.println("Options: ");
                    System.out.println("1) View today's most " +  Color.colorText("heated", Color.red) + " service records");
                    System.out.println("2) View certain service volume (call / sms / data) records");
                    int which = 0;

                    try{
                        which = scanner.nextInt();
                        scanner.nextLine();
                    }catch(IllegalArgumentException ex) {
                        System.out.println(Color.colorText("You need to enter a Number. Choose 1 or 2", Color.red));
                    }

                    viewServiceTypeVolume(scanner, which);
                    break;
                case 4:
                    revenueCalculator(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...\n");
                    mn.display();
                    return;
                default:
                    System.out.println(Color.colorText("Invalid option.", Color.red));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    display();
                    break;
            }
        }
    }

    private static void viewDataFiles(Scanner scanner) {
        List<CDR> cdrList = CSVFormatter.normalList();
        printList(cdrList);
    }

    private static void filterResultsBy(Scanner scanner) {
        System.out.println(Color.colorText("\nFilter results by:", Color.green));
        System.out.println("1) Alphabetical order");
        System.out.println("2) Service type");
        System.out.println("3) Usage Rates");
        System.out.println(Color.colorText("4)", Color.red) +" Go Back");
        System.out.print("\nChoose an option: ");
        int choice = 0;

        try{
            choice = scanner.nextInt();
            scanner.nextLine();
        }catch(IllegalArgumentException ex) {
            System.out.println(Color.colorText("You need to enter a number between 1-4", Color.red));
        }

        List<CDR> sortedList = new ArrayList<>();
        switch (choice) {
            case 1:
                System.out.print("Anum or Bnum? Type here: ");
                String num= scanner.nextLine().toLowerCase();
                if(num.equals("anum")){
                    sortedList = CSVFormatter.sortByAnum();
                }
                else if(num.equals("bnum")){
                    sortedList = CSVFormatter.sortByBnum();
                }
                else{
                    System.out.println(Color.colorText("Type either anum or bnum (Capitalization doesn't matter)", Color.red));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    filterResultsBy(scanner);
                }
                break;
            case 2:
                System.out.println("Filter by service type:");
                typeChoiceDisplay();
                System.out.print("Choose an option: ");
                int typeChoice = 0;
                try{
                    typeChoice = scanner.nextInt();
                    scanner.nextLine();
                }catch(IllegalArgumentException ex) {
                    System.out.println(Color.colorText("You need to choose a Number between 1-3", Color.red));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    display();
                }
                sortedList= CSVFormatter.filterByServiceType(typeChoice);
                break;
            case 3:
                sortedList = CSVFormatter.sortByUsage();
            case 4:
                display();
            default:
                System.out.println(Color.colorText("Invalid option.", Color.red));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                display();
                break;
        }
        System.out.println(Color.colorText("Filtered Results:", Color.green));
        printList(sortedList);
        openFileOrExit(scanner, sortedList);
    }

    //finished function
    private static void viewServiceTypeVolume(Scanner scanner, int choice) {
        List<CDR> filteredList = new ArrayList<>();
        if(choice == 1){
            filteredList = CSVFormatter.getServiceTypeList();
            System.out.print("\nToday's most repeated service: "+ Color.colorText(filteredList.getFirst().getServiceType().toUpperCase(),Color.red));
            printList(filteredList);
            openFileOrExit(scanner, filteredList);
        } else if(choice == 2){
            System.out.println("View service type volume:");
            typeChoiceDisplay();
            System.out.print("Choose an option: ");
            int choice2 = 0;
            try{
                choice2 = scanner.nextInt();
                scanner.nextLine();
            }catch(IllegalArgumentException ex) {
                System.out.println(Color.colorText("You need to choose a Number between 1-3", Color.red));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                display();
            }

            filteredList = CSVFormatter.filterByServiceType(choice2);
            printList(filteredList);
            openFileOrExit(scanner, filteredList);
        }
        else{
            System.out.println(Color.colorText("Invalid choice.", Color.red));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            display();
        }

    }

    private static void typeChoiceDisplay() {
        System.out.println(Color.colorText("1) Call", Color.yellow));
        System.out.println(Color.colorText("2) SMS", Color.purple));
        System.out.println(Color.colorText("3) Data", Color.cyan));
    }


    private static void revenueCalculator(Scanner scanner) {
        Boolean validDate = false;
        LocalDateTime specificDate = null;

        System.out.println(Color.colorText("\nRevenue calculator:", Color.blue));
        System.out.println("1) Today");
        System.out.println("2) Yesterday");
        System.out.println("3) Other..");
        System.out.println("4) Go Back");
        System.out.print("Choose: ");
        int choice = 0;
        try{
            choice= scanner.nextInt();
            scanner.nextLine();
        }catch(IllegalArgumentException ex) {
            System.out.println(Color.colorText("You need to choose a Number between 1-4", Color.red));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            revenueCalculator(scanner);
        }

        List<CDR> cdrList = new ArrayList<>();

        switch (choice) {
            case 1:
                cdrList = CSVFormatter.normalList(); // Existing list for today
                calculateAndPrintRevenue(cdrList, getCurrentDate(), scanner);
                break;
            case 2:
                LocalDateTime yesterday = LocalDate.now().minusDays(1).atStartOfDay();
                cdrList = generateAndSaveRecordsForDate(yesterday, scanner);
                calculateAndPrintRevenue(cdrList, yesterday.toLocalDate().toString(), scanner);
                break;
            case 3:
                String dateStr = null;
                while (!validDate) {
                    try {
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        dateStr = scanner.nextLine();
                        specificDate = LocalDate.parse(dateStr).atStartOfDay();
                        validDate = true;
                    } catch (DateTimeParseException e) {
                        System.out.println(Color.colorText("Invalid date format. Please enter the date in the format YYYY-MM-DD.", Color.red));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(e);
                        }
                        revenueCalculator(scanner);
                    }
                }
                cdrList = generateAndSaveRecordsForDate(specificDate, scanner);
                calculateAndPrintRevenue(cdrList, dateStr, scanner);

                break;
            case 4:
                display();
                return;
            default:
                System.out.println(Color.colorText("Invalid option.", Color.red));
                display();
                break;
        }
    }

    // Helper method to generate and save CDR records for a specific date
    private static List<CDR> generateAndSaveRecordsForDate(LocalDateTime specificDate, Scanner scanner) {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator(
                new NameExtracter(),
                new ServiceTypeGenerator(),
                new UsageGenerator(),
                new StartDateTimeGenerator()
        );

        List<CDR> cdrList = new ArrayList<>();
        int num = 60;
        for(int i=0; i< num; i++){
            try{
               cdrList.add(randomDataGenerator.generateRecordsForDate(specificDate));
            }catch(IllegalArgumentException e){
                i--;
            }
        }
        String dateStr = specificDate.toLocalDate().toString();
        String fileName = Paths.get(OUTPUT_DIR, dateStr + ".csv").toString();

        CSVFormatter csvFormatter = new CSVFormatter();
        csvFormatter.write(fileName, cdrList);

        System.out.println(Color.colorText("New CDR records for " + dateStr + " generated and saved to " + fileName, Color.green));

        return cdrList;
    }



    private static void calculateAndPrintRevenue(List<CDR> cdrList, String date,Scanner scanner) {
        double smsRevenue = 0;
        double dataRevenue = 0;
        double callRevenue = 0;

        for (CDR cdr : cdrList) {
            if (cdr.getStartDateTime().startsWith(date)) {
                switch (cdr.getServiceType().toLowerCase()) {
                    case "sms":
                        smsRevenue += cdr.getUsage() * 0.25;
                        break;
                    case "data":
                        dataRevenue += cdr.getUsage() * 0.50;
                        break;
                    case "call":
                        callRevenue += cdr.getUsage() * 0.50;
                        break;
                }
            }
        }

        double smsRevenuePerMinute = smsRevenue;
        double dataRevenuePerMinute = dataRevenue;
        double callRevenuePerMinute = callRevenue;

        double smsRevenuePerHour = smsRevenue * 60;
        double dataRevenuePerHour = dataRevenue * 60;
        double callRevenuePerHour = callRevenue * 60;

        System.out.println("Revenue Stats for " + date + ":");
        System.out.println(Color.colorText("SMS:", Color.yellow));
        System.out.printf("    Revenue per minute: $%.2f\n", smsRevenuePerMinute);
        System.out.printf("    Revenue per hour: $%.2f\n", smsRevenuePerHour);
        System.out.printf("    Total revenue on this day: $%.2f\n", smsRevenue);
        System.out.println(Color.colorText("CALL:", Color.purple));
        System.out.printf("    Revenue per minute: $%.2f\n", callRevenuePerMinute);
        System.out.printf("    Revenue per hour: $%.2f\n", callRevenuePerHour);
        System.out.printf("    Total revenue on this day: $%.2f\n", callRevenue);
        System.out.println(Color.colorText("DATA:", Color.cyan));
        System.out.printf("    Revenue per minute: $%.2f\n", dataRevenuePerMinute);
        System.out.printf("    Revenue per hour: $%.2f\n", dataRevenuePerHour);
        System.out.printf("    Total revenue on this day: $%.2f\n", dataRevenue);

        double totalRevenue = smsRevenue + dataRevenue + callRevenue;
        System.out.printf("Total Revenue: $%.2f\n", totalRevenue);
        //openFileOrExit(scanner,listaya );
    }



    private static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    private static void openFileOrExit(Scanner scanner, List<CDR> filteredList){
        System.out.println(Color.colorText("\n\nWould you like to export this info to a file?",  Color.yellow));
        System.out.println("1) Yes");
        System.out.println("2) No "+ Color.colorText("(EXIT)", Color.red));
        System.out.print("Choose: ");
        int choice = 0;
        try{
            choice = scanner.nextInt();
            scanner.nextLine();
        }catch(IllegalArgumentException e){
            System.out.println(Color.colorText("You need to enter a Number. Choose 1 or 2.", Color.red));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                throw new RuntimeException(e);
            }
            openFileOrExit(scanner, filteredList);
        }

        switch (choice) {
            case 1:
                String format = "";
                while (true) {
                    System.out.print(Color.colorText("In what format? (XML, JSON, YAML, CSV)\nType it here: ", Color.lavender));
                    format = scanner.nextLine().toLowerCase();
                    if (format.equals("xml") || format.equals("json") || format.equals("yaml") || format.equals("csv")) {
                        break; // Valid format, exit loop
                    } else {
                        System.out.println(Color.colorText("Invalid file type. Please enter one of the following formats: XML, JSON, YAML, CSV. (Capitalization doesn't matter)", Color.red));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        openFileOrExit(scanner, filteredList);
                    }
                }

                System.out.println("Name your file: ");
                String name = scanner.nextLine();
                String fileName = Paths.get(OUTPUT_DIR, name + "." + format).toString();
                System.out.println(Color.colorText("Your file (" + fileName + ") was created successfully in cdr_output directory.", Color.green));

                BaseFormatter fm = getFormatter(format);
                if (fm != null) {
                    fm.write(fileName, filteredList);
                }
              //  DirectoryControls.openFile(fileName);
                break;

            case 2:
                LoginMenu.displayRedirectingMessage();
                display();
        }
    }

    private static BaseFormatter getFormatter(String format) {
        for (BaseFormatter formatter : formatters) {
            if (formatter.getClass().getSimpleName().toLowerCase().contains(format)) {
                return formatter;
            }
        }
        return null;
    }

    private static void printList(List<CDR> list) {
        int index = 1;
        for (CDR cdr : list) {
            System.out.println(cdr.toString(index));
            index++;
        }
    }
}
