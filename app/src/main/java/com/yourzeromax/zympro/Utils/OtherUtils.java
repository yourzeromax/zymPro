package com.yourzeromax.zympro.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yourzeromax on 2017/12/4.
 */

public class OtherUtils {
    public static boolean isPhoneNumber(String phoneNumber){
        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[01256789]\\d{8}|17[0678]\\d{8}";
        Pattern p = Pattern.compile(telRegex);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }
}
