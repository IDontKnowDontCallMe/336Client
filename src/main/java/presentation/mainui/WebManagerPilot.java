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
import presentation.userui.CustomerInfoPanel;
import presentation.userui.HotelPanel;
import presentation.userui.WebMarketerPanel;

/**
 * @author samperson1997 网站管理人员导航栏
 *
 */
public class WebManagerPilot extends AnchorPane {

	private Label WebmanagerID;
	private Label name;
	private Button customerManageButton;
	private Button hotelManageButton;
	private Button webMarketerManageButton;
	private Button backButton;
	
	private int ID;

	public WebManagerPilot(int webManagerid) {
		Font icon = Font.loadFont(getClass().getResourceAsStream("fontawesome-webfont.ttf"), -1);
		this.WebmanagerID = new Label(Integer.toString(webManagerid));
		WebmanagerID.setId("id");
		this.setId("pane");
		this.ID = webManagerid;
		int width = 146;

		Label customermanage = new Label();
		customermanage.setFont(Font.font(icon.getFamily(), 26));
		customermanage.setText(String.valueOf('\uf007'));
		customerManageButton = new Button("客户管理", customermanage);
		customerManageButton.setWrapText(true);
		customerManageButton.setContentDisplay(ContentDisplay.LEFT);
		customerManageButton.setId("searchButton");
		customerManageButton.setMinSize(width, 40);
		customerManageButton.setMaxSize(width, 40);
		customermanage.setTranslateX(-28.0);
		customerManageButton.setGraphicTextGap(-5.0);

		Label hotelManage = new Label();
		hotelManage.setFont(Font.font(icon.getFamily(), 26));
		hotelManage.setText(String.valueOf('\uf1ad'));
		hotelManageButton = new Button("网站营销人员管理", hotelManage);
		hotelManageButton.setWrapText(true);
		hotelManageButton.setContentDisplay(ContentDisplay.LEFT);
		hotelManageButton.setId("orderButton");
		hotelManageButton.setMinSize(width, 40);
		hotelManageButton.setMaxSize(width, 40);
		hotelManage.setTranslateX(-5.0);
		hotelManageButton.setGraphicTextGap(-2.0);

		Label info = new Label();
		info.setFont(Font.font(icon.getFamily(), 26));
		info.setText(String.valueOf('\uf1e0'));
		webMarketerManageButton = new Button("网站营销人员管理", info);
		webMarketerManageButton.setWrapText(true);
		webMarketerManageButton.setContentDisplay(ContentDisplay.LEFT);
		webMarketerManageButton.setId("hotelButton");
		webMarketerManageButton.setMinSize(width, 40);
		webMarketerManageButton.setMaxSize(width, 40);
		info.setTranslateX(-5.0);
		webMarketerManageButton.setGraphicTextGap(-2.0);

		backButton = new Button("退出登录");
		backButton.setId("back");

		Label user = new Label();
		user.setFont(Font.font(icon.getFamily(), 70));
		user.setText(String.valueOf('\uf2bd'));
		user.setId("user");

		VBox webManagerinfo = new VBox(user, WebmanagerID);
		webManagerinfo.setSpacing(10.0);
		// hotelID.setTranslateX(-6.0);
		VBox buttonbox = new VBox(customerManageButton, hotelManageButton, webMarketerManageButton);
		buttonbox.setSpacing(5.0);

		this.getChildren().addAll(user, WebmanagerID, buttonbox, backButton);

		AnchorPane.setLeftAnchor(user, 40.0);
		AnchorPane.setTopAnchor(user, 60.0);
		AnchorPane.setLeftAnchor(WebmanagerID, 40.0);
		AnchorPane.setTopAnchor(WebmanagerID, 150.0);
		AnchorPane.setLeftAnchor(buttonbox, 16.0);
		AnchorPane.setBottomAnchor(buttonbox, 200.0);
		AnchorPane.setLeftAnchor(backButton, 18.0);
		AnchorPane.setBottomAnchor(backButton, 100.0);

		this.setMaxSize(180, 700);
		this.setMinSize(180, 700);

		this.getStylesheets().add(getClass().getResource("WebManagerInfoPane.css").toExternalForm());
		customerManageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new CustomerInfoPanel(webManagerid));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		hotelManageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new HotelPanel(webManagerid));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		webMarketerManageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				TheMainFrame.changeTo(new WebMarketerPanel(webManagerid));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.changeTo(new LoginPane());
			TheMainFrame.setLoginID(-1);
		});
		

		this.getStylesheets().add(getClass().getResource("WebManagerMainPane.css").toExternalForm());

	}
	

}
