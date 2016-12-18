package presentation.hotelui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;

import bussinesslogic.factory.BLFactory;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ImageEditPane extends VBox{
	
	private int hotelID;
	
	private ImageView imageView;
	private Image preImage;
	private Image transitionImage;
	private byte[] transitionImageData;
	
	private HBox editButtonBox;
	private Button chooseButton;
	private Button confirmButton;
	private Button cancleButton;
	
	public ImageEditPane(int hotelID) throws RemoteException{
		super();
		this.hotelID = hotelID;
		
		byte[] imageData = BLFactory.getInstance().getHotelBLService().getHotelImage(hotelID);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
		preImage = new Image(byteArrayInputStream);
		transitionImage = null;
		imageView = new ImageView();
		imageView.setImage(preImage);
		imageView.setPreserveRatio(false);
		imageView.setFitHeight(300);
		imageView.setFitWidth(300);
		
		editButtonBox = new HBox();
		chooseButton = new Button("选择图片");
		chooseButton.setOnAction((event)->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("选择一张图片");
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("PNG", "*.png"),
	                new FileChooser.ExtensionFilter("JPG", "*.jpg")
	            );
			
			File file = fileChooser.showOpenDialog(getScene().getWindow());
			
			if(file==null) return;
			
			try {
				transitionImage = new Image(new FileInputStream(file));
				FileInputStream fileInputStream = new FileInputStream(file);
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int numBytesRead = 0;
				while ((numBytesRead = fileInputStream.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
				}
				transitionImageData = output.toByteArray();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(transitionImage!=null){
				imageView.setImage(transitionImage);
			}
		});
		
		confirmButton = new Button("保存");
		confirmButton.setOnAction((event)->{
			if(transitionImage!=null){
				preImage = transitionImage;
				transitionImage = null;
				imageView.setImage(preImage);
				try {
					BLFactory.getInstance().getHotelBLService().saveHotelImage(hotelID, transitionImageData);
					transitionImageData = null;
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		cancleButton = new Button("取消");
		cancleButton.setOnAction((event)->{
			if(transitionImage!=null){
				imageView.setImage(preImage);
				transitionImage = null;
				transitionImageData = null;
			}
		});
		
		editButtonBox.getChildren().addAll(chooseButton,confirmButton,cancleButton);
		
		this.getChildren().addAll(imageView,editButtonBox);
		
		
	}
	

}
