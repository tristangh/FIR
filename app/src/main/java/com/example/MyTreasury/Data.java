package com.example.MyTreasury;

public class Data {

    String id;
    String date;
    String cause;
    int balance;
    String currency;
    String type;
    String state;
    String payer;
    String cat;
    String subcat;
    String comments;
    String img_id_str;
    String amount;

    public Data(String id, String date, String cause, int balance, String currency, String type,
                String state, String payer, String cat, String subcat, String comments, String img_id_str) {

        this.id=id;
        this.date=date;
        this.cause=cause;
        this.balance=balance;
        this.currency=currency;
        this.type=type;
        this.state=state;
        this.payer=payer;
        this.cat=cat;
        this.subcat=subcat;
        this.comments=comments;
        this.img_id_str=img_id_str;

        String sign;
        if (type.equals("Debit")){
            sign = "+";
        }
        else if (type.equals("Credit")) {
            sign = "-";
        }
        else {
            sign="";
        }

        this.amount=sign+balance+" "+currency;
    }

}
