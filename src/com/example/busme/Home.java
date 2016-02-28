package com.example.busme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

public class Home extends Activity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		ImageButton bn=(ImageButton)findViewById(R.id.login_btn);
		bn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			AddRouteClicked();	
			}
		});
		
	}
	
	public void AddRouteClicked()
	{

		Intent go=new Intent(Home.this, Login.class);

		startActivity(go);
		
	}

}
