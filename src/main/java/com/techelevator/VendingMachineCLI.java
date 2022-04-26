package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_OPTION_SALES_REPORT = "";
    //private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,MAIN_MENU_OPTION_EXIT };
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};
    //menu 2
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};
//
    private VendingMachine vendingMachine = new VendingMachine();
    private Item item = new Item();
    private Menu menu;
    private Map inventoryMap;

    Scanner userInput = new Scanner(System.in);

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }
    public void run() {
        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {

                for (Item item : vendingMachine.inventoryList) {
                    if (item.quantity > 0) {
                        System.out.println(item.itemLocation + "   $" + String.format("%.2f", item.itemPrice) + "   " + item.itemName); //" remaining:"+item.quantity
                    } else {
                        System.out.println(item.itemLocation + "  *SOLD OUT*   $" + String.format("%.2f", item.itemPrice) + "   " + (item.itemName));
                    }
                }
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                // do purchase

                while (true) {
                    String choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
                    if (choice2.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                        //tell the user explicitly we only accept these numbers - to avoid less user mistakes
                        System.out.println("Insert Money (This machine only accepts bills of: $1, $5, $10, $20)");
                        //what to do if user enters NaN string
                        //if !=null
                        //String userFedInput = userInput.nextLine();
                        //double userFedMoney = Double.parseDouble(userInput.nextLine());
                        //*******use REGEX?
                        String userFedMoneyStr = userInput.nextLine();
                        if (!userFedMoneyStr.matches("[0-9]+")) {
                            System.out.println("This machine only accepts bills of: $1, $5, $10, $20");
                            System.out.println("Current balance: $"+String.format("%.2f", vendingMachine.balanceOfCustomer));

                        } else {
                            double userFedMoney = Double.parseDouble(userFedMoneyStr);
                            //double userFedMoney = Double.parseDouble(userInput.nextLine());
                            //if(userFedMoney%1!=0 || userFedMoney<1 ) {
                            if (userFedMoney != 1 && userFedMoney != 5 && userFedMoney != 10 && userFedMoney != 20) {
                                System.out.println("This machine only accepts bills of: $1, $5, $10, $20");
                                System.out.println("Current balance: $"+String.format("%.2f", vendingMachine.balanceOfCustomer));
                                //do we want to accept coins
                            } else {
                                vendingMachine.addToBalance(userFedMoney);
                                System.out.println("Current balance: $" + String.format("%.2f", vendingMachine.balanceOfCustomer));
                            }
                        }
                    } else if (choice2.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                        // how to stay in this 2nd level of questions
                        //select product - we will give them it - if enough money and enough product
                        //updata their balance, update VM balance, update vm inventory
                        //2.** Select Product (check if its still available, check if you have enough $, take their money, subtract print your purchase)**don't display empty items
                        if (vendingMachine.balanceOfCustomer == 0) {
                            System.out.println("You must deposit money before making a selection");
                        } else {
                            for (Item item : vendingMachine.inventoryList) {
                                if (item.quantity > 0) {
                                    System.out.println(item.itemLocation + "   $" + String.format("%.2f", item.itemPrice) + "   " + item.itemName); //" remaining:"+item.quantity
                                } else {
                                    System.out.println(item.itemLocation + "  *SOLD OUT*   $" + String.format("%.2f", item.itemPrice) + "   " + (item.itemName));
                                }
                            }
                            System.out.println("Make your selection (example A1)");//make selection
                            String userSelection = userInput.nextLine().toUpperCase();
                            System.out.println(vendingMachine.purchase(userSelection)); //returns string
                        }
                    } else if (choice2.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                        System.out.println(vendingMachine.giveChange());
                        break;
                    }
                }
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                //exit/close the program
                break;
            }
            if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
                System.out.println(vendingMachine.salesReport());
            }
        }
    }
    //MAIN METHOD - run the program
    public static void main(String[] args) {
        File inventoryFile = new File("ExampleFilesVendingMachine.txt");
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
