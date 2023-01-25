import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {

        Employee[] employees = new Employee[4];

        File file = new File("employees.csv");

        if (file.exists()) {
            File stats = new File("stats.txt");
            stats.createNewFile();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader("employees.csv"));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                employees[i] = createEmployee(line);
                i++;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("stats.txt"));
            writer.write(calculateAndWriteAverageSalary(employees));
            writer.write(findLowestAndHighestSalaryAndWrite(employees));
            writer.write(countEmployeesAndWrite(employees));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static Employee createEmployee(String line) {
        String[] values = line.split(";");
        return new Employee(values[0], values[1], values[2], values[3], Double.parseDouble(values[4]));
    }

    private static String calculateAndWriteAverageSalary(Employee[] employees) {
        double[] salaries = new double[employees.length];
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

    private static String findLowestAndHighestSalaryAndWrite(Employee[] employees) {
        double[] salaries = new double[employees.length];
        int counter = 0;
        for (Employee employee : employees) {
            salaries[counter] = employee.getSalary();
            counter++;
        }
        Arrays.sort(salaries);
        return "Minimalna wypłata: " + salaries[0] + "\n" +
                "Maksymalna wypłata: " + salaries[salaries.length - 1] + "\n";
    }

    private static String countEmployeesAndWrite(Employee[] employees) {
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
