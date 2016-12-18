package presentation.hotelui;

import java.rmi.RemoteException;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import presentation.mainui.TheMainFrame;
import presentation.orderui.ProducingOrderDialog;
import vo.HotelVO;
import vo.RoomVO;

/**
 * @author samperson1997
 * 酒店列表面板
 *
 */
public class HotelListPane extends ScrollPane {

	private int customerID;

	/**
	 * @param hotelList
	 * @param customerID
	 * @throws RemoteException
	 * 酒店列表面板
	 * 
	 */
	public HotelListPane(List<HotelVO> hotelList, int customerID) throws RemoteException {
		
		super();
		this.customerID = customerID;

		VBox vBox = new VBox();
		vBox.setSpacing(20);
		this.setContent(vBox);

		for (HotelVO vo : hotelList) {
			vBox.getChildren().add(new SimpleHotelCell(vo));
		}
	}

	/**
	 * @author samperson1997
	 * 酒店简略信息单元格
	 *
	 */
	public class SimpleHotelCell extends GridPane {

		private HotelVO hotelVO;

		private Button produceButton;
		private Button detailedButton;

		public SimpleHotelCell(HotelVO hotelVO) throws RemoteException {
			super();
			this.hotelVO = hotelVO;

			this.setHgap(10);
			this.setVgap(20);

			String booked = "";
			switch (hotelVO.bookedTag) {
			case (2):
				booked = "曾入住";
				break;
			case (1):
				booked = "预订过";
				break;
			case (0):
				booked = "未预订过";
				break;
			}

			this.add(new Text(hotelVO.hotelName), 1, 0, 1, 1);
			this.add(new Text(String.valueOf(hotelVO.commentScore) + "分"), 2, 0, 1, 1);
			this.add(new Text(booked), 4, 1, 1, 1);
			this.add(new Text(String.valueOf(hotelVO.score) + "星级"), 3, 0, 1, 1);
			this.add(new Text("¥" + String.valueOf(hotelVO.minPrice) + "起"), 4, 0, 1, 1);
			this.add(new Text(String.valueOf(hotelVO.address)), 1, 1, 3, 1);

			produceButton = new Button("下订单");
			this.add(produceButton, 5, 1, 1, 1);
			List<RoomVO> roomList = BLFactory.getInstance().getRoomBLService().getRoomTypeList(hotelVO.hotelID);
			produceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

				try {
					ProducingOrderDialog producingOrderDialog = new ProducingOrderDialog(customerID, hotelVO, roomList,
							0);
					producingOrderDialog.show();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			detailedButton = new Button("详情");
			this.add(detailedButton, 6, 1, 1, 1);
			detailedButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
				try {
					TheMainFrame.jumpTo(new CustomerHotelInfoPane(hotelVO.hotelID, customerID));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			});
		}
	}

}