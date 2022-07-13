package fiscalapi.icg.es.apifiscal.Presenters.WrongVouchersPresenter;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;
import fiscalapi.icg.es.apifiscal.Data.Models.Comprobante;
import fiscalapi.icg.es.apifiscal.Data.Models.ComprobanteErroneo;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Presenters.Presenter;
import fiscalapi.icg.es.apifiscal.Views.WrongVouchersActivity.WrongVouchersActivity;

public class WrongVouchersPresenter <V extends WrongVouchersActivity> extends Presenter {
    @Inject
    public WrongVouchersPresenter(DatabaseManagerI dataManager) {
        super(dataManager);
    }

    public ArrayList<ComprobanteErroneo> getWrongVouchers(){
        ArrayList<ComprobanteErroneo> list;
        ArrayList<ComprobanteErroneo> data = (ArrayList<ComprobanteErroneo>)getDataManager().getComprobantesErrones();
        if(data != null) {
            list = data;
        }else{
            list = new ArrayList<ComprobanteErroneo>();
        }

        /*;
        ComprobanteErroneo voucher_1 = new ComprobanteErroneo();
        voucher_1.setError("No se pudo conectar a MDG Cloud");
        voucher_1.setHiopos_id("01051078");
        list.add(voucher_1);

        ComprobanteErroneo voucher_2 = new ComprobanteErroneo();
        voucher_2.setError("Este usuario no esta autorizado para enviar comprobantes");
        voucher_2.setHiopos_id("02047823");
        list.add(voucher_2);

        ComprobanteErroneo voucher_3 = new ComprobanteErroneo();
        voucher_3.setError("Los campos de provicia y canton no pueden estar vacíos");
        voucher_3.setHiopos_id("03258745");
        list.add(voucher_3);*/

       /* WrongVoucher voucher_4 = new WrongVoucher();
        voucher_4.setErrorMessage("No se pudo conectar a MDG Cloud");
        voucher_4.setVoucherNumber("01051078");
        list.add(voucher_4);

        WrongVoucher voucher_5 = new WrongVoucher();
        voucher_5.setErrorMessage("Este usuario no esta autorizado para enviar comprobantes");
        voucher_5.setVoucherNumber("02047823");
        list.add(voucher_5);

        WrongVoucher voucher_6 = new WrongVoucher();
        voucher_6.setErrorMessage("Los campos de provicia y canton no pueden estar vacíos");
        voucher_6.setVoucherNumber("03258745");
        list.add(voucher_6);

        WrongVoucher voucher_7 = new WrongVoucher();
        voucher_7.setErrorMessage("No se pudo conectar a MDG Cloud");
        voucher_7.setVoucherNumber("01051078");
        list.add(voucher_7);

        WrongVoucher voucher_8 = new WrongVoucher();
        voucher_8.setErrorMessage("Este usuario no esta autorizado para enviar comprobantes");
        voucher_8.setVoucherNumber("02047823");
        list.add(voucher_8);

        WrongVoucher voucher_9 = new WrongVoucher();
        voucher_9.setErrorMessage("Los campos de provicia y canton no pueden estar vacíos");
        voucher_9.setVoucherNumber("03258745");
        list.add(voucher_9);*/

        return list;
    }

    public int getNumPendingVouchers(boolean isProductionActivo){
        int number = 0;
        List<Comprobante> list = getDataManager().getPendingVouchers(isProductionActivo);
        number=list.size();
        return number;
    }

    public Configuracion getConfiguration(){
        return getDataManager().getConfiguration();
    }

    public void deleteWrongVoucher(ComprobanteErroneo voucher){
        getDataManager().deleteWrongVoucher(voucher);
    }

    public Context getViewContext(){
        return this.view.getContext();
    }

}
