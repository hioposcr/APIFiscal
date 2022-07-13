package fiscalapi.icg.es.apifiscal.Presenters.MainActivity;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Presenters.Presenter;
import fiscalapi.icg.es.apifiscal.Service.VoucherSenderService;
import fiscalapi.icg.es.apifiscal.Views.Configuration.ConfigurationActivity;
import fiscalapi.icg.es.apifiscal.Views.MainActivity.MainActivity;
import fiscalapi.icg.es.apifiscal.Views.WrongVouchersActivity.WrongVouchersActivity;

public class MainActivityPresenter<V extends MainActivity> extends Presenter {
    @Inject
    public MainActivityPresenter(DatabaseManagerI dataManager) {
        super(dataManager);
    }


    public void startVoucherSenderService(){
        Context context = view.getContext();
        Intent intent = new Intent(context,VoucherSenderService.class);
        context.startService(intent);
    }

    public void startNextScene(){
        Context context = view.getContext();
        Configuracion config = getDataManager().getConfiguration();
        if (config==null){
            Intent intent = new Intent(context, ConfigurationActivity.class);
            context.startActivity(intent);
        }else{
            Intent intent = new Intent(context, WrongVouchersActivity.class);
            context.startActivity(intent);
        }


    }
}
