/*     */ package com.sun.xml.ws.fault;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.model.ExceptionType;
/*     */ import com.sun.xml.ws.encoding.soap.SerializationException;
/*     */ import com.sun.xml.ws.message.FaultMessage;
/*     */ import com.sun.xml.ws.message.jaxb.JAXBMessage;
/*     */ import com.sun.xml.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.ws.util.DOMUtil;
/*     */ import com.sun.xml.ws.util.StringUtils;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.DetailEntry;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.ws.ProtocolException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SOAPFaultBuilder
/*     */ {
/*     */   private static final JAXBContext JAXB_CONTEXT;
/*     */   
/*     */   abstract DetailType getDetail();
/*     */   
/*     */   abstract void setDetail(DetailType paramDetailType);
/*     */   
/*     */   @XmlTransient
/*     */   @Nullable
/*     */   public QName getFirstDetailEntryName() {
/*  98 */     DetailType dt = getDetail();
/*  99 */     if (dt != null) {
/* 100 */       Node entry = dt.getDetail(0);
/* 101 */       if (entry != null) {
/* 102 */         return new QName(entry.getNamespaceURI(), entry.getLocalName());
/*     */       }
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract String getFaultString();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable createException(Map<QName, CheckedExceptionImpl> exceptions) throws JAXBException {
/* 117 */     DetailType dt = getDetail();
/* 118 */     Node detail = null;
/* 119 */     if (dt != null) detail = dt.getDetail(0);
/*     */ 
/*     */     
/* 122 */     if (detail == null || exceptions == null)
/*     */     {
/*     */       
/* 125 */       return attachServerException(getProtocolException());
/*     */     }
/*     */ 
/*     */     
/* 129 */     QName detailName = new QName(detail.getNamespaceURI(), detail.getLocalName());
/* 130 */     CheckedExceptionImpl ce = exceptions.get(detailName);
/* 131 */     if (ce == null)
/*     */     {
/* 133 */       return attachServerException(getProtocolException());
/*     */     }
/*     */ 
/*     */     
/* 137 */     if (ce.getExceptionType().equals(ExceptionType.UserDefined)) {
/* 138 */       return attachServerException(createUserDefinedException(ce));
/*     */     }
/*     */     
/* 141 */     Class exceptionClass = ce.getExceptionClass();
/*     */     try {
/* 143 */       Constructor<Exception> constructor = exceptionClass.getConstructor(new Class[] { String.class, (Class)(ce.getDetailType()).type });
/* 144 */       Exception exception = constructor.newInstance(new Object[] { getFaultString(), getJAXBObject(detail, ce) });
/* 145 */       return attachServerException(exception);
/* 146 */     } catch (Exception e) {
/* 147 */       throw new WebServiceException(e);
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
/*     */   @NotNull
/*     */   public static Message createSOAPFaultMessage(@NotNull SOAPVersion soapVersion, @NotNull ProtocolException ex, @Nullable QName faultcode) {
/* 161 */     Object detail = getFaultDetail(null, ex);
/* 162 */     if (soapVersion == SOAPVersion.SOAP_12)
/* 163 */       return createSOAP12Fault(soapVersion, ex, detail, null, faultcode); 
/* 164 */     return createSOAP11Fault(soapVersion, ex, detail, null, faultcode);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createSOAPFaultMessage(SOAPVersion soapVersion, CheckedExceptionImpl ceModel, Throwable ex) {
/* 192 */     Throwable t = (ex instanceof InvocationTargetException) ? ((InvocationTargetException)ex).getTargetException() : ex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     return createSOAPFaultMessage(soapVersion, ceModel, t, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createSOAPFaultMessage(SOAPVersion soapVersion, CheckedExceptionImpl ceModel, Throwable ex, QName faultCode) {
/* 207 */     Object detail = getFaultDetail(ceModel, ex);
/* 208 */     if (soapVersion == SOAPVersion.SOAP_12)
/* 209 */       return createSOAP12Fault(soapVersion, ex, detail, ceModel, faultCode); 
/* 210 */     return createSOAP11Fault(soapVersion, ex, detail, ceModel, faultCode);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createSOAPFaultMessage(SOAPVersion soapVersion, String faultString, QName faultCode) {
/* 237 */     if (faultCode == null)
/* 238 */       faultCode = getDefaultFaultCode(soapVersion); 
/* 239 */     return createSOAPFaultMessage(soapVersion, faultString, faultCode, (Element)null);
/*     */   }
/*     */   
/*     */   public static Message createSOAPFaultMessage(SOAPVersion soapVersion, SOAPFault fault) {
/* 243 */     switch (soapVersion) {
/*     */       case SOAP_11:
/* 245 */         return JAXBMessage.create(JAXB_CONTEXT, new SOAP11Fault(fault), soapVersion);
/*     */       case SOAP_12:
/* 247 */         return JAXBMessage.create(JAXB_CONTEXT, new SOAP12Fault(fault), soapVersion);
/*     */     } 
/* 249 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Message createSOAPFaultMessage(SOAPVersion soapVersion, String faultString, QName faultCode, Element detail) {
/* 254 */     switch (soapVersion) {
/*     */       case SOAP_11:
/* 256 */         return JAXBMessage.create(JAXB_CONTEXT, new SOAP11Fault(faultCode, faultString, null, detail), soapVersion);
/*     */       case SOAP_12:
/* 258 */         return JAXBMessage.create(JAXB_CONTEXT, new SOAP12Fault(faultCode, faultString, detail), soapVersion);
/*     */     } 
/* 260 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void captureStackTrace(@Nullable Throwable t) {
/* 269 */     if (t == null)
/* 270 */       return;  if (!captureStackTrace)
/*     */       return; 
/*     */     try {
/* 273 */       Document d = DOMUtil.createDom();
/* 274 */       ExceptionBean.marshal(t, d);
/*     */       
/* 276 */       DetailType detail = getDetail();
/* 277 */       if (detail == null) {
/* 278 */         setDetail(detail = new DetailType());
/*     */       }
/* 280 */       detail.getDetails().add(d.getDocumentElement());
/* 281 */     } catch (JAXBException e) {
/*     */       
/* 283 */       logger.log(Level.WARNING, "Unable to capture the stack trace into XML", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T extends Throwable> T attachServerException(T t) {
/* 291 */     DetailType detail = getDetail();
/* 292 */     if (detail == null) return t;
/*     */     
/* 294 */     for (Element n : detail.getDetails()) {
/* 295 */       if (ExceptionBean.isStackTraceXml(n)) {
/*     */         try {
/* 297 */           t.initCause((Throwable)ExceptionBean.unmarshal(n));
/* 298 */         } catch (JAXBException e) {
/*     */           
/* 300 */           logger.log(Level.WARNING, "Unable to read the capture stack trace in the fault", e);
/*     */         } 
/* 302 */         return t;
/*     */       } 
/*     */     } 
/*     */     
/* 306 */     return t;
/*     */   }
/*     */   
/*     */   protected abstract Throwable getProtocolException();
/*     */   
/*     */   private Object getJAXBObject(Node jaxbBean, CheckedExceptionImpl ce) throws JAXBException {
/* 312 */     XMLBridge bridge = ce.getBond();
/* 313 */     return bridge.unmarshal(jaxbBean, null);
/*     */   }
/*     */   
/*     */   private Exception createUserDefinedException(CheckedExceptionImpl ce) {
/* 317 */     Class exceptionClass = ce.getExceptionClass();
/* 318 */     Class detailBean = ce.getDetailBean();
/*     */     try {
/* 320 */       Node detailNode = getDetail().getDetails().get(0);
/* 321 */       Object jaxbDetail = getJAXBObject(detailNode, ce);
/*     */       
/*     */       try {
/* 324 */         Constructor<Exception> exConstructor = exceptionClass.getConstructor(new Class[] { String.class, detailBean });
/* 325 */         return exConstructor.newInstance(new Object[] { getFaultString(), jaxbDetail });
/* 326 */       } catch (NoSuchMethodException e) {
/* 327 */         Constructor<Exception> constructor = exceptionClass.getConstructor(new Class[] { String.class });
/* 328 */         return constructor.newInstance(new Object[] { getFaultString() });
/*     */       } 
/* 330 */     } catch (Exception e) {
/* 331 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getWriteMethod(Field f) {
/* 336 */     return "set" + StringUtils.capitalize(f.getName());
/*     */   }
/*     */   
/*     */   private static Object getFaultDetail(CheckedExceptionImpl ce, Throwable exception) {
/* 340 */     if (ce == null)
/* 341 */       return null; 
/* 342 */     if (ce.getExceptionType().equals(ExceptionType.UserDefined)) {
/* 343 */       return createDetailFromUserDefinedException(ce, exception);
/*     */     }
/*     */     try {
/* 346 */       Method m = exception.getClass().getMethod("getFaultInfo", new Class[0]);
/* 347 */       return m.invoke(exception, new Object[0]);
/* 348 */     } catch (Exception e) {
/* 349 */       throw new SerializationException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Object createDetailFromUserDefinedException(CheckedExceptionImpl ce, Object exception) {
/* 354 */     Class detailBean = ce.getDetailBean();
/* 355 */     Field[] fields = detailBean.getDeclaredFields();
/*     */     try {
/* 357 */       Object detail = detailBean.newInstance();
/* 358 */       for (Field f : fields) {
/* 359 */         Method em = exception.getClass().getMethod(getReadMethod(f), new Class[0]);
/*     */         try {
/* 361 */           Method sm = detailBean.getMethod(getWriteMethod(f), new Class[] { em.getReturnType() });
/* 362 */           sm.invoke(detail, new Object[] { em.invoke(exception, new Object[0]) });
/* 363 */         } catch (NoSuchMethodException ne) {
/*     */           
/* 365 */           Field sf = detailBean.getField(f.getName());
/* 366 */           sf.set(detail, em.invoke(exception, new Object[0]));
/*     */         } 
/*     */       } 
/* 369 */       return detail;
/* 370 */     } catch (Exception e) {
/* 371 */       throw new SerializationException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getReadMethod(Field f) {
/* 376 */     if (f.getType().isAssignableFrom(boolean.class))
/* 377 */       return "is" + StringUtils.capitalize(f.getName()); 
/* 378 */     return "get" + StringUtils.capitalize(f.getName());
/*     */   }
/*     */   
/*     */   private static Message createSOAP11Fault(SOAPVersion soapVersion, Throwable e, Object detail, CheckedExceptionImpl ce, QName faultCode) {
/* 382 */     SOAPFaultException soapFaultException = null;
/* 383 */     String faultString = null;
/* 384 */     String faultActor = null;
/* 385 */     Throwable cause = e.getCause();
/* 386 */     if (e instanceof SOAPFaultException) {
/* 387 */       soapFaultException = (SOAPFaultException)e;
/* 388 */     } else if (cause != null && cause instanceof SOAPFaultException) {
/* 389 */       soapFaultException = (SOAPFaultException)e.getCause();
/*     */     } 
/* 391 */     if (soapFaultException != null) {
/* 392 */       QName soapFaultCode = soapFaultException.getFault().getFaultCodeAsQName();
/* 393 */       if (soapFaultCode != null) {
/* 394 */         faultCode = soapFaultCode;
/*     */       }
/* 396 */       faultString = soapFaultException.getFault().getFaultString();
/* 397 */       faultActor = soapFaultException.getFault().getFaultActor();
/*     */     } 
/*     */     
/* 400 */     if (faultCode == null) {
/* 401 */       faultCode = getDefaultFaultCode(soapVersion);
/*     */     }
/*     */     
/* 404 */     if (faultString == null) {
/* 405 */       faultString = e.getMessage();
/* 406 */       if (faultString == null) {
/* 407 */         faultString = e.toString();
/*     */       }
/*     */     } 
/* 410 */     Element detailNode = null;
/* 411 */     QName firstEntry = null;
/* 412 */     if (detail == null && soapFaultException != null) {
/* 413 */       detailNode = soapFaultException.getFault().getDetail();
/* 414 */       firstEntry = getFirstDetailEntryName((Detail)detailNode);
/* 415 */     } else if (ce != null) {
/*     */       try {
/* 417 */         DOMResult dr = new DOMResult();
/* 418 */         ce.getBond().marshal(detail, dr);
/* 419 */         detailNode = (Element)dr.getNode().getFirstChild();
/* 420 */         firstEntry = getFirstDetailEntryName(detailNode);
/* 421 */       } catch (JAXBException e1) {
/*     */         
/* 423 */         faultString = e.getMessage();
/* 424 */         faultCode = getDefaultFaultCode(soapVersion);
/*     */       } 
/*     */     } 
/* 427 */     SOAP11Fault soap11Fault = new SOAP11Fault(faultCode, faultString, faultActor, detailNode);
/*     */ 
/*     */     
/* 430 */     if (ce == null) {
/* 431 */       soap11Fault.captureStackTrace(e);
/*     */     }
/* 433 */     Message msg = JAXBMessage.create(JAXB_CONTEXT, soap11Fault, soapVersion);
/* 434 */     return (Message)new FaultMessage(msg, firstEntry);
/*     */   }
/*     */   @Nullable
/*     */   private static QName getFirstDetailEntryName(@Nullable Detail detail) {
/* 438 */     if (detail != null) {
/* 439 */       Iterator<DetailEntry> it = detail.getDetailEntries();
/* 440 */       if (it.hasNext()) {
/* 441 */         DetailEntry entry = it.next();
/* 442 */         return getFirstDetailEntryName(entry);
/*     */       } 
/*     */     } 
/* 445 */     return null;
/*     */   }
/*     */   @NotNull
/*     */   private static QName getFirstDetailEntryName(@NotNull Element entry) {
/* 449 */     return new QName(entry.getNamespaceURI(), entry.getLocalName());
/*     */   }
/*     */   
/*     */   private static Message createSOAP12Fault(SOAPVersion soapVersion, Throwable e, Object detail, CheckedExceptionImpl ce, QName faultCode) {
/* 453 */     SOAPFaultException soapFaultException = null;
/* 454 */     CodeType code = null;
/* 455 */     String faultString = null;
/* 456 */     String faultRole = null;
/* 457 */     String faultNode = null;
/* 458 */     Throwable cause = e.getCause();
/* 459 */     if (e instanceof SOAPFaultException) {
/* 460 */       soapFaultException = (SOAPFaultException)e;
/* 461 */     } else if (cause != null && cause instanceof SOAPFaultException) {
/* 462 */       soapFaultException = (SOAPFaultException)e.getCause();
/*     */     } 
/* 464 */     if (soapFaultException != null) {
/* 465 */       SOAPFault fault = soapFaultException.getFault();
/* 466 */       QName soapFaultCode = fault.getFaultCodeAsQName();
/* 467 */       if (soapFaultCode != null) {
/* 468 */         faultCode = soapFaultCode;
/* 469 */         code = new CodeType(faultCode);
/* 470 */         Iterator<QName> iter = fault.getFaultSubcodes();
/* 471 */         boolean first = true;
/* 472 */         SubcodeType subcode = null;
/* 473 */         while (iter.hasNext()) {
/* 474 */           QName value = iter.next();
/* 475 */           if (first) {
/* 476 */             SubcodeType sct = new SubcodeType(value);
/* 477 */             code.setSubcode(sct);
/* 478 */             subcode = sct;
/* 479 */             first = false;
/*     */             continue;
/*     */           } 
/* 482 */           subcode = fillSubcodes(subcode, value);
/*     */         } 
/*     */       } 
/* 485 */       faultString = soapFaultException.getFault().getFaultString();
/* 486 */       faultRole = soapFaultException.getFault().getFaultActor();
/* 487 */       faultNode = soapFaultException.getFault().getFaultNode();
/*     */     } 
/*     */     
/* 490 */     if (faultCode == null) {
/* 491 */       faultCode = getDefaultFaultCode(soapVersion);
/* 492 */       code = new CodeType(faultCode);
/* 493 */     } else if (code == null) {
/* 494 */       code = new CodeType(faultCode);
/*     */     } 
/*     */     
/* 497 */     if (faultString == null) {
/* 498 */       faultString = e.getMessage();
/* 499 */       if (faultString == null) {
/* 500 */         faultString = e.toString();
/*     */       }
/*     */     } 
/*     */     
/* 504 */     ReasonType reason = new ReasonType(faultString);
/* 505 */     Element detailNode = null;
/* 506 */     QName firstEntry = null;
/* 507 */     if (detail == null && soapFaultException != null) {
/* 508 */       detailNode = soapFaultException.getFault().getDetail();
/* 509 */       firstEntry = getFirstDetailEntryName((Detail)detailNode);
/* 510 */     } else if (detail != null) {
/*     */       try {
/* 512 */         DOMResult dr = new DOMResult();
/* 513 */         ce.getBond().marshal(detail, dr);
/* 514 */         detailNode = (Element)dr.getNode().getFirstChild();
/* 515 */         firstEntry = getFirstDetailEntryName(detailNode);
/* 516 */       } catch (JAXBException e1) {
/*     */         
/* 518 */         faultString = e.getMessage();
/*     */       } 
/*     */     } 
/*     */     
/* 522 */     SOAP12Fault soap12Fault = new SOAP12Fault(code, reason, faultNode, faultRole, detailNode);
/*     */ 
/*     */     
/* 525 */     if (ce == null) {
/* 526 */       soap12Fault.captureStackTrace(e);
/*     */     }
/* 528 */     Message msg = JAXBMessage.create(JAXB_CONTEXT, soap12Fault, soapVersion);
/* 529 */     return (Message)new FaultMessage(msg, firstEntry);
/*     */   }
/*     */   
/*     */   private static SubcodeType fillSubcodes(SubcodeType parent, QName value) {
/* 533 */     SubcodeType newCode = new SubcodeType(value);
/* 534 */     parent.setSubcode(newCode);
/* 535 */     return newCode;
/*     */   }
/*     */   
/*     */   private static QName getDefaultFaultCode(SOAPVersion soapVersion) {
/* 539 */     return soapVersion.faultCodeServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPFaultBuilder create(Message msg) throws JAXBException {
/* 549 */     return (SOAPFaultBuilder)msg.readPayloadAsJAXB(JAXB_CONTEXT.createUnmarshaller());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 557 */   private static final Logger logger = Logger.getLogger(SOAPFaultBuilder.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean captureStackTrace;
/*     */ 
/*     */   
/* 564 */   static final String CAPTURE_STACK_TRACE_PROPERTY = SOAPFaultBuilder.class.getName() + ".captureStackTrace";
/*     */   
/*     */   static {
/* 567 */     boolean tmpVal = false;
/*     */     try {
/* 569 */       tmpVal = Boolean.getBoolean(CAPTURE_STACK_TRACE_PROPERTY);
/* 570 */     } catch (SecurityException e) {}
/*     */ 
/*     */     
/* 573 */     captureStackTrace = tmpVal;
/*     */     
/*     */     try {
/* 576 */       JAXB_CONTEXT = JAXBContext.newInstance(new Class[] { SOAP11Fault.class, SOAP12Fault.class });
/* 577 */     } catch (JAXBException e) {
/* 578 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\fault\SOAPFaultBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */