package com.ks.pointgame.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.pointgame.domain.MemberInfo;
import com.ks.pointgame.dto.CMRespDto;
import com.ks.pointgame.dto.MemberDto;
import com.ks.pointgame.service.LoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {
	
	private final LoginService loginService;
	
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
	
	@PostMapping("/login")
	public @ResponseBody ResponseEntity<?> login(MemberDto dto,HttpServletRequest req) {
		MemberInfo memberEntity = loginService.findByNumber(dto);
		HttpSession session = req.getSession();
		if(memberEntity==null) {
			return new ResponseEntity<>(new CMRespDto<>(-1,"ログイン失敗:会員IDまたはパスワードをもう一度確認してください。",null),HttpStatus.BAD_REQUEST);
		}else {
			session.setAttribute("member", memberEntity);
			loginService.updateRecDate(dto);
			return new ResponseEntity<>(new CMRespDto<>(1,"ログイン成功",memberEntity),HttpStatus.OK);
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
