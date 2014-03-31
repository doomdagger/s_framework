package com.hg.ecommerce.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hg.ecommerce.model.TestModel;


@Repository("testModelDao")
public class TestModelDao extends BaseDaoImpl<TestModel>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	protected void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
