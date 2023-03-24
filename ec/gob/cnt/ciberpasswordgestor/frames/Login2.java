/*     */ package ec.gob.cnt.ciberpasswordgestor.frames;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.DBConect;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.Encrypt;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.Utils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.File;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.LayoutStyle;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.SoftBevelBorder;
/*     */ 
/*     */ public class Login2 extends JFrame {
/*  25 */   String textoError = ""; boolean verPassword = false; private JButton jButton1; private JButton jButton3; private JButton jButton5; private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   
/*     */   public Login2() {
/*  32 */     initComponents();
/*  33 */     setLocationRelativeTo(null);
/*  34 */     getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
/*  35 */     this.msgError.setVisible(false);
/*  36 */     this.jLabelRestart.setFont(this.jLabelRestart.getFont().deriveFont(0));
/*     */     
/*  38 */     UIManager.put("OptionPane.background", Color.white);
/*  39 */     UIManager.put("Panel.background", Color.white);
/*  40 */     UIManager.put("Button.background", new Color(37, 84, 156));
/*  41 */     UIManager.put("Button.font", new Font("Segoe UI", 1, 14));
/*  42 */     UIManager.put("Button.foreground", new Color(255, 255, 255));
/*  43 */     this.usuarioField.requestFocus();
/*     */     
/*  45 */     setIconImage((new ImageIcon(getClass().getResource("/imgs/icono1.png"))).getImage());
/*  46 */     setTitle("CPM Login");
/*     */   }
/*     */   private JLabel jLabel7; private JLabel jLabel8; private JLabel jLabelRestart; private JPanel jPanel1; private JPanel jPanel2; private JPanel jPanel3; private JLabel msgError; private JPasswordField passwordField; private JTextField usuarioField;
/*     */   private void login() {
/*  50 */     Utils.USER = this.usuarioField.getText();
/*  51 */     Utils.PASS = String.valueOf(this.passwordField.getPassword());
/*     */     
/*  53 */     if (!Utils.USER.isEmpty() && !Utils.PASS.isEmpty() && Utils.PASS.length() >= 12) {
/*     */       try {
/*  55 */         DBConect dBConect = new DBConect();
/*     */ 
/*     */ 
/*     */         
/*  59 */         Utils.KEY = Utils.sKey(Encrypt.keyPass(Utils.PASS));
/*     */         
/*  61 */         if (dBConect.login(Encrypt.encryptText(Utils.KEY, Utils.USER), Encrypt.encryptText(Utils.KEY, Utils.PASS))) {
/*  62 */           Valida2FA vaFA = new Valida2FA();
/*  63 */           vaFA.setVisible(true);
/*  64 */           dispose();
/*     */         } else {
/*  66 */           mensajeError("Usuario y/o clave incorrectos " + this.textoError);
/*  67 */           System.out.println("Usuario no coincide con el de la bdd");
/*     */         } 
/*  69 */       } catch (Exception ex) {
/*     */         
/*  71 */         mensajeError("Usuario y/o clave incorrectos " + this.textoError);
/*  72 */         System.out.println("Error Usuario no coincide con el de la bdd");
/*     */       } 
/*     */     } else {
/*  75 */       mensajeError("Usuario y/o clave incorrectos " + this.textoError);
/*  76 */       System.out.println("Usuario y/o clave vacios incorrectos");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mensajeError(String mensaje) {
/*  87 */     System.out.println(mensaje);
/*  88 */     this.msgError.setText(mensaje);
/*  89 */     this.msgError.setVisible(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void eliminaBD() {
/*  94 */     File archivoEliminar = new File("data.db");
/*     */     
/*  96 */     if (archivoEliminar.exists())
/*  97 */     { archivoEliminar.delete();
/*  98 */       System.out.println("El archivo se eliminó correctamente"); }
/*  99 */     else { System.out.println("El archivo no existe"); }
/*     */   
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
/* 112 */     this.jPanel2 = new JPanel();
/* 113 */     this.jPanel3 = new JPanel();
/* 114 */     this.jLabel1 = new JLabel();
/* 115 */     this.jLabel2 = new JLabel();
/* 116 */     this.usuarioField = new JTextField();
/* 117 */     this.passwordField = new JPasswordField();
/* 118 */     this.jButton1 = new JButton();
/* 119 */     this.msgError = new JLabel();
/* 120 */     this.jButton5 = new JButton();
/* 121 */     this.jLabel3 = new JLabel();
/* 122 */     this.jLabel5 = new JLabel();
/* 123 */     this.jLabelRestart = new JLabel();
/* 124 */     this.jButton3 = new JButton();
/* 125 */     this.jLabel7 = new JLabel();
/* 126 */     this.jLabel8 = new JLabel();
/* 127 */     this.jPanel1 = new JPanel();
/* 128 */     this.jLabel4 = new JLabel();
/*     */     
/* 130 */     setDefaultCloseOperation(3);
/* 131 */     setBackground(new Color(37, 84, 156));
/* 132 */     setUndecorated(true);
/*     */     
/* 134 */     this.jPanel2.setBackground(new Color(254, 254, 254));
/*     */     
/* 136 */     this.jPanel3.setBackground(new Color(255, 255, 255));
/* 137 */     this.jPanel3.setPreferredSize(new Dimension(338, 350));
/*     */     
/* 139 */     this.jLabel1.setFont(new Font("Segoe UI", 1, 14));
/* 140 */     this.jLabel1.setText("Usuario");
/*     */     
/* 142 */     this.jLabel2.setFont(new Font("Segoe UI", 1, 14));
/* 143 */     this.jLabel2.setText("Password");
/*     */     
/* 145 */     this.usuarioField.setBackground(new Color(204, 204, 204));
/* 146 */     this.usuarioField.setFont(new Font("Segoe UI", 0, 14));
/* 147 */     this.usuarioField.setBorder(new SoftBevelBorder(0));
/* 148 */     this.usuarioField.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 150 */             Login2.this.usuarioFieldActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 154 */     this.passwordField.setBackground(new Color(204, 204, 204));
/* 155 */     this.passwordField.setFont(new Font("Segoe UI", 0, 14));
/* 156 */     this.passwordField.setBorder(new SoftBevelBorder(0));
/* 157 */     this.passwordField.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 159 */             Login2.this.passwordFieldActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 163 */     this.jButton1.setBackground(new Color(37, 84, 156));
/* 164 */     this.jButton1.setFont(new Font("Segoe UI", 1, 16));
/* 165 */     this.jButton1.setForeground(new Color(255, 255, 255));
/* 166 */     this.jButton1.setText("Inicia Sesión");
/* 167 */     this.jButton1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 169 */             Login2.this.jButton1ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 173 */     this.msgError.setForeground(new Color(255, 51, 51));
/* 174 */     this.msgError.setText("Usuario y/o clave incorrectos");
/*     */     
/* 176 */     this.jButton5.setBackground(new Color(203, 0, 0));
/* 177 */     this.jButton5.setFont(new Font("Calibri", 1, 18));
/* 178 */     this.jButton5.setForeground(new Color(255, 255, 255));
/* 179 */     this.jButton5.setText("X");
/* 180 */     this.jButton5.setAlignmentY(0.0F);
/* 181 */     this.jButton5.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 183 */             Login2.this.jButton5ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 187 */     this.jLabel3.setFont(new Font("Cambria Math", 0, 10));
/* 188 */     this.jLabel3.setText("CNT - JCSO v1.0.0");
/*     */     
/* 190 */     this.jLabel5.setFont(new Font("Segoe UI", 1, 18));
/* 191 */     this.jLabel5.setText("Bienvenido");
/*     */     
/* 193 */     this.jLabelRestart.setText("¿Olvidó su usuario y/o password?");
/* 194 */     this.jLabelRestart.addMouseListener(new MouseAdapter() {
/*     */           public void mouseClicked(MouseEvent evt) {
/* 196 */             Login2.this.jLabelRestartMouseClicked(evt);
/*     */           }
/*     */           public void mouseEntered(MouseEvent evt) {
/* 199 */             Login2.this.jLabelRestartMouseEntered(evt);
/*     */           }
/*     */           public void mouseExited(MouseEvent evt) {
/* 202 */             Login2.this.jLabelRestartMouseExited(evt);
/*     */           }
/*     */         });
/*     */     
/* 206 */     this.jButton3.setBackground(new Color(254, 254, 254));
/* 207 */     this.jButton3.setIcon(new ImageIcon(getClass().getResource("/imgs/verPassword.png")));
/* 208 */     this.jButton3.setToolTipText("Ver / Ocultar");
/* 209 */     this.jButton3.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 211 */             Login2.this.jButton3ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 215 */     this.jLabel7.setText("Inicia sesión con tu usario para gestionar tus ");
/*     */     
/* 217 */     this.jLabel8.setText("contraseñas. ");
/*     */     
/* 219 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/* 220 */     this.jPanel3.setLayout(jPanel3Layout);
/* 221 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout
/* 222 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 223 */         .addGroup(jPanel3Layout.createSequentialGroup()
/* 224 */           .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 225 */             .addGroup(jPanel3Layout.createSequentialGroup()
/* 226 */               .addGap(53, 53, 53)
/* 227 */               .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/* 228 */                 .addComponent(this.jLabelRestart)
/* 229 */                 .addComponent(this.jButton1, -2, 272, -2)
/* 230 */                 .addComponent(this.jLabel1)
/* 231 */                 .addComponent(this.jLabel2)
/* 232 */                 .addComponent(this.msgError)
/* 233 */                 .addComponent(this.jLabel5)
/* 234 */                 .addGroup(jPanel3Layout.createSequentialGroup()
/* 235 */                   .addComponent(this.passwordField, -2, 216, -2)
/* 236 */                   .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 237 */                   .addComponent(this.jButton3, -2, 53, -2))
/* 238 */                 .addComponent(this.jLabel7)
/* 239 */                 .addComponent(this.jLabel8)
/* 240 */                 .addComponent(this.usuarioField))
/* 241 */               .addGap(0, 48, 32767))
/* 242 */             .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
/* 243 */               .addGap(0, 0, 32767)
/* 244 */               .addComponent(this.jLabel3)))
/* 245 */           .addContainerGap())
/* 246 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
/* 247 */           .addGap(0, 0, 32767)
/* 248 */           .addComponent(this.jButton5, -2, 49, -2)));
/*     */     
/* 250 */     jPanel3Layout.setVerticalGroup(jPanel3Layout
/* 251 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 252 */         .addGroup(jPanel3Layout.createSequentialGroup()
/* 253 */           .addComponent(this.jButton5, -2, 47, -2)
/* 254 */           .addGap(50, 50, 50)
/* 255 */           .addComponent(this.jLabel5)
/* 256 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 257 */           .addComponent(this.jLabel7)
/* 258 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 259 */           .addComponent(this.jLabel8)
/* 260 */           .addGap(28, 28, 28)
/* 261 */           .addComponent(this.jLabel1)
/* 262 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 263 */           .addComponent(this.usuarioField, -2, -1, -2)
/* 264 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 265 */           .addComponent(this.jLabel2)
/* 266 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 267 */           .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 268 */             .addComponent(this.passwordField, -2, -1, -2)
/* 269 */             .addComponent(this.jButton3))
/* 270 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 271 */           .addComponent(this.msgError)
/* 272 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 56, 32767)
/* 273 */           .addComponent(this.jButton1, -2, 46, -2)
/* 274 */           .addGap(9, 9, 9)
/* 275 */           .addComponent(this.jLabelRestart)
/* 276 */           .addGap(30, 30, 30)
/* 277 */           .addComponent(this.jLabel3)
/* 278 */           .addContainerGap()));
/*     */ 
/*     */     
/* 281 */     this.jPanel1.setBackground(new Color(37, 84, 156));
/* 282 */     this.jPanel1.setPreferredSize(new Dimension(398, 350));
/*     */     
/* 284 */     this.jLabel4.setIcon(new ImageIcon(getClass().getResource("/imgs/logoazul5.png")));
/*     */     
/* 286 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 287 */     this.jPanel1.setLayout(jPanel1Layout);
/* 288 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/* 289 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 290 */         .addComponent(this.jLabel4, -1, 738, 32767));
/*     */     
/* 292 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/* 293 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 294 */         .addComponent(this.jLabel4, -2, 0, 32767));
/*     */ 
/*     */     
/* 297 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 298 */     this.jPanel2.setLayout(jPanel2Layout);
/* 299 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout
/* 300 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 301 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
/* 302 */           .addComponent(this.jPanel1, -2, 738, -2)
/* 303 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 304 */           .addComponent(this.jPanel3, -2, 382, -2)));
/*     */     
/* 306 */     jPanel2Layout.setVerticalGroup(jPanel2Layout
/* 307 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 308 */         .addComponent(this.jPanel1, -1, 508, 32767)
/* 309 */         .addComponent(this.jPanel3, -1, 508, 32767));
/*     */ 
/*     */     
/* 312 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 313 */     getContentPane().setLayout(layout);
/* 314 */     layout.setHorizontalGroup(layout
/* 315 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 316 */         .addComponent(this.jPanel2, -1, -1, 32767));
/*     */     
/* 318 */     layout.setVerticalGroup(layout
/* 319 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 320 */         .addGroup(layout.createSequentialGroup()
/* 321 */           .addComponent(this.jPanel2, -2, -1, -2)
/* 322 */           .addGap(0, 0, 32767)));
/*     */ 
/*     */     
/* 325 */     pack();
/*     */   }
/*     */ 
/*     */   
/*     */   private void usuarioFieldActionPerformed(ActionEvent evt) {
/* 330 */     login();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void jButton1ActionPerformed(ActionEvent evt) {
/* 336 */     login();
/*     */   }
/*     */   
/*     */   private void jButton5ActionPerformed(ActionEvent evt) {
/* 340 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void passwordFieldActionPerformed(ActionEvent evt) {
/* 345 */     login();
/*     */   }
/*     */ 
/*     */   
/*     */   private void jButton3ActionPerformed(ActionEvent evt) {
/* 350 */     if (this.verPassword) {
/* 351 */       this.passwordField.setEchoChar('●');
/* 352 */       this.verPassword = false;
/*     */     } else {
/* 354 */       this.passwordField.setEchoChar(false);
/* 355 */       this.verPassword = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void jLabelRestartMouseEntered(MouseEvent evt) {
/* 361 */     this.jLabelRestart.setFont(this.jLabelRestart.getFont().deriveFont(1));
/* 362 */     this.jLabelRestart.setText("<html><u>¿Olvidó su usuario y/o password?</u></html>");
/*     */   }
/*     */ 
/*     */   
/*     */   private void jLabelRestartMouseExited(MouseEvent evt) {
/* 367 */     this.jLabelRestart.setFont(this.jLabelRestart.getFont().deriveFont(0));
/* 368 */     this.jLabelRestart.setText("Olvidó su usuario y/o password?");
/*     */   }
/*     */ 
/*     */   
/*     */   private void jLabelRestartMouseClicked(MouseEvent evt) {
/* 373 */     String mensaje = "Debido al nivel de seguridad, no es posible recuperar el usuario y/o password,\nEn su lugar se deberá crear una nueva cuenta\n¡Se eliminaran todos los datos actuales y no se podrán recuperar!,\n¿Esta seguro de eliminar los datos actuales y crear una nueva cuenta?\n";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     int input = JOptionPane.showOptionDialog(null, mensaje, "Advertencia", 2, 3, null, null, null);
/*     */     
/* 380 */     if (input == 0) {
/*     */       
/* 382 */       eliminaBD();
/* 383 */       SingUp2 su = new SingUp2();
/* 384 */       su.setVisible(true);
/* 385 */       dispose();
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
/* 400 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
/* 401 */         if ("Nimbus".equals(info.getName())) {
/* 402 */           UIManager.setLookAndFeel(info.getClassName());
/*     */           break;
/*     */         } 
/*     */       } 
/* 406 */     } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|javax.swing.UnsupportedLookAndFeelException ex) {
/* 407 */       Logger.getLogger(Login2.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 416 */     EventQueue.invokeLater(new Runnable() {
/*     */           public void run() {
/* 418 */             (new Login2()).setVisible(true);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgestor\frames\Login2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */