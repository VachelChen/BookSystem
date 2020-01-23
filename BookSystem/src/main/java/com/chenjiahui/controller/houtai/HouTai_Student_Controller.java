package com.chenjiahui.controller.houtai;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chenjiahui.dao.BookDao;
import com.chenjiahui.dao.BookTypeDao;
import com.chenjiahui.entity.Book;
import com.chenjiahui.entity.BookType;
import com.chenjiahui.service.BookService;

@Controller
@RequestMapping("/houtai/student")
public class HouTai_Student_Controller {
	

	@Resource
	private BookDao bookDao;
	@Resource
	private BookService bookService;
	@Resource
	private BookTypeDao bookTypeDao;
	
	
	/**
	 * /houtai/student/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "学生查询");
		mav.setViewName("/admin/page/student/student_manage");
		return mav;
	}
	
	
}
