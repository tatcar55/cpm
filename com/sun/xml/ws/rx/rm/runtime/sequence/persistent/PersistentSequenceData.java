/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence.persistent;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.JaxwsApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateMessageRegistrationException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateSequenceException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceData;
/*     */ import com.sun.xml.ws.rx.util.TimeSynchronizer;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PersistentSequenceData
/*     */   implements SequenceData
/*     */ {
/*     */   private static final class FieldInfo<T>
/*     */   {
/*     */     final String columnName;
/*     */     final int sqlType;
/*     */     final Class<T> javaClass;
/*     */     
/*     */     public FieldInfo(String columnName, int sqlType, Class<T> javaClass) {
/* 121 */       this.columnName = columnName;
/* 122 */       this.sqlType = sqlType;
/* 123 */       this.javaClass = javaClass;
/*     */     }
/*     */   }
/*     */   
/*     */   enum SequenceType
/*     */   {
/* 129 */     Inbound("I"),
/* 130 */     Outbound("O");
/*     */     
/*     */     private final String id;
/*     */     
/*     */     SequenceType(String id) {
/* 135 */       this.id = id;
/*     */     }
/*     */     
/*     */     private static SequenceType fromId(String id) {
/* 139 */       for (SequenceType type : values()) {
/* 140 */         if (type.id.equals(id)) {
/* 141 */           return type;
/*     */         }
/*     */       } 
/*     */       
/* 145 */       return null;
/*     */     }
/*     */   }
/*     */   
/* 149 */   private static final Logger LOGGER = Logger.getLogger(PersistentSequenceData.class); private final String endpointUid; private final String sequenceId; private final SequenceType type;
/*     */   
/*     */   private static String b2s(boolean value) {
/* 152 */     return value ? "T" : "F";
/*     */   }
/*     */   private final String boundSecurityTokenReferenceId; private final String boundSequenceId; private final long expirationTime;
/*     */   private static boolean s2b(String string) {
/* 156 */     return "T".equals(string);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   private final FieldInfo<Integer> fState = new FieldInfo<Integer>("STATUS", 5, Integer.class);
/* 167 */   private final FieldInfo<String> fAckRequestedFlag = new FieldInfo<String>("ACK_REQUESTED_FLAG", 1, String.class);
/* 168 */   private final FieldInfo<Long> fLastMessageNumber = new FieldInfo<Long>("LAST_MESSAGE_NUMBER", -5, Long.class);
/* 169 */   private final FieldInfo<Long> fLastActivityTime = new FieldInfo<Long>("LAST_ACTIVITY_TIME", -5, Long.class);
/* 170 */   private final FieldInfo<Long> fLastAcknowledgementRequestTime = new FieldInfo<Long>("LAST_ACK_REQUEST_TIME", -5, Long.class);
/*     */   
/*     */   private final ConnectionManager cm;
/*     */   private final TimeSynchronizer ts;
/*     */   
/*     */   private PersistentSequenceData(TimeSynchronizer ts, ConnectionManager cm, String endpointUid, String sequenceId, SequenceType type, String securityContextTokenId, String boundId, long expirationTime) {
/* 176 */     this.ts = ts;
/* 177 */     this.cm = cm;
/*     */     
/* 179 */     this.endpointUid = endpointUid;
/* 180 */     this.sequenceId = sequenceId;
/* 181 */     this.type = type;
/* 182 */     this.boundSecurityTokenReferenceId = securityContextTokenId;
/* 183 */     this.boundSequenceId = boundId;
/* 184 */     this.expirationTime = expirationTime;
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
/*     */   static PersistentSequenceData newInstance(TimeSynchronizer ts, ConnectionManager cm, String enpointUid, String sequenceId, SequenceType type, String securityContextTokenId, long expirationTime, Sequence.State state, boolean ackRequestedFlag, long lastMessageId, long lastActivityTime, long lastAcknowledgementRequestTime) throws DuplicateSequenceException {
/* 201 */     Connection con = cm.getConnection(false);
/* 202 */     PreparedStatement ps = null;
/*     */     try {
/* 204 */       ps = cm.prepareStatement(con, "INSERT INTO RM_SEQUENCES (ENDPOINT_UID, ID, TYPE, EXP_TIME, STR_ID, STATUS, ACK_REQUESTED_FLAG, LAST_MESSAGE_NUMBER, LAST_ACTIVITY_TIME, LAST_ACK_REQUEST_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       int i = 0;
/* 210 */       ps.setString(++i, enpointUid);
/* 211 */       ps.setString(++i, sequenceId);
/* 212 */       ps.setString(++i, type.id);
/*     */       
/* 214 */       ps.setLong(++i, expirationTime);
/* 215 */       ps.setString(++i, securityContextTokenId);
/*     */ 
/*     */       
/* 218 */       ps.setInt(++i, state.asInt());
/* 219 */       ps.setString(++i, b2s(ackRequestedFlag));
/* 220 */       ps.setLong(++i, lastMessageId);
/* 221 */       ps.setLong(++i, lastActivityTime);
/* 222 */       ps.setLong(++i, lastAcknowledgementRequestTime);
/*     */       
/* 224 */       if (ps.executeUpdate() != 1) {
/* 225 */         cm.rollback(con);
/*     */         
/* 227 */         throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Inserting sequence data for %s sequence with id = [ %s ] failed: Expected inserted rows: 1, Actual: %d", new Object[] { type, sequenceId })));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 234 */       PersistentSequenceData data = loadInstance(con, ts, cm, enpointUid, sequenceId);
/* 235 */       cm.commit(con);
/*     */       
/* 237 */       return data;
/* 238 */     } catch (SQLException ex) {
/* 239 */       cm.rollback(con);
/* 240 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Inserting sequence data for %s sequence with id = [ %s ] failed: An unexpected JDBC exception occured", new Object[] { type, sequenceId }), ex));
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 246 */       cm.recycle(new PreparedStatement[] { ps });
/* 247 */       cm.recycle(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static PersistentSequenceData loadInstance(TimeSynchronizer ts, ConnectionManager cm, String endpointUid, String sequenceId) {
/* 253 */     Connection con = cm.getConnection(true);
/*     */     try {
/* 255 */       return loadInstance(con, ts, cm, endpointUid, sequenceId);
/*     */     } finally {
/* 257 */       cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static PersistentSequenceData loadInstance(Connection connection, TimeSynchronizer ts, ConnectionManager cm, String endpointUid, String sequenceId) {
/* 262 */     PreparedStatement ps = null;
/*     */     try {
/* 264 */       ps = cm.prepareStatement(connection, "SELECT TYPE, EXP_TIME, BOUND_ID, STR_ID FROM RM_SEQUENCES WHERE ENDPOINT_UID=? AND ID=?");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 269 */       ps.setString(1, endpointUid);
/* 270 */       ps.setString(2, sequenceId);
/*     */       
/* 272 */       ResultSet rs = ps.executeQuery();
/*     */       
/* 274 */       if (!rs.next()) {
/* 275 */         return null;
/*     */       }
/*     */       
/* 278 */       if (!rs.isFirst() && !rs.isLast()) {
/* 279 */         throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Duplicate sequence records detected for a sequence with id [ %s ]", new Object[] { sequenceId })));
/*     */       }
/*     */ 
/*     */       
/* 283 */       return new PersistentSequenceData(ts, cm, endpointUid, sequenceId, SequenceType.fromId(rs.getString("TYPE")), rs.getString("STR_ID"), rs.getString("BOUND_ID"), rs.getLong("EXP_TIME"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 293 */     catch (SQLException ex) {
/* 294 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Loading sequence data for a sequence with id = [ %s ] failed: An unexpected JDBC exception occured", new Object[] { sequenceId }), ex));
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 299 */       cm.recycle(new PreparedStatement[] { ps });
/*     */     } 
/*     */   }
/*     */   
/*     */   static void remove(ConnectionManager cm, String endpointUid, String sequenceId) {
/* 304 */     Connection con = cm.getConnection(false);
/* 305 */     PreparedStatement ps = null;
/*     */     try {
/* 307 */       ps = cm.prepareStatement(con, "DELETE FROM RM_UNACKED_MESSAGES WHERE ENDPOINT_UID=? AND SEQ_ID=?");
/*     */       
/* 309 */       ps.setString(1, endpointUid);
/* 310 */       ps.setString(2, sequenceId);
/*     */       
/* 312 */       int rowsAffected = ps.executeUpdate();
/* 313 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 314 */         LOGGER.fine(String.format("%d unacknowledged message records removed for a sequence with id [ %s ]", new Object[] { Integer.valueOf(rowsAffected), sequenceId }));
/*     */       }
/*     */       
/* 317 */       cm.recycle(new PreparedStatement[] { ps });
/*     */       
/* 319 */       ps = cm.prepareStatement(con, "DELETE FROM RM_SEQUENCES WHERE ENDPOINT_UID=? AND ID=?");
/*     */       
/* 321 */       ps.setString(1, endpointUid);
/* 322 */       ps.setString(2, sequenceId);
/*     */       
/* 324 */       rowsAffected = ps.executeUpdate();
/* 325 */       if (rowsAffected != 1) {
/* 326 */         cm.rollback(con);
/* 327 */         throw (PersistenceException)LOGGER.logException(new PersistenceException(String.format("Removing sequence with id = [ %s ] failed: Expected deleted rows: 1, Actual: %d", new Object[] { sequenceId, Integer.valueOf(rowsAffected) })), Level.WARNING);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 336 */       cm.commit(con);
/*     */     }
/* 338 */     catch (SQLException ex) {
/* 339 */       cm.rollback(con);
/* 340 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Removing sequence with id = [ %s ] failed: An unexpected JDBC exception occured", new Object[] { sequenceId }), ex));
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 345 */       cm.recycle(new PreparedStatement[] { ps });
/* 346 */       cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void bind(ConnectionManager cm, String endpointUid, String referenceSequenceId, String boundSequenceId) {
/* 351 */     Connection con = cm.getConnection(false);
/* 352 */     PreparedStatement ps = null;
/*     */     try {
/* 354 */       ps = cm.prepareStatement(con, "UPDATE RM_SEQUENCES SET BOUND_ID=? WHERE ENDPOINT_UID=? AND ID=?");
/*     */ 
/*     */ 
/*     */       
/* 358 */       ps.setString(1, boundSequenceId);
/* 359 */       ps.setString(2, endpointUid);
/* 360 */       ps.setString(3, referenceSequenceId);
/*     */       
/* 362 */       int rowsAffected = ps.executeUpdate();
/* 363 */       if (rowsAffected != 1) {
/* 364 */         cm.rollback(con);
/* 365 */         throw (PersistenceException)LOGGER.logException(new PersistenceException(String.format("Binding a sequence with id = [ %s ] to a sequence with id [ %s ] failed: Expected updated rows: 1, Actual: %d", new Object[] { boundSequenceId, referenceSequenceId, Integer.valueOf(rowsAffected) })), Level.WARNING);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 375 */       cm.commit(con);
/* 376 */     } catch (SQLException ex) {
/* 377 */       cm.rollback(con);
/* 378 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Binding a sequence with id = [ %s ] to a sequence with id [ %s ] failed: An unexpected JDBC exception occured", new Object[] { boundSequenceId, referenceSequenceId }), ex));
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 384 */       cm.recycle(new PreparedStatement[] { ps });
/* 385 */       cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getSequenceId() {
/* 390 */     return this.sequenceId;
/*     */   }
/*     */   
/*     */   public SequenceType getType() {
/* 394 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getBoundSecurityTokenReferenceId() {
/* 398 */     return this.boundSecurityTokenReferenceId;
/*     */   }
/*     */   
/*     */   public String getBoundSequenceId() {
/* 402 */     return this.boundSequenceId;
/*     */   }
/*     */   
/*     */   public long getExpirationTime() {
/* 406 */     return this.expirationTime;
/*     */   }
/*     */   
/*     */   public boolean isFailedOver(long messageNumber) {
/* 410 */     return false;
/*     */   }
/*     */   
/*     */   private <T> T getFieldData(Connection con, FieldInfo<T> fi) throws PersistenceException {
/* 414 */     PreparedStatement ps = null;
/*     */     try {
/* 416 */       ps = this.cm.prepareStatement(con, "SELECT " + fi.columnName + " " + "FROM RM_SEQUENCES " + "WHERE ENDPOINT_UID=? AND ID=?");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       ps.setString(1, this.endpointUid);
/* 422 */       ps.setString(2, this.sequenceId);
/*     */       
/* 424 */       ResultSet rs = ps.executeQuery();
/*     */       
/* 426 */       if (!rs.next()) {
/* 427 */         throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Sequence record not found for a sequence with id [ %s ]", new Object[] { this.sequenceId })));
/*     */       }
/*     */ 
/*     */       
/* 431 */       if (!rs.isFirst() && !rs.isLast()) {
/* 432 */         throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Duplicate sequence records detected for a sequence with id [ %s ]", new Object[] { this.sequenceId })));
/*     */       }
/*     */ 
/*     */       
/* 436 */       return fi.javaClass.cast(rs.getObject(fi.columnName));
/* 437 */     } catch (SQLException ex) {
/* 438 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Loading %s column data on a sequence with id = [ %s ]  failed: An unexpected JDBC exception occured", new Object[] { fi.columnName, this.sequenceId }), ex));
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 444 */       this.cm.recycle(new PreparedStatement[] { ps });
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> T getFieldData(FieldInfo<T> fi) {
/* 449 */     Connection con = this.cm.getConnection(true);
/*     */     try {
/* 451 */       return (T)getFieldData(con, (FieldInfo)fi);
/*     */     } finally {
/* 453 */       this.cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> void setFieldData(Connection con, FieldInfo<T> fi, T value, boolean updateLastActivityTime) {
/* 458 */     PreparedStatement ps = null;
/*     */     try {
/* 460 */       String lastActivityTimeUpdateString = "";
/* 461 */       if (updateLastActivityTime) {
/* 462 */         lastActivityTimeUpdateString = ", " + this.fLastActivityTime.columnName + "=? ";
/*     */       }
/*     */       
/* 465 */       ps = this.cm.prepareStatement(con, "UPDATE RM_SEQUENCES SET " + fi.columnName + "=?" + lastActivityTimeUpdateString + " " + "WHERE ENDPOINT_UID=? AND ID=?");
/*     */ 
/*     */ 
/*     */       
/* 469 */       int i = 0;
/* 470 */       ps.setObject(++i, value, fi.sqlType);
/* 471 */       if (updateLastActivityTime) {
/* 472 */         ps.setLong(++i, this.ts.currentTimeInMillis());
/*     */       }
/* 474 */       ps.setString(++i, this.endpointUid);
/* 475 */       ps.setString(++i, this.sequenceId);
/*     */       
/* 477 */       int rowsAffected = ps.executeUpdate();
/* 478 */       if (rowsAffected != 1) {
/* 479 */         throw (PersistenceException)LOGGER.logException(new PersistenceException(String.format("Updating %s column data on a sequence with id = [ %s ]  failed: Expected updated rows: 1, Actual: %d", new Object[] { fi.columnName, this.sequenceId, Integer.valueOf(rowsAffected) })), Level.WARNING);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 488 */     catch (SQLException ex) {
/* 489 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Updating %s column data on a sequence with id = [ %s ]  failed: An unexpected JDBC exception occured", new Object[] { fi.columnName, this.sequenceId }), ex));
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 495 */       this.cm.recycle(new PreparedStatement[] { ps });
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> void setFieldData(FieldInfo<T> fi, T value, boolean updateLastActivityTime) {
/* 500 */     Connection con = this.cm.getConnection(false);
/* 501 */     boolean commit = false;
/*     */     try {
/* 503 */       setFieldData(con, fi, value, updateLastActivityTime);
/* 504 */       commit = true;
/*     */     } finally {
/* 506 */       if (commit) {
/* 507 */         this.cm.commit(con);
/*     */       }
/* 509 */       this.cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getLastMessageNumber() {
/* 514 */     return ((Long)getFieldData(this.fLastMessageNumber)).longValue();
/*     */   }
/*     */   
/*     */   public Sequence.State getState() {
/* 518 */     return Sequence.State.asState(((Integer)getFieldData(this.fState)).intValue());
/*     */   }
/*     */   
/*     */   public void setState(Sequence.State newValue) {
/* 522 */     setFieldData(this.fState, Integer.valueOf(newValue.asInt()), true);
/*     */   }
/*     */   
/*     */   public boolean getAckRequestedFlag() {
/* 526 */     return s2b(getFieldData(this.fAckRequestedFlag));
/*     */   }
/*     */   
/*     */   public void setAckRequestedFlag(boolean newValue) {
/* 530 */     setFieldData(this.fAckRequestedFlag, b2s(newValue), true);
/*     */   }
/*     */   
/*     */   public long getLastAcknowledgementRequestTime() {
/* 534 */     return ((Long)getFieldData(this.fLastAcknowledgementRequestTime)).longValue();
/*     */   }
/*     */   
/*     */   public void setLastAcknowledgementRequestTime(long newValue) {
/* 538 */     setFieldData(this.fLastAcknowledgementRequestTime, Long.valueOf(newValue), true);
/*     */   }
/*     */   
/*     */   public long getLastActivityTime() {
/* 542 */     return ((Long)getFieldData(this.fLastActivityTime)).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long incrementAndGetLastMessageNumber(boolean received) {
/* 549 */     Connection con = this.cm.getConnection(false);
/* 550 */     PreparedStatement ps = null;
/*     */     try {
/* 552 */       ps = this.cm.prepareStatement(con, "UPDATE RM_SEQUENCES SET LAST_MESSAGE_NUMBER=LAST_MESSAGE_NUMBER+1, " + this.fLastActivityTime.columnName + "=? " + "WHERE ENDPOINT_UID=? AND ID=?");
/*     */ 
/*     */ 
/*     */       
/* 556 */       ps.setLong(1, this.ts.currentTimeInMillis());
/* 557 */       ps.setString(2, this.endpointUid);
/* 558 */       ps.setString(3, this.sequenceId);
/*     */       
/* 560 */       int rowsAffected = ps.executeUpdate();
/* 561 */       if (rowsAffected != 1) {
/* 562 */         this.cm.rollback(con);
/* 563 */         throw (PersistenceException)LOGGER.logException(new PersistenceException(String.format("Incrementing last message number on a sequence with id = [ %s ] failed: Expected updated rows: 1, Actual: %d", new Object[] { this.sequenceId, Integer.valueOf(rowsAffected) })), Level.WARNING);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 572 */       long newLastMessageId = ((Long)getFieldData(con, this.fLastMessageNumber)).longValue();
/* 573 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 574 */         LOGGER.finer("New last message id: " + newLastMessageId);
/*     */       }
/*     */       
/*     */       try {
/* 578 */         registerSingleUnackedMessageNumber(con, newLastMessageId, received);
/* 579 */       } catch (DuplicateMessageRegistrationException ex) {
/* 580 */         this.cm.rollback(con);
/* 581 */         throw new PersistenceException("Registering newly created last message id ", ex);
/* 582 */       } catch (PersistenceException ex) {
/* 583 */         this.cm.rollback(con);
/* 584 */         throw ex;
/*     */       } 
/*     */       
/* 587 */       this.cm.commit(con);
/* 588 */       return newLastMessageId;
/* 589 */     } catch (SQLException ex) {
/* 590 */       this.cm.rollback(con);
/* 591 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Incrementing last message number on a sequence with id = [ %s ] failed: An unexpected JDBC exception occured", new Object[] { this.sequenceId }), ex));
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 596 */       this.cm.recycle(new PreparedStatement[] { ps });
/* 597 */       this.cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean containsUnackedMessageNumberRegistration(Connection con, long messageNumber) throws PersistenceException {
/* 602 */     PreparedStatement ps = null;
/*     */     try {
/* 604 */       ps = this.cm.prepareStatement(con, "SELECT IS_RECEIVED FROM RM_UNACKED_MESSAGES WHERE ENDPOINT_UID=? AND SEQ_ID=? AND MSG_NUMBER=?");
/* 605 */       ps.setString(1, this.endpointUid);
/* 606 */       ps.setString(2, this.sequenceId);
/* 607 */       ps.setLong(3, messageNumber);
/*     */       
/* 609 */       ResultSet rs = ps.executeQuery();
/*     */       
/* 611 */       return rs.next();
/* 612 */     } catch (SQLException ex) {
/* 613 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Retrieving an unacked message number record for a message number [ %d ] on a sequence with id = [ %s ]  failed: An unexpected JDBC exception occured", new Object[] { Long.valueOf(messageNumber), this.sequenceId }), ex));
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 620 */       this.cm.recycle(new PreparedStatement[] { ps });
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerSingleUnackedMessageNumber(Connection con, long messageNumber, boolean received) throws PersistenceException, DuplicateMessageRegistrationException {
/* 625 */     PreparedStatement ps = null; try {
/*     */       int rowsAffected;
/* 627 */       ps = this.cm.prepareStatement(con, "SELECT IS_RECEIVED FROM RM_UNACKED_MESSAGES WHERE ENDPOINT_UID=? AND SEQ_ID=? AND MSG_NUMBER=?");
/* 628 */       ps.setString(1, this.endpointUid);
/* 629 */       ps.setString(2, this.sequenceId);
/* 630 */       ps.setLong(3, messageNumber);
/*     */       
/* 632 */       ResultSet rs = ps.executeQuery();
/*     */       
/* 634 */       boolean doInsert = !rs.next();
/*     */       
/* 636 */       if (!doInsert && s2b(rs.getString("IS_RECEIVED")) == received) {
/* 637 */         throw new DuplicateMessageRegistrationException(this.sequenceId, messageNumber);
/*     */       }
/*     */       
/* 640 */       this.cm.recycle(new PreparedStatement[] { ps });
/*     */ 
/*     */       
/* 643 */       if (doInsert) {
/*     */         
/* 645 */         ps = this.cm.prepareStatement(con, "INSERT INTO RM_UNACKED_MESSAGES (ENDPOINT_UID, SEQ_ID, MSG_NUMBER, IS_RECEIVED) VALUES (?, ?, ?, ?)");
/*     */ 
/*     */         
/* 648 */         ps.setString(1, this.endpointUid);
/* 649 */         ps.setString(2, this.sequenceId);
/* 650 */         ps.setLong(3, messageNumber);
/* 651 */         ps.setString(4, b2s(received));
/*     */         
/* 653 */         rowsAffected = ps.executeUpdate();
/* 654 */         if (rowsAffected != 1) {
/* 655 */           throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Inserting new unacked message number record for a message number [ %d ] on a sequence with id = [ %s ]  failed: Expected updated rows: 1, Actual: %d", new Object[] { Long.valueOf(messageNumber), this.sequenceId, Integer.valueOf(rowsAffected) })));
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 664 */         ps = this.cm.prepareStatement(con, "UPDATE RM_UNACKED_MESSAGES SET IS_RECEIVED=? WHERE ENDPOINT_UID=? AND SEQ_ID=? AND MSG_NUMBER=? AND IS_RECEIVED=?");
/*     */ 
/*     */         
/* 667 */         ps.setString(1, b2s(received));
/* 668 */         ps.setString(2, this.endpointUid);
/* 669 */         ps.setString(3, this.sequenceId);
/* 670 */         ps.setLong(4, messageNumber);
/* 671 */         ps.setString(5, b2s(!received));
/*     */         
/* 673 */         rowsAffected = ps.executeUpdate();
/*     */       } 
/* 675 */       if (rowsAffected != 1) {
/* 676 */         throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Registering an unacked message number record for a message number [ %d ] on a sequence with id = [ %s ]  failed: Expected affected rows: 1, Actual: %d", new Object[] { Long.valueOf(messageNumber), this.sequenceId, Integer.valueOf(rowsAffected) })));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 685 */     catch (SQLException ex) {
/* 686 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Registering an unacked message number record for a message number [ %d ] on a sequence with id = [ %s ]  failed: An unexpected JDBC exception occured", new Object[] { Long.valueOf(messageNumber), this.sequenceId }), ex));
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 693 */       this.cm.recycle(new PreparedStatement[] { ps });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerReceivedUnackedMessageNumber(long messageNumber) throws DuplicateMessageRegistrationException {
/* 701 */     Connection con = this.cm.getConnection(false);
/*     */     try {
/* 703 */       long lastMessageNumber = ((Long)getFieldData(con, this.fLastMessageNumber)).longValue();
/* 704 */       if (lastMessageNumber < messageNumber) {
/* 705 */         setFieldData(con, this.fLastMessageNumber, Long.valueOf(messageNumber), false); long i;
/* 706 */         for (i = lastMessageNumber + 1L; i < messageNumber; i++) {
/* 707 */           registerSingleUnackedMessageNumber(con, i, false);
/*     */         }
/* 709 */       } else if (!containsUnackedMessageNumberRegistration(con, messageNumber)) {
/* 710 */         throw new DuplicateMessageRegistrationException(this.sequenceId, messageNumber);
/*     */       } 
/*     */       
/* 713 */       registerSingleUnackedMessageNumber(con, messageNumber, true);
/* 714 */       setFieldData(con, this.fLastActivityTime, Long.valueOf(this.ts.currentTimeInMillis()), false);
/*     */       
/* 716 */       this.cm.commit(con);
/* 717 */     } catch (PersistenceException ex) {
/* 718 */       this.cm.rollback(con);
/* 719 */       throw ex;
/* 720 */     } catch (DuplicateMessageRegistrationException ex) {
/* 721 */       this.cm.rollback(con);
/* 722 */       throw ex;
/*     */     } finally {
/* 724 */       this.cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void markAsAcknowledged(long messageNumber) {
/* 729 */     Connection con = this.cm.getConnection(false);
/* 730 */     PreparedStatement ps = null;
/*     */     try {
/* 732 */       ps = this.cm.prepareStatement(con, "DELETE FROM RM_UNACKED_MESSAGES WHERE ENDPOINT_UID=? AND SEQ_ID=? AND MSG_NUMBER=?");
/*     */ 
/*     */       
/* 735 */       ps.setString(1, this.endpointUid);
/* 736 */       ps.setString(2, this.sequenceId);
/* 737 */       ps.setLong(3, messageNumber);
/*     */       
/* 739 */       int rowsAffected = ps.executeUpdate();
/* 740 */       if (rowsAffected != 1) {
/* 741 */         if (rowsAffected == 0) {
/* 742 */           if (LOGGER.isLoggable(Level.FINER)) {
/* 743 */             LOGGER.finer(String.format("No unacknowledged message record found for %s sequence with id = [ %s ] and message number [ %d ]: Message was probably already acknowledged earlier", new Object[] { this.type, this.sequenceId, Long.valueOf(messageNumber) }));
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 751 */           throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Message acknowledgement failed for %s sequence with id = [ %s ] and message number [ %d ]: Expected deleted rows: 1, Actual: %d", new Object[] { this.type, this.sequenceId, Long.valueOf(messageNumber), Integer.valueOf(rowsAffected) })));
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 761 */       setFieldData(con, this.fLastActivityTime, Long.valueOf(this.ts.currentTimeInMillis()), false);
/*     */       
/* 763 */       this.cm.commit(con);
/* 764 */     } catch (SQLException ex) {
/* 765 */       this.cm.rollback(con);
/* 766 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Message acknowledgement failed for %s sequence with id = [ %s ] and message number [ %d ]: An unexpected JDBC exception occured", new Object[] { this.type, this.sequenceId, Long.valueOf(messageNumber) }), ex));
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 773 */       this.cm.recycle(new PreparedStatement[] { ps });
/* 774 */       this.cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Long> getUnackedMessageNumbers() {
/* 779 */     Connection con = this.cm.getConnection(true);
/* 780 */     PreparedStatement ps = null;
/*     */     try {
/* 782 */       ps = this.cm.prepareStatement(con, "SELECT MSG_NUMBER FROM RM_UNACKED_MESSAGES WHERE ENDPOINT_UID=? AND SEQ_ID=?");
/*     */ 
/*     */       
/* 785 */       ps.setString(1, this.endpointUid);
/* 786 */       ps.setString(2, this.sequenceId);
/*     */       
/* 788 */       ResultSet rs = ps.executeQuery();
/*     */       
/* 790 */       List<Long> result = new LinkedList<Long>();
/* 791 */       while (rs.next()) {
/* 792 */         result.add(Long.valueOf(rs.getLong("MSG_NUMBER")));
/*     */       }
/*     */       
/* 795 */       return result;
/* 796 */     } catch (SQLException ex) {
/* 797 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Unable to load list of unacked message registration for %s sequence with id = [ %s ]: An unexpected JDBC exception occured", new Object[] { this.type, this.sequenceId }), ex));
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 803 */       this.cm.recycle(new PreparedStatement[] { ps });
/* 804 */       this.cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Long> getLastMessageNumberWithUnackedMessageNumbers() {
/* 809 */     Connection con = this.cm.getConnection(false);
/* 810 */     PreparedStatement ps = null;
/*     */     try {
/* 812 */       ps = this.cm.prepareStatement(con, "SELECT RM_SEQUENCES.LAST_MESSAGE_NUMBER AS LAST_NUMBER, RM_UNACKED_MESSAGES.MSG_NUMBER AS MESSAGE_NUMBER FROM RM_UNACKED_MESSAGES INNER JOIN RM_SEQUENCES ON RM_UNACKED_MESSAGES.ENDPOINT_UID=RM_SEQUENCES.ENDPOINT_UID AND RM_UNACKED_MESSAGES.SEQ_ID=RM_SEQUENCES.ID WHERE RM_UNACKED_MESSAGES.ENDPOINT_UID=? AND SEQ_ID=?");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 818 */       ps.setString(1, this.endpointUid);
/* 819 */       ps.setString(2, this.sequenceId);
/*     */       
/* 821 */       ResultSet rs = ps.executeQuery();
/*     */       
/* 823 */       List<Long> result = new LinkedList<Long>();
/* 824 */       if (rs.next()) {
/* 825 */         result.add(Long.valueOf(rs.getLong("LAST_NUMBER")));
/* 826 */         result.add(Long.valueOf(rs.getLong("MESSAGE_NUMBER")));
/*     */       } else {
/* 828 */         result.add(getFieldData(con, this.fLastMessageNumber));
/*     */       } 
/*     */       
/* 831 */       while (rs.next()) {
/* 832 */         result.add(Long.valueOf(rs.getLong("MESSAGE_NUMBER")));
/*     */       }
/*     */       
/* 835 */       this.cm.commit(con);
/*     */       
/* 837 */       return result;
/* 838 */     } catch (SQLException ex) {
/* 839 */       this.cm.rollback(con);
/* 840 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Unable to load list of unacked message registration for %s sequence with id = [ %s ]: An unexpected JDBC exception occured", new Object[] { this.type, this.sequenceId }), ex));
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 846 */       this.cm.recycle(new PreparedStatement[] { ps });
/* 847 */       this.cm.recycle(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void attachMessageToUnackedMessageNumber(ApplicationMessage message) {
/* 852 */     ByteArrayInputStream bais = null;
/* 853 */     Connection con = this.cm.getConnection(false);
/* 854 */     PreparedStatement ps = null;
/*     */     try {
/* 856 */       ps = this.cm.prepareStatement(con, "UPDATE RM_UNACKED_MESSAGES SET IS_RECEIVED=?, CORRELATION_ID=?, NEXT_RESEND_COUNT=?, WSA_ACTION=?, MSG_DATA=? WHERE ENDPOINT_UID=? AND SEQ_ID=? AND MSG_NUMBER=?");
/*     */ 
/*     */ 
/*     */       
/* 860 */       int i = 0;
/*     */       
/* 862 */       ps.setString(++i, b2s(true));
/*     */       
/* 864 */       ps.setString(++i, message.getCorrelationId());
/* 865 */       ps.setLong(++i, message.getNextResendCount());
/*     */       
/* 867 */       ps.setString(++i, ((JaxwsApplicationMessage)message).getWsaAction());
/* 868 */       byte[] msgData = message.toBytes();
/* 869 */       bais = new ByteArrayInputStream(msgData);
/* 870 */       ps.setBinaryStream(++i, bais, msgData.length);
/*     */       
/* 872 */       ps.setString(++i, this.endpointUid);
/* 873 */       ps.setString(++i, this.sequenceId);
/* 874 */       ps.setLong(++i, message.getMessageNumber());
/*     */       
/* 876 */       int rowsAffected = ps.executeUpdate();
/* 877 */       if (rowsAffected != 1) {
/* 878 */         this.cm.rollback(con);
/* 879 */         throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Storing message data in an unacked message registration for %s sequence with id = [ %s ] and message number [ %d ] has failed: Expected updated rows: 1, Actual: %d", new Object[] { this.type, this.sequenceId, Long.valueOf(message.getMessageNumber()), Integer.valueOf(rowsAffected) })));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 888 */       setFieldData(con, this.fLastActivityTime, Long.valueOf(this.ts.currentTimeInMillis()), false);
/*     */       
/* 890 */       this.cm.commit(con);
/* 891 */     } catch (SQLException ex) {
/* 892 */       this.cm.rollback(con);
/* 893 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Unable to store message data in an unacked message registration for %s sequence with id = [ %s ] and message number [ %d ]: An unexpected JDBC exception occured", new Object[] { this.type, this.sequenceId, Long.valueOf(message.getMessageNumber()) }), ex));
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 900 */       this.cm.recycle(new PreparedStatement[] { ps });
/* 901 */       this.cm.recycle(con);
/*     */       
/* 903 */       if (bais != null) {
/*     */         try {
/* 905 */           bais.close();
/* 906 */         } catch (IOException ex) {
/* 907 */           LOGGER.warning("Error closing ByteArrayOutputStream after message bytes were sent to DB", ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ApplicationMessage retrieveMessage(String correlationId) {
/* 915 */     Connection con = this.cm.getConnection(false);
/* 916 */     PreparedStatement ps = null;
/*     */     
/* 918 */     InputStream messageDataStream = null;
/*     */     try {
/* 920 */       ps = this.cm.prepareStatement(con, "SELECT MSG_NUMBER, NEXT_RESEND_COUNT, WSA_ACTION, MSG_DATA FROM RM_UNACKED_MESSAGES WHERE ENDPOINT_UID=? AND SEQ_ID=? AND CORRELATION_ID=?");
/*     */ 
/*     */       
/* 923 */       ps.setString(1, this.endpointUid);
/* 924 */       ps.setString(2, this.sequenceId);
/* 925 */       ps.setString(3, correlationId);
/*     */ 
/*     */       
/* 928 */       ResultSet rs = ps.executeQuery();
/* 929 */       if (!rs.next()) {
/* 930 */         return null;
/*     */       }
/*     */       
/* 933 */       if (!rs.isFirst() && !rs.isLast()) {
/* 934 */         this.cm.rollback(con);
/* 935 */         throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Duplicate records detected for unacked message registration on %s sequence with id = [ %s ] and correlation id [ %d ]", new Object[] { this.type, this.sequenceId, correlationId })));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 943 */       messageDataStream = rs.getBlob("MSG_DATA").getBinaryStream();
/* 944 */       JaxwsApplicationMessage jaxwsApplicationMessage = JaxwsApplicationMessage.newInstance(messageDataStream, rs.getInt("NEXT_RESEND_COUNT"), correlationId, rs.getString("WSA_ACTION"), this.sequenceId, rs.getLong("MSG_NUMBER"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 952 */       setFieldData(con, this.fLastActivityTime, Long.valueOf(this.ts.currentTimeInMillis()), false);
/*     */       
/* 954 */       this.cm.commit(con);
/*     */       
/* 956 */       return (ApplicationMessage)jaxwsApplicationMessage;
/* 957 */     } catch (SQLException ex) {
/* 958 */       this.cm.rollback(con);
/* 959 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(String.format("Unable to load message data from an unacked message registration for %s sequence with id = [ %s ] and correlation id [ %d ]: An unexpected JDBC exception occured", new Object[] { this.type, this.sequenceId, correlationId }), ex));
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 966 */       if (messageDataStream != null) try { messageDataStream.close(); } catch (IOException ex) {} 
/* 967 */       this.cm.recycle(new PreparedStatement[] { ps });
/* 968 */       this.cm.recycle(con);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\persistent\PersistentSequenceData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */