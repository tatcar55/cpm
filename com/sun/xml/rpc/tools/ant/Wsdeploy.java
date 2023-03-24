/*     */ package com.sun.xml.rpc.tools.ant;
/*     */ 
/*     */ import com.sun.xml.rpc.tools.wsdeploy.DeployTool;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.tools.ant.BuildException;
/*     */ import org.apache.tools.ant.Task;
/*     */ import org.apache.tools.ant.taskdefs.Execute;
/*     */ import org.apache.tools.ant.taskdefs.ExecuteStreamHandler;
/*     */ import org.apache.tools.ant.taskdefs.LogOutputStream;
/*     */ import org.apache.tools.ant.taskdefs.LogStreamHandler;
/*     */ import org.apache.tools.ant.taskdefs.MatchingTask;
/*     */ import org.apache.tools.ant.types.Commandline;
/*     */ import org.apache.tools.ant.types.CommandlineJava;
/*     */ import org.apache.tools.ant.types.Path;
/*     */ import org.apache.tools.ant.types.Reference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Wsdeploy
/*     */   extends MatchingTask
/*     */ {
/*     */   public String getF() {
/*  57 */     return getFeatures();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setF(String features) {
/*  64 */     setFeatures(features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private String features = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFeatures() {
/*  78 */     return this.features;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeatures(String features) {
/*  85 */     this.features = features;
/*     */   }
/*     */ 
/*     */   
/*  89 */   protected Path compileClasspath = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getClasspath() {
/*  95 */     return this.compileClasspath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClasspath(Path classpath) {
/* 102 */     if (this.compileClasspath == null) {
/* 103 */       this.compileClasspath = classpath;
/*     */     } else {
/* 105 */       this.compileClasspath.append(classpath);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path createClasspath() {
/* 113 */     if (this.compileClasspath == null) {
/* 114 */       this.compileClasspath = new Path(this.project);
/*     */     }
/* 116 */     return this.compileClasspath.createPath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClasspathRef(Reference r) {
/* 123 */     createClasspath().setRefid(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getCP() {
/* 131 */     return getClasspath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCP(Path classpath) {
/* 138 */     setClasspath(classpath);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean fork = false;
/*     */   protected String jvmargs;
/*     */   
/*     */   public boolean getFork() {
/* 146 */     return this.fork;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFork(boolean fork) {
/* 151 */     this.fork = fork;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmargs() {
/* 159 */     return this.jvmargs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJvmargs(String jvmargs) {
/* 164 */     this.jvmargs = jvmargs;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean keep = false;
/*     */ 
/*     */   
/*     */   public boolean getKeep() {
/* 172 */     return this.keep;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKeep(boolean keep) {
/* 177 */     this.keep = keep;
/*     */   }
/*     */ 
/*     */   
/* 181 */   private File tmpDir = null;
/*     */ 
/*     */   
/*     */   public File getTmpDir() {
/* 185 */     return this.tmpDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTmpDir(File tmpDir) {
/* 190 */     this.tmpDir = tmpDir;
/*     */   }
/*     */ 
/*     */   
/* 194 */   private String source = null;
/*     */   
/*     */   public String getSource() {
/* 197 */     return this.source;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSource(String version) {
/* 202 */     this.source = version;
/*     */   }
/*     */ 
/*     */   
/* 206 */   private File outWarFile = null;
/*     */ 
/*     */   
/*     */   public File getOutWarFile() {
/* 210 */     return this.outWarFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOutWarFile(File outWarFile) {
/* 215 */     this.outWarFile = outWarFile;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean verbose = false;
/*     */ 
/*     */   
/*     */   public boolean getVerbose() {
/* 223 */     return this.verbose;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVerbose(boolean verbose) {
/* 228 */     this.verbose = verbose;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean version = false;
/*     */ 
/*     */   
/*     */   public boolean getVersion() {
/* 236 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVersion(boolean version) {
/* 241 */     this.version = version;
/*     */   }
/*     */ 
/*     */   
/* 245 */   private File warFile = null;
/*     */ 
/*     */   
/*     */   public File getInWarFile() {
/* 249 */     return this.warFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInWarFile(File warFile) {
/* 254 */     this.warFile = warFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean includeAntRuntime = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIncludeantruntime(boolean include) {
/* 265 */     this.includeAntRuntime = include;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIncludeantruntime() {
/* 273 */     return this.includeAntRuntime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean includeJavaRuntime = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIncludejavaruntime(boolean include) {
/* 285 */     this.includeJavaRuntime = include;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIncludejavaruntime() {
/* 293 */     return this.includeJavaRuntime;
/*     */   }
/*     */ 
/*     */   
/*     */   private Path generateCompileClasspath() {
/* 298 */     Path classpath = new Path(getProject());
/*     */     
/* 300 */     if (getClasspath() == null) {
/* 301 */       if (getIncludeantruntime()) {
/* 302 */         classpath.addExisting(Path.systemClasspath);
/*     */       }
/*     */     }
/* 305 */     else if (getIncludeantruntime()) {
/* 306 */       classpath.addExisting(getClasspath().concatSystemClasspath("last"));
/*     */     } else {
/*     */       
/* 309 */       classpath.addExisting(getClasspath().concatSystemClasspath("ignore"));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 314 */     if (getIncludejavaruntime()) {
/*     */ 
/*     */       
/* 317 */       classpath.addExisting(new Path(null, System.getProperty("java.home") + File.separator + "lib" + File.separator + "rt.jar"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 324 */       classpath.addExisting(new Path(null, System.getProperty("java.home") + File.separator + "jre" + File.separator + "lib" + File.separator + "rt.jar"));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     return classpath;
/*     */   }
/*     */   
/*     */   private Commandline setupWsdeployCommand() {
/* 335 */     Commandline cmd = setupWsdeployArgs();
/*     */ 
/*     */ 
/*     */     
/* 339 */     Path classpath = getClasspath();
/*     */     
/* 341 */     if (classpath != null && !classpath.toString().equals("")) {
/* 342 */       cmd.createArgument().setValue("-classpath");
/* 343 */       cmd.createArgument().setPath(classpath);
/*     */     } 
/* 345 */     return cmd;
/*     */   }
/*     */   
/*     */   private Commandline setupWsdeployForkCommand() {
/* 349 */     CommandlineJava forkCmd = new CommandlineJava();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     Path classpath = getClasspath();
/* 356 */     forkCmd.createClasspath(getProject()).append(classpath);
/* 357 */     forkCmd.setClassname("com.sun.xml.rpc.tools.wsdeploy.Main");
/* 358 */     if (null != getJvmargs()) {
/* 359 */       forkCmd.createVmArgument().setLine(getJvmargs());
/*     */     }
/*     */     
/* 362 */     Commandline cmd = setupWsdeployArgs();
/* 363 */     cmd.createArgument(true).setLine(forkCmd.toString());
/* 364 */     return cmd;
/*     */   }
/*     */   
/*     */   private Commandline setupWsdeployArgs() {
/* 368 */     Commandline cmd = new Commandline();
/*     */ 
/*     */     
/* 371 */     if (getKeep()) {
/* 372 */       cmd.createArgument().setValue("-keep");
/*     */     }
/*     */ 
/*     */     
/* 376 */     if (getFeatures() != null && !getFeatures().equals("")) {
/* 377 */       cmd.createArgument().setValue("-features:" + getFeatures());
/*     */     }
/*     */ 
/*     */     
/* 381 */     if (null != getTmpDir() && !getTmpDir().equals("")) {
/* 382 */       cmd.createArgument().setValue("-tmpdir");
/* 383 */       cmd.createArgument().setFile(getTmpDir());
/*     */     } 
/*     */ 
/*     */     
/* 387 */     if (getSource() != null && !getSource().equals("")) {
/* 388 */       cmd.createArgument().setValue("-source");
/* 389 */       cmd.createArgument().setValue(getSource());
/*     */     } 
/*     */ 
/*     */     
/* 393 */     if (getOutWarFile() != null && !getOutWarFile().equals("")) {
/* 394 */       cmd.createArgument().setValue("-o");
/* 395 */       cmd.createArgument().setFile(getOutWarFile());
/*     */     } 
/*     */ 
/*     */     
/* 399 */     if (getVerbose()) {
/* 400 */       cmd.createArgument().setValue("-verbose");
/*     */     }
/*     */ 
/*     */     
/* 404 */     if (getVersion()) {
/* 405 */       cmd.createArgument().setValue("-version");
/*     */     }
/*     */ 
/*     */     
/* 409 */     if (this.warFile != null) {
/* 410 */       cmd.createArgument().setValue(this.warFile.toString());
/*     */     }
/*     */     
/* 413 */     return cmd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute() throws BuildException {
/* 418 */     if (!getVersion() && (this.warFile == null || !this.warFile.exists())) {
/* 419 */       throw new BuildException("wsdeploy input war file does not exist!", this.location);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 426 */     LogOutputStream logstr = null;
/* 427 */     boolean ok = false;
/*     */     try {
/* 429 */       Commandline cmd = this.fork ? setupWsdeployForkCommand() : setupWsdeployCommand();
/*     */       
/* 431 */       if (this.verbose) {
/* 432 */         log("command line: " + cmd.toString());
/*     */       }
/* 434 */       if (this.fork) {
/* 435 */         int status = run(cmd.getCommandline());
/* 436 */         ok = (status == 0);
/*     */       } else {
/* 438 */         logstr = new LogOutputStream((Task)this, 1);
/* 439 */         DeployTool depTool = new DeployTool((OutputStream)logstr, "wsdeploy");
/* 440 */         ok = depTool.run(cmd.getArguments());
/*     */       } 
/* 442 */       if (!ok) {
/* 443 */         if (!this.verbose) {
/* 444 */           log("Command invoked: " + cmd.toString());
/*     */         }
/* 446 */         throw new BuildException("wsdeploy failed", this.location);
/*     */       } 
/* 448 */     } catch (Exception ex) {
/* 449 */       if (ex instanceof BuildException) {
/* 450 */         throw (BuildException)ex;
/*     */       }
/* 452 */       throw new BuildException("Error starting wsdeploy: ", ex, getLocation());
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/* 457 */         if (logstr != null) {
/* 458 */           logstr.close();
/*     */         }
/* 460 */       } catch (IOException e) {
/* 461 */         throw new BuildException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int run(String[] command) throws BuildException {
/* 470 */     FileOutputStream fos = null;
/* 471 */     Execute exe = null;
/* 472 */     LogStreamHandler logstr = new LogStreamHandler((Task)this, 2, 1);
/*     */     
/* 474 */     exe = new Execute((ExecuteStreamHandler)logstr);
/* 475 */     exe.setAntRun(this.project);
/* 476 */     exe.setCommandline(command);
/*     */     try {
/* 478 */       int rc = exe.execute();
/* 479 */       if (exe.killedProcess()) {
/* 480 */         log("Timeout: killed the sub-process:wsdeploy", 1);
/*     */       }
/* 482 */       return rc;
/* 483 */     } catch (IOException e) {
/* 484 */       throw new BuildException(e, this.location);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\ant\Wsdeploy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */