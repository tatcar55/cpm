/*     */ package ec.gob.cnt.ciberpasswordgestor.frames;
/*     */ 
/*     */ import com.google.zxing.WriterException;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.Image;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.LayoutStyle;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ 
/*     */ public class QRFrame
/*     */   extends JFrame
/*     */ {
/*     */   ImageIcon icon;
/*     */   private JButton jButton1;
/*     */   private JButton jButton5;
/*     */   
/*     */   private void cargarImagen() {
/*  34 */     this.icon = new ImageIcon("qr.png");
/*  35 */     Image img = this.icon.getImage();
/*  36 */     int alto = img.getHeight(this.jPanel2);
/*  37 */     int ancho = img.getWidth(this.jPanel2);
/*     */     
/*  39 */     System.out.println(ancho + " " + alto);
/*     */     
/*  41 */     JLabel lbl = new JLabel();
/*  42 */     lbl.setIcon(this.icon);
/*  43 */     this.jPanel2.add(lbl);
/*  44 */     lbl.setBounds(5, 5, ancho, alto);
/*     */   }
/*     */   private JLabel jLabel1; private JLabel jLabel2; private JLabel jLabel3; private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   private JLabel jLabel6;
/*     */   private JLabel jLabel7;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   
/*     */   public QRFrame() throws WriterException, IOException {
/*  54 */     initComponents();
/*  55 */     setLocationRelativeTo(null);
/*  56 */     UIManager.put("OptionPane.background", Color.white);
/*  57 */     UIManager.put("Panel.background", Color.white);
/*  58 */     UIManager.put("Button.background", new Color(37, 84, 156));
/*  59 */     UIManager.put("Button.font", new Font("Segoe UI", 1, 14));
/*  60 */     UIManager.put("Button.foreground", new Color(255, 255, 255));
/*  61 */     this.jLabel3.requestFocus();
/*  62 */     cargarImagen();
/*     */     
/*  64 */     setIconImage((new ImageIcon(getClass().getResource("/imgs/icono1.png"))).getImage());
/*  65 */     setTitle("CPM QR Doble Autenticación");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void eliminaQR() {
/*  71 */     File archivoEliminar = new File("qr.png");
/*     */     
/*  73 */     if (archivoEliminar.exists())
/*  74 */     { archivoEliminar.delete();
/*  75 */       System.out.println("El archivo se eliminó correctamente"); }
/*  76 */     else { System.out.println("El archivo no existe"); }
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
/*     */   private void initComponents() {
/*  88 */     this.jPanel1 = new JPanel();
/*  89 */     this.jButton1 = new JButton();
/*  90 */     this.jLabel6 = new JLabel();
/*  91 */     this.jPanel2 = new JPanel();
/*  92 */     this.jLabel3 = new JLabel();
/*  93 */     this.jLabel2 = new JLabel();
/*  94 */     this.jLabel1 = new JLabel();
/*  95 */     this.jLabel4 = new JLabel();
/*  96 */     this.jLabel5 = new JLabel();
/*  97 */     this.jButton5 = new JButton();
/*  98 */     this.jLabel7 = new JLabel();
/*     */     
/* 100 */     setDefaultCloseOperation(3);
/* 101 */     setUndecorated(true);
/*     */     
/* 103 */     this.jPanel1.setBackground(new Color(255, 255, 255));
/*     */     
/* 105 */     this.jButton1.setBackground(new Color(37, 84, 156));
/* 106 */     this.jButton1.setFont(new Font("Segoe UI", 1, 14));
/* 107 */     this.jButton1.setForeground(new Color(255, 255, 255));
/* 108 */     this.jButton1.setText("Finalizar");
/* 109 */     this.jButton1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 111 */             QRFrame.this.jButton1ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 115 */     this.jLabel6.setText("4. Se generará un Código de 6 dígitos el cual deberas digitar en la pantalla de login");
/*     */     
/* 117 */     this.jPanel2.setPreferredSize(new Dimension(410, 410));
/*     */     
/* 119 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 120 */     this.jPanel2.setLayout(jPanel2Layout);
/* 121 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout
/* 122 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 123 */         .addGap(0, 410, 32767));
/*     */     
/* 125 */     jPanel2Layout.setVerticalGroup(jPanel2Layout
/* 126 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 127 */         .addGap(0, 410, 32767));
/*     */ 
/*     */     
/* 130 */     this.jLabel3.setText("3. Escanea el siguiente Código:");
/*     */     
/* 132 */     this.jLabel2.setText("2. Abre la aplicación, Presione el botón + y seleccione la opción Escanear un código QR");
/*     */     
/* 134 */     this.jLabel1.setText("1. Instala la aplicación Autenticador de Google en su dispositivo Android o IOS desde la Play Store o App Store");
/*     */     
/* 136 */     this.jLabel4.setText("Protegemos tu información con Doble Factor de Autenticación, sigue los siguientes pasos:");
/*     */     
/* 138 */     this.jLabel5.setFont(new Font("Segoe UI", 1, 12));
/* 139 */     this.jLabel5.setText("Seguridad");
/*     */     
/* 141 */     this.jButton5.setBackground(new Color(203, 0, 0));
/* 142 */     this.jButton5.setFont(new Font("Calibri", 1, 18));
/* 143 */     this.jButton5.setForeground(new Color(255, 255, 255));
/* 144 */     this.jButton5.setText("X");
/* 145 */     this.jButton5.setAlignmentY(0.0F);
/* 146 */     this.jButton5.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/* 148 */             QRFrame.this.jButton5ActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/* 152 */     this.jLabel7.setIcon(new ImageIcon(getClass().getResource("/imgs/logoblanco2.png")));
/*     */     
/* 154 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 155 */     this.jPanel1.setLayout(jPanel1Layout);
/* 156 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/* 157 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 158 */         .addGroup(jPanel1Layout.createSequentialGroup()
/* 159 */           .addGap(41, 41, 41)
/* 160 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 161 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 162 */               .addComponent(this.jLabel7)
/* 163 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/* 164 */               .addComponent(this.jButton5, -2, 49, -2))
/* 165 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 166 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 167 */                 .addGroup(jPanel1Layout.createSequentialGroup()
/* 168 */                   .addGap(88, 88, 88)
/* 169 */                   .addComponent(this.jPanel2, -2, -1, -2))
/* 170 */                 .addGroup(jPanel1Layout.createSequentialGroup()
/* 171 */                   .addGap(19, 19, 19)
/* 172 */                   .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 173 */                     .addComponent(this.jLabel3)
/* 174 */                     .addComponent(this.jLabel2)
/* 175 */                     .addComponent(this.jLabel1)
/* 176 */                     .addComponent(this.jLabel4)
/* 177 */                     .addGroup(jPanel1Layout.createSequentialGroup()
/* 178 */                       .addComponent(this.jLabel6)
/* 179 */                       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 104, 32767)
/* 180 */                       .addComponent(this.jButton1, -2, 104, -2))))
/* 181 */                 .addComponent(this.jLabel5))
/* 182 */               .addContainerGap()))));
/*     */     
/* 184 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/* 185 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 186 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
/* 187 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 188 */             .addComponent(this.jLabel7, -2, 46, -2)
/* 189 */             .addGroup(jPanel1Layout.createSequentialGroup()
/* 190 */               .addComponent(this.jButton5, -2, 47, -2)
/* 191 */               .addGap(6, 6, 6)))
/* 192 */           .addGap(26, 26, 26)
/* 193 */           .addComponent(this.jLabel5)
/* 194 */           .addGap(13, 13, 13)
/* 195 */           .addComponent(this.jLabel4)
/* 196 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 197 */           .addComponent(this.jLabel1)
/* 198 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 199 */           .addComponent(this.jLabel2)
/* 200 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 201 */           .addComponent(this.jLabel3)
/* 202 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 203 */           .addComponent(this.jPanel2, -2, -1, -2)
/* 204 */           .addGap(24, 24, 24)
/* 205 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 206 */             .addComponent(this.jLabel6)
/* 207 */             .addComponent(this.jButton1, -2, 46, -2))
/* 208 */           .addContainerGap(12, 32767)));
/*     */ 
/*     */     
/* 211 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 212 */     getContentPane().setLayout(layout);
/* 213 */     layout.setHorizontalGroup(layout
/* 214 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 215 */         .addComponent(this.jPanel1, -2, -1, -2));
/*     */     
/* 217 */     layout.setVerticalGroup(layout
/* 218 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 219 */         .addGroup(layout.createSequentialGroup()
/* 220 */           .addComponent(this.jPanel1, -2, -1, -2)
/* 221 */           .addGap(0, 0, 32767)));
/*     */ 
/*     */     
/* 224 */     pack();
/*     */   }
/*     */   
/*     */   private void jButton1ActionPerformed(ActionEvent evt) {
/* 228 */     String mensaje = "Advertencia:\nEl códogio QR se eliminará y no podrá ser recuperado, ¡escanee la imagen ahora!,\no guardela en un lugar seguro para escanearla más tarde\n";
/*     */ 
/*     */     
/* 231 */     JOptionPane.showMessageDialog(this, mensaje, "Advertencia", 1);
/*     */     
/* 233 */     Login2 l = new Login2();
/* 234 */     l.setVisible(true);
/* 235 */     dispose();
/* 236 */     eliminaQR();
/*     */   }
/*     */   
/*     */   private void jButton5ActionPerformed(ActionEvent evt) {
/* 240 */     System.exit(0);
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
/* 253 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
/* 254 */         if ("Nimbus".equals(info.getName())) {
/* 255 */           UIManager.setLookAndFeel(info.getClassName());
/*     */           break;
/*     */         } 
/*     */       } 
/* 259 */     } catch (ClassNotFoundException ex) {
/* 260 */       Logger.getLogger(QRFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 261 */     } catch (InstantiationException ex) {
/* 262 */       Logger.getLogger(QRFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 263 */     } catch (IllegalAccessException ex) {
/* 264 */       Logger.getLogger(QRFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 265 */     } catch (UnsupportedLookAndFeelException ex) {
/* 266 */       Logger.getLogger(QRFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     EventQueue.invokeLater(() -> {
/*     */           try {
/*     */             (new QRFrame()).setVisible(true);
/* 275 */           } catch (WriterException|IOException ex) {
/*     */             Logger.getLogger(QRFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgestor\frames\QRFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */