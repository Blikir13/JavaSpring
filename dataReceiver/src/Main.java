import console.Console;
import service.DataReceiverService;

import java.io.IOException;

//TODO: Main переименовать в зависимости от специфики бизнес-задачи
public class Main {
    public static void main(String[] args) throws IOException {
        //TODO: сканирование пропертей: адрес куда подключаться порты, место куда складывать данные: путь, наименование.
        //TODO: создавать объекты, пробрасываем в них проперти: консоль, сервис, репо, в чьи конструкторы передавать необходимые компоненты-свойства (репо, сервис итд)


        DataReceiverService serviceB = new DataReceiverService();
        serviceB.start();
    }
}
//
//import car.Car;
//
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Main {
//    public static void main(String[] args) {
//        try (ServerSocket serverSocket = new ServerSocket(5001)) {
//            System.out.println("Сервер запущен, ожидаем подключения...");
//
//            while (true) {
//                try (Socket clientSocket = serverSocket.accept();
//                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
//                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
//
//                    System.out.println("Клиент подключен.");
//
//                    // Принимаем объект от клиента
//                    Car car = (Car) in.readObject();
//                    System.out.println("Принят объект: " + car);
//
//                    // Отправляем ответ клиенту
//                    out.writeObject("Машина " + car + " принята.");
//                    out.flush();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}