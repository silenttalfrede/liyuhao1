package com.liyuhao.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.liyuhao.entity.Admin;
import com.liyuhao.service.AdminService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.liyuhao.utils.JsonResult;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AdminAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AdminAction.class);
	@Autowired
	private AdminService adminService;

	// 返回给浏览器的json封装类
	private JsonResult<Object> result;

	public JsonResult<Object> getResult() {
		return result;
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	public String login() {
		// 用户名
		String name = ServletActionContext.getRequest().getParameter("name");
		// 用户密码
		String password = ServletActionContext.getRequest().getParameter("password");

		Admin admin = adminService.login(name, password);
		log.info(admin);
		// 如果没有取到数据，则返回继续登录页面，取到数据则返回成功页面并且直接显示
		if (admin == null) {
			return "login";
		} else {
			ActionContext.getContext().put("info", admin.toString());
			return "success";
		}
	}

	/**
	 * 拆分程序的用户登录
	 * 
	 * @return
	 */
	public String login1() {
		// 用户名
		String name = ServletActionContext.getRequest().getParameter("name");
		// 用户密码
		String password = ServletActionContext.getRequest().getParameter("password");

		System.out.println("liyuhao1:" + name);
		System.out.println("liyuhao1:" + password);

		Admin admin = adminService.login(name, password);

		try {
			// 用户登录成功 返回json数据给浏览器进行跳转
			result = new JsonResult<Object>(admin);
			System.out.println("liyuhao1:" + result.toString());
		} catch (Exception e) {
			// 其他异常 不具体提示错误 浏览器会给用户一个友好的提示
			result = new JsonResult<Object>(e);
			e.printStackTrace();
		}
//		result = new JsonResult<Object>("123456789");
		return "success";
	}

	/**
	 * 拆分程序的用户登录
	 * 
	 * @return
	 * @throws IOException 
	 */
	public void login2() throws IOException {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/data; charset=utf-8");
		PrintWriter out = response.getWriter();

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");// 用户名
		String password = request.getParameter("password");// 用户密码

		System.out.println("liyuhao1:" + name);
		System.out.println("liyuhao1:" + password);

		Admin admin = adminService.login(name, password);
		System.out.println("liyuhao1:" + admin);

		out.write(admin.toString());
		out.flush();
		out.close();
	}

}
