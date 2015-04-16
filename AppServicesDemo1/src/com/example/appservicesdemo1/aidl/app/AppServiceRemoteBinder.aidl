package com.example.appservicesdemo1.aidl.app;
interface AppServiceRemoteBinder {
	
	void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,double aDouble, String aString);
	void setData(String data);
}