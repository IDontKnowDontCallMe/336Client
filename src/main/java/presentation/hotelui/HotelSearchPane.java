package presentation.hotelui;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import presentation.mainui.TheMainFrame;
import vo.AreaVO;
import vo.HotelVO;
import vo.SearchConditionVO;

public class HotelSearchPane extends VBox {

	private int customerID;
	private AreaVO areaVO;
	private List<HotelVO> list;
	private HotelListPane hotelListPane;

	public HotelSearchPane(AreaVO areaVO, int customerID) throws RemoteException {
		this.customerID = customerID;
		this.areaVO = areaVO;

		this.setSpacing(20);
		initSearchPane();
		initSortingPane();
		initHotelListPane();
		
		this.getStylesheets().add(getClass().getResource("HotelSearchPane.css").toExternalForm());
	}

	private void initSearchPane() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(15);
		gridPane.setVgap(15);

		Text hotelNameText = new Text("酒店名称");
		TextField hotelNameTextField = new TextField();
		gridPane.add(new HBox(hotelNameText, hotelNameTextField), 0, 0, 3, 1);
		Button searchButton = new Button("搜索");

		Button backButton = new Button("退出搜索");
		gridPane.add(searchButton, 3, 0, 1, 1);
		gridPane.add(backButton, 4, 0, 1, 1);

		Text checkInText = new Text("入: ");
		Text checkOutText = new Text("离: ");
		DatePicker checkInDatePicker = new DatePicker();
		DatePicker checkOutDatePicker = new DatePicker();

		checkInDatePicker.setValue(LocalDate.now());
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

		Button addButton = new Button("+");
		Button subButton = new Button("-");
		subButton.setDisable(true);
		TextField numTextField = new TextField("1");
		numTextField.setEditable(false);
		numTextField.setPrefColumnCount(2);

		addButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			numTextField.setText(String.valueOf(Integer.parseInt(numTextField.getText()) + 1));

			if (Integer.parseInt(numTextField.getText()) == 1) {
				subButton.setDisable(true);
			} else {
				subButton.setDisable(false);
			}
		});

		subButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			numTextField.setText(String.valueOf(Integer.parseInt(numTextField.getText()) - 1));
			if (Integer.parseInt(numTextField.getText()) == 1) {
				subButton.setDisable(true);
			} else {
				subButton.setDisable(false);
			}
		});

		gridPane.add(new HBox(checkInText, checkInDatePicker), 0, 1, 2, 1);
		gridPane.add(new HBox(checkOutText, checkOutDatePicker), 2, 1, 2, 1);
		gridPane.add(new HBox(subButton, numTextField, addButton), 4, 1, 1, 1);

		ObservableList<String> roomTypeList = FXCollections.observableArrayList("不限", "单人间(1人)", "双人房(2人)", "三人房(3人)");
		ComboBox<String> roomTypeChoiceBox = new ComboBox<>(roomTypeList);
		roomTypeChoiceBox.getSelectionModel().select(0);
		ObservableList<String> priceIntervalList = FXCollections.observableArrayList("不限", "0 - 150 ¥", "151 - 300 ¥",
				"301 - 450 ¥", "451 - 600 ¥", "601 ¥ 以上");
		ComboBox<String> priceIntervalChoiceBox = new ComboBox<>(priceIntervalList);
		priceIntervalChoiceBox.getSelectionModel().select(0);

		ObservableList<String> scoreList = FXCollections.observableArrayList("不限", "2星以上", "3星以上", "4星以上", "5星");
		ComboBox<String> scoreChoiceBox = new ComboBox<>(scoreList);
		scoreChoiceBox.getSelectionModel().select(0);

		ObservableList<String> commentScoreList = FXCollections.observableArrayList("不限", "2分以上", "3分以上", "4分以上");
		ComboBox<String> commentScoreChoiceBox = new ComboBox<>(commentScoreList);
		commentScoreChoiceBox.getSelectionModel().select(0);

		CheckBox isInteractiveCheckBox = new CheckBox("是联合搜索条件");
		CheckBox getBookedHotelCheckBox = new CheckBox("只搜索预订过的酒店");
		isInteractiveCheckBox.setSelected(true);

		gridPane.add(new HBox(new Text("房间类型"), roomTypeChoiceBox), 0, 2, 1, 1);
		gridPane.add(new HBox(new Text("价格区间"), priceIntervalChoiceBox), 1, 2, 1, 1);
		gridPane.add(new HBox(new Text("星级"), scoreChoiceBox), 2, 2, 1, 1);
		gridPane.add(new HBox(new Text("评分"), commentScoreChoiceBox), 3, 2, 1, 1);
		gridPane.add(isInteractiveCheckBox, 0, 3, 1, 1);
		gridPane.add(getBookedHotelCheckBox, 1, 3, 1, 1);

		searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			boolean hasRoomTypeLimit = false;
			boolean hasPriceLimit = false;
			boolean hasScoreLimit = false;
			boolean hasCommentScoreLimit = false;

			int minPrice = 0;
			int maxPrice = 0;
			if (roomTypeChoiceBox.getValue().equals("不限")) {
				hasRoomTypeLimit = false;
			} else {
				hasRoomTypeLimit = true;
			}

			if (priceIntervalChoiceBox.getValue().equals("不限")) {
				hasPriceLimit = false;
			} else {
				hasPriceLimit = true;
				switch (priceIntervalChoiceBox.getSelectionModel().selectedIndexProperty().getValue()) {
				case 1:
					minPrice = 0;
					maxPrice = 150;
					break;
				case 2:
					minPrice = 151;
					maxPrice = 300;
					break;
				case 3:
					minPrice = 301;
					maxPrice = 450;
					break;
				case 4:
					minPrice = 451;
					maxPrice = 600;
					break;
				case 5:
					minPrice = 601;
					maxPrice = Integer.MAX_VALUE;
					break;
				default:
					break;
				}
			}

			if (scoreChoiceBox.getValue().equals("不限")) {
				hasScoreLimit = false;
			} else {
				hasScoreLimit = true;
			}

			if (commentScoreChoiceBox.getValue().equals("不限")) {
				hasCommentScoreLimit = false;
			} else {
				hasCommentScoreLimit = true;
			}

			SearchConditionVO searchConditionVO = new SearchConditionVO(customerID, hotelNameTextField.getText(),
					hasRoomTypeLimit, roomTypeChoiceBox.getSelectionModel().selectedIndexProperty().getValue(),
					hasPriceLimit, minPrice, maxPrice, true, checkInDatePicker.getValue(),
					checkOutDatePicker.getValue(), Integer.parseInt(numTextField.getText()), hasScoreLimit,
					scoreChoiceBox.getSelectionModel().selectedIndexProperty().getValue() + 1, hasCommentScoreLimit,
					commentScoreChoiceBox.getSelectionModel().selectedIndexProperty().getValue() + 1,
					getBookedHotelCheckBox.isSelected(), isInteractiveCheckBox.isSelected());

			try {
				list = BLFactory.getInstance().getHotelBLService().search(areaVO, searchConditionVO);
				changeHotelListPane();
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		});

		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});

		this.getChildren().add(gridPane);

	}

	private void initSortingPane() {
		HBox hBox = new HBox();
		hBox.setSpacing(15);

		GridPane priceGridPane = new GridPane();
		Text priceText = new Text("价格排序");
		Button priceLowToHighButton = new Button("↑");
		Button priceHighToLowButton = new Button("↓");
		priceGridPane.add(priceText, 0, 0, 1, 2);
		priceGridPane.add(priceLowToHighButton, 1, 0, 1, 1);
		priceGridPane.add(priceHighToLowButton, 1, 1, 1, 1);

		priceLowToHighButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				list = BLFactory.getInstance().getHotelBLService().sort(customerID, "价格从低至高");
				changeHotelListPane();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		priceHighToLowButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				list = BLFactory.getInstance().getHotelBLService().sort(customerID, "价格从高至低");
				changeHotelListPane();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		GridPane scoreGridPane = new GridPane();
		Text scoreText = new Text("星级排序");
		Button scoreLowToHighButton = new Button("↑");
		Button scoreHighToLowButton = new Button("↓");
		scoreGridPane.add(scoreText, 0, 0, 1, 2);
		scoreGridPane.add(scoreLowToHighButton, 1, 0, 1, 1);
		scoreGridPane.add(scoreHighToLowButton, 1, 1, 1, 1);

		scoreLowToHighButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				list = BLFactory.getInstance().getHotelBLService().sort(customerID, "星级从低至高");
				changeHotelListPane();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		scoreHighToLowButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				list = BLFactory.getInstance().getHotelBLService().sort(customerID, "星级从高至低");
				changeHotelListPane();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		GridPane commentGridPane = new GridPane();
		Text commentText = new Text("评分排序");
		Button commentLowToHighButton = new Button("↑");
		Button commentHighToLowButton = new Button("↓");
		commentGridPane.add(commentText, 0, 0, 1, 2);
		commentGridPane.add(commentLowToHighButton, 1, 0, 1, 1);
		commentGridPane.add(commentHighToLowButton, 1, 1, 1, 1);

		commentLowToHighButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				list = BLFactory.getInstance().getHotelBLService().sort(customerID, "评分从低至高");
				changeHotelListPane();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		commentHighToLowButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			try {
				list = BLFactory.getInstance().getHotelBLService().sort(customerID, "评分从高至低");
				changeHotelListPane();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		hBox.getChildren().addAll(priceGridPane, scoreGridPane, commentGridPane);
		this.getChildren().add(hBox);
	}

	private void initHotelListPane() throws RemoteException {
		list = BLFactory.getInstance().getHotelBLService().getHotelVOsOfArea(areaVO, customerID);
		hotelListPane = new HotelListPane(list, customerID);
		this.getChildren().add(hotelListPane);
	}

	private void changeHotelListPane() throws RemoteException {
		this.getChildren().remove(hotelListPane);
		hotelListPane = new HotelListPane(list, customerID);

		this.getChildren().add(hotelListPane);
	}

}