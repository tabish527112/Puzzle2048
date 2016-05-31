package com.mtk.puzzle2048;

import java.util.Random;


import android.R.color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

public class Main extends Activity implements android.view.View.OnClickListener{
	
	int[][] matrix;
    int[][] undomatrix;
    boolean undoconstraint;
    int score=0;
	SharedPreferences someData;
	public static String filename="MySharedString";
	String highscore="0";
    TextView field00,field01,field02,field03,field10,field11,field12,field13,field20,field21,field22,field23,field30,field31,field32,field33;
    TextView tvundo,tvrestart,tvscore,tvhighscore;
    Button up,down,left,right;
    SoundPool sp;
	int soundint=0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		sp=new SoundPool(5,AudioManager.STREAM_MUSIC,0);
		soundint=sp.load(this, R.raw.tick, 1);
		init();
		paintit();
		tvundo.setOnClickListener(this);
		tvrestart.setOnClickListener(this);
		up.setOnClickListener(this);
		down.setOnClickListener(this);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Editor ed=someData.edit();
		ed.putString("sharedString", highscore);
		ed.commit();
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		someData=getSharedPreferences(filename,0);
		highscore=someData.getString("sharedString", "0");
		init();
		paintit();
	}
	

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tvundo:
		if(undoconstraint){
			for(int i=0;i<=3;i++){
                for(int j=0;j<=3;j++)
                    matrix[i][j]=undomatrix[i][j];
				}
			undoconstraint=false;
			paintit();
			Toast t1=Toast.makeText(getApplicationContext(), "No Undo moves left", Toast.LENGTH_SHORT);
			t1.show();
			}
			break;
		case R.id.tvrestart:
			 restart();
			 break;
		case R.id.bUp:
			slide(1);
			break;
		case R.id.bDown:
			slide(3);
			break;
		case R.id.bLeft:
			slide(4);
			break;
		case R.id.bRight:
			slide(2);
			break;
		}
		sp.play(soundint, 1, 1, 0, 0, 1);
	}
	
	public void init() {
		matrix=new int[4][4];
		undomatrix=new int[4][4];
		generate();
		generate();
		field00=(TextView)findViewById(R.id.field00);
		field01=(TextView)findViewById(R.id.field01);
		field02=(TextView)findViewById(R.id.field02);
		field03=(TextView)findViewById(R.id.field03);
		field10=(TextView)findViewById(R.id.field10);
		field11=(TextView)findViewById(R.id.field11);
		field12=(TextView)findViewById(R.id.field12);
		field13=(TextView)findViewById(R.id.field13);
		field20=(TextView)findViewById(R.id.field20);
		field21=(TextView)findViewById(R.id.field21);
		field22=(TextView)findViewById(R.id.field22);
		field23=(TextView)findViewById(R.id.field23);
		field30=(TextView)findViewById(R.id.field30);
		field31=(TextView)findViewById(R.id.field31);
		field32=(TextView)findViewById(R.id.field32);
		field33=(TextView)findViewById(R.id.field33);
		
		tvundo=(TextView)findViewById(R.id.tvundo);
		tvrestart=(TextView)findViewById(R.id.tvrestart);
		tvscore=(TextView)findViewById(R.id.tvscore);
		tvhighscore=(TextView)findViewById(R.id.tvhighscore);
		
		up=(Button)findViewById(R.id.bUp);
		down=(Button)findViewById(R.id.bDown);
		left=(Button)findViewById(R.id.bLeft);
		right=(Button)findViewById(R.id.bRight);
		
		tvscore.setText("SCORE\n"+score);
		//String dump=readFromFile();
		someData=getSharedPreferences(filename,0);
		highscore=someData.getString("sharedString", "0");
		tvhighscore.setText("BEST\n"+highscore);
		
	}
	
	public void paintit(){
		if(matrix[0][0]!=0){
			field00.setText(""+matrix[0][0]);
			field00.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field00.setText("");
			field00.setBackgroundColor(Color.GRAY);
		}
		if(matrix[0][1]!=0){
			field01.setText(""+matrix[0][1]);
			field01.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field01.setText("");
			field01.setBackgroundColor(Color.GRAY);
		}
		if(matrix[0][2]!=0){
			field02.setText(""+matrix[0][2]);
			field02.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field02.setText("");
			field02.setBackgroundColor(Color.GRAY);
		}
		if(matrix[0][3]!=0){
			field03.setText(""+matrix[0][3]);
			field03.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field03.setText("");
			field03.setBackgroundColor(Color.GRAY);
		}
		//-----------------------------
		if(matrix[1][0]!=0){
			field10.setText(""+matrix[1][0]);
			field10.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field10.setText("");
			field10.setBackgroundColor(Color.GRAY);
		}
		if(matrix[1][1]!=0){
			field11.setText(""+matrix[1][1]);
			field11.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field11.setText("");
			field11.setBackgroundColor(Color.GRAY);
		}
		if(matrix[1][2]!=0){
			field12.setText(""+matrix[1][2]);
			field12.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field12.setText("");
			field12.setBackgroundColor(Color.GRAY);
		}
		if(matrix[1][3]!=0){
			field13.setText(""+matrix[1][3]);
			field13.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field13.setText("");
			field13.setBackgroundColor(Color.GRAY);
		}
		//--------------------------------
		if(matrix[2][0]!=0){
			field20.setText(""+matrix[2][0]);
			field20.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field20.setText("");
			field20.setBackgroundColor(Color.GRAY);
		}
		if(matrix[2][1]!=0){
			field21.setText(""+matrix[2][1]);
			field21.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field21.setText("");
			field21.setBackgroundColor(Color.GRAY);
		}
		if(matrix[2][2]!=0){
			field22.setText(""+matrix[2][2]);
			field22.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field22.setText("");
			field22.setBackgroundColor(Color.GRAY);
		}
		if(matrix[2][3]!=0){
			field23.setText(""+matrix[2][3]);
			field23.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field23.setText("");
			field23.setBackgroundColor(Color.GRAY);
		}
		//--------------------------------
		if(matrix[3][0]!=0){
			field30.setText(""+matrix[3][0]);
			field30.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field30.setText("");
			field30.setBackgroundColor(Color.GRAY);
		}
		if(matrix[3][1]!=0){
			field31.setText(""+matrix[3][1]);
			field31.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field31.setText("");
			field31.setBackgroundColor(Color.GRAY);
		}
		if(matrix[3][2]!=0){
			field32.setText(""+matrix[3][2]);
			field32.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field32.setText("");
			field32.setBackgroundColor(Color.GRAY);
		}
		if(matrix[3][3]!=0){
			field33.setText(""+matrix[3][3]);
			field33.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else{
			field33.setText("");
			field33.setBackgroundColor(Color.GRAY);
		}
		tvscore.setText("SCORE\n"+score);
	}
	
	public void checkgameover(){
        for(int i=0;i<=3;i++){
            for(int j=0;j<=3;j++){
                if(matrix[i][j]==0)
                    return;
            }
        }
        Editor ed=someData.edit();
		ed.putString("sharedString", highscore);
		ed.commit();
        Toast t=Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT);
        t.show();
        restart();
    }

	public void restart(){
		for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                matrix[i][j]=0;
        score=0;
        undoconstraint=true;
        generate();
        generate();
        paintit();
        Toast t2=Toast.makeText(getApplicationContext(), "Game Restarted", Toast.LENGTH_SHORT);
        t2.show();
	}
	
	 public void generate(){
	        Random ran=new Random();
	        while(true){
	            int a=ran.nextInt(4);
	            int b=ran.nextInt(4);
	            if(matrix[a][b]==0){
	                matrix[a][b]=(ran.nextInt(2)+1)*2;
	                break;
	            }
	        }
	    }
	 
	 public int[] logic(int[] arr) throws ArrayIndexOutOfBoundsException{
	        int i,j;
	        for(i=3;i>0;i--){
	            if(arr[i]==0){
	                for(j=i;j>0;j--){
	                    arr[j]=arr[j-1];
	                }
	                arr[0]=0;
	            }
	        }
	        for(i=3;i>0;i--){
	            if(arr[i]==arr[i-1]){
	                arr[i]*=2;
	                score+=arr[i];
	                for(j=i-1;j>0;j--){
	                    arr[j]=arr[j-1];
	                }
	                arr[0]=0;
	            }
	        }
	        for(i=3;i>0;i--){
	            if(arr[i]==0&&arr[i-1]!=0)
	                arr=logic(arr);
	        }
	        int a=Integer.parseInt(highscore);
	        if(score>a){
	        	highscore=""+score;
	        	tvhighscore.setText("BEST\n"+highscore);
	        }
	        return arr;
	    }
	 
	 public void slide(int dir){
	        int[] arr=new int[4];
	        int i,j,k;
	        for(i=0;i<=3;i++){
	                    for(j=0;j<=3;j++)
	                        undomatrix[i][j]=matrix[i][j];
	        }
	        switch(dir){
	            case 1:
	            {
	                for(i=3;i>=0;i--){
	                    for(j=3,k=0;j>=0;j--,k++)
	                        arr[k]=matrix[j][i];
	                    arr=logic(arr);
	                    for(j=3,k=0;j>=0;j--,k++)
	                        matrix[j][i]=arr[k];
	                }
	                break;
	            }
	            case 2:
	            {
	                for(i=0;i<=3;i++){
	                    for(j=0;j<=3;j++)
	                        arr[j]=matrix[i][j];
	                    arr=logic(arr);
	                    for(j=0;j<=3;j++)
	                        matrix[i][j]=arr[j];
	                }
	                break;
	            }
	            case 3:
	            {
	                for(i=0;i<=3;i++){
	                        for(j=0;j<=3;j++)
	                            arr[j]=matrix[j][i];
	                        arr=logic(arr);
	                        for(j=0;j<=3;j++)
	                            matrix[j][i]=arr[j];
	                    }
	                break;
	            }
	            case 4:
	            {
	                for(i=3;i>=0;i--){
	                    for(j=3,k=0;j>=0;j--,k++)
	                        arr[k]=matrix[i][j];
	                    arr=logic(arr);
	                    for(j=3,k=0;j>=0;j--,k++)
	                        matrix[i][j]=arr[k];
	                }
	            }
	        }
	        checkgameover();
	        generate();
	        paintit();
	    }

}
