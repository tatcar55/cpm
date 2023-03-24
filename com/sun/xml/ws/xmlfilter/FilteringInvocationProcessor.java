/*     */ package com.sun.xml.ws.xmlfilter;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
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
/*     */ public final class FilteringInvocationProcessor
/*     */   implements InvocationProcessor
/*     */ {
/*  63 */   private static final Logger LOGGER = Logger.getLogger(FilteringInvocationProcessor.class);
/*  64 */   private static final XMLOutputFactory XML_OUTPUT_FACTORY = XMLOutputFactory.newInstance(); private final XMLStreamWriter originalWriter; private final XMLStreamWriter mirrorWriter; private final LinkedList<InvocationBuffer> invocationBuffers; private final StateMachineContext[] stateMachineContexts; private final List<StateMachineContext> startBufferingCandidates; private final List<StateMachineContext> stopBufferingCandidates; private final List<StateMachineContext> startFilteringCandidates;
/*     */   private final InvocationTransformer invocationTransformer;
/*     */   private int filteringCount;
/*     */   private boolean filtering;
/*     */   
/*     */   private static final class StateMachineContext { private final FilteringStateMachine stateMachine;
/*     */     
/*     */     StateMachineContext(FilteringStateMachine stateMachine) {
/*  72 */       this.stateMachine = stateMachine;
/*  73 */       this.bufferRef = null;
/*     */     }
/*     */     private WeakReference<FilteringInvocationProcessor.InvocationBuffer> bufferRef;
/*     */     public FilteringStateMachine getStateMachine() {
/*  77 */       return this.stateMachine;
/*     */     }
/*     */     
/*     */     public FilteringInvocationProcessor.InvocationBuffer getBuffer() {
/*  81 */       return (this.bufferRef == null) ? null : this.bufferRef.get();
/*     */     }
/*     */     
/*     */     public void setBuffer(FilteringInvocationProcessor.InvocationBuffer buffer) {
/*  85 */       this.bufferRef = new WeakReference<FilteringInvocationProcessor.InvocationBuffer>(buffer);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static final class InvocationBuffer
/*     */   {
/*     */     private final Queue<Invocation> queue;
/*     */     private int referenceCount;
/*     */     
/*     */     InvocationBuffer(int refCount) {
/*  95 */       this.queue = new LinkedList<Invocation>();
/*  96 */       this.referenceCount = refCount;
/*     */     }
/*     */     
/*     */     public Queue<Invocation> getQueue() {
/* 100 */       return this.queue;
/*     */     }
/*     */     
/*     */     public int removeReference() {
/* 104 */       if (this.referenceCount > 0) {
/* 105 */         this.referenceCount--;
/*     */       }
/* 107 */       return this.referenceCount;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 111 */       this.queue.clear();
/* 112 */       this.referenceCount = 0;
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
/*     */   public FilteringInvocationProcessor(XMLStreamWriter writer, FilteringStateMachine... stateMachines) throws XMLStreamException {
/* 128 */     this(writer, null, stateMachines);
/*     */   }
/*     */ 
/*     */   
/*     */   public FilteringInvocationProcessor(XMLStreamWriter writer, InvocationTransformer transformer, FilteringStateMachine... stateMachines) throws XMLStreamException {
/* 133 */     this.originalWriter = writer;
/* 134 */     this.stateMachineContexts = new StateMachineContext[stateMachines.length];
/* 135 */     for (int i = 0; i < stateMachines.length; i++) {
/* 136 */       this.stateMachineContexts[i] = new StateMachineContext(stateMachines[i]);
/*     */     }
/*     */     
/* 139 */     this.mirrorWriter = XML_OUTPUT_FACTORY.createXMLStreamWriter(new StringWriter());
/* 140 */     this.invocationBuffers = new LinkedList<InvocationBuffer>();
/* 141 */     this.startBufferingCandidates = new LinkedList<StateMachineContext>();
/* 142 */     this.stopBufferingCandidates = new LinkedList<StateMachineContext>();
/* 143 */     this.startFilteringCandidates = new LinkedList<StateMachineContext>();
/* 144 */     this.invocationTransformer = transformer;
/*     */   }
/*     */   
/*     */   public Object process(Invocation invocation) throws InvocationProcessingException {
/* 148 */     if (invocation.getMethodType().isFilterable()) {
/* 149 */       if (this.invocationTransformer != null) {
/* 150 */         Collection<Invocation> transformedInvocations = this.invocationTransformer.transform(invocation);
/*     */         
/* 152 */         Object returnValue = null;
/* 153 */         for (Invocation transformedInvocation : transformedInvocations) {
/* 154 */           if (transformedInvocation == invocation) {
/* 155 */             returnValue = filter(transformedInvocation); continue;
/*     */           } 
/* 157 */           filter(transformedInvocation);
/*     */         } 
/*     */         
/* 160 */         return returnValue;
/*     */       } 
/*     */       
/* 163 */       return filter(invocation);
/*     */     } 
/* 165 */     switch (invocation.getMethodType()) {
/*     */       case START_BUFFERING:
/* 167 */         invocation.execute(this.originalWriter);
/* 168 */         return invocation.execute(this.mirrorWriter);
/*     */       case RESTART_BUFFERING:
/* 170 */         executeAllBufferedInvocations(this.originalWriter);
/* 171 */         invocation.execute(this.originalWriter);
/* 172 */         return invocation.execute(this.mirrorWriter);
/*     */     } 
/* 174 */     return invocation.execute(this.mirrorWriter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object filter(Invocation invocation) throws InvocationProcessingException {
/* 180 */     LOGGER.entering(new Object[] { invocation }); try {
/*     */       XMLStreamWriter invocationTarget;
/* 182 */       resolveStateChangeCandidates(invocation);
/*     */       
/* 184 */       processStartFilteringCandidates();
/* 185 */       processStopBufferingCandidates();
/* 186 */       processStartBufferingCandidates();
/* 187 */       updateFilteringStatus();
/*     */ 
/*     */ 
/*     */       
/* 191 */       if (this.filtering) {
/* 192 */         this.filtering = (this.filteringCount > 0);
/* 193 */         invocationTarget = this.mirrorWriter;
/*     */       }
/* 195 */       else if (this.invocationBuffers.isEmpty()) {
/* 196 */         invocation.execute(this.mirrorWriter);
/* 197 */         invocationTarget = this.originalWriter;
/*     */       } else {
/* 199 */         ((InvocationBuffer)this.invocationBuffers.getLast()).getQueue().offer(invocation);
/* 200 */         invocationTarget = this.mirrorWriter;
/*     */       } 
/*     */ 
/*     */       
/* 204 */       return invocation.execute(invocationTarget);
/*     */     } finally {
/* 206 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processStartBufferingCandidates() {
/* 212 */     if (this.filteringCount == 0 && this.startBufferingCandidates.size() > 0) {
/* 213 */       InvocationBuffer buffer = new InvocationBuffer(this.startBufferingCandidates.size());
/* 214 */       this.invocationBuffers.addLast(buffer);
/* 215 */       for (StateMachineContext context : this.startBufferingCandidates) {
/* 216 */         context.setBuffer(buffer);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processStartFilteringCandidates() {
/* 223 */     int firstFilteredBufferIndex = this.invocationBuffers.size();
/* 224 */     for (StateMachineContext context : this.startFilteringCandidates) {
/* 225 */       InvocationBuffer buffer = context.getBuffer();
/* 226 */       context.setBuffer(null);
/*     */       int currentBufferIndex;
/* 228 */       if ((currentBufferIndex = this.invocationBuffers.indexOf(buffer)) < firstFilteredBufferIndex) {
/* 229 */         firstFilteredBufferIndex = currentBufferIndex;
/*     */       }
/*     */     } 
/* 232 */     while (this.invocationBuffers.size() > firstFilteredBufferIndex) {
/* 233 */       InvocationBuffer filteredBuffer = this.invocationBuffers.removeLast();
/* 234 */       filteredBuffer.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processStopBufferingCandidates() throws InvocationProcessingException {
/* 240 */     for (StateMachineContext context : this.stopBufferingCandidates) {
/* 241 */       InvocationBuffer buffer = context.getBuffer();
/* 242 */       context.setBuffer(null);
/* 243 */       if (buffer == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 247 */       if (buffer.removeReference() == 0) {
/*     */         int bufferIndex;
/* 249 */         if ((bufferIndex = this.invocationBuffers.indexOf(buffer)) != -1) {
/* 250 */           this.invocationBuffers.remove(bufferIndex);
/* 251 */           if (bufferIndex == 0) {
/* 252 */             Invocation.executeBatch(this.originalWriter, buffer.getQueue()); continue;
/*     */           } 
/* 254 */           ((InvocationBuffer)this.invocationBuffers.get(bufferIndex - 1)).getQueue().addAll(buffer.getQueue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void resolveStateChangeCandidates(Invocation invocation) {
/* 262 */     this.startBufferingCandidates.clear();
/* 263 */     this.stopBufferingCandidates.clear();
/* 264 */     this.startFilteringCandidates.clear();
/* 265 */     for (StateMachineContext context : this.stateMachineContexts) {
/* 266 */       switch (context.getStateMachine().getStateChange(invocation, this.mirrorWriter)) {
/*     */         case START_BUFFERING:
/* 268 */           this.startBufferingCandidates.add(context);
/*     */           break;
/*     */         case RESTART_BUFFERING:
/* 271 */           this.startBufferingCandidates.add(context);
/* 272 */           if (context.getBuffer() != null) {
/* 273 */             this.stopBufferingCandidates.add(context);
/*     */           }
/*     */           break;
/*     */         case STOP_BUFFERING:
/* 277 */           if (context.getBuffer() != null) {
/* 278 */             this.stopBufferingCandidates.add(context);
/*     */           }
/*     */           break;
/*     */         case START_FILTERING:
/* 282 */           if (context.getBuffer() != null) {
/* 283 */             this.startFilteringCandidates.add(context);
/*     */           }
/* 285 */           this.filteringCount++;
/*     */           break;
/*     */         case STOP_FILTERING:
/* 288 */           this.filteringCount--;
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateFilteringStatus() {
/* 298 */     if (!this.filtering) {
/* 299 */       this.filtering = (this.filteringCount > 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void executeAllBufferedInvocations(XMLStreamWriter target) {
/* 304 */     while (!this.invocationBuffers.isEmpty()) {
/* 305 */       InvocationBuffer buffer = this.invocationBuffers.removeFirst();
/* 306 */       Invocation.executeBatch(target, buffer.getQueue());
/* 307 */       buffer.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\FilteringInvocationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */