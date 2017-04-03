import java.math.BigDecimal;

/**
 * ģ��ALU���������͸���������������
 *
 * @author 151070063_������
 */

public class ALU {

    /**
     * ����
     *
     * @param x �ɱ������Ϊ'0'��'1'
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
     * ����
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
     * ����
     *
     * @param x
     * @return
     */
    public char not(char x) {
        return x == '1' ? '0' : '1';
    }

    /**
     * �����
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
     * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
     * ����integerRepresentation("9", 8)
     *
     * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
     * @param length �����Ʋ����ʾ�ĳ���
     * @return number�Ķ����Ʋ����ʾ������Ϊlength
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
     * ����תԭ��
     * �������������ôֱ�ӷ��أ�����Ǹ�������ô����λ���䣬����λȡ����һ
     * �н�λ���Ƿ���λ�ĺ�һλ
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
     * ������ԭ��С��תʮ����
     *
     * @param decimal
     * @return
     */
    public String decimalTrueFormToDec(String decimal) {
        BigDecimal res = new BigDecimal(0);
        char[] dec = decimal.replace("0.", "").toCharArray();
        for (int i = 0; i < dec.length; i++) {
            res = res.add(new BigDecimal((dec[i] - '0') * Math.pow(2, -i - 1)));
        }
        return res.toString();
    }

    /**
     * ʮ���Ƶ�С��ת������ԭ��
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
     * ʮ��������ת������ԭ��
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
     * ������ԭ��ת�����ŵ�ʮ����
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
     * ������ԭ��ת�������ŵ�ʮ����
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
     * �������ɸ������ȵ���ͬ�ַ����ַ���
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
     * ����ָ����ĩλ��0
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
     * ����β��������������ȳ���length������0ȡ�����������length����ĩβ��0
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
     * �ж��������Ƿ�ȫ���ַ�����ĳ���ַ�
     *
     * @param number
     * @param specificChar
     * @return
     */
    public boolean allOf(String number, char specificChar) {
        for (char c : number.toCharArray()) {
            if (c != specificChar) {
                return false;
            }
        }
        return true;
    }

    /**
     * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
     * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
     * �������Ϊ��0���롣<br/>
     * ����floatRepresentation("11.375", 8, 11)
     *
     * @param number  ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
     * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
     */
    public String floatRepresentation(String number, int eLength, int sLength) {
        StringBuilder sb = new StringBuilder();
        //�������������������ж�
        if (number.equals("+Inf") || number.equals("-Inf")) {
            sb.append(number.charAt(0) == '-' ? '1' : '0');
            return sb.append(generateChars('1', eLength)).append(generateChars('0', sLength)).toString();
        }

        //�������λ
        String num;
        if (number.charAt(0) == '-') {
            num = number.substring(1);
            sb.append('1');
        } else {
            num = number;
            sb.append('0');
        }

        //����0�����
        if (allOf(num.replace(".", ""), '0')) {
            return sb.append(generateExp(0, eLength)).append(generateMantissa("0", sLength)).toString();
        }

        //����ʮ���ƵĽ���Ͷ����Ƶ�β��
        String mantissa;
        int expDec;
        int dotPosition = num.indexOf('.');
        //exp�ǽ�����ֵ��ʮ���ƣ�mantissa�����ս��β���Ķ����Ʊ�ʾ
        //ֻ���������ֵ����
        if (dotPosition == -1) {
            mantissa = toInteger(num).substring(1);
            expDec = mantissa.length();
        } else {
            String rawExp = toInteger(num.substring(0, num.indexOf('.')));
            //ֻ��С�����ֵ����
            if (rawExp.length() == 0) {
                mantissa = decimalIntegerRepresentation(num.substring(num.indexOf('.')), 2 * sLength);
                expDec = -mantissa.indexOf('1') - 1;
                //��1��ȡ��һ��1֮��Ĳ��֣���Ϊ��һ��1��С����ǰ��1�����ؽ��������β������
                int mantissaBeginIndex = mantissa.indexOf('1') + 1;
                if (mantissaBeginIndex > mantissa.length()) {
                    mantissa = "0";
                } else if (mantissaBeginIndex == mantissa.length() && (expDec + (int) Math.pow(2, eLength - 1) - 1) == 0) {
                    //��ʱΪ�ǹ����
                    //����ָ��Ϊ8λʱ����ָ����ֵΪ-126(2 - 2^(n-1))ʱ���ҽ�������Ƶĵ�һ��1֮��ȫΪ0������Ϊ�ǹ����
                    //��������ֵΪ1-2^(n-1)�����ǹ�񻯵�ָ����Сֵ����Ĭ��С����ǰΪ1ȡβ��ʱ������β��ȫΪ0
                    //Ϊ��β����ȫΪ0�����ó�һλ��β��������ָ��ȫΪ0��Ĭ��С����ǰΪ0
                    mantissa = mantissa.substring(mantissaBeginIndex - 1);
                } else {
                    mantissa = mantissa.substring(mantissaBeginIndex);
                }
            } else {
                //��������������С�������
                expDec = rawExp.length() - 1;
                mantissa = rawExp.substring(1) + decimalIntegerRepresentation(num.substring(num.indexOf('.')), sLength);
            }
        }
        expDec += (int) Math.pow(2, eLength - 1) - 1;
        //��ӵ������
        sb.append(generateExp(expDec, eLength));
        sb.append(generateMantissa(mantissa, sLength));
        return sb.toString();
    }


    /**
     * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
     * ����ieee754("11.375", 32)
     *
     * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
     * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
     * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
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
     * ��������Ʋ����ʾ����������ֵ��<br/>
     * ����integerTrueValue("00001001")
     *
     * @param operand �����Ʋ����ʾ�Ĳ�����
     * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
     */
    public String integerTrueValue(String operand) {
        return trueFormToSignedDec(complementToTrueForm(operand));
    }


    /**
     * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
     * ����floatTrueValue("01000001001101100000", 8, 11)
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
     */
    public String floatTrueValue(String operand, int eLength, int sLength) {

        String expStr = operand.substring(1, 1 + eLength);
        String mantissa = operand.substring(1 + eLength);
        char beforeDot = '1';
        //���������Nan
        if (allOf(expStr, '1')) {
            //���ָ��ȫΪ1��β��ȫΪ0����ô������
            if (allOf(mantissa, '0')) {
                return (operand.charAt(0) == '0' ? '+' : '-') + "Inf";
            } else {
                //���ָ��ȫΪ1��β����ȫΪ0����ô�Ƿ���
                return "NaN";
            }
        } else if (allOf(expStr, '0')) {
            //����0�����
            if (allOf(mantissa, '0')) {
                return "0";
            } else {
                //������ȫΪ0����β����ȫΪ0ʱ��Ϊ�ǹ������С����ǰΪ0
                //��ʱ�涨����Ϊ2 - 2^(n-1)
                beforeDot = '0';
            }
        }
        StringBuilder sb = new StringBuilder();
        if (operand.charAt(0) == '1') {
            sb.append('-');
        }

        //��ȡ������ԭ���������С��
        int exp;
        String integer;
        String decimal;
        //���ǹ���������
        if (beforeDot == '1') {
            exp = trueFormToUnSignedDec(expStr) - ((int) Math.pow(2, eLength - 1) - 1);
            //�������ֲ�Ϊ0�����
            if (exp >= 0) {
                mantissa = new StringBuilder().append(beforeDot).append(mantissa).toString();
                integer = mantissa.substring(0, exp + 1);
                //������1�����뵽decimal��
                //ǰ��Ĭ����һ��0.
                decimal = "0." + mantissa.substring(exp + 1);
            } else {
                //��������Ϊ0�����
                integer = "0";
                decimal = decimalLeftShift(beforeDot + "." + mantissa, -exp);
            }
        } else {
            //���Ƿǹ����������������8λָ������ôָ����ֵΪ-126( 2- 2^(n-1))��
            exp = 2 - (int) Math.pow(2, eLength - 1);
            integer = "0";
            decimal = decimalLeftShift(beforeDot + "." + mantissa, -exp);
        }
        sb.append(trueFormToUnSignedDec(integer));
        sb.append('.');
        //������ԭ��С��תʮ����
        String dec = decimalTrueFormToDec(decimal);
        sb.append(dec.substring(dec.indexOf('.') + 1));
        return sb.toString();
    }

    /**
     * ��λȡ��������<br/>
     * ����negation("00001001")
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @return operand��λȡ���Ľ��
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
     * ���Ʋ�����<br/>
     * ����leftShift("00001001", 2)
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @param n       ���Ƶ�λ��
     * @return operand����nλ�Ľ��
     */
    public String leftShift(String operand, int n) {
        StringBuffer sb = new StringBuffer(operand.substring(n));
        for (int i = 0; i < n; i++) {
            sb.append("0");
        }
        return sb.toString();
    }

    /**
     * ����������
     *
     * @param operand
     * @param n
     * @return
     */
    public String decimalLeftShift(String operand, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append('0');
        }
        sb.insert(1, '.');
        sb.append(operand.replace(".", ""));
        return sb.toString();
    }

    /**
     * �߼����Ʋ�����<br/>
     * ����logRightShift("11110110", 2)
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @param n       ���Ƶ�λ��
     * @return operand�߼�����nλ�Ľ��
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
     * �������Ʋ�����<br/>
     * ����logRightShift("11110110", 2)
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @param n       ���Ƶ�λ��
     * @return operand��������nλ�Ľ��
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
     * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
     * ����fullAdder('1', '1', '0')
     * S = x xor y xor c
     * Ci = not ((not (x xor y and c ) ) and (not (x and y)))
     *
     * @param x ��������ĳһλ��ȡ0��1
     * @param y ������ĳһλ��ȡ0��1
     * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
     * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
     */
    public String fullAdder(char x, char y, char c) {
        char sum = xor(xor(x, y), c);
        char carry = not(and(not(and(xor(x, y), c)), not(and(x, y))));
        return carry + "" + sum;
    }

//**********************************************************************************************

    /**
     * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
     * ����claAdder("1001", "0001", '1')
     * C1 = G1 + P1C0
     * C2 = G2 + P2 G1 + P2P1C0
     * C3 = G3 + P3G2 + P3P2G1 + P3P2P1C0
     * C4 = G4 + P4 G3 + P4P3 G2 + P4P3 P2 G1 + P4P3P2P1C0
     *
     * @param operand1 4λ�����Ʊ�ʾ�ı�����
     * @param operand2 4λ�����Ʊ�ʾ�ļ���
     * @param c        ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
     * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
     */
    public String claAdder(String operand1, String operand2, char c) {
        char[] res = new char[5];
        char[] op1 = operand1.toCharArray();
        char[] op2 = operand2.toCharArray();
        char[] p = new char[4];
        char[] g = new char[4];
        //op1 i op2 i �Ǵ�����������i��
        for (int i = 0; i < 4; i++) {
            p[3 - i] = or(op1[i], op2[i]);
            g[3 - i] = and(op1[i], op2[i]);
        }
        //pi gi carry[i] �Ǵ�����������i��
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
     * ȡ��4λ��4λ��ӵĴθ�λ��λ
     *
     * @param operand1
     * @param operand2
     * @param c
     * @return �θ�λ��λ
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
     * ��һ����ʵ�ֲ�������1�����㡣
     * ��Ҫ�������š����š�����ŵ�ģ�⣬
     * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
     * {@link #claAdder(String, String, char) claAdder}��
     * {@link #adder(String, String, char, int) adder}��
     * {@link #integerAddition(String, String, int) integerAddition}������<br/>
     * ����oneAdder("00001001")
     *
     * @param operand �����Ʋ����ʾ�Ĳ�����
     * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
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
        //���λ�ʹθ�λȡ��������ͬ����ôû������������ͬ����ô�����
        res[0] = xor(carry, subCarry);
        return new String(res);
    }

    /**
     * ������չ
     * ���������������Ϊ�գ���ô�Ե����β���ָ���ķ���Ϊ׼
     * �������������Ϊ�գ���ô��number�ķ���λΪ׼
     *
     * @param number
     * @param length
     * @param optionalSign
     * @return
     */
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
     * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
     * ����adder("0100", "0011", ��0��, 8)
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ļ���
     * @param c        ���λ��λ
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
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
        //������λ
        //���λ�ʹθ�λ�Ľ�λ
        sb.insert(0, xor(carry, subCarry));
        return sb.toString();
    }

    /**
     * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
     * ����integerAddition("0100", "0011", 8)
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ļ���
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
     */
    public String integerAddition(String operand1, String operand2, int length) {
        return adder(operand1, operand2, '0', length);
    }

    /**
     * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
     * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
     * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
     * ����signedAddition("1100", "1011", 8)
     * 
     * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
     * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
     * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
     */
    public String signedAddition(String operand1, String operand2, int length) {
        String op1 = signExtension(operand1, length);
        String op2 = signExtension(operand2, length);
        char sign;
        if (op1.charAt(0) == op2.charAt(0)) {
            sign = op1.charAt(0);
        }
        String fullRes = adder(op1.substring(1), op2.substring(1), '0', length);
        char overflow = fullRes.charAt(0);
        String res = fullRes.substring(1);
        
        return null;
    }

    /**
     * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
     * ����integerSubtraction("0100", "0011", 8)
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ļ���
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
     */
    public String integerSubtraction(String operand1, String operand2, int length) {
        return adder(operand1, oneAdder(negation(signExtension(operand2, length))).substring(1), '0', length);
    }

    /**
     * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
     * ����integerMultiplication("0100", "0011", 8)
     * �����Ʋ���
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ĳ���
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
     */
    public String integerMultiplication(String operand1, String operand2, int length) {
        //�Ƚ��з�����չ
        String op1 = signExtension(operand1, length);
        String op2 = signExtension(operand2, length);
        Booth booth = new Booth(op1, op2);
        for (int i = 0; i < length; i++) {
            if (booth.isAllZeroOrOne()) {
                //ȫΪ0��ȫΪ1
                booth.rightShift();
            } else {
                if (booth.Q0() == '0' && booth.Qn_1() == '1') {
                    booth.addToA();
                } else if (booth.Q0() == '1' && booth.Qn_1() == '0') {
                    booth.subFromA();
                }
                booth.rightShift();
            }
        }
        return (booth.isOverFlow() ? '1' : '0') + booth.getResult();
    }

    /**
     * ��˹�㷨������
     * �򻯶�A��Q��Qn_1�Ĳ���
     */
    private class Booth {
        private String M;
        private String AQQn_1;
        private String Q;
        private int length;

        Booth(String Q, String M) {
            this.Q = Q;
            this.M = M;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Q.length(); i++) {
                sb.append('0');
            }
            sb.append(Q);
            sb.append('0');
            this.AQQn_1 = sb.toString();
            this.length = Q.length();
        }

        public void addToA() {
            setA(adder(getA(), M, '0', length).substring(1));
        }

        public void subFromA() {
            setA(adder(getA(), oneAdder(negation(M)).substring(1), '0', length).substring(1));
        }

        /**
         * �������������������
         */
        public void rightShift() {
            AQQn_1 = ariRightShift(AQQn_1, 1);
        }

        public String getA() {
            return AQQn_1.substring(0, length);
        }

        public String getQ() {
            return AQQn_1.substring(length);
        }

        public void setA(String A) {
            AQQn_1 = A + AQQn_1.substring(length);
        }

        public char Q0() {
            return AQQn_1.charAt(AQQn_1.length() - 2);
        }

        public char Qn_1() {
            return AQQn_1.charAt(AQQn_1.length() - 1);
        }

        /**
         * ֻ�е���32λȫΪ1/0�����32λ�����λ��ͬʱ����û����������������
         *
         * @return
         */
        public boolean isOverFlow() {

            if (allOf(getA(), '0') && getQ().charAt(0) == '0') {
                return false;
            } else if (allOf(getA(), '1') && getQ().charAt(0) == '1') {
                return false;
            } else {
                return true;
            }
        }

        public String getResult() {
            return AQQn_1.substring(length, AQQn_1.length() - 1);
        }

        public boolean isAllZeroOrOne() {
            return (Q0() == '0' && Qn_1() == '0') || (Q0() == '1' && Qn_1() == '1');
        }
    }


    /**
     * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
     * ����integerDivision("0100", "0011", 8)
     * nλ��������nλ��������-2^(n-1)/-1= 2^(n-1)�ᷢ������⣬������������ᷢ�����
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ĳ���
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
     */
    public String integerDivision(String operand1, String operand2, int length) {
        String dividend = signExtension(operand1, length);
        String divisor = signExtension(operand2, length);

        //�������Ϊ0����ô����NaN
        if (allOf(divisor.substring(1), '0')) {
            return "NaN";
        }

        //���������Ϊ0����ôֱ�ӷ���0
        if (allOf(dividend, '0')) {
            return generateChars('0', 2 * length + 1);
        }
        
        //�����-2^(n-1)�Ķ����Ʋ����ʾ
        String _2n_1 = '1' + generateChars('0', length - 2) + '1';
        //�����������-2^(n-1)�������Ʋ���Ϊ10000....0001���ҳ���Ϊ-1�������Ʋ���Ϊȫ1�������
        if (dividend.equals(_2n_1) && allOf(divisor, '1')) {
            return '1' + _2n_1;
        }
        
        //�����������
        NonRestoringRemainder nrr = new NonRestoringRemainder(dividend, divisor);
        //��һ�μӼ������ɱ������ͳ����ķ��ž�����ͬ�����ӷ���������ӷ�
        boolean nextSub = isSameSign(dividend, divisor);
        //ѭ��n+1�Σ����һ�β���������
        for (int i = 0; i < length + 1; i++) {
            if (nextSub) {
                nrr.subFromR();
            } else {
                nrr.addToR();
            }
            //�������������ͳ���ͬ�ţ���ô��1���´�������
            if (isSameSign(nrr.getR(), divisor)) {
                nrr.carry('1');
                nextSub = true;
            } else {
                //��������ͳ�����ţ���ô��0���´����ӷ�
                nrr.carry('0');
                nextSub = false;
            }
            if (i != length) {
                nrr.leftShift();
            }
        }
        return '0' + nrr.getResult();
    }

    /**
     * �ж����������Ʋ����Ƿ�ͬ��
     *
     * @param op1
     * @param op2
     * @return
     */
    public boolean isSameSign(String op1, String op2) {
        return op1.charAt(0) == op2.charAt(0);
    }

    /**
     * ���ָ�������������
     * ��ʼ��ʱ����X��Y
     * X�Ǳ�������Y�ǳ���
     * R�������Ĵ�����Q���̼Ĵ���
     * RQ��ʼֵ��X����2nλ�ķ�����չ֮���ֵ����nλ�ŵ�R�У���nλ�ŵ�Q��
     * ���R��������Q����
     */
    private class NonRestoringRemainder {
        private String dividend;
        private String divisor;
        private String RQ;
        private int length;
        private char carry;

        NonRestoringRemainder(String dividend, String divisor) {
            this.dividend = dividend;
            this.divisor = divisor;
            this.RQ = signExtension(dividend, dividend.length() * 2);
            this.length = dividend.length();
        }

        public String getR() {
            return RQ.substring(0, length);
        }

        public void setR(String R) {
            this.RQ = R + RQ.substring(length);
        }

        public String getQ() {
            return RQ.substring(length);
        }

        public void addToR() {
            setR(adder(getR(), divisor, '0', length).substring(1));
        }

        public void carry(char carry) {
            this.carry = carry;
        }

        public void subFromR() {
            setR(adder(getR(), oneAdder(negation(divisor)).substring(1), '0', length).substring(1));
        }

        public void leftShift() {
            this.RQ = RQ.substring(1) + carry;
        }

        /**
         * ����ǰ�������ں�
         *
         * @return
         */
        public String getResult() {
            //���һ��ֻ������λ
            String quotient = getQ().substring(1) + carry;
            //�����λ����<0����ô�� += 1
            if (quotient.charAt(0) == '1') {
                quotient = oneAdder(quotient).substring(1);
            }
            //�������ͱ�������ŵ�ǰ���£�����������ͳ���ͬ�ţ���ô����+=��������ţ�����-=����
            if (!isSameSign(getR(), dividend)) {
                if (isSameSign(dividend, divisor)) {
                    addToR();
                } else {
                    subFromR();
                }
            }
            return quotient + getR();
        }

    }


    /**
     * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
     * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
     *
     * @param operand1 �����Ʊ�ʾ�ı�����
     * @param operand2 �����Ʊ�ʾ�ļ���
     * @param eLength  ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength  β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param gLength  ����λ�ĳ���
     * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
     */
    public String floatAddition(String operand1, String operand2, int eLength, int sLength, int gLength) {
        // TODO YOUR CODE HERE.
        return null;
    }

    /**
     * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
     * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
     *
     * @param operand1 �����Ʊ�ʾ�ı�����
     * @param operand2 �����Ʊ�ʾ�ļ���
     * @param eLength  ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength  β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param gLength  ����λ�ĳ���
     * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
     */
    public String floatSubtraction(String operand1, String operand2, int eLength, int sLength, int gLength) {
        // TODO YOUR CODE HERE.
        return null;
    }

    /**
     * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
     * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
     *
     * @param operand1 �����Ʊ�ʾ�ı�����
     * @param operand2 �����Ʊ�ʾ�ĳ���
     * @param eLength  ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength  β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
     */
    public String floatMultiplication(String operand1, String operand2, int eLength, int sLength) {
        // TODO YOUR CODE HERE.
        return null;
    }

    /**
     * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
     * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
     *
     * @param operand1 �����Ʊ�ʾ�ı�����
     * @param operand2 �����Ʊ�ʾ�ĳ���
     * @param eLength  ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength  β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
     */
    public String floatDivision(String operand1, String operand2, int eLength, int sLength) {
        // TODO YOUR CODE HERE.
        return null;
    }
}
