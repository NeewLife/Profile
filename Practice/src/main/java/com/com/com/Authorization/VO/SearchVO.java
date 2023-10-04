package com.com.com.Authorization.VO;

import lombok.Data;

@Data
public class SearchVO {
	
	private String searchType;
	private String keyword;
	private String authType;
	private String startDate;
	private String endDate;
	
}
