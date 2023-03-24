/*     */ package javax.xml.bind.helpers;
/*     */ 
/*     */ import java.net.URL;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.ValidationEventLocator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultValidationEventHandler
/*     */   implements ValidationEventHandler
/*     */ {
/*     */   public boolean handleEvent(ValidationEvent event) {
/*  76 */     if (event == null) {
/*  77 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*  81 */     String severity = null;
/*  82 */     boolean retVal = false;
/*  83 */     switch (event.getSeverity())
/*     */     { case 0:
/*  85 */         severity = Messages.format("DefaultValidationEventHandler.Warning");
/*  86 */         retVal = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 103 */         location = getLocation(event);
/*     */         
/* 105 */         System.out.println(Messages.format("DefaultValidationEventHandler.SeverityMessage", severity, event.getMessage(), location));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 112 */         return retVal;case 1: severity = Messages.format("DefaultValidationEventHandler.Error"); retVal = false; location = getLocation(event); System.out.println(Messages.format("DefaultValidationEventHandler.SeverityMessage", severity, event.getMessage(), location)); return retVal;case 2: severity = Messages.format("DefaultValidationEventHandler.FatalError"); retVal = false; location = getLocation(event); System.out.println(Messages.format("DefaultValidationEventHandler.SeverityMessage", severity, event.getMessage(), location)); return retVal; }  assert false : Messages.format("DefaultValidationEventHandler.UnrecognizedSeverity", Integer.valueOf(event.getSeverity())); String location = getLocation(event); System.out.println(Messages.format("DefaultValidationEventHandler.SeverityMessage", severity, event.getMessage(), location)); return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getLocation(ValidationEvent event) {
/* 120 */     StringBuffer msg = new StringBuffer();
/*     */     
/* 122 */     ValidationEventLocator locator = event.getLocator();
/*     */     
/* 124 */     if (locator != null) {
/*     */       
/* 126 */       URL url = locator.getURL();
/* 127 */       Object obj = locator.getObject();
/* 128 */       Node node = locator.getNode();
/* 129 */       int line = locator.getLineNumber();
/*     */       
/* 131 */       if (url != null || line != -1) {
/* 132 */         msg.append("line " + line);
/* 133 */         if (url != null)
/* 134 */           msg.append(" of " + url); 
/* 135 */       } else if (obj != null) {
/* 136 */         msg.append(" obj: " + obj.toString());
/* 137 */       } else if (node != null) {
/* 138 */         msg.append(" node: " + node.toString());
/*     */       } 
/*     */     } else {
/* 141 */       msg.append(Messages.format("DefaultValidationEventHandler.LocationUnavailable"));
/*     */     } 
/*     */     
/* 144 */     return msg.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\helpers\DefaultValidationEventHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */