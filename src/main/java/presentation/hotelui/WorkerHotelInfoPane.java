package presentation.hotelui;

import java.rmi.RemoteException;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import presentation.mainui.CustomerMainPane;
import presentation.mainui.TheMainFrame;
import presentation.roomui.RoomCell;
import vo.HotelVO;
import vo.RoomVO;

/**
 * @author samperson1997 酒店工作人员查看酒店详细信息界面
 *
 */
public class WorkerHotelInfoPane extends GridPane {
	Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);

	private int hotelID;
	private GridPane infoPane;
	private ScrollPane roomPane;
	private HBox addBox1;
	private VBox roomBox;

	final int COLUMN = 15;
	private Button backButton;
	private Button infoEditButton;
	private Button addButton;

	private Text nameText;
	private Text addressText;
	private Text introductionText;
	private Text serviceText;
	private Text businessCircleText;
	private Text scoreText;
	private TextField nameTextField;
	private TextField addressTextField;
	private TextField introductionTextField;
	private TextField serviceTextField;
	private ComboBox<String> businessCircleBox;

	private TextField addRoomNameTextField;
	private TextField addNumOfRoomTextField;
	private TextField addServiceTextField;
	private TextField addMaxNumOfPeopleTextField;
	private TextField addPriceTextField;

	private ComboBox<String> scoreChoiceBox;

	HotelVO hotelVO;
	List<RoomVO> roomList;

	/**
	 * @param hotelID
	 * @throws RemoteException
	 *             酒店工作人员酒店详细信息面板
	 * 
	 */
	public WorkerHotelInfoPane(int hotelID) throws RemoteException {

		super();
		this.setHgap(10);
		this.setVgap(20);

		this.hotelID = hotelID;
		hotelVO = BLFactory.getInstance().getHotelBLService().getHotelInfo(hotelID);
		initInfoPane();
		initRoomPane(hotelID);

		this.add(new Text("酒店基本信息"), 0, 0, 1, 1);
		this.add(infoPane, 0, 1, 1, 1);

		this.add(new Text("房型列表"), 0, 2, 1, 1);
		this.add(roomBox, 0, 3, 2, 1);

		Label back = new Label();
		back.setFont(Font.font(icon.getFamily(), 28));
		back.setText(String.valueOf('\uf112'));
		backButton = new Button("返回", back);
		backButton.setWrapText(true);
		backButton.setContentDisplay(ContentDisplay.TOP);
		backButton.setId("back");
		backButton.setShape(new Circle(31));
		backButton.setMinSize(62, 62);
		backButton.setMaxSize(62, 62);

		ImageEditPane imageEditPane = new ImageEditPane(hotelID);
		this.add(imageEditPane, 1, 1, 1, 1);
		this.add(backButton, 1, 0, 1, 1);
		backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});
	}

	/**
	 * @param hotelID
	 * @throws RemoteException
	 *             酒店房间面板初始化
	 * 
	 */
	private void initRoomPane(int hotelID) throws RemoteException {

		roomList = BLFactory.getInstance().getRoomBLService().getRoomTypeList(hotelID);
		roomPane = new ScrollPane();
		roomBox = new VBox();
		addBox1 = new HBox();
		roomBox.setSpacing(10);
		addBox1.setSpacing(10);

		TableView<RoomCell> tableView = new TableView<>();

		tableView.setEditable(true);
		roomPane.setContent(tableView);

		TableColumn<RoomCell, String> roomNameCol = new TableColumn<>("房间类型");
		roomNameCol.setCellValueFactory(new PropertyValueFactory<>("roomName"));
		roomNameCol.setCellFactory(TextFieldTableCell.<RoomCell>forTableColumn());

		TableColumn<RoomCell, String> priceCol = new TableColumn<>("房间单价");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceCol.setCellFactory(TextFieldTableCell.<RoomCell>forTableColumn());

		TableColumn<RoomCell, String> numOfRoomCol = new TableColumn<>("房间数量");
		numOfRoomCol.setCellValueFactory(new PropertyValueFactory<>("numOfRoom"));
		numOfRoomCol.setCellFactory(TextFieldTableCell.<RoomCell>forTableColumn());

		TableColumn<RoomCell, String> serviceCol = new TableColumn<>("服务设施");
		serviceCol.setCellValueFactory(new PropertyValueFactory<>("service"));
		serviceCol.setCellFactory(TextFieldTableCell.<RoomCell>forTableColumn());

		TableColumn<RoomCell, String> maxNumOfPeopleCol = new TableColumn<>("最大房客数");
		maxNumOfPeopleCol.setCellValueFactory(new PropertyValueFactory<>("maxNumOfPeople"));
		maxNumOfPeopleCol.setCellFactory(TextFieldTableCell.<RoomCell>forTableColumn());

		tableView.getColumns().addAll(roomNameCol, priceCol, numOfRoomCol, serviceCol, maxNumOfPeopleCol);
		tableView.setPrefWidth(450);
		ObservableList<RoomCell> roomCells = FXCollections.observableArrayList();

		addRoomNameTextField = new TextField();
		addNumOfRoomTextField = new TextField();
		addServiceTextField = new TextField();
		addMaxNumOfPeopleTextField = new TextField();
		addPriceTextField = new TextField();

		addRoomNameTextField.setPromptText("房间类型");
		addNumOfRoomTextField.setPromptText("房间数量");
		addServiceTextField.setPromptText("服务设施");
		addMaxNumOfPeopleTextField.setPromptText("最大房客数");
		addPriceTextField.setPromptText("房间单价");

		addRoomNameTextField.setPrefColumnCount(COLUMN);
		addNumOfRoomTextField.setPrefColumnCount(COLUMN);
		addServiceTextField.setPrefColumnCount(COLUMN);
		addMaxNumOfPeopleTextField.setPrefColumnCount(COLUMN);
		addPriceTextField.setPrefColumnCount(COLUMN);

		for (RoomVO vo : roomList) {
			RoomCell cell = new RoomCell(vo);
			roomCells.add(cell);
		}
		addBox1.getChildren().addAll(addRoomNameTextField, addPriceTextField, addNumOfRoomTextField,
				addServiceTextField, addMaxNumOfPeopleTextField);
		
		addButton = new Button("新增房间类型");
		addButton.setOnAction((ActionEvent e) -> {
			if(addButton.getText().equals("确认添加")){
				roomCells.add(new RoomCell(addRoomNameTextField.getText(), addPriceTextField.getText(),
						addNumOfRoomTextField.getText(), addServiceTextField.getText(),
						addMaxNumOfPeopleTextField.getText()));
				RoomVO newVO = new RoomVO(-1, addRoomNameTextField.getText(), Integer.parseInt(addPriceTextField.getText()),
						Integer.parseInt(addNumOfRoomTextField.getText()), addServiceTextField.getText(),
						Integer.parseInt(addMaxNumOfPeopleTextField.getText()));
				roomList.add(newVO);

				addRoomNameTextField.clear();
				addNumOfRoomTextField.clear();
				addServiceTextField.clear();
				addMaxNumOfPeopleTextField.clear();
				addPriceTextField.clear();

				try {
					if (BLFactory.getInstance().getRoomBLService().addRoomType(hotelID, newVO)) {
						System.out.print("add a room type successfully.");
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				roomBox.getChildren().clear();
				roomBox.getChildren().addAll(roomPane, addButton);
				addButton.setText("新增房间类型");
			}
			else if(addButton.getText().equals("新增房间类型")){
				roomBox.getChildren().clear();
				roomBox.getChildren().addAll(roomPane, addBox1, addButton);
				addButton.setText("确认添加");
			}
			
		});

		tableView.setItems(roomCells);

		
		
		// this.getStylesheets().add(getClass().getResource("WorkerHotelInfoPane").toExternalForm());
	}

	/**
	 * 酒店信息面板初始化
	 */
	private void initInfoPane() {

		infoPane = new GridPane();

		infoPane.setHgap(10);
		infoPane.setVgap(10);

		infoEditButton = new Button("编辑");
		infoPane.add(infoEditButton, 0, 0, 1, 1);

		infoPane.add(new Text("酒店名称"), 0, 1, 1, 1);
		nameText = new Text(hotelVO.hotelName);
		infoPane.add(nameText, 1, 1, 1, 1);

		infoPane.add(new Text("酒店地址"), 0, 2, 1, 1);
		addressText = new Text(hotelVO.address);
		infoPane.add(addressText, 1, 2, 1, 1);

		infoPane.add(new Text("酒店简介"), 0, 3, 1, 1);
		introductionText = new Text(hotelVO.introduction);
		infoPane.add(introductionText, 1, 3, 1, 1);

		infoPane.add(new Text("设施服务"), 0, 4, 1, 1);
		serviceText = new Text(hotelVO.service);
		infoPane.add(serviceText, 1, 4, 1, 1);

		infoPane.add(new Text("所在商圈名"), 0, 5, 1, 1);
		businessCircleText = new Text(hotelVO.businessCircle);
		infoPane.add(businessCircleText, 1, 5, 1, 1);

		infoPane.add(new Text("酒店星级"), 0, 6, 1, 1);
		scoreText = new Text(hotelVO.score + "星");
		infoPane.add(scoreText, 1, 6, 1, 1);

		infoEditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			if (infoEditButton.getText().equals("编辑")) {
				infoPane.getChildren().removeAll(nameText, addressText, introductionText, serviceText,
						businessCircleText, scoreText);

				nameTextField = new TextField(hotelVO.hotelName);
				nameTextField.setPrefColumnCount(COLUMN);
				addressTextField = new TextField(hotelVO.address);
				addressTextField.setPrefColumnCount(COLUMN);
				introductionTextField = new TextField(hotelVO.introduction);
				introductionTextField.setPrefColumnCount(COLUMN);
				serviceTextField = new TextField(hotelVO.service);
				serviceTextField.setPrefColumnCount(COLUMN);
				ObservableList<String> businessCircleList = FXCollections.observableArrayList("栖霞区", "鼓楼区", "秦淮区");
				businessCircleBox = new ComboBox<String>(businessCircleList);
				businessCircleBox.getSelectionModel().select(0);

				ObservableList<String> scoreList = FXCollections.observableArrayList("1星", "2星", "3星", "4星", "5星");
				scoreChoiceBox = new ComboBox<>(scoreList);
				scoreChoiceBox.getSelectionModel().select(hotelVO.score - 1);

				infoPane.add(nameTextField, 1, 1, 1, 1);
				infoPane.add(addressTextField, 1, 2, 1, 1);
				infoPane.add(introductionTextField, 1, 3, 1, 1);
				infoPane.add(serviceTextField, 1, 4, 1, 1);
				infoPane.add(businessCircleBox, 1, 5, 1, 1);
				infoPane.add(scoreChoiceBox, 1, 6, 1, 1);

				infoEditButton.setText("保存");
			} else if (infoEditButton.getText().equals("保存")) {
				infoPane.getChildren().removeAll(nameTextField, addressTextField, introductionTextField,
						serviceTextField, businessCircleBox, scoreChoiceBox);
				infoPane.add(nameText, 1, 1, 1, 1);
				infoPane.add(addressText, 1, 2, 1, 1);
				infoPane.add(introductionText, 1, 3, 1, 1);
				infoPane.add(serviceText, 1, 4, 1, 1);
				infoPane.add(businessCircleText, 1, 5, 1, 1);
				infoPane.add(scoreText, 1, 6, 1, 1);

				HotelVO newVO = null;
				try {
					newVO = BLFactory.getInstance().getHotelBLService().getHotelInfo(hotelID);

				} catch (RemoteException e) {
					e.printStackTrace();
				}

				newVO.hotelName = nameTextField.getText();
				newVO.address = addressTextField.getText();
				newVO.introduction = introductionTextField.getText();
				newVO.service = serviceTextField.getText();
				newVO.businessCircle = businessCircleBox.getValue().toString();
				newVO.score = scoreChoiceBox.getSelectionModel().getSelectedIndex() + 1;

				try {
					if (BLFactory.getInstance().getHotelBLService().updateSimpleHotelInfo(newVO)) {
						nameText.setText(nameTextField.getText());
						addressText.setText(addressTextField.getText());
						introductionText.setText(introductionTextField.getText());
						serviceText.setText(serviceTextField.getText());
						businessCircleText.setText(businessCircleBox.getValue().toString());
						scoreText.setText(
								String.valueOf(scoreChoiceBox.getSelectionModel().getSelectedIndex() + 1) + "星");
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				infoEditButton.setText("编辑");
			}
		});
	}
}
