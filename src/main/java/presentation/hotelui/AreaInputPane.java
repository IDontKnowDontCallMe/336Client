package presentation.hotelui;

import java.rmi.RemoteException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import presentation.mainui.TheMainFrame;
import vo.AreaVO;

public class AreaInputPane extends VBox {

	final int COLUMN_COUNT = 10;

	private int customerID;
	private ChoiceBox cityBox;
	private ChoiceBox businessCircleBox;

	private Button confirmButton;
	private Button backButton;
	private GridPane gridPane;

	public AreaInputPane(int customerID) {
		super();

		this.customerID = customerID;
		gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		ObservableList<String> cityList = FXCollections.observableArrayList("南京");
		cityBox = new ChoiceBox<String>(cityList);
		cityBox.getSelectionModel().select(0);

		ObservableList<String> businessCircleList = FXCollections.observableArrayList("栖霞区", "鼓楼区", "秦淮区");
		businessCircleBox = new ChoiceBox<String>(businessCircleList);
		businessCircleBox.getSelectionModel().select(0);

		gridPane.add(new Text("请选择城市"), 0, 0, 1, 1);
		gridPane.add(cityBox, 1, 0, 1, 1);
		gridPane.add(new Text("请选择商圈"), 0, 1, 1, 1);
		gridPane.add(businessCircleBox, 1, 1, 1, 1);

		confirmButton = new Button("开始搜索");
		gridPane.add(confirmButton, 0, 2, 1, 1);

		confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			String city = cityBox.getValue().toString();
			String businessCircle = businessCircleBox.getValue().toString();

			try {
				TheMainFrame.jumpTo(new HotelSearchPane(new AreaVO(city, businessCircle), customerID));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		backButton = new Button("返回");
		gridPane.add(backButton, 1, 2, 1, 1);

		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		this.getChildren().add(gridPane);
	}

}
