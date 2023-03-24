/*    */ package org.glassfish.external.amx;
/*    */ 
/*    */ import javax.management.ObjectName;
/*    */ import org.glassfish.external.arc.Stability;
/*    */ import org.glassfish.external.arc.Taxonomy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Taxonomy(stability = Stability.UNCOMMITTED)
/*    */ public final class AMXUtil
/*    */ {
/*    */   public static ObjectName newObjectName(String s) {
/*    */     try {
/* 60 */       return new ObjectName(s);
/*    */     }
/* 62 */     catch (Exception e) {
/*    */       
/* 64 */       throw new RuntimeException("bad ObjectName", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ObjectName newObjectName(String domain, String props) {
/* 77 */     return newObjectName(domain + ":" + props);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ObjectName getMBeanServerDelegateObjectName() {
/* 85 */     return newObjectName("JMImplementation:type=MBeanServerDelegate");
/*    */   }
/*    */ 
/*    */   
/*    */   public static String prop(String key, String value) {
/* 90 */     return key + "=" + value;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\amx\AMXUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */