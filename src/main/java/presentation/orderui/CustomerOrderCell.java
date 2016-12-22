package presentation.orderui;

import java.rmi.RemoteException;
import java.util.Optional;
import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import vo.CommentVO;
import vo.OrderVO;

/**
 * @author samperson1997
 * 客户订单列表单元格
 *
 */
public class CustomerOrderCell extends Pane {

	OrderVO orderVO;

	private Label hotelName;
	private Label roomName;
	private Label checkInTime;
	private Label checkOutTime;
	private Label num;
	private Label children;
	private Label total;
	private Label state;
	private Label orderID;
	private Button button;

	/**
	 * @param orderVO
	 * 客户订单列表单元格
	 */
	public CustomerOrderCell(OrderVO orderVO) {
		
		super();
		this.orderVO = orderVO;
		this.setId("pane");
		orderID = new Label("订单编号   " + Integer.toString(orderVO.orderID));
		this.getChildren().add(orderID);
		orderID.setLayoutX(12.0);
		orderID.setLayoutY(3.0);
		orderID.setId("orderid");
		hotelName = new Label(orderVO.hotelName);
		this.getChildren().add(hotelName);
		hotelName.setLayoutX(16.0);
		hotelName.setLayoutY(41.0);
		hotelName.setId("hotelname");
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
		checkInTime = new Label("入住时间  " + orderVO.checkInTime);
		this.getChildren().add(checkInTime);
		checkInTime.setLayoutX(13.0);
		checkInTime.setLayoutY(96.0);
		checkInTime.setId("checkintime");
		checkOutTime = new Label("退房时间  " + orderVO.checkOutTime);
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

		if (orderVO.orderState.equals("正常")) {
			button = new Button("撤销");
			button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				try {
					if (BLFactory.getInstance().getOrderBLService().changeOrderState(orderVO.orderID, "已撤销")) {
						state.setText("已撤销");
						this.getChildren().remove(button);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			});
		} else if (orderVO.orderState.equals("已执行已离店") && orderVO.hasComment == false) {
			button = new Button("评价");
			button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				Dialog<CommentVO> commentDialog = new CommentDialog(orderVO);
				Optional<CommentVO> result = commentDialog.showAndWait();

				if (result.isPresent()) {
					try {
						if (BLFactory.getInstance().getHotelBLService().addComment(result.get())) {
							if (BLFactory.getInstance().getOrderBLService().changeOrderState(orderVO.orderID, "已评价")) {
								System.out.println("comment");
								this.getChildren().remove(button);
							}

						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}

			});
		} else {
			button = null;
		}

		if (button != null) {
			this.getChildren().add(button);
			button.setLayoutX(720.0);
			button.setLayoutY(70.0);
			button.setId("revoke");
		}

		this.getStylesheets().add(getClass().getResource("CustomerOrderCell.css").toExternalForm());
	}

}
