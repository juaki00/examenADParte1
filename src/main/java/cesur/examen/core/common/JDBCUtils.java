package cesur.examen.core.common;

import lombok.extern.java.Log;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno: Joaquin Romero Sanchez
 * Fecha: 11/12/2023
 *
 * No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 * En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 * o para seguir la traza de ejecución.
 */
@Log
public class JDBCUtils {

    /**
     * Connection is stored as a static final object accessible by all other classes.
     * Connection data is retrieved from db.properties file located in resource folder.
     *
     * Remember to open an InputStream to a file located in resource folder using
     * JDBCUtils.class.getClassLoader().getResourceAsStream()
     */
    private static final Connection conn;
    private final static Logger logger;
    static{

        try{
            /* Make implementation here ...  */
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream( "db.properties");
            Properties config = new Properties();
            logger = Logger.getLogger( JDBCUtils.class.getName());
            config.load(is);
            String host = (String) config.get("host");
            String name = (String) config.get("name");
            String pass = (String) config.get("pass");
            String port = (String) config.get("port");
            String user = (String) config.get("user");
            String url = (String) config.get("url");
            String urlCompleta = url + host + ":" + port + "/" + name;
            conn = DriverManager.getConnection(urlCompleta,user,pass);
            logger.info("Successful connection to database");


            if(conn==null) log.info("JDBCUtils Not implemented yet!");
            else log.info("Succesfully connected!");

        }catch( Exception ex){
            log.severe("Error connecting to database");
            throw new RuntimeException("Error connecting to database");
        }
    }


    public static Connection getConn() {
        return conn;
    }

    /**
     * Conversion utility from util.Date to sql.Date
     * Remember that sql.Date should be used in Jdbc queries but
     * class java.util.Date cannot be cast to class java.sql.Date
     * We can do the cast (util.Date) sql.Date safely, but not backwards.
     *
     * @param d date in util.Date format
     * @return the same date in sql.Date date
     */
    public static java.sql.Date dateUtilToSQL( java.util.Date d){
        return new java.sql.Date(d.getTime());
    }

}
