package presentation.mainui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class RegisterDialog extends Dialog<Integer> {

	private GridPane gridPane;
	private TextField passwordTextField;
	private TextField nameTextField;
	private TextField phoneTextField;

	public RegisterDialog() {
		super();
		gridPane = new GridPane();
		
		gridPane.setHgap(10);
		gridPane.setVgap(25);
		gridPane.setId("grid");
		Label register = new Label("注册一个客户账号");
		gridPane.add(register, 0, 0, 1, 1);

		Label password = new Label("密码");
		gridPane.add(password, 0, 1, 1, 1);
		passwordTextField = new TextField();
		gridPane.add(passwordTextField, 1, 1, 1, 1);
		passwordTextField.setTranslateX(-25.0);
		password.setTranslateX(41.0);
		Label name = new Label("姓名");
		gridPane.add(name, 0, 2, 1, 1);
		nameTextField = new TextField();
		gridPane.add(nameTextField, 1, 2, 1, 1);
		nameTextField.setTranslateX(-25.0);
		name.setTranslateX(41.0);
		Label number = new Label("电话");
		gridPane.add(number, 0, 3, 1, 1);
		phoneTextField = new TextField();
		gridPane.add(phoneTextField, 1, 3, 1, 1);
		phoneTextField.setTranslateX(-25.0);
		number.setTranslateX(41.0);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(ok, cancel);

		Callback<ButtonType, Integer> resultConverter = new Callback<ButtonType, Integer>() {

			@Override
			public Integer call(ButtonType param) {
				if (param.getButtonData() == ButtonData.OK_DONE) {

					int ID = -1;
					try {
						ID = BLFactory.getInstance().getUserBLService().register(nameTextField.getText(),
								phoneTextField.getText(), passwordTextField.getText());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return ID;
				} else {
					return null;
				}
			}
		};
		this.setResultConverter(resultConverter);
		
		this.getDialogPane().getStylesheets().add(RegisterDialog.class.getResource("RegisterDialog.css").toExternalForm());
		this.getDialogPane().setContent(gridPane);
	}

}
