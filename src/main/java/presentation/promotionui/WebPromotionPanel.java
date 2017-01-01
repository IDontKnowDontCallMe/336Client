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

import presentation.mainui.WebMarketerPilot;
import vo.WebPromotionVO;

/**
 * @author samperson1997
 * 网站促销策略面板
 *
 */
public class WebPromotionPanel extends GridPane {

	private List<WebPromotionVO> webPromotionList;
	private ScrollPane listPane;
	private VBox webPromotionBox;
	private HBox addBox;
	private Button addButton;
	private Label title;

	/**
	 * @throws RemoteException
	 * 网站促销策略面板
	 * 
	 */
	public WebPromotionPanel(int id) throws RemoteException{

		webPromotionList = BLFactory.getInstance().getPromotionBLService().getWebPromotionList();
		webPromotionBox = new VBox();
		webPromotionBox.setSpacing(15);
		buildWebPromotionBox(webPromotionList);
		listPane = new ScrollPane(webPromotionBox);
		listPane.setMinWidth(920.0);
		webPromotionBox.setTranslateX(20.0);
		listPane.getStyleClass().add("edge-to-edge");		

		title = new Label("网站促销策略");
		title.setId("title");
		title.setTranslateY(2.0);
		addButton = new Button("新增");
		addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			Dialog<WebPromotionVO> webPeomotionAddDialog = new WebPromotionAddDialog();

			Optional<WebPromotionVO> result = webPeomotionAddDialog.showAndWait();
			if (result.isPresent()) {
				try {
					if (BLFactory.getInstance().getPromotionBLService().addWebPromotion(result.get())) {
						System.out.println("add");
						webPromotionList.add(result.get());
						buildWebPromotionBox(webPromotionList);
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		
		
		addBox = new HBox();
		addBox.setSpacing(10);
		addBox.setMinHeight(50.0);
		addBox.setTranslateX(20.0);
		addBox.setTranslateY(10.0);
		addBox.getChildren().addAll(title, addButton);
		
		GridPane gridPane = new GridPane();
		gridPane.add(addBox, 0, 0);
		gridPane.add(listPane,0,1);
		
		
		WebMarketerPilot webMarketerPilot = new WebMarketerPilot(id);
		this.add(webMarketerPilot, 0, 0);
		this.add(gridPane, 1, 0);
		
		this.getStylesheets().add(getClass().getResource("WebPromotionPane.css").toExternalForm());
	}

	/**
	 * @param webPromotionList
	 * 建立网站促销策略列表
	 * 
	 */
	private void buildWebPromotionBox(List<WebPromotionVO> webPromotionList) {
		
		webPromotionBox.getChildren().clear();
		for (WebPromotionVO vo : webPromotionList) {
			webPromotionBox.getChildren().add(new WebPromotionCell(vo));
		}
	}
}
