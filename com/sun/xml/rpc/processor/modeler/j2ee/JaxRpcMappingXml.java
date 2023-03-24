/*     */ package com.sun.xml.rpc.processor.modeler.j2ee;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.javaWsdlMapping;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.javaWsdlMappingFactory;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.packageMappingType;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JaxRpcMappingXml
/*     */ {
/*  64 */   javaWsdlMappingFactory factory = new javaWsdlMappingFactory(); public JaxRpcMappingXml(String systemId) throws IOException {
/*  65 */     InputSource src = new InputSource(systemId);
/*  66 */     this.factory.setPackageName("com.sun.xml.rpc.processor.modeler.j2ee.xml");
/*     */     
/*  68 */     this.javaWsdlMap = (javaWsdlMapping)this.factory.loadDocument("javaWsdlMapping", src);
/*     */     
/*  70 */     if (this.javaWsdlMap == null) {
/*  71 */       throw new IOException("Unable to load mapping meta data at: " + systemId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   javaWsdlMapping javaWsdlMap;
/*     */   HashMap nsMap;
/*     */   
/*     */   public javaWsdlMapping getJavaWsdlMapping() {
/*  80 */     return this.javaWsdlMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap getNSToPkgMapping() {
/*  87 */     if (this.nsMap == null) {
/*  88 */       this.nsMap = new HashMap<Object, Object>();
/*  89 */       int numPkgMap = this.javaWsdlMap.getPackageMappingCount();
/*  90 */       for (int i = 0; i < numPkgMap; i++) {
/*  91 */         packageMappingType pkgMap = this.javaWsdlMap.getPackageMapping(i);
/*  92 */         this.nsMap.put(pkgMap.getNamespaceURI().getElementValue(), pkgMap.getPackageType().getElementValue());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  97 */     return this.nsMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 105 */       if (argv.length != 1) {
/* 106 */         System.out.println("usage: com.ibm.webservices.ri.deploy.JaxRpcMappingXml systemId");
/*     */         
/* 108 */         System.exit(1);
/*     */       } 
/* 110 */       JaxRpcMappingXml jaxRpcMap = new JaxRpcMappingXml(argv[0]);
/* 111 */       HashMap nsMap = jaxRpcMap.getNSToPkgMapping();
/* 112 */       Set keys = nsMap.keySet();
/* 113 */       System.out.println(nsMap.size() + " namespace to package mapping:");
/* 114 */       for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
/* 115 */         String ns = it.next();
/* 116 */         String pkg = (String)nsMap.get(ns);
/* 117 */         System.out.println("'" + ns + "' : '" + pkg + "'");
/*     */       } 
/*     */       
/* 120 */       javaWsdlMapping javaWsdlMap = jaxRpcMap.getJavaWsdlMapping();
/* 121 */       int numJavaXmlTypeMapping = javaWsdlMap.getJavaXmlTypeMappingCount();
/*     */       
/* 123 */       System.out.println("There are " + numJavaXmlTypeMapping + " java-xml-type-mapping");
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 128 */     catch (Throwable t) {
/* 129 */       t.printStackTrace();
/* 130 */       System.exit(1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\JaxRpcMappingXml.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */