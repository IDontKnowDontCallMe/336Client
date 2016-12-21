package presentation.hotelui;

import java.rmi.RemoteException;
import java.util.List;

import bussinesslogic.factory.BLFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import presentation.mainui.CustomerMainPane;
import presentation.mainui.TheMainFrame;
import vo.HotelVO;

/**
 * @author samperson1997
 * 预订过的酒店面板
 *
 */
public class BookedHotelPane extends VBox {
	Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);

	public BookedHotelPane(int customerID) throws RemoteException {
		
		Label back = new Label();
		back.setFont(Font.font(icon.getFamily(), 28));
		back.setText(String.valueOf('\uf112'));
		Button backButton = new Button("返回", back);
		backButton.setWrapText(true);
		backButton.setContentDisplay(ContentDisplay.TOP);
		backButton.setId("backButton");
		backButton.setShape(new Circle(31));
		backButton.setMinSize(62, 62);
		backButton.setMaxSize(62, 62);
		
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});

		List<HotelVO> hotelList = BLFactory.getInstance().getHotelBLService().getBookedHotelList(customerID);

		this.getChildren().addAll(backButton, new HotelListPane(hotelList, customerID));
		
		backButton.setTranslateX(1030.0);
		this.getStylesheets().add(getClass().getResource("BookedHotelPane.css").toExternalForm());
	}

}
