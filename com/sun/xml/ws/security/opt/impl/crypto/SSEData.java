/*     */ package com.sun.xml.ws.security.opt.impl.crypto;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.crypto.StreamWriterData;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.SignedMessagePart;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.XMLStreamFilter;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ public class SSEData
/*     */   implements StreamWriterData
/*     */ {
/*     */   private NamespaceContextEx nsContext;
/*     */   private boolean contentOnly;
/*     */   private SecurityElement data;
/*  65 */   private XMLStreamBuffer buffer = null;
/*  66 */   private HashMap props = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   public SSEData(SecurityElement se, boolean contentOnly, NamespaceContextEx ns) {
/*  70 */     this.data = se;
/*  71 */     this.nsContext = ns;
/*  72 */     this.contentOnly = contentOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   public SSEData(SecurityElement se, boolean contentOnly, NamespaceContextEx ns, HashMap props) {
/*  77 */     this.data = se;
/*  78 */     this.nsContext = ns;
/*  79 */     this.contentOnly = contentOnly;
/*  80 */     this.props = props;
/*     */   }
/*     */   
/*     */   public SSEData(XMLStreamBuffer buffer) {
/*  84 */     this.buffer = buffer;
/*     */   }
/*     */   
/*     */   public NamespaceContextEx getNamespaceContext() {
/*  88 */     return this.nsContext;
/*     */   }
/*     */   
/*     */   public SecurityElement getSecurityElement() {
/*  92 */     return this.data;
/*     */   }
/*     */   
/*     */   public void write(XMLStreamWriter writer) throws XMLStreamException {
/*  96 */     if (this.buffer != null) {
/*  97 */       this.buffer.writeToXMLStreamWriter(writer);
/*     */     }
/*     */     
/* 100 */     if (this.contentOnly) {
/*     */       
/* 102 */       if (this.data instanceof SignedMessagePart && writer instanceof StAXEXC14nCanonicalizerImpl) {
/* 103 */         SignedMessagePart body = (SignedMessagePart)this.data;
/* 104 */         List attributeValuePrefixes = body.getAttributeValuePrefixes();
/* 105 */         if (attributeValuePrefixes != null && !attributeValuePrefixes.isEmpty()) {
/* 106 */           List<?> prefixList = ((StAXEXC14nCanonicalizerImpl)writer).getInclusivePrefixList();
/* 107 */           if (prefixList == null) {
/* 108 */             prefixList = new ArrayList();
/*     */           }
/* 110 */           prefixList.addAll(attributeValuePrefixes);
/*     */           
/* 112 */           HashSet<?> set = new HashSet(prefixList);
/* 113 */           prefixList = new ArrayList(set);
/* 114 */           ((StAXEXC14nCanonicalizerImpl)writer).setInclusivePrefixList(prefixList);
/*     */         } 
/*     */       } 
/* 117 */       XMLStreamFilter xMLStreamFilter = new XMLStreamFilter(writer, (NamespaceContextEx)this.nsContext);
/* 118 */       if (this.props != null) {
/* 119 */         ((SecurityElementWriter)this.data).writeTo((XMLStreamWriter)xMLStreamFilter, this.props);
/*     */       } else {
/* 121 */         ((SecurityElementWriter)this.data).writeTo((XMLStreamWriter)xMLStreamFilter);
/*     */       }
/*     */     
/* 124 */     } else if (this.props != null) {
/* 125 */       ((SecurityElementWriter)this.data).writeTo(writer, this.props);
/*     */     } else {
/* 127 */       ((SecurityElementWriter)this.data).writeTo(writer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\crypto\SSEData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */