/*     */ package com.sun.xml.ws.api.databinding;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.xml.sax.EntityResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabindingConfig
/*     */ {
/*     */   protected Class contractClass;
/*     */   protected Class endpointClass;
/*  69 */   protected Set<Class> additionalValueTypes = (Set)new HashSet<Class<?>>();
/*     */ 
/*     */ 
/*     */   
/*  73 */   protected MappingInfo mappingInfo = new MappingInfo();
/*     */   
/*     */   protected URL wsdlURL;
/*     */   
/*     */   protected ClassLoader classLoader;
/*     */   
/*     */   protected Iterable<WebServiceFeature> features;
/*     */   
/*     */   protected WSBinding wsBinding;
/*     */   
/*     */   protected WSDLPort wsdlPort;
/*     */   protected MetadataReader metadataReader;
/*  85 */   protected Map<String, Object> properties = new HashMap<String, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Source wsdlSource;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityResolver entityResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getContractClass() {
/* 103 */     return this.contractClass;
/*     */   }
/*     */   public void setContractClass(Class contractClass) {
/* 106 */     this.contractClass = contractClass;
/*     */   }
/*     */   public Class getEndpointClass() {
/* 109 */     return this.endpointClass;
/*     */   }
/*     */   public void setEndpointClass(Class implBeanClass) {
/* 112 */     this.endpointClass = implBeanClass;
/*     */   }
/*     */   public MappingInfo getMappingInfo() {
/* 115 */     return this.mappingInfo;
/*     */   }
/*     */   public void setMappingInfo(MappingInfo mappingInfo) {
/* 118 */     this.mappingInfo = mappingInfo;
/*     */   }
/*     */   public URL getWsdlURL() {
/* 121 */     return this.wsdlURL;
/*     */   }
/*     */   public void setWsdlURL(URL wsdlURL) {
/* 124 */     this.wsdlURL = wsdlURL;
/*     */   }
/*     */   public ClassLoader getClassLoader() {
/* 127 */     return this.classLoader;
/*     */   }
/*     */   public void setClassLoader(ClassLoader classLoader) {
/* 130 */     this.classLoader = classLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<WebServiceFeature> getFeatures() {
/* 145 */     return this.features;
/*     */   }
/*     */   public void setFeatures(WebServiceFeature[] features) {
/* 148 */     setFeatures((Iterable<WebServiceFeature>)new WebServiceFeatureList(features));
/*     */   }
/*     */   public void setFeatures(Iterable<WebServiceFeature> features) {
/* 151 */     this.features = (Iterable<WebServiceFeature>)WebServiceFeatureList.toList(features);
/*     */   }
/*     */   public WSDLPort getWsdlPort() {
/* 154 */     return this.wsdlPort;
/*     */   }
/*     */   public void setWsdlPort(WSDLPort wsdlPort) {
/* 157 */     this.wsdlPort = wsdlPort;
/*     */   }
/*     */   public Set<Class> additionalValueTypes() {
/* 160 */     return this.additionalValueTypes;
/*     */   }
/*     */   public Map<String, Object> properties() {
/* 163 */     return this.properties;
/*     */   }
/*     */   public WSBinding getWSBinding() {
/* 166 */     return this.wsBinding;
/*     */   }
/*     */   public void setWSBinding(WSBinding wsBinding) {
/* 169 */     this.wsBinding = wsBinding;
/*     */   }
/*     */   public MetadataReader getMetadataReader() {
/* 172 */     return this.metadataReader;
/*     */   }
/*     */   public void setMetadataReader(MetadataReader reader) {
/* 175 */     this.metadataReader = reader;
/*     */   }
/*     */   
/*     */   public Source getWsdlSource() {
/* 179 */     return this.wsdlSource;
/*     */   }
/*     */   public void setWsdlSource(Source wsdlSource) {
/* 182 */     this.wsdlSource = wsdlSource;
/*     */   }
/*     */   public EntityResolver getEntityResolver() {
/* 185 */     return this.entityResolver;
/*     */   }
/*     */   public void setEntityResolver(EntityResolver entityResolver) {
/* 188 */     this.entityResolver = entityResolver;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\databinding\DatabindingConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */