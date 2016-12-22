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
import presentation.mainui.WebManagerPilot;
import vo.CustomerVO;

/**
 * @author samperson1997
 * 客户信息面板
 *
 */
public class CustomerInfoPanel extends GridPane {

	private ScrollPane listPane;
	private VBox customerBox;
	private HBox titleBox;
	private Label title;

	/**
	 * @throws RemoteException
	 * 客户信息面板
	 * 
	 */
	public CustomerInfoPanel(int id) throws RemoteException {
		
		List<CustomerVO> customerList = BLFactory.getInstance().getUserBLService().getCustomerList();

		customerBox = new VBox();
		customerBox.setSpacing(15);
		buildCustomerBox(customerList);
		listPane = new ScrollPane(customerBox);
		listPane.setMinWidth(920.0);
		listPane.getStyleClass().add("edge-to-edge");		
		title = new Label("客户列表");
		titleBox = new HBox();
		
		GridPane gridPane = new GridPane();
		this.getChildren().addAll(titleBox, listPane);
		gridPane.add(title, 0, 0);
		gridPane.add(listPane, 0, 1);
		WebManagerPilot webManagerPilot = new WebManagerPilot(id);
		this.add(webManagerPilot, 0, 0);
		this.add(gridPane, 1, 0);
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
