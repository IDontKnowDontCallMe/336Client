package presentation.hotelui;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ImagePane extends VBox {

	private int hotelID;

	private ImageView imageView;
	private Image preImage;

	public ImagePane(int hotelID) throws RemoteException {
		super();
		this.hotelID = hotelID;

		byte[] imageData = BLFactory.getInstance().getHotelBLService().getHotelImage(hotelID);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
		preImage = new Image(byteArrayInputStream);

		imageView = new ImageView();
		imageView.setImage(preImage);
		imageView.setPreserveRatio(false);
		imageView.setFitHeight(150);
		imageView.setFitWidth(200);

		this.getChildren().add(imageView);

	}

}
