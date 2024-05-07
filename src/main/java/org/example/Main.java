package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String filePath = "src/main/java/org/example/text.txt"; // Укажите путь к файлу с текстом
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }

        // Регулярное выражение для поиска телефонных номеров
        String regex = "\\+?\\d{1,4}?[\\s-]?\\(?(\\d{3})\\)?[\\s-]?(\\d{3})[\\s-]?(\\d{2})[\\s-]?(\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            // Найденный номер телефона
            String phone = matcher.group();
            // Заменяем номер телефона на форматированный
            matcher.appendReplacement(result, formatPhoneNumber(phone));
        }
        matcher.appendTail(result);

        System.out.println(result.toString());
    }

    private static String formatPhoneNumber(String phone) {
        // Удаляем все кроме цифр
        String digits = phone.replaceAll("\\D", "");
        // Предполагаем, что код страны и первые три цифры номера могут быть разделены пробелом или дефисом, а также могут быть заключены в скобки
        return "+1 (" + digits.substring(1, 4) + ") " + digits.substring(4, 7) + "-" + digits.substring(7, 9) + "-" + digits.substring(9);
    }
}
