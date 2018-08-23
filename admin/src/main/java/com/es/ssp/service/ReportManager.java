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
public class ReportManager {

	@Autowired
	private ReportDao reportDao;

	/** 
	 * 创建Report
	 **/
	public Report save(Report report) {
	    this.reportDao.save(report);
	    return report;
	}
	
	/** 
	 * 更新Report
	 **/	
    public Report update(Report report) {
        this.reportDao.update(report);
        return report;
    }	
    
	/** 
	 * 删除Report
	 **/
    public void removeById(Long id) {
        this.reportDao.deleteById(id);
    }
    
	/** 
	 * 根据ID得到Report
	 **/    
    public Report getById(Long id) {
        return this.reportDao.getById(id);
    }
    
	/** 
	 * 分页查询: Report
	 **/      
	@Transactional(readOnly=true)
	public Page findPage(ReportQuery query) {
	    Assert.notNull(query,"'query' must be not null");
		return reportDao.findPage(query);
	}
	
    
}
