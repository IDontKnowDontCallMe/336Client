package presentation.hotelui;

import java.rmi.RemoteException;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import presentation.mainui.TheMainFrame;
import presentation.orderui.ProducingOrderDialog;
import vo.HotelVO;
import vo.RoomVO;

/**
 * @author samperson1997 酒店列表面板
 *
 */
public class HotelListPane extends ScrollPane {

	private int customerID;

	/**
	 * @param hotelList
	 * @param customerID
	 * @throws RemoteException
	 *             酒店列表面板
	 * 
	 */
	public HotelListPane(List<HotelVO> hotelList, int customerID) throws RemoteException {

		super();
		this.customerID = customerID;

		this.getStyleClass().add("edge-to-edge");
		this.setMinWidth(920.0);
		
		VBox vBox = new VBox();
		vBox.setSpacing(20);
		vBox.setTranslateX(50.0);
		this.setContent(vBox);

		for (HotelVO vo : hotelList) {
			vBox.getChildren().add(new SimpleHotelCell(vo));
		}

		this.getStylesheets().add(getClass().getResource("HotelListPane.css").toExternalForm());
	}

	/**
	 * @author samperson1997 酒店简略信息单元格
	 *
	 */
	public class SimpleHotelCell extends Pane {

		private HotelVO hotelVO;

		private Button produceButton;
		private Button detailedButton;

		public SimpleHotelCell(HotelVO hotelVO) throws RemoteException {
			super();
			this.hotelVO = hotelVO;
			this.setId("gri");

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

			Label hotelName = new Label(hotelVO.hotelName);
			this.getChildren().add(hotelName);
			hotelName.setId("hotelname");
			Label commentScore = new Label("评分 "+String.valueOf(hotelVO.commentScore) + "分");
			this.getChildren().add(commentScore);
			commentScore.setId("commentscore");
			Label ifbooked = new Label(booked);
			this.getChildren().add(ifbooked);
			ifbooked.setId("ifbook");
			Label star = new Label(String.valueOf(hotelVO.score) + "星级");
			star.setId("star");
			this.getChildren().add(star);
			Label minPrice = new Label("¥" + String.valueOf(hotelVO.minPrice) + "起");
			minPrice.setId("price");
			this.getChildren().add(minPrice);
			Label address = new Label(String.valueOf(hotelVO.address));
			address.setId("address");
			this.getChildren().add(address);

			produceButton = new Button("下订单");
			this.getChildren().add(produceButton);
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
			this.getChildren().add(detailedButton);
			detailedButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
				try {
					TheMainFrame.jumpTo(new CustomerHotelInfoPane(hotelVO.hotelID, customerID));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			});
			
			hotelName.setLayoutX(230.0);
			hotelName.setLayoutY(16.0);
			star.setLayoutX(240.0);
			star.setLayoutY(56.0);
			minPrice.setLayoutX(520.0);
			minPrice.setLayoutY(62.0);
			commentScore.setLayoutX(240.0);
			commentScore.setLayoutY(86.0);
			ifbooked.setLayoutX(720.0);
			ifbooked.setLayoutY(10.0);
			produceButton.setLayoutX(700.0);
			produceButton.setLayoutY(80.0);
			address.setLayoutX(230.0);
			address.setLayoutY(150.0);
			detailedButton.setLayoutX(537.0);
			detailedButton.setLayoutY(105.0);
			
			ImagePane imagePane = new ImagePane(hotelVO.hotelID);
			this.getChildren().add(imagePane);
			imagePane.setLayoutX(11.0);
			imagePane.setLayoutY(11.0);

			this.getStylesheets().add(getClass().getResource("SimpleHotelCell.css").toExternalForm());
		}
	}

}