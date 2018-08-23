package com.es.ssp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import static common.util.SpringMVCUtils.toModelMap;
import static common.util.MybatisPageQueryUtils.pageQuery;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static cn.org.rapid_framework.beanutils.BeanUtils.copyProperties;

import cn.org.rapid_framework.page.Page;

import com.es.ssp.model.*;
import com.es.ssp.dao.*;
import com.es.ssp.query.*;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class GlobalSettingManager {

	@Autowired
	private GlobalSettingDao globalSettingDao;

	/** 
	 * 创建GlobalSetting
	 **/
	public GlobalSetting save(GlobalSetting globalSetting) {
	    this.globalSettingDao.save(globalSetting);
	    return globalSetting;
	}
	
	/** 
	 * 更新GlobalSetting
	 **/	
    public GlobalSetting update(GlobalSetting globalSetting) {
        this.globalSettingDao.update(globalSetting);
        return globalSetting;
    }	
    
	/** 
	 * 删除GlobalSetting
	 **/
    public void removeById(Integer id) {
        this.globalSettingDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到GlobalSetting
	 **/    
    public GlobalSetting getById(Integer id) {
        return this.globalSettingDao.getById(id);
    }
    
	/** 
	 * 分页查询: GlobalSetting
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(GlobalSettingQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return globalSettingDao.findPage(query);
	}
	
    
}
