/*    */ package com.sun.xml.ws.assembler.jaxws;
/*    */ 
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.assembler.dev.TubeFactory;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MustUnderstandTubeFactory
/*    */   implements TubeFactory
/*    */ {
/*    */   public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
/* 58 */     return context.getWrappedContext().createClientMUTube(context.getTubelineHead());
/*    */   }
/*    */   
/*    */   public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
/* 62 */     return context.getWrappedContext().createServerMUTube(context.getTubelineHead());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\jaxws\MustUnderstandTubeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */