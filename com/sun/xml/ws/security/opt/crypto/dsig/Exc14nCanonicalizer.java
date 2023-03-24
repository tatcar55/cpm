/*     */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.JAXBData;
/*     */ import com.sun.xml.ws.security.opt.crypto.StreamWriterData;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.ExcC14NParameterSpec;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import com.sun.xml.wss.impl.misc.UnsyncByteArrayOutputStream;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.OctetStreamData;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.TransformService;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
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
/*     */ public class Exc14nCanonicalizer
/*     */   extends TransformService
/*     */ {
/*  74 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*  78 */   StAXEXC14nCanonicalizerImpl _canonicalizer = new StAXEXC14nCanonicalizerImpl();
/*  79 */   UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
/*     */ 
/*     */   
/*     */   TransformParameterSpec _transformParameterSpec;
/*     */ 
/*     */   
/*     */   public void init(TransformParameterSpec transformParameterSpec) throws InvalidAlgorithmParameterException {
/*  86 */     this._transformParameterSpec = transformParameterSpec;
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshalParams(XMLStructure xMLStructure, XMLCryptoContext xMLCryptoContext) throws MarshalException {}
/*     */ 
/*     */   
/*     */   public void init(XMLStructure xMLStructure, XMLCryptoContext xMLCryptoContext) throws InvalidAlgorithmParameterException {}
/*     */   
/*     */   public AlgorithmParameterSpec getParameterSpec() {
/*  96 */     return this._transformParameterSpec;
/*     */   }
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext) throws TransformException {
/* 100 */     this._canonicalizer.setStream((OutputStream)this.baos);
/* 101 */     this._canonicalizer.reset();
/*     */     
/* 103 */     if (data instanceof StreamWriterData) {
/* 104 */       StreamWriterData swd = (StreamWriterData)data;
/* 105 */       NamespaceContextEx nc = swd.getNamespaceContext();
/* 106 */       Iterator<NamespaceContextEx.Binding> itr = nc.iterator();
/*     */       
/* 108 */       while (itr.hasNext()) {
/* 109 */         NamespaceContextEx.Binding nd = itr.next();
/*     */         try {
/* 111 */           this._canonicalizer.writeNamespace(nd.getPrefix(), nd.getNamespaceURI());
/* 112 */         } catch (XMLStreamException ex) {
/* 113 */           throw new TransformException(ex);
/*     */         } 
/*     */       } 
/*     */       try {
/* 117 */         ExcC14NParameterSpec spec = (ExcC14NParameterSpec)this._transformParameterSpec;
/* 118 */         if (spec != null) {
/* 119 */           this._canonicalizer.setInclusivePrefixList(spec.getPrefixList());
/*     */         }
/* 121 */         swd.write((XMLStreamWriter)this._canonicalizer);
/* 122 */         this._canonicalizer.flush();
/* 123 */       } catch (XMLStreamException ex) {
/* 124 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1759_TRANSFORM_ERROR(ex.getMessage()), ex);
/* 125 */         throw new TransformException(ex);
/*     */       } 
/*     */ 
/*     */       
/* 129 */       return new OctetStreamData(new ByteArrayInputStream(this.baos.getBytes(), 0, this.baos.getLength()));
/*     */     } 
/* 131 */     throw new UnsupportedOperationException("Data type" + data + " not yet supported");
/*     */   }
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext, OutputStream outputStream) throws TransformException {
/* 135 */     this._canonicalizer.setStream(outputStream);
/* 136 */     this._canonicalizer.reset();
/*     */     
/* 138 */     if (data instanceof StreamWriterData) {
/* 139 */       StreamWriterData swd = (StreamWriterData)data;
/* 140 */       NamespaceContextEx nc = swd.getNamespaceContext();
/* 141 */       Iterator<NamespaceContextEx.Binding> itr = nc.iterator();
/*     */       
/* 143 */       while (itr.hasNext()) {
/* 144 */         NamespaceContextEx.Binding nd = itr.next();
/*     */         try {
/* 146 */           this._canonicalizer.writeNamespace(nd.getPrefix(), nd.getNamespaceURI());
/* 147 */         } catch (XMLStreamException ex) {
/* 148 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1759_TRANSFORM_ERROR(ex.getMessage()), ex);
/* 149 */           throw new TransformException(ex);
/*     */         } 
/*     */       } 
/*     */       try {
/* 153 */         ExcC14NParameterSpec spec = (ExcC14NParameterSpec)this._transformParameterSpec;
/* 154 */         if (spec != null) {
/* 155 */           this._canonicalizer.setInclusivePrefixList(spec.getPrefixList());
/*     */         }
/* 157 */         swd.write((XMLStreamWriter)this._canonicalizer);
/* 158 */         this._canonicalizer.flush();
/* 159 */       } catch (XMLStreamException ex) {
/* 160 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1759_TRANSFORM_ERROR(ex.getMessage()), ex);
/* 161 */         throw new TransformException(ex);
/*     */       } 
/*     */       
/* 164 */       return null;
/* 165 */     }  if (data instanceof JAXBData) {
/* 166 */       JAXBData jd = (JAXBData)data;
/* 167 */       NamespaceContextEx nc = jd.getNamespaceContext();
/* 168 */       Iterator<NamespaceContextEx.Binding> itr = nc.iterator();
/*     */       
/* 170 */       while (itr.hasNext()) {
/* 171 */         NamespaceContextEx.Binding nd = itr.next();
/*     */         try {
/* 173 */           this._canonicalizer.writeNamespace(nd.getPrefix(), nd.getNamespaceURI());
/* 174 */         } catch (XMLStreamException ex) {
/* 175 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1759_TRANSFORM_ERROR(ex.getMessage()), ex);
/* 176 */           throw new TransformException(ex);
/*     */         } 
/*     */       } 
/*     */       
/*     */       try {
/* 181 */         ExcC14NParameterSpec spec = (ExcC14NParameterSpec)this._transformParameterSpec;
/* 182 */         if (spec != null) {
/* 183 */           this._canonicalizer.setInclusivePrefixList(spec.getPrefixList());
/*     */         }
/* 185 */         jd.writeTo((XMLStreamWriter)this._canonicalizer);
/* 186 */         this._canonicalizer.flush();
/* 187 */       } catch (XMLStreamException ex) {
/* 188 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1759_TRANSFORM_ERROR(ex.getMessage()), ex);
/* 189 */         throw new TransformException(ex);
/* 190 */       } catch (XWSSecurityException ex) {
/* 191 */         throw new TransformException(ex);
/*     */       } 
/*     */       
/* 194 */       return null;
/*     */     } 
/* 196 */     throw new UnsupportedOperationException("Data type " + data + " not yet supported");
/*     */   }
/*     */   
/*     */   public boolean isFeatureSupported(String string) {
/* 200 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\Exc14nCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */