package fiscalapi.icg.es.apifiscal.Dagger2.Modules;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import fiscalapi.icg.es.apifiscal.Dagger2.ActivityContext;
import fiscalapi.icg.es.apifiscal.Dagger2.PerActivity;
import fiscalapi.icg.es.apifiscal.Presenters.Configuration.ConfigurationActivityPresenter;
import fiscalapi.icg.es.apifiscal.Presenters.HIOPOSPresenter.HIOPOSPresenter;
import fiscalapi.icg.es.apifiscal.Presenters.MainActivity.MainActivityPresenter;
import fiscalapi.icg.es.apifiscal.Presenters.WrongVouchersPresenter.WrongVouchersPresenter;

@Module
public class ActivityModule {

    private final AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity){
        this.mActivity=activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    ConfigurationActivityPresenter provideConfigurationActivityPresenter(ConfigurationActivityPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainActivityPresenter provideMainActivityPresenter(MainActivityPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    HIOPOSPresenter provideHIOPOSPresenter(HIOPOSPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    WrongVouchersPresenter provideWrongVoucherPresenter(WrongVouchersPresenter presenter) {
        return presenter;
    }
}
