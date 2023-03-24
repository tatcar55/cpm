/*     */ package com.sun.xml.ws.util.pipe;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DumpTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   private final String name;
/*     */   private final PrintStream out;
/*     */   private final XMLOutputFactory staxOut;
/*     */   private static boolean warnStaxUtils;
/*     */   
/*     */   public DumpTube(String name, PrintStream out, Tube next) {
/*  83 */     super(next);
/*  84 */     this.name = name;
/*  85 */     this.out = out;
/*  86 */     this.staxOut = XMLOutputFactory.newInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DumpTube(DumpTube that, TubeCloner cloner) {
/*  94 */     super(that, cloner);
/*  95 */     this.name = that.name;
/*  96 */     this.out = that.out;
/*  97 */     this.staxOut = that.staxOut;
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 102 */     dump("request", request);
/* 103 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 108 */     dump("response", response);
/* 109 */     return super.processResponse(response);
/*     */   }
/*     */   
/*     */   protected void dump(String header, Packet packet) {
/* 113 */     this.out.println("====[" + this.name + ":" + header + "]====");
/* 114 */     if (packet.getMessage() == null) {
/* 115 */       this.out.println("(none)");
/*     */     } else {
/*     */       try {
/* 118 */         XMLStreamWriter writer = this.staxOut.createXMLStreamWriter(new PrintStream(this.out)
/*     */             {
/*     */               public void close() {}
/*     */             });
/*     */ 
/*     */         
/* 124 */         writer = createIndenter(writer);
/* 125 */         packet.getMessage().copy().writeTo(writer);
/* 126 */         writer.close();
/* 127 */       } catch (XMLStreamException e) {
/* 128 */         e.printStackTrace(this.out);
/*     */       } 
/* 130 */     }  this.out.println("============");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLStreamWriter createIndenter(XMLStreamWriter writer) {
/*     */     try {
/* 141 */       Class<?> clazz = getClass().getClassLoader().loadClass("javanet.staxutils.IndentingXMLStreamWriter");
/* 142 */       Constructor<?> c = clazz.getConstructor(new Class[] { XMLStreamWriter.class });
/* 143 */       writer = (XMLStreamWriter)c.newInstance(new Object[] { writer });
/* 144 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 147 */       if (!warnStaxUtils) {
/* 148 */         warnStaxUtils = true;
/* 149 */         this.out.println("WARNING: put stax-utils.jar to the classpath to indent the dump output");
/*     */       } 
/*     */     } 
/* 152 */     return writer;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 157 */     return (AbstractTubeImpl)new DumpTube(this, cloner);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\pipe\DumpTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */