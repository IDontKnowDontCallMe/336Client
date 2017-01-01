package presentation.userui;

import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vo.HotelVO;

/**
 * @author samperson1997
 * 酒店信息单元格
 *
 */
public class HotelCell extends GridPane {
	HotelVO hotelVO;
	private GridPane infoPane;
	private Label nameText;
	private Label hotelText;
	private Label phoneNumberText;
	private TextField nameTextField;
	private TextField phoneTextField;
	private Button editButton;

	/**
	 * @param hotelVO
	 * 酒店信息单元格
	 * 
	 */
	public HotelCell(HotelVO hotelVO) {
		
		super();
		this.hotelVO = hotelVO;
		infoPane = new GridPane();

		infoPane.add(new Label("姓名"), 0, 0, 1, 1);
		nameText = new Label(hotelVO.workerName);
		infoPane.add(nameText, 1, 0, 1, 1);

		infoPane.add(new Label("酒店"), 0, 1, 1, 1);
		hotelText = new Label(hotelVO.hotelName);
		infoPane.add(hotelText, 1, 1, 1, 1);

		infoPane.add(new Label("电话"), 0, 2, 1, 1);
		phoneNumberText = new Label(hotelVO.phoneNumber);
		infoPane.add(phoneNumberText, 1, 2, 1, 1);

		editButton = new Button("编辑");
		infoPane.add(editButton, 0, 3, 1, 1);

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
				infoPane.add(phoneTextField, 1, 2, 1, 1);
				editButton.setText("保存");
			} else if (editButton.getText().equals("保存")) {
				infoPane.getChildren().removeAll(nameTextField, phoneTextField);
				infoPane.add(nameText, 1, 0, 1, 1);
				infoPane.add(phoneNumberText, 1, 2, 1, 1);

				HotelVO newVO = hotelVO;
				newVO.workerName = nameTextField.getText();
				newVO.phoneNumber = phoneTextField.getText();
				try {
					if (BLFactory.getInstance().getUserBLService().updateHotelWorker(newVO)) {
						nameText.setText(nameTextField.getText());
						phoneNumberText.setText(phoneTextField.getText());
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				editButton.setText("编辑");
			}
		});
		this.add(infoPane, 0, 0, 1, 1);

		this.setHgap(10);
		this.setVgap(20);
	}
}
