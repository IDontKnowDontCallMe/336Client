package presentation.roomui;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class RoomManagePanel {
	
	public void getRoomList(){
		SplitPane pane = new SplitPane();
		pane.setOrientation(Orientation.VERTICAL);
		pane.setDividerPositions(0.2,0,8);
		
		Button add = new Button("添加");
		Button edit = new Button("编辑");
		HBox hBox = new HBox();
		hBox.getChildren().addAll(add,edit);
		
		TableView tableView = new TableView<>();
		TableColumn RoomID = new TableColumn("房间号");  
        TableColumn RoomType = new TableColumn("房间类型");  
        TableColumn guest = new TableColumn("入住人");  
       
        tableView.getColumns().addAll(RoomID, RoomType, guest);  
        
        
        pane.getItems().addAll(hBox,tableView);
	}
	
	public void edit(){
		
	}
	
	public void back(){
		
	}

}
