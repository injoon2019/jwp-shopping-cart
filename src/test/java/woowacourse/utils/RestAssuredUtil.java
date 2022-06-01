package woowacourse.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class RestAssuredUtil {
    public static ExtractableResponse<Response> httpPost(String path, Object object) {
        return RestAssured.
                given().log().all()
                .contentType(ContentType.JSON)
                .body(object)
                .when().post(path)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> deleteWithToken(String path, String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .when().delete(path)
                .then().log().all().extract();
    }
}
