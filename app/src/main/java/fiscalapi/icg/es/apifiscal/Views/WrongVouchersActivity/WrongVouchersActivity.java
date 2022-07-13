package fiscalapi.icg.es.apifiscal.Views.WrongVouchersActivity;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Data.Models.ComprobanteErroneo;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Presenters.WrongVouchersPresenter.WrongVouchersPresenter;
import fiscalapi.icg.es.apifiscal.R;
import fiscalapi.icg.es.apifiscal.Utils.AdapterWrongVoucher;
import fiscalapi.icg.es.apifiscal.Views.Configuration.ConfigurationActivity;
import fiscalapi.icg.es.apifiscal.Views.View;

public class WrongVouchersActivity extends View {
    @Inject
    protected WrongVouchersPresenter<WrongVouchersActivity> presenter;

    private ArrayList<ComprobanteErroneo> wrongVoucherArrayList = new ArrayList<ComprobanteErroneo>();
    private ListView listView;
    private AdapterWrongVoucher adapter;
    private ImageButton btnConfigurcion;
    private ImageButton btnDelete;
    private TextView txtPendingVouchers;
    private TextView txtVersion;
    private SimpleTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_vouchers);
        getActivityComponent().inject(this);
        presenter.onAttach(WrongVouchersActivity.this);
        listView = findViewById(R.id.listWrongVoucher);

        btnConfigurcion = findViewById(R.id.btn_configuration);
        btnDelete = findViewById(R.id.btnDelete);
        txtVersion = findViewById(R.id.versionText);

        try {
            txtVersion.setText("v" + getContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        }catch (Exception e){
            txtVersion.setText("Unkown");
        }

        btnConfigurcion.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(getApplicationContext(), ConfigurationActivity.class);
                startActivity(intent);
            }
        });

        StartNewTask();
    }

    protected void StartNewTask(){
        task = new SimpleTask();
        task.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        task.cancel(true);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(task != null && task.isCancelled() == true){
            task = new SimpleTask();
            task.execute();
        }
    }

    private class SimpleTask extends AsyncTask<Void, String, Void> {
        Boolean isProductionActive = false;
        Configuracion config;
        String count = "";
        boolean firstTime=true;
        boolean onError = false;
        int lastCount = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            config = presenter.getConfiguration();
            if (config!=null){
                isProductionActive=config.isProduccion_activo();
            }
            firstTime = true;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(onError == true){
                StartNewTask();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txtPendingVouchers.setText(values[0]);
            if (wrongVoucherArrayList.size()!=0 && wrongVoucherArrayList.size() != lastCount)
            {
                lastCount = wrongVoucherArrayList.size();
                adapter = new AdapterWrongVoucher(presenter.getViewContext(),wrongVoucherArrayList,presenter);
                listView.setAdapter(adapter);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            firstTime = false;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            txtPendingVouchers = findViewById(R.id.txtPendingVouchers);
            listView = findViewById(R.id.listWrongVoucher);

            while (firstTime){
                count = "Comprobantes pendientes: "+presenter.getNumPendingVouchers(isProductionActive);
                wrongVoucherArrayList = presenter.getWrongVouchers();
                this.publishProgress(count);
                try {
                    Thread.currentThread();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    firstTime = false;
                    onError = true;
                }
            }
            return null;
        }


    }
}


