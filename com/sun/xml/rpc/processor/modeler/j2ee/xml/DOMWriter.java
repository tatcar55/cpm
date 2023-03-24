/*     */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.CDATASection;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMWriter
/*     */ {
/*     */   protected PrintWriter out;
/*  60 */   protected int indent = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String encodingTag;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String docTypeString;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String prefix;
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMWriter(Document document, PrintWriter writer, String encodingTag, String docTypeString, String prefix) {
/*  77 */     this.prefix = prefix;
/*  78 */     this.indent = this.indent;
/*  79 */     this.encodingTag = encodingTag;
/*  80 */     this.docTypeString = docTypeString;
/*  81 */     this.out = writer;
/*  82 */     print(document);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMWriter(Document document, String outfile, String encoding, String encodingTag, String docTypeString) {
/*  91 */     this.encodingTag = encodingTag;
/*  92 */     this.docTypeString = docTypeString;
/*     */     
/*     */     try {
/*     */       OutputStreamWriter writer;
/*  96 */       if (encoding != null) {
/*  97 */         writer = new OutputStreamWriter(new FileOutputStream(outfile), encoding);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 103 */         writer = new OutputStreamWriter(new FileOutputStream(outfile), "UTF8");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 109 */       this.out = new PrintWriter(new BufferedWriter(writer));
/* 110 */       print(document);
/* 111 */     } catch (Exception e) {
/* 112 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void printIndent() {
/* 117 */     this.out.print(this.prefix);
/* 118 */     for (int i = 0; i < this.indent; i++)
/* 119 */       this.out.print(" "); 
/*     */   }
/*     */   
/*     */   public class XMLVisitor
/*     */   {
/*     */     public void visitNode(Node node) {
/* 125 */       switch (node.getNodeType()) {
/*     */         
/*     */         case 2:
/* 128 */           visitAttr((Attr)node);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 4:
/* 133 */           visitCDATASection((CDATASection)node);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 8:
/* 138 */           visitComment((Comment)node);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 9:
/* 143 */           visitDocument((Document)node);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1:
/* 148 */           visitElement((Element)node);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 7:
/* 153 */           visitProcessingInstruction((ProcessingInstruction)node);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 3:
/* 159 */           visitText((Text)node);
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void visitDocument(Document document) {
/* 166 */       if (DOMWriter.this.encodingTag != null && !DOMWriter.this.encodingTag.equals("")) {
/* 167 */         DOMWriter.this.out.println("<?xml version=\"1.0\" encoding=\"" + DOMWriter.this.encodingTag + "\"?>");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 172 */       if (DOMWriter.this.docTypeString != null && !DOMWriter.this.docTypeString.equals("")) {
/* 173 */         DOMWriter.this.out.println(DOMWriter.this.docTypeString);
/*     */       }
/*     */       
/* 176 */       visitChildNodesHelper(document);
/*     */     }
/*     */     
/*     */     public void visitElement(Element element) {
/* 180 */       boolean currentElementHasChildElements = hasChildElements(element);
/*     */       
/* 182 */       DOMWriter.this.printIndent();
/* 183 */       DOMWriter.this.out.print('<' + element.getNodeName());
/* 184 */       visitAttributesHelper(element);
/* 185 */       DOMWriter.this.out.print(">");
/*     */       
/* 187 */       if (currentElementHasChildElements) {
/* 188 */         DOMWriter.this.out.print("\n");
/*     */       }
/*     */       
/* 191 */       DOMWriter.this.indent += 2;
/* 192 */       visitChildNodesHelper(element);
/* 193 */       DOMWriter.this.indent -= 2;
/*     */       
/* 195 */       if (currentElementHasChildElements) {
/* 196 */         DOMWriter.this.printIndent();
/*     */       }
/*     */       
/* 199 */       DOMWriter.this.out.println("</" + element.getNodeName() + ">");
/*     */     }
/*     */ 
/*     */     
/*     */     public void visitAttr(Attr attr) {
/* 204 */       if (attr.getSpecified()) {
/* 205 */         DOMWriter.this.out.print(" ");
/* 206 */         DOMWriter.this.out.print(attr.getNodeName() + "=\"" + attr.getValue() + '"');
/*     */       } 
/*     */     }
/*     */     
/*     */     public void visitText(Text text) {
/* 211 */       DOMWriter.this.out.print(DOMWriter.this.normalize(text.getNodeValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void visitCDATASection(CDATASection cdataSection) {}
/*     */     
/*     */     public void visitComment(Comment comment) {
/* 218 */       DOMWriter.this.printIndent();
/* 219 */       DOMWriter.this.out.print("<!--");
/* 220 */       DOMWriter.this.out.print(DOMWriter.this.normalize(comment.getNodeValue()));
/* 221 */       DOMWriter.this.out.println("-->");
/*     */     }
/*     */     
/*     */     public void visitProcessingInstruction(ProcessingInstruction pi) {
/* 225 */       DOMWriter.this.printIndent();
/* 226 */       DOMWriter.this.out.print("<?");
/* 227 */       DOMWriter.this.out.print(pi.getNodeName());
/* 228 */       DOMWriter.this.out.print(" ");
/* 229 */       DOMWriter.this.out.print(DOMWriter.this.normalize(pi.getNodeValue()));
/* 230 */       DOMWriter.this.out.println("?>");
/*     */     }
/*     */     
/*     */     public boolean hasChildElements(Node node) {
/* 234 */       boolean result = false;
/* 235 */       NodeList children = node.getChildNodes();
/* 236 */       for (int i = 0; i < children.getLength(); i++) {
/* 237 */         if (children.item(i).getNodeType() == 1) {
/* 238 */           result = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 242 */       return result;
/*     */     }
/*     */     
/*     */     public void visitChildNodesHelper(Node node) {
/* 246 */       NodeList children = node.getChildNodes();
/*     */       
/* 248 */       for (int i = 0; i < children.getLength(); i++) {
/* 249 */         visitNode(children.item(i));
/*     */       }
/*     */     }
/*     */     
/*     */     public void visitAttributesHelper(Node node) {
/* 254 */       NamedNodeMap map = node.getAttributes();
/* 255 */       for (int i = 0; i < map.getLength(); i++) {
/* 256 */         visitNode(map.item(i));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(Node node) {
/* 266 */     if (node != null) {
/* 267 */       XMLVisitor visitor = new XMLVisitor();
/* 268 */       visitor.visitNode(node);
/*     */     } 
/* 270 */     this.out.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String normalize(String s) {
/* 277 */     StringBuffer str = new StringBuffer();
/* 278 */     s = s.trim();
/*     */     
/* 280 */     int len = (s != null) ? s.length() : 0;
/* 281 */     for (int i = 0; i < len; i++) {
/* 282 */       char ch = s.charAt(i);
/* 283 */       switch (ch) {
/*     */         
/*     */         case '<':
/* 286 */           str.append("&lt;");
/*     */           break;
/*     */ 
/*     */         
/*     */         case '>':
/* 291 */           str.append("&gt;");
/*     */           break;
/*     */ 
/*     */         
/*     */         case '&':
/* 296 */           str.append("&amp;");
/*     */           break;
/*     */ 
/*     */         
/*     */         case '"':
/* 301 */           str.append("&quot;");
/*     */           break;
/*     */ 
/*     */         
/*     */         case '\n':
/*     */         case '\r':
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 311 */           str.append(ch);
/*     */           break;
/*     */       } 
/*     */     } 
/* 315 */     return str.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\DOMWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */