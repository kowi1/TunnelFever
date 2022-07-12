/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.joyholdings.tunnel;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingClient.BillingResponseCode;
import java.util.List;
import java.util.ArrayList;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.android.billingclient.api.BillingClient.SkuType;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import android.content.Context;

public class BillingActivity extends Activity {

    private PurchasesUpdatedListener purchasesUpdatedListener;
    private BillingClient billingClient;
    private Context activityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
     //setContentView(R.layout.activity_main);    
    purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
       public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
            // To be implemented in a later section.
            if (billingResult.getResponseCode() == BillingResponseCode.OK
        && purchases != null) {
        for (Purchase purchase : purchases) {
            handlePurchase(purchase);
        }
    } else if (billingResult.getResponseCode() == BillingResponseCode.USER_CANCELED) {
        // Handle an error caused by a user cancelling the purchase flow.
        UpdateLife(0);
        Activity billingActivity=(Activity)activityContext;
                    billingActivity.finish();
    } else {
        // Handle any other error codes.
       UpdateLife(0);
        Activity billingActivity=(Activity)activityContext;
                    billingActivity.finish();
    }

        }
    };
    activityContext=this;
    billingClient = BillingClient.newBuilder(this)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build();
         
     billingClient.startConnection(new BillingClientStateListener() {
        @Override
        public void onBillingSetupFinished(BillingResult billingResult) {
            if (billingResult.getResponseCode() ==  BillingResponseCode.OK) {
                // The BillingClient is ready. You can query purchases here.

                List<String> skuList = new ArrayList<> ();
                skuList.add("life_pack9");
                //skuList.add("gas");
                SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                params.setSkusList(skuList).setType(SkuType.INAPP);
                billingClient.querySkuDetailsAsync(params.build(),
                    new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult,
                                List<SkuDetails> skuDetailsList) {
                            // Process the result.
                        if(skuDetailsList.get(0)!=null){
                            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                            .setSkuDetails(skuDetailsList.get(0))
                            .build();
                        int responseCode = billingClient.launchBillingFlow((Activity)activityContext, billingFlowParams).getResponseCode();
                        
                                }
                            }
                    });
            }
        }
        @Override
        public void onBillingServiceDisconnected() {
            // Try to restart the connection on the next request to
            // Google Play by calling the startConnection() method.
        }
    });     
   finish();
 }
      
    void handlePurchase(Purchase purchase) {
        // Purchase retrieved from BillingClient#queryPurchases or your PurchasesUpdatedListener.
       // Purchase purchase = ...;
    
        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.
    
        ConsumeParams consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();
    
        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                    UpdateLife(5);
                    Activity billingActivity=(Activity)activityContext;
                    billingActivity.finish();
                }
            }
        };
                billingClient.consumeAsync(consumeParams, listener);
    }
    public native String  UpdateLife(int life_nos);

    static {
        System.loadLibrary("game");
    }
}