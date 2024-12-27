package com.thinking.machines.university.servlets;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.thinking.machines.university.servlets.*;
import com.thinking.machines.university.servlets.db.*;
import com.thinking.machines.university.utils.*;
public class Authenticator extends HttpServlet
{
public void doGet(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
doPost(httpServletRequest,httpServletResponse);
}
public void doPost(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
try
{
long applicantId=0;
String db_first_name="";
String db_last_name="";
String db_password="";
String db_password_key="";
String db_mobile_number="";
System.out.println("Request Arrived");
//fetch the address of printWriter from httpServletResponse
PrintWriter printWriter=httpServletResponse.getWriter();

//fetch the data that user has entered
String emailId=httpServletRequest.getParameter("email_id");
String password=httpServletRequest.getParameter("password");

//establish connection from database
Connection connection=DBConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select * from applicant_account where email_id=?");
preparedStatement.setString(1,emailId);
ResultSet resultSet=preparedStatement.executeQuery();
//checking if this email exists or not

boolean isValid=false;
if(resultSet.next())
{
applicantId=resultSet.getLong("applicant_id");
db_first_name=resultSet.getString("first_name").trim();
db_last_name=resultSet.getString("last_name").trim();
db_password=resultSet.getString("password").trim();
db_password_key=resultSet.getString("password_key").trim();
db_mobile_number=resultSet.getString("mobile_number").trim();
String encryptedPassword=EncryptionUtility.encrypt(password,db_password_key);
System.out.println("EP: "+encryptedPassword);
System.out.println("pasw: "+db_password);
isValid=encryptedPassword.equals(db_password);
}

resultSet.close();
preparedStatement.close();
System.out.println("Is valis: "+isValid);
//encrypt password with the help of db_password_key

//checking if password is correct or not
if(isValid==false)
{
System.out.println("Login form with error message");
connection.close();
printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<html>");
printWriter.println("<head>");
printWriter.println("<title>University</title>");
printWriter.println("<script>");
printWriter.println("function validate_form(f)");
printWriter.println("{");
printWriter.println("var first_invalid_component=null;");
printWriter.println("var is_valid=true;");
printWriter.println("if(f.email_id.value.trim().length==0)");
printWriter.println("{");
printWriter.println("is_valid=false;");
printWriter.println("first_invalid_component=f.email_id;");
printWriter.println("document.getElementById('email_id_error_section').innerHTML='Required';");
printWriter.println("}");
printWriter.println("if(f.password.value.trim().length==0)");
printWriter.println("{");
printWriter.println("is_valid=false;");
printWriter.println("if(first_invalid_component==null)first_invalid_component=f.password;");
printWriter.println("document.getElementById('password_error_section').innerHTML='Required'");
printWriter.println("}");
printWriter.println("if(!is_valid)");
printWriter.println("{");
printWriter.println("first_invalid_component.focus();");
printWriter.println("}");
printWriter.println("alert(is_valid);");
printWriter.println("return is_valid;");
printWriter.println("");
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
printWriter.println("<h1>Authentication</h1>");
printWriter.println("<h3>Invalid email id and password</h3>");
printWriter.println("<form id='loginKaro' method='GET' action='/university/authenticateKaro' onsubmit='return validate_form(this)'>");
printWriter.println("Email Id");
printWriter.println("<input type='text' name='email_id' id='email_id' value="+emailId+">");
printWriter.println("<span id='email_id_error_section'class='input_error_section' ></span>");
printWriter.println("<br/>");
printWriter.println("Password");
printWriter.println("<input type='text' name='password' id='password'>");
printWriter.println("<span id='password_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("<button type='submit'>Login</button>");
printWriter.println("</form>");
printWriter.println("</body>");
printWriter.println("</html>");


return;
}
System.out.println("hello");
boolean isEmailIdVerified=false;
boolean isMobileNumberVerified=false;
preparedStatement=connection.prepareStatement("select email_verification_status,mobile_number_status from applicant_account_active_status where applicant_id=?");
preparedStatement.setLong(1,applicantId);
resultSet=preparedStatement.executeQuery();
resultSet.next();

String emailIdVerificationStatus=resultSet.getString("email_verification_status").trim();
System.out.println(emailIdVerificationStatus);
String mobileNumberVerificationStatus=resultSet.getString("mobile_number_status").trim();
System.out.println(mobileNumberVerificationStatus);
resultSet.close();
preparedStatement.close();
connection.close();

isEmailIdVerified=emailIdVerificationStatus.equals("Y");
isMobileNumberVerified=mobileNumberVerificationStatus.equals("Y");
System.out.println("emailId: "+isEmailIdVerified);
System.out.println("mobile number: "+isMobileNumberVerified);

HttpSession session=httpServletRequest.getSession();
session.setAttribute("applicantId",applicantId);

if(isEmailIdVerified && isMobileNumberVerified)
{
printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<html>");
printWriter.println("<head>");
printWriter.println("<title>VerifiedPage</title>");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<h2>Authentication successfull</h2>");
printWriter.println("<h4>Welcome "+db_first_name+" "+db_last_name+"</h4>");
printWriter.println("<a href='/university/create_account.html'>Create Account</a><br>");
printWriter.println("<a href='/university/editProfile'>Edit Profile</a><br/>");
printWriter.println("<a href='/university/changePassword'>Change Password</a><br/>");
printWriter.println("<form id='logoutButton' action='/university/logout'>");
printWriter.println("<button type='submit'>Logout</button>");
printWriter.println("</form>");
printWriter.println("</body>");
printWriter.println("</html>");
return;
}
if(isEmailIdVerified==false && isMobileNumberVerified==false)
{
//send back html form for the verification of email and number whichever is needed and verify button to verify whose action /university/verifyCommunicationChannels
System.out.println("Something is not verified");
printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<html>");
printWriter.println("<head>");
printWriter.println("<title>University</title>");
printWriter.println("<script>");
printWriter.println("function validate_form(f)");
printWriter.println("{");
printWriter.println("var is_valid=true;");
printWriter.println("document.getElementById('code_sent_to_email_id_error_section').innerHTML='';");
printWriter.println("document.getElementById('code_sent_to_mobile_number_error_section').innerHTML='';");
printWriter.println("var first_invalid_component=null;");
printWriter.println("if(f.code_sent_to_email_id.value.trim().length==0)");
printWriter.println("{");
printWriter.println("is_valid=false;");
printWriter.println("document.getElementById('code_sent_to_email_id_error_section').innerHTML='Required';");
printWriter.println("first_invalid_component=f.code_sent_to_email_id;");
printWriter.println("}");
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
printWriter.println("<h3>Verifying your email id and mobile number</h3>");
printWriter.println("<form id='verification_form' name='verification_form' action='/university/verifyCommunicationChannels' onsubmit='return validate_form(this)' >");
printWriter.println("Enter code sent to email("+emailId+")");
printWriter.println("<input type='text' id='code_sent_to_email_id' name='code_sent_to_email_id'>");
printWriter.println("<span id='code_sent_to_email_id_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
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
if(!isEmailIdVerified)
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
}catch(Exception exception)
{
System.out.println(exception);
}
}
}