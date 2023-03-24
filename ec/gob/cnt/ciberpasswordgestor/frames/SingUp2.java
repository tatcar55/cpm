/*     */ package ec.gob.cnt.ciberpasswordgestor.frames;
/*     */ import ec.gob.cnt.ciberpasswordgestor.model.Cuenta;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.DBConect;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.Encrypt;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.Utils;
/*     */ import java.awt.Color;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.LayoutStyle;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.SoftBevelBorder;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class SingUp2 extends JFrame {
/*     */   boolean verPassword = false;
/*     */   private JButton jButton1;
/*     */   private JButton jButton2;
/*     */   private JButton jButton3;
/*     */   
/*     */   public SingUp2() {
/*  35 */     initComponents();
/*  36 */     this.usrField.requestFocusInWindow();
/*  37 */     setLocationRelativeTo(null);
/*  38 */     getRootPane().setBorder(BorderFactory.createEtchedBorder(1));
/*     */     
/*  40 */     UIManager.put("OptionPane.background", Color.white);
/*  41 */     UIManager.put("Panel.background", Color.white);
/*  42 */     UIManager.put("Button.background", new Color(37, 84, 156));
/*  43 */     UIManager.put("Button.font", new Font("Segoe UI", 1, 14));
/*  44 */     UIManager.put("Button.foreground", new Color(255, 255, 255));
/*  45 */     this.jButton1.setForeground(new Color(0, 0, 0));
/*     */ 
/*     */ 
/*     */     
/*  49 */     setIconImage((new ImageIcon(getClass().getResource("/imgs/icono1.png"))).getImage());
/*  50 */     setTitle("CPM Login");
/*     */   }
/*     */   private JButton jButton4; private JButton jButton5; private JLabel jLabel1; private JLabel jLabel2; private JLabel jLabel3; private JLabel jLabel4; private JLabel jLabel5; private JLabel jLabel6; private JLabel jLabel7;
/*     */   private JPanel jPanel1;
/*     */   
/*     */   private void generateQR(String usuario) {
/*  56 */     System.out.println("usuario");
/*  57 */     System.out.println(usuario);
/*  58 */     DBConect dBConect = new DBConect();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  64 */       String usr = Encrypt.decryptText(Utils.KEY, dBConect.selectCuenta(Encrypt.encryptText(Utils.KEY, usuario)).getUser());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  69 */       Cuenta cuenta = new Cuenta(0, Encrypt.encryptText(Utils.KEY, "otp*"), Encrypt.encryptText(Utils.KEY, "otp*"), Encrypt.encryptText(Utils.KEY, String.valueOf(Utils.generateSecretKey())));
/*     */       
/*  71 */       dBConect.insertCuenta(cuenta);
/*     */ 
/*     */       
/*  74 */       String barCodeUrl = Utils.getGoogleAuthenticatorBarCode(Utils.otpKey(Utils.KEY), usr, "CNT");
/*  75 */       System.out.println(barCodeUrl);
/*     */       
/*  77 */       Utils.createQRCode(barCodeUrl, "qr.png", 400, 400);
/*     */       
/*  79 */       System.out.println("creo qr");
/*     */     
/*     */     }
/*  82 */     catch (Exception ex) {
/*  83 */       Logger.getLogger(SingUp2.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */   }
/*     */   private JPanel jPanel2; private JPanel jPanel3; private JPasswordField jPasswordField1;
/*     */   private JPasswordField pwdField;
/*     */   private JTextField usrField;
/*     */   
/*     */   private void signUp() {
/*     */     try {
/*  92 */       if (Utils.validaPassword(String.valueOf(this.pwdField.getPassword())) && Utils.validaUserName(this.usrField.getText())) {
/*  93 */         DBConect dBConect = new DBConect();
/*     */         
/*  95 */         Utils.USER = this.usrField.getText();
/*  96 */         Utils.PASS = String.valueOf(this.pwdField.getPassword());
/*  97 */         dBConect.createDB(Utils.USER, Utils.PASS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 103 */         String llaveTexto = Utils.generateKey();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 108 */         String llavePass = String.valueOf(this.pwdField.getPassword()) + String.valueOf(this.pwdField.getPassword());
/* 109 */         String truncated = StringUtils.substring(llavePass, 0, 16);
/* 110 */         String encodedText = Base64.getEncoder().encodeToString(truncated.getBytes());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 122 */         Cuenta cuenta = new Cuenta(0, Encrypt.encryptText(Encrypt.keyPass(String.valueOf(this.pwdField.getPassword())), "root"), Encrypt.encryptText(Encrypt.keyPass(String.valueOf(this.pwdField.getPassword())), "root"), Encrypt.encryptText(Encrypt.keyPass(String.valueOf(this.pwdField.getPassword())), Utils.generateKey()));
/*     */         
/* 124 */         dBConect.insertCuenta(cuenta);
/*     */ 
/*     */         
/* 127 */         Utils.KEY = Utils.sKey(Encrypt.keyPass(String.valueOf(this.pwdField.getPassword())));
/*     */ 
/*     */ 
/*     */         
/* 131 */         cuenta = new Cuenta(0, Encrypt.encryptText(Utils.KEY, "principal"), Encrypt.encryptText(Utils.KEY, this.usrField.getText()), Encrypt.encryptText(Utils.KEY, String.valueOf(this.pwdField.getPassword())));
/*     */         
/* 133 */         if (dBConect.insertCuenta(cuenta)) {
/* 134 */           dBConect.updateUtil(1);
/* 135 */           String mensaje = "Cuenta creada con éxito\nRecuerde su usuario y password\nlos cuales no se podrán recuperar si los olvida.\n";
/*     */ 
/*     */ 
/*     */           
/* 139 */           JOptionPane.showMessageDialog(this, mensaje, "Nueva Cuenta", 1);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 144 */         generateQR(this.usrField.getText());
/*     */ 
/*     */         
/* 147 */         QRFrame qRFrame = new QRFrame();
/* 148 */         qRFrame.setVisible(true);
/* 149 */         dispose();
/*     */       }
/*     */       else {
/*     */         
/* 153 */         String mensaje = "La clave no cumple uno los siguientes parámetros:\n- 1 letra mayúscula\n- 1 letra minúscula\n- 1 dígito\n- 1 caracter especial\n- nombre de usuario no debe estar vacío\n- y únicamente debe contener letras y/o números\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 160 */         JOptionPane.showMessageDialog(this, mensaje, "Error", 0);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 167 */     catch (Exception ex) {
/* 168 */       Logger.getLogger(SingUp2.class.getName()).log(Level.SEVERE, (String)null, ex);
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
/*     */   private void initComponents() {
/* 183 */     this.jPasswordField1 = new JPasswordField();
/* 184 */     this.jButton4 = new JButton();
/* 185 */     this.jPanel3 = new JPanel();
/* 186 */     this.jPanel1 = new JPanel();
/* 187 */     this.jLabel4 = new JLabel();
/* 188 */     this.jPanel2 = new JPanel();
/* 189 */     this.jButton2 = new JButton();
/* 190 */     this.jButton1 = new JButton();
/* 191 */     this.jButton3 = new JButton();
/* 192 */     this.pwdField = new JPasswordField();
/* 193 */     this.jLabel2 = new JLabel();
/* 194 */     this.jLabel3 = new JLabel();
/* 195 */     this.usrField = new JTextField();
/* 196 */     this.jButton5 = new JButton();
/* 197 */     this.jLabel1 = new JLabel();
/* 198 */     this.jLabel5 = new JLabel();
/* 199 */     this.jLabel6 = new JLabel();
/* 200 */     this.jLabel7 = new JLabel();
/*     */     
/* 202 */     this.jPasswordField1.setText("jPasswordField1");
/*     */     
/* 204 */     this.jButton4.setText("jButton4");
/*     */     
/* 206 */     setDefaultCloseOperation(3);
/* 207 */     setBackground(new Color(37, 84, 156));
/* 208 */     setUndecorated(true);
/*     */     
/* 210 */     this.jPanel3.setBackground(new Color(255, 255, 255));
/*     */     
/* 212 */     this.jPanel1.setBackground(new Color(32, 154, 227));
/*     */     
/* 214 */     this.jLabel4.setBackground(new Color(255, 255, 255));
/* 215 */     this.jLabel4.setIcon(new ImageIcon(getClass().getResource("/imgs/logoazul5.png")));
/*     */     
/* 217 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 218 */     this.jPanel1.setLayout(jPanel1Layout);
/* 219 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/* 220 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 221 */         .addGroup(jPanel1Layout.createSequentialGroup()
/* 222 */           .addComponent(this.jLabel4)
/* 223 */           .addGap(0, 0, 32767)));
/*     */     
/* 225 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/* 226 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 227 */         .addComponent(this.jLabel4, -2, 0, 32767));
/*     */ 
/*     */     
/* 230 */     this.jPanel2.setBackground(new Color(255, 255, 255));
/*     */     
/* 232 */     this.jButton2.setBackground(new Color(37, 84, 156));
/* 233 */     this.jButton2.setFont(new Font("Segoe UI", 1, 16));
/* 234 */     this.jButton2.setForeground(new Color(255, 255, 255));
/* 235 */     this.jButton2.setText("Comenzar");
/* 236 */     this.jButton2.setToolTipText("Registrar");
/* 237 */     this.jButton2.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 239 */             SingUp2.this.jButton2ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 243 */     this.jButton1.setBackground(new Color(254, 254, 254));
/* 244 */     this.jButton1.setFont(new Font("Segoe UI", 1, 12));
/* 245 */     this.jButton1.setText("Generar Clave");
/* 246 */     this.jButton1.setToolTipText("Crear clave aleatoria");
/* 247 */     this.jButton1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 249 */             SingUp2.this.jButton1ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 253 */     this.jButton3.setBackground(new Color(254, 254, 254));
/* 254 */     this.jButton3.setIcon(new ImageIcon(getClass().getResource("/imgs/verPassword.png")));
/* 255 */     this.jButton3.setToolTipText("Ver / Ocultar");
/* 256 */     this.jButton3.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 258 */             SingUp2.this.jButton3ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 262 */     this.pwdField.setBackground(new Color(204, 204, 204));
/* 263 */     this.pwdField.setFont(new Font("Segoe UI", 0, 14));
/* 264 */     this.pwdField.setToolTipText("Clave");
/* 265 */     this.pwdField.setBorder(new SoftBevelBorder(0));
/* 266 */     this.pwdField.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 268 */             SingUp2.this.pwdFieldActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 272 */     this.jLabel2.setFont(new Font("Segoe UI", 1, 14));
/* 273 */     this.jLabel2.setText("Password");
/*     */     
/* 275 */     this.jLabel3.setFont(new Font("Segoe UI", 1, 14));
/* 276 */     this.jLabel3.setText("Usuario");
/*     */     
/* 278 */     this.usrField.setBackground(new Color(204, 204, 204));
/* 279 */     this.usrField.setFont(new Font("Segoe UI", 0, 14));
/* 280 */     this.usrField.setToolTipText("Usuario");
/* 281 */     this.usrField.setBorder(new SoftBevelBorder(0));
/* 282 */     this.usrField.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 284 */             SingUp2.this.usrFieldActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 288 */     this.jButton5.setBackground(new Color(203, 0, 0));
/* 289 */     this.jButton5.setFont(new Font("Calibri", 1, 18));
/* 290 */     this.jButton5.setForeground(new Color(255, 255, 255));
/* 291 */     this.jButton5.setText("X");
/* 292 */     this.jButton5.setAlignmentY(0.0F);
/* 293 */     this.jButton5.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 295 */             SingUp2.this.jButton5ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 299 */     this.jLabel1.setFont(new Font("Segoe UI", 1, 18));
/* 300 */     this.jLabel1.setText("Hola, ¡Registrate ahora!");
/*     */     
/* 302 */     this.jLabel5.setFont(new Font("Cambria Math", 0, 10));
/* 303 */     this.jLabel5.setText("CNT - JCSO v1.0.0");
/*     */     
/* 305 */     this.jLabel6.setText("Crea un nuevo usuario para gestionar todas tus ");
/*     */     
/* 307 */     this.jLabel7.setText("contraseñas.");
/*     */     
/* 309 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 310 */     this.jPanel2.setLayout(jPanel2Layout);
/* 311 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout
/* 312 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 313 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
/* 314 */           .addGap(0, 0, 32767)
/* 315 */           .addComponent(this.jButton5, -2, 49, -2))
/* 316 */         .addGroup(jPanel2Layout.createSequentialGroup()
/* 317 */           .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 318 */             .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
/* 319 */               .addContainerGap(-1, 32767)
/* 320 */               .addComponent(this.jLabel5))
/* 321 */             .addGroup(jPanel2Layout.createSequentialGroup()
/* 322 */               .addGap(52, 52, 52)
/* 323 */               .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 324 */                 .addComponent(this.jButton2, -2, 276, -2)
/* 325 */                 .addComponent(this.jLabel1)
/* 326 */                 .addComponent(this.jLabel3)
/* 327 */                 .addGroup(jPanel2Layout.createSequentialGroup()
/* 328 */                   .addComponent(this.pwdField, -2, 217, -2)
/* 329 */                   .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 330 */                   .addComponent(this.jButton3, -2, 53, -2))
/* 331 */                 .addComponent(this.jButton1)
/* 332 */                 .addComponent(this.usrField, -2, 276, -2)
/* 333 */                 .addComponent(this.jLabel2, -2, 75, -2)
/* 334 */                 .addComponent(this.jLabel6)
/* 335 */                 .addComponent(this.jLabel7))
/* 336 */               .addGap(0, 48, 32767)))
/* 337 */           .addContainerGap()));
/*     */     
/* 339 */     jPanel2Layout.setVerticalGroup(jPanel2Layout
/* 340 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 341 */         .addGroup(jPanel2Layout.createSequentialGroup()
/* 342 */           .addComponent(this.jButton5, -2, 47, -2)
/* 343 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 52, 32767)
/* 344 */           .addComponent(this.jLabel1)
/* 345 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 346 */           .addComponent(this.jLabel6)
/* 347 */           .addGap(4, 4, 4)
/* 348 */           .addComponent(this.jLabel7)
/* 349 */           .addGap(28, 28, 28)
/* 350 */           .addComponent(this.jLabel3)
/* 351 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 352 */           .addComponent(this.usrField, -2, -1, -2)
/* 353 */           .addGap(12, 12, 12)
/* 354 */           .addComponent(this.jLabel2)
/* 355 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 356 */           .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 357 */             .addComponent(this.pwdField, -2, -1, -2)
/* 358 */             .addComponent(this.jButton3))
/* 359 */           .addGap(13, 13, 13)
/* 360 */           .addComponent(this.jButton1)
/* 361 */           .addGap(52, 52, 52)
/* 362 */           .addComponent(this.jButton2, -2, 46, -2)
/* 363 */           .addGap(49, 49, 49)
/* 364 */           .addComponent(this.jLabel5)
/* 365 */           .addContainerGap()));
/*     */ 
/*     */     
/* 368 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/* 369 */     this.jPanel3.setLayout(jPanel3Layout);
/* 370 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout
/* 371 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 372 */         .addGroup(jPanel3Layout.createSequentialGroup()
/* 373 */           .addComponent(this.jPanel1, -2, -1, -2)
/* 374 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 375 */           .addComponent(this.jPanel2, -1, -1, 32767)));
/*     */     
/* 377 */     jPanel3Layout.setVerticalGroup(jPanel3Layout
/* 378 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 379 */         .addComponent(this.jPanel1, -1, -1, 32767)
/* 380 */         .addComponent(this.jPanel2, -1, -1, 32767));
/*     */ 
/*     */     
/* 383 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 384 */     getContentPane().setLayout(layout);
/* 385 */     layout.setHorizontalGroup(layout
/* 386 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 387 */         .addGroup(layout.createSequentialGroup()
/* 388 */           .addComponent(this.jPanel3, -2, -1, -2)
/* 389 */           .addContainerGap(8, 32767)));
/*     */     
/* 391 */     layout.setVerticalGroup(layout
/* 392 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 393 */         .addComponent(this.jPanel3, -1, -1, 32767));
/*     */ 
/*     */     
/* 396 */     setBounds(0, 0, 1130, 511);
/*     */   }
/*     */ 
/*     */   
/*     */   private void jButton1ActionPerformed(ActionEvent evt) {
/* 401 */     this.pwdField.setText(Utils.generatePassword());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void jButton3ActionPerformed(ActionEvent evt) {
/* 407 */     if (this.verPassword) {
/* 408 */       this.pwdField.setEchoChar('●');
/* 409 */       this.verPassword = false;
/*     */     } else {
/* 411 */       this.pwdField.setEchoChar(false);
/* 412 */       this.verPassword = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void jButton2ActionPerformed(ActionEvent evt) {
/* 419 */     signUp();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void jButton5ActionPerformed(ActionEvent evt) {
/* 426 */     System.exit(0);
/*     */   }
/*     */   
/*     */   private void usrFieldActionPerformed(ActionEvent evt) {
/* 430 */     signUp();
/*     */   }
/*     */   
/*     */   private void pwdFieldActionPerformed(ActionEvent evt) {
/* 434 */     signUp();
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
/*     */   public static void main(String[] args) {
/*     */     try {
/* 447 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
/* 448 */         if ("Nimbus".equals(info.getName())) {
/* 449 */           UIManager.setLookAndFeel(info.getClassName());
/*     */           break;
/*     */         } 
/*     */       } 
/* 453 */     } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|javax.swing.UnsupportedLookAndFeelException ex) {
/* 454 */       Logger.getLogger(SingUp2.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 463 */     EventQueue.invokeLater(new Runnable() {
/*     */           public void run() {
/* 465 */             (new SingUp2()).setVisible(true);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgestor\frames\SingUp2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */