package presentation.promotionui;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import vo.HotelPromotionVO;

/**
 * @author samperson1997
 * 增加酒店促销策略对话框
 *
 */
public class HotelPromotionAddDialog extends Dialog {

	private ComboBox<String> hotelPromotionTypeChoiceBox;

	final int COLUMN_COUNT = 10;
	final int YEAR_COLUMN_COUNT = 3;
	final int LESS_COLUMN_COUNT = 2;
	final int BOX_SPACING = 15;
	private HBox chooseBox;
	private HBox paramBox;
	private HBox startBox;
	private HBox endBox;
	private HBox discountBox;
	private VBox promotionBox;
	private Label startTimeLabel;
	private Label endTimeLabel;
	private Label companyNameLabel;
	private Label minNumLabel;
	private Label discountLabel;
	private TextField companyNameTextField;
	private TextField minNumTextField;
	private TextField discountTextField;
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	private GridPane gridPane;

	/**
	 * @param hotelID
	 * 增加酒店促销策略对话框
	 * 
	 */
	public HotelPromotionAddDialog(int hotelID) {
		
		super();

		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		chooseBox = new HBox();
		chooseBox.setSpacing(BOX_SPACING);
		chooseBox.getChildren().clear();

		ObservableList<String> hotelPromotionTypeList = FXCollections.observableArrayList("预订多间促销策略", "特定时间促销策略",
				"合作企业促销策略", "客户生日促销策略");
		hotelPromotionTypeChoiceBox = new ComboBox<String>(hotelPromotionTypeList);

		promotionBox = new VBox();
		promotionBox.setSpacing(BOX_SPACING);
		promotionBox.getChildren().clear();
		promotionBox.setAlignment(Pos.TOP_LEFT);

		Text vacant = new Text("\n\n\n\n\n\n");
		promotionBox.getChildren().add(vacant);
		chooseBox.getChildren().addAll(new Text("选择新增酒店促销策略类型"), hotelPromotionTypeChoiceBox);
		gridPane.add(chooseBox, 0, 0, 1, 1);
		gridPane.add(promotionBox, 0, 1, 1, 1);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(ok, cancel);

		hotelPromotionTypeChoiceBox.getSelectionModel().selectedIndexProperty()
				.addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
					switch (new_val.intValue()) {
					case (0):
						promotionBox.getChildren().clear();
						showRoomPromotionBox();
						break;
					case (1):
						promotionBox.getChildren().clear();
						showTimePromotionBox();
						break;
					case (2):
						promotionBox.getChildren().clear();
						showCompanyPromotionBox();
						break;
					case (3):
						promotionBox.getChildren().clear();
						showBirthdayPromotionBox();
						break;
					}
				});

		Callback<ButtonType, HotelPromotionVO> resultConverter = new Callback<ButtonType, HotelPromotionVO>() {

			@Override
			public HotelPromotionVO call(ButtonType param) {
				LocalDate startDate = null;
				LocalDate endDate = null;
				String companyName = null;
				int minNum = -1;
				double discount = 1.0;

				if (param.getButtonData() == ButtonData.OK_DONE) {
					if (startDatePicker != null) {
						startDate = startDatePicker.getValue();
					}
					if (endDatePicker != null) {
						endDate = endDatePicker.getValue();
					}
					if (companyNameTextField != null) {
						companyName = companyNameTextField.getText();
					}
					if (minNumTextField != null) {
						minNum = Integer.valueOf(minNumTextField.getText());
					}
					if (discountTextField.getText() != null) {
						discount = Double.valueOf(discountTextField.getText());
					}
					return new HotelPromotionVO(hotelID, hotelPromotionTypeChoiceBox.getValue().toString(), startDate,
							endDate, companyName, minNum, discount);
				} else {
					return null;
				}
			}
		};
		this.setResultConverter(resultConverter);

		this.getDialogPane().setContent(gridPane);
	}

	/**
	 * @return
	 * 显示折扣
	 * 
	 */
	public HBox showDiscountBox() {
		
		discountLabel = new Label("输入折扣: ");
		discountTextField = new TextField();
		discountTextField.setPrefColumnCount(COLUMN_COUNT);
		discountTextField.setTooltip(new Tooltip("输入不大于1的小数"));
		discountBox = new HBox();
		discountBox.setSpacing(BOX_SPACING);
		discountBox.getChildren().clear();
		discountBox.getChildren().addAll(discountLabel, discountTextField);

		return discountBox;
	}

	/**
	 * 显示房间促销策略
	 * 
	 */
	public void showRoomPromotionBox() {
		
		minNumLabel = new Label("输入最小房间数量: ");
		minNumTextField = new TextField();
		minNumTextField.setPrefColumnCount(COLUMN_COUNT);
		minNumTextField.setTooltip(new Tooltip("输入不小于0的整数"));
		paramBox = new HBox();
		paramBox.setSpacing(BOX_SPACING);
		paramBox.getChildren().clear();
		paramBox.getChildren().addAll(minNumLabel, minNumTextField);

		promotionBox.getChildren().addAll(paramBox, showDiscountBox());

	}

	/**
	 * 显示特定时间促销策略
	 * 
	 */
	public void showTimePromotionBox() {
		
		startTimeLabel = new Label("选择起始时间: ");
		endTimeLabel = new Label("选择结束时间: ");
		startDatePicker = new DatePicker();
		endDatePicker = new DatePicker();

		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(startDatePicker.getValue())) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
						long p = ChronoUnit.DAYS.between(startDatePicker.getValue(), item);
						setTooltip(new Tooltip("持续" + p + "天"));
					}
				};
			}

		};
		endDatePicker.setDayCellFactory(dayCellFactory);

		startBox = new HBox();
		endBox = new HBox();

		startBox.getChildren().addAll(startTimeLabel, startDatePicker);
		endBox.getChildren().addAll(endTimeLabel, endDatePicker);

		promotionBox.getChildren().addAll(startBox, endBox, showDiscountBox());

	}

	/**
	 * 显示合作企业促销策略
	 * 
	 */
	public void showCompanyPromotionBox() {
		
		companyNameLabel = new Label("输入合作企业名称: ");
		companyNameTextField = new TextField();
		companyNameTextField.setPrefColumnCount(COLUMN_COUNT);

		paramBox = new HBox();
		paramBox.setSpacing(BOX_SPACING);
		paramBox.getChildren().clear();
		paramBox.getChildren().addAll(companyNameLabel, companyNameTextField);

		promotionBox.getChildren().addAll(paramBox, showDiscountBox());

	}

	/**
	 * 显示会员生日促销策略
	 * 
	 */
	public void showBirthdayPromotionBox() {
		
		promotionBox.getChildren().addAll(showDiscountBox());
	}

}
