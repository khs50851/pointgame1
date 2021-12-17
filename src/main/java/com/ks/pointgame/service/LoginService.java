package com.ks.pointgame.service;

import org.springframework.stereotype.Service;

import com.ks.pointgame.domain.MemberInfo;
import com.ks.pointgame.dto.MemberDto;
import com.ks.pointgame.mapper.LoginMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {
	
	private final LoginMapper loginMapper;
	
	public MemberInfo findByNumber(MemberDto dto) {
		return loginMapper.findByNumber(dto);
	}
	
	public int updateRecDate(MemberDto dto) {
		return loginMapper.updateRecDate(dto);
	}
	
}
