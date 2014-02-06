package com.reservation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.reservation.dao.impl.FoodsDaoImpl;
import com.reservation.pojo.Foods;

public class CaiXiServlet extends HttpServlet {

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int olid = 1;
		int tlid = -1;
		int currPage =1;
		int pageSize=1;
		String sOlid = request.getParameter("olid");
		String tlids = request.getParameter("tlid");
		String currPages = request.getParameter("currPage");
		String pageSizes = request.getParameter("pageSize");
		System.out.println(pageSizes);
		System.out.println(sOlid);
		if(sOlid!=null){
			olid = Integer.parseInt(sOlid);
		}
		if(tlids!=null){
			tlid = Integer.parseInt(tlids);
		}
		if(currPages!=null){
			currPage = Integer.parseInt(currPages);
		}
		if(pageSizes!=null){
			pageSize = Integer.parseInt(pageSizes);
		}
		FoodsDaoImpl impl = new FoodsDaoImpl();
		List<Foods> foods = impl.getSomeFoodsByType(olid, tlid, currPage, pageSize);
		JSONArray ja = JSONArray.fromObject(foods);
		System.out.println("safhs"+ja);
		out.print(ja);
		System.out.println(ja);
		out.close();
		//WSei
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
