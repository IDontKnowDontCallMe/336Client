package vo;

import java.io.Serializable;

/**
 * @author samperson1997
 * 会员等级制度vo
 *
 */
public class LevelVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int creditDistance;
	public int maxLevel;
	public double discountDistance;
	
	public LevelVO(int creditDistance, int maxLevel,double discountDistance){
		this.creditDistance=creditDistance;
		this.maxLevel=maxLevel;
		this.discountDistance=discountDistance;
	}
	
	public LevelVO(LevelVO vo){
		this.creditDistance=vo.creditDistance;
		this.maxLevel=vo.maxLevel;
		this.discountDistance=vo.discountDistance;
	}
}
