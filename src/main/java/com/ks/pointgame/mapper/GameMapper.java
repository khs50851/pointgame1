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
	
	// 会員のポイント情報update
	public int updatePoint(PointDto pdto);
	
	// 会員のポイント情報探し
	public int findPointByNumber(MemberInfo mem);
	
	// 会員の今日のゲームの回数を探す
	public int checkTodayGameCount(InputNumDto dto);
	
	// 会員のゲーム進行情報を入力
	public int insertGamest(GamestDto gdto);
	
	// 正解を当てられない場合、ポイントテーブルの時間だけupdate
	public int updatePointInfoDate(InputNumDto dto);
	
	// 今日会員がゲームに勝ったかを確認
	public int findCheckResult(InputNumDto dto);
	
	// 今日会員がしたゲームでの隠れ数字を探す
	public int findhidedNumber(InputNumDto dto);
	
	// 会員の今日のゲームの進行状況を探す
	public List<GameStDtoResp> gameStList(MemberInfo mem);
	
	// 今まで会員がゲームをしていた回数を出力
	public int findCountUntilNowByMember(String mem);
	
	// pointinfoテーブルに会員情報insert
	public int insertPointinfo(String mem);
}
