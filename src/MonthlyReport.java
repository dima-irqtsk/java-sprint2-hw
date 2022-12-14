import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MonthlyReport {

    HashMap<Integer, FullMonth> listOfMonths;

    public MonthlyReport() {
        listOfMonths = new HashMap<>();
    }


    List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл " + e.getMessage() + ". Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }

    void readAllMonthReports(int inputYear) {

        String[] allowedMonths = new String[]{"01", "02", "03", "09", "12"};
        for (String monthNumber : allowedMonths) { // Счетчик для номера месяца
            List<String> rowsOfMonthReport = readFileContents("resources/m." + inputYear + monthNumber + ".csv");
            FullMonth fullMonth = new FullMonth();
            if (rowsOfMonthReport.size() > 0) {
                for (int j = 1; j < rowsOfMonthReport.size(); j++) {
                    String[] lineContent = rowsOfMonthReport.get(j).split(",");
                    String itemName = lineContent[0];
                    Boolean isExpense = Boolean.parseBoolean(lineContent[1]);
                    int quantity = Integer.parseInt(lineContent[2]);
                    int sumOfOne = Integer.parseInt(lineContent[3]);
                    MonthRow monthRow = new MonthRow(itemName, isExpense, quantity, sumOfOne);
                    fullMonth.monthData.add(monthRow);
                }
            }
            if (rowsOfMonthReport.size() > 0) {
                listOfMonths.put(Integer.parseInt(monthNumber), fullMonth);
            }
        }
    }
}

class FullMonth {

    // Класс для создания объекта, который хранит ArrayList с объектами-строками из месячного отчета
    ArrayList<MonthRow> monthData;

    public FullMonth() {
        monthData = new ArrayList<>();
    }

}

class MonthRow {

    // Класс для создания объекта, который хранит одну строку из месячного отчета

    String itemName;
    Boolean isExpense;
    Integer quantity;
    Integer sumOfOne;

    public MonthRow(String itemName, Boolean isExpense, Integer quantity, Integer sumOfOne) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }
}

