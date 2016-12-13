package presentation.orderui;

import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vo.OrderVO;

public class MarketerOrderCell extends GridPane {

	OrderVO orderVO;

	private Text orderIDText;
	private Text customerIDText;
	private Text customerNameText;
	private Text hotelText;
	private Text roomText;
	private Text numText;
	private Text totalText;
	private Button recoverAllButton;
	private Button recoverHalfButton;

	public MarketerOrderCell(OrderVO orderVO) {
		super();
		this.orderVO = orderVO;

		orderIDText = new Text(String.valueOf(orderVO.orderID));
		this.add(orderIDText, 0, 0, 3, 1);
		customerIDText = new Text(String.valueOf(orderVO.customerID));
		this.add(customerIDText, 0, 1, 2, 1);
		customerNameText = new Text(orderVO.customerName);
		this.add(customerNameText, 2, 1, 1, 1);
		hotelText = new Text(orderVO.hotelName);
		this.add(hotelText, 0, 2, 1, 1);
		roomText = new Text(orderVO.roomName);
		this.add(roomText, 1, 2, 1, 1);
		totalText = new Text("¥" + String.valueOf(orderVO.total));
		this.add(totalText, 3, 0, 1, 1);

		numText = new Text(String.valueOf(orderVO.roomNum) + "间");
		this.add(numText, 2, 2, 1, 1);

		setButton();

		this.setHgap(10);
		this.setVgap(20);

	}

	private void setButton() {
		recoverAllButton = new Button("恢复一半");
		recoverHalfButton = new Button("恢复全部");
		this.add(recoverHalfButton, 3, 3, 1, 1);
		this.add(recoverAllButton, 4, 3, 1, 1);
		recoverAllButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			try {
				if (BLFactory.getInstance().getOrderBLService().changeOrderState(orderVO.orderID, "恢复全部")) {
					this.getChildren().removeAll(recoverAllButton, recoverHalfButton);
					this.add(new Text("已处理"), 3, 3, 2, 1);
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		recoverHalfButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			try {
				if (BLFactory.getInstance().getOrderBLService().changeOrderState(orderVO.orderID, "恢复一半")) {
					this.getChildren().removeAll(recoverAllButton, recoverHalfButton);
					this.add(new Text("已处理"), 3, 3, 2, 1);
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

}
