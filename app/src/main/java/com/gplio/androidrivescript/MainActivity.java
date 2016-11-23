package com.gplio.androidrivescript;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RiveScriptEngine rive;
    private TextView tvConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rive = RiveScriptEngine.startEngine("testbrain","rive");

        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        final EditText etSubmit = (EditText) findViewById(R.id.et_submit);
        tvConversation = (TextView) findViewById(R.id.tv_conversation);

        etSubmit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN) {
                    ask(String.valueOf(etSubmit.getText()));
                    etSubmit.setText("");
                    return true;
                }
                return false;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ask(String.valueOf(etSubmit.getText()));
                etSubmit.setText("");
            }
        });
    }

    private void ask(String question){
        if (question == null || question.isEmpty()) return;
        String reply = rive.reply(question);
        if (reply != null && !reply.isEmpty()){
            tvConversation.setText(tvConversation.getText() + "\n\n"+ question + "\n\t" + reply);
        }
    }
}
