package fiscalapi.icg.es.apifiscal.Utils;

public final class Constants {
    public static final String DB_NAME = "apifiscal-db";
    public static final String BASE_URL = "http://hioposfiscalapi.azurewebsites.net/api/";
    public static final String BASE_URL_MDG = "http://api-hiopos.mdg-cloud.com/Facturacion/";
    public static final String SEND_VOUCHER_MDG = "api/v2/Emitir";
    public static final String EMIT_VOUCHER_HEADER = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String GRANT_TYPE = "password";


    //HIOPOS INTENTS
    public static final String SALE = "icg.actions.fiscalPrinter.SALE";
    public static final String VOID_SALE = "icg.actions.fiscalPrinter.VOID_SALE";
    public static final String GET_VERSION = "icg.actions.fiscalPrinter.GET_VERSION";
    public static final String GET_BEHAVIOR = "icg.actions.fiscalPrinter.GET_BEHAVIOR";
    public static final String PRINT_SALE = "icg.actions.fiscalPrinter.PRINT_COPY";

    //HIOPOS DOCUMENT TYPES
    public static final int HIOPOS_TICKET = 1;
    public static final int HIOPOS_INVOICE = 2;
    public static final int HIOPOS_CREDIT_TICKET = 3;
    public static final int HIOPOS_CREDIT_INVOICE = 4;
    public static final int HIOPOS_PURCHASE = 5;
    public static final int HIOPOS_NON_PRINTABLE_TICKET = 6;
    public static final int HIOPOS_INVITATION = 7;

    //HACIENDA DOCUMENT TYPES
    public static final String MH_INVOICE = "01";
    public static final String MH_DEBIT_NOTE = "02";
    public static final String MH_CREDIT_NOTE = "03";
    public static final String MH_TICKET = "04";

    public static final String RESOLUTION = "Autorizada mediante resolución No DGT-R-033-2019 del 20 de junio del 2019";
    //public static final String RESOLUTION_CODE = "DGT-R-48-2016";
    //public static final String RESOLUTION_DATE = "07-10-2016 08:00:00";
    public static final String DEFAULT_HEADQUARTERS = "001";//JVB  21/04/2022 SE CAMBIA EL HEADQUARTERS DE 1 A 2 PARA PROYECTO PARQUE VIVA SE DEBE ANALIZAR PORQUE ESTABA EN 2.


    public static final int UNSENDED_STATE = 0;
    public static final int SENDED_STATE = 1;
    public static final int ERROR_STATE = 2;

    public static final String DISCOUNT_REASON = "Descuento de usuario Hiopos";

    //MDG XML TAGS
    public static final String INVOCE_TAG = "<FacturaElectronica";
    public static final String DEBIT_NOTE_TAG = "<NotaDebitoElectronica";
    public static final String CREDIT_NOTE_TAG = "<NotaCreditoElectronica";
    public static final String TICKET_TAG = "<TiqueteHacienda";

    public static final String INVOCE_TAG_END = "</FacturaElectronica";
    public static final String DEBIT_NOTE_TAG_END = "</NotaDebitoElectronica";
    public static final String CREDIT_NOTE_TAG_END = "</NotaCreditoElectronica";
    public static final String TICKET_TAG_END = "</TiqueteHacienda";

    //SERVICE
    public static final int MAX_TRIES_BEFORE_END_SERVICE_FOR_NO_VOUCHERS = 20;
    public static final int MAX_TRIES_BEFORE_END_SERVICE_FOR_FAIL_RESPONSES = 20;

    //ID_TYPES
    public static final String CEDULA_FISCA = "01";
    public static final String CEDULA_JURIDICA = "02";
    public static final String CEDULA_DIMEX = "03";
    public static final String CEDULA_NITE = "04";
    public static final String CEDULA_EXTRANJERA = "05";

    //Payment means HIOPOS
    public static final int EFECTIVO_HIOPOS = 1;
    public static final int TARJETA_HIOPOS = 2;
    public static final int DATAFONO_HIOPOS = 15;
    public static final int DATAFONO_BAC_HIOPOS = 4;
    public static final int DATAFONO_EXTERNO_HIOPOS = 16;
    public static final int TRANSFERENCIA_HIOPOS = 19;

    //Payment means MH
    public static final String EFECTIVO_MH = "01";
    public static final String TARJETA_MH = "02";
    public static final String CHEQUE_MH = "03";
    public static final String TRASNFERENCIA_MH = "04";
    public static final String TERCEROS_MH = "05";
    public static final String OTROS_MH = "99";

    //Taxes Types
    public static final String IMPUESTO_AL_VALOR_AGREGADO = "01";
    public static final String IMPUESTO_SELECTIVO_CONSUMO = "02";
    public static final String IMPUESTO_UNICO_A_LOS_COMBUSTIBLES = "03";
    public static final String IMPUESTO_ESPECIFICO_DE_BEBIDAS_ALCOHOLICAS = "04";
    public static final String IMPUESTO_ESPECIFICO_DE_BEBIDAS_NO_ALCOHOLICAS_JABONES_TOCADOR= "05";
    public static final String IMPUESTO_TABACO = "06";
    public static final String IVA_CALCULO_ESPECIAL = "07";
    public static final String IVA_REGIMEN_BIENES_USADOS = "08";
    public static final String IMPUESTO_CEMENTEO = "12";
    public static final String OTROS_IMPUESTOS = "99";

    //Rates types
    public static final String TARIFA_0 = "01";
    public static final String TARIFA_RED_1 = "02";
    public static final String TARIFA_RED_2 = "03";
    public static final String TARIFA_RED_4 = "04";
    public static final String TRANSITORIO_0 = "05";
    public static final String TRANSITORIO_4 = "06";
    public static final String TRANSITORIO_8 = "07";
    public static final String TRANSITORIO_13 = "08";


    //ERRORS
    public static final String EMPTY_SALE_INTENT = "La petición de HIOPOS Cloud viene vacía";
    public static final String VOUCHER_ALREADY_SENDED = "Un comprobante con la misma identificación ya esta en cola esperando ser enviado";
    public static final String NO_HEADERS = "No se consiguió obtener los encabezados del documento enviado por HIOPOS Cloud";
    public static final String NO_DOC_TYPE = "No se pudo obtener el tipo de documento enviado por HIOPOS Cloud";
    public static final String EMPTY_BASIC_INFO = "No se pudo obtener la fecha, la serie o el número de HIOPOS CLOUD";
    public static final String WRONG_DATE_FORMAT = "La fecha esta en formato incorrecto";
    public static final String NULL_POS_ID = "No se puede obtener el PosId desde HIOPOS Cloud";
    public static final String WRONG_XML_FORMAT = "El formato del xml recibido de HIOPOS Cloud es incorrecto";
    public static final String UNABLE_CONSECUTIVE_NUMBER = "No fue posible generar el número de consecutivo";
    public static final String UNSUPPORTED_HIOPOS_DOC_TYPE = "El tipo de documento recibido de HIOPOS es inválido";
    public static final String UNABLE_TO_CREATE_PASSWORD = "No se pudo crear la contraseña";
    public static final String UNABLE_TO_GET_CUSTOMER = "No se pudo obtener la información del cliente";
    public static final String EMPTY_LINES = "No se puede procesar una factura sin líneas";
    public static final String CREDIT_DEBIT_NOTE_INCOMPLETED = "La nota de crédito o débito no cumple con la información mínima requerida";
    public static final String UNSUPPORTED_MH_DOC_TYPE = "Tipo de documento del Ministerio de Hacienda inválido";
    public static final String UNABLE_GET_CUSTOMER_INFO = "No se pudo asignar la mínima información requerida para el cliente";
    public static final String UNABLE_PAYMENT_MEANS = "No se pudieron asignar los medios de pago";
    public static final String UNABLE_GET_LINE_FIELDS = "No se pudo obtener los detalles de las líneas para la factura";
    public static final String UNABLE_SET_PRICE = "No fue posible obtener el precio unitario ni el total de la venta";
    public static final String UNABLE_LINE_DETAILS = "No fue posible extraer los datos necesarios para generar la línea";
    public static final String UNABLE_LINE_TAX = "No fue posible agregar los impuestos correspondientes a la línea";
    public static final String UNABLE_TO_GET_TAX_RATE = "No fue posible obtener el porcentaje de impuestos a aplicar";
    public static final String UNABLE_PROCESS_MODIFIERS = "No fue posible procesar los modificadores de la línea";
    public static final String UNABLE_PROCESS_SERVICE_CHARGE = "No fue posible procesar el cargo por servicio";
    public static final String UNABLE_PROCESS_ROUNDING = "No fue posible procesar el redondeo";
    public static final String INVALID_EMAIL_FORMAT = "El formato del correo electrónico es incorrecto";
    public static final String INVALID_PHONE_FORMAT = "El formato del número de teléfono es incorrecto";
    public static final String INCOMPLETE_CURRENCY_INFO = "La información sobre el tipo de moneda esta incompleta";
    public static final String UNKNOWN_CURRENCY_INFO = "La información sobre el tipo de moneda no corresponde con un tipo de moneda aceptado por el Ministerio de Hacienda";
    public static final String CUSTOMER_ID_ERROR = "Problema con el número de identificación del cliente:\n Formato esperado: <código tipo>-<número de identificación>\n Tipos de cédula:\n Tipo: Cédula Física     Código tipo: 01     Extención: 9 dígitos.\n Tipo: Cédula Jurídica     Código tipo: 02     Extención: 10 dígitos.\n Tipo: DIMEX     Código tipo: 03     Extención: 12 dígitos.\n Tipo: NITE     Código tipo: 04\nNo use giones ni espacios para separar los dígitos del número de identificación";
    public static final String MAX_OTHER_CHARGES_MAXLIMIT = "Problema con otros cargos:\n Máximo de 15 elementos sobrepasado";
    public static final String MAX_OTHER_CHARGES_MISSING_ID = "Problema con otros cargos:\n Con el tipo \"04\" se requiere el ID de la persona teceros";
    public static final String TIPO_OTROS_CARGOS_IS_NULL = "El tipo de otros cargos es obligatorio y debe corresponder con un valor de la tabla \"Tipo de documento\"";
    public static final String UNABLE_TO_GET_TAX_RATE_CODE = "No es posible extraer un código de impuesto valido. Asegurece de que el producto tenga el campo \"MdhCodigoImp\" configurado correctamente";
    public static final String UNABLE_TO_GET_EXONERATION_INFO = "El usuario no tiene asignados todos los datos necesarios para procesar la exoneración";

    //Custom fields section
    public static final String PARTIDA_ARANCELARIA = "PartidaArancelaria";
    public static final String TIPO_DE_ARTICULO = "TipodeArticulo";
    public static final String CODIGO_TIPO_DE_ARTICULO = "CodArticuloHacienda";
    public static final String ES_OTROS_CARGOS = "OtrosCargos";
    public static final String TIPO_OTROS_CARGOS = "TipoOtrosCargos";
    public static final String DEVUELVE_IVA = "DevuelveIVA";
    public static final String PORCENTAJE_IVA_DEVUELTO = "PorcentajeIVADevuelto";
    public static final String CODIGO_IMPUESTO = "CodigoImpuesto";
    public static final String CODIGO_TARIFA = "CodigoTarifa";
    public static final String TARIFA = "Tarifa";
    public static final String FACTOR_IVA = "FactorImpuesto";
    public static final String TIPO_EXONERACION = "TipoExoneracion";
    public static final String NUMERO_DOC_EXONERACION = "NumeroDocumento";
    public static final String NOMBRE_INSTITUCION_EXONERACION = "NombreInstitucion";
    public static final String FECHA_EMISION_EXONERACION = "FechaEmision";

    public static final String TIPO_MERCANCIA = "mercancia";
    public static final String TIPO_SERVICIO = "servicio";

    //tipos de optros cargos
    public static final String CONTRIBUCION_PARAFISCAL = "01";
    public static final String COBRO_DE_UN_TERCERO = "04";
    public static final String COSTOS_DE_EXPORTACION = "05";
    public static final String IMPUESTO_DE_SERVICIO = "06";
    public static final String OTROS_CARGOS_OTROS_CARGOS = "99";

    public static final String UNIDAD_MEDIDA_UNIDADES = "Unid";
    public static final String UNIDAD_MEDIDA_SERVICIOS = "Sp";

    public static final String EMAIL_REGULAR_EXPRESION = "\\s*[a-zA-z0-9]+([-+.'][a-zA-z0-9]+)*@[a-zA-z0-9]+([-][a-zA-z0-9]+)*\\.[a-zA-z0-9]+([-.][a-zA-z0-9]+)*\\s*";
    public static final String PHONE_REGULAR_EXPRESION = "(\\+?((\\(\\d{1,4}\\))|(\\d{1,4}))\\s)?(\\d{8}|(\\d{4}-\\d{4})|(\\d{4}-\\d{2}-\\d{2}))";
}
