package com.thinking.machines.university.servlets;
import com.thinking.machines.university.servlets.*;
import com.thinking.machines.university.servlets.db.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class UpdateProfile extends HttpServlet
{
public void doGet(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
doPost(httpServletRequest,httpServletResponse);
}
public void doPost(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
try
{
PrintWriter printWriter=httpServletResponse.getWriter();
HttpSession session=httpServletRequest.getSession(false);
if(session==null)
{
printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<HTML>");
printWriter.println("<head>");
printWriter.println("<title>University</title>");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<h1>Session Timed Out</h1><br/>");
printWriter.println("<a href='/university/index.html'>Home</a>");
printWriter.println("</body>");
printWriter.println("</html>");
return;
}
Long applicant_id=(Long)session.getAttribute("applicantId");
if(applicant_id==null)
{
printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<HTML>");
printWriter.println("<head>");
printWriter.println("<title>University</title>");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<h1>Session Timed Out</h1><br/>");
printWriter.println("<a href='/university/index.html'>Home</a>");
printWriter.println("</body>");
printWriter.println("</html>");
return;
}
long applicantId=applicant_id;
String first_name=httpServletRequest.getParameter("first_name");
String last_name=httpServletRequest.getParameter("last_name");

Connection connection=DBConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("update applicant_account set first_name=?,last_name=? where applicant_id=?");
preparedStatement.setString(1,first_name);
preparedStatement.setString(2,last_name);
preparedStatement.setLong(3,applicantId);
preparedStatement.executeUpdate();

preparedStatement.close();
connection.close();

httpServletResponse.setContentType("text/html");

printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<html>");
printWriter.println("<head>");
printWriter.println("<title>VerifiedPage</title>");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<h2>Authentication successfull</h2>");
printWriter.println("<h4>Updated Successfully</h4>");
printWriter.println("<a href='/university/correct_and_verified.html'>Home</a><br>");
printWriter.println("<form id='logout' action='/university/logout' method='GET'>");
printWriter.println("<button type='submit'>Logout</button>");
printWriter.println("</form>");
printWriter.println("</body>");
printWriter.println("</html>");

}catch(Exception exception)
{
System.out.println(exception);
}
}
}