/*     */ package com.sun.xml.rpc.server.http;
/*     */ 
/*     */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*     */ import com.sun.xml.rpc.util.localization.Localizer;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Templates;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSDLPublisher
/*     */ {
/*     */   private ServletContext servletContext;
/*     */   private Localizer localizer;
/*     */   private LocalizableMessageFactory messageFactory;
/*     */   private JAXRPCRuntimeInfo jaxrpcInfo;
/*     */   private Map templatesByEndpointInfo;
/*     */   
/*     */   public WSDLPublisher(ServletContext context, JAXRPCRuntimeInfo jaxrpcInfo) {
/*  65 */     this.servletContext = context;
/*  66 */     this.jaxrpcInfo = jaxrpcInfo;
/*  67 */     this.templatesByEndpointInfo = new HashMap<Object, Object>();
/*  68 */     this.localizer = new Localizer();
/*  69 */     this.messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.jaxrpcservlet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(RuntimeEndpointInfo targetEndpoint, Map fixedUrlPatternEndpoints, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
/*     */     Templates templates;
/*  79 */     Iterator<String> urlPatterns = fixedUrlPatternEndpoints.keySet().iterator();
/*  80 */     String urlPattern = urlPatterns.next();
/*     */ 
/*     */     
/*  83 */     while (targetEndpoint != fixedUrlPatternEndpoints.get(urlPattern)) {
/*  84 */       urlPattern = urlPatterns.next();
/*     */     }
/*  86 */     response.setContentType("text/xml");
/*  87 */     response.setStatus(200);
/*  88 */     ServletOutputStream servletOutputStream = response.getOutputStream();
/*  89 */     String actualAddress = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     String baseAddress = actualAddress.substring(0, actualAddress.lastIndexOf(urlPattern));
/*     */ 
/*     */ 
/*     */     
/* 100 */     synchronized (this) {
/* 101 */       templates = (Templates)this.templatesByEndpointInfo.get(targetEndpoint);
/* 102 */       if (templates == null) {
/* 103 */         templates = createTemplatesFor(fixedUrlPatternEndpoints);
/* 104 */         this.templatesByEndpointInfo.put(targetEndpoint, templates);
/*     */       } 
/*     */     } 
/*     */     try {
/* 108 */       Iterator<String> iter = fixedUrlPatternEndpoints.keySet().iterator();
/* 109 */       while (iter.hasNext()) {
/* 110 */         logger.fine(this.localizer.localize(this.messageFactory.getMessage("publisher.info.applyingTransformation", baseAddress + iter.next())));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       Source wsdlDocument = new StreamSource(this.servletContext.getResourceAsStream(targetEndpoint.getWSDLFileName()));
/*     */ 
/*     */ 
/*     */       
/* 120 */       Transformer transformer = templates.newTransformer();
/* 121 */       transformer.setParameter("baseAddress", baseAddress);
/* 122 */       transformer.transform(wsdlDocument, new StreamResult((OutputStream)servletOutputStream));
/* 123 */     } catch (TransformerConfigurationException e) {
/* 124 */       throw new JAXRPCServletException("exception.cannotCreateTransformer");
/* 125 */     } catch (TransformerException e) {
/* 126 */       throw new JAXRPCServletException("exception.transformationFailed", e.getMessageAndLocation());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Templates createTemplatesFor(Map patternToPort) {
/*     */     try {
/* 135 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 136 */       OutputStreamWriter writer = new OutputStreamWriter(bos, "UTF-8");
/*     */       
/* 138 */       writer.write("<xsl:transform version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\">\n");
/*     */       
/* 140 */       writer.write("<xsl:param name=\"baseAddress\"/>\n");
/*     */       
/* 142 */       writer.write("<xsl:template match=\"/\"><xsl:apply-templates mode=\"copy\"/></xsl:template>\n");
/*     */ 
/*     */       
/* 145 */       Iterator<String> iter = patternToPort.keySet().iterator();
/* 146 */       while (iter.hasNext()) {
/* 147 */         String pattern = iter.next();
/* 148 */         RuntimeEndpointInfo info = (RuntimeEndpointInfo)patternToPort.get(pattern);
/*     */         
/* 150 */         writer.write("<xsl:template match=\"wsdl:definitions[@targetNamespace='");
/*     */         
/* 152 */         writer.write(info.getPortName().getNamespaceURI());
/* 153 */         writer.write("']/wsdl:service[@name='");
/* 154 */         writer.write(info.getServiceName().getLocalPart());
/* 155 */         writer.write("']/wsdl:port[@name='");
/* 156 */         writer.write(info.getPortName().getLocalPart());
/* 157 */         writer.write("']/soap:address\" mode=\"copy\">");
/* 158 */         writer.write("<soap:address><xsl:attribute name=\"location\">");
/* 159 */         writer.write("<xsl:value-of select=\"$baseAddress\"/>" + pattern);
/*     */         
/* 161 */         writer.write("</xsl:attribute></soap:address></xsl:template>");
/*     */       } 
/*     */       
/* 164 */       writer.write("<xsl:template match=\"@*|node()\" mode=\"copy\"><xsl:copy><xsl:apply-templates select=\"@*\" mode=\"copy\"/><xsl:apply-templates mode=\"copy\"/></xsl:copy></xsl:template>\n");
/*     */       
/* 166 */       writer.write("</xsl:transform>\n");
/* 167 */       writer.close();
/* 168 */       byte[] stylesheet = bos.toByteArray();
/* 169 */       Source stylesheetSource = new StreamSource(new ByteArrayInputStream(stylesheet));
/*     */       
/* 171 */       TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */       
/* 173 */       Templates templates = transformerFactory.newTemplates(stylesheetSource);
/*     */       
/* 175 */       return templates;
/* 176 */     } catch (Exception e) {
/* 177 */       throw new JAXRPCServletException("exception.templateCreationFailed");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void copyStream(InputStream istream, OutputStream ostream) throws IOException {
/* 183 */     byte[] buf = new byte[1024];
/* 184 */     int num = 0;
/* 185 */     while ((num = istream.read(buf)) != -1) {
/* 186 */       ostream.write(buf, 0, num);
/*     */     }
/* 188 */     ostream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   private static final Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.rpc.server.http");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\WSDLPublisher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */