/*    */ package com.sun.xml.ws.developer;
/*    */ 
/*    */ import java.net.URL;
/*    */ import javax.activation.DataSource;
/*    */ import org.jvnet.staxex.StreamingDataHandler;
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
/*    */ public abstract class StreamingDataHandler
/*    */   extends StreamingDataHandler
/*    */ {
/*    */   private String hrefCid;
/*    */   
/*    */   public StreamingDataHandler(Object o, String s) {
/* 71 */     super(o, s);
/*    */   }
/*    */   
/*    */   public StreamingDataHandler(URL url) {
/* 75 */     super(url);
/*    */   }
/*    */   
/*    */   public StreamingDataHandler(DataSource dataSource) {
/* 79 */     super(dataSource);
/*    */   }
/*    */   
/*    */   public String getHrefCid() {
/* 83 */     return this.hrefCid;
/*    */   }
/*    */   
/*    */   public void setHrefCid(String cid) {
/* 87 */     this.hrefCid = cid;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\StreamingDataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */