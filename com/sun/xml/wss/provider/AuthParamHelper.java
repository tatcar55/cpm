/*    */ package com.sun.xml.wss.provider;
/*    */ 
/*    */ import com.sun.enterprise.security.jauth.AuthParam;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import javax.xml.soap.SOAPMessage;
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
/*    */ class AuthParamHelper
/*    */ {
/*    */   static SOAPMessage getRequest(AuthParam param) {
/*    */     
/* 59 */     try { Class<?> clz = param.getClass();
/* 60 */       Method meth = clz.getMethod("getRequest", (Class[])null);
/* 61 */       if (meth != null) {
/* 62 */         Object obj = meth.invoke(param, (Object[])null);
/* 63 */         if (obj instanceof SOAPMessage) {
/* 64 */           return (SOAPMessage)obj;
/*    */         }
/*    */       }
/*    */        }
/* 68 */     catch (NoSuchMethodException ex) {  }
/* 69 */     catch (SecurityException ex) {  }
/* 70 */     catch (IllegalAccessException ex) {  }
/* 71 */     catch (IllegalArgumentException ex) {  }
/* 72 */     catch (InvocationTargetException ex) {}
/*    */     
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   static SOAPMessage getResponse(AuthParam param) {
/*    */     
/* 80 */     try { Class<?> clz = param.getClass();
/* 81 */       Method meth = clz.getMethod("getResponse", (Class[])null);
/* 82 */       if (meth != null) {
/* 83 */         Object obj = meth.invoke(param, (Object[])null);
/* 84 */         if (obj instanceof SOAPMessage) {
/* 85 */           return (SOAPMessage)obj;
/*    */         }
/*    */       }
/*    */        }
/* 89 */     catch (NoSuchMethodException ex) {  }
/* 90 */     catch (SecurityException ex) {  }
/* 91 */     catch (IllegalAccessException ex) {  }
/* 92 */     catch (IllegalArgumentException ex) {  }
/* 93 */     catch (InvocationTargetException ex) {}
/*    */     
/* 95 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\AuthParamHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */