package com.thinking.machines.university.servlets;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import com.thinking.machines.university.servlets.db.*;
import com.thinking.machines.university.utils.*;
public class UpdatePassword extends HttpServlet
{
public void doGet(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
doPost(httpServletRequest,httpServletResponse);
}
public void doPost(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
try
{
System.out.println("UPdated Password");
HttpSession session=httpServletRequest.getSession(false);
if(session==null)
{
return;
}
Long applicant_id=(Long)session.getAttribute("applicantId");
if(applicant_id==null)
{
return;
}
long applicantId=applicant_id;
PrintWriter printWriter=httpServletResponse.getWriter();

String old_password=httpServletRequest.getParameter("old_password");
String new_password=httpServletRequest.getParameter("new_password");
Connection connection=DBConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select * from applicant_account where applicant_id=?");
preparedStatement.setLong(1,applicantId);
ResultSet resultSet=preparedStatement.executeQuery();
resultSet.next();
boolean is_password_correct=false;

String first_name=resultSet.getString("first_name");
String last_name=resultSet.getString("last_name");
String db_password=resultSet.getString("password");
String db_password_key=resultSet.getString("password_key");
resultSet.close();
preparedStatement.close();

String encrypted_old_password=EncryptionUtility.encrypt(old_password,db_password_key);
is_password_correct=encrypted_old_password.equals(db_password);
if(!is_password_correct)
{
printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<HTML>");
printWriter.println("<head>");
printWriter.println("<title>University</title>");
printWriter.println("<script>");
printWriter.println("");
printWriter.println("function validate_password(password)");
printWriter.println("{");
printWriter.println("");
printWriter.println("var uc='ABCDEFGHIJKLMNOPQRSTUVWXYZ';");
printWriter.println("var lc='abcdefghijklmnopqrstuvwxyz';");
printWriter.println("var digit='0123456789';");
printWriter.println("var sc='!@#$%&*~'");
printWriter.println("");
printWriter.println("var c1=false;");
printWriter.println("var c2=false;");
printWriter.println("var c3=false;");
printWriter.println("var c4=false;");
printWriter.println("");
printWriter.println("var m;");
printWriter.println("for(var i=0;i<password.length;i++)");
printWriter.println("{");
printWriter.println("m=password.charAt(i);");
printWriter.println("if(!c1)c1=uc.indexOf(m)!=-1;");
printWriter.println("if(!c2)c2=lc.indexOf(m)!=-1;");
printWriter.println("if(!c3)c3=digit.indexOf(m)!=-1;");
printWriter.println("if(!c4)c4=sc.indexOf(m)!=-1;");
printWriter.println("}");
printWriter.println("return (c1 && c2 && c3 && c4);");
printWriter.println("");
printWriter.println("}");
printWriter.println("");
printWriter.println("function validate_form(f)");
printWriter.println("{");
printWriter.println("var isValid=true;");
printWriter.println("var firstInvalidComponent=null;");
printWriter.println("");
printWriter.println("var old_password=f.old_password.value.trim();");
printWriter.println("var new_password=f.new_password.value.trim();");
printWriter.println("var re_enter_new_password=f.re_enter_new_password.value.trim();");
printWriter.println("");
printWriter.println("var old_password_error_section=document.getElementById('old_password_error_section');");
printWriter.println("var new_password_error_section=document.getElementById('new_password_error_section');");
printWriter.println("var re_enter_new_password_error_section=document.getElementById('re_enter_new_password_error_section');");
printWriter.println("");
printWriter.println("old_password_error_section.innerHTML='';");
printWriter.println("new_password_error_section.innerHTML='';");
printWriter.println("re_enter_new_password_error_section.innerHTML='';");
printWriter.println("");
printWriter.println("if(old_password.length==0)");
printWriter.println("{");
printWriter.println("alert('yes');");
printWriter.println("isValid=false;");
printWriter.println("firstInvalidComponent=f.old_password;");
printWriter.println("old_password_error_section.innerHTML='Required';");
printWriter.println("}");
printWriter.println("if(new_password.length==0)");
printWriter.println("{");
printWriter.println("isValid=false;");
printWriter.println("if(firstInvalidComponent==null)firstInvalidComponent=f.new_password;");
printWriter.println("new_password_error_section.innerHTML='Required';");
printWriter.println("}");
printWriter.println("if(re_enter_new_password.length==0)");
printWriter.println("{");
printWriter.println("isValid=false;");
printWriter.println("if(firstInvalidComponent==null)firstInvalidComponent=f.re_enter_new_password;");
printWriter.println("re_enter_new_password_error_section.innerHTML='Required';");
printWriter.println("}");
printWriter.println("if(new_password.length<8)");
printWriter.println("{");
printWriter.println("alert('New password must conatin 8 character');");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("");
printWriter.println("if(validate_password(new_password)==false)");
printWriter.println("{");
printWriter.println("alert('Password must contain atleast 1 upper case,1 lower case,1 special character,1 number');");
printWriter.println("new_password_error_section.innerHTML='Password must contain atleast 1 upper case,1 lower case,1 special character,1 number';");
printWriter.println("f.new_password.focus();");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("");
printWriter.println("");
printWriter.println("if(new_password!=re_enter_new_password)");
printWriter.println("{");
printWriter.println("alert('Incorrect new password');");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("if(!isValid)");
printWriter.println("{");
printWriter.println("firstInvalidComponent.focus();");
printWriter.println("}");
printWriter.println("");
printWriter.println("return isValid;");
printWriter.println("}");
printWriter.println("</script>");
printWriter.println("<style>");
printWriter.println(".input_error_section");
printWriter.println("{");
printWriter.println("color:red;");
printWriter.println("}");
printWriter.println("</style>");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<h1>University</h1>");
printWriter.println("<h3>Change Password</h3>");
printWriter.println("<form id='changePassword' action='/university/changePassword' method='GET' onsubmit='return validate_form(this)'>");
printWriter.println("Enter old Password");
printWriter.println("<input type='text' id='old_password' name='old_password'>");
printWriter.println("<span id='old_password_error_section' class='input_error_section' >Incorrect Password</span>");
printWriter.println("<br/>");
printWriter.println("Enter new Password");
printWriter.println("<input type='text' id='new_password' name='new_password' >");
printWriter.println("<span id='new_password_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("Re-enter new Password");
printWriter.println("<input type='text' id='re_enter_new_password' name='re_enter_new_password'>");
printWriter.println("<span id='re_enter_new_password_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("<br/>");
printWriter.println("<button type='submit'>Change</button>");
printWriter.println("</form>");
printWriter.println("</body>");
printWriter.println("</html>");
connection.close();
return;
}

String encypted_new_password=EncryptionUtility.encrypt(new_password,db_password_key);
preparedStatement=connection.prepareStatement("update applicant_account set password=? where applicant_id=?");
preparedStatement.setString(1,encypted_new_password);
preparedStatement.setLong(2,applicantId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();

printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<html>");
printWriter.println("<head>");
printWriter.println("<title>VerifiedPage</title>");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<h2>Password Changed</h2>");
printWriter.println("<h4>Welcome "+first_name+" "+last_name+"</h4>");
printWriter.println("<a href='/university/create_account.html'>Create Account</a><br>");
printWriter.println("<a href='/university/edit_profile.html'?applicantId='23424'>Edit Profile</a><br/>");
printWriter.println("<a href='/university/change_password.html'?applicantId='23424'>Change Password</a><br/>");
printWriter.println("<form id='logoutButton' action='/university/logout'>");
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