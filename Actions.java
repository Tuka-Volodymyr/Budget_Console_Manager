package budget;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Actions {
    public static Scanner scString;
    public static Scanner scNum;
    private Map<String,Float> MapOfAll;
    private Map<String,Float> MapOfFood;
    private Map<String,Float> MapOfClothes;
    private Map<String,Float> MapOfEntertainment;
    private Map<String,Float> MapOfOther;
    public static Database database;
    public File file;
    private float balance;

    public Actions(){
        file=new File("purchases.txt");
        database=new Database();
        database.createDatabase();
        scNum=new Scanner(System.in);
        scString=new Scanner(System.in);
        MapOfFood=new LinkedHashMap<>();
        MapOfClothes =new LinkedHashMap<>();
        MapOfEntertainment=new LinkedHashMap<>();
        MapOfOther=new LinkedHashMap<>();
        MapOfAll=new LinkedHashMap<>();

        balance=0;
    }
    public void getBalance() {
        System.out.printf("Balance: $%.2f",balance);
        System.out.println();
    }

    public float getTotalPrice(Map<String,Float> purchaseMap) {
        float sum=0;
        for(float value:purchaseMap.values())sum+=value;
        return sum;
    }
    public void addIncome(){
        System.out.println("Enter income:");
        float income=scNum.nextFloat();
        balance+=income;
        System.out.println("Income was added!");
    }

    public String namePurchase(){
        System.out.println("Enter purchase name:");
        return scString.nextLine();
    }
    public float price(){
        System.out.println("Enter its price:");
        float price=scNum.nextFloat();
        System.out.println("Purchase was added!");
        System.out.println();
        balance-=price;
        return price;
    }
    public void addFoodPurchase(){
        String name=namePurchase();
        float price=price();
        MapOfFood.put(name,price);
        MapOfAll.put(name,price);
    }
    public void addClothesPurchase(){
        String name=namePurchase();
        float price=price();
        MapOfClothes.put(name,price);
        MapOfAll.put(name,price);
    }
    public void addEntertainmentPurchase(){
        String name=namePurchase();
        float price=price();
        MapOfEntertainment.put(name,price);
        MapOfAll.put(name,price);
    }
    public void addOtherPurchase(){
        String name=namePurchase();
        float price=price();
        MapOfOther.put(name,price);
        MapOfAll.put(name,price);
    }

    public void outputPurchases(Map<String,Float> purchaseMap){
        if(purchaseMap.isEmpty()){
            System.out.println("The purchase list is empty");
        }else {
            purchaseMap.forEach((something,price)->System.out.printf("%s $%.2f%n",something,price));
            System.out.printf("Total sum: $%.2f%n",getTotalPrice(purchaseMap));
        }
        System.out.println();
    }
    public void outputPurchaseOfFood(){
        System.out.println("Food:");
        outputPurchases(MapOfFood);
    }
    public void outputPurchaseOfClothes(){
        System.out.println("Clothes:");
        outputPurchases(MapOfClothes);
    }
    public void outputPurchaseOfEntertainment(){
        System.out.println("Entertainment:");
        outputPurchases(MapOfEntertainment);
    }
    public void outputPurchaseOfOther(){
        System.out.println("Other:");
        outputPurchases(MapOfOther);
    }
    public void outputPurchaseOfAll(){
        System.out.println("All:");
        outputPurchases(MapOfAll);
    }
    public void save(){
        database.createTable();
        database.save(MapOfFood,"food",balance);
        database.save(MapOfClothes,"clothes",balance);
        database.save(MapOfEntertainment,"entertainment",balance);
        database.save(MapOfOther,"other",balance);
        database.save(MapOfAll,"all",balance);
        System.out.println("Purchases were saved!");
    }
    public void load(){
        MapOfFood=database.loadMap("food");
        MapOfClothes=database.loadMap("clothes");
        MapOfEntertainment=database.loadMap("entertainment");
        MapOfOther=database.loadMap("other");
        MapOfAll=database.loadMap("all");
        balance+=database.balance();
        System.out.println("Purchases were loaded!");
    }
    public void sortAllPurchases(){
        System.out.println("All:");
        sortMap(MapOfAll);
    }
    public void sortByType(){
        Map<String,Float> typeOfPurchase=new LinkedHashMap<>();
        typeOfPurchase.put("Food -",getTotalPrice(MapOfFood));
        typeOfPurchase.put("Clothes -",getTotalPrice(MapOfClothes));
        typeOfPurchase.put("Entertainment -",getTotalPrice(MapOfEntertainment));
        typeOfPurchase.put("Other -",getTotalPrice(MapOfOther));
        sortMap(typeOfPurchase);

    }
    public void sortMap(Map<String,Float> mapOf){
        LinkedHashMap<String,Float> purchaseMap=mapOf.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue,newValue)->oldValue,LinkedHashMap::new));
        outputPurchases(purchaseMap);
    }
    public void sortMapOfType(String type){
        switch (type){
            case "food"->{
                System.out.println("Food:");
                sortMap(MapOfFood);
            }
            case "clothes"->{
                System.out.println("Clothes:");
                sortMap(MapOfClothes);
            }
            case "entertainment"->{
                System.out.println("Entertainment");
                sortMap(MapOfEntertainment);
            }
            case "other"->{
                System.out.println("Other:");
                sortMap(MapOfEntertainment);
            }
        }
    }
}


