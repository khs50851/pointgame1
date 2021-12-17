package com.ks.pointgame.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {
	String member_number;
	String member_password;
	String member_name;
	Timestamp rec_create_date;
	Timestamp rec_update_date;
	String logical_del_flg;
}
