package com.gorest.userinfo;

import com.gorest.testbase.TestBase;
import com.gorest.usersteps.UserSteps;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class GorestCrudTest extends TestBase {

        static String name = "Rohit Sharma" + TestUtils.getRandomValue();
        static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
        static String gender = "female";
        static String status = "active";
        static int userId;

        @Steps
        UserSteps userSteps;

        @Title("This will create new user")
        @Test
        public void test001() {
            ValidatableResponse response = userSteps.createUser(name, email, gender, status);
            response.log().all().statusCode(201);
        }

        @Title("Verify if the user was added to the application")
        @Test
        public void test002() {
            HashMap<String, Object> studentMap = userSteps.getUserInfoByName(name);
            Assert.assertThat(studentMap, hasValue(name));
            userId = (int) studentMap.get("id");

        }

        @Title("Update the user information and verify the updated information")
        @Test
        public void test003() {
            name = name + "_updated";
            ValidatableResponse response = userSteps.updateUser(userId, name, email, gender, status);
            response.log().all().statusCode(200);

            HashMap<String, Object> studentMap = userSteps.getUserInfoByName(name);
            Assert.assertThat(studentMap, hasValue(name));


        }

        @Title("Delete the product and verify if the product is deleted!")
        @Test
        public void test004() {
            userSteps.deleteStore(userId).statusCode(204);
            userSteps.getStoreById(userId).statusCode(404);

        }

    }

