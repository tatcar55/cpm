/*      */ package com.sun.xml.bind.v2.runtime;
/*      */ 
/*      */ import com.sun.istack.SAXException2;
/*      */ import com.sun.xml.bind.CycleRecoverable;
/*      */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*      */ import com.sun.xml.bind.util.ValidationEventLocatorExImpl;
/*      */ import com.sun.xml.bind.v2.runtime.output.MTOMXmlOutput;
/*      */ import com.sun.xml.bind.v2.runtime.output.NamespaceContextImpl;
/*      */ import com.sun.xml.bind.v2.runtime.output.Pcdata;
/*      */ import com.sun.xml.bind.v2.runtime.output.XmlOutput;
/*      */ import com.sun.xml.bind.v2.runtime.property.Property;
/*      */ import com.sun.xml.bind.v2.runtime.unmarshaller.Base64Data;
/*      */ import com.sun.xml.bind.v2.runtime.unmarshaller.IntData;
/*      */ import com.sun.xml.bind.v2.util.CollisionCheckStack;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.activation.MimeType;
/*      */ import javax.xml.bind.DatatypeConverter;
/*      */ import javax.xml.bind.JAXBException;
/*      */ import javax.xml.bind.Marshaller;
/*      */ import javax.xml.bind.ValidationEvent;
/*      */ import javax.xml.bind.ValidationEventHandler;
/*      */ import javax.xml.bind.ValidationEventLocator;
/*      */ import javax.xml.bind.annotation.DomHandler;
/*      */ import javax.xml.bind.annotation.XmlNs;
/*      */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*      */ import javax.xml.bind.helpers.NotIdentifiableEventImpl;
/*      */ import javax.xml.bind.helpers.ValidationEventImpl;
/*      */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.sax.SAXResult;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class XMLSerializer
/*      */   extends Coordinator
/*      */ {
/*      */   public final JAXBContextImpl grammar;
/*      */   private XmlOutput out;
/*      */   public final NameList nameList;
/*      */   public final int[] knownUri2prefixIndexMap;
/*      */   private final NamespaceContextImpl nsContext;
/*      */   private NamespaceContextImpl.Element nse;
/*  149 */   ThreadLocal<Property> currentProperty = new ThreadLocal<Property>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean textHasAlreadyPrinted = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean seenRoot = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private final MarshallerImpl marshaller;
/*      */ 
/*      */ 
/*      */   
/*  166 */   private final Set<Object> idReferencedObjects = new HashSet();
/*      */ 
/*      */   
/*  169 */   private final Set<Object> objectsWithId = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  175 */   private final CollisionCheckStack<Object> cycleDetectionStack = new CollisionCheckStack();
/*      */ 
/*      */ 
/*      */   
/*      */   private String schemaLocation;
/*      */ 
/*      */   
/*      */   private String noNsSchemaLocation;
/*      */ 
/*      */   
/*      */   private Transformer identityTransformer;
/*      */ 
/*      */   
/*      */   private ContentHandlerAdaptor contentHandlerAdapter;
/*      */ 
/*      */   
/*      */   private boolean fragment;
/*      */ 
/*      */   
/*      */   private Base64Data base64Data;
/*      */ 
/*      */   
/*  197 */   private final IntData intData = new IntData();
/*      */   public AttachmentMarshaller attachmentMarshaller;
/*      */   private MimeType expectedMimeType;
/*      */   
/*      */   XMLSerializer(MarshallerImpl _owner) {
/*  202 */     this.marshaller = _owner;
/*  203 */     this.grammar = this.marshaller.context;
/*  204 */     this.nsContext = new NamespaceContextImpl(this);
/*  205 */     this.nameList = this.marshaller.context.nameList;
/*  206 */     this.knownUri2prefixIndexMap = new int[this.nameList.namespaceURIs.length];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean inlineBinaryFlag;
/*      */   
/*      */   private QName schemaType;
/*      */ 
/*      */   
/*      */   public Base64Data getCachedBase64DataInstance() {
/*  217 */     return new Base64Data();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getIdFromObject(Object identifiableObject) throws SAXException, JAXBException {
/*  224 */     return this.grammar.getBeanInfo(identifiableObject, true).getId(identifiableObject, this);
/*      */   }
/*      */   
/*      */   private void handleMissingObjectError(String fieldName) throws SAXException, IOException, XMLStreamException {
/*  228 */     reportMissingObjectError(fieldName);
/*      */ 
/*      */     
/*  231 */     endNamespaceDecls((Object)null);
/*  232 */     endAttributes();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportError(ValidationEvent ve) throws SAXException {
/*      */     ValidationEventHandler handler;
/*      */     try {
/*  240 */       handler = this.marshaller.getEventHandler();
/*  241 */     } catch (JAXBException e) {
/*  242 */       throw new SAXException2(e);
/*      */     } 
/*      */     
/*  245 */     if (!handler.handleEvent(ve)) {
/*  246 */       if (ve.getLinkedException() instanceof Exception) {
/*  247 */         throw new SAXException2((Exception)ve.getLinkedException());
/*      */       }
/*  249 */       throw new SAXException2(ve.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void reportError(String fieldName, Throwable t) throws SAXException {
/*  260 */     ValidationEvent ve = new ValidationEventImpl(1, t.getMessage(), getCurrentLocation(fieldName), t);
/*      */     
/*  262 */     reportError(ve);
/*      */   }
/*      */   
/*      */   public void startElement(Name tagName, Object outerPeer) {
/*  266 */     startElement();
/*  267 */     this.nse.setTagName(tagName, outerPeer);
/*      */   }
/*      */   
/*      */   public void startElement(String nsUri, String localName, String preferredPrefix, Object outerPeer) {
/*  271 */     startElement();
/*  272 */     int idx = this.nsContext.declareNsUri(nsUri, preferredPrefix, false);
/*  273 */     this.nse.setTagName(idx, localName, outerPeer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElementForce(String nsUri, String localName, String forcedPrefix, Object outerPeer) {
/*  281 */     startElement();
/*  282 */     int idx = this.nsContext.force(nsUri, forcedPrefix);
/*  283 */     this.nse.setTagName(idx, localName, outerPeer);
/*      */   }
/*      */   
/*      */   public void endNamespaceDecls(Object innerPeer) throws IOException, XMLStreamException {
/*  287 */     this.nsContext.collectionMode = false;
/*  288 */     this.nse.startElement(this.out, innerPeer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endAttributes() throws SAXException, IOException, XMLStreamException {
/*  296 */     if (!this.seenRoot) {
/*  297 */       this.seenRoot = true;
/*  298 */       if (this.schemaLocation != null || this.noNsSchemaLocation != null) {
/*  299 */         int p = this.nsContext.getPrefixIndex("http://www.w3.org/2001/XMLSchema-instance");
/*  300 */         if (this.schemaLocation != null)
/*  301 */           this.out.attribute(p, "schemaLocation", this.schemaLocation); 
/*  302 */         if (this.noNsSchemaLocation != null) {
/*  303 */           this.out.attribute(p, "noNamespaceSchemaLocation", this.noNsSchemaLocation);
/*      */         }
/*      */       } 
/*      */     } 
/*  307 */     this.out.endStartTag();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement() throws SAXException, IOException, XMLStreamException {
/*  315 */     this.nse.endElement(this.out);
/*  316 */     this.nse = this.nse.pop();
/*  317 */     this.textHasAlreadyPrinted = false;
/*      */   }
/*      */   
/*      */   public void leafElement(Name tagName, String data, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  321 */     if (this.seenRoot) {
/*  322 */       this.textHasAlreadyPrinted = false;
/*  323 */       this.nse = this.nse.push();
/*  324 */       this.out.beginStartTag(tagName);
/*  325 */       this.out.endStartTag();
/*  326 */       if (data != null)
/*      */         try {
/*  328 */           this.out.text(data, false);
/*  329 */         } catch (IllegalArgumentException e) {
/*  330 */           throw new IllegalArgumentException(Messages.ILLEGAL_CONTENT.format(new Object[] { fieldName, e.getMessage() }));
/*      */         }  
/*  332 */       this.out.endTag(tagName);
/*  333 */       this.nse = this.nse.pop();
/*      */     }
/*      */     else {
/*      */       
/*  337 */       startElement(tagName, (Object)null);
/*  338 */       endNamespaceDecls((Object)null);
/*  339 */       endAttributes();
/*      */       try {
/*  341 */         this.out.text(data, false);
/*  342 */       } catch (IllegalArgumentException e) {
/*  343 */         throw new IllegalArgumentException(Messages.ILLEGAL_CONTENT.format(new Object[] { fieldName, e.getMessage() }));
/*      */       } 
/*  345 */       endElement();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void leafElement(Name tagName, Pcdata data, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  350 */     if (this.seenRoot) {
/*  351 */       this.textHasAlreadyPrinted = false;
/*  352 */       this.nse = this.nse.push();
/*  353 */       this.out.beginStartTag(tagName);
/*  354 */       this.out.endStartTag();
/*  355 */       if (data != null)
/*  356 */         this.out.text(data, false); 
/*  357 */       this.out.endTag(tagName);
/*  358 */       this.nse = this.nse.pop();
/*      */     }
/*      */     else {
/*      */       
/*  362 */       startElement(tagName, (Object)null);
/*  363 */       endNamespaceDecls((Object)null);
/*  364 */       endAttributes();
/*  365 */       this.out.text(data, false);
/*  366 */       endElement();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void leafElement(Name tagName, int data, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  371 */     this.intData.reset(data);
/*  372 */     leafElement(tagName, (Pcdata)this.intData, fieldName);
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
/*      */   public void text(String text, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  401 */     if (text == null) {
/*  402 */       reportMissingObjectError(fieldName);
/*      */       
/*      */       return;
/*      */     } 
/*  406 */     this.out.text(text, this.textHasAlreadyPrinted);
/*  407 */     this.textHasAlreadyPrinted = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void text(Pcdata text, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  416 */     if (text == null) {
/*  417 */       reportMissingObjectError(fieldName);
/*      */       
/*      */       return;
/*      */     } 
/*  421 */     this.out.text(text, this.textHasAlreadyPrinted);
/*  422 */     this.textHasAlreadyPrinted = true;
/*      */   }
/*      */   
/*      */   public void attribute(String uri, String local, String value) throws SAXException {
/*      */     int prefix;
/*  427 */     if (uri.length() == 0) {
/*      */       
/*  429 */       prefix = -1;
/*      */     } else {
/*  431 */       prefix = this.nsContext.getPrefixIndex(uri);
/*      */     } 
/*      */     
/*      */     try {
/*  435 */       this.out.attribute(prefix, local, value);
/*  436 */     } catch (IOException e) {
/*  437 */       throw new SAXException2(e);
/*  438 */     } catch (XMLStreamException e) {
/*  439 */       throw new SAXException2(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void attribute(Name name, CharSequence value) throws IOException, XMLStreamException {
/*  446 */     this.out.attribute(name, value.toString());
/*      */   }
/*      */   
/*      */   public NamespaceContext2 getNamespaceContext() {
/*  450 */     return (NamespaceContext2)this.nsContext;
/*      */   }
/*      */ 
/*      */   
/*      */   public String onID(Object owner, String value) {
/*  455 */     this.objectsWithId.add(owner);
/*  456 */     return value;
/*      */   }
/*      */   
/*      */   public String onIDREF(Object obj) throws SAXException {
/*      */     String id;
/*      */     try {
/*  462 */       id = getIdFromObject(obj);
/*  463 */     } catch (JAXBException e) {
/*  464 */       reportError((String)null, e);
/*  465 */       return null;
/*      */     } 
/*  467 */     this.idReferencedObjects.add(obj);
/*  468 */     if (id == null) {
/*  469 */       reportError(new NotIdentifiableEventImpl(1, Messages.NOT_IDENTIFIABLE.format(new Object[0]), new ValidationEventLocatorImpl(obj)));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  474 */     return id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void childAsRoot(Object obj) throws JAXBException, IOException, SAXException, XMLStreamException {
/*  482 */     JaxBeanInfo<Object> beanInfo = this.grammar.getBeanInfo(obj, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  487 */     this.cycleDetectionStack.pushNocheck(obj);
/*      */     
/*  489 */     boolean lookForLifecycleMethods = beanInfo.lookForLifecycleMethods();
/*  490 */     if (lookForLifecycleMethods) {
/*  491 */       fireBeforeMarshalEvents(beanInfo, obj);
/*      */     }
/*      */     
/*  494 */     beanInfo.serializeRoot(obj, this);
/*      */     
/*  496 */     if (lookForLifecycleMethods) {
/*  497 */       fireAfterMarshalEvents(beanInfo, obj);
/*      */     }
/*      */     
/*  500 */     this.cycleDetectionStack.pop();
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
/*      */   private Object pushObject(Object obj, String fieldName) throws SAXException {
/*  516 */     if (!this.cycleDetectionStack.push(obj)) {
/*  517 */       return obj;
/*      */     }
/*      */     
/*  520 */     if (obj instanceof CycleRecoverable) {
/*  521 */       obj = ((CycleRecoverable)obj).onCycleDetected(new CycleRecoverable.Context() {
/*      */             public Marshaller getMarshaller() {
/*  523 */               return XMLSerializer.this.marshaller;
/*      */             }
/*      */           });
/*  526 */       if (obj != null) {
/*      */ 
/*      */ 
/*      */         
/*  530 */         this.cycleDetectionStack.pop();
/*  531 */         return pushObject(obj, fieldName);
/*      */       } 
/*  533 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  537 */     reportError(new ValidationEventImpl(1, Messages.CYCLE_IN_MARSHALLER.format(new Object[] { this.cycleDetectionStack.getCycleString() }, ), getCurrentLocation(fieldName), null));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  542 */     return null;
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
/*      */   public final void childAsSoleContent(Object child, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  561 */     if (child == null) {
/*  562 */       handleMissingObjectError(fieldName);
/*      */     } else {
/*  564 */       JaxBeanInfo<Object> beanInfo; child = pushObject(child, fieldName);
/*  565 */       if (child == null) {
/*      */         
/*  567 */         endNamespaceDecls((Object)null);
/*  568 */         endAttributes();
/*  569 */         this.cycleDetectionStack.pop();
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  574 */         beanInfo = this.grammar.getBeanInfo(child, true);
/*  575 */       } catch (JAXBException e) {
/*  576 */         reportError(fieldName, e);
/*      */         
/*  578 */         endNamespaceDecls((Object)null);
/*  579 */         endAttributes();
/*  580 */         this.cycleDetectionStack.pop();
/*      */         
/*      */         return;
/*      */       } 
/*  584 */       boolean lookForLifecycleMethods = beanInfo.lookForLifecycleMethods();
/*  585 */       if (lookForLifecycleMethods) {
/*  586 */         fireBeforeMarshalEvents(beanInfo, child);
/*      */       }
/*      */       
/*  589 */       beanInfo.serializeURIs(child, this);
/*  590 */       endNamespaceDecls(child);
/*  591 */       beanInfo.serializeAttributes(child, this);
/*  592 */       endAttributes();
/*  593 */       beanInfo.serializeBody(child, this);
/*      */       
/*  595 */       if (lookForLifecycleMethods) {
/*  596 */         fireAfterMarshalEvents(beanInfo, child);
/*      */       }
/*      */       
/*  599 */       this.cycleDetectionStack.pop();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void childAsXsiType(Object child, String fieldName, JaxBeanInfo<Object> expected, boolean nillable) throws SAXException, IOException, XMLStreamException {
/*  628 */     if (child == null) {
/*  629 */       handleMissingObjectError(fieldName);
/*      */     } else {
/*  631 */       child = pushObject(child, fieldName);
/*  632 */       if (child == null) {
/*  633 */         endNamespaceDecls((Object)null);
/*  634 */         endAttributes();
/*      */         
/*      */         return;
/*      */       } 
/*  638 */       boolean asExpected = (child.getClass() == expected.jaxbType);
/*  639 */       JaxBeanInfo<Object> actual = expected;
/*  640 */       QName actualTypeName = null;
/*      */       
/*  642 */       if (asExpected && actual.lookForLifecycleMethods()) {
/*  643 */         fireBeforeMarshalEvents(actual, child);
/*      */       }
/*      */       
/*  646 */       if (!asExpected) {
/*      */         try {
/*  648 */           actual = this.grammar.getBeanInfo(child, true);
/*  649 */           if (actual.lookForLifecycleMethods()) {
/*  650 */             fireBeforeMarshalEvents(actual, child);
/*      */           }
/*  652 */         } catch (JAXBException e) {
/*  653 */           reportError(fieldName, e);
/*  654 */           endNamespaceDecls((Object)null);
/*  655 */           endAttributes();
/*      */           return;
/*      */         } 
/*  658 */         if (actual == expected) {
/*  659 */           asExpected = true;
/*      */         } else {
/*  661 */           actualTypeName = actual.getTypeName(child);
/*  662 */           if (actualTypeName == null) {
/*  663 */             reportError(new ValidationEventImpl(1, Messages.SUBSTITUTED_BY_ANONYMOUS_TYPE.format(new Object[] { expected.jaxbType.getName(), child.getClass().getName(), actual.jaxbType.getName() }, ), getCurrentLocation(fieldName)));
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */             
/*  672 */             getNamespaceContext().declareNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi", true);
/*  673 */             getNamespaceContext().declareNamespace(actualTypeName.getNamespaceURI(), null, false);
/*      */           } 
/*      */         } 
/*      */       } 
/*  677 */       actual.serializeURIs(child, this);
/*      */       
/*  679 */       if (nillable) {
/*  680 */         getNamespaceContext().declareNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi", true);
/*      */       }
/*      */       
/*  683 */       endNamespaceDecls(child);
/*  684 */       if (!asExpected) {
/*  685 */         attribute("http://www.w3.org/2001/XMLSchema-instance", "type", DatatypeConverter.printQName(actualTypeName, getNamespaceContext()));
/*      */       }
/*      */ 
/*      */       
/*  689 */       actual.serializeAttributes(child, this);
/*  690 */       boolean nilDefined = actual.isNilIncluded();
/*  691 */       if (nillable && !nilDefined) {
/*  692 */         attribute("http://www.w3.org/2001/XMLSchema-instance", "nil", "true");
/*      */       }
/*      */       
/*  695 */       endAttributes();
/*  696 */       actual.serializeBody(child, this);
/*      */       
/*  698 */       if (actual.lookForLifecycleMethods()) {
/*  699 */         fireAfterMarshalEvents(actual, child);
/*      */       }
/*      */       
/*  702 */       this.cycleDetectionStack.pop();
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
/*      */   private void fireAfterMarshalEvents(JaxBeanInfo beanInfo, Object currentTarget) {
/*  717 */     if (beanInfo.hasAfterMarshalMethod()) {
/*  718 */       Method m = (beanInfo.getLifecycleMethods()).afterMarshal;
/*  719 */       fireMarshalEvent(currentTarget, m);
/*      */     } 
/*      */ 
/*      */     
/*  723 */     Marshaller.Listener externalListener = this.marshaller.getListener();
/*  724 */     if (externalListener != null) {
/*  725 */       externalListener.afterMarshal(currentTarget);
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
/*      */   private void fireBeforeMarshalEvents(JaxBeanInfo beanInfo, Object currentTarget) {
/*  741 */     if (beanInfo.hasBeforeMarshalMethod()) {
/*  742 */       Method m = (beanInfo.getLifecycleMethods()).beforeMarshal;
/*  743 */       fireMarshalEvent(currentTarget, m);
/*      */     } 
/*      */ 
/*      */     
/*  747 */     Marshaller.Listener externalListener = this.marshaller.getListener();
/*  748 */     if (externalListener != null) {
/*  749 */       externalListener.beforeMarshal(currentTarget);
/*      */     }
/*      */   }
/*      */   
/*      */   private void fireMarshalEvent(Object target, Method m) {
/*      */     try {
/*  755 */       m.invoke(target, new Object[] { this.marshaller });
/*  756 */     } catch (Exception e) {
/*      */       
/*  758 */       throw new IllegalStateException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void attWildcardAsURIs(Map<QName, String> attributes, String fieldName) {
/*  763 */     if (attributes == null)
/*  764 */       return;  for (Map.Entry<QName, String> e : attributes.entrySet()) {
/*  765 */       QName n = e.getKey();
/*  766 */       String nsUri = n.getNamespaceURI();
/*  767 */       if (nsUri.length() > 0) {
/*  768 */         String p = n.getPrefix();
/*  769 */         if (p.length() == 0) p = null; 
/*  770 */         this.nsContext.declareNsUri(nsUri, p, true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void attWildcardAsAttributes(Map<QName, String> attributes, String fieldName) throws SAXException {
/*  776 */     if (attributes == null)
/*  777 */       return;  for (Map.Entry<QName, String> e : attributes.entrySet()) {
/*  778 */       QName n = e.getKey();
/*  779 */       attribute(n.getNamespaceURI(), n.getLocalPart(), e.getValue());
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
/*      */   public final void writeXsiNilTrue() throws SAXException, IOException, XMLStreamException {
/*  794 */     getNamespaceContext().declareNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi", true);
/*  795 */     endNamespaceDecls((Object)null);
/*  796 */     attribute("http://www.w3.org/2001/XMLSchema-instance", "nil", "true");
/*  797 */     endAttributes();
/*      */   }
/*      */   
/*      */   public <E> void writeDom(E element, DomHandler<E, ?> domHandler, Object parentBean, String fieldName) throws SAXException {
/*  801 */     Source source = domHandler.marshal(element, this);
/*  802 */     if (this.contentHandlerAdapter == null)
/*  803 */       this.contentHandlerAdapter = new ContentHandlerAdaptor(this); 
/*      */     try {
/*  805 */       getIdentityTransformer().transform(source, new SAXResult(this.contentHandlerAdapter));
/*  806 */     } catch (TransformerException e) {
/*  807 */       reportError(fieldName, e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Transformer getIdentityTransformer() {
/*  812 */     if (this.identityTransformer == null)
/*  813 */       this.identityTransformer = JAXBContextImpl.createTransformer(this.grammar.disableSecurityProcessing); 
/*  814 */     return this.identityTransformer;
/*      */   }
/*      */   
/*      */   public void setPrefixMapper(NamespacePrefixMapper prefixMapper) {
/*  818 */     this.nsContext.setPrefixMapper(prefixMapper);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startDocument(XmlOutput out, boolean fragment, String schemaLocation, String noNsSchemaLocation) throws IOException, SAXException, XMLStreamException {
/*      */     MTOMXmlOutput mTOMXmlOutput;
/*  830 */     setThreadAffinity();
/*  831 */     pushCoordinator();
/*  832 */     this.nsContext.reset();
/*  833 */     this.nse = this.nsContext.getCurrent();
/*  834 */     if (this.attachmentMarshaller != null && this.attachmentMarshaller.isXOPPackage())
/*  835 */       mTOMXmlOutput = new MTOMXmlOutput(out); 
/*  836 */     this.out = (XmlOutput)mTOMXmlOutput;
/*  837 */     this.objectsWithId.clear();
/*  838 */     this.idReferencedObjects.clear();
/*  839 */     this.textHasAlreadyPrinted = false;
/*  840 */     this.seenRoot = false;
/*  841 */     this.schemaLocation = schemaLocation;
/*  842 */     this.noNsSchemaLocation = noNsSchemaLocation;
/*  843 */     this.fragment = fragment;
/*  844 */     this.inlineBinaryFlag = false;
/*  845 */     this.expectedMimeType = null;
/*  846 */     this.cycleDetectionStack.reset();
/*      */     
/*  848 */     mTOMXmlOutput.startDocument(this, fragment, this.knownUri2prefixIndexMap, this.nsContext);
/*      */   }
/*      */   
/*      */   public void endDocument() throws IOException, SAXException, XMLStreamException {
/*  852 */     this.out.endDocument(this.fragment);
/*      */   }
/*      */   
/*      */   public void close() {
/*  856 */     this.out = null;
/*  857 */     clearCurrentProperty();
/*  858 */     popCoordinator();
/*  859 */     resetThreadAffinity();
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
/*      */   public void addInscopeBinding(String nsUri, String prefix) {
/*  872 */     this.nsContext.put(nsUri, prefix);
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
/*      */   public String getXMIMEContentType() {
/*  886 */     String v = this.grammar.getXMIMEContentType(this.cycleDetectionStack.peek());
/*  887 */     if (v != null) return v;
/*      */ 
/*      */     
/*  890 */     if (this.expectedMimeType != null) {
/*  891 */       return this.expectedMimeType.toString();
/*      */     }
/*  893 */     return null;
/*      */   }
/*      */   
/*      */   private void startElement() {
/*  897 */     this.nse = this.nse.push();
/*      */     
/*  899 */     if (!this.seenRoot) {
/*      */       
/*  901 */       if (this.grammar.getXmlNsSet() != null) {
/*  902 */         for (XmlNs xmlNs : this.grammar.getXmlNsSet()) {
/*  903 */           this.nsContext.declareNsUri(xmlNs.namespaceURI(), (xmlNs.prefix() == null) ? "" : xmlNs.prefix(), (xmlNs.prefix() != null));
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  911 */       String[] knownUris = this.nameList.namespaceURIs;
/*  912 */       for (int i = 0; i < knownUris.length; i++) {
/*  913 */         this.knownUri2prefixIndexMap[i] = this.nsContext.declareNsUri(knownUris[i], null, this.nameList.nsUriCannotBeDefaulted[i]);
/*      */       }
/*      */ 
/*      */       
/*  917 */       String[] uris = this.nsContext.getPrefixMapper().getPreDeclaredNamespaceUris();
/*  918 */       if (uris != null)
/*  919 */         for (String uri : uris) {
/*  920 */           if (uri != null) {
/*  921 */             this.nsContext.declareNsUri(uri, null, false);
/*      */           }
/*      */         }  
/*  924 */       String[] pairs = this.nsContext.getPrefixMapper().getPreDeclaredNamespaceUris2();
/*  925 */       if (pairs != null) {
/*  926 */         for (int j = 0; j < pairs.length; j += 2) {
/*  927 */           String prefix = pairs[j];
/*  928 */           String nsUri = pairs[j + 1];
/*  929 */           if (prefix != null && nsUri != null)
/*      */           {
/*      */ 
/*      */             
/*  933 */             this.nsContext.put(nsUri, prefix);
/*      */           }
/*      */         } 
/*      */       }
/*  937 */       if (this.schemaLocation != null || this.noNsSchemaLocation != null) {
/*  938 */         this.nsContext.declareNsUri("http://www.w3.org/2001/XMLSchema-instance", "xsi", true);
/*      */       }
/*      */     } 
/*      */     
/*  942 */     this.nsContext.collectionMode = true;
/*  943 */     this.textHasAlreadyPrinted = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MimeType setExpectedMimeType(MimeType expectedMimeType) {
/*  953 */     MimeType old = this.expectedMimeType;
/*  954 */     this.expectedMimeType = expectedMimeType;
/*  955 */     return old;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setInlineBinaryFlag(boolean value) {
/*  964 */     boolean old = this.inlineBinaryFlag;
/*  965 */     this.inlineBinaryFlag = value;
/*  966 */     return old;
/*      */   }
/*      */   
/*      */   public boolean getInlineBinaryFlag() {
/*  970 */     return this.inlineBinaryFlag;
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
/*      */   public QName setSchemaType(QName st) {
/*  985 */     QName old = this.schemaType;
/*  986 */     this.schemaType = st;
/*  987 */     return old;
/*      */   }
/*      */   
/*      */   public QName getSchemaType() {
/*  991 */     return this.schemaType;
/*      */   }
/*      */   
/*      */   public void setObjectIdentityCycleDetection(boolean val) {
/*  995 */     this.cycleDetectionStack.setUseIdentity(val);
/*      */   }
/*      */   public boolean getObjectIdentityCycleDetection() {
/*  998 */     return this.cycleDetectionStack.getUseIdentity();
/*      */   }
/*      */ 
/*      */   
/*      */   void reconcileID() throws SAXException {
/* 1003 */     this.idReferencedObjects.removeAll(this.objectsWithId);
/*      */     
/* 1005 */     for (Object idObj : this.idReferencedObjects) {
/*      */       try {
/* 1007 */         String id = getIdFromObject(idObj);
/* 1008 */         reportError(new NotIdentifiableEventImpl(1, Messages.DANGLING_IDREF.format(new Object[] { id }, ), new ValidationEventLocatorImpl(idObj)));
/*      */ 
/*      */       
/*      */       }
/* 1012 */       catch (JAXBException e) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1018 */     this.idReferencedObjects.clear();
/* 1019 */     this.objectsWithId.clear();
/*      */   }
/*      */   
/*      */   public boolean handleError(Exception e) {
/* 1023 */     return handleError(e, this.cycleDetectionStack.peek(), (String)null);
/*      */   }
/*      */   
/*      */   public boolean handleError(Exception e, Object source, String fieldName) {
/* 1027 */     return handleEvent(new ValidationEventImpl(1, e.getMessage(), (ValidationEventLocator)new ValidationEventLocatorExImpl(source, fieldName), e));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleEvent(ValidationEvent event) {
/*      */     try {
/* 1037 */       return this.marshaller.getEventHandler().handleEvent(event);
/* 1038 */     } catch (JAXBException e) {
/*      */       
/* 1040 */       throw new Error(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void reportMissingObjectError(String fieldName) throws SAXException {
/* 1045 */     reportError(new ValidationEventImpl(1, Messages.MISSING_OBJECT.format(new Object[] { fieldName }, ), getCurrentLocation(fieldName), new NullPointerException()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void errorMissingId(Object obj) throws SAXException {
/* 1056 */     reportError(new ValidationEventImpl(1, Messages.MISSING_ID.format(new Object[] { obj }, ), new ValidationEventLocatorImpl(obj)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValidationEventLocator getCurrentLocation(String fieldName) {
/* 1063 */     return (ValidationEventLocator)new ValidationEventLocatorExImpl(this.cycleDetectionStack.peek(), fieldName);
/*      */   }
/*      */   
/*      */   protected ValidationEventLocator getLocation() {
/* 1067 */     return getCurrentLocation((String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property getCurrentProperty() {
/* 1075 */     return this.currentProperty.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearCurrentProperty() {
/* 1082 */     if (this.currentProperty != null) {
/* 1083 */       this.currentProperty.remove();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLSerializer getInstance() {
/* 1092 */     return (XMLSerializer)Coordinator._getInstance();
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\XMLSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */