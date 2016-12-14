package presentation.promotionui;

import java.rmi.RemoteException;
import java.util.Optional;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vo.WebPromotionVO;

public class WebPromotionCell extends GridPane {

	WebPromotionVO webPromotionVO;

	private Text webPromotionTypeText;
	private Text startTimeText;
	private Text endTimeText;
	private Text businessCircleText;
	private Text discountText;

	private Button editButton;
	private Button deleteButton;

	public WebPromotionCell(WebPromotionVO webPromotionVO) {
		super();
		this.webPromotionVO = webPromotionVO;

		webPromotionTypeText = new Text(webPromotionVO.promotionType);
		this.add(webPromotionTypeText, 0, 0, 3, 1);
		if (webPromotionVO.startTime != null && webPromotionVO.endTime != null) {
			startTimeText = new Text("开始时间: " + webPromotionVO.startTime.toString());
			endTimeText = new Text("结束时间: " + webPromotionVO.endTime.toString());
			this.add(startTimeText, 0, 1, 3, 1);
			this.add(endTimeText, 4, 1, 3, 1);
		}
		if (webPromotionVO.businessCircleName != null) {
			businessCircleText = new Text("特定商圈名称: " + webPromotionVO.businessCircleName);
			this.add(businessCircleText, 0, 1, 3, 1);
		}

		discountText = new Text("折扣: " + webPromotionVO.discount);
		this.add(discountText, 3, 0, 3, 1);

		editButton = new Button("编辑");
		editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			Dialog<WebPromotionVO> webPeomotionEditDialog = new WebPromotionEditDialog(webPromotionVO);

			Optional<WebPromotionVO> result = webPeomotionEditDialog.showAndWait();
			if (result.isPresent()) {
				try {
					if (BLFactory.getInstance().getPromotionBLService().updateWebPromotion(result.get())) {
						System.out.println("edit");
						if (webPromotionVO.startTime != null && webPromotionVO.endTime != null) {
							startTimeText.setText("开始时间: " + result.get().startTime.toString());
							endTimeText.setText("结束时间: " + result.get().endTime.toString());
						}
						if (webPromotionVO.businessCircleName != null) {
							businessCircleText.setText("特定商圈名称: " + result.get().businessCircleName);
						}
						discountText.setText(String.valueOf("折扣: " + result.get().discount));
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
				if (BLFactory.getInstance().getPromotionBLService().deleteWebPromotion(webPromotionVO)) {
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
