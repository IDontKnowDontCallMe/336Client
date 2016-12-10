package presentation.mainui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
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

		int ID = 0;
		// 生成ID = BLFactory.getInstance().getCustomerBLService();
		gridPane.add(new Text("账号"), 0, 1, 1, 1);
		gridPane.add(new Text(String.valueOf(ID)), 1, 1, 1, 1);
		gridPane.add(new Text("此ID将用于登录"), 2, 1, 1, 1);

		gridPane.add(new Text("密码"), 0, 2, 1, 1);
		passwordTextField = new TextField();
		gridPane.add(passwordTextField, 1, 2, 1, 1);

		gridPane.add(new Text("姓名"), 0, 3, 1, 1);
		nameTextField = new TextField();
		gridPane.add(nameTextField, 1, 3, 1, 1);

		gridPane.add(new Text("电话"), 0, 4, 1, 1);
		phoneTextField = new TextField();
		gridPane.add(phoneTextField, 1, 4, 1, 1);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(ok, cancel);

		Callback<ButtonType, CustomerVO> resultConverter = new Callback<ButtonType, CustomerVO>() {

			@Override
			public CustomerVO call(ButtonType param) {
				// 还要保存账户和密码
				return new CustomerVO(ID, nameTextField.getText(), phoneTextField.getText(), false, null, false, null,
						0, 0);
			}
		};
		this.setResultConverter(resultConverter);

		this.getDialogPane().setContent(gridPane);
	}

}
