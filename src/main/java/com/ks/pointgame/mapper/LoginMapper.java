package com.ks.pointgame.mapper;

import org.springframework.stereotype.Repository;

import com.ks.pointgame.domain.MemberInfo;
import com.ks.pointgame.dto.MemberDto;

@Repository
public interface LoginMapper {
	
	public MemberInfo findByNumber(MemberDto mem);
	
	public int updateRecDate(MemberDto mem);
	
	
}	
