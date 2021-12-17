package com.ks.pointgame.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ks.pointgame.dto.MemberDto;
import com.ks.pointgame.mapper.TestMapper;

@Service
public class TestService {
	@Autowired
    TestMapper testMapper;
	public List<MemberDto> getAll() throws Exception{
        return testMapper.getAll();
    }
}
