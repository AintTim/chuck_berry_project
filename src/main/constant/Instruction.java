package main.constant;

public class Instruction {

    private Instruction() {}
    public static final String ENTER_SOURCE_PATH = "(Источник) Введите адрес файла: ";
    public static final String ENTER_DESTINATION_PATH = "(Результат) Введите адрес файла: ";
    public static final String ENTER_REFERENCE_PATH = "(Пример) Введите адрес файла: ";
    public static final String ENTER_KEY = "Введите ключ шифрования: ";
    public static final String AVAILABLE_ALPHABETS = "\nСписок доступных алфавитов для шифрования: ";
    public static final String SELECT_ALPHABET = "\nДля выбора нескольких алфавитов, пожалуйста, передайте их названия через пробел.\nВыберите алфавит: ";
    public static final String INVALID_ALPHABET = "Введенное значение отсутствует в списке доступных\nПожалуйста, введите корректное значение.";
    public static final String INVALID_KEY = "Введенный ключ шифрования должен быть больше нуля.\nПожалуйста, введите новый ключ: ";
    public static final String AVAILABLE_OPTIONS = "\nСписок доступных опций: ";
    public static final String SELECT_OPTION = "Выберите опцию: ";
    public static final String INVALID_OPTION = "Выбранная опция отсутствует в списке доступных.\nПожалуйста, выберите существующую опцию: ";
    public static final String ENCRYPTION_KEY = "Файл был зашифрован с помощью ключа - ";
}
