package com.ks.pointgame.dto;

import lombok.Data;

@Data
public class GameStDtoResp {
	private String member_number;
	private int input_count;
	private int input_number;
	private String check_result;
	private int hided_number;
	private int point;
}
