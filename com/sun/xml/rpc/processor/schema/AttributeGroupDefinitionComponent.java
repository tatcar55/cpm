/*    */ package com.sun.xml.rpc.processor.schema;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public class AttributeGroupDefinitionComponent
/*    */   extends Component
/*    */ {
/*    */   private QName _name;
/* 42 */   private List _attributeUses = new ArrayList();
/*    */   private WildcardComponent _attributeWildcard;
/*    */   
/*    */   public QName getName() {
/* 46 */     return this._name;
/*    */   }
/*    */   private AnnotationComponent _annotation;
/*    */   public void setName(QName name) {
/* 50 */     this._name = name;
/*    */   }
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 54 */     visitor.visit(this);
/*    */   }
/*    */   
/*    */   public Iterator attributeUses() {
/* 58 */     return this._attributeUses.iterator();
/*    */   }
/*    */   
/*    */   public void addAttributeUse(AttributeUseComponent c) {
/* 62 */     this._attributeUses.add(c);
/*    */   }
/*    */   
/*    */   public void addAttributeGroup(AttributeGroupDefinitionComponent c) {
/* 66 */     for (Iterator<AttributeUseComponent> iter = c.attributeUses(); iter.hasNext(); ) {
/* 67 */       AttributeUseComponent a = iter.next();
/* 68 */       addAttributeUse(a);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\AttributeGroupDefinitionComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */