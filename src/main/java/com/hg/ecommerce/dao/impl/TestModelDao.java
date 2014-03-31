package com.hg.ecommerce.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hg.ecommerce.model.test.TestModel;

@Repository("testModelDao")
public class TestModelDao extends BaseDaoImpl<TestModel>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
}
