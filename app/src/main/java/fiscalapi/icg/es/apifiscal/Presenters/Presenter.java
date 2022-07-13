package fiscalapi.icg.es.apifiscal.Presenters;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;
import fiscalapi.icg.es.apifiscal.Views.ViewI;

public class Presenter <V extends ViewI> implements PresenterI <V>{

    private final DatabaseManagerI mDataManager;
    protected V view;

    @Inject
    public Presenter(DatabaseManagerI dataManager) {
        this.mDataManager = dataManager;
    }


    @Override
    public void onAttach(V view) {
        this.view=view;
    }

    @Override
    public void onDetach() {
        this.view=null;
    }

    public DatabaseManagerI getDataManager(){
        return mDataManager;
    }
}
