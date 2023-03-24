/*    */ package com.sun.xml.wss.provider.wsit;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.pipe.ClientPipeAssemblerContext;
/*    */ import com.sun.xml.ws.api.pipe.Pipe;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.assembler.dev.ClientPipelineHook;
/*    */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.policy.PolicyMap;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientPipeCreator
/*    */   extends ClientPipelineHook
/*    */ {
/*    */   public Pipe createSecurityPipe(PolicyMap map, ClientPipeAssemblerContext ctxt, Pipe tail) {
/* 67 */     HashMap<Object, Object> propBag = new HashMap<Object, Object>();
/* 68 */     propBag.put("POLICY", map);
/* 69 */     propBag.put("WSDL_MODEL", ctxt.getWsdlModel());
/* 70 */     propBag.put("SERVICE", ctxt.getService());
/* 71 */     propBag.put("BINDING", ctxt.getBinding());
/* 72 */     propBag.put("ENDPOINT_ADDRESS", ctxt.getAddress());
/* 73 */     propBag.put("NEXT_PIPE", tail);
/* 74 */     propBag.put("CONTAINER", ctxt.getContainer());
/* 75 */     propBag.put("ASSEMBLER_CONTEXT", ctxt);
/* 76 */     ClientSecurityPipe ret = new ClientSecurityPipe(propBag, tail);
/* 77 */     return (Pipe)ret;
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Tube createSecurityTube(ClientTubelineAssemblyContext context) {
/* 83 */     HashMap<Object, Object> propBag = new HashMap<Object, Object>();
/* 84 */     propBag.put("POLICY", context.getPolicyMap());
/* 85 */     propBag.put("WSDL_MODEL", context.getWrappedContext().getWsdlModel());
/* 86 */     propBag.put("SERVICE", context.getService());
/* 87 */     propBag.put("BINDING", context.getBinding());
/* 88 */     propBag.put("ENDPOINT_ADDRESS", context.getAddress());
/*    */     
/* 90 */     propBag.put("NEXT_TUBE", context.getTubelineHead());
/* 91 */     propBag.put("CONTAINER", context.getContainer());
/* 92 */     propBag.put("WRAPPED_CONTEXT", context.getWrappedContext());
/* 93 */     propBag.put("ASSEMBLER_CONTEXT", context);
/* 94 */     ClientSecurityTube ret = new ClientSecurityTube(propBag, context.getTubelineHead());
/* 95 */     return (Tube)ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\ClientPipeCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */