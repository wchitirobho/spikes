/**
 * Copyright 2010-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazonaws.cognito.sync.demo;

import android.content.Context;
import android.util.Log;
import com.amazonaws.auth.AWSAbstractCognitoIdentityProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.regions.Regions;
import java.util.HashMap;
import java.util.Map;

public class Cognito {

    private static final String TAG = "CognitoSyncClient";

    /**
     * Enter here the Identity Pool associated with your app and the AWS
     * region where it belongs. Get this information from the AWS console.
     */

    private static final String IDENTITY_POOL_ID = BuildConfig.IDENTITY_POOL;
    private static final Regions REGION = Regions.fromName(BuildConfig.REGION);

    private static CognitoSyncManager syncManager;
    private static CognitoCachingCredentialsProvider credentialsProvider = null;

    /**
     * Initializes the Cognito Identity and Sync clients. This must be called before getInstance().
     * 
     * @param context a context of the app
     */
    public static void init(Context context) {
        if (syncManager != null) return;
        syncManager = createSyncManager(context);
    }

    private static CognitoSyncManager createSyncManager(Context context) {
        AWSAbstractCognitoIdentityProvider identityProvider = new ServerCognitoIdentityProvider(
                null, IDENTITY_POOL_ID, context, REGION);
        credentialsProvider = new CognitoCachingCredentialsProvider(context, identityProvider, REGION);
        Log.i(TAG, "Using developer authenticated identities");

        return new CognitoSyncManager(context, REGION, credentialsProvider);
    }

    /**
     * Sets the login so that you can use authorized identity. This requires a
     * network request, so you should call it in a background thread.
     * 
     * @param providerName the name of the external identity provider
     * @param token openId token
     */
    public static void addLogins(String providerName, String token) {
        if (syncManager == null) {
            throw new IllegalStateException("CognitoSyncClientManager not initialized yet");
        }

        Map<String, String> logins = credentialsProvider.getLogins();
        if (logins == null) {
            logins = new HashMap<>();
        }
        logins.put(providerName, token);
        credentialsProvider.setLogins(logins);
    }

    /**
     * Gets the singleton instance of the CognitoClient. init() must be called
     * prior to this.
     * 
     * @return an instance of CognitoClient
     */
    public static CognitoSyncManager getSyncManager() {
        if (syncManager == null) {
            throw new IllegalStateException("CognitoSyncClientManager not initialized yet");
        }
        return syncManager;
    }

    public static CognitoCachingCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }
}