package mx.itson.migration.clases;
import java.io.File;
import java.io.FileInputStream;	
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	
public class readXLSX {

     public void leerValores(String ruta,String m, String u, String p, String c) 
     throws FileNotFoundException, IOException, SQLException{
        List<Celda> titulos = new ArrayList();
        
        List<Celda> datosTemporales = new ArrayList();

        FileInputStream file = new FileInputStream(new File(ruta));
	
	// Crear el objeto que tendra el libro de Excel
	XSSFWorkbook workbook = new XSSFWorkbook(file);
        
	/*
	 * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
	 * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator
	 * que nos permite recorrer cada una de las filas que contiene.
	 */
	
	XSSFSheet sheet = workbook.getSheetAt(0);
	Iterator<Row> rowIterator = sheet.iterator();
	Row row;
	
	// Recorremos todas las filas para mostrar el contenido de cada celda
	
	while (rowIterator.hasNext()){
	    row = rowIterator.next();  // Obtenemos el iterator que permite recorres todas las celdas de una fila
	    Iterator<Cell> cellIterator = row.cellIterator();
	    Cell celda;
	    while (cellIterator.hasNext()){
		celda = cellIterator.next();// Dependiendo del formato de la celda el valor se debe mostrar como String, Fecha, boolean, entero...

                 Celda valor = new Celda(celda.toString(), celda.getColumnIndex());
                 
                 if (celda.toString().equals(m)) {
                     //System.out.println("Se eoncotro el titulo: " + celda.toString());
                    valor.setInfo("Mascota");
                    titulos.add(valor);
                }else if (celda.toString().equals(u)){
                    //System.out.println("Se eoncotro el titulo: " + celda.toString());
                    valor.setInfo("Ubicacion");
                    titulos.add(valor);
                }else if (celda.toString().equals(p)){
                   // System.out.println("Se eoncotro el titulo: " + celda.toString());
                   valor.setInfo("Precio");
                    titulos.add(valor);
                }else if (celda.toString().equals(c)){
                    //System.out.println("Se eoncotro el titulo: " + celda.toString());
                    valor.setInfo("Cliente");
                    titulos.add(valor);
                }else {
                 
                 datosTemporales.add(valor);
                }
               
               
	    }
            
	}
	// cerramos el libro excel
        
	workbook.close();
        separarValores(datosTemporales, titulos);
    }
    
    public void separarValores(List<Celda> valores, List<Celda> titulos) throws SQLException{
      
        List<String> mascotas = new ArrayList();
        List<String> ubicaciones = new ArrayList();
        List<Double> precios = new ArrayList();
        List<String> clientes = new ArrayList();
        
            for (int i = 0; i < valores.size(); i++) {
                for (int j = 0; j < titulos.size(); j++) {
                    if (valores.get(i).getColumna() == titulos.get(j).getColumna() && titulos.get(j).getInfo().equals("Mascota")) {
                        mascotas.add(valores.get(i).getInfo());
                        break;
                    }else if (valores.get(i).getColumna() == titulos.get(j).getColumna() && titulos.get(j).getInfo().equals("Ubicacion")) {
                        ubicaciones.add(valores.get(i).getInfo());
                        break;
                    }else if (valores.get(i).getColumna() == titulos.get(j).getColumna() && titulos.get(j).getInfo().equals("Precio")) {
                        precios.add(Double.parseDouble(valores.get(i).getInfo()));
                        break;
                    }else if (valores.get(i).getColumna() == titulos.get(j).getColumna() && titulos.get(j).getInfo().equals("Cliente")) {
                        clientes.add(valores.get(i).getInfo());
                        break;
                    }
                }
            }
            insertarEnDb(mascotas, ubicaciones, precios, clientes);
    }
    
    public void insertarEnDb(List<String> m, List<String> u, List<Double> p, List<String> c) throws SQLException{
        Connection conn = Conection.conexion();
        for (int i = 0; i < m.size(); i++) {
            String mascota = m.get(i);
            String ubicacion = u.get(i);
            Double precio = p.get(i);
            String cliente = c.get(i);
            
            CallableStatement storedProcedure = conn.prepareCall("CALL sp_insertarValores("+ "'" + mascota + "'" + ",+" + "'" +ubicacion +"'" +"," + precio + "," +"'" + cliente + "'"+ ")");
            storedProcedure.executeQuery();
            
        }
    }   
}