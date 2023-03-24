/*     */ package com.sun.xml.rpc.client.dii;
/*     */ 
/*     */ import com.sun.xml.rpc.util.Holders;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.rpc.Call;
/*     */ import javax.xml.rpc.Stub;
/*     */ import javax.xml.rpc.holders.Holder;
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
/*     */ public class CallInvocationHandler
/*     */   implements InvocationHandler, Stub
/*     */ {
/*     */   private static final Set recognizedProperties;
/*  55 */   private Map callMap = new HashMap<Object, Object>();
/*  56 */   private Map properties = new HashMap<Object, Object>();
/*     */   private Class portInterface;
/*     */   
/*     */   static {
/*  60 */     Set<String> temp = new HashSet();
/*  61 */     temp.add("javax.xml.rpc.security.auth.username");
/*  62 */     temp.add("javax.xml.rpc.security.auth.password");
/*  63 */     temp.add("javax.xml.rpc.service.endpoint.address");
/*  64 */     temp.add("javax.xml.rpc.session.maintain");
/*  65 */     temp.add("com.sun.xml.rpc.client.http.CookieJar");
/*  66 */     recognizedProperties = Collections.unmodifiableSet(temp);
/*     */   }
/*     */ 
/*     */   
/*     */   CallInvocationHandler(Class portInterface) {
/*  71 */     this.portInterface = portInterface;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCall(Method key, Call call) {
/*  76 */     this.callMap.put(key, call);
/*     */   }
/*     */   
/*     */   public Call getCall(Method key) {
/*  80 */     return (Call)this.callMap.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
/*  87 */     if (this.portInterface.equals(method.getDeclaringClass())) {
/*  88 */       Call call = getCall(method);
/*  89 */       if (call == null) {
/*  90 */         String knownMethodNames = "";
/*  91 */         Iterator<Method> eachKnownMethod = this.callMap.keySet().iterator();
/*  92 */         while (eachKnownMethod.hasNext()) {
/*  93 */           Method knownMethod = eachKnownMethod.next();
/*  94 */           knownMethodNames = knownMethodNames + "\n" + knownMethod.getName();
/*     */         } 
/*  96 */         throw new DynamicInvocationException("dii.dynamicproxy.method.unrecognized", new Object[] { (method != null) ? method.getName() : null, knownMethodNames });
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       return doCall(call, args);
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return doNonPort(method, args);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object doCall(Call call, Object[] args) throws RemoteException {
/* 112 */     if (args == null) {
/* 113 */       args = new Object[0];
/*     */     }
/*     */ 
/*     */     
/* 117 */     Holder[] holders = new Holder[args.length];
/* 118 */     int[] holderLocations = new int[args.length];
/*     */     
/* 120 */     int lastHolderIndex = 0;
/* 121 */     for (int i = 0; i < args.length; i++) {
/* 122 */       Object arg = args[i];
/* 123 */       if (arg instanceof Holder) {
/* 124 */         Holder holderArg = (Holder)arg;
/*     */         
/* 126 */         holderLocations[lastHolderIndex] = i;
/*     */         
/* 128 */         holders[lastHolderIndex] = holderArg;
/*     */ 
/*     */         
/* 131 */         args[i] = Holders.getValue(holderArg);
/*     */         
/* 133 */         lastHolderIndex++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 138 */     Object returnValue = call.invoke(args);
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (lastHolderIndex > 0) {
/* 143 */       List outputValues = call.getOutputValues();
/* 144 */       Iterator eachOutputValue = outputValues.iterator();
/* 145 */       int holderIndex = 0;
/* 146 */       while (eachOutputValue.hasNext()) {
/* 147 */         Object outParameter = eachOutputValue.next();
/*     */         
/* 149 */         int holderLocation = holderLocations[holderIndex];
/* 150 */         Holder holder = holders[holderIndex];
/* 151 */         Holders.setValue(holder, outParameter);
/* 152 */         args[holderLocation] = holder;
/*     */         
/* 154 */         holderIndex++;
/*     */       } 
/*     */     } 
/* 157 */     return returnValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object doNonPort(Method method, Object[] args) {
/*     */     try {
/* 163 */       return method.invoke(this, args);
/* 164 */     } catch (Exception e) {
/* 165 */       throw new DynamicInvocationException("dii.exception.nested", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void _setProperty(String name, Object value) {
/* 171 */     if (!recognizedProperties.contains(name)) {
/* 172 */       throw new IllegalArgumentException("Call object does not recognize property: " + name);
/*     */     }
/*     */     
/* 175 */     this.properties.put(name, value);
/*     */     
/* 177 */     setPropertyOnCallObjects(name, value);
/*     */   }
/*     */   
/*     */   public Object _getProperty(String name) {
/* 181 */     return this.properties.get(name);
/*     */   }
/*     */   
/*     */   public Iterator _getPropertyNames() {
/* 185 */     return this.properties.keySet().iterator();
/*     */   }
/*     */   
/*     */   private void setPropertyOnCallObjects(String propertyName, Object value) {
/* 189 */     Iterator<Call> eachCall = this.callMap.values().iterator();
/* 190 */     while (eachCall.hasNext()) {
/*     */ 
/*     */       
/* 193 */       Call call = eachCall.next();
/*     */       
/* 195 */       if ("javax.xml.rpc.service.endpoint.address".equals(propertyName)) {
/* 196 */         call.setTargetEndpointAddress((String)value); continue;
/*     */       } 
/* 198 */       call.setProperty(propertyName, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String _getDefaultEnvelopeEncodingStyle() {
/* 206 */     return null;
/*     */   }
/*     */   
/*     */   public String _getImplicitEnvelopeEncodingStyle() {
/* 210 */     return "";
/*     */   }
/*     */   
/*     */   public String _getEncodingStyle() {
/* 214 */     return "http://schemas.xmlsoap.org/soap/encoding/";
/*     */   }
/*     */   
/*     */   public void _setEncodingStyle(String encodingStyle) {
/* 218 */     throw new UnsupportedOperationException("cannot set encoding style");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\CallInvocationHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */