package fiscalapi.icg.es.apifiscal.Views;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import fiscalapi.icg.es.apifiscal.ApiFiscalApp;
import fiscalapi.icg.es.apifiscal.Dagger2.Components.ActivityComponent;
import fiscalapi.icg.es.apifiscal.Dagger2.Components.DaggerActivityComponent;
import fiscalapi.icg.es.apifiscal.Dagger2.Modules.ActivityModule;


public class View extends AppCompatActivity implements ViewI {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(ApiFiscalApp.getInstance().getComponent())
                .build();
    }

    @Override
    public ActivityComponent getActivityComponent(){return activityComponent;}

    @Override
    public Context getContext(){
        return this;
    }
}
