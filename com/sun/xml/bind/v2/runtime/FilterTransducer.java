/*    */ package com.sun.xml.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.bind.api.AccessorException;
/*    */ import java.io.IOException;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import org.xml.sax.SAXException;
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
/*    */ public abstract class FilterTransducer<T>
/*    */   implements Transducer<T>
/*    */ {
/*    */   protected final Transducer<T> core;
/*    */   
/*    */   protected FilterTransducer(Transducer<T> core) {
/* 62 */     this.core = core;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean isDefault() {
/* 68 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useNamespace() {
/* 72 */     return this.core.useNamespace();
/*    */   }
/*    */   
/*    */   public void declareNamespace(T o, XMLSerializer w) throws AccessorException {
/* 76 */     this.core.declareNamespace(o, w);
/*    */   }
/*    */   @NotNull
/*    */   public CharSequence print(@NotNull T o) throws AccessorException {
/* 80 */     return this.core.print(o);
/*    */   }
/*    */   
/*    */   public T parse(CharSequence lexical) throws AccessorException, SAXException {
/* 84 */     return this.core.parse(lexical);
/*    */   }
/*    */   
/*    */   public void writeText(XMLSerializer w, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 88 */     this.core.writeText(w, o, fieldName);
/*    */   }
/*    */   
/*    */   public void writeLeafElement(XMLSerializer w, Name tagName, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 92 */     this.core.writeLeafElement(w, tagName, o, fieldName);
/*    */   }
/*    */   
/*    */   public QName getTypeName(T instance) {
/* 96 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\FilterTransducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */