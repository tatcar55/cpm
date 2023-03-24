/*     */ package com.sun.xml.ws.fault;
/*     */ 
/*     */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*     */ import com.sun.xml.ws.developer.ServerSideException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementWrapper;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
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
/*     */ @XmlRootElement(namespace = "http://jax-ws.dev.java.net/", name = "exception")
/*     */ final class ExceptionBean
/*     */ {
/*     */   @XmlAttribute(name = "class")
/*     */   public String className;
/*     */   @XmlElement
/*     */   public String message;
/*     */   
/*     */   public static void marshal(Throwable t, Node parent) throws JAXBException {
/*  75 */     Marshaller m = JAXB_CONTEXT.createMarshaller();
/*     */     try {
/*  77 */       m.setProperty("com.sun.xml.bind.namespacePrefixMapper", nsp);
/*  78 */     } catch (PropertyException pe) {}
/*  79 */     m.marshal(new ExceptionBean(t), parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServerSideException unmarshal(Node xml) throws JAXBException {
/*  87 */     ExceptionBean e = (ExceptionBean)JAXB_CONTEXT.createUnmarshaller().unmarshal(xml);
/*  88 */     return e.toException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementWrapper(namespace = "http://jax-ws.dev.java.net/", name = "stackTrace")
/*     */   @XmlElement(namespace = "http://jax-ws.dev.java.net/", name = "frame")
/*  95 */   public List<StackFrame> stackTrace = new ArrayList<StackFrame>();
/*     */ 
/*     */   
/*     */   @XmlElement(namespace = "http://jax-ws.dev.java.net/", name = "cause")
/*     */   public ExceptionBean cause;
/*     */   
/*     */   @XmlAttribute
/* 102 */   public String note = "To disable this feature, set " + SOAPFaultBuilder.CAPTURE_STACK_TRACE_PROPERTY + " system property to false";
/*     */   
/*     */   private static final JAXBContext JAXB_CONTEXT;
/*     */   
/*     */   static final String NS = "http://jax-ws.dev.java.net/";
/*     */   static final String LOCAL_NAME = "exception";
/*     */   
/*     */   ExceptionBean() {}
/*     */   
/*     */   private ExceptionBean(Throwable t) {
/* 112 */     this.className = t.getClass().getName();
/* 113 */     this.message = t.getMessage();
/*     */     
/* 115 */     for (StackTraceElement f : t.getStackTrace()) {
/* 116 */       this.stackTrace.add(new StackFrame(f));
/*     */     }
/*     */     
/* 119 */     Throwable cause = t.getCause();
/* 120 */     if (t != cause && cause != null)
/* 121 */       this.cause = new ExceptionBean(cause); 
/*     */   }
/*     */   
/*     */   private ServerSideException toException() {
/* 125 */     ServerSideException e = new ServerSideException(this.className, this.message);
/* 126 */     if (this.stackTrace != null) {
/* 127 */       StackTraceElement[] ste = new StackTraceElement[this.stackTrace.size()];
/* 128 */       for (int i = 0; i < this.stackTrace.size(); i++)
/* 129 */         ste[i] = ((StackFrame)this.stackTrace.get(i)).toStackTraceElement(); 
/* 130 */       e.setStackTrace(ste);
/*     */     } 
/* 132 */     if (this.cause != null)
/* 133 */       e.initCause((Throwable)this.cause.toException()); 
/* 134 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class StackFrame
/*     */   {
/*     */     @XmlAttribute(name = "class")
/*     */     public String declaringClass;
/*     */     
/*     */     @XmlAttribute(name = "method")
/*     */     public String methodName;
/*     */     
/*     */     @XmlAttribute(name = "file")
/*     */     public String fileName;
/*     */     @XmlAttribute(name = "line")
/*     */     public String lineNumber;
/*     */     
/*     */     StackFrame() {}
/*     */     
/*     */     public StackFrame(StackTraceElement ste) {
/* 154 */       this.declaringClass = ste.getClassName();
/* 155 */       this.methodName = ste.getMethodName();
/* 156 */       this.fileName = ste.getFileName();
/* 157 */       this.lineNumber = box(ste.getLineNumber());
/*     */     }
/*     */     
/*     */     private String box(int i) {
/* 161 */       if (i >= 0) return String.valueOf(i); 
/* 162 */       if (i == -2) return "native"; 
/* 163 */       return "unknown";
/*     */     }
/*     */     
/*     */     private int unbox(String v) {
/*     */       try {
/* 168 */         return Integer.parseInt(v);
/* 169 */       } catch (NumberFormatException e) {
/* 170 */         if ("native".equals(v)) {
/* 171 */           return -2;
/*     */         }
/* 173 */         return -1;
/*     */       } 
/*     */     }
/*     */     
/*     */     private StackTraceElement toStackTraceElement() {
/* 178 */       return new StackTraceElement(this.declaringClass, this.methodName, this.fileName, unbox(this.lineNumber));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStackTraceXml(Element n) {
/* 186 */     return ("exception".equals(n.getLocalName()) && "http://jax-ws.dev.java.net/".equals(n.getNamespaceURI()));
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
/*     */   static {
/*     */     try {
/* 200 */       JAXB_CONTEXT = JAXBContext.newInstance(new Class[] { ExceptionBean.class });
/* 201 */     } catch (JAXBException e) {
/*     */       
/* 203 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/* 207 */   private static final NamespacePrefixMapper nsp = new NamespacePrefixMapper() {
/*     */       public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
/* 209 */         if ("http://jax-ws.dev.java.net/".equals(namespaceUri)) {
/* 210 */           return "";
/*     */         }
/* 212 */         return suggestion;
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\fault\ExceptionBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */