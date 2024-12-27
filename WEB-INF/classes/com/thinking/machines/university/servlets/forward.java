package com.thinking.machines.university.servlets;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
public class forward extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
doPost(request,response);
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
System.out.println("Good");
RequestDispatcher requestDispatcher;
requestDispatcher=request.getRequestDispatcher("/updatePassword");
requestDispatcher.forward(request,response);
return;
}catch(Exception exception)
{
System.out.println(exception);
}
}
}