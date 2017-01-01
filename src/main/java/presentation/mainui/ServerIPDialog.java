package presentation.mainui;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ServerIPDialog extends Dialog<String>{

	public ServerIPDialog() {
		// TODO Auto-generated constructor stub
		
		init();
		
	}
	
	private void init(){
		
		ToggleGroup ipToggleGroup = new ToggleGroup();
		
		RadioButton localhostButton = new RadioButton("localhost:8888");
		localhostButton.setToggleGroup(ipToggleGroup);
		localhostButton.setUserData("localhost:8888");
		
		RadioButton defaultButton = new RadioButton("172.26.102.100:8888");
		defaultButton.setToggleGroup(ipToggleGroup);
		defaultButton.setUserData("172.26.102.100:8888");
		
		RadioButton customizedButton = new RadioButton("自定义ip");
		customizedButton.setToggleGroup(ipToggleGroup);
		customizedButton.setUserData("customized");
		customizedButton.setSelected(true);
		
		HBox radioButtonBox = new HBox();
		radioButtonBox.getChildren().addAll(customizedButton,localhostButton,defaultButton);
		radioButtonBox.setSpacing(10);
		
		Text ipText = new Text("ip:");
		TextField inputField = new TextField();
		Text portText = new Text("port:");
		TextField inputField2 = new TextField();
		inputField2.setPrefColumnCount(4);
		
		HBox inputBox = new HBox();
		inputBox.getChildren().addAll(ipText,inputField,portText,inputField2);
		
		VBox contentBox = new VBox();
		contentBox.getChildren().addAll(radioButtonBox,inputBox);
		contentBox.setSpacing(15);
		
		this.getDialogPane().setContent(contentBox);
		
		ButtonType okButtonType = new ButtonType("确定", ButtonData.OK_DONE);
		ButtonType cancelButtonType = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(okButtonType,cancelButtonType);
		
		Callback<ButtonType, String> callback = new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType param) {
				// TODO Auto-generated method stub
				if(param.getButtonData().equals(ButtonData.OK_DONE)){
					
					String userData = (String)ipToggleGroup.getSelectedToggle().getUserData();
					
					if(userData.equals("customized")){
						return inputField.getText() + ":" + inputField2.getText();
					}
					else {
						return userData;
					}
					
				}
				else if(param.getButtonData().equals(ButtonData.CANCEL_CLOSE)){
					return "cancel";
				}
				
				return null;
			}
		};
		
		this.setResultConverter(callback);
		
	}
	
}
