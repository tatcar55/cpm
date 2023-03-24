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
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.processor.util.StringUtils;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExceptionModeler103
/*     */   extends ExceptionModelerBase
/*     */   implements RmiConstants
/*     */ {
/*     */   private RmiTypeModeler rmiTypeModeler;
/*     */   
/*     */   public ExceptionModeler103(RmiModeler modeler, RmiTypeModeler typeModeler) {
/*  76 */     super(modeler);
/*  77 */     this.rmiTypeModeler = typeModeler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fault createFault(String typeUri, String wsdlUri, Class classDef) {
/*  84 */     String exceptionName = classDef.getName();
/*  85 */     Fault fault = (Fault)this.faultMap.get(exceptionName);
/*  86 */     if (fault != null)
/*  87 */       return fault; 
/*  88 */     HashMap<Object, Object> members = new HashMap<Object, Object>();
/*  89 */     collectMembers(classDef, members);
/*  90 */     int getMessageFlags = 0;
/*  91 */     if (members.containsKey("getMessage")) {
/*  92 */       Iterator<Map.Entry> iterator = members.entrySet().iterator();
/*  93 */       while (iterator.hasNext()) {
/*  94 */         Method member = (Method)((Map.Entry)iterator.next()).getValue();
/*     */         
/*  96 */         if (member.getReturnType().equals(String.class) && !member.getName().equals("getMessage")) {
/*     */           
/*  98 */           members.remove("getMessage");
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 103 */     if (members.size() == 0) {
/* 104 */       members.put("getMessage", GET_MESSAGE_METHOD);
/*     */     }
/* 106 */     if (members.containsKey("getMessage")) {
/* 107 */       getMessageFlags = 2;
/*     */     }
/* 109 */     boolean hasDuplicates = false;
/* 110 */     Set duplicateMembers = getDuplicateMembers(members);
/* 111 */     if (duplicateMembers.size() > 0) {
/* 112 */       hasDuplicates = true;
/*     */     }
/* 114 */     if (members.size() > 0 && !hasDuplicates) {
/* 115 */       Constructor[] constrs = (Constructor[])classDef.getConstructors();
/*     */       
/* 117 */       for (int i = 0; i < constrs.length && fault == null; i++) {
/* 118 */         SOAPStructureMember[] soapMembers; if ((soapMembers = constructorMatches(typeUri, wsdlUri, classDef, constrs[i], members, getMessageFlags)) != null)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 127 */           fault = createFault(typeUri, wsdlUri, classDef, soapMembers);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     if (fault == null) {
/* 133 */       List<String> newMembers = new ArrayList();
/* 134 */       if (!members.containsKey("getMessage")) {
/* 135 */         newMembers.add("getMessage");
/*     */       }
/* 137 */       if (!members.containsKey("getLocalizedMessage")) {
/* 138 */         newMembers.add("getLocalizedMessage");
/*     */       }
/* 140 */       fault = createFault(typeUri, wsdlUri, classDef, addMessage(typeUri, wsdlUri, classDef, members, newMembers));
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
/* 152 */     this.faultMap.put(classDef.getName().toString(), fault);
/* 153 */     return fault;
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
/* 164 */     Class[] args = cstr.getParameterTypes();
/* 165 */     Object[] memberArray = members.values().toArray();
/* 166 */     boolean memberCountMatch = (args.length == memberArray.length);
/* 167 */     if (!memberCountMatch && (getMessageFlags == 0 || args.length != memberArray.length - 1))
/*     */     {
/*     */       
/* 170 */       return null; } 
/* 171 */     SOAPStructureMember[] soapMembers = new SOAPStructureMember[args.length];
/*     */     
/* 173 */     for (int i = 0; i < args.length; i++) {
/* 174 */       int j = 0;
/* 175 */       for (; j < memberArray.length && soapMembers[i] == null; 
/* 176 */         j++) {
/* 177 */         if (!memberCountMatch) {
/* 178 */           String memberName = ((Method)memberArray[j]).getName();
/* 179 */           if (getMessageFlags == 2 && memberName.equals("getMessage")) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 184 */         if (args[i].equals(((Method)memberArray[j]).getReturnType()))
/*     */         {
/* 186 */           soapMembers[i] = createSOAPMember(typeUri, wsdlUri, classDef, (Method)memberArray[j], i);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 195 */       if (soapMembers[i] == null)
/* 196 */         return null; 
/*     */     } 
/* 198 */     return soapMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fault createFault(String typeUri, String wsdlUri, Class classDef, SOAPStructureMember[] soapMembers) {
/* 207 */     String packageName = classDef.getPackage().getName();
/* 208 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 209 */     if (namespaceURI == null) {
/* 210 */       namespaceURI = typeUri;
/*     */     }
/* 212 */     Set sortedMembers = sortMembers(classDef, soapMembers);
/* 213 */     Fault fault = new Fault(Names.stripQualifier(classDef.getName()));
/* 214 */     SOAPOrderedStructureType sOAPOrderedStructureType = new SOAPOrderedStructureType(new QName(namespaceURI, fault.getName()));
/*     */ 
/*     */     
/* 217 */     namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 218 */     if (namespaceURI == null)
/* 219 */       namespaceURI = wsdlUri; 
/* 220 */     QName faultQName = new QName(namespaceURI, fault.getName());
/* 221 */     JavaException javaException = new JavaException(classDef.getName(), true, sOAPOrderedStructureType);
/*     */ 
/*     */     
/* 224 */     for (Iterator<SOAPStructureMember> iter = sortedMembers.iterator(); iter.hasNext(); ) {
/* 225 */       SOAPStructureMember member = iter.next();
/* 226 */       sOAPOrderedStructureType.add(member);
/* 227 */       javaException.add(member.getJavaStructureMember());
/*     */     } 
/*     */     
/* 230 */     Block faultBlock = new Block(faultQName, (AbstractType)sOAPOrderedStructureType);
/* 231 */     fault.setBlock(faultBlock);
/* 232 */     sOAPOrderedStructureType.setJavaType((JavaType)javaException);
/* 233 */     fault.setJavaException(javaException);
/* 234 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPStructureMember[] addMessage(String typeUri, String wsdlUri, Class classDef, Map members, List<Map.Entry> newMembers) {
/* 244 */     String packageName = classDef.getPackage().getName();
/* 245 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 246 */     if (namespaceURI == null)
/* 247 */       namespaceURI = wsdlUri; 
/* 248 */     SOAPStructureMember[] soapMembers = new SOAPStructureMember[members.size() + newMembers.size()];
/*     */ 
/*     */     
/* 251 */     Iterator<Map.Entry> iter = members.entrySet().iterator(); int i;
/* 252 */     for (i = 0; iter.hasNext(); i++) {
/* 253 */       Method argMember = (Method)((Map.Entry)iter.next()).getValue();
/* 254 */       soapMembers[i] = createSOAPMember(typeUri, wsdlUri, classDef, argMember, -1);
/*     */     } 
/*     */     
/* 257 */     iter = newMembers.iterator();
/* 258 */     for (i = members.size(); iter.hasNext(); i++) {
/* 259 */       String propertyName = StringUtils.decapitalize(((String)iter.next()).substring(3));
/*     */       
/* 261 */       QName propertyQName = new QName("", propertyName);
/* 262 */       SOAPSimpleType sOAPSimpleType = (this.rmiTypeModeler.getSOAPTypes()).XSD_STRING_SOAPTYPE;
/*     */       
/* 264 */       SOAPStructureMember soapMember = new SOAPStructureMember(propertyQName, (SOAPType)sOAPSimpleType, null);
/*     */       
/* 266 */       JavaStructureMember javaMember = new JavaStructureMember(propertyName, sOAPSimpleType.getJavaType(), soapMember);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       soapMember.setJavaStructureMember(javaMember);
/* 272 */       javaMember.setReadMethod("get" + StringUtils.capitalize(propertyName));
/*     */       
/* 274 */       soapMember.setJavaStructureMember(javaMember);
/* 275 */       soapMembers[i] = soapMember;
/*     */     } 
/* 277 */     return soapMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPStructureMember createSOAPMember(String typeUri, String wsdlUri, Class classDef, Method member, int cstrPos) {
/* 286 */     String packageName = classDef.getPackage().getName();
/* 287 */     RmiType memberType = RmiType.getRmiType(member.getReturnType());
/* 288 */     String readMethod = member.getName();
/* 289 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 290 */     if (namespaceURI == null)
/* 291 */       namespaceURI = wsdlUri; 
/* 292 */     String propertyName = StringUtils.decapitalize(readMethod.substring(3));
/* 293 */     QName propertyQName = new QName("", propertyName);
/* 294 */     SOAPType propertyType = this.rmiTypeModeler.modelTypeSOAP(typeUri, memberType);
/*     */     
/* 296 */     SOAPStructureMember soapMember = new SOAPStructureMember(propertyQName, propertyType, null);
/*     */     
/* 298 */     JavaStructureMember javaMember = new JavaStructureMember(propertyName, propertyType.getJavaType(), soapMember);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     soapMember.setJavaStructureMember(javaMember);
/* 304 */     javaMember.setConstructorPos(cstrPos);
/* 305 */     javaMember.setReadMethod(readMethod);
/* 306 */     soapMember.setJavaStructureMember(javaMember);
/* 307 */     return soapMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set sortMembers(Class classDef, SOAPStructureMember[] unsortedMembers) {
/* 314 */     Set<SOAPStructureMember> sortedMembers = new TreeSet(new SOAPStructureMemberComparator(classDef));
/*     */ 
/*     */     
/* 317 */     for (int i = 0; i < unsortedMembers.length; i++) {
/* 318 */       sortedMembers.add(unsortedMembers[i]);
/*     */     }
/* 320 */     return sortedMembers;
/*     */   }
/*     */   
/*     */   protected static Set getDuplicateMembers(Map members) {
/* 324 */     Set<RmiType> types = new HashSet();
/* 325 */     Set<Method> duplicateMembers = new HashSet();
/* 326 */     Iterator<Map.Entry> iter = members.entrySet().iterator();
/*     */ 
/*     */ 
/*     */     
/* 330 */     while (iter.hasNext()) {
/* 331 */       Method member = (Method)((Map.Entry)iter.next()).getValue();
/* 332 */       RmiType type = RmiType.getRmiType(member.getReturnType());
/* 333 */       String memberName = member.getName();
/* 334 */       if (types.contains(type)) {
/* 335 */         duplicateMembers.add(member); continue;
/*     */       } 
/* 337 */       types.add(type);
/*     */     } 
/*     */     
/* 340 */     return duplicateMembers;
/*     */   }
/*     */   
/*     */   public void collectMembers(Class<?> classDef, Map<String, Method> members) {
/*     */     try {
/* 345 */       if (this.defRuntimeException.isAssignableFrom(classDef)) {
/* 346 */         throw new ModelerException("rmimodeler.must.not.extend.runtimeexception", classDef.getName());
/*     */       }
/*     */ 
/*     */       
/* 350 */       if (classDef.equals(Throwable.class) || classDef.equals(Object.class)) {
/*     */         return;
/*     */       }
/*     */       
/* 354 */       if (classDef.getSuperclass() != null)
/* 355 */         collectMembers(classDef.getSuperclass(), members); 
/* 356 */       Method[] methods = classDef.getMethods();
/*     */       
/* 358 */       for (int i = 0; i < methods.length; i++) {
/* 359 */         Class<?> decClass = methods[i].getDeclaringClass();
/* 360 */         if (!Modifier.isStatic(methods[i].getModifiers()) && !decClass.equals(Throwable.class) && !decClass.equals(Object.class)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 365 */           String memberName = methods[i].getName();
/* 366 */           if (memberName.startsWith("get") && (methods[i].getParameterTypes()).length == 0)
/*     */           {
/* 368 */             if (!members.containsKey(memberName) && !memberName.equals("getLocalizedMessage"))
/*     */             {
/* 370 */               members.put(memberName, methods[i]); } 
/*     */           }
/*     */         } 
/*     */       } 
/* 374 */     } catch (Exception e) {
/* 375 */       throw new ModelerException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class SOAPStructureMemberComparator implements Comparator { Class classDef;
/*     */     
/*     */     public SOAPStructureMemberComparator(Class classDef) {
/* 382 */       this.classDef = classDef;
/*     */     }
/*     */     
/*     */     public int compare(Object o1, Object o2) {
/* 386 */       SOAPStructureMember mem1 = (SOAPStructureMember)o1;
/* 387 */       SOAPStructureMember mem2 = (SOAPStructureMember)o2;
/* 388 */       return sort(mem1, mem2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int sort(SOAPStructureMember mem1, SOAPStructureMember mem2) {
/* 396 */       String key1 = mem1.getJavaStructureMember().getName();
/* 397 */       String key2 = mem2.getJavaStructureMember().getName();
/* 398 */       Class class1 = getDeclaringClass(this.classDef, mem1);
/* 399 */       Class<?> class2 = getDeclaringClass(this.classDef, mem2);
/* 400 */       if (class1.equals(class2))
/* 401 */         return key1.compareTo(key2); 
/* 402 */       if (class1.equals(Throwable.class) || class1.equals(Exception.class))
/*     */       {
/* 404 */         return 1; } 
/* 405 */       if (class1.isAssignableFrom(class2))
/* 406 */         return -1; 
/* 407 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Class getDeclaringClass(Class testClass, SOAPStructureMember member) {
/* 414 */       String readMethod = member.getJavaStructureMember().getReadMethod();
/* 415 */       Class retClass = RmiTypeModeler.getDeclaringClassMethod(testClass, readMethod, new Class[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 420 */       return retClass;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\ExceptionModeler103.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */