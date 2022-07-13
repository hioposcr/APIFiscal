package fiscalapi.icg.es.apifiscal.Dagger2.Modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fiscalapi.icg.es.apifiscal.Dagger2.ApplicationContext;
import fiscalapi.icg.es.apifiscal.Dagger2.DatabaseInfo;
import fiscalapi.icg.es.apifiscal.Dagger2.HIOPOSCloudContext;
import fiscalapi.icg.es.apifiscal.Dagger2.MDGCloudContext;
import fiscalapi.icg.es.apifiscal.Data.ApiFiscalDatabaseManager;
import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.HIOPOSCloudService;
import fiscalapi.icg.es.apifiscal.Data.MDG.MDGCloudService;
import fiscalapi.icg.es.apifiscal.Utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class ApiFiscalAppModule {
    private final Application mApplication;

    public ApiFiscalAppModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return Constants.DB_NAME;
    }

    @Provides
    @MDGCloudContext
    String provideMDGBaseURL() {
        return Constants.BASE_URL_MDG;
    }

    @Provides
    @HIOPOSCloudContext
    String provideHIOPOSBaseURL() {
        return Constants.BASE_URL;
    }

    @Provides
    @Singleton
    DatabaseManagerI provideDataManager(ApiFiscalDatabaseManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @MDGCloudContext
    Retrofit provideRetrofitMDG(@MDGCloudContext String url){
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
    }

    @Provides
    @HIOPOSCloudContext
    Retrofit provideRetrofitHIOPOS(@HIOPOSCloudContext String url){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
    }

    @Provides
    MDGCloudService provideMD5CloudService(@MDGCloudContext Retrofit retrofit){
        return retrofit.create(MDGCloudService.class);
    }

    @Provides
    HIOPOSCloudService provideHIOPOSCloudService(@HIOPOSCloudContext Retrofit retrofit){
        return retrofit.create(HIOPOSCloudService.class);
    }
}
