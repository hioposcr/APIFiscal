package fiscalapi.icg.es.apifiscal.Views.Configuration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Data.MDG.MDGCloudService;
import fiscalapi.icg.es.apifiscal.Data.MDG.Models.ValidateCredentialsResponse;
import fiscalapi.icg.es.apifiscal.Data.Models.Barrio;
import fiscalapi.icg.es.apifiscal.Utils.LogWriter;
import fiscalapi.icg.es.apifiscal.Data.Models.Canton;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Data.Models.Distrito;
import fiscalapi.icg.es.apifiscal.Data.Models.Provincia;
import fiscalapi.icg.es.apifiscal.Data.Models.TipoIdentificacion;
import fiscalapi.icg.es.apifiscal.Data.Models.ActividadesEconomicas;
import fiscalapi.icg.es.apifiscal.Presenters.Configuration.ConfigurationActivityPresenter;
import fiscalapi.icg.es.apifiscal.R;
import fiscalapi.icg.es.apifiscal.Views.View;
import fiscalapi.icg.es.apifiscal.Views.WrongVouchersActivity.WrongVouchersActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurationActivity extends View {
    Spinner spProvincie;
    Spinner spCanton;
    Spinner spDistrit;
    Spinner spNeighborhood;
    Spinner spIdentification;
    Spinner spActividadEconomica;

    Provincia selectedProvince;
    Canton selectedCanton;
    Distrito selectedDistrict;
    Barrio selectedNeighborhood;
    TipoIdentificacion selectedIdType;
    ActividadesEconomicas selectedActividadEconomica;

    EditText txtIdentification;
    EditText txtMDGUser;
    EditText txtMDGPassword;
    EditText txtNameUser;
    EditText txtTradename;
    EditText txtAddress;
    EditText txtPhoneCod;
    EditText txtPhoneNumber;
    EditText txtFaxCod;
    EditText txtFaxNumber;
    EditText txtEmailAddress;
    EditText txtTerminalSoloCC; //JVB VALOR PARA TERMINAL SOLO CC

    Button btnValidateCredentials;
    Button btnSave;
    CheckBox chklog;
    ImageButton btnHome;
    RadioButton radioButton;
    RadioGroup radioGroup;

    private boolean firstTime = true;
    private boolean isValidCredentials;
    private Configuracion configuracion;

    @Inject
    protected MDGCloudService cloudService;
    @Inject
    protected ConfigurationActivityPresenter<ConfigurationActivity> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        getActivityComponent().inject(this);
        presenter.onAttach(ConfigurationActivity.this);
        //ButterKnife.bind(this);
        spProvincie = findViewById(R.id.spProvincie);
        spCanton = findViewById(R.id.spCanton);
        spDistrit = findViewById(R.id.spDistrit);
        spNeighborhood = findViewById(R.id.spNeighborhood);
        spIdentification = findViewById(R.id.spIdentification);
        spActividadEconomica = findViewById(R.id.spActividadEconomica);
        txtIdentification = findViewById(R.id.etIdentification);
        //txtActividadesEconomicas = findViewById(R.id.etIdentification);
        txtMDGUser = findViewById(R.id.etUsuarioMDG);
        txtMDGPassword = findViewById(R.id.etClaveMDG);
        txtNameUser = findViewById(R.id.etName) ;
        txtTradename = findViewById(R.id.etTradeName);
        txtAddress = findViewById(R.id.etAddress);
        txtPhoneCod = findViewById(R.id.etCodCell);
        txtPhoneNumber = findViewById(R.id.etCell);
        txtFaxCod = findViewById(R.id.etCodFax);
        txtFaxNumber = findViewById(R.id.etFax);
        txtEmailAddress = findViewById(R.id.etEmail);
        btnValidateCredentials = findViewById(R.id.btnValidarCredenciales);
        chklog= findViewById(R.id.idLog);
        btnSave = findViewById(R.id.btnSave);
        btnHome = findViewById(R.id.btn_home);
        radioGroup = findViewById(R.id.radioGroup);
        txtTerminalSoloCC = findViewById(R.id.etTerminalSoloCC) ;//JVB TEXT PARA TERMINAL

        loadConfiguation();
        showIdTypes();
        showActividadesEconomicas();
        showProvinces();
        setListeners();

        btnValidateCredentials.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                validateConfigMDG();
            }
        });

        btnSave.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (validateUserData()){
                    if(validateConfigMDG()){
                        saveConfiguration();
                        Toast.makeText(getApplicationContext(),R.string.guardando_configuracion,Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),R.string.llenar_campos_requeridos,Toast.LENGTH_SHORT).show();
                }

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.idProduction:
                        configuracion.setProduccion_activo(true);
                        configuracion.setSolo_cc(false);
                        txtTerminalSoloCC.setVisibility(android.view.View.INVISIBLE);//JVB  04-04-2022
                        Toast.makeText(getApplicationContext(),R.string.presiono_produccion,Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.idTest:

                        configuracion.setProduccion_activo(false);
                        configuracion.setSolo_cc(false);
                        txtTerminalSoloCC.setVisibility(android.view.View.INVISIBLE);//JVB  04-04-2022
                        Toast.makeText(getApplicationContext(),R.string.presiono_pruebas,Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.idCc://JVB
                        configuracion.setSolo_cc(true);
                        configuracion.setProduccion_activo(false);
                        txtTerminalSoloCC.setVisibility(android.view.View.VISIBLE);//JVB  04-04-2022
                        Toast.makeText(getApplicationContext(),R.string.presiono_cc,Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        });

        btnHome.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(getApplicationContext(), WrongVouchersActivity.class);
                startActivity(intent);
            }




        });
    }

    private void setListeners(){

        txtIdentification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0)
                    if (s.toString().substring(0).equals("0"))
                        s.replace(0, 1, "");
            }
        });
        spIdentification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                if (!firstTime){
                    txtIdentification.setText("");

                }else {
                    firstTime=false;
                }
                txtIdentification.setEnabled(true);
                selectedIdType = presenter.getIdsTypes().get(position);
                switch (selectedIdType.getCodigo()) {
                    case "01":
                        txtIdentification.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                        break;

                    case "02":
                        txtIdentification.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                        break;

                    case "03":
                        txtIdentification.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
                        break;

                    case "04":
                        txtIdentification.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Actividad Economica agregado en H4.3
        spActividadEconomica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {

                selectedActividadEconomica = presenter.getActividadesEconomicas().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spProvincie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedProvince = presenter.getListOfProvinces().get(position);
                showCantons(selectedProvince.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCanton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedCanton = presenter.getListOfCantons(selectedProvince.getId()).get(position);
                showDistricts(selectedCanton.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDistrit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedDistrict = presenter.getListOfDistricts(selectedCanton.getId()).get(position);
                showNeighborhoods(selectedDistrict.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNeighborhood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                if (position!=0){
                    selectedNeighborhood = presenter.getListOfNeighborhoods(selectedDistrict.getId()).get(position-1);
                }else {
                    selectedNeighborhood = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void showIdTypes(){
        ArrayAdapter<String> adapter;
        List<TipoIdentificacion> idTypesList = presenter.getIdsTypes();
        ArrayList<String> list = presenter.getNamesIdsTypes(idTypesList);

        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIdentification.setAdapter(adapter);

        if (!TextUtils.isEmpty(configuracion.getTipo_identificacion())){
            for (TipoIdentificacion item : idTypesList) {
                if (configuracion.getTipo_identificacion().equals(item.getCodigo())) {
                    spIdentification.setSelection(idTypesList.indexOf(item));
                    txtIdentification.setText(configuracion.getIdentificacion());
                }
            }
        }
    }
    public void showActividadesEconomicas(){
        ArrayAdapter<String> adapter;
        List<ActividadesEconomicas> ActividadesEconomicasList = presenter.getActividadesEconomicas();
        ArrayList<String> list = presenter.getNamesActividadesEconomicas(ActividadesEconomicasList);

        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spActividadEconomica.setAdapter(adapter);

        if (!TextUtils.isEmpty(configuracion.getActividadesEconomicas())){
            for (ActividadesEconomicas item : ActividadesEconomicasList) {
                if (configuracion.getActividadesEconomicas() .equals(item.getCodigo() )) {
                    spActividadEconomica.setSelection(ActividadesEconomicasList.indexOf(item));
                    //txtIdentification.setText(configuracion.getIdentificacion());
                }
            }
        }
    }
    public void showProvinces(){

        ArrayAdapter<String> adapter;
        List<Provincia> provincieList = presenter.getListOfProvinces();
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, presenter.getNameProvinces(provincieList));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvincie.setAdapter(adapter);

        if (!TextUtils.isEmpty(configuracion.getCodigo_provincia())){
            for (Provincia item : provincieList) {
                if (configuracion.getCodigo_provincia().equals(item.getCodigo())) {
                    spProvincie.setSelection(provincieList.indexOf(item));
                }
            }
        }

    }


    public void showCantons(Long idProvince){

        ArrayAdapter<String> adapter;
        List<Canton> cantonList = presenter.getListOfCantons(idProvince);
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, presenter.getNameCantons(cantonList));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCanton.setAdapter(adapter);

        if (!TextUtils.isEmpty(configuracion.getCodigo_canton())){
            for (Canton item : cantonList) {
                if (configuracion.getCodigo_canton().equals(item.getCodigo())) {
                    spCanton.setSelection(cantonList.indexOf(item));
                }
            }
        }
    }
    public void showDistricts(Long idCanton){
        ArrayAdapter<String> adapter;
        List<Distrito> districtList = presenter.getListOfDistricts(idCanton);
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, presenter.getNameDistricts(districtList));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistrit.setAdapter(adapter);

        if (!TextUtils.isEmpty(configuracion.getCodigo_distrito())){
            for (Distrito item : districtList) {
                if (configuracion.getCodigo_distrito().equals(item.getCodigo())) {
                    spDistrit.setSelection(districtList.indexOf(item));
                }
            }
        }
    }
    public void showNeighborhoods(Long idDistrict){
        ArrayList<String> list= new ArrayList<String>();
        List<Barrio> neighborhoodList = presenter.getListOfNeighborhoods(idDistrict);
        list = presenter.getNameNeighborhoods(neighborhoodList);
        list.add(0,getString(R.string.seleccionar_una_opcion));

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNeighborhood.setAdapter(adapter);

        if (!TextUtils.isEmpty(configuracion.getCodigo_barrio())){
            for (Barrio item : neighborhoodList) {
                if (configuracion.getCodigo_barrio().equals(item.getCodigo())) {
                    spNeighborhood.setSelection(neighborhoodList.indexOf(item)+1);
                }
            }
        }
    }
    public void saveConfiguration(){
    //User




        if (!txtTerminalSoloCC.getText().toString().isEmpty())//JVB SE AÑADE LA TERMINAL PARA SOLO CC
            configuracion.setTerminalsolocc(txtTerminalSoloCC.getText().toString());//JVB SE AÑADE LA TERMINAL PARA SOLO CC

        if (!txtNameUser.getText().toString().isEmpty())
            configuracion.setNombre(txtNameUser.getText().toString());

        if (selectedIdType != null)
            configuracion.setTipo_identificacion(selectedIdType.getCodigo());

        if (selectedActividadEconomica != null) 
            configuracion.setActividad_economica(selectedActividadEconomica.getCodigo());

        if (!txtIdentification.getText().toString().isEmpty())
            configuracion.setIdentificacion(txtIdentification.getText().toString());

        if (!txtTradename.getText().toString().isEmpty())
            configuracion.setNombre_comercial(txtTradename.getText().toString());

        if (selectedProvince != null)
            configuracion.setCodigo_provincia(selectedProvince.getCodigo());

        if (selectedCanton != null)
            configuracion.setCodigo_canton(selectedCanton.getCodigo());

        if (selectedDistrict != null)
            configuracion.setCodigo_distrito(selectedDistrict.getCodigo());

        if (selectedNeighborhood != null)
            configuracion.setCodigo_barrio(selectedNeighborhood.getCodigo());

        if (!txtAddress.getText().toString().isEmpty())
            configuracion.setDireccion(txtAddress.getText().toString());

        if (!txtPhoneCod.getText().toString().isEmpty()) {
            configuracion.setCodigo_pais_telefono(txtPhoneCod.getText().toString());
        }

        if (!txtPhoneNumber.getText().toString().isEmpty()) {
            configuracion.setTelefono(txtPhoneNumber.getText().toString());
        }

        if (!txtFaxCod.getText().toString().isEmpty()) {
            configuracion.setCodigo_pais_fax(txtFaxCod.getText().toString());
        }

        if (!txtFaxNumber.getText().toString().isEmpty()) {
            configuracion.setFax(txtFaxNumber.getText().toString());
        }

        if (!txtEmailAddress.getText().toString().isEmpty())
            configuracion.setCorreo_electronico(presenter.stripAccents(txtEmailAddress.getText().toString()));

        //MDG Cloud
        if (!txtMDGUser.getText().toString().isEmpty())
            configuracion.setUsuario_mdg(txtMDGUser.getText().toString().trim());

        if (!txtMDGPassword.getText().toString().isEmpty())
            configuracion.setClave_mdg(txtMDGPassword.getText().toString().trim());

        presenter.saveConfiguration(configuracion);
        if (chklog.isChecked()){//JVB check para activar Logs 03-05-2022
            configuracion.setLog(true);
            LogWriter.verifyStoragePermissions(this);//JVB Solicitud de permisos para versiones 10+ Logs 03-05-2022
            LogWriter.WriteLine("Log Activado ;  Fun Save()");
        }
        else{
            configuracion.setLog(false);;
            LogWriter.WriteLine("Log Desactivado ; Fun Save()");
        }

        if(configuracion.getProduccion_activo())//Log para registro de cambio de ambientes
        LogWriter.WriteLine("Se ha guardado la configuración en ;Ambiente Prod");// Se incorpora log de seguimiento  JVB 02/05/2022
        if(configuracion.getProduccion_activo()==false&configuracion.getSolo_cc()==false )
            LogWriter.WriteLine("Se ha guardado la configuración en ;Ambiente Pruebas");// Se incorpora log de seguimiento  JVB 02/05/2022
        if(configuracion.getSolo_cc()==true )
            LogWriter.WriteLine("Se ha guardado la configuración con ;Solo Clave y Consec");// Se incorpora log de seguimiento  JVB 02/05/2022


    }
    public void loadConfiguation(){
        configuracion = presenter.loadConfiguration();
        if (configuracion == null){
            Toast.makeText(getApplicationContext(),R.string.configuracion_vacia,Toast.LENGTH_SHORT).show();
            configuracion = new Configuracion();
        }else {
            Toast.makeText(getApplicationContext(),String.format(getString(R.string.cargar_configuracion_usuario),configuracion.getNombre()),Toast.LENGTH_SHORT).show();

            if (!TextUtils.isEmpty(configuracion.getClave_mdg()))
                txtMDGPassword.setText(configuracion.getClave_mdg());

            if (!TextUtils.isEmpty(configuracion.getUsuario_mdg()))
                txtMDGUser.setText(configuracion.getUsuario_mdg());

            if (!TextUtils.isEmpty(configuracion.getTerminalsolocc()))//JVB
                txtTerminalSoloCC.setText(configuracion.getTerminalsolocc());//JVB

            if (!TextUtils.isEmpty(configuracion.getNombre()))
                txtNameUser.setText(configuracion.getNombre());

            if (!TextUtils.isEmpty(configuracion.getNombre_comercial()))
                txtTradename.setText(configuracion.getNombre_comercial());

            if (!TextUtils.isEmpty(configuracion.getDireccion()))
                txtAddress.setText(configuracion.getDireccion());

            if (!TextUtils.isEmpty(configuracion.getCodigo_pais_telefono())) {
                //isSetInitialText = true;
                txtPhoneCod.setText(configuracion.getCodigo_pais_telefono());
            }

            if (!TextUtils.isEmpty(configuracion.getTelefono())) {
                //isSetInitialText = true;
                txtPhoneNumber.setText(configuracion.getTelefono());
            }

            if (!TextUtils.isEmpty(configuracion.getCodigo_pais_fax())) {
                //isSetInitialText = true;
                txtFaxCod.setText(configuracion.getCodigo_pais_fax());
            }

            if (!TextUtils.isEmpty(configuracion.getFax())) {
                //isSetInitialText = true;
                txtFaxNumber.setText(configuracion.getFax());
            }

            if (!TextUtils.isEmpty(configuracion.getCorreo_electronico()))
                txtEmailAddress.setText(configuracion.getCorreo_electronico());

            if (configuracion.getLog()){
               chklog.setChecked(true);
               LogWriter.verifyStoragePermissions(this);
               LogWriter.WriteLine("Log activo ; loadConfiguation()");

            }



/*
            if (configuracion.getProduccion_activo()){ //JVB RESPALDO DE CÓDIGO ORIGINAL ANTES DE SOLO CC
                radioButton = findViewById(R.id.idProduction);
                radioButton.setChecked(true);
                Toast.makeText(getApplicationContext(),R.string.actualmente_produccion,Toast.LENGTH_SHORT).show();
            }else {

                radioButton = findViewById(R.id.idTest);
                radioButton.setChecked(true);
                Toast.makeText(getApplicationContext(),R.string.actualmente_pruebas,Toast.LENGTH_SHORT).show();
            }*/


            if (configuracion.getProduccion_activo()){
                radioButton = findViewById(R.id.idProduction);
                radioButton.setChecked(true);
                txtTerminalSoloCC.setVisibility(android.view.View.INVISIBLE);//JVB
                Toast.makeText(getApplicationContext(),R.string.actualmente_produccion,Toast.LENGTH_SHORT).show();


            } if (!configuracion.getSolo_cc()&!configuracion.getProduccion_activo()) {//JVB 29-03-2022  SE CAMBIA LÓGICA DE RADIO BUTTON PARA GUARDAR EL PARAMETRO DE SOLO CC

                radioButton = findViewById(R.id.idTest);
                radioButton.setChecked(true);
                txtTerminalSoloCC.setVisibility(android.view.View.INVISIBLE);//JVB
                Toast.makeText(getApplicationContext(),R.string.actualmente_pruebas,Toast.LENGTH_SHORT).show();
            }
            if (configuracion.getSolo_cc()){//JVB 29-03-2022  SE CAMBIA LÓGICA DE RADIO BUTTON PARA GUARDAR EL PARAMETRO DE SOLO CC
                radioButton = findViewById(R.id.idCc);
                radioButton.setChecked(true);
                txtTerminalSoloCC.setVisibility(android.view.View.VISIBLE);//JVB
                Toast.makeText(getApplicationContext(),R.string.actualmente_cc,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateConfigMDG() {

        txtMDGUser.setError(null);
        txtMDGPassword.setError(null);
        isValidCredentials = true;


        if (TextUtils.isEmpty(txtMDGUser.getText().toString())) {
            txtMDGUser.setError(getString(R.string.campo_requerido));
            Toast.makeText(getApplicationContext(), R.string.usuario_mdg_requerido, Toast.LENGTH_SHORT).show();
            isValidCredentials = false;
        }

        if (txtMDGPassword.getText().toString().isEmpty()) {
            txtMDGPassword.setError(getString(R.string.campo_requerido));
            Toast.makeText(getApplicationContext(), R.string.contrasena_mdg_requerido, Toast.LENGTH_SHORT).show();
            isValidCredentials = false;
        }
        if (isValidCredentials){
            Call<ValidateCredentialsResponse> call = cloudService.validateCredentials("password",txtMDGUser.getText().toString(),txtMDGPassword.getText().toString());

            call.enqueue(new Callback<ValidateCredentialsResponse>() {
                @Override
                public void onResponse(Call<ValidateCredentialsResponse> call, Response<ValidateCredentialsResponse> response) {

                    if (response.isSuccessful()) {
                        //Log.d("SERVICE_LOG",String.format(getString(R.string.validar_credenciales),"Success"));
                        //Log.d("SERVICE_LOG","Success Credentials"+response.code());
                        Toast.makeText(getApplicationContext(),R.string.credenciales_correctas,Toast.LENGTH_SHORT).show();

                    } else {
                        //Log.d("SERVICE_LOG","Incorrect Credentials"+response.code());
                        Toast.makeText(getApplicationContext(),R.string.credenciales_incorrectas,Toast.LENGTH_SHORT).show();
                        txtMDGUser.setError(getString(R.string.verificar));
                        txtMDGPassword.setError(getString(R.string.verificar));
                        isValidCredentials= false;
                    }

                }

                @Override
                public void onFailure(Call<ValidateCredentialsResponse> call, Throwable t) {
                    //Log.d("SERVICE_LOG","Connection Failed");
                    Toast.makeText(getApplicationContext(),R.string.fallo_conexion,Toast.LENGTH_SHORT).show();
                    isValidCredentials= false;
                    //Comentario de prueba
                }
            });
        }

        return isValidCredentials;
    }

    private boolean validateUserData() {

        boolean isValid = true;

        if (txtNameUser.getText().toString().isEmpty()) {
            txtNameUser.setError(getString(R.string.campo_requerido));
            isValid = false;
        }

        /*if (selectedIdType == null) {
            Toast.makeText(getApplicationContext(), R.string.seleccione_tipo_identificacion, Toast.LENGTH_SHORT).show();
            isValid = false;
        }*/

        if (txtIdentification.getText().toString().isEmpty()) {
            txtIdentification.setError(getString(R.string.campo_requerido));
            isValid = false;
        } else {

            int length = txtIdentification.getText().toString().length();

            switch (selectedIdType.getCodigo()) {

                case "01": //cedula fisica
                    if (length < 9) {
                        txtIdentification.setError(getString(R.string.digitos_minimos_nueve));
                        isValid = false;
                    }
                    break;

                case "02": //cedula juridica
                    if (length < 10) {
                        txtIdentification.setError(getString(R.string.digitos_minimos_diez));
                        isValid = false;
                    }
                    break;

                case "03": //DIMEX
                    if (length < 11) {
                        txtIdentification.setError(getString(R.string.digitos_minimos_once));
                        isValid = false;
                    }
                    break;

                case "04": //NITE
                    if (length < 10) {
                        txtIdentification.setError(getString(R.string.digitos_minimos_diez));
                        isValid = false;
                    }
                    break;
            }
        }

        if (txtAddress.getText().toString().isEmpty()) {
            txtAddress.setError(getString(R.string.campo_requerido));
            isValid = false;
        }

        if (txtEmailAddress.getText().toString().isEmpty()) {
            txtEmailAddress.setError(getString(R.string.campo_requerido));
            isValid = false;
        } else if (!presenter.emailValidator(txtEmailAddress.getText().toString())) {
            txtEmailAddress.setError(getString(R.string.email_invalido));
            isValid = false;
        }

        return isValid;
    }

}
