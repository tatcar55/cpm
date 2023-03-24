/*    */ package javanet.staxutils;
/*    */ 
/*    */ import java.io.Writer;
/*    */ import javanet.staxutils.io.StAXStreamWriter;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamWriter;
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
/*    */ public class OutputFactory
/*    */   extends BaseXMLOutputFactory
/*    */ {
/*    */   public XMLStreamWriter createXMLStreamWriter(Writer stream) throws XMLStreamException {
/* 53 */     return (XMLStreamWriter)new StAXStreamWriter(stream);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\OutputFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */