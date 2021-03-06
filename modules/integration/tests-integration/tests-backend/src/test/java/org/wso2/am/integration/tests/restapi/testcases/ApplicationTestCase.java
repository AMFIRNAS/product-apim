/*
*Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.am.integration.tests.restapi.testcases;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.wso2.am.integration.test.utils.base.APIMIntegrationBaseTest;
import org.wso2.am.integration.tests.restapi.RESTAPITestConstants;
import org.wso2.am.integration.tests.restapi.utils.RESTAPITestUtil;
import org.wso2.carbon.automation.engine.annotations.ExecutionEnvironment;
import org.wso2.carbon.automation.engine.annotations.SetEnvironment;
import org.wso2.carbon.automation.engine.context.TestUserMode;

import java.io.File;

import static org.testng.Assert.assertTrue;

@SetEnvironment(executionEnvironments = {ExecutionEnvironment.STANDALONE})
public class ApplicationTestCase extends APIMIntegrationBaseTest {

    @Factory(dataProvider = "userModeDataProvider")
    public ApplicationTestCase(TestUserMode userMode) {
        this.userMode = userMode;
    }

    @DataProvider
    public static Object[][] userModeDataProvider() {
        return new Object[][]{
                new Object[]{TestUserMode.SUPER_TENANT_ADMIN}
        };
    }

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        super.init(userMode);
    }

    @Test(groups = {"wso2.am"}, description = "REST API Implementation test : Application handling test case")
    public void testApplications() {

        String gatewayURL = getGatewayURLNhttp();
        String keyManagerURL = getKeyManagerURLHttp();

        //file name of the JSON data file related to : Application handling test case
        String dataFileName = "ApplicationTestCase.txt";
        String dataFilePath = (new File(System.getProperty("user.dir"))).getParent() +
                RESTAPITestConstants.PATH_SUBSTRING + dataFileName;
        boolean testSuccessStatus = new RESTAPITestUtil().testRestAPI(dataFilePath, gatewayURL, keyManagerURL);
        assertTrue(testSuccessStatus);
    }

    // NOTE: Uncomment this test after upgrading the carbon-apimgt version. Test will pass after the upgrade.
    // Because currently the product build fails if the carbon-version is upgraded due to dependency issues caused by
    // (Removing Open Tracing dependencies) b839e5c7583f1546a05ba3023418eb168a2eff78
    // @Test(groups = {"wso2.am"}, description = "REST API Implementation test : Add application passing groupId and check " +
    //        "whether the it is honored")
    public void testAddApplicationWithGroupId() {

        String gatewayURL = getGatewayURLNhttp();
        String keyManagerURL = getKeyManagerURLHttp();

        //file name of the JSON data file related to : Application handling test case
        String dataFileName = "AddApplicationWithGroupIDTestCase.txt";
        String dataFilePath = (new File(System.getProperty("user.dir"))).getParent() +
                RESTAPITestConstants.PATH_SUBSTRING + dataFileName;
        boolean testSuccessStatus = new RESTAPITestUtil().testRestAPI(dataFilePath, gatewayURL, keyManagerURL);
        assertTrue(testSuccessStatus);
    }

    @AfterClass(alwaysRun = true)
    public void destroy() throws Exception {
        super.cleanUp();
    }

}
