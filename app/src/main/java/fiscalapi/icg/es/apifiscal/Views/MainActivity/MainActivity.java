package fiscalapi.icg.es.apifiscal.Views.MainActivity;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.ApiFiscalApp;
import fiscalapi.icg.es.apifiscal.Presenters.MainActivity.MainActivityPresenter;


import fiscalapi.icg.es.apifiscal.Views.View;

public class MainActivity extends View {

    @Inject
    MainActivityPresenter<MainActivity> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        //Log.d("HIOPOSACTIVITY","test "+ ApiFiscalApp.getInstance().getVersionCode());

        presenter.startVoucherSenderService();
        presenter.startNextScene();
        finish();
    }
}
