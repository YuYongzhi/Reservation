package com.reservation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.reservation.dao.impl.OneLevelTypeDaoImpl;
import com.reservation.dao.impl.TwoLevelTypeDaoImpl;
import com.reservation.pojo.OneLevelType;
import com.reservation.pojo.TwoLevelType;

public class LevelServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		List<OneLevelType> list =OneLevelTypeDaoImpl.getAllTypes();
		PrintWriter out = response.getWriter();
		JSONArray ja = JSONArray.fromObject(list);
		out.print(ja);
		out.close();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}
}
