/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.spi.tools.HandlerChainInfo;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
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
/*    */ public class HandlerChainInfo
/*    */   implements HandlerChainInfo
/*    */ {
/* 43 */   private List handlers = new ArrayList();
/* 44 */   private Set roles = new HashSet();
/*    */ 
/*    */   
/*    */   public void add(HandlerInfo i) {
/* 48 */     this.handlers.add(i);
/*    */   }
/*    */   
/*    */   public Iterator getHandlers() {
/* 52 */     return this.handlers.iterator();
/*    */   }
/*    */   
/*    */   public int getHandlersCount() {
/* 56 */     return this.handlers.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public List getHandlersList() {
/* 61 */     return this.handlers;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHandlersList(List l) {
/* 66 */     this.handlers = l;
/*    */   }
/*    */   
/*    */   public void addRole(String s) {
/* 70 */     this.roles.add(s);
/*    */   }
/*    */   
/*    */   public Set getRoles() {
/* 74 */     return this.roles;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRoles(Set s) {
/* 79 */     this.roles = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\HandlerChainInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */