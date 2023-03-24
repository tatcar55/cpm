/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
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
/*     */ public class FaultExceptionBuilderGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private Set operations;
/*     */   private Port port;
/*     */   
/*     */   public FaultExceptionBuilderGenerator() {}
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  63 */     return new FaultExceptionBuilderGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  71 */     return new FaultExceptionBuilderGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FaultExceptionBuilderGenerator(Model model, Configuration config, Properties properties) {
/*  78 */     super(model, config, properties);
/*     */   }
/*     */   
/*     */   public void preVisitModel(Model model) {
/*  82 */     this.operations = new HashSet();
/*     */   }
/*     */   
/*     */   public void postVisitModel(Model model) {
/*  86 */     this.operations = null;
/*     */   }
/*     */   
/*     */   protected void preVisitPort(Port port) throws Exception {
/*  90 */     super.preVisitPort(port);
/*  91 */     this.port = port;
/*     */   }
/*     */   
/*     */   protected void postVisitPort(Port port) throws Exception {
/*  95 */     this.port = null;
/*  96 */     super.postVisitPort(port);
/*     */   }
/*     */   
/*     */   public void preVisitOperation(Operation operation) throws Exception {
/* 100 */     if (!isRegistered(operation))
/* 101 */       registerFault(operation); 
/*     */   }
/*     */   private boolean isRegistered(Operation operation) {
/* 104 */     return this.operations.contains(getInputMessageName(operation));
/*     */   }
/*     */   
/*     */   private String getInputMessageName(Operation operation) {
/* 108 */     QName value = (QName)operation.getRequest().getProperty("com.sun.xml.rpc.processor.model.WSDLMessageName");
/*     */ 
/*     */     
/* 111 */     if (value != null) {
/* 112 */       return value.getLocalPart();
/*     */     }
/* 114 */     return operation.getName().getLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerFault(Operation operation) throws Exception {
/* 119 */     this.operations.add(getInputMessageName(operation));
/* 120 */     generateBuilderForOperation(operation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateBuilderForOperation(Operation operation) throws IOException, GeneratorException {
/* 130 */     if (needsBuilder(operation)) {
/* 131 */       writeBuilderForOperation(operation);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean needsBuilder(Operation operation) {
/* 136 */     Iterator faults = operation.getFaults();
/* 137 */     return (faults != null) ? faults.hasNext() : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeBuilderForOperation(Operation operation) throws IOException, GeneratorException {
/* 143 */     String className = this.env.getNames().faultBuilderClassName(this.servicePackage, this.port, operation);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 149 */       log("Class " + className + " exists. Not overriding.");
/*     */       return;
/*     */     } 
/* 152 */     File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 160 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 161 */       fi.setFile(classFile);
/* 162 */       fi.setType("FaultExceptionBuilder");
/* 163 */       this.env.addGeneratedFile(fi);
/*     */       
/* 165 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 168 */       writeObjectBuilderCode(out, operation, className);
/* 169 */       out.close();
/* 170 */     } catch (IOException e) {
/* 171 */       fail("generator.cant.write", classFile.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObjectBuilderCode(IndentingWriter p, Operation operation, String className) throws IOException, GeneratorException {
/* 181 */     log("writing object builder for: " + operation.getName());
/*     */     
/* 183 */     writePackage(p, className);
/* 184 */     writeImports(p);
/* 185 */     p.pln();
/* 186 */     writeObjectClassDecl(p, className);
/* 187 */     writeMembers(p, operation);
/* 188 */     p.pln();
/* 189 */     writeMemberGateTypeMethod(p);
/* 190 */     p.pln();
/* 191 */     writeConstructMethod(p);
/* 192 */     p.pln();
/* 193 */     writeSetMemberMethod(p, operation);
/* 194 */     p.pln();
/* 195 */     writeInitializeMethod(p, operation);
/* 196 */     p.pln();
/* 197 */     writeGetSetInstanceMethods(p);
/* 198 */     p.pOln("}");
/*     */   }
/*     */   
/*     */   private void writeImports(IndentingWriter p) throws IOException {
/* 202 */     p.pln("import com.sun.xml.rpc.encoding.*;");
/* 203 */     p.pln("import com.sun.xml.rpc.soap.message.SOAPFaultInfo;");
/* 204 */     p.pln("import java.lang.IllegalArgumentException;");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObjectClassDecl(IndentingWriter p, String className) throws IOException {
/* 209 */     p.plnI("public final class " + Names.stripQualifier(className) + " implements com.sun.xml.rpc.encoding.SOAPInstanceBuilder {");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, Operation operation) throws IOException {
/* 217 */     p.pln("private SOAPFaultInfo instance = null;");
/* 218 */     p.pln("private java.lang.Object detail;");
/* 219 */     p.pln("// this is the index of the fault deserialized");
/* 220 */     p.pln("private int index = -1;");
/*     */     
/* 222 */     Iterator<Fault> faults = operation.getFaults();
/*     */     
/* 224 */     for (int i = 0; faults.hasNext(); ) {
/* 225 */       Fault fault = faults.next();
/* 226 */       p.pln("private static final int " + fault.getJavaException().getName().toUpperCase().replace('.', '_') + "_INDEX = " + i + ";");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 234 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeMemberGateTypeMethod(IndentingWriter p) throws IOException {
/* 240 */     p.plnI("public int memberGateType(int memberIndex) {");
/* 241 */     p.pln("return GATES_INITIALIZATION + REQUIRES_COMPLETION;");
/* 242 */     p.pOln("}");
/*     */   }
/*     */   
/*     */   private void writeConstructMethod(IndentingWriter p) throws IOException {
/* 246 */     p.plnI("public void construct() {");
/* 247 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeSetMemberMethod(IndentingWriter p, Operation operation) throws IOException {
/* 252 */     p.plnI("public void setMember(int index, java.lang.Object memberValue) {");
/* 253 */     p.pln("this.index = index;");
/* 254 */     p.pln("detail = memberValue;");
/* 255 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeInitializeMethod(IndentingWriter p, Operation operation) throws IOException {
/* 260 */     p.plnI("public void initialize() {");
/* 261 */     p.plnI("switch (index) {");
/*     */     
/* 263 */     Iterator<Fault> faults = operation.getFaults();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     for (int i = 0; faults.hasNext(); ) {
/* 269 */       Fault fault = faults.next();
/* 270 */       JavaException javaException = fault.getJavaException();
/* 271 */       p.plnI("case " + fault.getJavaException().getName().toUpperCase().replace('.', '_') + "_INDEX" + ":");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       p.pln("instance.setDetail(detail);");
/* 279 */       p.pln("break;");
/* 280 */       p.pO();
/*     */     } 
/* 282 */     p.pOln("}");
/* 283 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeGetSetInstanceMethods(IndentingWriter p) throws IOException {
/* 288 */     p.plnI("public void setInstance(java.lang.Object instance) {");
/* 289 */     p.pln("this.instance = (SOAPFaultInfo)instance;");
/* 290 */     p.pOln("}");
/* 291 */     p.pln();
/* 292 */     p.plnI("public java.lang.Object getInstance() {");
/* 293 */     p.pln("return instance;");
/* 294 */     p.pOln("}");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\FaultExceptionBuilderGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */