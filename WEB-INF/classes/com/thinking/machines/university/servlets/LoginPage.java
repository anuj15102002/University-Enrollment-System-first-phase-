package com.thinking.machines.university.servlets;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import com.thinking.machines.university.utils.*;
import com.thinking.machines.university.servlets.db.*;
public class LoginPage extends HttpServlet
{
public void doGet(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
doPost(httpServletRequest,httpServletResponse);
}
public void doPost(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
{
try
{
System.out.println("Request Arrived");
PrintWriter printWriter=httpServletResponse.getWriter();
String emailId=httpServletRequest.getParameter("email_id");
String password=httpServletRequest.getParameter("password");
boolean isValid;

Connection connection=DBConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("Select applicant_id,email_id,password,password_key from applicant_account where email_id=?");
preparedStatement.setString(1,emailId);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
isValid=resultSet.next();
if(isValid==false)
{
//send back html of login form with error message and filled email
resultSet.close();
preparedStatement.close();
connection.close();
}
long applicantId=resultSet.getLong("applicatn_id");
String db_password=resultSet.getString("password");
String db_password_key=resultSet.getString("password_key");
resultSet.close();
preparedStatement.close();

String encryptedPassword=EncryptionUtility.encrypt(password,db_password_key);
isValid=db_password.equals(encryptedPassword);

if(isValid==false)
{
//send login html with error message and filled email textbox
connection.close();
}
preparedStatement=connection.prepareStatement("select email_verification_status,mobile_number_verification_status from applicant_account_active_status where applicant_id=?");
preparedStatement.setLong(1,applicantId);
String emailVerificationStatus=resultSet.getString(4);
String mobileNumberVerificationStatus=resultSet.getString(5);

resultSet.close();
preparedStatement.close();
connection.close();

boolean isEmailVerified=emailVerificationStatus.equals("V");
boolean isMobileNumberVerified=mobileNumberVerificationStatus.equals("V");

if(isEmailVerified && isMobileNumberVerified)
{
//send back html with email and asking for resume or create application,and logout button with action of /university/logout
return;
}
//send back html asking for email and mobile number verifcation code and have verify button with action of /univeristy/verifyCommunicationChannel




}catch(Exception exception)
{
System.out.println(exception);
}
}
}
