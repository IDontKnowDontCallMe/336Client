package presentation.promotionui;

import java.rmi.RemoteException;
import java.util.Optional;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vo.HotelPromotionVO;

public class HotelPromotionCell extends GridPane {

	HotelPromotionVO hotelPromotionVO;

	private Text hotelPromotionTypeText;
	private Text startTimeText;
	private Text endTimeText;
	private Text companyNameText;
	private Text minNumText;
	private Text discountText;

	private Button editButton;
	private Button deleteButton;

	public HotelPromotionCell(HotelPromotionVO hotelPromotionVO) {
		super();
		this.hotelPromotionVO = hotelPromotionVO;

		hotelPromotionTypeText = new Text(hotelPromotionVO.promotionType);
		this.add(hotelPromotionTypeText, 0, 0, 3, 1);
		if (hotelPromotionVO.startTime != null && hotelPromotionVO.endTime != null) {
			startTimeText = new Text("开始时间: " + hotelPromotionVO.startTime.toString());
			endTimeText = new Text("结束时间: " + hotelPromotionVO.endTime.toString());
			this.add(startTimeText, 0, 1, 3, 1);
			this.add(endTimeText, 3, 1, 3, 1);
		}
		if (hotelPromotionVO.companyName != null) {
			companyNameText = new Text("合作企业名称: " + hotelPromotionVO.companyName);
			this.add(companyNameText, 0, 1, 3, 1);
		}
		if (hotelPromotionVO.minNum != -1) {
			minNumText = new Text("最小预订房间数量: " + hotelPromotionVO.minNum);
			this.add(minNumText, 0, 1, 3, 1);
		}
		discountText = new Text("折扣: " + hotelPromotionVO.discount);
		this.add(discountText, 3, 0, 3, 1);

		editButton = new Button("编辑");
		editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			Dialog<HotelPromotionVO> hotelPeomotionEditDialog = new HotelPromotionEditDialog(hotelPromotionVO);

			Optional<HotelPromotionVO> result = hotelPeomotionEditDialog.showAndWait();
			if (result.isPresent()) {
				try {
					if (BLFactory.getInstance().getPromotionBLService().updateHotelPromotion(result.get())) {
						System.out.println("edit");
						if (hotelPromotionVO.startTime != null && hotelPromotionVO.endTime != null) {
							startTimeText.setText(hotelPromotionVO.startTime.toString());
							endTimeText.setText(hotelPromotionVO.endTime.toString());
						}
						if (hotelPromotionVO.companyName != null) {
							companyNameText.setText(hotelPromotionVO.companyName);
						}
						if (hotelPromotionVO.minNum != -1) {
							minNumText.setText(String.valueOf(hotelPromotionVO.minNum));
						}
						discountText.setText(String.valueOf(hotelPromotionVO.discount));
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		deleteButton = new Button("删除");
		deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			try {
				if (BLFactory.getInstance().getPromotionBLService().deleteHotelPromotion(hotelPromotionVO)) {
					System.out.println("delete");
					this.getChildren().clear();

				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		this.add(editButton, 10, 0, 1, 1);
		this.add(deleteButton, 12, 0, 1, 1);

		this.setHgap(10);
		this.setVgap(20);

	}

}
