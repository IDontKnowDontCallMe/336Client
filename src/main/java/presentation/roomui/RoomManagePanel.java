package presentation.roomui;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

//��ʾ�Ƶ�ͷ��������壬���ھƵ깤����Ա
public class RoomManagePanel {
	
	//��ʾ�Ƶ�Ŀͷ��б�
	public void getRoomList(){
		SplitPane pane = new SplitPane();
		pane.setOrientation(Orientation.VERTICAL);
		pane.setDividerPositions(0.2,0,8);
		
		Button add = new Button("���");
		Button edit = new Button("�༭");
		HBox hBox = new HBox();
		hBox.getChildren().addAll(add,edit);
		
		TableView tableView = new TableView<>();
		TableColumn RoomID = new TableColumn("�����");  
        TableColumn RoomType = new TableColumn("��������");  
        TableColumn guest = new TableColumn("��ס��");  
       
        tableView.getColumns().addAll(RoomID, RoomType, guest);  
        
        
        pane.getItems().addAll(hBox,tableView);
	}
	
	//��ʾ����/�༭�ͷ���Ϣ�Ľ���
	public void edit(){
		
	}
	
	//ֹͣ��ʾ
	public void back(){
		
	}

}
