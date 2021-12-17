package com.ks.pointgame.dto;

import java.sql.Timestamp;

import com.ks.pointgame.domain.PointInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDto {
	private String member_number;
	private int point;
}
