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

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		int id = Integer.parseInt(req.getParameter("id"));

		String name = req.getParameter("bookName");
		String edition = req.getParameter("bookEdition");
		String author = req.getParameter("bookAuthor");
		float price = Float.parseFloat(req.getParameter("bookPrice"));
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
		try(
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookreg?useSSL=false","root","Asmin@2002");
				PreparedStatement ps = connection.prepareStatement("update book set name=?,eddition=?,author=?,price=? where id=?;")){
				
			ps.setString(1, name);
			ps.setString(2, edition);
			ps.setString(3, author);
			ps.setFloat(4, price);
			ps.setInt(5, id);
			    
			int count = ps.executeUpdate();
			
			if(count>0) {
				out.println("<h1 style='color:green'>Record Edited Successfully</h1>");
			}else {
				out.println("<h1 style='color:red'>Record Not Edited</h1>");
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
