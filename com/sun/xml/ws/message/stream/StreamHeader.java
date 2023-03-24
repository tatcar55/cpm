/*     */ package com.sun.xml.ws.message.stream;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferSource;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.message.AbstractHeaderImpl;
/*     */ import java.util.Set;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
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
/*     */ 
/*     */ 
/*     */ public abstract class StreamHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   protected final XMLStreamBuffer _mark;
/*     */   protected boolean _isMustUnderstand;
/*     */   @NotNull
/*     */   protected String _role;
/*     */   protected boolean _isRelay;
/*     */   protected String _localName;
/*     */   protected String _namespaceURI;
/*     */   private final FinalArrayList<Attribute> attributes;
/*     */   
/*     */   protected static final class Attribute
/*     */   {
/*     */     final String nsUri;
/*     */     final String localName;
/*     */     final String value;
/*     */     
/*     */     public Attribute(String nsUri, String localName, String value) {
/* 104 */       this.nsUri = StreamHeader.fixNull(nsUri);
/* 105 */       this.localName = localName;
/* 106 */       this.value = value;
/*     */     }
/*     */   }
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
/*     */   protected StreamHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 130 */     assert reader != null && mark != null;
/* 131 */     this._mark = mark;
/* 132 */     this._localName = reader.getLocalName();
/* 133 */     this._namespaceURI = reader.getNamespaceURI();
/* 134 */     this.attributes = processHeaderAttributes(reader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StreamHeader(XMLStreamReader reader) throws XMLStreamException {
/* 146 */     this._localName = reader.getLocalName();
/* 147 */     this._namespaceURI = reader.getNamespaceURI();
/* 148 */     this.attributes = processHeaderAttributes(reader);
/*     */     
/* 150 */     this._mark = XMLStreamBuffer.createNewBufferFromXMLStreamReader(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isIgnorable(@NotNull SOAPVersion soapVersion, @NotNull Set<String> roles) {
/* 155 */     if (!this._isMustUnderstand) return true;
/*     */     
/* 157 */     if (roles == null) {
/* 158 */       return true;
/*     */     }
/*     */     
/* 161 */     return !roles.contains(this._role);
/*     */   }
/*     */   @NotNull
/*     */   public String getRole(@NotNull SOAPVersion soapVersion) {
/* 165 */     assert this._role != null;
/* 166 */     return this._role;
/*     */   }
/*     */   
/*     */   public boolean isRelay() {
/* 170 */     return this._isRelay;
/*     */   }
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/* 174 */     return this._namespaceURI;
/*     */   }
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 178 */     return this._localName;
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 182 */     if (this.attributes != null)
/* 183 */       for (int i = this.attributes.size() - 1; i >= 0; i--) {
/* 184 */         Attribute a = (Attribute)this.attributes.get(i);
/* 185 */         if (a.localName.equals(localName) && a.nsUri.equals(nsUri)) {
/* 186 */           return a.value;
/*     */         }
/*     */       }  
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 196 */     return (XMLStreamReader)this._mark.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 200 */     if (this._mark.getInscopeNamespaces().size() > 0) {
/* 201 */       this._mark.writeToXMLStreamWriter(w, true);
/*     */     } else {
/* 203 */       this._mark.writeToXMLStreamWriter(w);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 211 */       TransformerFactory tf = TransformerFactory.newInstance();
/* 212 */       Transformer t = tf.newTransformer();
/* 213 */       XMLStreamBufferSource source = new XMLStreamBufferSource(this._mark);
/* 214 */       DOMResult result = new DOMResult();
/* 215 */       t.transform((Source)source, result);
/* 216 */       Node d = result.getNode();
/* 217 */       if (d.getNodeType() == 9)
/* 218 */         d = d.getFirstChild(); 
/* 219 */       SOAPHeader header = saaj.getSOAPHeader();
/* 220 */       if (header == null)
/* 221 */         header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 222 */       Node node = header.getOwnerDocument().importNode(d, true);
/* 223 */       header.appendChild(node);
/* 224 */     } catch (Exception e) {
/* 225 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 230 */     this._mark.writeTo(contentHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSEndpointReference readAsEPR(AddressingVersion expected) throws XMLStreamException {
/* 241 */     return new WSEndpointReference(this._mark, expected);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String fixNull(String s) {
/* 250 */     if (s == null) return ""; 
/* 251 */     return s;
/*     */   }
/*     */   
/*     */   protected abstract FinalArrayList<Attribute> processHeaderAttributes(XMLStreamReader paramXMLStreamReader);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\stream\StreamHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */