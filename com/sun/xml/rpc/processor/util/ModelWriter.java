/*     */ package com.sun.xml.rpc.processor.util;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.ProcessorAction;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.ExtendedModelVisitor;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttachmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralIDType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralListType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralTypeVisitor;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralWildcardMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCRequestOrderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPListType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPTypeVisitor;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPUnorderedStructureType;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelWriter
/*     */   extends ExtendedModelVisitor
/*     */   implements ProcessorAction, SOAPTypeVisitor, LiteralTypeVisitor
/*     */ {
/*     */   private IndentingWriter _writer;
/*     */   private ComponentWriter _componentWriter;
/*     */   private String _currentNamespaceURI;
/*     */   private Set _visitedComplexTypes;
/*     */   private static final boolean writeComponentInformation = false;
/*     */   
/*     */   public ModelWriter(IndentingWriter w) {
/* 104 */     this._writer = w;
/* 105 */     this._componentWriter = new ComponentWriter(this._writer);
/*     */   }
/*     */   
/*     */   public ModelWriter(OutputStream out) {
/* 109 */     this(new IndentingWriter(new OutputStreamWriter(out), 2));
/*     */   }
/*     */   
/*     */   public ModelWriter(File f) throws FileNotFoundException {
/* 113 */     this(new FileOutputStream(f));
/*     */   }
/*     */   
/*     */   public void write(Model model) {
/*     */     try {
/* 118 */       this._visitedComplexTypes = new HashSet();
/* 119 */       visit(model);
/* 120 */       this._writer.close();
/* 121 */     } catch (Exception e) {
/* 122 */       e.printStackTrace();
/*     */     } finally {
/* 124 */       this._visitedComplexTypes = null;
/* 125 */       this._currentNamespaceURI = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void perform(Model model, Configuration config, Properties options) {
/* 130 */     write(model);
/*     */   }
/*     */   
/*     */   protected void preVisit(Model model) throws Exception {
/* 134 */     this._writer.p("MODEL ");
/* 135 */     writeQName(model.getName());
/* 136 */     this._writer.pln();
/* 137 */     this._writer.pI();
/* 138 */     this._currentNamespaceURI = model.getTargetNamespaceURI();
/* 139 */     if (this._currentNamespaceURI != null) {
/* 140 */       this._writer.p("TARGET-NAMESPACE ");
/* 141 */       this._writer.pln(this._currentNamespaceURI);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void postVisit(Model model) throws Exception {
/* 146 */     processTypes(model);
/* 147 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   protected void processTypes(Model model) throws Exception {
/* 151 */     for (Iterator<AbstractType> iter = model.getExtraTypes(); iter.hasNext(); ) {
/* 152 */       AbstractType extraType = iter.next();
/* 153 */       if (extraType.isLiteralType()) {
/* 154 */         describe((LiteralType)extraType); continue;
/* 155 */       }  if (extraType.isSOAPType()) {
/* 156 */         describe((SOAPType)extraType);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void preVisit(Service service) throws Exception {
/* 162 */     this._writer.p("SERVICE ");
/* 163 */     writeQName(service.getName());
/* 164 */     this._writer.p(" INTERFACE ");
/* 165 */     this._writer.p(service.getJavaInterface().getName());
/* 166 */     this._writer.pln();
/* 167 */     this._writer.pI();
/* 168 */     this._currentNamespaceURI = service.getName().getNamespaceURI();
/*     */   }
/*     */   
/*     */   protected void postVisit(Service service) throws Exception {
/* 172 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   protected void preVisit(Port port) throws Exception {
/* 176 */     this._writer.p("PORT ");
/* 177 */     writeQName(port.getName());
/* 178 */     this._writer.p(" INTERFACE ");
/* 179 */     this._writer.p(port.getJavaInterface().getName());
/* 180 */     this._writer.pln();
/* 181 */     this._writer.pI();
/* 182 */     this._currentNamespaceURI = port.getName().getNamespaceURI();
/*     */   }
/*     */   
/*     */   protected void postVisit(Port port) throws Exception {
/* 186 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   protected void preVisit(Operation operation) throws Exception {
/* 190 */     this._writer.p("OPERATION ");
/* 191 */     writeQName(operation.getName());
/* 192 */     if (operation.isOverloaded()) {
/* 193 */       this._writer.p(" (OVERLOADED)");
/*     */     }
/* 195 */     if (operation.getStyle() != null) {
/* 196 */       if (operation.getStyle().equals(SOAPStyle.RPC)) {
/* 197 */         this._writer.p(" (RPC)");
/* 198 */       } else if (operation.getStyle().equals(SOAPStyle.DOCUMENT)) {
/* 199 */         this._writer.p(" (DOCUMENT)");
/*     */       } 
/*     */     }
/* 202 */     this._writer.pln();
/* 203 */     this._writer.pI();
/*     */   }
/*     */   
/*     */   protected void postVisit(Operation operation) throws Exception {
/* 207 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   protected void preVisit(Request request) throws Exception {
/* 211 */     this._writer.plnI("REQUEST");
/*     */   }
/*     */   
/*     */   protected void postVisit(Request request) throws Exception {
/* 215 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   protected void preVisit(Response response) throws Exception {
/* 219 */     this._writer.plnI("RESPONSE");
/*     */   }
/*     */   
/*     */   protected void postVisit(Response response) throws Exception {
/* 223 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   protected void preVisit(Fault fault) throws Exception {
/* 227 */     this._writer.p("FAULT ");
/* 228 */     this._writer.p(fault.getName());
/* 229 */     this._writer.pln();
/* 230 */     this._writer.pI();
/*     */   }
/*     */   
/*     */   protected void postVisit(Fault fault) throws Exception {
/* 234 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   protected void visitBodyBlock(Block block) throws Exception {
/* 238 */     this._writer.p("BODY-BLOCK ");
/* 239 */     writeQName(block.getName());
/* 240 */     this._writer.p(" TYPE ");
/* 241 */     writeQName(block.getType().getName());
/* 242 */     if (block.getType().isLiteralType()) {
/* 243 */       this._writer.pln(" (LITERAL)");
/* 244 */       describe((LiteralType)block.getType());
/* 245 */     } else if (block.getType().isSOAPType()) {
/* 246 */       this._writer.pln(" (ENCODED)");
/* 247 */       describe((SOAPType)block.getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void visitHeaderBlock(Block block) throws Exception {
/* 252 */     this._writer.p("HEADER-BLOCK ");
/* 253 */     writeQName(block.getName());
/* 254 */     this._writer.p(" TYPE ");
/* 255 */     writeQName(block.getType().getName());
/* 256 */     if (block.getType().isLiteralType()) {
/* 257 */       this._writer.pln(" (LITERAL)");
/* 258 */       describe((LiteralType)block.getType());
/* 259 */     } else if (block.getType().isSOAPType()) {
/* 260 */       this._writer.pln(" (ENCODED)");
/* 261 */       describe((SOAPType)block.getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void visitFaultBlock(Block block) throws Exception {
/* 266 */     this._writer.p("FAULT-BLOCK ");
/* 267 */     writeQName(block.getName());
/* 268 */     this._writer.p(" TYPE ");
/* 269 */     writeQName(block.getType().getName());
/* 270 */     if (block.getType().isLiteralType()) {
/* 271 */       this._writer.pln(" (LITERAL)");
/* 272 */       describe((LiteralType)block.getType());
/* 273 */     } else if (block.getType().isSOAPType()) {
/* 274 */       this._writer.pln(" (ENCODED)");
/* 275 */       describe((SOAPType)block.getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void visit(Parameter parameter) throws Exception {
/* 280 */     this._writer.p("PARAMETER ");
/* 281 */     this._writer.p(parameter.getName());
/* 282 */     this._writer.p(" TYPE ");
/* 283 */     writeQName(parameter.getType().getName());
/* 284 */     if (parameter.isEmbedded()) {
/* 285 */       this._writer.p(" (EMBEDDED)");
/*     */     }
/* 287 */     if (parameter.getType().isLiteralType()) {
/* 288 */       this._writer.pln(" (LITERAL)");
/* 289 */       describe((LiteralType)parameter.getType());
/* 290 */     } else if (parameter.getType().isSOAPType()) {
/* 291 */       this._writer.pln(" (ENCODED)");
/* 292 */       describe((SOAPType)parameter.getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void describe(LiteralType type) throws Exception {
/* 297 */     this._writer.pI();
/* 298 */     type.accept(this);
/* 299 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   public void visit(LiteralEnumerationType type) throws Exception {
/* 303 */     this._writer.p("LITERAL-ENUMERATION-TYPE ");
/* 304 */     writeQName(type.getName());
/* 305 */     this._writer.p(" JAVA-TYPE ");
/* 306 */     this._writer.p(type.getJavaType().getName());
/* 307 */     this._writer.pln();
/* 308 */     describe(type.getBaseType());
/*     */   }
/*     */   
/*     */   public void visit(LiteralSimpleType type) throws Exception {
/* 312 */     this._writer.p("LITERAL-SIMPLE-TYPE ");
/* 313 */     writeQName(type.getName());
/* 314 */     this._writer.p(" JAVA-TYPE ");
/* 315 */     this._writer.p(type.getJavaType().getName());
/* 316 */     this._writer.pln();
/*     */   }
/*     */   
/*     */   public void visit(LiteralIDType type) throws Exception {
/* 320 */     this._writer.p("LITERAL-SIMPLE-TYPE ");
/* 321 */     writeQName(type.getName());
/* 322 */     this._writer.p(" JAVA-TYPE ");
/* 323 */     this._writer.p(type.getJavaType().getName());
/* 324 */     this._writer.p(" resloveIDREF " + (new Boolean(type.getResolveIDREF())).toString());
/*     */     
/* 326 */     this._writer.pln();
/*     */   }
/*     */   
/*     */   public void visit(LiteralSequenceType type) throws Exception {
/* 330 */     visitLiteralStructuredType((LiteralStructuredType)type, "LITERAL-SEQUENCE-TYPE ", true);
/*     */   }
/*     */   
/*     */   public void visit(LiteralAllType type) throws Exception {
/* 334 */     visitLiteralStructuredType((LiteralStructuredType)type, "LITERAL-ALL-TYPE ", true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void visitLiteralStructuredType(LiteralStructuredType type, String header, boolean detailed) throws Exception {
/* 340 */     boolean alreadySeen = this._visitedComplexTypes.contains(type);
/* 341 */     this._writer.p(header);
/* 342 */     writeQName(type.getName());
/* 343 */     if (alreadySeen) {
/* 344 */       this._writer.p(" (REF)");
/*     */     } else {
/* 346 */       this._visitedComplexTypes.add(type);
/*     */     } 
/* 348 */     this._writer.p(" JAVA-TYPE ");
/* 349 */     this._writer.p(type.getJavaType().getName());
/* 350 */     this._writer.pln();
/*     */     
/* 352 */     if (alreadySeen) {
/*     */       return;
/*     */     }
/*     */     
/* 356 */     if (detailed) {
/* 357 */       this._writer.pI();
/* 358 */       processContentMember(type);
/* 359 */       processAttributeMembers(type);
/* 360 */       processElementMembers(type);
/* 361 */       this._writer.pO();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processContentMember(LiteralStructuredType type) throws Exception {
/* 368 */     if (type.getContentMember() != null) {
/* 369 */       this._writer.p("CONTENT");
/* 370 */       this._writer.pln();
/* 371 */       describe(type.getContentMember().getType());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processAttributeMembers(LiteralStructuredType type) throws Exception {
/* 378 */     for (Iterator<LiteralAttributeMember> iter = type.getAttributeMembers(); iter.hasNext(); ) {
/* 379 */       LiteralAttributeMember attribute = iter.next();
/*     */       
/* 381 */       writeAttributeMember(attribute);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeAttributeMember(LiteralAttributeMember attribute) throws Exception {
/* 388 */     this._writer.p("ATTRIBUTE ");
/* 389 */     this._writer.p(attribute.getName().getLocalPart());
/* 390 */     if (attribute.isRequired()) {
/* 391 */       this._writer.p(" (REQUIRED)");
/*     */     }
/* 393 */     this._writer.pln();
/* 394 */     describe(attribute.getType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processElementMembers(LiteralStructuredType type) throws Exception {
/* 400 */     for (Iterator<LiteralElementMember> iter = type.getElementMembers(); iter.hasNext(); ) {
/* 401 */       LiteralElementMember member = iter.next();
/* 402 */       if (member.isWildcard()) {
/* 403 */         writeWildcardMember((LiteralWildcardMember)member); continue;
/*     */       } 
/* 405 */       writeElementMember(member);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeElementMember(LiteralElementMember element) throws Exception {
/* 413 */     this._writer.p("ELEMENT ");
/* 414 */     this._writer.p(element.getName().getLocalPart());
/* 415 */     if (element.isNillable()) {
/* 416 */       this._writer.p(" (NILLABLE)");
/*     */     }
/* 418 */     if (element.isRequired()) {
/* 419 */       this._writer.p(" (REQUIRED)");
/*     */     }
/* 421 */     if (element.isRepeated()) {
/* 422 */       this._writer.p(" (REPEATED)");
/*     */     }
/* 424 */     this._writer.pln();
/* 425 */     describe(element.getType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeWildcardMember(LiteralWildcardMember wildcard) throws Exception {
/* 431 */     this._writer.p("WILDCARD (ANY)");
/* 432 */     if (wildcard.getExcludedNamespaceName() != null) {
/* 433 */       this._writer.p(" (OTHER) ");
/* 434 */       this._writer.p(wildcard.getExcludedNamespaceName());
/*     */     } 
/* 436 */     if (wildcard.isNillable()) {
/* 437 */       this._writer.p(" (NILLABLE)");
/*     */     }
/* 439 */     if (wildcard.isRequired()) {
/* 440 */       this._writer.p(" (REQUIRED)");
/*     */     }
/* 442 */     if (wildcard.isRepeated()) {
/* 443 */       this._writer.p(" (REPEATED)");
/*     */     }
/* 445 */     this._writer.pln();
/* 446 */     describe(wildcard.getType());
/*     */   }
/*     */   
/*     */   public void visit(LiteralArrayType type) throws Exception {
/* 450 */     this._writer.p("LITERAL-ARRAY-TYPE ");
/* 451 */     writeQName(type.getName());
/* 452 */     this._writer.p(" JAVA-TYPE ");
/* 453 */     this._writer.p(type.getJavaType().getName());
/* 454 */     this._writer.pln();
/* 455 */     describe(type.getElementType());
/*     */   }
/*     */   
/*     */   public void visit(LiteralArrayWrapperType type) throws Exception {
/* 459 */     this._writer.p("LITERAL-ARRAY-WRAPPER-TYPE ");
/* 460 */     writeQName(type.getName());
/* 461 */     this._writer.p(" JAVA-TYPE ");
/* 462 */     this._writer.p(type.getJavaType().getName());
/* 463 */     this._writer.p("ELEMENT-MEMBER ");
/* 464 */     writeQName(type.getElementMember().getName());
/* 465 */     this._writer.p(": TYPE: ");
/* 466 */     writeQName(type.getElementMember().getType().getName());
/* 467 */     this._writer.pln();
/* 468 */     describe(type.getElementMember().getType());
/*     */   }
/*     */   
/*     */   public void visit(LiteralListType type) throws Exception {
/* 472 */     this._writer.p("LITERAL-LIST-TYPE ");
/* 473 */     writeQName(type.getName());
/* 474 */     this._writer.p(" JAVA-TYPE ");
/* 475 */     this._writer.p(type.getJavaType().getName());
/* 476 */     this._writer.pln();
/* 477 */     describe(type.getItemType());
/*     */   }
/*     */   
/*     */   public void visit(SOAPListType type) throws Exception {
/* 481 */     this._writer.p("SOAP-LIST-TYPE ");
/* 482 */     writeQName(type.getName());
/* 483 */     this._writer.p(" JAVA-TYPE ");
/* 484 */     this._writer.p(type.getJavaType().getName());
/* 485 */     this._writer.pln();
/* 486 */     describe(type.getItemType());
/*     */   }
/*     */   
/*     */   public void visit(LiteralFragmentType type) throws Exception {
/* 490 */     this._writer.p("LITERAL-FRAGMENT-TYPE ");
/* 491 */     writeQName(type.getName());
/* 492 */     this._writer.p(" JAVA-TYPE ");
/* 493 */     this._writer.p(type.getJavaType().getName());
/* 494 */     this._writer.pln();
/*     */   }
/*     */   
/*     */   protected void describe(SOAPType type) throws Exception {
/* 498 */     this._writer.pI();
/* 499 */     type.accept(this);
/* 500 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   public void visit(SOAPArrayType type) throws Exception {
/* 504 */     this._writer.p("SOAP-ARRAY-TYPE ");
/* 505 */     writeQName(type.getName());
/* 506 */     this._writer.p(" RANK ");
/* 507 */     this._writer.p(Integer.toString(type.getRank()));
/* 508 */     this._writer.p(" JAVA-TYPE ");
/* 509 */     this._writer.p(type.getJavaType().getName());
/* 510 */     this._writer.pln();
/* 511 */     describe(type.getElementType());
/*     */   }
/*     */   
/*     */   public void visit(SOAPCustomType type) throws Exception {
/* 515 */     this._writer.p("SOAP-CUSTOM-TYPE ");
/* 516 */     writeQName(type.getName());
/* 517 */     this._writer.p(" JAVA-TYPE ");
/* 518 */     this._writer.p(type.getJavaType().getName());
/* 519 */     this._writer.pln();
/* 520 */     this._writer.pI();
/* 521 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   public void visit(SOAPEnumerationType type) throws Exception {
/* 525 */     this._writer.p("SOAP-ENUMERATION-TYPE ");
/* 526 */     writeQName(type.getName());
/* 527 */     this._writer.p(" JAVA-TYPE ");
/* 528 */     this._writer.p(type.getJavaType().getName());
/* 529 */     this._writer.pln();
/* 530 */     describe(type.getBaseType());
/*     */   }
/*     */   
/*     */   public void visit(SOAPSimpleType type) throws Exception {
/* 534 */     this._writer.p("SOAP-SIMPLE-TYPE ");
/* 535 */     writeQName(type.getName());
/* 536 */     this._writer.p(" JAVA-TYPE ");
/* 537 */     this._writer.p(type.getJavaType().getName());
/* 538 */     this._writer.pln();
/*     */   }
/*     */   
/*     */   public void visit(SOAPAnyType type) throws Exception {
/* 542 */     this._writer.p("SOAP-ANY-TYPE ");
/* 543 */     writeQName(type.getName());
/* 544 */     this._writer.p(" JAVA-TYPE ");
/* 545 */     this._writer.p(type.getJavaType().getName());
/* 546 */     this._writer.pln();
/*     */   }
/*     */   
/*     */   public void visit(SOAPOrderedStructureType type) throws Exception {
/* 550 */     visitSOAPStructureType((SOAPStructureType)type, "SOAP-ORDERED-STRUCTURE-TYPE", true);
/*     */   }
/*     */   
/*     */   public void visit(SOAPUnorderedStructureType type) throws Exception {
/* 554 */     visitSOAPStructureType((SOAPStructureType)type, "SOAP-UNORDERED-STRUCTURE-TYPE", true);
/*     */   }
/*     */   
/*     */   public void visit(RPCRequestOrderedStructureType type) throws Exception {
/* 558 */     visitSOAPStructureType((SOAPStructureType)type, "RPC-REQUEST-ORDERED-STRUCTURE-TYPE", false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(RPCRequestUnorderedStructureType type) throws Exception {
/* 563 */     visitSOAPStructureType((SOAPStructureType)type, "RPC-REQUEST-UNORDERED-STRUCTURE-TYPE", false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(RPCResponseStructureType type) throws Exception {
/* 568 */     visitSOAPStructureType((SOAPStructureType)type, "RPC-RESPONSE-STRUCTURE-TYPE", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void visitSOAPStructureType(SOAPStructureType type, String header, boolean detailed) throws Exception {
/* 574 */     boolean alreadySeen = this._visitedComplexTypes.contains(type);
/* 575 */     this._writer.p(header);
/* 576 */     this._writer.p(" ");
/* 577 */     writeQName(type.getName());
/* 578 */     if (alreadySeen) {
/* 579 */       this._writer.p(" (REF)");
/*     */     } else {
/* 581 */       this._visitedComplexTypes.add(type);
/*     */     } 
/* 583 */     this._writer.p(" JAVA-TYPE ");
/* 584 */     this._writer.p(type.getJavaType().getName());
/* 585 */     this._writer.pln();
/*     */     
/* 587 */     if (alreadySeen) {
/*     */       return;
/*     */     }
/*     */     
/* 591 */     if (detailed) {
/* 592 */       this._writer.pI();
/* 593 */       if (type.getParentType() != null) {
/* 594 */         this._writer.pln("PARENT TYPE");
/* 595 */         this._writer.pI();
/* 596 */         describe((SOAPType)type.getParentType());
/* 597 */         this._writer.pO();
/*     */       } 
/* 599 */       processMembers(type);
/* 600 */       this._writer.pO();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processMembers(SOAPStructureType type) throws Exception {
/* 605 */     for (Iterator<SOAPStructureMember> iter = type.getMembers(); iter.hasNext(); ) {
/* 606 */       SOAPStructureMember member = iter.next();
/* 607 */       writeMember(member);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void writeMember(SOAPStructureMember member) throws Exception {
/* 612 */     this._writer.p("MEMBER ");
/* 613 */     if (member.isInherited()) {
/* 614 */       this._writer.p("(INHERITED) ");
/*     */     }
/* 616 */     this._writer.p(member.getName().getLocalPart());
/* 617 */     this._writer.pln();
/* 618 */     describe(member.getType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeQName(QName name) throws IOException {
/* 629 */     if (name == null) {
/* 630 */       this._writer.p("null");
/*     */     } else {
/* 632 */       String nsURI = name.getNamespaceURI();
/* 633 */       if (!nsURI.equals(this._currentNamespaceURI))
/*     */       {
/*     */         
/* 636 */         if (nsURI.length() > 0)
/* 637 */           if (nsURI.equals("http://schemas.xmlsoap.org/wsdl/")) {
/* 638 */             this._writer.p("{wsdl}");
/* 639 */           } else if (nsURI.equals("http://www.w3.org/2001/XMLSchema")) {
/* 640 */             this._writer.p("{xsd}");
/*     */           } else {
/* 642 */             this._writer.p("{");
/* 643 */             this._writer.p(nsURI);
/* 644 */             this._writer.p("}");
/*     */           }  
/*     */       }
/* 647 */       this._writer.p(name.getLocalPart());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visit(LiteralAttachmentType type) throws Exception {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\ModelWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */