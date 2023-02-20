package com.study.domain.resttime;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResttimeMapper {
	
	/* List<String> restAsMbti */
	String getRestAsMbti (ResttimeRequest params);
	Integer randomNum (ResttimeRequest params);

}
