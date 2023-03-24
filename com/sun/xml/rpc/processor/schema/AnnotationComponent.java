/*    */ package com.sun.xml.rpc.processor.schema;
/*    */ 
/*    */ import com.sun.xml.rpc.util.NullIterator;
/*    */ import com.sun.xml.rpc.wsdl.document.schema.SchemaAttribute;
/*    */ import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ public class AnnotationComponent
/*    */   extends Component
/*    */ {
/*    */   private List _applicationInformationElements;
/*    */   private List _userInformationElements;
/*    */   private List _attributes;
/*    */   
/*    */   public void addApplicationInformation(SchemaElement element) {
/* 46 */     if (this._applicationInformationElements == null) {
/* 47 */       this._applicationInformationElements = new ArrayList();
/*    */     }
/*    */     
/* 50 */     this._applicationInformationElements.add(element);
/*    */   }
/*    */   
/*    */   public void addUserInformation(SchemaElement element) {
/* 54 */     if (this._userInformationElements == null) {
/* 55 */       this._userInformationElements = new ArrayList();
/*    */     }
/*    */     
/* 58 */     this._userInformationElements.add(element);
/*    */   }
/*    */   
/*    */   public Iterator attributes() {
/* 62 */     if (this._attributes == null) {
/* 63 */       return (Iterator)NullIterator.getInstance();
/*    */     }
/* 65 */     return this._attributes.iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public void addAttribute(SchemaAttribute attribute) {
/* 70 */     if (this._attributes == null) {
/* 71 */       this._attributes = new ArrayList();
/*    */     }
/*    */     
/* 74 */     this._attributes.add(attribute);
/*    */   }
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 78 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\AnnotationComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */