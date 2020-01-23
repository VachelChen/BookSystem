package com.chenjiahui.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenjiahui.dao.BookDao;
import com.chenjiahui.entity.Book;
import com.chenjiahui.entity.BookType;
import com.chenjiahui.service.BookService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/book")
public class Admin_Book_Controler {

	@Resource
	private BookDao bookDao;
	@Resource
	private BookService bookService;

	/**
	 * /admin/book/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Book book, BindingResult bindingResult, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		if (bindingResult.hasErrors()) {
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		} else {
			book.setCreateDateTime(new Date());
			bookDao.save(book);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}
	}
	

	/**
	 *  /admin/book/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(@Valid  Book book  ,BindingResult bindingResult, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			book.setUpdateDateTime(new Date());
			bookService.update(book);
			result.put("success", true);
			result.put("msg", "修改成功");
			return result;
		}
	}
	

	/**
	 * /admin/book/list
	 * @param page    默认1
	 * @param limit   数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit, 
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Book> list = bookService.list(map, page-1, limit);
		long total = bookService.getTotal(map);
		
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	/**
	 * /admin/book/find
	 * @param page    默认1
	 * @param limit   数据多少
	 */
	@ResponseBody
	@RequestMapping("/find")
	public Map<String, Object> find(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit, 
			@RequestParam(name = "name", required = false) String where, 
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Book> list = bookService.find(map, page-1, limit, where);
		long total = bookService.getTotal(map);
		
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	/**
	 * /admin/book/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();

		for (int i = 0; i < idsStr.length; i++) {
			bookDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		return result;
	}
	
	
	
	
}
