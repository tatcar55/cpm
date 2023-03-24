/*    */ package com.sun.xml.rpc.wsdl.document.schema;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*    */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*    */ import com.sun.xml.rpc.wsdl.framework.GloballyKnown;
/*    */ import com.sun.xml.rpc.wsdl.framework.Kind;
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
/*    */ 
/*    */ 
/*    */ public class SchemaEntity
/*    */   extends Entity
/*    */   implements GloballyKnown
/*    */ {
/*    */   private Schema _parent;
/*    */   private SchemaElement _element;
/*    */   private Kind _kind;
/*    */   private QName _name;
/*    */   
/*    */   public SchemaEntity(Schema parent, SchemaElement element, Kind kind, QName name) {
/* 47 */     this._parent = parent;
/* 48 */     this._element = element;
/* 49 */     this._kind = kind;
/* 50 */     this._name = name;
/*    */   }
/*    */   
/*    */   public SchemaElement getElement() {
/* 54 */     return this._element;
/*    */   }
/*    */   
/*    */   public QName getElementName() {
/* 58 */     return this._element.getQName();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 62 */     return this._name.getLocalPart();
/*    */   }
/*    */   
/*    */   public Kind getKind() {
/* 66 */     return this._kind;
/*    */   }
/*    */   
/*    */   public Schema getSchema() {
/* 70 */     return this._parent;
/*    */   }
/*    */   
/*    */   public Defining getDefining() {
/* 74 */     return this._parent;
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\schema\SchemaEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */