/*     */ package com.sun.xml.ws.runtime.config;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  74 */   private static final QName _Tubelines_QNAME = new QName("http://java.sun.com/xml/ns/metro/config", "tubelines");
/*  75 */   private static final QName _TubelineMapping_QNAME = new QName("http://java.sun.com/xml/ns/metro/config", "tubeline-mapping");
/*  76 */   private static final QName _Tubeline_QNAME = new QName("http://java.sun.com/xml/ns/metro/config", "tubeline");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TubeFactoryConfig createTubeFactoryConfig() {
/*  90 */     return new TubeFactoryConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TubeFactoryList createTubeFactoryList() {
/*  98 */     return new TubeFactoryList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TubelineDefinition createTubelineDefinition() {
/* 106 */     return new TubelineDefinition();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tubelines createTubelines() {
/* 114 */     return new Tubelines();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetroConfig createMetroConfig() {
/* 122 */     return new MetroConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TubelineMapping createTubelineMapping() {
/* 130 */     return new TubelineMapping();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/metro/config", name = "tubelines")
/*     */   public JAXBElement<Tubelines> createTubelines(Tubelines value) {
/* 139 */     return new JAXBElement<Tubelines>(_Tubelines_QNAME, Tubelines.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/metro/config", name = "tubeline-mapping")
/*     */   public JAXBElement<TubelineMapping> createTubelineMapping(TubelineMapping value) {
/* 148 */     return new JAXBElement<TubelineMapping>(_TubelineMapping_QNAME, TubelineMapping.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://java.sun.com/xml/ns/metro/config", name = "tubeline")
/*     */   public JAXBElement<TubelineDefinition> createTubeline(TubelineDefinition value) {
/* 157 */     return new JAXBElement<TubelineDefinition>(_Tubeline_QNAME, TubelineDefinition.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\runtime\config\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */