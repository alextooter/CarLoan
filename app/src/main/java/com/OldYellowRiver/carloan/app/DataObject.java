package com.OldYellowRiver.carloan.app;

/**
 * Created by guoqi on 14-5-14.
 */
public class DataObject extends Object{

     public Double car_price = 0.0;
     public Double car_real_price = 0.0;
     public Integer car_type = 0;
     public Integer down_percent = 0;
     public Double down_payment = 0.0;
     public Integer loan_year = 0;
     public Double loan = 0.0;
     public Double  buy_tax = 0.0;
     public Integer insurance = 0;
     public Integer PDI = 0;
     public Integer manager_fee = 0;
     public Double total_first_pay = 0.0;
     public Double month_pay = 0.0;

    /*
    1年0.0886
    2年0.0468
    3年0.0329
    4年0.0262
    5年0.0221
     */
    Double[] pct = {
        0.0886,
        0.0468,
        0.0329,
        0.0262,
        0.0221
    };

    public DataObject() {
            car_real_price = 0.0;
            car_type = 0;
            down_percent = 0;
            down_payment = 0.0;
            loan_year = 0;
            loan = 0.0;
            buy_tax = 0.0;
            insurance = 0;
            PDI = 0;
            manager_fee = 0;
            total_first_pay = 0.0;
            month_pay = 0.0;
    }

    void clearAll () {
            car_price = 0.0;
            car_real_price = 0.0;
            car_type = 0;
            down_percent = 0;
            down_payment = 0.0;
            loan_year = 0;
            loan = 0.0;
            buy_tax = 0.0;
            insurance = 0;
            PDI = 0;
            manager_fee = 0;
            total_first_pay = 0.0;
            month_pay = 0.0;
    }

    Double calcAll () {
        //首付	车款首付金额+购置税+保险+PDI+管理费
        total_first_pay = down_payment + buy_tax + insurance + PDI + manager_fee;
        //月供	贷款*几年的利率
        month_pay = loan * pct[loan_year - 1];

        return  total_first_pay;
    }
}