package fiscalapi.icg.es.apifiscal.Dagger2.Modules;

import android.app.Service;

import dagger.Module;
import dagger.Provides;
import fiscalapi.icg.es.apifiscal.Dagger2.PerService;
import fiscalapi.icg.es.apifiscal.Service.VoucherSenderService;

@Module
public class ServiceModule {
    private final Service mService;

    public ServiceModule(Service service){
        mService = service;
    }

    @Provides
    Service provideService() {
        return mService;
    }

    @Provides
    @PerService
    VoucherSenderService providesVoucherSenderService(VoucherSenderService service){
        return  service;
    }
}
