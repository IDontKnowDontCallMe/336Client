package presentation.orderui;

import java.rmi.RemoteException;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import presentation.mainui.TheMainFrame;
import vo.OrderVO;

public class MarketerOrdersPane extends VBox {

	private HBox titleBox;
	private Text title;
	private Button backButton;
	private ScrollPane listPane;
	private VBox orderBox;
	private List<OrderVO> orderList;

	public MarketerOrdersPane() throws RemoteException {
		initTitle();
		orderList = BLFactory.getInstance().getOrderBLService().getAbnormalOrdersOfToday();
		orderBox = new VBox();
		orderBox.setSpacing(15);
		buildOrderBox(orderList);
		listPane = new ScrollPane(orderBox);
		this.getChildren().addAll(titleBox, listPane);
		this.setPrefWidth(500);
		
		this.getStylesheets().add(getClass().getResource("MarketerOrdersPane.css").toExternalForm());
	}

	private void initTitle() {
		title = new Text("处理异常订单");
		backButton = new Button("返回");
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});

		titleBox = new HBox();
		titleBox.setSpacing(10);
		titleBox.setPrefWidth(500);
		titleBox.getChildren().addAll(title, backButton);
	}

	private void buildOrderBox(List<OrderVO> orderList) {

		orderBox.getChildren().clear();
		for (OrderVO vo : orderList) {
			orderBox.getChildren().add(new MarketerOrderCell(vo));
		}
	}

}
