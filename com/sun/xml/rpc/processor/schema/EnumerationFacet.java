/*    */ package com.sun.xml.rpc.processor.schema;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class EnumerationFacet
/*    */   extends ConstrainingFacet
/*    */ {
/*    */   private List values;
/*    */   private Map prefixes;
/*    */   
/*    */   public EnumerationFacet() {
/* 44 */     super(SchemaConstants.QNAME_ENUMERATION);
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
/* 67 */     this.values = new ArrayList();
/* 68 */     this.prefixes = new HashMap<Object, Object>();
/*    */   }
/*    */   
/*    */   public void addValue(String s) {
/*    */     this.values.add(s);
/*    */   }
/*    */   
/*    */   public Iterator values() {
/*    */     return this.values.iterator();
/*    */   }
/*    */   
/*    */   public void addPrefix(String prefix, String nspace) {
/*    */     this.prefixes.put(prefix, nspace);
/*    */   }
/*    */   
/*    */   public String getNamespaceURI(String prefix) {
/*    */     return (String)this.prefixes.get(prefix);
/*    */   }
/*    */   
/*    */   public Map getPrefixes() {
/*    */     return this.prefixes;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\EnumerationFacet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */