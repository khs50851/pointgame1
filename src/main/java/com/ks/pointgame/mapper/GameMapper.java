package com.ks.pointgame.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ks.pointgame.domain.MemberInfo;
import com.ks.pointgame.domain.PointInfo;
import com.ks.pointgame.dto.GameStDtoResp;
import com.ks.pointgame.dto.GamestDto;
import com.ks.pointgame.dto.InputNumDto;
import com.ks.pointgame.dto.PointDto;

@Repository
public interface GameMapper {
	
	public int updatePoint(PointDto pdto);
	
	public int findPointByNumber(MemberInfo mem);
	
	public int checkTodayGameCount(InputNumDto dto);
	
	public int insertGamest(GamestDto gdto);
	
	public int updatePointInfoDate(InputNumDto dto);
	
	public int findCheckResult(InputNumDto dto);
	
	public int findhidedNumber(InputNumDto dto);
	
	public List<GameStDtoResp> gameStList(MemberInfo mem);
}
