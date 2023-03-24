/*     */ package com.sun.xml.rpc.server.http.ea;
/*     */ 
/*     */ import com.sun.xml.rpc.server.http.JAXRPCServletException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private ServletConfig _servletConfig;
/*     */   private ServletContext _servletContext;
/*     */   private String _wsdlLocation;
/*     */   private boolean _wsdlTransform;
/*     */   private Map _ports;
/*     */   private byte[] _xsltDocument;
/*     */   private Templates _xsltTemplates;
/*     */   private static final String PROPERTY_PORT_COUNT = "portcount";
/*     */   private static final String PROPERTY_PORT = "port";
/*     */   private static final String PROPERTY_NAME = "name";
/*     */   private static final String PROPERTY_WSDL = "wsdl";
/*     */   private static final String PROPERTY_TNS = "targetNamespace";
/*     */   private static final String PROPERTY_SERVICE_NAME = "serviceName";
/*     */   private static final String PROPERTY_PORT_NAME = "portName";
/*     */   private static final String PROPERTY_LOCATION = "location";
/*     */   private static final String PROPERTY_TRANSFORM = "transform";
/*     */   
/*     */   public WSDLPublisher(ServletConfig servletConfig) {
/* 248 */     this._ports = new HashMap<Object, Object>(); this._servletConfig = servletConfig; } public WSDLPublisher(ServletConfig servletConfig, InputStream configInputStream) { this._ports = new HashMap<Object, Object>();
/*     */     if (configInputStream == null)
/*     */       throw new IllegalArgumentException("error.wsdlPublisher.noInputStream"); 
/*     */     this._servletConfig = servletConfig;
/*     */     this._servletContext = servletConfig.getServletContext();
/*     */     readFrom(configInputStream); }
/*     */ 
/*     */   
/*     */   public boolean hasDocument() {
/*     */     return (this._wsdlLocation != null);
/*     */   }
/*     */   
/*     */   public void publish(String prefix, HttpServletResponse response) throws IOException {
/*     */     response.setContentType("text/xml");
/*     */     response.setStatus(200);
/*     */     ServletOutputStream servletOutputStream = response.getOutputStream();
/*     */     if (this._wsdlTransform) {
/*     */       try {
/*     */         Source wsdlDoc = new StreamSource(this._servletContext.getResourceAsStream(this._wsdlLocation));
/*     */         Transformer transformer = this._xsltTemplates.newTransformer();
/*     */         transformer.setParameter("baseURI", prefix);
/*     */         transformer.transform(wsdlDoc, new StreamResult((OutputStream)servletOutputStream));
/*     */       } catch (TransformerConfigurationException e) {
/*     */         throw new IOException("cannot create transformer");
/*     */       } catch (TransformerException e) {
/*     */         throw new IOException("transformation failed");
/*     */       } 
/*     */     } else {
/*     */       InputStream is = this._servletContext.getResourceAsStream(this._wsdlLocation);
/*     */       copyStream(is, (OutputStream)servletOutputStream);
/*     */       is.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readFrom(InputStream inputStream) {
/*     */     try {
/*     */       Properties properties = new Properties();
/*     */       properties.load(inputStream);
/*     */       inputStream.close();
/*     */       this._wsdlLocation = properties.getProperty("wsdl.location");
/*     */       if (this._wsdlLocation != null) {
/*     */         this._wsdlLocation = this._wsdlLocation.trim();
/*     */         InputStream wsdlFile = this._servletContext.getResourceAsStream(this._wsdlLocation);
/*     */         if (wsdlFile != null) {
/*     */           wsdlFile.close();
/*     */         } else {
/*     */           this._wsdlLocation = null;
/*     */           return;
/*     */         } 
/*     */         this._wsdlTransform = true;
/*     */         String transform = properties.getProperty("wsdl.transform");
/*     */         if (transform != null && !Boolean.valueOf(transform).booleanValue())
/*     */           this._wsdlTransform = false; 
/*     */         if (this._wsdlTransform) {
/*     */           int portCount = Integer.parseInt(properties.getProperty("portcount"));
/*     */           for (int i = 0; i < portCount; i++) {
/*     */             String portPrefix = "port" + Integer.toString(i) + ".";
/*     */             String name = properties.getProperty(portPrefix + "name");
/*     */             String portWsdlPrefix = portPrefix + "wsdl" + ".";
/*     */             String targetNamespace = properties.getProperty(portWsdlPrefix + "targetNamespace");
/*     */             String serviceName = properties.getProperty(portWsdlPrefix + "serviceName");
/*     */             String portName = properties.getProperty(portWsdlPrefix + "portName");
/*     */             if (name != null && targetNamespace != null && serviceName != null && portName != null)
/*     */               this._ports.put(name, new WSDLPortInfo(targetNamespace, serviceName, portName)); 
/*     */           } 
/*     */           ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */           OutputStreamWriter writer = new OutputStreamWriter(bos, "UTF-8");
/*     */           writer.write("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\">\n");
/*     */           writer.write("<xsl:param name=\"baseURI\"/>\n");
/*     */           writer.write("<xsl:template match=\"/\"><xsl:apply-templates mode=\"copy\"/></xsl:template>\n");
/*     */           Iterator<String> iter = this._ports.keySet().iterator();
/*     */           while (iter.hasNext()) {
/*     */             String name = iter.next();
/*     */             WSDLPortInfo portInfo = (WSDLPortInfo)this._ports.get(name);
/*     */             writer.write("<xsl:template match=\"wsdl:definitions[@targetNamespace='");
/*     */             writer.write(portInfo.getTargetNamespace());
/*     */             writer.write("']/wsdl:service[@name='");
/*     */             writer.write(portInfo.getServiceName());
/*     */             writer.write("']/wsdl:port[@name='");
/*     */             writer.write(portInfo.getPortName());
/*     */             writer.write("']/soap:address\" mode=\"copy\">");
/*     */             writer.write("<soap:address><xsl:attribute name=\"location\"><xsl:value-of select=\"$baseURI\"/><xsl:text>");
/*     */             writer.write(name);
/*     */             writer.write("</xsl:text></xsl:attribute></soap:address></xsl:template>");
/*     */           } 
/*     */           writer.write("<xsl:template match=\"@*|node()\" mode=\"copy\"><xsl:copy><xsl:apply-templates select=\"@*\" mode=\"copy\"/><xsl:apply-templates mode=\"copy\"/></xsl:copy></xsl:template>\n");
/*     */           writer.write("</xsl:stylesheet>\n");
/*     */           writer.close();
/*     */           this._xsltDocument = bos.toByteArray();
/*     */           try {
/*     */             Source xsltDoc = new StreamSource(new ByteArrayInputStream(this._xsltDocument));
/*     */             TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */             this._xsltTemplates = transformerFactory.newTemplates(xsltDoc);
/*     */           } catch (TransformerConfigurationException e) {
/*     */             this._wsdlTransform = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } catch (IOException e) {
/*     */       throw new JAXRPCServletException("error.wsdlPublisher.cannotReadConfiguration");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void copyStream(InputStream istream, OutputStream ostream) throws IOException {
/*     */     byte[] buf = new byte[1024];
/*     */     int num = 0;
/*     */     while ((num = istream.read(buf)) != -1)
/*     */       ostream.write(buf, 0, num); 
/*     */     ostream.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\ea\WSDLPublisher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */