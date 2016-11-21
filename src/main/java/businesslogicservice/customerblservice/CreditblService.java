package businesslogicservice.customerblservice;

import po.CreditPO;
import po.CustomerPO;
import vo.CreditVO;

public interface CreditblService {

	public CreditVO[] checkCredit(CustomerPO po);
	
	
	public CreditPO concreteCheckCredit (CustomerPO po, int index);
	
}
