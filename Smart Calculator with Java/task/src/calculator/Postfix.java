package calculator;

import java.util.*;


public class Postfix {

    public static String convertToPostfix(String expression) {
        Deque<Character> stack = new ArrayDeque<>();


        // map of operator priorities
        Map<Character, Integer> operatorPriority = new HashMap<>();
        operatorPriority.put('+', 1);
        operatorPriority.put('-', 1);
        operatorPriority.put('*', 2);
        operatorPriority.put('/', 2);
        operatorPriority.put('^', 3);

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char currentInputChar = expression.charAt(i);

            if (Character.isDigit(currentInputChar)) { // 1. add numbers to result as they arrive
                output.append(currentInputChar);
            } else if (stack.isEmpty() || stack.peek() == '(') { // 2. if stack is empty or top = ( -> push to stack
                stack.offerFirst(currentInputChar);

                // 3. if incoming operator has higher priority -> push to stack
            } else if (operatorPriority.containsKey(currentInputChar) && (operatorPriority.get(currentInputChar) >= operatorPriority.get(stack.peek()))) {
                stack.offerFirst(currentInputChar);
            }
            // 4. if priority of incoming operator is <= top of the stack, pop stack and add operators to result until top
            // that is < or '(', add incoming operator to stack
            else if (operatorPriority.containsKey(currentInputChar) && (operatorPriority.get(currentInputChar) <= operatorPriority.get(stack.peek()))) {
                while (!stack.isEmpty() && !(stack.peek() == '(' || operatorPriority.get(stack.peek()) < operatorPriority.get(currentInputChar))) {
                    output.append(stack.pop());
                }

                stack.offerFirst(currentInputChar);
            }

            else if (currentInputChar == '(') { // 5. if incoming element is a left paren -> push to stack
                stack.offerFirst(currentInputChar);
            }
            else if (currentInputChar == ')') { // 6. element = right paren -> pop stack & add operators to result until '('
                while (stack.peek() != '(' && !stack.isEmpty()) {
                    output.append(stack.pop());
                }
                stack.pop();
            }

        }

        while (!stack.isEmpty()) { // end of expression -> pop everything to result
            output.append(stack.pop());
        }

        return output.toString();
    }

    public static int calculatePostfix(String expression) {
        Deque<Integer> stack = new ArrayDeque<>();

        List<Character> operators = Arrays.asList('+', '-', '*', '/', '^');

        int result = 0;

        for (int i = 0; i < expression.length(); i++) {
            char currentInputChar = expression.charAt(i);

            // if element is number -> push to stack
            if (Character.isDigit(currentInputChar)) {
                stack.offerFirst(Character.getNumericValue(currentInputChar));
                // if element is operator -> pop twice & calculate, push result to stack
            } else if (operators.contains(currentInputChar)) {
                int operand1 = stack.pop();
                int operand2 = stack.pop();

                switch (currentInputChar) {
                    case '+':
                        result = operand1 + operand2;
                        break;
                    case '-':
                        result = operand2 - operand1;
                        break;
                    case '*':
                        result = operand1 * operand2;
                        break;
                    case '/':
                        result = operand2 / operand1;
                        break;
                    case '^':
                        result = (int) Math.pow(operand2, operand1);
                        break;
                }
                stack.offerFirst(result);
            }
        }

        return stack.pop();
    }
}
