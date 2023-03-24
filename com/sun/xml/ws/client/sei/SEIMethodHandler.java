/*     */ package com.sun.xml.ws.client.sei;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.ws.model.ParameterImpl;
/*     */ import com.sun.xml.ws.model.WrapperParameter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
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
/*     */ abstract class SEIMethodHandler
/*     */   extends MethodHandler
/*     */ {
/*     */   private BodyBuilder bodyBuilder;
/*     */   private MessageFiller[] inFillers;
/*     */   protected String soapAction;
/*     */   protected boolean isOneWay;
/*     */   protected JavaMethodImpl javaMethod;
/*     */   protected Map<QName, CheckedExceptionImpl> checkedExceptions;
/*     */   
/*     */   SEIMethodHandler(SEIStub owner) {
/*  91 */     super(owner, null);
/*     */   }
/*     */   
/*     */   SEIMethodHandler(SEIStub owner, JavaMethodImpl method) {
/*  95 */     super(owner, null);
/*     */ 
/*     */     
/*  98 */     this.checkedExceptions = new HashMap<QName, CheckedExceptionImpl>();
/*  99 */     for (CheckedExceptionImpl ce : method.getCheckedExceptions()) {
/* 100 */       this.checkedExceptions.put((ce.getBond().getTypeInfo()).tagName, ce);
/*     */     }
/*     */     
/* 103 */     if (method.getInputAction() != null && !method.getBinding().getSOAPAction().equals("")) {
/* 104 */       this.soapAction = method.getInputAction();
/*     */     } else {
/* 106 */       this.soapAction = method.getBinding().getSOAPAction();
/*     */     } 
/* 108 */     this.javaMethod = method;
/*     */ 
/*     */     
/* 111 */     List<ParameterImpl> rp = method.getRequestParameters();
/*     */     
/* 113 */     BodyBuilder tmpBodyBuilder = null;
/* 114 */     List<MessageFiller> fillers = new ArrayList<MessageFiller>();
/*     */     
/* 116 */     for (ParameterImpl param : rp) {
/* 117 */       ValueGetter getter = getValueGetterFactory().get(param);
/*     */       
/* 119 */       switch ((param.getInBinding()).kind) {
/*     */         case SOAP_11:
/* 121 */           if (param.isWrapperStyle()) {
/* 122 */             if (param.getParent().getBinding().isRpcLit()) {
/* 123 */               tmpBodyBuilder = new BodyBuilder.RpcLit((WrapperParameter)param, owner.soapVersion, getValueGetterFactory()); continue;
/*     */             } 
/* 125 */             tmpBodyBuilder = new BodyBuilder.DocLit((WrapperParameter)param, owner.soapVersion, getValueGetterFactory()); continue;
/*     */           } 
/* 127 */           tmpBodyBuilder = new BodyBuilder.Bare(param, owner.soapVersion, getter);
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 131 */           fillers.add(new MessageFiller.Header(param.getIndex(), param.getXMLBridge(), getter));
/*     */           continue;
/*     */ 
/*     */ 
/*     */         
/*     */         case null:
/* 137 */           fillers.add(MessageFiller.AttachmentFiller.createAttachmentFiller(param, getter));
/*     */           continue;
/*     */         case null:
/*     */           continue;
/*     */       } 
/* 142 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 146 */     if (tmpBodyBuilder == null)
/*     */     {
/* 148 */       switch (owner.soapVersion) {
/*     */         case SOAP_11:
/* 150 */           tmpBodyBuilder = BodyBuilder.EMPTY_SOAP11;
/*     */           break;
/*     */         case SOAP_12:
/* 153 */           tmpBodyBuilder = BodyBuilder.EMPTY_SOAP12;
/*     */           break;
/*     */         default:
/* 156 */           throw new AssertionError();
/*     */       } 
/*     */     
/*     */     }
/* 160 */     this.bodyBuilder = tmpBodyBuilder;
/* 161 */     this.inFillers = fillers.<MessageFiller>toArray(new MessageFiller[fillers.size()]);
/*     */ 
/*     */     
/* 164 */     this.isOneWay = method.getMEP().isOneWay();
/*     */   }
/*     */ 
/*     */   
/*     */   ResponseBuilder buildResponseBuilder(JavaMethodImpl method, ValueSetterFactory setterFactory) {
/* 169 */     List<ParameterImpl> rp = method.getResponseParameters();
/* 170 */     List<ResponseBuilder> builders = new ArrayList<ResponseBuilder>();
/*     */     
/* 172 */     for (ParameterImpl param : rp) {
/*     */       ValueSetter setter;
/* 174 */       switch ((param.getOutBinding()).kind) {
/*     */         case SOAP_11:
/* 176 */           if (param.isWrapperStyle()) {
/* 177 */             if (param.getParent().getBinding().isRpcLit()) {
/* 178 */               builders.add(new ResponseBuilder.RpcLit((WrapperParameter)param, setterFactory)); continue;
/*     */             } 
/* 180 */             builders.add(new ResponseBuilder.DocLit((WrapperParameter)param, setterFactory)); continue;
/*     */           } 
/* 182 */           setter = setterFactory.get(param);
/* 183 */           builders.add(new ResponseBuilder.Body(param.getXMLBridge(), setter));
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 187 */           setter = setterFactory.get(param);
/* 188 */           builders.add(new ResponseBuilder.Header(this.owner.soapVersion, param, setter));
/*     */           continue;
/*     */         case null:
/* 191 */           setter = setterFactory.get(param);
/* 192 */           builders.add(ResponseBuilder.AttachmentBuilder.createAttachmentBuilder(param, setter));
/*     */           continue;
/*     */         case null:
/* 195 */           setter = setterFactory.get(param);
/* 196 */           builders.add(new ResponseBuilder.NullSetter(setter, ResponseBuilder.getVMUninitializedValue((param.getTypeInfo()).type)));
/*     */           continue;
/*     */       } 
/*     */       
/* 200 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 204 */     switch (builders.size())
/*     */     { case 0:
/* 206 */         rb = ResponseBuilder.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 214 */         return rb;case 1: rb = builders.get(0); return rb; }  ResponseBuilder rb = new ResponseBuilder.Composite(builders); return rb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Message createRequestMessage(Object[] args) {
/* 224 */     Message msg = this.bodyBuilder.createMessage(args);
/*     */     
/* 226 */     for (MessageFiller filler : this.inFillers) {
/* 227 */       filler.fillIn(args, msg);
/*     */     }
/* 229 */     return msg;
/*     */   }
/*     */   
/*     */   abstract ValueGetterFactory getValueGetterFactory();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\SEIMethodHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */