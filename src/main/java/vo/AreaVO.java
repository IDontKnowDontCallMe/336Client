package vo;

import java.io.Serializable;

/**
 * @author samperson1997
 * 地区vo
 *
 */
public class AreaVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String city;
	public String businessCircle;
	
	public AreaVO(String city, String businessCircle) {
		// TODO Auto-generated constructor stub
		this.city = city;
		this.businessCircle = businessCircle;
	}
}
