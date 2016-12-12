package presentation.orderui;

import java.rmi.RemoteException;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vo.OrderVO;

public class HotelOrdersPane extends VBox {

	private int hotelID;
	private HBox radioBox;
	private ToggleGroup toggleGroup;
	private RadioButton allButton;
	private RadioButton unexecutedButton;
	private RadioButton executedButton;
	private RadioButton revokedButton;
	private RadioButton leftButton;
	private RadioButton abnormalButton;

	private ScrollPane listPane;
	private VBox orderBox;

	public HotelOrdersPane(int hotelID) throws RemoteException {
		this.hotelID = hotelID;
		initRadioButton();
		List<OrderVO> orderList = BLFactory.getInstance().getOrderBLService().getHotelOrder(hotelID);

		orderBox = new VBox();
		orderBox.setSpacing(15);
		buildOrderBox(orderList);
		listPane = new ScrollPane(orderBox);
		this.getChildren().addAll(radioBox, listPane);
		this.setPrefWidth(500);
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
						if (newToggle.getUserData().equals("全部订单")) {
							try {
								buildOrderBox(BLFactory.getInstance().getOrderBLService().getHotelOrder(hotelID));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
						try {
							buildOrderBox(BLFactory.getInstance().getOrderBLService().filterHotelList(hotelID,
									(String) newToggle.getUserData()));
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				});

		radioBox = new HBox();
		radioBox.setSpacing(10);
		radioBox.setPrefWidth(500);
		radioBox.getChildren().addAll(allButton, unexecutedButton, executedButton, leftButton, revokedButton,
				abnormalButton);

		this.getStylesheets().add(getClass().getResource("HotelOrderPane.css").toExternalForm());
	}

	private void buildOrderBox(List<OrderVO> orderList) throws RemoteException {

		orderBox.getChildren().clear();
		for (OrderVO vo : orderList) {
			orderBox.getChildren().add(new HotelOrderCell(vo));
		}
	}

}
