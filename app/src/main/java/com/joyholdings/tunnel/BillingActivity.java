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

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;

public class BillingActivity extends Activity implements BillingProcessor.IBillingHandler {
 
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      //setContentView(R.layout.activity_main);
  
      bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkmMaZqgRWRWhfLcK5mHunr4sLcPHZ8gngtjgnb7prqBFf/5WA2EIzeUPAGV6Z1aWpAD3ILivbk6PCbzIfNnyx6Jym/ldpunMsn+ZoUge+rsl6zmvXTmA/ZY++U3n/HRJi0ccxIhH+1a3juqGnjJublWCoN+jSOqvMWuaQoaW0l86wGuxxz26Uipm1/cjLivjpXMUdALT4t4WiBrVItcxSym8O/oael+7LKwIwik5kv8uBpweCZ+AkZpmbSYEXMxTZNIJKeGGQg5xewCb1ynr/gGJsAMQGejsRM0CRGNUR9aQ+/BYpwQMkq2+8aQTWnotn9hIjLcCLVaRR05UO2nDxwIDAQAB", this);
      bp.initialize();
      // or bp = BillingProcessor.newBillingProcessor(this, "YOUR LICENSE KEY FROM GOOGLE PLAY CONSOLE HERE", this);
      // See below on why this is a useful alternative
    }
      
    // IBillingHandler implementation
      
    @Override
    public void onBillingInitialized() {
      /*
      * Called when BillingProcessor was initialized and it's ready to purchase 
      
      */
      //bp.consumePurchase("life_pack9");
      bp.purchase(this,"life_pack9");
     
      
    }
      
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
      /*
      * Called when requested PRODUCT ID was successfully purchased
      */
      bp.consumePurchase("life_pack9");
      this.finish();
    }
      
    @Override
    public void onBillingError(int errorCode, Throwable error) {
      /*
      * Called when some error occurred. See Constants class for more details
      * 
      * Note - this includes handling the case where the user canceled the buy dialog:
      * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
      */
    }
      
    @Override
    public void onPurchaseHistoryRestored() {
      /*
      * Called when purchase history was restored and the list of all owned PRODUCT ID's 
      * was loaded from Google Play
      */
    }


static {
    System.loadLibrary("game");
}

}