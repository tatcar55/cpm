/*    */ package com.sun.xml.ws.util;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.server.SDDocument;
/*    */ import com.sun.xml.ws.wsdl.SDDocumentResolver;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MetadataUtil
/*    */ {
/*    */   public static Map<String, SDDocument> getMetadataClosure(@NotNull String systemId, @NotNull SDDocumentResolver resolver, boolean onlyTopLevelSchemas) {
/* 68 */     Map<String, SDDocument> closureDocs = new HashMap<String, SDDocument>();
/* 69 */     Set<String> remaining = new HashSet<String>();
/* 70 */     remaining.add(systemId);
/*    */     
/* 72 */     while (!remaining.isEmpty()) {
/* 73 */       Iterator<String> it = remaining.iterator();
/* 74 */       String current = it.next();
/* 75 */       remaining.remove(current);
/*    */       
/* 77 */       SDDocument currentDoc = resolver.resolve(current);
/* 78 */       SDDocument old = closureDocs.put(currentDoc.getURL().toExternalForm(), currentDoc);
/* 79 */       assert old == null;
/*    */       
/* 81 */       Set<String> imports = currentDoc.getImports();
/* 82 */       if (!currentDoc.isSchema() || !onlyTopLevelSchemas) {
/* 83 */         for (String importedDoc : imports) {
/* 84 */           if (closureDocs.get(importedDoc) == null) {
/* 85 */             remaining.add(importedDoc);
/*    */           }
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 91 */     return closureDocs;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\MetadataUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */