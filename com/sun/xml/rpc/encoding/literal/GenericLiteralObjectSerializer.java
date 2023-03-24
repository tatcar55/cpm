/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.client.dii.ParameterMemberInfo;
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.DynamicInternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.EncodingException;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.LiteralSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GenericLiteralObjectSerializer
/*     */   extends LiteralObjectSerializerBase
/*     */   implements Initializable
/*     */ {
/*  71 */   protected Class targetClass = null;
/*  72 */   protected List members = new ArrayList();
/*  73 */   protected Map xmlToJavaType = new HashMap<Object, Object>();
/*  74 */   protected Map javaToXmlType = new HashMap<Object, Object>();
/*     */   
/*     */   protected InternalTypeMappingRegistry registry;
/*     */   protected Collection memberOrder;
/*  78 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  81 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GenericLiteralObjectSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  89 */     this(type, encodeType, isNullable, encodingStyle, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GenericLiteralObjectSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, SOAPVersion ver) {
/*  98 */     super(type, isNullable, encodingStyle, encodeType);
/*  99 */     init(ver);
/*     */     
/* 101 */     LiteralSimpleTypeCreator typeCreator = new LiteralSimpleTypeCreator();
/* 102 */     typeCreator.initializeJavaToXmlTypeMap(this.javaToXmlType);
/*     */   }
/*     */   
/*     */   public static interface GetterMethod {
/*     */     Object get(Object param1Object) throws Exception;
/*     */   }
/*     */   
/*     */   public static interface SetterMethod {
/*     */     void set(Object param1Object1, Object param1Object2) throws Exception;
/*     */   }
/*     */   
/*     */   public static class MemberInfo {
/* 114 */     QName name = null;
/* 115 */     QName xmlType = null;
/* 116 */     Class javaType = null;
/* 117 */     JAXRPCSerializer serializer = null;
/* 118 */     JAXRPCDeserializer deserializer = null;
/* 119 */     GenericLiteralObjectSerializer.GetterMethod getter = null;
/* 120 */     GenericLiteralObjectSerializer.SetterMethod setter = null;
/*     */   }
/*     */   
/*     */   public void addTypeRelation(Class<?> javaType, QName xmlType) {
/* 124 */     if (javaType == null || xmlType == null) {
/* 125 */       throw new IllegalArgumentException("Neither javaType nor xmlType may be null");
/*     */     }
/*     */     
/* 128 */     this.javaToXmlType.put(javaType, xmlType);
/* 129 */     this.xmlToJavaType.put(xmlType, javaType);
/*     */   }
/*     */   
/*     */   public void setTargetClass(Class targetClass) {
/* 133 */     clearMembers();
/* 134 */     doSetTargetClass(targetClass);
/* 135 */     this.targetClass = targetClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSetTargetClass(Class targetClass) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 145 */     this.registry = registry;
/*     */ 
/*     */     
/* 148 */     ParameterMemberInfo[] pmemberInfos = ((DynamicInternalTypeMappingRegistry)registry).getDynamicRegistryMembers(this.targetClass, this.type, "");
/*     */ 
/*     */ 
/*     */     
/* 152 */     int msize = this.members.size();
/* 153 */     int pmsize = 0;
/* 154 */     if (pmemberInfos != null) {
/* 155 */       pmsize = pmemberInfos.length;
/* 156 */       this.members = orderCurrentMembers(this.members, pmemberInfos);
/*     */     } 
/*     */ 
/*     */     
/* 160 */     Iterator eachMember = this.members.iterator();
/*     */ 
/*     */     
/* 163 */     for (int i = 0; i < this.members.size(); i++) {
/* 164 */       MemberInfo currentMember = this.members.get(i);
/* 165 */       Class pmJavaClass = null;
/* 166 */       QName pmXmlType = null;
/* 167 */       String pmName = null;
/*     */       
/* 169 */       if (i < pmsize) {
/* 170 */         ParameterMemberInfo pmInfo = pmemberInfos[i];
/* 171 */         pmJavaClass = pmInfo.getMemberJavaClass();
/* 172 */         pmXmlType = pmInfo.getMemberXmlType();
/* 173 */         pmName = pmInfo.getMemberName();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       if (currentMember.xmlType == null && currentMember.javaType.isArray()) {
/* 187 */         currentMember.xmlType = (currentMember.name != null) ? currentMember.name : currentMember.xmlType;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       if (currentMember.javaType == pmJavaClass && currentMember.name.getLocalPart().equalsIgnoreCase(pmName)) {
/*     */         
/* 195 */         if (pmXmlType != null) {
/* 196 */           currentMember.xmlType = pmXmlType;
/*     */         }
/* 198 */         if (pmName != null) {
/* 199 */           currentMember.name = new QName("", pmName);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 206 */       currentMember.serializer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, currentMember.javaType, currentMember.xmlType);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 211 */       currentMember.deserializer = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, currentMember.javaType, currentMember.xmlType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearMembers() {
/* 221 */     this.members.clear();
/*     */   }
/*     */   
/*     */   public void addMember(MemberInfo member) throws Exception {
/* 225 */     Iterator<MemberInfo> eachMember = this.members.iterator();
/*     */     
/* 227 */     while (eachMember.hasNext()) {
/* 228 */       MemberInfo existingMember = eachMember.next();
/* 229 */       if (existingMember.name.equals(member.name)) {
/* 230 */         throw new EncodingException("soap.duplicate.data.member", new Object[] { member.name });
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 236 */     if (member.xmlType == null) {
/* 237 */       member.xmlType = (QName)this.javaToXmlType.get(member.javaType);
/*     */     }
/* 239 */     if (member.javaType == null) {
/* 240 */       member.javaType = (Class)this.xmlToJavaType.get(member.xmlType);
/*     */     }
/* 242 */     this.members.add(member);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 247 */     doSerializeInstance(obj, writer, context);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {}
/*     */   
/*     */   protected Object doDeserialize(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 254 */     return doDeserialize((SOAPDeserializationState)null, reader, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerializeInstance(Object instance, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 263 */     ParameterMemberInfo[] pmemberInfos = ((DynamicInternalTypeMappingRegistry)this.registry).getDynamicRegistryMembers(this.targetClass, this.type, "");
/*     */ 
/*     */ 
/*     */     
/* 267 */     int msize = this.members.size();
/* 268 */     int pmsize = 0;
/* 269 */     if (pmemberInfos != null) {
/* 270 */       pmsize = pmemberInfos.length;
/*     */     }
/* 272 */     if (this.memberOrder == null || this.memberOrder.size() == 0) {
/* 273 */       this.members = getMemberOrder(instance, this.members);
/*     */     } else {
/* 275 */       this.members = checkFieldCase(this.members);
/*     */     } 
/* 277 */     for (int i = 0; i < this.members.size(); i++) {
/* 278 */       MemberInfo currentMember = this.members.get(i);
/*     */       
/* 280 */       Class pmJavaClass = null;
/* 281 */       QName pmXmlType = null;
/* 282 */       String pmName = null;
/*     */       
/* 284 */       if (i < pmsize) {
/* 285 */         ParameterMemberInfo pmInfo = pmemberInfos[i];
/* 286 */         pmJavaClass = pmInfo.getMemberJavaClass();
/* 287 */         pmXmlType = pmInfo.getMemberXmlType();
/* 288 */         pmName = pmInfo.getMemberName();
/*     */       } 
/*     */       
/* 291 */       if (currentMember.javaType == pmJavaClass && currentMember.name.getLocalPart().equalsIgnoreCase(pmName)) {
/*     */         
/* 293 */         if (pmXmlType != null) {
/* 294 */           currentMember.xmlType = pmXmlType;
/*     */         }
/* 296 */         currentMember.name = new QName("", pmName);
/*     */       } 
/*     */ 
/*     */       
/* 300 */       if (currentMember.serializer == null) {
/* 301 */         Class<?> javaType = instance.getClass();
/* 302 */         currentMember.serializer = (JAXRPCSerializer)this.registry.getSerializer(this.encodingStyle, javaType, currentMember.xmlType);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 309 */       currentMember.serializer.serialize(currentMember.getter.get(instance), currentMember.name, null, writer, context);
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
/* 324 */     Object instance = this.targetClass.newInstance();
/* 325 */     ParameterMemberInfo[] pmemberInfos = ((DynamicInternalTypeMappingRegistry)this.registry).getDynamicRegistryMembers(this.targetClass, this.type, "");
/*     */ 
/*     */ 
/*     */     
/* 329 */     int msize = this.members.size();
/* 330 */     int pmsize = 0;
/* 331 */     if (pmemberInfos != null) {
/* 332 */       pmsize = pmemberInfos.length;
/*     */     }
/*     */     
/* 335 */     if (this.memberOrder == null || this.memberOrder.size() == 0) {
/* 336 */       this.members = getMemberOrder(instance, this.members);
/*     */     } else {
/* 338 */       this.members = checkFieldCase(this.members);
/*     */     } 
/*     */     
/* 341 */     SOAPGenericObjectInstanceBuilder builder = null;
/* 342 */     boolean isComplete = true;
/*     */     
/* 344 */     int lastMemberIndex = this.members.size();
/*     */ 
/*     */     
/* 347 */     reader.nextElementContent();
/* 348 */     int memberCount = 0;
/* 349 */     for (; memberCount < lastMemberIndex; 
/* 350 */       memberCount++) {
/*     */ 
/*     */       
/* 353 */       int memberIndex = memberCount;
/*     */       
/*     */       do {
/* 356 */         Class pmJavaClass = null;
/* 357 */         QName pmXmlType = null;
/* 358 */         String pmName = null;
/*     */         
/* 360 */         if (memberIndex < pmsize) {
/* 361 */           ParameterMemberInfo pmInfo = pmemberInfos[memberIndex];
/* 362 */           pmJavaClass = pmInfo.getMemberJavaClass();
/* 363 */           pmXmlType = pmInfo.getMemberXmlType();
/* 364 */           pmName = pmInfo.getMemberName();
/*     */         } 
/*     */         
/* 367 */         QName elementName = reader.getName();
/* 368 */         MemberInfo currentMember = this.members.get(memberIndex);
/*     */ 
/*     */         
/* 371 */         if (currentMember.javaType == pmJavaClass && currentMember.name.getLocalPart().equalsIgnoreCase(pmName)) {
/*     */           
/* 373 */           if (pmXmlType != null)
/* 374 */             currentMember.xmlType = pmXmlType; 
/* 375 */           if (pmName != null) {
/* 376 */             currentMember.name = new QName("", pmName);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 383 */         int i = 0;
/* 384 */         while (reader.getState() == 2 && i < lastMemberIndex)
/*     */         {
/* 386 */           reader.nextElementContent();
/*     */         }
/*     */         
/* 389 */         if (reader.getState() != 1) {
/*     */           continue;
/*     */         }
/* 392 */         if (currentMember.serializer != null && currentMember.javaType.isArray()) {
/* 393 */           currentMember.name = ((LiteralObjectSerializerBase)currentMember.serializer).getXmlType();
/*     */         }
/* 395 */         if (reader.getName().equals(currentMember.name)) {
/*     */           
/* 397 */           if (currentMember.deserializer == null) {
/* 398 */             QName xmlType = (currentMember.xmlType != null) ? currentMember.xmlType : SerializerBase.getType(reader);
/* 399 */             currentMember.deserializer = (JAXRPCDeserializer)this.registry.getDeserializer(this.encodingStyle, currentMember.javaType, currentMember.xmlType);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 406 */           Object member = currentMember.deserializer.deserialize(currentMember.name, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 423 */           if (member instanceof SOAPDeserializationState) {
/* 424 */             if (builder == null) {
/* 425 */               builder = new SOAPGenericObjectInstanceBuilder(instance);
/*     */             }
/*     */             
/* 428 */             state = registerWithMemberState(instance, state, member, memberIndex, builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 435 */             isComplete = false; break;
/*     */           } 
/* 437 */           currentMember.setter.set(instance, member);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 442 */         if (memberIndex == lastMemberIndex) {
/* 443 */           memberIndex = 0;
/*     */         } else {
/* 445 */           memberIndex++;
/*     */         }
/*     */       
/*     */       }
/* 449 */       while (memberIndex < memberCount);
/*     */     } 
/*     */ 
/*     */     
/* 453 */     XMLReaderUtil.verifyReaderState(reader, 2);
/* 454 */     return isComplete ? instance : state;
/*     */   }
/*     */   
/*     */   protected void verifyType(XMLReader reader) throws Exception {
/* 458 */     QName actualType = getType(reader);
/*     */     
/* 460 */     if (actualType != null);
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
/*     */ 
/*     */   
/*     */   private List checkFieldCase(List<MemberInfo> members) {
/* 476 */     Iterator<String> iter = this.memberOrder.iterator();
/* 477 */     while (iter.hasNext()) {
/* 478 */       String name = iter.next();
/* 479 */       for (int i = 0; i < members.size(); i++) {
/*     */         
/* 481 */         MemberInfo info = members.get(i);
/* 482 */         if (name.equalsIgnoreCase(info.name.getLocalPart()))
/*     */         {
/* 484 */           if (!name.equals(info.name.getLocalPart())) {
/*     */             
/* 486 */             String ns = info.name.getNamespaceURI();
/* 487 */             info.name = new QName(ns, name);
/*     */             
/* 489 */             info.xmlType = new QName(ns, name);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 494 */     return members;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List getMemberOrder(Object instance, List<MemberInfo> membersAlphabetically) {
/* 500 */     if (membersAlphabetically == null) {
/* 501 */       return null;
/*     */     }
/* 503 */     List<String> members = new ArrayList();
/*     */     
/* 505 */     Class<?> targetClass = instance.getClass();
/*     */     
/* 507 */     int introspectedSize = membersAlphabetically.size();
/*     */     
/* 509 */     Field[] fields = targetClass.getDeclaredFields();
/* 510 */     int flen = (fields != null) ? fields.length : 0;
/*     */     
/* 512 */     ArrayList<Object> fieldList = new ArrayList(Arrays.asList((Object[])fields));
/*     */     
/* 514 */     if (flen < introspectedSize) {
/*     */       
/* 516 */       Class<?> superClass = targetClass.getSuperclass();
/* 517 */       while (superClass != null) {
/*     */         
/* 519 */         ArrayList superList = null;
/* 520 */         Field[] superFields = superClass.getDeclaredFields();
/* 521 */         if (superFields != null) {
/* 522 */           superList = new ArrayList(Arrays.asList((Object[])superFields));
/*     */           
/* 524 */           for (int k = 0; k < superList.size(); k++) {
/* 525 */             Object obj = superList.get(k);
/* 526 */             fieldList.add(k, obj);
/*     */           } 
/*     */         } 
/* 529 */         superClass = superClass.getSuperclass();
/*     */       } 
/*     */     } 
/*     */     
/* 533 */     fields = null;
/*     */     
/* 535 */     fields = fieldList.<Field>toArray(new Field[fieldList.size()]);
/* 536 */     for (int i = 0; i < fields.length; i++) {
/* 537 */       Field field = fields[i];
/*     */       
/* 539 */       int fieldModifiers = field.getModifiers();
/*     */ 
/*     */ 
/*     */       
/* 543 */       if (!Modifier.isTransient(fieldModifiers))
/*     */       {
/*     */         
/* 546 */         if (!Modifier.isFinal(fieldModifiers)) {
/*     */ 
/*     */ 
/*     */           
/* 550 */           String name = field.getName();
/* 551 */           members.add(name);
/*     */         }  } 
/*     */     } 
/* 554 */     if (members.size() == 0) {
/* 555 */       return membersAlphabetically;
/*     */     }
/* 557 */     List<MemberInfo> orderedMembers = new ArrayList();
/* 558 */     for (int j = 0; j < members.size(); j++) {
/* 559 */       String name = members.get(j);
/* 560 */       for (int k = 0; k < membersAlphabetically.size(); k++) {
/* 561 */         MemberInfo info = membersAlphabetically.get(k);
/* 562 */         if (name.equalsIgnoreCase(info.name.getLocalPart())) {
/*     */           
/* 564 */           if (!name.equals(info.name.getLocalPart())) {
/*     */             
/* 566 */             String ns = info.name.getNamespaceURI();
/* 567 */             info.name = new QName(ns, name);
/*     */             
/* 569 */             info.xmlType = new QName(ns, name);
/*     */           } 
/* 571 */           orderedMembers.add(info);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 577 */     if (orderedMembers.size() == 0)
/* 578 */       return membersAlphabetically; 
/* 579 */     return orderedMembers;
/*     */   }
/*     */ 
/*     */   
/*     */   private List orderCurrentMembers(List members, ParameterMemberInfo[] pmemberInfos) {
/* 584 */     ArrayList<MemberInfo> newMembers = new ArrayList();
/*     */     
/* 586 */     MemberInfo[] memberInfos = (MemberInfo[])members.toArray((Object[])new MemberInfo[members.size()]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 593 */     if (memberInfos.length != pmemberInfos.length) {
/* 594 */       return members;
/*     */     }
/*     */     
/* 597 */     for (int i = 0; i < pmemberInfos.length; i++) {
/* 598 */       ParameterMemberInfo pminfo = pmemberInfos[i];
/* 599 */       Class pmJavaClass = pminfo.getMemberJavaClass();
/* 600 */       QName pmXmlType = pminfo.getMemberXmlType();
/* 601 */       String pmName = pminfo.getMemberName();
/* 602 */       for (int j = 0; j < memberInfos.length; j++) {
/* 603 */         MemberInfo minfo = memberInfos[j];
/*     */ 
/*     */         
/* 606 */         if (minfo.javaType == pmJavaClass)
/*     */         {
/* 608 */           if (minfo.name.getLocalPart().equalsIgnoreCase(pmName)) {
/* 609 */             minfo.xmlType = pmXmlType;
/* 610 */             newMembers.add(minfo);
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 619 */     if (newMembers.size() != members.size())
/* 620 */       return members; 
/* 621 */     return newMembers;
/*     */   }
/*     */ 
/*     */   
/*     */   protected class SOAPGenericObjectInstanceBuilder
/*     */     implements SOAPInstanceBuilder
/*     */   {
/*     */     Object instance;
/*     */ 
/*     */     
/*     */     SOAPGenericObjectInstanceBuilder(Object instance) {
/* 632 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 636 */       return 6;
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
/* 647 */         ((GenericLiteralObjectSerializer.MemberInfo)GenericLiteralObjectSerializer.this.members.get(index)).setter.set(this.instance, memberValue);
/*     */       
/*     */       }
/* 650 */       catch (Exception e) {
/* 651 */         throw new DeserializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
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
/* 662 */       instance = instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 666 */       return this.instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\GenericLiteralObjectSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */