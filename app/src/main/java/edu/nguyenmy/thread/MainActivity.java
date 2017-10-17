package edu.nguyenmy.thread;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtn1, mBtn2;
    private TextView mTv1, mTv2;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mBtn1 = (Button)findViewById(R.id.btn1);
        mBtn2 = (Button)findViewById(R.id.btn2);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String values = (String)msg.obj;
            mTv1.setText(values);
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = handler.obtainMessage();
                        message.obj = "Hello!";
                        handler.sendMessage(message);
                    }
                });
                thread.start();

                break;
            case R.id.btn2:
                CountTask countTask = new CountTask();
                countTask.execute();
                break;
            default:
                break;
        }

    }

    public class CountTask extends AsyncTask<Void, Integer, Void>{

        //ham duoc goi ngay sau ham onPreExecute()
        //ham nay thuc hien cac tac vu chay ngam va gui ket qua den onProgressUpdate de hien thi
        @Override
        protected Void doInBackground(Void... voids) {
            for (int i=0; i<=100; i++){
                SystemClock.sleep(1000);
                publishProgress(i);
            }
            return null;
        }

        //ham duoc goi dau tien khi khoi tao tien trinh
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //hien thi ket qua nhan duoc tu doInBackground gui xuong
        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
            int number = values[0];
            mProgressBar.setProgress(number);
            mTv2.setText(number + "%");

        }
        //ham duoc goi khi tien trinh ket thuc
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
