package console;

public enum ConsolePrompt {
    STATION_NUMBER("Введите номер станции: "),
    CITY("Введите город: "),
    TEMPERATURE("Введите температуру: "),
    PRESSURE("Введите давление: "),
    WIND_SPEED("Введите скорость ветра: "),
    WIND_DIRECTION("Введите направление ветра: "),

    ENTER_ID_DELETE("Введите id для удаления: "),
    ENTER_ID_UPDATE("Введите id для обновления: "),

    ENTER_NUMBER("Пожалуйста, введите число!"),
    UNKNOWN_COMMAND("Вы ввели неизвестную команду. Введите help, чтобы узнать о командах"),

    STATION_INFO("Доступные записи по станциям:"),;


    private final String prompt;

    ConsolePrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }
}
