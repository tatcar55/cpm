/*      */ package com.sun.xml.messaging.saaj.soap;
/*      */ 
/*      */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.Header;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.internet.BMMimeMultipart;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.internet.ContentType;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeBodyPart;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeMultipart;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.internet.MimePullMultipart;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.internet.ParameterList;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.internet.ParseException;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.internet.SharedInputStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.ASCIIUtility;
/*      */ import com.sun.xml.messaging.saaj.soap.impl.EnvelopeImpl;
/*      */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*      */ import com.sun.xml.messaging.saaj.util.FastInfosetReflection;
/*      */ import com.sun.xml.messaging.saaj.util.FinalArrayList;
/*      */ import com.sun.xml.messaging.saaj.util.SAAJUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.activation.DataSource;
/*      */ import javax.xml.soap.AttachmentPart;
/*      */ import javax.xml.soap.MimeHeaders;
/*      */ import javax.xml.soap.Node;
/*      */ import javax.xml.soap.SOAPBody;
/*      */ import javax.xml.soap.SOAPConstants;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import javax.xml.soap.SOAPHeader;
/*      */ import javax.xml.soap.SOAPMessage;
/*      */ import javax.xml.soap.SOAPPart;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ import org.jvnet.mimepull.MIMEPart;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class MessageImpl
/*      */   extends SOAPMessage
/*      */   implements SOAPConstants
/*      */ {
/*      */   public static final String CONTENT_ID = "Content-ID";
/*      */   public static final String CONTENT_LOCATION = "Content-Location";
/*   82 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap", "com.sun.xml.messaging.saaj.soap.LocalStrings");
/*      */   
/*      */   protected static final int PLAIN_XML_FLAG = 1;
/*      */   
/*      */   protected static final int MIME_MULTIPART_FLAG = 2;
/*      */   
/*      */   protected static final int SOAP1_1_FLAG = 4;
/*      */   
/*      */   protected static final int SOAP1_2_FLAG = 8;
/*      */   
/*      */   protected static final int MIME_MULTIPART_XOP_SOAP1_1_FLAG = 6;
/*      */   protected static final int MIME_MULTIPART_XOP_SOAP1_2_FLAG = 10;
/*      */   protected static final int XOP_FLAG = 13;
/*      */   protected static final int FI_ENCODED_FLAG = 16;
/*      */   protected MimeHeaders headers;
/*      */   protected ContentType contentType;
/*      */   protected SOAPPartImpl soapPartImpl;
/*      */   protected FinalArrayList attachments;
/*      */   protected boolean saved = false;
/*      */   protected byte[] messageBytes;
/*      */   protected int messageByteCount;
/*  103 */   protected HashMap properties = new HashMap<Object, Object>();
/*      */ 
/*      */   
/*  106 */   protected MimeMultipart multiPart = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean attachmentsInitialized = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isFastInfoset = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean acceptFastInfoset = false;
/*      */ 
/*      */   
/*  121 */   protected MimeMultipart mmp = null;
/*      */ 
/*      */   
/*      */   private boolean optimizeAttachmentProcessing = true;
/*      */   
/*  126 */   private InputStream inputStreamAfterSaveChanges = null;
/*      */   
/*      */   private static boolean switchOffBM = false;
/*      */   
/*      */   private static boolean switchOffLazyAttachment = false;
/*      */   private static boolean useMimePull = false;
/*      */   
/*      */   static {
/*  134 */     String s = SAAJUtil.getSystemProperty("saaj.mime.optimization");
/*  135 */     if (s != null && s.equals("false")) {
/*  136 */       switchOffBM = true;
/*      */     }
/*  138 */     s = SAAJUtil.getSystemProperty("saaj.lazy.mime.optimization");
/*  139 */     if (s != null && s.equals("false")) {
/*  140 */       switchOffLazyAttachment = true;
/*      */     }
/*  142 */     useMimePull = SAAJUtil.getSystemBoolean("saaj.use.mimepull");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  830 */     nullIter = Collections.EMPTY_LIST.iterator();
/*      */   }
/*      */   private boolean lazyAttachments = false; private static final Iterator nullIter; private static boolean isSoap1_1Type(String primary, String sub) { return ((primary.equalsIgnoreCase("text") && sub.equalsIgnoreCase("xml")) || (primary.equalsIgnoreCase("text") && sub.equalsIgnoreCase("xml-soap")) || (primary.equals("application") && sub.equals("fastinfoset"))); } private static boolean isEqualToSoap1_1Type(String type) { return (type.startsWith("text/xml") || type.startsWith("application/fastinfoset")); } private static boolean isSoap1_2Type(String primary, String sub) { return (primary.equals("application") && (sub.equals("soap+xml") || sub.equals("soap+fastinfoset"))); } private static boolean isEqualToSoap1_2Type(String type) { return (type.startsWith("application/soap+xml") || type.startsWith("application/soap+fastinfoset")); } protected MessageImpl() { this(false, false); this.attachmentsInitialized = true; } protected MessageImpl(boolean isFastInfoset, boolean acceptFastInfoset) { this.isFastInfoset = isFastInfoset; this.acceptFastInfoset = acceptFastInfoset; this.headers = new MimeHeaders(); this.headers.setHeader("Accept", getExpectedAcceptHeader()); this.contentType = new ContentType(); } protected MessageImpl(SOAPMessage msg) { if (!(msg instanceof MessageImpl)); MessageImpl src = (MessageImpl)msg; this.headers = src.headers; this.soapPartImpl = src.soapPartImpl; this.attachments = src.attachments; this.saved = src.saved; this.messageBytes = src.messageBytes; this.messageByteCount = src.messageByteCount; this.properties = src.properties; this.contentType = src.contentType; } protected static boolean isSoap1_1Content(int stat) { return ((stat & 0x4) != 0); } protected static boolean isSoap1_2Content(int stat) { return ((stat & 0x8) != 0); } private static boolean isMimeMultipartXOPSoap1_2Package(ContentType contentType) { String type = contentType.getParameter("type"); if (type == null) return false;  type = type.toLowerCase(); if (!type.startsWith("application/xop+xml")) return false;  String startinfo = contentType.getParameter("start-info"); if (startinfo == null) return false;  startinfo = startinfo.toLowerCase(); return isEqualToSoap1_2Type(startinfo); } private static boolean isMimeMultipartXOPSoap1_1Package(ContentType contentType) { String type = contentType.getParameter("type"); if (type == null) return false;  type = type.toLowerCase(); if (!type.startsWith("application/xop+xml")) return false;  String startinfo = contentType.getParameter("start-info"); if (startinfo == null) return false;  startinfo = startinfo.toLowerCase(); return isEqualToSoap1_1Type(startinfo); } private static boolean isSOAPBodyXOPPackage(ContentType contentType) { String primary = contentType.getPrimaryType(); String sub = contentType.getSubType(); if (primary.equalsIgnoreCase("application") && sub.equalsIgnoreCase("xop+xml")) { String type = getTypeParameter(contentType); return (isEqualToSoap1_2Type(type) || isEqualToSoap1_1Type(type)); }  return false; } protected MessageImpl(MimeHeaders headers, InputStream in) throws SOAPExceptionImpl { this.contentType = parseContentType(headers); init(headers, identifyContentType(this.contentType), this.contentType, in); } private static ContentType parseContentType(MimeHeaders headers) throws SOAPExceptionImpl { String ct; if (headers != null) { ct = getContentType(headers); } else { log.severe("SAAJ0550.soap.null.headers"); throw new SOAPExceptionImpl("Cannot create message: Headers can't be null"); }  if (ct == null) { log.severe("SAAJ0532.soap.no.Content-Type"); throw new SOAPExceptionImpl("Absent Content-Type"); }  try { return new ContentType(ct); } catch (Throwable ex) { log.severe("SAAJ0535.soap.cannot.internalize.message"); throw new SOAPExceptionImpl("Unable to internalize message", ex); }  } protected MessageImpl(MimeHeaders headers, ContentType contentType, int stat, InputStream in) throws SOAPExceptionImpl { init(headers, stat, contentType, in); } private void init(MimeHeaders headers, int stat, final ContentType contentType, final InputStream in) throws SOAPExceptionImpl { this.headers = headers; try { if ((stat & 0x10) > 0) this.isFastInfoset = this.acceptFastInfoset = true;  if (!this.isFastInfoset) { String[] values = headers.getHeader("Accept"); if (values != null) for (int i = 0; i < values.length; i++) { StringTokenizer st = new StringTokenizer(values[i], ","); while (st.hasMoreTokens()) { String token = st.nextToken().trim(); if (token.equalsIgnoreCase("application/fastinfoset") || token.equalsIgnoreCase("application/soap+fastinfoset")) { this.acceptFastInfoset = true; break; }  }  }   }  if (!isCorrectSoapVersion(stat)) { log.log(Level.SEVERE, "SAAJ0533.soap.incorrect.Content-Type", (Object[])new String[] { contentType.toString(), getExpectedContentType() }); throw new SOAPVersionMismatchException("Cannot create message: incorrect content-type for SOAP version. Got: " + contentType + " Expected: " + getExpectedContentType()); }  if ((stat & 0x1) != 0) { if (this.isFastInfoset) { getSOAPPart().setContent(FastInfosetReflection.FastInfosetSource_new(in)); } else { initCharsetProperty(contentType); getSOAPPart().setContent(new StreamSource(in)); }  } else if ((stat & 0x2) != 0) { DataSource ds = new DataSource() {
/*      */             public InputStream getInputStream() { return in; } public OutputStream getOutputStream() { return null; } public String getContentType() { return contentType.toString(); } public String getName() { return ""; }
/*  834 */           }; this.multiPart = null; if (useMimePull) { this.multiPart = (MimeMultipart)new MimePullMultipart(ds, contentType); } else if (switchOffBM) { this.multiPart = new MimeMultipart(ds, contentType); } else { this.multiPart = (MimeMultipart)new BMMimeMultipart(ds, contentType); }  String startParam = contentType.getParameter("start"); MimeBodyPart soapMessagePart = null; InputStream soapPartInputStream = null; String contentID = null; String contentIDNoAngle = null; if (switchOffBM || switchOffLazyAttachment) { if (startParam == null) { soapMessagePart = this.multiPart.getBodyPart(0); for (int i = 1; i < this.multiPart.getCount(); i++) initializeAttachment(this.multiPart, i);  } else { soapMessagePart = this.multiPart.getBodyPart(startParam); for (int i = 0; i < this.multiPart.getCount(); i++) { contentID = this.multiPart.getBodyPart(i).getContentID(); contentIDNoAngle = (contentID != null) ? contentID.replaceFirst("^<", "").replaceFirst(">$", "") : null; if (!startParam.equals(contentID) && !startParam.equals(contentIDNoAngle)) initializeAttachment(this.multiPart, i);  }  }  } else if (useMimePull) { MimePullMultipart mpMultipart = (MimePullMultipart)this.multiPart; MIMEPart sp = mpMultipart.readAndReturnSOAPPart(); soapMessagePart = new MimeBodyPart(sp); soapPartInputStream = sp.readOnce(); } else { BMMimeMultipart bmMultipart = (BMMimeMultipart)this.multiPart; InputStream stream = bmMultipart.initStream(); SharedInputStream sin = null; if (stream instanceof SharedInputStream) sin = (SharedInputStream)stream;  String boundary = "--" + contentType.getParameter("boundary"); byte[] bndbytes = ASCIIUtility.getBytes(boundary); if (startParam == null) { soapMessagePart = bmMultipart.getNextPart(stream, bndbytes, sin); bmMultipart.removeBodyPart(soapMessagePart); } else { MimeBodyPart bp = null; try { while (!startParam.equals(contentID) && !startParam.equals(contentIDNoAngle)) { bp = bmMultipart.getNextPart(stream, bndbytes, sin); contentID = bp.getContentID(); contentIDNoAngle = (contentID != null) ? contentID.replaceFirst("^<", "").replaceFirst(">$", "") : null; }  soapMessagePart = bp; bmMultipart.removeBodyPart(bp); } catch (Exception e) { throw new SOAPExceptionImpl(e); }  }  }  if (soapPartInputStream == null && soapMessagePart != null) soapPartInputStream = soapMessagePart.getInputStream();  ContentType soapPartCType = new ContentType(soapMessagePart.getContentType()); initCharsetProperty(soapPartCType); String baseType = soapPartCType.getBaseType().toLowerCase(); if (!isEqualToSoap1_1Type(baseType) && !isEqualToSoap1_2Type(baseType) && !isSOAPBodyXOPPackage(soapPartCType)) { log.log(Level.SEVERE, "SAAJ0549.soap.part.invalid.Content-Type", new Object[] { baseType }); throw new SOAPExceptionImpl("Bad Content-Type for SOAP Part : " + baseType); }  SOAPPart soapPart = getSOAPPart(); setMimeHeaders(soapPart, soapMessagePart); soapPart.setContent(this.isFastInfoset ? FastInfosetReflection.FastInfosetSource_new(soapPartInputStream) : new StreamSource(soapPartInputStream)); } else { log.severe("SAAJ0534.soap.unknown.Content-Type"); throw new SOAPExceptionImpl("Unrecognized Content-Type"); }  } catch (Throwable ex) { log.severe("SAAJ0535.soap.cannot.internalize.message"); throw new SOAPExceptionImpl("Unable to internalize message", ex); }  needsSave(); } public boolean isFastInfoset() { return this.isFastInfoset; } public boolean acceptFastInfoset() { return this.acceptFastInfoset; } public void setIsFastInfoset(boolean value) { if (value != this.isFastInfoset) { this.isFastInfoset = value; if (this.isFastInfoset) this.acceptFastInfoset = true;  this.saved = false; }  } public Object getProperty(String property) { return this.properties.get(property); } public Iterator getAttachments() { try { initializeAllAttachments(); }
/*  835 */     catch (Exception e)
/*  836 */     { throw new RuntimeException(e); }
/*      */     
/*  838 */     if (this.attachments == null)
/*  839 */       return nullIter; 
/*  840 */     return this.attachments.iterator(); }
/*      */   public void setProperty(String property, Object value) { verify(property, value); this.properties.put(property, value); }
/*      */   private void verify(String property, Object value) { if (property.equalsIgnoreCase("javax.xml.soap.write-xml-declaration")) { if (!"true".equals(value) && !"false".equals(value)) throw new RuntimeException(property + " must have value false or true");  try { EnvelopeImpl env = (EnvelopeImpl)getSOAPPart().getEnvelope(); if ("true".equalsIgnoreCase((String)value)) { env.setOmitXmlDecl("no"); } else if ("false".equalsIgnoreCase((String)value)) { env.setOmitXmlDecl("yes"); }  } catch (Exception e) { log.log(Level.SEVERE, "SAAJ0591.soap.exception.in.set.property", new Object[] { e.getMessage(), "javax.xml.soap.write-xml-declaration" }); throw new RuntimeException(e); }  return; }  if (property.equalsIgnoreCase("javax.xml.soap.character-set-encoding")) try { ((EnvelopeImpl)getSOAPPart().getEnvelope()).setCharsetEncoding((String)value); } catch (Exception e) { log.log(Level.SEVERE, "SAAJ0591.soap.exception.in.set.property", new Object[] { e.getMessage(), "javax.xml.soap.character-set-encoding" }); throw new RuntimeException(e); }   }
/*      */   static int identifyContentType(ContentType ct) throws SOAPExceptionImpl { String primary = ct.getPrimaryType().toLowerCase(); String sub = ct.getSubType().toLowerCase(); if (primary.equals("multipart")) { if (sub.equals("related")) { String type = getTypeParameter(ct); if (isEqualToSoap1_1Type(type)) return (type.equals("application/fastinfoset") ? 16 : 0) | 0x2 | 0x4;  if (isEqualToSoap1_2Type(type)) return (type.equals("application/soap+fastinfoset") ? 16 : 0) | 0x2 | 0x8;  if (isMimeMultipartXOPSoap1_1Package(ct)) return 6;  if (isMimeMultipartXOPSoap1_2Package(ct)) return 10;  log.severe("SAAJ0536.soap.content-type.mustbe.multipart"); throw new SOAPExceptionImpl("Content-Type needs to be Multipart/Related and with \"type=text/xml\" or \"type=application/soap+xml\""); }  log.severe("SAAJ0537.soap.invalid.content-type"); throw new SOAPExceptionImpl("Invalid Content-Type: " + primary + '/' + sub); }  if (isSoap1_1Type(primary, sub)) return ((primary.equalsIgnoreCase("application") && sub.equalsIgnoreCase("fastinfoset")) ? 16 : 0) | 0x1 | 0x4;  if (isSoap1_2Type(primary, sub)) return ((primary.equalsIgnoreCase("application") && sub.equalsIgnoreCase("soap+fastinfoset")) ? 16 : 0) | 0x1 | 0x8;  if (isSOAPBodyXOPPackage(ct)) return 13;  log.severe("SAAJ0537.soap.invalid.content-type"); throw new SOAPExceptionImpl("Invalid Content-Type:" + primary + '/' + sub + ". Is this an error message instead of a SOAP response?"); }
/*  844 */   private static String getTypeParameter(ContentType contentType) { String p = contentType.getParameter("type"); if (p != null) return p.toLowerCase();  return "text/xml"; } public MimeHeaders getMimeHeaders() { return this.headers; } static final String getContentType(MimeHeaders headers) { String[] values = headers.getHeader("Content-Type"); if (values == null) return null;  return values[0]; } public String getContentType() { return getContentType(this.headers); } public void setContentType(String type) { this.headers.setHeader("Content-Type", type); needsSave(); } private ContentType contentType() { ContentType ct = null; try { String currentContent = getContentType(); if (currentContent == null) return this.contentType;  ct = new ContentType(currentContent); } catch (Exception e) {} return ct; } public String getBaseType() { return contentType().getBaseType(); } public void setBaseType(String type) { ContentType ct = contentType(); ct.setParameter("type", type); this.headers.setHeader("Content-Type", ct.toString()); needsSave(); } public String getAction() { return contentType().getParameter("action"); } public void setAction(String action) { ContentType ct = contentType(); ct.setParameter("action", action); this.headers.setHeader("Content-Type", ct.toString()); needsSave(); } public String getCharset() { return contentType().getParameter("charset"); } public void setCharset(String charset) { ContentType ct = contentType(); ct.setParameter("charset", charset); this.headers.setHeader("Content-Type", ct.toString()); needsSave(); } private final void needsSave() { this.saved = false; } public boolean saveRequired() { return (this.saved != true); } public String getContentDescription() { String[] values = this.headers.getHeader("Content-Description"); if (values != null && values.length > 0) return values[0];  return null; } public void setContentDescription(String description) { this.headers.setHeader("Content-Description", description); needsSave(); } public void removeAllAttachments() { try { initializeAllAttachments(); } catch (Exception e) { throw new RuntimeException(e); }  if (this.attachments != null) { this.attachments.clear(); needsSave(); }  } public int countAttachments() { try { initializeAllAttachments(); } catch (Exception e) { throw new RuntimeException(e); }  if (this.attachments != null) return this.attachments.size();  return 0; } public void addAttachmentPart(AttachmentPart attachment) { try { initializeAllAttachments(); this.optimizeAttachmentProcessing = true; } catch (Exception e) { throw new RuntimeException(e); }  if (this.attachments == null) this.attachments = new FinalArrayList();  this.attachments.add(attachment); needsSave(); } private void setFinalContentType(String charset) { ContentType ct = contentType();
/*  845 */     if (ct == null) {
/*  846 */       ct = new ContentType();
/*      */     }
/*  848 */     String[] split = getExpectedContentType().split("/");
/*  849 */     ct.setPrimaryType(split[0]);
/*  850 */     ct.setSubType(split[1]);
/*  851 */     ct.setParameter("charset", charset);
/*  852 */     this.headers.setHeader("Content-Type", ct.toString()); }
/*      */   
/*      */   private class MimeMatchingIterator implements Iterator { private Iterator iter; private MimeHeaders headers; private Object nextAttachment;
/*      */     
/*      */     public MimeMatchingIterator(MimeHeaders headers) {
/*  857 */       this.headers = headers;
/*  858 */       this.iter = MessageImpl.this.attachments.iterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  866 */       if (this.nextAttachment == null)
/*  867 */         this.nextAttachment = nextMatch(); 
/*  868 */       return (this.nextAttachment != null);
/*      */     }
/*      */     
/*      */     public Object next() {
/*  872 */       if (this.nextAttachment != null) {
/*  873 */         Object ret = this.nextAttachment;
/*  874 */         this.nextAttachment = null;
/*  875 */         return ret;
/*      */       } 
/*      */       
/*  878 */       if (hasNext()) {
/*  879 */         return this.nextAttachment;
/*      */       }
/*  881 */       return null;
/*      */     }
/*      */     
/*      */     Object nextMatch() {
/*  885 */       while (this.iter.hasNext()) {
/*  886 */         AttachmentPartImpl ap = this.iter.next();
/*  887 */         if (ap.hasAllHeaders(this.headers))
/*  888 */           return ap; 
/*      */       } 
/*  890 */       return null;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  894 */       this.iter.remove();
/*      */     } }
/*      */ 
/*      */   
/*      */   public Iterator getAttachments(MimeHeaders headers) {
/*      */     try {
/*  900 */       initializeAllAttachments();
/*  901 */     } catch (Exception e) {
/*  902 */       throw new RuntimeException(e);
/*      */     } 
/*  904 */     if (this.attachments == null) {
/*  905 */       return nullIter;
/*      */     }
/*  907 */     return new MimeMatchingIterator(headers);
/*      */   }
/*      */   
/*      */   public void removeAttachments(MimeHeaders headers) {
/*      */     try {
/*  912 */       initializeAllAttachments();
/*  913 */     } catch (Exception e) {
/*  914 */       throw new RuntimeException(e);
/*      */     } 
/*  916 */     if (this.attachments == null) {
/*      */       return;
/*      */     }
/*  919 */     Iterator it = new MimeMatchingIterator(headers);
/*  920 */     while (it.hasNext()) {
/*  921 */       int index = this.attachments.indexOf(it.next());
/*  922 */       this.attachments.set(index, null);
/*      */     } 
/*  924 */     FinalArrayList f = new FinalArrayList();
/*  925 */     for (int i = 0; i < this.attachments.size(); i++) {
/*  926 */       if (this.attachments.get(i) != null) {
/*  927 */         f.add(this.attachments.get(i));
/*      */       }
/*      */     } 
/*  930 */     this.attachments = f;
/*      */   }
/*      */ 
/*      */   
/*      */   public AttachmentPart createAttachmentPart() {
/*  935 */     return new AttachmentPartImpl();
/*      */   }
/*      */   
/*      */   public AttachmentPart getAttachment(SOAPElement element) throws SOAPException {
/*      */     String uri;
/*      */     try {
/*  941 */       initializeAllAttachments();
/*  942 */     } catch (Exception e) {
/*  943 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/*  946 */     String hrefAttr = element.getAttribute("href");
/*  947 */     if ("".equals(hrefAttr)) {
/*  948 */       Node node = getValueNodeStrict(element);
/*  949 */       String swaRef = null;
/*  950 */       if (node != null) {
/*  951 */         swaRef = node.getValue();
/*      */       }
/*  953 */       if (swaRef == null || "".equals(swaRef)) {
/*  954 */         return null;
/*      */       }
/*  956 */       uri = swaRef;
/*      */     } else {
/*      */       
/*  959 */       uri = hrefAttr;
/*      */     } 
/*  961 */     return getAttachmentPart(uri);
/*      */   }
/*      */   
/*      */   private Node getValueNodeStrict(SOAPElement element) {
/*  965 */     Node node = (Node)element.getFirstChild();
/*  966 */     if (node != null) {
/*  967 */       if (node.getNextSibling() == null && node.getNodeType() == 3)
/*      */       {
/*  969 */         return node;
/*      */       }
/*  971 */       return null;
/*      */     } 
/*      */     
/*  974 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private AttachmentPart getAttachmentPart(String uri) throws SOAPException {
/*      */     AttachmentPart _part;
/*      */     try {
/*  981 */       if (uri.startsWith("cid:")) {
/*      */         
/*  983 */         uri = '<' + uri.substring("cid:".length()) + '>';
/*      */         
/*  985 */         MimeHeaders headersToMatch = new MimeHeaders();
/*  986 */         headersToMatch.addHeader("Content-ID", uri);
/*      */         
/*  988 */         Iterator<AttachmentPart> i = getAttachments(headersToMatch);
/*  989 */         _part = (i == null) ? null : i.next();
/*      */       } else {
/*      */         
/*  992 */         MimeHeaders headersToMatch = new MimeHeaders();
/*  993 */         headersToMatch.addHeader("Content-Location", uri);
/*      */         
/*  995 */         Iterator<AttachmentPart> i = getAttachments(headersToMatch);
/*  996 */         _part = (i == null) ? null : i.next();
/*      */       } 
/*      */ 
/*      */       
/* 1000 */       if (_part == null) {
/* 1001 */         Iterator<AttachmentPart> j = getAttachments();
/*      */         
/* 1003 */         while (j.hasNext()) {
/* 1004 */           AttachmentPart p = j.next();
/* 1005 */           String cl = p.getContentId();
/* 1006 */           if (cl != null) {
/*      */             
/* 1008 */             int eqIndex = cl.indexOf("=");
/* 1009 */             if (eqIndex > -1) {
/* 1010 */               cl = cl.substring(1, eqIndex);
/* 1011 */               if (cl.equalsIgnoreCase(uri)) {
/* 1012 */                 _part = p;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1020 */     } catch (Exception se) {
/* 1021 */       log.log(Level.SEVERE, "SAAJ0590.soap.unable.to.locate.attachment", new Object[] { uri });
/* 1022 */       throw new SOAPExceptionImpl(se);
/*      */     } 
/* 1024 */     return _part;
/*      */   }
/*      */ 
/*      */   
/*      */   private final InputStream getHeaderBytes() throws IOException {
/* 1029 */     SOAPPartImpl sp = (SOAPPartImpl)getSOAPPart();
/* 1030 */     return sp.getContentAsStream();
/*      */   }
/*      */   
/*      */   private String convertToSingleLine(String contentType) {
/* 1034 */     StringBuffer buffer = new StringBuffer();
/* 1035 */     for (int i = 0; i < contentType.length(); i++) {
/* 1036 */       char c = contentType.charAt(i);
/* 1037 */       if (c != '\r' && c != '\n' && c != '\t')
/* 1038 */         buffer.append(c); 
/*      */     } 
/* 1040 */     return buffer.toString();
/*      */   }
/*      */   
/*      */   private MimeMultipart getMimeMessage() throws SOAPException {
/*      */     try {
/* 1045 */       SOAPPartImpl soapPart = (SOAPPartImpl)getSOAPPart();
/* 1046 */       MimeBodyPart mimeSoapPart = soapPart.getMimePart();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1052 */       ContentType soapPartCtype = new ContentType(getExpectedContentType());
/*      */       
/* 1054 */       if (!this.isFastInfoset) {
/* 1055 */         soapPartCtype.setParameter("charset", initCharset());
/*      */       }
/* 1057 */       mimeSoapPart.setHeader("Content-Type", soapPartCtype.toString());
/*      */       
/* 1059 */       MimeMultipart headerAndBody = null;
/*      */       
/* 1061 */       if (!switchOffBM && !switchOffLazyAttachment && this.multiPart != null && !this.attachmentsInitialized) {
/*      */         
/* 1063 */         BMMimeMultipart bMMimeMultipart = new BMMimeMultipart();
/* 1064 */         bMMimeMultipart.addBodyPart(mimeSoapPart);
/* 1065 */         if (this.attachments != null) {
/* 1066 */           Iterator<AttachmentPartImpl> eachAttachment = this.attachments.iterator();
/* 1067 */           while (eachAttachment.hasNext()) {
/* 1068 */             bMMimeMultipart.addBodyPart(((AttachmentPartImpl)eachAttachment.next()).getMimePart());
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1073 */         InputStream in = ((BMMimeMultipart)this.multiPart).getInputStream();
/* 1074 */         if (!((BMMimeMultipart)this.multiPart).lastBodyPartFound() && !((BMMimeMultipart)this.multiPart).isEndOfStream())
/*      */         {
/* 1076 */           bMMimeMultipart.setInputStream(in);
/* 1077 */           bMMimeMultipart.setBoundary(((BMMimeMultipart)this.multiPart).getBoundary());
/*      */           
/* 1079 */           bMMimeMultipart.setLazyAttachments(this.lazyAttachments);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1084 */         headerAndBody = new MimeMultipart();
/* 1085 */         headerAndBody.addBodyPart(mimeSoapPart);
/*      */         
/* 1087 */         Iterator<AttachmentPartImpl> eachAttachement = getAttachments();
/* 1088 */         while (eachAttachement.hasNext())
/*      */         {
/* 1090 */           headerAndBody.addBodyPart(((AttachmentPartImpl)eachAttachement.next()).getMimePart());
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1096 */       ContentType contentType = headerAndBody.getContentType();
/*      */       
/* 1098 */       ParameterList l = contentType.getParameterList();
/*      */ 
/*      */       
/* 1101 */       l.set("type", getExpectedContentType());
/* 1102 */       l.set("boundary", contentType.getParameter("boundary"));
/* 1103 */       ContentType nct = new ContentType("multipart", "related", l);
/*      */       
/* 1105 */       this.headers.setHeader("Content-Type", convertToSingleLine(nct.toString()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1111 */       return headerAndBody;
/* 1112 */     } catch (SOAPException ex) {
/* 1113 */       throw ex;
/* 1114 */     } catch (Throwable ex) {
/* 1115 */       log.severe("SAAJ0538.soap.cannot.convert.msg.to.multipart.obj");
/* 1116 */       throw new SOAPExceptionImpl("Unable to convert SOAP message into a MimeMultipart object", ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String initCharset() {
/* 1125 */     String charset = null;
/*      */     
/* 1127 */     String[] cts = getMimeHeaders().getHeader("Content-Type");
/* 1128 */     if (cts != null && cts[0] != null) {
/* 1129 */       charset = getCharsetString(cts[0]);
/*      */     }
/*      */     
/* 1132 */     if (charset == null) {
/* 1133 */       charset = (String)getProperty("javax.xml.soap.character-set-encoding");
/*      */     }
/*      */     
/* 1136 */     if (charset != null) {
/* 1137 */       return charset;
/*      */     }
/*      */     
/* 1140 */     return "utf-8";
/*      */   }
/*      */   
/*      */   private String getCharsetString(String s) {
/*      */     try {
/* 1145 */       int index = s.indexOf(";");
/* 1146 */       if (index < 0)
/* 1147 */         return null; 
/* 1148 */       ParameterList pl = new ParameterList(s.substring(index));
/* 1149 */       return pl.get("charset");
/* 1150 */     } catch (Exception e) {
/* 1151 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveChanges() throws SOAPException {
/* 1160 */     String charset = initCharset();
/*      */ 
/*      */     
/* 1163 */     int attachmentCount = (this.attachments == null) ? 0 : this.attachments.size();
/* 1164 */     if (attachmentCount == 0 && 
/* 1165 */       !switchOffBM && !switchOffLazyAttachment && !this.attachmentsInitialized && this.multiPart != null)
/*      */     {
/*      */       
/* 1168 */       attachmentCount = 1;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1173 */       if (attachmentCount == 0 && !hasXOPContent()) {
/*      */         InputStream in;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1181 */           in = getHeaderBytes();
/*      */           
/* 1183 */           this.optimizeAttachmentProcessing = false;
/* 1184 */           if (SOAPPartImpl.lazyContentLength) {
/* 1185 */             this.inputStreamAfterSaveChanges = in;
/*      */           }
/* 1187 */         } catch (IOException ex) {
/* 1188 */           log.severe("SAAJ0539.soap.cannot.get.header.stream");
/* 1189 */           throw new SOAPExceptionImpl("Unable to get header stream in saveChanges: ", ex);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1194 */         if (in instanceof ByteInputStream) {
/* 1195 */           ByteInputStream bIn = (ByteInputStream)in;
/* 1196 */           this.messageBytes = bIn.getBytes();
/* 1197 */           this.messageByteCount = bIn.getCount();
/*      */         } 
/*      */         
/* 1200 */         setFinalContentType(charset);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1206 */         if (this.messageByteCount > 0) {
/* 1207 */           this.headers.setHeader("Content-Length", Integer.toString(this.messageByteCount));
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1212 */       else if (hasXOPContent()) {
/* 1213 */         this.mmp = getXOPMessage();
/*      */       } else {
/* 1215 */         this.mmp = getMimeMessage();
/*      */       } 
/* 1217 */     } catch (Throwable ex) {
/* 1218 */       log.severe("SAAJ0540.soap.err.saving.multipart.msg");
/* 1219 */       throw new SOAPExceptionImpl("Error during saving a multipart message", ex);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1236 */     this.saved = true;
/*      */   }
/*      */   
/*      */   private MimeMultipart getXOPMessage() throws SOAPException {
/*      */     try {
/* 1241 */       MimeMultipart headerAndBody = new MimeMultipart();
/* 1242 */       SOAPPartImpl soapPart = (SOAPPartImpl)getSOAPPart();
/* 1243 */       MimeBodyPart mimeSoapPart = soapPart.getMimePart();
/* 1244 */       ContentType soapPartCtype = new ContentType("application/xop+xml");
/*      */       
/* 1246 */       soapPartCtype.setParameter("type", getExpectedContentType());
/* 1247 */       String charset = initCharset();
/* 1248 */       soapPartCtype.setParameter("charset", charset);
/* 1249 */       mimeSoapPart.setHeader("Content-Type", soapPartCtype.toString());
/* 1250 */       headerAndBody.addBodyPart(mimeSoapPart);
/*      */       
/* 1252 */       Iterator<AttachmentPartImpl> eachAttachement = getAttachments();
/* 1253 */       while (eachAttachement.hasNext())
/*      */       {
/* 1255 */         headerAndBody.addBodyPart(((AttachmentPartImpl)eachAttachement.next()).getMimePart());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1260 */       ContentType contentType = headerAndBody.getContentType();
/*      */       
/* 1262 */       ParameterList l = contentType.getParameterList();
/*      */ 
/*      */       
/* 1265 */       l.set("start-info", getExpectedContentType());
/*      */ 
/*      */       
/* 1268 */       l.set("type", "application/xop+xml");
/*      */       
/* 1270 */       if (isCorrectSoapVersion(8)) {
/* 1271 */         String action = getAction();
/* 1272 */         if (action != null) {
/* 1273 */           l.set("action", action);
/*      */         }
/*      */       } 
/* 1276 */       l.set("boundary", contentType.getParameter("boundary"));
/* 1277 */       ContentType nct = new ContentType("Multipart", "Related", l);
/* 1278 */       this.headers.setHeader("Content-Type", convertToSingleLine(nct.toString()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1284 */       return headerAndBody;
/* 1285 */     } catch (SOAPException ex) {
/* 1286 */       throw ex;
/* 1287 */     } catch (Throwable ex) {
/* 1288 */       log.severe("SAAJ0538.soap.cannot.convert.msg.to.multipart.obj");
/* 1289 */       throw new SOAPExceptionImpl("Unable to convert SOAP message into a MimeMultipart object", ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasXOPContent() throws ParseException {
/* 1298 */     String type = getContentType();
/* 1299 */     if (type == null)
/* 1300 */       return false; 
/* 1301 */     ContentType ct = new ContentType(type);
/*      */     
/* 1303 */     return (isMimeMultipartXOPSoap1_1Package(ct) || isMimeMultipartXOPSoap1_2Package(ct) || isSOAPBodyXOPPackage(ct));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(OutputStream out) throws SOAPException, IOException {
/* 1309 */     if (saveRequired()) {
/* 1310 */       this.optimizeAttachmentProcessing = true;
/* 1311 */       saveChanges();
/*      */     } 
/*      */     
/* 1314 */     if (!this.optimizeAttachmentProcessing) {
/* 1315 */       if (SOAPPartImpl.lazyContentLength && this.messageByteCount <= 0) {
/* 1316 */         byte[] buf = new byte[1024];
/*      */         
/* 1318 */         int length = 0;
/* 1319 */         while ((length = this.inputStreamAfterSaveChanges.read(buf)) != -1) {
/* 1320 */           out.write(buf, 0, length);
/* 1321 */           this.messageByteCount += length;
/*      */         } 
/* 1323 */         if (this.messageByteCount > 0) {
/* 1324 */           this.headers.setHeader("Content-Length", Integer.toString(this.messageByteCount));
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1329 */         out.write(this.messageBytes, 0, this.messageByteCount);
/*      */       } 
/*      */     } else {
/*      */       
/*      */       try {
/* 1334 */         if (hasXOPContent()) {
/* 1335 */           this.mmp.writeTo(out);
/*      */         } else {
/* 1337 */           this.mmp.writeTo(out);
/* 1338 */           if (!switchOffBM && !switchOffLazyAttachment && this.multiPart != null && !this.attachmentsInitialized)
/*      */           {
/* 1340 */             ((BMMimeMultipart)this.multiPart).setInputStream(((BMMimeMultipart)this.mmp).getInputStream());
/*      */           }
/*      */         }
/*      */       
/* 1344 */       } catch (Exception ex) {
/* 1345 */         log.severe("SAAJ0540.soap.err.saving.multipart.msg");
/* 1346 */         throw new SOAPExceptionImpl("Error during saving a multipart message", ex);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1352 */     if (isCorrectSoapVersion(4)) {
/*      */       
/* 1354 */       String[] soapAction = this.headers.getHeader("SOAPAction");
/*      */       
/* 1356 */       if (soapAction == null || soapAction.length == 0) {
/* 1357 */         this.headers.setHeader("SOAPAction", "\"\"");
/*      */       }
/*      */     } 
/*      */     
/* 1361 */     this.messageBytes = null;
/* 1362 */     needsSave();
/*      */   }
/*      */   
/*      */   public SOAPBody getSOAPBody() throws SOAPException {
/* 1366 */     SOAPBody body = getSOAPPart().getEnvelope().getBody();
/*      */ 
/*      */ 
/*      */     
/* 1370 */     return body;
/*      */   }
/*      */   
/*      */   public SOAPHeader getSOAPHeader() throws SOAPException {
/* 1374 */     SOAPHeader hdr = getSOAPPart().getEnvelope().getHeader();
/*      */ 
/*      */ 
/*      */     
/* 1378 */     return hdr;
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeAllAttachments() throws MessagingException, SOAPException {
/* 1383 */     if (switchOffBM || switchOffLazyAttachment) {
/*      */       return;
/*      */     }
/*      */     
/* 1387 */     if (this.attachmentsInitialized || this.multiPart == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1391 */     if (this.attachments == null) {
/* 1392 */       this.attachments = new FinalArrayList();
/*      */     }
/* 1394 */     int count = this.multiPart.getCount();
/* 1395 */     for (int i = 0; i < count; i++) {
/* 1396 */       initializeAttachment(this.multiPart.getBodyPart(i));
/*      */     }
/* 1398 */     this.attachmentsInitialized = true;
/*      */     
/* 1400 */     needsSave();
/*      */   }
/*      */   
/*      */   private void initializeAttachment(MimeBodyPart mbp) throws SOAPException {
/* 1404 */     AttachmentPartImpl attachmentPart = new AttachmentPartImpl();
/* 1405 */     DataHandler attachmentHandler = mbp.getDataHandler();
/* 1406 */     attachmentPart.setDataHandler(attachmentHandler);
/*      */     
/* 1408 */     AttachmentPartImpl.copyMimeHeaders(mbp, attachmentPart);
/* 1409 */     this.attachments.add(attachmentPart);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeAttachment(MimeMultipart multiPart, int i) throws Exception {
/* 1414 */     MimeBodyPart currentBodyPart = multiPart.getBodyPart(i);
/* 1415 */     AttachmentPartImpl attachmentPart = new AttachmentPartImpl();
/*      */     
/* 1417 */     DataHandler attachmentHandler = currentBodyPart.getDataHandler();
/* 1418 */     attachmentPart.setDataHandler(attachmentHandler);
/*      */     
/* 1420 */     AttachmentPartImpl.copyMimeHeaders(currentBodyPart, attachmentPart);
/* 1421 */     addAttachmentPart(attachmentPart);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setMimeHeaders(SOAPPart soapPart, MimeBodyPart soapMessagePart) throws Exception {
/* 1428 */     soapPart.removeAllMimeHeaders();
/*      */     
/* 1430 */     FinalArrayList<Header> finalArrayList = soapMessagePart.getAllHeaders();
/* 1431 */     int sz = finalArrayList.size();
/* 1432 */     for (int i = 0; i < sz; i++) {
/* 1433 */       Header h = finalArrayList.get(i);
/* 1434 */       soapPart.addMimeHeader(h.getName(), h.getValue());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initCharsetProperty(ContentType contentType) {
/* 1439 */     String charset = contentType.getParameter("charset");
/* 1440 */     if (charset != null) {
/* 1441 */       ((SOAPPartImpl)getSOAPPart()).setSourceCharsetEncoding(charset);
/* 1442 */       if (!charset.equalsIgnoreCase("utf-8"))
/* 1443 */         setProperty("javax.xml.soap.character-set-encoding", charset); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setLazyAttachments(boolean flag) {
/* 1448 */     this.lazyAttachments = flag;
/*      */   }
/*      */   
/*      */   protected abstract boolean isCorrectSoapVersion(int paramInt);
/*      */   
/*      */   protected abstract String getExpectedContentType();
/*      */   
/*      */   protected abstract String getExpectedAcceptHeader();
/*      */   
/*      */   public abstract SOAPPart getSOAPPart();
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\MessageImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */