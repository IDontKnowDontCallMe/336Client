package presentation.orderui;

import java.rmi.RemoteException;
import java.util.List;

import bussinesslogic.factory.BLFactory;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Toggle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presentation.mainui.TheMainFrame;
import vo.OrderVO;

public class CustomerOrdersPane extends VBox {

	private int customerID;
<<<<<<< HEAD
	//以后用工厂模式替换之
	
	private AnchorPane radioBox;
=======

	private HBox radioBox;
>>>>>>> refs/remotes/origin/master
	private ToggleGroup toggleGroup;
	private RadioButton allButton;
	private RadioButton unexecutedButton;
	private RadioButton executedButton;
	private RadioButton revokedButton;
	private RadioButton abnormalButton;
	private RadioButton leftButton;
	private Button backButton;

	private ScrollPane listPane;
	private VBox orderBox;

	public CustomerOrdersPane(int customerID) throws RemoteException {

		this.customerID = customerID;
		initRadioButton();
		List<OrderVO> orderList = BLFactory.getInstance().getOrderBLService().getCustomerOrder(customerID);
		orderBox = new VBox();
		orderBox.setSpacing(10);
		buildOrderBox(orderList);
		
		listPane = new ScrollPane();
		listPane.setContent(orderBox);
		orderBox.setTranslateX(150.0);
		
		radioBox.setId("radio");
		orderBox.setId("order");

		backButton = new Button("返回");
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		this.getChildren().addAll(radioBox, backButton, listPane);
		this.setPrefWidth(500);
		this.getStylesheets().add(getClass().getResource("CustomerOrderPane.css").toExternalForm());
	}

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

		toggleGroup.selectedToggleProperty()
				.addListener((ObservableValue<? extends Toggle> obvalue, Toggle oldToggle, Toggle newToggle) -> {
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
<<<<<<< HEAD
				}
				);
		
		backButton = new Button("返回");
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED	, (event)->{
			TheMainFrame.backTo();
		});
		
		radioBox = new AnchorPane();
		radioBox.getChildren().addAll(allButton,unexecutedButton,executedButton,revokedButton,abnormalButton, backButton);
=======
				});

		radioBox = new HBox();
		radioBox.setSpacing(10);
		radioBox.setPrefWidth(500);
		radioBox.getChildren().addAll(allButton, unexecutedButton, executedButton, leftButton, revokedButton,
				abnormalButton);

>>>>>>> refs/remotes/origin/master
	}

	private void buildOrderBox(List<OrderVO> orderList) {

		orderBox.getChildren().clear();
		for (OrderVO vo : orderList) {
			orderBox.getChildren().add(new CustomerOrderCell(vo));
		}
	}

}
