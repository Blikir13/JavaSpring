import console.Console;
import service.DataReceiverService;

import java.io.IOException;
import java.rmi.RemoteException;

//TODO: Main переименовать в зависимости от специфики бизнес-задачи
public class Main {
    public static void main(String[] args) throws IOException { //FIXME remove throws?
        //TODO: сканирование пропертей: адрес куда подключаться порты, место куда складывать данные: путь, наименование.
        //TODO: создавать объекты, пробрасываем в них проперти: консоль, сервис, репо, в чьи конструкторы передавать необходимые компоненты-свойства (репо, сервис итд)

        Console console = new Console();
        console.scanCommand();
    }
}
