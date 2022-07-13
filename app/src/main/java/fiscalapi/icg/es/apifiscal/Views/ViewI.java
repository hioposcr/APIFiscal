package fiscalapi.icg.es.apifiscal.Views;

import android.content.Context;

import fiscalapi.icg.es.apifiscal.Dagger2.Components.ActivityComponent;

public interface ViewI {
    ActivityComponent getActivityComponent();
    Context getContext();
}
