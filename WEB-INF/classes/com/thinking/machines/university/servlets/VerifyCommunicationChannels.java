package com.thinking.machines.university.servlets;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.thinking.machines.university.servlets.db.*;
public class VerifyCommunicationChannels extends HttpServlet
{
public void doGet(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
doPost(httpServletRequest,httpServletResponse);
}
public void doPost(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
try
{
System.out.println("Request Arrived for VerifyCommunicationChannels");
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

String input_email_code=httpServletRequest.getParameter("code_sent_to_email_id");
String input_mobile_code=httpServletRequest.getParameter("code_sent_to_mobile_number");

Connection connection=DBConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select * from applicant_account where applicant_id=?");
preparedStatement.setLong(1,applicantId);
ResultSet resultSet=preparedStatement.executeQuery();
resultSet.next();
String first_name=resultSet.getString("first_name");
String last_name=resultSet.getString("last_name");
String emailId=resultSet.getString("email_id");
String db_mobile_number=resultSet.getString("mobile_number");
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("Select code_sent_to_mobile_number,code_sent_to_email from applicant_account_active_status where applicant_id=?");
preparedStatement.setLong(1,applicantId);
resultSet=preparedStatement.executeQuery();
resultSet.next();


String db_email_code=resultSet.getString("code_sent_to_email");
String db_mobile_code=resultSet.getString("code_sent_to_mobile_number");
System.out.println(db_email_code);
System.out.println(db_mobile_code);


boolean is_email_code_valid=true;
boolean is_mobile_code_valid=true;
if(input_email_code!=null && db_email_code!=null)
{
is_email_code_valid=input_email_code.equals(db_email_code);
System.out.println(is_email_code_valid);
}

if(input_mobile_code!=null && db_mobile_code!=null)
{
is_mobile_code_valid=input_mobile_code.equals(db_mobile_code);
System.out.println(is_mobile_code_valid);
}

resultSet.close();
preparedStatement.close();



if(is_email_code_valid && is_mobile_code_valid)
{
preparedStatement=connection.prepareStatement("update applicant_account_active_status set email_verification_status=?,mobile_number_status=? where applicant_id=?");
preparedStatement.setString(1,"Y");
preparedStatement.setString(2,"Y");
preparedStatement.setLong(3,applicantId);
preparedStatement.executeUpdate();
preparedStatement.close();
printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<html>");
printWriter.println("<head>");
printWriter.println("<title>VerifiedPage</title>");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<h2>Successfully Verified</h2>");
printWriter.println("<h4>Welcome "+first_name+" "+last_name+"</h4>");
printWriter.println("<a href='/university/create_account.html'>Create Account</a><br>");
printWriter.println("<a href='/university/edit_profile.html'?applicantId='23424'>Edit Profile</a><br/>");
printWriter.println("<a href='/university/change_password.html'?applicantId='23424'>Change Password</a><br/>");
printWriter.println("<form id='logoutButton' action='/university/logout'>");
printWriter.println("<button type='submit'>Logout</button>");
printWriter.println("</form>");
printWriter.println("</body>");
printWriter.println("</html>");

return;
}
if(!is_email_code_valid)
{

printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<html>");
printWriter.println("<head>");
printWriter.println("<title>University</title>");
printWriter.println("<script>");
printWriter.println("function validate_form(f)");
printWriter.println("{");
printWriter.println("var is_valid=true;");
printWriter.println("document.getElementById('code_sent_to_email_id_error_section').innerHTML='';");
printWriter.println("var first_invalid_component=null;");
printWriter.println("if(f.code_sent_to_email_id.value.trim().length==0)");
printWriter.println("{");
printWriter.println("is_valid=false;");
printWriter.println("document.getElementById('code_sent_to_email_id_error_section').innerHTML='Required';");
printWriter.println("first_invalid_component=f.code_sent_to_email_id;");
printWriter.println("}");
printWriter.println("if(!is_valid)first_invalid_component.focus();");
printWriter.println("alert(is_valid);");
printWriter.println("return is_valid;");
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
printWriter.println("<h3>Verifying your email id </h3>");
printWriter.println("<form id='verification_form' name='verification_form' action='/university/verifyCommunicationChannels' onsubmit='return validate_form(this)' >");
printWriter.println("Enter code sent to email("+emailId+")");
printWriter.println("<input type='text' id='code_sent_to_email_id' name='code_sent_to_email_id'>");
printWriter.println("<span id='code_sent_to_email_id_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("<button type='submit'>Verify</button>");
printWriter.println("</form>");
printWriter.println("<br/>");
printWriter.println("<br/>");
printWriter.println("");
printWriter.println("<form id='logout' action='/university/logout' method='GET'>");
printWriter.println("<button type='submit'>Logout</button>");
printWriter.println("</form>");
printWriter.println("</body>");
printWriter.println("</html>");
return;
}
else
{
preparedStatement=connection.prepareStatement("update applicant_account_active_status set email_verification_status=? where applicant_id=?");
preparedStatement.setString(1,"Y");
preparedStatement.setLong(2,applicantId);
preparedStatement.executeUpdate();
preparedStatement.close();

}
if(!is_mobile_code_valid)
{
printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<html>");
printWriter.println("<head>");
printWriter.println("<title>University</title>");
printWriter.println("<script>");
printWriter.println("function validate_form(f)");
printWriter.println("{");
printWriter.println("var is_valid=true;");
printWriter.println("document.getElementById('code_sent_to_mobile_number_error_section').innerHTML='';");
printWriter.println("var first_invalid_component=null;");
printWriter.println("if(f.code_sent_to_mobile_number.value.trim().length==0)");
printWriter.println("{");
printWriter.println("is_valid=false;");
printWriter.println("document.getElementById('code_sent_to_mobile_number_error_section').innerHTML='Required';");
printWriter.println("if(first_invalid_component==null)first_invalid_component=f.code_sent_to_mobile_number;");
printWriter.println("}");
printWriter.println("if(!is_valid)first_invalid_component.focus();");
printWriter.println("alert(is_valid);");
printWriter.println("return is_valid;");
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
printWriter.println("<h3>Verifying your mobile number</h3>");
printWriter.println("<form id='verification_form' name='verification_form' action='/university/verifyCommunicationChannels' onsubmit='return validate_form(this)' >");
printWriter.println("Enter code sent to mobile number("+db_mobile_number+")");
printWriter.println("<input type='text' id='code_sent_to_mobile_number' name='code_sent_to_mobile_number'>");
printWriter.println("<span id='code_sent_to_mobile_number_error_section' class='input_error_section'></span><br/>");
printWriter.println("<button type='submit'>Verify</button>");
printWriter.println("</form>");
printWriter.println("<br/>");
printWriter.println("<br/>");
printWriter.println("");
printWriter.println("<form id='logout' action='/university/logout' method='GET'>");
printWriter.println("<button type='submit'>Logout</button>");
printWriter.println("</form>");
printWriter.println("</body>");
printWriter.println("</html>");
return;
}
else
{
preparedStatement=connection.prepareStatement("update applicant_account_active_status set mobile_number_status=? where applicantId=?");
preparedStatement.setString(1,"Y");
preparedStatement.setLong(2,applicantId);
preparedStatement.executeUpdate();
preparedStatement.close();

}
connection.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}