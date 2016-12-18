package vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author samperson1997
 * 信用值vo
 *
 */
public class CreditVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int customerID;
	public LocalDateTime producingDateTime;
	public String orderID;
	public String action;
	public int creditDelta;
	public int creditResult;
	
	public CreditVO(int customerID, LocalDateTime producingDateTime, String orderID, String action, int creditDelta, int creditResult) {
		// TODO Auto-generated constructor stub
		this.customerID = customerID;
		this.producingDateTime = producingDateTime;
		this.orderID = orderID;
		this.action = action;
		this.creditDelta = creditDelta;
		this.creditResult = creditResult;
	}
	
	
	
}
