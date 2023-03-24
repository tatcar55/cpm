/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.assembler.ServerPipelineHook;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerPipeCreator
/*     */   extends ServerPipelineHook
/*     */ {
/*     */   public Pipe createSecurityPipe(PolicyMap map, SEIModel sei, WSDLPort port, WSEndpoint owner, Pipe tail) {
/*  72 */     HashMap<Object, Object> props = new HashMap<Object, Object>();
/*     */     
/*  74 */     boolean httpBinding = BindingID.XML_HTTP.equals(owner.getBinding().getBindingId());
/*  75 */     props.put("POLICY", map);
/*  76 */     props.put("SEI_MODEL", sei);
/*  77 */     props.put("WSDL_MODEL", port);
/*  78 */     props.put("ENDPOINT", owner);
/*  79 */     props.put("NEXT_PIPE", tail);
/*  80 */     props.put("CONTAINER", owner.getContainer());
/*  81 */     return (Pipe)new ServerSecurityPipe(props, tail, httpBinding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Tube createSecurityTube(ServerTubelineAssemblyContext context) {
/*  89 */     HashMap<Object, Object> props = new HashMap<Object, Object>();
/*  90 */     boolean httpBinding = BindingID.XML_HTTP.equals(context.getEndpoint().getBinding().getBindingId());
/*  91 */     props.put("POLICY", context.getPolicyMap());
/*  92 */     props.put("SEI_MODEL", context.getSEIModel());
/*  93 */     props.put("WSDL_MODEL", context.getWsdlPort());
/*  94 */     props.put("ENDPOINT", context.getEndpoint());
/*     */     
/*  96 */     props.put("NEXT_TUBE", context.getTubelineHead());
/*  97 */     props.put("CONTAINER", context.getEndpoint().getContainer());
/*     */     
/*  99 */     ServerSecurityTube serverTube = new ServerSecurityTube(props, context.getTubelineHead(), httpBinding);
/* 100 */     return (Tube)serverTube;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\ServerPipeCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */