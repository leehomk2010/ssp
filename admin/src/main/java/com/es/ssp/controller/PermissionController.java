
package com.es.ssp.controller;

import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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

import java.io.*;
import java.net.*;
import java.util.*;

import java.math.*;

import common.base.*;

import static common.util.DateFormats.*;
import static common.util.GlobalMessages.*;
import static common.util.SpringMVCUtils.toModelMap;
import static common.util.MybatisPageQueryUtils.pageQuery;

import static cn.org.rapid_framework.util.ValidationErrorsUtils.convert;
import static cn.org.rapid_framework.beanutils.BeanUtils.copyProperties;
import static cn.org.rapid_framework.util.holder.BeanValidatorHolder.validateWithException;
import cn.org.rapid_framework.util.DateConvertUtils;

import cn.org.rapid_framework.page.PageRequest;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.*;

import com.es.ssp.model.*;
import com.es.ssp.dao.*;
import com.es.ssp.service.*;
import com.es.ssp.query.*;


/**
 * 标准的rest方法列表
 * <pre>
 * /permission                => index()  
 * /permission/new            => _new()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /permission/{id}           => show()  
 * /permission/{id}/edit      => edit()  
 * /permission        POST    => create()  
 * /permission/{id}   PUT     => update()  
 * /permission/{id}   DELETE  => delete()  
 * /permission        DELETE  => batchDelete()  
 * </pre>
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 

	@Autowired
	private PermissionManager permissionManager;
	
	private final String LIST_ACTION = "redirect:/permission";
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}

    @ModelAttribute
    public void init(ModelMap model,Integer permissionId) {
		if(!ObjectUtils.isNullOrEmptyString(permissionId)){
		    model.addAttribute("permission",permissionManager.getById(permissionId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	public String index(ModelMap model,PermissionQuery query,HttpServletRequest request) {
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = this.permissionManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "/permission/index";
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable Integer id) throws Exception {
		Permission permission = (Permission)permissionManager.getById(id);
		model.addAttribute("permission",permission);
		return "/permission/show";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	public String _new(ModelMap model,Permission permission) throws Exception {
		model.addAttribute("permission",permission);
		return "/permission/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method=RequestMethod.POST)
	public String create(ModelMap model,Permission permission,BindingResult errors) throws Exception {
		try {
			permissionManager.save(permission);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "/permission/new";
		}catch(ValidationException e) {
			Flash.current().error(e.getMessage());
			return  "/permission/new";
		}
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable Integer id) throws Exception {
		Permission permission = (Permission)permissionManager.getById(id);
		model.addAttribute("permission",permission);
		return "/permission/edit";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(ModelMap model,@PathVariable Integer id,Permission permission,BindingResult errors) throws Exception {
		try {
			permissionManager.update(permission);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "/permission/edit";
		}catch(ValidationException e) {
			Flash.current().error(e.getMessage());
			return  "/permission/edit";
		}
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/** 删除 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(ModelMap model,@PathVariable Integer id) {
		permissionManager.removeById(id);
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}

	/** 批量删除 */
	@RequestMapping(method=RequestMethod.DELETE)
	public String batchDelete(ModelMap model,@RequestParam("items") Integer[] items) {
		for(int i = 0; i < items.length; i++) {
			permissionManager.removeById(items[i]);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
}

