package presentation.userui;

import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vo.CustomerVO;

/**
 * @author samperson1997
 * 客户信息单元格
 *
 */
public class CustomerInfoCell extends GridPane {

	CustomerVO customerVO;
	private GridPane infoPane;
	private Text nameText;
	private Text phoneNumberText;
	private Text levelText;
	private Text birthdayText;
	private Text companyText;
	private TextField nameTextField;
	private TextField phoneTextField;
	private Button editButton;

	/**
	 * @param customerVO
	 * 客户信息单元格
	 * 
	 */
	public CustomerInfoCell(CustomerVO customerVO) {
		
		super();
		this.customerVO = customerVO;
		infoPane = new GridPane();

		// 这部分代码和CustomerInfoPane是差不多的，做美化的时候可以省点力气
		infoPane.add(new Text("姓名"), 0, 0, 1, 1);
		nameText = new Text(customerVO.customerName);
		infoPane.add(nameText, 1, 0, 1, 1);

		infoPane.add(new Text("电话"), 0, 1, 1, 1);
		phoneNumberText = new Text(customerVO.phoneNumber);
		infoPane.add(phoneNumberText, 1, 1, 1, 1);

		infoPane.add(new Text("等级"), 0, 2, 1, 1);
		levelText = new Text(String.valueOf(customerVO.level));
		infoPane.add(levelText, 1, 2, 1, 1);

		infoPane.add(new Text("生日会员"), 0, 3, 1, 1);
		if (customerVO.isBirthVIP) {
			birthdayText = new Text(String.valueOf(customerVO.birthday));
		} else {
			birthdayText = new Text("无");
		}
		infoPane.add(birthdayText, 1, 3, 1, 1);

		infoPane.add(new Text("企业会员"), 0, 4, 1, 1);
		if (customerVO.isCompanyVIP) {
			companyText = new Text(customerVO.companyName);
		} else {
			companyText = new Text("无");
		}
		infoPane.add(companyText, 1, 4, 1, 1);

		editButton = new Button("编辑");
		infoPane.add(editButton, 0, 5, 1, 1);

		nameTextField = new TextField();
		nameTextField.setPrefColumnCount(8);
		phoneTextField = new TextField();
		phoneTextField.setPrefColumnCount(8);

		editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			if (editButton.getText().equals("编辑")) {
				infoPane.getChildren().removeAll(nameText, phoneNumberText);
				nameTextField.setText(nameText.getText());
				phoneTextField.setText(phoneNumberText.getText());

				infoPane.add(nameTextField, 1, 0, 1, 1);
				infoPane.add(phoneTextField, 1, 1, 1, 1);
				editButton.setText("保存");
			} else if (editButton.getText().equals("保存")) {
				infoPane.getChildren().removeAll(nameTextField, phoneTextField);
				infoPane.add(nameText, 1, 0, 1, 1);
				infoPane.add(phoneNumberText, 1, 1, 1, 1);
				infoPane.add(birthdayText, 1, 3, 1, 1);
				infoPane.add(companyText, 1, 4, 1, 1);

				CustomerVO newVO = customerVO;
				newVO.customerName = nameTextField.getText();
				newVO.phoneNumber = phoneTextField.getText();

				try {
					if (BLFactory.getInstance().getUserBLService().updateCustomer(newVO)) {
						nameText.setText(nameTextField.getText());
						phoneNumberText.setText(phoneTextField.getText());
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				editButton.setText("编辑");
			}
		});
		this.add(infoPane, 1, 0, 1, 1);

		this.setHgap(10);
		this.setVgap(20);
	}
}
