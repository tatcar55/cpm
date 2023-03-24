/*    */ package com.sun.xml.rpc.sp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import org.xml.sax.EntityResolver;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.Locator;
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
/*    */ class ExternalEntity
/*    */   extends EntityDecl
/*    */ {
/*    */   String systemId;
/*    */   String publicId;
/*    */   String notation;
/*    */   
/*    */   public ExternalEntity(Locator l) {}
/*    */   
/*    */   public InputSource getInputSource(EntityResolver r) throws SAXException, IOException {
/* 53 */     InputSource retval = r.resolveEntity(this.publicId, this.systemId);
/*    */     
/* 55 */     if (retval == null)
/* 56 */       retval = Resolver.createInputSource(new URL(this.systemId), false); 
/* 57 */     return retval;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\ExternalEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */