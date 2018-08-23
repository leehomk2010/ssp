package com.es.ssp.dao;

import common.base.*;

import static common.util.MybatisPageQueryUtils.pageQuery;

import cn.org.rapid_framework.page.Page;

import com.es.ssp.model.*;
import com.es.ssp.query.*;


import org.springframework.stereotype.Repository;

/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
@Repository
public class ReportDao extends BaseMybatisDao<Report,Long>{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "Report";
	}
	
	public void saveOrUpdate(Report entity) {
		if(entity.getReportId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ReportQuery query) {
		return pageQuery(getSqlSession(),"Report.findPage",query);
	}
	

}
