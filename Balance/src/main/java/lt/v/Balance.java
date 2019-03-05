package lt.v;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Balance  {
    protected double amount;
    protected List<String> categories = new ArrayList<>();
    protected List<Double> amounts = new ArrayList<>();
    protected List<Record> records = new ArrayList<>();
    public static int i=1;
    public int resetNumber() {

        return this.i=1;
    }




    public void setCategories(ArrayList<String> categories) {//   Do not delete, are using in Json!
        this.categories = categories;
    }

    public void setAmounts(ArrayList<Double> amounts) {//   Do not delete, are using in Json!
        this.amounts = amounts;
    }

    protected List<Record> getRecords() {
        return records;
    }


    public Balance() {
    }
    public void printCategories() {
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        for (int i = 0; i < this.categories.size(); i++) {
            System.out.println(String.format("|%-200s|", ("    " + (i + 1) + ". " + categories.get(i))));
        }
        System.out.println(String.format("|%-200s|", ("    " + (this.categories.size() + 1) + ". Exit")));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    Input number from menu..."));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
    }

    public void application() throws IOException {
        loadFromFile();
        while (true) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    case1();//Input income info
                    break;
                case 2:
                    case2();//Input costs info
                    break;
                case 3:
                    case3();//Records info
                    break;

                case 4:
                    saveToFileJson();
                    System.exit(0);
            }
        }
    }

    public static void printMenu() {
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    1. Input income info"));
        System.out.println(String.format("|%-200s|", "    2. Input costs info"));
        System.out.println(String.format("|%-200s|", "    3. Records info"));
        System.out.println(String.format("|%-200s|", "    4. Exit"));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    Input number from menu..."));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
    }
    public void printInfo() {
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(String.format("|%-200s|", ("    " + categories.get(i) + " : " + amounts.get(i) + Currency.EUR)));
        }
    }

    public String getCategoriesValue(int index) {
        return this.categories.get(index);
    }

    public void saveToFileJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Records.json");
        mapper.writeValue(file, getRecords());
    }

    public void printAllRecords() {
        resetNumber();
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        for (Object o : getRecords()) {
            System.out.println(String.format("|%-200s|", ("   " + o)));

        }
    }

    public void loadFromFile() throws IOException {
        ObjectMapper mapper1 = new ObjectMapper();
        File file1 = new File("Records.json");
        records = mapper1.readValue(file1, new TypeReference<List<Record>>() {});
        Record maxId;
        maxId = getRecords().stream().max(Comparator.comparing(Record::getId)).get();
        Record.setIdSet(maxId.getId());
    }

    public void case1() {
        Income income = new Income();
        Scanner scanner = new Scanner(System.in);
        income.printCategories();
        int choice11 = scanner.nextInt();
        while (choice11 > 0 && choice11 < income.categories.size() + 1) {
            System.out.println(String.format("|%-200s|", ("    Input " + income.getCategoriesValue(choice11 - 1) + " amount: ")));
            double choice12 = scanner.nextDouble();
            records.add(new Record(choice12, "Income", income.getCategoriesValue(choice11 - 1)));
            income.printCategories();
            choice11 = scanner.nextInt();

        }
    }

    public void case2() {
        Costs costs = new Costs();
        Scanner scanner = new Scanner(System.in);
        costs.printCategories();
        int choice21 = scanner.nextInt();
        while (choice21 > 0 && choice21 < costs.categories.size() + 1) {
            System.out.println(String.format("|%-200s|", ("    Input " + costs.getCategoriesValue(choice21 - 1) + " amount: ")));
            double choice22 = scanner.nextDouble();
            records.add(new Record(choice22, "Costs", costs.getCategoriesValue(choice21 - 1)));
            costs.printCategories();
            choice21 = scanner.nextInt();

        }
    }

    public void case3() throws IOException {
        Scanner scanner = new Scanner(System.in);
        Record record = new Record();
        record.printCategories();
        int choice32 = scanner.nextInt();
        while (choice32 > 0 && choice32 < record.categories.size() + 1) {

            switch (choice32) {
                case 1:
                    case31();
                    break;
                case 2:
                    case32();
                    break;
                case 3:
                    case33();
                    break;
                case 4:
                    case34();
                    break;
                case 5:
                    case35();
                    break;
                case 6:
                    case36();
                    break;
                case 7:
                    case37();
                    break;
            }
            record.printCategories();
            choice32 = scanner.nextInt();
        }
    }

    public void case31() {
        Double amountIncome = records.stream()
                .filter(r -> r.getFundsType().equals("Income"))
                .collect(Collectors.summingDouble(Record::getAmount));
        Double amountCosts = records.stream()
                .filter(r -> r.getFundsType().equals("Costs"))
                .collect(Collectors.summingDouble(Record::getAmount));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    Balance"));
        System.out.println(String.format("-%-200s-", "-".repeat(200)));
        System.out.println(String.format("|%-200s|", ("    Income total: " + amountIncome + Currency.EUR)));
        System.out.println(String.format("|%-200s|", ("    Costs total: " + amountCosts + Currency.EUR)));
        System.out.println(String.format("-%-200s-", "-".repeat(200)));
        System.out.println(String.format("|%-200s|", ("    Balance total:" + (amountIncome - amountCosts) + Currency.EUR)));
        System.out.println(String.format("-%-200s-", "-".repeat(200)));
    }

    public void case32() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    Edit record"));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        printAllRecords();
        System.out.println(String.format("|%-200s|", "    Input number of edited record..."));
        int choice321 = scanner.nextInt();
        System.out.println(String.format("|%-200s|", "    Input amount of edited record..."));
        double choice322 = scanner.nextDouble();
        records.get(choice321 - 1).setAmount(choice322);
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        printAllRecords();
        saveToFileJson();
    }
    public void case33() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    Delete record"));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        printAllRecords();
        System.out.println(String.format("|%-200s|", "    Input number of deleting record..."));
        int choice331 = scanner.nextInt();
        records.remove(choice331 - 1);
        printAllRecords();
        saveToFileJson();
    }
    public void case34() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    Record by dates"));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    Input start date(date format YYYY-mm-dd)"));
        String dateStart = scanner.next();
        LocalDate localDate = LocalDate.parse(dateStart);
        LocalDateTime d1 = LocalDateTime.of(localDate, LocalTime.MIN);
        System.out.println(String.format("|%-200s|", "    Input end date(date format YYYY-mm-dd)"));
        String date2 = scanner.next();
        LocalDate localDate2 = LocalDate.parse(date2);
        LocalDateTime d2 = LocalDateTime.of(localDate2, LocalTime.MIN);
        Map<String, Double> categoryAmountsByDates = records.stream()
                .filter(r -> r.getFundsType().equals("Income"))
                .filter(date -> date.getTime().isAfter(d1) && date.getTime().isBefore(d2))
                .collect(Collectors.groupingBy(Record::getCategory, Collectors.summingDouble(Record::getAmount)));
        List<Map.Entry<String, Double>> sortedCategories1 = categoryAmountsByDates.entrySet().stream()
                .sorted((v1, v2) -> v2.getValue() > v1.getValue() ? 1 : v2.getValue().equals(v1.getValue()) ? 0 : -1)
                .collect(Collectors.toList());
        System.out.println(String.format("|%-200s|", ("    Income summary: " + sortedCategories1)));
        Map<String, Double> categoryAmmountsByDates1 = records.stream()
                .filter(r -> r.getFundsType().equals("Costs"))
                .filter(date -> date.getTime().isAfter(d1) && date.getTime().isBefore(d2))
                .collect(Collectors.groupingBy(Record::getCategory, Collectors.summingDouble(Record::getAmount)));
        List<Map.Entry<String, Double>> sortedCategories2 = categoryAmmountsByDates1.entrySet().stream()
                .sorted((v1, v2) -> v2.getValue() > v1.getValue() ? 1 : v2.getValue().equals(v1.getValue()) ? 0 : -1)
                .collect(Collectors.toList());
        System.out.println(String.format("|%-200s|", ("    Costs summary: " + sortedCategories2)));

    }
    public void case35() {
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    Record by income"));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        Map<String, Double> categoryAmountsIncome = records.stream()
                .filter(r -> r.getFundsType().equals("Income"))
                .collect(Collectors.groupingBy(Record::getCategory, Collectors.summingDouble(Record::getAmount)));
        List<Map.Entry<String, Double>> sortedCategories = categoryAmountsIncome.entrySet().stream()
                .sorted((v1, v2) -> v2.getValue() > v1.getValue() ? 1 : v2.getValue().equals(v1.getValue()) ? 0 : -1)
                .collect(Collectors.toList());

        System.out.println(String.format("|%-200s|", ("    " + sortedCategories)));
    }
    public void case36() {
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    Record by costs"));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        Map<String, Double> categoryAmountsCosts = records.stream()
                .filter(r -> r.getFundsType().equals("Costs"))
                .collect(Collectors.groupingBy(Record::getCategory, Collectors.summingDouble(Record::getAmount)));
        List<Map.Entry<String, Double>> sortedCategoriesCosts = categoryAmountsCosts.entrySet().stream()
                .sorted((v1, v2) -> v2.getValue() > v1.getValue() ? 1 : v2.getValue().equals(v1.getValue()) ? 0 : -1)
                .collect(Collectors.toList());
        System.out.println(String.format("|%-200s|", ("    " + sortedCategoriesCosts)));
    }
    public void case37() {
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        System.out.println(String.format("|%-200s|", "    All records"));
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
        printAllRecords();
        System.out.println(String.format("x%-200sx", "x".repeat(200)));
    }
}
