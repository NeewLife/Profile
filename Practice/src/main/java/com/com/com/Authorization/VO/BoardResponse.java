package com.com.com.Authorization.VO;

import lombok.Data;

@Data
public class BoardResponse {

	private int postId;
	private String title;
	private int writerPkNum;
	private String writer = "";
	private String writeDate;
	private String content = "";
	private String confirmDate = "";
	private String confirmPerson = "";
	private String proxyConfirmPerson;
	private String confirmStatus;
}
