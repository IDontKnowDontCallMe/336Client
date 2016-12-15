package presentation.mainui;

import java.rmi.RemoteException;
import java.util.Optional;

import bussinesslogic.factory.BLFactory;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginPane extends AnchorPane {

	private TextField userIDTextField;
	private PasswordField passwordField;
	private Button loginButton;
	private Button registerButton;
	private Label hint;
	private int ID;

	public LoginPane() {
		super();
		userIDTextField = new TextField();
		userIDTextField.setPromptText("UserID");
		passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		loginButton = new Button("Login");
		loginButton.setId("loginbutton");

		registerButton = new Button("现在创建一个");
		registerButton.setId("registerbutton");
		hint = new Label("有 Aipapa ID 吗?");

		registerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			Dialog<Integer> registerDialog = new RegisterDialog();

			Optional<Integer> result = registerDialog.showAndWait();
			if (result.isPresent()) {
				ID = result.get();
				Stage popup1 = new Stage();
				popup1.setAlwaysOnTop(true);
				popup1.initModality(Modality.APPLICATION_MODAL);
				Button closeButton1 = new Button("确定");
				closeButton1.setOnAction(e -> {
					popup1.close();
					TheMainFrame.jumpTo(new CustomerMainPane(ID));
				});
				VBox root1 = new VBox();
				root1.setAlignment(Pos.BASELINE_CENTER);
				root1.setSpacing(20);
				root1.getChildren().addAll(new Label("您的账号为" + ID + ","), new Label("该账号将用于今后的登录"), closeButton1);
				Scene scene1 = new Scene(root1, 250, 120);
				popup1.setScene(scene1);
				popup1.setTitle("账号消息");
				popup1.show();

			}
		});

		this.getChildren().addAll(userIDTextField, passwordField, loginButton, registerButton, hint);
		AnchorPane.setLeftAnchor(userIDTextField, 420.0);
		AnchorPane.setLeftAnchor(passwordField, 420.0);
		AnchorPane.setTopAnchor(userIDTextField, 380.0);
		AnchorPane.setTopAnchor(passwordField, 410.0);
		AnchorPane.setLeftAnchor(loginButton, 490.0);
		AnchorPane.setTopAnchor(loginButton, 452.0);
		AnchorPane.setLeftAnchor(hint, 425.0);
		AnchorPane.setTopAnchor(hint, 500.0);
		AnchorPane.setLeftAnchor(registerButton, 520.0);
		AnchorPane.setTopAnchor(registerButton, 500.0);

		loginButton.setAlignment(Pos.CENTER_RIGHT);

		userIDTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.DOWN)) {
					passwordField.requestFocus();

				}
				if (event.getCode().equals(KeyCode.ENTER) && userIDTextField.getText() != ""
						&& passwordField.getText() != "") {
					try {
						login();
					} catch (NumberFormatException | RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.UP)) {
					userIDTextField.requestFocus();
				}
				if (event.getCode().equals(KeyCode.ENTER) && userIDTextField.getText() != ""
						&& passwordField.getText() != "") {
					try {
						login();
					} catch (NumberFormatException | RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

			try {
				login();
			} catch (NumberFormatException | RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		this.getStylesheets().add(getClass().getResource("LoginPane.css").toExternalForm());
	}

	private void login() throws NumberFormatException, RemoteException {

		String res = BLFactory.getInstance().getUserBLService().login(Integer.parseInt(userIDTextField.getText()),
				passwordField.getText());
		switch (res) {
		case ("NOT_FOUND"):
			Stage popup1 = new Stage();
			popup1.setAlwaysOnTop(true);
			popup1.initModality(Modality.APPLICATION_MODAL);
			Button closeButton1 = new Button("确定");
			closeButton1.setOnAction(e -> {
				popup1.close();
			});
			VBox root1 = new VBox();
			root1.setAlignment(Pos.BASELINE_CENTER);
			root1.setSpacing(20);
			root1.getChildren().addAll(new Label("账号不存在！"), closeButton1);
			Scene scene1 = new Scene(root1, 200, 90);
			popup1.setScene(scene1);
			popup1.setTitle("提示");
			popup1.show();
			break;
		case ("has logined"):
			Stage popup2 = new Stage();
			popup2.setAlwaysOnTop(true);
			popup2.initModality(Modality.APPLICATION_MODAL);
			Button closeButton2 = new Button("确定");
			closeButton2.setOnAction(e -> {
				popup2.close();
			});
			VBox root2 = new VBox();
			root2.setAlignment(Pos.BASELINE_CENTER);
			root2.setSpacing(20);
			root2.getChildren().addAll(new Label("账号已在其他设备登录！"), closeButton2);
			Scene scene2 = new Scene(root2, 200, 90);
			popup2.setScene(scene2);
			popup2.setTitle("提示");
			popup2.show();
			break;
		case ("password wrong"):
			Stage popup3 = new Stage();
			popup3.setAlwaysOnTop(true);
			popup3.initModality(Modality.APPLICATION_MODAL);
			Button closeButton3 = new Button("确定");
			closeButton3.setOnAction(e -> {
				popup3.close();
			});
			VBox root3 = new VBox();
			root3.setAlignment(Pos.BASELINE_CENTER);
			root3.setSpacing(20);
			root3.getChildren().addAll(new Label("密码错误！"), closeButton3);
			Scene scene3 = new Scene(root3, 200, 90);
			popup3.setScene(scene3);
			popup3.setTitle("提示");
			popup3.show();
			break;
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
	}

}
