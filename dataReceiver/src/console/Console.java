package console;

import dto.StationDataDto;

import java.util.Scanner;

public class Console {
    private Scanner scanner;

    // Конструктор класса, инициализирует сканер
    public Console() {
        this.scanner = new Scanner(System.in);
    }

    // Метод для получения строки от пользователя
    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Метод для получения целого числа от пользователя
    public int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите целое число.");
            scanner.next(); // Пропускаем некорректный ввод
        }
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    // Метод для получения числа с плавающей запятой от пользователя
    public double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Пожалуйста, введите число.");
            scanner.next(); // Пропускаем некорректный ввод
        }
        double result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

//    public StationDataDto inputStationData() {
//        int stationNumber = getIntInput("Input station number: ");
//        String city = getStringInput("input city: ");
//        double temperature = getDoubleInput("Input temperature: ");
//        double pressure = getDoubleInput("Input pressure: ");
//        double windSpeed = getDoubleInput("Input wind speed: ");
//        String windDirection = getStringInput("input wind direction: ");
//    }

    public void printException(String exceptionMessage) {
        System.out.println("Ошибка: " + exceptionMessage);
    }


    // Закрытие сканера
    public void close() {
        scanner.close();
    }

    public static void main(String[] args) {
        Console console = new Console();

        // Пример использования
        String name = console.getStringInput("Введите ваше имя: ");
        int age = console.getIntInput("Введите ваш возраст: ");
        double salary = console.getDoubleInput("Введите вашу зарплату: ");

        System.out.println("Имя: " + name);
        System.out.println("Возраст: " + age);
        System.out.println("Зарплата: " + salary);

        console.close();
    }
}