package presentation.promotionui;

import java.rmi.RemoteException;
import java.util.Optional;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import vo.HotelPromotionVO;

/**
 * @author samperson1997
 * 酒店促销策略单元格
 *
 */
public class HotelPromotionCell extends GridPane {

	HotelPromotionVO hotelPromotionVO;

	private Label hotelPromotionTypeText;
	private Label startTimeText;
	private Label endTimeText;
	private Label companyNameText;
	private Label minNumText;
	private Label discountText;

	private Button editButton;
	private Button deleteButton;

	public HotelPromotionCell(HotelPromotionVO hotelPromotionVO) {
		super();
		this.hotelPromotionVO = hotelPromotionVO;
		this.setId("pane");
		hotelPromotionTypeText = new Label("  "+hotelPromotionVO.promotionType);
		this.add(hotelPromotionTypeText, 0, 0, 1, 1);
		if (hotelPromotionVO.startTime != null && hotelPromotionVO.endTime != null) {
			startTimeText = new Label("  开始时间: " + hotelPromotionVO.startTime.toString());
			endTimeText = new Label("   结束时间: " + hotelPromotionVO.endTime.toString());
			this.add(startTimeText, 0, 1, 1, 1);
			this.add(endTimeText, 1, 1, 1, 1);
		}
		if (hotelPromotionVO.companyName != null) {
			companyNameText = new Label("  合作企业名称: " + hotelPromotionVO.companyName);
			this.add(companyNameText, 0, 1, 3, 1);
		}
		if (hotelPromotionVO.minNum != -1) {
			minNumText = new Label("  最小预订房间数量: " + hotelPromotionVO.minNum);
			this.add(minNumText, 0, 1, 3, 1);
		}
		discountText = new Label("折扣: " + hotelPromotionVO.discount);
		this.add(discountText, 1, 0, 1, 1);

		editButton = new Button("编辑");
		editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			Dialog<HotelPromotionVO> hotelPeomotionEditDialog = new HotelPromotionEditDialog(hotelPromotionVO);

			Optional<HotelPromotionVO> result = hotelPeomotionEditDialog.showAndWait();
			if (result.isPresent()) {
				try {
					if (BLFactory.getInstance().getPromotionBLService().updateHotelPromotion(result.get())) {
						System.out.println("edit");
						if (hotelPromotionVO.startTime != null && hotelPromotionVO.endTime != null) {
							startTimeText.setText("  开始时间: " + result.get().startTime.toString());
							endTimeText.setText("   结束时间: " + result.get().endTime.toString());
						}
						if (hotelPromotionVO.companyName != null) {
							companyNameText.setText("  合作企业名称: " + result.get().companyName);
						}
						if (hotelPromotionVO.minNum != -1) {
							minNumText.setText(String.valueOf("  最小预订房间数量: " + result.get().minNum));
						}
						discountText.setText("折扣: " + String.valueOf(result.get().discount));
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

		HBox hBox =new HBox(editButton,deleteButton);
		hBox.setSpacing(10.0);
		hBox.setTranslateY(5.0);
		this.add(hBox, 3, 0, 1, 1);

		this.setHgap(10);
		this.setVgap(20);
		
		this.getStylesheets().add(getClass().getResource("HotelPromotionCell.css").toExternalForm());

	}

}
