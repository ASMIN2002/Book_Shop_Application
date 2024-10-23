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

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
		try(
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookreg?useSSL=false","root","Asmin@2002");
				PreparedStatement ps = connection.prepareStatement("select * from book;")){
				
				ResultSet rs = ps.executeQuery();
				out.println("<table border='1' align='center'>");
				out.println("<tr>");
				out.println("<th>Book Id</th>");
				out.println("<th>Book Name</th>");
				out.println("<th>Book Edition</th>");
				out.println("<th>Book Author</th>");
				out.println("<th>Book Price</th>");
				out.println("<th>Edit</th>");
				out.println("<th>Delete</th>");
				out.println("</tr>");
				
				
				while(rs.next()) {
					out.println("<tr>");
					out.println("<td>"+rs.getInt(1)+"</td>");
					out.println("<td>"+rs.getString(2)+"</td>");
					out.println("<td>"+rs.getString(3)+"</td>");
					out.println("<td>"+rs.getString(4)+"</td>");
					out.println("<td>"+rs.getFloat(5)+"</td>");				
					out.println("<td><a href='editScreen?id="+rs.getInt(1)+"'>Edit</a></td>");				
					out.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>Delete</a></td>");				
					out.println("</tr>");
				}
				out.println("<table>");
			
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
