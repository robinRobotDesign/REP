
import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUIController extends Application implements Initializable {

	@FXML
	private Button newOrderBtn, prevOrderBtn, printerBtn, exitBtn, mainBtn, mainBtn2, cleanOrderBtn;
	@FXML
	private Button plusBtn, minBtn, zeroBtn, oneBtn, twoBtn, threeBtn, fourBtn, fiveBtn, sixBtn, sevenBtn, eightBtn,
			nineBtn;
	@FXML
	private Button coffeeBtn, espressoBtn, cappuccinoBtn, caffeLatteBtn, latteMacchiatoBtn, hotChocoBtn,
			wienerMelangeBtn, teaBtn, soda1Btn, soda2Btn, soda3Btn, soda4Btn;

	@FXML
	private GridPane orderDrinksGrid;

	@FXML
	private GridPane oldOrderGrid;

	@FXML
	private Label l1, l2, lb, lb2;

	private Scene scene;
	private Stage primaryStage;
	private static OrderList orderlist;
	private static Order order;
	private static Order saveOrder;
	private ArrayList<String> drinks;

	static Drink selectedDrink;
	static int drinkAmounts[];
	static String drinkAmountDigits;

	public static void main(String[] args) {
		orderlist = new OrderList();
		selectedDrink = null;
		drinkAmounts = new int[12];
		drinkAmountDigits = "";
		saveOrder = null;
		order = null;
		Application.launch(GUIController.class, (java.lang.String[]) null);
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			this.primaryStage = primaryStage;
			AnchorPane page = (AnchorPane) FXMLLoader.load(GUIController.class.getResource("Start Menu.fxml"));
			scene = new Scene(page);
			primaryStage.setScene(scene);

			primaryStage.setTitle("REP");
			primaryStage.show();
		} catch (Exception ex) {
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert newOrderBtn != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";
		assert prevOrderBtn != null : "blabla";
		assert exitBtn != null : "blablabla";
		if (oldOrderGrid != null) {
			oldOrderGrid.add(new Label("ID Bestelling"), 0, 0);
			oldOrderGrid.add(new Label("Bestelling"), 1, 0);
			int size = Math.min(11, (orderlist.getOrders().size() + 1));
			for (int i = 1; i < size; i++) {
				final int j = i;
				Label l1 = new Label(
						Integer.toString(orderlist.getOrders().get(orderlist.getOrders().size() - i).getOrderNumber()));
				Label l2 = new Label(orderlist.getOrders().get(orderlist.getOrders().size() - i).toOldOrderString());
				l1.setPrefSize(1000, 100);
				l2.setPrefSize(1000, 100);
				l1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					public void handle(final MouseEvent mouseEvent) {
						if (lb != null && lb2 != null) {
							lb.setStyle("-fx-background-color: transparent;");
							lb2.setStyle("-fx-background-color: transparent;");
						}
						lb = l1;
						lb2 = l2;
						order = orderlist.getOrders().get(orderlist.getOrders().size() - j);
						lb.setStyle("-fx-background-color: #d3d3d3;");
						lb2.setStyle("-fx-background-color: #d3d3d3;");
					}
				});
				l2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					public void handle(final MouseEvent mouseEvent) {
						if (lb != null && lb2 != null) {
							lb.setStyle("-fx-background-color: transparent;");
							lb2.setStyle("-fx-background-color: transparent;");
						}
						lb = l1;
						lb2 = l2;
						order = orderlist.getOrders().get(orderlist.getOrders().size() - j);
						lb.setStyle("-fx-background-color: #d3d3d3;");
						lb2.setStyle("-fx-background-color: #d3d3d3;");
					}
				});
				oldOrderGrid.add(l1, 0, i);
				oldOrderGrid.add(l2, 1, i);
			}
		}
		if (orderDrinksGrid != null) {
			Label cons = new Label("Consumptie");
			Label amount = new Label("Aantal");
			cons.setId("cons");
			amount.setId("amount");
			for (int i = 0; i < Drink.values().length; i++) {
				TextField text1 = new TextField("");
				text1.setId("tfa" + i);
				TextField text2 = new TextField("");
				text2.setId("tfb" + i);
				orderDrinksGrid.add(text1, 0, i + 1);
				orderDrinksGrid.add(text2, 1, i + 1);
			}
		}

	}

	@FXML
	private void handlePrinterAction(ActionEvent event) throws IOException {
		if (order != null) {
			order.orderToFile();
			String encodedString = OrderEncoder.buildBase(Long.valueOf(order.compactOrder()));
			makeBarcode.makeCode(encodedString, Integer.toString(order.getOrderNumber()));

			Image image = ImageIO.read(new File("combined.png"));
			PrinterService printerService = new PrinterService();
			PageFormat pf = new PageFormat();
			Paper p = new Paper();
			p.setSize(50*200, 50*200);
			p.setImageableArea(0, 0, 50*200, 50*200);
			pf.setPaper(p);
			Graphics graphics = image.getGraphics();
			try {
				printerService.print(graphics, pf, 0);
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			PrinterJob printJob = PrinterJob.getPrinterJob();
//			printJob.setPrintable(new Printable() {
		/*		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
					if (pageIndex != 0) {
						return NO_SUCH_PAGE;
					}
					return PAGE_EXISTS;
				}
			});*/
		/*	if(printJob.printDialog()){
				try {
					printJob.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}*/
			order = new Order();
			selectedDrink = null;
			saveOrder = null;
			clearTable();

		}
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Scene sc = null;
		if (event.getSource() == cleanOrderBtn) {
			for (Drink drink : Drink.values()) {
				selectedDrink = drink;
				order.setDrink(selectedDrink, 0);
				clearTable();
			}
			selectedDrink = null;
		} else {
			if (event.getSource() == newOrderBtn) {
				if (saveOrder != null) {
					order = saveOrder;
					saveOrder = null;
				} else {
					order = new Order();
				}
				System.out.println(order);
				primaryStage = (Stage) newOrderBtn.getScene().getWindow();
				AnchorPane page2 = (AnchorPane) FXMLLoader.load(GUIController.class.getResource("New Order.fxml"));
				sc = new Scene(page2);
			} else if (event.getSource() == prevOrderBtn) {
				primaryStage = (Stage) prevOrderBtn.getScene().getWindow();
				AnchorPane page3 = (AnchorPane) FXMLLoader.load(GUIController.class.getResource("OldOrders.fxml"));
				sc = new Scene(page3);
			} else if (event.getSource() == exitBtn) {
				Platform.exit();
				System.exit(0);
			} else if (event.getSource() == mainBtn) {
				primaryStage = (Stage) mainBtn.getScene().getWindow();
				AnchorPane page1 = (AnchorPane) FXMLLoader.load(GUIController.class.getResource("Start Menu.fxml"));
				sc = new Scene(page1);
			} else if (event.getSource() == mainBtn2) {
				saveOrder = order;
				primaryStage = (Stage) mainBtn2.getScene().getWindow();
				AnchorPane page1 = (AnchorPane) FXMLLoader.load(GUIController.class.getResource("Start Menu.fxml"));
				sc = new Scene(page1);
			}

			primaryStage.setScene(sc);
			primaryStage.show();
		}
	}

	@FXML
	private void updateTable() {
		if (order.getDrinkValue(selectedDrink) > 0) {
			ArrayList<TextField> tf = findEmptyGroup(selectedDrink.toString());
			if (tf.get(0).getCharacters().toString().isEmpty()) {
				tf.get(0).appendText(selectedDrink.toString());
			}
			tf.get(1).clear();
			tf.get(1).appendText(Integer.toString(order.getDrinkValue(selectedDrink)));
		} else {
			ArrayList<TextField> tf = findEmptyGroup(selectedDrink.toString());
			tf.get(0).clear();
			tf.get(1).clear();

		}
	}

	@FXML
	private void clearTable() {
		//updateTable();
		ObservableList<Node> list = orderDrinksGrid.getChildren();
		for (int i = 0; i < orderDrinksGrid.getChildren().size(); i++) {
			if (list.get(i) instanceof TextField) {
				TextField tf = (TextField) list.get(i);
				tf.clear();
			}
		}
	}

	@FXML
	private void handleKeyPressedAction(KeyEvent event) throws IOException {
		String number = event.getText();
		System.out.println(number);
		if (selectedDrink != null) {
			switch (number) {
			case "0":
				order.setDrink(selectedDrink, 0);
				updateTable();
				break;
			case "1":
				order.setDrink(selectedDrink, 1);
				updateTable();
				break;
			case "2":
				order.setDrink(selectedDrink, 2);
				updateTable();
				break;
			case "3":
				order.setDrink(selectedDrink, 3);
				updateTable();
				break;
			case "4":
				order.setDrink(selectedDrink, 4);
				updateTable();
				break;
			case "5":
				order.setDrink(selectedDrink, 5);
				updateTable();
				break;
			case "6":
				order.setDrink(selectedDrink, 6);
				updateTable();
				break;
			case "7":
				order.setDrink(selectedDrink, 7);
				updateTable();
				break;
			case "8":
				order.setDrink(selectedDrink, 8);
				updateTable();
				break;
			case "9":
				order.setDrink(selectedDrink, 9);
				updateTable();
				break;
			case "+":
				order.incrementDrink(selectedDrink);
				updateTable();
				break;
			case "-":
				order.decrementDrink(selectedDrink);
				updateTable();
				break;
			}
		}
	}

	@FXML
	private void handleItem(int number) {
		if(selectedDrink != null){
		order.setDrink(selectedDrink, number);
		updateTable();
		}
	}
	
	@FXML
	private void handleOrderButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == coffeeBtn) {
			selectedDrink = Drink.Coffee;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == espressoBtn) {
			selectedDrink = Drink.Espresso;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == cappuccinoBtn) {
			selectedDrink = Drink.Cappuccino;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == caffeLatteBtn) {
			selectedDrink = Drink.CaffeLatte;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == latteMacchiatoBtn) {
			selectedDrink = Drink.LatteMacchiato;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == hotChocoBtn) {
			selectedDrink = Drink.HotChoco;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == wienerMelangeBtn) {
			selectedDrink = Drink.WienerMelange;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == teaBtn) {
			selectedDrink = Drink.Tea;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == soda1Btn) {
			selectedDrink = Drink.Soda1;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == soda2Btn) {
			selectedDrink = Drink.Soda2;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == soda3Btn) {
			selectedDrink = Drink.Soda3;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == soda4Btn) {
			selectedDrink = Drink.Soda4;
			order.incrementDrink(selectedDrink);
			updateTable();
		} else if (event.getSource() == plusBtn) {
			if(selectedDrink!= null) {
				order.incrementDrink(selectedDrink);
				updateTable();
			}
		} else if (event.getSource() == minBtn) {
			if(selectedDrink!= null){
			order.decrementDrink(selectedDrink);
			updateTable();
			}
		} else if (event.getSource() == zeroBtn) {
			handleItem(0);
		} else if (event.getSource() == oneBtn) {
			handleItem(1);
		} else if (event.getSource() == twoBtn) {
			handleItem(2);
		} else if (event.getSource() == threeBtn) {
			handleItem(3);
		} else if (event.getSource() == fourBtn) {
			handleItem(4);
		} else if (event.getSource() == fiveBtn) {
			handleItem(5);
		} else if (event.getSource() == sixBtn) {
			handleItem(6);
		} else if (event.getSource() == sevenBtn) {
			handleItem(7);
		} else if (event.getSource() == eightBtn) {
			handleItem(8);
		} else if (event.getSource() == nineBtn) {
			handleItem(9);
		} else if (event.getSource() == printerBtn) {
			handlePrinterAction(event);
		}
	}
	/*
	 * private void setDrinkAmounts(int a) { if (drinkAmountDigits.length() < 2)
	 * { drinkAmountDigits += Integer.toString(a); } }
	 */

	private ArrayList<TextField> findEmptyGroup(String drink) {
		ArrayList<TextField> values = new ArrayList<>();
		ObservableList<Node> list = orderDrinksGrid.getChildren();
		for (int i = 0; i < orderDrinksGrid.getChildren().size(); i++) {
			if (list.get(i) instanceof TextField) {
				TextField a = (TextField) list.get(i);
				if (a.getCharacters().length() < 1
						&& (!(list.get(i).getId() == null) && list.get(i).getId().contains("tfa"))) {
					values.add((TextField) list.get(i));
					values.add((TextField) list.get(i + 1));
					break;
				} else if (a.getCharacters().toString().contains(drink) && list.get(i).getId().contains("tfa")) {
					values.add((TextField) list.get(i));
					values.add((TextField) list.get(i + 1));
					break;
				}
			}
		}
		return values;

	}
}