package com.thinking.machines.university.utils;
public class EMailUtility 
{
private EMailUtility()
{
}
public static void sendEMail(String from,String to,String subject,String message)
{
System.out.println(from+"------>"+to);
System.out.println("Subject: "+subject);
System.out.println("Message: "+message);
//later when the code to send the mail is implemented,remove the above three lines and paste that code
}
}