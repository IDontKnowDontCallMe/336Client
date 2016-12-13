package presentation.orderui;

import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vo.OrderVO;

public class HotelOrderCell extends GridPane {

	OrderVO orderVO;

	private Text customerNameText;
	private Text customerPhoneText;
	private Text roomText;
	private Text checkInText;
	private Text checkOutText;
	private Text numText;
	private Text childrenText;
	private Text totalText;
	private Text stateText;
	private Button button;

	public HotelOrderCell(OrderVO orderVO) throws RemoteException {
		super();
		this.orderVO = orderVO;

		customerNameText = new Text(orderVO.customerName);
		this.add(customerNameText, 0, 0, 1, 1);
		customerPhoneText = new Text(orderVO.customerPhoneNumber);
		this.add(customerPhoneText, 1, 0, 2, 1);
		stateText = new Text(orderVO.orderState);
		this.add(stateText, 4, 0, 1, 1);
		roomText = new Text(orderVO.roomName);
		this.add(roomText, 0, 1, 2, 1);
		totalText = new Text("¥" + String.valueOf(orderVO.total));
		this.add(totalText, 4, 1, 1, 1);
		checkInText = new Text("入 " + orderVO.checkInTime);
		this.add(checkInText, 0, 2, 1, 1);
		checkOutText = new Text("离" + orderVO.checkOutTime);
		this.add(checkOutText, 1, 2, 1, 1);
		numText = new Text(String.valueOf(orderVO.roomNum) + "间");
		this.add(numText, 1, 1, 1, 1);
		childrenText = new Text(orderVO.hasChildren ? "有" : "无" + "儿童");
		this.add(childrenText, 2, 1, 1, 1);

		setButton(orderVO.orderState);
		if (button != null) {
			this.add(button, 4, 2, 1, 1);
		}

		this.setHgap(10);
		this.setVgap(20);
		this.getStylesheets().add(getClass().getResource("HotelOrderCell.css").toExternalForm());
	}

	private void setButton(String state) throws RemoteException {
		if (state.equals("正常")) {
			setExecutingButton();
		} else if (state.equals("异常")) {
			setDelayInButton();
		} else if (state.equals("已执行未离店")) {
			setLeavingButton();
		} else {
			button = null;
		}
	}

	private void setExecutingButton() {
		button = new Button("执行订单");
		
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			try {
				if (BLFactory.getInstance().getOrderBLService().changeOrderState(orderVO.orderID, "已执行未离店")) {
					stateText.setText("已执行未离店");
					this.getChildren().remove(button);
					setLeavingButton();
					System.out.println("excute.");
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
	}

	private void setDelayInButton() {
		button = new Button("延迟入住");
		
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			try {
				if (BLFactory.getInstance().getOrderBLService().changeOrderState(orderVO.orderID, "正常")) {
					stateText.setText("正常");
					this.getChildren().remove(button);
					setExecutingButton();
					System.out.println("delay.");
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
	}

	private void setLeavingButton() {
		button = new Button("办理退房");
		
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			try {
				if (BLFactory.getInstance().getOrderBLService().changeOrderState(orderVO.orderID, "已执行已离店")) {
					stateText.setText("已执行已离店");
					this.getChildren().remove(button);
					System.out.println("leave.");
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
	}

}
