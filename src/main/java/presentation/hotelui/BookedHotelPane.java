package presentation.hotelui;

import java.rmi.RemoteException;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import presentation.mainui.CustomerMainPane;
import presentation.mainui.CustomerPilot;
import vo.HotelVO;

/**
 * @author samperson1997 预订过的酒店面板
 *
 */
public class BookedHotelPane extends GridPane {
	Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);

	public BookedHotelPane(int customerID) throws RemoteException {


		List<HotelVO> hotelList = BLFactory.getInstance().getHotelBLService().getBookedHotelList(customerID);

		VBox vBox = new VBox();
		vBox.getChildren().add(new HotelListPane(hotelList, customerID));

		CustomerPilot customerPilot = new CustomerPilot(customerID);

		this.add(customerPilot, 0, 0);
		this.add(vBox, 1, 0);

		this.getStylesheets().add(getClass().getResource("BookedHotelPane.css").toExternalForm());
	}

}
