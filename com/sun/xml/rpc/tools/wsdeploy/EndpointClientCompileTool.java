/*     */ package com.sun.xml.rpc.tools.wsdeploy;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.Processor;
/*     */ import com.sun.xml.rpc.processor.ProcessorAction;
/*     */ import com.sun.xml.rpc.processor.ProcessorNotificationListener;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.config.ModelFileModelInfo;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.XMLModelWriter;
/*     */ import com.sun.xml.rpc.spi.tools.Configuration;
/*     */ import com.sun.xml.rpc.spi.tools.ModelInfo;
/*     */ import com.sun.xml.rpc.spi.tools.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.tools.wscompile.CompileTool;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EndpointClientCompileTool
/*     */   extends CompileTool
/*     */ {
/*     */   protected WebServicesInfo webServicesInfo;
/*     */   protected File targetDirectory;
/*     */   protected boolean useModel;
/*     */   protected String additionalClasspath;
/*     */   protected Hashtable hashtable;
/*     */   protected ArrayList vector;
/*     */   protected EndpointClientInfo endpointClient;
/*     */   protected ArrayList clientList;
/*     */   protected boolean localUseWSIBasicProfile;
/*     */   
/*     */   public EndpointClientCompileTool(OutputStream out, String program, WebServicesInfo wsi, ArrayList list, File dir, String target, String classpath, ProcessorNotificationListener l) {
/*  62 */     super(out, program);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     this.localUseWSIBasicProfile = false; this.webServicesInfo = wsi; this.targetDirectory = dir; this.additionalClasspath = classpath; this.listener = l; this.clientList = list; this.endpointClient = this.clientList.get(0); this.useModel = (this.endpointClient.getModel() != null); this.targetVersion = target;
/*     */   }
/* 262 */   protected void beforeHook() { String targetPath = this.targetDirectory.getAbsolutePath(); this.nonclassDestDir = new File(targetPath + FS + "WEB-INF"); this.userClasspath = targetPath + FS + "WEB-INF" + FS + "lib"; this.destDir = new File(this.userClasspath); this.userClasspath = targetPath + FS + "WEB-INF" + FS + "lib" + PS + targetPath + FS + "WEB-INF" + FS + "classes"; if ((new File(targetPath + FS + "WEB-INF" + FS + "lib")).exists()) { File[] fs = (new File(targetPath + FS + "WEB-INF" + FS + "lib")).listFiles(); for (int counter = 0; counter < fs.length; counter++) this.userClasspath += PS + fs[counter];  }  if (this.additionalClasspath != null && this.additionalClasspath.length() > 0) this.userClasspath += PS + this.additionalClasspath;  if (!this.useModel) { this.modelFile = new File(makeModelFileName()); } else if (this.targetVersion != null) { onWarning(getMessage("wscompile.warning.ignoringTargetVersionForModel", this.endpointClient.getModel(), this.targetVersion)); this.targetVersion = null; }  this.serializerInfix = "_" + this.endpointClient.getName() + "_"; this.keepGenerated = true; this.compilerDebug = false; this.compilerOptimize = true; super.beforeHook(); } protected void withModelHook() {} public Configuration createConfiguration() throws Exception { Configuration config = new Configuration((ProcessorEnvironment)this.environment); if (this.useModel) { ModelFileModelInfo modelInfo = new ModelFileModelInfo(); modelInfo.setLocation(makeAbsolute(this.endpointClient.getModel())); config.setModelInfo((ModelInfo)modelInfo); }  return (Configuration)config; } protected String makeTargetNamespaceURI() { String base = this.webServicesInfo.getTargetNamespaceBase(); if (base.endsWith("/") || base.startsWith("urn:")) return base + this.endpointClient.getName();  return base + "/" + this.endpointClient.getName(); } protected String makeTypeNamespaceURI() { String base = this.webServicesInfo.getTypeNamespaceBase(); if (base.endsWith("/") || base.startsWith("urn:")) return base + this.endpointClient.getName();  return base + "/" + this.endpointClient.getName(); } protected String makeModelFileName() { return this.targetDirectory.getAbsolutePath() + FS + "WEB-INF" + FS + this.endpointClient.getName() + "_model.xml.gz"; } protected String makeJavaPackageName() { return "jaxrpc.generated." + this.environment.getNames().validJavaPackageName(this.endpointClient.getName()); } private static final String PS = System.getProperty("path.separator");
/* 263 */   protected String makeAbsolute(String s) { if (s == null) return null;  return (new File(this.targetDirectory.getAbsolutePath() + s)).getAbsolutePath(); } protected String makeAppRelative(File f) { if (f == null) return null;  String s = f.getAbsolutePath(); String target = this.targetDirectory.getAbsolutePath(); if (s.startsWith(target)) return s.substring(target.length()).replace(FSCHAR, '/');  return null; } protected File findGeneratedFileEndingWith(String s) { Iterator<GeneratedFileInfo> iter = this.environment.getGeneratedFiles(); while (iter.hasNext()) { GeneratedFileInfo fileInfo = iter.next(); File file = fileInfo.getFile(); if (file.getAbsolutePath().endsWith(s)) return file;  }  return null; } protected void registerProcessorActions(Processor processor) { if (!this.useModel) try { processor.add((ProcessorAction)new XMLModelWriter(this.modelFile)); } catch (FileNotFoundException e) {}  processor.add(getAction("service.interface.generator")); processor.add(getAction("service.generator")); processor.add(getAction("custom.class.generator")); processor.add(getAction("enumeration.encoder.generator")); processor.add(getAction("literal.object.serializer.generator")); processor.add(getAction("soap.fault.serializer.generator")); processor.add(getAction("fault.exception.builder.generator")); if (this.delegate != null) this.delegate.postRegisterProcessorActions();  } public void onError(Localizable msg) { if (this.delegate != null) this.delegate.preOnError();  report(getMessage("wscompile.error", this.localizer.localize(msg))); } public void onWarning(Localizable msg) { report(getMessage("wscompile.warning", this.localizer.localize(msg))); } public void onInfo(Localizable msg) { report(getMessage("wscompile.info", this.localizer.localize(msg))); } private static final char PSCHAR = System.getProperty("path.separator").charAt(0);
/*     */   
/* 265 */   private static final String FS = System.getProperty("file.separator");
/* 266 */   private static final char FSCHAR = System.getProperty("file.separator").charAt(0);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wsdeploy\EndpointClientCompileTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */