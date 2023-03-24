/*     */ package com.sun.xml.rpc.processor.model.exporter;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.HandlerChainInfo;
/*     */ import com.sun.xml.rpc.processor.config.HandlerInfo;
/*     */ import com.sun.xml.rpc.processor.config.ImportedDocumentInfo;
/*     */ import com.sun.xml.rpc.processor.config.TypeMappingInfo;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.HeaderFault;
/*     */ import com.sun.xml.rpc.processor.model.Message;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import com.sun.xml.rpc.processor.model.ModelObject;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaCustomType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationEntry;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaParameter;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttachmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralContentMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralIDType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralListType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralWildcardMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCRequestOrderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPListType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPUnorderedStructureType;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ 
/*     */ public class ModelExporter
/*     */   extends ExporterBase
/*     */   implements Constants
/*     */ {
/*     */   public ModelExporter(OutputStream s) {
/* 102 */     super(s);
/*     */   }
/*     */   
/*     */   public void doExport(Model m) {
/* 106 */     internalDoExport(m);
/*     */   }
/*     */   
/*     */   protected QName getContainerName() {
/* 110 */     return QNAME_MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getVersion() {
/* 117 */     return "1.1.3";
/*     */   }
/*     */   
/*     */   protected void failUnsupportedClass(Class klass) {
/* 121 */     throw new ModelException("model.exporter.unsupportedClass", klass.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void visit(Object obj) {
/* 128 */     if (obj == null)
/*     */       return; 
/* 130 */     if (obj instanceof LiteralWildcardMember) {
/* 131 */       visitLiteralWildcardMember((LiteralWildcardMember)obj);
/*     */     }
/* 133 */     else if (obj instanceof ImportedDocumentInfo) {
/* 134 */       visitImportedDocumentInfo((ImportedDocumentInfo)obj);
/*     */     }
/* 136 */     else if (obj instanceof JavaInterface) {
/* 137 */       visitJavaInterface((JavaInterface)obj);
/*     */     }
/* 139 */     else if (obj instanceof JavaCustomType) {
/* 140 */       visitJavaCustomType((JavaCustomType)obj);
/*     */     }
/* 142 */     else if (obj instanceof Operation) {
/* 143 */       visitOperation((Operation)obj);
/*     */     }
/* 145 */     else if (obj instanceof HandlerChainInfo) {
/* 146 */       visitHandlerChainInfo((HandlerChainInfo)obj);
/*     */     }
/* 148 */     else if (obj instanceof JavaException) {
/* 149 */       visitJavaException((JavaException)obj);
/*     */     }
/* 151 */     else if (obj instanceof JavaStructureType) {
/* 152 */       visitJavaStructureType((JavaStructureType)obj);
/*     */     }
/* 154 */     else if (obj instanceof JavaSimpleType) {
/* 155 */       visitJavaSimpleType((JavaSimpleType)obj);
/*     */     }
/* 157 */     else if (obj instanceof JavaStructureMember) {
/* 158 */       visitJavaStructureMember((JavaStructureMember)obj);
/*     */     }
/* 160 */     else if (obj instanceof Block) {
/* 161 */       visitBlock((Block)obj);
/*     */     }
/* 163 */     else if (obj instanceof LiteralElementMember) {
/* 164 */       visitLiteralElementMember((LiteralElementMember)obj);
/*     */     }
/* 166 */     else if (obj instanceof LiteralArrayWrapperType) {
/* 167 */       visitLiteralArrayWrapperType((LiteralArrayWrapperType)obj);
/*     */     }
/* 169 */     else if (obj instanceof LiteralSequenceType) {
/* 170 */       visitLiteralSequenceType((LiteralSequenceType)obj);
/*     */     }
/* 172 */     else if (obj instanceof RPCRequestUnorderedStructureType) {
/* 173 */       visitRPCRequestUnorderedStructureType((RPCRequestUnorderedStructureType)obj);
/*     */     }
/* 175 */     else if (obj instanceof JavaEnumerationEntry) {
/* 176 */       visitJavaEnumerationEntry((JavaEnumerationEntry)obj);
/*     */     }
/* 178 */     else if (obj instanceof Response) {
/* 179 */       visitResponse((Response)obj);
/*     */     }
/* 181 */     else if (obj instanceof RPCRequestOrderedStructureType) {
/* 182 */       visitRPCRequestOrderedStructureType((RPCRequestOrderedStructureType)obj);
/*     */     }
/* 184 */     else if (obj instanceof LiteralEnumerationType) {
/* 185 */       visitLiteralEnumerationType((LiteralEnumerationType)obj);
/*     */     }
/* 187 */     else if (obj instanceof Request) {
/* 188 */       visitRequest((Request)obj);
/*     */     }
/* 190 */     else if (obj instanceof LiteralAllType) {
/* 191 */       visitLiteralAllType((LiteralAllType)obj);
/*     */     }
/* 193 */     else if (obj instanceof JavaArrayType) {
/* 194 */       visitJavaArrayType((JavaArrayType)obj);
/*     */     }
/* 196 */     else if (obj instanceof Port) {
/* 197 */       visitPort((Port)obj);
/*     */     }
/* 199 */     else if (obj instanceof LiteralAttributeMember) {
/* 200 */       visitLiteralAttributeMember((LiteralAttributeMember)obj);
/*     */     }
/* 202 */     else if (obj instanceof HandlerInfo) {
/* 203 */       visitHandlerInfo((HandlerInfo)obj);
/*     */     }
/* 205 */     else if (obj instanceof Service) {
/* 206 */       visitService((Service)obj);
/*     */     }
/* 208 */     else if (obj instanceof SOAPStructureMember) {
/* 209 */       visitSOAPStructureMember((SOAPStructureMember)obj);
/*     */     }
/* 211 */     else if (obj instanceof JavaParameter) {
/* 212 */       visitJavaParameter((JavaParameter)obj);
/*     */     }
/* 214 */     else if (obj instanceof Model) {
/* 215 */       visitModel((Model)obj);
/*     */     }
/* 217 */     else if (obj instanceof LiteralSimpleType) {
/* 218 */       visitLiteralSimpleType((LiteralSimpleType)obj);
/*     */     }
/* 220 */     else if (obj instanceof LiteralArrayType) {
/* 221 */       visitLiteralArrayType((LiteralArrayType)obj);
/*     */     }
/* 223 */     else if (obj instanceof LiteralListType) {
/* 224 */       visitLiteralListType((LiteralListType)obj);
/*     */     }
/* 226 */     else if (obj instanceof JavaEnumerationType) {
/* 227 */       visitJavaEnumerationType((JavaEnumerationType)obj);
/*     */     }
/* 229 */     else if (obj instanceof SOAPCustomType) {
/* 230 */       visitSOAPCustomType((SOAPCustomType)obj);
/*     */     }
/* 232 */     else if (obj instanceof LiteralFragmentType) {
/* 233 */       visitLiteralFragmentType((LiteralFragmentType)obj);
/*     */     }
/* 235 */     else if (obj instanceof SOAPArrayType) {
/* 236 */       visitSOAPArrayType((SOAPArrayType)obj);
/*     */     }
/* 238 */     else if (obj instanceof SOAPUnorderedStructureType) {
/* 239 */       visitSOAPUnorderedStructureType((SOAPUnorderedStructureType)obj);
/*     */     }
/* 241 */     else if (obj instanceof Message) {
/* 242 */       visitMessage((Message)obj);
/*     */     }
/* 244 */     else if (obj instanceof HeaderFault) {
/* 245 */       visitHeaderFault((HeaderFault)obj);
/*     */     }
/* 247 */     else if (obj instanceof JavaMethod) {
/* 248 */       visitJavaMethod((JavaMethod)obj);
/*     */     }
/* 250 */     else if (obj instanceof SOAPAnyType) {
/* 251 */       visitSOAPAnyType((SOAPAnyType)obj);
/*     */     }
/* 253 */     else if (obj instanceof SOAPSimpleType) {
/* 254 */       visitSOAPSimpleType((SOAPSimpleType)obj);
/*     */     }
/* 256 */     else if (obj instanceof SOAPOrderedStructureType) {
/* 257 */       visitSOAPOrderedStructureType((SOAPOrderedStructureType)obj);
/* 258 */     } else if (obj instanceof SOAPAttributeMember) {
/* 259 */       visitSOAPAttributeMember((SOAPAttributeMember)obj);
/*     */     }
/* 261 */     else if (obj instanceof RPCResponseStructureType) {
/* 262 */       visitRPCResponseStructureType((RPCResponseStructureType)obj);
/*     */     }
/* 264 */     else if (obj instanceof Parameter) {
/* 265 */       visitParameter((Parameter)obj);
/*     */     }
/* 267 */     else if (obj instanceof TypeMappingInfo) {
/* 268 */       visitTypeMappingInfo((TypeMappingInfo)obj);
/*     */     }
/* 270 */     else if (obj instanceof Fault) {
/* 271 */       visitFault((Fault)obj);
/*     */     }
/* 273 */     else if (obj instanceof LiteralContentMember) {
/* 274 */       visitLiteralContentMember((LiteralContentMember)obj);
/*     */     }
/* 276 */     else if (obj instanceof SOAPEnumerationType) {
/* 277 */       visitSOAPEnumerationType((SOAPEnumerationType)obj);
/*     */     }
/* 279 */     else if (obj instanceof JavaType) {
/* 280 */       visitJavaType((JavaType)obj);
/*     */     }
/* 282 */     else if (obj instanceof ModelObject) {
/* 283 */       visitModelObject((ModelObject)obj);
/*     */     }
/* 285 */     else if (obj instanceof LiteralIDType) {
/* 286 */       visitLiteralIDType((LiteralIDType)obj);
/* 287 */     } else if (obj instanceof SOAPListType) {
/* 288 */       visitSOAPListType((SOAPListType)obj);
/* 289 */     } else if (obj instanceof LiteralAttachmentType) {
/* 290 */       visitLiteralAttachmentType((LiteralAttachmentType)obj);
/*     */     } else {
/*     */       
/* 293 */       super.visit(obj);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void visitLiteralAttachmentType(LiteralAttachmentType target) {
/* 301 */     property("mimeType", target, target.getMIMEType());
/* 302 */     property("alternateMIMETypes", target, target.getAlternateMIMETypes());
/* 303 */     property("contentId", target, target.getContentID());
/* 304 */     property("isSwaRef", target, new Boolean(target.isSwaRef()));
/* 305 */     property("javaType", target, target.getJavaType());
/*     */   }
/*     */   
/*     */   protected void visitLiteralWildcardMember(LiteralWildcardMember target) {
/* 309 */     property("nillable", target, new Boolean(target.isNillable()));
/* 310 */     property("javaStructureMember", target, target.getJavaStructureMember());
/* 311 */     property("required", target, new Boolean(target.isRequired()));
/* 312 */     property("repeated", target, new Boolean(target.isRepeated()));
/* 313 */     property("type", target, target.getType());
/* 314 */     property("name", target, target.getName());
/* 315 */     property("excludedNamespaceName", target, target.getExcludedNamespaceName());
/*     */   }
/*     */   
/*     */   protected void visitImportedDocumentInfo(ImportedDocumentInfo target) {
/* 319 */     property("namespace", target, target.getNamespace());
/* 320 */     property("type", target, new Integer(target.getType()));
/* 321 */     property("location", target, target.getLocation());
/*     */   }
/*     */   
/*     */   protected void visitJavaInterface(JavaInterface target) {
/* 325 */     property("realName", target, target.getRealName());
/* 326 */     property("formalName", target, target.getFormalName());
/* 327 */     property("methodsList", target, target.getMethodsList());
/* 328 */     property("interfacesList", target, target.getInterfacesList());
/* 329 */     property("impl", target, target.getImpl());
/*     */   }
/*     */   
/*     */   protected void visitJavaCustomType(JavaCustomType target) {
/* 333 */     property("realName", target, target.getRealName());
/* 334 */     property("formalName", target, target.getFormalName());
/* 335 */     property("present", target, new Boolean(target.isPresent()));
/* 336 */     property("holder", target, new Boolean(target.isHolder()));
/* 337 */     property("holderPresent", target, new Boolean(target.isHolderPresent()));
/* 338 */     property("initString", target, target.getInitString());
/* 339 */     property("holderName", target, target.getHolderName());
/* 340 */     property("typeMappingInfo", target, target.getTypeMappingInfo());
/*     */   }
/*     */   
/*     */   protected void visitOperation(Operation target) {
/* 344 */     property("propertiesMap", target, target.getPropertiesMap());
/* 345 */     property("uniqueName", target, target.getUniqueName());
/* 346 */     property("request", target, target.getRequest());
/* 347 */     property("response", target, target.getResponse());
/* 348 */     property("faultsSet", target, target.getFaultsSet());
/* 349 */     property("javaMethod", target, target.getJavaMethod());
/* 350 */     property("SOAPAction", target, target.getSOAPAction());
/* 351 */     property("use", target, target.getUse());
/* 352 */     property("style", target, target.getStyle());
/* 353 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitHandlerChainInfo(HandlerChainInfo target) {
/* 357 */     property("handlersList", target, target.getHandlersList());
/* 358 */     property("roles", target, target.getRoles());
/*     */   }
/*     */   
/*     */   protected void visitJavaException(JavaException target) {
/* 362 */     property("realName", target, target.getRealName());
/* 363 */     property("formalName", target, target.getFormalName());
/* 364 */     property("present", target, new Boolean(target.isPresent()));
/* 365 */     property("holder", target, new Boolean(target.isHolder()));
/* 366 */     property("holderPresent", target, new Boolean(target.isHolderPresent()));
/* 367 */     property("initString", target, target.getInitString());
/* 368 */     property("holderName", target, target.getHolderName());
/* 369 */     property("membersList", target, target.getMembersList());
/* 370 */     property("subclassesSet", target, target.getSubclassesSet());
/* 371 */     property("abstract", target, new Boolean(target.isAbstract()));
/* 372 */     property("owner", target, target.getOwner());
/* 373 */     property("superclass", target, target.getSuperclass());
/*     */   }
/*     */   
/*     */   protected void visitJavaStructureType(JavaStructureType target) {
/* 377 */     property("realName", target, target.getRealName());
/* 378 */     property("formalName", target, target.getFormalName());
/* 379 */     property("present", target, new Boolean(target.isPresent()));
/* 380 */     property("holder", target, new Boolean(target.isHolder()));
/* 381 */     property("holderPresent", target, new Boolean(target.isHolderPresent()));
/* 382 */     property("initString", target, target.getInitString());
/* 383 */     property("holderName", target, target.getHolderName());
/* 384 */     property("membersList", target, target.getMembersList());
/* 385 */     property("subclassesSet", target, target.getSubclassesSet());
/* 386 */     property("abstract", target, new Boolean(target.isAbstract()));
/* 387 */     property("owner", target, target.getOwner());
/* 388 */     property("superclass", target, target.getSuperclass());
/*     */   }
/*     */   
/*     */   protected void visitJavaSimpleType(JavaSimpleType target) {
/* 392 */     property("realName", target, target.getRealName());
/* 393 */     property("formalName", target, target.getFormalName());
/* 394 */     property("present", target, new Boolean(target.isPresent()));
/* 395 */     property("holder", target, new Boolean(target.isHolder()));
/* 396 */     property("holderPresent", target, new Boolean(target.isHolderPresent()));
/* 397 */     property("initString", target, target.getInitString());
/* 398 */     property("holderName", target, target.getHolderName());
/*     */   }
/*     */   
/*     */   protected void visitJavaStructureMember(JavaStructureMember target) {
/* 402 */     property("readMethod", target, target.getReadMethod());
/* 403 */     property("writeMethod", target, target.getWriteMethod());
/* 404 */     property("inherited", target, new Boolean(target.isInherited()));
/* 405 */     property("constructorPos", target, new Integer(target.getConstructorPos()));
/* 406 */     property("type", target, target.getType());
/* 407 */     property("public", target, new Boolean(target.isPublic()));
/* 408 */     property("declaringClass", target, target.getDeclaringClass());
/* 409 */     property("owner", target, target.getOwner());
/* 410 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitBlock(Block target) {
/* 414 */     property("propertiesMap", target, target.getPropertiesMap());
/* 415 */     property("type", target, target.getType());
/* 416 */     property("location", target, new Integer(target.getLocation()));
/* 417 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitLiteralElementMember(LiteralElementMember target) {
/* 421 */     property("nillable", target, new Boolean(target.isNillable()));
/* 422 */     property("javaStructureMember", target, target.getJavaStructureMember());
/* 423 */     property("required", target, new Boolean(target.isRequired()));
/* 424 */     property("repeated", target, new Boolean(target.isRepeated()));
/* 425 */     property("type", target, target.getType());
/* 426 */     property("name", target, target.getName());
/* 427 */     property("inherited", target, new Boolean(target.isInherited()));
/*     */   }
/*     */   
/*     */   protected void visitLiteralSequenceType(LiteralSequenceType target) {
/* 431 */     property("javaType", target, target.getJavaType());
/* 432 */     property("propertiesMap", target, target.getPropertiesMap());
/* 433 */     property("version", target, target.getVersion());
/* 434 */     property("name", target, target.getName());
/* 435 */     property("schemaTypeRef", target, target.getSchemaTypeRef());
/* 436 */     property("attributeMembersList", target, target.getAttributeMembersList());
/* 437 */     property("elementMembersList", target, target.getElementMembersList());
/* 438 */     property("contentMember", target, target.getContentMember());
/* 439 */     property("subtypesSet", target, target.getSubtypesSet());
/* 440 */     property("parentType", target, target.getParentType());
/* 441 */     property("nillable", target, new Boolean(target.isNillable()));
/* 442 */     property("rpcWrapper", target, new Boolean(target.isRpcWrapper()));
/* 443 */     property("isUnwrapped", target, new Boolean(target.isUnwrapped()));
/*     */   }
/*     */   
/*     */   protected void visitLiteralArrayWrapperType(LiteralArrayWrapperType target) {
/* 447 */     visitLiteralSequenceType((LiteralSequenceType)target);
/* 448 */     property("javaArrayType", target, target.getJavaArrayType());
/*     */   }
/*     */   
/*     */   protected void visitRPCRequestUnorderedStructureType(RPCRequestUnorderedStructureType target) {
/* 452 */     property("javaType", target, target.getJavaType());
/* 453 */     property("propertiesMap", target, target.getPropertiesMap());
/* 454 */     property("version", target, target.getVersion());
/* 455 */     property("name", target, target.getName());
/* 456 */     property("membersList", target, target.getMembersList());
/* 457 */     property("attributeMembersList", target, target.getAttributeMembersList());
/* 458 */     property("subtypesSet", target, target.getSubtypesSet());
/* 459 */     property("parentType", target, target.getParentType());
/*     */   }
/*     */   
/*     */   protected void visitJavaEnumerationEntry(JavaEnumerationEntry target) {
/* 463 */     property("literalValue", target, target.getLiteralValue());
/* 464 */     property("value", target, target.getValue());
/* 465 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitResponse(Response target) {
/* 469 */     property("propertiesMap", target, target.getPropertiesMap());
/* 470 */     property("bodyBlocksMap", target, target.getBodyBlocksMap());
/* 471 */     property("headerBlocksMap", target, target.getHeaderBlocksMap());
/* 472 */     property("parametersList", target, target.getParametersList());
/* 473 */     property("faultBlocksMap", target, target.getFaultBlocksMap());
/* 474 */     property("attachmentBlocksMap", target, target.getAttachmentBlocksMap());
/*     */   }
/*     */   
/*     */   protected void visitRPCRequestOrderedStructureType(RPCRequestOrderedStructureType target) {
/* 478 */     property("javaType", target, target.getJavaType());
/* 479 */     property("propertiesMap", target, target.getPropertiesMap());
/* 480 */     property("version", target, target.getVersion());
/* 481 */     property("name", target, target.getName());
/* 482 */     property("membersList", target, target.getMembersList());
/* 483 */     property("subtypesSet", target, target.getSubtypesSet());
/* 484 */     property("parentType", target, target.getParentType());
/*     */   }
/*     */   
/*     */   protected void visitLiteralEnumerationType(LiteralEnumerationType target) {
/* 488 */     property("javaType", target, target.getJavaType());
/* 489 */     property("propertiesMap", target, target.getPropertiesMap());
/* 490 */     property("version", target, target.getVersion());
/* 491 */     property("name", target, target.getName());
/* 492 */     property("schemaTypeRef", target, target.getSchemaTypeRef());
/* 493 */     property("baseType", target, target.getBaseType());
/* 494 */     property("nillable", target, new Boolean(target.isNillable()));
/*     */   }
/*     */   
/*     */   protected void visitRequest(Request target) {
/* 498 */     property("propertiesMap", target, target.getPropertiesMap());
/* 499 */     property("bodyBlocksMap", target, target.getBodyBlocksMap());
/* 500 */     property("headerBlocksMap", target, target.getHeaderBlocksMap());
/* 501 */     property("parametersList", target, target.getParametersList());
/*     */   }
/*     */   
/*     */   protected void visitLiteralAllType(LiteralAllType target) {
/* 505 */     property("javaType", target, target.getJavaType());
/* 506 */     property("propertiesMap", target, target.getPropertiesMap());
/* 507 */     property("version", target, target.getVersion());
/* 508 */     property("name", target, target.getName());
/* 509 */     property("schemaTypeRef", target, target.getSchemaTypeRef());
/* 510 */     property("attributeMembersList", target, target.getAttributeMembersList());
/* 511 */     property("elementMembersList", target, target.getElementMembersList());
/* 512 */     property("contentMember", target, target.getContentMember());
/* 513 */     property("subtypesSet", target, target.getSubtypesSet());
/* 514 */     property("parentType", target, target.getParentType());
/* 515 */     property("nillable", target, new Boolean(target.isNillable()));
/* 516 */     property("rpcWrapper", target, new Boolean(target.isRpcWrapper()));
/*     */   }
/*     */   
/*     */   protected void visitJavaArrayType(JavaArrayType target) {
/* 520 */     property("realName", target, target.getRealName());
/* 521 */     property("formalName", target, target.getFormalName());
/* 522 */     property("present", target, new Boolean(target.isPresent()));
/* 523 */     property("holder", target, new Boolean(target.isHolder()));
/* 524 */     property("holderPresent", target, new Boolean(target.isHolderPresent()));
/* 525 */     property("initString", target, target.getInitString());
/* 526 */     property("holderName", target, target.getHolderName());
/* 527 */     property("elementName", target, target.getElementName());
/* 528 */     property("elementType", target, target.getElementType());
/*     */     
/* 530 */     property("soapArrayHolderName", target, target.getSOAPArrayHolderName());
/*     */   }
/*     */   
/*     */   protected void visitPort(Port target) {
/* 534 */     property("propertiesMap", target, target.getPropertiesMap());
/* 535 */     property("operationsList", target, target.getOperationsList());
/* 536 */     property("javaInterface", target, target.getJavaInterface());
/* 537 */     property("clientHandlerChainInfo", target, target.getClientHandlerChainInfo());
/* 538 */     property("serverHandlerChainInfo", target, target.getServerHandlerChainInfo());
/* 539 */     property("SOAPVersion", target, target.getSOAPVersion());
/* 540 */     property("address", target, target.getAddress());
/* 541 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitLiteralAttributeMember(LiteralAttributeMember target) {
/* 545 */     property("javaStructureMember", target, target.getJavaStructureMember());
/* 546 */     property("required", target, new Boolean(target.isRequired()));
/* 547 */     property("type", target, target.getType());
/* 548 */     property("name", target, target.getName());
/* 549 */     property("inherited", target, new Boolean(target.isInherited()));
/*     */   }
/*     */   
/*     */   protected void visitHandlerInfo(HandlerInfo target) {
/* 553 */     property("handlerClassName", target, target.getHandlerClassName());
/* 554 */     property("headerNames", target, target.getHeaderNames());
/* 555 */     property("properties", target, target.getProperties());
/*     */   }
/*     */   
/*     */   protected void visitService(Service target) {
/* 559 */     property("propertiesMap", target, target.getPropertiesMap());
/* 560 */     property("javaInterface", target, target.getJavaInterface());
/* 561 */     property("portsList", target, target.getPortsList());
/* 562 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitSOAPStructureMember(SOAPStructureMember target) {
/* 566 */     property("inherited", target, new Boolean(target.isInherited()));
/* 567 */     property("javaStructureMember", target, target.getJavaStructureMember());
/* 568 */     property("type", target, target.getType());
/* 569 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitSOAPAttributeMember(SOAPAttributeMember target) {
/* 573 */     property("javaStructureMember", target, target.getJavaStructureMember());
/* 574 */     property("required", target, new Boolean(target.isRequired()));
/* 575 */     property("type", target, target.getType());
/* 576 */     property("name", target, target.getName());
/* 577 */     property("inherited", target, new Boolean(target.isInherited()));
/*     */   }
/*     */   
/*     */   protected void visitJavaParameter(JavaParameter target) {
/* 581 */     property("holder", target, new Boolean(target.isHolder()));
/* 582 */     property("type", target, target.getType());
/* 583 */     property("parameter", target, target.getParameter());
/* 584 */     property("name", target, target.getName());
/* 585 */     property("holderName", target, target.getHolderName());
/*     */   }
/*     */   
/*     */   protected void visitModel(Model target) {
/* 589 */     property("propertiesMap", target, target.getPropertiesMap());
/* 590 */     property("targetNamespaceURI", target, target.getTargetNamespaceURI());
/* 591 */     property("servicesList", target, target.getServicesList());
/* 592 */     property("extraTypesSet", target, target.getExtraTypesSet());
/* 593 */     property("importedDocumentsMap", target, target.getImportedDocumentsMap());
/* 594 */     property("name", target, target.getName());
/* 595 */     property("target", target, target.getSource());
/*     */   }
/*     */   
/*     */   protected void visitLiteralSimpleType(LiteralSimpleType target) {
/* 599 */     property("javaType", target, target.getJavaType());
/* 600 */     property("propertiesMap", target, target.getPropertiesMap());
/* 601 */     property("version", target, target.getVersion());
/* 602 */     property("name", target, target.getName());
/* 603 */     property("schemaTypeRef", target, target.getSchemaTypeRef());
/* 604 */     property("nillable", target, new Boolean(target.isNillable()));
/*     */   }
/*     */   
/*     */   protected void visitLiteralArrayType(LiteralArrayType target) {
/* 608 */     property("javaType", target, target.getJavaType());
/* 609 */     property("propertiesMap", target, target.getPropertiesMap());
/* 610 */     property("version", target, target.getVersion());
/* 611 */     property("name", target, target.getName());
/* 612 */     property("schemaTypeRef", target, target.getSchemaTypeRef());
/* 613 */     property("elementType", target, target.getElementType());
/* 614 */     property("nillable", target, new Boolean(target.isNillable()));
/*     */   }
/*     */   
/*     */   protected void visitLiteralListType(LiteralListType target) {
/* 618 */     property("javaType", target, target.getJavaType());
/* 619 */     property("propertiesMap", target, target.getPropertiesMap());
/* 620 */     property("version", target, target.getVersion());
/* 621 */     property("name", target, target.getName());
/* 622 */     property("schemaTypeRef", target, target.getSchemaTypeRef());
/* 623 */     property("itemType", target, target.getItemType());
/* 624 */     property("nillable", target, new Boolean(target.isNillable()));
/*     */   }
/*     */   
/*     */   protected void visitJavaEnumerationType(JavaEnumerationType target) {
/* 628 */     property("realName", target, target.getRealName());
/* 629 */     property("formalName", target, target.getFormalName());
/* 630 */     property("present", target, new Boolean(target.isPresent()));
/* 631 */     property("holder", target, new Boolean(target.isHolder()));
/* 632 */     property("holderPresent", target, new Boolean(target.isHolderPresent()));
/* 633 */     property("initString", target, target.getInitString());
/* 634 */     property("holderName", target, target.getHolderName());
/* 635 */     property("baseType", target, target.getBaseType());
/* 636 */     property("entriesList", target, target.getEntriesList());
/*     */   }
/*     */   
/*     */   protected void visitSOAPCustomType(SOAPCustomType target) {
/* 640 */     property("javaType", target, target.getJavaType());
/* 641 */     property("propertiesMap", target, target.getPropertiesMap());
/* 642 */     property("version", target, target.getVersion());
/* 643 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitLiteralFragmentType(LiteralFragmentType target) {
/* 647 */     property("javaType", target, target.getJavaType());
/* 648 */     property("propertiesMap", target, target.getPropertiesMap());
/* 649 */     property("version", target, target.getVersion());
/* 650 */     property("name", target, target.getName());
/* 651 */     property("schemaTypeRef", target, target.getSchemaTypeRef());
/* 652 */     property("nillable", target, new Boolean(target.isNillable()));
/*     */   }
/*     */   
/*     */   protected void visitSOAPArrayType(SOAPArrayType target) {
/* 656 */     property("javaType", target, target.getJavaType());
/* 657 */     property("propertiesMap", target, target.getPropertiesMap());
/* 658 */     property("version", target, target.getVersion());
/* 659 */     property("name", target, target.getName());
/* 660 */     property("elementName", target, target.getElementName());
/* 661 */     property("elementType", target, target.getElementType());
/* 662 */     property("rank", target, new Integer(target.getRank()));
/* 663 */     property("size", target, target.getSize());
/*     */   }
/*     */   
/*     */   protected void visitSOAPUnorderedStructureType(SOAPUnorderedStructureType target) {
/* 667 */     property("javaType", target, target.getJavaType());
/* 668 */     property("propertiesMap", target, target.getPropertiesMap());
/* 669 */     property("version", target, target.getVersion());
/* 670 */     property("name", target, target.getName());
/* 671 */     property("membersList", target, target.getMembersList());
/* 672 */     property("subtypesSet", target, target.getSubtypesSet());
/* 673 */     property("parentType", target, target.getParentType());
/*     */   }
/*     */   
/*     */   protected void visitMessage(Message target) {
/* 677 */     property("propertiesMap", target, target.getPropertiesMap());
/* 678 */     property("bodyBlocksMap", target, target.getBodyBlocksMap());
/* 679 */     property("headerBlocksMap", target, target.getHeaderBlocksMap());
/* 680 */     property("parametersList", target, target.getParametersList());
/*     */   }
/*     */   
/*     */   protected void visitHeaderFault(HeaderFault target) {
/* 684 */     property("propertiesMap", target, target.getPropertiesMap());
/* 685 */     property("block", target, target.getBlock());
/* 686 */     property("javaException", target, target.getJavaException());
/* 687 */     property("parentFault", target, target.getParentFault());
/* 688 */     property("subfaultsSet", target, target.getSubfaultsSet());
/* 689 */     property("name", target, target.getName());
/* 690 */     property("elementName", target, target.getElementName());
/* 691 */     property("message", target, target.getMessage());
/* 692 */     property("part", target, target.getPart());
/*     */   }
/*     */   
/*     */   protected void visitJavaMethod(JavaMethod target) {
/* 696 */     property("parametersList", target, target.getParametersList());
/* 697 */     property("exceptionsList", target, target.getExceptionsList());
/* 698 */     property("returnType", target, target.getReturnType());
/* 699 */     property("declaringClass", target, target.getDeclaringClass());
/* 700 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitSOAPAnyType(SOAPAnyType target) {
/* 704 */     property("javaType", target, target.getJavaType());
/* 705 */     property("propertiesMap", target, target.getPropertiesMap());
/* 706 */     property("version", target, target.getVersion());
/* 707 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitSOAPSimpleType(SOAPSimpleType target) {
/* 711 */     property("javaType", target, target.getJavaType());
/* 712 */     property("propertiesMap", target, target.getPropertiesMap());
/* 713 */     property("version", target, target.getVersion());
/* 714 */     property("name", target, target.getName());
/* 715 */     property("referenceable", target, new Boolean(target.isReferenceable()));
/* 716 */     property("schemaTypeRef", target, target.getSchemaTypeRef());
/*     */   }
/*     */   
/*     */   protected void visitSOAPOrderedStructureType(SOAPOrderedStructureType target) {
/* 720 */     property("javaType", target, target.getJavaType());
/* 721 */     property("propertiesMap", target, target.getPropertiesMap());
/* 722 */     property("version", target, target.getVersion());
/* 723 */     property("name", target, target.getName());
/* 724 */     property("membersList", target, target.getMembersList());
/* 725 */     property("attributeMembersList", target, target.getAttributeMembersList());
/* 726 */     property("subtypesSet", target, target.getSubtypesSet());
/* 727 */     property("parentType", target, target.getParentType());
/*     */   }
/*     */   
/*     */   protected void visitRPCResponseStructureType(RPCResponseStructureType target) {
/* 731 */     property("javaType", target, target.getJavaType());
/* 732 */     property("propertiesMap", target, target.getPropertiesMap());
/* 733 */     property("version", target, target.getVersion());
/* 734 */     property("name", target, target.getName());
/* 735 */     property("membersList", target, target.getMembersList());
/* 736 */     property("attributeMembersList", target, target.getAttributeMembersList());
/* 737 */     property("subtypesSet", target, target.getSubtypesSet());
/* 738 */     property("parentType", target, target.getParentType());
/*     */   }
/*     */   
/*     */   protected void visitParameter(Parameter target) {
/* 742 */     property("propertiesMap", target, target.getPropertiesMap());
/* 743 */     property("block", target, target.getBlock());
/* 744 */     property("javaParameter", target, target.getJavaParameter());
/* 745 */     property("linkedParameter", target, target.getLinkedParameter());
/* 746 */     property("type", target, target.getType());
/* 747 */     property("embedded", target, new Boolean(target.isEmbedded()));
/* 748 */     property("name", target, target.getName());
/*     */   }
/*     */   
/*     */   protected void visitTypeMappingInfo(TypeMappingInfo target) {
/* 752 */     property("encodingStyle", target, target.getEncodingStyle());
/* 753 */     property("XMLType", target, target.getXMLType());
/* 754 */     property("javaTypeName", target, target.getJavaTypeName());
/* 755 */     property("serializerFactoryName", target, target.getSerializerFactoryName());
/* 756 */     property("deserializerFactoryName", target, target.getDeserializerFactoryName());
/*     */   }
/*     */   
/*     */   protected void visitFault(Fault target) {
/* 760 */     property("propertiesMap", target, target.getPropertiesMap());
/* 761 */     property("block", target, target.getBlock());
/* 762 */     property("javaException", target, target.getJavaException());
/* 763 */     property("parentFault", target, target.getParentFault());
/* 764 */     property("subfaultsSet", target, target.getSubfaultsSet());
/* 765 */     property("name", target, target.getName());
/* 766 */     property("elementName", target, target.getElementName());
/* 767 */     property("javaMemberName", target, target.getJavaMemberName());
/*     */   }
/*     */   
/*     */   protected void visitLiteralContentMember(LiteralContentMember target) {
/* 771 */     property("javaStructureMember", target, target.getJavaStructureMember());
/* 772 */     property("type", target, target.getType());
/*     */   }
/*     */   
/*     */   protected void visitSOAPEnumerationType(SOAPEnumerationType target) {
/* 776 */     property("javaType", target, target.getJavaType());
/* 777 */     property("propertiesMap", target, target.getPropertiesMap());
/* 778 */     property("version", target, target.getVersion());
/* 779 */     property("name", target, target.getName());
/* 780 */     property("baseType", target, target.getBaseType());
/*     */   }
/*     */   
/*     */   protected void visitJavaType(JavaType target) {
/* 784 */     property("realName", target, target.getRealName());
/* 785 */     property("formalName", target, target.getFormalName());
/* 786 */     property("present", target, new Boolean(target.isPresent()));
/* 787 */     property("holder", target, new Boolean(target.isHolder()));
/* 788 */     property("holderPresent", target, new Boolean(target.isHolderPresent()));
/* 789 */     property("initString", target, target.getInitString());
/* 790 */     property("holderName", target, target.getHolderName());
/*     */   }
/*     */   
/*     */   protected void visitModelObject(ModelObject target) {
/* 794 */     property("propertiesMap", target, target.getPropertiesMap());
/*     */   }
/*     */   
/*     */   protected void visitLiteralIDType(LiteralIDType target) {
/* 798 */     property("javaType", target, target.getJavaType());
/* 799 */     property("propertiesMap", target, target.getPropertiesMap());
/* 800 */     property("version", target, target.getVersion());
/* 801 */     property("name", target, target.getName());
/* 802 */     property("schemaTypeRef", target, target.getSchemaTypeRef());
/* 803 */     property("nillable", target, new Boolean(target.isNillable()));
/* 804 */     property("resolveIDREF", target, new Boolean(target.getResolveIDREF()));
/*     */   }
/*     */   
/*     */   protected void visitSOAPListType(SOAPListType target) {
/* 808 */     property("javaType", target, target.getJavaType());
/* 809 */     property("propertiesMap", target, target.getPropertiesMap());
/* 810 */     property("version", target, target.getVersion());
/* 811 */     property("name", target, target.getName());
/* 812 */     property("itemType", target, target.getItemType());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\exporter\ModelExporter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */