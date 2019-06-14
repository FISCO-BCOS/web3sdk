package org.fisco.bcos.channel.test.amop;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.fisco.bcos.channel.client.ChannelPushCallback;
import org.fisco.bcos.channel.dto.ChannelPush;
import org.fisco.bcos.channel.dto.ChannelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PushCallback extends ChannelPushCallback {
  static Logger logger = LoggerFactory.getLogger(PushCallback.class);
  
  public static void getFileFromBytes(byte[] b, String outputFile) {    
      File ret = null;    
      BufferedOutputStream stream = null;    
      try {    
          ret = new File(outputFile);    
          FileOutputStream fstream = new FileOutputStream(ret);    
          stream = new BufferedOutputStream(fstream);
          stream.write(b);
      } 
      catch (Exception e) {      
          e.printStackTrace();    
      } finally {    
          if (stream != null) {    
              try {    
                  stream.close();    
              } catch (IOException e) {     
                  e.printStackTrace();
              }    
          }    
      } 
  }
  
  public static byte[] subbytes(byte[] src, int begin, int count)
  {
	  byte[] bs = new byte[count];
	  System.arraycopy(src, begin, bs, 0, count);
	  return bs;
  }
  
  
  public static int byteArrayToInt(byte[] bytes) {
      int value= 0;
      for (int i = 0; i < 4; i++) {
          int shift= (4 - 1 - i) * 8;
          value +=(bytes[i] & 0x000000FF) << shift;
      }
      return value;
}

  @Override
  public void onPush(ChannelPush push) {
    System.out.println("onPush content length:"+push.getContent2().length);
    logger.debug("onPush content content:"+Arrays.toString(push.getContent2()));
    
    if(push.getContent2().length < 8)
    {
    	String content = new String(push.getContent2());
    	System.out.println("onPush content:"+content);
    }
    else
    {
    	byte[] byteflag = subbytes(push.getContent2(),0,4);
    	int flag = byteArrayToInt(byteflag);
    	
    	if(flag == -128)
    	{
    		byte[] bytelength = subbytes(push.getContent2(),4,4);
        	int length = byteArrayToInt(bytelength);
        	byte[] bytefilename = subbytes(push.getContent2(),8,length);
        	String filename = new String(bytefilename);
        	System.out.println("filename length:"+length
        			+" filename binary:"+Arrays.toString(bytefilename)
        			+" filename:"+filename);
        	
        	int contentlength = push.getContent2().length-8-filename.length();
        	byte[] content = subbytes(push.getContent2(),8+filename.length(),contentlength);
        	getFileFromBytes(content,filename);
        	System.out.println("save file:"+filename+" success");
        	
    	}
    	else
    	{	
    		String content = new String(push.getContent2());
        	System.out.println("onPush  content:"+content);
    	}
    }
    ChannelResponse response = new ChannelResponse();
    response.setContent("receive request seq:" + String.valueOf(push.getMessageID()));
    response.setErrorCode(0);
    push.sendResponse(response);
  }
}
