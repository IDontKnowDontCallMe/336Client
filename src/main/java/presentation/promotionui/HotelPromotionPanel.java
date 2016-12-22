package presentation.promotionui;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import presentation.mainui.TheMainFrame;
import vo.HotelPromotionVO;

/**
 * @author samperson1997
 * 酒店促销策略面板
 *
 */
public class HotelPromotionPanel extends VBox {

	private List<HotelPromotionVO> hotelPromotionList;
	private ScrollPane listPane;
	private VBox hotelPromotionBox;
	private HBox addBox;
	private Button addButton;
	private Button backButton;
	private Text title;

	/**
	 * @param hotelID
	 * @throws RemoteException
	 * 酒店促销策略面板
	 * 
	 */
	public HotelPromotionPanel(int hotelID) throws RemoteException {
		
		hotelPromotionList = BLFactory.getInstance().getPromotionBLService().getHotelPromotionList(hotelID);
		hotelPromotionBox = new VBox();
		hotelPromotionBox.setSpacing(15);
		buildHotelPromotionBox(hotelPromotionList);
		listPane = new ScrollPane(hotelPromotionBox);
		listPane.getStyleClass().add("edge-to-edge");		

		title = new Text("酒店促销策略");
		addButton = new Button("新增");
		addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			Dialog<HotelPromotionVO> hotelPeomotionAddDialog = new HotelPromotionAddDialog(hotelID);

			Optional<HotelPromotionVO> result = hotelPeomotionAddDialog.showAndWait();
			if (result.isPresent()) {
				try {
					if (BLFactory.getInstance().getPromotionBLService().addHotelPromotion(result.get())) {
						System.out.println("add");
						hotelPromotionList.add(result.get());
						buildHotelPromotionBox(hotelPromotionList);
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		backButton = new Button("返回");
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			TheMainFrame.backTo();
		});
		addBox = new HBox();
		addBox.setSpacing(10);
		addBox.setPrefWidth(500);
		addBox.getChildren().addAll(title, addButton, backButton);
		this.getChildren().addAll(addBox, listPane);
		this.getStylesheets().add(getClass().getResource("HotelPromotionPane.css").toExternalForm());
	}

	/**
	 * @param hotelPromotionList
	 * 建立酒店促销策略列表
	 * 
	 */
	private void buildHotelPromotionBox(List<HotelPromotionVO> hotelPromotionList) {
		
		hotelPromotionBox.getChildren().clear();
		for (HotelPromotionVO vo : hotelPromotionList) {
			hotelPromotionBox.getChildren().add(new HotelPromotionCell(vo));
		}
	}
}
