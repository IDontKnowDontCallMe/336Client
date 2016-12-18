package presentation.customerui;

import java.time.LocalDate;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * @author samperson1997
 * 注册生日会员对话框
 *
 */
public class BirthVIPDialog extends Dialog {
	private DatePicker birthdayPicker;
	private GridPane gridPane;

	public BirthVIPDialog() {
		super();

		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		gridPane.add(new Text("注册生日会员"), 0, 0, 1, 1);
		gridPane.add(new Text("请选择生日"), 0, 1, 1, 1);

		birthdayPicker = new DatePicker();
		gridPane.add(birthdayPicker, 1, 1, 1, 1);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(ok, cancel);

		Callback<ButtonType, LocalDate> resultConverter = new Callback<ButtonType, LocalDate>() {

			@Override
			public LocalDate call(ButtonType param) {
				return birthdayPicker.getValue();
			}
		};
		this.setResultConverter(resultConverter);

		this.getDialogPane().setContent(gridPane);
	}

}
