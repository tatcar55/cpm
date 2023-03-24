/*     */ package com.sun.xml.ws.transport.tcp.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;
/*     */ import com.sun.xml.ws.transport.tcp.grizzly.GrizzlyTCPConnector;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPURI;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSTCP
/*     */ {
/*  65 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */   
/*     */   private static final String JAXWS_RI_RUNTIME = "WEB-INF/sun-jaxws.xml";
/*     */   
/*     */   private static final String ENABLE_PROTOCOL_CHECK = "-enableProtocolCheck";
/*     */   
/*     */   private final TCPContext context;
/*     */   
/*     */   private final ClassLoader initClassLoader;
/*     */   
/*     */   private WSTCPDelegate delegate;
/*     */   
/*     */   private Collection<WSTCPConnector> connectors;
/*     */   private final String contextPath;
/*     */   private boolean isProtocolCheck;
/*     */   
/*     */   public WSTCP(@NotNull TCPContext context, @NotNull ClassLoader initClassLoader, @NotNull String contextPath) {
/*  82 */     this.context = context;
/*  83 */     this.initClassLoader = initClassLoader;
/*  84 */     this.contextPath = contextPath;
/*     */   }
/*     */   
/*     */   public boolean isProtocolCheck() {
/*  88 */     return this.isProtocolCheck;
/*     */   }
/*     */   
/*     */   public void setProtocolCheck(boolean isProtocolCheck) {
/*  92 */     this.isProtocolCheck = isProtocolCheck;
/*     */   }
/*     */   @NotNull
/*     */   public List<TCPAdapter> parseDeploymentDescriptor() throws IOException {
/*  96 */     DeploymentDescriptorParser<TCPAdapter> parser = new DeploymentDescriptorParser(this.initClassLoader, new TCPResourceLoader(this.context), null, TCPAdapter.FACTORY);
/*     */     
/*  98 */     URL sunJaxWsXml = this.context.getResource("WEB-INF/sun-jaxws.xml");
/*     */     
/* 100 */     return parser.parse(sunJaxWsXml.toExternalForm(), sunJaxWsXml.openStream());
/*     */   }
/*     */   @NotNull
/*     */   public Collection<WSTCPConnector> initialize() throws IOException {
/* 104 */     List<TCPAdapter> adapters = parseDeploymentDescriptor();
/* 105 */     this.delegate = new WSTCPDelegate();
/* 106 */     Collection<WSTCPConnector> connectors = new LinkedList<WSTCPConnector>();
/*     */     
/* 108 */     Iterator<TCPAdapter> it = adapters.iterator();
/* 109 */     while (it.hasNext()) {
/* 110 */       TCPAdapter adapter = it.next();
/* 111 */       URI uri = adapter.getEndpoint().getPort().getAddress().getURI();
/* 112 */       WSTCPURI tcpURI = WSTCPURI.parse(uri);
/*     */       
/* 114 */       if (this.isProtocolCheck && !"vnd.sun.ws.tcp".equals(uri.getScheme())) {
/*     */         
/* 116 */         logger.log(Level.INFO, MessagesMessages.WSTCP_2002_STANDALONE_ADAPTER_NOT_REGISTERED(adapter.name, adapter.urlPattern));
/*     */ 
/*     */         
/* 119 */         it.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 123 */       GrizzlyTCPConnector grizzlyTCPConnector = new GrizzlyTCPConnector(tcpURI.host, tcpURI.port, this.delegate);
/*     */       
/* 125 */       grizzlyTCPConnector.listen();
/* 126 */       connectors.add(grizzlyTCPConnector);
/* 127 */       logger.log(Level.FINE, MessagesMessages.WSTCP_2001_STANDALONE_ADAPTER_REGISTERED(adapter.name, adapter.urlPattern));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 132 */     this.delegate.registerAdapters(this.contextPath, adapters);
/* 133 */     return connectors;
/*     */   }
/*     */   
/*     */   public void process() throws IOException {
/* 137 */     this.connectors = initialize();
/*     */   }
/*     */   
/*     */   public void close() {
/* 141 */     if (this.connectors != null)
/* 142 */       for (WSTCPConnector connector : this.connectors) {
/* 143 */         connector.close();
/*     */       } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 148 */     Set<String> params = new HashSet<String>();
/*     */     
/* 150 */     if (args.length < 1) {
/* 151 */       System.out.println(MessagesMessages.STANDALONE_RUN());
/* 152 */       System.exit(0);
/*     */     } 
/*     */     
/* 155 */     for (int i = 1; i < args.length; i++) {
/* 156 */       params.add(args[i]);
/*     */     }
/*     */     
/* 159 */     String contextPath = args[0];
/*     */     
/* 161 */     ClassLoader classloader = Thread.currentThread().getContextClassLoader();
/* 162 */     TCPContext context = new TCPStandaloneContext(classloader);
/*     */     
/* 164 */     WSTCP wsTCP = new WSTCP(context, classloader, contextPath);
/* 165 */     wsTCP.setProtocolCheck(params.contains("-enableProtocolCheck"));
/*     */     
/*     */     try {
/* 168 */       wsTCP.process();
/* 169 */       System.out.println(MessagesMessages.STANDALONE_EXIT());
/* 170 */       System.in.read();
/* 171 */     } catch (Exception e) {
/* 172 */       logger.log(Level.SEVERE, MessagesMessages.WSTCP_2000_STANDALONE_EXCEPTION(), e);
/*     */     } finally {
/* 174 */       wsTCP.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\WSTCP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */