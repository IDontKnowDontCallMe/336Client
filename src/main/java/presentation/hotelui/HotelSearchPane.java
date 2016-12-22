package presentation.hotelui;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import presentation.mainui.CustomerPilot;
import presentation.mainui.TheMainFrame;
import vo.AreaVO;
import vo.HotelVO;
import vo.SearchConditionVO;

/**
 * @author samperson1997
 * 酒店搜索面板
 *
 */
public class HotelSearchPane extends GridPane {

	private int customerID;
	private AreaVO areaVO;
	private List<HotelVO> list;
	private HotelListPane hotelListPane;
	private Button searchButton;
	private Button backButton;
	private VBox vBox = new VBox();


	/**
	 * @param areaVO
	 * @param customerID
	 * @throws RemoteException
	 * 酒店搜索面板
	 * 
	 */
	public HotelSearchPane(AreaVO areaVO, int customerID) throws RemoteException {
		
		this.customerID = customerID;
		this.areaVO = areaVO;

		vBox.setSpacing(20);
		initSearchPane();
		initSortingPane();
		initHotelListPane();
		
		CustomerPilot customerPilot = new CustomerPilot(customerID);
		this.add(customerPilot, 0, 0);
		this.add(vBox, 1, 0);
		
		this.getStylesheets().add(getClass().getResource("HotelSearchPane.css").toExternalForm());
	}

	/**
	 * 酒店搜索面板初始化
	 * 
	 */
	private void initSearchPane() {
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(15);
		gridPane.setVgap(15);

		Label hotelNameText = new Label("  酒店名称");
		hotelNameText.setId("hotelt");
		TextField hotelNameTextField = new TextField();
		hotelNameTextField.setId("hotelIn");
		gridPane.add(new HBox(hotelNameText, hotelNameTextField), 0, 0, 3, 1);
		searchButton = new Button("搜索");
		searchButton.setId("search");

		backButton = new Button("退出搜索");
		backButton.setId("back");
		gridPane.add(searchButton, 2, 0, 1, 1);
		gridPane.add(backButton, 3, 0, 1, 1);

		Label checkInText = new Label("   入: ");
		checkInText.setId("in");
		Label checkOutText = new Label("         离: ");
		checkOutText.setId("out");
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

		Label label = new Label("房间数量: ");
		label.setId("roomnum");
		Button addButton = new Button("+");
		addButton.setId("ab");
		Button subButton = new Button("-");
		subButton.setId("sub");
		subButton.setDisable(true);
		TextField numTextField = new TextField("1");
		numTextField.setAlignment(Pos.CENTER);
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
		gridPane.add(new HBox(checkOutText, checkOutDatePicker), 1, 1, 2, 1);
		gridPane.add(new HBox(label,subButton, numTextField, addButton), 3, 1, 1, 1);

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

		Label roomtype = new Label("  房间类型 ");
		Label price = new Label("  价格区间 ");
		Label star = new Label("  星级 ");
		Label score = new Label("  评分 ");
		roomtype.setId("roomtype");
		price.setId("p");
		star.setId("s");
		score.setId("so");
		gridPane.add(new HBox(roomtype, roomTypeChoiceBox), 0, 2, 1, 1);
		gridPane.add(new HBox(price, priceIntervalChoiceBox), 1, 2, 1, 1);
		gridPane.add(new HBox(star, scoreChoiceBox), 2, 2, 1, 1);
		gridPane.add(new HBox(score, commentScoreChoiceBox), 3, 2, 1, 1);
		gridPane.add(isInteractiveCheckBox, 1, 3, 1, 1);
		gridPane.add(getBookedHotelCheckBox, 2, 3, 1, 1);

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

		vBox.getChildren().add(gridPane);

	}

	/**
	 * 酒店排序面板初始化
	 * 
	 */
	private void initSortingPane() {
		
		HBox hBox = new HBox();
		hBox.setSpacing(15);

		GridPane priceGridPane = new GridPane();
		Label priceText = new Label("   价格排序 ");
		priceText.setId("pt");
		Button priceSortButton = new Button("↑");
		priceSortButton.setId("pb");
		priceGridPane.add(priceText, 0, 0, 1, 2);

		priceGridPane.add(priceSortButton, 1, 0, 1, 1);
		
		priceSortButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			if(priceSortButton.getText().equals("↑")){
				try {
					list = BLFactory.getInstance().getHotelBLService().sort(customerID, "价格从低至高");
					changeHotelListPane();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				priceSortButton.setText("↓");
			}
			else if(priceSortButton.getText().equals("↓")){
				try {
					list = BLFactory.getInstance().getHotelBLService().sort(customerID, "价格从高到低");
					changeHotelListPane();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				priceSortButton.setText("↑");
			}
		});
		
		GridPane scoreGridPane = new GridPane();
		Label scoreText = new Label("星级排序 ");
		scoreText.setId("st");
		Button scoreSortButton = new Button("↑");
		scoreSortButton.setId("sb");
		scoreGridPane.add(scoreText, 0, 0, 1, 2);
		scoreGridPane.add(scoreSortButton, 1, 0, 1, 1);

		scoreSortButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			if(scoreSortButton.getText().equals("↑")){
				try {
					list = BLFactory.getInstance().getHotelBLService().sort(customerID, "星级从低至高");
					changeHotelListPane();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				scoreSortButton.setText("↓");
			}
			else if(scoreSortButton.getText().equals("↓")){
				try {
					list = BLFactory.getInstance().getHotelBLService().sort(customerID, "星级从高至低");
					changeHotelListPane();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				scoreSortButton.setText("↑");
			}
			
		});

		GridPane commentGridPane = new GridPane();
		Label commentText = new Label("评分排序 ");
		commentText.setId("ct");
		Button commentSortButton = new Button("↑");
		commentSortButton.setId("cb");
		commentGridPane.add(commentText, 0, 0, 1, 2);
		commentGridPane.add(commentSortButton, 1, 0, 1, 1);

		commentSortButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			if(commentSortButton.getText().equals("↓")){
				try {
					list = BLFactory.getInstance().getHotelBLService().sort(customerID, "评分从低至高");
					changeHotelListPane();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				commentSortButton.setText("↑");
			}
			else if(commentSortButton.getText().equals("↑")){
				try {
					list = BLFactory.getInstance().getHotelBLService().sort(customerID, "评分从高至低");
					changeHotelListPane();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				commentSortButton.setText("↓");
			}
		});

		hBox.getChildren().addAll(priceGridPane, scoreGridPane, commentGridPane);
		vBox.getChildren().add(hBox);
	}

	/**
	 * @throws RemoteException
	 * 酒店列表面板初始化
	 * 
	 */
	private void initHotelListPane() throws RemoteException {
		
		list = BLFactory.getInstance().getHotelBLService().getHotelVOsOfArea(areaVO, customerID);
		hotelListPane = new HotelListPane(list, customerID);
		vBox.getChildren().add(hotelListPane);
	}

	/**
	 * @throws RemoteException
	 * 修改酒店列表
	 */
	private void changeHotelListPane() throws RemoteException {
		
		vBox.getChildren().remove(hotelListPane);
		hotelListPane = new HotelListPane(list, customerID);

		vBox.getChildren().add(hotelListPane);
	}

}