/*     */ package com.sun.xml.rpc.tools.wsdeploy;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.ProcessorNotificationListener;
/*     */ import com.sun.xml.rpc.util.ToolBase;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.xml.NullEntityResolver;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import java.util.jar.JarOutputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeployTool
/*     */   extends ToolBase
/*     */   implements ProcessorNotificationListener
/*     */ {
/*     */   protected Properties props;
/*     */   protected boolean doNothing;
/*     */   protected boolean clientSpecified;
/*     */   protected boolean verbose;
/*     */   protected boolean keepTemporaryFiles;
/*     */   protected File sourceFile;
/*     */   protected File destFile;
/*     */   protected File tmpdirBase;
/*     */   protected File tmpdir;
/*     */   protected String userClasspath;
/*     */   protected String target;
/*     */   public static final String WEBAPP_DD = "WEB-INF/web.xml";
/*     */   public static final String WEBAPP_DD_PROCESSED = "WEB-INF/web-before.xml";
/*     */   public static final String JAXRPC_RI_DD = "WEB-INF/jaxrpc-ri.xml";
/*     */   public static final String JAXRPC_RI_DD_PROCESSED = "WEB-INF/jaxrpc-ri-before.xml";
/*     */   public static final String JAXRPC_RI_RUNTIME = "WEB-INF/jaxrpc-ri-runtime.xml";
/*     */   
/*     */   public DeployTool(OutputStream out, String program) {
/*  83 */     super(out, program);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 793 */     this.props = null;
/* 794 */     this.doNothing = false;
/* 795 */     this.clientSpecified = false;
/* 796 */     this.verbose = false;
/* 797 */     this.keepTemporaryFiles = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 802 */     this.userClasspath = null;
/* 803 */     this.target = null; this.props = new Properties();
/*     */   }
/*     */   protected void initialize() { super.initialize(); }
/*     */   protected boolean parseArguments(String[] args) { int i; for (i = 0; i < args.length; i++) { if (args[i].equals("")) { args[i] = null; } else if (args[i].startsWith("-verbose")) { this.verbose = true; this.props.setProperty("verbose", "true"); args[i] = null; } else { if (args[i].equals("-version")) { report(getMessage("wsdeploy.version", "JAX-RPC Standard Implementation", "1.1.3", "R2")); args[i] = null; this.doNothing = true; return true; }  if (args[i].equals("-keep")) { this.keepTemporaryFiles = true; this.props.setProperty("keepGenerated", "true"); args[i] = null; } else if (args[i].equals("-o")) { if (i + 1 < args.length) { if (this.destFile != null) { onError(getMessage("wsdeploy.duplicateOption", "-o")); usage(); return false; }  args[i] = null; this.destFile = new File(args[++i]); args[i] = null; if (this.destFile.isDirectory() || (this.destFile.getParentFile() != null && !this.destFile.getParentFile().exists())) { onError(getMessage("wsdeploy.invalidOutputFile", this.destFile.getPath())); usage(); return false; }  } else { onError(getMessage("wsdeploy.missingOptionArgument", "-model")); usage(); return false; }  } else if (args[i].equals("-classpath") || args[i].equals("-cp")) { if (i + 1 < args.length) { if (this.userClasspath != null) { onError(getMessage("wsdeploy.duplicateOption", args[i])); usage(); return false; }  args[i] = null; this.userClasspath = args[++i]; args[i] = null; }  } else if (args[i].equals("-tmpdir")) { if (i + 1 < args.length) { if (this.tmpdirBase != null) { onError(getMessage("wsdeploy.duplicateOption", "-tmpdir")); usage(); return false; }  args[i] = null; this.tmpdirBase = new File(args[++i]); args[i] = null; if (!this.tmpdirBase.exists()) { onError(getMessage("wsdeploy.noSuchDirectory", this.tmpdirBase.getPath())); usage(); return false; }  } else { onError(getMessage("wsdeploy.missingOptionArgument", "-tmpdir")); usage(); return false; }  } else if (args[i].equals("-source")) { if (i + 1 < args.length) { if (this.target != null) { onError(getMessage("wsdeploy.duplicateOption", "-source")); usage(); return false; }  args[i] = null; this.target = new String(args[++i]); args[i] = null; } else { onError(getMessage("wsdeploy.missingOptionArgument", "-source")); usage(); return false; }  if (this.target.length() == 0) { onError(getMessage("wsdeploy.invalidOption", args[i])); usage(); return false; }  if (!VersionUtil.isValidVersion(this.target)) { onError(getMessage("wsdeploy.invalidTargetVersion", this.target)); usage(); return false; }  }  }  }  for (i = 0; i < args.length; i++) { if (args[i] != null) { if (args[i].startsWith("-")) { onError(getMessage("wsdeploy.invalidOption", args[i])); usage(); return false; }  if (this.sourceFile != null) { onError(getMessage("wsdeploy.multipleWarFiles", args[i])); usage(); return false; }  this.sourceFile = new File(args[i]); args[i] = null; if (!this.sourceFile.exists()) { onError(getMessage("wsdeploy.fileNotFound", this.sourceFile.getPath())); usage(); return false; }  }  }  if (this.sourceFile == null) { onError(getMessage("wsdeploy.missingWarFile")); usage(); return false; }  if (this.destFile == null) { onError(getMessage("wsdeploy.missingDestinationWarFile")); usage(); return false; }  if (this.sourceFile.equals(this.destFile)) { onError(getMessage("wsdeploy.sourceDestinationConflict")); usage(); return false; }  return true; }
/*     */   protected void usage() { report(getMessage("wsdeploy.usage", this.program)); }
/*     */   public void run() throws Exception { if (this.doNothing) return;  if (this.tmpdirBase == null) this.tmpdirBase = new File(System.getProperty("java.io.tmpdir"));  try { this.tmpdir = createTemporaryDirectory(); if (this.verbose) onInfo(getMessage("wsdeploy.info.createdTempDir", this.tmpdir.getAbsolutePath()));  expandSourceFile(); DeploymentDescriptorParser parser = new DeploymentDescriptorParser(); File dd = translateSourceAppFileName("WEB-INF/jaxrpc-ri.xml"); WebServicesInfo webServicesInfo = parser.parse(new FileInputStream(dd)); dd.renameTo(translateSourceAppFileName("WEB-INF/jaxrpc-ri-before.xml")); process(webServicesInfo); defineServletsAndListeners(webServicesInfo); createRuntimeDescriptor(webServicesInfo); if (this.destFile != null) packageDestinationFile();  } finally { if (this.tmpdir != null && !this.keepTemporaryFiles) { removeDirectory(this.tmpdir); if (this.verbose) onInfo(getMessage("wsdeploy.info.removedTempDir", this.tmpdir.getAbsolutePath()));  this.tmpdir = null; }  }  }
/*     */   protected void process(WebServicesInfo webServicesInfo) throws Exception { Hashtable<Object, Object> hashtable = new Hashtable<Object, Object>(); ArrayList<ArrayList> endpointClientList = null; ArrayList<EndpointClientInfo> clientList = null; ArrayList<EndpointInfo> endpointList = null; EndpointInfo endpointInfo = null; EndpointClientInfo endpointClient = null; Iterator<EndpointInfo> iter = webServicesInfo.getEndpoints().values().iterator(); Iterator<EndpointClientInfo> iterClient = webServicesInfo.getEndpointClients().values().iterator(); while (iter.hasNext()) { endpointInfo = iter.next(); if (endpointInfo.getModel() != null) { if (hashtable.containsKey(endpointInfo.getModel())) { endpointClientList = (ArrayList)hashtable.get(endpointInfo.getModel()); endpointList = endpointClientList.get(0); } else { endpointClientList = new ArrayList<ArrayList>(); endpointList = new ArrayList(); clientList = new ArrayList(); }  endpointList.add(endpointInfo); endpointClientList.add(0, endpointList); endpointClientList.add(1, clientList); hashtable.put(endpointInfo.getModel(), endpointClientList); continue; }  process(endpointInfo, webServicesInfo); }  while (iterClient.hasNext()) { endpointClient = iterClient.next(); if (endpointClient.getModel() != null) { if (hashtable.containsKey(endpointClient.getModel())) { endpointClientList = (ArrayList<ArrayList>)hashtable.get(endpointClient.getModel()); clientList = endpointClientList.get(1); clientList.add(endpointClient); endpointClientList.add(1, clientList); continue; }  System.out.println("\n BIG PROBLEM"); }  }  Enumeration<String> e = hashtable.keys(); String key = null; while (e.hasMoreElements()) { key = e.nextElement(); endpointClientList = (ArrayList<ArrayList>)hashtable.get(key); ArrayList list = endpointClientList.get(0); EndpointCompileTool compiler = new EndpointCompileTool(this.out, this.program, webServicesInfo, list, this.tmpdir, this.target, this.props, this.userClasspath, this); compiler.run(); list = endpointClientList.get(1); if (!list.isEmpty()) { EndpointClientCompileTool compilerClient = new EndpointClientCompileTool(this.out, this.program, webServicesInfo, list, this.tmpdir, this.target, this.userClasspath, this); compilerClient.run(); this.clientSpecified = true; }  }  }
/*     */   protected void process(EndpointInfo endpointInfo, WebServicesInfo webServicesInfo) throws Exception { if (this.verbose) onInfo(getMessage("wsdeploy.info.processingEndpoint", endpointInfo.getName()));  EndpointCompileTool compiler = new EndpointCompileTool(this.out, this.program, endpointInfo, webServicesInfo, this.tmpdir, this.target, this.props, this.userClasspath, this); compiler.run(); endpointInfo.setRuntimeDeployed(compiler.wasSuccessful()); }
/*     */   protected void defineServletsAndListeners(WebServicesInfo webServicesInfo) throws Exception { File webappdd = translateSourceAppFileName("WEB-INF/web.xml"); File webappddExisting = translateSourceAppFileName("WEB-INF/web-before.xml"); webappdd.renameTo(webappddExisting); webappdd.delete(); DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); DocumentBuilder builder = factory.newDocumentBuilder(); builder.setEntityResolver((EntityResolver)new NullEntityResolver()); Document document = builder.parse(new InputSource(new FileInputStream(webappddExisting))); String publicId = "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"; String systemId = "http://java.sun.com/j2ee/dtds/web-app_2_3.dtd"; DocumentType doctype = document.getDoctype(); boolean isDTDType = false; if (doctype != null && doctype.getPublicId() != null && doctype.getSystemId() != null) { publicId = doctype.getPublicId(); systemId = doctype.getSystemId(); isDTDType = true; }  Element webAppElement = document.getDocumentElement(); String[] skipNodeNames = { "display-name", "description", "distributable", "context-param", "filter", "filter-mapping", "listener" }; Node currentNode = null; for (int i = skipNodeNames.length - 1; i >= 0; i--) { NodeList testNodes = webAppElement.getElementsByTagName(skipNodeNames[i]); if (testNodes.getLength() > 0) { currentNode = testNodes.item(testNodes.getLength() - 1); break; }  }  if (currentNode == null) currentNode = webAppElement.getChildNodes().item(0);  currentNode = movePastText(currentNode); currentNode = webAppElement.insertBefore(document.createElement("listener"), currentNode.getNextSibling()); currentNode.appendChild(document.createElement("listener-class")).appendChild(document.createTextNode("com.sun.xml.rpc.server.http.JAXRPCContextListener")); currentNode = currentNode.getNextSibling(); if (currentNode.getNodeType() == 1 && currentNode.getNodeName().equals("servlet")) { currentNode = currentNode.getNextSibling(); currentNode = movePastText(currentNode); }  Iterator<EndpointInfo> iterator1 = webServicesInfo.getEndpoints().values().iterator(); while (iterator1.hasNext()) { EndpointInfo endpointInfo = iterator1.next(); if (!endpointInfo.isRuntimeDeployed()) continue;  Element servletElement = document.createElement("servlet"); servletElement.appendChild(document.createElement("servlet-name")).appendChild(document.createTextNode(endpointInfo.getName())); if (isDTDType) { servletElement.appendChild(document.createElement("display-name")).appendChild(document.createTextNode(endpointInfo.getName())); servletElement.appendChild(document.createElement("description")).appendChild(document.createTextNode("JAX-RPC endpoint - " + endpointInfo.getName())); }  servletElement.appendChild(document.createElement("servlet-class")).appendChild(document.createTextNode("com.sun.xml.rpc.server.http.JAXRPCServlet")); servletElement.appendChild(document.createElement("load-on-startup")).appendChild(document.createTextNode("1")); webAppElement.insertBefore(servletElement, currentNode); }  Iterator<EndpointInfo> iter = webServicesInfo.getEndpoints().values().iterator(); while (iter.hasNext()) { EndpointInfo endpointInfo = iter.next(); if (!endpointInfo.isRuntimeDeployed()) continue;  String urlPattern = null; EndpointMappingInfo mappingInfo = (EndpointMappingInfo)webServicesInfo.getEndpointMappings().get(endpointInfo.getName()); if (mappingInfo != null) { urlPattern = mappingInfo.getUrlPattern(); } else { urlPattern = webServicesInfo.getUrlPatternBase() + "/" + endpointInfo.getName(); }  endpointInfo.setRuntimeUrlPattern(urlPattern); Element mappingElement = document.createElement("servlet-mapping"); mappingElement.appendChild(document.createElement("servlet-name")).appendChild(document.createTextNode(endpointInfo.getName())); mappingElement.appendChild(document.createElement("url-pattern")).appendChild(document.createTextNode(urlPattern)); webAppElement.insertBefore(mappingElement, currentNode); }  TransformerFactory factory2 = TransformerFactory.newInstance(); Transformer transformer = null; transformer = factory2.newTransformer(); transformer.setOutputProperty("indent", "yes"); if (isDTDType) { transformer.setOutputProperty("doctype-public", publicId); transformer.setOutputProperty("doctype-system", systemId); }  transformer.transform(new DOMSource(document), new StreamResult(webappdd)); }
/*     */   private Node movePastText(Node node) { if (node != null && node.getNextSibling() != null && node.getNextSibling().getNodeType() == 3) return node.getNextSibling();  return node; }
/* 813 */   protected void createRuntimeDescriptor(WebServicesInfo webServicesInfo) throws Exception { String descriptorContents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<endpoints xmlns='http://java.sun.com/xml/ns/jax-rpc/ri/runtime' version='1.0'>\n"; Iterator<EndpointInfo> iter = webServicesInfo.getEndpoints().values().iterator(); while (iter.hasNext()) { EndpointInfo endpointInfo = iter.next(); if (!endpointInfo.isRuntimeDeployed()) continue;  descriptorContents = descriptorContents + "  <endpoint\n    name='" + endpointInfo.getName() + "'\n" + "    interface='" + endpointInfo.getInterface() + "'\n" + "    implementation='" + endpointInfo.getImplementation() + "'\n" + "    tie='" + endpointInfo.getRuntimeTie() + "'\n" + "    model='" + endpointInfo.getRuntimeModel() + "'\n"; if (endpointInfo.getRuntimeWSDL() != null) descriptorContents = descriptorContents + "    wsdl='" + endpointInfo.getRuntimeWSDL() + "'\n";  descriptorContents = descriptorContents + "    service='" + endpointInfo.getRuntimeServiceName().toString() + "'\n" + "    port='" + endpointInfo.getRuntimePortName().toString() + "'\n" + "    urlpattern='" + endpointInfo.getRuntimeUrlPattern().toString() + "'/>\n"; }  descriptorContents = descriptorContents + "</endpoints>\n"; FileOutputStream fos = new FileOutputStream(translateSourceAppFileName("WEB-INF/jaxrpc-ri-runtime.xml")); OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8"); writer.write(descriptorContents); writer.close(); } protected void expandSourceFile() throws Exception { ZipInputStream zis = new ZipInputStream(new FileInputStream(this.sourceFile)); while (true) { ZipEntry entry = zis.getNextEntry(); if (entry == null) break;  String name = entry.getName(); if (entry.isDirectory()) continue;  File outputFile = translateSourceAppFileName(name); outputFile.getParentFile().mkdirs(); FileOutputStream fos = new FileOutputStream(outputFile); copyStream(zis, fos); fos.close(); }  } protected void packageEndpointClient() throws Exception { File clientFiles = new File(this.tmpdir.getAbsolutePath() + FS + "WEB-INF" + FS + "lib"); List files = new ArrayList(); collectAllFiles(clientFiles, files); String rootPath = this.tmpdir.getAbsolutePath(); File scratchFile = File.createTempFile("jar", "jar", this.tmpdirBase); JarOutputStream jos = new JarOutputStream(new FileOutputStream(scratchFile)); for (Iterator<File> iter = files.iterator(); iter.hasNext(); ) { File file = iter.next(); String filePath = file.getAbsolutePath(); if (filePath.startsWith(rootPath)) { String name = filePath.substring(rootPath.length() + 1).replace(FSCHAR, '/'); jos.putNextEntry(new ZipEntry(name)); FileInputStream fileInputStream = new FileInputStream(file); copyStream(fileInputStream, jos); fileInputStream.close(); jos.closeEntry(); }  }  jos.close(); FileInputStream fis = new FileInputStream(scratchFile); FileOutputStream fos = new FileOutputStream(new File(clientFiles + FS + "Client.jar")); copyStream(fis, fos); fos.close(); fis.close(); scratchFile.delete(); if (this.verbose) onInfo(getMessage("wsdeploy.info.createdWarFile", this.destFile.getAbsolutePath()));  } private static final String PS = System.getProperty("path.separator");
/* 814 */   protected void packageDestinationFile() throws Exception { if (this.clientSpecified) packageEndpointClient();  List files = new ArrayList(); collectAllFiles(this.tmpdir, files); String rootPath = this.tmpdir.getAbsolutePath(); File scratchFile = File.createTempFile("war", "war", this.tmpdirBase); JarOutputStream jos = new JarOutputStream(new FileOutputStream(scratchFile)); for (Iterator<File> iter = files.iterator(); iter.hasNext(); ) { File file = iter.next(); String filePath = file.getAbsolutePath(); if (filePath.startsWith(rootPath)) { String name = filePath.substring(rootPath.length() + 1).replace(FSCHAR, '/'); jos.putNextEntry(new ZipEntry(name)); FileInputStream fileInputStream = new FileInputStream(file); copyStream(fileInputStream, jos); fileInputStream.close(); jos.closeEntry(); }  }  jos.close(); FileInputStream fis = new FileInputStream(scratchFile); FileOutputStream fos = new FileOutputStream(this.destFile); copyStream(fis, fos); fos.close(); fis.close(); scratchFile.delete(); if (this.verbose) onInfo(getMessage("wsdeploy.info.createdWarFile", this.destFile.getAbsolutePath()));  } protected File translateSourceAppFileName(String name) { if (name.charAt(0) == '/') return translateSourceAppFileName(name.substring(1));  return new File(this.tmpdir, name.replace('/', FSCHAR)); } protected File createTemporaryDirectory() { String base = this.tmpdirBase.getAbsolutePath(); String uniqueName = null; File dir = null; while (true) { String suffix = Long.toHexString((new Random()).nextLong() & 0xFFFFFFL); uniqueName = "jaxrpc-deploy-" + suffix; String dirName = base + FS + uniqueName; dir = new File(dirName); if (!dir.exists()) { dir.mkdir(); return dir; }  }  } protected void collectAllFiles(File dir, List<File> files) throws Exception { if (dir.isDirectory()) { File[] fs = dir.listFiles(); for (int i = 0; i < fs.length; i++) { if (fs[i].isDirectory()) { collectAllFiles(fs[i], files); } else { files.add(fs[i]); }  }  }  } protected static void removeDirectory(File directory) throws IOException { if (directory.exists() && !directory.delete()) { File[] files = directory.listFiles(); for (int i = 0; i < files.length; i++) { if (files[i].isDirectory()) { removeDirectory(files[i]); } else { files[i].delete(); }  }  directory.delete(); }  } protected static void copyFile(File in, File out) throws IOException { FileInputStream fis = new FileInputStream(in); FileOutputStream fos = new FileOutputStream(out); copyStream(fis, fos); fos.close(); fis.close(); } protected static void copyStream(InputStream is, OutputStream os) throws IOException { byte[] buf = new byte[1024]; int len = 0; while (len != -1) { try { len = is.read(buf, 0, buf.length); } catch (EOFException eof) { break; }  if (len != -1) os.write(buf, 0, len);  }  } protected String getGenericErrorMessage() { return "wsdeploy.error"; } protected String getResourceBundleName() { return "com.sun.xml.rpc.resources.wsdeploy"; } public void onError(Localizable msg) { report(getMessage("wsdeploy.error", this.localizer.localize(msg))); } public void onWarning(Localizable msg) { report(getMessage("wsdeploy.warning", this.localizer.localize(msg))); } public void onInfo(Localizable msg) { report(getMessage("wsdeploy.info", this.localizer.localize(msg))); } private static final char PSCHAR = System.getProperty("path.separator").charAt(0);
/*     */   
/* 816 */   private static final String FS = System.getProperty("file.separator");
/* 817 */   private static final char FSCHAR = System.getProperty("file.separator").charAt(0);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wsdeploy\DeployTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */