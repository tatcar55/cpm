/*     */ package com.sun.xml.ws.config.metro.parser;
/*     */ 
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParsedElement
/*     */ {
/*     */   private final String portComponentName;
/*     */   private final String portComponentRefName;
/*     */   private final String operationWsdlName;
/*     */   private final boolean isInputMessage;
/*     */   private final boolean isOutputMessage;
/*     */   private final String faultWsdlName;
/*     */   private final WebServiceFeature webServiceFeature;
/*     */   
/*     */   private ParsedElement(String portComponentName, String portComponentRefName, String operationName, boolean isInputMessage, boolean isOutputMessage, String faultName, WebServiceFeature feature) {
/*  71 */     this.portComponentName = portComponentName;
/*  72 */     this.portComponentRefName = portComponentRefName;
/*  73 */     this.operationWsdlName = operationName;
/*  74 */     this.isInputMessage = isInputMessage;
/*  75 */     this.isOutputMessage = isOutputMessage;
/*  76 */     this.faultWsdlName = faultName;
/*  77 */     this.webServiceFeature = feature;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentElement(String portComponentName, WebServiceFeature feature) {
/*  82 */     return new ParsedElement(portComponentName, null, null, false, false, null, feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentRefElement(String portComponentRefName, WebServiceFeature feature) {
/*  87 */     return new ParsedElement(null, portComponentRefName, null, false, false, null, feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentOperationElement(String portComponentName, String operationWsdlName, WebServiceFeature feature) {
/*  92 */     return new ParsedElement(portComponentName, null, operationWsdlName, false, false, null, feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentRefOperationElement(String portComponentRefName, String operationWsdlName, WebServiceFeature feature) {
/*  97 */     return new ParsedElement(null, portComponentRefName, operationWsdlName, false, false, null, feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentInputElement(String portComponentName, String operationWsdlName, WebServiceFeature feature) {
/* 102 */     return new ParsedElement(portComponentName, null, operationWsdlName, true, false, null, feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentRefInputElement(String portComponentRefName, String operationWsdlName, WebServiceFeature feature) {
/* 107 */     return new ParsedElement(null, portComponentRefName, operationWsdlName, true, false, null, feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentOutputElement(String portComponentName, String operationWsdlName, WebServiceFeature feature) {
/* 112 */     return new ParsedElement(portComponentName, null, operationWsdlName, false, true, null, feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentRefOutputElement(String portComponentRefName, String operationWsdlName, WebServiceFeature feature) {
/* 117 */     return new ParsedElement(null, portComponentRefName, operationWsdlName, false, true, null, feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentFaultElement(String portComponentName, String operationWsdlName, String faultWsdlName, WebServiceFeature feature) {
/* 122 */     return new ParsedElement(portComponentName, null, operationWsdlName, false, false, faultWsdlName, feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedElement createPortComponentRefFaultElement(String portComponentRefName, String operationWsdlName, String faultWsdlName, WebServiceFeature feature) {
/* 127 */     return new ParsedElement(null, portComponentRefName, operationWsdlName, false, false, faultWsdlName, feature);
/*     */   }
/*     */   
/*     */   public String getPortComponentName() {
/* 131 */     return this.portComponentName;
/*     */   }
/*     */   
/*     */   public String getPortComponentRefName() {
/* 135 */     return this.portComponentRefName;
/*     */   }
/*     */   
/*     */   public String getOperationWsdlName() {
/* 139 */     return this.operationWsdlName;
/*     */   }
/*     */   
/*     */   public String getFaultWsdlName() {
/* 143 */     return this.faultWsdlName;
/*     */   }
/*     */   
/*     */   public WebServiceFeature getWebServiceFeature() {
/* 147 */     return this.webServiceFeature;
/*     */   }
/*     */   
/*     */   public boolean isInputMessage() {
/* 151 */     return this.isInputMessage;
/*     */   }
/*     */   
/*     */   public boolean isOutputMessage() {
/* 155 */     return this.isOutputMessage;
/*     */   }
/*     */   
/*     */   public boolean isPortComponent() {
/* 159 */     return (this.portComponentName != null && this.operationWsdlName == null);
/*     */   }
/*     */   
/*     */   public boolean isPortComponentRef() {
/* 163 */     return (this.portComponentRefName != null && this.operationWsdlName == null);
/*     */   }
/*     */   
/*     */   public boolean isPortComponentOperation() {
/* 167 */     return (this.portComponentName != null && this.operationWsdlName != null && !this.isInputMessage && !this.isOutputMessage && this.faultWsdlName == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPortComponentRefOperation() {
/* 172 */     return (this.portComponentRefName != null && this.operationWsdlName != null && !this.isInputMessage && !this.isOutputMessage && this.faultWsdlName == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPortComponentInput() {
/* 177 */     return (this.portComponentName != null && this.operationWsdlName != null && this.isInputMessage);
/*     */   }
/*     */   
/*     */   public boolean isPortComponentOutput() {
/* 181 */     return (this.portComponentName != null && this.operationWsdlName != null && this.isOutputMessage);
/*     */   }
/*     */   
/*     */   public boolean isPortComponentFault() {
/* 185 */     return (this.portComponentName != null && this.operationWsdlName != null && this.faultWsdlName != null);
/*     */   }
/*     */   
/*     */   public boolean isPortComponentRefInput() {
/* 189 */     return (this.portComponentRefName != null && this.operationWsdlName != null && this.isInputMessage);
/*     */   }
/*     */   
/*     */   public boolean isPortComponentRefOutput() {
/* 193 */     return (this.portComponentRefName != null && this.operationWsdlName != null && this.isOutputMessage);
/*     */   }
/*     */   
/*     */   public boolean isPortComponentRefFault() {
/* 197 */     return (this.portComponentRefName != null && this.operationWsdlName != null && this.faultWsdlName != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 202 */     StringBuilder text = new StringBuilder("ParsedElement: ");
/* 203 */     if (isPortComponent()) {
/* 204 */       text.append("port component [");
/* 205 */       text.append(this.portComponentName);
/* 206 */       text.append(']');
/*     */     }
/* 208 */     else if (isPortComponentRef()) {
/* 209 */       text.append("port component ref [");
/* 210 */       text.append(this.portComponentRefName);
/* 211 */       text.append(']');
/*     */     }
/* 213 */     else if (isPortComponentOperation()) {
/* 214 */       text.append("port component operation [");
/* 215 */       text.append(this.portComponentName);
/* 216 */       text.append(", ");
/* 217 */       text.append(this.operationWsdlName);
/* 218 */       text.append(']');
/*     */     }
/* 220 */     else if (isPortComponentRefOperation()) {
/* 221 */       text.append("port component ref operation [");
/* 222 */       text.append(this.portComponentRefName);
/* 223 */       text.append(", ");
/* 224 */       text.append(this.operationWsdlName);
/* 225 */       text.append(']');
/*     */     }
/* 227 */     else if (isPortComponentInput()) {
/* 228 */       text.append("port component input [");
/* 229 */       text.append(this.portComponentName);
/* 230 */       text.append(", ");
/* 231 */       text.append(this.operationWsdlName);
/* 232 */       text.append(']');
/*     */     }
/* 234 */     else if (isPortComponentRefInput()) {
/* 235 */       text.append("port component ref input [");
/* 236 */       text.append(this.portComponentName);
/* 237 */       text.append(", ");
/* 238 */       text.append(this.operationWsdlName);
/* 239 */       text.append(']');
/*     */     }
/* 241 */     else if (isPortComponentOutput()) {
/* 242 */       text.append("port component output [");
/* 243 */       text.append(this.portComponentName);
/* 244 */       text.append(", ");
/* 245 */       text.append(this.operationWsdlName);
/* 246 */       text.append(']');
/*     */     }
/* 248 */     else if (isPortComponentRefOutput()) {
/* 249 */       text.append("port component ref output [");
/* 250 */       text.append(this.portComponentName);
/* 251 */       text.append(", ");
/* 252 */       text.append(this.operationWsdlName);
/* 253 */       text.append(']');
/*     */     }
/* 255 */     else if (isPortComponentFault()) {
/* 256 */       text.append("port component fault [");
/* 257 */       text.append(this.portComponentName);
/* 258 */       text.append(", ");
/* 259 */       text.append(this.operationWsdlName);
/* 260 */       text.append(", ");
/* 261 */       text.append(this.faultWsdlName);
/* 262 */       text.append(']');
/*     */     }
/* 264 */     else if (isPortComponentRefFault()) {
/* 265 */       text.append("port component ref fault [");
/* 266 */       text.append(this.portComponentRefName);
/* 267 */       text.append(", ");
/* 268 */       text.append(this.operationWsdlName);
/* 269 */       text.append(", ");
/* 270 */       text.append(this.faultWsdlName);
/* 271 */       text.append(']');
/*     */     } else {
/*     */       
/* 274 */       text.append("unknown element");
/*     */     } 
/* 276 */     text.append(" -> [");
/* 277 */     text.append(this.webServiceFeature);
/* 278 */     text.append(']');
/* 279 */     return text.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\parser\ParsedElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */