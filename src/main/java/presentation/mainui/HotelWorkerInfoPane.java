package presentation.mainui;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HotelWorkerInfoPane extends AnchorPane{
	Label hotelID;
	Label name;
	Button manageButton;
	Button hotelPromotionButton;
	Button offlineButton;
	Button orderListButton;
	Button backButton;
	
	public HotelWorkerInfoPane(int hotelid) {
		Font icon = Font.loadFont(getClass().getResourceAsStream("fontawesome-webfont.ttf"), -1);
		this.hotelID = new Label(Integer.toString(hotelid));
		hotelID.setId("id");
		this.setId("pane");
		int width = 146;
		
		Label manage = new Label();
		manage.setFont(Font.font(icon.getFamily(), 26));
		manage.setText(String.valueOf('\uf040'));
		manageButton = new Button("酒店基本信息维护", manage);
		manageButton.setWrapText(true);
		manageButton.setContentDisplay(ContentDisplay.LEFT);
		manageButton.setMinSize(width, 40);
		manageButton.setMaxSize(width, 40);
		manage.setTranslateX(-6.0);
		manageButton.setGraphicTextGap(-2.0);
		
		
		Label hotelPromotion = new Label();
		hotelPromotion.setFont(Font.font(icon.getFamily(), 26));
		hotelPromotion.setText(String.valueOf('\uf044'));
		hotelPromotionButton = new Button("酒店促销策略制定", hotelPromotion);
		hotelPromotionButton.setWrapText(true);
		hotelPromotionButton.setContentDisplay(ContentDisplay.LEFT);
		hotelPromotionButton.setId("hotelPromotionButton");
		hotelPromotionButton.setMinSize(width, 40);
		hotelPromotionButton.setMaxSize(width, 40);
		hotelPromotion.setTranslateX(-7.0);
		hotelPromotionButton.setGraphicTextGap(-4.0);
		
		Label offline = new Label();
		offline.setFont(Font.font(icon.getFamily(), 26));
		offline.setText(String.valueOf('\uf0c5'));
		offlineButton = new Button("线下订单办理", offline);
		offlineButton.setWrapText(true);
		offlineButton.setContentDisplay(ContentDisplay.LEFT);
		offlineButton.setId("offline");
		offlineButton.setMinSize(width, 40);
		offlineButton.setMaxSize(width, 40);
		offline.setTranslateX(-18.0);
		offlineButton.setGraphicTextGap(-5.0);
		
		
		Label orderList = new Label();
		orderList.setFont(Font.font(icon.getFamily(), 26));
		orderList.setText(String.valueOf('\uf108'));
		orderListButton = new Button("线上订单办理", orderList);
		orderListButton.setWrapText(true);
		orderListButton.setContentDisplay(ContentDisplay.LEFT);
		orderListButton.setId("orderListButton");
		orderListButton.setMinSize(width, 40);
		orderListButton.setMaxSize(width, 40);
		orderList.setTranslateX(-18.0);
		orderListButton.setGraphicTextGap(-5.0);
		
		backButton = new Button("退出登录");
		backButton.setId("back");
		
		Label user = new Label();
		user.setFont(Font.font(icon.getFamily(), 70));
		user.setText(String.valueOf('\uf2bd'));
		user.setId("user");
		
		VBox customerinfo = new VBox(user,hotelID);
		customerinfo.setSpacing(10.0);
		//hotelID.setTranslateX(-6.0);
		VBox buttonbox = new VBox(manageButton,hotelPromotionButton,offlineButton,orderListButton);
		buttonbox.setSpacing(5.0);
		
		this.getChildren().addAll(user,hotelID,buttonbox,backButton);
		
		AnchorPane.setLeftAnchor(user, 40.0);
		AnchorPane.setTopAnchor(user, 60.0);
		AnchorPane.setLeftAnchor(hotelID, 40.0);
		AnchorPane.setTopAnchor(hotelID, 150.0);
		AnchorPane.setLeftAnchor(buttonbox, 16.0);
		AnchorPane.setBottomAnchor(buttonbox, 200.0);
		AnchorPane.setLeftAnchor(backButton, 18.0);
		AnchorPane.setBottomAnchor(backButton, 100.0);
		
		this.setMaxSize(180, 700);
		this.setMinSize(180, 700);
		this.getStylesheets().add(getClass().getResource("HotelWorkerInfoPane.css").toExternalForm());
	}

}
