/*     */ package com.sun.xml.ws.transport.tcp.server.tomcat.grizzly10;
/*     */ 
/*     */ import com.sun.enterprise.web.connector.grizzly.SelectorThread;
/*     */ import com.sun.enterprise.web.portunif.PortUnificationPipeline;
/*     */ import com.sun.enterprise.web.portunif.ProtocolFinder;
/*     */ import com.sun.enterprise.web.portunif.ProtocolHandler;
/*     */ import com.sun.enterprise.web.portunif.TlsProtocolFinder;
/*     */ import com.sun.xml.ws.transport.tcp.grizzly.WSTCPProtocolFinder;
/*     */ import com.sun.xml.ws.transport.tcp.grizzly.WSTCPProtocolHandler;
/*     */ import com.sun.xml.ws.transport.tcp.server.IncomeMessageProcessor;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPMessageListener;
/*     */ import com.sun.xml.ws.transport.tcp.server.tomcat.WSTCPTomcatProtocolHandlerBase;
/*     */ import com.sun.xml.ws.transport.tcp.server.tomcat.WSTCPTomcatRegistry;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSTCPGrizzly10ProtocolHandler
/*     */   extends WSTCPTomcatProtocolHandlerBase
/*     */ {
/*     */   private SelectorThread grizzlySelectorThread;
/*     */   
/*     */   public void init() throws Exception {
/*  61 */     super.init();
/*     */     
/*     */     try {
/*  64 */       this.grizzlySelectorThread = createSelectorThread();
/*  65 */     } catch (InstantiationException ex) {
/*  66 */       ex.printStackTrace();
/*  67 */     } catch (IOException ex) {
/*  68 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     try {
/*  74 */       this.grizzlySelectorThread.startEndpoint();
/*  75 */     } catch (InstantiationException ex) {
/*  76 */       ex.printStackTrace();
/*  77 */     } catch (IOException ex) {
/*  78 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void destroy() throws Exception {
/*  83 */     if (this.grizzlySelectorThread != null) {
/*  84 */       this.grizzlySelectorThread.stopEndpoint();
/*     */     }
/*     */   }
/*     */   
/*     */   private SelectorThread createSelectorThread() throws IOException, InstantiationException {
/*  89 */     IncomeMessageProcessor messageProcessor = IncomeMessageProcessor.registerListener(this.port, (TCPMessageListener)WSTCPTomcatRegistry.getInstance(), null);
/*     */ 
/*     */     
/*  92 */     SelectorThread selectorThread = new SelectorThread()
/*     */       {
/*     */         protected void rampUpProcessorTask() {}
/*     */ 
/*     */         
/*     */         protected void registerComponents() {}
/*     */       };
/*  99 */     selectorThread.setPort(this.port);
/* 100 */     if (this.readThreadsCount > 0) {
/* 101 */       selectorThread.setSelectorReadThreadsCount(this.readThreadsCount);
/*     */     }
/*     */     
/* 104 */     if (this.maxWorkerThreadsCount >= 0) {
/* 105 */       selectorThread.setMaxThreads(this.maxWorkerThreadsCount);
/*     */     }
/*     */     
/* 108 */     if (this.minWorkerThreadsCount >= 0) {
/* 109 */       selectorThread.setMinThreads(this.minWorkerThreadsCount);
/*     */     }
/*     */     
/* 112 */     selectorThread.setPipelineClassName(PortUnificationPipeline.class.getName());
/* 113 */     selectorThread.initEndpoint();
/*     */     
/* 115 */     PortUnificationPipeline puPipeline = (PortUnificationPipeline)selectorThread.getProcessorPipeline();
/* 116 */     puPipeline.addProtocolFinder((ProtocolFinder)new WSTCPProtocolFinder());
/* 117 */     puPipeline.addProtocolFinder((ProtocolFinder)new TlsProtocolFinder());
/* 118 */     puPipeline.addProtocolFinder(new HttpRedirectorProtocolFinder());
/*     */     
/* 120 */     WSTCPProtocolHandler protocolHandler = new WSTCPProtocolHandler();
/* 121 */     WSTCPProtocolHandler.setIncomingMessageProcessor(messageProcessor);
/* 122 */     puPipeline.addProtocolHandler((ProtocolHandler)protocolHandler);
/*     */     
/* 124 */     puPipeline.addProtocolHandler(new HttpRedirectorProtocolHandler(this.redirectHttpPort));
/*     */     
/* 126 */     return selectorThread;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\tomcat\grizzly10\WSTCPGrizzly10ProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */