/*      */ package javanet.staxutils.io;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import javanet.staxutils.XMLStreamUtils;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamWriter;
/*      */ import javax.xml.stream.events.Attribute;
/*      */ import javax.xml.stream.events.Characters;
/*      */ import javax.xml.stream.events.Comment;
/*      */ import javax.xml.stream.events.DTD;
/*      */ import javax.xml.stream.events.EndDocument;
/*      */ import javax.xml.stream.events.EndElement;
/*      */ import javax.xml.stream.events.EntityDeclaration;
/*      */ import javax.xml.stream.events.EntityReference;
/*      */ import javax.xml.stream.events.Namespace;
/*      */ import javax.xml.stream.events.NotationDeclaration;
/*      */ import javax.xml.stream.events.ProcessingInstruction;
/*      */ import javax.xml.stream.events.StartDocument;
/*      */ import javax.xml.stream.events.StartElement;
/*      */ import javax.xml.stream.events.XMLEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class XMLWriterUtils
/*      */ {
/*      */   public static final void writeQuotedValue(String value, Writer writer) throws IOException {
/*   89 */     char quoteChar = (value.indexOf('"') < 0) ? '"' : '\'';
/*   90 */     writer.write(quoteChar);
/*   91 */     writer.write(value);
/*   92 */     writer.write(quoteChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEncodedQuotedValue(String value, Writer writer) throws IOException {
/*  108 */     char quoteChar = (value.indexOf('"') < 0) ? '"' : '\'';
/*  109 */     writer.write(quoteChar);
/*  110 */     writeEncodedValue(value, quoteChar, writer);
/*  111 */     writer.write(quoteChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEncodedValue(String value, char quoteChar, Writer writer) throws IOException {
/*  130 */     for (int i = 0, s = value.length(); i < s; i++) {
/*      */       
/*  132 */       char c = value.charAt(i);
/*  133 */       if (c == '\'') {
/*      */         
/*  135 */         writer.write((quoteChar == '\'') ? "&apos;" : "'");
/*      */       }
/*  137 */       else if (c == '"') {
/*      */         
/*  139 */         writer.write((quoteChar == '"') ? "&quot;" : "\"");
/*      */       }
/*  141 */       else if (c == '\n') {
/*      */         
/*  143 */         writer.write("&#xA;");
/*      */       }
/*      */       else {
/*      */         
/*  147 */         writeEncodedCharacter(c, writer);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEncodedText(CharSequence text, Writer writer) throws IOException {
/*  167 */     for (int i = 0, s = text.length(); i < s; i++)
/*      */     {
/*  169 */       writeEncodedCharacter(text.charAt(i), writer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEncodedText(char[] text, int start, int len, Writer writer) throws IOException {
/*  178 */     for (int i = start, s = start + len; i < s; i++)
/*      */     {
/*  180 */       writeEncodedCharacter(text[i], writer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEncodedCharacter(char c, Writer writer) throws IOException {
/*  196 */     if (c == '&') {
/*      */       
/*  198 */       writer.write("&amp;");
/*      */     }
/*  200 */     else if (c == '<') {
/*      */       
/*  202 */       writer.write("&lt;");
/*      */     }
/*  204 */     else if (c == '>') {
/*      */       
/*  206 */       writer.write("&gt;");
/*      */     }
/*  208 */     else if (c == '\r') {
/*      */       
/*  210 */       writer.write("&#xD;");
/*      */     }
/*      */     else {
/*      */       
/*  214 */       writer.write(c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeQName(QName name, Writer writer) throws IOException {
/*  230 */     String prefix = name.getPrefix();
/*  231 */     if (prefix != null && prefix.length() > 0) {
/*      */       
/*  233 */       writer.write(prefix);
/*  234 */       writer.write(58);
/*      */     } 
/*      */ 
/*      */     
/*  238 */     writer.write(name.getLocalPart());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeQName(String prefix, String localPart, Writer writer) throws IOException {
/*  253 */     if (prefix != null && prefix.length() > 0) {
/*      */       
/*  255 */       writer.write(prefix);
/*  256 */       writer.write(58);
/*      */     } 
/*      */ 
/*      */     
/*  260 */     writer.write(localPart);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEvent(XMLEvent event, Writer writer) throws IOException, XMLStreamException {
/*  279 */     int eventType = event.getEventType();
/*  280 */     switch (eventType) {
/*      */       
/*      */       case 1:
/*  283 */         writeStartElement(event.asStartElement(), false, writer);
/*      */         return;
/*      */       
/*      */       case 2:
/*  287 */         writeEndElement(event.asEndElement(), writer);
/*      */         return;
/*      */       
/*      */       case 4:
/*      */       case 6:
/*      */       case 12:
/*  293 */         writeCharacters(event.asCharacters(), writer);
/*      */         return;
/*      */       
/*      */       case 5:
/*  297 */         writeComment((Comment)event, writer);
/*      */         return;
/*      */       
/*      */       case 9:
/*  301 */         writeEntityReference((EntityReference)event, writer);
/*      */         return;
/*      */       
/*      */       case 3:
/*  305 */         writeProcessingInstruction((ProcessingInstruction)event, writer);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 11:
/*  310 */         writeDTD((DTD)event, writer);
/*      */         return;
/*      */       
/*      */       case 7:
/*  314 */         writeStartDocument((StartDocument)event, writer);
/*      */         return;
/*      */       
/*      */       case 8:
/*  318 */         writeEndDocument((EndDocument)event, writer);
/*      */         return;
/*      */       
/*      */       case 13:
/*  322 */         writeNamespace((Namespace)event, writer);
/*      */         return;
/*      */       
/*      */       case 10:
/*  326 */         writeAttribute((Attribute)event, writer);
/*      */         return;
/*      */       
/*      */       case 15:
/*  330 */         writeEntityDeclaration((EntityDeclaration)event, writer);
/*      */         return;
/*      */       
/*      */       case 14:
/*  334 */         writeNotationDeclaration((NotationDeclaration)event, writer);
/*      */         return;
/*      */     } 
/*      */     
/*  338 */     throw new IllegalArgumentException("Unrecognized event (" + XMLStreamUtils.getEventTypeName(eventType) + "): " + event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartDocument(StartDocument start, Writer writer) throws IOException {
/*  356 */     String version = start.getVersion();
/*  357 */     String encoding = start.getCharacterEncodingScheme();
/*      */     
/*  359 */     if (start.standaloneSet()) {
/*      */       
/*  361 */       writeStartDocument(version, encoding, start.isStandalone(), writer);
/*      */     }
/*      */     else {
/*      */       
/*  365 */       writeStartDocument(version, encoding, writer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartDocument(Writer writer) throws IOException {
/*  380 */     writeStartDocument("1.0", (String)null, (String)null, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartDocument(String version, Writer writer) throws IOException {
/*  394 */     writeStartDocument(version, (String)null, (String)null, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartDocument(String version, String encoding, Writer writer) throws IOException {
/*  409 */     writeStartDocument(version, encoding, (String)null, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartDocument(String version, String encoding, boolean standalone, Writer writer) throws IOException {
/*  426 */     writeStartDocument(version, encoding, standalone ? "yes" : "no", writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartDocument(String version, String encoding, String standalone, Writer writer) throws IOException {
/*  444 */     writer.write("<?xml version=");
/*  445 */     writeQuotedValue(version, writer);
/*      */     
/*  447 */     if (encoding != null) {
/*      */       
/*  449 */       writer.write(" encoding=");
/*  450 */       writeQuotedValue(encoding, writer);
/*      */     } 
/*      */ 
/*      */     
/*  454 */     if (standalone != null) {
/*      */       
/*  456 */       writer.write(" standalone=");
/*  457 */       writeQuotedValue(standalone, writer);
/*      */     } 
/*      */ 
/*      */     
/*  461 */     writer.write("?>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEndDocument(EndDocument end, Writer writer) throws IOException {
/*  475 */     writeEndDocument(writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEndDocument(Writer writer) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartElement(StartElement start, Writer writer) throws IOException, XMLStreamException {
/*  503 */     writeStartElement(start.getName(), start.getAttributes(), start.getNamespaces(), false, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartElement(StartElement start, boolean empty, Writer writer) throws IOException, XMLStreamException {
/*  522 */     writeStartElement(start.getName(), start.getAttributes(), start.getNamespaces(), empty, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartElement(QName name, Iterator attributes, Iterator namespaces, Writer writer) throws IOException, XMLStreamException {
/*  543 */     writeStartElement(name, attributes, namespaces, false, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartElement(QName name, Iterator attributes, Iterator namespaces, boolean empty, Writer writer) throws IOException, XMLStreamException {
/*  563 */     writer.write(60);
/*  564 */     writeQName(name, writer);
/*      */ 
/*      */     
/*  567 */     if (namespaces != null)
/*      */     {
/*  569 */       while (namespaces.hasNext()) {
/*      */         
/*  571 */         Namespace ns = namespaces.next();
/*  572 */         writer.write(32);
/*  573 */         ns.writeAsEncodedUnicode(writer);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  580 */     if (attributes != null)
/*      */     {
/*  582 */       while (attributes.hasNext()) {
/*      */         
/*  584 */         Attribute attr = attributes.next();
/*  585 */         writer.write(32);
/*  586 */         attr.writeAsEncodedUnicode(writer);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  592 */     if (empty) {
/*      */       
/*  594 */       writer.write("/>");
/*      */     }
/*      */     else {
/*      */       
/*  598 */       writer.write(62);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartElement(QName name, Map attributes, Map namespaces, boolean empty, Writer writer) throws IOException {
/*  620 */     writer.write(60);
/*  621 */     writeQName(name, writer);
/*      */ 
/*      */     
/*  624 */     if (namespaces != null)
/*      */     {
/*  626 */       for (Iterator i = namespaces.entrySet().iterator(); i.hasNext(); ) {
/*      */         
/*  628 */         Map.Entry entry = i.next();
/*  629 */         writer.write(32);
/*  630 */         writeNamespace((String)entry.getKey(), (String)entry.getValue(), writer);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  638 */     if (attributes != null)
/*      */     {
/*  640 */       for (Iterator i = attributes.entrySet().iterator(); i.hasNext(); ) {
/*      */         
/*  642 */         Map.Entry entry = i.next();
/*  643 */         writer.write(32);
/*  644 */         writeAttribute((QName)entry.getKey(), (String)entry.getValue(), writer);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  651 */     if (empty) {
/*      */       
/*  653 */       writer.write("/>");
/*      */     }
/*      */     else {
/*      */       
/*  657 */       writer.write(62);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeAttribute(Attribute attr, Writer writer) throws IOException {
/*  673 */     QName name = attr.getName();
/*  674 */     String value = attr.getValue();
/*  675 */     writeAttribute(name, value, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeAttribute(QName name, String value, Writer writer) throws IOException {
/*  690 */     writeQName(name, writer);
/*  691 */     writer.write(61);
/*  692 */     writeEncodedQuotedValue(value, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeNamespace(Namespace ns, Writer writer) throws IOException {
/*  706 */     String prefix = ns.getPrefix();
/*  707 */     String uri = ns.getNamespaceURI();
/*  708 */     writeNamespace(prefix, uri, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeNamespace(String prefix, String uri, Writer writer) throws IOException {
/*  723 */     writer.write("xmlns");
/*  724 */     if (prefix != null && prefix.length() > 0) {
/*      */       
/*  726 */       writer.write(58);
/*  727 */       writer.write(prefix);
/*      */     } 
/*      */ 
/*      */     
/*  731 */     writer.write(61);
/*  732 */     writeEncodedQuotedValue(uri, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEndElement(EndElement end, Writer writer) throws IOException {
/*  746 */     writeEndElement(end.getName(), writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEndElement(QName name, Writer writer) throws IOException {
/*  760 */     writer.write("</");
/*  761 */     writeQName(name, writer);
/*  762 */     writer.write(62);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeCharacters(Characters chars, Writer writer) throws IOException {
/*  776 */     if (chars.isCData()) {
/*      */       
/*  778 */       writeCData(chars.getData(), writer);
/*      */     }
/*      */     else {
/*      */       
/*  782 */       writeCharacters(chars.getData(), writer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeCharacters(CharSequence text, Writer writer) throws IOException {
/*  798 */     writeEncodedText(text, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeCharacters(char[] data, int start, int length, Writer writer) throws IOException {
/*  814 */     writeEncodedText(data, start, length, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeCData(String text, Writer writer) throws IOException {
/*  828 */     writer.write("<![CDATA[");
/*  829 */     writer.write(text);
/*  830 */     writer.write("]]>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeCData(char[] data, int start, int length, Writer writer) throws IOException {
/*  846 */     writer.write("<![CDATA[");
/*  847 */     writer.write(data, start, length);
/*  848 */     writer.write("]]>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeComment(Comment comment, Writer writer) throws IOException {
/*  862 */     writeComment(comment.getText(), writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeComment(String comment, Writer writer) throws IOException {
/*  876 */     writer.write("<!--");
/*  877 */     writer.write(comment);
/*  878 */     writer.write("-->");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEntityReference(EntityReference entityRef, Writer writer) throws IOException {
/*  892 */     writeEntityReference(entityRef.getName(), writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEntityReference(String entityRef, Writer writer) throws IOException {
/*  906 */     writer.write(38);
/*  907 */     writer.write(entityRef);
/*  908 */     writer.write(59);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEntityDeclaration(EntityDeclaration declaration, Writer writer) throws IOException {
/*  922 */     String name = declaration.getName();
/*  923 */     String notation = declaration.getNotationName();
/*      */     
/*  925 */     String text = declaration.getReplacementText();
/*  926 */     if (text != null) {
/*      */       
/*  928 */       writeEntityDeclaration(name, text, notation, writer);
/*      */     }
/*      */     else {
/*      */       
/*  932 */       String publicId = declaration.getPublicId();
/*  933 */       String systemId = declaration.getSystemId();
/*      */       
/*  935 */       writeEntityDeclaration(name, publicId, systemId, notation, writer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEntityDeclaration(String name, String publicId, String systemId, String notation, Writer writer) throws IOException {
/*  956 */     writer.write("<!ENTITY ");
/*  957 */     writer.write(name);
/*      */     
/*  959 */     if (publicId != null) {
/*      */ 
/*      */       
/*  962 */       writer.write("PUBLIC ");
/*  963 */       writeQuotedValue(publicId, writer);
/*  964 */       if (systemId != null)
/*      */       {
/*  966 */         writer.write(" ");
/*  967 */         writeQuotedValue(systemId, writer);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  974 */       writer.write("SYSTEM ");
/*  975 */       writeQuotedValue(systemId, writer);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  980 */     if (notation != null) {
/*      */       
/*  982 */       writer.write(" NDATA");
/*  983 */       writer.write(notation);
/*      */     } 
/*      */ 
/*      */     
/*  987 */     writer.write(">");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEntityDeclaration(String name, String text, String notation, Writer writer) throws IOException {
/* 1003 */     writer.write("<!ENTITY ");
/* 1004 */     writer.write(name);
/*      */     
/* 1006 */     writeEncodedQuotedValue(text, writer);
/*      */ 
/*      */     
/* 1009 */     if (notation != null) {
/*      */       
/* 1011 */       writer.write(" NDATA");
/* 1012 */       writer.write(notation);
/*      */     } 
/*      */ 
/*      */     
/* 1016 */     writer.write(">");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeNotationDeclaration(NotationDeclaration declaration, Writer writer) throws IOException {
/* 1030 */     String name = declaration.getName();
/* 1031 */     String publicId = declaration.getPublicId();
/* 1032 */     String systemId = declaration.getSystemId();
/*      */     
/* 1034 */     writeNotationDeclaration(name, publicId, systemId, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeNotationDeclaration(String name, String publicId, String systemId, Writer writer) throws IOException {
/* 1052 */     writer.write("<!NOTATION ");
/* 1053 */     writer.write(name);
/*      */     
/* 1055 */     if (publicId != null) {
/*      */ 
/*      */       
/* 1058 */       writer.write("PUBLIC ");
/* 1059 */       writeQuotedValue(publicId, writer);
/* 1060 */       if (systemId != null)
/*      */       {
/* 1062 */         writer.write(" ");
/* 1063 */         writeQuotedValue(systemId, writer);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1070 */       writer.write("SYSTEM ");
/* 1071 */       writeQuotedValue(systemId, writer);
/*      */     } 
/*      */ 
/*      */     
/* 1075 */     writer.write(">");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeProcessingInstruction(ProcessingInstruction procInst, Writer writer) throws IOException {
/* 1089 */     writeProcessingInstruction(procInst.getTarget(), procInst.getData(), writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeProcessingInstruction(String target, String data, Writer writer) throws IOException {
/* 1105 */     writer.write("<?");
/* 1106 */     writer.write(target);
/*      */     
/* 1108 */     if (data != null) {
/*      */       
/* 1110 */       writer.write(32);
/* 1111 */       writer.write(data);
/*      */     } 
/*      */ 
/*      */     
/* 1115 */     writer.write("?>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeDTD(DTD dtd, Writer writer) throws IOException {
/* 1129 */     writeDTD(dtd, writer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeDTD(String dtd, Writer writer) throws IOException {
/* 1143 */     writer.write(dtd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEvent(XMLEvent event, XMLStreamWriter writer) throws XMLStreamException {
/* 1159 */     int eventType = event.getEventType();
/* 1160 */     switch (eventType) {
/*      */       
/*      */       case 1:
/* 1163 */         writeStartElement(event.asStartElement(), false, writer);
/*      */         return;
/*      */       
/*      */       case 2:
/* 1167 */         writeEndElement(event.asEndElement(), writer);
/*      */         return;
/*      */       
/*      */       case 4:
/*      */       case 6:
/*      */       case 12:
/* 1173 */         writeCharacters(event.asCharacters(), writer);
/*      */         return;
/*      */       
/*      */       case 5:
/* 1177 */         writeComment((Comment)event, writer);
/*      */         return;
/*      */       
/*      */       case 9:
/* 1181 */         writeEntityReference((EntityReference)event, writer);
/*      */         return;
/*      */       
/*      */       case 3:
/* 1185 */         writeProcessingInstruction((ProcessingInstruction)event, writer);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 11:
/* 1190 */         writeDTD((DTD)event, writer);
/*      */         return;
/*      */       
/*      */       case 7:
/* 1194 */         writeStartDocument((StartDocument)event, writer);
/*      */         return;
/*      */       
/*      */       case 8:
/* 1198 */         writeEndDocument((EndDocument)event, writer);
/*      */         return;
/*      */       
/*      */       case 13:
/* 1202 */         writeNamespace((Namespace)event, writer);
/*      */         return;
/*      */       
/*      */       case 10:
/* 1206 */         writeAttribute((Attribute)event, writer);
/*      */         return;
/*      */     } 
/*      */     
/* 1210 */     throw new XMLStreamException("Unrecognized event (" + XMLStreamUtils.getEventTypeName(eventType) + "): " + event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartElement(StartElement start, boolean empty, XMLStreamWriter writer) throws XMLStreamException {
/* 1229 */     QName name = start.getName();
/* 1230 */     String nsURI = name.getNamespaceURI();
/* 1231 */     String localName = name.getLocalPart();
/* 1232 */     String prefix = name.getPrefix();
/*      */     
/* 1234 */     if (prefix != null && prefix.length() > 0) {
/*      */       
/* 1236 */       if (empty)
/*      */       {
/* 1238 */         writer.writeEmptyElement(prefix, localName, nsURI);
/*      */       }
/*      */       else
/*      */       {
/* 1242 */         writer.writeStartElement(prefix, localName, nsURI);
/*      */       }
/*      */     
/*      */     }
/* 1246 */     else if (nsURI != null && nsURI.length() > 0) {
/*      */       
/* 1248 */       if (empty)
/*      */       {
/* 1250 */         writer.writeEmptyElement(nsURI, localName);
/*      */       }
/*      */       else
/*      */       {
/* 1254 */         writer.writeStartElement(nsURI, localName);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1260 */     else if (empty) {
/*      */       
/* 1262 */       writer.writeEmptyElement(localName);
/*      */     }
/*      */     else {
/*      */       
/* 1266 */       writer.writeStartElement(localName);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1273 */     Iterator nsIter = start.getNamespaces();
/* 1274 */     while (nsIter.hasNext()) {
/*      */       
/* 1276 */       Namespace ns = nsIter.next();
/* 1277 */       writeNamespace(ns, writer);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1282 */     Iterator attrIter = start.getAttributes();
/* 1283 */     while (attrIter.hasNext()) {
/*      */       
/* 1285 */       Attribute attr = attrIter.next();
/* 1286 */       writeAttribute(attr, writer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEndElement(EndElement end, XMLStreamWriter writer) throws XMLStreamException {
/* 1302 */     writer.writeEndElement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeAttribute(Attribute attr, XMLStreamWriter writer) throws XMLStreamException {
/* 1316 */     QName name = attr.getName();
/* 1317 */     String nsURI = name.getNamespaceURI();
/* 1318 */     String localName = name.getLocalPart();
/* 1319 */     String prefix = name.getPrefix();
/* 1320 */     String value = attr.getValue();
/*      */     
/* 1322 */     if (prefix != null) {
/*      */       
/* 1324 */       writer.writeAttribute(prefix, nsURI, localName, value);
/*      */     }
/* 1326 */     else if (nsURI != null) {
/*      */       
/* 1328 */       writer.writeAttribute(nsURI, localName, value);
/*      */     }
/*      */     else {
/*      */       
/* 1332 */       writer.writeAttribute(localName, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeNamespace(Namespace ns, XMLStreamWriter writer) throws XMLStreamException {
/* 1348 */     if (ns.isDefaultNamespaceDeclaration()) {
/*      */       
/* 1350 */       writer.writeDefaultNamespace(ns.getNamespaceURI());
/*      */     }
/*      */     else {
/*      */       
/* 1354 */       writer.writeNamespace(ns.getPrefix(), ns.getNamespaceURI());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeStartDocument(StartDocument start, XMLStreamWriter writer) throws XMLStreamException {
/* 1370 */     String version = start.getVersion();
/* 1371 */     if (start.encodingSet()) {
/*      */       
/* 1373 */       String encoding = start.getCharacterEncodingScheme();
/* 1374 */       writer.writeStartDocument(encoding, version);
/*      */     }
/*      */     else {
/*      */       
/* 1378 */       writer.writeStartDocument(version);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEndDocument(EndDocument end, XMLStreamWriter writer) throws XMLStreamException {
/* 1394 */     writer.writeEndDocument();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeCharacters(Characters chars, XMLStreamWriter writer) throws XMLStreamException {
/* 1408 */     if (chars.isCData()) {
/*      */       
/* 1410 */       writer.writeCData(chars.getData());
/*      */     }
/*      */     else {
/*      */       
/* 1414 */       writer.writeCharacters(chars.getData());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeComment(Comment comment, XMLStreamWriter writer) throws XMLStreamException {
/* 1430 */     writer.writeComment(comment.getText());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeEntityReference(EntityReference entityRef, XMLStreamWriter writer) throws XMLStreamException {
/* 1444 */     writer.writeEntityRef(entityRef.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeProcessingInstruction(ProcessingInstruction procInst, XMLStreamWriter writer) throws XMLStreamException {
/* 1459 */     String data = procInst.getData();
/* 1460 */     if (data != null) {
/*      */       
/* 1462 */       writer.writeProcessingInstruction(procInst.getTarget(), data);
/*      */     }
/*      */     else {
/*      */       
/* 1466 */       writer.writeProcessingInstruction(procInst.getTarget());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writeDTD(DTD dtd, XMLStreamWriter writer) throws XMLStreamException {
/* 1482 */     writer.writeDTD(dtd.getDocumentTypeDeclaration());
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\io\XMLWriterUtils.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */