package fiscalapi.icg.es.apifiscal.Presenters.Configuration;

import android.util.Log;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;
import fiscalapi.icg.es.apifiscal.Data.Models.Barrio;
import fiscalapi.icg.es.apifiscal.Data.Models.Canton;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Data.Models.Distrito;
import fiscalapi.icg.es.apifiscal.Data.Models.Provincia;
import fiscalapi.icg.es.apifiscal.Data.Models.TipoIdentificacion;
import fiscalapi.icg.es.apifiscal.Data.Models.ActividadesEconomicas;
import fiscalapi.icg.es.apifiscal.Presenters.Presenter;
import fiscalapi.icg.es.apifiscal.Utils.Constants;
import fiscalapi.icg.es.apifiscal.Views.Configuration.ConfigurationActivity;
import fiscalapi.icg.es.apifiscal.Views.ViewI;

public class ConfigurationActivityPresenter <V extends ConfigurationActivity> extends Presenter {
    @Inject
    public ConfigurationActivityPresenter(DatabaseManagerI dataManager) {
        super(dataManager);
    }

    public boolean saveConfiguration(Configuracion configuracion){
       // Log.d("guardó la configuración",configuracion.getNombre());
        return getDataManager().saveConfiguration(configuracion);
    }
    public Configuracion loadConfiguration(){
        return getDataManager().getConfiguration();
    }
    public List<Provincia> getListOfProvinces(){
        return getDataManager().getProvinces();
    }
    public ArrayList<String> getNameProvinces(List<Provincia> list){
        ArrayList<String> names = new ArrayList<String>();
        for ( Provincia provincia: list ) {
            names.add(provincia.getNombre());
        }
        return names;
    }
    public List<Canton> getListOfCantons(Long idProvince){
        return getDataManager().getCantons(idProvince);
    }
    public  ArrayList<String> getNameCantons(List<Canton> list){
        ArrayList<String> names = new ArrayList<String>();
        for ( Canton canton: list ) {
            names.add(canton.getNombre());
        }
        return names;
    }

    public List<Distrito> getListOfDistricts(Long idCanton){
        return getDataManager().getDistricts(idCanton);
    }
    public  ArrayList<String> getNameDistricts(List<Distrito> list){
        ArrayList<String> names = new ArrayList<String>();
        for ( Distrito district: list ) {
            names.add(district.getNombre());
           // Log.d("Cantón seleccionado ID",district.getId_canton().toString());
        }
        return names;
    }
    public List<Barrio> getListOfNeighborhoods(Long idDistrict){
        return getDataManager().getNeighborhoods(idDistrict);
    }
    public  ArrayList<String> getNameNeighborhoods(List<Barrio> list){
        ArrayList<String> names = new ArrayList<String>();
        for ( Barrio neighborhoods: list ) {
            names.add(neighborhoods.getNombre());
            //Log.d("Barrio seleccionado ID",neighborhoods.getId_distrito().toString());
        }
        return names;
    }

    public List<TipoIdentificacion> getIdsTypes(){
        return getDataManager().getIdsTypes();
    }
    public ArrayList<String> getNamesIdsTypes(List<TipoIdentificacion> list){
        ArrayList<String> identifications = new ArrayList<String>();
        for (TipoIdentificacion identification: list) {
            identifications.add(identification.getDescripcion());
        }
        return identifications;
    }
    public List<ActividadesEconomicas> getActividadesEconomicas(){
        return getDataManager().getActividadesEconomicas();
    }
    public ArrayList<String> getNamesActividadesEconomicas(List<ActividadesEconomicas> list){
        ArrayList<String> names = new ArrayList<String>();
        for (ActividadesEconomicas actividadesEconomicas: list) {
            names.add(actividadesEconomicas.getNombre());
        }
        return names;
    }

    public boolean emailValidator(String email) {
        String regExpn = Constants.EMAIL_REGULAR_EXPRESION;
/*
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";*/

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    public String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

}
