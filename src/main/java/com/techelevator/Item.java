package com.techelevator;

public class Item {

    public String itemLocation;
    public String itemName;
    public double itemPrice;
    public String itemType; //beverage,candy,chips,gum
    public int quantity;
    public int soldOnSalesReport;

    public int getSoldOnSalesReport() {
        return soldOnSalesReport;
    }

//to string method - CLI 43-48ish



    public String getItemLocation() {
        return itemLocation;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getItemType() {
        return itemType;
    }


    //constructor
    public Item(){

    }

    public Item(String itemLocation, String itemName,double itemPrice, String itemType, int soldOnSalesReport){
        this.itemLocation=itemLocation;
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.itemType=itemType;
        this.quantity=5;
        this.soldOnSalesReport=soldOnSalesReport;
    }

    //methods

}
