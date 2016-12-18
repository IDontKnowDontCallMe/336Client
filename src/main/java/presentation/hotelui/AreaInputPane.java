package presentation.hotelui;

import java.rmi.RemoteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import presentation.mainui.CustomerMainPane;
import presentation.mainui.TheMainFrame;
import vo.AreaVO;

/**
 * @author samperson1997
 * 城市和商圈选择面板
 * 
 */
public class AreaInputPane extends AnchorPane {

	final int COLUMN_COUNT = 10;

	private int customerID;

	private ComboBox<String> cityBox;
	private ComboBox<String> businessCircleBox;

	private Button confirmButton;
	private Button backButton;

	/**
	 * @param customerID
	 * 城市和商圈选择面板
	 * 
	 */
	public AreaInputPane(int customerID) {
		
		super();
		Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);
		this.customerID = customerID;

		ObservableList<String> cityList = FXCollections.observableArrayList("南京");
		cityBox = new ComboBox<String>(cityList);
		cityBox.getSelectionModel().select(0);

		ObservableList<String> businessCircleList = FXCollections.observableArrayList("栖霞区", "鼓楼区", "秦淮区");
		businessCircleBox = new ComboBox<String>(businessCircleList);
		businessCircleBox.getSelectionModel().select(0);

		Label comfirm = new Label();
		comfirm.setFont(Font.font(icon.getFamily(), 46));
		comfirm.setText(String.valueOf('\uf002'));
		confirmButton = new Button("搜索", comfirm);
		confirmButton.setWrapText(true);
		confirmButton.setContentDisplay(ContentDisplay.TOP);
		confirmButton.setId("comfirmButton");
		confirmButton.setShape(new Circle(42));
		confirmButton.setMinSize(84, 84);
		confirmButton.setMaxSize(84, 84);

		Label back = new Label();
		back.setFont(Font.font(icon.getFamily(), 46));
		back.setText(String.valueOf('\uf112'));
		backButton = new Button("返回", back);
		backButton.setWrapText(true);
		backButton.setContentDisplay(ContentDisplay.TOP);
		backButton.setId("backButton");
		backButton.setShape(new Circle(42));
		backButton.setMinSize(84, 84);
		backButton.setMaxSize(84, 84);

		Label chooseCity = new Label("请选择城市：");
		chooseCity.setId("lb1");
		Label chooseBussin = new Label("请选择商圈：");
		chooseBussin.setId("lb2");
		this.getChildren().addAll(cityBox, businessCircleBox, confirmButton, backButton, chooseCity, chooseBussin);

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
		AnchorPane.setTopAnchor(cityBox, 213.0);
		AnchorPane.setLeftAnchor(cityBox, 546.0);

		AnchorPane.setTopAnchor(chooseBussin, 312.0);
		AnchorPane.setLeftAnchor(chooseBussin, 420.0);
		AnchorPane.setTopAnchor(businessCircleBox, 305.0);
		AnchorPane.setLeftAnchor(businessCircleBox, 546.0);

		AnchorPane.setTopAnchor(confirmButton, 410.0);
		AnchorPane.setLeftAnchor(confirmButton, 450.0);
		AnchorPane.setTopAnchor(backButton, 410.0);
		AnchorPane.setLeftAnchor(backButton, 580.0);

		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
		this.getStylesheets().add(getClass().getResource("AreaInputPane.css").toExternalForm());
	}

}
