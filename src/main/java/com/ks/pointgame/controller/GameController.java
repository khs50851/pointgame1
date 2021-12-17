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
import com.ks.pointgame.domain.PointInfo;
import com.ks.pointgame.dto.CMRespDto;
import com.ks.pointgame.dto.GameStDtoResp;
import com.ks.pointgame.dto.GamestDto;
import com.ks.pointgame.dto.InputNumDto;
import com.ks.pointgame.dto.PointDto;
import com.ks.pointgame.game.Game;
import com.ks.pointgame.handler.ex.CustomApiException;
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
		int po = gameService.findPointByNumber(mem);
		
		
		List<GameStDtoResp> list = gameService.gameStList(mem);
		model.addAttribute("list", list);
		model.addAttribute("point", po);
		return "gamestatus/game";
	}

	@PostMapping("/game")
	public @ResponseBody ResponseEntity<?> inputNum(InputNumDto dto) {
		
		// 오늘 게임에서 이긴 내용이 있는지 확인
		int checkTodaysResult = gameService.findCheckResult(dto);
		if (checkTodaysResult == 1) {
			return new ResponseEntity<>(new CMRespDto<>(1,"오늘은 이미 당첨 되셨습니다. 게임을 진행 할 수 없습니다",null),HttpStatus.BAD_REQUEST);
		}
		
		Game game = new Game();
		int point1000 = 1000;
		int point500 = 500;
		int point200 = 200;
		int checkTodayGameCount = gameService.checkTodayGameCount(dto);
		System.out.println("오늘 게임 횟수 : "+checkTodayGameCount);
		int hidedNumber = 0;
		PointDto pdto = new PointDto();
		GamestDto gdto = new GamestDto();
		// 0번에 hit 횟수 1번 인덱스에 missed 저장되어 있음 3번째엔 숨은 숫자
		
		
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
					
					return new ResponseEntity<>(new CMRespDto<>(1,"도전에 성공하셨습니다. 오늘은 더 이상 게임 진행이 불가합니다.",gdto),HttpStatus.OK);
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
				
				// 오늘 첫게임이 아님
			} else {
				hidedNumber = gameService.findhidedNumber(dto); // 오늘 제일 최근에 했던 게임에서 숨겨진 숫자를 가져옴
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

					return new ResponseEntity<>(new CMRespDto<>(1,"도전에 성공하셨습니다. 오늘은 더 이상 게임 진행이 불가합니다.",gdto),HttpStatus.OK);
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
			hidedNumber = gameService.findhidedNumber(dto); // 오늘 제일 최근에 했던 게임에서 숨겨진 숫자를 가져옴
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
				return new ResponseEntity<>(new CMRespDto<>(1,"도전에 성공하셨습니다. 오늘은 더 이상 게임 진행이 불가합니다.",gdto),HttpStatus.OK);
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
			hidedNumber = gameService.findhidedNumber(dto); // 오늘 제일 최근에 했던 게임에서 숨겨진 숫자를 가져옴
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
				return new ResponseEntity<>(new CMRespDto<>(1,"도전에 성공하셨습니다. 오늘은 더 이상 게임 진행이 불가합니다.",gdto),HttpStatus.OK);
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
			return new ResponseEntity<>(new CMRespDto<>(-1,"오늘 10번의 도전에 실패하셨습니다. 내일 다시 도전해주세요.",null),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new CMRespDto<>(-1,"no",null),HttpStatus.BAD_REQUEST);
	}

}
