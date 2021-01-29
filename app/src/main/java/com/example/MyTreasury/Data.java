package com.example.MyTreasury;

public class Data {

    public String id;

    //for Transaction
    public String date;
    public String cause;
    public Double amount;
    public String currency;
    public String type;
    public String state;
    public String payer;
    public String cat;
    public String subcat;
    public String comments;
    public String img_id_str;
    public String balance;

    //for Categories and members
    public String noun;


    //for Account Info
    public String assoName, school, purpose, link, status;


    //Data object for expenses

    public Data(String id, String date, String cause, double amount, String currency, String type,
                String state, String payer, String cat, String subcat, String comments, String img_id_str) {

        this.id=id;
        this.date=date;
        this.cause=cause;
        this.amount=amount;
        this.currency=currency;
        this.type=type;
        this.state=state;
        this.payer=payer;
        this.cat=cat;
        this.subcat=subcat;
        this.comments=comments;
        this.img_id_str=img_id_str;

        if (type.equals("Debit")){
            this.amount = 0-amount;
        }
        else if (type.equals("Credit")) {
            this.amount = 0+amount;
        }
        else {
            this.amount=amount;
        }

    }


    //data object for AccountInfo

    public Data(String id, String date, String assoName, String school, String type, String purpose, String link,
                                String status) {

        this.id=id;
        this.date=date;
        this.assoName=assoName;
        this.school=school;
        this.type=type;
        this.purpose=purpose;
        this.link=link;
        this.status=status;


    }

    //data object for categories and members

    public Data(String id, String noun) {
        this.id=id;
        this.noun=noun;
    }

    public String getNoun() {
        return noun;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImg_id_str() {
        return img_id_str;
    }

    public void setImg_id_str(String img_id_str) {
        this.img_id_str = img_id_str;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Data(){}
}

