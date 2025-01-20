package calculator;

import java.math.BigInteger;
import java.util.*;


public class Postfix {
    Tokenizer tokenizer = new Tokenizer();

    public List<String> convertToPostfix(String expression) {
        List<String> input = tokenizer.tokenize(expression);

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

    public BigInteger calculatePostfix(String expression) {
        List<String> input = convertToPostfix(expression);

        Deque<BigInteger> stack = new ArrayDeque<>();

        List<String> operators = Arrays.asList("+", "-", "*", "/", "^");

        BigInteger result = BigInteger.ZERO;

        for (String s : input) {

            // if element is number -> push to stack
            if (isNumeric(s)) {
                stack.offerFirst(new BigInteger(s));
                // if element is operator -> pop twice & calculate, push result to stack
            } else if (operators.contains(s) && !s.equals("#")) {
                BigInteger operand1 = stack.pop();
                BigInteger operand2 = stack.pop();

                switch (s) {
                    case "+":
                        result = operand1.add(operand2);
                        break;
                    case "-":
                        result = operand2.subtract(operand1);
                        break;
                    case "*":
                        result = operand1.multiply(operand2);
                        break;
                    case "/":
                        result = operand2.divide(operand1);
                        break;
                    case "^":
                        result = operand2.pow(operand1.intValue());
                        break;
                }
                stack.offerFirst(result);
            } else if (s.equals("#")) {
                BigInteger operand = stack.pop();
                result = operand.negate();
                stack.offerFirst(result);
            }
        }

        return stack.pop();
    }



    public static boolean isNumeric(String str) {
        try {
            new BigInteger(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
