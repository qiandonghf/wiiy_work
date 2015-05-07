package com.wiiy.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	/**
	 * 分割字符串 返回integer型数据
	 * 
	 * @param string
	 * @param regex
	 *            分割符(可以为长字符串)
	 * @return
	 */
	public static Integer[] splitToIntegerArray(String string, String regex) {
		String[] ids = string.split(regex);
		List<Integer> list = new ArrayList<Integer>();
		for (String id : ids) {
			if (id.trim().length() > 0 && !id.equals(regex)) {
				list.add(Integer.parseInt(id.trim()));
			}
		}
		Integer[] array = new Integer[list.size()];
		list.toArray(array);
		return array;
	}

	/**
	 * ,分割字符串 返回integer型数据
	 * 
	 * @param string
	 * @return
	 */
	public static Integer[] splitToIntegerArray(String string) {
		return splitToIntegerArray(string, ",");
	}

	/**
	 * 分割字符串 返回long型数据
	 * 
	 * @param string
	 * @param regex
	 *            分割符(可以为长字符串)
	 * @return
	 */
	public static Long[] splitToLongArray(String string, String regex) {
		String[] ids = string.split(regex);
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			if (id.trim().length() > 0 && !id.equals(regex)) {
				list.add(Long.parseLong(id.trim()));
			}
		}
		Long[] array = new Long[list.size()];
		list.toArray(array);
		return array;
	}

	/**
	 * ,分割字符串 返回long型数据
	 * 
	 * @param string
	 * @return
	 */
	public static Long[] splitToLongArray(String string) {
		return splitToLongArray(string, ",");
	}

	/**
	 * ISOToUTF8
	 * 
	 * @param s
	 * @return
	 */
	public static String ISOToUTF8(String s) {
		try {
			return new String(s.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}

	/**
	 * URLEncoderToUTF8
	 * 
	 * @param s
	 * @return
	 */
	public static String URLEncoderToUTF8(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}

	/**
	 * 将首字母转换为大写
	 * 
	 * @param string
	 * @return
	 */
	public static String convertFirstCharToUpperCase(String string) {
		if (string != null && string.length() > 1) {
			return Character.toUpperCase(string.charAt(0)) + string.substring(1);
		}
		return string;
	}

	/**
	 * 将首字母转换为小写
	 * 
	 * @param string
	 * @return
	 */
	public static String convertFirstCharToLowerCase(String string) {
		if (string != null && string.length() > 1) {
			return Character.toLowerCase(string.charAt(0)) + string.substring(1);
		}
		return string;
	}

	/**
	 * 如果string 以exp开头或以exp结尾则截去
	 * 
	 * @param string
	 * @param exp
	 * @return
	 */
	public static String trim(String string, String exp) {
		while (string.startsWith(exp)) {
			string = string.substring(exp.length());
		}
		while (string.endsWith(exp)) {
			string = string.substring(0, string.lastIndexOf(exp));
		}
		return string;
	}

	public static void main(String[] args) {
		//System.out.println(trim("aaasdlfsfwerafea", "ea"));
		
		
		/* //整数
        System.out.println(digitUppercase(0));              // 零元整
        System.out.println(digitUppercase(123));            // 壹佰贰拾叁元整
        System.out.println(digitUppercase(1000000));        // 壹佰万元整
        System.out.println(digitUppercase(100000001));      // 壹亿零壹元整
        System.out.println(digitUppercase(1000000000));     // 壹拾亿元整
        System.out.println(digitUppercase(1234567890));     // 壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元整
        System.out.println(digitUppercase(1001100101));     // 壹拾亿零壹佰壹拾万零壹佰零壹元整
        System.out.println(digitUppercase(110101010));      // 壹亿壹仟零壹拾万壹仟零壹拾元整
     
        //小数
        System.out.println(digitUppercase(0.12));          // 壹角贰分
        System.out.println(digitUppercase(123.34));        // 壹佰贰拾叁元叁角肆分
        System.out.println(digitUppercase(1000000.56));    // 壹佰万元伍角陆分
        System.out.println(digitUppercase(100000001.78));  // 壹亿零壹元柒角捌分
        System.out.println(digitUppercase(1000000000.90)); // 壹拾亿元玖角
        System.out.println(digitUppercase(1234567890.03)); // 壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元叁分
        System.out.println(digitUppercase(1001100101.00)); // 壹拾亿零壹佰壹拾万零壹佰零壹元整
        System.out.println(digitUppercase(110101010.10));  // 壹亿壹仟零壹拾万壹仟零壹拾元壹角
         
        //负数
        System.out.println(digitUppercase(-0.12));          // 负壹角贰分
        System.out.println(digitUppercase(-123.34));        // 负壹佰贰拾叁元叁角肆分
        System.out.println(digitUppercase(-1000000.56));    // 负壹佰万元伍角陆分
        System.out.println(digitUppercase(-100000001.78));  // 负壹亿零壹元柒角捌分
        System.out.println(digitUppercase(-1000000000.90)); // 负壹拾亿元玖角
        System.out.println(digitUppercase(-1234567890.03)); // 负壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元叁分
        System.out.println(digitUppercase(-1001100101.00)); // 负壹拾亿零壹佰壹拾万零壹佰零壹元整
        System.out.println(digitUppercase(-110101010.10));  // 负壹亿壹仟零壹拾万壹仟零壹拾元壹角
*/	}
	
	/**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
     * 要用到正则表达式
     */
    public static String digitUppercase(double n){
        String fraction[] = {"角", "分"};
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String unit[][] = {{"元", "万", "亿"},
                     {"", "拾", "佰", "仟"}};
 
        String head = n < 0? "负": "";
        n = Math.abs(n);
         
        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if(s.length()<1){
            s = "整";    
        }
        int integerPart = (int)Math.floor(n);
 
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p ="";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart%10]+unit[1][j] + p;
                integerPart = integerPart/10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }
}


