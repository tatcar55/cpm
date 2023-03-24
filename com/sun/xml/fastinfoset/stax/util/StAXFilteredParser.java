/*    */ package com.sun.xml.fastinfoset.stax.util;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*    */ import javax.xml.stream.StreamFilter;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StAXFilteredParser
/*    */   extends StAXParserWrapper
/*    */ {
/*    */   private StreamFilter _filter;
/*    */   
/*    */   public StAXFilteredParser() {}
/*    */   
/*    */   public StAXFilteredParser(XMLStreamReader reader, StreamFilter filter) {
/* 33 */     super(reader);
/* 34 */     this._filter = filter;
/*    */   }
/*    */   
/*    */   public void setFilter(StreamFilter filter) {
/* 38 */     this._filter = filter;
/*    */   }
/*    */ 
/*    */   
/*    */   public int next() throws XMLStreamException {
/* 43 */     if (hasNext())
/* 44 */       return super.next(); 
/* 45 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.noMoreItems"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() throws XMLStreamException {
/* 50 */     while (super.hasNext()) {
/* 51 */       if (this._filter.accept(getReader())) return true; 
/* 52 */       super.next();
/*    */     } 
/* 54 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\sta\\util\StAXFilteredParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */