/*     */ package com.sun.xml.rpc.client.dii;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.util.Holders;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.ParameterMode;
/*     */ import javax.xml.rpc.handler.HandlerRegistry;
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
/*     */ public class ConfiguredCall
/*     */   extends BasicCall
/*     */ {
/*     */   ServiceInfo configuration;
/*  45 */   Method operationMethod = null;
/*  46 */   String methodName = null;
/*  47 */   QName portTypeName = EMPTY_QNAME;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConfiguredCall(InternalTypeMappingRegistry registry, HandlerRegistry handlerRegistry, ServiceInfo configuration) {
/*  53 */     super(registry, handlerRegistry);
/*  54 */     if (configuration == null) {
/*  55 */       throw new IllegalArgumentException("configuration not allowed to be null");
/*     */     }
/*     */     
/*  58 */     this.configuration = configuration;
/*     */   }
/*     */   
/*     */   public boolean isParameterAndReturnSpecRequired(QName operation) {
/*  62 */     return false;
/*     */   }
/*     */   
/*     */   public void setMethodName(String methodName) {
/*  66 */     this.methodName = methodName;
/*  67 */     configureCall();
/*     */   }
/*     */   
/*     */   public void setOperationName(QName operationName) {
/*  71 */     super.setOperationName(operationName);
/*  72 */     this.methodName = operationName.getLocalPart();
/*  73 */     configureCall();
/*     */   }
/*     */   
/*     */   public void setPortName(QName port) {
/*  77 */     super.setPortName(port);
/*  78 */     configureCall();
/*     */   }
/*     */   
/*     */   public void setOperationMethod(Method method) {
/*  82 */     this.operationMethod = method;
/*     */   }
/*     */   
/*     */   protected void configureCall() {
/*  86 */     configureCall(this.operationMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configureCall(Method method) {
/*  92 */     if (readyToConfigure()) {
/*  93 */       PortInfo currentPort = this.configuration.getPortInfo(getPortName());
/*     */       
/*  95 */       setPortTypeName(currentPort.getPortTypeName());
/*     */       
/*  97 */       MethodInfo currentMethod = new MethodInfo(method);
/*     */       
/*  99 */       Iterator<OperationInfo> eachOperation = currentPort.getOperations();
/* 100 */       boolean operationHasBeenFound = false;
/* 101 */       while (eachOperation.hasNext() && !operationHasBeenFound) {
/* 102 */         OperationInfo currentOperation = eachOperation.next();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 107 */         if (currentMethod.matches(this.methodName, currentOperation)) {
/* 108 */           operationHasBeenFound = true;
/*     */           
/* 110 */           doConfigureCall(currentMethod, currentOperation);
/*     */         } 
/*     */       } 
/*     */       
/* 114 */       if (!operationHasBeenFound) {
/* 115 */         throw new DynamicInvocationException("dii.port.does.not.contain.operation", new Object[] { getPortName(), this.methodName });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConfigureCall(MethodInfo currentMethod, OperationInfo currentOperation) {
/* 125 */     PortInfo currentPort = this.configuration.getPortInfo(getPortName());
/*     */ 
/*     */     
/* 128 */     super.setOperationName(currentOperation.getName());
/* 129 */     setOperationInfo(currentOperation);
/* 130 */     this.isOneWay = currentOperation.isOneWay();
/*     */ 
/*     */     
/* 133 */     setTargetEndpointAddress(currentPort.getTargetEndpoint());
/*     */ 
/*     */     
/* 136 */     String[] parameterNames = currentOperation.getParameterNames();
/*     */     
/* 138 */     QName[] parameterTypes = currentOperation.getParameterXmlTypes();
/*     */     
/* 140 */     Class[] parameterClasses = currentMethod.getParameterTypes(parameterTypes.length);
/*     */ 
/*     */     
/* 143 */     ParameterMode[] parameterModes = currentOperation.getParameterModes();
/*     */     
/* 145 */     QName[] parameterXmlTypeQNames = currentOperation.getParameterXmlTypeQNames();
/*     */ 
/*     */     
/* 148 */     for (int i = 0; i < parameterNames.length; i++) {
/* 149 */       String parameterName = parameterNames[i];
/* 150 */       QName parameterType = (parameterTypes != null) ? parameterTypes[i] : null;
/*     */       
/* 152 */       Class parameterClass = Holders.stripHolderClass(parameterClasses[i]);
/*     */       
/* 154 */       ParameterMode mode = parameterModes[i];
/* 155 */       QName parameterXmlTypeQName = null;
/* 156 */       if (i < parameterXmlTypeQNames.length)
/* 157 */         parameterXmlTypeQName = parameterXmlTypeQNames[i]; 
/* 158 */       ParameterMemberInfo[] members = null;
/* 159 */       members = currentOperation.getMemberInfo(i);
/*     */ 
/*     */       
/* 162 */       doAddParameter(parameterName, parameterType, parameterXmlTypeQName, parameterClass, members, mode);
/*     */     } 
/*     */     
/* 165 */     Class javaReturn = null;
/*     */     
/* 167 */     if (currentMethod != null) {
/* 168 */       javaReturn = currentMethod.getReturnType();
/* 169 */       if (javaReturn != null && 
/* 170 */         javaReturn.getName().equalsIgnoreCase("void")) {
/* 171 */         javaReturn = null;
/*     */       }
/*     */     } 
/* 174 */     if (javaReturn == null)
/* 175 */       javaReturn = currentOperation.getReturnClass(); 
/* 176 */     doSetReturnType(currentOperation.getReturnXmlType(), javaReturn);
/*     */     
/* 178 */     setReturnXmlTypeQName(currentOperation.getReturnXmlTypeQName());
/* 179 */     setReturnTypeName(currentOperation.getReturnClassName());
/* 180 */     setReturnParameterInfos(currentOperation.getReturnMembers());
/* 181 */     Iterator<String> eachPropertyKey = currentOperation.getPropertyKeys();
/* 182 */     while (eachPropertyKey.hasNext()) {
/* 183 */       String currentKey = eachPropertyKey.next();
/* 184 */       setProperty(currentKey, currentOperation.getProperty(currentKey));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean readyToConfigure() {
/* 189 */     return (getPortName() != null && !getPortName().equals(EMPTY_QNAME) && this.methodName != null && !this.methodName.equals(""));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\ConfiguredCall.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */