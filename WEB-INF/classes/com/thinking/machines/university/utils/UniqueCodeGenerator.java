package com.thinking.machines.university.utils;
import java.util.*;
public class UniqueCodeGenerator
{
private UniqueCodeGenerator()
{
}
public static String getUniqueSixCharacterCode()
{
String s;
s=UUID.randomUUID().toString();
String code="";
char m;
int x=0;
while(true)
{
m=s.charAt(x);
if(m=='0' || m=='o' || m=='O' || m=='l' || m=='1')
{
x++;
continue;
}
if(m!='-')
{
code=code+m;
if(code.length()==6)break;
}
x++;
}
System.out.println(code);
return code;
}
}