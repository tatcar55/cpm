/*    */ package com.sun.xml.ws.transport.tcp.util;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChannelZeroContext
/*    */   extends ChannelContext
/*    */ {
/* 51 */   static final ChannelSettings channelZeroSettings = new ChannelSettings(Arrays.asList(new String[] { "text/xml", "application/fastinfoset" }, ), Arrays.asList(new String[] { "charset", "SOAPAction" }, ), 0, TCPConstants.SERVICE_CHANNEL_WS_NAME, WSTCPURI.parse("vnd.sun.ws.tcp://somehost:8080/service"));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChannelZeroContext(@NotNull ConnectionSession connectionSession) {
/* 59 */     super(connectionSession, channelZeroSettings);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\ChannelZeroContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */