/*     */ package com.sun.xml.rpc.tools.wsdlp;
/*     */ 
/*     */ import com.sun.xml.rpc.util.Debug;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.localization.Localizer;
/*     */ import com.sun.xml.rpc.util.localization.Resources;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceValidator;
/*     */ import com.sun.xml.rpc.wsdl.framework.ParseException;
/*     */ import com.sun.xml.rpc.wsdl.framework.ParserListener;
/*     */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*     */ import com.sun.xml.rpc.wsdl.parser.SOAPEntityReferenceValidator;
/*     */ import com.sun.xml.rpc.wsdl.parser.SchemaParser;
/*     */ import com.sun.xml.rpc.wsdl.parser.SchemaWriter;
/*     */ import com.sun.xml.rpc.wsdl.parser.WSDLParser;
/*     */ import com.sun.xml.rpc.wsdl.parser.WSDLWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.MissingResourceException;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */ {
/*     */   private boolean _succeeded;
/*     */   private Localizer _localizer;
/*     */   private Resources _resources;
/*     */   private String _sourceFilename;
/*     */   private boolean _shouldValidate;
/*     */   private boolean _beVerbose;
/*     */   private boolean _echo;
/*     */   private boolean _parseSchema;
/*     */   private boolean _useWSIBasicProfile;
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/*  62 */       Main tool = new Main();
/*  63 */       tool.run(args);
/*  64 */       System.exit(tool.succeeded() ? 0 : 1);
/*  65 */     } catch (MissingResourceException e) {
/*  66 */       System.err.println("wsdlp: resources not available");
/*  67 */       System.exit(2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Main() throws MissingResourceException {
/*  72 */     this._resources = new Resources("com.sun.xml.rpc.resources.wsdlp");
/*  73 */     this._localizer = new Localizer();
/*     */   }
/*     */   
/*     */   public boolean succeeded() {
/*  77 */     return this._succeeded;
/*     */   }
/*     */   
/*     */   public void run(String[] args) {
/*  81 */     this._succeeded = false;
/*     */     
/*  83 */     if (!processArgs(args)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  89 */       if (this._parseSchema) {
/*  90 */         SchemaParser parser = new SchemaParser();
/*     */         
/*  92 */         if (this._shouldValidate) {
/*  93 */           parser.setFollowImports(true);
/*     */         }
/*     */         
/*  96 */         InputSource inputSource = new InputSource((new File(this._sourceFilename)).toURL().toString());
/*     */         
/*  98 */         SchemaDocument document = parser.parse(inputSource);
/*  99 */         document.validateLocally();
/* 100 */         if (this._shouldValidate)
/*     */         {
/*     */           
/* 103 */           document.validate((EntityReferenceValidator)new SOAPEntityReferenceValidator());
/*     */         }
/*     */         
/* 106 */         if (this._echo) {
/* 107 */           SchemaWriter writer = new SchemaWriter();
/* 108 */           writer.write(document, System.out);
/*     */         } 
/*     */       } else {
/* 111 */         WSDLParser parser = new WSDLParser();
/*     */         
/* 113 */         if (this._beVerbose) {
/* 114 */           parser.addParserListener(new ParserListener()
/*     */               {
/*     */                 public void ignoringExtension(QName name, QName parent)
/*     */                 {
/* 118 */                   System.err.println(Main.this._resources.getString("message.ignoring", new String[] { name.getLocalPart(), name.getNamespaceURI() }));
/*     */                 }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void doneParsingEntity(QName element, Entity entity) {
/* 128 */                   System.err.println(Main.this._resources.getString("message.processed", new String[] { element.getLocalPart(), element.getNamespaceURI() }));
/*     */                 }
/*     */               });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         if (this._shouldValidate) {
/* 138 */           parser.setFollowImports(true);
/*     */         }
/*     */         
/* 141 */         InputSource inputSource = new InputSource((new File(this._sourceFilename)).toURL().toString());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 148 */         WSDLDocument document = parser.parse(inputSource, this._useWSIBasicProfile);
/*     */         
/* 150 */         document.validateLocally();
/*     */         
/* 152 */         if (this._shouldValidate)
/*     */         {
/*     */           
/* 155 */           document.validate((EntityReferenceValidator)new SOAPEntityReferenceValidator());
/*     */         }
/*     */         
/* 158 */         if (this._echo) {
/* 159 */           WSDLWriter writer = new WSDLWriter();
/* 160 */           writer.write(document, System.out);
/*     */         } 
/*     */       } 
/*     */       
/* 164 */       this._succeeded = true;
/* 165 */     } catch (ParseException e) {
/* 166 */       System.err.println(this._resources.getString("error.parsing", this._localizer.localize((Localizable)e)));
/*     */     }
/* 168 */     catch (ValidationException e) {
/* 169 */       System.err.println(this._resources.getString("error.validation", this._localizer.localize((Localizable)e)));
/*     */     }
/* 171 */     catch (JAXRPCExceptionBase e) {
/* 172 */       System.err.println(this._resources.getString("error.generic", this._localizer.localize((Localizable)e)));
/*     */     }
/* 174 */     catch (IOException e) {
/* 175 */       System.err.println(this._resources.getString("error.io", e.toString()));
/* 176 */     } catch (Exception e) {
/* 177 */       System.err.println(this._resources.getString("error.generic", e.toString()));
/*     */       
/* 179 */       if (Debug.enabled()) {
/* 180 */         e.printStackTrace();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean processArgs(String[] args) {
/* 186 */     if (args.length == 0) {
/* 187 */       help();
/* 188 */       return false;
/*     */     } 
/*     */     
/* 191 */     int ac = 0;
/* 192 */     while (ac < args.length) {
/* 193 */       String arg = args[ac];
/* 194 */       ac++;
/* 195 */       if (arg.startsWith("-")) {
/* 196 */         if (arg.equals("-help")) {
/* 197 */           help();
/* 198 */           this._succeeded = true;
/* 199 */           return false;
/* 200 */         }  if (arg.equals("-echo")) {
/* 201 */           this._echo = true; continue;
/* 202 */         }  if (arg.equals("-schema")) {
/* 203 */           this._parseSchema = true; continue;
/* 204 */         }  if (arg.equals("-validate")) {
/* 205 */           this._shouldValidate = true; continue;
/* 206 */         }  if (arg.equals("-v") || arg.equals("-verbose")) {
/* 207 */           this._beVerbose = true; continue;
/* 208 */         }  if (arg.equals("-version")) {
/* 209 */           System.err.println(this._resources.getString("message.version"));
/* 210 */           this._succeeded = true;
/* 211 */           return false;
/* 212 */         }  if (arg.equals("-wsi")) {
/* 213 */           this._useWSIBasicProfile = true; continue;
/*     */         } 
/* 215 */         usageError("error.invalidOption", arg);
/* 216 */         return false;
/*     */       } 
/*     */       
/* 219 */       if (this._sourceFilename != null) {
/* 220 */         usageError("error.multipleFilenames", null);
/* 221 */         return false;
/*     */       } 
/* 223 */       this._sourceFilename = arg;
/*     */     } 
/*     */ 
/*     */     
/* 227 */     if (this._sourceFilename == null) {
/* 228 */       usageError("error.missingFilename", null);
/* 229 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 233 */     return true;
/*     */   }
/*     */   
/*     */   private void help() {
/* 237 */     System.err.println(this._resources.getString("message.header"));
/* 238 */     System.err.println(this._resources.getString("message.usage"));
/*     */   }
/*     */   
/*     */   private void printError(String msg) {
/* 242 */     System.err.println(this._resources.getString("message.name") + ": " + msg);
/*     */   }
/*     */   
/*     */   private void usageError(String key, String arg) {
/* 246 */     printError(this._resources.getString(key, arg));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wsdlp\Main.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */