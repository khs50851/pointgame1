package com.ks.pointgame.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointInfo {
	private int point_id;
	private String member_number;
	private int point;
	private Timestamp rec_create_date;
	private Timestamp rec_update_date;
}
