package presentation.userui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * @author samperson1997
 * 信用充值对话框
 *
 */
public class CreditDialog extends Dialog {

	final int COLUMN_COUNT = 8;
	private GridPane gridPane;
	private Text title;
	private Text creditTitle;
	private TextField creditTextField;
	private HBox creditBox;

	/**
	 * @param customerID
	 * 信用充值对话框
	 * 
	 */
	public CreditDialog(int customerID) {
		
		super();

		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		title = new Text("信用充值");
		creditTitle = new Text("增加信用额度: ");
		creditTextField = new TextField();
		creditTextField.setPrefColumnCount(COLUMN_COUNT);
		creditTextField.setTooltip(new Tooltip("输入大于0的整数"));

		creditBox = new HBox();
		creditBox.getChildren().addAll(creditTitle, creditTextField);
		gridPane.add(title, 0, 0, 1, 1);
		gridPane.add(creditBox, 0, 1, 1, 1);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

		this.getDialogPane().getButtonTypes().addAll(ok, cancel);
		Callback<ButtonType, Integer> resultConverter = new Callback<ButtonType, Integer>() {

			@Override
			public Integer call(ButtonType param) {
				int addCredit = 0;
				if (param.getButtonData() == ButtonData.OK_DONE) {
					addCredit = Integer.valueOf(creditTextField.getText());
					return addCredit;
				} else {
					return 0;
				}
			}
		};
		this.setResultConverter(resultConverter);
		this.getDialogPane().setContent(gridPane);
	}
}
