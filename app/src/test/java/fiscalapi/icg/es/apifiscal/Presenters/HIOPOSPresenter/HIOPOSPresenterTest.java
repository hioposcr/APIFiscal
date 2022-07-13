package fiscalapi.icg.es.apifiscal.Presenters.HIOPOSPresenter;

import android.os.LocaleList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.exceptions.verification.junit.ArgumentsAreDifferent;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Field;
import java.net.ContentHandler;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.Data.ApiFiscalDatabaseManager;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.CustomProductFields;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.CustomTaxFields;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.CustomerField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.HeaderField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.HioposSale;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.LineField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.LineTaxField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.PaymentMeanField;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.CodigoComercial;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.EmisorReceptor;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.FacturaElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Identificacion;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Impuesto;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.LineaDetalle;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.NotaCreditoElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.NotaDebitoElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Telefono;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.TiqueteHacienda;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Ubicacion;
import fiscalapi.icg.es.apifiscal.Data.Models.Canton;
import fiscalapi.icg.es.apifiscal.Data.Models.Comprobante;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Data.Models.Distrito;
import fiscalapi.icg.es.apifiscal.Data.Models.Provincia;
import fiscalapi.icg.es.apifiscal.Utils.Constants;
import fiscalapi.icg.es.apifiscal.Utils.HIOPOSInputFormatException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HIOPOSPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    HIOPOSPresenter presenter;

    @Mock
    ApiFiscalDatabaseManager databaseManagerMock;
    @Mock
    Configuracion config;
    @Mock
    HIOPOSPresenter presenterMock;

    @Before
    public void setUp() throws Exception {
        presenter = new HIOPOSPresenter(databaseManagerMock);
    }

    public HioposSale crearHioposVenta(){
        HioposSale venta = new HioposSale();
        HeaderField headerField = new HeaderField();
        Map<String,String> headerFields = new HashMap<>();
        headerFields.put("CurrencyISOCode","CRC");
        headerFields.put("CurrencyExchangeRate","1");
        headerFields.put("Serie","F0002115");
        headerFields.put("Number","1");
        headerFields.put("Date","06-06-2019");
        headerFields.put("Time","14:39:46");
        headerField.setHeaderFields(headerFields);

        //Customer
        CustomerField customerField = new CustomerField();
        Map<String,String> customer = new HashMap<>();
        customer.put("customerId","149");
        customer.put("Name","Diego");
        customer.put("FiscalIdDocType","04");
        customer.put("FiscalId","01-201745528");
        customer.put("Phone","86882145");
        customer.put("Email","diego@gmail.com");
        customer.put("IsTaxExempt","false");
        customerField.setCustomerFields(customer);
        headerField.setCustomer(customerField);

        //Medios de pago
        ArrayList<PaymentMeanField> mediosDePago = new ArrayList<>();

        PaymentMeanField medioPago = new PaymentMeanField();
        Map<String,String> medios = new HashMap<>();
        medios.put("PaymentMeanId","1");
        medioPago.setPaymentMeanFields(medios);
        mediosDePago.add(medioPago);


        //lineas
        ArrayList<LineField> lineas = new ArrayList<>();

        for(int i = 0; i < (Math.random()*(10-1)+1);i++) {

            LineField inputLine = new LineField();
            //Campos linea
            Hashtable<String, String> camposLinea = new Hashtable<>();
            camposLinea.put("Units", "5");
            camposLinea.put("Name", "Test");
            camposLinea.put("ProductId", "141");
            camposLinea.put("Price", "600");
            camposLinea.put("DiscountAmount", "0");
            camposLinea.put("BaseAmount", "600");
            camposLinea.put("TaxesAmount", "0");
            camposLinea.put("NetAmount", "600");
            inputLine.setLineFields(camposLinea);

            //Custom campos linea
            CustomProductFields customProductFields = new CustomProductFields();
            Hashtable<String, String> customCamposLinea = new Hashtable<>();
            customCamposLinea.put(Constants.TIPO_DE_ARTICULO, "Mercancia");
            customCamposLinea.put(Constants.CODIGO_TIPO_DE_ARTICULO, "01");
            customProductFields.setCustomProductFields(customCamposLinea);
            ArrayList<CustomProductFields> customProductFieldsA = new ArrayList<>();
            customProductFieldsA.add(customProductFields);

            inputLine.setCustomProductFields(customProductFieldsA);

            //Impuestos por linea
            ArrayList<LineTaxField> taxes = new ArrayList<>();
            LineTaxField lineTax = new LineTaxField();
            //campos impuesto
            Hashtable<String, String> lineTaxes = new Hashtable<>();
            lineTaxes.put("Percentage", "13");
            lineTax.setLineTaxFields(lineTaxes);
            // custom campos de impuesto
            CustomTaxFields customTaxField = new CustomTaxFields();
            Hashtable<String, String> customTaxes = new Hashtable<>();
            customTaxes.put(Constants.CODIGO_IMPUESTO, "01");
            customTaxes.put(Constants.TARIFA, "13");
            customTaxes.put(Constants.CODIGO_TARIFA, "08");
            customTaxField.setCustomTaxFields(customTaxes);
            lineTax.setCustomTaxFields(customTaxField);
            taxes.add(lineTax);

            inputLine.setLineTaxes(taxes);


            lineas.add(inputLine);
        }


        venta.setHeader(headerField);
        venta.setPaymentMeans(mediosDePago);
        venta.setLines(lineas);


        return  venta;
    }

    @Test
    public void getHDADocumentType() throws Exception {
        thrown.expect(HIOPOSInputFormatException.class);
        presenter.obtenerTipoDocumentoHacienda(0);
        assertEquals(Constants.MH_TICKET,presenter.obtenerTipoDocumentoHacienda(Constants.HIOPOS_TICKET));
        assertEquals(Constants.MH_INVOICE,presenter.obtenerTipoDocumentoHacienda(Constants.HIOPOS_INVOICE));
        assertEquals(Constants.MH_CREDIT_NOTE,presenter.obtenerTipoDocumentoHacienda(Constants.HIOPOS_CREDIT_TICKET));
        assertEquals(Constants.MH_CREDIT_NOTE,presenter.obtenerTipoDocumentoHacienda(Constants.HIOPOS_CREDIT_INVOICE));
        assertEquals(Constants.MH_TICKET,presenter.obtenerTipoDocumentoHacienda(Constants.HIOPOS_NON_PRINTABLE_TICKET));
        assertEquals(Constants.MH_TICKET,presenter.obtenerTipoDocumentoHacienda(Constants.HIOPOS_INVITATION));
    }

    @Test
    public void obtenerNumeroConsecutivoFinal() throws Exception {
        String posId = "00";
        String hda = "01";
        int consecutivo = 11;

        String result =Constants.DEFAULT_HEADQUARTERS+posId+hda+String.format(Locale.getDefault(), "%010d", consecutivo);
        assertEquals(result,presenter.obtenerNumeroConsecutivoFinal(posId,hda,consecutivo));

        thrown.expect(HIOPOSInputFormatException.class);
        presenter.obtenerNumeroConsecutivoFinal("","",0);
    }

    @Test
    public void obtenerContrasena() throws Exception {
        String date = "02-15-2019";
        String idEmisor = String.format(Locale.getDefault(), "%012d", Long.parseLong("207610083"));
        String voucher = "3";
        String consecutive = "00200040000000011";
        String result = "506150219000207610083002000400000000113";
        String actual = presenter.obtenerContrasena(date,idEmisor,voucher,consecutive);
        assertEquals(result,actual.substring(0,39));
        assertEquals(8,actual.substring(39,47).length());

        thrown.expect(HIOPOSInputFormatException.class);
        date = "02-2019";
        presenter.obtenerContrasena(date,idEmisor,voucher,consecutive);
    }

    @Test
    public void verificarComprobanteExistente() throws NoSuchFieldException, IllegalAccessException {
        Comprobante voucher = new Comprobante();
        voucher.setHiopos_id("987654321-123456789");
        voucher.setAmbiente(false);

        Field privateField = HIOPOSPresenter.class.
                getDeclaredField("configuracion");

        privateField.setAccessible(true);

        privateField.set(presenter, config);

        Mockito.when(databaseManagerMock.getVoucher(false,"987654321","123456789")).thenReturn(voucher);
        Mockito.when(config.isProduccion_activo()).thenReturn(false);
        assertTrue("Fail search voucher",presenter.verificarComprobanteExistente("987654321","123456789"));
    }

    @Test
    public void obtenerDocumentoMH() throws Exception {

        assertEquals(new FacturaElectronica().toString().substring(0,59),presenter.obtenerDocumentoMH(Constants.MH_INVOICE).toString().substring(0,59));
        assertEquals(new NotaDebitoElectronica().toString().substring(0,62),presenter.obtenerDocumentoMH(Constants.MH_DEBIT_NOTE).toString().substring(0,62));
        assertEquals(new NotaCreditoElectronica().toString().substring(0,63),presenter.obtenerDocumentoMH(Constants.MH_CREDIT_NOTE).toString().substring(0,63));
        assertEquals(new TiqueteHacienda().toString().substring(0,56),presenter.obtenerDocumentoMH(Constants.MH_TICKET).toString().substring(0,56));
        //de o a 41 contando el ultimo .

        thrown.expect(HIOPOSInputFormatException.class);
        presenter.obtenerDocumentoMH("0");
    }

    @Test
    public void obtenerInformacionDelReceptor() throws Exception {

        EmisorReceptor receiver= new EmisorReceptor();
        Map<String,String> customer = new HashMap<>();
        customer.put("Name","Diego");
        customer.put("FiscalIdDocType","04");
        customer.put("FiscalId","01-201745528");
        customer.put("State","San José, San José");
        customer.put("City","Carmen, Carmen");
        customer.put("Address","100 Metros norte del colegio");
        customer.put("Phone","86882145");
        customer.put("Email","diego@gmail.com");

        receiver.setNombre("Diego");
        Identificacion identification = new Identificacion();
        identification.setTipo("01");
        identification.setNumero("201745528");
        receiver.setIdentificacion(identification);
        Ubicacion ubication = new Ubicacion();
        ubication.setProvincia("1");//San José
        ubication.setCanton("01");//San José
        ubication.setDistrito("01");//Carmen
        ubication.setBarrio("01");//Amón
        ubication.setOtrasSenas("100 Metros norte del colegio");
        receiver.setUbicacion(ubication);
        Telefono cel = new Telefono();
        cel.setCodigoPais("506");
        cel.setNumTelefono("86882145");
        receiver.setTelefono(cel);
        receiver.setFax(cel);
        receiver.setCorreoElectronico("diego@gmail.com");

        Provincia provincie = new Provincia();
        provincie.setNombre("San José");
        provincie.setCodigo("1");
        Canton canton = new Canton();
        canton.setNombre("San José");
        canton.setCodigo("01");
        Distrito district = new Distrito();
        district.setNombre("Carmen");
        district.setCodigo("01");

        Mockito.when(databaseManagerMock.getProvince("San José")).thenReturn(provincie);
        Mockito.when(databaseManagerMock.getCanton(canton.getNombre(),provincie.getId())).thenReturn(canton);
        Mockito.when(databaseManagerMock.getDistrict(district.getNombre(), canton.getId())).thenReturn(district);

        EmisorReceptor actual = presenter.obtenerInformacionDelReceptor(customer);
        assertNotNull(presenter.obtenerInformacionDelReceptor(customer));
        assertEquals(receiver.getUbicacion().getProvincia(),actual.getUbicacion().getProvincia());
        assertEquals(receiver.getUbicacion().getCanton(),actual.getUbicacion().getCanton());
        assertEquals(receiver.getUbicacion().getDistrito(),actual.getUbicacion().getDistrito());
        assertEquals(receiver.getTelefono().getNumTelefono(),actual.getTelefono().getNumTelefono());
        assertEquals(receiver.getIdentificacion().getNumero(),actual.getIdentificacion().getNumero());

        thrown.expect(HIOPOSInputFormatException.class);
        customer.put("FiscalId","201745528");
        presenter.obtenerInformacionDelReceptor(customer);

    }

    @Test public void obtenerInformacionId() throws Exception {
        String idInformation = "01-201745528";

        String[] expected = {"01","201745528"};
        assertNotNull(presenter.obtenerInformacionId(idInformation));
        assertThat(expected,is(presenter.obtenerInformacionId(idInformation)));

        idInformation = "201745528";
        thrown.expect(HIOPOSInputFormatException.class);
        presenter.obtenerInformacionId(idInformation);

        idInformation = "08-201745528";
        presenter.obtenerInformacionId(idInformation);
    }

    @Test
    public void validarIdInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(2);
        sb.append(0);
        sb.append(1);
        sb.append(7);
        sb.append(4);
        sb.append(5);
        sb.append(5);
        sb.append(2);
        sb.append(8);

        assertTrue(presenter.validarIdInfo(sb,9));
    }

    @Test
    public void obtenerReceptorIDDeHIOPOS(){
        Identificacion receptorIdentificacion = new Identificacion();
        receptorIdentificacion.setTipo("01");
        receptorIdentificacion.setNumero("607850054");

        Identificacion actual = presenter.obtenerReceptorIDDeHIOPOS("01","607850054");
        assertEquals("They are not the same",receptorIdentificacion.toString().substring(0,55),actual.toString().substring(0,55));
        receptorIdentificacion.setNumero("107550054");
        assertNotEquals("Same Identifications",receptorIdentificacion.getNumero(),actual.getNumero());
    }

    @Test
    public void obtenerReceptorUbicacionDeHIOPOS(){
        String state ="San José, San José";
        String city ="Carmen, Carmen";
        String address ="100 Metros norte del colegio";
        Provincia provincie = new Provincia();
        provincie.setNombre("San José");
        provincie.setCodigo("00");

        Mockito.when(databaseManagerMock.getProvince("San José")).thenReturn(provincie);

        Ubicacion receptorUbicacion = presenter.obtenerReceptorUbicacionDeHIOPOS(state,city,address);
        assertNotNull(receptorUbicacion);

        assertEquals(provincie.getCodigo(),receptorUbicacion.getProvincia());
    }

    @Test
    public void obtenerReceptorTelefonoDeHIOPOS(){
        String phoneInfo = "86996532";

        Telefono receptorTelefono = new Telefono();
        receptorTelefono.setCodigoPais("506");
        receptorTelefono.setNumTelefono("86996532");

        assertEquals(receptorTelefono.getNumTelefono(),presenter.obtenerReceptorTelefonoDeHIOPOS(phoneInfo).getNumTelefono());
    }

    @Test
    public void obtenerCodigoPorLinea(){
        String idProduct = "product_01";
        CodigoComercial codOneLine = new CodigoComercial();
        codOneLine.setCodigo(idProduct);

        CodigoComercial actual = presenter.obtenerCodigoPorLinea(idProduct);
        assertEquals(codOneLine.toString().substring(0,47),actual.toString().substring(0,47));
        assertEquals(codOneLine.getCodigo(),actual.getCodigo());
    }

    @Test
    public void parseStringToNumbers() throws Exception {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        String input = "1,5";
        double num =  1.5d;
        assertEquals(num,presenter.transformarTextoEnNumero(input).doubleValue(),0.0d);
        assertEquals(num,presenter.transformarTextoEnNumeroDecimal(input).doubleValue(),0.0d);

        input = "1,0";
        num = 1.0d;
        assertEquals(num,presenter.transformarTextoEnNumero(input).doubleValue(),0.0d);
        assertEquals(num,presenter.transformarTextoEnNumeroDecimal(input).doubleValue(),0.0d);

        input = "1";
        num = 1d;
        assertEquals(num,presenter.transformarTextoEnNumero(input).doubleValue(),0.0d);
        assertEquals(num,presenter.transformarTextoEnNumeroDecimal(input).doubleValue(),0.0d);
    }

    @Test
    public void vectorSum(){
        double[] first = {1.5,2.6,4.5,9.0};
        double[] second = {4.1,1.2,7.4,7.5};

        double[] sum = {5.6,3.8,11.9,16.5};

        double[] actual = presenter.vectorSum(first,second);
        assertThat("They are not same",sum,is(actual));
        double[] newfirst = {1.5,2.6,4.5};
        assertNull("Both objects have same length",presenter.vectorSum(newfirst,second));
    }

    @Test
    public void procesarLinea() throws Exception{
        HioposSale venta = crearHioposVenta();
        Date fecha = presenter.formatearFecha("06-17-2019","00:08:00 pm","MM-dd-yyyy HH:mm:ss");
        String consecutivo = "00200010000000011";
        String password = presenter.obtenerContrasena("06-17-2019","112770761","3",consecutivo);


        Field privateField = HIOPOSPresenter.class.
                getDeclaredField("configuracion");

        privateField.setAccessible(true);

        privateField.set(presenter, config);
        Mockito.when(config.getActividad_economica()).thenReturn("11101");

        String raw = presenter.generarComprobanteElectronico("01",consecutivo, fecha,password,venta);

        assertFalse(raw!= null && raw.isEmpty() );

        presenter.generarComprobanteYEncolarEnBaseDeDatos("000","000",raw,"01",fecha);
    }

    @Test
    public void obtenerImpuestosPorLinea() throws Exception{
        ArrayList<LineTaxField> lines = new ArrayList<>();
        LineTaxField lineTax = new LineTaxField();

        Hashtable<String,String> lineTaxes = new Hashtable<>();
        lineTaxes.put("Percentage","13");

        CustomTaxFields customTaxField = new CustomTaxFields();
        Hashtable<String,String> customTaxes = new Hashtable<>();
        customTaxes.put(Constants.CODIGO_IMPUESTO,"01");
        customTaxes.put(Constants.TARIFA,"13");
        customTaxes.put(Constants.CODIGO_TARIFA,"08");

        customTaxField.setCustomTaxFields((Map<String,String>)customTaxes);

        lineTax.setLineTaxFields((Map<String,String>)lineTaxes);
        lineTax.setCustomTaxFields(customTaxField);

        lines.add(lineTax);

        ArrayList<Impuesto> impuestosEsperados = new ArrayList<>();
        Impuesto impuestoEsperado = new Impuesto();
        impuestoEsperado.setCodigoTarifa("08");
        impuestoEsperado.setTarifa("13.00");
        impuestoEsperado.setMonto("130.00000");
        impuestoEsperado.setCodigo("01");
        impuestosEsperados.add(impuestoEsperado);

        ArrayList<Impuesto> impuestosObtenidos = presenter.obtenerImpuestosPorLinea(lines,1000d,0d,"1000");

        assertEquals(impuestosEsperados.size(),impuestosObtenidos.size());
        for(int i = 0; i < impuestosEsperados.size();++i){
            assertEquals(impuestosEsperados.get(i),impuestosObtenidos.get(i));
        }
    }

    @Test
    public void calcularMontoImpuesto(){
        assertEquals(130,presenter.calcularMontoImpuesto(1000d,13d,Constants.IMPUESTO_AL_VALOR_AGREGADO),0);
    }

    @Test
    public void obtenerValorModificadores() throws Exception {

        Map<String,String> modifier = new HashMap<>();
        modifier.put("Units","500");
        modifier.put("BaseAmount","100");
        modifier.put("DiscountAmount","200");

        double qtyModifier = 500;
        double baseAmount = 100;

        double discountAmount = 0d;
        String discount = modifier.get("DiscountAmount");
        if ( discount != null) {
            discountAmount = 200;
        }
        double[] values = new double[4];
        values[0] = discountAmount;
        values[1] = baseAmount;
        values[2] = (baseAmount / qtyModifier) + (discountAmount / qtyModifier);
        values[3] = ((baseAmount / qtyModifier) + (discountAmount / qtyModifier)) * qtyModifier;

        assertThat(values,is(presenter.obtenerValorModificadores(modifier)));
    }

    @Test
    public void procesarCargoPorServicio() throws Exception {

    }

    @Test
    public void procesarRedondeo() throws Exception {

    }

    @Test
    public void testformatearFecha() throws Exception{

        Date date = presenter.formatearFecha("03-05-2019","3:18:21","MM-dd-yyyy HH:mm:ss");

        String format = presenter.formatearFecha(date,"yyyy-MM-dd'T'HH:mm:ss");

        assertEquals("2019-03-05T03:18:21",format);
    }

    @Test
    public void testValidarEmail()throws Exception{
        assertNotNull(presenter.validarEmail("test@test.com"));

        thrown.expect(HIOPOSInputFormatException.class);
        presenter.validarEmail("");

        thrown.expect(HIOPOSInputFormatException.class);
        presenter.validarEmail("test.com");

        thrown.expect(HIOPOSInputFormatException.class);
        presenter.validarEmail("\\dfs@test.com");

        thrown.expect(HIOPOSInputFormatException.class);
        presenter.validarEmail("#test@.com");

        thrown.expect(HIOPOSInputFormatException.class);
        presenter.validarEmail("test@.com");

        thrown.expect(HIOPOSInputFormatException.class);
        presenter.validarEmail("paúltest@gmail.com");
    }

    @Test
    public void testValidarTelefono() throws Exception{
        assertNotNull( presenter.validarTelefono("+506 88896885"));
        assertNotNull( presenter.validarTelefono("+(506) 88896885"));
        assertNotNull( presenter.validarTelefono("+506 8889-6885"));
        thrown.expect(HIOPOSInputFormatException.class);
        assertNotNull( presenter.validarTelefono("506 8889-68-859"));
    }

    @Test
    public void testSerializeHIOPOSDoc() throws Exception{
        String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Header><HeaderField Key=\"PosId\">139</HeaderField><HeaderField Key=\"Alias\"/><HeaderField Key=\"SaleId\">7640f458-b06a-43a2-a7a3-54dcb76c51ad</HeaderField><HeaderField Key=\"Serie\">T0002116</HeaderField><HeaderField Key=\"Number\">1</HeaderField><HeaderField Key=\"DocumentTypeId\">1</HeaderField><HeaderField Key=\"Z\">1</HeaderField><HeaderField Key=\"Date\">06-18-2019</HeaderField><HeaderField Key=\"Time\">16:50:03</HeaderField><HeaderField Key=\"IsTaxIncluded\">true</HeaderField><HeaderField Key=\"IsClosed\">false</HeaderField><HeaderField Key=\"IsSubTotalized\">false</HeaderField><HeaderField Key=\"TaxesAmount\">288.0000</HeaderField><HeaderField Key=\"NetAmount\">2720.0000</HeaderField><HeaderField Key=\"ServiceTypeId\">0</HeaderField><HeaderField Key=\"LoyaltyCardNumber\"/><HeaderField Key=\"PrintCount\">0</HeaderField><HeaderField Key=\"BlockToPrint\">null</HeaderField><HeaderField Key=\"TicketToPrint\"/><HeaderField Key=\"Rounding\">-1.0000</HeaderField><HeaderField Key=\"TaxExemption\"/><HeaderField Key=\"IsoDocumentId\"/><HeaderField Key=\"PriceListId\">1</HeaderField><HeaderField Key=\"CurrencyISOCode\">CRC</HeaderField><HeaderField Key=\"CurrencyExchangeRate\">1.0000</HeaderField><CustomDocHeaderFields/><CustomSerieFields/><Discount><DiscountField Key=\"DiscountReasonId\">0</DiscountField><DiscountField Key=\"Percentage\">0.0000</DiscountField><DiscountField Key=\"Amount\">0.0000</DiscountField><DiscountField Key=\"AmountWithTaxes\">0.0000</DiscountField></Discount><ServiceCharge><ServiceChargeField Key=\"Percentage\">10.0000</ServiceChargeField><ServiceChargeField Key=\"Amount\">221.0000</ServiceChargeField><ServiceChargeField Key=\"AmountWithTaxes\">221.0000</ServiceChargeField></ServiceCharge><Shop><ShopField Key=\"ShopId\">2</ShopField><ShopField Key=\"FiscalId\">1-1077-8888</ShopField><ShopField Key=\"Name\">DEMO APP FISCAL 1</ShopField><ShopField Key=\"Address\">De Pops 300 Sur 150 Este</ShopField><ShopField Key=\"PostalCode\">50201</ShopField><ShopField Key=\"City\">Curridabat / Central</ShopField><ShopField Key=\"State\">San Jose</ShopField><ShopField Key=\"Phone\">+506 4404-1585</ShopField><ShopField Key=\"Email\">demoappfiscal</ShopField></Shop><Seller><SellerField Key=\"SellerId\">4</SellerField><SellerField Key=\"ContactType\">1</SellerField><SellerField Key=\"Gender\">0</SellerField><SellerField Key=\"FiscalIdDocType\">0</SellerField><SellerField Key=\"FiscalId\"/><SellerField Key=\"Name\">Administrador 1</SellerField><SellerField Key=\"Address\"/><SellerField Key=\"PostalCode\"/><SellerField Key=\"City\">San Jose</SellerField><SellerField Key=\"State\"/><SellerField Key=\"Phone\"/><SellerField Key=\"Email\">admin</SellerField></Seller><Customer><CustomerField Key=\"customerId\">0</CustomerField></Customer><Provider><ProviderField Key=\"providerId\">null</ProviderField></Provider></Header><Lines><Line><LineField Key=\"LineId\">c9f77808-4948-44af-a5d0-a74c9b811497</LineField><LineField Key=\"LineNumber\">1</LineField><LineField Key=\"ProductId\">956</LineField><LineField Key=\"ProductSizeId\">1116</LineField><LineField Key=\"ExternalProductId\">0</LineField><LineField Key=\"Name\">Normal</LineField><LineField Key=\"Size\"/><LineField Key=\"Units\">1.0</LineField><LineField Key=\"IsMenu\">false</LineField><LineField Key=\"IsGift\">false</LineField><LineField Key=\"PriceListId\">1</LineField><LineField Key=\"Price\">2500.0000</LineField><LineField Key=\"SellerId\">4</LineField><LineField Key=\"WarehouseId\">2</LineField><LineField Key=\"DiscountReasonId\">0</LineField><LineField Key=\"DiscountPercentage\">0.0000</LineField><LineField Key=\"DiscountAmount\">0.0000</LineField><LineField Key=\"BaseAmount\">2212.3894</LineField><LineField Key=\"TaxesAmount\">287.6106</LineField><LineField Key=\"NetAmount\">2500.0000</LineField><LineField Key=\"Price\">2500.0000</LineField><CustomProductFields><CustomProductField Key=\"ProductId\"/><CustomProductField Key=\"Version\"/><CustomProductField Key=\"PartidaArancelaria\"/><CustomProductField Key=\"ActividadEconomica\"/><CustomProductField Key=\"TipodeArticulo\"/><CustomProductField Key=\"CodTipoArticulo\"/><CustomProductField Key=\"FactordeImpuesto\">00000.0000</CustomProductField><CustomProductField Key=\"DevuelveIVA\"/><CustomProductField Key=\"PorcentajeIVADevuelto\">000.00</CustomProductField><CustomProductField Key=\"OtrosCargos\">False</CustomProductField><CustomProductField Key=\"TipoOtrosCargos\"/><CustomProductField Key=\"CodArticuloHacienda\"/></CustomProductFields><CustomDocLineFields/><LineTaxes><LineTax><LineTaxField Key=\"TaxId\">2</LineTaxField><LineTaxField Key=\"Position\">1</LineTaxField><LineTaxField Key=\"Percentage\">13.0000</LineTaxField><CustomDocLineTaxFields/><CustomTaxFields/></LineTax></LineTaxes><CustomDocLineSummaryFields/></Line></Lines><Taxes><Tax><TaxField Key=\"TaxId\">2</TaxField><TaxField Key=\"Description\">IMPUESTO DE VENTA</TaxField><TaxField Key=\"LineNumber\">1</TaxField><TaxField Key=\"TaxBase\">2212.0000</TaxField><TaxField Key=\"Percentage\">13.0</TaxField><TaxField Key=\"TaxAmount\">288.0000</TaxField><TaxField Key=\"FiscalId\"/><TaxField Key=\"ExemptReason\"/><CustomDocTaxFields/><CustomTaxFields/></Tax><Tax><TaxField Key=\"TaxId\">4</TaxField><TaxField Key=\"Description\">0%</TaxField><TaxField Key=\"LineNumber\">2</TaxField><TaxField Key=\"TaxBase\">221.0000</TaxField><TaxField Key=\"Percentage\">0.0</TaxField><TaxField Key=\"TaxAmount\">0.0000</TaxField><TaxField Key=\"FiscalId\"/><TaxField Key=\"ExemptReason\"/><CustomDocTaxFields/><CustomTaxFields/></Tax></Taxes><PaymentMeans><PaymentMean><PaymentMeanField Key=\"PaymentMeanId\">1</PaymentMeanField><PaymentMeanField Key=\"Description\">Efectivo</PaymentMeanField><PaymentMeanField Key=\"Type\">0</PaymentMeanField><PaymentMeanField Key=\"LineNumber\">1</PaymentMeanField><PaymentMeanField Key=\"PaymenMeanName\">Efectivo</PaymentMeanField><PaymentMeanField Key=\"Amount\">2720.0000</PaymentMeanField><PaymentMeanField Key=\"CurrencyISOCode\">CRC</PaymentMeanField><PaymentMeanField Key=\"CurrencyExchangeRate\">1.0000</PaymentMeanField><PaymentMeanField Key=\"TransactionId\"/><PaymentMeanField Key=\"AuthorizationId\"/><CustomPaymentMeanFields/><CustomPaymentMeanFields/><Currency><CurrencyField Key=\"Name\">Colón</CurrencyField><CurrencyField Key=\"Initials\">¢</CurrencyField><CurrencyField Key=\"InitialsBefore\">true</CurrencyField><CurrencyField Key=\"DecimalCount\">0</CurrencyField><CurrencyField Key=\"IsoCode\">CRC</CurrencyField><CustomCurrencyFields/></Currency><CurrencyField Key=\"CurrencyId\">1</CurrencyField></PaymentMean></PaymentMeans><AdditionalFields><AdditionalField Key=\"5000009\">Administrador 1</AdditionalField><AdditionalField Key=\"5000010\">DEMO APP FISCAL 1</AdditionalField><AdditionalField Key=\"5000011\">HIOPOS</AdditionalField><AdditionalField Key=\"5000012\">1-1077-8888</AdditionalField><AdditionalField Key=\"5000013\">De Pops 300 Sur 150 Este</AdditionalField><AdditionalField Key=\"5000014\">Curridabat / Central</AdditionalField><AdditionalField Key=\"5000015\">50201</AdditionalField><AdditionalField Key=\"5000016\">demoappfiscal</AdditionalField><AdditionalField Key=\"5000017\">+506 4404-1585</AdditionalField></AdditionalFields></Document>";

        HioposSale venta = presenter.serializarDocumentoHIOPOS(input);


        assertNotNull(venta);

        Date fecha = presenter.formatearFecha("06-17-2019","00:08:00 pm","MM-dd-yyyy HH:mm:ss");
        String consecutivo = "00200010000000011";
        String password = presenter.obtenerContrasena("06-17-2019","112770761","3",consecutivo);

        Field privateField = HIOPOSPresenter.class.
                getDeclaredField("configuracion");

        privateField.setAccessible(true);

        privateField.set(presenter, config);
        Mockito.when(config.getActividad_economica()).thenReturn("11101");

        String raw = presenter.generarComprobanteElectronico("01",consecutivo, fecha,password,venta);

        assertFalse(raw!= null && raw.isEmpty() );
    }
}
