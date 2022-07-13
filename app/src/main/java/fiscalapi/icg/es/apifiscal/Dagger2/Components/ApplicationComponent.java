package fiscalapi.icg.es.apifiscal.Dagger2.Components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import fiscalapi.icg.es.apifiscal.ApiFiscalApp;
import fiscalapi.icg.es.apifiscal.Dagger2.ApplicationContext;
import fiscalapi.icg.es.apifiscal.Dagger2.Modules.ApiFiscalAppModule;
import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.HIOPOSCloudService;
import fiscalapi.icg.es.apifiscal.Data.MDG.MDGCloudService;

@Singleton
@Component(modules = ApiFiscalAppModule.class)
public interface ApplicationComponent {

    void inject(ApiFiscalApp application);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DatabaseManagerI getDataManager();

    MDGCloudService getMDGCloudService();
    HIOPOSCloudService getHIOPOSCloudService();
}
