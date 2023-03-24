/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomExceptionGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private Set faults;
/*     */   
/*     */   public CustomExceptionGenerator() {}
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  62 */     return new CustomExceptionGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  70 */     return new CustomExceptionGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CustomExceptionGenerator(Model model, Configuration config, Properties properties) {
/*  77 */     super(model, config, properties);
/*     */   }
/*     */   
/*     */   protected void preVisitModel(Model model) throws Exception {
/*  81 */     this.faults = new HashSet();
/*     */   }
/*     */   
/*     */   protected void postVisitModel(Model model) throws Exception {
/*  85 */     this.faults = null;
/*     */   }
/*     */   
/*     */   protected void preVisitFault(Fault fault) throws Exception {
/*  89 */     if (isRegistered(fault))
/*     */       return; 
/*  91 */     registerFault(fault);
/*  92 */     if (fault.getParentFault() != null) {
/*  93 */       preVisitFault(fault.getParentFault());
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isRegistered(Fault fault) {
/*  98 */     return this.faults.contains(fault.getJavaException().getName());
/*     */   }
/*     */   
/*     */   private void registerFault(Fault fault) {
/* 102 */     this.faults.add(fault.getJavaException().getName());
/* 103 */     generateCustomException(fault);
/*     */   }
/*     */   
/*     */   private void generateCustomException(Fault fault) {
/* 107 */     if (fault.getJavaException().isPresent())
/*     */       return; 
/* 109 */     log("generating CustomException for: " + fault.getJavaException().getName());
/*     */ 
/*     */     
/*     */     try {
/* 113 */       String className = this.env.getNames().customExceptionClassName(fault);
/* 114 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/*     */         
/* 116 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 119 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 127 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 128 */       fi.setFile(classFile);
/* 129 */       fi.setType("Exception");
/* 130 */       this.env.addGeneratedFile(fi);
/*     */       
/* 132 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 135 */       writePackage(out, className);
/* 136 */       out.pln();
/* 137 */       JavaException javaException = fault.getJavaException();
/*     */       
/* 139 */       writeClassDecl(out, className, (JavaStructureType)javaException);
/* 140 */       writeMembers(out, fault);
/* 141 */       out.pln();
/* 142 */       writeClassConstructor(out, className, fault);
/* 143 */       out.pln();
/* 144 */       writeGetter(out, fault);
/* 145 */       out.pOln("}");
/* 146 */       out.close();
/* 147 */     } catch (Exception e) {
/* 148 */       fail(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeClassDecl(IndentingWriter p, String className, JavaStructureType javaStruct) throws IOException {
/* 157 */     JavaStructureType superclass = javaStruct.getSuperclass();
/* 158 */     if (superclass != null) {
/* 159 */       p.plnI("public class " + Names.stripQualifier(className) + " extends " + superclass.getName() + " {");
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 166 */       p.plnI("public class " + Names.stripQualifier(className) + " extends java.lang.Exception {");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, Fault fault) throws IOException {
/* 175 */     Iterator<JavaStructureMember> members = fault.getJavaException().getMembers();
/*     */ 
/*     */     
/* 178 */     while (members.hasNext()) {
/* 179 */       JavaStructureMember member = members.next();
/* 180 */       if (!member.isInherited()) {
/* 181 */         p.pln("private " + member.getType().getName() + " " + member.getName() + ";");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     p.pln();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeClassConstructor(IndentingWriter p, String className, Fault fault) throws IOException {
/* 197 */     JavaException javaException = fault.getJavaException();
/*     */ 
/*     */     
/* 200 */     p.p("public " + Names.stripQualifier(className) + "(");
/* 201 */     Iterator<JavaStructureMember> members = javaException.getMembers();
/*     */     
/* 203 */     int stringTypeCount = 0;
/* 204 */     String stringMemberName = null; int i;
/* 205 */     for (i = 0; members.hasNext(); i++) {
/* 206 */       JavaStructureMember member = members.next();
/* 207 */       if (i > 0)
/* 208 */         p.p(", "); 
/* 209 */       p.p(member.getType().getName() + " " + member.getName());
/* 210 */       if (member.getType().getName().equals("java.lang.String")) {
/*     */ 
/*     */ 
/*     */         
/* 214 */         stringTypeCount++;
/* 215 */         stringMemberName = member.getName();
/*     */       } 
/*     */     } 
/* 218 */     p.plnI(") {");
/* 219 */     if (fault.getParentFault() != null) {
/* 220 */       members = javaException.getMembers();
/* 221 */       i = 0;
/* 222 */       while (members.hasNext()) {
/* 223 */         JavaStructureMember member = members.next();
/* 224 */         if (member.isInherited()) {
/* 225 */           if (i++ == 0) {
/* 226 */             p.p("super(");
/*     */           } else {
/* 228 */             p.p(", ");
/* 229 */           }  p.p(member.getName());
/*     */         } 
/*     */       } 
/* 232 */       if (i > 0)
/* 233 */         p.pln(");"); 
/* 234 */     } else if (stringTypeCount == 1) {
/* 235 */       p.pln("super(" + stringMemberName + ");");
/*     */     } 
/* 237 */     members = fault.getJavaException().getMembers();
/* 238 */     for (i = 0; members.hasNext(); i++) {
/* 239 */       JavaStructureMember member = members.next();
/* 240 */       if (!member.isInherited()) {
/* 241 */         p.pln("this." + member.getName() + " = " + member.getName() + ";");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeGetter(IndentingWriter p, Fault fault) throws IOException {
/* 254 */     Iterator<JavaStructureMember> members = fault.getJavaException().getMembers();
/*     */ 
/*     */     
/* 257 */     int i = 0;
/* 258 */     while (members.hasNext()) {
/* 259 */       if (i > 0)
/* 260 */         p.pln(); 
/* 261 */       JavaStructureMember member = members.next();
/* 262 */       if (!member.isInherited()) {
/* 263 */         p.plnI("public " + member.getType().getName() + " " + member.getReadMethod() + "() {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 269 */         p.pln("return " + member.getName() + ";");
/* 270 */         p.pOln("}");
/* 271 */         i++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\CustomExceptionGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */