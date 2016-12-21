package presentation.mainui;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class WebManagerInfoPane extends AnchorPane{
	Label WebmanagerID;
	Label name;
	Button customerManageButton;
	Button hotelManageButton;
	Button webMarketerManageButton;
	Button backButton;
	
	public WebManagerInfoPane(int webManagerid ) {
		Font icon = Font.loadFont(getClass().getResourceAsStream("fontawesome-webfont.ttf"), -1);
		this.WebmanagerID = new Label(Integer.toString(webManagerid));
		WebmanagerID.setId("id");
		this.setId("pane");
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
		customermanage.setTranslateX(-18.0);
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
		hotelManage.setTranslateX(-18.0);
		hotelManageButton.setGraphicTextGap(-5.0);
		

		
		Label info = new Label();
		info.setFont(Font.font(icon.getFamily(), 26));
		info.setText(String.valueOf('\uf1e0'));
		webMarketerManageButton = new Button("网站营销人员管理", info);
		webMarketerManageButton.setWrapText(true);
		webMarketerManageButton.setContentDisplay(ContentDisplay.LEFT);
		webMarketerManageButton.setId("hotelButton");
		webMarketerManageButton.setMinSize(width,40);
		webMarketerManageButton.setMaxSize(width,40);
		info.setTranslateX(-18.0);
		webMarketerManageButton.setGraphicTextGap(-5.0);
		
		backButton = new Button("退出登录");
		backButton.setId("back");
		
		Label user = new Label();
		user.setFont(Font.font(icon.getFamily(), 70));
		user.setText(String.valueOf('\uf2bd'));
		user.setId("user");
		
		VBox customerinfo = new VBox(user,WebmanagerID);
		customerinfo.setSpacing(10.0);
		//hotelID.setTranslateX(-6.0);
		VBox buttonbox = new VBox(customerManageButton,hotelManageButton,webMarketerManageButton);
		buttonbox.setSpacing(5.0);
		
		this.getChildren().addAll(user,WebmanagerID,buttonbox,backButton);
		
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
	}

}

