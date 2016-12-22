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
import vo.WebPromotionVO;

/**
 * @author samperson1997
 * 网站促销策略单元格
 *
 */
public class WebPromotionCell extends GridPane {

	WebPromotionVO webPromotionVO;

	private Label webPromotionTypeText;
	private Label startTimeText;
	private Label endTimeText;
	private Label businessCircleText;
	private Label discountText;

	private Button editButton;
	private Button deleteButton;

	/**
	 * @param webPromotionVO
	 * 网站促销策略单元格
	 */
	public WebPromotionCell(WebPromotionVO webPromotionVO) {
		
		super();
		this.webPromotionVO = webPromotionVO;
		this.setId("pane");
		webPromotionTypeText = new Label("  "+webPromotionVO.promotionType);
		this.add(webPromotionTypeText, 0, 0, 1, 1);
		if (webPromotionVO.startTime != null && webPromotionVO.endTime != null) {
			startTimeText = new Label("  开始时间: " + webPromotionVO.startTime.toString());
			endTimeText = new Label("   结束时间: " + webPromotionVO.endTime.toString());
			discountText = new Label("折扣: " + webPromotionVO.discount);
			this.add(discountText, 1, 0, 1, 1);
			this.add(startTimeText, 0, 1, 1, 1);
			this.add(endTimeText, 1, 1, 1, 1);
		}
		if (webPromotionVO.businessCircleName != null) {
			businessCircleText = new Label("  特定商圈名称: " + webPromotionVO.businessCircleName);
			discountText = new Label("会员每升一级在该商圈订酒店折扣减少: " + webPromotionVO.discount);
			this.add(discountText, 3, 1, 3, 1);
			this.add(businessCircleText, 0, 1, 3, 1);
		}

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
							discountText.setText(String.valueOf("折扣: " + result.get().discount));
						}
						if (webPromotionVO.businessCircleName != null) {
							businessCircleText.setText("特定商圈名称: " + result.get().businessCircleName);
							discountText.setText(String.valueOf("会员每升ß一级在该商圈订酒店时折扣减少: " + result.get().discount));
						}

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

		HBox hBox =new HBox(editButton,deleteButton);
		hBox.setSpacing(10.0);
		hBox.setTranslateY(5.0);
		this.add(hBox, 3, 0, 1, 1);

		this.setHgap(10);
		this.setVgap(20);
		this.getStylesheets().add(getClass().getResource("WebPromotionCell.css").toExternalForm());

	}

}
