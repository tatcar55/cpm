/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.spi.tools.HandlerInfo;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class HandlerInfo
/*    */   implements HandlerInfo
/*    */ {
/*    */   private String handlerClassName;
/* 42 */   private Map properties = new HashMap<Object, Object>();
/* 43 */   private Set headerNames = new HashSet();
/*    */ 
/*    */   
/*    */   public String getHandlerClassName() {
/* 47 */     return this.handlerClassName;
/*    */   }
/*    */   
/*    */   public void setHandlerClassName(String s) {
/* 51 */     this.handlerClassName = s;
/*    */   }
/*    */   
/*    */   public Map getProperties() {
/* 55 */     return this.properties;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProperties(Map m) {
/* 60 */     this.properties = m;
/*    */   }
/*    */   
/*    */   public void addHeaderName(QName name) {
/* 64 */     this.headerNames.add(name);
/*    */   }
/*    */   
/*    */   public Set getHeaderNames() {
/* 68 */     return this.headerNames;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHeaderNames(Set s) {
/* 73 */     this.headerNames = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\HandlerInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */