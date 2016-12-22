package presentation.orderui;

import java.rmi.RemoteException;
import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import vo.OrderVO;

/**
 * @author samperson1997
 * 酒店工作人员订单列表单元格
 *
 */
public class HotelOrderCell extends Pane {

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

	/**
	 * @param orderVO
	 * @throws RemoteException
	 * 酒店工作人员订单列表单元格
	 */
	public HotelOrderCell(OrderVO orderVO) throws RemoteException {
		
		super();
		this.orderVO = orderVO;
		this.setId("gridp");
		orderID = new Label(Integer.toString(orderVO.orderID));
		orderID.setLayoutX(12.0);
		orderID.setLayoutY(3.0);
		orderID.setId("orderid");
		this.getChildren().add(orderID);
		customerName = new Label(orderVO.customerName);
		this.getChildren().add(customerName);
		customerName.setLayoutX(16.0);
		customerName.setLayoutY(41.0);
		customerName.setId("customername");
		customerPhone = new Label("联系方式: "+orderVO.customerPhoneNumber);
		this.getChildren().add(customerPhone);
		customerPhone.setLayoutX(166.0);
		customerPhone.setLayoutY(51.0);
		customerPhone.setId("customerPhone");
		state = new Label(orderVO.orderState);
		this.getChildren().add(state);
		state.setLayoutX(15.0);
		state.setLayoutY(132.0);
		state.setId("state");
		roomName = new Label(orderVO.roomName);
		this.getChildren().add(roomName);
		roomName.setLayoutX(521.0);
		roomName.setLayoutY(50.0);
		roomName.setId("roomname");
		total = new Label("¥" + String.valueOf(orderVO.total));
		this.getChildren().add(total);
		total.setLayoutX(618.0);
		total.setLayoutY(78.0);
		total.setId("total");
		checkInTime = new Label("入 " + orderVO.checkInTime);
		this.getChildren().add(checkInTime);
		checkInTime.setLayoutX(13.0);
		checkInTime.setLayoutY(96.0);
		checkInTime.setId("checkintime");
		checkOutTime = new Label("离" + orderVO.checkOutTime);
		this.getChildren().add(checkOutTime);
		checkOutTime.setLayoutX(250.0);
		checkOutTime.setLayoutY(96.0);
		checkOutTime.setId("checkouttime");
		num = new Label(String.valueOf(orderVO.roomNum) + "间");
		this.getChildren().add(num);
		num.setLayoutX(521.0);
		num.setLayoutY(85.0);
		num.setId("num");
		children = new Label(orderVO.hasChildren ? "有" : "无" + "儿童");
		this.getChildren().add(children);
		children.setLayoutX(521.0);
		children.setLayoutY(120.0);
		children.setId("children");

		setButton(orderVO.orderState);
		if (button != null) {
			button.setId("btn");
			this.getChildren().add(button);
			button.setLayoutX(711.0);
			button.setLayoutY(70.0);
		}

		this.getStylesheets().add(getClass().getResource("HotelOrderCell.css").toExternalForm());

	}

	/**
	 * @param state
	 * @throws RemoteException
	 * 设置按钮
	 * 
	 */
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

	/**
	 * 设置执行按钮
	 */
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

	/**
	 * 设置延迟入住按钮
	 */
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

	/**
	 * 设置办理退房按钮
	 */
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
