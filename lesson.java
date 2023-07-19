package lessons;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class lesson {
    private static final Map<Character, Integer> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put('I', 1);
        ROMAN_NUMERALS.put('V', 5);
        ROMAN_NUMERALS.put('X', 10);
        ROMAN_NUMERALS.put('L', 50);
        ROMAN_NUMERALS.put('C', 100);
        ROMAN_NUMERALS.put('D', 500);
        ROMAN_NUMERALS.put('M', 1000);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String input = scanner.nextLine();
        scanner.close();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат выражения");
        }

        String operand1 = parts[0];
        char operator = parts[1].charAt(0);
        String operand2 = parts[2];

        boolean isRomanNumeral = isRomanNumeral(operand1) && isRomanNumeral(operand2);

        int num1 = isRomanNumeral ? romanToDecimal(operand1) : Integer.parseInt(operand1);
        int num2 = isRomanNumeral ? romanToDecimal(operand2) : Integer.parseInt(operand2);

        int result;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль недопустимо");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неверный оператор: " + operator);
        }

        if (isRomanNumeral) {
            if (result <= 0) {
                throw new IllegalArgumentException("Результат не может быть отрицательным или нулевым для римских чисел");
            }
            return decimalToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRomanNumeral(String input) {
        return input.matches("[IVXLCDM]+");
    }

    private static int romanToDecimal(String romanNumeral) {
        int result = 0;
        int prevValue = 0;

        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            char currentChar = romanNumeral.charAt(i);
            int currentValue = ROMAN_NUMERALS.get(currentChar);

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
                prevValue = currentValue;
            }
        }

        return result;
    }

    private static String decimalToRoman(int number) {
        if (number == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : ROMAN_NUMERALS.entrySet()) {
            char romanDigit = entry.getKey();
            int decimalValue = entry.getValue();

            while (number >= decimalValue) {
                sb.append(romanDigit);
                number -= decimalValue;
            }
        }

        return sb.toString();
    }
}