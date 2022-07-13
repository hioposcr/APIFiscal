package fiscalapi.icg.es.apifiscal.Dagger2.Components;

import android.app.Service;

import dagger.Component;
import fiscalapi.icg.es.apifiscal.Dagger2.Modules.ServiceModule;
import fiscalapi.icg.es.apifiscal.Dagger2.PerService;
import fiscalapi.icg.es.apifiscal.Service.VoucherSenderService;

@PerService
@Component(dependencies = ApplicationComponent.class, modules= ServiceModule.class)
public interface ServiceComponent {
    void inject(VoucherSenderService service);

    Service getService();
}
