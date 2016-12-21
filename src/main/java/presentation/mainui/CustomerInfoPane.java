package presentation.mainui;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CustomerInfoPane extends AnchorPane{
	Label customerID;
	Label name;
	Button searchButton;
	Button orderButton;
	Button bookedhotelButton;
	Button infoButton;
	Button backButton;
	
	public CustomerInfoPane(int customerid ) {
		Font icon = Font.loadFont(getClass().getResourceAsStream("fontawesome-webfont.ttf"), -1);
		this.customerID = new Label(Integer.toString(customerid));
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
		infoButton.setMinSize(width,40);
		infoButton.setMaxSize(width,40);
		info.setTranslateX(-18.0);
		infoButton.setGraphicTextGap(-5.0);
		
		backButton = new Button("退出登录");
		backButton.setId("back");
		
		Label user = new Label();
		user.setFont(Font.font(icon.getFamily(), 70));
		user.setText(String.valueOf('\uf2bd'));
		user.setId("user");
		
		VBox customerinfo = new VBox(user,customerID);
		customerinfo.setSpacing(10.0);
		//hotelID.setTranslateX(-6.0);
		VBox buttonbox = new VBox(searchButton,orderButton,bookedhotelButton,infoButton);
		buttonbox.setSpacing(5.0);
		
		this.getChildren().addAll(user,customerID,buttonbox,backButton);
		
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
		
		this.getStylesheets().add(getClass().getResource("CustomerInfoPane.css").toExternalForm());
	}

}
