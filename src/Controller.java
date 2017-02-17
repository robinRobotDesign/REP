import java.util.Scanner;

public class Controller {

	Scanner sc1 = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		OrderList list = new OrderList();
		Order a = new Order();
		a.setDrink(Drink.Soda3, 5);
		a.setDrink(Drink.Cappuccino, 2);
		a.setDrink(Drink.Espresso, 3);
		a.orderToFile();
		a = new Order();
		a.setDrink(Drink.CaffeLatte, 1);
		a.setDrink(Drink.Soda4, 3);
		a.orderToFile();
		String encodedString = OrderEncoder.buildBase(Long.valueOf(a.compactOrder()));
		makeBarcode.makeCode(encodedString, Integer.toString(a.getOrderNumber()));
		System.out.println(encodedString);
	}

}
