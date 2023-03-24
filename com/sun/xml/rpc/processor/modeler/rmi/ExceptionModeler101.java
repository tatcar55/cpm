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
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ExceptionModeler101
/*     */   extends ExceptionModelerBase
/*     */   implements RmiConstants
/*     */ {
/*     */   private RmiTypeModeler rmiTypeModeler;
/*     */   
/*     */   public ExceptionModeler101(RmiModeler modeler, RmiTypeModeler typeModeler) {
/*  74 */     super(modeler);
/*  75 */     this.rmiTypeModeler = typeModeler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fault createFault(String typeUri, String wsdlUri, Class classDef) {
/*  82 */     String exceptionName = classDef.getName();
/*  83 */     Fault fault = (Fault)this.faultMap.get(exceptionName);
/*  84 */     if (fault != null)
/*  85 */       return fault; 
/*  86 */     HashMap<Object, Object> members = new HashMap<Object, Object>();
/*  87 */     collectMembers(classDef, members);
/*  88 */     int getMessageFlags = 0;
/*  89 */     if (members.containsKey("getMessage")) {
/*  90 */       Iterator<Map.Entry> iterator = members.entrySet().iterator();
/*  91 */       while (iterator.hasNext()) {
/*  92 */         Method member = (Method)((Map.Entry)iterator.next()).getValue();
/*     */         
/*  94 */         if (member.getReturnType().equals(String.class) && !member.getName().equals("getMessage")) {
/*     */           
/*  96 */           members.remove("getMessage");
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 101 */     if (members.size() == 0) {
/* 102 */       members.put("getMessage", GET_MESSAGE_METHOD);
/*     */     }
/* 104 */     if (members.containsKey("getMessage")) {
/* 105 */       getMessageFlags = 2;
/*     */     }
/* 107 */     boolean hasDuplicates = false;
/* 108 */     Set duplicateMembers = getDuplicateMembers(members);
/* 109 */     if (duplicateMembers.size() > 0) {
/* 110 */       hasDuplicates = true;
/*     */     }
/* 112 */     if (members.size() > 0 && !hasDuplicates) {
/* 113 */       Constructor[] constrs = (Constructor[])classDef.getConstructors();
/*     */       
/* 115 */       for (int i = 0; i < constrs.length && fault == null; i++) {
/* 116 */         SOAPStructureMember[] soapMembers; if ((soapMembers = constructorMatches(typeUri, wsdlUri, classDef, constrs[i], members, getMessageFlags)) != null)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 125 */           fault = createFault(typeUri, wsdlUri, classDef, soapMembers);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     if (fault == null) {
/* 131 */       List<String> newMembers = new ArrayList();
/* 132 */       if (!members.containsKey("getMessage")) {
/* 133 */         newMembers.add("getMessage");
/*     */       }
/* 135 */       if (!members.containsKey("getLocalizedMessage")) {
/* 136 */         newMembers.add("getLocalizedMessage");
/*     */       }
/* 138 */       fault = createFault(typeUri, wsdlUri, classDef, addMessage(typeUri, wsdlUri, classDef, members, newMembers));
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
/* 150 */     this.faultMap.put(classDef.getName().toString(), fault);
/* 151 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPStructureMember[] addMessage(String typeUri, String wsdlUri, Class classDef, Map members, List<Map.Entry> newMembers) {
/* 161 */     String packageName = classDef.getPackage().getName();
/* 162 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 163 */     if (namespaceURI == null)
/* 164 */       namespaceURI = wsdlUri; 
/* 165 */     SOAPStructureMember[] soapMembers = new SOAPStructureMember[members.size() + newMembers.size()];
/*     */ 
/*     */     
/* 168 */     Iterator<Map.Entry> iter = members.entrySet().iterator(); int i;
/* 169 */     for (i = 0; iter.hasNext(); i++) {
/* 170 */       Method argMember = (Method)((Map.Entry)iter.next()).getValue();
/* 171 */       soapMembers[i] = createSOAPMember(typeUri, wsdlUri, classDef, argMember, -1);
/*     */     } 
/*     */     
/* 174 */     iter = newMembers.iterator();
/* 175 */     for (i = members.size(); iter.hasNext(); i++) {
/* 176 */       String propertyName = StringUtils.decapitalize(((String)iter.next()).substring(3));
/*     */       
/* 178 */       QName propertyQName = new QName("", propertyName);
/* 179 */       SOAPSimpleType sOAPSimpleType = (this.rmiTypeModeler.getSOAPTypes()).XSD_STRING_SOAPTYPE;
/*     */       
/* 181 */       SOAPStructureMember soapMember = new SOAPStructureMember(propertyQName, (SOAPType)sOAPSimpleType, null);
/*     */       
/* 183 */       JavaStructureMember javaMember = new JavaStructureMember(propertyName, sOAPSimpleType.getJavaType(), soapMember);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       soapMember.setJavaStructureMember(javaMember);
/* 189 */       javaMember.setReadMethod("get" + StringUtils.capitalize(propertyName));
/*     */       
/* 191 */       soapMember.setJavaStructureMember(javaMember);
/* 192 */       soapMembers[i] = soapMember;
/*     */     } 
/* 194 */     return soapMembers;
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
/* 205 */     Class[] args = cstr.getParameterTypes();
/* 206 */     Object[] memberArray = members.values().toArray();
/* 207 */     boolean memberCountMatch = (args.length == memberArray.length);
/* 208 */     if (!memberCountMatch && (getMessageFlags == 0 || args.length != memberArray.length - 1))
/*     */     {
/*     */       
/* 211 */       return null; } 
/* 212 */     SOAPStructureMember[] soapMembers = new SOAPStructureMember[args.length];
/*     */     
/* 214 */     for (int i = 0; i < args.length; i++) {
/* 215 */       int j = 0;
/* 216 */       for (; j < memberArray.length && soapMembers[i] == null; 
/* 217 */         j++) {
/* 218 */         if (!memberCountMatch) {
/* 219 */           String memberName = ((Method)memberArray[j]).getName();
/* 220 */           if (getMessageFlags == 2 && memberName.equals("getMessage")) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 225 */         if (args[i].equals(((Method)memberArray[j]).getReturnType()))
/*     */         {
/* 227 */           soapMembers[i] = createSOAPMember(typeUri, wsdlUri, classDef, (Method)memberArray[j], i);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 236 */       if (soapMembers[i] == null)
/* 237 */         return null; 
/*     */     } 
/* 239 */     return soapMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fault createFault(String typeUri, String wsdlUri, Class classDef, SOAPStructureMember[] soapMembers) {
/*     */     Block faultBlock;
/* 248 */     String packageName = classDef.getPackage().getName();
/* 249 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 250 */     if (namespaceURI == null)
/* 251 */       namespaceURI = typeUri; 
/* 252 */     Fault fault = new Fault(Names.stripQualifier(classDef.getName()));
/* 253 */     SOAPOrderedStructureType sOAPOrderedStructureType = new SOAPOrderedStructureType(new QName(namespaceURI, fault.getName()));
/*     */ 
/*     */     
/* 256 */     namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 257 */     if (namespaceURI == null)
/* 258 */       namespaceURI = wsdlUri; 
/* 259 */     QName faultQName = new QName(namespaceURI, fault.getName());
/* 260 */     JavaException javaException = new JavaException(classDef.getName(), true, sOAPOrderedStructureType);
/*     */     
/* 262 */     for (int i = 0; i < soapMembers.length; i++) {
/* 263 */       sOAPOrderedStructureType.add(soapMembers[i]);
/* 264 */       javaException.add(soapMembers[i].getJavaStructureMember());
/*     */     } 
/*     */     
/* 267 */     if (soapMembers.length == 1) {
/* 268 */       faultBlock = new Block(faultQName, (AbstractType)soapMembers[0].getType());
/*     */     } else {
/* 270 */       faultBlock = new Block(faultQName, (AbstractType)sOAPOrderedStructureType);
/*     */     } 
/* 272 */     fault.setBlock(faultBlock);
/* 273 */     sOAPOrderedStructureType.setJavaType((JavaType)javaException);
/* 274 */     fault.setJavaException(javaException);
/* 275 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPStructureMember createSOAPMember(String typeUri, String wsdlUri, Class classDef, Method member, int cstrPos) {
/* 285 */     String packageName = classDef.getPackage().getName();
/* 286 */     RmiType memberType = RmiType.getRmiType(member.getReturnType());
/* 287 */     String readMethod = member.getName();
/* 288 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 289 */     if (namespaceURI == null)
/* 290 */       namespaceURI = wsdlUri; 
/* 291 */     String propertyName = StringUtils.decapitalize(readMethod.substring(3));
/* 292 */     QName propertyQName = new QName("", propertyName);
/* 293 */     SOAPType propertyType = this.rmiTypeModeler.modelTypeSOAP(typeUri, memberType);
/*     */     
/* 295 */     SOAPStructureMember soapMember = new SOAPStructureMember(propertyQName, propertyType, null);
/*     */     
/* 297 */     JavaStructureMember javaMember = new JavaStructureMember(propertyName, propertyType.getJavaType(), soapMember);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     soapMember.setJavaStructureMember(javaMember);
/* 303 */     javaMember.setConstructorPos(cstrPos);
/* 304 */     javaMember.setReadMethod(readMethod);
/* 305 */     soapMember.setJavaStructureMember(javaMember);
/* 306 */     return soapMember;
/*     */   }
/*     */   
/*     */   public void collectMembers(Class<?> classDef, Map<String, Method> members) {
/*     */     try {
/* 311 */       if (this.defRuntimeException.isAssignableFrom(classDef)) {
/* 312 */         throw new ModelerException("rmimodeler.must.not.extend.runtimeexception", classDef.getName());
/*     */       }
/*     */ 
/*     */       
/* 316 */       Method[] methods = classDef.getMethods();
/*     */       
/* 318 */       for (int i = 0; i < methods.length; i++) {
/* 319 */         Class<?> decClass = methods[i].getDeclaringClass();
/* 320 */         if (!Modifier.isStatic(methods[i].getModifiers()) && !decClass.equals(Throwable.class) && !decClass.equals(Object.class)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 325 */           String memberName = methods[i].getName();
/* 326 */           if (memberName.startsWith("get") && (methods[i].getParameterTypes()).length == 0)
/*     */           {
/* 328 */             if (!members.containsKey(memberName) && !memberName.equals("getLocalizedMessage"))
/*     */             {
/* 330 */               members.put(memberName, methods[i]); } 
/*     */           }
/*     */         } 
/*     */       } 
/* 334 */     } catch (Exception e) {
/* 335 */       throw new ModelerException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static Set getDuplicateMembers(Map members) {
/* 340 */     Set<RmiType> types = new HashSet();
/* 341 */     Set<Method> duplicateMembers = new HashSet();
/* 342 */     Iterator<Map.Entry> iter = members.entrySet().iterator();
/*     */ 
/*     */ 
/*     */     
/* 346 */     while (iter.hasNext()) {
/* 347 */       Method member = (Method)((Map.Entry)iter.next()).getValue();
/* 348 */       RmiType type = RmiType.getRmiType(member.getReturnType());
/* 349 */       String memberName = member.getName();
/* 350 */       if (types.contains(type)) {
/* 351 */         duplicateMembers.add(member); continue;
/*     */       } 
/* 353 */       types.add(type);
/*     */     } 
/*     */     
/* 356 */     return duplicateMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Fault getMappedFault(String className) {
/* 364 */     return (Fault)this.faultMap.get(className);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\ExceptionModeler101.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */