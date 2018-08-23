
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
 * /loginuser                => index()  
 * /loginuser/new            => _new()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /loginuser/{id}           => show()  
 * /loginuser/{id}/edit      => edit()  
 * /loginuser        POST    => create()  
 * /loginuser/{id}   PUT     => update()  
 * /loginuser/{id}   DELETE  => delete()  
 * /loginuser        DELETE  => batchDelete()  
 * </pre>
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/loginuser")
public class LoginUserController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "createTime desc";

	@Autowired
	private LoginUserManager loginUserManager;
	
	private final String LIST_ACTION = "redirect:/loginuser";
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}

    @ModelAttribute
    public void init(ModelMap model,Integer userId) {
		if(!ObjectUtils.isNullOrEmptyString(userId)){
		    model.addAttribute("loginUser",loginUserManager.getById(userId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	public String index(ModelMap model,LoginUserQuery query,HttpServletRequest request) {
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = loginUserManager.findPage(query);
		model.addAllAttributes(toModelMap(page, query));
		return "/loginuser/list";
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable Integer id) throws Exception {
		LoginUser loginUser = (LoginUser)loginUserManager.getById(id);
		model.addAttribute("loginUser",loginUser);
		return "/loginuser/show";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	public String _new(ModelMap model,LoginUser loginUser) throws Exception {
		model.addAttribute("loginUser",loginUser);
		return "/loginuser/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method=RequestMethod.POST)
	public String create(ModelMap model,LoginUser loginUser,BindingResult errors) throws Exception {
		try {
			loginUserManager.save(loginUser);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "/loginuser/new";
		}catch(ValidationException e) {
			Flash.current().error(e.getMessage());
			return  "/loginuser/new";
		}
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable Integer id) throws Exception {
		LoginUser loginUser = (LoginUser)loginUserManager.getById(id);
		model.addAttribute("loginUser",loginUser);
		return "/loginuser/edit";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(ModelMap model,@PathVariable Integer id,LoginUser loginUser,BindingResult errors) throws Exception {
		try {
			loginUserManager.update(loginUser);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "/loginuser/edit";
		}catch(ValidationException e) {
			return  "/loginuser/edit";
		}
		return LIST_ACTION;
	}
	
	/** 删除 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(ModelMap model,@PathVariable Integer id) {
		loginUserManager.removeById(id);
		return LIST_ACTION;
	}

	/** 批量删除 */
	@RequestMapping(method=RequestMethod.DELETE)
	public String batchDelete(ModelMap model,@RequestParam("items") Integer[] items) {
		for(int i = 0; i < items.length; i++) {
			loginUserManager.removeById(items[i]);
		}
		return LIST_ACTION;
	}
	
}

