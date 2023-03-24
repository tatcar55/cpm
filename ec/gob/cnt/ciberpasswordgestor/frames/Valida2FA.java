/*     */ package ec.gob.cnt.ciberpasswordgestor.frames;
/*     */ import ec.gob.cnt.ciberpasswordgestor.utils.Utils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.LayoutStyle;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class Valida2FA extends JFrame {
/*     */   private JTextField codigoField;
/*     */   private JButton jButton1;
/*     */   private JButton jButton5;
/*     */   
/*     */   public Valida2FA() {
/*  22 */     initComponents();
/*  23 */     setLocationRelativeTo(null);
/*  24 */     getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
/*  25 */     this.msgError.setVisible(false);
/*     */     
/*  27 */     UIManager.put("OptionPane.background", Color.white);
/*  28 */     UIManager.put("Panel.background", Color.white);
/*  29 */     UIManager.put("Button.background", new Color(32, 154, 227));
/*  30 */     UIManager.put("Button.font", new Font("Segoe UI", 1, 14));
/*  31 */     UIManager.put("Button.foreground", new Color(255, 255, 255));
/*  32 */     this.codigoField.requestFocus();
/*     */     
/*  34 */     setIconImage((new ImageIcon(getClass().getResource("/imgs/icono1.png"))).getImage());
/*  35 */     setTitle("CPM Valida Código");
/*     */   }
/*     */   private JLabel jLabel1; private JPanel jPanel1; private JLabel msgError;
/*     */   
/*     */   private boolean validarCodigo() {
/*  40 */     String code = Utils.getTOTPCode(Utils.otpKey(Utils.KEY));
/*  41 */     System.out.println(code);
/*  42 */     System.out.println(this.codigoField.getText());
/*  43 */     return code.equals(this.codigoField.getText());
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
/*  55 */     this.jPanel1 = new JPanel();
/*  56 */     this.jButton1 = new JButton();
/*  57 */     this.jLabel1 = new JLabel();
/*  58 */     this.codigoField = new JTextField();
/*  59 */     this.msgError = new JLabel();
/*  60 */     this.jButton5 = new JButton();
/*     */     
/*  62 */     setDefaultCloseOperation(3);
/*  63 */     setUndecorated(true);
/*     */     
/*  65 */     this.jPanel1.setBackground(new Color(255, 255, 255));
/*     */     
/*  67 */     this.jButton1.setBackground(new Color(37, 84, 156));
/*  68 */     this.jButton1.setFont(new Font("Segoe UI", 1, 14));
/*  69 */     this.jButton1.setForeground(new Color(255, 255, 255));
/*  70 */     this.jButton1.setText("Continuar");
/*  71 */     this.jButton1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  73 */             Valida2FA.this.jButton1ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/*  77 */     this.jLabel1.setText("Ingresa el código de autenticación de 6 dígitos");
/*     */     
/*  79 */     this.codigoField.setBackground(new Color(204, 204, 204));
/*  80 */     this.codigoField.setFont(new Font("Segoe UI", 0, 16));
/*  81 */     this.codigoField.setBorder(new SoftBevelBorder(0));
/*  82 */     this.codigoField.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  84 */             Valida2FA.this.codigoFieldActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/*  88 */     this.msgError.setForeground(new Color(255, 51, 51));
/*  89 */     this.msgError.setText("Código incorrecto");
/*     */     
/*  91 */     this.jButton5.setBackground(new Color(203, 0, 0));
/*  92 */     this.jButton5.setFont(new Font("Calibri", 1, 18));
/*  93 */     this.jButton5.setForeground(new Color(255, 255, 255));
/*  94 */     this.jButton5.setText("X");
/*  95 */     this.jButton5.setAlignmentY(0.0F);
/*  96 */     this.jButton5.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  98 */             Valida2FA.this.jButton5ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 102 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 103 */     this.jPanel1.setLayout(jPanel1Layout);
/* 104 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/* 105 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 106 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
/* 107 */           .addGap(65, 65, 65)
/* 108 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 109 */             .addComponent(this.jButton5, GroupLayout.Alignment.TRAILING, -2, 49, -2)
/* 110 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 111 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
/* 112 */                 .addComponent(this.jButton1, GroupLayout.Alignment.LEADING, -1, -1, 32767)
/* 113 */                 .addComponent(this.jLabel1, GroupLayout.Alignment.LEADING, -1, -1, 32767))
/* 114 */               .addContainerGap(64, 32767))))
/* 115 */         .addGroup(jPanel1Layout.createSequentialGroup()
/* 116 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 117 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 118 */               .addGap(138, 138, 138)
/* 119 */               .addComponent(this.msgError))
/* 120 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 121 */               .addGap(126, 126, 126)
/* 122 */               .addComponent(this.codigoField, -2, 118, -2)))
/* 123 */           .addContainerGap(-1, 32767)));
/*     */     
/* 125 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/* 126 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 127 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
/* 128 */           .addComponent(this.jButton5, -2, 47, -2)
/* 129 */           .addGap(38, 38, 38)
/* 130 */           .addComponent(this.jLabel1)
/* 131 */           .addGap(18, 18, 18)
/* 132 */           .addComponent(this.codigoField, -2, -1, -2)
/* 133 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 134 */           .addComponent(this.msgError)
/* 135 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 32, 32767)
/* 136 */           .addComponent(this.jButton1, -2, 40, -2)
/* 137 */           .addGap(23, 23, 23)));
/*     */ 
/*     */     
/* 140 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 141 */     getContentPane().setLayout(layout);
/* 142 */     layout.setHorizontalGroup(layout
/* 143 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 144 */         .addComponent(this.jPanel1, -1, -1, 32767));
/*     */     
/* 146 */     layout.setVerticalGroup(layout
/* 147 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 148 */         .addComponent(this.jPanel1, -2, -1, -2));
/*     */ 
/*     */     
/* 151 */     pack();
/*     */   }
/*     */ 
/*     */   
/*     */   private void jButton1ActionPerformed(ActionEvent evt) {
/* 156 */     if (validarCodigo()) {
/* 157 */       VentanaPrincipal vp = new VentanaPrincipal();
/* 158 */       vp.setVisible(true);
/* 159 */       dispose();
/*     */     } else {
/* 161 */       this.msgError.setVisible(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void jButton5ActionPerformed(ActionEvent evt) {
/* 168 */     System.exit(0);
/*     */   }
/*     */   
/*     */   private void codigoFieldActionPerformed(ActionEvent evt) {
/* 172 */     if (validarCodigo()) {
/* 173 */       VentanaPrincipal vp = new VentanaPrincipal();
/* 174 */       vp.setVisible(true);
/* 175 */       dispose();
/*     */     } else {
/* 177 */       this.msgError.setVisible(true);
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
/*     */   public static void main(String[] args) {
/*     */     try {
/* 191 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
/* 192 */         if ("Nimbus".equals(info.getName())) {
/* 193 */           UIManager.setLookAndFeel(info.getClassName());
/*     */           break;
/*     */         } 
/*     */       } 
/* 197 */     } catch (ClassNotFoundException ex) {
/* 198 */       Logger.getLogger(Valida2FA.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 199 */     } catch (InstantiationException ex) {
/* 200 */       Logger.getLogger(Valida2FA.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 201 */     } catch (IllegalAccessException ex) {
/* 202 */       Logger.getLogger(Valida2FA.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 203 */     } catch (UnsupportedLookAndFeelException ex) {
/* 204 */       Logger.getLogger(Valida2FA.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     EventQueue.invokeLater(new Runnable() {
/*     */           public void run() {
/* 214 */             (new Valida2FA()).setVisible(true);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgestor\frames\Valida2FA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */