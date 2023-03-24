/*     */ package com.oracle.xmlns.webservices.jaxws_databinding;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceRef;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "")
/*     */ @XmlRootElement(name = "web-service-ref")
/*     */ public class XmlWebServiceRef
/*     */   implements WebServiceRef
/*     */ {
/*     */   @XmlAttribute(name = "name")
/*     */   protected String name;
/*     */   @XmlAttribute(name = "type")
/*     */   protected String type;
/*     */   @XmlAttribute(name = "mappedName")
/*     */   protected String mappedName;
/*     */   @XmlAttribute(name = "value")
/*     */   protected String value;
/*     */   @XmlAttribute(name = "wsdlLocation")
/*     */   protected String wsdlLocation;
/*     */   @XmlAttribute(name = "lookup")
/*     */   protected String lookup;
/*     */   
/*     */   public String getName() {
/* 102 */     return this.name;
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
/*     */   public void setName(String value) {
/* 114 */     this.name = value;
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
/*     */   public String getType() {
/* 126 */     return this.type;
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
/*     */   public void setType(String value) {
/* 138 */     this.type = value;
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
/*     */   public String getMappedName() {
/* 150 */     return this.mappedName;
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
/*     */   public void setMappedName(String value) {
/* 162 */     this.mappedName = value;
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
/*     */   public String getValue() {
/* 174 */     return this.value;
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
/*     */   public void setValue(String value) {
/* 186 */     this.value = value;
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
/*     */   public String getWsdlLocation() {
/* 198 */     return this.wsdlLocation;
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
/*     */   public void setWsdlLocation(String value) {
/* 210 */     this.wsdlLocation = value;
/*     */   }
/*     */   
/*     */   public String getLookup() {
/* 214 */     return this.lookup;
/*     */   }
/*     */   
/*     */   public void setLookup(String lookup) {
/* 218 */     this.lookup = lookup;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/* 223 */     return Util.nullSafe(this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> type() {
/* 228 */     if (this.type == null) {
/* 229 */       return Object.class;
/*     */     }
/* 231 */     return Util.findClass(this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public String mappedName() {
/* 236 */     return Util.nullSafe(this.mappedName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends Service> value() {
/* 242 */     if (this.value == null) {
/* 243 */       return Service.class;
/*     */     }
/* 245 */     return (Class)Util.findClass(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String wsdlLocation() {
/* 250 */     return Util.nullSafe(this.wsdlLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public String lookup() {
/* 255 */     return Util.nullSafe(this.lookup);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Annotation> annotationType() {
/* 260 */     return (Class)WebServiceRef.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\xmlns\webservices\jaxws_databinding\XmlWebServiceRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */