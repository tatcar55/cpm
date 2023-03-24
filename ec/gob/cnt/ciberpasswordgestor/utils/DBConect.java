/*     */ package ec.gob.cnt.ciberpasswordgestor.utils;
/*     */ 
/*     */ import ec.gob.cnt.ciberpasswordgestor.model.Cuenta;
/*     */ import java.io.File;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DBConect
/*     */ {
/*     */   public boolean existsDB() {
/*  20 */     String rutaBaseDatos = "data.db";
/*  21 */     File archivoBD = new File(rutaBaseDatos);
/*  22 */     if (archivoBD.exists()) {
/*  23 */       System.out.println("La base de datos existe");
/*  24 */       return true;
/*     */     } 
/*     */     
/*  27 */     System.out.println("La base de datos NO existe");
/*  28 */     return false;
/*     */   }
/*     */   
/*     */   public void createDB(String user, String pass) {
/*  32 */     Connection connection = null;
/*     */     
/*  34 */     PreparedStatement stm = null;
/*     */ 
/*     */     
/*     */     try {
/*  38 */       String url = "jdbc:sqlite:file:data.db?cipher=chacha20&key=" + pass + "Y";
/*     */ 
/*     */ 
/*     */       
/*  42 */       connection = DriverManager.getConnection(url, user, pass + "Z");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  50 */       String sql = "CREATE TABLE DATA (ID INTEGER PRIMARY KEY AUTOINCREMENT, CUENTA           TEXT    NOT NULL,  USER           TEXT    NOT NULL,  PASSWORD       CHAR(50)     NOT NULL )";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  56 */       stm = connection.prepareStatement(sql);
/*  57 */       stm.setQueryTimeout(30);
/*  58 */       stm.executeUpdate();
/*     */       
/*  60 */       sql = "CREATE TABLE UTIL (ID INTEGER PRIMARY KEY AUTOINCREMENT, VALOR           INTEGER    NOT NULL )";
/*     */ 
/*     */ 
/*     */       
/*  64 */       stm = connection.prepareStatement(sql);
/*  65 */       stm.executeUpdate();
/*  66 */       System.out.println("Tablas creada con exito");
/*     */ 
/*     */       
/*  69 */       sql = "INSERT INTO UTIL(valor) VALUES(?)";
/*  70 */       stm = connection.prepareStatement(sql);
/*  71 */       stm.setInt(1, 0);
/*  72 */       stm.executeUpdate();
/*     */     }
/*  74 */     catch (SQLException ex) {
/*  75 */       Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, (String)null, ex);
/*  76 */       System.out.println("Error al crear tabla");
/*     */     }
/*     */     finally {
/*     */       
/*  80 */       cerrarConexion(connection, stm);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection openConnection() {
/*  90 */     Connection connection = null;
/*     */ 
/*     */ 
/*     */     
/*  94 */     String url = "jdbc:sqlite:file:data.db?cipher=chacha20&key=" + Utils.PASS + "Y";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 100 */       connection = DriverManager.getConnection(url, Utils.USER, Utils.PASS + "Z");
/* 101 */     } catch (SQLException ex) {
/*     */       
/* 103 */       System.out.println("Usuario no es el de la BDD no OpenConnection");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 108 */     return connection;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFirstTime() {
/* 113 */     Connection conn = openConnection();
/* 114 */     PreparedStatement statement = null;
/*     */     
/*     */     try {
/* 117 */       String sql = "SELECT * FROM UTIL";
/*     */       
/* 119 */       statement = conn.prepareStatement(sql);
/*     */       
/* 121 */       ResultSet rs = statement.executeQuery();
/* 122 */       int valor = 0;
/* 123 */       while (rs.next())
/*     */       {
/*     */         
/* 126 */         valor = rs.getInt("valor");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       if (valor == 0) {
/* 133 */         rs.close();
/* 134 */         conn.close();
/* 135 */         return true;
/*     */       }
/*     */     
/*     */     }
/* 139 */     catch (SQLException ex) {
/* 140 */       Logger.getLogger(DBConect.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 141 */       return false;
/*     */     } finally {
/*     */       
/* 144 */       cerrarConexion(conn, statement);
/*     */     } 
/*     */     
/* 147 */     return false;
/*     */   }
/*     */   
/*     */   public Cuenta selectCuenta(String user) {
/* 151 */     Cuenta cuenta = new Cuenta("", "");
/* 152 */     Connection conn = openConnection();
/* 153 */     PreparedStatement statement = null;
/*     */     
/*     */     try {
/* 156 */       String sql = "SELECT * FROM DATA where user = (?)";
/* 157 */       statement = conn.prepareStatement(sql);
/* 158 */       statement.setString(1, user);
/* 159 */       ResultSet rs = statement.executeQuery();
/* 160 */       while (rs.next()) {
/* 161 */         cuenta.setCuenta(rs.getString("cuenta"));
/* 162 */         cuenta.setUser(rs.getString("user"));
/* 163 */         cuenta.setPassword(rs.getString("password"));
/*     */       } 
/* 165 */       return cuenta;
/*     */     }
/* 167 */     catch (SQLException ex) {
/* 168 */       Logger.getLogger(DBConect.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } finally {
/* 170 */       cerrarConexion(conn, statement);
/*     */     } 
/* 172 */     return cuenta;
/*     */   }
/*     */   
/*     */   public boolean insertCuenta(Cuenta cuenta) {
/* 176 */     Connection conn = openConnection();
/* 177 */     PreparedStatement statement = null;
/*     */     
/*     */     try {
/* 180 */       String sql = "INSERT INTO DATA(cuenta, user,password) VALUES(?,?,?)";
/* 181 */       statement = conn.prepareStatement(sql);
/* 182 */       statement.setString(1, cuenta.getCuenta());
/* 183 */       statement.setString(2, cuenta.getUser());
/* 184 */       statement.setString(3, cuenta.getPassword());
/* 185 */       statement.executeUpdate();
/* 186 */       return true;
/* 187 */     } catch (SQLException e) {
/* 188 */       System.out.println(e.getMessage());
/*     */     } finally {
/* 190 */       cerrarConexion(conn, statement);
/*     */     } 
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUtil(int valor) {
/* 197 */     Connection conn = openConnection();
/* 198 */     PreparedStatement statement = null;
/*     */     
/*     */     try {
/* 201 */       String sql = "UPDATE UTIL set valor=?";
/* 202 */       statement = conn.prepareStatement(sql);
/* 203 */       statement.setInt(1, valor);
/* 204 */       statement.executeUpdate();
/* 205 */     } catch (SQLException e) {
/* 206 */       System.out.println(e.getMessage());
/*     */     } finally {
/* 208 */       cerrarConexion(conn, statement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean login(String user, String password) {
/* 216 */     Connection conn = openConnection();
/* 217 */     PreparedStatement statement = null;
/*     */     
/*     */     try {
/* 220 */       String sql = "SELECT * FROM DATA where cuenta = ? and id = 2 and user = ? and password = ?";
/* 221 */       statement = conn.prepareStatement(sql);
/* 222 */       statement.setString(1, Encrypt.encryptText(Utils.KEY, "principal"));
/* 223 */       statement.setString(2, user);
/* 224 */       statement.setString(3, password);
/* 225 */       ResultSet rs = statement.executeQuery();
/* 226 */       if (rs.next()) {
/* 227 */         return true;
/*     */       
/*     */       }
/*     */     }
/* 231 */     catch (SQLException ex) {
/* 232 */       Logger.getLogger(DBConect.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 233 */     } catch (Exception ex) {
/* 234 */       Logger.getLogger(DBConect.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } finally {
/* 236 */       cerrarConexion(conn, statement);
/*     */     } 
/* 238 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Cuenta> obtenerCuentas() {
/* 245 */     ArrayList<Cuenta> lista = new ArrayList<>();
/* 246 */     Connection conn = openConnection();
/* 247 */     PreparedStatement statement = null;
/*     */     
/*     */     try {
/* 250 */       String sql = "SELECT * FROM DATA where id > 3";
/*     */       
/* 252 */       statement = conn.prepareStatement(sql);
/*     */       
/* 254 */       ResultSet rs = statement.executeQuery();
/* 255 */       System.out.println("entro");
/* 256 */       while (rs.next()) {
/* 257 */         System.out.println("entro2");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 263 */           lista.add(new Cuenta(rs
/* 264 */                 .getInt("id"), 
/* 265 */                 Encrypt.decryptText(Utils.KEY, rs.getString("cuenta")), 
/* 266 */                 Encrypt.decryptText(Utils.KEY, rs.getString("user")), 
/* 267 */                 Encrypt.decryptText(Utils.KEY, rs.getString("password"))));
/* 268 */         } catch (Exception ex) {
/* 269 */           Logger.getLogger(DBConect.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */         }
/*     */       
/*     */       } 
/* 273 */     } catch (SQLException ex) {
/* 274 */       Logger.getLogger(DBConect.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } finally {
/* 276 */       cerrarConexion(conn, statement);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     return lista;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateCuenta(Cuenta cuenta) {
/* 291 */     Connection conn = openConnection();
/* 292 */     PreparedStatement statement = null;
/*     */     
/*     */     try {
/* 295 */       String sql = "UPDATE DATA set cuenta=?, user=?, password=? where id= ?";
/* 296 */       statement = conn.prepareStatement(sql);
/* 297 */       statement.setString(1, cuenta.getCuenta());
/* 298 */       statement.setString(2, cuenta.getUser());
/* 299 */       statement.setString(3, cuenta.getPassword());
/* 300 */       statement.setInt(4, cuenta.getId());
/* 301 */       statement.executeUpdate();
/* 302 */       return true;
/* 303 */     } catch (SQLException e) {
/* 304 */       System.out.println(e.getMessage());
/*     */     } finally {
/* 306 */       cerrarConexion(conn, statement);
/*     */     } 
/* 308 */     return false;
/*     */   }
/*     */   
/*     */   public void deleteCuenta(Cuenta cuenta) {
/* 312 */     Connection conn = openConnection();
/* 313 */     PreparedStatement statement = null;
/*     */     
/*     */     try {
/* 316 */       String sql = "DELETE FROM DATA where id= ?";
/* 317 */       statement = conn.prepareStatement(sql);
/* 318 */       statement.setInt(1, cuenta.getId());
/* 319 */       statement.executeUpdate();
/* 320 */     } catch (SQLException e) {
/* 321 */       System.out.println(e.getMessage());
/*     */     } finally {
/* 323 */       cerrarConexion(conn, statement);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cerrarConexion(Connection conn, PreparedStatement statement) {
/*     */     try {
/* 330 */       if (conn != null) {
/* 331 */         conn.close();
/*     */       }
/* 333 */       if (statement != null) {
/* 334 */         statement.close();
/*     */       }
/*     */     }
/* 337 */     catch (SQLException ex) {
/* 338 */       Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgesto\\utils\DBConect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */