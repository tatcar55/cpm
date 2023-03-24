/*    */ package com.sun.xml.ws.transport.http.servlet;
/*    */ 
/*    */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*    */ import com.sun.xml.ws.transport.http.WSHTTPConnection;
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Logger;
/*    */ import javax.servlet.AsyncContext;
/*    */ import javax.servlet.AsyncEvent;
/*    */ import javax.servlet.AsyncListener;
/*    */ import javax.servlet.http.HttpServletRequest;
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
/*    */ public class WSAsyncListener
/*    */ {
/*    */   private final WSHTTPConnection con;
/*    */   private final HttpAdapter.CompletionCallback callback;
/*    */   
/*    */   WSAsyncListener(WSHTTPConnection con, HttpAdapter.CompletionCallback callback) {
/* 61 */     this.con = con;
/* 62 */     this.callback = callback;
/*    */   }
/*    */   
/*    */   public void addListenerTo(AsyncContext context, final ServletAdapter.AsyncCompletionCheck completionCheck) {
/* 66 */     context.addListener(new AsyncListener()
/*    */         {
/*    */           public void onComplete(AsyncEvent event) throws IOException {
/* 69 */             WSAsyncListener.LOGGER.finer("Asynchronous Servlet Invocation completed for " + ((HttpServletRequest)event.getAsyncContext().getRequest()).getRequestURL());
/* 70 */             WSAsyncListener.this.callback.onCompletion();
/*    */           }
/*    */ 
/*    */           
/*    */           public void onTimeout(AsyncEvent event) throws IOException {
/* 75 */             completionCheck.markComplete();
/* 76 */             WSAsyncListener.LOGGER.fine("Time out on Request:" + ((HttpServletRequest)event.getAsyncContext().getRequest()).getRequestURL());
/* 77 */             WSAsyncListener.this.con.close();
/*    */           }
/*    */           
/*    */           public void onError(AsyncEvent event) throws IOException {
/* 81 */             WSAsyncListener.LOGGER.fine("Error processing Request:" + ((HttpServletRequest)event.getAsyncContext().getRequest()).getRequestURL());
/* 82 */             WSAsyncListener.this.con.close();
/*    */           }
/*    */           
/*    */           public void onStartAsync(AsyncEvent event) throws IOException {
/* 86 */             WSAsyncListener.LOGGER.finer("Asynchronous Servlet Invocation started for " + ((HttpServletRequest)event.getAsyncContext().getRequest()).getRequestURL());
/*    */           }
/*    */         });
/*    */   }
/* 90 */   private static final Logger LOGGER = Logger.getLogger(WSAsyncListener.class.getName());
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\WSAsyncListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */