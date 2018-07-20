package com.gupiao;

import com.mysql.jdbc.Connection;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yejun on 18/1/14.
 */
public class Test001 {

    public static List<String> dbExcluds = new ArrayList<String>();
    public static List<String> fieldExcluds = new ArrayList<String>();
    public static Map<String,List<String>> dbTable =  new HashMap<String,List<String>>();
    public static Map<String, Map<String,List<String>>> dbTbField = new HashMap<String, Map<String,List<String>>>();


    static {
        dbExcluds.add("information_schema");
        dbExcluds.add("sys");
        dbExcluds.add("performance_schema");
        dbExcluds.add("activemq");

        fieldExcluds.add("ID");
        fieldExcluds.add("VERIFY_ENTITY_ID");
        fieldExcluds.add("FROM_ID");
        fieldExcluds.add("TO_ID");
        fieldExcluds.add("TOTAL_COUNT");
        fieldExcluds.add("CURRENT_UPDATED_COUNT");
        fieldExcluds.add("QUANTITY");
        fieldExcluds.add("RETRY_TIMES");
        fieldExcluds.add("CHANNEL_ID");
        fieldExcluds.add("AUTH_LEVEL");
        fieldExcluds.add("ORDER_NO");
        fieldExcluds.add("CHANNEL_ID");
        fieldExcluds.add("AUTH_LEVEL");
        fieldExcluds.add("ORDER_NO");
        fieldExcluds.add("VOUCHER_NO");
        fieldExcluds.add("CITY_ID");
        fieldExcluds.add("STRATEGY_ID");
        fieldExcluds.add("UNIT_ID");
        fieldExcluds.add("OUNPUT_ID");
        fieldExcluds.add("LIMITED_VALUE");
        fieldExcluds.add("MSG_ID");
        fieldExcluds.add("CHANNEL_ID");
        fieldExcluds.add("DISTRICT_ID");
        fieldExcluds.add("VERIFY_ENTITY_ID");
        fieldExcluds.add("FILED_ID");
        fieldExcluds.add("PARTY_PAYMENT_ID");
        fieldExcluds.add("RULE_ID");
        fieldExcluds.add("AGREEMENT_ID");
        fieldExcluds.add("EVENT_CODE_ID");
        fieldExcluds.add("PAYMENT_FUNDS_ID");
        fieldExcluds.add("BATCH_SEQ_NO");
        fieldExcluds.add("ORDER_SUITE_NO");
        fieldExcluds.add("SESSION_TEMPLATE_ID");
        fieldExcluds.add("RULE_ID");
        fieldExcluds.add("SUITE_NO");
        fieldExcluds.add("TOTAL_NUM");
        fieldExcluds.add("END_NUM");
    }

    public static void main(String[] args) throws Exception{
        List<String> databases = new ArrayList<String>();

        Connection conn = JDBCUtils.getConnection();

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("show DATABASES");

        while (rs.next()){
            String database = rs.getString("Database");
            if(!dbExcluds.contains(database)){
                databases.add(database);
            }
        }

        for(String dbTmp : databases){
//            System.out.println("====" + dbTmp );
            statement.execute("use "+dbTmp);
            rs = statement.executeQuery("show TABLES");
            while (rs.next()){
                String tableName = rs.getString("Tables_in_"+dbTmp);
//                System.out.println("========" + tableName);
                if(dbTable.get(dbTmp) != null){
                    dbTable.get(dbTmp).add(tableName);
                }else {
                    List tbList = new ArrayList<String>();
                    tbList.add(tableName);
                    dbTable.put(dbTmp,tbList);
                }
            }
        }

        BigDecimal minLen = new BigDecimal("10");

        for(String dbTmp : dbTable.keySet()){
//            System.out.println("|---" + dbTmp);
            statement.execute("use "+dbTmp);
            for(String tbTmp : dbTable.get(dbTmp)){
//                System.out.println("|------" + tbTmp);
                rs = statement.executeQuery("show columns from " + tbTmp);
                while (rs.next()){
                    String field = rs.getString("Field");
                    String type = rs.getString("Type");
                    //DEFAULT '0.0000'
                    String defaultStr = rs.getString("Default") == null ? "" : " DEFAULT '"+rs.getString("Default")+"'";
                    if(defaultStr.length() > 0){
                        defaultStr = " DEFAULT '0.00000000'";
                    }

                    if(!fieldExcluds.contains(field) && type.startsWith("decimal")){
                        String lengthStrt = type.split(",")[0].split("\\(")[1];
                        if(lengthStrt.length() > 1 && new BigDecimal(lengthStrt).compareTo(minLen) > 0){

//                            System.out.println("|---------" +tbTmp + "===>" + field + " : " + type);

                            String fieldType = field +" "+ type + defaultStr;

                            fieldType = field +" "+ "decimal(20,8)" + defaultStr;

                            if(dbTbField.get(dbTmp) != null){
                                Map<String,List<String>> tbFile = dbTbField.get(dbTmp);
                                if(tbFile.get(tbTmp)!=null){
                                    tbFile.get(tbTmp).add(fieldType);
                                }else {
                                    List<String> fieldList = new ArrayList<String>();
                                    fieldList.add(fieldType);
                                    tbFile.put(tbTmp,fieldList);
                                }
                            }else{
                                List<String> fieldList = new ArrayList<String>();
                                fieldList.add(fieldType);
                                Map<String,List<String>> tbFile = new HashMap<String,List<String>>();
                                tbFile.put(tbTmp,fieldList);
                                dbTbField.put(dbTmp,tbFile);
                            }

                        }
                    }

                }
            }
        }


        for(String dbStr : dbTbField.keySet()){
//            System.out.println("|---"+dbStr);
            Map<String,List<String>> tbFile = dbTbField.get(dbStr);
            for(String tbStr : tbFile.keySet()){
//                System.out.println("|------" + tbStr);
                List<String> fields = tbFile.get(tbStr);
                for(String field : fields){
//                    System.out.println("|---------"+field);


                    System.out.println("alter table " + dbStr + "." + tbStr + " MODIFY " + field + ";");

                }
            }
        }


// show columns from tt_inst_order

    }
}
