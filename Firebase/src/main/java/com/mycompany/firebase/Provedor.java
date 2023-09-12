package com.mycompany.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Precondition;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class Provedor {
    CollectionReference reference;
    static Firestore db;
    public static boolean guardarUsuario(String coleccion, String documento,
            Map<String, Object> data) {
       db = FirestoreClient.getFirestore();
       
        try {
            DocumentReference docRef =  db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Guardado exitosamente");
            return true;
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());           
        }
        return false;
    }
    
    public static boolean actualizar(String coleccion, String documento,
            Map<String, Object> data) {
       db = FirestoreClient.getFirestore();
       
        try {
            DocumentReference docRef =  db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.update(data);
            System.out.println("Actualizado exitosamente");
            return true;
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());           
        }
        return false;
    }
    
    public static boolean eliminar(String coleccion, String documento) {
       db = FirestoreClient.getFirestore();
       
        try {
            DocumentReference docRef =  db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.delete(Precondition.NONE);
            System.out.println("Eliminado exitosamente");
            return true;
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());           
        }
        return false;
    }
    
    public static void leer(JTable table) {
        
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Nombre");
    model.addColumn("Edad");
    model.addColumn("Correo");
    model.addColumn("Contraseña");
    
        try {
            CollectionReference usuarios = Conexion.db.collection("Persona");
            ApiFuture<QuerySnapshot> querysnap = usuarios.get();
            for (DocumentSnapshot document: querysnap.get().getDocuments()) {
                model.addRow (new Object[] {
                    document.getString("Nombre"),
                    document.getString("Edad"),
                    document.getString("Correo"),
                    document.getString("Contraseña")
                });
            }
        }catch (InterruptedException | ExecutionException e){
          System.err.println("Error: " + e.getMessage());
        }
        table.setModel(model);
    }
}
