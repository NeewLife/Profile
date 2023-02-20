package com.study.domain.resttime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResttimeService {
	private final ResttimeMapper resttimeMapper;
	
	// resttimeMapper에서 휴식방법 가져오기
	public String getRestAsMbti(final ResttimeRequest params) {
		return resttimeMapper.getRestAsMbti(params);
	}
}
