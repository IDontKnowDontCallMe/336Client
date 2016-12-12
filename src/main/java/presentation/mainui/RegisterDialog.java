package presentation.mainui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import vo.CustomerVO;

public class RegisterDialog extends Dialog {

	private GridPane gridPane;
	private TextField passwordTextField;
	private TextField nameTextField;
	private TextField phoneTextField;

	public RegisterDialog() {
		super();

		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		gridPane.add(new Text("注册一个客户账号"), 0, 0, 1, 1);

		gridPane.add(new Text("密码"), 0, 1, 1, 1);
		passwordTextField = new TextField();
		gridPane.add(passwordTextField, 1, 1, 1, 1);

		gridPane.add(new Text("姓名"), 0, 2, 1, 1);
		nameTextField = new TextField();
		gridPane.add(nameTextField, 1, 2, 1, 1);

		gridPane.add(new Text("电话"), 0, 3, 1, 1);
		phoneTextField = new TextField();
		gridPane.add(phoneTextField, 1, 3, 1, 1);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(ok, cancel);

		Callback<ButtonType, Integer> resultConverter = new Callback<ButtonType, Integer>() {

			@Override
			public Integer call(ButtonType param) {
				int ID = -1;
				try {
					ID = BLFactory.getInstance().getUserBLService().register(nameTextField.getText(),
							phoneTextField.getText(), passwordTextField.getText());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ID;
			}
		};
		this.setResultConverter(resultConverter);

		this.getDialogPane().setContent(gridPane);
	}

}
