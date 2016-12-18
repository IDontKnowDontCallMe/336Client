package presentation.orderui;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

import bussinesslogic.factory.BLFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import vo.CommentVO;
import vo.OrderVO;

/**
 * @author samperson1997
 * 评论对话框
 *
 */
public class CommentDialog extends Dialog {

	final int WIDTH_COUNT = 300;
	final int HEIGHT_COUNT = 200;
	GridPane gridPane;
	private TextArea commentTextArea;

	/**
	 * @param orderVO
	 * 评论对话框
	 * 
	 */
	public CommentDialog(OrderVO orderVO) {
		
		super();
		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		gridPane.add(new Text("评分"), 0, 0, 1, 1);

		ObservableList<Integer> scoreList = FXCollections.observableArrayList(1, 2, 3, 4, 5);
		ComboBox<Integer> scoreBox = new ComboBox<>(scoreList);

		gridPane.add(scoreBox, 1, 0, 1, 1);
		gridPane.add(new Text("评价"), 0, 1, 1, 1);

		commentTextArea = new TextArea();
		commentTextArea.setPrefSize(WIDTH_COUNT, HEIGHT_COUNT);
		commentTextArea.setWrapText(true);
		gridPane.add(commentTextArea, 1, 1, 1, 1);

		ButtonType ok = new ButtonType("确认", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(ok, cancel);

		Callback<ButtonType, CommentVO> resultConverter = new Callback<ButtonType, CommentVO>() {

			@Override
			public CommentVO call(ButtonType param) {

				if (param.getButtonData() == ButtonData.OK_DONE) {
					int hotelID = -1;
					try {
						hotelID = BLFactory.getInstance().getHotelBLService().getHotelIDbyOrderID(orderVO.orderID);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return new CommentVO(hotelID, orderVO.hotelName, orderVO.roomName, orderVO.customerID,
							commentTextArea.getText(),
							Double.valueOf(scoreBox.getSelectionModel().getSelectedIndex() + 1), LocalDateTime.now());
				} else {
					return null;
				}
			}
		};
		this.setResultConverter(resultConverter);

		this.getDialogPane().setContent(gridPane);
	}
}
