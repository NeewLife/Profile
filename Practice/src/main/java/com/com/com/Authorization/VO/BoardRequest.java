package com.com.com.Authorization.VO;

import lombok.Data;

@Data
public class BoardRequest {
	
	private int postId;
	private int writerPkNum;
	private String writer;
	private String title;
	private String content;
	private String confirmDate;
	private String confirmPerson;
	private String confirmStatus;
	private int proxyConfirmPerson;
}
	