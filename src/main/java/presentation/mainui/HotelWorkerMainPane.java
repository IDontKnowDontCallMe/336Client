package presentation.mainui;

import java.rmi.RemoteException;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import presentation.hotelui.WorkerHotelInfoPane;
import presentation.orderui.HotelOrdersPane;
import presentation.orderui.ProducingOrderDialog;
import presentation.orderui.ProducingOrderOfflineDialog;
import presentation.promotionui.HotelPromotionPanel;

public class HotelWorkerMainPane extends VBox {

	private int hotelID;

	public HotelWorkerMainPane(int hotelID) {

		super();
		this.hotelID = hotelID;

		Button manageButton = new Button("酒店基本信息维护");
		Button hotelPromotionButton = new Button("酒店促销策略制定");
		Button offlineButton = new Button("线下入住与退房办理");
		Button onlineButton = new Button("线上入住与退房办理");
		Button orderListButton = new Button("查看订单");
		Button logoutButton = new Button("注销登录");

		
		
		
		this.getChildren().addAll(manageButton, hotelPromotionButton, offlineButton, onlineButton, orderListButton,
				logoutButton);

		
		
		
		
		
		
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

		onlineButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			// TheMainFrame.jumpTo();
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
