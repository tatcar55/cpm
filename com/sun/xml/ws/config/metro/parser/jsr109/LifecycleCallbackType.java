/*     */ package com.sun.xml.ws.config.metro.parser.jsr109;
/*     */ 
/*     */ import com.sun.xml.bind.Locatable;
/*     */ import com.sun.xml.bind.annotation.XmlLocation;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "lifecycle-callbackType", propOrder = {"lifecycleCallbackClass", "lifecycleCallbackMethod"})
/*     */ public class LifecycleCallbackType
/*     */   implements Locatable
/*     */ {
/*     */   @XmlElement(name = "lifecycle-callback-class")
/*     */   protected FullyQualifiedClassType lifecycleCallbackClass;
/*     */   @XmlElement(name = "lifecycle-callback-method", required = true)
/*     */   protected JavaIdentifierType lifecycleCallbackMethod;
/*     */   @XmlLocation
/*     */   @XmlTransient
/*     */   protected Locator locator;
/*     */   
/*     */   public FullyQualifiedClassType getLifecycleCallbackClass() {
/* 122 */     return this.lifecycleCallbackClass;
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
/*     */   public void setLifecycleCallbackClass(FullyQualifiedClassType value) {
/* 134 */     this.lifecycleCallbackClass = value;
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
/*     */   public JavaIdentifierType getLifecycleCallbackMethod() {
/* 146 */     return this.lifecycleCallbackMethod;
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
/*     */   public void setLifecycleCallbackMethod(JavaIdentifierType value) {
/* 158 */     this.lifecycleCallbackMethod = value;
/*     */   }
/*     */   
/*     */   public Locator sourceLocation() {
/* 162 */     return this.locator;
/*     */   }
/*     */   
/*     */   public void setSourceLocation(Locator newLocator) {
/* 166 */     this.locator = newLocator;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\parser\jsr109\LifecycleCallbackType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */