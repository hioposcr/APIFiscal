package fiscalapi.icg.es.apifiscal.Utils;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter { ///Esta Clase es un log que escribe en la carpeta /storage/emulated/0/HioposApiFiscalLog cada archivo es por fecha

    public static File dir = new File(new File(Environment.getExternalStorageDirectory(), "bleh"), "bleh");

    public static void WriteLine(String Line) {

try{
        String Nombre = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File Directorio = new File(Environment.getExternalStorageDirectory()+"/HioposApiFiscalLog");

        BufferedWriter bw = null;
        FileWriter fw = null;
        String fecha = new SimpleDateFormat("dd-MM-yyyy ; HH:mm:ss").format(new Date());

        try {
            if(!Directorio.exists())
            {
                Directorio.mkdir();
            }
            String data = fecha.toString()+":  ; "+Line+"\n";
            File file = new File( "/storage/emulated/0/HioposApiFiscalLog/"+Nombre+".txt");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }catch (Exception e){ Log.e("Hiopos", "Error Escribiendo Log desde LogWriter");}

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public static void verifyStoragePermissions(Activity activity) {
       try {

           // Check if we have write permission
           int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

           if (permission != PackageManager.PERMISSION_GRANTED) {
               // We don't have permission so prompt the user
               ActivityCompat.requestPermissions(
                       activity,
                       PERMISSIONS_STORAGE,
                       REQUEST_EXTERNAL_STORAGE
               );
           }
       }catch (Exception E){ Log.e("Hiopos", "Error Solicitando permisos de escritura desde LogWriter");
       }
       }

}
