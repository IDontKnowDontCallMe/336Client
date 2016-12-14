package presentation.mainui;

import java.rmi.RemoteException;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import presentation.orderui.MarketerOrdersPane;
import presentation.promotionui.LevelPanel;
import presentation.promotionui.WebPromotionPanel;
import presentation.userui.CustomerCreditPanel;

public class WebMarketerMainPane extends AnchorPane {

	public WebMarketerMainPane() {
		super();
		int r =200;
		int dim = 130;
		Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);

		Label webPromotion = new Label();
		webPromotion.setFont(Font.font(icon.getFamily(), dim));
		webPromotion.setText(String.valueOf('\uf044'));
		Button webPromotionButton = new Button("网站促销策略制定", webPromotion);
		webPromotionButton.setWrapText(true);
		webPromotionButton.setContentDisplay(ContentDisplay.TOP);
		webPromotionButton.setId("webPromotionButton");
		webPromotionButton.setMinSize(r, r);
		webPromotionButton.setMaxSize(r,r);
		
		Label level = new Label();
		level.setFont(Font.font(icon.getFamily(), dim));
		level.setText(String.valueOf('\uf0cb'));
		Button levelButton = new Button("等级策略制定", level);
		levelButton.setWrapText(true);
		levelButton.setContentDisplay(ContentDisplay.TOP);
		levelButton.setId("levelButton");
		levelButton.setMinSize(r, r);
		levelButton.setMaxSize(r,r);
		
		Label marketerOrder = new Label();
		marketerOrder.setFont(Font.font(icon.getFamily(), dim));
		marketerOrder.setText(String.valueOf('\uf0ea'));
		Button marketerOrderButton = new Button("异常订单处理", marketerOrder);
		marketerOrderButton.setWrapText(true);
		marketerOrderButton.setContentDisplay(ContentDisplay.TOP);
		marketerOrderButton.setId("marketerOrderButton");
		marketerOrderButton.setMinSize(r, r);
		marketerOrderButton.setMaxSize(r,r);
		
		Label credit = new Label();
		credit.setFont(Font.font(icon.getFamily(), dim));
		credit.setText(String.valueOf('\uf283'));
		Button creditButton = new Button("信用充值", credit);
		creditButton.setWrapText(true);
		creditButton.setContentDisplay(ContentDisplay.TOP);
		creditButton.setId("creditButton");
		creditButton.setMinSize(r, r);
		creditButton.setMaxSize(r,r);
		
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

		
		
		this.getChildren().addAll(webPromotionButton, levelButton, marketerOrderButton, creditButton, logoutButton);
		
		AnchorPane.setLeftAnchor(webPromotionButton, 250.0);
		AnchorPane.setTopAnchor(webPromotionButton, 100.0);
		AnchorPane.setLeftAnchor(levelButton, 600.0);
		AnchorPane.setTopAnchor(levelButton, 100.0);
		AnchorPane.setLeftAnchor(marketerOrderButton, 250.0);
		AnchorPane.setTopAnchor(marketerOrderButton, 400.0);
		AnchorPane.setLeftAnchor(creditButton, 600.0);
		AnchorPane.setTopAnchor(creditButton, 400.0);
		AnchorPane.setLeftAnchor(logoutButton, 985.0);
		AnchorPane.setTopAnchor(logoutButton, 30.0);

		
		
		
		
		
		
		webPromotionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new WebPromotionPanel());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		
		levelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new LevelPanel());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		marketerOrderButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new MarketerOrdersPane());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		creditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new CustomerCreditPanel());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		
		this.getStylesheets().add(getClass().getResource("WebMarketerMainPane.css").toExternalForm());

	}
}
