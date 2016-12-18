package presentation.customerui;


import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * @author samperson1997
 * 注册企业会员对话框
 */
public class CompanyVIPDialog extends Dialog {
	private TextField companyTextField;
	private GridPane gridPane;

	public CompanyVIPDialog() {
		super();

		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		gridPane.add(new Text("注册企业会员"), 0, 0, 1, 1);
		gridPane.add(new Text("请输入企业名称"), 0, 1, 1, 1);

		companyTextField = new TextField();
		gridPane.add(companyTextField, 1, 1, 1, 1);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(ok, cancel);

		Callback<ButtonType, String> resultConverter = new Callback<ButtonType, String>() {

			@Override
			public String call(ButtonType param) {
				return companyTextField.getText();
			}
		};
		this.setResultConverter(resultConverter);

		this.getDialogPane().setContent(gridPane);
	}

}
