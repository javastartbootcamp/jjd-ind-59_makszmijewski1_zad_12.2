import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Main main = new Main();
        main.createStatsFileIfNotExist();
        List<Employee> employees = main.readData();
        main.writeData(employees);
        main.printAllData(employees);
    }

    private void writeData(List<Employee> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("stats.txt"))) {
            writer.write(calculateAndWriteAverageSalary(employees));
            writer.write(findLowestAndHighestSalaryAndWrite(employees));
            writer.write(countEmployeesAndWrite(employees));
        } catch (IOException e) {
            System.out.println("Nie udało się zapisać pliku");
            e.printStackTrace();
        }
    }

    private void printAllData(List<Employee> employees) {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private List<Employee> readData() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("employees.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                employees.add(createEmployee(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private void createStatsFileIfNotExist() throws IOException {
        File file = new File("employees.csv");

        if (file.exists()) {
            File stats = new File("stats.txt");
            stats.createNewFile();
        }
    }

    private static Employee createEmployee(String line) {
        String[] values = line.split(";");
        return new Employee(values[0], values[1], values[2], values[3], Double.parseDouble(values[4]));
    }

    private static String calculateAndWriteAverageSalary(List<Employee> employees) {
        double[] salaries = new double[employees.size()];
        int counter = 0;
        double sum = 0;
        for (Employee employee : employees) {
            salaries[counter] = employee.getSalary();
            counter++;
            sum += employee.getSalary();
        }
        double average = sum / salaries.length;
        return "Średnia wypłata: " + average + "\n";
    }

    private static String findLowestAndHighestSalaryAndWrite(List<Employee> employees) {
        double[] salaries = new double[employees.size()];
        int counter = 0;
        for (Employee employee : employees) {
            salaries[counter] = employee.getSalary();
            counter++;
        }
        double smallest = salaries[0];
        double largetst = salaries[0];

        for (int i = 1; i < salaries.length; i++) {
            if (salaries[i] > largetst) {
                largetst = salaries[i];
            } else if (salaries[i] < smallest) {
                smallest = salaries[i];
            }
        }
        return "Minimalna wypłata: " + smallest + "\n" +
                "Maksymalna wypłata: " + largetst + "\n";
    }

    private static String countEmployeesAndWrite(List<Employee> employees) {
        int itCounter = 0;
        int supportCounter = 0;
        int managementCounter = 0;
        for (Employee employee : employees) {
            if (employee.getDepartment().equals("IT")) {
                itCounter++;
            } else if (employee.getDepartment().equals("Management")) {
                managementCounter++;
            } else if (employee.getDepartment().equals("Support")) {
                supportCounter++;
            }
        }
        return "Liczba pracowników IT: " + itCounter + "\n" +
                "Liczba pracowników Support: " + supportCounter + "\n" +
                "Liczba pracowników Management: " + managementCounter;
    }
}
