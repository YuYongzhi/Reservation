package com.reservation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.reservation.dao.impl.TwoLevelTypeDaoImpl;
import com.reservation.pojo.TwoLevelType;

public class TwoLevelServlet extends HttpServlet {

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		List<TwoLevelType> tlist = TwoLevelTypeDaoImpl.getAllType();
		JSONArray ja1 = JSONArray.fromObject(tlist);
		out.print(ja1);
		out.flush();
		out.close();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}
}
