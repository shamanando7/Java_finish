import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataParser {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные (в формате Фамилия Имя Отчество ДатаРождения НомерТелефона Пол):");
        String input = scanner.nextLine();

        try {
            String[] userData = parseUserData(input);

            if (userData.length != 6) {
                System.out.println("Ошибка: Вы ввели недостаточно или слишком много данных.");
                return;
            }

            String lastName = userData[0];
            String firstName = userData[1];
            String middleName = userData[2];
            String dateOfBirth = userData[3];
            String phoneNumber = userData[4];
            String gender = userData[5];

            if (!validateDateOfBirth(dateOfBirth) || !validatePhoneNumber(phoneNumber) || !validateGender(gender)) {
                System.out.println("Ошибка: Неверный формат данных.");
                return;
            }

            String fileName = lastName + ".txt";
            String userDataString = lastName + firstName + middleName + dateOfBirth + " " + phoneNumber + gender;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(userDataString);
                writer.newLine();
                System.out.println("Данные успешно записаны в файл: " + fileName);
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static String[] parseUserData(String input) {
        return input.split(" ");
    }

    private static boolean validateDateOfBirth(String dateOfBirth) {
        // Проверка формата даты рождения (dd.mm.yyyy)
        String datePattern = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(dateOfBirth);
        return matcher.matches();
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        // Проверка, что номер телефона - это целое число
        try {
            Long.parseLong(phoneNumber);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validateGender(String gender) {
        // Проверка, что пол - 'f' или 'm'
        return gender.equals("f") || gender.equals("m");
    }
}
