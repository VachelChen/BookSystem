package com.chenjiahui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.chenjiahui.dao.MenuDao;
import com.chenjiahui.dao.RoleMenuDao;
import com.chenjiahui.entity.Menu;
import com.chenjiahui.entity.RoleMenu;
import com.chenjiahui.entity.User;
import com.chenjiahui.service.MenuService;
import com.chenjiahui.util.BrowserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class IndexController {
	
	@Autowired 
	private ServletContext servletContext;
	@Resource
	private MenuDao menuDao;
	@Resource
	private MenuService menuService;
	@Resource
	private  RoleMenuDao   roleMenuDao;
	
	
	/**
	 *# 请求首页  
	 */
	@RequestMapping("/")
	public String  index_1(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		return "redirect:/login";
	}
	
	/**
	 *   #请求首页  /index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		return "redirect:/login";
	}
	
	
	/**
	 *    /login
	 *    #后台 用户电脑登陆
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		// ModelAndView是spring mvc的方法，通过.setViewName方法返回界面
		ModelAndView mav = new ModelAndView();
		String UserAgent = req.getHeader("User-Agent");
		// 判断AppleWebKit 和  Firefox    
		if(BrowserUtil.checkUserAgent(UserAgent)){
			mav.setViewName("/pc/login/login");
		}else{
			mav.setViewName("/common/s_mode");
		}
		return mav;
	}
	
	
	
	/**
	 * # 后台主页
	 */
	@RequestMapping("/admin/main")
	public ModelAndView admin_main(HttpServletResponse  res,HttpServletRequest req,HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		String UserAgent = req.getHeader("User-Agent");
		//判断AppleWebKit 和  Firefox    
		if(BrowserUtil.checkUserAgent(UserAgent)){
			mav.setViewName("/admin/main");
		}else{
			mav.setViewName("/common/s_mode");
		}
		//session.getAttribute("currentUser"); 
		//shiro方法，获取当前用户
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser"); 
		if(currentUser.getRole()==null){
			return mav;
		}
		
		//根据当前的用户   对应的角色。 展示菜单 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", -1);
		List<Menu> menuList = menuService.list(map , 0, 100);
		List<JSONObject>  list =  new ArrayList<JSONObject>();
		for (Menu menu : menuList) {
			//当前下当前用户的角色  有没有这个菜单 
			RoleMenu roleMenu=	roleMenuDao.findByRoleIdAndMenuId(currentUser.getRole().getId(), menu.getId());
			if(roleMenu!=null) {
				JSONObject node = new JSONObject();
				node.put("id", menu.getId());
				node.put("text", menu.getName());
				node.put("url", menu.getUrl());
				node.put("type", menu.getType());
				node.put("icon", menu.getIcon());
				node.put("divId", menu.getDivId());
				node.put("children", getChildren(menu.getId(),currentUser.getRole().getId()));
				list.add(node);
			}
		}
		//根据当前的用户   对应的角色。 展示菜单 
		
		mav.addObject("treeList", list);
		
		
		return mav;
	}
	
	
	/**
	 * 辅助方法  辅助 上面的 admin_main（getCheckedTreeMenu）
	 * @param pId
	 * @param roleId
	 * @return
	 */
	public List<JSONObject> getChildren(Integer pId, Integer roleId)  throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", pId);
		List<Menu> menuList = menuService.list(map , 0, 100);
		
		List<JSONObject>  list =  new ArrayList<JSONObject>();
		for (Menu menu : menuList) {
			RoleMenu roleMenu=	roleMenuDao.findByRoleIdAndMenuId(roleId, menu.getId());
			if(roleMenu!=null){
				JSONObject node = new JSONObject();
				node.put("id", menu.getId());
				node.put("text", menu.getName());
				node.put("url", menu.getUrl());
				node.put("type", menu.getType());
				node.put("icon", menu.getIcon());
				node.put("divId", menu.getDivId());
				list.add(node);
			}
		}
		return list;
	}
	
	
	
	 
}
