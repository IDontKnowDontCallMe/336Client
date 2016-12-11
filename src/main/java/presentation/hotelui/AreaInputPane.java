package presentation.hotelui;

import java.rmi.RemoteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import presentation.mainui.CustomerMainPane;
import presentation.mainui.TheMainFrame;
import vo.AreaVO;

public class AreaInputPane extends AnchorPane {

	final int COLUMN_COUNT = 10;

	private int customerID;
	private ChoiceBox cityBox;
	private ChoiceBox businessCircleBox;

	private Button confirmButton;
	private Button backButton;

	public AreaInputPane(int customerID) {
		super();
		Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);
		this.customerID = customerID;
		
		ObservableList<String> cityList = FXCollections.observableArrayList("南京");
		cityBox = new ChoiceBox<String>(cityList);
		cityBox.getSelectionModel().select(0);

		ObservableList<String> businessCircleList = FXCollections.observableArrayList("栖霞区", "鼓楼区", "秦淮区");
		businessCircleBox = new ChoiceBox<String>(businessCircleList);
		businessCircleBox.getSelectionModel().select(0);

		Label comfirm = new Label();
		comfirm.setFont(Font.font(icon.getFamily(), 45));
		comfirm.setText(String.valueOf('\uf002'));
		confirmButton = new Button("", comfirm);
		confirmButton.setWrapText(true);
		confirmButton.setContentDisplay(ContentDisplay.TOP);
		confirmButton.setId("backButton");
		confirmButton.setShape(new Circle(40));
		confirmButton.setMinSize(80, 80);
		confirmButton.setMaxSize(80, 80);

		Label back = new Label();
		back.setFont(Font.font(icon.getFamily(), 45));
		back.setText(String.valueOf('\uf112'));
		backButton = new Button("", back);
		backButton.setWrapText(true);
		backButton.setContentDisplay(ContentDisplay.TOP);
		backButton.setId("backButton");
		backButton.setShape(new Circle(40));
		backButton.setMinSize(80, 80);
		backButton.setMaxSize(80, 80);

		Label chooseCity = new Label("请选择城市：");
		chooseCity.setId("lb1");
		Label chooseBussin = new Label("请选择商圈：");
		chooseBussin.setId("lb2");
		this.getChildren().addAll(cityBox,businessCircleBox,confirmButton,backButton,chooseCity,chooseBussin);
		
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
		AnchorPane.setTopAnchor(chooseCity, 222.0);
		AnchorPane.setLeftAnchor(chooseCity, 420.0);
		AnchorPane.setTopAnchor(cityBox, 220.0);
		AnchorPane.setLeftAnchor(cityBox, 546.0);
		
		AnchorPane.setTopAnchor(chooseBussin, 282.0);
		AnchorPane.setLeftAnchor(chooseBussin, 420.0);
		AnchorPane.setTopAnchor(businessCircleBox, 280.0);
		AnchorPane.setLeftAnchor(businessCircleBox, 550.0);
		
		AnchorPane.setTopAnchor(confirmButton, 375.0);
		AnchorPane.setLeftAnchor(confirmButton, 450.0);
		AnchorPane.setTopAnchor(backButton, 375.0);
		AnchorPane.setLeftAnchor(backButton, 580.0);
		
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		this.getStylesheets().add(getClass().getResource("AreaInputPane.css").toExternalForm());
	}

}
