package com.seaguest.game;

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
	int size;
	boolean allowundo = false;

	Spinner levelSpinner;
	Spinner sizeSpinners;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle(R.string.setting);
		setContentView(R.layout.setting);

		okBtn = (Button) findViewById(R.id.ok);
		allowUndoBtn = (Button) findViewById(R.id.allowUndoBtn);
		levelSpinner = (Spinner) findViewById(R.id.level);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.Level, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		levelSpinner.setAdapter(adapter);

		sizeSpinners = (Spinner) findViewById(R.id.size);
		ArrayAdapter<CharSequence> sizeadapter = ArrayAdapter.createFromResource(this,
				R.array.Size, android.R.layout.simple_spinner_item);
		sizeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sizeSpinners.setAdapter(sizeadapter);

		okBtn.setOnClickListener(this);
		allowUndoBtn.setOnClickListener(this);
		levelSpinner.setOnItemSelectedListener(this);
		sizeSpinners.setOnItemSelectedListener(this);

		Intent intent = getIntent();
		level = intent.getIntExtra("level", 1);
		size = intent.getIntExtra("size", 13);
		allowundo = intent.getBooleanExtra("allowundo", false);
		setValues();
	}

	public void setValues(){
		sizeSpinners.setSelection((size-7)/2);
		levelSpinner.setSelection(level - 1);
		if(allowundo){
			allowUndoBtn.setText("Yes");
		}else{
			allowUndoBtn.setText("No");
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if(view.getId() == R.id.size){
			size = position*2 + 7;
		}else if(view.getId() == R.id.level){
			level = position+1;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		level = 1;
		size = 13;
	}

	public  void checkValues(){
		level = levelSpinner.getSelectedItemPosition()+1;
		size = sizeSpinners.getSelectedItemPosition()*2 + 7;
		allowundo = allowUndoBtn.getText().equals("Yes");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ok:
				checkValues();
				resultIntent = new Intent();
				resultIntent.putExtra("level", level);
				resultIntent.putExtra("size", size);
				resultIntent.putExtra("allowundo", allowundo);
				setResult(RESULT_OK, resultIntent);
				finish();
				break;
			case R.id.allowUndoBtn:
				if(allowUndoBtn.getText().equals("Yes")){
					allowUndoBtn.setText("No");
				}else if(allowUndoBtn.getText().equals("No")){
					allowUndoBtn.setText("Yes");
				}
				allowundo = allowUndoBtn.getText().equals("Yes");
				break;
		}

	}
}
