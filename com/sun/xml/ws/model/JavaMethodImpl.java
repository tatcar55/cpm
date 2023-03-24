/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.MEP;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.soap.SOAPBinding;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.model.soap.SOAPBindingImpl;
/*     */ import com.sun.xml.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.ws.wsdl.ActionBasedOperationSignature;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.WebMethod;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Action;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public final class JavaMethodImpl
/*     */   implements JavaMethod
/*     */ {
/*  75 */   private String inputAction = "";
/*  76 */   private String outputAction = "";
/*  77 */   private final List<CheckedExceptionImpl> exceptions = new ArrayList<CheckedExceptionImpl>();
/*     */   private final Method method;
/*  79 */   final List<ParameterImpl> requestParams = new ArrayList<ParameterImpl>();
/*  80 */   final List<ParameterImpl> responseParams = new ArrayList<ParameterImpl>();
/*  81 */   private final List<ParameterImpl> unmReqParams = Collections.unmodifiableList(this.requestParams);
/*  82 */   private final List<ParameterImpl> unmResParams = Collections.unmodifiableList(this.responseParams);
/*     */   
/*     */   private SOAPBinding binding;
/*     */   
/*     */   private MEP mep;
/*     */   
/*     */   private QName operationName;
/*     */   
/*     */   private WSDLBoundOperation wsdlOperation;
/*     */   
/*     */   final AbstractSEIModelImpl owner;
/*     */   
/*     */   private final Method seiMethod;
/*     */   private QName requestPayloadName;
/*     */   private String soapAction;
/*     */   
/*     */   public JavaMethodImpl(AbstractSEIModelImpl owner, Method method, Method seiMethod, MetadataReader metadataReader) {
/*  99 */     this.owner = owner;
/* 100 */     this.method = method;
/* 101 */     this.seiMethod = seiMethod;
/* 102 */     setWsaActions(metadataReader);
/*     */   }
/*     */   
/*     */   private void setWsaActions(MetadataReader metadataReader) {
/* 106 */     Action action = (metadataReader != null) ? (Action)metadataReader.getAnnotation(Action.class, this.seiMethod) : this.seiMethod.<Action>getAnnotation(Action.class);
/* 107 */     if (action != null) {
/* 108 */       this.inputAction = action.input();
/* 109 */       this.outputAction = action.output();
/*     */     } 
/*     */ 
/*     */     
/* 113 */     WebMethod webMethod = (metadataReader != null) ? (WebMethod)metadataReader.getAnnotation(WebMethod.class, this.seiMethod) : this.seiMethod.<WebMethod>getAnnotation(WebMethod.class);
/* 114 */     this.soapAction = "";
/* 115 */     if (webMethod != null)
/* 116 */       this.soapAction = webMethod.action(); 
/* 117 */     if (!this.soapAction.equals(""))
/*     */     {
/* 119 */       if (this.inputAction.equals("")) {
/*     */         
/* 121 */         this.inputAction = this.soapAction;
/* 122 */       } else if (!this.inputAction.equals(this.soapAction)) {
/*     */         
/* 124 */         throw new WebServiceException("@Action and @WebMethod(action=\"\" does not match on operation " + this.method.getName());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public ActionBasedOperationSignature getOperationSignature() {
/* 130 */     QName qname = getRequestPayloadName();
/* 131 */     if (qname == null) qname = new QName("", ""); 
/* 132 */     return new ActionBasedOperationSignature(getInputAction(), qname);
/*     */   }
/*     */   
/*     */   public SEIModel getOwner() {
/* 136 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method getMethod() {
/* 145 */     return this.method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method getSEIMethod() {
/* 154 */     return this.seiMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MEP getMEP() {
/* 161 */     return this.mep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setMEP(MEP mep) {
/* 169 */     this.mep = mep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPBinding getBinding() {
/* 176 */     if (this.binding == null)
/* 177 */       return (SOAPBinding)new SOAPBindingImpl(); 
/* 178 */     return this.binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setBinding(SOAPBinding binding) {
/* 185 */     this.binding = binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLBoundOperation getOperation() {
/* 196 */     return this.wsdlOperation;
/*     */   }
/*     */   
/*     */   public void setOperationQName(QName name) {
/* 200 */     this.operationName = name;
/*     */   }
/*     */   
/*     */   public QName getOperationQName() {
/* 204 */     return (this.wsdlOperation != null) ? this.wsdlOperation.getName() : this.operationName;
/*     */   }
/*     */   
/*     */   public String getSOAPAction() {
/* 208 */     return (this.wsdlOperation != null) ? this.wsdlOperation.getSOAPAction() : this.soapAction;
/*     */   }
/*     */   
/*     */   public String getOperationName() {
/* 212 */     return this.operationName.getLocalPart();
/*     */   }
/*     */   
/*     */   public String getRequestMessageName() {
/* 216 */     return getOperationName();
/*     */   }
/*     */   
/*     */   public String getResponseMessageName() {
/* 220 */     if (this.mep.isOneWay())
/* 221 */       return null; 
/* 222 */     return getOperationName() + "Response";
/*     */   }
/*     */   
/*     */   public void setRequestPayloadName(QName n) {
/* 226 */     this.requestPayloadName = n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public QName getRequestPayloadName() {
/* 233 */     return (this.wsdlOperation != null) ? this.wsdlOperation.getReqPayloadName() : this.requestPayloadName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public QName getResponsePayloadName() {
/* 240 */     return (this.mep == MEP.ONE_WAY) ? null : this.wsdlOperation.getResPayloadName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ParameterImpl> getRequestParameters() {
/* 247 */     return this.unmReqParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ParameterImpl> getResponseParameters() {
/* 254 */     return this.unmResParams;
/*     */   }
/*     */   
/*     */   void addParameter(ParameterImpl p) {
/* 258 */     if (p.isIN() || p.isINOUT()) {
/* 259 */       assert !this.requestParams.contains(p);
/* 260 */       this.requestParams.add(p);
/*     */     } 
/*     */     
/* 263 */     if (p.isOUT() || p.isINOUT()) {
/*     */       
/* 265 */       assert !this.responseParams.contains(p);
/* 266 */       this.responseParams.add(p);
/*     */     } 
/*     */   }
/*     */   
/*     */   void addRequestParameter(ParameterImpl p) {
/* 271 */     if (p.isIN() || p.isINOUT()) {
/* 272 */       this.requestParams.add(p);
/*     */     }
/*     */   }
/*     */   
/*     */   void addResponseParameter(ParameterImpl p) {
/* 277 */     if (p.isOUT() || p.isINOUT()) {
/* 278 */       this.responseParams.add(p);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInputParametersCount() {
/* 289 */     int count = 0;
/* 290 */     for (ParameterImpl param : this.requestParams) {
/* 291 */       if (param.isWrapperStyle()) {
/* 292 */         count += ((WrapperParameter)param).getWrapperChildren().size(); continue;
/*     */       } 
/* 294 */       count++;
/*     */     } 
/*     */ 
/*     */     
/* 298 */     for (ParameterImpl param : this.responseParams) {
/* 299 */       if (param.isWrapperStyle()) {
/* 300 */         for (ParameterImpl wc : ((WrapperParameter)param).getWrapperChildren()) {
/* 301 */           if (!wc.isResponse() && wc.isOUT())
/* 302 */             count++; 
/*     */         }  continue;
/*     */       } 
/* 305 */       if (!param.isResponse() && param.isOUT()) {
/* 306 */         count++;
/*     */       }
/*     */     } 
/*     */     
/* 310 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addException(CheckedExceptionImpl ce) {
/* 317 */     if (!this.exceptions.contains(ce)) {
/* 318 */       this.exceptions.add(ce);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckedExceptionImpl getCheckedException(Class exceptionClass) {
/* 327 */     for (CheckedExceptionImpl ce : this.exceptions) {
/* 328 */       if (ce.getExceptionClass() == exceptionClass)
/* 329 */         return ce; 
/*     */     } 
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CheckedExceptionImpl> getCheckedExceptions() {
/* 339 */     return Collections.unmodifiableList(this.exceptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInputAction() {
/* 344 */     return this.inputAction;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOutputAction() {
/* 349 */     return this.outputAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckedExceptionImpl getCheckedException(TypeReference detailType) {
/* 359 */     for (CheckedExceptionImpl ce : this.exceptions) {
/* 360 */       TypeInfo actual = ce.getDetailType();
/* 361 */       if (actual.tagName.equals(detailType.tagName) && actual.type == detailType.type) {
/* 362 */         return ce;
/*     */       }
/*     */     } 
/* 365 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAsync() {
/* 375 */     return this.mep.isAsync;
/*     */   }
/*     */   
/*     */   void freeze(WSDLPort portType) {
/* 379 */     this.wsdlOperation = portType.getBinding().get(new QName(portType.getBinding().getPortType().getName().getNamespaceURI(), getOperationName()));
/*     */     
/* 381 */     if (this.wsdlOperation == null) {
/* 382 */       throw new WebServiceException("Method " + this.seiMethod.getName() + " is exposed as WebMethod, but there is no corresponding wsdl operation with name " + this.operationName + " in the wsdl:portType" + portType.getBinding().getPortType().getName());
/*     */     }
/*     */ 
/*     */     
/* 386 */     if (this.inputAction.equals("")) {
/* 387 */       this.inputAction = this.wsdlOperation.getOperation().getInput().getAction();
/* 388 */     } else if (!this.inputAction.equals(this.wsdlOperation.getOperation().getInput().getAction())) {
/*     */       
/* 390 */       LOGGER.warning("Input Action on WSDL operation " + this.wsdlOperation.getName().getLocalPart() + " and @Action on its associated Web Method " + this.seiMethod.getName() + " did not match and will cause problems in dispatching the requests");
/*     */     } 
/* 392 */     if (!this.mep.isOneWay()) {
/* 393 */       if (this.outputAction.equals("")) {
/* 394 */         this.outputAction = this.wsdlOperation.getOperation().getOutput().getAction();
/*     */       }
/* 396 */       for (CheckedExceptionImpl ce : this.exceptions) {
/* 397 */         if (ce.getFaultAction().equals("")) {
/* 398 */           QName detailQName = (ce.getDetailType()).tagName;
/* 399 */           WSDLFault wsdlfault = this.wsdlOperation.getOperation().getFault(detailQName);
/* 400 */           if (wsdlfault == null) {
/*     */             
/* 402 */             LOGGER.warning("Mismatch between Java model and WSDL model found, For wsdl operation " + this.wsdlOperation.getName() + ",There is no matching wsdl fault with detail QName " + (ce.getDetailType()).tagName);
/*     */ 
/*     */             
/* 405 */             ce.setFaultAction(ce.getDefaultFaultAction()); continue;
/*     */           } 
/* 407 */           ce.setFaultAction(wsdlfault.getAction());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   final void fillTypes(List<TypeInfo> types) {
/* 415 */     fillTypes(this.requestParams, types);
/* 416 */     fillTypes(this.responseParams, types);
/*     */     
/* 418 */     for (CheckedExceptionImpl ce : this.exceptions) {
/* 419 */       types.add(ce.getDetailType());
/*     */     }
/*     */   }
/*     */   
/*     */   private void fillTypes(List<ParameterImpl> params, List<TypeInfo> types) {
/* 424 */     for (ParameterImpl p : params) {
/* 425 */       p.fillTypes(types);
/*     */     }
/*     */   }
/*     */   
/* 429 */   private static final Logger LOGGER = Logger.getLogger(JavaMethodImpl.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\JavaMethodImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */