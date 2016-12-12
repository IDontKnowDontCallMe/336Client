package presentation.orderui;

import java.rmi.RemoteException;
import java.util.Optional;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vo.CommentVO;
import vo.OrderVO;

public class CustomerOrderCell extends GridPane {

	OrderVO orderVO;

	Text hotelText;
	Text roomText;
	Text checkInText;
	Text checkOutText;
	Text numText;
	Text childrenText;
	Text totalText;
	Text stateText;
	Text revokeText;
	Button button;

	public CustomerOrderCell(OrderVO orderVO) {
		super();
		this.orderVO = orderVO;

		hotelText = new Text(orderVO.hotelName);
		this.add(hotelText, 0, 0, 3, 1);
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

		if (orderVO.orderState.equals("正常")) {
			button = new Button("撤销");
			button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				try {
					if (BLFactory.getInstance().getOrderBLService().changeOrderState(orderVO.orderID, "已撤销")) {
						System.out.println("revoke");
						revokeText = new Text("已撤销");
						this.getChildren().remove(button);
						this.add(revokeText, 4, 2, 1, 1);
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
							// presentation层无法更改orderVO，bl层需要在addComment方法里添加方法将订单的hasComment属性改为true
							System.out.println("comment");
							this.getChildren().remove(button);
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
			this.add(button, 4, 2, 1, 1);
		}

		this.setHgap(10);
		this.setVgap(20);
		this.getStylesheets().add(getClass().getResource("CustomerOrderCell.css").toExternalForm());
	}

}
