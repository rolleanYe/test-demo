package com.gupiao;

import com.mysql.jdbc.Connection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Created by yejun on 18/1/1.
 */
public class GuPiaoDao {

    public GupiaoTO getByCode(String code) throws Exception{
        GupiaoTO to = new GupiaoTO();

        Connection conn = JDBCUtils.getConnection();

        String sql = "select * from zhuhe.t_gupiao where stock_code = ? ";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,code);
        ResultSet rs = statement.executeQuery();
        int count = 0;
        while(rs.next())  {
            count ++;
            String stockCode = rs.getString("stock_code");
            String stockName = rs.getString("stock_name");
            String transDate = rs.getString("trans_date");
            Timestamp gmtModified = rs.getTimestamp("GMT_MODIFIED");
            String presentPrice = rs.getString("present_price");
            String buildingPrice = rs.getString("building_price");
            String addingPrice = rs.getString("adding_price");
            String heavyPrice = rs.getString("heavy_price");
            String presentBuilding = rs.getString("present_building");
            String presentAdding = rs.getString("present_adding");
            String presentHeavy = rs.getString("present_heavy");
            String memo = rs.getString("memo");

            to.setStockCode(stockCode);
            to.setStockName(stockName);
            to.setTransDate(transDate);
            to.setModifiedTime(gmtModified);
            to.setPresent_price(presentPrice);
            to.setBuilding_price(buildingPrice);
            to.setAdding_price(addingPrice);
            to.setHeavy_price(heavyPrice);
            to.setPresent_building(presentBuilding);
            to.setPresent_adding(presentAdding);
            to.setPresent_heavy(presentHeavy);
            to.setMemo(memo);
            return to;
        }

        if(count == 0 ){
            return null;
        }

        JDBCUtils.close(conn,statement,rs);
        return to;
    }


    public void updateGupiao(GupiaoTO to) throws Exception{

        String presentPrice = to.getPresent_price();
        String transDate = to.getTransDate();

        Connection conn = JDBCUtils.getConnection();

        String sql = "UPDATE zhuhe.t_gupiao set present_price=?,trans_date=?,GMT_MODIFIED=?,present_building=?,present_adding=?,present_heavy=?,memo=? where stock_code = ? ";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,presentPrice);
        statement.setString(2,transDate);
        statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

        statement.setString(4,new BigDecimal(presentPrice).divide(new BigDecimal(to.getBuilding_price()),4,BigDecimal.ROUND_HALF_UP).toString());
        statement.setString(5,new BigDecimal(presentPrice).divide(new BigDecimal(to.getAdding_price()),4,BigDecimal.ROUND_HALF_UP).toString());
        statement.setString(6,new BigDecimal(presentPrice).divide(new BigDecimal(to.getHeavy_price()),4,BigDecimal.ROUND_HALF_UP).toString());

        if(new BigDecimal(presentPrice).divide(new BigDecimal(to.getBuilding_price()),4,BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal("1")) <= 0){
            statement.setString(7,"建仓");
        }else {
            statement.setString(7,"观察");
        }

        statement.setString(8,to.getStockCode());

        statement.executeUpdate();

    }



}
