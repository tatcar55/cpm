/*    */ package com.sun.xml.wss.impl.callback;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import javax.security.auth.callback.Callback;
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
/*    */ public class RuntimeProperties
/*    */   implements Callback
/*    */ {
/* 54 */   private Map<Object, Object> runtimeProps = null;
/*    */   
/*    */   public RuntimeProperties(Map<?, ?> props) {
/* 57 */     this.runtimeProps = Collections.unmodifiableMap(props);
/*    */   }
/*    */   
/*    */   public Map getRuntimeProperties() {
/* 61 */     return this.runtimeProps;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\RuntimeProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */