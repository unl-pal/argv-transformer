/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\���η�ɳ-��׿������Ŀ\\���η�ɳ-���ص�\\Android��Դ\\[��׿��Դ]ImiFirewall\\imiFirewall\\src\\com\\android\\internal\\telephony\\ITelephony.aidl
 */
package com.android.internal.telephony;
/* * Copyright (C) 2007 The Android Open Source Project
* * Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* * [url=http://www.apache.org/licenses/LICENSE-2.0]http://www.apache.org/licenses/LICENSE-2.0[/url]
* * Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*//**
* Interface used to interact with the phone. Mostly this is used by the
* TelephonyManager class. A few places are still using this directly.
* Please clean them up if possible and use TelephonyManager insteadl.
* * {@hide}
*/
public interface ITelephony extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ITelephony
{
private static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ITelephony";
private static class Proxy implements com.android.internal.telephony.ITelephony
{
private android.os.IBinder mRemote;
}
static final int TRANSACTION_endCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_isIdle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_isOffhook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_isRisinging = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_answerRingingCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_cancelMissedCallsNotification = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
}
