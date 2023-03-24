/*     */ package com.sun.xml.ws.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLInput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLMessage;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOutput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtensionContext;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLBoundPortTypeImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortImpl;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WSDLParserExtensionFacade
/*     */   extends WSDLParserExtension
/*     */ {
/*     */   private final WSDLParserExtension[] extensions;
/*     */   
/*     */   WSDLParserExtensionFacade(WSDLParserExtension... extensions) {
/*  74 */     assert extensions != null;
/*  75 */     this.extensions = extensions;
/*     */   }
/*     */   
/*     */   public void start(WSDLParserExtensionContext context) {
/*  79 */     for (WSDLParserExtension e : this.extensions) {
/*  80 */       e.start(context);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean serviceElements(WSDLService service, XMLStreamReader reader) {
/*  85 */     for (WSDLParserExtension e : this.extensions) {
/*  86 */       if (e.serviceElements(service, reader))
/*  87 */         return true; 
/*     */     } 
/*  89 */     XMLStreamReaderUtil.skipElement(reader);
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   public void serviceAttributes(WSDLService service, XMLStreamReader reader) {
/*  94 */     for (WSDLParserExtension e : this.extensions)
/*  95 */       e.serviceAttributes(service, reader); 
/*     */   }
/*     */   
/*     */   public boolean portElements(WSDLPort port, XMLStreamReader reader) {
/*  99 */     for (WSDLParserExtension e : this.extensions) {
/* 100 */       if (e.portElements(port, reader)) {
/* 101 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 105 */     if (isRequiredExtension(reader)) {
/* 106 */       ((WSDLPortImpl)port).addNotUnderstoodExtension(reader.getName(), getLocator(reader));
/*     */     }
/* 108 */     XMLStreamReaderUtil.skipElement(reader);
/* 109 */     return true;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInput(WSDLOperation op, XMLStreamReader reader) {
/* 113 */     for (WSDLParserExtension e : this.extensions) {
/* 114 */       e.portTypeOperationInput(op, reader);
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutput(WSDLOperation op, XMLStreamReader reader) {
/* 120 */     for (WSDLParserExtension e : this.extensions) {
/* 121 */       e.portTypeOperationOutput(op, reader);
/*     */     }
/* 123 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFault(WSDLOperation op, XMLStreamReader reader) {
/* 127 */     for (WSDLParserExtension e : this.extensions) {
/* 128 */       e.portTypeOperationFault(op, reader);
/*     */     }
/* 130 */     return false;
/*     */   }
/*     */   
/*     */   public void portAttributes(WSDLPort port, XMLStreamReader reader) {
/* 134 */     for (WSDLParserExtension e : this.extensions)
/* 135 */       e.portAttributes(port, reader); 
/*     */   }
/*     */   
/*     */   public boolean definitionsElements(XMLStreamReader reader) {
/* 139 */     for (WSDLParserExtension e : this.extensions) {
/* 140 */       if (e.definitionsElements(reader)) {
/* 141 */         return true;
/*     */       }
/*     */     } 
/* 144 */     XMLStreamReaderUtil.skipElement(reader);
/* 145 */     return true;
/*     */   }
/*     */   
/*     */   public boolean bindingElements(WSDLBoundPortType binding, XMLStreamReader reader) {
/* 149 */     for (WSDLParserExtension e : this.extensions) {
/* 150 */       if (e.bindingElements(binding, reader)) {
/* 151 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 156 */     if (isRequiredExtension(reader)) {
/* 157 */       ((WSDLBoundPortTypeImpl)binding).addNotUnderstoodExtension(reader.getName(), getLocator(reader));
/*     */     }
/*     */     
/* 160 */     XMLStreamReaderUtil.skipElement(reader);
/* 161 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingAttributes(WSDLBoundPortType binding, XMLStreamReader reader) {
/* 165 */     for (WSDLParserExtension e : this.extensions) {
/* 166 */       e.bindingAttributes(binding, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeElements(WSDLPortType portType, XMLStreamReader reader) {
/* 171 */     for (WSDLParserExtension e : this.extensions) {
/* 172 */       if (e.portTypeElements(portType, reader)) {
/* 173 */         return true;
/*     */       }
/*     */     } 
/* 176 */     XMLStreamReaderUtil.skipElement(reader);
/* 177 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeAttributes(WSDLPortType portType, XMLStreamReader reader) {
/* 181 */     for (WSDLParserExtension e : this.extensions) {
/* 182 */       e.portTypeAttributes(portType, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationElements(WSDLOperation operation, XMLStreamReader reader) {
/* 187 */     for (WSDLParserExtension e : this.extensions) {
/* 188 */       if (e.portTypeOperationElements(operation, reader)) {
/* 189 */         return true;
/*     */       }
/*     */     } 
/* 192 */     XMLStreamReaderUtil.skipElement(reader);
/* 193 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeOperationAttributes(WSDLOperation operation, XMLStreamReader reader) {
/* 197 */     for (WSDLParserExtension e : this.extensions) {
/* 198 */       e.portTypeOperationAttributes(operation, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean bindingOperationElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 203 */     for (WSDLParserExtension e : this.extensions) {
/* 204 */       if (e.bindingOperationElements(operation, reader)) {
/* 205 */         return true;
/*     */       }
/*     */     } 
/* 208 */     XMLStreamReaderUtil.skipElement(reader);
/* 209 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingOperationAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 213 */     for (WSDLParserExtension e : this.extensions) {
/* 214 */       e.bindingOperationAttributes(operation, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean messageElements(WSDLMessage msg, XMLStreamReader reader) {
/* 219 */     for (WSDLParserExtension e : this.extensions) {
/* 220 */       if (e.messageElements(msg, reader)) {
/* 221 */         return true;
/*     */       }
/*     */     } 
/* 224 */     XMLStreamReaderUtil.skipElement(reader);
/* 225 */     return true;
/*     */   }
/*     */   
/*     */   public void messageAttributes(WSDLMessage msg, XMLStreamReader reader) {
/* 229 */     for (WSDLParserExtension e : this.extensions) {
/* 230 */       e.messageAttributes(msg, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInputElements(WSDLInput input, XMLStreamReader reader) {
/* 235 */     for (WSDLParserExtension e : this.extensions) {
/* 236 */       if (e.portTypeOperationInputElements(input, reader)) {
/* 237 */         return true;
/*     */       }
/*     */     } 
/* 240 */     XMLStreamReaderUtil.skipElement(reader);
/* 241 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeOperationInputAttributes(WSDLInput input, XMLStreamReader reader) {
/* 245 */     for (WSDLParserExtension e : this.extensions) {
/* 246 */       e.portTypeOperationInputAttributes(input, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutputElements(WSDLOutput output, XMLStreamReader reader) {
/* 251 */     for (WSDLParserExtension e : this.extensions) {
/* 252 */       if (e.portTypeOperationOutputElements(output, reader)) {
/* 253 */         return true;
/*     */       }
/*     */     } 
/* 256 */     XMLStreamReaderUtil.skipElement(reader);
/* 257 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeOperationOutputAttributes(WSDLOutput output, XMLStreamReader reader) {
/* 261 */     for (WSDLParserExtension e : this.extensions) {
/* 262 */       e.portTypeOperationOutputAttributes(output, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFaultElements(WSDLFault fault, XMLStreamReader reader) {
/* 267 */     for (WSDLParserExtension e : this.extensions) {
/* 268 */       if (e.portTypeOperationFaultElements(fault, reader)) {
/* 269 */         return true;
/*     */       }
/*     */     } 
/* 272 */     XMLStreamReaderUtil.skipElement(reader);
/* 273 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeOperationFaultAttributes(WSDLFault fault, XMLStreamReader reader) {
/* 277 */     for (WSDLParserExtension e : this.extensions) {
/* 278 */       e.portTypeOperationFaultAttributes(fault, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean bindingOperationInputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 283 */     for (WSDLParserExtension e : this.extensions) {
/* 284 */       if (e.bindingOperationInputElements(operation, reader)) {
/* 285 */         return true;
/*     */       }
/*     */     } 
/* 288 */     XMLStreamReaderUtil.skipElement(reader);
/* 289 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingOperationInputAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 293 */     for (WSDLParserExtension e : this.extensions) {
/* 294 */       e.bindingOperationInputAttributes(operation, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean bindingOperationOutputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 299 */     for (WSDLParserExtension e : this.extensions) {
/* 300 */       if (e.bindingOperationOutputElements(operation, reader)) {
/* 301 */         return true;
/*     */       }
/*     */     } 
/* 304 */     XMLStreamReaderUtil.skipElement(reader);
/* 305 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingOperationOutputAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 309 */     for (WSDLParserExtension e : this.extensions) {
/* 310 */       e.bindingOperationOutputAttributes(operation, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean bindingOperationFaultElements(WSDLBoundFault fault, XMLStreamReader reader) {
/* 315 */     for (WSDLParserExtension e : this.extensions) {
/* 316 */       if (e.bindingOperationFaultElements(fault, reader)) {
/* 317 */         return true;
/*     */       }
/*     */     } 
/* 320 */     XMLStreamReaderUtil.skipElement(reader);
/* 321 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingOperationFaultAttributes(WSDLBoundFault fault, XMLStreamReader reader) {
/* 325 */     for (WSDLParserExtension e : this.extensions) {
/* 326 */       e.bindingOperationFaultAttributes(fault, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public void finished(WSDLParserExtensionContext context) {
/* 331 */     for (WSDLParserExtension e : this.extensions) {
/* 332 */       e.finished(context);
/*     */     }
/*     */   }
/*     */   
/*     */   public void postFinished(WSDLParserExtensionContext context) {
/* 337 */     for (WSDLParserExtension e : this.extensions) {
/* 338 */       e.postFinished(context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRequiredExtension(XMLStreamReader reader) {
/* 348 */     String required = reader.getAttributeValue("http://schemas.xmlsoap.org/wsdl/", "required");
/* 349 */     if (required != null)
/* 350 */       return Boolean.parseBoolean(required); 
/* 351 */     return false;
/*     */   }
/*     */   
/*     */   private Locator getLocator(XMLStreamReader reader) {
/* 355 */     Location location = reader.getLocation();
/* 356 */     LocatorImpl loc = new LocatorImpl();
/* 357 */     loc.setSystemId(location.getSystemId());
/* 358 */     loc.setLineNumber(location.getLineNumber());
/* 359 */     return loc;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\WSDLParserExtensionFacade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */