/*    */ package com.sun.xml.rpc.client;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.xml.rpc.handler.HandlerInfo;
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
/*    */ public class HandlerChainInfoImpl
/*    */   extends ArrayList
/*    */ {
/*    */   private List handlers;
/*    */   private String[] roles;
/*    */   
/*    */   public HandlerChainInfoImpl() {
/* 41 */     this.handlers = new ArrayList();
/* 42 */     this.roles = null;
/*    */   }
/*    */   
/*    */   public HandlerChainInfoImpl(List list) {
/* 46 */     this.handlers = list;
/* 47 */     this.roles = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public List getHandlerList() {
/* 52 */     return this.handlers;
/*    */   }
/*    */   
/*    */   public Iterator getHandlers() {
/* 56 */     return this.handlers.iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public void addHandler(HandlerInfo info) {
/* 61 */     this.handlers.add(info);
/*    */   }
/*    */   
/*    */   public String[] getRoles() {
/* 65 */     return this.roles;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRoles(String[] roles) {
/* 70 */     this.roles = roles;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\HandlerChainInfoImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */