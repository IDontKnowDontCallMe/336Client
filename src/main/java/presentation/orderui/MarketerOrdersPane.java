package presentation.orderui;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import presentation.mainui.CustomerMainPane;
import presentation.mainui.HotelWorkerPilot;
import presentation.mainui.TheMainFrame;
import presentation.mainui.WebMarketerPilot;
import vo.HotelVO;
import vo.OrderVO;

/**
 * @author samperson1997 
 * 网站营销人员订单面板
 *
 */
public class MarketerOrdersPane extends GridPane {

	Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);
	private AnchorPane radioBox;

	private ToggleGroup toggleGroup;
	private RadioButton abnormalButton;
	private RadioButton unexecutedButton;
	private ScrollPane listPane;
	private VBox orderBox;
	private List<OrderVO> orderList;

	/**
	 * @throws RemoteException
	 * 网站营销人员订单面板
	 * 
	 */
	public MarketerOrdersPane(int id) throws RemoteException {

		initRadioButton();
		orderList = BLFactory.getInstance().getOrderBLService().getAbnormalOrdersOfToday();
		orderBox = new VBox();
		orderBox.setSpacing(10);
		buildOrderBox(orderList);
		listPane = new ScrollPane();
		listPane.setContent(orderBox);
		listPane.setMinWidth(920.0);
		listPane.getStyleClass().add("edge-to-edge");		
		orderBox.setTranslateX(50.0);

		radioBox.setId("radio");

		WebMarketerPilot webMarketerPilot = new WebMarketerPilot(id);
		VBox vBox = new VBox();
		vBox.getChildren().addAll(radioBox,listPane);
		vBox.setPrefWidth(500);
		
		this.add(webMarketerPilot, 0, 0);
		this.add(vBox, 1, 0);

		vBox.getStylesheets().add(getClass().getResource("MarketerOrdersPane.css").toExternalForm());
		this.getStylesheets().add(getClass().getResource("MarketerOrderPane2.css").toExternalForm());

	}

	/**
	 * 初始化单选框按钮组件
	 */
	private void initRadioButton() {
		toggleGroup = new ToggleGroup();
		unexecutedButton = new RadioButton("未执行订单");
		unexecutedButton.setUserData("正常");
		unexecutedButton.setToggleGroup(toggleGroup);
		unexecutedButton.setId("all");
		abnormalButton = new RadioButton("异常订单");
		abnormalButton.setUserData("异常");
		abnormalButton.setToggleGroup(toggleGroup);
		abnormalButton.setId("all");
		toggleGroup.selectedToggleProperty()
				.addListener((ObservableValue<? extends Toggle> obvalue, Toggle oldToggle, Toggle newToggle) -> {
					if (oldToggle.getUserData().equals(newToggle.getUserData())) {
						return;
					} else {
						if (newToggle.getUserData().equals("异常订单")) {
							try {
								buildOrderBox(BLFactory.getInstance().getOrderBLService().getAbnormalOrdersOfToday());
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						} else if (newToggle.getUserData().equals("未执行订单")) {
							try {
								List <OrderVO> unexecutedList = new ArrayList<OrderVO>();
								List <HotelVO> hotelList = BLFactory.getInstance().getUserBLService().getHotelList();
								for(HotelVO hotel: hotelList){
									for(OrderVO order: BLFactory.getInstance().getOrderBLService().filterHotelList(hotel.hotelID, "未执行订单")){
										if(order.checkInTime.equals(LocalDate.now())){
											unexecutedList.add(order);
										}
									}
								}
								buildOrderBox(unexecutedList);
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}

					}

				});

		
		radioBox = new AnchorPane();
		radioBox.getChildren().addAll(unexecutedButton, abnormalButton);
		AnchorPane.setLeftAnchor(abnormalButton, 53.0);
		AnchorPane.setTopAnchor(abnormalButton, 30.0);
		AnchorPane.setLeftAnchor(unexecutedButton, 165.0);
		AnchorPane.setTopAnchor(unexecutedButton, 30.0);

	}

	/**
	 * @param orderList
	 * 建立订单列表面板
	 */
	private void buildOrderBox(List<OrderVO> orderList) {

		orderBox.getChildren().clear();
		for (OrderVO vo : orderList) {
			orderBox.getChildren().add(new MarketerOrderCell(vo));
		}
	}

}
