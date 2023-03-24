/*     */ package javanet.staxutils.helpers;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.Comment;
/*     */ import javax.xml.stream.events.DTD;
/*     */ import javax.xml.stream.events.EndDocument;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.EntityDeclaration;
/*     */ import javax.xml.stream.events.EntityReference;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.NotationDeclaration;
/*     */ import javax.xml.stream.events.ProcessingInstruction;
/*     */ import javax.xml.stream.events.StartDocument;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EventMatcher
/*     */ {
/*     */   public static boolean eventsMatch(XMLEvent a, XMLEvent b) {
/*  81 */     if (a == b)
/*     */     {
/*  83 */       return true;
/*     */     }
/*  85 */     if (a == null || b == null)
/*     */     {
/*  87 */       return false;
/*     */     }
/*  89 */     if (a.getEventType() == b.getEventType())
/*     */     {
/*  91 */       switch (a.getEventType()) {
/*     */         
/*     */         case 1:
/*  94 */           return eventsMatch(a.asStartElement(), b.asStartElement());
/*     */         
/*     */         case 2:
/*  97 */           return eventsMatch(a.asEndElement(), b.asEndElement());
/*     */         
/*     */         case 4:
/*     */         case 6:
/*     */         case 12:
/* 102 */           return eventsMatch(a.asCharacters(), b.asCharacters());
/*     */         
/*     */         case 5:
/* 105 */           return eventsMatch((Comment)a, (Comment)b);
/*     */         
/*     */         case 9:
/* 108 */           return eventsMatch((EntityReference)a, (EntityReference)b);
/*     */         
/*     */         case 10:
/* 111 */           return eventsMatch((Attribute)a, (Attribute)b);
/*     */         
/*     */         case 13:
/* 114 */           return eventsMatch((Namespace)a, (Namespace)b);
/*     */         
/*     */         case 7:
/* 117 */           return eventsMatch((StartDocument)a, (StartDocument)b);
/*     */         
/*     */         case 8:
/* 120 */           return eventsMatch((EndDocument)a, (EndDocument)b);
/*     */         
/*     */         case 3:
/* 123 */           return eventsMatch((ProcessingInstruction)a, (ProcessingInstruction)b);
/*     */ 
/*     */         
/*     */         case 11:
/* 127 */           return eventsMatch((DTD)a, (DTD)b);
/*     */         
/*     */         case 15:
/* 130 */           return eventsMatch((EntityDeclaration)a, (EntityDeclaration)b);
/*     */ 
/*     */         
/*     */         case 14:
/* 134 */           return eventsMatch((NotationDeclaration)a, (NotationDeclaration)b);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 141 */     return false;
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
/*     */   public static boolean eventsMatch(Attribute a, Attribute b) {
/* 155 */     if (a == b)
/*     */     {
/* 157 */       return true;
/*     */     }
/* 159 */     if (a == null || b == null)
/*     */     {
/* 161 */       return false;
/*     */     }
/* 163 */     if (a.getName().equals(b.getName()))
/*     */     {
/* 165 */       return a.getValue().equals(b.getValue());
/*     */     }
/*     */ 
/*     */     
/* 169 */     return false;
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
/*     */   
/*     */   public static boolean eventsMatch(Characters a, Characters b) {
/* 186 */     if (a == b)
/*     */     {
/* 188 */       return true;
/*     */     }
/* 190 */     if (a == null || b == null)
/*     */     {
/* 192 */       return false;
/*     */     }
/* 194 */     if (a.getEventType() == b.getEventType())
/*     */     {
/* 196 */       return a.getData().equals(b.getData());
/*     */     }
/*     */ 
/*     */     
/* 200 */     return false;
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
/*     */   public static boolean eventsMatch(Comment a, Comment b) {
/* 216 */     if (a == b)
/*     */     {
/* 218 */       return true;
/*     */     }
/* 220 */     if (a == null || b == null)
/*     */     {
/* 222 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 226 */     return a.getText().equals(b.getText());
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
/*     */   public static boolean eventsMatch(DTD a, DTD b) {
/* 241 */     if (a == b)
/*     */     {
/* 243 */       return true;
/*     */     }
/* 245 */     if (a == null || a == null)
/*     */     {
/* 247 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 252 */     return a.getDocumentTypeDeclaration().equals(b.getDocumentTypeDeclaration());
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
/*     */   
/*     */   public static boolean eventsMatch(EndDocument a, EndDocument b) {
/* 269 */     return (a != null && b != null);
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
/*     */   public static boolean eventsMatch(EndElement a, EndElement b) {
/* 283 */     if (a == b)
/*     */     {
/* 285 */       return true;
/*     */     }
/* 287 */     if (a == null || b == null)
/*     */     {
/* 289 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 293 */     return a.getName().equals(b.getName());
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
/*     */ 
/*     */   
/*     */   public static boolean eventsMatch(EntityDeclaration a, EntityDeclaration b) {
/* 311 */     if (a == b)
/*     */     {
/* 313 */       return true;
/*     */     }
/* 315 */     if (a == null || b == null)
/*     */     {
/* 317 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 322 */     if (!a.getName().equals(b.getName()))
/*     */     {
/* 324 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 329 */     String baseURI = a.getBaseURI();
/* 330 */     if ((baseURI == null) ? (b.getBaseURI() == null) : baseURI.equals(b.getBaseURI())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 339 */       String text = a.getReplacementText();
/* 340 */       if ((text == null) ? (b.getReplacementText() == null) : text.equals(b.getReplacementText())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 349 */         String publicId = a.getPublicId();
/* 350 */         if ((publicId == null) ? (b.getPublicId() == null) : publicId.equals(b.getPublicId())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 359 */           String systemId = a.getSystemId();
/* 360 */           if ((systemId == null) ? (b.getSystemId() == null) : systemId.equals(b.getSystemId())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 369 */             String ndata = a.getNotationName();
/* 370 */             if ((ndata == null) ? (b.getNotationName() == null) : ndata.equals(b.getNotationName()))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 378 */               return true;
/*     */             }
/*     */             return false;
/*     */           } 
/*     */           return false;
/*     */         } 
/*     */         return false;
/*     */       } 
/*     */       return false;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean eventsMatch(EntityReference a, EntityReference b) {
/* 393 */     if (a == b)
/*     */     {
/* 395 */       return true;
/*     */     }
/* 397 */     if (a == null || b == null)
/*     */     {
/* 399 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 403 */     if (a.getName().equals(b.getName()))
/*     */     {
/* 405 */       return eventsMatch(a.getDeclaration(), b.getDeclaration());
/*     */     }
/*     */ 
/*     */     
/* 409 */     return false;
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
/*     */   public static boolean eventsMatch(Namespace a, Namespace b) {
/* 425 */     if (a == b)
/*     */     {
/* 427 */       return true;
/*     */     }
/* 429 */     if (a == null || b == null)
/*     */     {
/* 431 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 435 */     return (a.getPrefix().equals(b.getPrefix()) && a.getNamespaceURI().equals(b.getNamespaceURI()));
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
/*     */   
/*     */   public static boolean eventsMatch(NotationDeclaration a, NotationDeclaration b) {
/* 452 */     if (a == b)
/*     */     {
/* 454 */       return true;
/*     */     }
/* 456 */     if (a == null || b == null)
/*     */     {
/* 458 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 463 */     if (!a.getName().equals(b.getName()))
/*     */     {
/* 465 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 470 */     String publicId = a.getPublicId();
/* 471 */     if ((publicId == null) ? (b.getPublicId() == null) : publicId.equals(b.getPublicId())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 480 */       String systemId = a.getSystemId();
/* 481 */       if ((systemId == null) ? (b.getSystemId() == null) : systemId.equals(b.getSystemId()))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 489 */         return true;
/*     */       }
/*     */       return false;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean eventsMatch(ProcessingInstruction a, ProcessingInstruction b) {
/* 504 */     if (a == b)
/*     */     {
/* 506 */       return true;
/*     */     }
/* 508 */     if (a == null || b == null)
/*     */     {
/* 510 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 514 */     return (a.getTarget().equals(b.getTarget()) && a.getData().equals(b.getData()));
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
/*     */   public static boolean eventsMatch(StartDocument a, StartDocument b) {
/* 530 */     if (a == b)
/*     */     {
/* 532 */       return true;
/*     */     }
/* 534 */     if (a == null || b == null)
/*     */     {
/* 536 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 540 */     if (!a.getCharacterEncodingScheme().equals(b.getCharacterEncodingScheme()))
/*     */     {
/*     */       
/* 543 */       return false;
/*     */     }
/* 545 */     if (a.isStandalone() != b.isStandalone())
/*     */     {
/* 547 */       return false;
/*     */     }
/* 549 */     if (!a.getVersion().equals(b.getVersion()))
/*     */     {
/* 551 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 556 */     return true;
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
/*     */   
/*     */   public static boolean eventsMatch(StartElement a, StartElement b) {
/* 573 */     if (a == b)
/*     */     {
/* 575 */       return true;
/*     */     }
/* 577 */     if (a == null || b == null)
/*     */     {
/* 579 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 583 */     if (!a.getName().equals(b.getName()))
/*     */     {
/* 585 */       return false;
/*     */     }
/* 587 */     if (!matchAttributes(a.getAttributes(), b.getAttributes()))
/*     */     {
/* 589 */       return false;
/*     */     }
/* 591 */     if (!matchNamespaces(a.getNamespaces(), b.getNamespaces()))
/*     */     {
/* 593 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 597 */     return true;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchAttributes(Iterator a, Iterator b) {
/* 616 */     if (a == b)
/*     */     {
/* 618 */       return true;
/*     */     }
/* 620 */     if (a == null || b == null)
/*     */     {
/* 622 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 626 */     while (a.hasNext() && b.hasNext()) {
/*     */       
/* 628 */       Attribute A = a.next();
/* 629 */       Attribute B = b.next();
/*     */       
/* 631 */       if (!eventsMatch(A, B))
/*     */       {
/* 633 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 639 */     return (a.hasNext() == b.hasNext());
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
/*     */   
/*     */   public static boolean matchNamespaces(Iterator a, Iterator b) {
/* 656 */     if (a == b)
/*     */     {
/* 658 */       return true;
/*     */     }
/* 660 */     if (a == null || b == null)
/*     */     {
/* 662 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 666 */     while (a.hasNext() && b.hasNext()) {
/*     */       
/* 668 */       Namespace A = a.next();
/* 669 */       Namespace B = b.next();
/*     */       
/* 671 */       if (!eventsMatch(A, B))
/*     */       {
/* 673 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 679 */     return (a.hasNext() == b.hasNext());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\helpers\EventMatcher.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */