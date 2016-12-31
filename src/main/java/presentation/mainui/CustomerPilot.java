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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import presentation.customerui.CustomerInfoPane;
import presentation.hotelui.AreaInputPane;
import presentation.hotelui.BookedHotelPane;
import presentation.orderui.CustomerOrdersPane;

/**
 * @author samperson1997 
 * 客户导航
 *
 */
public class CustomerPilot extends AnchorPane {

	private Label customerID;
	private Label name;
	private Button searchButton;
	private Button orderButton;
	private Button bookedhotelButton;
	private Button infoButton;
	private Button backButton;
	private int ID;

	/**
	 * @param customerid
	 * 客户导航
	 */
	public CustomerPilot(int customerid) {

		Font icon = Font.loadFont(getClass().getResourceAsStream("fontawesome-webfont.ttf"), -1);
		this.customerID = new Label(Integer.toString(customerid));
		this.ID = customerid;
		customerID.setId("id");
		this.setId("pane");
		int width = 146;

		Label search = new Label();
		search.setFont(Font.font(icon.getFamily(), 26));
		search.setText(String.valueOf('\uf002'));
		searchButton = new Button("搜索酒店", search);
		searchButton.setWrapText(true);
		searchButton.setContentDisplay(ContentDisplay.LEFT);
		searchButton.setId("searchButton");
		searchButton.setMinSize(width, 40);
		searchButton.setMaxSize(width, 40);
		search.setTranslateX(-18.0);
		searchButton.setGraphicTextGap(-5.0);

		Label order = new Label();
		order.setFont(Font.font(icon.getFamily(), 26));
		order.setText(String.valueOf('\uf03a'));
		orderButton = new Button("我的订单", order);
		orderButton.setWrapText(true);
		orderButton.setContentDisplay(ContentDisplay.LEFT);
		orderButton.setId("orderButton");
		orderButton.setMinSize(width, 40);
		orderButton.setMaxSize(width, 40);
		order.setTranslateX(-18.0);
		orderButton.setGraphicTextGap(-5.0);

		Label hotel = new Label();
		hotel.setFont(Font.font(icon.getFamily(), 26));
		hotel.setText(String.valueOf('\uf015'));
		bookedhotelButton = new Button("预订过的酒店", hotel);
		bookedhotelButton.setWrapText(true);
		bookedhotelButton.setContentDisplay(ContentDisplay.LEFT);
		bookedhotelButton.setId("hotelButton");
		bookedhotelButton.setMinSize(width, 40);
		bookedhotelButton.setMaxSize(width, 40);
		hotel.setTranslateX(-5.0);
		bookedhotelButton.setGraphicTextGap(-1.0);

		Label info = new Label();
		info.setFont(Font.font(icon.getFamily(), 26));
		info.setText(String.valueOf('\uf2bc'));
		infoButton = new Button("我的信息", info);
		infoButton.setWrapText(true);
		infoButton.setContentDisplay(ContentDisplay.LEFT);
		infoButton.setId("hotelButton");
		infoButton.setMinSize(width, 40);
		infoButton.setMaxSize(width, 40);
		info.setTranslateX(-18.0);
		infoButton.setGraphicTextGap(-5.0);

		backButton = new Button("退出登录");
		backButton.setId("back");

		Label user = new Label();
		user.setFont(Font.font(icon.getFamily(), 70));
		user.setText(String.valueOf('\uf2bd'));
		user.setId("user");

		VBox customerinfo = new VBox(user, customerID);
		customerinfo.setSpacing(10.0);
		// hotelID.setTranslateX(-6.0);
		VBox buttonbox = new VBox(searchButton, orderButton, bookedhotelButton, infoButton);
		buttonbox.setSpacing(5.0);

		this.getChildren().addAll(user, customerID, buttonbox, backButton);

		AnchorPane.setLeftAnchor(user, 40.0);
		AnchorPane.setTopAnchor(user, 60.0);
		AnchorPane.setLeftAnchor(customerID, 40.0);
		AnchorPane.setTopAnchor(customerID, 150.0);
		AnchorPane.setLeftAnchor(buttonbox, 16.0);
		AnchorPane.setBottomAnchor(buttonbox, 200.0);
		AnchorPane.setLeftAnchor(backButton, 18.0);
		AnchorPane.setBottomAnchor(backButton, 100.0);

		this.setMaxSize(180, 700);
		this.setMinSize(180, 700);

		searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new AreaInputPane(customerid));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		orderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new CustomerOrdersPane(customerid));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		bookedhotelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new BookedHotelPane(customerid));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		infoButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new CustomerInfoPane(customerid));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.changeTo(new LoginPane());
			TheMainFrame.setLoginID(-1);
		});

		

		this.getStylesheets().add(getClass().getResource("CustomerInfoPane.css").toExternalForm());
	}

	
}
