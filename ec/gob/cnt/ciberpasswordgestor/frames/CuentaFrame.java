/*     */ package ec.gob.cnt.ciberpasswordgestor.frames;
/*     */ import ec.gob.cnt.ciberpasswordgestor.model.Cuenta;
/*     */ import ec.gob.cnt.ciberpasswordgestor.model.ModeloTabla;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.DBConect;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.Encrypt;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.Utils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.LayoutStyle;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import javax.swing.border.SoftBevelBorder;
/*     */ 
/*     */ public class CuentaFrame extends JFrame {
/*  26 */   static Cuenta cuentaFinal = new Cuenta("", "");
/*     */   
/*     */   boolean isUpdate = false;
/*     */   
/*     */   boolean verPassword = false;
/*     */   static ModeloTabla modeloTablaCuentas;
/*     */   private JTextField cuentaField;
/*     */   private JButton jButton1;
/*     */   private JButton jButton4;
/*     */   private JButton jButton5;
/*     */   
/*     */   public CuentaFrame(boolean valor, Cuenta cuenta, ModeloTabla modeloTablaCuentas) {
/*  38 */     initComponents();
/*  39 */     setLocationRelativeTo(null);
/*  40 */     getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
/*     */     
/*  42 */     UIManager.put("OptionPane.background", Color.white);
/*  43 */     UIManager.put("Panel.background", Color.white);
/*  44 */     UIManager.put("Button.background", new Color(37, 84, 156));
/*  45 */     UIManager.put("Button.font", new Font("Segoe UI", 1, 14));
/*  46 */     UIManager.put("Button.foreground", new Color(255, 255, 255));
/*  47 */     this.jButton6.setForeground(new Color(0, 0, 0));
/*  48 */     this.cuentaField.requestFocus();
/*     */     
/*  50 */     setIconImage((new ImageIcon(getClass().getResource("/imgs/icono1.png"))).getImage());
/*  51 */     setTitle("CPM Datos");
/*     */     
/*  53 */     setDefaultCloseOperation(2);
/*  54 */     this.isUpdate = valor;
/*  55 */     if (valor) {
/*  56 */       cargarDatos(cuenta);
/*  57 */       System.out.println("cuenta");
/*  58 */       System.out.println(cuenta.getId());
/*  59 */       System.out.println(cuenta.getUser());
/*     */     } 
/*  61 */     cuentaFinal = cuenta;
/*  62 */     CuentaFrame.modeloTablaCuentas = modeloTablaCuentas;
/*     */   }
/*     */   private JButton jButton6; private JButton jButton7; private JLabel jLabel1; private JLabel jLabel2; private JLabel jLabel3; private JPanel jPanel1; private JPasswordField passwordField; private JTextField usuarioField;
/*     */   
/*     */   private void cargarDatos(Cuenta cuenta) {
/*  67 */     this.cuentaField.setText(cuenta.getCuenta());
/*  68 */     this.usuarioField.setText(cuenta.getUser());
/*  69 */     this.passwordField.setText(cuenta.getPassword());
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
/*     */   private void initComponents() {
/*  81 */     this.jPanel1 = new JPanel();
/*  82 */     this.jLabel1 = new JLabel();
/*  83 */     this.cuentaField = new JTextField();
/*  84 */     this.jLabel2 = new JLabel();
/*  85 */     this.usuarioField = new JTextField();
/*  86 */     this.jLabel3 = new JLabel();
/*  87 */     this.passwordField = new JPasswordField();
/*  88 */     this.jButton4 = new JButton();
/*  89 */     this.jButton1 = new JButton();
/*  90 */     this.jButton5 = new JButton();
/*  91 */     this.jButton6 = new JButton();
/*  92 */     this.jButton7 = new JButton();
/*     */     
/*  94 */     setDefaultCloseOperation(3);
/*  95 */     setUndecorated(true);
/*     */     
/*  97 */     this.jPanel1.setBackground(new Color(255, 255, 255));
/*     */     
/*  99 */     this.jLabel1.setFont(new Font("Segoe UI", 0, 14));
/* 100 */     this.jLabel1.setText("Cuenta");
/*     */     
/* 102 */     this.cuentaField.setBackground(new Color(204, 204, 204));
/* 103 */     this.cuentaField.setFont(new Font("Segoe UI", 0, 14));
/* 104 */     this.cuentaField.setBorder(new SoftBevelBorder(0));
/*     */     
/* 106 */     this.jLabel2.setFont(new Font("Segoe UI", 0, 14));
/* 107 */     this.jLabel2.setText("Usuario");
/*     */     
/* 109 */     this.usuarioField.setBackground(new Color(204, 204, 204));
/* 110 */     this.usuarioField.setFont(new Font("Segoe UI", 0, 14));
/* 111 */     this.usuarioField.setBorder(new SoftBevelBorder(0));
/*     */     
/* 113 */     this.jLabel3.setFont(new Font("Segoe UI", 0, 14));
/* 114 */     this.jLabel3.setText("Password");
/*     */     
/* 116 */     this.passwordField.setBackground(new Color(204, 204, 204));
/* 117 */     this.passwordField.setFont(new Font("Segoe UI", 0, 14));
/* 118 */     this.passwordField.setBorder(new SoftBevelBorder(0));
/*     */     
/* 120 */     this.jButton4.setBackground(new Color(37, 84, 156));
/* 121 */     this.jButton4.setFont(new Font("Segoe UI", 1, 14));
/* 122 */     this.jButton4.setForeground(new Color(255, 255, 255));
/* 123 */     this.jButton4.setText("Cancelar");
/* 124 */     this.jButton4.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 126 */             CuentaFrame.this.jButton4ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 130 */     this.jButton1.setBackground(new Color(37, 84, 156));
/* 131 */     this.jButton1.setFont(new Font("Segoe UI", 1, 14));
/* 132 */     this.jButton1.setForeground(new Color(255, 255, 255));
/* 133 */     this.jButton1.setText("Guardar");
/* 134 */     this.jButton1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 136 */             CuentaFrame.this.jButton1ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 140 */     this.jButton5.setBackground(new Color(203, 0, 0));
/* 141 */     this.jButton5.setFont(new Font("Calibri", 1, 18));
/* 142 */     this.jButton5.setForeground(new Color(255, 255, 255));
/* 143 */     this.jButton5.setText("X");
/* 144 */     this.jButton5.setAlignmentY(0.0F);
/* 145 */     this.jButton5.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 147 */             CuentaFrame.this.jButton5ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 151 */     this.jButton6.setBackground(new Color(254, 254, 254));
/* 152 */     this.jButton6.setText("Generar Clave");
/* 153 */     this.jButton6.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 155 */             CuentaFrame.this.jButton6ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 159 */     this.jButton7.setBackground(new Color(254, 254, 254));
/* 160 */     this.jButton7.setIcon(new ImageIcon(getClass().getResource("/imgs/verPassword.png")));
/* 161 */     this.jButton7.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 163 */             CuentaFrame.this.jButton7ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 167 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 168 */     this.jPanel1.setLayout(jPanel1Layout);
/* 169 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/* 170 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 171 */         .addGroup(jPanel1Layout.createSequentialGroup()
/* 172 */           .addGap(24, 24, 24)
/* 173 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 174 */             .addComponent(this.jLabel3)
/* 175 */             .addComponent(this.jLabel2)
/* 176 */             .addComponent(this.jLabel1)
/* 177 */             .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
/* 178 */               .addGroup(jPanel1Layout.createSequentialGroup()
/* 179 */                 .addComponent(this.passwordField)
/* 180 */                 .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 181 */                 .addComponent(this.jButton7, -2, 53, -2))
/* 182 */               .addComponent(this.jButton6, GroupLayout.Alignment.LEADING)
/* 183 */               .addComponent(this.cuentaField, GroupLayout.Alignment.LEADING)
/* 184 */               .addGroup(jPanel1Layout.createSequentialGroup()
/* 185 */                 .addComponent(this.jButton1, -2, 136, -2)
/* 186 */                 .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 187 */                 .addComponent(this.jButton4, -2, 131, -2))
/* 188 */               .addComponent(this.usuarioField, GroupLayout.Alignment.LEADING)))
/* 189 */           .addContainerGap(29, 32767))
/* 190 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
/* 191 */           .addGap(0, 0, 32767)
/* 192 */           .addComponent(this.jButton5, -2, 49, -2)));
/*     */     
/* 194 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/* 195 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 196 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
/* 197 */           .addComponent(this.jButton5, -2, 47, -2)
/* 198 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, 32767)
/* 199 */           .addComponent(this.jLabel1)
/* 200 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 201 */           .addComponent(this.cuentaField, -2, -1, -2)
/* 202 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 203 */           .addComponent(this.jLabel2)
/* 204 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 205 */           .addComponent(this.usuarioField, -2, -1, -2)
/* 206 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 207 */           .addComponent(this.jLabel3)
/* 208 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 209 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 210 */             .addComponent(this.jButton7)
/* 211 */             .addComponent(this.passwordField, -2, -1, -2))
/* 212 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 213 */           .addComponent(this.jButton6)
/* 214 */           .addGap(34, 34, 34)
/* 215 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 216 */             .addComponent(this.jButton4, -2, 38, -2)
/* 217 */             .addComponent(this.jButton1, -2, 38, -2))
/* 218 */           .addGap(33, 33, 33)));
/*     */ 
/*     */     
/* 221 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 222 */     getContentPane().setLayout(layout);
/* 223 */     layout.setHorizontalGroup(layout
/* 224 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 225 */         .addComponent(this.jPanel1, -1, -1, 32767));
/*     */     
/* 227 */     layout.setVerticalGroup(layout
/* 228 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 229 */         .addGroup(layout.createSequentialGroup()
/* 230 */           .addComponent(this.jPanel1, -2, -1, -2)
/* 231 */           .addGap(0, 0, 32767)));
/*     */ 
/*     */     
/* 234 */     pack();
/*     */   }
/*     */   
/*     */   private void jButton1ActionPerformed(ActionEvent evt) {
/* 238 */     DBConect dBConect = new DBConect();
/*     */     
/* 240 */     if (this.isUpdate) {
/*     */       try {
/* 242 */         cuentaFinal.setCuenta(Encrypt.encryptText(Utils.KEY, this.cuentaField.getText()));
/* 243 */         cuentaFinal.setUser(Encrypt.encryptText(Utils.KEY, this.usuarioField.getText()));
/* 244 */         cuentaFinal.setPassword(Encrypt.encryptText(Utils.KEY, String.valueOf(this.passwordField.getPassword())));
/* 245 */         System.out.println("update");
/* 246 */         System.out.println("id" + cuentaFinal.getId());
/* 247 */         if (dBConect.updateCuenta(cuentaFinal)) {
/* 248 */           JOptionPane.showMessageDialog(this, "Registro actualizado con éxito", "Actualiza Registro", 1);
/* 249 */           dispose();
/*     */         } 
/* 251 */       } catch (Exception ex) {
/* 252 */         Logger.getLogger(CuentaFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */       } 
/*     */     } else {
/*     */       
/*     */       try {
/* 257 */         cuentaFinal = new Cuenta(0, Encrypt.encryptText(Utils.KEY, this.cuentaField.getText()), Encrypt.encryptText(Utils.KEY, this.usuarioField.getText()), Encrypt.encryptText(Utils.KEY, String.valueOf(this.passwordField.getPassword())));
/*     */         
/* 259 */         if (dBConect.insertCuenta(cuentaFinal)) {
/* 260 */           JOptionPane.showMessageDialog(this, "Registro insertado con éxito", "Nuevo Registro", 1);
/* 261 */           dispose();
/*     */         }
/*     */       
/* 264 */       } catch (Exception ex) {
/* 265 */         Logger.getLogger(CuentaFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 271 */     VentanaPrincipal.actualizaModelo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void jButton4ActionPerformed(ActionEvent evt) {
/* 278 */     dispose();
/*     */   }
/*     */   
/*     */   private void jButton5ActionPerformed(ActionEvent evt) {
/* 282 */     dispose();
/*     */   }
/*     */   
/*     */   private void jButton6ActionPerformed(ActionEvent evt) {
/* 286 */     this.passwordField.setText(Utils.generatePassword());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void jButton7ActionPerformed(ActionEvent evt) {
/* 292 */     if (this.verPassword) {
/* 293 */       this.passwordField.setEchoChar('*');
/* 294 */       this.verPassword = false;
/*     */     } else {
/* 296 */       this.passwordField.setEchoChar(false);
/* 297 */       this.verPassword = true;
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
/*     */   public static void main(String[] args) {
/*     */     try {
/* 312 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
/* 313 */         if ("Nimbus".equals(info.getName())) {
/* 314 */           UIManager.setLookAndFeel(info.getClassName());
/*     */           break;
/*     */         } 
/*     */       } 
/* 318 */     } catch (ClassNotFoundException ex) {
/* 319 */       Logger.getLogger(CuentaFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 320 */     } catch (InstantiationException ex) {
/* 321 */       Logger.getLogger(CuentaFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 322 */     } catch (IllegalAccessException ex) {
/* 323 */       Logger.getLogger(CuentaFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 324 */     } catch (UnsupportedLookAndFeelException ex) {
/* 325 */       Logger.getLogger(CuentaFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     EventQueue.invokeLater(new Runnable() {
/*     */           public void run() {
/* 333 */             (new CuentaFrame(false, CuentaFrame.cuentaFinal, CuentaFrame.modeloTablaCuentas)).setVisible(true);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgestor\frames\CuentaFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */