package com.ks.pointgame.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.pointgame.domain.MemberInfo;
import com.ks.pointgame.dto.CMRespDto;
import com.ks.pointgame.dto.GameStDtoResp;
import com.ks.pointgame.dto.GamestDto;
import com.ks.pointgame.dto.InputNumDto;
import com.ks.pointgame.dto.PointDto;
import com.ks.pointgame.game.Game;
import com.ks.pointgame.service.GameService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class GameController {

	private final GameService gameService;

	@GetMapping("/game")
	public String game(HttpServletRequest req,Model model) {
		
		HttpSession session = req.getSession();
		MemberInfo mem = (MemberInfo)session.getAttribute("member");
		if(mem==null) {
			return "login/login";
		}
		int po = gameService.findPointByNumber(mem);
		
		List<GameStDtoResp> list = gameService.gameStList(mem);
		
		// 判定結果がSSSだったら当たる、Xだったらはずれ入力。
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getCheck_result().equals("SSS")) {
				list.get(i).setCheck_result("当たる");
			}
			if(list.get(i).getCheck_result().equals("X")) {
				list.get(i).setCheck_result("はずれ");
			}
		}
		int input_num = 0;
		int inputLength = 0;
		for(int i=0;i<list.size();i++) {
			input_num = list.get(i).getInput_number();
			inputLength = (int)(Math.log10(input_num)+1);
			list.get(i).setPoint(inputLength);
		}
		model.addAttribute("list", list);
		model.addAttribute("point", po);
		return "gamestatus/game";
	}

	@PostMapping("/game")
	public @ResponseBody ResponseEntity<?> inputNum(InputNumDto dto) {
		
		// 今日のゲームで勝った内容があるか確認。
		int checkTodaysResult = gameService.findCheckResult(dto);
		if (checkTodaysResult == 1) {
			return new ResponseEntity<>(new CMRespDto<>(1,"今日はすでに当たられました。 ゲームを進行できません。",null),HttpStatus.BAD_REQUEST);
		}
		
		Game game = new Game();
		
		// 入力した数字の中に重複している数があるか検査。
		int temp = dto.getEnter_num();
		ArrayList<Integer> duplicateInputNumCheck = game.myNum(temp);
		
		for(int i=0;i<2;i++) {
			for(int j=i+1;j<3;j++) {
				if (duplicateInputNumCheck.get(i)==duplicateInputNumCheck.get(j)) {
					return new ResponseEntity<>(new CMRespDto<>(-1,"重複していない数を入力してください。",null),HttpStatus.BAD_REQUEST);
				}
			}
		}
		
		int point1000 = 1000;
		int point500 = 500;
		int point200 = 200;
		// 今日のゲーム回数
		int checkTodayGameCount = gameService.checkTodayGameCount(dto);
		System.out.println("今日のゲーム回数 : "+checkTodayGameCount);
		int hidedNumber = 0;
		PointDto pdto = new PointDto();
		GamestDto gdto = new GamestDto();
		// 0回目のhit回数1回目インデックスにmissed保存されている。3回目は隠れた数字
		
		
		// 오늘 게임한 횟수 0-5회차
		if (0 <= checkTodayGameCount && checkTodayGameCount <= 4) {
			// 오늘 첫게임인지 확인
			if (checkTodayGameCount == 0) {
				ArrayList<Integer> hitAndMissed = game.gameStart(dto.getEnter_num());
				if (hitAndMissed.get(0) == 3 && hitAndMissed.get(1) == 0) {
					pdto.setMember_number(dto.getMember_number());
					pdto.setPoint(point1000);
					gameService.updatePoint(pdto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("SSS");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					
					return new ResponseEntity<>(new CMRespDto<>(1,"挑戦に成功しました。 今日はこれ以上、ゲームを進めることができません。",gdto),HttpStatus.OK);
				}else if(hitAndMissed.get(0) == 2 && hitAndMissed.get(1) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("2S");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"2S",gdto),HttpStatus.OK);
				}else if(hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("1S");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"1S",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 1) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("1S1B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"1S1B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 2) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("1S2B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"1S2B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(1) == 1 && hitAndMissed.get(0) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("1B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"1B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(1) == 2 && hitAndMissed.get(0) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("2B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"2B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(1) == 3 && hitAndMissed.get(0) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("3B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"3B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(0) == 0 && hitAndMissed.get(1) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("X");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"X",gdto),HttpStatus.OK);
				}
				
				// 今日、最初のゲームじゃなければ
			} else {
				hidedNumber = gameService.findhidedNumber(dto); // 今日一番最近やったゲームで 隠された数字を持ってくる。
				ArrayList<Integer> hitAndMissed = game.gameStart(dto.getEnter_num(),hidedNumber);
				if (hitAndMissed.get(0) == 3 && hitAndMissed.get(1) == 0) {
					pdto.setMember_number(dto.getMember_number());
					pdto.setPoint(point1000);
					gameService.updatePoint(pdto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("SSS");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);

					return new ResponseEntity<>(new CMRespDto<>(1,"挑戦に成功しました。 今日はこれ以上、ゲームを進めることができません。",gdto),HttpStatus.OK);
				} else if (hitAndMissed.get(0) == 2 && hitAndMissed.get(1)==0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("2S");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"2S",gdto),HttpStatus.OK);
				}else if(hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("1S");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"1S",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 1) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("1S1B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"1S1B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 2) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("1S2B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"1S2B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(1) == 1 && hitAndMissed.get(0) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("1B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"1B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(1) == 2 && hitAndMissed.get(0) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("2B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"2B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(1) == 3 && hitAndMissed.get(0) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("3B");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"3B",gdto),HttpStatus.OK);
				}else if (hitAndMissed.get(0) == 0 && hitAndMissed.get(1) == 0) {
					pdto.setMember_number(dto.getMember_number());
					gameService.updatePointInfoDate(dto);

					gdto.setMember_number(dto.getMember_number());
					gdto.setInput_number(dto.getEnter_num());
					gdto.setCheck_result("X");
					gdto.setHided_number(hitAndMissed.get(2));
					gdto.setInput_count(checkTodayGameCount + 1);
					gameService.insertGamest(gdto);
					return new ResponseEntity<>(new CMRespDto<>(1,"X",gdto),HttpStatus.OK);
				}
			}
		} else if (5 <= checkTodayGameCount && checkTodayGameCount <= 6) {
			hidedNumber = gameService.findhidedNumber(dto); 
			ArrayList<Integer> hitAndMissed = game.gameStart(dto.getEnter_num(),hidedNumber);
			if (hitAndMissed.get(0) == 3 && hitAndMissed.get(1) == 0) {
				pdto.setMember_number(dto.getMember_number());
				pdto.setPoint(point500);
				gameService.updatePoint(pdto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("SSS");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"挑戦に成功しました。 今日はこれ以上、ゲームを進めることができません。",gdto),HttpStatus.OK);
			} else if (hitAndMissed.get(0) == 2 && hitAndMissed.get(1) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("2S");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"2S",gdto),HttpStatus.OK);
			}else if(hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("1S");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"1S",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 1) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("1S1B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"1S1B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 2) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("1S2B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"1S2B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(1) == 1 && hitAndMissed.get(0) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("1B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"1B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(1) == 2 && hitAndMissed.get(0) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("2B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"2B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(1) == 3 && hitAndMissed.get(0) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("3B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"3B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(0) == 0 && hitAndMissed.get(1) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("X");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"X",gdto),HttpStatus.OK);
			}
		} else if (7 <= checkTodayGameCount && checkTodayGameCount <= 9) {
			hidedNumber = gameService.findhidedNumber(dto); 
			ArrayList<Integer> hitAndMissed = game.gameStart(dto.getEnter_num(),hidedNumber);
			if (hitAndMissed.get(0) == 3 && hitAndMissed.get(1) == 0) {
				pdto.setMember_number(dto.getMember_number());
				pdto.setPoint(point200);
				gameService.updatePoint(pdto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("SSS");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"挑戦に成功しました。 今日はこれ以上、ゲームを進めることができません。",gdto),HttpStatus.OK);
			} else if (hitAndMissed.get(0) == 2 && hitAndMissed.get(1) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("2S");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"2S",gdto),HttpStatus.OK);
			}else if(hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("1S");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"1S",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 1) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("1S1B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"1S1B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(0) == 1 && hitAndMissed.get(1) == 2) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("1S2B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"1S2B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(1) == 1 && hitAndMissed.get(0) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("1B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"1B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(1) == 2 && hitAndMissed.get(0) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("2B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"2B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(1) == 3 && hitAndMissed.get(0) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("3B");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"3B",gdto),HttpStatus.OK);
			}else if (hitAndMissed.get(0) == 0 && hitAndMissed.get(1) == 0) {
				pdto.setMember_number(dto.getMember_number());
				gameService.updatePointInfoDate(dto);

				gdto.setMember_number(dto.getMember_number());
				gdto.setInput_number(dto.getEnter_num());
				gdto.setCheck_result("X");
				gdto.setHided_number(hitAndMissed.get(2));
				gdto.setInput_count(checkTodayGameCount + 1);
				gameService.insertGamest(gdto);
				return new ResponseEntity<>(new CMRespDto<>(1,"X",gdto),HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(new CMRespDto<>(-1,"今日の10回の挑戦に失敗しました。 明日また挑戦してください。",null),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new CMRespDto<>(-1,"no",null),HttpStatus.BAD_REQUEST);
	}

}
