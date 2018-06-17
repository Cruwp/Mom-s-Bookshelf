package momsbookshelf.cruwpstudio.fr;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {
    @GET("api/books")
    Call<ResponseBody> listRepos(@Query("bibkeys") String isbn, @Query("format") String json, @Query("jscmd") String data);
}
