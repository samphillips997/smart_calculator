package calculator;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PostfixTest {
    @Test
    void testConvertToPostfix() {
        Postfix postfix = new Postfix();

        String postfixExpression = "2 2 10 10 * * + 2 /";
        var expressionArray = Arrays.asList(postfixExpression.split(" "));

        assertEquals(expressionArray, postfix.convertToPostfix("(2+2*(10*10))/2"));
    }

    @Test
    void testCalculatePostfixComplex() {
        Postfix postfix = new Postfix();
        assertEquals(new BigInteger("101"), postfix.calculatePostfix("(2+2*(10*10))/2"));
    }
}