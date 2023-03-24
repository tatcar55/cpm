/*     */ package ec.gob.cnt.ciberpasswordgestor.frames;
/*     */ import ec.gob.cnt.ciberpasswordgestor.model.Cuenta;
/*     */ import ec.gob.cnt.ciberpasswordgestor.model.ModeloTabla;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.DBConect;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ public class VentanaPrincipal extends JFrame {
/*  39 */   static ArrayList<ArrayList<Object>> passw = new ArrayList<>();
/*     */   
/*     */   public static ModeloTabla modeloTablaCuentas;
/*     */   
/*     */   boolean isUpdate = false;
/*  44 */   JPopupMenu popupMenu = new JPopupMenu();
/*  45 */   JMenuItem copyItem = new JMenuItem("Copy"); private JButton btnMostrarOcultar; private JButton jBobtenerDatos;
/*     */   private JButton jButton1;
/*     */   private JButton jButton2;
/*     */   private JButton jButton5;
/*     */   private JDialog jDialog1;
/*     */   
/*     */   public VentanaPrincipal() {
/*  52 */     initComponents();
/*  53 */     getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
/*  54 */     Font font = new Font("Segoe UI", 0, 14);
/*  55 */     jTcuentas.setFont(font);
/*  56 */     JTableHeader header = jTcuentas.getTableHeader();
/*  57 */     header.setBackground(new Color(37, 84, 156));
/*  58 */     header.setForeground(new Color(255, 255, 255));
/*  59 */     header.setFont(new Font("Segoe UI", 1, 16));
/*  60 */     header.setOpaque(false);
/*  61 */     jTcuentas.setRowHeight(25);
/*  62 */     this.jLabel5.requestFocus();
/*  63 */     this.popupMenu.add(this.copyItem);
/*     */     
/*  65 */     setIconImage((new ImageIcon(getClass().getResource("/imgs/icono1.png"))).getImage());
/*  66 */     setTitle("CPM Cuentas");
/*     */ 
/*     */ 
/*     */     
/*  70 */     getRootPane().setBorder(BorderFactory.createEtchedBorder(1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     setLocationRelativeTo(null);
/*  76 */     setModeloTabla();
/*     */     
/*  78 */     UIManager.put("OptionPane.background", Color.white);
/*  79 */     UIManager.put("Panel.background", Color.white);
/*  80 */     UIManager.put("Button.background", new Color(32, 154, 227));
/*  81 */     UIManager.put("Button.font", new Font("Segoe UI", 1, 14));
/*  82 */     UIManager.put("Button.foreground", new Color(255, 255, 255));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JDialog jDialog2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JLabel jLabel5;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JLabel jLabel7;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel jPanel1;
/*     */ 
/*     */ 
/*     */   
/*     */   private JScrollPane jScrollPane1;
/*     */ 
/*     */ 
/*     */   
/*     */   private static JTable jTcuentas;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setModeloTabla() {
/* 118 */     DBConect dBConect = new DBConect();
/* 119 */     modeloTablaCuentas = new ModeloTabla();
/*     */     
/* 121 */     if (!dBConect.obtenerCuentas().isEmpty()) {
/*     */       
/* 123 */       ArrayList<Cuenta> lista = new ArrayList<>(dBConect.obtenerCuentas());
/*     */       
/* 125 */       for (int i = 0; i < lista.size(); i++) {
/* 126 */         Cuenta cuenta = lista.get(i);
/* 127 */         ArrayList<Object> row1 = new ArrayList();
/* 128 */         row1.add(cuenta.getPassword());
/* 129 */         row1.add(Boolean.valueOf(true));
/* 130 */         passw.add(row1);
/* 131 */         cuenta.setPassword("********");
/* 132 */         lista.set(i, cuenta);
/*     */       } 
/*     */       
/* 135 */       modeloTablaCuentas.setCuentas(lista);
/*     */       
/* 137 */       jTcuentas.setModel((TableModel)modeloTablaCuentas);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void actualizaModelo() {
/* 148 */     System.out.println("actualzia modelo");
/*     */ 
/*     */     
/* 151 */     DBConect dBConect = new DBConect();
/* 152 */     modeloTablaCuentas = new ModeloTabla();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     if (!dBConect.obtenerCuentas().isEmpty()) {
/* 158 */       passw = new ArrayList<>();
/* 159 */       ArrayList<Cuenta> lista = new ArrayList<>(dBConect.obtenerCuentas());
/*     */       
/* 161 */       for (int i = 0; i < lista.size(); i++) {
/* 162 */         Cuenta cuenta = lista.get(i);
/* 163 */         ArrayList<Object> row1 = new ArrayList();
/* 164 */         row1.add(cuenta.getPassword());
/* 165 */         row1.add(Boolean.valueOf(true));
/* 166 */         passw.add(row1);
/* 167 */         cuenta.setPassword("********");
/* 168 */         lista.set(i, cuenta);
/*     */       } 
/* 170 */       modeloTablaCuentas.setCuentas(lista);
/* 171 */       System.out.println("obtener cuentas");
/* 172 */       jTcuentas.setModel((TableModel)modeloTablaCuentas);
/* 173 */       modeloTablaCuentas.fireTableDataChanged();
/*     */     } else {
/* 175 */       ArrayList<Cuenta> lista = new ArrayList<>();
/* 176 */       modeloTablaCuentas.setCuentas(lista);
/* 177 */       jTcuentas.setModel((TableModel)modeloTablaCuentas);
/* 178 */       System.out.println("tabla vacia");
/* 179 */       modeloTablaCuentas.fireTableDataChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void verPassword() {
/* 185 */     int fila = jTcuentas.getSelectedRow();
/*     */     
/* 187 */     if (fila < 0) {
/* 188 */       JOptionPane.showMessageDialog(this, "Debe Seleccionar una Fila", "Error", 0);
/*     */       
/*     */       return;
/*     */     } 
/* 192 */     System.out.println(((ArrayList<String>)passw.get(fila)).get(0));
/* 193 */     System.out.println(((ArrayList<Boolean>)passw.get(fila)).get(1));
/*     */     
/* 195 */     if (((Boolean)((ArrayList<Boolean>)passw.get(fila)).get(1)).booleanValue()) {
/* 196 */       System.out.println("entra true");
/* 197 */       modeloTablaCuentas.setValueAt(((ArrayList<String>)passw.get(fila)).get(0), fila, 2);
/* 198 */       ArrayList<Object> row1 = passw.get(fila);
/* 199 */       row1.set(1, Boolean.valueOf(false));
/* 200 */       passw.set(fila, row1);
/*     */     } else {
/* 202 */       System.out.println("entra false");
/* 203 */       modeloTablaCuentas.setValueAt("********", fila, 2);
/* 204 */       ArrayList<Object> row1 = passw.get(fila);
/* 205 */       row1.set(1, Boolean.valueOf(true));
/* 206 */       passw.set(fila, row1);
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
/*     */   
/*     */   private void initComponents() {
/* 226 */     this.jDialog1 = new JDialog();
/* 227 */     this.jDialog2 = new JDialog();
/* 228 */     this.jPanel1 = new JPanel();
/* 229 */     this.jScrollPane1 = new JScrollPane();
/* 230 */     jTcuentas = new JTable();
/* 231 */     this.jButton2 = new JButton();
/* 232 */     this.jBobtenerDatos = new JButton();
/* 233 */     this.jButton1 = new JButton();
/* 234 */     this.jButton5 = new JButton();
/* 235 */     this.btnMostrarOcultar = new JButton();
/* 236 */     this.jLabel7 = new JLabel();
/* 237 */     this.jLabel5 = new JLabel();
/*     */     
/* 239 */     GroupLayout jDialog1Layout = new GroupLayout(this.jDialog1.getContentPane());
/* 240 */     this.jDialog1.getContentPane().setLayout(jDialog1Layout);
/* 241 */     jDialog1Layout.setHorizontalGroup(jDialog1Layout
/* 242 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 243 */         .addGap(0, 400, 32767));
/*     */     
/* 245 */     jDialog1Layout.setVerticalGroup(jDialog1Layout
/* 246 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 247 */         .addGap(0, 300, 32767));
/*     */ 
/*     */     
/* 250 */     this.jDialog2.setAlwaysOnTop(true);
/*     */     
/* 252 */     GroupLayout jDialog2Layout = new GroupLayout(this.jDialog2.getContentPane());
/* 253 */     this.jDialog2.getContentPane().setLayout(jDialog2Layout);
/* 254 */     jDialog2Layout.setHorizontalGroup(jDialog2Layout
/* 255 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 256 */         .addGap(0, 400, 32767));
/*     */     
/* 258 */     jDialog2Layout.setVerticalGroup(jDialog2Layout
/* 259 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 260 */         .addGap(0, 300, 32767));
/*     */ 
/*     */     
/* 263 */     setDefaultCloseOperation(3);
/* 264 */     setUndecorated(true);
/*     */     
/* 266 */     this.jPanel1.setBackground(new Color(255, 255, 255));
/*     */     
/* 268 */     jTcuentas.setModel(new DefaultTableModel(new Object[0][], (Object[])new String[] { "Cuenta", "Usuario", "Password" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     jTcuentas.setSelectionBackground(new Color(153, 204, 255));
/* 277 */     jTcuentas.addMouseListener(new MouseAdapter() {
/*     */           public void mouseClicked(MouseEvent evt) {
/* 279 */             VentanaPrincipal.this.jTcuentasMouseClicked(evt);
/*     */           }
/*     */         });
/* 282 */     jTcuentas.addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent evt) {
/* 284 */             VentanaPrincipal.this.jTcuentasKeyPressed(evt);
/*     */           }
/*     */           public void keyTyped(KeyEvent evt) {
/* 287 */             VentanaPrincipal.this.jTcuentasKeyTyped(evt);
/*     */           }
/*     */         });
/* 290 */     this.jScrollPane1.setViewportView(jTcuentas);
/*     */     
/* 292 */     this.jButton2.setBackground(new Color(254, 254, 254));
/* 293 */     this.jButton2.setFont(new Font("Segoe UI", 1, 12));
/* 294 */     this.jButton2.setForeground(new Color(255, 255, 255));
/* 295 */     this.jButton2.setIcon(new ImageIcon(getClass().getResource("/imgs/nuevo.png")));
/* 296 */     this.jButton2.setToolTipText("Crear Nuevo");
/* 297 */     this.jButton2.setMargin(new Insets(2, 4, 2, 4));
/* 298 */     this.jButton2.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 300 */             VentanaPrincipal.this.jButton2ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 304 */     this.jBobtenerDatos.setBackground(new Color(254, 254, 254));
/* 305 */     this.jBobtenerDatos.setFont(new Font("Segoe UI", 1, 12));
/* 306 */     this.jBobtenerDatos.setForeground(new Color(255, 255, 255));
/* 307 */     this.jBobtenerDatos.setIcon(new ImageIcon(getClass().getResource("/imgs/editar.png")));
/* 308 */     this.jBobtenerDatos.setToolTipText("Modificar");
/* 309 */     this.jBobtenerDatos.setMargin(new Insets(2, 4, 2, 4));
/* 310 */     this.jBobtenerDatos.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 312 */             VentanaPrincipal.this.jBobtenerDatosActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 316 */     this.jButton1.setBackground(new Color(254, 254, 254));
/* 317 */     this.jButton1.setFont(new Font("Segoe UI", 1, 12));
/* 318 */     this.jButton1.setForeground(new Color(255, 255, 255));
/* 319 */     this.jButton1.setIcon(new ImageIcon(getClass().getResource("/imgs/eliminar.png")));
/* 320 */     this.jButton1.setToolTipText("Eliminar");
/* 321 */     this.jButton1.setMargin(new Insets(2, 4, 2, 4));
/* 322 */     this.jButton1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 324 */             VentanaPrincipal.this.jButton1ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 328 */     this.jButton5.setBackground(new Color(203, 0, 0));
/* 329 */     this.jButton5.setFont(new Font("Calibri", 1, 18));
/* 330 */     this.jButton5.setForeground(new Color(255, 255, 255));
/* 331 */     this.jButton5.setText("X");
/* 332 */     this.jButton5.setAlignmentY(0.0F);
/* 333 */     this.jButton5.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 335 */             VentanaPrincipal.this.jButton5ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 339 */     this.btnMostrarOcultar.setBackground(new Color(254, 254, 254));
/* 340 */     this.btnMostrarOcultar.setFont(new Font("Segoe UI", 1, 12));
/* 341 */     this.btnMostrarOcultar.setForeground(new Color(255, 255, 255));
/* 342 */     this.btnMostrarOcultar.setIcon(new ImageIcon(getClass().getResource("/imgs/ver.png")));
/* 343 */     this.btnMostrarOcultar.setToolTipText("Ver / Ocultar");
/* 344 */     this.btnMostrarOcultar.setMargin(new Insets(2, 4, 2, 4));
/* 345 */     this.btnMostrarOcultar.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 347 */             VentanaPrincipal.this.btnMostrarOcultarActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 351 */     this.jLabel7.setIcon(new ImageIcon(getClass().getResource("/imgs/logoblanco2.png")));
/*     */     
/* 353 */     this.jLabel5.setFont(new Font("Cambria Math", 0, 10));
/* 354 */     this.jLabel5.setText("CNT - JCSO v1.0.0");
/*     */     
/* 356 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 357 */     this.jPanel1.setLayout(jPanel1Layout);
/* 358 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/* 359 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 360 */         .addGroup(jPanel1Layout.createSequentialGroup()
/* 361 */           .addGap(24, 24, 24)
/* 362 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 363 */             .addComponent(this.jScrollPane1, -2, 634, -2)
/* 364 */             .addComponent(this.jLabel7))
/* 365 */           .addGap(18, 18, 18)
/* 366 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 367 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 368 */               .addGap(0, 10, 32767)
/* 369 */               .addComponent(this.jButton5, -2, 49, -2))
/* 370 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 371 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
/* 372 */                 .addComponent(this.btnMostrarOcultar, GroupLayout.Alignment.LEADING, -1, -1, 32767)
/* 373 */                 .addComponent(this.jBobtenerDatos, GroupLayout.Alignment.LEADING, -1, -1, 32767)
/* 374 */                 .addComponent(this.jButton1, GroupLayout.Alignment.LEADING, -1, -1, 32767)
/* 375 */                 .addComponent(this.jButton2, -1, -1, 32767))
/* 376 */               .addGap(0, 0, 32767))))
/* 377 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
/* 378 */           .addContainerGap(-1, 32767)
/* 379 */           .addComponent(this.jLabel5)
/* 380 */           .addContainerGap()));
/*     */     
/* 382 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/* 383 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 384 */         .addGroup(jPanel1Layout.createSequentialGroup()
/* 385 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 386 */             .addComponent(this.jButton5, -2, 47, -2)
/* 387 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 388 */               .addContainerGap()
/* 389 */               .addComponent(this.jLabel7, -2, 46, -2)))
/* 390 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 391 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 392 */               .addGap(74, 74, 74)
/* 393 */               .addComponent(this.jButton2, -2, 37, -2)
/* 394 */               .addGap(18, 18, 18)
/* 395 */               .addComponent(this.jBobtenerDatos)
/* 396 */               .addGap(18, 18, 18)
/* 397 */               .addComponent(this.jButton1)
/* 398 */               .addGap(18, 18, 18)
/* 399 */               .addComponent(this.btnMostrarOcultar))
/* 400 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 401 */               .addGap(18, 18, 18)
/* 402 */               .addComponent(this.jScrollPane1, -2, 653, -2)))
/* 403 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 19, 32767)
/* 404 */           .addComponent(this.jLabel5)));
/*     */ 
/*     */     
/* 407 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 408 */     getContentPane().setLayout(layout);
/* 409 */     layout.setHorizontalGroup(layout
/* 410 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 411 */         .addComponent(this.jPanel1, -1, -1, 32767));
/*     */     
/* 413 */     layout.setVerticalGroup(layout
/* 414 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 415 */         .addComponent(this.jPanel1, -1, -1, 32767));
/*     */ 
/*     */     
/* 418 */     pack();
/*     */   }
/*     */ 
/*     */   
/*     */   private void jBobtenerDatosActionPerformed(ActionEvent evt) {
/* 423 */     int fila = jTcuentas.getSelectedRow();
/*     */     
/* 425 */     if (fila < 0) {
/* 426 */       JOptionPane.showMessageDialog(this, "Debe Seleccionar una Fila", "Error", 0);
/*     */       return;
/*     */     } 
/* 429 */     Cuenta cuenta = modeloTablaCuentas.getCuentas().get(fila);
/* 430 */     cuenta.setPassword(((ArrayList<String>)passw.get(fila)).get(0));
/*     */ 
/*     */     
/* 433 */     CuentaFrame cf = new CuentaFrame(true, cuenta, modeloTablaCuentas);
/* 434 */     cf.setVisible(true);
/*     */   }
/*     */   
/*     */   private void jButton2ActionPerformed(ActionEvent evt) {
/* 438 */     Cuenta cuenta = new Cuenta("", "");
/* 439 */     CuentaFrame cf = new CuentaFrame(false, cuenta, modeloTablaCuentas);
/* 440 */     cf.setVisible(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void jButton1ActionPerformed(ActionEvent evt) {
/* 445 */     DBConect dBConect = new DBConect();
/* 446 */     int fila = jTcuentas.getSelectedRow();
/*     */     
/* 448 */     if (fila < 0) {
/* 449 */       JOptionPane.showMessageDialog(this, "Debe Seleccionar una Fila", "Error", 0);
/*     */       return;
/*     */     } 
/* 452 */     Cuenta cuenta = modeloTablaCuentas.getCuentas().get(fila);
/*     */     
/* 454 */     int input = JOptionPane.showOptionDialog(null, "¿Está seguro de elminar el registro?", "Eliminar", 2, 3, null, null, null);
/*     */     
/* 456 */     if (input == 0) {
/*     */       
/* 458 */       dBConect.deleteCuenta(cuenta);
/* 459 */       actualizaModelo();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void jButton5ActionPerformed(ActionEvent evt) {
/* 466 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void btnMostrarOcultarActionPerformed(ActionEvent evt) {
/* 471 */     verPassword();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void jTcuentasKeyPressed(KeyEvent evt) {
/* 477 */     if (evt.getKeyCode() == 67 && evt.isControlDown()) {
/* 478 */       JTable target = (JTable)evt.getSource();
/* 479 */       int row = target.getSelectedRow();
/* 480 */       int column = target.getSelectedColumn();
/*     */       
/* 482 */       String text = (String)target.getValueAt(row, column);
/* 483 */       Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 484 */       StringSelection stringSelection = new StringSelection(text);
/* 485 */       clipboard.setContents(stringSelection, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void jTcuentasKeyTyped(KeyEvent evt) {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void jTcuentasMouseClicked(MouseEvent evt) {
/* 497 */     if (evt.getClickCount() == 2) {
/* 498 */       JTable target = (JTable)evt.getSource();
/* 499 */       int row = target.getSelectedRow();
/* 500 */       int column = target.getSelectedColumn();
/* 501 */       String text = (String)target.getValueAt(row, column);
/* 502 */       Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 503 */       StringSelection stringSelection = new StringSelection(text);
/* 504 */       clipboard.setContents(stringSelection, null);
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
/*     */   public static void main(String[] args) {
/*     */     try {
/* 520 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
/* 521 */         if ("Nimbus".equals(info.getName())) {
/* 522 */           UIManager.setLookAndFeel(info.getClassName());
/*     */           break;
/*     */         } 
/*     */       } 
/* 526 */     } catch (ClassNotFoundException ex) {
/* 527 */       Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 528 */     } catch (InstantiationException ex) {
/* 529 */       Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 530 */     } catch (IllegalAccessException ex) {
/* 531 */       Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 532 */     } catch (UnsupportedLookAndFeelException ex) {
/* 533 */       Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 539 */     EventQueue.invokeLater(new Runnable() {
/*     */           public void run() {
/* 541 */             (new VentanaPrincipal()).setVisible(true);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgestor\frames\VentanaPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */