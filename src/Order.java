import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
	private static int currentOrder = 0;
	private int orderID;
	private Map<Drink, Integer> orderList;

	public Order() {
		orderList = new LinkedHashMap<Drink, Integer>();
		for (Drink drink : Drink.values()) {
			orderList.put(drink, 0);
		}
		orderID = currentOrder;
		currentOrder = currentOrder + 1;
	}

	public void incrementDrink(Drink drink) {
		if(orderList.get(drink) <9){
		orderList.replace(drink, orderList.get(drink) + 1);
		}
	}

	public void decrementDrink(Drink drink) {
		if (orderList.get(drink) > 1) {
			orderList.replace(drink, orderList.get(drink) - 1);
		}
	}

	public void incrementDrink(Drink drink, int amount) {
		if (orderList.get(drink) <9) {
			orderList.replace(drink, orderList.get(drink) + amount);
		}
	}

	public void setDrink(Drink drink, int amount) {
		orderList.replace(drink, amount);

	}

	public String toString() {
		StringBuilder orderString = new StringBuilder();
		orderString.append("OrderID " + orderID + "\n");
		for (Map.Entry<Drink, Integer> order : orderList.entrySet()) {
			orderString.append(order.getKey() + " " + order.getValue() + "\n");
		}
		return orderString.toString();
	}

	public String toOldOrderString() {
		StringBuilder orderString = new StringBuilder();
		orderString.append("");
		for (Map.Entry<Drink, Integer> order : orderList.entrySet()) {
			if (order.getValue() > 0) {
				orderString.append(order.getKey() + " " + order.getValue() + " ");
			}
		}
		return orderString.toString();
	}

	public int getDrinkValue(Drink drink) {
		return orderList.get(drink);
	}

	public void orderToFile() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("Order.txt", true));
			StringBuilder fileOrder = new StringBuilder();
			fileOrder.append(orderID + " ");
			for (Map.Entry<Drink, Integer> order : orderList.entrySet()) {
				fileOrder.append(order.getKey() + ";" + order.getValue() + " ");
			}
			fileOrder.append("\n");
			pw.append(fileOrder);
			pw.flush();
			pw.close();
			OrderList.addOrder(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long compactOrder() {
		StringBuilder compactOrder = new StringBuilder();
		compactOrder.append(orderID);
		for (Map.Entry<Drink, Integer> order : orderList.entrySet()) {
			compactOrder.append(order.getValue());
		}
		long a = Long.parseLong(compactOrder.toString());
		return a;
	}

	public int getOrderNumber() {
		return this.orderID;
	}
}