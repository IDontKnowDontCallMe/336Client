package presentation.mainui;

import java.rmi.RemoteException;
import java.util.Optional;

import bussinesslogic.factory.BLFactory;
import vo.CustomerVO;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginPane extends AnchorPane {

	private TextField userIDTextField;
	private PasswordField passwordField;
	private Button loginButton;
	private Button registerButton;
	private Label hint;

	public LoginPane() {
		super();
		userIDTextField = new TextField();
		userIDTextField.setPromptText("UserID");
		passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		loginButton = new Button("Login");
		loginButton.setId("loginbutton");

		registerButton = new Button("现在注册一个");
		registerButton.setId("registerbutton");
		hint = new Label("有 Aipapa ID 吗?");

		registerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			Dialog<CustomerVO> registerDialog = new RegisterDialog();

			Optional<CustomerVO> result = registerDialog.showAndWait();
			if (result.isPresent()) {
				CustomerVO customerVO = result.get();
				// 添加新用户信息及密码
				try {
					if (BLFactory.getInstance().getUserBLService().addCustomer(customerVO)) {
						TheMainFrame.jumpTo(new CustomerMainPane(customerVO.customerID));
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		this.getChildren().addAll(userIDTextField, passwordField, loginButton, registerButton, hint);
		AnchorPane.setLeftAnchor(userIDTextField, 425.0);
		AnchorPane.setLeftAnchor(passwordField, 425.0);
		AnchorPane.setTopAnchor(userIDTextField, 420.0);
		AnchorPane.setTopAnchor(passwordField, 450.0);
		AnchorPane.setLeftAnchor(loginButton, 690.0);
		AnchorPane.setTopAnchor(loginButton, 420.0);
		AnchorPane.setLeftAnchor(hint, 425.0);
		AnchorPane.setTopAnchor(hint, 485.0);
		AnchorPane.setLeftAnchor(registerButton, 520.0);
		AnchorPane.setTopAnchor(registerButton, 481.0);

		loginButton.setAlignment(Pos.CENTER_RIGHT);

		loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

			// 可能会有问题？

			try {
				String res = BLFactory.getInstance().getUserBLService()
						.login(Integer.parseInt(userIDTextField.getText()), passwordField.getText());
				switch (res) {
				case ("customer"):
					TheMainFrame.jumpTo(new CustomerMainPane(Integer.valueOf(userIDTextField.getText())));
					break;
				case ("hotelWorker"):
					TheMainFrame.jumpTo(new HotelWorkerMainPane(Integer.valueOf(userIDTextField.getText())));
					break;
				case ("webMarketer"):
					TheMainFrame.jumpTo(new WebMarketerMainPane());
					break;
				case ("webManager"):
					TheMainFrame.jumpTo(new WebManagerMainPane());
					break;
				}

			} catch (NumberFormatException | RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		this.getStylesheets().add(getClass().getResource("LoginPane.css").toExternalForm());
	}

}
