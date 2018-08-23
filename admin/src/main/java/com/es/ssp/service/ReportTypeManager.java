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
public class ReportTypeManager {

	@Autowired
	private ReportTypeDao reportTypeDao;

	/** 
	 * 创建ReportType
	 **/
	public ReportType save(ReportType reportType) {
	    this.reportTypeDao.save(reportType);
	    return reportType;
	}
	
	/** 
	 * 更新ReportType
	 **/	
    public ReportType update(ReportType reportType) {
        this.reportTypeDao.update(reportType);
        return reportType;
    }	
    
	/** 
	 * 删除ReportType
	 **/
    public void removeById(Integer id) {
        this.reportTypeDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到ReportType
	 **/    
    public ReportType getById(Integer id) {
        return this.reportTypeDao.getById(id);
    }
    
	/** 
	 * 分页查询: ReportType
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(ReportTypeQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return reportTypeDao.findPage(query);
	}
	
    
}
