/*    */ package ec.gob.cnt.ciberpasswordgestor;
/*    */ 
/*    */ import ec.gob.cnt.ciberpasswordgestor.frames.Login2;
/*    */ import ec.gob.cnt.ciberpasswordgestor.frames.SingUp2;
/*    */ import ec.gob.cnt.ciberpasswordgestor.utils.DBConect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ciberpasswordgestor
/*    */ {
/*    */   public static void main(String[] args) {
/* 19 */     System.out.println("Hello World!");
/*    */ 
/*    */ 
/*    */     
/* 23 */     DBConect dBConect = new DBConect();
/*    */ 
/*    */     
/* 26 */     if (!dBConect.existsDB()) {
/*    */ 
/*    */       
/* 29 */       SingUp2 su = new SingUp2();
/* 30 */       su.setVisible(true);
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 35 */       Login2 lo = new Login2();
/* 36 */       lo.setVisible(true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgestor\Ciberpasswordgestor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */