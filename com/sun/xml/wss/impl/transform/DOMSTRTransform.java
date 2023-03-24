/*     */ package com.sun.xml.wss.impl.transform;
/*     */ 
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.TransformService;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class DOMSTRTransform
/*     */   extends TransformService
/*     */ {
/*     */   private STRTransformParameterSpec params;
/*  85 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
/*     */ 
/*     */   
/*     */   public static final String WSSE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
/*     */   
/*     */   public static final String WSU = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
/*     */ 
/*     */   
/*     */   public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException {
/*  94 */     if (params == null) {
/*  95 */       throw new InvalidAlgorithmParameterException("params are required");
/*     */     }
/*  97 */     this.params = (STRTransformParameterSpec)params;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(XMLStructure params, XMLCryptoContext xMLCryptoContext) throws InvalidAlgorithmParameterException {
/* 102 */     DOMStructure domParams = (DOMStructure)params;
/*     */     try {
/* 104 */       unmarshalParams(domParams.getNode(), xMLCryptoContext);
/* 105 */     } catch (MarshalException me) {
/* 106 */       throw new InvalidAlgorithmParameterException(me.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public AlgorithmParameterSpec getParameterSpec() {
/* 111 */     return this.params;
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException {
/* 116 */     Node pn = ((DOMStructure)parent).getNode();
/* 117 */     Document ownerDoc = XMLUtil.getOwnerDocument(pn);
/*     */     
/* 119 */     String prefix = null;
/* 120 */     String dsPrefix = null;
/* 121 */     if (context != null) {
/* 122 */       prefix = context.getNamespacePrefix("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse");
/*     */       
/* 124 */       dsPrefix = context.getNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", context.getDefaultNamespacePrefix());
/*     */     } 
/*     */ 
/*     */     
/* 128 */     Element transformParamElem = XMLUtil.createElement(ownerDoc, "TransformationParameters", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", prefix);
/*     */ 
/*     */     
/* 131 */     CanonicalizationMethod cm = this.params.getCanonicalizationMethod();
/* 132 */     Element c14nElem = XMLUtil.createElement(ownerDoc, "CanonicalizationMethod", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
/*     */     
/* 134 */     c14nElem.setAttributeNS((String)null, "Algorithm", cm.getAlgorithm());
/*     */     
/* 136 */     C14NMethodParameterSpec cs = (C14NMethodParameterSpec)cm.getParameterSpec();
/*     */     
/* 138 */     if (cs != null) {
/* 139 */       TransformService cmSpi = null;
/*     */       try {
/* 141 */         cmSpi = TransformService.getInstance(cm.getAlgorithm(), "DOM");
/* 142 */         cmSpi.init(cs);
/* 143 */         cmSpi.marshalParams(new DOMStructure(c14nElem), context);
/* 144 */       } catch (Exception e) {
/* 145 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1321_STR_MARSHAL_TRANSFORM_ERROR(), e);
/* 146 */         throw new MarshalException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     transformParamElem.appendChild(c14nElem);
/* 151 */     pn.appendChild(transformParamElem);
/*     */   }
/*     */ 
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xc) throws TransformException {
/* 156 */     OutputStream outputStream = null;
/* 157 */     new STRTransformImpl(); return STRTransformImpl.transform(data, xc, outputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xc, OutputStream outputStream) throws TransformException {
/* 163 */     new STRTransformImpl(); return STRTransformImpl.transform(data, xc, outputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unmarshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException, InvalidAlgorithmParameterException {
/* 169 */     Element transformElem = (Element)((DOMStructure)parent).getNode();
/* 170 */     Element tpElem = XMLUtil.getFirstChildElement(transformElem);
/* 171 */     unmarshalParams(tpElem, context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unmarshalParams(Node tpElem, XMLCryptoContext context) throws MarshalException, InvalidAlgorithmParameterException {
/* 177 */     Element c14nElem = null;
/* 178 */     if (tpElem.getNodeType() == 9) {
/* 179 */       c14nElem = (Element)((Document)tpElem).getFirstChild();
/*     */     } else {
/* 181 */       c14nElem = XMLUtil.getFirstChildElement(tpElem);
/*     */     } 
/*     */     
/* 184 */     if (!"CanonicalizationMethod".equals(c14nElem.getLocalName())) {
/* 185 */       NodeList nl = c14nElem.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "CanonicalizationMethod");
/* 186 */       if (nl.getLength() > 0) c14nElem = (Element)nl.item(0); 
/*     */     } 
/* 188 */     if (c14nElem == null) {
/* 189 */       throw new InvalidAlgorithmParameterException("Cannont find CanonicalizationMethod in TransformationParameters element");
/*     */     }
/* 191 */     String c14nAlg = c14nElem.getAttributeNodeNS((String)null, "Algorithm").getValue();
/* 192 */     if (logger.isLoggable(Level.FINE)) {
/* 193 */       logger.log(Level.FINE, "C14 Algo=" + c14nAlg);
/*     */     }
/* 195 */     C14NMethodParameterSpec cs = null;
/* 196 */     Element paramsElem = XMLUtil.getFirstChildElement(c14nElem);
/* 197 */     TransformService cmSpi = null;
/*     */     try {
/* 199 */       cmSpi = TransformService.getInstance(c14nAlg, "DOM");
/* 200 */       if (paramsElem != null) {
/* 201 */         cmSpi.init(new DOMStructure(paramsElem), context);
/*     */       }
/*     */       
/* 204 */       CanonicalizationMethod cm = new STRC14NMethod(cmSpi);
/* 205 */       this.params = new STRTransformParameterSpec(cm);
/* 206 */     } catch (Throwable e) {
/* 207 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1320_STR_UN_TRANSFORM_ERROR(), e);
/* 208 */       throw new MarshalException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFeatureSupported(String str) {
/* 214 */     return false;
/*     */   }
/*     */   
/*     */   private static class STRC14NMethod implements CanonicalizationMethod {
/*     */     private TransformService cmSpi;
/*     */     
/*     */     STRC14NMethod(TransformService cmSpi) {
/* 221 */       this.cmSpi = cmSpi;
/*     */     } public String getAlgorithm() {
/* 223 */       return this.cmSpi.getAlgorithm();
/*     */     } public AlgorithmParameterSpec getParameterSpec() {
/* 225 */       return this.cmSpi.getParameterSpec();
/*     */     } public boolean isFeatureSupported(String feature) {
/* 227 */       return false;
/*     */     } public Data transform(Data data, XMLCryptoContext context) throws TransformException {
/* 229 */       return this.cmSpi.transform(data, context);
/*     */     }
/*     */     public Data transform(Data data, XMLCryptoContext context, OutputStream os) throws TransformException {
/* 232 */       return this.cmSpi.transform(data, context, os);
/*     */     } }
/*     */   
/*     */   public static class STRTransformParameterSpec implements TransformParameterSpec {
/*     */     private CanonicalizationMethod c14nMethod;
/*     */     
/*     */     public STRTransformParameterSpec(CanonicalizationMethod c14nMethod) {
/* 239 */       this.c14nMethod = c14nMethod;
/*     */     }
/*     */     public CanonicalizationMethod getCanonicalizationMethod() {
/* 242 */       return this.c14nMethod;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\transform\DOMSTRTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */