/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.internal.DigesterOutputStream;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ 
/*     */ public class EnvelopedSignedMessageHeader
/*     */   implements SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  67 */   private Reference ref = null;
/*  68 */   private SecurityHeaderElement she = null;
/*  69 */   private StAXEXC14nCanonicalizerImpl stAXC14n = null;
/*  70 */   private String id = "";
/*  71 */   private NamespaceContextEx nsContext = null;
/*     */   
/*     */   public EnvelopedSignedMessageHeader(SecurityHeaderElement she, Reference ref, JAXBSignatureHeaderElement jse, NamespaceContextEx nsContext) {
/*  74 */     this.she = she;
/*  75 */     this.ref = ref;
/*     */     
/*  77 */     this.nsContext = nsContext;
/*  78 */     this.stAXC14n = new StAXEXC14nCanonicalizerImpl();
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/*  82 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  86 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String id) {}
/*     */   
/*     */   public String getNamespaceURI() {
/*  93 */     return this.she.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/*  97 */     return this.she.getLocalPart();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 105 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCanonicalized() {
/* 110 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 118 */     if (this.nsContext == null) {
/* 119 */       throw new XMLStreamException("NamespaceContext is null in writeTo method");
/*     */     }
/*     */     
/* 122 */     Iterator<NamespaceContextEx.Binding> itr = this.nsContext.iterator();
/* 123 */     this.stAXC14n.reset();
/* 124 */     while (itr.hasNext()) {
/* 125 */       NamespaceContextEx.Binding nd = itr.next();
/* 126 */       this.stAXC14n.writeNamespace(nd.getPrefix(), nd.getNamespaceURI());
/*     */     } 
/* 128 */     DigesterOutputStream dos = null;
/*     */     try {
/* 130 */       dos = this.ref.getDigestOutputStream();
/* 131 */     } catch (XMLSignatureException xse) {
/* 132 */       throw new XMLStreamException(xse);
/*     */     } 
/* 134 */     OutputStream os = new UnsyncBufferedOutputStream((OutputStream)dos);
/* 135 */     this.stAXC14n.setStream(os);
/*     */     
/* 137 */     ((SecurityElementWriter)this.she).writeTo(streamWriter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 146 */     throw new UnsupportedOperationException("Not implemented yet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 154 */     throw new UnsupportedOperationException("Not implemented yet");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\EnvelopedSignedMessageHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */