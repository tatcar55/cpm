/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributeEvent
/*     */   extends AbstractXMLEvent
/*     */   implements Attribute
/*     */ {
/*     */   private boolean specified = true;
/*     */   private QName name;
/*     */   private String value;
/*  61 */   private String dtdType = "CDATA";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeEvent(QName name, String value) {
/*  71 */     this.name = name;
/*  72 */     this.value = value;
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
/*     */   public AttributeEvent(QName name, String value, boolean specified) {
/*  87 */     this.name = name;
/*  88 */     this.value = value;
/*  89 */     this.specified = specified;
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
/*     */   public AttributeEvent(QName name, String value, Location location) {
/* 102 */     super(location);
/* 103 */     this.name = name;
/* 104 */     this.value = value;
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
/*     */   public AttributeEvent(QName name, String value, Location location, QName schemaType) {
/* 119 */     super(location, schemaType);
/* 120 */     this.name = name;
/* 121 */     this.value = value;
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
/*     */   public AttributeEvent(QName name, String value, boolean specified, String dtdType, Location location, QName schemaType) {
/* 140 */     super(location, schemaType);
/* 141 */     this.name = name;
/* 142 */     this.value = value;
/* 143 */     this.specified = specified;
/* 144 */     this.dtdType = dtdType;
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
/*     */   public AttributeEvent(QName name, String value, Attribute that) {
/* 159 */     super(that);
/* 160 */     this.specified = that.isSpecified();
/* 161 */     this.name = (name == null) ? that.getName() : name;
/* 162 */     this.value = (value == null) ? that.getValue() : value;
/* 163 */     this.dtdType = that.getDTDType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeEvent(Attribute that) {
/* 174 */     super(that);
/* 175 */     this.specified = that.isSpecified();
/* 176 */     this.name = that.getName();
/* 177 */     this.value = that.getValue();
/* 178 */     this.dtdType = that.getDTDType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventType() {
/* 185 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 191 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 197 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecified() {
/* 203 */     return this.specified;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDTDType() {
/* 209 */     return this.dtdType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\AttributeEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */