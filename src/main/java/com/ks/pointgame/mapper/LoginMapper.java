package com.ks.pointgame.mapper;

import org.springframework.stereotype.Repository;

import com.ks.pointgame.domain.MemberInfo;
import com.ks.pointgame.dto.MemberDto;

@Repository
public interface LoginMapper {
	
	// 会員のIDとパスワードで登録されている会員であるかを確認。
	public MemberInfo findByNumber(MemberDto mem);
	
	// ログインした時間を現在の時間にupdate
	public int updateRecDate(MemberDto mem);
	
	
}	
