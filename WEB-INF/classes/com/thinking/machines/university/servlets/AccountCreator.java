package com.thinking.machines.university.servlets;
/*
till tomcat version 9
import javax.servlet.*;
import javax.servlet.http.*;
*/

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import com.thinking.machines.university.utils.*;
import com.thinking.machines.university.servlets.db.*;
import java.util.*;
import java.sql.*;

public class AccountCreator extends HttpServlet
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
//now extract the data from form using getParameter() method from httpServletRequest
String firstName;
String lastName;
String emailId;
String mobileNumber;
String password;
String passwordKey;
String encryptedPassword;

firstName=httpServletRequest.getParameter("first_name");
lastName=httpServletRequest.getParameter("last_name");
emailId=httpServletRequest.getParameter("email_id");
password=httpServletRequest.getParameter("password");
mobileNumber=httpServletRequest.getParameter("mobile_number");
System.out.println(mobileNumber);

boolean emailIdExists=false;
boolean mobileNumberExists=false;

Connection connection=DBConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select email_id from applicant_account where email_id=?");
preparedStatement.setString(1,emailId);
ResultSet resultSet=preparedStatement.executeQuery();
emailIdExists=resultSet.next();
resultSet.close();
preparedStatement.close();

preparedStatement=connection.prepareStatement("select mobile_number from applicant_account where mobile_number=?");
preparedStatement.setString(1,mobileNumber);
resultSet=preparedStatement.executeQuery();
mobileNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

if(emailIdExists ||mobileNumberExists)
{
printWriter.println("<!DOCTYPE HTML>");
printWriter.println("<html>");
printWriter.println("<head>");
printWriter.println("<title>Create Account</title>");
printWriter.println("<script>");
printWriter.println("function validate_email_id(email_id)");
printWriter.println("{");
printWriter.println("var sc='~!#$%^&*()_+=[]{};:,</?';");
printWriter.println("var dotIndex=email_id.indexOf('.');");
printWriter.println("var atTheRateIndex=email_id.indexOf('@');");

printWriter.println("if(email_id.indexOf('@')==-1 || email_id.indexOf('@')==0 || email_id.indexOf('@')==0)");
printWriter.println("{");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("if(email_id.indexOf('.')==-1 || email_id.indexOf('.')==email_id.length-1 || email_id.indexOf('.')==0)");
printWriter.println("{");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("if(dotIndex==atTheRateIndex+1)return false;");
printWriter.println("if(email_id.indexOf('@')!=email_id.lastIndexOf('@'))return false;");
printWriter.println("if(email_id.indexOf('.')!=email_id.lastIndexOf('.'))return false;");
printWriter.println("if(email_id.indexOf(' ')!=-1)return false;");
printWriter.println("for(var i=0;i<sc.length;++i)");
printWriter.println("{");
printWriter.println("var m=sc.charAt(i);");
printWriter.println("if(email_id.indexOf(m)!=-1)");
printWriter.println("{");
printWriter.println("alert('email should only contain (a-z),(0-9) and (.) as special character before @');");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("}");

printWriter.println("}");
printWriter.println("function validate_password(password)");
printWriter.println("{");
printWriter.println("//password should contain atleast 1 lower case,atleast 1 upper case,atleast 1 special_character,atleast 1 number");
printWriter.println("var specialCharacter='@!#$*';");
printWriter.println("var lowerCase='abcdefghijklmnopqrstuvwxyz';");
printWriter.println("var upperCase='ABCDEFGHIJKLMNOPQRSTUVWXYZ';");
printWriter.println("var digit='0123456789';");
printWriter.println("var c1=false;");
printWriter.println("var c2=false;");
printWriter.println("var c3=false;");
printWriter.println("var c4=false;");

printWriter.println("var m;");
printWriter.println("for(var i=0;i<password.length;i++)");
printWriter.println("{");
printWriter.println("m=password.charAt(i);");
printWriter.println("if(!c1)c1=lowerCase.indexOf(m)!=-1;");
printWriter.println("if(!c2)c2=upperCase.indexOf(m)!=-1;");
printWriter.println("if(!c3)c3=digit.indexOf(m)!=-1;");
printWriter.println("if(!c4)c4=specialCharacter.indexOf(m)!=-1;");
printWriter.println("}");
printWriter.println("alert(c1 && c2 && c3 && c4);");
printWriter.println("return c1 && c2 && c3 && c4;");
printWriter.println("}");
printWriter.println("function validate_form(f)");
printWriter.println("{");
printWriter.println("var first_name=f.first_name.value.trim();  //here f.first_name (first_name is name property)");
printWriter.println("var last_name=f.last_name.value.trim();    //here f.last_name (last_name is name property)");
printWriter.println("var email_id=f.email_id.value.trim();      //here f.email_id (email_id is name property) ");
printWriter.println("var password=f.password.value.trim();      //here f.password (password is name property)");
printWriter.println("var re_password=f.re_password.value.trim();//here f.re_passowrd (re_password is name property)");
printWriter.println("var mobile_number=f.mobile_number.value.trim();//here f.mobile_number(mobile_number is name property)");
 
printWriter.println("var is_invalid;");
printWriter.println("var first_invalid_component=null;");

printWriter.println("var first_name_error_section=document.getElementById('first_name_error_section');");
printWriter.println("var last_name_error_section=document.getElementById('last_name_error_section');");
printWriter.println("var email_id_error_section=document.getElementById('email_id_error_section');");
printWriter.println("var password_error_section=document.getElementById('password_error_section');");
printWriter.println("var re_password_error_section=document.getElementById('re_password_error_section');");
printWriter.println("var mobile_number_error_section=document.getElementById('mobile_number_error_section');");

printWriter.println("first_name_error_section.innerHTML='';");
printWriter.println("last_name_error_section.innerHTML='';");
printWriter.println("email_id_error_section.innerHTML='';");
printWriter.println("password_error_section.innerHTML='';");
printWriter.println("re_password_error_section.innerHTML='';");
printWriter.println("mobile_number_error_section.innerHTML='';");


printWriter.println("if(first_name.length==0)");
printWriter.println("{");
printWriter.println("first_name_error_section.innerHTML='first name required';");
printWriter.println("is_invalid=false;");
printWriter.println("first_invalid_component=f.first_name;");
printWriter.println("}");
printWriter.println("if(last_name.length==0)");
printWriter.println("{");
printWriter.println("last_name_error_section.innerHTML='last_name_required';");
printWriter.println("is_invalid=false;");
printWriter.println("if(first_invalid_component==null)first_invalid_component=f.last_name;");
printWriter.println("}");
printWriter.println("if(email_id.length==0)");
printWriter.println("{");
printWriter.println("email_id_error_section.innerHTML='email id required';");
printWriter.println("is_invalid=false;");
printWriter.println("if(first_invalid_component==null)first_invalid_component=f.email_id;");
printWriter.println("}");
printWriter.println("if(password.length==0)");
printWriter.println("{");
printWriter.println("password_error_section.innerHTML='password required';");
printWriter.println("is_invalid=false;");
printWriter.println("if(first_invalid_component==null)first_invalid_component=f.password;");
printWriter.println("}");
printWriter.println("if(re_password.length==0)");
printWriter.println("{");
printWriter.println("re_password_error_section.innerHTML='re type password is required';");
printWriter.println("is_invalid=false;");
printWriter.println("if(first_invalid_component==null)first_invalid_component=f.re_password;");
printWriter.println("}");
printWriter.println("if(mobile_number.length==0)");
printWriter.println("{");
printWriter.println("mobile_number_error_section.innerHTML='Mobile Number is required';");
printWriter.println("is_invalid=false;");
printWriter.println("if(first_invalid_component==null)first_invalid_component=f.mobile_number;");
printWriter.println("}");
printWriter.println("if(!is_invalid)");
printWriter.println("{");
printWriter.println("first_invalid_component.focus();");
printWriter.println("}");

printWriter.println("if(password.length<8)");
printWriter.println("{");
printWriter.println("alert('password must contain atleast 8 characters');");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("if(password!=re_password)");
printWriter.println("{");
printWriter.println("alert('Wrong password');");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("if(valid_password(password)==false)");
printWriter.println("{");
printWriter.println("alert('Invalid password format');");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("if(validate_email_id(email_id)==false)");
printWriter.println("{");
printWriter.println("alert('invalid email id format');");
printWriter.println("return false;");
printWriter.println("}");
printWriter.println("alert(is_invalid);");
printWriter.println("return is_invalid;");
printWriter.println("}");
printWriter.println("</script>");
printWriter.println("<style>");
printWriter.println(".input_error_section");
printWriter.println("{");
printWriter.println("color:#BA0000;");
printWriter.println("}");
printWriter.println(".input_error_section");
printWriter.println("{");
printWriter.println("color:#BA0000;");
printWriter.println("}");

printWriter.println("</style>");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<h1>University</h1>");
printWriter.println("<h5>Create Account<h5>");
if(emailIdExists)
{
printWriter.println("<h2 class='input_error_section'>Email id "+emailId+" exists</h2>");
}
else if(mobileNumberExists)
{
printWriter.println("<h2 class='input_error_section'>Mobile number "+mobileNumber+" exists</h2>");
}
if(emailIdExists && mobileNumberExists)
{
printWriter.println("<h2 class='input_error_section'>Both "+emailId+" and "+mobileNumber+" exists</h2>");
}
printWriter.println("<form id='account_creation_form' method='GET' action='accountCreateKaro' onsubmit='return validate_form(this)'>");
printWriter.println("First Name");
printWriter.println("<input type='text' name='first_name' id='first_name' value='"+firstName+"'>*");
printWriter.println("<span id='first_name_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("Last Name");
printWriter.println("<input type='text' name='last_name' if='last_name' value='"+lastName+"'>*");
printWriter.println("<span id='last_name_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("Email id");
printWriter.println("<input type='text' name='email_id' id='email_id' value='"+emailId+"'>*");
printWriter.println("<span id='email_id_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("Password");
printWriter.println("<input type='text' name='password' id='password'>*");
printWriter.println("<span id='password_error_section' class='input_error_section'></span>");
printWriter.println("(minimum 8 character ,atleast 1 upper case,1 lower case,1 special character,1 digit)");
printWriter.println("<br/>");
printWriter.println("Re-type Password");
printWriter.println("<input type='password' name='re_password' id='re_password'>*");
printWriter.println("<span id='re_password_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("Mobile Number");
printWriter.println("<input type='text' name='mobile_number' id='mobile_number' value='"+mobileNumber+"'>*");
printWriter.println("<span id='mobile_number_error_section' class='input_error_section'></span>");
printWriter.println("<br/>");
printWriter.println("<button type='submit'>Create Account</button>");
printWriter.println("</form>");
printWriter.println("<br/>");
printWriter.println("<a href='/university/authenticateKaro'>Home</a>");
printWriter.println("</body>");
printWriter.println("</html>");




connection.close();
return;
}
//send mail
System.out.println("send mail");
String codeToSendByEMail=UniqueCodeGenerator.getUniqueSixCharacterCode();
String from,to,subject,message;
from="some_email_id_of_university@gmail.com";
to=emailId;
subject="Email verification code from university";
message="The following is your 6 character code to verify your email id ["+codeToSendByEMail+"]";
EMailUtility.sendEMail(from,to,subject,message);

//send sms
System.out.println("send sms");
String codeToSendBySMS=UniqueCodeGenerator.getUniqueSixCharacterCode();
SMSUtility.sendSMS(mobileNumber,"Your mobile number verification code is ["+codeToSendBySMS+"]");

//encrypt password
System.out.println("encrypt password");
passwordKey=UUID.randomUUID().toString();
encryptedPassword=EncryptionUtility.encrypt(password,passwordKey);

//insert record in database
System.out.println("Insert record in database");
preparedStatement=connection.prepareStatement("insert into applicant_account(email_id,first_name,last_name,password,password_key,mobile_number) values(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,emailId);
preparedStatement.setString(2,firstName);
preparedStatement.setString(3,lastName);
preparedStatement.setString(4,encryptedPassword);
preparedStatement.setString(5,passwordKey);
preparedStatement.setString(6,mobileNumber);

preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
long applicantId=resultSet.getLong(1);
resultSet.close();
preparedStatement.close();

preparedStatement=connection.prepareStatement("insert into applicant_account_active_status values(?,?,?,?,?)");
preparedStatement.setLong(1,applicantId);
preparedStatement.setString(2,codeToSendByEMail);
preparedStatement.setString(3,codeToSendBySMS);
preparedStatement.setString(4,"N");
preparedStatement.setString(5,"N");
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();

//send success html
printWriter.println("<!DOCTYPE html>");
printWriter.println("<HTML>");
printWriter.println("<head>");
printWriter.println("<meta charset='utf-8'>");
printWriter.println("<title>University</title>");
printWriter.println("</head>");
printWriter.println("<body>");
printWriter.println("<h1>Account Creation</h1>");
printWriter.println("Your account has been created<br>");
printWriter.println("Verification code has been sent to your email id("+emailId+")<br>");
printWriter.println("and mobile umber ("+mobileNumber+")<br>");
printWriter.println("<form action='/university/login.html'>");
printWriter.println("<button type='submit'>Proceed to Login</button>");
printWriter.println("</form>");
printWriter.println("</body>");
printWriter.println("</html>");
System.out.println("Send success html");


}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
}
