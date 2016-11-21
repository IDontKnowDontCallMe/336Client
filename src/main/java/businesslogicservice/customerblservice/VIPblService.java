package businesslogicservice.customerblservice;

import po.CustomerPO;
import po.LevelPO;

public interface VIPblService {
	
	public void registerVIP(CustomerPO po);
	
	public void updateLevel(CustomerPO cpo, LevelPO lpo);

}
