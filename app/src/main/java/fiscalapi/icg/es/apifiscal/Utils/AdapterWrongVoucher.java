package fiscalapi.icg.es.apifiscal.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fiscalapi.icg.es.apifiscal.Data.Models.ComprobanteErroneo;
import fiscalapi.icg.es.apifiscal.Presenters.WrongVouchersPresenter.WrongVouchersPresenter;
import fiscalapi.icg.es.apifiscal.R;
import fiscalapi.icg.es.apifiscal.Views.WrongVouchersActivity.WrongVouchersActivity;

public class AdapterWrongVoucher extends BaseAdapter {
    Context context;
    ArrayList<ComprobanteErroneo> listWrongVoucher;
    ComprobanteErroneo wrongVoucher;
    WrongVouchersPresenter<WrongVouchersActivity> presenter;

    public AdapterWrongVoucher(Context context, ArrayList<ComprobanteErroneo> listWrongVoucher, WrongVouchersPresenter<WrongVouchersActivity> presenter) {
        this.context = context;
        this.listWrongVoucher = listWrongVoucher;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return listWrongVoucher.size();
    }

    @Override
    public Object getItem(int position) {
        return listWrongVoucher.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.wrong_voucher_item, null);
        }
        if (listWrongVoucher.size()!=0){
            ComprobanteErroneo dir = listWrongVoucher.get(position);

            TextView title = v.findViewById(R.id.numVoucher);
            title.setText("Comprobante # "+dir.getHiopos_id());

            TextView message = v.findViewById(R.id.errorMessage);
            message.setText(dir.getError());

            ImageButton btnDelete = v.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ComprobanteErroneo voucher = listWrongVoucher.get(position);
                    listWrongVoucher.remove(voucher);
                    //Log.d("Toco el voucher ", "onClick: "+voucher.getHiopos_id());
                    //Log.d("Toco ", "Cantidad: "+listWrongVoucher.size());
                    presenter.deleteWrongVoucher(voucher);
                    notifyDataSetChanged();
                    //setWrongVoucher(voucher);

                }
            });

        }


        return v;
    }

    public void clear() {
        listWrongVoucher.clear();
    }

    public void addAll(ArrayList<ComprobanteErroneo> list) {
        for (int i = 0; i < listWrongVoucher.size(); i++) {
            listWrongVoucher.add(list.get(i));
        }
    }

    public void setWrongVoucher(ComprobanteErroneo voucher){
        wrongVoucher = voucher;
    }
    public ComprobanteErroneo getWrongVoucher(){
        return wrongVoucher;
    }
}
