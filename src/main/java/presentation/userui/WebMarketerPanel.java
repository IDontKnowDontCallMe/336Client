package presentation.userui;

import java.rmi.RemoteException;
import java.util.List;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import presentation.mainui.TheMainFrame;
import vo.WebMarketerVO;

/**
 * @author samperson1997
 * 网站营销人员信息面板
 *
 */
public class WebMarketerPanel extends VBox {

	private ScrollPane listPane;
	private VBox webMarketerBox;
	private HBox titleBox;
	private Button backButton;
	private Text title;

	/**
	 * @throws RemoteException
	 * 网站营销人员信息面板
	 */
	public WebMarketerPanel() throws RemoteException {
		
		List<WebMarketerVO> webMarketerList = BLFactory.getInstance().getUserBLService().getWebMarketerList();

		webMarketerBox = new VBox();
		webMarketerBox.setSpacing(15);
		buildWebMarketerBox(webMarketerList);
		listPane = new ScrollPane(webMarketerBox);

		title = new Text("网站营销人员列表");
		titleBox = new HBox();
		backButton = new Button("返回");
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		titleBox.getChildren().addAll(title, backButton);
		this.getChildren().addAll(titleBox, listPane);
		
		this.getStylesheets().add(getClass().getResource("WebMarketerPane.css").toExternalForm());
	}

	/**
	 * @param webMarketerList
	 * 建立网站营销人员列表
	 * 
	 */
	public void buildWebMarketerBox(List<WebMarketerVO> webMarketerList) {
		
		webMarketerBox.getChildren().clear();
		for (WebMarketerVO vo : webMarketerList) {
			webMarketerBox.getChildren().addAll(new WebMarketerCell(vo));
		}
	}
}
