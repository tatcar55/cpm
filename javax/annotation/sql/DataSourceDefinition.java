package javax.annotation.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceDefinition {
  String name();
  
  String className();
  
  String description() default "";
  
  String url() default "";
  
  String user() default "";
  
  String password() default "";
  
  String databaseName() default "";
  
  int portNumber() default -1;
  
  String serverName() default "localhost";
  
  int isolationLevel() default -1;
  
  boolean transactional() default true;
  
  int initialPoolSize() default -1;
  
  int maxPoolSize() default -1;
  
  int minPoolSize() default -1;
  
  int maxIdleTime() default -1;
  
  int maxStatements() default -1;
  
  String[] properties() default {};
  
  int loginTimeout() default 0;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\annotation\sql\DataSourceDefinition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */