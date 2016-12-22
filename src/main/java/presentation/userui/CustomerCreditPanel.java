package presentation.userui;

import java.rmi.RemoteException;
import java.util.List;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import presentation.mainui.TheMainFrame;
import presentation.mainui.WebMarketerPilot;
import vo.CustomerVO;

/**
 * @author samperson1997
 * 客户信用值列表面板
 *
 */
public class CustomerCreditPanel extends GridPane {

	private ScrollPane listPane;
	private VBox customerBox;
	private HBox titleBox;
	private Text title;
	private Button backButton;
	private VBox vBox;

	private List<CustomerVO> customerList;

	/**
	 * @throws RemoteException
	 * 客户信用值列表面板
	 * 
	 */
	public CustomerCreditPanel(int id) throws RemoteException {
		
		customerList = BLFactory.getInstance().getUserBLService().getCustomerList();

		customerBox = new VBox();
		customerBox.setSpacing(15);
		buildCustomerBox(customerList);
		listPane = new ScrollPane(customerBox);
		listPane.getStyleClass().add("edge-to-edge");		

		title = new Text("客户列表");
		titleBox = new HBox();
		backButton = new Button("返回");
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		titleBox.getChildren().addAll(title, backButton);
		vBox = new VBox();
		vBox.getChildren().addAll(titleBox, listPane);
		WebMarketerPilot webMarketerPilot = new WebMarketerPilot(id);
		this.add(webMarketerPilot, 0, 0);
		this.add(vBox, 1, 0);
		vBox.getStylesheets().add(getClass().getResource("CustomerCreditPane.css").toExternalForm());

	}

	/**
	 * @param customerList
	 * 建立客户信用值列表
	 * 
	 */
	public void buildCustomerBox(List<CustomerVO> customerList) {
		
		customerBox.getChildren().clear();
		for (CustomerVO vo : customerList) {
			customerBox.getChildren().addAll(new CustomerCreditCell(vo));
		}
	}
}
