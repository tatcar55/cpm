/*     */ package com.sun.xml.rpc.plugins;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.tools.plugin.ToolPlugin;
/*     */ import com.sun.xml.rpc.tools.wscompile.ModelIf;
/*     */ import com.sun.xml.rpc.tools.wscompile.StubHooksIf;
/*     */ import com.sun.xml.rpc.tools.wscompile.TieHooksIf;
/*     */ import com.sun.xml.rpc.tools.wscompile.UsageIf;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityPlugin
/*     */   extends ToolPlugin
/*     */   implements UsageIf, ModelIf, StubHooksIf, TieHooksIf
/*     */ {
/*  82 */   private File securityFile = null;
/*     */   
/*     */   private LocalizableMessageFactory messageFactory;
/*     */   private static final String sec_util = "secPgUtil";
/*     */   private static final String sec_util_pkg = "com.sun.xml.rpc.security";
/*     */   private static final String SECURITY_PROPERTY = "com.sun.xml.rpc.security";
/*     */   
/*     */   public SecurityPlugin() {
/*  90 */     this.messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.plugins.sec");
/*     */   }
/*     */   
/*     */   public Localizable getOptionsUsage() {
/*  94 */     return this.messageFactory.getMessage("sec.usage.options", (Object[])null);
/*     */   }
/*     */   
/*     */   public Localizable getFeaturesUsage() {
/*  98 */     return null;
/*     */   }
/*     */   
/*     */   public Localizable getInternalUsage() {
/* 102 */     return null;
/*     */   }
/*     */   
/*     */   public Localizable getExamplesUsage() {
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   public boolean parseArguments(String[] args, UsageIf.UsageError err) {
/* 110 */     this.securityFile = null;
/*     */     
/* 112 */     for (int i = 0; i < args.length; i++) {
/* 113 */       if (args[i] != null && args[i].equals("-security")) {
/* 114 */         if (i + 1 < args.length) {
/* 115 */           if (this.securityFile != null) {
/* 116 */             err.msg = this.messageFactory.getMessage("sec.duplicateOption", new Object[] { "-security" });
/*     */ 
/*     */ 
/*     */             
/* 120 */             return false;
/*     */           } 
/* 122 */           args[i] = null;
/* 123 */           this.securityFile = new File(args[++i]);
/* 124 */           args[i] = null;
/*     */         } else {
/* 126 */           err.msg = this.messageFactory.getMessage("sec.missingOptionArgument", new Object[] { "-security" });
/*     */ 
/*     */ 
/*     */           
/* 130 */           return false;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 135 */     return true;
/*     */   }
/*     */   
/*     */   public void updateModel(ModelIf.ModelProperty property) {
/* 139 */     if (this.securityFile != null) {
/* 140 */       property.attr = "com.sun.xml.rpc.security";
/*     */       try {
/* 142 */         DataInputStream in = new DataInputStream(new FileInputStream(this.securityFile));
/* 143 */         byte[] xmlBytes = new byte[(int)this.securityFile.length()];
/* 144 */         in.readFully(xmlBytes);
/* 145 */         in.close();
/*     */ 
/*     */         
/* 148 */         DocumentBuilderFactory factory = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*     */         
/* 150 */         factory.setAttribute("http://apache.org/xml/features/validation/dynamic", Boolean.FALSE);
/* 151 */         factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/*     */         
/* 153 */         InputStream is = SecurityPlugin.class.getResourceAsStream("xwssconfig.xsd");
/*     */         
/* 155 */         boolean validate = true;
/*     */         try {
/* 157 */           InputStream isV = SecurityPlugin.class.getResourceAsStream("disablevalidation.xml");
/* 158 */           if (isV != null)
/* 159 */             validate = false; 
/* 160 */         } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */         
/* 164 */         if (validate) {
/* 165 */           factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", is);
/* 166 */           factory.setValidating(true);
/* 167 */           factory.setIgnoringComments(true);
/* 168 */           factory.setNamespaceAware(true);
/* 169 */           DocumentBuilder builder = factory.newDocumentBuilder();
/* 170 */           builder.setErrorHandler(new ErrorHandler(System.out));
/* 171 */           ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlBytes);
/* 172 */           builder.parse(inputStream);
/*     */         } 
/*     */         
/* 175 */         property.value = processString(new String(xmlBytes));
/* 176 */       } catch (Exception e) {
/* 177 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _preHandlingHook(Model model, IndentingWriter p, StubHooksIf.StubHooksState state) throws IOException {
/* 186 */     String config = (String)model.getProperty("com.sun.xml.rpc.security");
/* 187 */     if (config != null) {
/* 188 */       p.pln("//Generated by security plugin");
/* 189 */       writeCheckMustUnderstandInStub(p);
/* 190 */       p.pln("secPgUtil._preHandlingHook(state);");
/* 191 */       p.pln("super._preHandlingHook(state);");
/* 192 */       p.flush();
/* 193 */       state.superDone = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _preRequestSendingHook(Model model, IndentingWriter p, StubHooksIf.StubHooksState state) throws IOException {
/* 201 */     String config = (String)model.getProperty("com.sun.xml.rpc.security");
/* 202 */     if (config != null) {
/* 203 */       p.pln("//Generated by security plugin");
/* 204 */       p.pln("super._preRequestSendingHook(state);");
/* 205 */       p.pln("bool = secPgUtil._preRequestSendingHook(state);");
/* 206 */       p.flush();
/* 207 */       state.superDone = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preHandlingHook(Model model, IndentingWriter p, TieHooksIf.TieHooksState state) throws IOException {
/* 215 */     String config = (String)model.getProperty("com.sun.xml.rpc.security");
/* 216 */     if (config != null) {
/* 217 */       p.pln("//Generated by security plugin");
/* 218 */       writeCheckMustUnderstandInTie(p);
/* 219 */       p.plnI("try {");
/* 220 */       p.pln("if (!secPgUtil.preHandlingHook(state)) return false;");
/* 221 */       p.pOlnI("} catch (javax.xml.rpc.soap.SOAPFaultException sfe) {");
/* 222 */       p.pln("SOAPFaultInfo fault = new SOAPFaultInfo(sfe.getFaultCode(), sfe.getFaultString(), sfe.getFaultActor());");
/* 223 */       p.pln("reportFault(fault, state);");
/* 224 */       p.pln("return false;");
/* 225 */       p.pOln("}");
/* 226 */       p.pln("bool = super.preHandlingHook(state);");
/* 227 */       p.flush();
/* 228 */       state.superDone = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postResponseWritingHook(Model model, IndentingWriter p, TieHooksIf.TieHooksState state) throws IOException {
/* 236 */     String config = (String)model.getProperty("com.sun.xml.rpc.security");
/* 237 */     if (config != null) {
/* 238 */       p.pln("//Generated by security plugin");
/* 239 */       p.pln("super.postResponseWritingHook(state);");
/* 240 */       p.pln("secPgUtil.postResponseWritingHook(state);");
/* 241 */       p.flush();
/* 242 */       state.superDone = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStubStatic(Model model, IndentingWriter p) throws IOException {}
/*     */ 
/*     */   
/*     */   public void writeTieStatic(Model model, IndentingWriter p) throws IOException {}
/*     */ 
/*     */   
/*     */   public void writeStubStatic(Model model, Port port, IndentingWriter p) throws IOException {
/* 255 */     writeStatics(model, port, p, true);
/*     */   }
/*     */   
/*     */   public void writeTieStatic(Model model, Port port, IndentingWriter p) throws IOException {
/* 259 */     writeStatics(model, port, p, false);
/*     */   }
/*     */   
/*     */   private void writeStatics(Model model, Port port, IndentingWriter p, boolean isStub) throws IOException {
/* 263 */     String config = (String)model.getProperty("com.sun.xml.rpc.security");
/* 264 */     if (config != null) {
/* 265 */       config = "\"[version 1.0 FCS]" + config + "\"";
/*     */       
/* 267 */       String decl = "private static com.sun.xml.rpc.security.SecurityPluginUtil secPgUtil;";
/*     */ 
/*     */       
/* 270 */       String block_begin = "static {";
/* 271 */       String block_body1 = "try {";
/* 272 */       String block_body2 = "secPgUtil = new com.sun.xml.rpc.security.SecurityPluginUtil(" + config + ", \"" + port.getName() + "\"" + ", " + Boolean.valueOf(isStub) + ");";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       String block_body3 = "} catch (Exception e) {";
/* 279 */       String block_body4 = "e.printStackTrace();";
/* 280 */       String block_body5 = "throw new RuntimeException(e);";
/* 281 */       String block_body6 = "}";
/* 282 */       String block_end = "}";
/*     */       
/* 284 */       p.pln(decl);
/* 285 */       p.pln();
/* 286 */       p.plnI(block_begin);
/* 287 */       p.plnI(block_body1);
/* 288 */       p.flush();
/* 289 */       char[] array = block_body2.toCharArray();
/* 290 */       for (int i = 0; i < array.length; i++)
/* 291 */         p.write(array[i]); 
/* 292 */       p.newLine();
/* 293 */       p.flush();
/* 294 */       p.pOlnI(block_body3);
/* 295 */       p.pln(block_body4);
/* 296 */       p.pOln(block_body5);
/* 297 */       p.pOln(block_body6);
/* 298 */       p.pOln(block_end);
/* 299 */       p.flush();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeCheckMustUnderstandInStub(IndentingWriter p) throws IOException {
/* 304 */     p.pln("// prepare message for check");
/* 305 */     p.pln("secPgUtil.prepareMessageForMUCheck(state.getResponse().getMessage());");
/* 306 */     p.pln();
/* 307 */     p.pln("com.sun.xml.rpc.client.HandlerChainImpl handlerChain = (com.sun.xml.rpc.client.HandlerChainImpl) state.getHandlerChain();");
/* 308 */     p.plnI("if (handlerChain != null && !handlerChain.isEmpty()) {");
/* 309 */     p.pln("boolean allUnderstood = handlerChain.checkMustUnderstand(state.getMessageContext());");
/* 310 */     p.plnI("if (allUnderstood == false) {");
/* 311 */     p.pln("throw new javax.xml.rpc.soap.SOAPFaultException(com.sun.xml.rpc.encoding.soap.SOAPConstants.FAULT_CODE_MUST_UNDERSTAND,\"SOAP must understand error\",_getActor(),null);");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     p.pOln("}");
/* 317 */     p.pOln("}");
/* 318 */     p.pln();
/* 319 */     p.pln("// restore message after check");
/* 320 */     p.pln("secPgUtil.restoreMessageAfterMUCheck(state.getResponse().getMessage());");
/* 321 */     p.flush();
/*     */   }
/*     */   
/*     */   private void writeCheckMustUnderstandInTie(IndentingWriter p) throws IOException {
/* 325 */     p.pln("// prepare message for check");
/* 326 */     p.pln("secPgUtil.prepareMessageForMUCheck(state.getRequest().getMessage());");
/* 327 */     p.pln();
/* 328 */     p.pln("com.sun.xml.rpc.client.HandlerChainImpl handlerChain = getHandlerChain();");
/* 329 */     p.plnI("if (handlerChain != null && !handlerChain.isEmpty()) {");
/* 330 */     p.pln("boolean allUnderstood = handlerChain.checkMustUnderstand(state.getMessageContext());");
/* 331 */     p.plnI("if (allUnderstood == false) {");
/* 332 */     p.pln("com.sun.xml.rpc.soap.message.SOAPFaultInfo fault = new com.sun.xml.rpc.soap.message.SOAPFaultInfo(com.sun.xml.rpc.encoding.soap.SOAPConstants.FAULT_CODE_MUST_UNDERSTAND,\"SOAP must understand error\", getActor());");
/*     */ 
/*     */     
/* 335 */     p.pln("reportFault(fault, state);");
/* 336 */     p.pln("state.getRequest().setHeaderNotUnderstood(true);");
/* 337 */     p.pln("state.setHandlerFlag(StreamingHandlerState.CALL_NO_HANDLERS);");
/* 338 */     p.pln("return false;");
/* 339 */     p.pOln("}");
/* 340 */     p.pOln("}");
/* 341 */     p.pln();
/* 342 */     p.pln("// restore message after check");
/* 343 */     p.pln("secPgUtil.restoreMessageAfterMUCheck(state.getRequest().getMessage());");
/* 344 */     p.flush();
/*     */   }
/*     */   
/*     */   private String processString(String config) {
/* 348 */     return replaceOthers(replaceOthers(replaceNewLine(config), "\"", "\\\""), " ", " ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String replaceNewLine(String config) {
/* 357 */     return config.replace('\r', ' ').replace('\n', ' ');
/*     */   }
/*     */   
/*     */   private String replaceOthers(String config, String delim, String append) {
/* 361 */     StringTokenizer strTokenizer = new StringTokenizer(config, delim);
/* 362 */     StringBuffer sbuf = new StringBuffer();
/* 363 */     while (strTokenizer.hasMoreTokens()) {
/* 364 */       String tok = strTokenizer.nextToken();
/* 365 */       sbuf.append(tok);
/*     */       
/* 367 */       if (strTokenizer.hasMoreTokens()) {
/* 368 */         sbuf.append(append);
/*     */       }
/*     */     } 
/* 371 */     return sbuf.toString();
/*     */   }
/*     */   
/*     */   private static class ErrorHandler extends DefaultHandler {
/*     */     PrintStream out;
/*     */     
/*     */     public ErrorHandler(PrintStream out) {
/* 378 */       this.out = out;
/*     */     }
/*     */     
/*     */     public void error(SAXParseException e) throws SAXException {
/* 382 */       if (this.out != null)
/* 383 */         this.out.println(e); 
/* 384 */       throw e;
/*     */     }
/*     */     
/*     */     public void warning(SAXParseException e) throws SAXException {
/* 388 */       if (this.out != null) {
/* 389 */         this.out.println(e);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void fatalError(SAXParseException e) throws SAXException {
/* 395 */       if (this.out != null)
/* 396 */         this.out.println(e); 
/* 397 */       throw e;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\plugins\SecurityPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */