import java.io.*;
class ServletUtility
{
public static void main(String args[])
{
if(args.length!=2)
{
System.out.println("Usage [ ServletUtility source.html target.tmp]");
return;
}
String source=args[0];
String target=args[1];
try
{
File sourceFile=new File(source);
if(sourceFile.exists()==false)
{
System.out.println(sourceFile+" does not exist");
return;
}
RandomAccessFile sourceRAF=new RandomAccessFile(sourceFile,"rw");
File targetFile=new File(target);
if(targetFile.exists())targetFile.delete();

RandomAccessFile targetRAF=new RandomAccessFile(targetFile,"rw");
String line;
while(sourceRAF.getFilePointer()<sourceRAF.length())
{
line=sourceRAF.readLine();
targetRAF.writeBytes("printWriter.println(\""+line+"\");\r\n");
}
sourceRAF.close();
targetRAF.close();
System.out.println(target +" generated");
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}

}

}