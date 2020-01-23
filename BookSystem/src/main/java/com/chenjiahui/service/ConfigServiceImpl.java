package com.chenjiahui.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chenjiahui.dao.ConfigDao;
import com.chenjiahui.entity.Config;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	
	@Resource
	private ConfigDao configDao;
	
	@Override
	public Config findById(Integer id) {
		return configDao.findId(id);
	}

}
