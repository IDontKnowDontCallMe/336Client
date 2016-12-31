package presentation.userui;

import java.rmi.RemoteException;
import java.util.Optional;

import bussinesslogic.factory.BLFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import presentation.userui.CreditDialog;
import vo.CustomerVO;

/**
 * @author samperson1997
 * 客户信用值单元格
 *
 */
public class CustomerCreditCell extends GridPane {

	CustomerVO customerVO;
	private GridPane infoPane;
	private Label nameText;
	private Label phoneNumberText;
	private Label creditText;
	private Label birthdayText;
	private Label companyText;
	private Button editButton;

	/**
	 * @param customerVO
	 * 客户信用值单元格
	 * 
	 */
	public CustomerCreditCell(CustomerVO customerVO) {
		
		super();
		this.customerVO = customerVO;
		infoPane = new GridPane();

		// 这部分代码和CustomerInfoPane是差不多的，做美化的时候可以省点力气
		infoPane.add(new Label("姓名"), 0, 0, 1, 1);
		nameText = new Label(customerVO.customerName);
		infoPane.add(nameText, 1, 0, 1, 1);

		infoPane.add(new Label("电话"), 0, 1, 1, 1);
		phoneNumberText = new Label(customerVO.phoneNumber);
		infoPane.add(phoneNumberText, 1, 1, 1, 1);

		infoPane.add(new Label("信用值"), 0, 2, 1, 1);
		creditText = new Label(String.valueOf(customerVO.credit));
		infoPane.add(creditText, 1, 2, 1, 1);

		infoPane.add(new Label("生日会员"), 0, 3, 1, 1);
		if (customerVO.isBirthVIP) {
			birthdayText = new Label(String.valueOf(customerVO.birthday));
		} else {
			birthdayText = new Label("无");
		}
		infoPane.add(birthdayText, 1, 3, 1, 1);

		infoPane.add(new Label("企业会员"), 0, 4, 1, 1);
		if (customerVO.isCompanyVIP) {
			companyText = new Label(customerVO.companyName);
		} else {
			companyText = new Label("无");
		}
		infoPane.add(companyText, 1, 4, 1, 1);

		editButton = new Button("信用充值");
		infoPane.add(editButton, 0, 5, 1, 1);
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Dialog<Integer> creditDialog = new CreditDialog(customerVO.customerID);

				Optional<Integer> result = creditDialog.showAndWait();
				if (result.isPresent()) {
					try {
						if (BLFactory.getInstance().getUserBLService().updateCreditOfCustomer(customerVO.customerID,
								result.get())) {
							creditText.setText(String.valueOf(customerVO.credit + result.get()));
							System.out.println("add credit!");
							System.out.println("new credit is " + customerVO.credit + result.get());
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("nothing changed");
				}

			}
		});

		this.add(infoPane, 1, 0, 1, 1);

		this.setHgap(10);
		this.setVgap(20);
	}
}
