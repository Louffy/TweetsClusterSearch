package com.zfx;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;

/**
 * Servlet implementation class WeiboSearch
 */
public class WeiboSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//public Mylucene mylucene = new Mylucene();
	/**
     * @see HttpServlet#HttpServlet()
     */
    public WeiboSearch() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() throws ServletException {
    	//Mylucene mylucene = new Mylucene();
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HashMap<String,String> wResult = Mylucene.WSearch(request.getParameter("name"));
		/*<String,String> wResult = new HashMap<String,String>();
		wResult.put("1 ", "hello");
		wResult.put("2 ", "world");
		wResult.put("3 ", s);*/
		Mylucene mylucene = new Mylucene();
		//HashMap<String,String> wResult = mylucene.WSearch(request.getParameter("name"));
		TreeMap<String,String> wResult = mylucene.WSearch(request.getParameter("name"));
		request.setCharacterEncoding("gb2312");
		response.setContentType("text/heml;charset=gb2312");
		PrintWriter out = response.getWriter();
		if(wResult != null){
			Set<String> keys = wResult.keySet();
			out.println("<br />检索词: " + request.getParameter("name") +"    共找到   " + keys.size() + " 条结果 <br />");
			for(Iterator<String> it = keys.iterator(); it.hasNext();){
				String key = it.next();
				out.println("<br /><font color='green'>User: </font>" + "<font color='green'>" + key + "</font>" + 
				"<br /> <font color='blue'>Content: </font> " + "<font color='blue'>" + wResult.get(key) + "</font>" + "<br />");
				}
		}
		else out.println("<br /> <font color='red'> 未找到结果 </font>");
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Mylucene lucene = new Mylucene();
	}

}

