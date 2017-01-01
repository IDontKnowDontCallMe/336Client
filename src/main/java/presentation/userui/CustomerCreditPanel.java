package presentation.userui;

import java.rmi.RemoteException;
import java.util.List;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private Label title;
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
		listPane.setMinWidth(920.0);

		title = new Label("客户列表");
		
		vBox = new VBox();
		vBox.getChildren().addAll(title,listPane);
		
		WebMarketerPilot webMarketerPilot = new WebMarketerPilot(id);
		this.add(webMarketerPilot, 0, 0);
		this.add(vBox, 1, 0);
		vBox.setId("pane");
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
