/*    */ package com.sun.xml.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.bind.api.AccessorException;
/*    */ import java.io.IOException;
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
/*    */ public class InlineBinaryTransducer<V>
/*    */   extends FilterTransducer<V>
/*    */ {
/*    */   public InlineBinaryTransducer(Transducer<V> core) {
/* 59 */     super(core);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public CharSequence print(@NotNull V o) throws AccessorException {
/* 64 */     XMLSerializer w = XMLSerializer.getInstance();
/* 65 */     boolean old = w.setInlineBinaryFlag(true);
/*    */     try {
/* 67 */       return this.core.print(o);
/*    */     } finally {
/* 69 */       w.setInlineBinaryFlag(old);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeText(XMLSerializer w, V o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 75 */     boolean old = w.setInlineBinaryFlag(true);
/*    */     try {
/* 77 */       this.core.writeText(w, o, fieldName);
/*    */     } finally {
/* 79 */       w.setInlineBinaryFlag(old);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeLeafElement(XMLSerializer w, Name tagName, V o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 85 */     boolean old = w.setInlineBinaryFlag(true);
/*    */     try {
/* 87 */       this.core.writeLeafElement(w, tagName, o, fieldName);
/*    */     } finally {
/* 89 */       w.setInlineBinaryFlag(old);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\InlineBinaryTransducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */