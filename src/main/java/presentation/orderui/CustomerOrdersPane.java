package presentation.orderui;

import java.rmi.RemoteException;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Toggle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import presentation.customerui.CustomerInfoPane;
import presentation.mainui.CustomerMainPane;
import presentation.mainui.CustomerPilot;
import presentation.mainui.TheMainFrame;
import vo.OrderVO;

/**
 * @author samperson1997
 * 客户订单列表面板
 *
 */
public class CustomerOrdersPane extends GridPane {
	Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);

	private int customerID;
	
	private HBox radioBox;

	private ToggleGroup toggleGroup;
	private RadioButton allButton;
	private RadioButton unexecutedButton;
	private RadioButton executedButton;
	private RadioButton revokedButton;
	private RadioButton abnormalButton;
	private RadioButton leftButton;

	private ScrollPane listPane;
	private VBox orderBox;

	/**
	 * @param customerID
	 * @throws RemoteException
	 * 客户订单面板
	 * 
	 */
	public CustomerOrdersPane(int customerID) throws RemoteException {

		this.customerID = customerID;
		initRadioButton();
		List<OrderVO> orderList = BLFactory.getInstance().getOrderBLService().getCustomerOrder(customerID);
		orderBox = new VBox();
		orderBox.setSpacing(10);
		buildOrderBox(orderList);
		
		listPane = new ScrollPane();
		listPane.setContent(orderBox);
		listPane.getStyleClass().add("edge-to-edge");		
		orderBox.setTranslateX(70.0);
		orderBox.setId("ob");
		listPane.setMinWidth(800.0);
		radioBox.setId("radio");

		VBox vBox= new VBox();
		radioBox.setTranslateY(20.0);
		radioBox.setTranslateX(20.0);
		vBox.getChildren().addAll(radioBox, listPane);
		vBox.setMinWidth(920.0);
		CustomerPilot customerPilot = new CustomerPilot(customerID);

		this.add(customerPilot, 0, 0);
		this.add(vBox, 1, 0);
		
		this.getStylesheets().add(getClass().getResource("CustomerOrderPane.css").toExternalForm());
	}

	/**
	 * 初始化单选框按钮组件
	 */
	private void initRadioButton() {
		
		toggleGroup = new ToggleGroup();
		allButton = new RadioButton("全部订单");
		allButton.setUserData("全部订单");
		allButton.setSelected(true);
		allButton.setToggleGroup(toggleGroup);
		unexecutedButton = new RadioButton("未执行订单");
		unexecutedButton.setUserData("正常");
		unexecutedButton.setToggleGroup(toggleGroup);
		executedButton = new RadioButton("已执行未离店订单");
		executedButton.setUserData("已执行未离店");
		executedButton.setToggleGroup(toggleGroup);
		leftButton = new RadioButton("已执行已离店订单");
		leftButton.setUserData("已执行已离店");
		leftButton.setToggleGroup(toggleGroup);
		revokedButton = new RadioButton("已撤销订单");
		revokedButton.setUserData("已撤销");
		revokedButton.setToggleGroup(toggleGroup);
		abnormalButton = new RadioButton("异常订单");
		abnormalButton.setUserData("异常");
		abnormalButton.setToggleGroup(toggleGroup);

		toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> obvalue, Toggle oldToggle, Toggle newToggle) -> {
					if (oldToggle.getUserData().equals(newToggle.getUserData())) {
						return;
					} else {
						if ((newToggle.getUserData()).equals("全部订单")) {
							try {
								buildOrderBox(BLFactory.getInstance().getOrderBLService().getCustomerOrder(customerID));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						} else {
							try {
								buildOrderBox(BLFactory.getInstance().getOrderBLService().filterCustomerList(customerID,
										(String) newToggle.getUserData()));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
					}
				}
				);
		
		
		radioBox = new HBox();
		radioBox.getChildren().addAll(allButton,unexecutedButton,executedButton,revokedButton,abnormalButton,leftButton);
		
		radioBox.setSpacing(15.0);
	}

	/**
	 * @param orderList
	 * 建立订单列表面板
	 * 
	 */
	private void buildOrderBox(List<OrderVO> orderList) {

		orderBox.getChildren().clear();
		for (OrderVO vo : orderList) {
			orderBox.getChildren().add(new CustomerOrderCell(vo));
		}
	}

}
