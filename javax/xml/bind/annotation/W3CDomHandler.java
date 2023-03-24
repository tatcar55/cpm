/*     */ package javax.xml.bind.annotation;
/*     */ 
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W3CDomHandler
/*     */   implements DomHandler<Element, DOMResult>
/*     */ {
/*     */   private DocumentBuilder builder;
/*     */   
/*     */   public W3CDomHandler() {
/*  71 */     this.builder = null;
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
/*     */   public W3CDomHandler(DocumentBuilder builder) {
/*  83 */     if (builder == null)
/*  84 */       throw new IllegalArgumentException(); 
/*  85 */     this.builder = builder;
/*     */   }
/*     */   
/*     */   public DocumentBuilder getBuilder() {
/*  89 */     return this.builder;
/*     */   }
/*     */   
/*     */   public void setBuilder(DocumentBuilder builder) {
/*  93 */     this.builder = builder;
/*     */   }
/*     */   
/*     */   public DOMResult createUnmarshaller(ValidationEventHandler errorHandler) {
/*  97 */     if (this.builder == null) {
/*  98 */       return new DOMResult();
/*     */     }
/* 100 */     return new DOMResult(this.builder.newDocument());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getElement(DOMResult r) {
/* 106 */     Node n = r.getNode();
/* 107 */     if (n instanceof Document) {
/* 108 */       return ((Document)n).getDocumentElement();
/*     */     }
/* 110 */     if (n instanceof Element)
/* 111 */       return (Element)n; 
/* 112 */     if (n instanceof org.w3c.dom.DocumentFragment) {
/* 113 */       return (Element)n.getChildNodes().item(0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 118 */     throw new IllegalStateException(n.toString());
/*     */   }
/*     */   
/*     */   public Source marshal(Element element, ValidationEventHandler errorHandler) {
/* 122 */     return new DOMSource(element);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\annotation\W3CDomHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */