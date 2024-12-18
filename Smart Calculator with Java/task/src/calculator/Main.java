package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            // menu options
            if (input.matches("/\\w*")) {

                if (input.equals("/exit")) {
                    System.out.println("Bye!");
                    break;
                } else if (input.equals("/help")) {
                    System.out.println("The program calculates the sum of numbers");
                    continue;
                } else {
                    System.out.println("Unknown command");
                    continue;
                }

            }

            String expression = convertInputToExpression(input);

            // filter for arithmetic expression or single number
            if (expression.matches("([-+]?\\d+([+-]+\\d+)+)|([-+]?\\d+)")) {
                System.out.println(calculate(expression));

            } else if (!(input.isEmpty())) { // do nothing if no input, print if one input
                System.out.println("Invalid expression");
            }
        }

    }

    // convert input into expression
    public static String convertInputToExpression(String input) {

        return input.replaceAll("\\s", "") // remove spaces
                .replaceAll("--", "+") // replace double negative
                .replaceAll("\\+{2,}", "+"); // replace multiple plus signs

    }

    // finds all tokens positive or negative and then adds them together
    public static int calculate(String expression) {

        String regex = "-?\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);

        List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }

        int result = 0;

        for (Integer number : numbers) {
            result += number;
        }

        return result;
    }

}
