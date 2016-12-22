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
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import presentation.hotelui.WorkerHotelInfoPane;
import presentation.orderui.HotelOrdersPane;
import presentation.orderui.ProducingOrderOfflineDialog;
import presentation.promotionui.HotelPromotionPanel;

/**
 * @author samperson1997
 * 酒店工作人员主界面
 *
 */
public class HotelWorkerMainPane extends GridPane {

	private int hotelID;
	private Timer surviveTimer;

	public HotelWorkerMainPane(int hotelID) {

		super();
		this.hotelID = hotelID;

		int r =200;
		int dim = 130;
		Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);

		
		Label manage = new Label();
		manage.setFont(Font.font(icon.getFamily(), dim));
		manage.setText(String.valueOf('\uf040'));
		Button manageButton = new Button("酒店基本信息维护", manage);
		manageButton.setWrapText(true);
		manageButton.setContentDisplay(ContentDisplay.TOP);
		manageButton.setId("manage");
		manageButton.setMinSize(r, r);
		manageButton.setMaxSize(r, r);
		
		
		Label hotelPromotion = new Label();
		hotelPromotion.setFont(Font.font(icon.getFamily(), dim));
		hotelPromotion.setText(String.valueOf('\uf044'));
		Button hotelPromotionButton = new Button("酒店促销策略制定", hotelPromotion);
		hotelPromotionButton.setWrapText(true);
		hotelPromotionButton.setContentDisplay(ContentDisplay.TOP);
		hotelPromotionButton.setId("hotelPromotionButton");
		hotelPromotionButton.setMinSize(r, r);
		hotelPromotionButton.setMaxSize(r, r);
		
		Label offline = new Label();
		offline.setFont(Font.font(icon.getFamily(), dim));
		offline.setText(String.valueOf('\uf0c5'));
		Button offlineButton = new Button("线下订单办理", offline);
		offlineButton.setWrapText(true);
		offlineButton.setContentDisplay(ContentDisplay.TOP);
		offlineButton.setId("offline");
		offlineButton.setMinSize(r, r);
		offlineButton.setMaxSize(r, r);
		
		
		Label orderList = new Label();
		orderList.setFont(Font.font(icon.getFamily(), dim));
		orderList.setText(String.valueOf('\uf108'));
		Button orderListButton = new Button("线上订单办理", orderList);
		orderListButton.setWrapText(true);
		orderListButton.setContentDisplay(ContentDisplay.TOP);
		orderListButton.setId("orderListButton");
		orderListButton.setMinSize(r, r);
		orderListButton.setMaxSize(r, r);
		
		
		
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
		
		AnchorPane anchorPane = new AnchorPane();
		
		anchorPane.getChildren().addAll(manageButton, hotelPromotionButton, offlineButton, orderListButton, logoutButton);

		AnchorPane.setLeftAnchor(orderListButton, 170.0);
		AnchorPane.setTopAnchor(orderListButton, 100.0);
		AnchorPane.setLeftAnchor(offlineButton, 520.0);
		AnchorPane.setTopAnchor(offlineButton, 100.0);
		AnchorPane.setLeftAnchor(hotelPromotionButton, 170.0);
		AnchorPane.setTopAnchor(hotelPromotionButton, 400.0);
		AnchorPane.setLeftAnchor(manageButton, 520.0);
		AnchorPane.setTopAnchor(manageButton, 400.0);
		AnchorPane.setLeftAnchor(logoutButton, 985.0);
		AnchorPane.setTopAnchor(logoutButton, 30.0);
		
		HotelWorkerPilot hotelWorkerPilot = new HotelWorkerPilot(hotelID);
		this.add(hotelWorkerPilot, 0, 0);
		this.add(anchorPane, 1, 0);
		
		anchorPane.getStylesheets().add(getClass().getResource("HotelWorkerMainPane.css").toExternalForm());
		
		
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
			surviveTimer.cancel();
			TheMainFrame.backTo();
		});
		
		surviveTimer = new Timer(true);
		surviveTimer.schedule(new SurvivalTast(), 1, 1000);

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
				BLFactory.getInstance().getUserBLService().survivalConfirm(hotelID);
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
