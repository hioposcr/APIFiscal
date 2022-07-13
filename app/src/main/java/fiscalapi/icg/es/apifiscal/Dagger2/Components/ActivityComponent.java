package fiscalapi.icg.es.apifiscal.Dagger2.Components;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import dagger.Component;
import fiscalapi.icg.es.apifiscal.Dagger2.ActivityContext;
import fiscalapi.icg.es.apifiscal.Dagger2.Modules.ActivityModule;
import fiscalapi.icg.es.apifiscal.Dagger2.PerActivity;
import fiscalapi.icg.es.apifiscal.Views.Configuration.ConfigurationActivity;
import fiscalapi.icg.es.apifiscal.Views.HIOPOSActivity.HIOPOSActivity;
import fiscalapi.icg.es.apifiscal.Views.MainActivity.MainActivity;
import fiscalapi.icg.es.apifiscal.Views.WrongVouchersActivity.WrongVouchersActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(ConfigurationActivity activity);
    void inject(HIOPOSActivity activity);
    void inject(WrongVouchersActivity activity);

    @ActivityContext
    Context getContext();

    AppCompatActivity getActivity();
}
