/*    */ package com.sun.xml.rpc.wsdl.document.schema;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.framework.WriterContext;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchemaAttribute
/*    */ {
/*    */   private String _nsURI;
/*    */   private String _localName;
/*    */   private String _value;
/*    */   private QName _qnameValue;
/*    */   private SchemaElement _parent;
/*    */   
/*    */   public SchemaAttribute() {}
/*    */   
/*    */   public SchemaAttribute(String localName) {
/* 43 */     this._localName = localName;
/*    */   }
/*    */   
/*    */   public String getNamespaceURI() {
/* 47 */     return this._nsURI;
/*    */   }
/*    */   
/*    */   public void setNamespaceURI(String s) {
/* 51 */     this._nsURI = s;
/*    */   }
/*    */   
/*    */   public String getLocalName() {
/* 55 */     return this._localName;
/*    */   }
/*    */   
/*    */   public void setLocalName(String s) {
/* 59 */     this._localName = s;
/*    */   }
/*    */   
/*    */   public QName getQName() {
/* 63 */     return new QName(this._nsURI, this._localName);
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 67 */     if (this._qnameValue != null) {
/* 68 */       if (this._parent == null) {
/* 69 */         throw new IllegalStateException();
/*    */       }
/* 71 */       return this._parent.asString(this._qnameValue);
/*    */     } 
/*    */     
/* 74 */     return this._value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValue(WriterContext context) {
/* 79 */     if (this._qnameValue != null) {
/* 80 */       return context.getQNameString(this._qnameValue);
/*    */     }
/* 82 */     return this._value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(String s) {
/* 87 */     this._value = s;
/*    */   }
/*    */   
/*    */   public void setValue(QName name) {
/* 91 */     this._qnameValue = name;
/*    */   }
/*    */   
/*    */   public SchemaElement getParent() {
/* 95 */     return this._parent;
/*    */   }
/*    */   
/*    */   public void setParent(SchemaElement e) {
/* 99 */     this._parent = e;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\schema\SchemaAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */