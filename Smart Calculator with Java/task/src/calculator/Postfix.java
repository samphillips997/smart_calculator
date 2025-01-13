package calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Postfix {

    public static List<String> convertToPostfix(String expression) {
        List<String> input = tokenize(expression);

        Deque<String> stack = new ArrayDeque<>();

        // map of operator priorities
        Map<String, Integer> operatorPriority = new HashMap<>();
        operatorPriority.put("+", 1);
        operatorPriority.put("-", 1);
        operatorPriority.put("*", 2);
        operatorPriority.put("/", 2);
        operatorPriority.put("^", 3);

        List<String> output = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);

            // 1. Add numbers to result as they arrive
            if (isNumeric(s)) {
                output.add(s);
                // 2. If the stack is empty or contains "(" -> push to stack
            } else if (stack.isEmpty() || stack.peek().equals("(")) {
                if (s.equals("-") && (output.isEmpty())) {
                    output.add(input.get(i + 1));
                    output.add("#");
                    i++;
                } else {
                    stack.offerFirst(s);
                }
                // 3. if incoming operator has higher priority -> push to stack
            } else if (operatorPriority.containsKey(s) && (operatorPriority.get(s) > operatorPriority.get(stack.peek()))) {
                stack.offerFirst(s);
            }
            // 4. if priority of incoming operator is <= top of the stack, pop stack and add operators to result until top
            // that is < or '(', add incoming operator to stack
            else if (operatorPriority.containsKey(s) && (operatorPriority.get(s) <= operatorPriority.get(stack.peek()))) {
                if (operatorPriority.containsKey(input.get(i - 1)) && s.equals("-")) {
                    output.add(input.get(i + 1));
                    output.add("#");
                    i++;
                } else {
                    while (!stack.isEmpty() && !(stack.peek().equals("(") || operatorPriority.get(stack.peek()) < operatorPriority.get(s))) {
                        output.add(stack.pop());
                    }

                    stack.offerFirst(s);
                }

                // 5. if incoming element is a left paren -> push to stack
            } else if (s.equals("(")) {
                stack.offerFirst(s);
            }
            // 6. element = right paren -> pop stack & add operators to result until '('
            else if (s.equals(")")) {
                while (!stack.peek().equals("(") && !stack.isEmpty()) {
                    output.add(stack.pop());
                }
                stack.pop();
            }
        }

        while (!stack.isEmpty()) { // end of expression -> pop everything to result
            output.add(stack.pop());
        }

        return output;
    }

    public static int calculatePostfix(String expression) {
        List<String> input = convertToPostfix(expression);

        Deque<Integer> stack = new ArrayDeque<>();

        List<String> operators = Arrays.asList("+", "-", "*", "/", "^");

        int result = 0;

        for (String s : input) {

            // if element is number -> push to stack
            if (isNumeric(s)) {
                stack.offerFirst(Integer.valueOf(s));
                // if element is operator -> pop twice & calculate, push result to stack
            } else if (operators.contains(s) && !s.equals("#")) {
                int operand1 = stack.pop();
                int operand2 = stack.pop();

                switch (s) {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand2 - operand1;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                    case "/":
                        result = operand2 / operand1;
                        break;
                    case "^":
                        result = (int) Math.pow(operand2, operand1);
                        break;
                }
                stack.offerFirst(result);
            } else if (s.equals("#")) {
                int operand = stack.pop();
                result = operand * -1;
                stack.offerFirst(result);
            }
        }

        return stack.pop();
    }

    public static List<String> tokenize(String expression) {
        List<String> output = new ArrayList<>();

        // match number or operator
        String pattern = "\\d+|[()+\\-/*^]";

        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(expression);

        while (matcher.find()) {
            output.add(matcher.group());
        }

        // make sure right amount of parentheses
        int leftParenCount = 0;
        int rightParenCount = 0;

        for (String s : output) {
            if (s.equals("(")) {
                leftParenCount++;
            } else if (s.equals(")")) {
                rightParenCount++;
            }
        }

        if (!(leftParenCount == rightParenCount)) {
            throw new NoSuchElementException("Incorrect parentheses");
        }

        return output;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
