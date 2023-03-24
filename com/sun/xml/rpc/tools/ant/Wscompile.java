/*     */ package com.sun.xml.rpc.tools.ant;
/*     */ 
/*     */ import com.sun.xml.rpc.tools.wscompile.CompileTool;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
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
/*     */ public class Wscompile
/*     */   extends MatchingTask
/*     */ {
/*  54 */   protected Path compileClasspath = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getClasspath() {
/*  60 */     return this.compileClasspath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClasspath(Path classpath) {
/*  67 */     if (this.compileClasspath == null) {
/*  68 */       this.compileClasspath = classpath;
/*     */     } else {
/*  70 */       this.compileClasspath.append(classpath);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path createClasspath() {
/*  78 */     if (this.compileClasspath == null) {
/*  79 */       this.compileClasspath = new Path(this.project);
/*     */     }
/*  81 */     return this.compileClasspath.createPath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClasspathRef(Reference r) {
/*  88 */     createClasspath().setRefid(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getCP() {
/*  96 */     return getClasspath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCP(Path classpath) {
/* 103 */     setClasspath(classpath);
/*     */   }
/*     */ 
/*     */   
/* 107 */   private File baseDir = null;
/*     */ 
/*     */   
/*     */   public File getBase() {
/* 111 */     return this.baseDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBase(File base) {
/* 116 */     this.baseDir = base;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean define = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDefine() {
/* 126 */     return this.define;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefine(boolean define) {
/* 133 */     this.define = define;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getF() {
/* 139 */     return getFeatures();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setF(String features) {
/* 144 */     setFeatures(features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   private String features = null;
/*     */ 
/*     */   
/*     */   public String getFeatures() {
/* 157 */     return this.features;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFeatures(String features) {
/* 162 */     this.features = features;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean debug = false;
/*     */ 
/*     */   
/*     */   public boolean getDebug() {
/* 170 */     return this.debug;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDebug(boolean debug) {
/* 175 */     this.debug = debug;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getGen() {
/* 181 */     return getClient();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGen(boolean client) {
/* 186 */     setClient(client);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean client = false;
/*     */ 
/*     */   
/*     */   public boolean getClient() {
/* 194 */     return this.client;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClient(boolean client) {
/* 199 */     this.client = client;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean server = false;
/*     */ 
/*     */   
/*     */   public boolean getServer() {
/* 207 */     return this.server;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServer(boolean server) {
/* 212 */     this.server = server;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean both = false;
/*     */ 
/*     */   
/*     */   public boolean getBoth() {
/* 221 */     return this.both;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoth(boolean both) {
/* 231 */     this.both = both;
/*     */   }
/*     */ 
/*     */   
/* 235 */   private String HTTPProxyURL = null;
/* 236 */   private URL proxyURL = null;
/*     */ 
/*     */   
/*     */   public String getHTTPProxy() {
/* 240 */     return this.HTTPProxyURL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHTTPProxy(String HTTPProxy) {
/* 247 */     if (HTTPProxy != null && !HTTPProxy.equals("")) {
/* 248 */       if (HTTPProxy.startsWith("http://")) {
/* 249 */         this.HTTPProxyURL = HTTPProxy;
/*     */       } else {
/* 251 */         this.HTTPProxyURL = "http://" + HTTPProxy;
/*     */       } 
/*     */       
/*     */       try {
/* 255 */         URL proxyServer = new URL(this.HTTPProxyURL);
/* 256 */         setProxyServer(proxyServer);
/* 257 */       } catch (MalformedURLException e) {
/* 258 */         throw new Error("Invalid HTTP URL specified: " + this.HTTPProxyURL);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getProxyServer() {
/* 266 */     return this.proxyURL;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProxyServer(URL proxyURL) {
/* 271 */     this.proxyURL = proxyURL;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean genImport = false;
/*     */   protected String jvmargs;
/*     */   
/*     */   public boolean getImport() {
/* 279 */     return this.genImport;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImport(boolean genImport) {
/* 284 */     this.genImport = genImport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmargs() {
/* 292 */     return this.jvmargs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJvmargs(String jvmargs) {
/* 297 */     this.jvmargs = jvmargs;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean keep = false;
/*     */ 
/*     */   
/*     */   public boolean getKeep() {
/* 305 */     return this.keep;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKeep(boolean keep) {
/* 310 */     this.keep = keep;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean fork = false;
/*     */ 
/*     */   
/*     */   public boolean getFork() {
/* 318 */     return this.fork;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFork(boolean fork) {
/* 323 */     this.fork = fork;
/*     */   }
/*     */ 
/*     */   
/* 327 */   private File modelFile = null;
/*     */ 
/*     */   
/*     */   public File getModel() {
/* 331 */     return this.modelFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setModel(File modelFile) {
/* 336 */     this.modelFile = modelFile;
/*     */   }
/*     */ 
/*     */   
/* 340 */   private File mappingFile = null;
/*     */ 
/*     */   
/*     */   public File getMapping() {
/* 344 */     return this.mappingFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMapping(File mappingFile) {
/* 349 */     this.mappingFile = mappingFile;
/*     */   }
/*     */ 
/*     */   
/* 353 */   private File securityFile = null;
/*     */ 
/*     */   
/*     */   public File getSecurity() {
/* 357 */     return this.securityFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSecurity(File securityFile) {
/* 362 */     this.securityFile = securityFile;
/*     */   }
/*     */ 
/*     */   
/* 366 */   private File nonClassDir = null;
/*     */ 
/*     */   
/*     */   public File getNonClassDir() {
/* 370 */     return this.nonClassDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNonClassDir(File nonClassDir) {
/* 375 */     this.nonClassDir = nonClassDir;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean optimize = false;
/*     */   private File sourceBase;
/*     */   
/*     */   public boolean getOptimize() {
/* 383 */     return this.optimize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOptimize(boolean optimize) {
/* 388 */     this.optimize = optimize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceBase(File sourceBase) {
/* 396 */     this.keep = true;
/* 397 */     this.sourceBase = sourceBase;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getSourceBase() {
/* 402 */     return this.sourceBase;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean verbose = false;
/*     */ 
/*     */   
/*     */   public boolean getVerbose() {
/* 410 */     return this.verbose;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVerbose(boolean verbose) {
/* 415 */     this.verbose = verbose;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean version = false;
/*     */ 
/*     */   
/*     */   public boolean getVersion() {
/* 423 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVersion(boolean version) {
/* 428 */     this.version = version;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean xPrintStackTrace = false;
/*     */ 
/*     */   
/*     */   public boolean getXPrintStackTrace() {
/* 436 */     return this.xPrintStackTrace;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXPrintStackTrace(boolean xPrintStackTrace) {
/* 441 */     this.xPrintStackTrace = xPrintStackTrace;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean xSerializable = false;
/*     */ 
/*     */   
/*     */   public boolean getXSerializable() {
/* 449 */     return this.xSerializable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXSerializable(boolean xSerializable) {
/* 454 */     this.xSerializable = xSerializable;
/*     */   }
/*     */ 
/*     */   
/* 458 */   private File xDebugModel = null;
/*     */ 
/*     */   
/*     */   public File getXDebugModel() {
/* 462 */     return this.xDebugModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXDebugModel(File xDebugModel) {
/* 467 */     this.xDebugModel = xDebugModel;
/*     */   }
/*     */ 
/*     */   
/* 471 */   private File config = null;
/*     */ 
/*     */   
/*     */   public File getConfig() {
/* 475 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setConfig(File config) {
/* 480 */     this.config = config;
/*     */   }
/*     */ 
/*     */   
/* 484 */   private String source = null;
/*     */   
/*     */   public String getSource() {
/* 487 */     return this.source;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSource(String version) {
/* 492 */     this.source = version;
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
/* 503 */     this.includeAntRuntime = include;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIncludeantruntime() {
/* 511 */     return this.includeAntRuntime;
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
/* 523 */     this.includeJavaRuntime = include;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIncludejavaruntime() {
/* 531 */     return this.includeJavaRuntime;
/*     */   }
/*     */ 
/*     */   
/*     */   private Path generateCompileClasspath() {
/* 536 */     Path classpath = new Path(getProject());
/*     */     
/* 538 */     if (getClasspath() == null) {
/* 539 */       if (getIncludeantruntime()) {
/* 540 */         classpath.addExisting(Path.systemClasspath);
/*     */       }
/*     */     }
/* 543 */     else if (getIncludeantruntime()) {
/* 544 */       classpath.addExisting(getClasspath().concatSystemClasspath("last"));
/*     */     } else {
/*     */       
/* 547 */       classpath.addExisting(getClasspath().concatSystemClasspath("ignore"));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 552 */     if (getIncludejavaruntime()) {
/*     */ 
/*     */       
/* 555 */       classpath.addExisting(new Path(null, System.getProperty("java.home") + File.separator + "lib" + File.separator + "rt.jar"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 563 */       classpath.addExisting(new Path(null, System.getProperty("java.home") + File.separator + "jre" + File.separator + "lib" + File.separator + "rt.jar"));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 570 */     return classpath;
/*     */   }
/*     */   
/*     */   private Commandline setupWscompileCommand() {
/* 574 */     Commandline cmd = setupWscompileArgs();
/*     */ 
/*     */ 
/*     */     
/* 578 */     Path classpath = getClasspath();
/*     */     
/* 580 */     if (classpath != null && !classpath.toString().equals("")) {
/* 581 */       cmd.createArgument().setValue("-classpath");
/* 582 */       cmd.createArgument().setPath(classpath);
/*     */     } 
/* 584 */     return cmd;
/*     */   }
/*     */   
/*     */   private Commandline setupWscompileForkCommand() {
/* 588 */     CommandlineJava forkCmd = new CommandlineJava();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 594 */     Path classpath = getClasspath();
/* 595 */     forkCmd.createClasspath(getProject()).append(classpath);
/* 596 */     forkCmd.setClassname("com.sun.xml.rpc.tools.wscompile.Main");
/* 597 */     if (null != getJvmargs()) {
/* 598 */       forkCmd.createVmArgument().setLine(getJvmargs());
/*     */     }
/*     */     
/* 601 */     Commandline cmd = setupWscompileArgs();
/* 602 */     cmd.createArgument(true).setLine(forkCmd.toString());
/* 603 */     return cmd;
/*     */   }
/*     */   
/*     */   private Commandline setupWscompileArgs() {
/* 607 */     Commandline cmd = new Commandline();
/*     */ 
/*     */     
/* 610 */     if (null != getBase() && !getBase().equals("")) {
/* 611 */       cmd.createArgument().setValue("-d");
/* 612 */       cmd.createArgument().setFile(getBase());
/*     */     } 
/*     */ 
/*     */     
/* 616 */     if (getDefine()) {
/* 617 */       cmd.createArgument().setValue("-define");
/*     */     }
/*     */ 
/*     */     
/* 621 */     if (getSource() != null && !getSource().equals("")) {
/* 622 */       cmd.createArgument().setValue("-source");
/* 623 */       cmd.createArgument().setValue(getSource());
/*     */     } 
/*     */ 
/*     */     
/* 627 */     if (getFeatures() != null && !getFeatures().equals("")) {
/* 628 */       cmd.createArgument().setValue("-features:" + getFeatures());
/*     */     }
/*     */ 
/*     */     
/* 632 */     if (getDebug()) {
/* 633 */       cmd.createArgument().setValue("-g");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 639 */     if (getBoth()) {
/* 640 */       cmd.createArgument().setValue("-gen:both");
/* 641 */     } else if (getClient()) {
/* 642 */       cmd.createArgument().setValue("-gen:client");
/* 643 */     } else if (getServer()) {
/* 644 */       cmd.createArgument().setValue("-gen:server");
/*     */     } 
/*     */ 
/*     */     
/* 648 */     if (getProxyServer() != null) {
/* 649 */       String host = getProxyServer().getHost();
/* 650 */       if (host != null && !host.equals("")) {
/* 651 */         String proxyVal = "-httpproxy:" + host;
/* 652 */         if (getProxyServer().getPort() != -1) {
/* 653 */           proxyVal = proxyVal + ":" + getProxyServer().getPort();
/*     */         }
/*     */         
/* 656 */         cmd.createArgument().setValue(proxyVal);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 661 */     if (getImport()) {
/* 662 */       cmd.createArgument().setValue("-import");
/*     */     }
/*     */ 
/*     */     
/* 666 */     if (getKeep()) {
/* 667 */       cmd.createArgument().setValue("-keep");
/*     */     }
/*     */ 
/*     */     
/* 671 */     if (getModel() != null && !getModel().equals("")) {
/* 672 */       cmd.createArgument().setValue("-model");
/* 673 */       cmd.createArgument().setFile(getModel());
/*     */     } 
/*     */ 
/*     */     
/* 677 */     if (getMapping() != null && !getMapping().equals("")) {
/* 678 */       cmd.createArgument().setValue("-mapping");
/* 679 */       cmd.createArgument().setFile(getMapping());
/*     */     } 
/*     */ 
/*     */     
/* 683 */     if (getSecurity() != null && !getSecurity().equals("")) {
/* 684 */       cmd.createArgument().setValue("-security");
/* 685 */       cmd.createArgument().setFile(getSecurity());
/*     */     } 
/*     */ 
/*     */     
/* 689 */     if (null != getNonClassDir() && !getNonClassDir().equals("")) {
/* 690 */       cmd.createArgument().setValue("-nd");
/* 691 */       cmd.createArgument().setFile(getNonClassDir());
/*     */     } 
/*     */ 
/*     */     
/* 695 */     if (getOptimize()) {
/* 696 */       cmd.createArgument().setValue("-O");
/*     */     }
/*     */ 
/*     */     
/* 700 */     if (null != getSourceBase() && !getSourceBase().equals("")) {
/* 701 */       cmd.createArgument().setValue("-s");
/* 702 */       cmd.createArgument().setFile(getSourceBase());
/*     */     } 
/*     */ 
/*     */     
/* 706 */     if (getVerbose()) {
/* 707 */       cmd.createArgument().setValue("-verbose");
/*     */     }
/*     */ 
/*     */     
/* 711 */     if (getVersion()) {
/* 712 */       cmd.createArgument().setValue("-version");
/*     */     }
/*     */ 
/*     */     
/* 716 */     if (getXPrintStackTrace()) {
/* 717 */       cmd.createArgument().setValue("-Xprintstacktrace");
/*     */     }
/*     */ 
/*     */     
/* 721 */     if (getXSerializable()) {
/* 722 */       cmd.createArgument().setValue("-Xserializable");
/*     */     }
/*     */ 
/*     */     
/* 726 */     if (getXDebugModel() != null && !getXDebugModel().equals("")) {
/* 727 */       cmd.createArgument().setValue("-Xdebugmodel:" + getXDebugModel());
/*     */     }
/*     */ 
/*     */     
/* 731 */     if (getConfig() != null) {
/* 732 */       cmd.createArgument().setValue(getConfig().toString());
/*     */     }
/*     */     
/* 735 */     return cmd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() throws BuildException {
/* 741 */     if (!getVersion() && (getConfig() == null || !getConfig().exists())) {
/* 742 */       throw new BuildException("wscompile config file does not exist!", this.location);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 749 */     LogOutputStream logstr = null;
/* 750 */     boolean ok = false;
/*     */     try {
/* 752 */       Commandline cmd = this.fork ? setupWscompileForkCommand() : setupWscompileCommand();
/*     */       
/* 754 */       if (this.verbose) {
/* 755 */         log("command line: wscompile " + cmd.toString());
/*     */       }
/* 757 */       if (this.fork) {
/* 758 */         int status = run(cmd.getCommandline());
/* 759 */         ok = (status == 0);
/*     */       } else {
/* 761 */         logstr = new LogOutputStream((Task)this, 1);
/* 762 */         CompileTool compTool = new CompileTool((OutputStream)logstr, "wscompile");
/* 763 */         ok = compTool.run(cmd.getArguments());
/*     */       } 
/* 765 */       if (!ok) {
/* 766 */         if (!this.verbose) {
/* 767 */           log("Command invoked: wscompile " + cmd.toString());
/*     */         }
/* 769 */         throw new BuildException("wscompile failed", this.location);
/*     */       } 
/* 771 */     } catch (Exception ex) {
/* 772 */       if (ex instanceof BuildException) {
/* 773 */         throw (BuildException)ex;
/*     */       }
/* 775 */       throw new BuildException("Error starting wscompile: ", ex, getLocation());
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/* 780 */         if (logstr != null) {
/* 781 */           logstr.close();
/*     */         }
/* 783 */       } catch (IOException e) {
/* 784 */         throw new BuildException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int run(String[] command) throws BuildException {
/* 793 */     FileOutputStream fos = null;
/* 794 */     Execute exe = null;
/* 795 */     LogStreamHandler logstr = new LogStreamHandler((Task)this, 2, 1);
/*     */     
/* 797 */     exe = new Execute((ExecuteStreamHandler)logstr);
/* 798 */     exe.setAntRun(this.project);
/* 799 */     exe.setCommandline(command);
/*     */     try {
/* 801 */       int rc = exe.execute();
/* 802 */       if (exe.killedProcess()) {
/* 803 */         log("Timeout: killed the sub-process", 1);
/*     */       }
/* 805 */       return rc;
/* 806 */     } catch (IOException e) {
/* 807 */       throw new BuildException(e, this.location);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\ant\Wscompile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */