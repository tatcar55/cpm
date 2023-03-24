/*     */ package ec.gob.cnt.ciberpasswordgestor.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModeloTabla
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private final Class[] tipoColumnas;
/*     */   private final String[] titleColumnas;
/*     */   private List<Cuenta> cuentas;
/*     */   
/*     */   public List<Cuenta> getCuentas() {
/*  24 */     return this.cuentas;
/*     */   }
/*     */   
/*     */   public void setCuentas(List<Cuenta> cuentas) {
/*  28 */     this.cuentas = cuentas;
/*     */   }
/*     */   
/*     */   public ModeloTabla() {
/*  32 */     this.cuentas = new ArrayList<>();
/*  33 */     this.titleColumnas = new String[] { "Cuenta", "Usuario", "Password" };
/*  34 */     this.tipoColumnas = new Class[] { String.class, String.class, String.class };
/*     */   }
/*     */ 
/*     */   
/*     */   public String getColumnName(int column) {
/*  39 */     return this.titleColumnas[column];
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> getColumnClass(int columnIndex) {
/*  44 */     return this.tipoColumnas[columnIndex];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int rowIndex, int columnIndex) {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/*  54 */     return this.cuentas.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/*  59 */     return this.titleColumnas.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValueAt(int rowIndex, int columnIndex) {
/*  65 */     switch (columnIndex) {
/*     */       case 0:
/*  67 */         return ((Cuenta)this.cuentas.get(rowIndex)).getCuenta();
/*     */       case 1:
/*  69 */         return ((Cuenta)this.cuentas.get(rowIndex)).getUser();
/*     */       case 2:
/*  71 */         return ((Cuenta)this.cuentas.get(rowIndex)).getPassword();
/*     */     } 
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
/*  79 */     switch (columnIndex) {
/*     */       case 0:
/*  81 */         ((Cuenta)this.cuentas.get(rowIndex)).setUser(aValue.toString());
/*     */         break;
/*     */       case 1:
/*  84 */         ((Cuenta)this.cuentas.get(rowIndex)).setPassword(aValue.toString());
/*     */         break;
/*     */     } 
/*     */     
/*  88 */     fireTableCellUpdated(rowIndex, columnIndex);
/*  89 */     fireTableRowsUpdated(rowIndex, rowIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueAt(String aValue, int rowIndex, int columnIndex) {
/*  94 */     switch (columnIndex) {
/*     */       case 0:
/*  96 */         ((Cuenta)this.cuentas.get(rowIndex)).setCuenta(aValue);
/*     */         break;
/*     */       case 1:
/*  99 */         ((Cuenta)this.cuentas.get(rowIndex)).setUser(aValue);
/*     */         break;
/*     */       case 2:
/* 102 */         ((Cuenta)this.cuentas.get(rowIndex)).setPassword(aValue);
/*     */         break;
/*     */     } 
/*     */     
/* 106 */     fireTableCellUpdated(rowIndex, columnIndex);
/* 107 */     fireTableRowsUpdated(rowIndex, rowIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgestor\model\ModeloTabla.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */