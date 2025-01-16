package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    public List<String> tokenize(String expression) {
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
}
