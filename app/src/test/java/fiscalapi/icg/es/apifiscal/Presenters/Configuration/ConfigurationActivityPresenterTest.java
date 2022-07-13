package fiscalapi.icg.es.apifiscal.Presenters.Configuration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Data.ApiFiscalDatabaseManager;
import fiscalapi.icg.es.apifiscal.Data.Models.Barrio;
import fiscalapi.icg.es.apifiscal.Data.Models.Canton;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Data.Models.Distrito;
import fiscalapi.icg.es.apifiscal.Data.Models.Provincia;
import fiscalapi.icg.es.apifiscal.Data.Models.TipoIdentificacion;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationActivityPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Inject
    ConfigurationActivityPresenter presenter;

    @Mock
    ApiFiscalDatabaseManager databaseManagerMock;

    @Before
    public void setUp(){
        presenter = new ConfigurationActivityPresenter(databaseManagerMock);
    }

    @Test
    public void saveConfiguration() {
        Configuracion newConfiguration = new Configuracion();
        newConfiguration.setClave_mdg("sqh9tlZUzIFy");
        newConfiguration.setUsuario_mdg("220");
        newConfiguration.setNombre("Lucas Martinez");
        newConfiguration.setCodigo_provincia("1");
        newConfiguration.setCodigo_canton("1");
        newConfiguration.setCodigo_distrito("1");
        newConfiguration.setDireccion("200 sur de la plaza");

        Mockito.when(databaseManagerMock.saveConfiguration(newConfiguration)).thenReturn(true);
        Mockito.when(databaseManagerMock.saveConfiguration(null)).thenReturn(false);
        assertTrue("Failed to save the configuration ",presenter.saveConfiguration(newConfiguration));
        assertFalse("False",presenter.saveConfiguration(null));

    }

    @Test
    public void loadConfiguration() {
        Configuracion config = new Configuracion();
        Configuracion newConfiguration = new Configuracion();
        newConfiguration.setClave_mdg("sqh9tlZUzIFy");
        newConfiguration.setUsuario_mdg("220");
        newConfiguration.setNombre("Lucas Martinez");
        newConfiguration.setCodigo_provincia("1");
        newConfiguration.setCodigo_canton("1");
        newConfiguration.setCodigo_distrito("1");
        newConfiguration.setDireccion("200 sur de la plaza");


        Mockito.when(databaseManagerMock.getConfiguration()).thenReturn(newConfiguration);
        assertNotNull("Failed to load configuration",presenter.loadConfiguration());
    }

    @Test
    public void getNameProvinces() {
        List<Provincia> provincie = new ArrayList<Provincia>();
        Provincia provincia = new Provincia();
        provincia.setNombre("Alajuela");
        provincie.add(provincia);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Alajuela");
        assertEquals ("Failed compare Provincies", list, presenter.getNameProvinces(provincie));
    }

    @Test
    public void getNameCantons() {
        List<Canton> cantons= new ArrayList<Canton>();
        Canton canton = new Canton();
        canton.setNombre("San Mateo");
        cantons.add(canton);
        ArrayList<String> list = new ArrayList<String>();
        list.add("San Mateo");
        assertEquals ("Failed to load cantons", list, presenter.getNameCantons(cantons));
    }

    @Test
    public void getNameDistricts() {
        List<Distrito> listDistrict = new ArrayList<Distrito>();
        Distrito district = new Distrito();
        district.setNombre("Labrador");
        listDistrict.add(district);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Labrador");
        assertEquals ("Failed compare District", list, presenter.getNameDistricts(listDistrict));
    }

    @Test
    public void getNameNeighborhoods() {
        List<Barrio> listNeighborhoods = new ArrayList<Barrio>();
        Barrio neighborhoods = new Barrio();
        neighborhoods.setNombre("Los lotes");
        listNeighborhoods.add(neighborhoods);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Los lotes");
        assertEquals ("Failed compare Neighborhoods", list, presenter.getNameNeighborhoods(listNeighborhoods));
    }

    @Test
    public void getNamesIdsTypes() {
        List<TipoIdentificacion> listIdType = new ArrayList<TipoIdentificacion>();
        TipoIdentificacion idType = new TipoIdentificacion();
        idType.setDescripcion("cédula");
        listIdType.add(idType);
        ArrayList<String> list = new ArrayList<String>();
        list.add("cédula");
        assertEquals ("Failed compare idTypes", list, presenter.getNamesIdsTypes(listIdType));
    }

    @Test
    public void emailValidator() {

        String email = "max";
        assertFalse("Invalid Email recognized",presenter.emailValidator(email));
        email = "maxwell@gmail.com";
        assertTrue("Valid email was not recognized",presenter.emailValidator(email));
    }

    @Test
    public void stripAccents() {
        String email = "máx@gmail.com";
        String correctEmail = "max@gmail.com";
        assertEquals(correctEmail,presenter.stripAccents(email));

    }
}