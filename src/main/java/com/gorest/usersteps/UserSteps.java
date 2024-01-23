package com.gorest.usersteps;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class UserSteps {
    @Step("Creating user with name:{0},email:{1},gender:{2},status:{3}")
    public ValidatableResponse createUser(String name, String email, String gender, String status) {
        UserPojo userPojo = UserPojo.getUserPojo(name, email, gender, status);
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .contentType(ContentType.JSON)
                .body(userPojo)
                .when()
                .post(EndPoints.CREATE_USERS)
                .then();
    }

    @Step("Getting the user information with name: {0}")
    public HashMap<String, Object> getUserInfoByName(String name) {

        String p1 = "findAll{it.name == '";
        String p2 = "'}.get(0)";

        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .when()
                .get(EndPoints.GET_ALL_USER)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);
    }

    @Step("Update user with name:{0},email:{1},gender:{2},status:{3}")
    public ValidatableResponse updateUser(int id, String name, String email,String gender, String status) {

        UserPojo userPojo = UserPojo.getUserPojo(name, email,gender,status);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .pathParam("id", id)
                .body(userPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }

    @Step("Deleting store information with id: {0}")
    public ValidatableResponse deleteStore(int id) {
        return SerenityRest
                .given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }

    @Step("Getting student information with studentId: {0}")
    public ValidatableResponse getStoreById(int id) {
        return SerenityRest
                .given()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_USER_BY_ID)
                .then();
    }
}
