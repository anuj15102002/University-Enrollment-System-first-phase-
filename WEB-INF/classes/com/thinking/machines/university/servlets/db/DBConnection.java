package com.thinking.machines.university.servlets.db;
import java.sql.*;

public class DBConnection
{
private DBConnection()
{
}
public static Connection getConnection()throws DBException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_db?allowPublicKeyRetrieval=true&useSSL=false","university","university");
return connection;
}catch(ClassNotFoundException cnfe)
{
throw new DBException(cnfe.getMessage());
}
catch(SQLException sqlException)
{
throw new DBException(sqlException.getMessage());
}
catch(Exception exception)
{
throw new DBException(exception.getMessage());
}
}
}

