package presentation.userui;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import bussinesslogic.factory.BLFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presentation.mainui.TheMainFrame;
import presentation.mainui.WebManagerPilot;
import vo.HotelVO;

/**
 * @author samperson1997
 * 酒店信息面板
 *
 */
public class HotelPanel extends GridPane {
	private ScrollPane listPane;
	private VBox hotelBox;
	private Label title;
	private Button addButton;
	private Button backButton;
	private HBox titleBox;

	private List<HotelVO> hotelList;

	/**
	 * @throws RemoteException
	 * 酒店信息面板
	 * 
	 */
	public HotelPanel(int id) throws RemoteException {
		
		hotelList = BLFactory.getInstance().getUserBLService().getHotelList();

		hotelBox = new VBox();
		hotelBox.setSpacing(15);
		buildHotelBox(hotelList);
		listPane = new ScrollPane(hotelBox);
		listPane.setMinWidth(920.0);
		listPane.getStyleClass().add("edge-to-edge");
		title = new Label("酒店及酒店工作人员列表");
		addButton = new Button("新增酒店及酒店工作人员");

		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Dialog<HotelVO> addHotelDialog = new AddHotelDialog();

				Optional<HotelVO> result = addHotelDialog.showAndWait();
				if (result.isPresent()) {
					try {
						if (BLFactory.getInstance().getUserBLService().addHotel(result.get())) {
							System.out.println("add hotel!");
							hotelList.add(result.get());
							buildHotelBox(hotelList);
							Stage popup1 = new Stage();
							popup1.setAlwaysOnTop(true);
							popup1.initModality(Modality.APPLICATION_MODAL);
							Button closeButton1 = new Button("确定");
							closeButton1.setOnAction(e -> {
								popup1.close();
							});
							VBox root1 = new VBox();
							root1.setAlignment(Pos.BASELINE_CENTER);
							root1.setSpacing(20);
							root1.getChildren().addAll(new Label("酒店已添加，默认密码为123456"), 
									new Label("请尽快联系酒店工作人员修改酒店详细信息"), closeButton1);
							Scene scene1 = new Scene(root1, 250, 120);
							popup1.setScene(scene1);
							popup1.setTitle("提示");
							popup1.show();
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					System.out.println("nothing added");
				}

			}
		});
		backButton = new Button("返回");
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		titleBox = new HBox();
		titleBox.getChildren().addAll(title, addButton, backButton);
		titleBox.setSpacing(20.0);
		GridPane gridPane = new GridPane();
		gridPane.add(titleBox, 0, 0);
		gridPane.add(listPane, 0, 1);
		WebManagerPilot webManagerPilot = new WebManagerPilot(id);
		this.add(webManagerPilot, 0, 0);
		this.add(gridPane, 1, 0);
		gridPane.setId("pane");
		gridPane.getStylesheets().add(getClass().getResource("HotelPane.css").toExternalForm());
	}

	/**
	 * @param hotelList
	 * 建立酒店信息列表
	 * 
	 */
	public void buildHotelBox(List<HotelVO> hotelList) {
		
		hotelBox.getChildren().clear();
		for (HotelVO vo : hotelList) {
			hotelBox.getChildren().addAll(new HotelCell(vo));
		}
	}
}
