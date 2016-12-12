package presentation.mainui;

import java.rmi.RemoteException;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import presentation.hotelui.WorkerHotelInfoPane;
import presentation.orderui.HotelOrdersPane;
import presentation.orderui.ProducingOrderOfflineDialog;
import presentation.promotionui.HotelPromotionPanel;

public class HotelWorkerMainPane extends VBox {

	private int hotelID;

	public HotelWorkerMainPane(int hotelID) {

		super();
		this.hotelID = hotelID;

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
		
		Button manageButton = new Button("酒店基本信息维护");
		
		Label hotelPromotion = new Label();
		hotelPromotion.setFont(Font.font(icon.getFamily(), dim));
		hotelPromotion.setText(String.valueOf('\uf0cb'));
		Button hotelPromotionButton = new Button("酒店促销策略制定", hotelPromotion);
		hotelPromotionButton.setWrapText(true);
		hotelPromotionButton.setContentDisplay(ContentDisplay.TOP);
		hotelPromotionButton.setId("searchButton");
		hotelPromotionButton.setMinSize(r, r);
		hotelPromotionButton.setMaxSize(r, r);
		
		Button offlineButton = new Button("线下入住办理");
		Button orderListButton = new Button("线上订单办理");
		Button logoutButton = new Button("注销登录");

		this.getChildren().addAll(manageButton, hotelPromotionButton, offlineButton, orderListButton, logoutButton);

		manageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new WorkerHotelInfoPane(hotelID));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		hotelPromotionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new HotelPromotionPanel(hotelID));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		offlineButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				ProducingOrderOfflineDialog producingOrderOfflineDialog = new ProducingOrderOfflineDialog(hotelID);
				producingOrderOfflineDialog.show();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		orderListButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new HotelOrdersPane(hotelID));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});

		this.getStylesheets().add(getClass().getResource("HotelWorkerMainPane.css").toExternalForm());
	}
}
