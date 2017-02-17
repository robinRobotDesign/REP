import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderList {
	private static ArrayList<Order> orders;
	Scanner sc;
	
	public OrderList() {
		try {
			File orderFile = new File("order.txt");
			orderFile.createNewFile();
			sc = new Scanner(orderFile);
			orders = new ArrayList<Order>();
				while(sc.hasNextLine()){
					Order order = new Order();
					String[] items = sc.nextLine().split("\\s");
					for(int i = 0;i<items.length;i++) {
						String[] drink = items[i].split(";");
						switch(drink[0]) {
						case "Soda1": order.setDrink(Drink.Soda1, Integer.parseInt(drink[1]));
							break;
						case "Soda2": order.setDrink(Drink.Soda2, Integer.parseInt(drink[1]));
							break;
						case "Soda3": order.setDrink(Drink.Soda3, Integer.parseInt(drink[1]));
							break;
						case "Soda4": order.setDrink(Drink.Soda4, Integer.parseInt(drink[1]));
							break;
						case "Coffee": order.setDrink(Drink.Coffee, Integer.parseInt(drink[1]));
							break;
						case "Tea": order.setDrink(Drink.Tea, Integer.parseInt(drink[1]));
							break;
						case "Espresso": order.setDrink(Drink.Espresso, Integer.parseInt(drink[1]));
						break;
						case "Cappuccino": order.setDrink(Drink.Cappuccino, Integer.parseInt(drink[1]));
							break;
						case "CaffeLatte": order.setDrink(Drink.CaffeLatte, Integer.parseInt(drink[1]));
							break;
						case "LatteMacchiato": order.setDrink(Drink.LatteMacchiato, Integer.parseInt(drink[1]));
							break;
						case "HotChoco": order.setDrink(Drink.HotChoco, Integer.parseInt(drink[1]));
							break;
						case "WienerMelange": order.setDrink(Drink.WienerMelange, Integer.parseInt(drink[1]));
							break;
						}
					}
					orders.add(order);
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Order> getOrders(){
		return orders;
	}
	
	public static void addOrder(Order order) {
		orders.add(order);
	}
}
