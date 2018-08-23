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
public class OperateRecordManager {

	@Autowired
	private OperateRecordDao operateRecordDao;

	/** 
	 * 创建OperateRecord
	 **/
	public OperateRecord save(OperateRecord operateRecord) {
	    this.operateRecordDao.save(operateRecord);
	    return operateRecord;
	}
	
	/** 
	 * 更新OperateRecord
	 **/	
    public OperateRecord update(OperateRecord operateRecord) {
        this.operateRecordDao.update(operateRecord);
        return operateRecord;
    }	
    
	/** 
	 * 删除OperateRecord
	 **/
    public void removeById(Long id) {
        this.operateRecordDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到OperateRecord
	 **/    
    public OperateRecord getById(Long id) {
        return this.operateRecordDao.getById(id);
    }
    
	/** 
	 * 分页查询: OperateRecord
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(OperateRecordQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return operateRecordDao.findPage(query);
	}
	
    
}
