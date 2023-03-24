/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreatorBase;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.util.JAXRPCClassFactory;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public class GenericObjectSerializer
/*     */   extends ObjectSerializerBase
/*     */   implements Initializable
/*     */ {
/*  53 */   protected Class targetClass = null;
/*  54 */   protected List members = new ArrayList();
/*  55 */   protected Map xmlToJavaType = new HashMap<Object, Object>();
/*  56 */   protected Map javaToXmlType = new HashMap<Object, Object>();
/*     */   
/*     */   protected InternalTypeMappingRegistry registry;
/*  59 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  63 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GenericObjectSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  73 */     this(type, encodeType, isNullable, encodingStyle, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GenericObjectSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, SOAPVersion ver) {
/*  83 */     super(type, encodeType, isNullable, encodingStyle);
/*  84 */     init(ver);
/*  85 */     SOAPSimpleTypeCreatorBase typeCreator = JAXRPCClassFactory.newInstance().createSOAPSimpleTypeCreator(false, ver);
/*     */ 
/*     */ 
/*     */     
/*  89 */     typeCreator.initializeJavaToXmlTypeMap(this.javaToXmlType);
/*     */   }
/*     */   
/*     */   public static interface GetterMethod {
/*     */     Object get(Object param1Object) throws Exception; }
/*     */   
/*     */   public static interface SetterMethod {
/*     */     void set(Object param1Object1, Object param1Object2) throws Exception;
/*     */   }
/*     */   
/*     */   public static class MemberInfo {
/* 100 */     QName name = null;
/* 101 */     QName xmlType = null;
/* 102 */     Class javaType = null;
/* 103 */     JAXRPCSerializer serializer = null;
/* 104 */     JAXRPCDeserializer deserializer = null;
/* 105 */     GenericObjectSerializer.GetterMethod getter = null;
/* 106 */     GenericObjectSerializer.SetterMethod setter = null;
/*     */   }
/*     */   
/*     */   public void addTypeRelation(Class<?> javaType, QName xmlType) {
/* 110 */     if (javaType == null || xmlType == null) {
/* 111 */       throw new IllegalArgumentException("Neither javaType nor xmlType may be null");
/*     */     }
/*     */     
/* 114 */     this.javaToXmlType.put(javaType, xmlType);
/* 115 */     this.xmlToJavaType.put(xmlType, javaType);
/*     */   }
/*     */   
/*     */   public void setTargetClass(Class targetClass) {
/* 119 */     clearMembers();
/* 120 */     doSetTargetClass(targetClass);
/* 121 */     this.targetClass = targetClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSetTargetClass(Class targetClass) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 131 */     this.registry = registry;
/*     */     
/* 133 */     Iterator<MemberInfo> eachMember = this.members.iterator();
/*     */     
/* 135 */     while (eachMember.hasNext()) {
/* 136 */       MemberInfo currentMember = eachMember.next();
/* 137 */       currentMember.serializer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, currentMember.javaType, currentMember.xmlType);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       currentMember.deserializer = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, currentMember.javaType, currentMember.xmlType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearMembers() {
/* 151 */     this.members.clear();
/*     */   }
/*     */   
/*     */   public void addMember(MemberInfo member) throws Exception {
/* 155 */     Iterator<MemberInfo> eachMember = this.members.iterator();
/*     */     
/* 157 */     while (eachMember.hasNext()) {
/* 158 */       MemberInfo existingMember = eachMember.next();
/* 159 */       if (existingMember.name.equals(member.name)) {
/* 160 */         throw new EncodingException("soap.duplicate.data.member", new Object[] { member.name });
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (member.xmlType == null) {
/* 167 */       member.xmlType = (QName)this.javaToXmlType.get(member.javaType);
/*     */     }
/* 169 */     if (member.javaType == null) {
/* 170 */       member.javaType = (Class)this.xmlToJavaType.get(member.xmlType);
/*     */     }
/* 172 */     this.members.add(member);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerializeInstance(Object instance, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 181 */     this.members = getMemberOrder(instance, this.members);
/*     */     
/* 183 */     for (int i = 0; i < this.members.size(); i++) {
/* 184 */       MemberInfo currentMember = this.members.get(i);
/*     */       
/* 186 */       if (currentMember.serializer == null) {
/* 187 */         Class<?> javaType = instance.getClass();
/* 188 */         currentMember.serializer = (JAXRPCSerializer)this.registry.getSerializer(this.soapEncodingConstants.getSOAPEncodingNamespace(), javaType, currentMember.xmlType);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 195 */       currentMember.serializer.serialize(currentMember.getter.get(instance), currentMember.name, null, writer, context);
/*     */     } 
/*     */   }
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
/*     */   protected Object doDeserialize(SOAPDeserializationState state, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 210 */     Object instance = this.targetClass.newInstance();
/*     */     
/* 212 */     SOAPGenericObjectInstanceBuilder builder = null;
/* 213 */     boolean isComplete = true;
/*     */     
/* 215 */     this.members = getMemberOrder(instance, this.members);
/*     */     
/* 217 */     int lastMemberIndex = this.members.size() - 1;
/*     */     
/* 219 */     int memberCount = 0;
/* 220 */     for (; memberCount <= lastMemberIndex; 
/* 221 */       memberCount++) {
/* 222 */       reader.nextElementContent();
/* 223 */       int memberIndex = memberCount;
/*     */       do {
/* 225 */         MemberInfo currentMember = this.members.get(memberIndex);
/*     */ 
/*     */         
/* 228 */         if (reader.getName().equals(currentMember.name)) {
/*     */           
/* 230 */           if (currentMember.deserializer == null) {
/* 231 */             QName xmlType = (currentMember.xmlType != null) ? currentMember.xmlType : SerializerBase.getType(reader);
/*     */ 
/*     */ 
/*     */             
/* 235 */             currentMember.deserializer = (JAXRPCDeserializer)this.registry.getDeserializer(this.soapEncodingConstants.getSOAPEncodingNamespace(), currentMember.javaType, xmlType);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 243 */           Object member = currentMember.deserializer.deserialize(currentMember.name, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 249 */           if (member instanceof SOAPDeserializationState) {
/* 250 */             if (builder == null) {
/* 251 */               builder = new SOAPGenericObjectInstanceBuilder(instance);
/*     */             }
/*     */             
/* 254 */             state = registerWithMemberState(instance, state, member, memberIndex, builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 261 */             isComplete = false; break;
/*     */           } 
/* 263 */           currentMember.setter.set(instance, member);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 268 */         if (memberIndex == lastMemberIndex) {
/* 269 */           memberIndex = 0;
/*     */         } else {
/* 271 */           memberIndex++;
/*     */         } 
/* 273 */       } while (memberIndex != memberCount);
/*     */     } 
/*     */     
/* 276 */     return isComplete ? instance : state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List getMemberOrder(Object instance, List<MemberInfo> membersAlphabetically) {
/* 282 */     if (membersAlphabetically == null) {
/* 283 */       return null;
/*     */     }
/* 285 */     List<String> members = new ArrayList();
/*     */     
/* 287 */     Class<?> targetClass = instance.getClass();
/*     */     
/* 289 */     int introspectedSize = membersAlphabetically.size();
/*     */     
/* 291 */     Field[] fields = targetClass.getDeclaredFields();
/* 292 */     int flen = (fields != null) ? fields.length : 0;
/*     */     
/* 294 */     ArrayList<Object> fieldList = new ArrayList(Arrays.asList((Object[])fields));
/*     */     
/* 296 */     if (flen < introspectedSize) {
/*     */       
/* 298 */       Class<?> superClass = targetClass.getSuperclass();
/* 299 */       while (superClass != null) {
/*     */         
/* 301 */         ArrayList superList = null;
/* 302 */         Field[] superFields = superClass.getDeclaredFields();
/* 303 */         if (superFields != null) {
/* 304 */           superList = new ArrayList(Arrays.asList((Object[])superFields));
/*     */           
/* 306 */           for (int k = 0; k < superList.size(); k++) {
/* 307 */             Object obj = superList.get(k);
/* 308 */             fieldList.add(k, obj);
/*     */           } 
/*     */         } 
/* 311 */         superClass = superClass.getSuperclass();
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     fields = null;
/*     */     
/* 317 */     fields = fieldList.<Field>toArray(new Field[fieldList.size()]);
/* 318 */     for (int i = 0; i < fields.length; i++) {
/* 319 */       Field field = fields[i];
/*     */       
/* 321 */       int fieldModifiers = field.getModifiers();
/*     */ 
/*     */ 
/*     */       
/* 325 */       if (!Modifier.isTransient(fieldModifiers))
/*     */       {
/*     */         
/* 328 */         if (!Modifier.isFinal(fieldModifiers)) {
/*     */ 
/*     */ 
/*     */           
/* 332 */           String name = field.getName();
/* 333 */           members.add(name);
/*     */         }  } 
/*     */     } 
/* 336 */     if (members.size() == 0) {
/* 337 */       return membersAlphabetically;
/*     */     }
/* 339 */     List<MemberInfo> orderedMembers = new ArrayList();
/* 340 */     for (int j = 0; j < members.size(); j++) {
/* 341 */       String name = members.get(j);
/* 342 */       for (int k = 0; k < membersAlphabetically.size(); k++) {
/* 343 */         MemberInfo info = membersAlphabetically.get(k);
/*     */         
/* 345 */         if (name.equalsIgnoreCase(info.name.getLocalPart())) {
/*     */           
/* 347 */           if (!name.equals(info.name.getLocalPart())) {
/*     */             
/* 349 */             String ns = info.name.getNamespaceURI();
/* 350 */             info.name = new QName(ns, name);
/*     */             
/* 352 */             info.xmlType = new QName(ns, name);
/*     */           } 
/* 354 */           orderedMembers.add(info);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 360 */     if (orderedMembers.size() == 0)
/* 361 */       return membersAlphabetically; 
/* 362 */     return orderedMembers;
/*     */   }
/*     */ 
/*     */   
/*     */   protected class SOAPGenericObjectInstanceBuilder
/*     */     implements SOAPInstanceBuilder
/*     */   {
/*     */     Object instance;
/*     */     
/*     */     SOAPGenericObjectInstanceBuilder(Object instance) {
/* 372 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 376 */       return 6;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMember(int index, Object memberValue) {
/*     */       try {
/* 387 */         ((GenericObjectSerializer.MemberInfo)GenericObjectSerializer.this.members.get(index)).setter.set(this.instance, memberValue);
/*     */       
/*     */       }
/* 390 */       catch (Exception e) {
/* 391 */         throw new DeserializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void initialize() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInstance(Object instance) {
/* 402 */       instance = instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 406 */       return this.instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\GenericObjectSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */