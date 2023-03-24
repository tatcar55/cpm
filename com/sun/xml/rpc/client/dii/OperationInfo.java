/*     */ package com.sun.xml.rpc.client.dii;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.ParameterMode;
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
/*     */ public class OperationInfo
/*     */ {
/*     */   String namespace;
/*     */   String localName;
/*     */   QName qualifiedName;
/*     */   HashMap parameterModels;
/*     */   ArrayList parameterMembers;
/*     */   ArrayList parameterNames;
/*     */   ArrayList parameterXmlTypes;
/*     */   ArrayList parameterXmlTypeQNames;
/*     */   ArrayList parameterJavaTypes;
/*     */   ArrayList parameterModes;
/*     */   String endPointAddress;
/*     */   QName requestQName;
/*     */   QName requestXmlType;
/*     */   LiteralType returnLiteralType;
/*     */   QName responseQName;
/*     */   QName returnXmlType;
/*     */   QName returnXmlTypeQName;
/*     */   Class returnJavaType;
/*     */   String returnClassName;
/*     */   ArrayList returnMembers;
/*     */   LiteralElementMember returnTypeModel;
/*     */   Map properties;
/*     */   boolean isDocumentOperationFlag;
/*     */   boolean isRPCLiteralOperationFlag;
/*     */   boolean isOneWay;
/*  75 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  78 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */   
/*     */   public OperationInfo(String localName) {
/*  82 */     this(localName, SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   public OperationInfo(String localName, SOAPVersion ver) {
/*  86 */     init(ver);
/*  87 */     init();
/*  88 */     this.localName = localName;
/*     */   }
/*     */   
/*     */   protected void init() {
/*  92 */     this.namespace = "";
/*  93 */     this.localName = "";
/*  94 */     this.qualifiedName = null;
/*  95 */     this.parameterMembers = new ArrayList();
/*  96 */     this.parameterModels = new HashMap<Object, Object>();
/*  97 */     this.parameterNames = new ArrayList();
/*  98 */     this.parameterXmlTypes = new ArrayList();
/*  99 */     this.parameterXmlTypeQNames = new ArrayList();
/* 100 */     this.parameterJavaTypes = new ArrayList();
/* 101 */     this.parameterModes = new ArrayList();
/* 102 */     this.endPointAddress = "";
/* 103 */     this.returnXmlType = null;
/* 104 */     this.returnJavaType = null;
/* 105 */     this.returnXmlTypeQName = null;
/* 106 */     this.returnMembers = new ArrayList();
/* 107 */     this.requestQName = null;
/* 108 */     this.requestXmlType = null;
/* 109 */     this.responseQName = null;
/* 110 */     this.properties = new HashMap<Object, Object>();
/* 111 */     this.isDocumentOperationFlag = false;
/* 112 */     setProperty("javax.xml.rpc.soap.operation.style", "rpc");
/* 113 */     setProperty("javax.xml.rpc.encodingstyle.namespace.uri", this.soapEncodingConstants.getURIEncoding());
/*     */   }
/*     */   
/*     */   public void setNamespace(String namespace) {
/* 117 */     this.namespace = namespace;
/* 118 */     this.qualifiedName = null;
/*     */   }
/*     */   
/*     */   public String getNamespace() {
/* 122 */     return this.namespace;
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 126 */     if (this.qualifiedName == null) {
/* 127 */       this.qualifiedName = new QName(this.namespace, this.localName);
/*     */     }
/*     */     
/* 130 */     return this.qualifiedName;
/*     */   }
/*     */   
/*     */   public void addParameter(String parameterName, QName parameterXmlType) {
/* 134 */     addParameter(parameterName, parameterXmlType, null, ParameterMode.IN);
/*     */   }
/*     */   
/*     */   public void addParameter(String parameterName, QName parameterXmlType, Class<?> javaType, ParameterMode mode) {
/* 138 */     this.parameterNames.add(parameterName);
/* 139 */     this.parameterXmlTypes.add(parameterXmlType);
/* 140 */     this.parameterJavaTypes.add(javaType);
/* 141 */     this.parameterModes.add(mode);
/*     */   }
/*     */   
/*     */   public void addParameterModel(String parameterName, LiteralElementMember parameterWsdlModel) {
/* 145 */     this.parameterModels.put(parameterName, parameterWsdlModel);
/*     */   }
/*     */   
/*     */   public Collection getParameterModels() {
/* 149 */     return this.parameterModels.values();
/*     */   }
/*     */   
/*     */   public void setReturnType(QName returnXmlType) {
/* 153 */     setReturnType(returnXmlType, null);
/*     */   }
/*     */   
/*     */   public void setReturnTypeQName(QName returnXmlTypeQName) {
/* 157 */     this.returnXmlTypeQName = returnXmlTypeQName;
/*     */   }
/*     */   
/*     */   public QName getReturnXmlTypeQName() {
/* 161 */     return this.returnXmlTypeQName;
/*     */   }
/*     */   
/*     */   public void setReturnType(QName returnXmlType, Class returnJavaType) {
/* 165 */     this.returnXmlType = returnXmlType;
/* 166 */     this.returnJavaType = returnJavaType;
/*     */   }
/*     */   
/*     */   public void addParameterXmlTypeQName(QName parameterXmlTypeQName) {
/* 170 */     this.parameterXmlTypeQNames.add(parameterXmlTypeQName);
/*     */   }
/*     */   
/*     */   public QName[] getParameterXmlTypeQNames() {
/* 174 */     return (QName[])this.parameterXmlTypeQNames.toArray((Object[])new QName[this.parameterXmlTypeQNames.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReturnClassName(String name) {
/* 179 */     this.returnClassName = name;
/*     */   }
/*     */   
/*     */   public ParameterMemberInfo[] getReturnMembers() {
/* 183 */     return (ParameterMemberInfo[])this.returnMembers.toArray((Object[])new ParameterMemberInfo[this.returnMembers.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReturnMembers(ArrayList members) {
/* 188 */     this.returnMembers = members;
/*     */   }
/*     */   
/*     */   public String getReturnClassName() {
/* 192 */     return this.returnClassName;
/*     */   }
/*     */   
/*     */   public Class getReturnClass() {
/* 196 */     return this.returnJavaType;
/*     */   }
/*     */   
/*     */   public void setRequestQName(QName name) {
/* 200 */     this.requestQName = name;
/*     */   }
/*     */   
/*     */   public QName getRequestQName() {
/* 204 */     return this.requestQName;
/*     */   }
/*     */   
/*     */   public void setResponseQName(QName name) {
/* 208 */     this.responseQName = name;
/*     */   }
/*     */   
/*     */   public QName getResponseQName() {
/* 212 */     return this.responseQName;
/*     */   }
/*     */   
/*     */   public QName getReturnXmlType() {
/* 216 */     return this.returnXmlType;
/*     */   }
/*     */   
/*     */   public void setReturnTypeModel(LiteralElementMember returnTypeModel) {
/* 220 */     this.returnTypeModel = returnTypeModel;
/*     */   }
/*     */   
/*     */   public String[] getParameterNames() {
/* 224 */     return (String[])this.parameterNames.toArray((Object[])new String[this.parameterNames.size()]);
/*     */   }
/*     */   
/*     */   public int getParameterCount() {
/* 228 */     return this.parameterNames.size();
/*     */   }
/*     */   
/*     */   public QName[] getParameterXmlTypes() {
/* 232 */     return (QName[])this.parameterXmlTypes.toArray((Object[])new QName[this.parameterXmlTypes.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class[] getParameterJavaTypes() {
/* 237 */     return (Class[])this.parameterJavaTypes.toArray((Object[])new QName[this.parameterJavaTypes.size()]);
/*     */   }
/*     */   
/*     */   public ParameterMode[] getParameterModes() {
/* 241 */     return (ParameterMode[])this.parameterModes.toArray((Object[])new ParameterMode[this.parameterModes.size()]);
/*     */   }
/*     */   
/*     */   public void addMemberInfos(ArrayList infos) {
/* 245 */     this.parameterMembers.add(infos);
/*     */   }
/*     */   
/*     */   public ParameterMemberInfo[] getMemberInfo(int index) {
/* 249 */     if (this.parameterMembers.size() > index) {
/* 250 */       ArrayList infosByParameterIndex = this.parameterMembers.get(index);
/* 251 */       return (ParameterMemberInfo[])infosByParameterIndex.toArray((Object[])new ParameterMemberInfo[infosByParameterIndex.size()]);
/*     */     } 
/* 253 */     return new ParameterMemberInfo[0];
/*     */   }
/*     */   
/*     */   public String makeKey(String parameterName, Class parameterClass) {
/* 257 */     String className = "";
/* 258 */     if (parameterClass != null)
/* 259 */       className = parameterClass.getName(); 
/* 260 */     return new String(parameterName + className);
/*     */   }
/*     */   
/*     */   public void setEndPointAddress(String address) {
/* 264 */     this.endPointAddress = address;
/*     */   }
/*     */   
/*     */   public String getEndPointAddress() {
/* 268 */     return this.endPointAddress;
/*     */   }
/*     */   
/*     */   public void setProperty(String key, Object value) {
/* 272 */     this.properties.put(key, value);
/*     */   }
/*     */   
/*     */   public Object getProperty(String key) {
/* 276 */     return this.properties.get(key);
/*     */   }
/*     */   
/*     */   public Iterator getPropertyKeys() {
/* 280 */     return this.properties.keySet().iterator();
/*     */   }
/*     */   
/*     */   public void beDocumentOperation() {
/* 284 */     this.isDocumentOperationFlag = true;
/*     */   }
/*     */   
/*     */   public boolean isDocumentOperation() {
/* 288 */     return this.isDocumentOperationFlag;
/*     */   }
/*     */   
/*     */   public void setIsOneWay(boolean oneway) {
/* 292 */     this.isOneWay = oneway;
/*     */   }
/*     */   
/*     */   public boolean isOneWay() {
/* 296 */     return this.isOneWay;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\OperationInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */