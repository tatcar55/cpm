/*    */ package javanet.staxutils.events;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.events.Namespace;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NamespaceEvent
/*    */   extends AttributeEvent
/*    */   implements Namespace
/*    */ {
/* 49 */   public static final QName DEFAULT_NS_DECL = new QName("http://www.w3.org/2000/xmlns/", "xmlns");
/*    */ 
/*    */ 
/*    */   
/*    */   public NamespaceEvent(String prefix, String nsURI) {
/* 54 */     this(prefix, nsURI, (Location)null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NamespaceEvent(String prefix, String nsURI, Location location) {
/* 60 */     super((prefix != null) ? new QName("http://www.w3.org/2000/xmlns/", prefix, "xmlns") : DEFAULT_NS_DECL, nsURI, location);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 70 */     return 13;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNamespaceURI() {
/* 76 */     return getValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 82 */     String prefix = getName().getLocalPart();
/* 83 */     if (!"xmlns".equals(prefix))
/*    */     {
/* 85 */       return prefix;
/*    */     }
/*    */ 
/*    */     
/* 89 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDefaultNamespaceDeclaration() {
/* 97 */     return "".equals(getPrefix());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\NamespaceEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */