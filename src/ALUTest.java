import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by SinjinSong on 2017/3/30.
 */
public class ALUTest {
    private ALU alu = new ALU();


    @Test
    public void testNegation() {
        assertEquals(alu.negation("00001001"), "11110110");
    }

    @Test
    public void testLeftShift() {
        assertEquals(alu.leftShift("00001001", 2), "00100100");
    }

    @Test
    public void testLogRightShift() {
        assertEquals(alu.logRightShift("11110110", 2), "00111101");
    }

    @Test
    public void testAriRightShift() {
        assertEquals(alu.ariRightShift("11110110", 2), "11111101");
    }

    @Test
    public void notTest() {
        assertEquals('0', alu.not('1'));
        assertEquals('1', alu.not('0'));
    }

    @Test
    public void andTest() {
        assertEquals('0', alu.and('1', '0'));
        assertEquals('1', alu.and('1', '1'));
        assertEquals('0', alu.and('0', '0'));
        assertEquals('0', alu.and('0', '1'));
    }

    @Test
    public void orTest() {
        assertEquals('1', alu.or('1', '0'));
        assertEquals('1', alu.or('1', '1'));
        assertEquals('0', alu.or('0', '0'));
        assertEquals('1', alu.or('0', '1'));
    }

    @Test
    public void xorTest() {
        assertEquals('1', alu.xor('1', '0'));
        assertEquals('0', alu.xor('1', '1'));
        assertEquals('0', alu.xor('0', '0'));
        assertEquals('1', alu.xor('0', '1'));
    }

    @Test
    public void integerRepresentationTest() {
        assertEquals("00001001", alu.integerRepresentation("9", 8));
        assertEquals("00000000000000000000000000001001", alu.integerRepresentation("9", 32));
        assertEquals("01111111111111111111111111111111", alu.integerRepresentation("2147483647", 32));
        assertEquals("10000000000000000000000000000000", alu.integerRepresentation("-2147483648", 32));
        assertEquals("0000000000000000000000000000000000000000000000000000000000001001", alu.integerRepresentation("9", 64));
        assertEquals("111111111111111111111111111111111111111111111111111111111110000", alu.integerRepresentation("-16", 63));
        assertEquals("111111111111111111111111111111111111111111111111111111111111111", alu.integerRepresentation("-1", 63));
        assertEquals("00000000000000000000000000001001", alu.integerRepresentation("9", 32));
        assertEquals("1000", alu.integerRepresentation("-8", 4));
        assertEquals("1001", alu.integerRepresentation("-7", 4));
        assertEquals("", alu.integerRepresentation("-7", 0));
        assertEquals("00001001", alu.integerRepresentation("9", 8));
    }

    @Test
    public void decimalIntegerRepresentationTest() {
        assertEquals("011", alu.decimalIntegerRepresentation("0.375", 8));
    }

    @Test
    public void floatRepresentationTest() {
        assertEquals("00000010000000000", alu.floatRepresentation("0.000030517578125", 5, 11));
        assertEquals("0011101000000", alu.floatRepresentation("1.25", 4, 8));
        assertEquals("0010100000000", alu.floatRepresentation("0.25", 4, 8));
        assertEquals("0111100000000", alu.floatRepresentation("+Inf", 4, 8));
        assertEquals("1111111100000000", alu.floatRepresentation("-Inf", 7, 8));
        assertEquals("0000000000000", alu.floatRepresentation("0", 4, 8));
        assertEquals("1000000000000", alu.floatRepresentation("-0.0", 4, 8));
        assertEquals("1000000000000", alu.floatRepresentation("-0.00000000000", 4, 8));
        assertEquals("0000000000000", alu.floatRepresentation("0.000000", 4, 8));
        assertEquals("1000000000000", alu.floatRepresentation("-0.000", 4, 8));
        assertEquals("01000001001101100000", alu.floatRepresentation("11.375", 8, 11));
        assertEquals("1010100000000", alu.floatRepresentation("-0.25", 4, 8));
        assertEquals("0111011111111", alu.floatRepresentation("255.5", 4, 8));
        assertEquals("0011111111111", alu.floatRepresentation("1.99609375", 4, 8));
        assertEquals("0111100000000", alu.floatRepresentation("256.0", 4, 8));
        assertEquals("0000110000000", alu.floatRepresentation("0.0234375", 4, 8));
    }

    @Test
    public void ieee754Test() {
        assertEquals("00000000000000000000000000000000", alu.ieee754("0.0", 32));
        assertEquals("00111111000000000000000000000000", alu.ieee754("0.5", 32));
        assertEquals("01000001001101100000000000000000", alu.ieee754("11.375", 32));
    }

    @Test
    public void integerTrueValueTest() {
        assertEquals("9", alu.integerTrueValue("00001001"));
        assertEquals("-7", alu.integerTrueValue("1001"));
        assertEquals("-7", alu.integerTrueValue("11111001"));
        assertEquals("2147483647", alu.integerTrueValue("01111111111111111111111111111111"));
        assertEquals("-2147483648", alu.integerTrueValue("10000000000000000000000000000000"));
        assertEquals("-16", alu.integerTrueValue("111111111111111111111111111111111111111111111111111111111110000"));
        assertEquals("9", alu.integerTrueValue("00001001"));
    }

    @Test
    public void floatTrueValueTest() {
        assertEquals("11.375", alu.floatTrueValue("01000001001101100000", 8, 11));
        assertEquals("0.0234375", alu.floatTrueValue("0000110000000", 4, 8));
        assertEquals("10.0", alu.floatTrueValue("01000001001000000000", 8, 11));
        assertEquals("1.0", alu.floatTrueValue("00111111100000000000", 8, 11));
        assertEquals("-1.0", alu.floatTrueValue("10111111100000000000", 8, 11));
        assertEquals("11.375", alu.floatTrueValue("01000001001101100000", 8, 11));
        assertEquals("-11.375", alu.floatTrueValue("11000001001101100000", 8, 11));
        assertEquals("+Inf", alu.floatTrueValue("01111111100000000000", 8, 11));
        assertEquals("-Inf", alu.floatTrueValue("11111100000000000", 5, 11));
        assertEquals("NaN", alu.floatTrueValue("01111111101101100000", 8, 11));
        assertEquals("NaN", alu.floatTrueValue("111111111101101100000", 9, 11));
        assertEquals("0", alu.floatTrueValue("00000000000000000", 5, 11));
        assertEquals("0", alu.floatTrueValue("10000000000000000", 5, 11));
        assertEquals("0.000030517578125", alu.floatTrueValue("00000010000000000", 5, 11));
        assertEquals("0.0078125", alu.floatTrueValue("0000010000000", 4, 8));
    }

    @Test
    public void negationTest() {
        assertEquals("111111", alu.negation("000000"));
        assertEquals("11110110", alu.negation("00001001"));
    }

    @Test
    public void leftShiftTest() {
        assertEquals("00100100", alu.leftShift("00001001", 2));
    }

    @Test
    public void logRightShiftTest() {
        assertEquals("00111101", alu.logRightShift("11110110", 2));
    }

    @Test
    public void ariRightShiftTest() {
        assertEquals("11111101", alu.ariRightShift("11110110", 2));
    }

    @Test
    public void fullAdderTest() {
        assertEquals("01", alu.fullAdder('1', '0', '0'));
        assertEquals("01", alu.fullAdder('0', '1', '0'));
        assertEquals("01", alu.fullAdder('0', '0', '1'));
        assertEquals("10", alu.fullAdder('1', '1', '0'));
        assertEquals("10", alu.fullAdder('1', '0', '1'));
        assertEquals("10", alu.fullAdder('0', '1', '1'));
        assertEquals("11", alu.fullAdder('1', '1', '1'));
    }

    @Test
    public void claAdderTest() {
        assertEquals("01011", alu.claAdder("1001", "0001", '1'));
        assertEquals("10011", alu.claAdder("1001", "1001", '1'));
        assertEquals("11111", alu.claAdder("1111", "1111", '1'));
        assertEquals("00001", alu.claAdder("0000", "0000", '1'));
        assertEquals("11000", alu.claAdder("1001", "1111", '0'));
    }

    @Test
    public void oneAdderTest() {
        assertEquals("01010", alu.oneAdder("1001"));
        assertEquals("01110", alu.oneAdder("1101"));
        assertEquals("00000", alu.oneAdder("1111"));
        assertEquals("0111100", alu.oneAdder("111011"));
        assertEquals("000000000", alu.oneAdder("11111111"));
        assertEquals("000001010", alu.oneAdder("00001001"));
        assertEquals("110000000", alu.oneAdder("01111111"));
    }

    @Test
    public void testSignExtension() {
        assertEquals("11111100", alu.signExtension("1100", 8));
        assertEquals("00000011", alu.signExtension("0011", 8));
    }

    @Test
    public void adderTest() {
        assertEquals("011111010", alu.adder("1001", "0001", '0', 8));
        assertEquals("011110111", alu.adder("1001", "1101", '1', 8));
        assertEquals("00101", alu.adder("0111", "1101", '1', 4));
        assertEquals("011111010", alu.adder("01001", "10001", '0', 8));
        assertEquals("00000000000000011", alu.adder("01", "01", '1', 16));
        assertEquals("10111111111111110", alu.adder("1000000000000101", "1001", '0', 16));
        assertEquals("000000111", alu.adder("0100", "0011", '0', 8));
    }


    @Test
    public void integerAdditionTest() {
        assertEquals("011111010", alu.integerAddition("1001", "0001", 8));
        assertEquals("011110110", alu.integerAddition("1001", "1101", 8));
        assertEquals("011111010", alu.integerAddition("01001", "10001", 8));
        assertEquals("00000000000000010", alu.integerAddition("01", "01", 16));
        assertEquals("10111111111111110", alu.integerAddition("1000000000000101", "1001", 16));
        assertEquals("000000111", alu.integerAddition("0100", "0011", 8));
    }

    @Test
    public void integerSubtractionTest() {
        assertEquals("011111000", alu.integerSubtraction("1001", "0001", 8));
        assertEquals("000000000", alu.integerSubtraction("1001", "1001", 8));
        assertEquals("000000000", alu.integerSubtraction("0001", "0001", 8));
        assertEquals("0000000000100", alu.integerSubtraction("000101", "0001", 12));
        assertEquals("000000010", alu.integerSubtraction("0001", "1111", 8));
        assertEquals("000000001", alu.integerSubtraction("0100", "0011", 8));
    }

    @Test
    public void integerMultiplicationTest() {
        assertEquals("000001100", alu.integerMultiplication("0100", "0011", 8));
        assertEquals("000000110", alu.integerMultiplication("0010", "0011", 8));
        assertEquals("011111000", alu.integerMultiplication("1110", "0100", 8));
        assertEquals("000000110", alu.integerMultiplication("1110", "1101", 8));
        assertEquals("11100", alu.integerMultiplication("0100", "0011", 4));
        assertEquals("10101", alu.integerMultiplication("0111", "0011", 4));
        assertEquals("000001100", alu.integerMultiplication("0100", "0011", 8));
    }

    @Test
    public void integerDivisionTest() {
        assertEquals("00000000100000001", alu.integerDivision("0100", "0011", 8));
        assertEquals("000010001", alu.integerDivision("0100", "0011", 4));
        
        assertEquals("NaN", alu.integerDivision("1111", "0000", 4));
        assertEquals("110000001", alu.integerDivision("10000001", "11111111", 8));
        
        assertEquals("000000011", alu.integerDivision("0000", "0011", 4));
        assertEquals("01111111011111111", alu.integerDivision("1001", "0011", 8));
        assertEquals("011101111", alu.integerDivision("1001", "0011", 4));
        assertEquals("01111111011111111", alu.integerDivision("1001", "0011", 8));
        assertEquals("000010010", alu.integerDivision("0110", "0100", 4));
        assertEquals("011111110", alu.integerDivision("1010", "0100", 4));
        assertEquals("000011110", alu.integerDivision("1010", "1100", 4));
        assertEquals("011110010", alu.integerDivision("0110", "1100", 4));
        assertEquals("011100000", alu.integerDivision("0110", "1101", 4));
        assertEquals("011111101", alu.integerDivision("1010", "0011", 4));
        assertEquals("001011111", alu.integerDivision("1010", "1111", 4));
        assertEquals("001111111", alu.integerDivision("1000", "1111", 4));
    }

    @Test
    public void signedAdditionTest() {
        assertEquals("0110000000", alu.signedAddition("11000000", "11000000", 8));
        assertEquals("0100000111", alu.signedAddition("1100", "1011", 8));
        assertEquals("0000000110", alu.signedAddition("11001", "001111", 8));
        assertEquals("010111", alu.signedAddition("1100", "1011", 4));
        assertEquals("100111", alu.signedAddition("01101", "01010", 4));
        assertEquals("0100001001", alu.signedAddition("11100", "0011", 8));
        assertEquals("011001", alu.signedAddition("11100", "00011", 4));
        assertEquals("0100000111", alu.signedAddition("1100", "1011", 8));
    }

    @Test
    public void floatAdditionTest() {
        assertEquals("000111111101110000", alu.floatAddition("00111111010100000", "00111111001000000", 8, 8, 8));
        assertEquals("010000001100000", alu.floatAddition("10000010100000", "00000001000000", 5, 8, 6));
        assertEquals("111111100000000", alu.floatAddition("11111100000000", "11111100000000", 5, 8, 6));
        assertEquals("001111011101011", alu.floatAddition("01100101110000", "01111011100000", 5, 8, 6));
        assertEquals("101111100000000", alu.floatAddition("01110101110000", "01111011100000", 5, 8, 6));
        assertEquals("000000000000000", alu.floatAddition("10001111110000", "00001111110000", 5, 8, 6));
        assertEquals("010010011110000", alu.floatAddition("10001111110000", "10001111110000", 5, 8, 6));
        assertEquals("000111111101110000", alu.floatAddition("00111111010100000", "00111111001000000", 8, 8, 4));
        assertEquals("000010000010100", alu.floatAddition("00001111110000", "00000011100000", 5, 8, 6));
        assertEquals("010001110111000", alu.floatAddition("10001111110000", "00000011100000", 5, 8, 6));
        assertEquals("000000011100000", alu.floatAddition("00000010100000", "00000001000000", 5, 8, 6));
        assertEquals("111111100000000", alu.floatAddition("11111100000000", "01100001000000", 5, 8, 6));
        assertEquals("000111101100000000000000000000000", alu.floatAddition("00111111000000000000000000000000", "10111110111000000000000000000000", 8, 23, 6));
    }

    @Test
    public void floatSubtractionTest() {
        assertEquals("000000000000000", alu.floatSubtraction("10001111110000", "10001111110000", 5, 8, 6));
        assertEquals("111111100000000", alu.floatSubtraction("01100001000000", "01111100000000", 5, 8, 6));
        assertEquals("000000000000000", alu.floatSubtraction("10001111110000", "10001111110000", 5, 8, 6));
        assertEquals("000111110010000000", alu.floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 4));
        assertEquals("010001110111000", alu.floatSubtraction("00000011100000", "00001111110000", 5, 8, 6));
    }


}
