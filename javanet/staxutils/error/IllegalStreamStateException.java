/*    */ package javanet.staxutils.error;
/*    */ 
/*    */ import javax.xml.stream.Location;
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
/*    */ public class IllegalStreamStateException
/*    */   extends IllegalStateException
/*    */ {
/*    */   private Location location;
/*    */   
/*    */   public IllegalStreamStateException() {}
/*    */   
/*    */   public IllegalStreamStateException(Location location) {
/* 26 */     this.location = location;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IllegalStreamStateException(String s) {
/* 32 */     super(s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IllegalStreamStateException(String s, Location location) {
/* 38 */     super(s);
/* 39 */     this.location = location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Location getLocation() {
/* 50 */     return this.location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLocation(Location location) {
/* 61 */     this.location = location;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\error\IllegalStreamStateException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */