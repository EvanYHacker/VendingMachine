package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

public class VendingMachine {


    //not used - should be private
//    public void setBalanceOfCustomer(double balanceOfCustomer) {
//        this.balanceOfCustomer = balanceOfCustomer;
//    }

    //INSTANCE VARIABLES
    public double balanceVendingMachine;
    public double balanceOfCustomer;
    public List<Item> inventoryList = new ArrayList<>();
    //declare a map
    public Map<String, Item> inventoryMap = new HashMap<String, Item>();
    //
    File auditFile = new File("Audit.txt");
    PrintWriter auditWriter;

    {
        try {
            auditWriter = new PrintWriter(auditFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //constructor
    public VendingMachine() {
        //read file
        File inventoryFile = new File("ExampleFiles/VendingMachine.txt");
        try {
            Scanner scannerInventoryFile = new Scanner(inventoryFile);
            while (scannerInventoryFile.hasNextLine()) {
                String line = scannerInventoryFile.nextLine();
                String[] stringArray = line.split("\\|", 0); // pipe | is a speical character, needs \\
                String locationOfItem = stringArray[0];
                String nameOfItem = stringArray[1];
                //String priceOfItem = stringArray[2];
                double priceOfItem = Double.parseDouble(stringArray[2]);
                String typeOfItem = stringArray[3];
                int soldOnSalesReport = 0;

                Item item = new Item(locationOfItem, nameOfItem, priceOfItem, typeOfItem, soldOnSalesReport);
                inventoryList.add(item);
                inventoryMap.put(locationOfItem, item);
                ///


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e.getMessage();
        }
        //inventory list
        //create an item from each line

        //add item to inventory
        // inventoryList.add();

        //balace of machine starts at 0
        balanceVendingMachine = 0;

        //balance of new customer
        balanceOfCustomer = 0;

    }

    //methods

    public String purchase(String itemLocationSlot) {

        if (!inventoryMap.containsKey(itemLocationSlot)) {
            return "Please choose a valid option";
        }
        Item selectedItem = inventoryMap.get(itemLocationSlot);
        double selectedItemPrice = selectedItem.itemPrice;
        String selectedItemType = selectedItem.itemType;

        if (inventoryMap.containsKey(itemLocationSlot)) {
            if (selectedItem.quantity < 1) {
                return "Sorry sold out";
            } else if (balanceOfCustomer < selectedItemPrice) {
                return "Insufficient funds. Please add more money. You're short by $" + String.format("%.2f", selectedItemPrice - balanceOfCustomer);
            } else {
                if (selectedItem.itemType.toLowerCase().equals("chip")) {
                    balanceOfCustomer -= selectedItemPrice;
                    balanceVendingMachine += selectedItemPrice;
                    selectedItem.quantity -= 1;
                    selectedItem.soldOnSalesReport += 1;
                    //selectedItem
                    //SALESREPORT
                    //log item name
                    //quantity sold++
                    addToAudit(selectedItem.itemName + " " + selectedItem.itemLocation, selectedItemPrice);
                    return "Crunch Crunch, Yum!" + " Your remaining balance is: $"+String.format("%.2f", balanceOfCustomer);

                } else if (selectedItem.itemType.toLowerCase().equals("candy")) {
                    balanceOfCustomer -= selectedItemPrice;
                    balanceVendingMachine += selectedItemPrice;
                    selectedItem.quantity -= 1;
                    selectedItem.soldOnSalesReport += 1;
                    addToAudit(selectedItem.itemName + " " + selectedItem.itemLocation, selectedItemPrice);
                    return "Munch Munch, Yum!"  + " Your remaining balance is: $"+String.format("%.2f", balanceOfCustomer);

                } else if (selectedItem.itemType.toLowerCase().equals("drink")) {
                    balanceOfCustomer -= selectedItemPrice;
                    balanceVendingMachine += selectedItemPrice;
                    selectedItem.quantity -= 1;
                    selectedItem.soldOnSalesReport += 1;
                    addToAudit(selectedItem.itemName + " " + selectedItem.itemLocation, selectedItemPrice);
                    return "Glug Glug, Yum!" + " Your remaining balance is: $"+String.format("%.2f", balanceOfCustomer);

                } else if (selectedItem.itemType.toLowerCase().equals("gum")) {
                    balanceOfCustomer -= selectedItemPrice;
                    balanceVendingMachine += selectedItemPrice;
                    selectedItem.quantity -= 1;
                    selectedItem.soldOnSalesReport += 1;
                    addToAudit(selectedItem.itemName + " " + selectedItem.itemLocation, selectedItemPrice);
                    return "Chew Chew, Yum!"  + " Your remaining balance is: $"+String.format("%.2f", balanceOfCustomer);
                }
                //return "Here s your "+ selectedItem.itemName;
            }
        }
        return "";
    }


    //SALRES REPORT METHOD

    public String salesReport() {

        for (Item item : inventoryList) {
            System.out.println(item.itemName + " | " + item.soldOnSalesReport);
        }
        System.out.println();
        return "**TOTAL SALES** $" + String.format("%.2f", balanceVendingMachine);
    }


    //method called .addToBalance
    //update audit

    public double addToBalance(double userFedMoney) {
        balanceOfCustomer = balanceOfCustomer + userFedMoney;
        //update audit
        //addToAudit()
        addToAudit("FEED MONEY:", userFedMoney);//create a file called audit, add time/date/money/total

        return balanceOfCustomer;
    }


    //audit method
    public void addToAudit(String function, double amount) {
        Date date = new Date();
        //format the date
        //print to file
        File auditTextFile = new File("Audit.txt");
        try (FileWriter auditFileWriter = new FileWriter(auditTextFile, true)) {
            //PrintWriter auditFileWriter = new PrintWriter("Audit.txt");
            //EXACT formatting no startnig zeros? hh dd mm
            SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy h:mm:ssaa");
            //System.out.println(formatter.format(date));
            String auditLine = formatter.format(date) + " " + function + " $" + String.format("%.2f", amount) + " $" + String.format("%.2f", balanceOfCustomer);
            auditFileWriter.append(auditLine + "\r\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return date.toString() + " " + function + " $" + String.format("%.2f",amount) + " $" + String.format("%.2f",balanceOfCustomer);
    }

//01/01/2019 12:00:15 PM FEED MONEY: $5.00 $10.00
//01/01/2019 12:00:20 PM Crunchie B4 $1.75 $8.25
//01/01/2019 12:01:25 PM Cowtales B2 $1.50 $6.75
//01/01/2019 12:01:35 PM GIVE CHANGE: $6.75 $0.00

    //GIVE CHANGE METHOD
    public String giveChange() {
        //example: double userChange = 1.65;
        int change = (int) (balanceOfCustomer * 100);
        //BigDecimal changeBD = new BigDecimal(change);
        int nickelChange = 0;
        int dimeChange = 0;
        int quarterChange = 0;

        //while(change.compareTo(new BigDecimal(.25)){

        while (change >= 25) {
            change -= 25;
            quarterChange++;
        }
        while (change >= 10) {
            change -= 10;
            dimeChange++;
        }
        while (change >= 05) {
            change -= 05;
            nickelChange++;
        }

        //local variable so balance of customer doesnt mess up the last line of audit
        double changeToGive = balanceOfCustomer;
        balanceOfCustomer = 0;
        addToAudit("GIVE CHANGE:", changeToGive);


        return "Please Take Your Change = Quarters:" + quarterChange + "  Dimes:" + dimeChange + "  Nickels:" + nickelChange;
    }
}

