/*     */ package javax.xml.soap;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SOAPPart
/*     */   implements Document, Node
/*     */ {
/*     */   public abstract SOAPEnvelope getEnvelope() throws SOAPException;
/*     */   
/*     */   public String getContentId() {
/* 104 */     String[] values = getMimeHeader("Content-Id");
/* 105 */     if (values != null && values.length > 0)
/* 106 */       return values[0]; 
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentLocation() {
/* 118 */     String[] values = getMimeHeader("Content-Location");
/* 119 */     if (values != null && values.length > 0)
/* 120 */       return values[0]; 
/* 121 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentId(String contentId) {
/* 137 */     setMimeHeader("Content-Id", contentId);
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
/*     */ 
/*     */   
/*     */   public void setContentLocation(String contentLocation) {
/* 152 */     setMimeHeader("Content-Location", contentLocation);
/*     */   }
/*     */   
/*     */   public abstract void removeMimeHeader(String paramString);
/*     */   
/*     */   public abstract void removeAllMimeHeaders();
/*     */   
/*     */   public abstract String[] getMimeHeader(String paramString);
/*     */   
/*     */   public abstract void setMimeHeader(String paramString1, String paramString2);
/*     */   
/*     */   public abstract void addMimeHeader(String paramString1, String paramString2);
/*     */   
/*     */   public abstract Iterator getAllMimeHeaders();
/*     */   
/*     */   public abstract Iterator getMatchingMimeHeaders(String[] paramArrayOfString);
/*     */   
/*     */   public abstract Iterator getNonMatchingMimeHeaders(String[] paramArrayOfString);
/*     */   
/*     */   public abstract void setContent(Source paramSource) throws SOAPException;
/*     */   
/*     */   public abstract Source getContent() throws SOAPException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\soap\SOAPPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */