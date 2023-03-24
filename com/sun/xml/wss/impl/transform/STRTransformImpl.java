/*     */ package com.sun.xml.wss.impl.transform;
/*     */ 
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.NodeSetData;
/*     */ import javax.xml.crypto.OctetStreamData;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class STRTransformImpl
/*     */ {
/*  70 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
/*     */ 
/*     */   
/*     */   protected static Data transform(Data data, XMLCryptoContext context, OutputStream outputStream) throws TransformException {
/*     */     try {
/*  75 */       Set nodeSet = getNodeSet(data);
/*  76 */       if (outputStream == null) {
/*  77 */         ByteArrayOutputStream bs = new ByteArrayOutputStream();
/*  78 */         (new Canonicalizer20010315ExclOmitComments()).engineCanonicalizeXPathNodeSet(nodeSet, "", bs, context);
/*  79 */         OctetStreamData osd = new OctetStreamData(new ByteArrayInputStream(bs.toByteArray()));
/*  80 */         bs.close();
/*  81 */         return osd;
/*     */       } 
/*  83 */       (new Canonicalizer20010315ExclOmitComments()).engineCanonicalizeXPathNodeSet(nodeSet, "", outputStream, context);
/*     */       
/*  85 */       return null;
/*  86 */     } catch (Exception ex) {
/*  87 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1322_STR_TRANSFORM(), ex);
/*     */       
/*  89 */       return null;
/*     */     } 
/*     */   }
/*     */   private static Set getNodeSet(Data data) throws TransformException {
/*  93 */     HashSet<Object> nodeSet = new HashSet();
/*  94 */     if (data instanceof NodeSetData) {
/*  95 */       Iterator it = ((NodeSetData)data).iterator();
/*  96 */       while (it.hasNext()) {
/*  97 */         Object node = it.next();
/*     */ 
/*     */ 
/*     */         
/* 101 */         nodeSet.add(node);
/*     */       } 
/* 103 */     } else if (data instanceof OctetStreamData) {
/*     */       try {
/* 105 */         DocumentBuilderFactory factory = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*     */         
/* 107 */         factory.setNamespaceAware(true);
/* 108 */         Document doc = factory.newDocumentBuilder().parse(((OctetStreamData)data).getOctetStream());
/* 109 */         toNodeSet(doc, nodeSet);
/* 110 */       } catch (Exception ex) {
/* 111 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1322_STR_TRANSFORM(), ex);
/* 112 */         throw new TransformException(ex.getMessage());
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return nodeSet;
/*     */   }
/*     */   static final void toNodeSet(Node rootNode, Set<Node> result) {
/*     */     Element el;
/*     */     Node r;
/* 121 */     if (rootNode == null)
/* 122 */       return;  switch (rootNode.getNodeType()) {
/*     */       case 1:
/* 124 */         result.add(rootNode);
/* 125 */         el = (Element)rootNode;
/* 126 */         if (el.hasAttributes()) {
/* 127 */           NamedNodeMap nl = ((Element)rootNode).getAttributes();
/* 128 */           for (int i = 0; i < nl.getLength(); i++) {
/* 129 */             result.add(nl.item(i));
/*     */           }
/*     */         } 
/*     */       
/*     */       case 9:
/* 134 */         for (r = rootNode.getFirstChild(); r != null; r = r.getNextSibling()) {
/* 135 */           if (r.getNodeType() == 3) {
/* 136 */             result.add(r);
/* 137 */             while (r != null && r.getNodeType() == 3) {
/* 138 */               r = r.getNextSibling();
/*     */             }
/* 140 */             if (r == null)
/*     */               return; 
/*     */           } 
/* 143 */           toNodeSet(r, result);
/*     */         } 
/*     */         return;
/*     */       case 8:
/*     */         return;
/*     */       case 10:
/*     */         return;
/*     */     } 
/* 151 */     result.add(rootNode);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\transform\STRTransformImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */