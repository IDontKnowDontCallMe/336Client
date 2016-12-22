package presentation.customerui;

import java.time.LocalDate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

import bussinesslogic.factory.BLFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import presentation.mainui.CustomerMainPane;
import presentation.mainui.CustomerPilot;
import presentation.mainui.PasswordEditDialog;
import presentation.mainui.TheMainFrame;
import vo.CreditVO;
import vo.CustomerVO;

/**
 * @author samperson1997 客户面板
 */
public class CustomerInfoPane extends GridPane {

	private int customerID;

	private GridPane infoPane;
	private Label nameText;
	private Label phoneNumberText;
	private Label levelText;
	private Button setBirthVIPButton;
	private Button setCompanyVIPButton;
	private TextField nameTextField;
	private TextField phoneTextField;
	private Button editPasswordButton;
	private Button editButton;
	private Button backButton;
	private HBox titleBox;
	private TableView<CreditCell> tableView;
	private Label complabel;
	private Label birthlabel;

	private ScrollPane creditPane;

	/**
	 * @param customerID
	 * @throws RemoteException
	 *             客户面板
	 */
	public CustomerInfoPane(int customerID) throws RemoteException {

		super();
		this.customerID = customerID;
		Font icon = Font.loadFont(CustomerMainPane.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);

		Pane pane = new Pane();
		titleBox = new HBox();

		Label back = new Label();
		back.setFont(Font.font(icon.getFamily(), 30));
		back.setText(String.valueOf('\uf112'));
		backButton = new Button("返回", back);
		backButton.setWrapText(true);
		backButton.setContentDisplay(ContentDisplay.TOP);
		backButton.setId("backButton");
		backButton.setShape(new Circle(35));
		backButton.setMinSize(70, 70);
		backButton.setMaxSize(70, 70);
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			TheMainFrame.backTo();
		});

		titleBox.getChildren().add(backButton);

		initInfoPane();
		initCreditList();

		pane.getChildren().addAll(backButton, titleBox, infoPane, creditPane);
		infoPane.setLayoutX(100.0);
		infoPane.setLayoutY(90.0);
		creditPane.setLayoutX(120.0);
		creditPane.setLayoutY(260.0);
		backButton.setLayoutX(985.0);
		backButton.setLayoutY(30.0);

		CustomerPilot customerPilot = new CustomerPilot(customerID);

		this.add(customerPilot, 0, 0);
		this.add(pane, 1, 0);
		this.getStylesheets().add(getClass().getResource("CustomerInfoPane.css").toExternalForm());
	}

	/**
	 * @throws RemoteException
	 *             初始化客户信息面板
	 */
	private void initInfoPane() throws RemoteException {

		CustomerVO customerVO = BLFactory.getInstance().getCustomerBLService().getCustomerInfo(customerID);

		infoPane = new GridPane();

		Label name = new Label("姓名");
		infoPane.add(name, 0, 0, 1, 1);
		name.setId("name");
		nameText = new Label(customerVO.customerName);
		nameText.setId("nametext");
		infoPane.add(nameText, 1, 0, 1, 1);
		Label phone = new Label("电话");
		phone.setId("phone");
		infoPane.add(phone, 0, 1, 1, 1);
		phoneNumberText = new Label(customerVO.phoneNumber);
		phoneNumberText.setId("phonetext");
		infoPane.add(phoneNumberText, 1, 1, 1, 1);
		Label level = new Label("等级");
		level.setId("level");
		infoPane.add(level, 0, 2, 1, 1);
		levelText = new Label(String.valueOf(customerVO.level));
		infoPane.add(levelText, 1, 2, 1, 1);
		levelText.setId("leveltext");
		Label birth = new Label("生日会员");
		birth.setId("birth");
		infoPane.add(birth, 0, 3, 1, 1);
		setBirthVIPButton = new Button("注册");
		if (!BLFactory.getInstance().getCustomerBLService().getCustomerInfo(customerID).isBirthVIP) {
			infoPane.add(setBirthVIPButton, 4, 3, 1, 1);
			setBirthVIPButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
				Dialog<LocalDate> birthVIPDialog = new BirthVIPDialog();

				Optional<LocalDate> result = birthVIPDialog.showAndWait();
				if (result.isPresent()) {
					try {
						if (BLFactory.getInstance().getCustomerBLService().registerBirthVIP(customerID, result.get())) {
							infoPane.getChildren().remove(setBirthVIPButton);
							birthlabel = new Label("  已注册，生日为" + result.get().toString());
							birthlabel.setId("bid");
							infoPane.add(birthlabel, 1, 3, 1, 1);
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} else {
			birthlabel = new Label("  已注册，生日为" + customerVO.birthday.toString());
			birthlabel.setId("bid");
			infoPane.add(birthlabel, 1, 3, 1, 1);
		}

		Label comp = new Label("企业会员");
		comp.setId("comp");
		infoPane.add(comp, 0, 4, 1, 1);
		setCompanyVIPButton = new Button("注册");
		if (!BLFactory.getInstance().getCustomerBLService().getCustomerInfo(customerID).isCompanyVIP) {
			infoPane.add(setCompanyVIPButton, 4, 4, 1, 1);
			setCompanyVIPButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
				Dialog<String> companyVIPDialog = new CompanyVIPDialog();

				Optional<String> result = companyVIPDialog.showAndWait();
				if (result.isPresent()) {

					try {
						if (BLFactory.getInstance().getCustomerBLService().registerCompanyVIP(customerID,
								result.get())) {
							infoPane.getChildren().remove(setCompanyVIPButton);
							complabel = new Label("  已注册，企业名称为 " + result.get());
							complabel.setId("compl");
							infoPane.add(comp, 1, 4, 1, 1);
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} else {
			complabel = new Label("  已注册，企业名称为" + customerVO.companyName);
			complabel.setId("compl");
			infoPane.add(complabel, 1, 4, 1, 1);
		}

		editButton = new Button("编辑");
		infoPane.add(editButton, 5, 1, 1, 1);

		nameTextField = new TextField();
		nameTextField.setPrefColumnCount(8);
		phoneTextField = new TextField();
		phoneTextField.setPrefColumnCount(8);

		editPasswordButton = new Button("修改密码");
		infoPane.add(editPasswordButton, 6, 1, 1, 1);
		
		editPasswordButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			PasswordEditDialog passwordEditDialog = new PasswordEditDialog(customerID);
			passwordEditDialog.show();
		});
		
		editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			if (editButton.getText().equals("编辑")) {
				infoPane.getChildren().removeAll(nameText, phoneNumberText);
				infoPane.add(nameTextField, 1, 0, 1, 1);
				infoPane.add(phoneTextField, 1, 1, 1, 1);
				nameTextField.setText(nameText.getText());
				phoneTextField.setText(phoneNumberText.getText());
				editButton.setText("保存");
			} else if (editButton.getText().equals("保存")) {
				infoPane.getChildren().removeAll(nameTextField, phoneTextField);
				infoPane.add(nameText, 1, 0, 1, 1);
				infoPane.add(phoneNumberText, 1, 1, 1, 1);

				CustomerVO newVO = null;
				try {
					newVO = BLFactory.getInstance().getCustomerBLService().getCustomerInfo(customerID);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				newVO.customerName = nameTextField.getText();
				newVO.phoneNumber = phoneTextField.getText();
				try {
					if (BLFactory.getInstance().getCustomerBLService().updateCustomerInfo(newVO)) {
						nameText.setText(nameTextField.getText());
						phoneNumberText.setText(phoneTextField.getText());
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				editButton.setText("编辑");
			}
		});

	}

	/**
	 * @throws RemoteException
	 *             初始化信用值列表
	 */
	private void initCreditList() throws RemoteException {

		creditPane = new ScrollPane();
		tableView = new TableView<>();
		creditPane.setContent(tableView);

		TableColumn<CreditCell, String> producingTimeCol = new TableColumn<>("时间");
		producingTimeCol.setMinWidth(180.0);
		producingTimeCol.setCellValueFactory(new PropertyValueFactory<>("producingTime"));
		TableColumn<CreditCell, String> orderIDCol = new TableColumn<>("订单号");
		orderIDCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));
		orderIDCol.setMinWidth(100.0);
		TableColumn<CreditCell, String> actionCol = new TableColumn<>("原因");
		actionCol.setCellValueFactory(new PropertyValueFactory<>("action"));
		actionCol.setMinWidth(200.0);
		TableColumn<CreditCell, String> creditDeltaCol = new TableColumn<>("变化");
		creditDeltaCol.setCellValueFactory(new PropertyValueFactory<>("creditDelta"));
		creditDeltaCol.setMinWidth(100.0);
		TableColumn<CreditCell, String> creditResultCol = new TableColumn<>("结果");
		creditResultCol.setCellValueFactory(new PropertyValueFactory<>("creditResult"));
		creditResultCol.setMinWidth(100.0);

		tableView.getColumns().addAll(producingTimeCol, orderIDCol, actionCol, creditDeltaCol, creditResultCol);
		List<CreditVO> creditList = BLFactory.getInstance().getCustomerBLService().getCreditList(customerID);
		ObservableList<CreditCell> creditCells = FXCollections.observableArrayList();
		for (CreditVO vo : creditList) {
			CreditCell cell = new CreditCell(vo);
			creditCells.add(cell);
		}

		tableView.setItems(creditCells);
	}

	/**
	 * @author samperson1997 信用值单元格
	 */
	public class CreditCell {

		private SimpleStringProperty producingTime;
		private SimpleStringProperty orderID;
		private SimpleStringProperty action;
		private SimpleStringProperty creditDelta;
		private SimpleStringProperty creditResult;

		public CreditCell(CreditVO vo) {
			producingTime = new SimpleStringProperty(vo.producingDateTime.toString());
			orderID = new SimpleStringProperty(vo.orderID);
			action = new SimpleStringProperty(String.valueOf(vo.action));
			creditDelta = new SimpleStringProperty(String.valueOf(vo.creditDelta));
			creditResult = new SimpleStringProperty(String.valueOf(vo.creditResult));
		}

		public void setProducingTime(String s) {
			producingTime.set(s);
		}

		public String getProducingTime() {
			return producingTime.get();
		}

		public void setOrderID(String s) {
			orderID.set(s);
		}

		public String getOrderID() {
			return orderID.get();
		}

		public void setAction(String s) {
			action.set(s);
		}

		public String getAction() {
			return action.get();
		}

		public void setCreditDelta(String s) {
			creditDelta.set(s);
		}

		public String getCreditDelta() {
			return creditDelta.get();
		}

		public void setCreditResult(String s) {
			creditResult.set(s);
		}

		public String getCreditResult() {
			return creditResult.get();
		}

	}
}
