package com.huaxing.crud.test;


import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.huaxing.crud.bean.Employee;

/*
 * 使用Spring测试模块提供的测试请求功能，测试curd请求的正确性
 * Spring4测试的时候，需要servlet3.0的支持
*/
@RunWith(SpringJUnit4ClassRunner.class)
//获取WebIOC容器
@WebAppConfiguration
/*
 * @WebAppConfiguration must be used in conjunction（连接）
 * with @ContextConfiguration. In the below（之内） test class, we load
 * WebApplicationContext and MockServletContext using the @WebAppConfiguration
 * annotation and inject their instances（实例） using @Autowired. In test
 * testGetRequest(), we run a request which in turn calls the controller method
 * and then print the response.
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml" })
public class MvcTest {
	// 传入Springmvc的ioc
	@Autowired
	WebApplicationContext context;
		// 虚拟mvc请求，获取到处理结果。
	MockMvc mockMvc;
	@Before    //每次junit之前都初始化一个虚拟mvc
	public void initMokcMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void testPage() throws Exception {
		//这个类模拟对页面的请求，请求拿到返回值
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5"))
				.andReturn();
		
		//请求成功以后，请求域中会有pageInfo；我们可以取出pageInfo进行验证
		//这里和EmployeeController.java有关
		MockHttpServletRequest request = result.getRequest();
		PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("当前页码："+pi.getPageNum());
		System.out.println("总页码："+pi.getPages());
		System.out.println("总记录数："+pi.getTotal());
		System.out.println("在页面需要连续显示的页码");
		int[] nums = pi.getNavigatepageNums();
		for (int i : nums) {
			System.out.print(" "+i);
		}
		
		//获取员工数据
		List<Employee> list = pi.getList();
		for (Employee employee : list) {
			System.out.println("ID："+employee.getEmpId()+"==>Name:"+employee.getEmpName());
		}
		
	}

}
