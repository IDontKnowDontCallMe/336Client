package presentation.orderui;

import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import vo.OrderVO;

public class HotelOrderCell extends GridPane {

	OrderVO orderVO;

	private Label customerName;
	private Label customerPhone;
	private Label roomName;
	private Label checkInTime;
	private Label checkOutTime;
	private Label num;
	private Label children;
	private Label total;
	private Label state;
	private Button button;
	private Label orderID;

	public HotelOrderCell(OrderVO orderVO) throws RemoteException {
		super();
		this.orderVO = orderVO;
		this.setId("gridp");
		orderID = new Label(Integer.toString(orderVO.orderID));
		this.getChildren().add(orderID);
		customerName = new Label(orderVO.customerName);
		this.add(customerName, 0, 0, 1, 1);
		customerPhone = new Label(orderVO.customerPhoneNumber);
		this.add(customerPhone, 1, 0, 2, 1);
		state = new Label(orderVO.orderState);
		this.add(state, 4, 0, 1, 1);
		roomName = new Label(orderVO.roomName);
		this.add(roomName, 0, 1, 2, 1);
		total = new Label("¥" + String.valueOf(orderVO.total));
		this.add(total, 4, 1, 1, 1);
		checkInTime = new Label("入 " + orderVO.checkInTime);
		this.add(checkInTime, 0, 2, 1, 1);
		checkOutTime = new Label("离" + orderVO.checkOutTime);
		this.add(checkOutTime, 1, 2, 1, 1);
		num = new Label(String.valueOf(orderVO.roomNum) + "间");
		this.add(num, 1, 1, 1, 1);
		children = new Label(orderVO.hasChildren ? "有" : "无" + "儿童");
		this.add(children, 2, 1, 1, 1);

		setButton(orderVO.orderState);
		if (button != null) {
			button.setId("btn");
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
					state.setText("已执行未离店");
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
					state.setText("正常");
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
					state.setText("已执行已离店");
					this.getChildren().remove(button);
					System.out.println("leave.");
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
	}

}
