/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import java.io.Writer;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.EndElement;
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
/*     */ public abstract class EventBase
/*     */   implements XMLEvent
/*     */ {
/*     */   protected int _eventType;
/*  34 */   protected Location _location = null;
/*     */ 
/*     */   
/*     */   public EventBase() {}
/*     */ 
/*     */   
/*     */   public EventBase(int eventType) {
/*  41 */     this._eventType = eventType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventType() {
/*  48 */     return this._eventType;
/*     */   }
/*     */   
/*     */   protected void setEventType(int eventType) {
/*  52 */     this._eventType = eventType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStartElement() {
/*  57 */     return (this._eventType == 1);
/*     */   }
/*     */   
/*     */   public boolean isEndElement() {
/*  61 */     return (this._eventType == 2);
/*     */   }
/*     */   
/*     */   public boolean isEntityReference() {
/*  65 */     return (this._eventType == 9);
/*     */   }
/*     */   
/*     */   public boolean isProcessingInstruction() {
/*  69 */     return (this._eventType == 3);
/*     */   }
/*     */   
/*     */   public boolean isStartDocument() {
/*  73 */     return (this._eventType == 7);
/*     */   }
/*     */   
/*     */   public boolean isEndDocument() {
/*  77 */     return (this._eventType == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location getLocation() {
/*  87 */     return this._location;
/*     */   }
/*     */   
/*     */   public void setLocation(Location loc) {
/*  91 */     this._location = loc;
/*     */   }
/*     */   public String getSystemId() {
/*  94 */     if (this._location == null) {
/*  95 */       return "";
/*     */     }
/*  97 */     return this._location.getSystemId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters asCharacters() {
/* 104 */     if (isCharacters()) {
/* 105 */       return (Characters)this;
/*     */     }
/* 107 */     throw new ClassCastException(CommonResourceBundle.getInstance().getString("message.charactersCast", new Object[] { getEventTypeString() }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElement asEndElement() {
/* 114 */     if (isEndElement()) {
/* 115 */       return (EndElement)this;
/*     */     }
/* 117 */     throw new ClassCastException(CommonResourceBundle.getInstance().getString("message.endElementCase", new Object[] { getEventTypeString() }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElement asStartElement() {
/* 125 */     if (isStartElement()) {
/* 126 */       return (StartElement)this;
/*     */     }
/* 128 */     throw new ClassCastException(CommonResourceBundle.getInstance().getString("message.startElementCase", new Object[] { getEventTypeString() }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getSchemaType() {
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAttribute() {
/* 145 */     return (this._eventType == 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCharacters() {
/* 152 */     return (this._eventType == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNamespace() {
/* 159 */     return (this._eventType == 13);
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
/*     */   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getEventTypeString() {
/* 183 */     switch (this._eventType) {
/*     */       case 1:
/* 185 */         return "StartElementEvent";
/*     */       case 2:
/* 187 */         return "EndElementEvent";
/*     */       case 3:
/* 189 */         return "ProcessingInstructionEvent";
/*     */       case 4:
/* 191 */         return "CharacterEvent";
/*     */       case 5:
/* 193 */         return "CommentEvent";
/*     */       case 7:
/* 195 */         return "StartDocumentEvent";
/*     */       case 8:
/* 197 */         return "EndDocumentEvent";
/*     */       case 9:
/* 199 */         return "EntityReferenceEvent";
/*     */       case 10:
/* 201 */         return "AttributeBase";
/*     */       case 11:
/* 203 */         return "DTDEvent";
/*     */       case 12:
/* 205 */         return "CDATA";
/*     */     } 
/* 207 */     return "UNKNOWN_EVENT_TYPE";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\EventBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */