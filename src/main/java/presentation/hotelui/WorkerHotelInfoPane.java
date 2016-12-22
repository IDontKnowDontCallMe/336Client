package presentation.hotelui;

import java.rmi.RemoteException;
import java.util.List;
import bussinesslogic.factory.BLFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import presentation.mainui.CustomerMainPane;
import presentation.mainui.HotelWorkerPilot;
import presentation.mainui.PasswordEditDialog;
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
	private Button infoEditButton;
	private Button addButton;
	private Button editPasswordButton;

	private Label nameText;
	private Label addressText;
	private Label introductionText;
	private Label serviceText;
	private Label businessCircleText;
	private Label scoreText;
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
		GridPane gridPane = new GridPane();
		gridPane.setId("grid");

		gridPane.setHgap(10);
		gridPane.setVgap(20);

		this.hotelID = hotelID;
		hotelVO = BLFactory.getInstance().getHotelBLService().getHotelInfo(hotelID);
		initInfoPane();
		initRoomPane(hotelID);

		Label label = new Label("酒店基本信息");
		label.setId("l1");
		gridPane.add(label, 1, 0, 2, 1);
		gridPane.add(infoPane, 2, 1, 2, 1);

		Label label2 = new Label("房型列表");
		label2.setId("l2");
		gridPane.add(label2, 1, 2, 1, 1);
		gridPane.add(roomBox, 1, 3, 2, 1);
		roomBox.setTranslateX(50.0);

		ImageEditPane imageEditPane = new ImageEditPane(hotelID);
		gridPane.add(imageEditPane, 4, 1, 2, 1);

		HotelWorkerPilot hotelWorkerPilot = new HotelWorkerPilot(hotelID);
		this.add(hotelWorkerPilot, 0, 0);
		this.add(gridPane, 1, 0);
		gridPane.getStylesheets().add(getClass().getResource("WorkerHotelInfoPane.css").toExternalForm());

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
		//roomPane.getStyleClass().add("edge-to-edge");
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
		tableView.autosize();
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
		roomBox.getChildren().addAll(addButton, roomPane);
		addButton.setOnAction((ActionEvent e) -> {
			if (addButton.getText().equals("确认添加")) {
				if (addRoomNameTextField.getText().equals("") || addPriceTextField.getText().equals("")
						|| addNumOfRoomTextField.getText().equals("") || addServiceTextField.getText().equals("")
						|| addMaxNumOfPeopleTextField.getText().equals("")) {
					Stage popup3 = new Stage();
					popup3.setAlwaysOnTop(true);
					popup3.initModality(Modality.APPLICATION_MODAL);
					Button closeButton3 = new Button("确定");
					closeButton3.setOnAction(e1 -> {
						popup3.close();
					});
					VBox root3 = new VBox();
					root3.setAlignment(Pos.BASELINE_CENTER);
					root3.setSpacing(20);
					root3.getChildren().addAll(new Label("请将客房信息填写完整！"), closeButton3);
					Scene scene3 = new Scene(root3, 200, 90);
					popup3.setScene(scene3);
					popup3.setTitle("提示");
					popup3.show();
				} else {
					roomCells.add(new RoomCell(addRoomNameTextField.getText(), addPriceTextField.getText(),
							addNumOfRoomTextField.getText(), addServiceTextField.getText(),
							addMaxNumOfPeopleTextField.getText()));
					RoomVO newVO = new RoomVO(-1, addRoomNameTextField.getText(),
							Integer.parseInt(addPriceTextField.getText()),
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
					roomBox.getChildren().addAll(addButton, roomPane);
					addButton.setText("新增房间类型");
				}

			} else if (addButton.getText().equals("新增房间类型")) {
				roomBox.getChildren().clear();
				roomBox.getChildren().addAll(addButton, addBox1, roomPane);
				addButton.setText("确认添加");
			}

		});

		tableView.setItems(roomCells);

	}

	/**
	 * 酒店信息面板初始化
	 */
	private void initInfoPane() {

		infoPane = new GridPane();
		infoPane.setId("infopane");

		infoPane.setHgap(10);
		infoPane.setVgap(10);

		infoEditButton = new Button("编辑");
		infoPane.add(infoEditButton, 0, 0, 1, 1);

		infoPane.add(new Label("酒店名称"), 0, 1, 1, 1);
		nameText = new Label(hotelVO.hotelName);
		infoPane.add(nameText, 1, 1, 1, 1);

		infoPane.add(new Label("酒店地址"), 0, 2, 1, 1);
		addressText = new Label(hotelVO.address);
		infoPane.add(addressText, 1, 2, 1, 1);

		infoPane.add(new Label("酒店简介"), 0, 3, 1, 1);
		introductionText = new Label(hotelVO.introduction);
		infoPane.add(introductionText, 1, 3, 1, 1);

		infoPane.add(new Label("设施服务"), 0, 4, 1, 1);
		serviceText = new Label(hotelVO.service);
		infoPane.add(serviceText, 1, 4, 1, 1);

		infoPane.add(new Label("所在商圈名"), 0, 5, 1, 1);
		businessCircleText = new Label(hotelVO.businessCircle);
		infoPane.add(businessCircleText, 1, 5, 1, 1);

		infoPane.add(new Label("酒店星级"), 0, 6, 1, 1);
		scoreText = new Label(hotelVO.score + "星");
		infoPane.add(scoreText, 1, 6, 1, 1);

		editPasswordButton = new Button("修改密码");
		infoPane.add(editPasswordButton, 1, 0, 1, 1);

		editPasswordButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			PasswordEditDialog passwordEditDialog = new PasswordEditDialog(hotelID);
			passwordEditDialog.show();
		});

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
