package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud;

import java.util.List;

import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Sign.SignXMLRequest;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Sign.SignXMLResponse;
import fiscalapi.icg.es.apifiscal.Data.Models.ConsecutivoComprobante;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HIOPOSCloudService {

    @POST("XadesSigner")
    Call<SignXMLResponse> signXML(@Body SignXMLRequest request);

    @GET("FiscalAPI/GetConsecutivosEmisor")
    Call<List<ConsecutivoComprobante>> getConsecutiveOfSender(@Query("identificacionEmisor") String identificacionEmisor);
}
