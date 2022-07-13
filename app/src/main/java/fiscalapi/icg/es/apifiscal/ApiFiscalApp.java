package fiscalapi.icg.es.apifiscal;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Locale;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Dagger2.Components.ApplicationComponent;
import fiscalapi.icg.es.apifiscal.Dagger2.Components.DaggerApplicationComponent;
import fiscalapi.icg.es.apifiscal.Dagger2.Modules.ApiFiscalAppModule;
import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;

public class ApiFiscalApp extends Application{

    protected ApplicationComponent mApplicationComponent;
    protected static PackageInfo pInfo;

    private static ApiFiscalApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .apiFiscalAppModule(new ApiFiscalAppModule(this))
                .build();

        mApplicationComponent.inject(this);

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        mInstance = this;

        pInfo = null;

        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ApiFiscalApp getInstance(){
        return mInstance;
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public static int getVersionCode() {

        int versionCode = 0;
        if(pInfo != null){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P && !"P".equals(Build.VERSION.CODENAME)) {
                versionCode = pInfo.versionCode;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    versionCode = (int)(pInfo.getLongVersionCode() & 0x00000000ffffffff);
                }
            }
        }

        return versionCode;
    }

    public static String getVersionName() {
        if(pInfo != null){
            return pInfo.versionName;
        }else{
            return  null;
        }
    }
}
