
/**
 * 模拟ALU进行整数和浮点数的四则运算
 *
 * @author 151070063_宋欣建
 */

public class ALU {
    private static String _127 = "01111111";

    /**
     * 与门
     *
     * @param x 可变参数，为'0'或'1'
     * @return x[0] and x[1] ... and x[n-1]
     */
    public char and(char... x) {
        for (char c : x) {
            if (c == '0') {
                return '0';
            }
        }
        return '1';
    }

    /**
     * 或门
     *
     * @param x
     * @return x
     */
    public char or(char... x) {
        for (char c : x) {
            if (c == '1') {
                return '1';
            }
        }
        return '0';
    }

    /**
     * 非门
     *
     * @param x
     * @return
     */
    public char not(char x) {
        return x == '1' ? '0' : '1';
    }

    /**
     * 异或门
     *
     * @param x
     * @param y
     * @return
     */
    public char xor(char x, char y) {
        if (x == y) {
            return '0';
        } else {
            return '1';
        }
    }

    /**
     * 生成十进制整数的二进制补码表示。<br/>
     * 例：integerRepresentation("9", 8)
     *
     * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
     * @param length 二进制补码表示的长度
     * @return number的二进制补码表示，长度为length
     */
    public String integerRepresentation(String number, int length) {
        if (length == 0) {
            return "";
        }
        if (number.charAt(0) == '-') {
            return signExtension(oneAdder(negation(toInteger(number.substring(1)))).substring(1), length, '1');
        } else {
            return signExtension(toInteger(number), length, '0');
        }
    }

    /**
     * 补码转原码
     * 如果是正数，那么直接返回；如果是负数，那么符号位不变，其他位取反加一
     * 有进位，是符号位的后一位
     *
     * @param complement
     * @return
     */
    public String complementToTrueForm(String complement) {
        if (complement.charAt(0) == '0') {
            return complement;
        } else {
            return '1' + oneAdder('0' + negation(complement.substring(1))).substring(1);
        }
    }

    /**
     * 十进制的小数转补码
     *
     * @param number
     * @param length
     * @return
     */
    public String decimalIntegerRepresentation(String number, int length) {
        double num = Double.parseDouble(number);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            num *= 2;
            //if num == 1
            if (Double.compare(num, 1) > 0) {
                sb.append('1');
                num -= 1;
            } else if (Double.compare(num, 1) == 0) {
                sb.append('1');
                return sb.toString();
            } else {
                sb.append('0');
            }
        }
        return sb.toString();
    }

    /**
     * 十进制整数转二进制原码
     *
     * @param number
     * @return
     */
    public String toInteger(String number) {
        long num = Long.parseLong(number);
        StringBuilder sb = new StringBuilder();
        while (num != 0) {
            sb.insert(0, num % 2);
            num /= 2;
        }
        return sb.toString();
    }

    /**
     * 二进制原码转带符号的十进制
     *
     * @param binary
     * @return
     */
    public String trueFormToSignedDec(String binary) {
        long res = 0;
        char[] bin = binary.toCharArray();
        for (int i = bin.length - 1; i >= 1; --i) {
            res += (bin[i] - '0') * (long) Math.pow(2, bin.length - i - 1);
        }
        if (bin[0] == '1') {
            res *= -1;
        }
        return String.valueOf(res);
    }

    /**
     * 二进制原码转不带符号的十进制
     *
     * @param binary
     * @return
     */
    public int trueFormToUnSignedDec(String binary) {
        int res = 0;
        char[] bin = binary.toCharArray();
        for (int i = bin.length - 1; i >= 0; --i) {
            res += (bin[i] - '0') * (long) Math.pow(2, bin.length - i - 1);
        }
        return res;
    }

    /**
     * 批量生成给定长度的相同字符的字符串
     *
     * @param c
     * @param length
     * @return
     */
    public String generateChars(char c, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 生成指数，末位补0
     *
     * @param exp
     * @param length
     * @return
     */
    public String generateExp(int exp, int length) {
        String expBin = toInteger(String.valueOf(exp));
        return new StringBuilder().append(generateChars('0', length - expBin.length())).append(expBin).toString();
    }


    /**
     * 生成尾数，如果参数长度超过length，便向0取整；如果少于length，便末尾补0
     *
     * @param mantissa
     * @param length
     * @return
     */
    public String generateMantissa(String mantissa, int length) {
        if (mantissa.length() >= length) {
            return mantissa.substring(0, length);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(mantissa);
            sb.append(generateChars('0', length - mantissa.length()));
            return sb.toString();
        }
    }

    /**
     * 生成十进制浮点数的二进制表示。
     * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
     * 舍入策略为向0舍入。<br/>
     * 例：floatRepresentation("11.375", 8, 11)
     *
     * @param number  十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
     * @param eLength 指数的长度，取值大于等于 4
     * @param sLength 尾数的长度，取值大于等于 4
     * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
     */
    public String floatRepresentation(String number, int eLength, int sLength) {
        StringBuilder sb = new StringBuilder();
        //处理无穷的情况，先行判断
        if (number.equals("+Inf") || number.equals("-Inf")) {
            sb.append(number.charAt(0) == '-' ? '1' : '0');
            return sb.append(generateChars('1', eLength)).append(generateChars('0', sLength)).toString();
        }

        //处理符号位
        String num;
        if (number.charAt(0) == '-') {
            num = number.substring(1);
            sb.append('1');
        } else {
            num = number;
            sb.append('0');
        }

        //处理0的情况
        boolean allZero = true;
        for (char c : num.replace(".", "").toCharArray()) {
            if (c != '0') {
                allZero = false;
                break;
            }
        }
        if (allZero) {
            return sb.append(generateExp(0, eLength)).append(generateMantissa("0", sLength)).toString();
        }

        //生成十进制的阶码和二进制的尾数
        String mantissa;
        int expDec;
        int dotPosition = num.indexOf('.');
        //exp是阶码真值的十进制，mantissa是最终结果尾数的二进制表示
        //只有整数部分的情况
        if (dotPosition == -1) {
            mantissa = toInteger(num).substring(1);
            expDec = mantissa.length();
        } else {
            String rawExp = toInteger(num.substring(0, num.indexOf('.')));
            //只有小数部分的情况
            if (rawExp.length() == 0) {
                mantissa = decimalIntegerRepresentation(num.substring(num.indexOf('.')), sLength);
                expDec = -mantissa.indexOf('1') - 1;
                int beginIndex = mantissa.indexOf('1') + 1;
                if (beginIndex >= mantissa.length()) {
                    mantissa = "0";
                } else {
                    mantissa = mantissa.substring(beginIndex);
                }
            } else {
                //既有整数，又有小数的情况
                expDec = rawExp.length() - 1;
                mantissa = rawExp.substring(1) + decimalIntegerRepresentation(num.substring(num.indexOf('.')), sLength);
            }
        }
        expDec += (int) Math.pow(2, eLength - 1) - 1;
        //添加到结果中
        sb.append(generateExp(expDec, eLength));
        sb.append(generateMantissa(mantissa, sLength));
        return sb.toString();
    }


    /**
     * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
     * 例：ieee754("11.375", 32)
     *
     * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
     * @param length 二进制表示的长度，为32或64
     * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
     */
    public String ieee754(String number, int length) {
        if (length == 32) {
            return floatRepresentation(number, 8, 23);
        } else if (length == 64) {
            return floatRepresentation(number, 11, 52);
        } else {
            return null;
        }
    }

    /**
     * 计算二进制补码表示的整数的真值。<br/>
     * 例：integerTrueValue("00001001")
     *
     * @param operand 二进制补码表示的操作数
     * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
     */
    public String integerTrueValue(String operand) {
        return trueFormToSignedDec(complementToTrueForm(operand));
    }


    /**
     * 计算二进制原码表示的浮点数的真值。<br/>
     * 例：floatTrueValue("01000001001101100000", 8, 11)
     *
     * @param operand 二进制表示的操作数
     * @param eLength 指数的长度，取值大于等于 4
     * @param sLength 尾数的长度，取值大于等于 4
     * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
     */
    public String floatTrueValue(String operand, int eLength, int sLength) {
        StringBuilder sb = new StringBuilder();
        if (operand.charAt(0) == '1') {
            sb.append('-');
        }
        String expStr = operand.substring(1, 1 + eLength);
        String mantissa = operand.substring(1 + eLength);
        int exp = trueFormToUnSignedDec(expStr) - ((int)Math.pow(2, eLength - 1) - 1);
        
        return null;
    }

    /**
     * 按位取反操作。<br/>
     * 例：negation("00001001")
     *
     * @param operand 二进制表示的操作数
     * @return operand按位取反的结果
     */
    public String negation(String operand) {
        StringBuilder sb = new StringBuilder();
        for (char c : operand.toCharArray()) {
            if (c == '0') {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    /**
     * 左移操作。<br/>
     * 例：leftShift("00001001", 2)
     *
     * @param operand 二进制表示的操作数
     * @param n       左移的位数
     * @return operand左移n位的结果
     */
    public String leftShift(String operand, int n) {
        StringBuffer sb = new StringBuffer(operand.substring(n));
        for (int i = 0; i < n; i++) {
            sb.append("0");
        }
        return sb.toString();
    }

    /**
     * 逻辑右移操作。<br/>
     * 例：logRightShift("11110110", 2)
     *
     * @param operand 二进制表示的操作数
     * @param n       右移的位数
     * @return operand逻辑右移n位的结果
     */
    public String logRightShift(String operand, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("0");
        }
        sb.append(operand.substring(0, operand.length() - n));
        return sb.toString();
    }

    /**
     * 算术右移操作。<br/>
     * 例：logRightShift("11110110", 2)
     *
     * @param operand 二进制表示的操作数
     * @param n       右移的位数
     * @return operand算术右移n位的结果
     */
    public String ariRightShift(String operand, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(operand.charAt(0));
        }
        sb.append(operand.substring(0, operand.length() - n));
        return sb.toString();
    }

    /**
     * 全加器，对两位以及进位进行加法运算。<br/>
     * 例：fullAdder('1', '1', '0')
     * S = x xor y xor c
     * Ci = not ((not (x xor y and c ) ) and (not (x and y)))
     *
     * @param x 被加数的某一位，取0或1
     * @param y 加数的某一位，取0或1
     * @param c 低位对当前位的进位，取0或1
     * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
     */
    public String fullAdder(char x, char y, char c) {
        char sum = xor(xor(x, y), c);
        char carry = not(and(not(and(xor(x, y), c)), not(and(x, y))));
        return carry + "" + sum;
    }

//**********************************************************************************************

    /**
     * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
     * 例：claAdder("1001", "0001", '1')
     * C1 = G1 + P1C0
     * C2 = G2 + P2 G1 + P2P1C0
     * C3 = G3 + P3G2 + P3P2G1 + P3P2P1C0
     * C4 = G4 + P4 G3 + P4P3 G2 + P4P3 P2 G1 + P4P3 P2P1C0
     *
     * @param operand1 4位二进制表示的被加数
     * @param operand2 4位二进制表示的加数
     * @param c        低位对当前位的进位，取0或1
     * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
     */
    public String claAdder(String operand1, String operand2, char c) {
        char[] res = new char[5];
        char[] op1 = operand1.toCharArray();
        char[] op2 = operand2.toCharArray();
        char[] p = new char[4];
        char[] g = new char[4];
        //op1 i op2 i 是从左往右数第i个
        for (int i = 0; i < 4; i++) {
            p[3 - i] = or(op1[i], op2[i]);
            g[3 - i] = and(op1[i], op2[i]);
        }
        //pi gi carry[i] 是从右往左数第i个
        char[] carry = new char[5];
        carry[0] = c;
        carry[1] = or(g[0], and(p[0], c));
        carry[2] = or(g[1], and(p[1], g[0]), and(p[1], p[0], c));
        carry[3] = or(g[2], and(p[2], g[1]), and(p[2], p[1], g[0]), and(p[2], p[1], p[0], c));
        carry[4] = or(g[3], and(p[3], g[2]), and(p[3], p[2], g[1]), and(p[3], p[2], p[1], g[0]), and(p[3], p[2], p[1], p[0], c));
        for (int i = 3; i >= 0; --i) {
            res[i + 1] = fullAdder(op1[i], op2[i], carry[3 - i]).charAt(1);
        }
        res[0] = carry[4];
        return new String(res);
    }

    /**
     * 取得4位和4位相加的次高位进位
     *
     * @param operand1
     * @param operand2
     * @param c
     * @return 次高位进位
     */
    public char getSubCarry(String operand1, String operand2, char c) {
        char[] op1 = operand1.toCharArray();
        char[] op2 = operand2.toCharArray();
        char[] p = new char[4];
        char[] g = new char[4];
        for (int i = 0; i < 4; i++) {
            p[3 - i] = or(op1[i], op2[i]);
            g[3 - i] = and(op1[i], op2[i]);
        }
        return or(g[2], and(p[2], g[1]), and(p[2], p[1], g[0]), and(p[2], p[1], p[0], c));
    }

    /**
     * 加一器，实现操作数加1的运算。
     * 需要采用与门、或门、异或门等模拟，
     * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
     * {@link #claAdder(String, String, char) claAdder}、
     * {@link #adder(String, String, char, int) adder}、
     * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
     * 例：oneAdder("00001001")
     *
     * @param operand 二进制补码表示的操作数
     * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
     */
    public String oneAdder(String operand) {
        char[] res = new char[operand.length() + 1];
        char[] op = operand.toCharArray();
        char sum = 0;
        char addValue = '1';
        char carry = '0';
        char subCarry = '0';
        for (int i = op.length - 1; i >= 0; --i) {
            res[i + 1] = xor(xor(op[i], addValue), carry);
            carry = not(and(not(and(xor(op[i], addValue), carry)), not(and(op[i], addValue))));
            if (addValue == '1') {
                addValue = '0';
            }
            if (i == 1) {
                subCarry = carry;
            }
        }
        //最高位和次高位取异或，如果相同，那么没有溢出；如果不同，那么有溢出
        res[0] = xor(carry, subCarry);
        return new String(res);
    }


    public String signExtension(String number, int length, char... optionalSign) {
        if (number.length() == length) {
            return number;
        }
        StringBuilder sb = new StringBuilder(length);
        char sign;
        if (optionalSign.length != 0) {
            sign = optionalSign[0];
        } else {
            sign = number.charAt(0);
        }
        for (int i = 0; i < length - number.length(); i++) {
            sb.append(sign);
        }
        sb.append(number);
        return sb.toString();
    }

    /**
     * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
     * 例：adder("0100", "0011", ‘0’, 8)
     *
     * @param operand1 二进制补码表示的被加数
     * @param operand2 二进制补码表示的加数
     * @param c        最低位进位
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
     */
    public String adder(String operand1, String operand2, char c, int length) {
        StringBuilder sb = new StringBuilder();
        String op1 = signExtension(operand1, length);
        String op2 = signExtension(operand2, length);
        char carry = c;
        char subCarry = '0';
        for (int i = length / 4 - 1; i >= 0; --i) {
            String res = claAdder(op1.substring(4 * i, 4 * (i + 1)), op2.substring(4 * i, 4 * (i + 1)), carry);
            sb.insert(0, res.substring(1));
            if (i == 0) {
                subCarry = getSubCarry(op1.substring(0, 4), op2.substring(0, 4), carry);
            }
            carry = res.charAt(0);
        }
        //添加溢出位
        //最高位和次高位的进位
        sb.insert(0, xor(carry, subCarry));
        return sb.toString();
    }

    /**
     * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
     * 例：integerAddition("0100", "0011", 8)
     *
     * @param operand1 二进制补码表示的被加数
     * @param operand2 二进制补码表示的加数
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
     */
    public String integerAddition(String operand1, String operand2, int length) {
        return adder(operand1, operand2, '0', length);
    }

    /**
     * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
     * 例：integerSubtraction("0100", "0011", 8)
     *
     * @param operand1 二进制补码表示的被减数
     * @param operand2 二进制补码表示的减数
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
     */
    public String integerSubtraction(String operand1, String operand2, int length) {
        return adder(operand1, oneAdder(negation(signExtension(operand2, length))).substring(1), '0', length);
    }

    /**
     * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
     * 例：integerMultiplication("0100", "0011", 8)
     *
     * @param operand1 二进制补码表示的被乘数
     * @param operand2 二进制补码表示的乘数
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
     */
    public String integerMultiplication(String operand1, String operand2, int length) {
        // TODO YOUR CODE HERE.
        return null;
    }

    /**
     * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
     * 例：integerDivision("0100", "0011", 8)
     *
     * @param operand1 二进制补码表示的被除数
     * @param operand2 二进制补码表示的除数
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
     */
    public String integerDivision(String operand1, String operand2, int length) {
        // TODO YOUR CODE HERE.
        return null;
    }

    /**
     * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
     * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
     * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
     * 例：signedAddition("1100", "1011", 8)
     *
     * @param operand1 二进制原码表示的被加数，其中第1位为符号位
     * @param operand2 二进制原码表示的加数，其中第1位为符号位
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
     * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
     */
    public String signedAddition(String operand1, String operand2, int length) {
        // TODO YOUR CODE HERE.
        return null;
    }

    /**
     * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
     * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
     *
     * @param operand1 二进制表示的被加数
     * @param operand2 二进制表示的加数
     * @param eLength  指数的长度，取值大于等于 4
     * @param sLength  尾数的长度，取值大于等于 4
     * @param gLength  保护位的长度
     * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
     */
    public String floatAddition(String operand1, String operand2, int eLength, int sLength, int gLength) {
        // TODO YOUR CODE HERE.
        return null;
    }

    /**
     * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
     * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
     *
     * @param operand1 二进制表示的被减数
     * @param operand2 二进制表示的减数
     * @param eLength  指数的长度，取值大于等于 4
     * @param sLength  尾数的长度，取值大于等于 4
     * @param gLength  保护位的长度
     * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
     */
    public String floatSubtraction(String operand1, String operand2, int eLength, int sLength, int gLength) {
        // TODO YOUR CODE HERE.
        return null;
    }

    /**
     * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
     * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
     *
     * @param operand1 二进制表示的被乘数
     * @param operand2 二进制表示的乘数
     * @param eLength  指数的长度，取值大于等于 4
     * @param sLength  尾数的长度，取值大于等于 4
     * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
     */
    public String floatMultiplication(String operand1, String operand2, int eLength, int sLength) {
        // TODO YOUR CODE HERE.
        return null;
    }

    /**
     * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
     * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
     *
     * @param operand1 二进制表示的被除数
     * @param operand2 二进制表示的除数
     * @param eLength  指数的长度，取值大于等于 4
     * @param sLength  尾数的长度，取值大于等于 4
     * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
     */
    public String floatDivision(String operand1, String operand2, int eLength, int sLength) {
        // TODO YOUR CODE HERE.
        return null;
    }
}
