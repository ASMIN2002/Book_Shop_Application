package com.asmin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterBook extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		Random rand = new Random();
		int id = rand.nextInt(9000) + 1000;
		
		String name = req.getParameter("bookName");
		String addition = req.getParameter("bookAdition");
		String author = req.getParameter("bookAuthor");
		float price = Float.parseFloat(req.getParameter("bookPrice"));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
		try(
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookreg?useSSL=false","root","Asmin@2002");
				PreparedStatement ps = connection.prepareStatement("insert into book values(?,?,?,?,?);")){
				
				ps.setInt(1, id);
				ps.setString(2, name);
				ps.setString(3, addition);
				ps.setString(4, author);
				ps.setFloat(5, price);
				
				int count = ps.executeUpdate();
				
				if(count>0) {
					out.println("<h2 style='color:green;'>Book Data Inserted Successfully  "+id+"  : Your Book ID</h2>");
				}else {
					out.println("<h2 style='color:red;'>Book Data Insertion Failed</h2>");
				}
				
			
		}catch(SQLException e) {
			e.printStackTrace();
			out.println("<h1>"+e.getMessage()+"</h1>");
		}catch(Exception e) {
			e.printStackTrace();
			out.println("<h1>"+e.getMessage()+"</h1>");
		}
		out.println("<a href='home.html'>Home</a>");
		out.println("<br>");
		out.println("<a href='bookList'>Book List</a>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
