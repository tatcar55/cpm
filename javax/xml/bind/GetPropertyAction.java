/*    */ package javax.xml.bind;
/*    */ 
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class GetPropertyAction
/*    */   implements PrivilegedAction<String>
/*    */ {
/*    */   private final String propertyName;
/*    */   
/*    */   public GetPropertyAction(String propertyName) {
/* 53 */     this.propertyName = propertyName;
/*    */   }
/*    */   
/*    */   public String run() {
/* 57 */     return System.getProperty(this.propertyName);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\GetPropertyAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */