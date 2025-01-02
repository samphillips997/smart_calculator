package calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var varMap = new HashMap<String, Integer>();

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

            // variable assignment
            if (expression.contains("=")) {
                var list = Arrays.asList(expression.split("="));

                // variable = number
                if (input.matches("[a-zA-Z]+=\\d+")) {
                    varMap.put(list.get(0), Integer.valueOf(list.get(1)));

                // variable = variable
                } else if (input.matches("[a-zA-Z]+=[a-zA-Z]+")) {
                    addNameVarToMap(varMap, list.get(0), list.get(1));

                } else if (list.get(0).matches(".*\\d.*")) {
                    System.out.println("Invalid identifier");
                } else {
                    System.out.println("Invalid assignment");
                }

                System.out.println(varMap);
                continue;
            }

            // filter for arithmetic expression or single number
            if (expression.matches("([-+]?\\d+([+-]+\\d+)+)|([-+]?\\d+)")) {

                System.out.println(calculate(expression));

            } else if (!(input.isEmpty())) { // do nothing if no input, print if one input
                fetchValueFromMap(varMap, expression);
            }
        }

    }

    // convert input into expression
    public static String convertInputToExpression(String input) {

        return input.replaceAll("\\s", "") // remove spaces
                .replaceAll("--", "+") // replace double negative
                .replaceAll("\\+{2,}", "+"); // replace multiple plus signs

    }

    public static void addNameVarToMap(Map<String, Integer> map, String key, String varName) {
        if (map.containsKey(varName)) {
            map.put(key, map.get(varName));
        } else {
            System.out.println("Unknown variable");
        }
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

    public static void fetchValueFromMap(Map<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            System.out.println(map.get(key));
        } else {
            System.out.println("Unknown variable");
        }
    }

}
