package com.gupiao;

import java.util.Currency;

/**
 * Created by yejun on 18/1/20.
 */
public class Test002 {

    public static void main(String[] args) {
        String a = "MAX_AMOUNT decimal(15,2)";
        String b = "RSVD_AMT2 decimal(19,4) DEFAULT '0.0000'";

        System.out.println(a.split(" ")[0] + " decimal(20,0) ");

        System.out.println(b.replaceAll("0.0000","0.00000000"));

        System.out.println(Currency.getInstance("CNY").getDefaultFractionDigits());


    }
}
