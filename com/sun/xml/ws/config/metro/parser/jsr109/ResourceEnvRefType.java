/*     */ package com.sun.xml.ws.config.metro.parser.jsr109;
/*     */ 
/*     */ import com.sun.xml.bind.Locatable;
/*     */ import com.sun.xml.bind.annotation.XmlLocation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import org.xml.sax.Locator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlType(name = "resource-env-refType", propOrder = {"description", "resourceEnvRefName", "resourceEnvRefType", "mappedName", "injectionTarget", "lookupName"})
/*     */ public class ResourceEnvRefType
/*     */   implements Locatable
/*     */ {
/*     */   protected List<DescriptionType> description;
/*     */   @XmlElement(name = "resource-env-ref-name", required = true)
/*     */   protected JndiNameType resourceEnvRefName;
/*     */   @XmlElement(name = "resource-env-ref-type")
/*     */   protected FullyQualifiedClassType resourceEnvRefType;
/*     */   @XmlElement(name = "mapped-name")
/*     */   protected XsdStringType mappedName;
/*     */   @XmlElement(name = "injection-target")
/*     */   protected List<InjectionTargetType> injectionTarget;
/*     */   @XmlElement(name = "lookup-name")
/*     */   protected XsdStringType lookupName;
/*     */   @XmlAttribute(name = "id")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   @XmlID
/*     */   @XmlSchemaType(name = "ID")
/*     */   protected java.lang.String id;
/*     */   @XmlLocation
/*     */   @XmlTransient
/*     */   protected Locator locator;
/*     */   
/*     */   public List<DescriptionType> getDescription() {
/* 179 */     if (this.description == null) {
/* 180 */       this.description = new ArrayList<DescriptionType>();
/*     */     }
/* 182 */     return this.description;
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
/*     */   public JndiNameType getResourceEnvRefName() {
/* 194 */     return this.resourceEnvRefName;
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
/*     */   public void setResourceEnvRefName(JndiNameType value) {
/* 206 */     this.resourceEnvRefName = value;
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
/*     */   public FullyQualifiedClassType getResourceEnvRefType() {
/* 218 */     return this.resourceEnvRefType;
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
/*     */   public void setResourceEnvRefType(FullyQualifiedClassType value) {
/* 230 */     this.resourceEnvRefType = value;
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
/*     */   public XsdStringType getMappedName() {
/* 242 */     return this.mappedName;
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
/*     */   public void setMappedName(XsdStringType value) {
/* 254 */     this.mappedName = value;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<InjectionTargetType> getInjectionTarget() {
/* 280 */     if (this.injectionTarget == null) {
/* 281 */       this.injectionTarget = new ArrayList<InjectionTargetType>();
/*     */     }
/* 283 */     return this.injectionTarget;
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
/*     */   public XsdStringType getLookupName() {
/* 295 */     return this.lookupName;
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
/*     */   public void setLookupName(XsdStringType value) {
/* 307 */     this.lookupName = value;
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
/*     */   public java.lang.String getId() {
/* 319 */     return this.id;
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
/*     */   public void setId(java.lang.String value) {
/* 331 */     this.id = value;
/*     */   }
/*     */   
/*     */   public Locator sourceLocation() {
/* 335 */     return this.locator;
/*     */   }
/*     */   
/*     */   public void setSourceLocation(Locator newLocator) {
/* 339 */     this.locator = newLocator;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\parser\jsr109\ResourceEnvRefType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */