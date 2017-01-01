package presentation.mainui;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import bussinesslogic.factory.BLFactory;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import presentation.hotelui.ImageEditPane;

/**
 * 
 * @author samperson1997
 * 主界面的方法
 *
 */
public class TheMainFrame extends Application {

	private static Stack<Parent> parentStack;
	private static Scene scene;
	private BLFactory blFactory;
	
	private static Timer loginTimer = new Timer(true);
	private static int id = -1;
	
	/**
	 * 用于设置当前登录的id，id用于服务器端验证在线情况。
	 * id为-1表示当前程序为登录任一账号
	 * @param settingID
	 */
	public static void setLoginID(int settingID){
		id = settingID;
	}

	/**
	 * 用于多级界面的跳转至下一界面
	 * @param parent
	 */
	public static void jumpTo(Parent parent) {

		parentStack.push(scene.getRoot());
		scene.setRoot(parent);

	}
	
	/**
	 * 用于导航栏的跳转
	 * @param parent
	 */
	public static void changeTo(Parent parent){
		scene.setRoot(parent);
		parentStack.clear();
	}

	/**
	 * 用于多级界面订单返回上一界面
	 */
	public static void backTo() {
		scene.setRoot(parentStack.pop());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		linkToServer();

		scene = new Scene(new LoginPane(), 1100, 700);
		parentStack = new Stack<Parent>();
		
		
		primaryStage.setScene(scene);

		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * 连接至服务器
	 * 
	 */
	private void linkToServer() {
		// 172.26.102.100
		// 114.212.43.130
		while(true){
			
			try {
				
				String url = null;
				
				Dialog<String> ipDialog = new ServerIPDialog();
				
				url = ipDialog.showAndWait().get();
				if(url.equals("cancel")){
					System.out.println("退出");
					System.exit(0);
				}
				
				blFactory = BLFactory.getInstance();

				blFactory.setRemote(Naming.lookup("rmi://" + url + "/controllerRemoteFactory"));
				
				loginTimer.schedule(new SurvivalTast(), 1000, 1000);

				System.out.println("linked");
				
				break;
			} catch (MalformedURLException e) {
				//e.printStackTrace();
				System.out.println("地址输入格式有误");
				continue;
			} catch (RemoteException e) {
				//e.printStackTrace();
				System.out.println("连接失败");
				continue;
			} catch (NotBoundException e) {
				//e.printStackTrace();
				System.out.println("连接失败");
				continue;
			}
			
		}
	}
	
	
	/**
	 * @author samperson1997 
	 * 提醒服务器端此账号仍然登陆
	 *
	 */
	public class SurvivalTast extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				BLFactory.getInstance().getUserBLService().survivalConfirm(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
