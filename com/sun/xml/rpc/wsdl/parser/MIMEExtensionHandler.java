/*     */ package com.sun.xml.rpc.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.mime.MIMEConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.mime.MIMEContent;
/*     */ import com.sun.xml.rpc.wsdl.document.mime.MIMEMultipartRelated;
/*     */ import com.sun.xml.rpc.wsdl.document.mime.MIMEPart;
/*     */ import com.sun.xml.rpc.wsdl.document.mime.MIMEXml;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.ParserContext;
/*     */ import com.sun.xml.rpc.wsdl.framework.WriterContext;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class MIMEExtensionHandler
/*     */   extends ExtensionHandler
/*     */ {
/*     */   public String getNamespaceURI() {
/*  59 */     return "http://schemas.xmlsoap.org/wsdl/mime/";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doHandleExtension(ParserContext context, Extensible parent, Element e) {
/*  66 */     if (parent.getElementName().equals(WSDLConstants.QNAME_OUTPUT))
/*  67 */       return handleInputOutputExtension(context, parent, e); 
/*  68 */     if (parent.getElementName().equals(WSDLConstants.QNAME_INPUT))
/*  69 */       return handleInputOutputExtension(context, parent, e); 
/*  70 */     if (parent.getElementName().equals(MIMEConstants.QNAME_PART)) {
/*  71 */       return handleMIMEPartExtension(context, parent, e);
/*     */     }
/*  73 */     context.fireIgnoringExtension(new QName(e.getNamespaceURI(), e.getLocalName()), parent.getElementName());
/*     */ 
/*     */     
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleInputOutputExtension(ParserContext context, Extensible parent, Element e) {
/*  84 */     if (XmlUtil.matchesTagNS(e, MIMEConstants.QNAME_MULTIPART_RELATED)) {
/*  85 */       context.push();
/*  86 */       context.registerNamespaces(e);
/*     */       
/*  88 */       MIMEMultipartRelated mpr = new MIMEMultipartRelated();
/*     */       
/*  90 */       for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*  91 */         Element e2 = Util.nextElement(iter);
/*  92 */         if (e2 == null) {
/*     */           break;
/*     */         }
/*  95 */         if (XmlUtil.matchesTagNS(e2, MIMEConstants.QNAME_PART)) {
/*  96 */           context.push();
/*  97 */           context.registerNamespaces(e2);
/*     */           
/*  99 */           MIMEPart part = new MIMEPart();
/*     */           
/* 101 */           String name = XmlUtil.getAttributeOrNull(e2, "name");
/*     */           
/* 103 */           if (name != null) {
/* 104 */             part.setName(name);
/*     */           }
/*     */           
/* 107 */           Iterator iter2 = XmlUtil.getAllChildren(e2);
/* 108 */           while (iter2.hasNext()) {
/*     */             
/* 110 */             Element e3 = Util.nextElement(iter2);
/* 111 */             if (e3 == null) {
/*     */               break;
/*     */             }
/* 114 */             ExtensionHandler h = (ExtensionHandler)this._extensionHandlers.get(e3.getNamespaceURI());
/*     */ 
/*     */             
/* 117 */             boolean handled = false;
/* 118 */             if (h != null) {
/* 119 */               handled = h.doHandleExtension(context, (Extensible)part, e3);
/*     */             }
/*     */             
/* 122 */             if (!handled) {
/* 123 */               String required = XmlUtil.getAttributeNSOrNull(e3, "required", "http://schemas.xmlsoap.org/wsdl/");
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 128 */               if (required != null && required.equals("true")) {
/*     */                 
/* 130 */                 Util.fail("parsing.requiredExtensibilityElement", e3.getTagName(), e3.getNamespaceURI());
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */               
/* 135 */               context.fireIgnoringExtension(new QName(e3.getNamespaceURI(), e3.getLocalName()), part.getElementName());
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 144 */           mpr.add(part);
/* 145 */           context.pop();
/* 146 */           context.fireDoneParsingEntity(MIMEConstants.QNAME_PART, (Entity)part);
/*     */           
/*     */           continue;
/*     */         } 
/* 150 */         Util.fail("parsing.invalidElement", e2.getTagName(), e2.getNamespaceURI());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       parent.addExtension((Extension)mpr);
/* 158 */       context.pop();
/* 159 */       context.fireDoneParsingEntity(MIMEConstants.QNAME_MULTIPART_RELATED, (Entity)mpr);
/*     */ 
/*     */       
/* 162 */       return true;
/* 163 */     }  if (XmlUtil.matchesTagNS(e, MIMEConstants.QNAME_CONTENT)) {
/* 164 */       MIMEContent content = parseMIMEContent(context, e);
/* 165 */       parent.addExtension((Extension)content);
/* 166 */       return true;
/* 167 */     }  if (XmlUtil.matchesTagNS(e, MIMEConstants.QNAME_MIME_XML)) {
/* 168 */       MIMEXml mimeXml = parseMIMEXml(context, e);
/* 169 */       parent.addExtension((Extension)mimeXml);
/* 170 */       return true;
/*     */     } 
/* 172 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleMIMEPartExtension(ParserContext context, Extensible parent, Element e) {
/* 184 */     if (XmlUtil.matchesTagNS(e, MIMEConstants.QNAME_CONTENT)) {
/* 185 */       MIMEContent content = parseMIMEContent(context, e);
/* 186 */       parent.addExtension((Extension)content);
/* 187 */       return true;
/* 188 */     }  if (XmlUtil.matchesTagNS(e, MIMEConstants.QNAME_MIME_XML)) {
/* 189 */       MIMEXml mimeXml = parseMIMEXml(context, e);
/* 190 */       parent.addExtension((Extension)mimeXml);
/* 191 */       return true;
/*     */     } 
/* 193 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MIMEContent parseMIMEContent(ParserContext context, Element e) {
/* 202 */     context.push();
/* 203 */     context.registerNamespaces(e);
/*     */     
/* 205 */     MIMEContent content = new MIMEContent();
/*     */     
/* 207 */     String part = XmlUtil.getAttributeOrNull(e, "part");
/* 208 */     if (part != null) {
/* 209 */       content.setPart(part);
/*     */     }
/*     */     
/* 212 */     String type = XmlUtil.getAttributeOrNull(e, "type");
/* 213 */     if (type != null) {
/* 214 */       content.setType(type);
/*     */     }
/*     */     
/* 217 */     context.pop();
/* 218 */     context.fireDoneParsingEntity(MIMEConstants.QNAME_CONTENT, (Entity)content);
/* 219 */     return content;
/*     */   }
/*     */   
/*     */   protected MIMEXml parseMIMEXml(ParserContext context, Element e) {
/* 223 */     context.push();
/* 224 */     context.registerNamespaces(e);
/*     */     
/* 226 */     MIMEXml mimeXml = new MIMEXml();
/*     */     
/* 228 */     String part = XmlUtil.getAttributeOrNull(e, "part");
/* 229 */     if (part != null) {
/* 230 */       mimeXml.setPart(part);
/*     */     }
/*     */     
/* 233 */     context.pop();
/* 234 */     context.fireDoneParsingEntity(MIMEConstants.QNAME_MIME_XML, (Entity)mimeXml);
/* 235 */     return mimeXml;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doHandleExtension(WriterContext context, Extension extension) throws IOException {
/* 242 */     if (extension instanceof MIMEContent) {
/* 243 */       MIMEContent content = (MIMEContent)extension;
/* 244 */       context.writeStartTag(content.getElementName());
/* 245 */       context.writeAttribute("part", content.getPart());
/* 246 */       context.writeAttribute("type", content.getType());
/* 247 */       context.writeEndTag(content.getElementName());
/* 248 */     } else if (extension instanceof MIMEXml) {
/* 249 */       MIMEXml mimeXml = (MIMEXml)extension;
/* 250 */       context.writeStartTag(mimeXml.getElementName());
/* 251 */       context.writeAttribute("part", mimeXml.getPart());
/* 252 */       context.writeEndTag(mimeXml.getElementName());
/* 253 */     } else if (extension instanceof MIMEMultipartRelated) {
/* 254 */       MIMEMultipartRelated mpr = (MIMEMultipartRelated)extension;
/* 255 */       context.writeStartTag(mpr.getElementName());
/* 256 */       for (Iterator<MIMEPart> iter = mpr.getParts(); iter.hasNext(); ) {
/* 257 */         MIMEPart part = iter.next();
/* 258 */         context.writeStartTag(part.getElementName());
/* 259 */         for (Iterator<Extension> iter2 = part.extensions(); iter2.hasNext(); ) {
/* 260 */           Extension e = iter2.next();
/* 261 */           ExtensionHandler h = (ExtensionHandler)this._extensionHandlers.get(e.getElementName().getNamespaceURI());
/*     */ 
/*     */           
/* 264 */           if (h != null) {
/* 265 */             h.doHandleExtension(context, e);
/*     */           }
/*     */         } 
/* 268 */         context.writeEndTag(part.getElementName());
/*     */       } 
/* 270 */       context.writeEndTag(mpr.getElementName());
/*     */     } else {
/* 272 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\MIMEExtensionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */