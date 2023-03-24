/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.StringUtils;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class ExceptionModeler
/*     */   extends ExceptionModelerBase
/*     */   implements RmiConstants
/*     */ {
/*     */   private RmiTypeModeler rmiTypeModeler;
/*     */   
/*     */   public ExceptionModeler(RmiModeler modeler, RmiTypeModeler typeModeler) {
/*  64 */     super(modeler);
/*  65 */     this.rmiTypeModeler = typeModeler;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkForJavaExceptions(String className) {}
/*     */   
/*     */   public Fault createFault(String typeUri, String wsdlUri, Class classDef) {
/*  72 */     String exceptionName = classDef.getName();
/*  73 */     Fault fault = (Fault)this.faultMap.get(exceptionName);
/*  74 */     if (fault != null)
/*  75 */       return fault; 
/*  76 */     HashMap<Object, Object> members = new HashMap<Object, Object>();
/*  77 */     collectMembers(classDef, members);
/*  78 */     int getMessageFlags = 0;
/*  79 */     if (members.containsKey("getMessage")) {
/*  80 */       Iterator<Map.Entry> iterator = members.entrySet().iterator();
/*  81 */       while (iterator.hasNext()) {
/*  82 */         Method member = (Method)((Map.Entry)iterator.next()).getValue();
/*     */         
/*  84 */         if (member.getReturnType().equals(String.class) && !member.getName().equals("getMessage")) {
/*     */           
/*  86 */           members.remove("getMessage");
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  91 */     if (members.size() == 0) {
/*  92 */       members.put("getMessage", GET_MESSAGE_METHOD);
/*     */     }
/*  94 */     if (members.containsKey("getMessage")) {
/*  95 */       getMessageFlags = 2;
/*     */     }
/*  97 */     boolean hasDuplicates = false;
/*  98 */     Set duplicateMembers = getDuplicateMembers(members);
/*  99 */     if (duplicateMembers.size() > 0) {
/* 100 */       hasDuplicates = true;
/*     */     }
/* 102 */     if (members.size() > 0 && !hasDuplicates) {
/* 103 */       Constructor[] constrs = (Constructor[])classDef.getConstructors();
/*     */       
/* 105 */       Constructor defaultConstructor = null;
/* 106 */       for (int i = 0; i < constrs.length && fault == null; i++) {
/* 107 */         if ((constrs[i].getParameterTypes()).length == 0) {
/* 108 */           defaultConstructor = constrs[i];
/*     */         } else {
/*     */           SOAPStructureMember[] arrayOfSOAPStructureMember;
/* 111 */           if ((arrayOfSOAPStructureMember = constructorMatches(typeUri, wsdlUri, classDef, constrs[i], members, getMessageFlags)) != null)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 120 */             fault = createFault(typeUri, wsdlUri, classDef, arrayOfSOAPStructureMember); } 
/*     */         } 
/*     */       } 
/*     */       SOAPStructureMember[] soapMembers;
/* 124 */       if (fault == null && defaultConstructor != null && (soapMembers = constructorMatches(typeUri, wsdlUri, classDef, defaultConstructor, members, getMessageFlags)) != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 135 */         fault = createFault(typeUri, wsdlUri, classDef, soapMembers);
/*     */       }
/*     */     } 
/* 138 */     if (fault == null) {
/* 139 */       List<String> newMembers = new ArrayList();
/* 140 */       if (!members.containsKey("getMessage")) {
/* 141 */         newMembers.add("getMessage");
/*     */       }
/* 143 */       fault = createFault(typeUri, wsdlUri, classDef, addMessage(typeUri, wsdlUri, classDef, members, newMembers));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     this.faultMap.put(classDef.getName().toString(), fault);
/* 156 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPStructureMember[] constructorMatches(String typeUri, String wsdlUri, Class classDef, Constructor cstr, Map members, int getMessageFlags) {
/* 167 */     Class[] args = cstr.getParameterTypes();
/* 168 */     Object[] memberArray = members.values().toArray();
/* 169 */     boolean memberCountMatch = (args.length == memberArray.length);
/* 170 */     if (!memberCountMatch && (getMessageFlags == 0 || args.length != memberArray.length - 1))
/*     */     {
/*     */       
/* 173 */       return null; } 
/* 174 */     SOAPStructureMember[] soapMembers = new SOAPStructureMember[args.length];
/*     */     
/* 176 */     for (int i = 0; i < args.length; i++) {
/* 177 */       int j = 0;
/* 178 */       for (; j < memberArray.length && soapMembers[i] == null; 
/* 179 */         j++) {
/* 180 */         if (!memberCountMatch) {
/* 181 */           String memberName = ((Method)memberArray[j]).getName();
/* 182 */           if (getMessageFlags == 2 && memberName.equals("getMessage")) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 187 */         if (args[i].equals(((Method)memberArray[j]).getReturnType()))
/*     */         {
/* 189 */           soapMembers[i] = createSOAPMember(typeUri, wsdlUri, classDef, (Method)memberArray[j], i);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 198 */       if (soapMembers[i] == null)
/* 199 */         return null; 
/*     */     } 
/* 201 */     return soapMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPStructureMember createSOAPMember(String typeUri, String wsdlUri, Class classDef, Method member, int cstrPos) {
/* 211 */     String propertyName, packageName = classDef.getPackage().getName();
/* 212 */     RmiType memberType = RmiType.getRmiType(member.getReturnType());
/* 213 */     String readMethod = member.getName();
/* 214 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 215 */     if (namespaceURI == null) {
/* 216 */       namespaceURI = wsdlUri;
/*     */     }
/* 218 */     if (readMethod.startsWith("get")) {
/* 219 */       propertyName = StringUtils.decapitalize(readMethod.substring(3));
/*     */     } else {
/* 221 */       propertyName = StringUtils.decapitalize(readMethod.substring(2));
/* 222 */     }  QName propertyQName = new QName("", propertyName);
/* 223 */     SOAPType propertyType = this.rmiTypeModeler.modelTypeSOAP(typeUri, memberType);
/*     */     
/* 225 */     SOAPStructureMember soapMember = new SOAPStructureMember(propertyQName, propertyType, null);
/*     */     
/* 227 */     JavaStructureMember javaMember = new JavaStructureMember(propertyName, propertyType.getJavaType(), soapMember);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     soapMember.setJavaStructureMember(javaMember);
/* 233 */     javaMember.setConstructorPos(cstrPos);
/* 234 */     javaMember.setReadMethod(readMethod);
/* 235 */     soapMember.setJavaStructureMember(javaMember);
/* 236 */     return soapMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPStructureMember[] addMessage(String typeUri, String wsdlUri, Class classDef, Map members, List<Map.Entry> newMembers) {
/* 246 */     String packageName = classDef.getPackage().getName();
/* 247 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 248 */     if (namespaceURI == null)
/* 249 */       namespaceURI = wsdlUri; 
/* 250 */     SOAPStructureMember[] soapMembers = new SOAPStructureMember[members.size() + newMembers.size()];
/*     */ 
/*     */     
/* 253 */     Iterator<Map.Entry> iter = members.entrySet().iterator();
/* 254 */     for (int i = 0; iter.hasNext(); i++) {
/* 255 */       Method argMember = (Method)((Map.Entry)iter.next()).getValue();
/* 256 */       soapMembers[i] = createSOAPMember(typeUri, wsdlUri, classDef, argMember, -1);
/*     */     } 
/*     */     
/* 259 */     iter = newMembers.iterator();
/*     */     
/* 261 */     for (int j = members.size(); iter.hasNext(); j++) {
/* 262 */       String propertyName, tmp = (String)iter.next();
/*     */       
/* 264 */       if (tmp.startsWith("get")) {
/* 265 */         propertyName = StringUtils.decapitalize(tmp.substring(3));
/*     */       } else {
/* 267 */         propertyName = StringUtils.decapitalize(tmp.substring(2));
/* 268 */       }  QName propertyQName = new QName("", propertyName);
/* 269 */       SOAPSimpleType sOAPSimpleType = (this.rmiTypeModeler.getSOAPTypes()).XSD_STRING_SOAPTYPE;
/*     */       
/* 271 */       SOAPStructureMember soapMember = new SOAPStructureMember(propertyQName, (SOAPType)sOAPSimpleType, null);
/*     */       
/* 273 */       JavaStructureMember javaMember = new JavaStructureMember(propertyName, sOAPSimpleType.getJavaType(), soapMember);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       soapMember.setJavaStructureMember(javaMember);
/* 279 */       javaMember.setReadMethod(tmp);
/* 280 */       soapMember.setJavaStructureMember(javaMember);
/* 281 */       soapMembers[j] = soapMember;
/*     */     } 
/* 283 */     return soapMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fault createFault(String typeUri, String wsdlUri, Class classDef, SOAPStructureMember[] soapMembers) {
/* 292 */     String packageName = classDef.getPackage().getName();
/* 293 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 294 */     if (namespaceURI == null) {
/* 295 */       namespaceURI = typeUri;
/*     */     }
/* 297 */     Set sortedMembers = sortMembers(classDef, soapMembers);
/* 298 */     Fault fault = new Fault(Names.stripQualifier(classDef.getName()));
/* 299 */     SOAPOrderedStructureType sOAPOrderedStructureType = new SOAPOrderedStructureType(new QName(namespaceURI, fault.getName()), this.rmiTypeModeler.getSOAPVersion());
/*     */ 
/*     */ 
/*     */     
/* 303 */     namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 304 */     if (namespaceURI == null)
/* 305 */       namespaceURI = wsdlUri; 
/* 306 */     QName faultQName = new QName(namespaceURI, fault.getName());
/* 307 */     JavaException javaException = new JavaException(classDef.getName(), true, sOAPOrderedStructureType);
/*     */ 
/*     */     
/* 310 */     for (Iterator<SOAPStructureMember> iter = sortedMembers.iterator(); iter.hasNext(); ) {
/* 311 */       SOAPStructureMember member = iter.next();
/* 312 */       sOAPOrderedStructureType.add(member);
/* 313 */       javaException.add(member.getJavaStructureMember());
/*     */     } 
/*     */ 
/*     */     
/* 317 */     Block faultBlock = new Block(faultQName, (AbstractType)sOAPOrderedStructureType);
/* 318 */     fault.setBlock(faultBlock);
/* 319 */     sOAPOrderedStructureType.setJavaType((JavaType)javaException);
/* 320 */     fault.setJavaException(javaException);
/* 321 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set sortMembers(Class classDef, SOAPStructureMember[] unsortedMembers) {
/* 328 */     Set<SOAPStructureMember> sortedMembers = new TreeSet(new SOAPStructureMemberComparator(classDef));
/*     */ 
/*     */     
/* 331 */     for (int i = 0; i < unsortedMembers.length; i++) {
/* 332 */       sortedMembers.add(unsortedMembers[i]);
/*     */     }
/* 334 */     return sortedMembers;
/*     */   }
/*     */   
/*     */   public static class SOAPStructureMemberComparator implements Comparator { Class classDef;
/*     */     
/*     */     public SOAPStructureMemberComparator(Class classDef) {
/* 340 */       this.classDef = classDef;
/*     */     }
/*     */     
/*     */     public int compare(Object o1, Object o2) {
/* 344 */       SOAPStructureMember mem1 = (SOAPStructureMember)o1;
/* 345 */       SOAPStructureMember mem2 = (SOAPStructureMember)o2;
/* 346 */       return sort(mem1, mem2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int sort(SOAPStructureMember mem1, SOAPStructureMember mem2) {
/* 354 */       String key1 = mem1.getJavaStructureMember().getName();
/* 355 */       String key2 = mem2.getJavaStructureMember().getName();
/* 356 */       Class class1 = getDeclaringClass(this.classDef, mem1);
/* 357 */       Class<?> class2 = getDeclaringClass(this.classDef, mem2);
/* 358 */       if (class1.equals(class2))
/* 359 */         return key1.compareTo(key2); 
/* 360 */       if (class1.equals(Throwable.class) || class1.equals(Exception.class))
/*     */       {
/* 362 */         return 1; } 
/* 363 */       if (class1.isAssignableFrom(class2))
/* 364 */         return -1; 
/* 365 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Class getDeclaringClass(Class testClass, SOAPStructureMember member) {
/* 372 */       String readMethod = member.getJavaStructureMember().getReadMethod();
/* 373 */       Class retClass = RmiTypeModeler.getDeclaringClassMethod(testClass, readMethod, new Class[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 378 */       return retClass;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\ExceptionModeler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */