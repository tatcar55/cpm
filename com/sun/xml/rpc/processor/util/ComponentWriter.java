/*     */ package com.sun.xml.rpc.processor.util;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.schema.AnnotationComponent;
/*     */ import com.sun.xml.rpc.processor.schema.AttributeDeclarationComponent;
/*     */ import com.sun.xml.rpc.processor.schema.AttributeGroupDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.AttributeUseComponent;
/*     */ import com.sun.xml.rpc.processor.schema.ComplexTypeDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.ComponentVisitor;
/*     */ import com.sun.xml.rpc.processor.schema.ElementDeclarationComponent;
/*     */ import com.sun.xml.rpc.processor.schema.IdentityConstraintDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.InternalSchemaConstants;
/*     */ import com.sun.xml.rpc.processor.schema.ModelGroupComponent;
/*     */ import com.sun.xml.rpc.processor.schema.ModelGroupDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.NotationDeclarationComponent;
/*     */ import com.sun.xml.rpc.processor.schema.ParticleComponent;
/*     */ import com.sun.xml.rpc.processor.schema.SimpleTypeDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.WildcardComponent;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentWriter
/*     */   implements ComponentVisitor
/*     */ {
/*     */   private IndentingWriter _writer;
/*     */   
/*     */   public ComponentWriter(IndentingWriter w) {
/*  59 */     this._writer = w;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(AnnotationComponent component) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(AttributeDeclarationComponent component) throws Exception {
/*  68 */     this._writer.p("ATTRIBUTE ");
/*  69 */     writeName(component.getName());
/*  70 */     this._writer.pln();
/*  71 */     this._writer.pI();
/*     */     
/*  73 */     if (component.getScope() == null) {
/*  74 */       this._writer.pln("SCOPE global");
/*     */     }
/*  76 */     if (component.getTypeDefinition() != null) {
/*  77 */       this._writer.pln("TYPE");
/*  78 */       this._writer.pI();
/*  79 */       component.getTypeDefinition().accept(this);
/*  80 */       this._writer.pO();
/*     */     } 
/*     */     
/*  83 */     this._writer.pO();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(AttributeGroupDefinitionComponent component) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(AttributeUseComponent component) throws Exception {
/*  91 */     this._writer.p("ATTRIBUTE USE ");
/*  92 */     this._writer.pln(component.isRequired() ? "required" : "optional");
/*  93 */     this._writer.pI();
/*  94 */     component.getAttributeDeclaration().accept(this);
/*  95 */     this._writer.pO();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(ComplexTypeDefinitionComponent component) throws Exception {
/* 101 */     this._writer.p("COMPLEX-TYPE ");
/* 102 */     writeName(component.getName());
/* 103 */     this._writer.pln();
/*     */     
/* 105 */     if (component.getName() != null && component.getName().equals(InternalSchemaConstants.QNAME_TYPE_URTYPE)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     this._writer.pI();
/* 113 */     if (component.getBaseTypeDefinition() != null) {
/* 114 */       this._writer.pln("BASE-TYPE");
/* 115 */       this._writer.pI();
/* 116 */       component.getBaseTypeDefinition().accept(this);
/* 117 */       this._writer.pO();
/*     */     } 
/* 119 */     for (Iterator<AttributeUseComponent> iter = component.attributeUses(); iter.hasNext();) {
/* 120 */       ((AttributeUseComponent)iter.next()).accept(this);
/*     */     }
/* 122 */     switch (component.getContentTag()) {
/*     */       case 1:
/* 124 */         this._writer.pln("EMPTY");
/*     */         break;
/*     */       case 2:
/* 127 */         this._writer.pln("SIMPLE");
/* 128 */         component.getSimpleTypeContent().accept(this);
/*     */         break;
/*     */       case 3:
/* 131 */         this._writer.pln("MIXED");
/* 132 */         component.getParticleContent().accept(this);
/*     */         break;
/*     */       case 4:
/* 135 */         this._writer.pln("ELEMENT-ONLY");
/* 136 */         component.getParticleContent().accept(this);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 142 */     this._writer.pO();
/*     */   }
/*     */   
/*     */   public void visit(ElementDeclarationComponent component) throws Exception {
/* 146 */     this._writer.p("ELEMENT ");
/* 147 */     writeName(component.getName());
/* 148 */     this._writer.pln();
/* 149 */     this._writer.pI();
/* 150 */     if (component.getScope() == null) {
/* 151 */       this._writer.pln("SCOPE global");
/*     */     }
/* 153 */     if (component.getTypeDefinition() != null) {
/* 154 */       component.getTypeDefinition().accept(this);
/*     */     }
/* 156 */     this._writer.pO();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(IdentityConstraintDefinitionComponent component) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(ModelGroupComponent component) throws Exception {
/* 164 */     this._writer.p("GROUP ");
/* 165 */     this._writer.p(component.getCompositor().getName());
/* 166 */     this._writer.pln();
/* 167 */     this._writer.pI();
/* 168 */     for (Iterator<ParticleComponent> iter = component.particles(); iter.hasNext(); ) {
/* 169 */       ParticleComponent particle = iter.next();
/* 170 */       particle.accept(this);
/*     */     } 
/* 172 */     this._writer.pO();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(ModelGroupDefinitionComponent component) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(NotationDeclarationComponent component) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(ParticleComponent component) throws Exception {
/* 183 */     this._writer.p("PARTICLE (");
/* 184 */     this._writer.p(Integer.toString(component.getMinOccurs()));
/* 185 */     this._writer.p(", ");
/* 186 */     if (component.isMaxOccursUnbounded()) {
/* 187 */       this._writer.p("UNBOUNDED)");
/*     */     } else {
/* 189 */       this._writer.p(Integer.toString(component.getMaxOccurs()));
/* 190 */       this._writer.p(")");
/*     */     } 
/* 192 */     this._writer.pln();
/* 193 */     this._writer.pI();
/* 194 */     if (component.getModelGroupTerm() != null) {
/* 195 */       component.getModelGroupTerm().accept(this);
/* 196 */     } else if (component.getElementTerm() != null) {
/* 197 */       component.getElementTerm().accept(this);
/*     */     } 
/* 199 */     this._writer.pO();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(SimpleTypeDefinitionComponent component) throws Exception {
/* 205 */     this._writer.p("SIMPLE-TYPE ");
/* 206 */     writeName(component.getName());
/* 207 */     this._writer.pln();
/*     */     
/* 209 */     if (component.getName() != null && component.getName().equals(InternalSchemaConstants.QNAME_TYPE_SIMPLE_URTYPE)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     this._writer.pI();
/* 217 */     this._writer.pO();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(WildcardComponent component) throws Exception {}
/*     */   
/*     */   private void writeName(QName name) throws IOException {
/* 224 */     if (name != null) {
/* 225 */       String nsURI = name.getNamespaceURI();
/* 226 */       if (nsURI.equals("http://www.w3.org/2001/XMLSchema")) {
/* 227 */         this._writer.p("xsd:");
/* 228 */       } else if (nsURI.equals("http://schemas.xmlsoap.org/soap/encoding/")) {
/* 229 */         this._writer.p("soap-enc:");
/*     */       } 
/* 231 */       this._writer.p(name.getLocalPart());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\ComponentWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */