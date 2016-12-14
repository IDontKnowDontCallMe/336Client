package presentation.userui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import vo.HotelVO;

public class AddHotelDialog extends Dialog {
	final int COLUMN_COUNT = 8;
	private GridPane gridPane;
	private Text title;
	private Text hotelNameText;
	private Text workerNameText;
	private Text phoneNumberText;
	private Text cityText;
	private TextField hotelNameTextField;
	private TextField workerNameTextField;
	private TextField phoneNumberTextField;
	private HBox hotelNameBox;
	private HBox workerNameBox;
	private HBox phoneNumberBox;
	private HBox cityBox;
	private ComboBox<String> cityComboBox;

	public AddHotelDialog() {
		super();

		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		title = new Text("新增酒店及酒店工作人员");
		hotelNameText = new Text("酒店名称: ");
		hotelNameTextField = new TextField();
		hotelNameTextField.setPrefColumnCount(COLUMN_COUNT);
		hotelNameTextField.setTooltip(new Tooltip("输入酒店名称"));
		hotelNameBox = new HBox();
		hotelNameBox.getChildren().addAll(hotelNameText, hotelNameTextField);

		workerNameText = new Text("酒店工作人员名称: ");
		workerNameTextField = new TextField();
		workerNameTextField.setPrefColumnCount(COLUMN_COUNT);
		workerNameTextField.setTooltip(new Tooltip("输入酒店工作人员名称"));
		workerNameBox = new HBox();
		workerNameBox.getChildren().addAll(workerNameText, workerNameTextField);

		phoneNumberText = new Text("酒店工作人员联系方式: ");
		phoneNumberTextField = new TextField();
		phoneNumberTextField.setPrefColumnCount(COLUMN_COUNT);
		phoneNumberTextField.setTooltip(new Tooltip("输入酒店工作人员联系方式"));
		phoneNumberBox = new HBox();
		phoneNumberBox.getChildren().addAll(phoneNumberText, phoneNumberTextField);

		cityText = new Text("选择酒店所在城市");
		ObservableList<String> cityList = FXCollections.observableArrayList("南京");
		cityComboBox = new ComboBox<String>(cityList);
		cityComboBox.getSelectionModel().select(0);
		cityBox = new HBox();
		cityBox.getChildren().addAll(cityText, cityComboBox);

		gridPane.add(title, 0, 0, 1, 1);
		gridPane.add(hotelNameBox, 0, 1, 1, 1);
		gridPane.add(workerNameBox, 0, 2, 1, 1);
		gridPane.add(phoneNumberBox, 0, 3, 1, 1);
		gridPane.add(cityBox, 0, 4, 1, 1);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

		this.getDialogPane().getButtonTypes().addAll(ok, cancel);
		Callback<ButtonType, HotelVO> resultConverter = new Callback<ButtonType, HotelVO>() {

			@Override
			public HotelVO call(ButtonType param) {
				if (param.getButtonData() == ButtonData.OK_DONE) {
					HotelVO vo = new HotelVO(-1, hotelNameTextField.getText(), cityComboBox.getValue().toString(), "无", null,
							null, null, 0, 0, workerNameTextField.getText(), phoneNumberTextField.getText(), 0, 0);
					return vo;
				} else {
					return null;
				}
			}
		};
		this.setResultConverter(resultConverter);
		this.getDialogPane().setContent(gridPane);
	}
}
