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
/*    */ public abstract class PipeCloner
/*    */   extends TubeCloner
/*    */ {
/*    */   public static Pipe clone(Pipe p) {
/* 62 */     return (new PipeClonerImpl()).copy(p);
/*    */   }
/*    */ 
/*    */   
/*    */   PipeCloner(Map<Object, Object> master2copy) {
/* 67 */     super(master2copy);
/*    */   }
/*    */   
/*    */   public abstract <T extends Pipe> T copy(T paramT);
/*    */   
/*    */   public abstract void add(Pipe paramPipe1, Pipe paramPipe2);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\PipeCloner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */