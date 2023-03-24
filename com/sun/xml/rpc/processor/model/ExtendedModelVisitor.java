/*     */ package com.sun.xml.rpc.processor.model;
/*     */ 
/*     */ import java.util.Iterator;
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
/*     */ public class ExtendedModelVisitor
/*     */ {
/*     */   public void visit(Model model) throws Exception {
/*  42 */     preVisit(model);
/*  43 */     for (Iterator<Service> iter = model.getServices(); iter.hasNext(); ) {
/*  44 */       Service service = iter.next();
/*  45 */       preVisit(service);
/*  46 */       for (Iterator<Port> iter2 = service.getPorts(); iter2.hasNext(); ) {
/*  47 */         Port port = iter2.next();
/*  48 */         preVisit(port);
/*  49 */         if (shouldVisit(port)) {
/*  50 */           Iterator<Operation> iter3 = port.getOperations();
/*  51 */           while (iter3.hasNext()) {
/*     */             
/*  53 */             Operation operation = iter3.next();
/*  54 */             preVisit(operation);
/*  55 */             Request request = operation.getRequest();
/*  56 */             if (request != null) {
/*  57 */               preVisit(request);
/*  58 */               Iterator<Block> iterator2 = request.getHeaderBlocks();
/*  59 */               while (iterator2.hasNext()) {
/*     */                 
/*  61 */                 Block block = iterator2.next();
/*  62 */                 visitHeaderBlock(block);
/*     */               } 
/*  64 */               Iterator<Block> iterator1 = request.getBodyBlocks();
/*  65 */               while (iterator1.hasNext()) {
/*     */                 
/*  67 */                 Block block = iterator1.next();
/*  68 */                 visitBodyBlock(block);
/*     */               } 
/*  70 */               Iterator<Parameter> iterator = request.getParameters();
/*  71 */               while (iterator.hasNext()) {
/*     */                 
/*  73 */                 Parameter parameter = iterator.next();
/*  74 */                 visit(parameter);
/*     */               } 
/*  76 */               postVisit(request);
/*     */             } 
/*     */             
/*  79 */             Response response = operation.getResponse();
/*  80 */             if (response != null) {
/*  81 */               preVisit(response);
/*  82 */               Iterator<Block> iterator2 = response.getHeaderBlocks();
/*  83 */               while (iterator2.hasNext()) {
/*     */                 
/*  85 */                 Block block = iterator2.next();
/*  86 */                 visitHeaderBlock(block);
/*     */               } 
/*  88 */               Iterator<Block> iterator1 = response.getBodyBlocks();
/*  89 */               while (iterator1.hasNext()) {
/*     */                 
/*  91 */                 Block block = iterator1.next();
/*  92 */                 visitBodyBlock(block);
/*     */               } 
/*  94 */               Iterator<Parameter> iterator = response.getParameters();
/*  95 */               while (iterator.hasNext()) {
/*     */                 
/*  97 */                 Parameter parameter = iterator.next();
/*  98 */                 visit(parameter);
/*     */               } 
/* 100 */               postVisit(response);
/*     */             } 
/*     */             
/* 103 */             Iterator<Fault> iter4 = operation.getFaults();
/* 104 */             while (iter4.hasNext()) {
/*     */               
/* 106 */               Fault fault = iter4.next();
/* 107 */               preVisit(fault);
/* 108 */               visitFaultBlock(fault.getBlock());
/* 109 */               postVisit(fault);
/*     */             } 
/* 111 */             postVisit(operation);
/*     */           } 
/*     */         } 
/* 114 */         postVisit(port);
/*     */       } 
/* 116 */       postVisit(service);
/*     */     } 
/* 118 */     postVisit(model);
/*     */   }
/*     */   
/*     */   protected boolean shouldVisit(Port port) {
/* 122 */     return true;
/*     */   }
/*     */   
/*     */   protected void preVisit(Model model) throws Exception {}
/*     */   
/*     */   protected void postVisit(Model model) throws Exception {}
/*     */   
/*     */   protected void preVisit(Service service) throws Exception {}
/*     */   
/*     */   protected void postVisit(Service service) throws Exception {}
/*     */   
/*     */   protected void preVisit(Port port) throws Exception {}
/*     */   
/*     */   protected void postVisit(Port port) throws Exception {}
/*     */   
/*     */   protected void preVisit(Operation operation) throws Exception {}
/*     */   
/*     */   protected void postVisit(Operation operation) throws Exception {}
/*     */   
/*     */   protected void preVisit(Request request) throws Exception {}
/*     */   
/*     */   protected void postVisit(Request request) throws Exception {}
/*     */   
/*     */   protected void preVisit(Response response) throws Exception {}
/*     */   
/*     */   protected void postVisit(Response response) throws Exception {}
/*     */   
/*     */   protected void preVisit(Fault fault) throws Exception {}
/*     */   
/*     */   protected void postVisit(Fault fault) throws Exception {}
/*     */   
/*     */   protected void visitBodyBlock(Block block) throws Exception {}
/*     */   
/*     */   protected void visitHeaderBlock(Block block) throws Exception {}
/*     */   
/*     */   protected void visitFaultBlock(Block block) throws Exception {}
/*     */   
/*     */   protected void visit(Parameter parameter) throws Exception {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\ExtendedModelVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */