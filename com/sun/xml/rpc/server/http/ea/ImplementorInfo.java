/*    */ package com.sun.xml.rpc.server.http.ea;
/*    */ 
/*    */ import com.sun.xml.rpc.server.Tie;
/*    */ import com.sun.xml.rpc.server.http.Implementor;
/*    */ import com.sun.xml.rpc.spi.runtime.Tie;
/*    */ import java.rmi.Remote;
/*    */ import javax.servlet.ServletContext;
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
/*    */ public class ImplementorInfo
/*    */ {
/*    */   private Class _tieClass;
/*    */   private Class _servantClass;
/*    */   
/*    */   public ImplementorInfo(Class tieClass, Class servantClass) {
/* 44 */     this._tieClass = tieClass;
/* 45 */     this._servantClass = servantClass;
/*    */   }
/*    */   
/*    */   public Class getTieClass() {
/* 49 */     return this._tieClass;
/*    */   }
/*    */   public Class getServantClass() {
/* 52 */     return this._servantClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public Implementor createImplementor(ServletContext context) throws IllegalAccessException, InstantiationException {
/* 57 */     Tie tie = this._tieClass.newInstance();
/* 58 */     Remote servant = this._servantClass.newInstance();
/* 59 */     tie.setTarget(servant);
/* 60 */     return new Implementor(context, (Tie)tie);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\ea\ImplementorInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */