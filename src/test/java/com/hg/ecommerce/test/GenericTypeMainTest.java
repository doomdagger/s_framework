package com.hg.ecommerce.test;

import com.hg.ecommerce.model.SysSetting;
import com.hg.ecommerce.websocket.support.SocketEventListener;

public class GenericTypeMainTest {
	public static void main(String[] args){
		
		SocketEventListener<SysSetting> listener = new SocketEventListener<SysSetting>() {

			@Override
			public void actionPerformed(SysSetting message) {
				System.out.println("hapiness");
			}
		};
		
		
		
	}
}
