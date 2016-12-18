package presentation.mainui;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import presentation.customerui.CustomerInfoPane;
import presentation.hotelui.AreaInputPane;
import presentation.hotelui.BookedHotelPane;
import presentation.orderui.CustomerOrdersPane;

/**
 * @author samperson1997
 * 客户主界面
 *
 */
public class CustomerMainPane extends AnchorPane {

	private int customerID;

	public CustomerMainPane(int customerID) {
		super();
		this.customerID = customerID;
		int r =200;
		int dim = 130;
		Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);

		Label search = new Label();
		search.setFont(Font.font(icon.getFamily(), dim));
		search.setText(String.valueOf('\uf002'));
		Button searchButton = new Button("搜索酒店", search);
		searchButton.setWrapText(true);
		searchButton.setContentDisplay(ContentDisplay.TOP);
		searchButton.setId("searchButton");
		searchButton.setMinSize(r, r);
		searchButton.setMaxSize(r, r);
		
		Label order = new Label();
		order.setFont(Font.font(icon.getFamily(), dim));
		order.setText(String.valueOf('\uf03a'));
		Button orderListButton = new Button("我的订单", order);
		orderListButton.setWrapText(true);
		orderListButton.setContentDisplay(ContentDisplay.TOP);
		orderListButton.setId("orderListButton");
		orderListButton.setMinSize(r, r);
		orderListButton.setMaxSize(r,r);
		
		Label hotel = new Label();
		hotel.setFont(Font.font(icon.getFamily(), dim));
		hotel.setText(String.valueOf('\uf015'));
		Button hotelListButton = new Button("预订过的酒店", hotel);
		hotelListButton.setWrapText(true);
		hotelListButton.setContentDisplay(ContentDisplay.TOP);
		hotelListButton.setId("hotelListButton");
		hotelListButton.setMinSize(r, r);
		hotelListButton.setMaxSize(r, r);
		
		Label info = new Label();
		info.setFont(Font.font(icon.getFamily(), dim));
		info.setText(String.valueOf('\uf2bc'));
		Button infoButton = new Button("我的信息", info);
		infoButton.setWrapText(true);
		infoButton.setContentDisplay(ContentDisplay.TOP);
		infoButton.setId("infoButton");
		infoButton.setMinSize(r, r);
		infoButton.setMaxSize(r,r);
		
		
		Label back = new Label();
		back.setFont(Font.font(icon.getFamily(), 45));
		back.setText(String.valueOf('\uf011'));
		Button logoutButton = new Button("注销", back);
		logoutButton.setWrapText(true);
		logoutButton.setContentDisplay(ContentDisplay.TOP);
		logoutButton.setId("backButton");
		logoutButton.setShape(new Circle(42));
		logoutButton.setMinSize(84, 84);
		logoutButton.setMaxSize(84, 84);
		
		this.getChildren().addAll(searchButton, orderListButton, hotelListButton, infoButton, logoutButton);

		AnchorPane.setLeftAnchor(searchButton, 250.0);
		AnchorPane.setTopAnchor(searchButton, 100.0);
		AnchorPane.setLeftAnchor(orderListButton, 600.0);
		AnchorPane.setTopAnchor(orderListButton, 100.0);
		AnchorPane.setLeftAnchor(hotelListButton, 250.0);
		AnchorPane.setTopAnchor(hotelListButton, 400.0);
		AnchorPane.setLeftAnchor(infoButton, 600.0);
		AnchorPane.setTopAnchor(infoButton, 400.0);
		AnchorPane.setLeftAnchor(logoutButton, 985.0);
		AnchorPane.setTopAnchor(logoutButton, 30.0);

		searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.jumpTo(new AreaInputPane(customerID));
		});

		orderListButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new CustomerOrdersPane(customerID));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		hotelListButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new BookedHotelPane(customerID));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		infoButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new CustomerInfoPane(customerID));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		
		Timer surviveTimer = new Timer(true);
		surviveTimer.schedule(new SurvivalTast(), 1, 1000);

		this.getStylesheets().add(getClass().getResource("customerMainPane.css").toExternalForm());

	}
	
	
	/**
	 * @author samperson1997
	 * 提醒服务器端此账号仍然登陆
	 *
	 */
	public class SurvivalTast extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				BLFactory.getInstance().getUserBLService().survivalConfirm(customerID);
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
