/*      */ package com.sun.xml.fastinfoset.dom;
/*      */ 
/*      */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*      */ import com.sun.xml.fastinfoset.Decoder;
/*      */ import com.sun.xml.fastinfoset.DecoderStateTables;
/*      */ import com.sun.xml.fastinfoset.QualifiedName;
/*      */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithmFactory;
/*      */ import com.sun.xml.fastinfoset.util.CharArray;
/*      */ import com.sun.xml.fastinfoset.util.CharArrayString;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import org.jvnet.fastinfoset.EncodingAlgorithm;
/*      */ import org.jvnet.fastinfoset.EncodingAlgorithmException;
/*      */ import org.jvnet.fastinfoset.FastInfosetException;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.Text;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DOMDocumentParser
/*      */   extends Decoder
/*      */ {
/*      */   protected Document _document;
/*      */   protected Node _currentNode;
/*      */   protected Element _currentElement;
/*   55 */   protected Attr[] _namespaceAttributes = new Attr[16];
/*      */   
/*      */   protected int _namespaceAttributesIndex;
/*      */   
/*   59 */   protected int[] _namespacePrefixes = new int[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _namespacePrefixesIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void parse(Document d, InputStream s) throws FastInfosetException, IOException {
/*   73 */     this._currentNode = this._document = d;
/*   74 */     this._namespaceAttributesIndex = 0;
/*      */     
/*   76 */     parse(s);
/*      */   }
/*      */   
/*      */   protected final void parse(InputStream s) throws FastInfosetException, IOException {
/*   80 */     setInputStream(s);
/*   81 */     parse();
/*      */   }
/*      */   
/*      */   protected void resetOnError() {
/*   85 */     this._namespacePrefixesIndex = 0;
/*      */     
/*   87 */     if (this._v == null) {
/*   88 */       this._prefixTable.clearCompletely();
/*      */     }
/*   90 */     this._duplicateAttributeVerifier.clear();
/*      */   }
/*      */   
/*      */   protected final void parse() throws FastInfosetException, IOException {
/*      */     try {
/*   95 */       reset();
/*   96 */       decodeHeader();
/*   97 */       processDII();
/*   98 */     } catch (RuntimeException e) {
/*   99 */       resetOnError();
/*      */       
/*  101 */       throw new FastInfosetException(e);
/*  102 */     } catch (FastInfosetException e) {
/*  103 */       resetOnError();
/*  104 */       throw e;
/*  105 */     } catch (IOException e) {
/*  106 */       resetOnError();
/*  107 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processDII() throws FastInfosetException, IOException {
/*  112 */     this._b = read();
/*  113 */     if (this._b > 0) {
/*  114 */       processDIIOptionalProperties();
/*      */     }
/*      */ 
/*      */     
/*  118 */     boolean firstElementHasOccured = false;
/*  119 */     boolean documentTypeDeclarationOccured = false;
/*  120 */     while (!this._terminate || !firstElementHasOccured) {
/*  121 */       QualifiedName qn; String system_identifier, public_identifier; this._b = read();
/*  122 */       switch (DecoderStateTables.DII(this._b)) {
/*      */         case 0:
/*  124 */           processEII(this._elementNameTable._array[this._b], false);
/*  125 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 1:
/*  128 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*  129 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 2:
/*  132 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*  133 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 3:
/*  136 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*  137 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 5:
/*  141 */           qn = processLiteralQualifiedName(this._b & 0x3, this._elementNameTable.getNext());
/*      */ 
/*      */           
/*  144 */           this._elementNameTable.add(qn);
/*  145 */           processEII(qn, ((this._b & 0x40) > 0));
/*  146 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 4:
/*  150 */           processEIIWithNamespaces();
/*  151 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 20:
/*  155 */           if (documentTypeDeclarationOccured) {
/*  156 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.secondOccurenceOfDTDII"));
/*      */           }
/*  158 */           documentTypeDeclarationOccured = true;
/*      */           
/*  160 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : null;
/*      */           
/*  162 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : null;
/*      */ 
/*      */           
/*  165 */           this._b = read();
/*  166 */           while (this._b == 225) {
/*  167 */             switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */               case 0:
/*  169 */                 if (this._addToTable) {
/*  170 */                   this._v.otherString.add(new CharArray(this._charBuffer, 0, this._charBufferLength, true));
/*      */                 }
/*      */                 break;
/*      */               case 2:
/*  174 */                 throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  180 */             this._b = read();
/*      */           } 
/*  182 */           if ((this._b & 0xF0) != 240) {
/*  183 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingInstructionIIsNotTerminatedCorrectly"));
/*      */           }
/*  185 */           if (this._b == 255) {
/*  186 */             this._terminate = true;
/*      */           }
/*      */           
/*  189 */           this._notations.clear();
/*  190 */           this._unparsedEntities.clear();
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 18:
/*  198 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  201 */           processProcessingII();
/*      */           continue;
/*      */         case 23:
/*  204 */           this._doubleTerminate = true;
/*      */         case 22:
/*  206 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  209 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingDII"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  214 */     while (!this._terminate) {
/*  215 */       this._b = read();
/*  216 */       switch (DecoderStateTables.DII(this._b)) {
/*      */         case 18:
/*  218 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  221 */           processProcessingII();
/*      */           continue;
/*      */         case 23:
/*  224 */           this._doubleTerminate = true;
/*      */         case 22:
/*  226 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  229 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingDII"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processDIIOptionalProperties() throws FastInfosetException, IOException {
/*  237 */     if (this._b == 32) {
/*  238 */       decodeInitialVocabulary();
/*      */       
/*      */       return;
/*      */     } 
/*  242 */     if ((this._b & 0x40) > 0) {
/*  243 */       decodeAdditionalData();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  250 */     if ((this._b & 0x20) > 0) {
/*  251 */       decodeInitialVocabulary();
/*      */     }
/*      */     
/*  254 */     if ((this._b & 0x10) > 0) {
/*  255 */       decodeNotations();
/*      */     }
/*      */ 
/*      */     
/*  259 */     if ((this._b & 0x8) > 0) {
/*  260 */       decodeUnparsedEntities();
/*      */     }
/*      */ 
/*      */     
/*  264 */     if ((this._b & 0x4) > 0) {
/*  265 */       String version = decodeCharacterEncodingScheme();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  272 */     if ((this._b & 0x2) > 0) {
/*  273 */       boolean standalone = (read() > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  280 */     if ((this._b & 0x1) > 0) {
/*  281 */       decodeVersion();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processEII(QualifiedName name, boolean hasAttributes) throws FastInfosetException, IOException {
/*  290 */     if (this._prefixTable._currentInScope[name.prefixIndex] != name.namespaceNameIndex) {
/*  291 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qnameOfEIINotInScope"));
/*      */     }
/*      */     
/*  294 */     Node parentCurrentNode = this._currentNode;
/*      */     
/*  296 */     this._currentNode = this._currentElement = createElement(name.namespaceName, name.qName, name.localName);
/*      */     
/*  298 */     if (this._namespaceAttributesIndex > 0) {
/*  299 */       for (int i = 0; i < this._namespaceAttributesIndex; i++) {
/*  300 */         this._currentElement.setAttributeNode(this._namespaceAttributes[i]);
/*  301 */         this._namespaceAttributes[i] = null;
/*      */       } 
/*  303 */       this._namespaceAttributesIndex = 0;
/*      */     } 
/*      */     
/*  306 */     if (hasAttributes) {
/*  307 */       processAIIs();
/*      */     }
/*      */     
/*  310 */     parentCurrentNode.appendChild(this._currentElement);
/*      */     
/*  312 */     while (!this._terminate) {
/*  313 */       QualifiedName qn; String v; boolean addToTable; String s; int index; String entity_reference_name, str2, str1, system_identifier, public_identifier; this._b = read();
/*  314 */       switch (DecoderStateTables.EII(this._b)) {
/*      */         case 0:
/*  316 */           processEII(this._elementNameTable._array[this._b], false);
/*      */           continue;
/*      */         case 1:
/*  319 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*      */           continue;
/*      */         case 2:
/*  322 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         case 3:
/*  325 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 5:
/*  329 */           qn = processLiteralQualifiedName(this._b & 0x3, this._elementNameTable.getNext());
/*      */ 
/*      */           
/*  332 */           this._elementNameTable.add(qn);
/*  333 */           processEII(qn, ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 4:
/*  337 */           processEIIWithNamespaces();
/*      */           continue;
/*      */         
/*      */         case 6:
/*  341 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  343 */           appendOrCreateTextData(processUtf8CharacterString());
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 7:
/*  348 */           this._octetBufferLength = read() + 3;
/*  349 */           appendOrCreateTextData(processUtf8CharacterString());
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 8:
/*  354 */           this._octetBufferLength = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */           
/*  358 */           this._octetBufferLength += 259;
/*  359 */           appendOrCreateTextData(processUtf8CharacterString());
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 9:
/*  364 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  366 */           v = decodeUtf16StringAsString();
/*  367 */           if ((this._b & 0x10) > 0) {
/*  368 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*  371 */           appendOrCreateTextData(v);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 10:
/*  376 */           this._octetBufferLength = read() + 3;
/*  377 */           v = decodeUtf16StringAsString();
/*  378 */           if ((this._b & 0x10) > 0) {
/*  379 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*  382 */           appendOrCreateTextData(v);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 11:
/*  387 */           this._octetBufferLength = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */           
/*  391 */           this._octetBufferLength += 259;
/*  392 */           v = decodeUtf16StringAsString();
/*  393 */           if ((this._b & 0x10) > 0) {
/*  394 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*  397 */           appendOrCreateTextData(v);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 12:
/*  402 */           addToTable = ((this._b & 0x10) > 0);
/*      */ 
/*      */           
/*  405 */           this._identifier = (this._b & 0x2) << 6;
/*  406 */           this._b = read();
/*  407 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  409 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/*  411 */           str2 = decodeRestrictedAlphabetAsString();
/*  412 */           if (addToTable) {
/*  413 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*  416 */           appendOrCreateTextData(str2);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 13:
/*  421 */           addToTable = ((this._b & 0x10) > 0);
/*      */           
/*  423 */           this._identifier = (this._b & 0x2) << 6;
/*  424 */           this._b = read();
/*  425 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  427 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*  428 */           str1 = convertEncodingAlgorithmDataToCharacters(false);
/*  429 */           if (addToTable) {
/*  430 */             this._characterContentChunkTable.add(str1.toCharArray(), str1.length());
/*      */           }
/*  432 */           appendOrCreateTextData(str1);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 14:
/*  437 */           s = this._characterContentChunkTable.getString(this._b & 0xF);
/*      */           
/*  439 */           appendOrCreateTextData(s);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 15:
/*  444 */           index = ((this._b & 0x3) << 8 | read()) + 16;
/*      */           
/*  446 */           str1 = this._characterContentChunkTable.getString(index);
/*      */           
/*  448 */           appendOrCreateTextData(str1);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 16:
/*  453 */           index = (this._b & 0x3) << 16 | read() << 8 | read();
/*      */ 
/*      */           
/*  456 */           index += 1040;
/*  457 */           str1 = this._characterContentChunkTable.getString(index);
/*      */           
/*  459 */           appendOrCreateTextData(str1);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 17:
/*  464 */           index = read() << 16 | read() << 8 | read();
/*      */ 
/*      */           
/*  467 */           index += 263184;
/*  468 */           str1 = this._characterContentChunkTable.getString(index);
/*      */           
/*  470 */           appendOrCreateTextData(str1);
/*      */           continue;
/*      */         
/*      */         case 18:
/*  474 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  477 */           processProcessingII();
/*      */           continue;
/*      */         
/*      */         case 21:
/*  481 */           entity_reference_name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */           
/*  483 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : null;
/*      */           
/*  485 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : null;
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 23:
/*  492 */           this._doubleTerminate = true;
/*      */         case 22:
/*  494 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  497 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     } 
/*      */ 
/*      */     
/*  501 */     this._terminate = this._doubleTerminate;
/*  502 */     this._doubleTerminate = false;
/*      */     
/*  504 */     this._currentNode = parentCurrentNode;
/*      */   }
/*      */   
/*      */   private void appendOrCreateTextData(String textData) {
/*  508 */     Node lastChild = this._currentNode.getLastChild();
/*  509 */     if (lastChild instanceof Text) {
/*  510 */       ((Text)lastChild).appendData(textData);
/*      */     } else {
/*  512 */       this._currentNode.appendChild(this._document.createTextNode(textData));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final String processUtf8CharacterString() throws FastInfosetException, IOException {
/*  518 */     if ((this._b & 0x10) > 0) {
/*  519 */       this._characterContentChunkTable.ensureSize(this._octetBufferLength);
/*  520 */       int charactersOffset = this._characterContentChunkTable._arrayIndex;
/*  521 */       decodeUtf8StringAsCharBuffer(this._characterContentChunkTable._array, charactersOffset);
/*  522 */       this._characterContentChunkTable.add(this._charBufferLength);
/*  523 */       return this._characterContentChunkTable.getString(this._characterContentChunkTable._cachedIndex);
/*      */     } 
/*  525 */     decodeUtf8StringAsCharBuffer();
/*  526 */     return new String(this._charBuffer, 0, this._charBufferLength);
/*      */   }
/*      */   
/*      */   protected final void processEIIWithNamespaces() throws FastInfosetException, IOException {
/*      */     QualifiedName qn;
/*  531 */     boolean hasAttributes = ((this._b & 0x40) > 0);
/*      */     
/*  533 */     if (++this._prefixTable._declarationId == Integer.MAX_VALUE) {
/*  534 */       this._prefixTable.clearDeclarationIds();
/*      */     }
/*      */ 
/*      */     
/*  538 */     Attr a = null;
/*  539 */     int start = this._namespacePrefixesIndex;
/*  540 */     int b = read();
/*  541 */     while ((b & 0xFC) == 204) {
/*  542 */       String prefix; if (this._namespaceAttributesIndex == this._namespaceAttributes.length) {
/*  543 */         Attr[] newNamespaceAttributes = new Attr[this._namespaceAttributesIndex * 3 / 2 + 1];
/*  544 */         System.arraycopy(this._namespaceAttributes, 0, newNamespaceAttributes, 0, this._namespaceAttributesIndex);
/*  545 */         this._namespaceAttributes = newNamespaceAttributes;
/*      */       } 
/*      */       
/*  548 */       if (this._namespacePrefixesIndex == this._namespacePrefixes.length) {
/*  549 */         int[] namespaceAIIs = new int[this._namespacePrefixesIndex * 3 / 2 + 1];
/*  550 */         System.arraycopy(this._namespacePrefixes, 0, namespaceAIIs, 0, this._namespacePrefixesIndex);
/*  551 */         this._namespacePrefixes = namespaceAIIs;
/*      */       } 
/*      */ 
/*      */       
/*  555 */       switch (b & 0x3) {
/*      */ 
/*      */         
/*      */         case 0:
/*  559 */           a = createAttribute("http://www.w3.org/2000/xmlns/", "xmlns", "xmlns");
/*      */ 
/*      */ 
/*      */           
/*  563 */           a.setValue("");
/*      */           
/*  565 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = -1; this._prefixIndex = this._namespaceNameIndex = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 1:
/*  570 */           a = createAttribute("http://www.w3.org/2000/xmlns/", "xmlns", "xmlns");
/*      */ 
/*      */ 
/*      */           
/*  574 */           a.setValue(decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(false));
/*      */           
/*  576 */           this._prefixIndex = this._namespacePrefixes[this._namespacePrefixesIndex++] = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/*  581 */           prefix = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(false);
/*  582 */           a = createAttribute("http://www.w3.org/2000/xmlns/", createQualifiedNameString(prefix), prefix);
/*      */ 
/*      */ 
/*      */           
/*  586 */           a.setValue("");
/*      */           
/*  588 */           this._namespaceNameIndex = -1;
/*  589 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = this._prefixIndex;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 3:
/*  594 */           prefix = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(true);
/*  595 */           a = createAttribute("http://www.w3.org/2000/xmlns/", createQualifiedNameString(prefix), prefix);
/*      */ 
/*      */ 
/*      */           
/*  599 */           a.setValue(decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(true));
/*      */           
/*  601 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = this._prefixIndex;
/*      */           break;
/*      */       } 
/*      */       
/*  605 */       this._prefixTable.pushScope(this._prefixIndex, this._namespaceNameIndex);
/*      */       
/*  607 */       this._namespaceAttributes[this._namespaceAttributesIndex++] = a;
/*      */       
/*  609 */       b = read();
/*      */     } 
/*  611 */     if (b != 240) {
/*  612 */       throw new IOException(CommonResourceBundle.getInstance().getString("message.EIInamespaceNameNotTerminatedCorrectly"));
/*      */     }
/*  614 */     int end = this._namespacePrefixesIndex;
/*      */     
/*  616 */     this._b = read();
/*  617 */     switch (DecoderStateTables.EII(this._b)) {
/*      */       case 0:
/*  619 */         processEII(this._elementNameTable._array[this._b], hasAttributes);
/*      */         break;
/*      */       case 2:
/*  622 */         processEII(decodeEIIIndexMedium(), hasAttributes);
/*      */         break;
/*      */       case 3:
/*  625 */         processEII(decodeEIIIndexLarge(), hasAttributes);
/*      */         break;
/*      */       
/*      */       case 5:
/*  629 */         qn = processLiteralQualifiedName(this._b & 0x3, this._elementNameTable.getNext());
/*      */ 
/*      */         
/*  632 */         this._elementNameTable.add(qn);
/*  633 */         processEII(qn, hasAttributes);
/*      */         break;
/*      */       
/*      */       default:
/*  637 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEIIAfterAIIs"));
/*      */     } 
/*      */     
/*  640 */     for (int i = start; i < end; i++) {
/*  641 */       this._prefixTable.popScope(this._namespacePrefixes[i]);
/*      */     }
/*  643 */     this._namespacePrefixesIndex = start;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final QualifiedName processLiteralQualifiedName(int state, QualifiedName q) throws FastInfosetException, IOException {
/*  649 */     if (q == null) q = new QualifiedName();
/*      */     
/*  651 */     switch (state) {
/*      */       
/*      */       case 0:
/*  654 */         return q.set(null, null, decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, -1, this._identifier, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  664 */         return q.set(null, decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(false), decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, this._namespaceNameIndex, this._identifier, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  674 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qNameMissingNamespaceName"));
/*      */       
/*      */       case 3:
/*  677 */         return q.set(decodeIdentifyingNonEmptyStringIndexOnFirstBitAsPrefix(true), decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(true), decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), this._prefixIndex, this._namespaceNameIndex, this._identifier, this._charBuffer);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  686 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingEII"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final QualifiedName processLiteralQualifiedName(int state) throws FastInfosetException, IOException {
/*  692 */     switch (state) {
/*      */       
/*      */       case 0:
/*  695 */         return new QualifiedName(null, null, decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, -1, this._identifier, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  705 */         return new QualifiedName(null, decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(false), decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, this._namespaceNameIndex, this._identifier, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  715 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qNameMissingNamespaceName"));
/*      */       
/*      */       case 3:
/*  718 */         return new QualifiedName(decodeIdentifyingNonEmptyStringIndexOnFirstBitAsPrefix(true), decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(true), decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), this._prefixIndex, this._namespaceNameIndex, this._identifier, this._charBuffer);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  727 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingEII"));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processAIIs() throws FastInfosetException, IOException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   4: dup
/*      */     //   5: getfield _currentIteration : I
/*      */     //   8: iconst_1
/*      */     //   9: iadd
/*      */     //   10: dup_x1
/*      */     //   11: putfield _currentIteration : I
/*      */     //   14: ldc 2147483647
/*      */     //   16: if_icmpne -> 26
/*      */     //   19: aload_0
/*      */     //   20: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   23: invokevirtual clear : ()V
/*      */     //   26: aload_0
/*      */     //   27: invokevirtual read : ()I
/*      */     //   30: istore_2
/*      */     //   31: iload_2
/*      */     //   32: invokestatic AII : (I)I
/*      */     //   35: tableswitch default -> 202, 0 -> 72, 1 -> 85, 2 -> 116, 3 -> 156, 4 -> 194, 5 -> 189
/*      */     //   72: aload_0
/*      */     //   73: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   76: getfield _array : [Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   79: iload_2
/*      */     //   80: aaload
/*      */     //   81: astore_1
/*      */     //   82: goto -> 218
/*      */     //   85: iload_2
/*      */     //   86: bipush #31
/*      */     //   88: iand
/*      */     //   89: bipush #8
/*      */     //   91: ishl
/*      */     //   92: aload_0
/*      */     //   93: invokevirtual read : ()I
/*      */     //   96: ior
/*      */     //   97: bipush #64
/*      */     //   99: iadd
/*      */     //   100: istore #4
/*      */     //   102: aload_0
/*      */     //   103: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   106: getfield _array : [Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   109: iload #4
/*      */     //   111: aaload
/*      */     //   112: astore_1
/*      */     //   113: goto -> 218
/*      */     //   116: iload_2
/*      */     //   117: bipush #15
/*      */     //   119: iand
/*      */     //   120: bipush #16
/*      */     //   122: ishl
/*      */     //   123: aload_0
/*      */     //   124: invokevirtual read : ()I
/*      */     //   127: bipush #8
/*      */     //   129: ishl
/*      */     //   130: ior
/*      */     //   131: aload_0
/*      */     //   132: invokevirtual read : ()I
/*      */     //   135: ior
/*      */     //   136: sipush #8256
/*      */     //   139: iadd
/*      */     //   140: istore #4
/*      */     //   142: aload_0
/*      */     //   143: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   146: getfield _array : [Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   149: iload #4
/*      */     //   151: aaload
/*      */     //   152: astore_1
/*      */     //   153: goto -> 218
/*      */     //   156: aload_0
/*      */     //   157: iload_2
/*      */     //   158: iconst_3
/*      */     //   159: iand
/*      */     //   160: aload_0
/*      */     //   161: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   164: invokevirtual getNext : ()Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   167: invokevirtual processLiteralQualifiedName : (ILcom/sun/xml/fastinfoset/QualifiedName;)Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   170: astore_1
/*      */     //   171: aload_1
/*      */     //   172: sipush #256
/*      */     //   175: invokevirtual createAttributeValues : (I)V
/*      */     //   178: aload_0
/*      */     //   179: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   182: aload_1
/*      */     //   183: invokevirtual add : (Lcom/sun/xml/fastinfoset/QualifiedName;)V
/*      */     //   186: goto -> 218
/*      */     //   189: aload_0
/*      */     //   190: iconst_1
/*      */     //   191: putfield _doubleTerminate : Z
/*      */     //   194: aload_0
/*      */     //   195: iconst_1
/*      */     //   196: putfield _terminate : Z
/*      */     //   199: goto -> 1194
/*      */     //   202: new java/io/IOException
/*      */     //   205: dup
/*      */     //   206: invokestatic getInstance : ()Lcom/sun/xml/fastinfoset/CommonResourceBundle;
/*      */     //   209: ldc 'message.decodingAIIs'
/*      */     //   211: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   214: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   217: athrow
/*      */     //   218: aload_1
/*      */     //   219: getfield prefixIndex : I
/*      */     //   222: ifle -> 260
/*      */     //   225: aload_0
/*      */     //   226: getfield _prefixTable : Lcom/sun/xml/fastinfoset/util/PrefixArray;
/*      */     //   229: getfield _currentInScope : [I
/*      */     //   232: aload_1
/*      */     //   233: getfield prefixIndex : I
/*      */     //   236: iaload
/*      */     //   237: aload_1
/*      */     //   238: getfield namespaceNameIndex : I
/*      */     //   241: if_icmpeq -> 260
/*      */     //   244: new org/jvnet/fastinfoset/FastInfosetException
/*      */     //   247: dup
/*      */     //   248: invokestatic getInstance : ()Lcom/sun/xml/fastinfoset/CommonResourceBundle;
/*      */     //   251: ldc 'message.AIIqNameNotInScope'
/*      */     //   253: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   256: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   259: athrow
/*      */     //   260: aload_0
/*      */     //   261: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   264: aload_1
/*      */     //   265: getfield attributeHash : I
/*      */     //   268: aload_1
/*      */     //   269: getfield attributeId : I
/*      */     //   272: invokevirtual checkForDuplicateAttribute : (II)V
/*      */     //   275: aload_0
/*      */     //   276: aload_1
/*      */     //   277: getfield namespaceName : Ljava/lang/String;
/*      */     //   280: aload_1
/*      */     //   281: getfield qName : Ljava/lang/String;
/*      */     //   284: aload_1
/*      */     //   285: getfield localName : Ljava/lang/String;
/*      */     //   288: invokevirtual createAttribute : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/w3c/dom/Attr;
/*      */     //   291: astore #4
/*      */     //   293: aload_0
/*      */     //   294: invokevirtual read : ()I
/*      */     //   297: istore_2
/*      */     //   298: iload_2
/*      */     //   299: invokestatic NISTRING : (I)I
/*      */     //   302: tableswitch default -> 1178, 0 -> 364, 1 -> 430, 2 -> 497, 3 -> 593, 4 -> 659, 5 -> 726, 6 -> 822, 7 -> 914, 8 -> 1007, 9 -> 1043, 10 -> 1094, 11 -> 1154
/*      */     //   364: iload_2
/*      */     //   365: bipush #64
/*      */     //   367: iand
/*      */     //   368: ifle -> 375
/*      */     //   371: iconst_1
/*      */     //   372: goto -> 376
/*      */     //   375: iconst_0
/*      */     //   376: istore #5
/*      */     //   378: aload_0
/*      */     //   379: iload_2
/*      */     //   380: bipush #7
/*      */     //   382: iand
/*      */     //   383: iconst_1
/*      */     //   384: iadd
/*      */     //   385: putfield _octetBufferLength : I
/*      */     //   388: aload_0
/*      */     //   389: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   392: astore_3
/*      */     //   393: iload #5
/*      */     //   395: ifeq -> 407
/*      */     //   398: aload_0
/*      */     //   399: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   402: aload_3
/*      */     //   403: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   406: pop
/*      */     //   407: aload #4
/*      */     //   409: aload_3
/*      */     //   410: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   415: aload_0
/*      */     //   416: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   419: aload #4
/*      */     //   421: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   426: pop
/*      */     //   427: goto -> 1194
/*      */     //   430: iload_2
/*      */     //   431: bipush #64
/*      */     //   433: iand
/*      */     //   434: ifle -> 441
/*      */     //   437: iconst_1
/*      */     //   438: goto -> 442
/*      */     //   441: iconst_0
/*      */     //   442: istore #5
/*      */     //   444: aload_0
/*      */     //   445: aload_0
/*      */     //   446: invokevirtual read : ()I
/*      */     //   449: bipush #9
/*      */     //   451: iadd
/*      */     //   452: putfield _octetBufferLength : I
/*      */     //   455: aload_0
/*      */     //   456: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   459: astore_3
/*      */     //   460: iload #5
/*      */     //   462: ifeq -> 474
/*      */     //   465: aload_0
/*      */     //   466: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   469: aload_3
/*      */     //   470: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   473: pop
/*      */     //   474: aload #4
/*      */     //   476: aload_3
/*      */     //   477: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   482: aload_0
/*      */     //   483: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   486: aload #4
/*      */     //   488: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   493: pop
/*      */     //   494: goto -> 1194
/*      */     //   497: iload_2
/*      */     //   498: bipush #64
/*      */     //   500: iand
/*      */     //   501: ifle -> 508
/*      */     //   504: iconst_1
/*      */     //   505: goto -> 509
/*      */     //   508: iconst_0
/*      */     //   509: istore #5
/*      */     //   511: aload_0
/*      */     //   512: invokevirtual read : ()I
/*      */     //   515: bipush #24
/*      */     //   517: ishl
/*      */     //   518: aload_0
/*      */     //   519: invokevirtual read : ()I
/*      */     //   522: bipush #16
/*      */     //   524: ishl
/*      */     //   525: ior
/*      */     //   526: aload_0
/*      */     //   527: invokevirtual read : ()I
/*      */     //   530: bipush #8
/*      */     //   532: ishl
/*      */     //   533: ior
/*      */     //   534: aload_0
/*      */     //   535: invokevirtual read : ()I
/*      */     //   538: ior
/*      */     //   539: istore #6
/*      */     //   541: aload_0
/*      */     //   542: iload #6
/*      */     //   544: sipush #265
/*      */     //   547: iadd
/*      */     //   548: putfield _octetBufferLength : I
/*      */     //   551: aload_0
/*      */     //   552: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   555: astore_3
/*      */     //   556: iload #5
/*      */     //   558: ifeq -> 570
/*      */     //   561: aload_0
/*      */     //   562: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   565: aload_3
/*      */     //   566: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   569: pop
/*      */     //   570: aload #4
/*      */     //   572: aload_3
/*      */     //   573: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   578: aload_0
/*      */     //   579: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   582: aload #4
/*      */     //   584: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   589: pop
/*      */     //   590: goto -> 1194
/*      */     //   593: iload_2
/*      */     //   594: bipush #64
/*      */     //   596: iand
/*      */     //   597: ifle -> 604
/*      */     //   600: iconst_1
/*      */     //   601: goto -> 605
/*      */     //   604: iconst_0
/*      */     //   605: istore #5
/*      */     //   607: aload_0
/*      */     //   608: iload_2
/*      */     //   609: bipush #7
/*      */     //   611: iand
/*      */     //   612: iconst_1
/*      */     //   613: iadd
/*      */     //   614: putfield _octetBufferLength : I
/*      */     //   617: aload_0
/*      */     //   618: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   621: astore_3
/*      */     //   622: iload #5
/*      */     //   624: ifeq -> 636
/*      */     //   627: aload_0
/*      */     //   628: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   631: aload_3
/*      */     //   632: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   635: pop
/*      */     //   636: aload #4
/*      */     //   638: aload_3
/*      */     //   639: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   644: aload_0
/*      */     //   645: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   648: aload #4
/*      */     //   650: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   655: pop
/*      */     //   656: goto -> 1194
/*      */     //   659: iload_2
/*      */     //   660: bipush #64
/*      */     //   662: iand
/*      */     //   663: ifle -> 670
/*      */     //   666: iconst_1
/*      */     //   667: goto -> 671
/*      */     //   670: iconst_0
/*      */     //   671: istore #5
/*      */     //   673: aload_0
/*      */     //   674: aload_0
/*      */     //   675: invokevirtual read : ()I
/*      */     //   678: bipush #9
/*      */     //   680: iadd
/*      */     //   681: putfield _octetBufferLength : I
/*      */     //   684: aload_0
/*      */     //   685: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   688: astore_3
/*      */     //   689: iload #5
/*      */     //   691: ifeq -> 703
/*      */     //   694: aload_0
/*      */     //   695: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   698: aload_3
/*      */     //   699: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   702: pop
/*      */     //   703: aload #4
/*      */     //   705: aload_3
/*      */     //   706: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   711: aload_0
/*      */     //   712: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   715: aload #4
/*      */     //   717: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   722: pop
/*      */     //   723: goto -> 1194
/*      */     //   726: iload_2
/*      */     //   727: bipush #64
/*      */     //   729: iand
/*      */     //   730: ifle -> 737
/*      */     //   733: iconst_1
/*      */     //   734: goto -> 738
/*      */     //   737: iconst_0
/*      */     //   738: istore #5
/*      */     //   740: aload_0
/*      */     //   741: invokevirtual read : ()I
/*      */     //   744: bipush #24
/*      */     //   746: ishl
/*      */     //   747: aload_0
/*      */     //   748: invokevirtual read : ()I
/*      */     //   751: bipush #16
/*      */     //   753: ishl
/*      */     //   754: ior
/*      */     //   755: aload_0
/*      */     //   756: invokevirtual read : ()I
/*      */     //   759: bipush #8
/*      */     //   761: ishl
/*      */     //   762: ior
/*      */     //   763: aload_0
/*      */     //   764: invokevirtual read : ()I
/*      */     //   767: ior
/*      */     //   768: istore #6
/*      */     //   770: aload_0
/*      */     //   771: iload #6
/*      */     //   773: sipush #265
/*      */     //   776: iadd
/*      */     //   777: putfield _octetBufferLength : I
/*      */     //   780: aload_0
/*      */     //   781: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   784: astore_3
/*      */     //   785: iload #5
/*      */     //   787: ifeq -> 799
/*      */     //   790: aload_0
/*      */     //   791: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   794: aload_3
/*      */     //   795: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   798: pop
/*      */     //   799: aload #4
/*      */     //   801: aload_3
/*      */     //   802: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   807: aload_0
/*      */     //   808: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   811: aload #4
/*      */     //   813: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   818: pop
/*      */     //   819: goto -> 1194
/*      */     //   822: iload_2
/*      */     //   823: bipush #64
/*      */     //   825: iand
/*      */     //   826: ifle -> 833
/*      */     //   829: iconst_1
/*      */     //   830: goto -> 834
/*      */     //   833: iconst_0
/*      */     //   834: istore #5
/*      */     //   836: aload_0
/*      */     //   837: iload_2
/*      */     //   838: bipush #15
/*      */     //   840: iand
/*      */     //   841: iconst_4
/*      */     //   842: ishl
/*      */     //   843: putfield _identifier : I
/*      */     //   846: aload_0
/*      */     //   847: invokevirtual read : ()I
/*      */     //   850: istore_2
/*      */     //   851: aload_0
/*      */     //   852: dup
/*      */     //   853: getfield _identifier : I
/*      */     //   856: iload_2
/*      */     //   857: sipush #240
/*      */     //   860: iand
/*      */     //   861: iconst_4
/*      */     //   862: ishr
/*      */     //   863: ior
/*      */     //   864: putfield _identifier : I
/*      */     //   867: aload_0
/*      */     //   868: iload_2
/*      */     //   869: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   872: aload_0
/*      */     //   873: invokevirtual decodeRestrictedAlphabetAsString : ()Ljava/lang/String;
/*      */     //   876: astore_3
/*      */     //   877: iload #5
/*      */     //   879: ifeq -> 891
/*      */     //   882: aload_0
/*      */     //   883: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   886: aload_3
/*      */     //   887: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   890: pop
/*      */     //   891: aload #4
/*      */     //   893: aload_3
/*      */     //   894: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   899: aload_0
/*      */     //   900: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   903: aload #4
/*      */     //   905: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   910: pop
/*      */     //   911: goto -> 1194
/*      */     //   914: iload_2
/*      */     //   915: bipush #64
/*      */     //   917: iand
/*      */     //   918: ifle -> 925
/*      */     //   921: iconst_1
/*      */     //   922: goto -> 926
/*      */     //   925: iconst_0
/*      */     //   926: istore #5
/*      */     //   928: aload_0
/*      */     //   929: iload_2
/*      */     //   930: bipush #15
/*      */     //   932: iand
/*      */     //   933: iconst_4
/*      */     //   934: ishl
/*      */     //   935: putfield _identifier : I
/*      */     //   938: aload_0
/*      */     //   939: invokevirtual read : ()I
/*      */     //   942: istore_2
/*      */     //   943: aload_0
/*      */     //   944: dup
/*      */     //   945: getfield _identifier : I
/*      */     //   948: iload_2
/*      */     //   949: sipush #240
/*      */     //   952: iand
/*      */     //   953: iconst_4
/*      */     //   954: ishr
/*      */     //   955: ior
/*      */     //   956: putfield _identifier : I
/*      */     //   959: aload_0
/*      */     //   960: iload_2
/*      */     //   961: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   964: aload_0
/*      */     //   965: iconst_1
/*      */     //   966: invokevirtual convertEncodingAlgorithmDataToCharacters : (Z)Ljava/lang/String;
/*      */     //   969: astore_3
/*      */     //   970: iload #5
/*      */     //   972: ifeq -> 984
/*      */     //   975: aload_0
/*      */     //   976: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   979: aload_3
/*      */     //   980: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   983: pop
/*      */     //   984: aload #4
/*      */     //   986: aload_3
/*      */     //   987: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   992: aload_0
/*      */     //   993: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   996: aload #4
/*      */     //   998: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1003: pop
/*      */     //   1004: goto -> 1194
/*      */     //   1007: aload_0
/*      */     //   1008: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   1011: getfield _array : [Ljava/lang/String;
/*      */     //   1014: iload_2
/*      */     //   1015: bipush #63
/*      */     //   1017: iand
/*      */     //   1018: aaload
/*      */     //   1019: astore_3
/*      */     //   1020: aload #4
/*      */     //   1022: aload_3
/*      */     //   1023: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   1028: aload_0
/*      */     //   1029: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   1032: aload #4
/*      */     //   1034: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1039: pop
/*      */     //   1040: goto -> 1194
/*      */     //   1043: iload_2
/*      */     //   1044: bipush #31
/*      */     //   1046: iand
/*      */     //   1047: bipush #8
/*      */     //   1049: ishl
/*      */     //   1050: aload_0
/*      */     //   1051: invokevirtual read : ()I
/*      */     //   1054: ior
/*      */     //   1055: bipush #64
/*      */     //   1057: iadd
/*      */     //   1058: istore #5
/*      */     //   1060: aload_0
/*      */     //   1061: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   1064: getfield _array : [Ljava/lang/String;
/*      */     //   1067: iload #5
/*      */     //   1069: aaload
/*      */     //   1070: astore_3
/*      */     //   1071: aload #4
/*      */     //   1073: aload_3
/*      */     //   1074: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   1079: aload_0
/*      */     //   1080: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   1083: aload #4
/*      */     //   1085: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1090: pop
/*      */     //   1091: goto -> 1194
/*      */     //   1094: iload_2
/*      */     //   1095: bipush #15
/*      */     //   1097: iand
/*      */     //   1098: bipush #16
/*      */     //   1100: ishl
/*      */     //   1101: aload_0
/*      */     //   1102: invokevirtual read : ()I
/*      */     //   1105: bipush #8
/*      */     //   1107: ishl
/*      */     //   1108: ior
/*      */     //   1109: aload_0
/*      */     //   1110: invokevirtual read : ()I
/*      */     //   1113: ior
/*      */     //   1114: sipush #8256
/*      */     //   1117: iadd
/*      */     //   1118: istore #5
/*      */     //   1120: aload_0
/*      */     //   1121: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   1124: getfield _array : [Ljava/lang/String;
/*      */     //   1127: iload #5
/*      */     //   1129: aaload
/*      */     //   1130: astore_3
/*      */     //   1131: aload #4
/*      */     //   1133: aload_3
/*      */     //   1134: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   1139: aload_0
/*      */     //   1140: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   1143: aload #4
/*      */     //   1145: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1150: pop
/*      */     //   1151: goto -> 1194
/*      */     //   1154: aload #4
/*      */     //   1156: ldc ''
/*      */     //   1158: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   1163: aload_0
/*      */     //   1164: getfield _currentElement : Lorg/w3c/dom/Element;
/*      */     //   1167: aload #4
/*      */     //   1169: invokeinterface setAttributeNode : (Lorg/w3c/dom/Attr;)Lorg/w3c/dom/Attr;
/*      */     //   1174: pop
/*      */     //   1175: goto -> 1194
/*      */     //   1178: new java/io/IOException
/*      */     //   1181: dup
/*      */     //   1182: invokestatic getInstance : ()Lcom/sun/xml/fastinfoset/CommonResourceBundle;
/*      */     //   1185: ldc 'message.decodingAIIValue'
/*      */     //   1187: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1190: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1193: athrow
/*      */     //   1194: aload_0
/*      */     //   1195: getfield _terminate : Z
/*      */     //   1198: ifeq -> 26
/*      */     //   1201: aload_0
/*      */     //   1202: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   1205: aload_0
/*      */     //   1206: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   1209: getfield _poolHead : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   1212: putfield _poolCurrent : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   1215: aload_0
/*      */     //   1216: aload_0
/*      */     //   1217: getfield _doubleTerminate : Z
/*      */     //   1220: putfield _terminate : Z
/*      */     //   1223: aload_0
/*      */     //   1224: iconst_0
/*      */     //   1225: putfield _doubleTerminate : Z
/*      */     //   1228: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #736	-> 0
/*      */     //   #737	-> 19
/*      */     //   #742	-> 26
/*      */     //   #743	-> 31
/*      */     //   #745	-> 72
/*      */     //   #746	-> 82
/*      */     //   #749	-> 85
/*      */     //   #751	-> 102
/*      */     //   #752	-> 113
/*      */     //   #756	-> 116
/*      */     //   #758	-> 142
/*      */     //   #759	-> 153
/*      */     //   #762	-> 156
/*      */     //   #765	-> 171
/*      */     //   #766	-> 178
/*      */     //   #767	-> 186
/*      */     //   #769	-> 189
/*      */     //   #771	-> 194
/*      */     //   #773	-> 199
/*      */     //   #775	-> 202
/*      */     //   #778	-> 218
/*      */     //   #779	-> 244
/*      */     //   #782	-> 260
/*      */     //   #784	-> 275
/*      */     //   #791	-> 293
/*      */     //   #792	-> 298
/*      */     //   #795	-> 364
/*      */     //   #796	-> 378
/*      */     //   #797	-> 388
/*      */     //   #798	-> 393
/*      */     //   #799	-> 398
/*      */     //   #802	-> 407
/*      */     //   #803	-> 415
/*      */     //   #804	-> 427
/*      */     //   #808	-> 430
/*      */     //   #809	-> 444
/*      */     //   #810	-> 455
/*      */     //   #811	-> 460
/*      */     //   #812	-> 465
/*      */     //   #815	-> 474
/*      */     //   #816	-> 482
/*      */     //   #817	-> 494
/*      */     //   #821	-> 497
/*      */     //   #822	-> 511
/*      */     //   #826	-> 541
/*      */     //   #827	-> 551
/*      */     //   #828	-> 556
/*      */     //   #829	-> 561
/*      */     //   #832	-> 570
/*      */     //   #833	-> 578
/*      */     //   #834	-> 590
/*      */     //   #838	-> 593
/*      */     //   #839	-> 607
/*      */     //   #840	-> 617
/*      */     //   #841	-> 622
/*      */     //   #842	-> 627
/*      */     //   #845	-> 636
/*      */     //   #846	-> 644
/*      */     //   #847	-> 656
/*      */     //   #851	-> 659
/*      */     //   #852	-> 673
/*      */     //   #853	-> 684
/*      */     //   #854	-> 689
/*      */     //   #855	-> 694
/*      */     //   #858	-> 703
/*      */     //   #859	-> 711
/*      */     //   #860	-> 723
/*      */     //   #864	-> 726
/*      */     //   #865	-> 740
/*      */     //   #869	-> 770
/*      */     //   #870	-> 780
/*      */     //   #871	-> 785
/*      */     //   #872	-> 790
/*      */     //   #875	-> 799
/*      */     //   #876	-> 807
/*      */     //   #877	-> 819
/*      */     //   #881	-> 822
/*      */     //   #883	-> 836
/*      */     //   #884	-> 846
/*      */     //   #885	-> 851
/*      */     //   #887	-> 867
/*      */     //   #889	-> 872
/*      */     //   #890	-> 877
/*      */     //   #891	-> 882
/*      */     //   #894	-> 891
/*      */     //   #895	-> 899
/*      */     //   #896	-> 911
/*      */     //   #900	-> 914
/*      */     //   #901	-> 928
/*      */     //   #902	-> 938
/*      */     //   #903	-> 943
/*      */     //   #905	-> 959
/*      */     //   #906	-> 964
/*      */     //   #907	-> 970
/*      */     //   #908	-> 975
/*      */     //   #910	-> 984
/*      */     //   #911	-> 992
/*      */     //   #912	-> 1004
/*      */     //   #915	-> 1007
/*      */     //   #917	-> 1020
/*      */     //   #918	-> 1028
/*      */     //   #919	-> 1040
/*      */     //   #922	-> 1043
/*      */     //   #924	-> 1060
/*      */     //   #926	-> 1071
/*      */     //   #927	-> 1079
/*      */     //   #928	-> 1091
/*      */     //   #932	-> 1094
/*      */     //   #934	-> 1120
/*      */     //   #936	-> 1131
/*      */     //   #937	-> 1139
/*      */     //   #938	-> 1151
/*      */     //   #941	-> 1154
/*      */     //   #942	-> 1163
/*      */     //   #943	-> 1175
/*      */     //   #945	-> 1178
/*      */     //   #948	-> 1194
/*      */     //   #951	-> 1201
/*      */     //   #953	-> 1215
/*      */     //   #954	-> 1223
/*      */     //   #955	-> 1228
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   102	14	4	i	I
/*      */     //   142	14	4	i	I
/*      */     //   378	52	5	addToTable	Z
/*      */     //   444	53	5	addToTable	Z
/*      */     //   511	82	5	addToTable	Z
/*      */     //   541	52	6	length	I
/*      */     //   607	52	5	addToTable	Z
/*      */     //   673	53	5	addToTable	Z
/*      */     //   740	82	5	addToTable	Z
/*      */     //   770	52	6	length	I
/*      */     //   836	78	5	addToTable	Z
/*      */     //   928	79	5	addToTable	Z
/*      */     //   1060	34	5	index	I
/*      */     //   1120	34	5	index	I
/*      */     //   293	901	4	a	Lorg/w3c/dom/Attr;
/*      */     //   82	1112	1	name	Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   0	1229	0	this	Lcom/sun/xml/fastinfoset/dom/DOMDocumentParser;
/*      */     //   31	1198	2	b	I
/*      */     //   393	836	3	value	Ljava/lang/String;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processCommentII() throws FastInfosetException, IOException {
/*      */     String s;
/*  958 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       
/*      */       case 0:
/*  961 */         s = new String(this._charBuffer, 0, this._charBufferLength);
/*  962 */         if (this._addToTable) {
/*  963 */           this._v.otherString.add((CharArray)new CharArrayString(s, false));
/*      */         }
/*      */         
/*  966 */         this._currentNode.appendChild(this._document.createComment(s));
/*      */         break;
/*      */       
/*      */       case 2:
/*  970 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.commentIIAlgorithmNotSupported"));
/*      */       
/*      */       case 1:
/*  973 */         s = this._v.otherString.get(this._integer).toString();
/*      */         
/*  975 */         this._currentNode.appendChild(this._document.createComment(s));
/*      */         break;
/*      */       
/*      */       case 3:
/*  979 */         this._currentNode.appendChild(this._document.createComment(""));
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processProcessingII() throws FastInfosetException, IOException {
/*  985 */     String data, target = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */     
/*  987 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       
/*      */       case 0:
/*  990 */         data = new String(this._charBuffer, 0, this._charBufferLength);
/*  991 */         if (this._addToTable) {
/*  992 */           this._v.otherString.add((CharArray)new CharArrayString(data, false));
/*      */         }
/*      */         
/*  995 */         this._currentNode.appendChild(this._document.createProcessingInstruction(target, data));
/*      */         break;
/*      */       
/*      */       case 2:
/*  999 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */       
/*      */       case 1:
/* 1002 */         data = this._v.otherString.get(this._integer).toString();
/*      */         
/* 1004 */         this._currentNode.appendChild(this._document.createProcessingInstruction(target, data));
/*      */         break;
/*      */       
/*      */       case 3:
/* 1008 */         this._currentNode.appendChild(this._document.createProcessingInstruction(target, ""));
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Element createElement(String namespaceName, String qName, String localName) {
/* 1014 */     return this._document.createElementNS(namespaceName, qName);
/*      */   }
/*      */   
/*      */   protected Attr createAttribute(String namespaceName, String qName, String localName) {
/* 1018 */     return this._document.createAttributeNS(namespaceName, qName);
/*      */   }
/*      */   
/*      */   protected String convertEncodingAlgorithmDataToCharacters(boolean isAttributeValue) throws FastInfosetException, IOException {
/* 1022 */     StringBuffer buffer = new StringBuffer();
/* 1023 */     if (this._identifier < 9)
/* 1024 */     { Object array = BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier).decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */       
/* 1026 */       BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier).convertToCharacters(array, buffer); }
/* 1027 */     else { if (this._identifier == 9) {
/* 1028 */         if (!isAttributeValue) {
/*      */           
/* 1030 */           this._octetBufferOffset -= this._octetBufferLength;
/* 1031 */           return decodeUtf8StringAsString();
/*      */         } 
/* 1033 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.CDATAAlgorithmNotSupported"));
/* 1034 */       }  if (this._identifier >= 32) {
/* 1035 */         String URI = this._v.encodingAlgorithm.get(this._identifier - 32);
/* 1036 */         EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 1037 */         if (ea != null) {
/* 1038 */           Object data = ea.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/* 1039 */           ea.convertToCharacters(data, buffer);
/*      */         } else {
/* 1041 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.algorithmDataCannotBeReported"));
/*      */         } 
/*      */       }  }
/*      */     
/* 1045 */     return buffer.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\dom\DOMDocumentParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */