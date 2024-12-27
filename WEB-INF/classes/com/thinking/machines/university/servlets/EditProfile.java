package com.thinking.machines.university.servlets;
import com.thinking.machines.university.servlets.*;
import com.thinking.machines.university.servlets.db.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class EditProfile extends HttpServlet
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
System.out.println("Applicant ID: "+applicantId);

System.out.println("Request Arrived");
Connection connection=DBConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select first_name,last_name from applicant_account where applicant_id=?");
preparedStatement.setLong(1,applicantId);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
String firstName=resultSet.getString("first_name");
String lastName=resultSet.getString("last_name");
resultSet.close();
preparedStatement.close();
connection.close();

httpServletResponse.setContentType("text/html");


printWriter.println("<!DOTYPE HTML>");
printWriter.println("<HTML>");
printWriter.println("<head>");
printWriter.println("<title>University</title>");
printWriter.println("<script>");
printWriter.println("function validate_form(f)");
printWriter.println("{");
printWriter.println("var isValid=true;");
printWriter.println("var firstInvalidComponent=null;");
printWriter.println("document.getElementById('first_name_error_section').innerHTML='';");
printWriter.println("document.getElementById('last_name_error_section').innerHTML='';");
printWriter.println("if(f.first_name.value.trim().length==0)");
printWriter.println("{");
printWriter.println("isValid=false;");
printWriter.println("firstInvalidComponent=f.first_name;");
printWriter.println("document.getElementById('first_name_error_section').innerHTML='Required';");
printWriter.println("}");
printWriter.println("if(f.last_name.value.trim().length==0)");
printWriter.println("{");
printWriter.println("isValid=false;");
printWriter.println("if(firstInvalidComponent==null)firstInvalidComponent=f.last_name;");
printWriter.println("document.getElementById('last_name_error_section').innerHTML='Required';");
printWriter.println("}");
printWriter.println("if(!isValid)");
printWriter.println("{");
printWriter.println("firstInvalidComponent.focus();");
printWriter.println("}");
printWriter.println("return isValid;");
printWriter.println("}");
printWriter.println("</script>");
printWriter.println("");
printWriter.println("<style>");
printWriter.println(".input_error_section");
printWriter.println("{");
printWriter.println("color:red;");
printWriter.println("}");
printWriter.println("</style>");
printWriter.println("");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<form id='editProfile' name='editProfile' action='/university/updateProfile' method='POST' onsubmit='return validate_form(this)'>");
printWriter.println("<h1>University</h1>");
printWriter.println("<h3>Edit Profile</h3>");
printWriter.println("First Name");
printWriter.println("<input type='text' id='first_name' name='first_name' value='"+firstName+"'>");
printWriter.println("<span id='first_name_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("Last Name ");
printWriter.println("<input type='text' id='last_name' name='last_name' value='"+lastName+"'>");
printWriter.println("<span id='last_name_error_section' class='input_error_section'></span><br/>");
printWriter.println("<br/>");
printWriter.println("<button type='submit'>Update</button>");
printWriter.println("</form>");
printWriter.println("</body>");
printWriter.println("</html>");

}catch(Exception exception)
{
System.out.println(exception);
}
}
}