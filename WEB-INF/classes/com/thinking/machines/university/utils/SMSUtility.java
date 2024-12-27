package com.thinking.machines.university.utils;
public class SMSUtility
{
private SMSUtility()
{
}
public static void sendSMS(String toMobileNumber,String message)
{
System.out.println("Mobile Number: "+toMobileNumber);
System.out.println("Message: "+message);
}
}