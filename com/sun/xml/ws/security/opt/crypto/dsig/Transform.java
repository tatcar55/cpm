/*     */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.InclusiveNamespacesType;
/*     */ import com.sun.xml.security.core.dsig.ObjectFactory;
/*     */ import com.sun.xml.security.core.dsig.TransformType;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.ExcC14NParameterSpec;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.StAXEnvelopedTransformWriter;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.StAXSTRTransformWriter;
/*     */ import com.sun.xml.ws.security.secext10.TransformationParametersType;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ @XmlRootElement(name = "Transform", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*     */ public class Transform
/*     */   extends TransformType
/*     */   implements Transform
/*     */ {
/*     */   @XmlTransient
/*  82 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */   
/*     */   @XmlTransient
/*  85 */   private AlgorithmParameterSpec algSpec = null;
/*     */   @XmlTransient
/*  87 */   private String refId = "";
/*     */ 
/*     */   
/*     */   @XmlTransient
/*     */   private Exc14nCanonicalizer _exc14nTransform;
/*     */ 
/*     */   
/*     */   public AlgorithmParameterSpec getParameterSpec() {
/*  95 */     return this.algSpec;
/*     */   }
/*     */   
/*     */   public void setParameterSpec(AlgorithmParameterSpec algSpec) {
/*  99 */     this.algSpec = algSpec;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(List content) {
/* 104 */     this.content = content;
/*     */   }
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext) throws TransformException {
/* 108 */     if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(getAlgorithm())) {
/* 109 */       if (this._exc14nTransform == null) {
/* 110 */         this._exc14nTransform = new Exc14nCanonicalizer();
/*     */       }
/* 112 */       return this._exc14nTransform.transform(data, xMLCryptoContext);
/* 113 */     }  if (getAlgorithm().equals("http://www.w3.org/2000/09/xmldsig#enveloped-signature"))
/* 114 */       return (Data)new StAXEnvelopedTransformWriter(data); 
/* 115 */     if (getAlgorithm().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform")) {
/* 116 */       return (Data)new StAXSTRTransformWriter(data, xMLCryptoContext, this.refId);
/*     */     }
/* 118 */     throw new UnsupportedOperationException("Algorithm Transform " + getAlgorithm() + " not supported yet");
/*     */   }
/*     */ 
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext, OutputStream outputStream) throws TransformException {
/* 123 */     if (getAlgorithm().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform")) {
/* 124 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 125 */       OutputStream outputStream1 = outputStream;
/* 126 */       if (logger.isLoggable(Level.FINEST)) {
/* 127 */         outputStream1 = byteArrayOutputStream;
/*     */       }
/* 129 */       StAXEXC14nCanonicalizerImpl _canonicalizer = null;
/* 130 */       if (this.algSpec != null || this.content.size() > 0) {
/* 131 */         Object ob = this.content.get(0);
/* 132 */         if (ob instanceof JAXBElement) {
/* 133 */           JAXBElement<TransformationParametersType> el = (JAXBElement)ob;
/* 134 */           TransformationParametersType tp = el.getValue();
/* 135 */           CanonicalizationMethod cm = tp.getAny().get(0);
/* 136 */           String algo = cm.getAlgorithm();
/* 137 */           if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(algo)) {
/* 138 */             _canonicalizer = new StAXEXC14nCanonicalizerImpl();
/* 139 */             if (!logger.isLoggable(Level.FINEST)) {
/* 140 */               _canonicalizer.setStream(outputStream);
/*     */             } else {
/* 142 */               _canonicalizer.setStream(outputStream1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 148 */       StAXSTRTransformWriter strWriter = new StAXSTRTransformWriter(data, xMLCryptoContext, this.refId);
/*     */       try {
/* 150 */         strWriter.write((XMLStreamWriter)_canonicalizer);
/* 151 */       } catch (XMLStreamException ex) {
/* 152 */         throw new TransformException(ex);
/*     */       } 
/*     */       
/* 155 */       if (logger.isLoggable(Level.FINEST)) {
/* 156 */         logger.log(Level.FINEST, LogStringsMessages.WSS_1757_CANONICALIZED_TARGET_VALUE(byteArrayOutputStream.toString()));
/*     */         try {
/* 158 */           outputStream.write(byteArrayOutputStream.toByteArray());
/* 159 */           return null;
/* 160 */         } catch (IOException ex) {
/* 161 */           throw new TransformException(ex);
/*     */         } 
/*     */       } 
/* 164 */       return null;
/*     */     } 
/*     */     
/* 167 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 168 */     OutputStream fis = outputStream;
/* 169 */     if (logger.isLoggable(Level.FINEST)) {
/* 170 */       fis = bos;
/*     */     }
/* 172 */     if (getAlgorithm().intern() == "http://www.w3.org/2001/10/xml-exc-c14n#".intern()) {
/* 173 */       if (this._exc14nTransform == null) {
/* 174 */         this._exc14nTransform = new Exc14nCanonicalizer();
/*     */         try {
/* 176 */           this._exc14nTransform.init((TransformParameterSpec)this.algSpec);
/* 177 */         } catch (InvalidAlgorithmParameterException e) {
/* 178 */           throw new TransformException(e);
/*     */         } 
/*     */       } 
/* 181 */       if (!logger.isLoggable(Level.FINEST)) {
/*     */         
/* 183 */         Data canData = this._exc14nTransform.transform(data, xMLCryptoContext, fis);
/* 184 */         setContentList();
/* 185 */         return canData;
/*     */       } 
/* 187 */       this._exc14nTransform.transform(data, xMLCryptoContext, fis);
/* 188 */       setContentList();
/* 189 */       logger.log(Level.FINEST, LogStringsMessages.WSS_1757_CANONICALIZED_TARGET_VALUE(bos.toString()));
/*     */       try {
/* 191 */         outputStream.write(bos.toByteArray());
/* 192 */         return null;
/* 193 */       } catch (IOException ex) {
/* 194 */         throw new TransformException(ex);
/*     */       } 
/*     */     } 
/* 197 */     if (getAlgorithm().intern() == "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform") {
/* 198 */       ACOTransform acoTransform = new ACOTransform();
/* 199 */       return acoTransform.transform(data, xMLCryptoContext, fis);
/* 200 */     }  if (getAlgorithm().intern() != "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform")
/*     */     {
/* 202 */       if (getAlgorithm().intern() == "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Ciphertext-Transform");
/*     */     }
/*     */ 
/*     */     
/* 206 */     throw new UnsupportedOperationException("Algorithm Transform " + getAlgorithm() + " not supported yet");
/*     */   }
/*     */   
/*     */   public boolean isFeatureSupported(String string) {
/* 210 */     return false;
/*     */   }
/*     */   
/*     */   public void setReferenceId(String id) {
/* 214 */     this.refId = id;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setContentList() {
/* 219 */     if (this.algSpec != null) {
/* 220 */       this.content = setInclusiveNamespaces((ExcC14NParameterSpec)this.algSpec);
/*     */     }
/*     */   }
/*     */   
/*     */   private List setInclusiveNamespaces(ExcC14NParameterSpec spec) {
/* 225 */     ObjectFactory objFac = new ObjectFactory();
/* 226 */     InclusiveNamespacesType incList = objFac.createInclusiveNamespaces();
/* 227 */     List<String> prefixList = spec.getPrefixList();
/* 228 */     for (int j = 0; j < prefixList.size(); j++) {
/* 229 */       String prefix = prefixList.get(j);
/* 230 */       incList.addToPrefixList(prefix);
/*     */     } 
/* 232 */     JAXBElement<InclusiveNamespacesType> je = objFac.createInclusiveNamespaces(incList);
/* 233 */     List<JAXBElement<InclusiveNamespacesType>> contentList = new ArrayList();
/* 234 */     contentList.add(je);
/* 235 */     return contentList;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\Transform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */