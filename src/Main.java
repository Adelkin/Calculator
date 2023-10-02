import java.util.Scanner;

// Enum для римских чисел и их соответствующих арабских значений
enum RomanNumeral {
    I(1), II(2), III(3), IV(4), V(5),
    VI(6), VII(7), VIII(8), IX(9), X(10);

    private final int value;
    RomanNumeral(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите математическое выражение (принимаются только числа от 1 до 10 включительно):");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        // Разбиваем введенную строку на части. (В ТЗ все все примеры через пробелы, поэтому разделитель - пробел " ")
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            throw new Exception("Неверный формат математической операции. Дожно быть два операнда и один оператор (+, -, /, *)");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        boolean isRoman1 = isRoman(operand1);
        boolean isRoman2 = isRoman(operand2);

        if ((isRoman1 && !isRoman2) || (!isRoman1 && isRoman2)) {
            throw new Exception("Используются одновременно разные системы счисления или Вы ввели число не от 1 до 10");
        }

        int num1, num2;
        if (isRoman1) {
            num1 = getRomanValue(operand1);
            num2 = getRomanValue(operand2);
        } else {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
        }

        if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10)) {
            throw new Exception("Операнды должны быть от 1 до 10 включительно");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new Exception("Неверный оператор: " + operator);
        }

        if (isRoman1) {
            if (result < 1) {
                throw new Exception("В римской системе нет отрицательных чисел");
            }
            return toRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    // Проверка, является ли строка римским числом
    public static boolean isRoman(String str) {
        for (RomanNumeral numeral : RomanNumeral.values()) {
            if (numeral.name().equals(str)) {
                return true;
            }
        }
        return false;
    }

    // Получение арабского значения римского числа
    public static int getRomanValue(String str) {
        for (RomanNumeral numeral : RomanNumeral.values()) {
            if (numeral.name().equals(str)) {
                return numeral.getValue();
            }
        }
        return 0;
    }

    // Преобразование арабского числа в римское
    public static String toRoman(int num) {
        String[] romanSymbols = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {100, 90, 50, 40, 10, 9, 5, 4, 1};

        String romanNumeral = "";

        int i = 0;
        while (num > 0) {
            if (num >= arabicValues[i]) {
                romanNumeral += romanSymbols[i];
                num = num - arabicValues[i];
            } else {
                // Переходим к следующей римской цифре
                i++;
            }
        }
        return romanNumeral;
    }
}