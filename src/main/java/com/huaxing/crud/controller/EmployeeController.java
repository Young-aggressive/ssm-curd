package com.huaxing.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huaxing.crud.bean.Employee;
import com.huaxing.crud.bean.Msg;
import com.huaxing.crud.service.EmployeeService;

/**
 * 处理员工增删改查CRUD
 * @author gmcc
 *
 */

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;

	
	@RequestMapping("/emps")
	@ResponseBody       //@ResponseBody作用自动将返回的对象转为json字符串   需要导入jackson包
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn) {
		PageHelper.startPage(pn, 5);//顺序重要
		List<Employee> emps= employeeService.getAll();
		//使用pageInfo包装查询后的结果，只需将pageInfo交给页面就行了。
		//里面封装了详细的分页信息，包括有我们查询出来的数据
		PageInfo page=new PageInfo(emps,5);  //连续显示的页数
		//return page;
		return Msg.success().add("pageInfo", page);
		
	}
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1")Integer pn,
			Model model) {
		//这不是一个分页查询
		//引入PageHelper分页插件
		//在查询之前只需要调用,传入页码pn，以及每页显示的数量
		PageHelper.startPage(pn,5);
		//startPage后面紧跟的这个查询就是一个分页查询
		List<Employee> emps = employeeService.getAll();  //调用动作
		//使用pageinfo包装查询后的结果，只需要将pageinfo交给页面就可以了
		//封装了详细的分页信息，包括有我们查询出来的数据,传入连续显示的页数
		PageInfo page = new PageInfo(emps,5);
		model.addAttribute("pageInfo",page);
		return "list";
		
	}

}
