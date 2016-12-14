package presentation.mainui;

import java.rmi.RemoteException;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import presentation.userui.CustomerInfoPanel;
import presentation.userui.HotelPanel;
import presentation.userui.WebMarketerPanel;

public class WebManagerMainPane extends AnchorPane {

	public WebManagerMainPane() {
		super();
		int r =200;
		int dim = 130;
		Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);

		Label customerManage = new Label();
		customerManage.setFont(Font.font(icon.getFamily(), dim));
		customerManage.setText(String.valueOf('\uf007'));
		Button customerManageButton = new Button("客户管理", customerManage);
		customerManageButton.setWrapText(true);
		customerManageButton.setContentDisplay(ContentDisplay.TOP);
		customerManageButton.setId("customerManageButton");
		customerManageButton.setMinSize(r, r);
		customerManageButton.setMaxSize(r, r);
		
				
		Label hotelManage = new Label();
		hotelManage.setFont(Font.font(icon.getFamily(), dim));
		hotelManage.setText(String.valueOf('\uf1ad'));
		Button hotelManageButton = new Button("酒店及酒店工作人员管理", hotelManage);
		hotelManageButton.setWrapText(true);
		hotelManageButton.setContentDisplay(ContentDisplay.TOP);
		hotelManageButton.setId("hotelManageButton");
		hotelManageButton.setMinSize(r, r);
		hotelManageButton.setMaxSize(r, r);
		
		Label webMarketerManage = new Label();
		webMarketerManage.setFont(Font.font(icon.getFamily(), dim));
		webMarketerManage.setText(String.valueOf('\uf1e0'));
		Button webMarketerManageButton = new Button("网站营销人员管理", webMarketerManage);
		webMarketerManageButton.setWrapText(true);
		webMarketerManageButton.setContentDisplay(ContentDisplay.TOP);
		webMarketerManageButton.setId("webMarketerManageButton");
		webMarketerManageButton.setMinSize(r, r);
		webMarketerManageButton.setMaxSize(r, r);
		
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
		

		this.getChildren().addAll(customerManageButton, hotelManageButton, webMarketerManageButton, logoutButton);
		
		AnchorPane.setLeftAnchor(customerManageButton, 124.0);
		AnchorPane.setTopAnchor(customerManageButton, 250.0);
		AnchorPane.setLeftAnchor(hotelManageButton, 450.0);
		AnchorPane.setTopAnchor(hotelManageButton, 250.0);
		AnchorPane.setLeftAnchor(webMarketerManageButton, 776.0);
		AnchorPane.setTopAnchor(webMarketerManageButton, 250.0);
		AnchorPane.setLeftAnchor(logoutButton, 985.0);
		AnchorPane.setTopAnchor(logoutButton, 30.0);
		
		customerManageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new CustomerInfoPanel());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		hotelManageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new HotelPanel());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		webMarketerManageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.jumpTo(new WebMarketerPanel());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});

		this.getStylesheets().add(getClass().getResource("WebManagerMainPane.css").toExternalForm());

	}
}
