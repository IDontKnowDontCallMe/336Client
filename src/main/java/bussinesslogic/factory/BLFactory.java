package bussinesslogic.factory;

import java.rmi.Remote;
import rmi.RemoteCustomerService;
import rmi.RemoteHotelBLService;
import rmi.RemoteOrderBLService;
import rmi.RemotePromotionBLService;
import rmi.RemoteRoomBLService;
import rmi.RemoteUserBLService;

/**
 * @author samperson1997
 * 业务逻辑层工厂
 *
 */
public class BLFactory {
	private Remote remote;
	private static BLFactory blFactory = new BLFactory();
	public static BLFactory getInstance(){
		return blFactory;
	}
	
	/**
	 * 业务逻辑层工厂
	 */
	private BLFactory() {
		
	}
	
	/**
	 * @param remote
	 * 设置romote
	 */
	public void setRemote(Remote remote){
		this.remote = remote;
	}
	
	/**
	 * @return
	 * 获得customer模块blservice
	 */
	public RemoteCustomerService getCustomerBLService() {
		return (RemoteCustomerService)remote;
	}
	
	/**
	 * @return
	 * 获得hotel模块blservice
	 */
	public RemoteHotelBLService getHotelBLService() {
		return (RemoteHotelBLService)remote;
	}
	
	/**
	 * @return
	 * 获得order模块blservice
	 */
	public RemoteOrderBLService getOrderBLService() {
		return (RemoteOrderBLService)remote;
	}
	
	/**
	 * @return
	 * 获得promotion模块blservice
	 */
	public RemotePromotionBLService getPromotionBLService() {
		return (RemotePromotionBLService)remote;
	}
	
	/**
	 * @return
	 * 获得room模块blservice
	 */
	public RemoteRoomBLService getRoomBLService() {
		return (RemoteRoomBLService)remote;
	}
	
	
	/**
	 * @return
	 * 获得user模块blservice
	 */
	public RemoteUserBLService getUserBLService() {
		return (RemoteUserBLService)remote;
	}
	
	
}
