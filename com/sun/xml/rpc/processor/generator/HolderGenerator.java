/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttachmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralListType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPListType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
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
/*     */ public class HolderGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private Set types;
/*     */   private Port port;
/*     */   private Map generatedHolderClassMap;
/*     */   
/*     */   private void init() {
/*  72 */     this.generatedHolderClassMap = new HashMap<Object, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HolderGenerator() {}
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  81 */     return new HolderGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  89 */     return new HolderGenerator(model, config, properties, ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HolderGenerator(Model model, Configuration config, Properties properties) {
/*  96 */     super(model, config, properties);
/*  97 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HolderGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/* 105 */     this(model, config, properties);
/*     */   }
/*     */   
/*     */   protected void preVisitModel(Model model) throws Exception {
/* 109 */     this.types = new HashSet();
/*     */   }
/*     */   
/*     */   protected void postVisitModel(Model model) throws Exception {
/* 113 */     this.types = null;
/*     */   }
/*     */   
/*     */   protected void preVisitPort(Port port) throws Exception {
/* 117 */     super.preVisitPort(port);
/* 118 */     this.port = port;
/*     */   }
/*     */   
/*     */   protected void postVisitPort(Port port) throws Exception {
/* 122 */     this.port = null;
/* 123 */     super.postVisitPort(port);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(SOAPCustomType type) throws Exception {
/* 128 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 131 */     registerType((AbstractType)type);
/* 132 */     if (type.getJavaType().isHolder()) {
/* 133 */       generateHolder((AbstractType)type);
/*     */     }
/*     */   }
/*     */   
/*     */   public void visit(SOAPSimpleType type) throws Exception {
/* 138 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 141 */     registerType((AbstractType)type);
/* 142 */     if (type.getJavaType().isHolder()) {
/* 143 */       generateHolder((AbstractType)type);
/*     */     }
/*     */   }
/*     */   
/*     */   public void visit(SOAPAnyType type) throws Exception {
/* 148 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 151 */     registerType((AbstractType)type);
/* 152 */     if (type.getJavaType().isHolder()) {
/* 153 */       generateHolder((AbstractType)type);
/*     */     }
/*     */   }
/*     */   
/*     */   public void visit(SOAPEnumerationType type) throws Exception {
/* 158 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 161 */     registerType((AbstractType)type);
/* 162 */     if (type.getJavaType().isHolder()) {
/* 163 */       generateHolder((AbstractType)type);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(LiteralEnumerationType type) throws Exception {
/* 169 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 172 */     registerType((AbstractType)type);
/* 173 */     if (type.getJavaType().isHolder()) {
/* 174 */       generateHolder((AbstractType)type);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void visitSOAPArrayType(SOAPArrayType type) throws Exception {
/* 179 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 182 */     registerType((AbstractType)type);
/* 183 */     if (type.getJavaType().isHolder()) {
/* 184 */       generateHolder((AbstractType)type);
/*     */     }
/* 186 */     super.visitSOAPArrayType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void visitSOAPListType(SOAPListType type) throws Exception {
/* 191 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 194 */     registerType((AbstractType)type);
/* 195 */     if (type.getJavaType().isHolder()) {
/* 196 */       generateHolder((AbstractType)type);
/*     */     }
/* 198 */     super.visitSOAPListType(type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void visitLiteralListType(LiteralListType type) throws Exception {
/* 204 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 207 */     registerType((AbstractType)type);
/* 208 */     if (type.getJavaType().isHolder()) {
/* 209 */       generateHolder((AbstractType)type);
/*     */     }
/* 211 */     super.visitLiteralListType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void visitSOAPStructureType(SOAPStructureType type) throws Exception {
/* 216 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 219 */     registerType((AbstractType)type);
/* 220 */     if (type.getJavaType().isHolder()) {
/* 221 */       generateHolder((AbstractType)type);
/*     */     }
/* 223 */     super.visitSOAPStructureType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void visitLiteralSimpleType(LiteralSimpleType type) throws Exception {
/* 228 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 231 */     registerType((AbstractType)type);
/* 232 */     if (type.getJavaType().isHolder()) {
/* 233 */       generateHolder((AbstractType)type);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void visitLiteralSequenceType(LiteralSequenceType type) throws Exception {
/* 239 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 242 */     registerType((AbstractType)type);
/* 243 */     if (type.getJavaType().isHolder()) {
/* 244 */       generateHolder((AbstractType)type);
/*     */     }
/* 246 */     super.visitLiteralSequenceType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void preVisitLiteralAllType(LiteralAllType type) throws Exception {
/* 251 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 254 */     registerType((AbstractType)type);
/* 255 */     if (type.getJavaType().isHolder()) {
/* 256 */       generateHolder((AbstractType)type);
/*     */     }
/* 258 */     super.preVisitLiteralAllType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void preVisitLiteralFragmentType(LiteralFragmentType type) throws Exception {
/* 263 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 266 */     registerType((AbstractType)type);
/* 267 */     if (type.getJavaType().isHolder()) {
/* 268 */       generateHolder((AbstractType)type);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void visitLiteralAttachmentType(LiteralAttachmentType type) throws Exception {
/* 274 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 277 */     registerType((AbstractType)type);
/* 278 */     if (type.getJavaType().isHolder()) {
/* 279 */       generateHolder((AbstractType)type);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isRegistered(AbstractType type) {
/* 284 */     return this.types.contains(type);
/*     */   }
/*     */   
/*     */   private void registerType(AbstractType type) {
/* 288 */     this.types.add(type);
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateHolder(AbstractType type) {
/* 293 */     if (type.getJavaType().isHolderPresent()) {
/*     */       return;
/*     */     }
/*     */     try {
/* 297 */       String className = this.env.getNames().holderClassName(this.port, type);
/* 298 */       if (className.startsWith("javax.xml.rpc.holders.")) {
/*     */         return;
/*     */       }
/*     */       
/* 302 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 303 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 306 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 313 */       if (this.generatedHolderClassMap.get(className) == null) {
/*     */         
/* 315 */         GeneratedFileInfo fi = new GeneratedFileInfo();
/* 316 */         fi.setFile(classFile);
/* 317 */         fi.setType("Holder");
/* 318 */         this.env.addGeneratedFile(fi);
/*     */         
/* 320 */         IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */ 
/*     */         
/* 324 */         writePackage(out, className);
/* 325 */         out.pln();
/* 326 */         writeClassDecl(out, className);
/* 327 */         writeMembers(out, type);
/* 328 */         out.pln();
/* 329 */         writeClassConstructor(out, className, type);
/* 330 */         out.pOln("}");
/* 331 */         out.close();
/* 332 */         this.generatedHolderClassMap.put(className, type.getJavaType());
/*     */       } 
/* 334 */     } catch (Exception e) {
/* 335 */       fail(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeClassDecl(IndentingWriter p, String className) throws IOException {
/* 341 */     p.plnI("public class " + Names.stripQualifier(className) + " implements javax.xml.rpc.holders.Holder {");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, AbstractType type) throws IOException {
/* 349 */     p.pln("public " + type.getJavaType().getName() + " value;");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeClassConstructor(IndentingWriter p, String className, AbstractType type) throws IOException {
/* 357 */     p.pln("public " + Names.stripQualifier(className) + "() {");
/* 358 */     p.pln("}");
/* 359 */     p.pln();
/* 360 */     p.plnI("public " + Names.stripQualifier(className) + "(" + type.getJavaType().getName() + " " + this.env.getNames().getTypeMemberName(type) + ") {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     p.pln("this.value = " + this.env.getNames().getTypeMemberName(type) + ";");
/* 369 */     p.pOln("}");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\HolderGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */