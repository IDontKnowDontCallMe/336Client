package presentation.userui;

import java.rmi.RemoteException;
import java.util.List;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import presentation.mainui.TheMainFrame;
import vo.CustomerVO;

/**
 * @author samperson1997
 * 客户信息面板
 *
 */
public class CustomerInfoPanel extends VBox {

	private ScrollPane listPane;
	private VBox customerBox;
	private HBox titleBox;
	private Button backButton;
	private Text title;

	/**
	 * @throws RemoteException
	 * 客户信息面板
	 * 
	 */
	public CustomerInfoPanel() throws RemoteException {
		
		List<CustomerVO> customerList = BLFactory.getInstance().getUserBLService().getCustomerList();

		customerBox = new VBox();
		customerBox.setSpacing(15);
		buildCustomerBox(customerList);
		listPane = new ScrollPane(customerBox);

		title = new Text("客户列表");
		titleBox = new HBox();
		backButton = new Button("返回");
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		titleBox.getChildren().addAll(title, backButton);
		this.getChildren().addAll(titleBox, listPane);
		
		this.getStylesheets().add(getClass().getResource("CustomerInfoPane.css").toExternalForm());
	}

	/**
	 * @param customerList
	 * 建立客户信息列表
	 * 
	 */
	public void buildCustomerBox(List<CustomerVO> customerList) {
		
		customerBox.getChildren().clear();
		for (CustomerVO vo : customerList) {
			customerBox.getChildren().addAll(new CustomerInfoCell(vo));
		}
	}
}
