package fiscalapi.icg.es.apifiscal.Views.HIOPOSActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Presenters.HIOPOSPresenter.HIOPOSPresenter;
import fiscalapi.icg.es.apifiscal.R;
import fiscalapi.icg.es.apifiscal.Utils.Constants;
import fiscalapi.icg.es.apifiscal.Utils.LogWriter;
import fiscalapi.icg.es.apifiscal.Views.View;

public class HIOPOSActivity extends View {
    @Inject
    HIOPOSPresenter<HIOPOSActivity> presenter;
    private Configuracion configuracion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        presenter.onAttach(this);

        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();

            if (action != null) {

                switch (action){
                    case Constants.GET_VERSION:{

                        presenter.manejarVersionIntent();

                    }
                    break;
                    case Constants.GET_BEHAVIOR:{
                        presenter.manejarComportamientoVersionIntent();
                    }
                    break;
                    case Constants.SALE:{


                        presenter.manejarVentaOIntentVacio(intent);
                    }
                    break;
                    case Constants.VOID_SALE:{
                        presenter.manejarVentaOIntentVacio(intent);

                    }
                    break;
                    case Constants.PRINT_SALE:{
                     //   presenter.manejarPrintSale(intent); Se comenta esta línea y se añade el mismo procesamiento de venta para reprocesamiento por reimpresión JVB 02/05/2022

                        presenter.manejarVentaOIntentVacio(intent);

                    }
                    break;
                }
            }
        }

        finish();
    }
}
