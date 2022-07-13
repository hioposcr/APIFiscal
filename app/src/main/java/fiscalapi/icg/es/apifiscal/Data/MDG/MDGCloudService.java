package fiscalapi.icg.es.apifiscal.Data.MDG;

import fiscalapi.icg.es.apifiscal.Data.MDG.Models.ApiEmisionRequest;
import fiscalapi.icg.es.apifiscal.Data.MDG.Models.ApiEmisionResponse;
import fiscalapi.icg.es.apifiscal.Data.MDG.Models.ValidateCredentialsResponse;
import fiscalapi.icg.es.apifiscal.Utils.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MDGCloudService {

    @POST("token")
    @FormUrlEncoded
    Call<ValidateCredentialsResponse> validateCredentials(@Field("grant_type") String grant_type, @Field("username") String username,@Field("password") String password);

    //@Headers("Content-Type: application/json")
    @POST(Constants.SEND_VOUCHER_MDG)
    Call<ApiEmisionResponse> emitVoucher(@Body ApiEmisionRequest request, @Header(Constants.EMIT_VOUCHER_HEADER) String header);
}
