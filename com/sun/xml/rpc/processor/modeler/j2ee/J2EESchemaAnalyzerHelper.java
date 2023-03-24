/*     */ package com.sun.xml.rpc.processor.modeler.j2ee;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.J2EEModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.ExceptionModelerBase;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.MemberInfo;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.RmiType;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.RmiTypeModeler;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.RmiUtils;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzerBase;
/*     */ import com.sun.xml.rpc.processor.schema.ComplexTypeDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.ElementDeclarationComponent;
/*     */ import com.sun.xml.rpc.processor.schema.TypeDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.processor.util.StringUtils;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ public class J2EESchemaAnalyzerHelper
/*     */ {
/*     */   private J2EESchemaAnalyzerIf base;
/*     */   private JavaSimpleTypeCreator javaSimpleTypeCreator;
/*     */   private J2EEModelInfo _j2eeModelInfo;
/*     */   private ProcessorEnvironment _env;
/*     */   
/*     */   public J2EESchemaAnalyzerHelper(J2EESchemaAnalyzerIf base, J2EEModelInfo modelInfo, ProcessorEnvironment env, JavaSimpleTypeCreator javaTypes) {
/*  73 */     this.base = base;
/*  74 */     this._j2eeModelInfo = modelInfo;
/*  75 */     this._env = env;
/*  76 */     this.javaSimpleTypeCreator = new JavaSimpleTypeCreator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJavaNameOfType(TypeDefinitionComponent component, QName nameHint) {
/*  82 */     String className = this._j2eeModelInfo.javaNameOfType(component);
/*  83 */     return getLoadableClassName(className);
/*     */   }
/*     */   
/*     */   private String getLoadableClassName(String className) {
/*  87 */     if (className != null) {
/*     */       
/*     */       try {
/*     */         
/*  91 */         className = RmiUtils.getLoadableClassName(className, this._env.getClassLoader());
/*     */       }
/*  93 */       catch (ClassNotFoundException ce) {}
/*     */     }
/*     */     
/*  96 */     return className;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateModifiers(JavaStructureType javaStructureType) {
/*     */     try {
/* 103 */       String javaName = javaStructureType.getName();
/* 104 */       Class typeClass = RmiUtils.getClassForName(javaName, this._env.getClassLoader());
/* 105 */       if (typeClass.isInterface() || Modifier.isAbstract(typeClass.getModifiers()))
/*     */       {
/* 107 */         javaStructureType.setAbstract(true);
/*     */       }
/* 109 */     } catch (ClassNotFoundException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJavaNameOfSOAPStructureType(SOAPStructureType structureType, TypeDefinitionComponent component, QName nameHint) {
/* 117 */     return getJavaNameOfType(component, nameHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SchemaAnalyzerBase.SchemaJavaMemberInfo getJavaMemberInfo(TypeDefinitionComponent component, ElementDeclarationComponent element) {
/* 124 */     return (SchemaAnalyzerBase.SchemaJavaMemberInfo)this._j2eeModelInfo.javaMemberInfo(component, element.getName().getLocalPart());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJavaNameOfElementType(LiteralStructuredType structureType, TypeDefinitionComponent component, QName nameHint) {
/* 133 */     String isAnonymousName = (String)structureType.getProperty("com.sun.xml.rpc.processor.model.AnonymousTypeName");
/*     */ 
/*     */     
/* 136 */     String className = this._j2eeModelInfo.javaNameOfElementType(structureType.getName(), isAnonymousName);
/*     */ 
/*     */     
/* 139 */     return getLoadableClassName(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SchemaAnalyzerBase.SchemaJavaMemberInfo getJavaMemberOfElementInfo(QName typeName, String memberName) {
/* 146 */     return (SchemaAnalyzerBase.SchemaJavaMemberInfo)this._j2eeModelInfo.javaMemberOfElementInfo(typeName, memberName);
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
/*     */   protected SOAPType getSOAPMemberType(ComplexTypeDefinitionComponent component, SOAPStructureType structureType, ElementDeclarationComponent element, QName nameHint, boolean occursZeroOrOne) {
/* 158 */     SOAPType memberType = this.base.getSuperSOAPMemberType(component, structureType, element, nameHint, occursZeroOrOne);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     JavaType javaType = getMemberJavaType((AbstractType)memberType, (AbstractType)structureType, component, element);
/*     */     
/* 167 */     if (javaType != null) {
/* 168 */       memberType.setJavaType(javaType);
/*     */     }
/* 170 */     return memberType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JavaType getMemberJavaType(AbstractType memberType, AbstractType structureType, ComplexTypeDefinitionComponent component, ElementDeclarationComponent element) {
/*     */     JavaSimpleType javaSimpleType;
/* 178 */     JavaType javaType = null;
/*     */     try {
/* 180 */       Class<?> javaClass = RmiUtils.getClassForName(structureType.getJavaType().getName(), this._env.getClassLoader());
/*     */ 
/*     */ 
/*     */       
/* 184 */       SchemaAnalyzerBase.SchemaJavaMemberInfo memberInfo = getJavaMemberInfo((TypeDefinitionComponent)component, element);
/*     */       
/* 186 */       Map<Object, Object> members = null;
/* 187 */       if (Exception.class.isAssignableFrom(javaClass)) {
/* 188 */         members = new HashMap<Object, Object>();
/* 189 */         Map<Object, Object> exceptionMembers = new HashMap<Object, Object>();
/* 190 */         ExceptionModelerBase.collectExceptionMembers(javaClass, exceptionMembers);
/*     */ 
/*     */         
/* 193 */         Iterator<Map.Entry> iter = exceptionMembers.entrySet().iterator();
/*     */         
/* 195 */         while (iter.hasNext()) {
/* 196 */           String propertyName; Method member = (Method)((Map.Entry)iter.next()).getValue();
/* 197 */           RmiType returnType = RmiType.getRmiType(member.getReturnType());
/*     */           
/* 199 */           String readMethod = member.getName();
/*     */           
/* 201 */           if (readMethod.startsWith("get")) {
/* 202 */             propertyName = StringUtils.decapitalize(readMethod.substring(3));
/*     */           } else {
/*     */             
/* 205 */             propertyName = StringUtils.decapitalize(readMethod.substring(2));
/*     */           } 
/* 207 */           MemberInfo memInfo = new MemberInfo(propertyName, returnType, false);
/*     */           
/* 209 */           memInfo.setReadMethod(readMethod);
/* 210 */           members.put(propertyName, memInfo);
/*     */         } 
/*     */       } else {
/* 213 */         members = RmiTypeModeler.collectMembers(this._env, RmiType.getRmiType(javaClass));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 218 */       RmiType rmiType = getMemberType(memberInfo, members);
/* 219 */       if (rmiType == null) {
/* 220 */         memberInfo = this.base.getSuperJavaMemberInfo((TypeDefinitionComponent)component, element);
/* 221 */         rmiType = getMemberType(memberInfo, members);
/*     */       } 
/*     */ 
/*     */       
/* 225 */       if (rmiType != null && rmiType.getTypeCode() != 9) {
/* 226 */         String typeString = rmiType.typeString(false);
/* 227 */         if (!memberType.getJavaType().getName().equals(typeString) && 
/* 228 */           memberType.getJavaType() instanceof JavaSimpleType) {
/* 229 */           javaSimpleType = this.javaSimpleTypeCreator.getJavaSimpleType(typeString);
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 234 */     catch (ClassNotFoundException e) {}
/*     */ 
/*     */     
/* 237 */     return (JavaType)javaSimpleType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LiteralType getLiteralMemberType(ComplexTypeDefinitionComponent component, LiteralType memberType, ElementDeclarationComponent element, LiteralStructuredType structureType) {
/* 246 */     JavaType javaType = getMemberJavaType((AbstractType)memberType, (AbstractType)structureType, component, element);
/*     */     
/* 248 */     if (javaType != null) {
/* 249 */       memberType.setJavaType(javaType);
/*     */     }
/* 251 */     return memberType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RmiType getMemberType(SchemaAnalyzerBase.SchemaJavaMemberInfo javaMemberInfo, Map members) {
/* 257 */     RmiType type = null;
/*     */     
/* 259 */     String memberName = javaMemberInfo.javaMemberName;
/* 260 */     for (Iterator<Map.Entry> iter = members.entrySet().iterator(); iter.hasNext(); ) {
/* 261 */       MemberInfo memInfo = (MemberInfo)((Map.Entry)iter.next()).getValue();
/* 262 */       if (memInfo.getName().equals(memberName)) {
/* 263 */         return memInfo.getType();
/*     */       }
/* 265 */       if (memInfo.getWriteMethod() != null && memInfo.getWriteMethod().substring(3).equalsIgnoreCase(memberName))
/*     */       {
/*     */         
/* 268 */         return memInfo.getType();
/*     */       }
/*     */     } 
/*     */     
/* 272 */     return type;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\J2EESchemaAnalyzerHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */