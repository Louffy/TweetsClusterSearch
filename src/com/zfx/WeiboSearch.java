package com.zfx;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.lucene.document.Document;

/**
 * Servlet implementation class WeiboSearch
 */
public class WeiboSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private json s;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public WeiboSearch() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() throws ServletException {
    
    	//w = new Webcontent();
    	//s = new Showcontent();
    	s = new json();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//TreeMap<String, String> wResult = Mylucene.WSearch(request.getParameter("name"));
		/*<String,String> wResult = new HashMap<String,String>();
		wResult.put("1 ", "hello");
		wResult.put("2 ", "world");
		wResult.put("3 ", s);*/
		
		//HashMap<String,String> wResult = mylucene.WSearch(request.getParameter("name"));
		/*PrintWriter out = response.getWriter();
		String query = request.getParameter("name");
		
		Date d1 = new Date();
		out.println(d1.toString());
		//String[] wResult = mylucene.WSearch(query);//time is very long,why????????????????
		w.Init(query);
		Date d2 = new Date();
		out.println(d2.getTime() - d1.getTime());
		String[] docs = w.getDocs();
		Cluster[] c = w.getClusters();
		
		request.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=gb2312");
		long t = (long) ((d2.getTime() - d1.getTime())/1000.0);
		s.Init(docs, c, query, t);
		request.setAttribute("Showcontent", s);
		ServletContext app = this.getServletContext();
		RequestDispatcher rd = app.getRequestDispatcher("/show.jsp");
		rd.forward(request, response);
	/*	if(wResult != null){
			///*out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta content=\"text/html; UTF-8\" http-equiv=\"content-type\">");
			out.println("<title>result</title>");
			out.println("</head>");
			//out.println("<body>");
			out.println("<br />Query: " + request.getParameter("name") +"    Find:  " + wResult.length  + "  Time: " + t + "s <br />");
			for(int i = 0; i < wResult.length; i++){
				out.println("<br />" + wResult[i] +  "<br />");
				}
		}
		else out.println("<br /> <font color='red'> Not find 未找到结果 </font>");*/
		//response.sendRedirect("show.jsp");
		// out.println("<br /> <font color='red'> Not find 未找到结果 </font>");
		//out.flush();
		request.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=gb2312");
		String query = request.getParameter("name");
		System.out.println(query);
		s.Init(query);
		JSONArray j = s.getJson();
		PrintWriter out = response.getWriter();  
		System.out.println(j.toString());
	    out.print(j.toString());  
	    out.flush();  
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Mylucene lucene = new Mylucene();
		doGet(request, response);
	}

}

