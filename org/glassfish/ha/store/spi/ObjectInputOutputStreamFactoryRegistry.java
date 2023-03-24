/*    */ package org.glassfish.ha.store.spi;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectInputOutputStreamFactoryRegistry
/*    */ {
/* 53 */   private static ObjectInputOutputStreamFactory _factory = new DefaultObjectInputOutputStreamFactory();
/*    */   
/*    */   public static ObjectInputOutputStreamFactory getObjectInputOutputStreamFactory() {
/* 56 */     return _factory;
/*    */   }
/*    */   
/*    */   private static class DefaultObjectInputOutputStreamFactory
/*    */     implements ObjectInputOutputStreamFactory {
/*    */     private DefaultObjectInputOutputStreamFactory() {}
/*    */     
/*    */     public ObjectOutputStream createObjectOutputStream(OutputStream os) throws IOException {
/* 64 */       return new ObjectOutputStream(os);
/*    */     }
/*    */ 
/*    */     
/*    */     public ObjectInputStream createObjectInputStream(InputStream is, ClassLoader loader) throws IOException {
/* 69 */       return new ObjectInputStreamWithLoader(is, loader);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\spi\ObjectInputOutputStreamFactoryRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */