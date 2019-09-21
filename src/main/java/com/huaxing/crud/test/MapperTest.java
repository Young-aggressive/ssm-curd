package com.huaxing.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huaxing.crud.bean.Department;
import com.huaxing.crud.bean.Employee;
import com.huaxing.crud.dao.DepartmentMapper;
import com.huaxing.crud.dao.EmployeeMapper;

/**
 * 测试dao层的工作
 * 
 * @author lfy 推荐Spring的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件 1、导入SpringTest模块，
 *         在pom.xml中导入spring-test，有了测试的提示 2、@ContextConfiguration指定Spring配置文件的位置
 *         3、直接autowired要使用的组件即可
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
public class MapperTest {

	@Autowired
	DepartmentMapper departmentMapper;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	SqlSession sqlSession; // 这里是一个批量的sqlSession,通过application.xml文件定义。BATCH
	/*
	 * SqlSession SqlSession = SqlSessionFactory.openSession();
	 * 这里的这句话在applicationcontext.xml中定义了
	 */
	
	/**
	 * 测试DepartmentMapper
	 */
	@Test
	public void testCRUD() {
		/*
		 * //1、创建SpringIOC容器 ApplicationContext ioc = new
		 * ClassPathXmlApplicationContext("applicationContext.xml"); //2、从容器中获取mapper
		 * DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
		 */
		System.out.println(departmentMapper);

		// 1、插入几个部门
		/*
		 * // 构造函数的方式
		 */
		departmentMapper.insertSelective(new Department(null, "销售部"));
		departmentMapper.insertSelective(new Department(1, "测试部"));
		/*
		 * get,set的方法
		 * 
		 * Department department = new Department(); // ctrl+2,L
		 * department.setDeptId(2); department.setDeptName("运维部");
		 * departmentMapper.insertSelective(department);
		 */
		// 2、生成员工数据，测试员工插入
//		employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@huaxing.com", 2));

		// 3、批量插入多个员工；批量，使用可以执行批量操作的sqlSession。

//		for(){
//			employeeMapper.insertSelective(new Employee(null, , "M", "Jerry@atguigu.com", 1));
//		}

		/*
		 * 接口中的insertSelective并没有实现 
		   接口绑定xml文件后，mybatis为接口自动创建了一个代理对象，代理对象执行增删改查
		 */		
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		
		for(int i = 0;i<1000;i++){ 
			String uid = UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Employee(null,uid, "M", uid+"@atguigu.com", 1)); 
		}
		
		System.out.println("批量完成");
		
		
	}

}