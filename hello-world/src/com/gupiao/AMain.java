package com.gupiao;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by yejun on 18/1/1.
 */
public class AMain {

    //=============================

    public static final String URL = "http://stock.liangyee.com/bus-api/stock/freeStockMarketData/getDailyKBar";
    public static final String PARAM = "userKey={userKey}&startDate={date}&symbol={symbol}&endDate={date}&type=0";
    public static final String USERKEY = "6BEA02F1C56E4C4B98281FC0A9F87EAD";

    public static void main(String[] args) throws Exception{
//        Scanner sc= new Scanner(System.in);
//        System.out.println("输入股票代码:");
//        String symbolIN = sc.next();
//        System.out.println("键盘输入的内容是："+ symbolIN);

        String symbolList = "600292,600340,600116";
        if(args != null && args.length > 0){
            System.out.println("接收外部参数: " + args[0]);
            symbolList = args[0];
        }

        String[] symbols = symbolList.split(",");

        for(String symbol : symbols){

            //查询等待,防止超过频频限制
            Thread.sleep(1000*5);

//        String symbol = "600340";
//        if(symbolIN != null && symbolIN.trim().length()>0){
//            symbol = symbolIN.trim();
//        }

            SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-DD");
            String dataStr = sf.format(new Date());
//        dataStr = "2017-12-25";


            System.out.println("开始查询股票信息: (股票代码:" + symbol + ",交易日:" + dataStr + ")");
            String reqParam = PARAM.replaceAll("\\{userKey\\}",USERKEY).replaceAll("\\{date\\}",dataStr).replaceAll("\\{symbol\\}",symbol);
            String result = HttpUtils.sendGet(URL,reqParam);

            if(result.indexOf("success") == -1){
                System.out.println("查询失败,结果:" + result);
                return;
            }

            String resultFormat = result.replaceAll(" ","");
            String strTmp = resultFormat.split("\\[")[1].split("]")[0];
            strTmp = strTmp.replaceAll("\"","");
            strTmp = resultFormat.split("\\[")[0] +"\""+ strTmp +"\""+ resultFormat.split("]")[1];
            System.out.println("查询结果: "+strTmp);

            Map a = (Map) JSON.parse(strTmp);
            String columns  = (String)a.get("columns");
            String resultStr = (String)a.get("result");
            if(resultStr == null || resultStr.length() == 0){
                System.out.println("没有查到当前股票的数据");
                return;
            }

            System.out.println("准备更新数据库信息...");
            System.out.println("查询数据库中当前股票信息: (股票代码:" + symbol + ")");

            GuPiaoDao guPiaoDao = new GuPiaoDao();
            GupiaoTO gupiaoTO = guPiaoDao.getByCode(symbol);

            if(gupiaoTO != null){
                System.out.println(gupiaoTO.getStockCode() + "(" + gupiaoTO.getStockName() + ") --> 现价:" + resultStr.split(",")[2] );

                gupiaoTO.setPresent_price(resultStr.split(",")[2]); // 现价
                gupiaoTO.setTransDate(dataStr);

                guPiaoDao.updateGupiao(gupiaoTO);
                System.out.println("更新成功!");

            }else {
                System.out.println("数据库没有纪录");
            }
        }
    }
}
