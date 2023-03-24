/*    */ package ec.gob.cnt.ciberpasswordgestor.model;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Cuenta
/*    */ {
/*    */   int id;
/*    */   String cuenta;
/*    */   String user;
/*    */   String password;
/*    */   
/*    */   public Cuenta(int id, String cuenta, String user, String password) {
/* 18 */     this.id = id;
/* 19 */     this.cuenta = cuenta;
/* 20 */     this.user = user;
/* 21 */     this.password = password;
/*    */   }
/*    */   
/*    */   public Cuenta(String user, String password) {
/* 25 */     this.user = user;
/* 26 */     this.password = password;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUser() {
/* 32 */     return this.user;
/*    */   }
/*    */   
/*    */   public void setUser(String user) {
/* 36 */     this.user = user;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 40 */     return this.password;
/*    */   }
/*    */   
/*    */   public void setPassword(String password) {
/* 44 */     this.password = password;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 48 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 52 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getCuenta() {
/* 56 */     return this.cuenta;
/*    */   }
/*    */   
/*    */   public void setCuenta(String cuenta) {
/* 60 */     this.cuenta = cuenta;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgestor\model\Cuenta.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */