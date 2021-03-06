package br.ipti.org.apptag.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import br.ipti.org.apptag.models.FrequencyClassStudentReturn;
import br.ipti.org.apptag.models.FrequencyReturn;
import br.ipti.org.apptag.models.GradeReturn;
import br.ipti.org.apptag.models.LoginReturn;
import br.ipti.org.apptag.models.SchoolReportReturn;
import br.ipti.org.apptag.models.UserInfoReturn;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Filipi Andrade on 04-Jul-17.
 */

public class TAGAPI {

    private static final String BASE_URL = "https://api-tag.ipti.org.br/api-tag/";
    private static TAGInterfaceAPI mTagInterfaceAPI;

    public static TAGInterfaceAPI getClient() {
        try {
            if (mTagInterfaceAPI == null) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                return chain.proceed(chain.request());
                            }
                        }).build();

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                mTagInterfaceAPI = retrofit.create(TAGInterfaceAPI.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mTagInterfaceAPI;
    }

    public interface TAGInterfaceAPI {
        // --------------- LOGIN ------------------ //
        @FormUrlEncoded
        @POST("parent/login")
        Call<ArrayList<LoginReturn>> getLogin(@Field("username") String username,
                                              @Field("password") String password);

        // --------------- USERS ------------------ //
        @GET("user/info/{username}")
        Call<ArrayList<UserInfoReturn>> getUserInfo(@Path("username") String username);

        // --------------- STUDENT ------------------ //
        @GET("student/parent/{responsable_cpf}")
        Call<ArrayList<SchoolReportReturn>> getStudentParent(@Path("responsable_cpf") String responsable_cpf);

        // --------------- GRADE ------------------ //
        @GET("grade/{enrollment_fk}/{classroom_fk}")
        Call<ArrayList<GradeReturn>> getGrade(@Path("enrollment_fk") String enrollment_fk,
                                              @Path("classroom_fk") String classroom_fk);

        // --------------- FREQUENCY ------------------ //
        @GET("frequency/{student_fk}/{classroom_fk}/{month}")
        Call<ArrayList<FrequencyClassStudentReturn>> getFrequency(@Path("student_fk") String student_fk,
                                                                  @Path("classroom_fk") String classroom_fk,
                                                                  @Path("month") String month);
    }
}
