package vo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author samperson1997
 * 酒店促销策略vo
 *
 */
public class HotelPromotionVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int hotelID;

	public String promotionType;
	public LocalDate startTime;
	public LocalDate endTime;
	public String companyName;
	public int minNum;
	public double discount;

	public HotelPromotionVO(int hotelID, String promotionType, LocalDate startTime, LocalDate endTime,
			String companyName, int minNum, double discount) {
		this.hotelID = hotelID;
		this.promotionType = promotionType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.companyName = companyName;
		this.minNum = minNum;
		this.discount = discount;
	}

	public HotelPromotionVO(HotelPromotionVO vo) {
		this.hotelID = vo.hotelID;
		this.promotionType = vo.promotionType;
		this.startTime = vo.startTime;
		this.endTime = vo.endTime;
		this.companyName = vo.companyName;
		this.minNum = vo.minNum;
		this.discount = vo.discount;
	}
}
