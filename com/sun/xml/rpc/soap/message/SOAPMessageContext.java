/*     */ package com.sun.xml.rpc.soap.message;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.MessageFactoryImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.MessageImpl;
/*     */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*     */ import com.sun.xml.rpc.spi.runtime.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.util.NullIterator;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPMessageContext
/*     */   implements SOAPMessageContext
/*     */ {
/*     */   public String[] getRoles() {
/*  66 */     return this.roles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoles(String[] roles) {
/*  73 */     this.roles = roles;
/*     */   }
/*     */   
/*     */   public SOAPMessage getMessage() {
/*  77 */     return this._message;
/*     */   }
/*     */   
/*     */   public void setMessage(SOAPMessage message) {
/*  81 */     this._message = message;
/*     */   }
/*     */   
/*     */   public boolean isFailure() {
/*  85 */     return this._failure;
/*     */   }
/*     */   
/*     */   public void setFailure(boolean b) {
/*  89 */     this._failure = b;
/*     */   }
/*     */   
/*  92 */   int currentHandler = -1; private SOAPMessage _message; private boolean _failure; private Map _properties;
/*     */   
/*     */   public void setCurrentHandler(int i) {
/*  95 */     this.currentHandler = i;
/*     */   }
/*     */   private String[] roles; private static MessageFactory _messageFactory; private static final String DEFAULT_SERVER_ERROR_ENVELOPE = "<?xml version='1.0' encoding='UTF-8'?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Body><env:Fault><faultcode>env:Server</faultcode><faultstring>Internal server error</faultstring></env:Fault></env:Body></env:Envelope>";
/*     */   public int getCurrentHandler() {
/*  99 */     return this.currentHandler;
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) {
/* 103 */     if (this._properties == null) {
/* 104 */       return null;
/*     */     }
/* 106 */     return this._properties.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) {
/* 111 */     if (this._properties == null) {
/* 112 */       this._properties = new HashMap<Object, Object>();
/*     */     }
/*     */     
/* 115 */     this._properties.put(name, value);
/*     */   }
/*     */   
/*     */   public void removeProperty(String name) {
/* 119 */     if (this._properties != null) {
/* 120 */       this._properties.remove(name);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean containsProperty(String name) {
/* 125 */     if (this._properties == null) {
/* 126 */       return false;
/*     */     }
/* 128 */     return this._properties.containsKey(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator getPropertyNames() {
/* 133 */     if (this._properties == null) {
/* 134 */       return (Iterator)NullIterator.getInstance();
/*     */     }
/* 136 */     return this._properties.keySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage createMessage() {
/* 141 */     return createMessage(false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage createMessage(boolean useFastInfoset, boolean acceptFastInfoset) {
/*     */     try {
/* 148 */       MessageFactory msgFactory = getMessageFactory();
/* 149 */       if (useFastInfoset || acceptFastInfoset) {
/*     */         
/* 151 */         MessageFactoryImpl msgFactoryImpl = (MessageFactoryImpl)msgFactory;
/* 152 */         return msgFactoryImpl.createMessage(useFastInfoset, acceptFastInfoset);
/*     */       } 
/* 154 */       return msgFactory.createMessage();
/*     */     }
/* 156 */     catch (SOAPException e) {
/* 157 */       throw new SOAPMsgCreateException("soap.msg.create.err", new Object[] { e });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage createMessage(MimeHeaders headers, InputStream in) throws IOException {
/*     */     try {
/* 167 */       return getMessageFactory().createMessage(headers, in);
/* 168 */     } catch (SOAPException e) {
/* 169 */       throw new SOAPMsgCreateException("soap.msg.create.err", new Object[] { e });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInternalServerErrorResponse() {
/*     */     try {
/* 177 */       setFailure(true);
/* 178 */       SOAPMessage message = createMessage();
/* 179 */       message.getSOAPPart().setContent(new StreamSource((InputStream)XmlUtil.getUTF8ByteInputStream("<?xml version='1.0' encoding='UTF-8'?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Body><env:Fault><faultcode>env:Server</faultcode><faultstring>Internal server error</faultstring></env:Fault></env:Body></env:Envelope>")));
/*     */ 
/*     */ 
/*     */       
/* 183 */       setMessage(message);
/* 184 */     } catch (SOAPException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeSimpleErrorResponse(QName faultCode, String faultString) {
/*     */     try {
/* 191 */       setFailure(true);
/* 192 */       SOAPMessage message = createMessage();
/* 193 */       ByteArrayOutputStream bufferedStream = new ByteArrayOutputStream();
/* 194 */       Writer writer = new OutputStreamWriter(bufferedStream, "UTF-8");
/* 195 */       writer.write("<?xml version='1.0' encoding='UTF-8'?>\n<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Body><env:Fault><faultcode>env:");
/*     */ 
/*     */ 
/*     */       
/* 199 */       writer.write(faultCode.getLocalPart());
/* 200 */       writer.write("</faultcode><faultstring>");
/* 201 */       writer.write(faultString);
/* 202 */       writer.write("</faultstring></env:Fault></env:Body></env:Envelope>");
/*     */       
/* 204 */       writer.close();
/* 205 */       byte[] data = bufferedStream.toByteArray();
/* 206 */       message.getSOAPPart().setContent(new StreamSource((InputStream)new ByteInputStream(data, data.length)));
/*     */       
/* 208 */       setMessage(message);
/* 209 */     } catch (Exception e) {
/* 210 */       writeInternalServerErrorResponse();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static MessageFactory getMessageFactory() {
/*     */     try {
/* 216 */       if (_messageFactory == null) {
/* 217 */         _messageFactory = MessageFactory.newInstance();
/*     */       }
/* 219 */     } catch (SOAPException e) {
/* 220 */       throw new SOAPMsgFactoryCreateException("soap.msg.factory.create.err", new Object[] { e });
/*     */     } 
/*     */ 
/*     */     
/* 224 */     return _messageFactory;
/*     */   }
/*     */   
/*     */   public boolean isFastInfoset() {
/*     */     try {
/* 229 */       return ((MessageImpl)this._message).isFastInfoset();
/*     */     }
/* 231 */     catch (Exception e) {
/* 232 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean acceptFastInfoset() {
/*     */     try {
/* 238 */       return ((MessageImpl)this._message).acceptFastInfoset();
/*     */     }
/* 240 */     catch (Exception e) {
/* 241 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\message\SOAPMessageContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */