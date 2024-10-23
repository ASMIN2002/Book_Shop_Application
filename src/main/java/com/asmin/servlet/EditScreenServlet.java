package com.asmin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		int id = Integer.parseInt(req.getParameter("id"));

			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
		try(
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookreg?useSSL=false","root","Asmin@2002");
				PreparedStatement ps = connection.prepareStatement("select * from book where id=?")){
				
			    ps.setInt(1, id);
			    
			    ResultSet rs = ps.executeQuery();
			    rs.next();
			    
			    out.println("<form action='editurl?id="+id+"' method='post'>");

			    out.println("<table align='center'>");
			    out.println("<tr>");
			    out.println("<td>Book Name</td>");
			    out.println("<td><input type='text' name='bookName' value='"+rs.getString(2)+"'></td>");
			    out.println("</tr>");
			    out.println("<tr>");
			    out.println("<td>Book Edition</td>");
			    out.println("<td><input type='text' name='bookEdition' value='"+rs.getString(3)+"'></td>");
			    out.println("</tr>");
			    out.println("<tr>");
			    out.println("<td>Book Author</td>");
			    out.println("<td><input type='text' name='bookAuthor' value='"+rs.getString(4)+"'></td>");
			    out.println("</tr>");
			    out.println("<tr>");
			    out.println("<td>Book Price</td>");
			    out.println("<td><input type='text' name='bookPrice' value='"+rs.getFloat(5)+"'></td>");
			    out.println("</tr>");
			    out.println("<tr>");
			    out.println("<td><input type='submit' value='edit'></td>");
			    out.println("<td><input type='reset' value='cancel'></td>");
			    out.println("</tr>");
			    
			    
			    out.println("</table>");
			    out.println("</form>");
		}catch(SQLException e) {
			e.printStackTrace();
			out.println("<h1>"+e.getMessage()+"</h1>");
		}catch(Exception e) {
			e.printStackTrace();
			out.println("<h1>"+e.getMessage()+"</h1>");
		}
		out.println("<a href='home.html'>Home</a>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
