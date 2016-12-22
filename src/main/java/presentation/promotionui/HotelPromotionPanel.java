package presentation.promotionui;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presentation.mainui.HotelWorkerPilot;
import presentation.mainui.TheMainFrame;
import vo.HotelPromotionVO;

/**
 * @author samperson1997
 * 酒店促销策略面板
 *
 */
public class HotelPromotionPanel extends GridPane {

	private List<HotelPromotionVO> hotelPromotionList;
	private ScrollPane listPane;
	private VBox hotelPromotionBox;
	private HBox addBox;
	private Button addButton;
	private Button backButton;
	private Label title;

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
		listPane.setMinWidth(920.0);
		hotelPromotionBox.setTranslateX(20.0);
		listPane.getStyleClass().add("edge-to-edge");		

		title = new Label("酒店促销策略");
		title.setId("title");
		title.setTranslateY(2.0);
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
		addBox.setMinHeight(50.0);
		addBox.setTranslateX(20.0);
		addBox.getChildren().addAll(title, addButton, backButton);
		
		GridPane gridPane = new GridPane();
		gridPane.add(addBox, 0, 0);
		gridPane.add(listPane,0,1);
		
		HotelWorkerPilot hotelWorkerPilot = new HotelWorkerPilot(hotelID);
		this.add(hotelWorkerPilot, 0, 0);
		this.add(gridPane, 1, 0);
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
