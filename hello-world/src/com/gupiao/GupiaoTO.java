package com.gupiao;
import java.sql.Timestamp;
/**
 * Created by yejun on 18/1/1.
 */
public class GupiaoTO {
    private String stockCode; //股票代码
    private String stockName; //股票名称
    private String transDate; //交易日
    private Timestamp modifiedTime; //最后修改时间
    private String present_price; //现价

    private String building_price; //建仓价
    private String adding_price; //加仓价
    private String heavy_price; //重仓价

    private String present_building; // '现价/建仓价'
    private String present_adding; // '现价/加仓价'
    private String present_heavy; // '现价/重仓价'

    private String memo; //备注

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getPresent_price() {
        return present_price;
    }

    public void setPresent_price(String present_price) {
        this.present_price = present_price;
    }

    public String getBuilding_price() {
        return building_price;
    }

    public void setBuilding_price(String building_price) {
        this.building_price = building_price;
    }

    public String getAdding_price() {
        return adding_price;
    }

    public void setAdding_price(String adding_price) {
        this.adding_price = adding_price;
    }

    public String getHeavy_price() {
        return heavy_price;
    }

    public void setHeavy_price(String heavy_price) {
        this.heavy_price = heavy_price;
    }

    public String getPresent_building() {
        return present_building;
    }

    public void setPresent_building(String present_building) {
        this.present_building = present_building;
    }

    public String getPresent_adding() {
        return present_adding;
    }

    public void setPresent_adding(String present_adding) {
        this.present_adding = present_adding;
    }

    public String getPresent_heavy() {
        return present_heavy;
    }

    public void setPresent_heavy(String present_heavy) {
        this.present_heavy = present_heavy;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
