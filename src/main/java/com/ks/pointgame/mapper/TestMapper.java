package com.ks.pointgame.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ks.pointgame.dto.MemberDto;

@Repository
public interface TestMapper {
	  public List<MemberDto> getAll();
}
