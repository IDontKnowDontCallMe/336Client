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
import presentation.orderui.MarketerOrdersPane;
import presentation.promotionui.LevelPanel;
import presentation.promotionui.WebPromotionPanel;
import presentation.userui.CustomerCreditPanel;

/**
 * @author samperson1997 
 * 网站营销人员导航栏
 *
 */
public class WebMarketerPilot extends AnchorPane {

	private Label webMarketerID;
	private Label name;
	private Button webPromotionButton;
	private Button levelButton;
	private Button marketerOrderButton;
	private Button creditButton;
	private Button backButton;
	
	private int ID;

	public WebMarketerPilot(int id) {
		Font icon = Font.loadFont(getClass().getResourceAsStream("fontawesome-webfont.ttf"), -1);
		this.webMarketerID = new Label(Integer.toString(id));
		webMarketerID.setId("id");
		this.setId("pane");
		int width = 146;
		this.ID = id;

		Label webPromotion = new Label();
		webPromotion.setFont(Font.font(icon.getFamily(), 26));
		webPromotion.setText(String.valueOf('\uf044'));
		webPromotionButton = new Button("网站促销策略制定", webPromotion);
		webPromotionButton.setWrapText(true);
		webPromotionButton.setContentDisplay(ContentDisplay.LEFT);
		webPromotionButton.setId("searchButton");
		webPromotionButton.setMinSize(width, 40);
		webPromotionButton.setMaxSize(width, 40);
		webPromotion.setTranslateX(-5.0);
		webPromotionButton.setGraphicTextGap(-4.0);

		Label level = new Label();
		level.setFont(Font.font(icon.getFamily(), 26));
		level.setText(String.valueOf('\uf0cb'));
		levelButton = new Button("等级策略制定", level);
		levelButton.setWrapText(true);
		levelButton.setContentDisplay(ContentDisplay.LEFT);
		levelButton.setId("levelButton");
		levelButton.setMinSize(width, 40);
		levelButton.setMaxSize(width, 40);
		level.setTranslateX(-18.0);
		levelButton.setGraphicTextGap(-5.0);

		Label marketerOrder = new Label();
		marketerOrder.setFont(Font.font(icon.getFamily(), 26));
		marketerOrder.setText(String.valueOf('\uf0ea'));
		marketerOrderButton = new Button("异常订单处理", marketerOrder);
		marketerOrderButton.setWrapText(true);
		marketerOrderButton.setContentDisplay(ContentDisplay.LEFT);
		marketerOrderButton.setId("hotelButton");
		marketerOrderButton.setMinSize(width, 40);
		marketerOrderButton.setMaxSize(width, 40);
		marketerOrder.setTranslateX(-15.0);
		marketerOrderButton.setGraphicTextGap(-5.0);

		Label credit = new Label();
		credit.setFont(Font.font(icon.getFamily(), 26));
		credit.setText(String.valueOf('\uf283'));
		creditButton = new Button("信用充值", credit);
		creditButton.setWrapText(true);
		creditButton.setContentDisplay(ContentDisplay.LEFT);
		creditButton.setId("hotelButton");
		creditButton.setMinSize(width, 40);
		creditButton.setMaxSize(width, 40);
		credit.setTranslateX(-28.0);
		creditButton.setGraphicTextGap(-10.0);


		backButton = new Button("退出登录");
		backButton.setId("back");

		Label user = new Label();
		user.setFont(Font.font(icon.getFamily(), 70));
		user.setText(String.valueOf('\uf2bd'));
		user.setId("user");

		VBox customerinfo = new VBox(user, webMarketerID);
		customerinfo.setSpacing(10.0);
		// hotelID.setTranslateX(-6.0);
		VBox buttonbox = new VBox(webPromotionButton, levelButton, marketerOrderButton, creditButton);
		buttonbox.setSpacing(5.0);

		this.getChildren().addAll(user, webMarketerID, buttonbox, backButton);

		AnchorPane.setLeftAnchor(user, 40.0);
		AnchorPane.setTopAnchor(user, 60.0);
		AnchorPane.setLeftAnchor(webMarketerID, 40.0);
		AnchorPane.setTopAnchor(webMarketerID, 150.0);
		AnchorPane.setLeftAnchor(buttonbox, 16.0);
		AnchorPane.setBottomAnchor(buttonbox, 200.0);
		AnchorPane.setLeftAnchor(backButton, 18.0);
		AnchorPane.setBottomAnchor(backButton, 100.0);

		this.setMaxSize(180, 700);
		this.setMinSize(180, 700);

		this.getStylesheets().add(getClass().getResource("WebMarketerInfoPane.css").toExternalForm());
		webPromotionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new WebPromotionPanel(ID));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		
		levelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new LevelPanel(ID));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		marketerOrderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new MarketerOrdersPane(ID));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		creditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new CustomerCreditPanel(ID));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.changeTo(new LoginPane());
			TheMainFrame.setLoginID(-1);
		});
		
		
	}
	


}
