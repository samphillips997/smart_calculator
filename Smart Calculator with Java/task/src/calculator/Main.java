package calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* TODO
    * multi-digit numbers
*/
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var varMap = new HashMap<String, Integer>();

        while (true) {
            String input = scanner.nextLine();

            // menu options
            // can I put this in its own method?
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
                if (expression.matches("[a-zA-Z]+=-*\\d+")) {
                    varMap.put(list.get(0), Integer.valueOf(list.get(1)));

                // variable = variable
                } else if (expression.matches("[a-zA-Z]+=[a-zA-Z]+")) {
                    addNameVarToMap(varMap, list.get(0), list.get(1));

                } else if (list.get(0).matches(".*\\d.*")) { // check left side for invalid varName
                    System.out.println("Invalid identifier");
                } else {
                    System.out.println("Invalid assignment");
                }

                continue;
            }

            if (input.matches("-?\\d+")) {
                System.out.println(input);
            } // filter for arithmetic expression or single number
            else if (expression.matches("(.*[-+*/^].*)+")) {

                List<String> vars = returnVarsFromExpression(expression);
                String newExpression = replaceVarsWithNums(expression, varMap, vars);

                // add some error handling to postfix to say 'Invalid expression' if it gets an error

                try {
                    System.out.println(Postfix.calculatePostfix(newExpression));
                } catch (NoSuchElementException e) {
                    System.out.println("Invalid expression");
                } catch (ArithmeticException e) {
                    System.out.println("Invalid expression");
                } catch (NullPointerException e) {
                    System.out.println("Invalid expression");
                }

            } else if (!(input.isEmpty())) {
                if (input.matches(".*\\d.*")) {
                    System.out.println("Invalid identifier");
                } else {
                    fetchValueFromMap(varMap, expression);
                }
            }
        }

    }

    // remove whitespace, replace double negative and multiple positive signs
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

    public static void fetchValueFromMap(Map<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            System.out.println(map.get(key));
        } else {
            System.out.println("Unknown variable");
        }
    }

    public static List<String> returnVarsFromExpression(String expression) {
        List<String> vars = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            if (Character.isLetter(expression.charAt(i))) {
                vars.add(String.valueOf(expression.charAt(i)));
            }
        }

        return vars;
    }

    public static String replaceVarsWithNums(String expression, Map<String, Integer> map, List<String> vars) {
        for (String var : vars) {
            if (expression.contains(var)) {
                expression = expression.replace(var, String.valueOf(map.get(var)));
            }
        }

        return expression;
    }

}
