/**
 * 
 */
package com.terima.irail.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author vignesh
 *
 */
@Data
@ToString
public class IRailInfo {
	
	private String trainNo;
	
	private String trainName;
	
	private String orgiStation;
	
	private String depatureDt;
	
	private String destStation;
	
	private String arrDt;
	
	private String travelTime;
	
	private RailAvailablity availDays;

}
