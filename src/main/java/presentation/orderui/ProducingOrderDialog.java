package presentation.orderui;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import bussinesslogic.factory.BLFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import presentation.mainui.TheMainFrame;
import vo.CalculationConditionVO;
import vo.CustomerVO;
import vo.HotelVO;
import vo.OrderVO;
import vo.RoomVO;

public class ProducingOrderDialog extends Dialog<OrderVO> {

	HotelVO hotelVO;
	List<RoomVO> roomList;

	GridPane gridPane;
	ChoiceBox<String> roomTypeChoiceBox;
	DatePicker checkInDatePicker;
	DatePicker checkOutDatePicker;
	TextField numTextField;
	Button addButton;
	Button subButton;
	CheckBox childrenCheckBox;
	Text totalText;

	int total;
	CalculationConditionVO calculationConditionVO;
	OrderVO orderVO;

	public ProducingOrderDialog(int customerID, HotelVO hotelVO, List<RoomVO> roomList, int roomIndex)
			throws RemoteException {
		this.hotelVO = hotelVO;
		this.roomList = roomList;

		initUI(customerID, roomIndex);
		this.getDialogPane().setContent(gridPane);
		updateTotal(customerID);

		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().add(cancel);

	}

	private void initUI(int customerID, int roomIndex) {
		gridPane = new GridPane();

		roomTypeChoiceBox = new ChoiceBox<String>();
		for (RoomVO vo : roomList) {
			roomTypeChoiceBox.getItems().add(vo.roomName);
		}
		roomTypeChoiceBox.getSelectionModel().select(roomIndex);
		gridPane.add(new Text("房间类型"), 0, 1, 1, 1);
		gridPane.add(roomTypeChoiceBox, 1, 1, 1, 1);

		roomTypeChoiceBox.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					updateTotal(customerID);
					totalText.setText(String.valueOf(total));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		checkInDatePicker = new DatePicker();
		checkInDatePicker.setValue(LocalDate.now());
		checkOutDatePicker = new DatePicker();
		checkOutDatePicker.setValue(LocalDate.now().plusDays(1));
		Callback<DatePicker, DateCell> checkInDayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(LocalDate.now()) || !item.isBefore(checkOutDatePicker.getValue())) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
					}
				};
			}
		};
		checkInDatePicker.setDayCellFactory(checkInDayCellFactory);
		Callback<DatePicker, DateCell> checkOutDayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (!item.isAfter(checkInDatePicker.getValue())) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
					}
				};
			}
		};
		checkOutDatePicker.setDayCellFactory(checkOutDayCellFactory);
		gridPane.add(new Text("入住时间"), 0, 2, 1, 1);
		gridPane.add(checkInDatePicker, 1, 2, 1, 1);
		gridPane.add(new Text("离店时间"), 0, 3, 1, 1);
		gridPane.add(checkOutDatePicker, 1, 3, 1, 1);
		checkInDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				try {
					updateTotal(customerID);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		checkOutDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				try {
					updateTotal(customerID);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		numTextField = new TextField();
		numTextField.setEditable(false);
		numTextField.setPrefColumnCount(2);
		numTextField.setText("1");
		addButton = new Button("+");
		subButton = new Button("-");
		subButton.setDisable(true);
		HBox numBox = new HBox();
		numBox.getChildren().addAll(new Text("数量:"), subButton, numTextField, addButton);
		gridPane.add(numBox, 1, 4, 1, 1);

		addButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			numTextField.setText(String.valueOf(Integer.parseInt(numTextField.getText()) + 1));

			if (Integer.parseInt(numTextField.getText()) == 1) {
				subButton.setDisable(true);
			} else {
				subButton.setDisable(false);
			}
			try {
				updateTotal(customerID);
				totalText.setText(String.valueOf(total));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		subButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			numTextField.setText(String.valueOf(Integer.parseInt(numTextField.getText()) - 1));
			if (Integer.parseInt(numTextField.getText()) == 1) {
				subButton.setDisable(true);
			} else {
				subButton.setDisable(false);
			}
			try {
				updateTotal(customerID);
				totalText.setText(String.valueOf(total));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		childrenCheckBox = new CheckBox("携带儿童");
		childrenCheckBox.setSelected(false);
		gridPane.add(childrenCheckBox, 0, 4, 1, 1);

		totalText = new Text("0");
		HBox totalBox = new HBox(new Text("优惠后总价:"), totalText);
		gridPane.add(totalBox, 0, 5, 1, 1);

		Button produceButton = new Button("生产订单");
		gridPane.add(produceButton, 0, 6, 1, 1);
		produceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			CustomerVO vo = null;
			try {
				vo = BLFactory.getInstance().getCustomerBLService().getCustomerInfo(customerID);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			OrderVO orderVO = new OrderVO(-1, vo.customerName, customerID, vo.phoneNumber, LocalDateTime.now(),
					hotelVO.hotelName, roomTypeChoiceBox.getValue(), Integer.valueOf(numTextField.getText()), 1,
					childrenCheckBox.isSelected(), checkInDatePicker.getValue(), LocalTime.MIDNIGHT.minusSeconds(1),
					checkOutDatePicker.getValue(), Integer.valueOf(totalText.getText()), "正常", false);

			try {
				if (BLFactory.getInstance().getOrderBLService().produceOrder(orderVO, updateTotal(customerID))) {
					Stage popup = new Stage();
					popup.setAlwaysOnTop(true);
					popup.initModality(Modality.APPLICATION_MODAL);
					Button closeButton = new Button("确定");
					closeButton.setOnAction(e -> {
						popup.close();
					});
					VBox root = new VBox();
					root.setAlignment(Pos.BASELINE_CENTER);
					root.setSpacing(20);
					root.getChildren().addAll(new Label("下订单成功，可至“我的订单”查看"), closeButton);
					Scene scene = new Scene(root, 200, 90);
					popup.setScene(scene);
					popup.setTitle("提示");
					popup.show();
					System.out.println("succeed in producing order");
					
					this.close();
				} else {
					Stage popup = new Stage();
					popup.setAlwaysOnTop(true);
					popup.initModality(Modality.APPLICATION_MODAL);
					Button closeButton = new Button("确定");
					closeButton.setOnAction(e -> {
						popup.close();
					});
					VBox root = new VBox();
					root.setAlignment(Pos.BASELINE_CENTER);
					root.setSpacing(20);
					root.getChildren().addAll(new Label("诶呦喂，出错啦，等会再试试吧"), closeButton);
					Scene scene = new Scene(root, 200, 90);
					popup.setScene(scene);
					popup.setTitle("提示");
					popup.show();
					System.out.println("failed.");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private CalculationConditionVO updateTotal(int customerID) throws RemoteException {

		int roomIndex = roomTypeChoiceBox.getSelectionModel().selectedIndexProperty().getValue();
		calculationConditionVO = new CalculationConditionVO(hotelVO.hotelID, roomList.get(roomIndex).roomID, customerID,
				checkInDatePicker.getValue(), checkOutDatePicker.getValue(), Integer.valueOf(numTextField.getText()),
				roomList.get(roomIndex).price, false, hotelVO.city, hotelVO.businessCircle);
		total = BLFactory.getInstance().getOrderBLService().calculateTotal(calculationConditionVO);
		totalText.setText(String.valueOf(total));

		return calculationConditionVO;
	}

}
