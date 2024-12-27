package com.thinking.machines.university.servlets;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.util.*;
public class Logout extends HttpServlet
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
System.out.println("Request arrived for logout");
HttpSession session=httpServletRequest.getSession();
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
session.removeAttribute("applicantId");
return;
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
