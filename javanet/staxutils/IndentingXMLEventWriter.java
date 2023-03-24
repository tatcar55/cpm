/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Pattern;
/*     */ import javanet.staxutils.events.AbstractCharactersEvent;
/*     */ import javanet.staxutils.helpers.EventWriterDelegate;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndentingXMLEventWriter
/*     */   extends EventWriterDelegate
/*     */   implements Indentation
/*     */ {
/*     */   private int depth;
/*     */   private int[] stack;
/*     */   private static final int WROTE_MARKUP = 1;
/*     */   private static final int WROTE_DATA = 2;
/*     */   private final PrefixCharacters newLineEvent;
/*     */   
/*     */   public IndentingXMLEventWriter(XMLEventWriter out) {
/*  82 */     super(out);
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.depth = 0;
/*     */ 
/*     */     
/*  89 */     this.stack = new int[] { 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.newLineEvent = new PrefixCharacters();
/*     */   }
/*     */   public void setIndent(String indent) {
/*  99 */     this.newLineEvent.setIndent(indent);
/*     */   }
/*     */   
/*     */   public void setNewLine(String newLine) {
/* 103 */     this.newLineEvent.setNewLine(newLine);
/*     */   }
/*     */   
/*     */   public String getIndent() {
/* 107 */     return this.newLineEvent.getIndent();
/*     */   }
/*     */   
/*     */   public String getNewLine() {
/* 111 */     return this.newLineEvent.getNewLine();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getLineSeparator() {
/* 119 */     return IndentingXMLStreamWriter.getLineSeparator();
/*     */   }
/*     */   
/*     */   public void add(XMLEvent event) throws XMLStreamException {
/* 123 */     switch (event.getEventType()) {
/*     */       case 4:
/*     */       case 6:
/*     */       case 12:
/* 127 */         this.out.add(event);
/* 128 */         afterData();
/*     */         return;
/*     */       
/*     */       case 1:
/* 132 */         beforeStartElement();
/* 133 */         this.out.add(event);
/* 134 */         afterStartElement();
/*     */         return;
/*     */       
/*     */       case 2:
/* 138 */         beforeEndElement();
/* 139 */         this.out.add(event);
/* 140 */         afterEndElement();
/*     */         return;
/*     */       
/*     */       case 3:
/*     */       case 5:
/*     */       case 7:
/*     */       case 11:
/* 147 */         beforeMarkup();
/* 148 */         this.out.add(event);
/* 149 */         afterMarkup();
/*     */         return;
/*     */       
/*     */       case 8:
/* 153 */         this.out.add(event);
/* 154 */         afterEndDocument();
/*     */         return;
/*     */     } 
/*     */     
/* 158 */     this.out.add(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void beforeMarkup() {
/* 165 */     int soFar = this.stack[this.depth];
/* 166 */     if ((soFar & 0x2) == 0 && (this.depth > 0 || soFar != 0)) {
/*     */       
/*     */       try {
/*     */         
/* 170 */         this.newLineEvent.write(this.out, this.depth);
/* 171 */         if (this.depth > 0 && getIndent().length() > 0) {
/* 172 */           afterMarkup();
/*     */         }
/* 174 */       } catch (Exception e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void afterMarkup() {
/* 181 */     this.stack[this.depth] = this.stack[this.depth] | 0x1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void afterData() {
/* 186 */     this.stack[this.depth] = this.stack[this.depth] | 0x2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void beforeStartElement() {
/* 191 */     beforeMarkup();
/* 192 */     if (this.stack.length <= this.depth + 1) {
/*     */       
/* 194 */       int[] newWrote = new int[this.stack.length * 2];
/* 195 */       System.arraycopy(this.stack, 0, newWrote, 0, this.stack.length);
/* 196 */       this.stack = newWrote;
/*     */     } 
/* 198 */     this.stack[this.depth + 1] = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void afterStartElement() {
/* 203 */     afterMarkup();
/* 204 */     this.depth++;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void beforeEndElement() {
/* 209 */     if (this.depth > 0 && this.stack[this.depth] == 1) {
/*     */       try {
/* 211 */         this.newLineEvent.write(this.out, this.depth - 1);
/* 212 */       } catch (Exception ignored) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void afterEndElement() {
/* 219 */     if (this.depth > 0) {
/* 220 */       this.depth--;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void afterEndDocument() {
/* 226 */     this.depth = 0;
/* 227 */     if (this.stack[0] == 1) {
/*     */       try {
/* 229 */         this.newLineEvent.write(this.out, 0);
/* 230 */       } catch (Exception ignored) {}
/*     */     }
/*     */     
/* 233 */     this.stack[0] = 0;
/*     */   }
/*     */   private static class PrefixCharacters extends AbstractCharactersEvent implements Indentation { private String indent; private String newLine; private final String[] prefixes;
/*     */     private int minimumPrefix;
/*     */     private int depth;
/*     */     
/* 239 */     PrefixCharacters() { super((String)null);
/*     */ 
/*     */ 
/*     */       
/* 243 */       this.indent = "  ";
/*     */ 
/*     */       
/* 246 */       this.newLine = "\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       this.prefixes = new String[] { null, null, null, null, null, null };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 264 */       this.minimumPrefix = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 294 */       this.depth = 0; }
/*     */     public String getIndent() { return this.indent; }
/*     */     public String getNewLine() { return this.newLine; }
/* 297 */     public String getData() { while (this.depth >= this.minimumPrefix + this.prefixes.length) {
/* 298 */         this.prefixes[this.minimumPrefix++ % this.prefixes.length] = null;
/*     */       }
/* 300 */       while (this.depth < this.minimumPrefix) {
/* 301 */         this.prefixes[--this.minimumPrefix % this.prefixes.length] = null;
/*     */       }
/* 303 */       int p = this.depth % this.prefixes.length;
/* 304 */       String data = this.prefixes[p];
/* 305 */       if (data == null) {
/* 306 */         StringBuffer b = new StringBuffer(this.newLine.length() + this.indent.length() * this.depth);
/* 307 */         b.append(this.newLine);
/* 308 */         for (int d = 0; d < this.depth; d++) {
/* 309 */           b.append(this.indent);
/*     */         }
/* 311 */         data = this.prefixes[p] = b.toString();
/*     */       } 
/* 313 */       return data; }
/*     */     public void setIndent(String indent) {
/*     */       if (!indent.equals(this.indent))
/*     */         Arrays.fill((Object[])this.prefixes, (Object)null); 
/*     */       this.indent = indent;
/* 318 */     } public int getEventType() { return 4; }
/*     */     public void setNewLine(String newLine) { if (!newLine.equals(this.newLine))
/*     */         Arrays.fill((Object[])this.prefixes, (Object)null);  this.newLine = newLine; }
/*     */     void write(XMLEventWriter out, int depth) throws XMLStreamException { this.depth = depth;
/* 322 */       out.add((XMLEvent)this); } public Characters asCharacters() { return (Characters)this; }
/*     */ 
/*     */     
/*     */     public boolean isCData() {
/* 326 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isIgnorableWhiteSpace() {
/* 330 */       return isWhiteSpace();
/*     */     }
/*     */     
/* 333 */     private static final Pattern ENCODABLE = Pattern.compile("[&<>]");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
/*     */       try {
/* 340 */         String s = getData();
/* 341 */         if (!ENCODABLE.matcher(s).find()) {
/* 342 */           writer.write(s);
/*     */         } else {
/* 344 */           char[] data = s.toCharArray();
/* 345 */           int first = 0;
/* 346 */           for (int d = first; d < data.length; d++) {
/* 347 */             switch (data[d]) {
/*     */               case '&':
/* 349 */                 writer.write(data, first, d - first);
/* 350 */                 writer.write("&amp;");
/* 351 */                 first = d + 1;
/*     */                 break;
/*     */               case '<':
/* 354 */                 writer.write(data, first, d - first);
/* 355 */                 writer.write("&lt;");
/* 356 */                 first = d + 1;
/*     */                 break;
/*     */               case '>':
/* 359 */                 writer.write(data, first, d - first);
/* 360 */                 writer.write("&gt;");
/* 361 */                 first = d + 1;
/*     */                 break;
/*     */             } 
/*     */           
/*     */           } 
/* 366 */           writer.write(data, first, data.length - first);
/*     */         } 
/* 368 */       } catch (IOException e) {
/* 369 */         throw new XMLStreamException(e);
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\IndentingXMLEventWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */