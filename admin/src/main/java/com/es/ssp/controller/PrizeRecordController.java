
package com.es.ssp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import common.base.BaseController;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;

import static common.util.GlobalMessages.*;
import static common.util.SpringMVCUtils.toModelMap;
import static common.util.MybatisPageQueryUtils.pageQuery;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static cn.org.rapid_framework.beanutils.BeanUtils.copyProperties;

import cn.org.rapid_framework.util.*;

import com.es.ssp.model.*;
import com.es.ssp.query.*;
import com.es.ssp.service.*;


/**
 * 标准的rest方法列表
 * <pre>
 * /prizerecord                => index()  
 * /prizerecord/new            => _new()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /prizerecord/{id}           => show()  
 * /prizerecord/{id}/edit      => edit()  
 * /prizerecord        POST    => create()  
 * /prizerecord/{id}   PUT     => update()  
 * /prizerecord/{id}   DELETE  => delete()  
 * /prizerecord        DELETE  => batchDelete()  
 * </pre>
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/prizerecord")
public class PrizeRecordController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 

	@Autowired
	private PrizeRecordManager prizeRecordManager;
	
	private final String LIST_ACTION = "redirect:/prizerecord";
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}

    @ModelAttribute
    public void init(ModelMap model,Long recordId) {
		if(!ObjectUtils.isNullOrEmptyString(recordId)){
		    model.addAttribute("prizeRecord",prizeRecordManager.getById(recordId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	public String index(ModelMap model,PrizeRecordQuery query,HttpServletRequest request) {
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = prizeRecordManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "/prizerecord/index";
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable Long id) throws Exception {
		PrizeRecord prizeRecord = (PrizeRecord)prizeRecordManager.getById(id);
		model.addAttribute("prizeRecord",prizeRecord);
		return "/prizerecord/show";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	public String _new(ModelMap model,PrizeRecord prizeRecord) throws Exception {
		model.addAttribute("prizeRecord",prizeRecord);
		return "/prizerecord/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method=RequestMethod.POST)
	public String create(ModelMap model,PrizeRecord prizeRecord,BindingResult errors) throws Exception {
		try {
			prizeRecordManager.save(prizeRecord);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "/prizerecord/new";
		}catch(ValidationException e) {
			return  "/prizerecord/new";
		}
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable Long id) throws Exception {
		PrizeRecord prizeRecord = (PrizeRecord)prizeRecordManager.getById(id);
		model.addAttribute("prizeRecord",prizeRecord);
		return "/prizerecord/edit";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(ModelMap model,@PathVariable Long id,PrizeRecord prizeRecord,BindingResult errors) throws Exception {
		try {
			prizeRecordManager.update(prizeRecord);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "/prizerecord/edit";
		}catch(ValidationException e) {
			return  "/prizerecord/edit";
		}
		return LIST_ACTION;
	}
	
	/** 删除 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(ModelMap model,@PathVariable Long id) {
		prizeRecordManager.removeById(id);
		return LIST_ACTION;
	}

	/** 批量删除 */
	@RequestMapping(method=RequestMethod.DELETE)
	public String batchDelete(ModelMap model,@RequestParam("items") Long[] items) {
		for(int i = 0; i < items.length; i++) {
			prizeRecordManager.removeById(items[i]);
		}
		return LIST_ACTION;
	}
	
}

