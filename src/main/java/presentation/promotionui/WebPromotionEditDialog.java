package presentation.promotionui;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
import vo.WebPromotionVO;

/**
 * @author samperson1997
 * 编辑网站促销策略对话框
 *
 */
public class WebPromotionEditDialog extends Dialog {

	WebPromotionVO webPromotionVO;

	final int COLUMN_COUNT = 10;
	final int YEAR_COLUMN_COUNT = 3;
	final int LESS_COLUMN_COUNT = 2;
	final int BOX_SPACING = 15;

	private HBox paramBox;
	private HBox startBox;
	private HBox endBox;
	private HBox discountBox;
	private VBox promotionBox;
	private Text editText;
	private Label startTimeLabel;
	private Label endTimeLabel;
	private Label businessCircleLabel;
	private Label discountLabel;
	private ComboBox<String> cityBox;
	private ComboBox<String> businessCircleBox;

	private TextField discountTextField;
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	private GridPane gridPane;

	/**
	 * @param webPromotionVO
	 * 编辑网站促销策略对话框
	 * 
	 */
	public WebPromotionEditDialog(WebPromotionVO webPromotionVO) {
		
		super();

		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		this.webPromotionVO = webPromotionVO;
		editText = new Text("编辑网站促销策略: ");
		gridPane.add(editText, 0, 0, 1, 1);

		promotionBox = new VBox();
		promotionBox.setSpacing(BOX_SPACING);
		promotionBox.getChildren().clear();
		promotionBox.setAlignment(Pos.TOP_LEFT);

		switch (webPromotionVO.promotionType) {
		case ("特定时间促销策略"):
			promotionBox.getChildren().clear();
			showTimePromotionBox();
			break;
		case ("特定商圈促销策略"):
			promotionBox.getChildren().clear();
			showBusinessCirclePromotionBox();
			break;
		}
		gridPane.add(promotionBox, 0, 1, 1, 1);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(ok, cancel);

		Callback<ButtonType, WebPromotionVO> resultConverter = new Callback<ButtonType, WebPromotionVO>() {

			@Override
			public WebPromotionVO call(ButtonType param) {
				LocalDate startDate = null;
				LocalDate endDate = null;
				String cityName = null;
				String businessCircleName = null;
				double discount = 1.0;

				if (param.getButtonData() == ButtonData.OK_DONE) {
					if (startDatePicker != null) {
						startDate = startDatePicker.getValue();
					}
					if (endDatePicker != null) {
						endDate = endDatePicker.getValue();
					}
					if (businessCircleBox != null && cityBox != null) {
						businessCircleName = businessCircleBox.getValue().toString();
						cityName = cityBox.getValue().toString();
					}

					if (discountTextField.getText() != null) {
						discount = Double.valueOf(discountTextField.getText());
					}
					return new WebPromotionVO(webPromotionVO.promotionType, startDate, endDate, cityName,
							businessCircleName, discount);
				} else {
					return null;
				}
			}
		};
		this.setResultConverter(resultConverter);

		this.getDialogPane().setContent(gridPane);
	}

	/**
	 * 显示折扣
	 * 
	 */
	public void showDiscountBox() {
		
		discountLabel = new Label();
		if (startDatePicker != null) {
			discountLabel.setText("输入折扣: ");
		}
		if (businessCircleBox != null && cityBox != null) {
			discountLabel.setText("会员每升一级在该商圈订酒店时折扣减少: ");
		}
		discountTextField = new TextField();
		discountTextField.setPrefColumnCount(COLUMN_COUNT);
		discountTextField.setTooltip(new Tooltip("输入不大于1的小数"));
		discountTextField.setText(String.valueOf(webPromotionVO.discount));

		discountBox = new HBox();
		discountBox.setSpacing(BOX_SPACING);
		discountBox.getChildren().clear();
		discountBox.getChildren().addAll(discountLabel, discountTextField);

		promotionBox.getChildren().add(discountBox);
	}

	/**
	 * 显示特定时间促销策略
	 * 
	 */
	public void showTimePromotionBox() {
		
		startTimeLabel = new Label("选择起始时间: ");
		endTimeLabel = new Label("选择结束时间: ");
		startDatePicker = new DatePicker();
		startDatePicker.setValue(webPromotionVO.startTime);
		endDatePicker = new DatePicker();
		endDatePicker.setValue(webPromotionVO.endTime);

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

		promotionBox.getChildren().addAll(startBox, endBox);
		showDiscountBox();

	}

	/**
	 * 显示特定商圈促销策略
	 * 
	 */
	public void showBusinessCirclePromotionBox() {
		
		businessCircleLabel = new Label("选择城市和商圈");
		ObservableList<String> cityList = FXCollections.observableArrayList("南京");
		cityBox = new ComboBox<String>(cityList);
		cityBox.getSelectionModel().select(0);

		ObservableList<String> businessCircleList = FXCollections.observableArrayList("栖霞区", "鼓楼区", "秦淮区");
		businessCircleBox = new ComboBox<String>(businessCircleList);
		int index = -1;
		for (String s : businessCircleList) {
			index++;
			if (s.equals(webPromotionVO.businessCircleName)) {
				break;
			}
		}
		businessCircleBox.getSelectionModel().select(index);

		paramBox = new HBox();
		paramBox.setSpacing(BOX_SPACING);
		paramBox.getChildren().clear();
		paramBox.getChildren().addAll(businessCircleLabel, cityBox, businessCircleBox);

		promotionBox.getChildren().add(paramBox);
		showDiscountBox();
	}
}
