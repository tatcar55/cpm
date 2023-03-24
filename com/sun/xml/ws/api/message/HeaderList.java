/*     */ package com.sun.xml.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.binding.SOAPBindingImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HeaderList
/*     */   extends ArrayList<Header>
/*     */   implements MessageHeaders
/*     */ {
/*     */   private static final long serialVersionUID = -6358045781349627237L;
/*     */   private int understoodBits;
/* 144 */   private BitSet moreUnderstoodBits = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPVersion soapVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderList(SOAPVersion soapVersion) {
/* 162 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderList(HeaderList that) {
/* 169 */     super(that);
/* 170 */     this.understoodBits = that.understoodBits;
/* 171 */     if (that.moreUnderstoodBits != null) {
/* 172 */       this.moreUnderstoodBits = (BitSet)that.moreUnderstoodBits.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 181 */     return super.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void addAll(Header... headers) {
/* 190 */     addAll(Arrays.asList(headers));
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
/*     */   public Header get(int index) {
/* 203 */     return super.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void understood(int index) {
/* 212 */     if (index >= size()) {
/* 213 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/*     */     
/* 216 */     if (index < 32) {
/* 217 */       this.understoodBits |= 1 << index;
/*     */     } else {
/* 219 */       if (this.moreUnderstoodBits == null) {
/* 220 */         this.moreUnderstoodBits = new BitSet();
/*     */       }
/* 222 */       this.moreUnderstoodBits.set(index - 32);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(int index) {
/* 232 */     if (index >= size()) {
/* 233 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/*     */     
/* 236 */     if (index < 32) {
/* 237 */       return (this.understoodBits == (this.understoodBits | 1 << index));
/*     */     }
/* 239 */     if (this.moreUnderstoodBits == null) {
/* 240 */       return false;
/*     */     }
/* 242 */     return this.moreUnderstoodBits.get(index - 32);
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
/*     */   
/*     */   public void understood(@NotNull Header header) {
/* 262 */     int sz = size();
/* 263 */     for (int i = 0; i < sz; i++) {
/* 264 */       if (get(i) == header) {
/* 265 */         understood(i);
/*     */         return;
/*     */       } 
/*     */     } 
/* 269 */     throw new IllegalArgumentException();
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
/*     */   @Nullable
/*     */   public Header get(@NotNull String nsUri, @NotNull String localName, boolean markAsUnderstood) {
/* 282 */     int len = size();
/* 283 */     for (int i = 0; i < len; i++) {
/* 284 */       Header h = get(i);
/* 285 */       if (h.getLocalPart().equals(localName) && h.getNamespaceURI().equals(nsUri)) {
/* 286 */         if (markAsUnderstood) {
/* 287 */           understood(i);
/*     */         }
/* 289 */         return h;
/*     */       } 
/*     */     } 
/* 292 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Header get(String nsUri, String localName) {
/* 300 */     return get(nsUri, localName, true);
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
/*     */   @Nullable
/*     */   public Header get(@NotNull QName name, boolean markAsUnderstood) {
/* 314 */     return get(name.getNamespaceURI(), name.getLocalPart(), markAsUnderstood);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Header get(@NotNull QName name) {
/* 324 */     return get(name, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders(String nsUri, String localName) {
/* 332 */     return getHeaders(nsUri, localName, true);
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
/*     */   @NotNull
/*     */   public Iterator<Header> getHeaders(@NotNull final String nsUri, @NotNull final String localName, final boolean markAsUnderstood) {
/* 349 */     return new Iterator<Header>()
/*     */       {
/* 351 */         int idx = 0;
/*     */         
/*     */         Header next;
/*     */         
/*     */         public boolean hasNext() {
/* 356 */           if (this.next == null) {
/* 357 */             fetch();
/*     */           }
/* 359 */           return (this.next != null);
/*     */         }
/*     */ 
/*     */         
/*     */         public Header next() {
/* 364 */           if (this.next == null) {
/* 365 */             fetch();
/* 366 */             if (this.next == null) {
/* 367 */               throw new NoSuchElementException();
/*     */             }
/*     */           } 
/*     */           
/* 371 */           if (markAsUnderstood) {
/* 372 */             assert HeaderList.this.get(this.idx - 1) == this.next;
/* 373 */             HeaderList.this.understood(this.idx - 1);
/*     */           } 
/*     */           
/* 376 */           Header r = this.next;
/* 377 */           this.next = null;
/* 378 */           return r;
/*     */         }
/*     */         
/*     */         private void fetch() {
/* 382 */           while (this.idx < HeaderList.this.size()) {
/* 383 */             Header h = HeaderList.this.get(this.idx++);
/* 384 */             if (h.getLocalPart().equals(localName) && h.getNamespaceURI().equals(nsUri)) {
/* 385 */               this.next = h;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 393 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Iterator<Header> getHeaders(@NotNull QName headerName, boolean markAsUnderstood) {
/* 405 */     return getHeaders(headerName.getNamespaceURI(), headerName.getLocalPart(), markAsUnderstood);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Iterator<Header> getHeaders(@NotNull String nsUri) {
/* 415 */     return getHeaders(nsUri, true);
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
/*     */   @NotNull
/*     */   public Iterator<Header> getHeaders(@NotNull final String nsUri, final boolean markAsUnderstood) {
/* 433 */     return new Iterator<Header>()
/*     */       {
/* 435 */         int idx = 0;
/*     */         
/*     */         Header next;
/*     */         
/*     */         public boolean hasNext() {
/* 440 */           if (this.next == null) {
/* 441 */             fetch();
/*     */           }
/* 443 */           return (this.next != null);
/*     */         }
/*     */ 
/*     */         
/*     */         public Header next() {
/* 448 */           if (this.next == null) {
/* 449 */             fetch();
/* 450 */             if (this.next == null) {
/* 451 */               throw new NoSuchElementException();
/*     */             }
/*     */           } 
/*     */           
/* 455 */           if (markAsUnderstood) {
/* 456 */             assert HeaderList.this.get(this.idx - 1) == this.next;
/* 457 */             HeaderList.this.understood(this.idx - 1);
/*     */           } 
/*     */           
/* 460 */           Header r = this.next;
/* 461 */           this.next = null;
/* 462 */           return r;
/*     */         }
/*     */         
/*     */         private void fetch() {
/* 466 */           while (this.idx < HeaderList.this.size()) {
/* 467 */             Header h = HeaderList.this.get(this.idx++);
/* 468 */             if (h.getNamespaceURI().equals(nsUri)) {
/* 469 */               this.next = h;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 477 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
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
/*     */   public String getTo(AddressingVersion av, SOAPVersion sv) {
/* 494 */     return AddressingUtils.getTo(this, av, sv);
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
/*     */   public String getAction(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 509 */     return AddressingUtils.getAction(this, av, sv);
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
/*     */   public WSEndpointReference getReplyTo(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 524 */     return AddressingUtils.getReplyTo(this, av, sv);
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
/*     */   public WSEndpointReference getFaultTo(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 539 */     return AddressingUtils.getFaultTo(this, av, sv);
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
/*     */   public String getMessageID(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 554 */     return AddressingUtils.getMessageID(this, av, sv);
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
/*     */   public String getRelatesTo(@NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 569 */     return AddressingUtils.getRelatesTo(this, av, sv);
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
/*     */ 
/*     */   
/*     */   public void fillRequestAddressingHeaders(Packet packet, AddressingVersion av, SOAPVersion sv, boolean oneway, String action, boolean mustUnderstand) {
/* 590 */     AddressingUtils.fillRequestAddressingHeaders(this, packet, av, sv, oneway, action, mustUnderstand);
/*     */   }
/*     */   
/*     */   public void fillRequestAddressingHeaders(Packet packet, AddressingVersion av, SOAPVersion sv, boolean oneway, String action) {
/* 594 */     AddressingUtils.fillRequestAddressingHeaders(this, packet, av, sv, oneway, action);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRequestAddressingHeaders(WSDLPort wsdlPort, @NotNull WSBinding binding, Packet packet) {
/* 617 */     AddressingUtils.fillRequestAddressingHeaders(this, wsdlPort, binding, packet);
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
/*     */   public boolean add(Header header) {
/* 633 */     return super.add(header);
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
/*     */   @Nullable
/*     */   public Header remove(@NotNull String nsUri, @NotNull String localName) {
/* 647 */     int len = size();
/* 648 */     for (int i = 0; i < len; i++) {
/* 649 */       Header h = get(i);
/* 650 */       if (h.getLocalPart().equals(localName) && h.getNamespaceURI().equals(nsUri)) {
/* 651 */         return remove(i);
/*     */       }
/*     */     } 
/* 654 */     return null;
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
/*     */   public boolean addOrReplace(Header header) {
/* 670 */     for (int i = 0; i < size(); i++) {
/* 671 */       Header hdr = get(i);
/* 672 */       if (hdr.getNamespaceURI().equals(header.getNamespaceURI()) && hdr.getLocalPart().equals(header.getLocalPart())) {
/*     */ 
/*     */ 
/*     */         
/* 676 */         removeInternal(i);
/* 677 */         addInternal(i, header);
/* 678 */         return true;
/*     */       } 
/*     */     } 
/* 681 */     return add(header);
/*     */   }
/*     */   
/*     */   protected void addInternal(int index, Header header) {
/* 685 */     add(index, (E)header);
/*     */   }
/*     */   
/*     */   protected Header removeInternal(int index) {
/* 689 */     return super.remove(index);
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
/*     */   @Nullable
/*     */   public Header remove(@NotNull QName name) {
/* 703 */     return remove(name.getNamespaceURI(), name.getLocalPart());
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
/*     */   public Header remove(int index) {
/* 715 */     removeUnderstoodBit(index);
/* 716 */     return super.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeUnderstoodBit(int index) {
/* 726 */     assert index < size();
/*     */     
/* 728 */     if (index < 32) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 747 */       int shiftedUpperBits = this.understoodBits >>> -31 + index << index;
/* 748 */       int lowerBits = this.understoodBits << -index >>> 31 - index >>> 1;
/* 749 */       this.understoodBits = shiftedUpperBits | lowerBits;
/*     */       
/* 751 */       if (this.moreUnderstoodBits != null && this.moreUnderstoodBits.cardinality() > 0) {
/* 752 */         if (this.moreUnderstoodBits.get(0)) {
/* 753 */           this.understoodBits |= Integer.MIN_VALUE;
/*     */         }
/*     */         
/* 756 */         this.moreUnderstoodBits.clear(0); int i;
/* 757 */         for (i = this.moreUnderstoodBits.nextSetBit(1); i > 0; i = this.moreUnderstoodBits.nextSetBit(i + 1)) {
/* 758 */           this.moreUnderstoodBits.set(i - 1);
/* 759 */           this.moreUnderstoodBits.clear(i);
/*     */         } 
/*     */       } 
/* 762 */     } else if (this.moreUnderstoodBits != null && this.moreUnderstoodBits.cardinality() > 0) {
/* 763 */       index -= 32;
/* 764 */       this.moreUnderstoodBits.clear(index);
/* 765 */       for (int i = this.moreUnderstoodBits.nextSetBit(index); i >= 1; i = this.moreUnderstoodBits.nextSetBit(i + 1)) {
/* 766 */         this.moreUnderstoodBits.set(i - 1);
/* 767 */         this.moreUnderstoodBits.clear(i);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 772 */     if (size() - 1 <= 33 && this.moreUnderstoodBits != null) {
/* 773 */       this.moreUnderstoodBits = null;
/*     */     }
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
/*     */   public boolean remove(Object o) {
/* 792 */     if (o != null) {
/* 793 */       for (int index = 0; index < size(); index++) {
/* 794 */         if (o.equals(get(index))) {
/* 795 */           remove(index);
/* 796 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 801 */     return false;
/*     */   }
/*     */   
/*     */   public Header remove(Header h) {
/* 805 */     if (remove(h)) {
/* 806 */       return h;
/*     */     }
/* 808 */     return null;
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
/*     */   public static HeaderList copy(HeaderList original) {
/* 821 */     if (original == null) {
/* 822 */       return null;
/*     */     }
/* 824 */     return new HeaderList(original);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readResponseAddressingHeaders(WSDLPort wsdlPort, WSBinding binding) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void understood(QName name) {
/* 836 */     get(name, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void understood(String nsUri, String localName) {
/* 841 */     get(nsUri, localName, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<QName> getUnderstoodHeaders() {
/* 846 */     Set<QName> understoodHdrs = new HashSet<QName>();
/* 847 */     for (int i = 0; i < size(); i++) {
/* 848 */       if (isUnderstood(i)) {
/* 849 */         Header header = get(i);
/* 850 */         understoodHdrs.add(new QName(header.getNamespaceURI(), header.getLocalPart()));
/*     */       } 
/*     */     } 
/* 853 */     return understoodHdrs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(Header header) {
/* 859 */     return isUnderstood(header.getNamespaceURI(), header.getLocalPart());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(String nsUri, String localName) {
/* 864 */     for (int i = 0; i < size(); i++) {
/* 865 */       Header h = get(i);
/* 866 */       if (h.getLocalPart().equals(localName) && h.getNamespaceURI().equals(nsUri)) {
/* 867 */         return isUnderstood(i);
/*     */       }
/*     */     } 
/* 870 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderstood(QName name) {
/* 875 */     return isUnderstood(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<QName> getNotUnderstoodHeaders(Set<String> roles, Set<QName> knownHeaders, WSBinding binding) {
/* 880 */     Set<QName> notUnderstoodHeaders = null;
/* 881 */     if (roles == null) {
/* 882 */       roles = new HashSet<String>();
/*     */     }
/* 884 */     SOAPVersion effectiveSoapVersion = getEffectiveSOAPVersion(binding);
/* 885 */     roles.add(effectiveSoapVersion.implicitRole);
/* 886 */     for (int i = 0; i < size(); i++) {
/* 887 */       if (!isUnderstood(i)) {
/* 888 */         Header header = get(i);
/* 889 */         if (!header.isIgnorable(effectiveSoapVersion, roles)) {
/* 890 */           QName qName = new QName(header.getNamespaceURI(), header.getLocalPart());
/* 891 */           if (binding == null) {
/*     */ 
/*     */ 
/*     */             
/* 895 */             if (notUnderstoodHeaders == null) {
/* 896 */               notUnderstoodHeaders = new HashSet<QName>();
/*     */             }
/* 898 */             notUnderstoodHeaders.add(qName);
/*     */           
/*     */           }
/* 901 */           else if (binding instanceof SOAPBindingImpl && !((SOAPBindingImpl)binding).understandsHeader(qName) && 
/* 902 */             !knownHeaders.contains(qName)) {
/*     */             
/* 904 */             if (notUnderstoodHeaders == null) {
/* 905 */               notUnderstoodHeaders = new HashSet<QName>();
/*     */             }
/* 907 */             notUnderstoodHeaders.add(qName);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 914 */     return notUnderstoodHeaders;
/*     */   }
/*     */   
/*     */   private SOAPVersion getEffectiveSOAPVersion(WSBinding binding) {
/* 918 */     SOAPVersion mySOAPVersion = (this.soapVersion != null) ? this.soapVersion : binding.getSOAPVersion();
/* 919 */     if (mySOAPVersion == null) {
/* 920 */       mySOAPVersion = SOAPVersion.SOAP_11;
/*     */     }
/* 922 */     return mySOAPVersion;
/*     */   }
/*     */   
/*     */   public void setSoapVersion(SOAPVersion soapVersion) {
/* 926 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Header> getHeaders() {
/* 931 */     return iterator();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public HeaderList() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\HeaderList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */