package businesslogicservice.customerblservice;

import vo.CustomerVO;

public interface CustomerInfoblService {
	
	public CustomerVO[] getInfoList();
	
	public CustomerVO getInfo(String customerID);

}
