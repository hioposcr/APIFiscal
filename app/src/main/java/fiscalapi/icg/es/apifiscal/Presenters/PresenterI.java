package fiscalapi.icg.es.apifiscal.Presenters;

import fiscalapi.icg.es.apifiscal.Data.ApiFiscalDatabaseManager;
import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;
import fiscalapi.icg.es.apifiscal.Views.ViewI;

public interface PresenterI <V extends ViewI>{

    public void onAttach(V view);
    public void onDetach();
    DatabaseManagerI getDataManager();
}
