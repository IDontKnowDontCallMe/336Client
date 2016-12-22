package presentation.mainui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PasswordEditDialog extends Dialog {
	final int WIDTH_COUNT = 300;
	final int HEIGHT_COUNT = 200;
	GridPane gridPane;

	private PasswordField passwordField2;
	private PasswordField passwordField3;

	/**
	 * @param orderVO
	 *            修改密码对话框
	 * 
	 */
	public PasswordEditDialog(int id) {

		super();
		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		passwordField2 = new PasswordField();
		passwordField3 = new PasswordField();

		gridPane.add(new Text("修改密码"), 0, 0, 1, 1);
		gridPane.add(new Text("账号"), 0, 1, 1, 1);
		gridPane.add(new Text(String.valueOf(id)), 1, 1, 1, 1);
		gridPane.add(new Text("请输入新的密码"), 0, 3, 1, 1);
		gridPane.add(passwordField2, 1, 3, 1, 1);
		gridPane.add(new Text("请再次输入确认"), 0, 4, 1, 1);
		gridPane.add(passwordField3, 1, 4, 1, 1);

		Button ok = new Button("确认修改");
		ok.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

			try {
				if (passwordField2.getText().equals(passwordField3.getText())) {
					BLFactory.getInstance().getUserBLService().changePassword(id, passwordField2.getText());
					Stage popup3 = new Stage();
					popup3.setAlwaysOnTop(true);
					popup3.initModality(Modality.APPLICATION_MODAL);
					Button closeButton3 = new Button("确定");
					closeButton3.setOnAction(e -> {
						popup3.close();
						passwordField2.clear();
						passwordField3.clear();
					});
					VBox root3 = new VBox();
					root3.setAlignment(Pos.BASELINE_CENTER);
					root3.setSpacing(20);
					root3.getChildren().addAll(new Label("修改成功！"), closeButton3);
					Scene scene3 = new Scene(root3, 200, 90);
					popup3.setScene(scene3);
					popup3.setTitle("提示");
					popup3.show();
				} else {
					Stage popup3 = new Stage();
					popup3.setAlwaysOnTop(true);
					popup3.initModality(Modality.APPLICATION_MODAL);
					Button closeButton3 = new Button("确定");
					closeButton3.setOnAction(e -> {
						popup3.close();
					});
					VBox root3 = new VBox();
					root3.setAlignment(Pos.BASELINE_CENTER);
					root3.setSpacing(20);
					root3.getChildren().addAll(new Label("两次输入新密码不一致！"), closeButton3);
					Scene scene3 = new Scene(root3, 200, 90);
					popup3.setScene(scene3);
					popup3.setTitle("提示");
					popup3.show();
				}

			} catch (NumberFormatException | RemoteException e) {
				e.printStackTrace();
			}
		});
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		gridPane.add(ok, 0, 5, 1, 1);
		this.getDialogPane().getButtonTypes().add(cancel);

		this.getDialogPane().setContent(gridPane);
	}
}
