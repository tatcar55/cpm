/*    */ package com.sun.xml.bind.v2.runtime.output;
/*    */ 
/*    */ import com.sun.xml.bind.v2.runtime.unmarshaller.Base64Data;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamWriter;
/*    */ import org.jvnet.staxex.XMLStreamWriterEx;
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
/*    */ public final class StAXExStreamWriterOutput
/*    */   extends XMLStreamWriterOutput
/*    */ {
/*    */   private final XMLStreamWriterEx out;
/*    */   
/*    */   public StAXExStreamWriterOutput(XMLStreamWriterEx out) {
/* 58 */     super((XMLStreamWriter)out);
/* 59 */     this.out = out;
/*    */   }
/*    */   
/*    */   public void text(Pcdata value, boolean needsSeparatingWhitespace) throws XMLStreamException {
/* 63 */     if (needsSeparatingWhitespace) {
/* 64 */       this.out.writeCharacters(" ");
/*    */     }
/*    */     
/* 67 */     if (!(value instanceof Base64Data)) {
/* 68 */       this.out.writeCharacters(value.toString());
/*    */     } else {
/* 70 */       Base64Data v = (Base64Data)value;
/* 71 */       this.out.writeBinary(v.getDataHandler());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\StAXExStreamWriterOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */