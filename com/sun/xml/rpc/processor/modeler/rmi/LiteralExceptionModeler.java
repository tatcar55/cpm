/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
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
/*     */ 
/*     */ public class LiteralExceptionModeler
/*     */   extends ExceptionModelerBase
/*     */   implements RmiConstants
/*     */ {
/*     */   protected LiteralTypeModeler literalTypeModeler;
/*     */   
/*     */   public LiteralExceptionModeler(RmiModeler modeler, LiteralTypeModeler typeModeler) {
/*  65 */     super(modeler);
/*  66 */     this.literalTypeModeler = typeModeler;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkForJavaExceptions(String className) {}
/*     */   
/*     */   public Fault createFault(String typeUri, String wsdlUri, Class classDef) {
/*  73 */     String exceptionName = classDef.getName();
/*  74 */     Fault fault = (Fault)this.faultMap.get(exceptionName);
/*  75 */     if (fault != null)
/*  76 */       return fault; 
/*  77 */     HashMap<Object, Object> members = new HashMap<Object, Object>();
/*  78 */     collectMembers(classDef, members);
/*  79 */     int getMessageFlags = 0;
/*  80 */     if (members.containsKey("getMessage")) {
/*  81 */       Iterator<Map.Entry> iterator = members.entrySet().iterator();
/*  82 */       while (iterator.hasNext()) {
/*  83 */         Method member = (Method)((Map.Entry)iterator.next()).getValue();
/*     */         
/*  85 */         if (member.getReturnType().equals(String.class) && !member.getName().equals("getMessage")) {
/*     */           
/*  87 */           members.remove("getMessage");
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  92 */     if (members.size() == 0) {
/*  93 */       members.put("getMessage", GET_MESSAGE_METHOD);
/*     */     }
/*  95 */     if (members.containsKey("getMessage")) {
/*  96 */       getMessageFlags = 2;
/*     */     }
/*  98 */     boolean hasDuplicates = false;
/*  99 */     Set duplicateMembers = getDuplicateMembers(members);
/* 100 */     if (duplicateMembers.size() > 0) {
/* 101 */       hasDuplicates = true;
/*     */     }
/* 103 */     if (members.size() > 0 && !hasDuplicates) {
/* 104 */       Constructor[] constrs = (Constructor[])classDef.getConstructors();
/*     */       
/* 106 */       Constructor defaultConstructor = null;
/* 107 */       for (int i = 0; i < constrs.length && fault == null; i++) {
/* 108 */         if ((constrs[i].getParameterTypes()).length == 0) {
/* 109 */           defaultConstructor = constrs[i];
/*     */         } else {
/*     */           LiteralElementMember[] arrayOfLiteralElementMember;
/* 112 */           if ((arrayOfLiteralElementMember = constructorMatches(typeUri, wsdlUri, classDef, constrs[i], members, getMessageFlags)) != null)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 121 */             fault = createFault(typeUri, wsdlUri, classDef, arrayOfLiteralElementMember); } 
/*     */         } 
/*     */       } 
/*     */       LiteralElementMember[] literalMembers;
/* 125 */       if (fault == null && defaultConstructor != null && (literalMembers = constructorMatches(typeUri, wsdlUri, classDef, defaultConstructor, members, getMessageFlags)) != null)
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
/* 136 */         fault = createFault(typeUri, wsdlUri, classDef, literalMembers);
/*     */       }
/*     */     } 
/* 139 */     if (fault == null) {
/* 140 */       List<String> newMembers = new ArrayList();
/* 141 */       if (!members.containsKey("getMessage")) {
/* 142 */         newMembers.add("getMessage");
/*     */       }
/* 144 */       fault = createFault(typeUri, wsdlUri, classDef, addMessage(typeUri, wsdlUri, classDef, members, newMembers));
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
/* 156 */     this.faultMap.put(classDef.getName().toString(), fault);
/* 157 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LiteralElementMember[] constructorMatches(String typeUri, String wsdlUri, Class classDef, Constructor cstr, Map members, int getMessageFlags) {
/* 168 */     Class[] args = cstr.getParameterTypes();
/* 169 */     Object[] memberArray = members.values().toArray();
/* 170 */     boolean memberCountMatch = (args.length == memberArray.length);
/* 171 */     if (!memberCountMatch && (getMessageFlags == 0 || args.length != memberArray.length - 1))
/*     */     {
/*     */       
/* 174 */       return null;
/*     */     }
/* 176 */     LiteralElementMember[] literalMembers = new LiteralElementMember[args.length];
/*     */     
/* 178 */     for (int i = 0; i < args.length; i++) {
/* 179 */       int j = 0;
/* 180 */       for (; j < memberArray.length && literalMembers[i] == null; 
/* 181 */         j++) {
/* 182 */         if (!memberCountMatch) {
/* 183 */           String memberName = ((Method)memberArray[j]).getName();
/* 184 */           if (getMessageFlags == 2 && memberName.equals("getMessage")) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 189 */         if (args[i].equals(((Method)memberArray[j]).getReturnType()))
/*     */         {
/* 191 */           literalMembers[i] = createLiteralMember(typeUri, wsdlUri, classDef, (Method)memberArray[j], i);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 200 */       if (literalMembers[i] == null) {
/* 201 */         return null;
/*     */       }
/*     */     } 
/* 204 */     return literalMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralElementMember createLiteralMember(String typeUri, String wsdlUri, Class classDef, Method member, int cstrPos) {
/* 214 */     String propertyName, packageName = classDef.getPackage().getName();
/* 215 */     RmiType memberType = RmiType.getRmiType(member.getReturnType());
/* 216 */     String readMethod = member.getName();
/* 217 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 218 */     if (namespaceURI == null) {
/* 219 */       namespaceURI = wsdlUri;
/*     */     }
/* 221 */     if (readMethod.startsWith("get")) {
/* 222 */       propertyName = StringUtils.decapitalize(readMethod.substring(3));
/*     */     } else {
/* 224 */       propertyName = StringUtils.decapitalize(readMethod.substring(2));
/* 225 */     }  QName propertyQName = new QName("", propertyName);
/* 226 */     LiteralElementMember literalMember = this.literalTypeModeler.modelTypeLiteral(propertyQName, typeUri, memberType);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     JavaStructureMember javaMember = literalMember.getJavaStructureMember();
/* 232 */     javaMember.setConstructorPos(cstrPos);
/* 233 */     javaMember.setReadMethod(readMethod);
/* 234 */     return literalMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralElementMember[] addMessage(String typeUri, String wsdlUri, Class classDef, Map members, List<Map.Entry> newMembers) {
/* 244 */     String packageName = classDef.getPackage().getName();
/* 245 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 246 */     if (namespaceURI == null)
/* 247 */       namespaceURI = wsdlUri; 
/* 248 */     LiteralElementMember[] literalMembers = new LiteralElementMember[members.size() + newMembers.size()];
/*     */ 
/*     */     
/* 251 */     Iterator<Map.Entry> iter = members.entrySet().iterator();
/* 252 */     for (int i = 0; iter.hasNext(); i++) {
/* 253 */       Method argMember = (Method)((Map.Entry)iter.next()).getValue();
/* 254 */       literalMembers[i] = createLiteralMember(typeUri, wsdlUri, classDef, argMember, -1);
/*     */     } 
/*     */ 
/*     */     
/* 258 */     iter = newMembers.iterator();
/*     */     
/* 260 */     for (int j = members.size(); iter.hasNext(); j++) {
/* 261 */       String propertyName, tmp = (String)iter.next();
/*     */       
/* 263 */       if (tmp.startsWith("get")) {
/* 264 */         propertyName = StringUtils.decapitalize(tmp.substring(3));
/*     */       } else {
/* 266 */         propertyName = StringUtils.decapitalize(tmp.substring(2));
/* 267 */       }  QName propertyQName = new QName("", propertyName);
/* 268 */       LiteralSimpleType literalSimpleType = (this.literalTypeModeler.getLiteralTypes()).XSD_STRING_LITERALTYPE;
/*     */       
/* 270 */       LiteralElementMember literalMember = new LiteralElementMember(propertyQName, (LiteralType)literalSimpleType, null);
/*     */       
/* 272 */       literalMember.setNillable(isNewMemberNillable(literalMember));
/* 273 */       JavaStructureMember javaMember = new JavaStructureMember(propertyName, literalSimpleType.getJavaType(), literalMember);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       literalMember.setJavaStructureMember(javaMember);
/*     */       
/* 280 */       javaMember.setReadMethod(tmp);
/* 281 */       literalMember.setJavaStructureMember(javaMember);
/* 282 */       literalMembers[j] = literalMember;
/*     */     } 
/* 284 */     return literalMembers;
/*     */   }
/*     */   
/*     */   protected boolean isNewMemberNillable(LiteralElementMember member) {
/* 288 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fault createFault(String typeUri, String wsdlUri, Class classDef, LiteralElementMember[] literalMembers) {
/* 298 */     String packageName = classDef.getPackage().getName();
/* 299 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 300 */     if (namespaceURI == null) {
/* 301 */       namespaceURI = typeUri;
/*     */     }
/* 303 */     Set sortedMembers = sortMembers(classDef, literalMembers);
/* 304 */     Fault fault = new Fault(Names.stripQualifier(classDef.getName()));
/* 305 */     LiteralSequenceType literalStruct = new LiteralSequenceType(new QName(namespaceURI, fault.getName()));
/*     */     
/* 307 */     namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 308 */     if (namespaceURI == null)
/* 309 */       namespaceURI = wsdlUri; 
/* 310 */     QName faultQName = new QName(namespaceURI, fault.getName());
/* 311 */     JavaException javaException = new JavaException(classDef.getName(), true, literalStruct);
/*     */ 
/*     */     
/* 314 */     for (Iterator<LiteralElementMember> iter = sortedMembers.iterator(); iter.hasNext(); ) {
/* 315 */       LiteralElementMember member = iter.next();
/* 316 */       literalStruct.add(member);
/* 317 */       javaException.add(member.getJavaStructureMember());
/*     */     } 
/*     */ 
/*     */     
/* 321 */     Block faultBlock = new Block(faultQName, (AbstractType)literalStruct);
/* 322 */     fault.setBlock(faultBlock);
/* 323 */     literalStruct.setJavaType((JavaType)javaException);
/* 324 */     fault.setJavaException(javaException);
/* 325 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set sortMembers(Class classDef, LiteralElementMember[] unsortedMembers) {
/* 331 */     Set<LiteralElementMember> sortedMembers = new TreeSet(new LiteralElementMemberComparator(classDef));
/*     */ 
/*     */     
/* 334 */     for (int i = 0; i < unsortedMembers.length; i++) {
/* 335 */       sortedMembers.add(unsortedMembers[i]);
/*     */     }
/* 337 */     return sortedMembers;
/*     */   }
/*     */   
/*     */   public static class LiteralElementMemberComparator implements Comparator { Class classDef;
/*     */     
/*     */     public LiteralElementMemberComparator(Class classDef) {
/* 343 */       this.classDef = classDef;
/*     */     }
/*     */     
/*     */     public int compare(Object o1, Object o2) {
/* 347 */       LiteralElementMember mem1 = (LiteralElementMember)o1;
/* 348 */       LiteralElementMember mem2 = (LiteralElementMember)o2;
/* 349 */       return sort(mem1, mem2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int sort(LiteralElementMember mem1, LiteralElementMember mem2) {
/* 357 */       String key1 = mem1.getJavaStructureMember().getName();
/* 358 */       String key2 = mem2.getJavaStructureMember().getName();
/* 359 */       Class class1 = getDeclaringClass(this.classDef, mem1);
/* 360 */       Class<?> class2 = getDeclaringClass(this.classDef, mem2);
/* 361 */       if (class1.equals(class2))
/* 362 */         return key1.compareTo(key2); 
/* 363 */       if (class1.equals(Throwable.class) || class1.equals(Exception.class))
/*     */       {
/* 365 */         return 1; } 
/* 366 */       if (class1.isAssignableFrom(class2))
/* 367 */         return -1; 
/* 368 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Class getDeclaringClass(Class testClass, LiteralElementMember member) {
/* 375 */       String readMethod = member.getJavaStructureMember().getReadMethod();
/* 376 */       Class retClass = RmiTypeModeler.getDeclaringClassMethod(testClass, readMethod, new Class[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 381 */       return retClass;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\LiteralExceptionModeler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */