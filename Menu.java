package budget;
import static budget.Actions.database;
import static budget.Actions.scString;
public class Menu {
    public Actions actions=new Actions();
    boolean run=true;
    public void mainMenu(){
        while (run){
            System.out.println("""
                    Choose your action:
                    1) Add income
                    2) Add purchase
                    3) Show list of purchases
                    4) Balance
                    5) Save
                    6) Load
                    7) Analyze (Sort)
                    0) Exit""");
            String choose=scString.nextLine();
            System.out.println();
            switch (choose){
                case "0"->{
                    database.closeDB();
                    System.out.println("Bye!");
                    run=false;
                }
                case "1"->actions.addIncome();
                case "2"->menuOfAddPurchase();
                case "3"->menuOFOutputPurchases();
                case "4"->actions.getBalance();
                case "5"->saveData();
                case "6"->actions.load();
                case "7"->menuOfAnalyze();
            }
            System.out.println();
        }
    }
    public void saveData(){
        System.out.println("""
                1) Delete old purchase and add new!
                2) Add new purchase to old!""");
        String choose=scString.nextLine();
        System.out.println();
        switch (choose){
            case "1"->{
                database.deleteDataInTables();
                actions.save();
            }
            case "2"->actions.save();
        }
    }
    public void menuOfAddPurchase(){
        while (run){
            System.out.println("""
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) Back""");
            String choose=scString.nextLine();
            System.out.println();
            switch (choose){
                case "1"->actions.addFoodPurchase();
                case "2"->actions.addClothesPurchase();
                case "3"->actions.addEntertainmentPurchase();
                case "4"->actions.addOtherPurchase();
                case "5"->mainMenu();
            }
        }
    }
    public void menuOFOutputPurchases(){
        while (run){
            System.out.println("""
                Choose the type of purchases
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) All
                6) Back""");
            String choose=scString.nextLine();
            System.out.println();
            switch (choose){
                case "1"->actions.outputPurchaseOfFood();
                case "2"->actions.outputPurchaseOfClothes();
                case "3"->actions.outputPurchaseOfEntertainment();
                case "4"->actions.outputPurchaseOfOther();
                case "5"->actions.outputPurchaseOfAll();
                case "6"->mainMenu();
            }
        }
    }
    public void menuOfAnalyze(){
        while (run){
            System.out.println("""
                How do you want to sort?
                1) Sort all purchases
                2) Sort by type
                3) Sort certain type
                4) Back""");
            String choose=scString.nextLine();
            System.out.println();
            switch (choose){
                case "1"->actions.sortAllPurchases();
                case "2"->actions.sortByType();
                case "3"->chooseTypeOfPurchase();
                case "4"->mainMenu();
            }
        }
    }
    public void chooseTypeOfPurchase(){
        System.out.println("""
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other""");
        String choose=scString.nextLine();
        System.out.println();
        switch (choose){
            case "1"->actions.sortMapOfType("food");
            case "2"->actions.sortMapOfType("clothes");
            case "3"->actions.sortMapOfType("entertainment");
            case "4"->actions.sortMapOfType("other");
        }
    }
}

