package com.chenjiahui.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chenjiahui.entity.Config;


public interface ConfigDao extends JpaRepository< Config,Integer> {
	
	
	@Query(value="select * from t_config  where id = ?1",nativeQuery = true)
	public  Config findId(Integer id);
	
}
