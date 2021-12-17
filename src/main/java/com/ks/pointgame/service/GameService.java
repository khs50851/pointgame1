package com.ks.pointgame.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ks.pointgame.domain.MemberInfo;
import com.ks.pointgame.domain.PointInfo;
import com.ks.pointgame.dto.GameStDtoResp;
import com.ks.pointgame.dto.GamestDto;
import com.ks.pointgame.dto.InputNumDto;
import com.ks.pointgame.dto.PointDto;
import com.ks.pointgame.game.Game;
import com.ks.pointgame.mapper.GameMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GameService {
	
	private final GameMapper gameMapper;
	
	public int updatePoint(PointDto pdto) {
		return gameMapper.updatePoint(pdto);
	}
	
	public int findPointByNumber(MemberInfo mem) {
		return gameMapper.findPointByNumber(mem);
	}
	
	public int checkTodayGameCount(InputNumDto dto) {
		return gameMapper.checkTodayGameCount(dto);
	}
	
	
	public int updatePointInfoDate(InputNumDto dto) {
		return gameMapper.updatePointInfoDate(dto);
	}
	
	public int findCheckResult(InputNumDto dto) {
		return gameMapper.findCheckResult(dto);
	}
	
	public int findhidedNumber(InputNumDto dto) {
		return gameMapper.findhidedNumber(dto);
	}
	
	
	public int insertGamest(GamestDto gdto) {
		return gameMapper.insertGamest(gdto);
	}
	
	public List<GameStDtoResp> gameStList(MemberInfo mem){
		return gameMapper.gameStList(mem);
	}
}
