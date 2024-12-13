package calculator;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("/exit")) {
                System.out.println("Bye!");
                break;
            }

            if (input.equals("/help")) {
                System.out.println("The program calculates the sum of numbers");
                continue;
            }

            // if 2 numbers are input -> add
            if (input.contains(" ")) {
                int[] numbers = convertStringToIntArray(input);

                System.out.println(sumIntArray(numbers));
            } else if (!(input.isEmpty())) { // do nothing if no input, print if one input
                System.out.println(input);
            }
        }

    }

    public static int[] convertStringToIntArray(String input) {
        String[] elements = input.split(" ");
        int[] numbers = new int[elements.length];

        for (int i = 0; i < elements.length; i++) {
            numbers[i] = Integer.parseInt(elements[i]);
        }

        return numbers;
    }

    public static int sumIntArray(int[] numbers) {
        int sum = 0;

        for (int number : numbers) {
            sum += number;
        }

        return sum;
    }
}
