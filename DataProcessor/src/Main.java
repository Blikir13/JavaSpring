import service.DataProcessorService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DataProcessorService serviceA = new DataProcessorService("localhost", 12345);
            serviceA.start();

    }
}
//
//
//import car.Car;
//
//import java.io.ObjectOutputStream;
//import java.io.ObjectInputStream;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        try (Socket socket = new Socket("localhost", 5001);
//             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
//
//            String continueInput;
//            do {
//                System.out.println("Введите марку машины:");
//                String brand = scanner.nextLine();
//
//                System.out.println("Введите модель машины:");
//                String model = scanner.nextLine();
//
//                System.out.println("Введите год выпуска машины:");
//                int year = scanner.nextInt();
//                scanner.nextLine();  // Consume newline
//
//                Car car = new Car(brand, model, year);
//
//                // Отправляем объект на сервер
//                out.writeObject(car);
//                out.flush();
//
//                // Получаем ответ от сервера
//                String response = (String) in.readObject();
//                System.out.println("Ответ сервера: " + response);
//
//                System.out.println("Хотите ввести еще одну машину? (да/нет):");
//                continueInput = scanner.nextLine();
//            } while (continueInput.equalsIgnoreCase("да"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}