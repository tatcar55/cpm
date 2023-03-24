/*     */ package com.sun.xml.stream.buffer.stax;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.AbstractProcessor;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.XMLStreamWriterEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamWriterBufferProcessor
/*     */   extends AbstractProcessor
/*     */ {
/*     */   public StreamWriterBufferProcessor() {}
/*     */   
/*     */   public StreamWriterBufferProcessor(XMLStreamBuffer buffer) {
/*  77 */     setXMLStreamBuffer(buffer, buffer.isFragment());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamWriterBufferProcessor(XMLStreamBuffer buffer, boolean produceFragmentEvent) {
/*  86 */     setXMLStreamBuffer(buffer, produceFragmentEvent);
/*     */   }
/*     */   
/*     */   public final void process(XMLStreamBuffer buffer, XMLStreamWriter writer) throws XMLStreamException {
/*  90 */     setXMLStreamBuffer(buffer, buffer.isFragment());
/*  91 */     process(writer);
/*     */   }
/*     */   
/*     */   public void process(XMLStreamWriter writer) throws XMLStreamException {
/*  95 */     if (this._fragmentMode) {
/*  96 */       writeFragment(writer);
/*     */     } else {
/*  98 */       write(writer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(XMLStreamBuffer buffer) {
/* 107 */     setBuffer(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(XMLStreamBuffer buffer, boolean produceFragmentEvent) {
/* 116 */     setBuffer(buffer, produceFragmentEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(XMLStreamWriter writer) throws XMLStreamException {
/*     */     int item;
/* 127 */     if (!this._fragmentMode) {
/* 128 */       if (this._treeCount > 1)
/* 129 */         throw new IllegalStateException("forest cannot be written as a full infoset"); 
/* 130 */       writer.writeStartDocument();
/*     */     }  while (true) {
/*     */       int length; char[] ch; int start;
/*     */       String comment;
/* 134 */       item = getEIIState(peekStructure());
/* 135 */       writer.flush();
/*     */       
/* 137 */       switch (item) {
/*     */         case 1:
/* 139 */           readStructure();
/*     */           continue;
/*     */         case 3:
/*     */         case 4:
/*     */         case 5:
/*     */         case 6:
/* 145 */           writeFragment(writer);
/*     */           continue;
/*     */         case 12:
/* 148 */           readStructure();
/* 149 */           length = readStructure();
/* 150 */           start = readContentCharactersBuffer(length);
/* 151 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 152 */           writer.writeComment(comment);
/*     */           continue;
/*     */         
/*     */         case 13:
/* 156 */           readStructure();
/* 157 */           length = readStructure16();
/* 158 */           start = readContentCharactersBuffer(length);
/* 159 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 160 */           writer.writeComment(comment);
/*     */           continue;
/*     */         
/*     */         case 14:
/* 164 */           readStructure();
/* 165 */           ch = readContentCharactersCopy();
/* 166 */           writer.writeComment(new String(ch));
/*     */           continue;
/*     */         
/*     */         case 16:
/* 170 */           readStructure();
/* 171 */           writer.writeProcessingInstruction(readStructureString(), readStructureString());
/*     */           continue;
/*     */         case 17:
/* 174 */           readStructure();
/* 175 */           writer.writeEndDocument(); return;
/*     */       }  break;
/*     */     } 
/* 178 */     throw new XMLStreamException("Invalid State " + item);
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
/*     */   public void writeFragment(XMLStreamWriter writer) throws XMLStreamException {
/* 193 */     if (writer instanceof XMLStreamWriterEx) {
/* 194 */       writeFragmentEx((XMLStreamWriterEx)writer);
/*     */     } else {
/* 196 */       writeFragmentNoEx(writer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeFragmentEx(XMLStreamWriterEx writer) throws XMLStreamException {
/* 201 */     int depth = 0;
/*     */     
/* 203 */     int item = getEIIState(peekStructure());
/* 204 */     if (item == 1)
/* 205 */       readStructure();  do {
/*     */       String str1, prefix, uri, localName; int i; char[] arrayOfChar1; String s; CharSequence c; int length; char[] ch; String str4, str3, str2;
/*     */       int start;
/*     */       String str6, str5, comment;
/* 209 */       item = readEiiState();
/*     */       
/* 211 */       switch (item) {
/*     */         case 1:
/* 213 */           throw new AssertionError();
/*     */         case 3:
/* 215 */           depth++;
/* 216 */           str1 = readStructureString();
/* 217 */           str4 = readStructureString();
/* 218 */           str6 = getPrefixFromQName(readStructureString());
/* 219 */           writer.writeStartElement(str6, str4, str1);
/* 220 */           writeAttributes((XMLStreamWriter)writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 4:
/* 224 */           depth++;
/* 225 */           prefix = readStructureString();
/* 226 */           str3 = readStructureString();
/* 227 */           str5 = readStructureString();
/* 228 */           writer.writeStartElement(prefix, str5, str3);
/* 229 */           writeAttributes((XMLStreamWriter)writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 5:
/* 233 */           depth++;
/* 234 */           uri = readStructureString();
/* 235 */           str2 = readStructureString();
/* 236 */           writer.writeStartElement("", str2, uri);
/* 237 */           writeAttributes((XMLStreamWriter)writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 6:
/* 241 */           depth++;
/* 242 */           localName = readStructureString();
/* 243 */           writer.writeStartElement(localName);
/* 244 */           writeAttributes((XMLStreamWriter)writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 7:
/* 248 */           i = readStructure();
/* 249 */           start = readContentCharactersBuffer(i);
/* 250 */           writer.writeCharacters(this._contentCharactersBuffer, start, i);
/*     */           break;
/*     */         
/*     */         case 8:
/* 254 */           i = readStructure16();
/* 255 */           start = readContentCharactersBuffer(i);
/* 256 */           writer.writeCharacters(this._contentCharactersBuffer, start, i);
/*     */           break;
/*     */         
/*     */         case 9:
/* 260 */           arrayOfChar1 = readContentCharactersCopy();
/* 261 */           writer.writeCharacters(arrayOfChar1, 0, arrayOfChar1.length);
/*     */           break;
/*     */         
/*     */         case 10:
/* 265 */           s = readContentString();
/* 266 */           writer.writeCharacters(s);
/*     */           break;
/*     */         
/*     */         case 11:
/* 270 */           c = (CharSequence)readContentObject();
/* 271 */           writer.writePCDATA(c);
/*     */           break;
/*     */         
/*     */         case 12:
/* 275 */           length = readStructure();
/* 276 */           start = readContentCharactersBuffer(length);
/* 277 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 278 */           writer.writeComment(comment);
/*     */           break;
/*     */         
/*     */         case 13:
/* 282 */           length = readStructure16();
/* 283 */           start = readContentCharactersBuffer(length);
/* 284 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 285 */           writer.writeComment(comment);
/*     */           break;
/*     */         
/*     */         case 14:
/* 289 */           ch = readContentCharactersCopy();
/* 290 */           writer.writeComment(new String(ch));
/*     */           break;
/*     */         
/*     */         case 16:
/* 294 */           writer.writeProcessingInstruction(readStructureString(), readStructureString());
/*     */           break;
/*     */         case 17:
/* 297 */           writer.writeEndElement();
/* 298 */           depth--;
/* 299 */           if (depth == 0)
/* 300 */             this._treeCount--; 
/*     */           break;
/*     */         default:
/* 303 */           throw new XMLStreamException("Invalid State " + item);
/*     */       } 
/* 305 */     } while (depth > 0 || this._treeCount > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFragmentNoEx(XMLStreamWriter writer) throws XMLStreamException {
/* 310 */     int depth = 0;
/*     */     
/* 312 */     int item = getEIIState(peekStructure());
/* 313 */     if (item == 1)
/* 314 */       readStructure();  do {
/*     */       String str1, prefix, uri, localName; int i; char[] arrayOfChar1; String s; CharSequence c; int length; char[] ch; String str4, str3, str2; int start;
/*     */       String str6, str5, comment;
/* 317 */       item = readEiiState();
/*     */       
/* 319 */       switch (item) {
/*     */         case 1:
/* 321 */           throw new AssertionError();
/*     */         case 3:
/* 323 */           depth++;
/* 324 */           str1 = readStructureString();
/* 325 */           str4 = readStructureString();
/* 326 */           str6 = getPrefixFromQName(readStructureString());
/* 327 */           writer.writeStartElement(str6, str4, str1);
/* 328 */           writeAttributes(writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 4:
/* 332 */           depth++;
/* 333 */           prefix = readStructureString();
/* 334 */           str3 = readStructureString();
/* 335 */           str5 = readStructureString();
/* 336 */           writer.writeStartElement(prefix, str5, str3);
/* 337 */           writeAttributes(writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 5:
/* 341 */           depth++;
/* 342 */           uri = readStructureString();
/* 343 */           str2 = readStructureString();
/* 344 */           writer.writeStartElement("", str2, uri);
/* 345 */           writeAttributes(writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 6:
/* 349 */           depth++;
/* 350 */           localName = readStructureString();
/* 351 */           writer.writeStartElement(localName);
/* 352 */           writeAttributes(writer, isInscope(depth));
/*     */           break;
/*     */         
/*     */         case 7:
/* 356 */           i = readStructure();
/* 357 */           start = readContentCharactersBuffer(i);
/* 358 */           writer.writeCharacters(this._contentCharactersBuffer, start, i);
/*     */           break;
/*     */         
/*     */         case 8:
/* 362 */           i = readStructure16();
/* 363 */           start = readContentCharactersBuffer(i);
/* 364 */           writer.writeCharacters(this._contentCharactersBuffer, start, i);
/*     */           break;
/*     */         
/*     */         case 9:
/* 368 */           arrayOfChar1 = readContentCharactersCopy();
/* 369 */           writer.writeCharacters(arrayOfChar1, 0, arrayOfChar1.length);
/*     */           break;
/*     */         
/*     */         case 10:
/* 373 */           s = readContentString();
/* 374 */           writer.writeCharacters(s);
/*     */           break;
/*     */         
/*     */         case 11:
/* 378 */           c = (CharSequence)readContentObject();
/* 379 */           if (c instanceof Base64Data) {
/*     */             try {
/* 381 */               Base64Data bd = (Base64Data)c;
/* 382 */               bd.writeTo(writer);
/* 383 */             } catch (IOException e) {
/* 384 */               throw new XMLStreamException(e);
/*     */             }  break;
/*     */           } 
/* 387 */           writer.writeCharacters(c.toString());
/*     */           break;
/*     */ 
/*     */         
/*     */         case 12:
/* 392 */           length = readStructure();
/* 393 */           start = readContentCharactersBuffer(length);
/* 394 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 395 */           writer.writeComment(comment);
/*     */           break;
/*     */         
/*     */         case 13:
/* 399 */           length = readStructure16();
/* 400 */           start = readContentCharactersBuffer(length);
/* 401 */           comment = new String(this._contentCharactersBuffer, start, length);
/* 402 */           writer.writeComment(comment);
/*     */           break;
/*     */         
/*     */         case 14:
/* 406 */           ch = readContentCharactersCopy();
/* 407 */           writer.writeComment(new String(ch));
/*     */           break;
/*     */         
/*     */         case 16:
/* 411 */           writer.writeProcessingInstruction(readStructureString(), readStructureString());
/*     */           break;
/*     */         case 17:
/* 414 */           writer.writeEndElement();
/* 415 */           depth--;
/* 416 */           if (depth == 0)
/* 417 */             this._treeCount--; 
/*     */           break;
/*     */         default:
/* 420 */           throw new XMLStreamException("Invalid State " + item);
/*     */       } 
/* 422 */     } while (depth > 0 || this._treeCount > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isInscope(int depth) {
/* 427 */     return (this._buffer.getInscopeNamespaces().size() > 0 && depth == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeAttributes(XMLStreamWriter writer, boolean inscope) throws XMLStreamException {
/* 435 */     Set<String> prefixSet = inscope ? new HashSet<String>() : Collections.<String>emptySet();
/* 436 */     int item = peekStructure();
/* 437 */     if ((item & 0xF0) == 64)
/*     */     {
/*     */       
/* 440 */       item = writeNamespaceAttributes(item, writer, inscope, prefixSet);
/*     */     }
/* 442 */     if (inscope) {
/* 443 */       writeInscopeNamespaces(writer, prefixSet);
/*     */     }
/* 445 */     if ((item & 0xF0) == 48) {
/* 446 */       writeAttributes(item, writer);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String fixNull(String s) {
/* 451 */     if (s == null) return ""; 
/* 452 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeInscopeNamespaces(XMLStreamWriter writer, Set<String> prefixSet) throws XMLStreamException {
/* 459 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)this._buffer.getInscopeNamespaces().entrySet()) {
/* 460 */       String key = fixNull(e.getKey());
/*     */       
/* 462 */       if (!prefixSet.contains(key))
/* 463 */         writer.writeNamespace(key, e.getValue()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int writeNamespaceAttributes(int item, XMLStreamWriter writer, boolean collectPrefixes, Set<String> prefixSet) throws XMLStreamException {
/*     */     do {
/*     */       String prefix;
/* 470 */       switch (getNIIState(item)) {
/*     */         
/*     */         case 1:
/* 473 */           writer.writeDefaultNamespace("");
/* 474 */           if (collectPrefixes) {
/* 475 */             prefixSet.add("");
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 481 */           prefix = readStructureString();
/* 482 */           writer.writeNamespace(prefix, "");
/* 483 */           if (collectPrefixes) {
/* 484 */             prefixSet.add(prefix);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/* 489 */           prefix = readStructureString();
/* 490 */           writer.writeNamespace(prefix, readStructureString());
/* 491 */           if (collectPrefixes) {
/* 492 */             prefixSet.add(prefix);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 4:
/* 497 */           writer.writeDefaultNamespace(readStructureString());
/* 498 */           if (collectPrefixes) {
/* 499 */             prefixSet.add("");
/*     */           }
/*     */           break;
/*     */       } 
/* 503 */       readStructure();
/*     */       
/* 505 */       item = peekStructure();
/* 506 */     } while ((item & 0xF0) == 64);
/*     */     
/* 508 */     return item;
/*     */   }
/*     */   private void writeAttributes(int item, XMLStreamWriter writer) throws XMLStreamException {
/*     */     do {
/*     */       String uri, localName, prefix;
/* 513 */       switch (getAIIState(item)) {
/*     */         case 1:
/* 515 */           uri = readStructureString();
/* 516 */           localName = readStructureString();
/* 517 */           prefix = getPrefixFromQName(readStructureString());
/* 518 */           writer.writeAttribute(prefix, uri, localName, readContentString());
/*     */           break;
/*     */         
/*     */         case 2:
/* 522 */           writer.writeAttribute(readStructureString(), readStructureString(), readStructureString(), readContentString());
/*     */           break;
/*     */         
/*     */         case 3:
/* 526 */           writer.writeAttribute(readStructureString(), readStructureString(), readContentString());
/*     */           break;
/*     */         case 4:
/* 529 */           writer.writeAttribute(readStructureString(), readContentString());
/*     */           break;
/*     */       } 
/*     */       
/* 533 */       readStructureString();
/*     */       
/* 535 */       readStructure();
/*     */       
/* 537 */       item = peekStructure();
/* 538 */     } while ((item & 0xF0) == 48);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\stax\StreamWriterBufferProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */