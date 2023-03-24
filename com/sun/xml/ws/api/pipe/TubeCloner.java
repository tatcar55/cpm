/*    */ package com.sun.xml.ws.api.pipe;
/*    */ 
/*    */ import java.util.Map;
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
/*    */ public abstract class TubeCloner
/*    */ {
/*    */   public final Map<Object, Object> master2copy;
/*    */   
/*    */   public static Tube clone(Tube p) {
/* 75 */     return (new PipeClonerImpl()).copy(p);
/*    */   }
/*    */ 
/*    */   
/*    */   TubeCloner(Map<Object, Object> master2copy) {
/* 80 */     this.master2copy = master2copy;
/*    */   }
/*    */   
/*    */   public abstract <T extends Tube> T copy(T paramT);
/*    */   
/*    */   public abstract void add(Tube paramTube1, Tube paramTube2);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\TubeCloner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */