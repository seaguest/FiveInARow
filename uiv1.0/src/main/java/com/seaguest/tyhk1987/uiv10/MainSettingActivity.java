package com.seaguest.tyhk1987.uiv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainSettingActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
	public Button okBtn;
	public Button allowUndoBtn;

	Intent resultIntent;
	//返回的值必需有
	int level;
	boolean allowUndo = false;

	Spinner spinner;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle(R.string.setting);
		setContentView(R.layout.setting);

		okBtn = (Button) findViewById(R.id.ok);
		allowUndoBtn = (Button) findViewById(R.id.allowUndoBtn);
		spinner = (Spinner) findViewById(R.id.level);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.Level, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		okBtn.setOnClickListener(this);
		allowUndoBtn.setOnClickListener(this);
		spinner.setOnItemSelectedListener(this);
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		level = position+1;
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx index:" + position);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		level = 0;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ok:
				resultIntent = new Intent();
				resultIntent.putExtra("level", level);
				resultIntent.putExtra("allowundo", allowUndo);
				setResult(RESULT_OK, resultIntent);
				finish();
				break;
			case R.id.allowUndoBtn:
				if(allowUndoBtn.getText().equals("Yes")){
					allowUndoBtn.setText("No");
				}else if(allowUndoBtn.getText().equals("No")){
					allowUndoBtn.setText("Yes");
				}
				allowUndo = allowUndoBtn.getText().equals("Yes");
				break;
		}

	}
}
