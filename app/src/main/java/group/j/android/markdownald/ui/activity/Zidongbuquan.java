package group.j.android.markdownald.ui.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Zidongbuquan {

    private ArrayList<String> al = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText et;
    private TextView tv;
    private Context c;
    private Spinner sp;
    private int preLength = 0;

    public Zidongbuquan(EditText et){
        this.et = et;
    }

    public Zidongbuquan(EditText et, TextView tv, Spinner sp, Context c){
        this.et = et;
        this.tv = tv;
        this.sp = sp;
        this.c = c;
    }

    public void run(){
        initial();
        addMyTextListener();
    }

    private void addSpinner(){
        adapter = new ArrayAdapter(c,android.R.layout.simple_spinner_dropdown_item,al);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                addChar(et, parent.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
    }


    private void deletTextListener(){
        et.addTextChangedListener(new TextWatcher() {
            int length = 0;
            int prelength = 0;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                length = et.getText().length();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prelength = et.getText().length();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (length>prelength) {
                    et.removeTextChangedListener(this);
                    addMyTextListener();
                }
            }
        });
    }

    private void addMyTextListener(){
        et.addTextChangedListener(new TextWatcher() {
            String ss = "";
            boolean run = false;
            int length = 0;
            int prelength = 0;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et.getText().equals("")) {
                    run = false;
                } else {
                    ss = et.getText().toString();
                    if(al.contains(ss)){
                        run = true;
                    }else if(ss.startsWith("***")&&ss.charAt(3)!='*'&&!ss.endsWith("***")) {
                        run = true;
                    }else if(ss.startsWith("**")&&ss.charAt(2)!='*'&&!ss.endsWith("**")){
                        run = true;
                    }else if(ss.startsWith("*")&&ss.charAt(1)!='*'&&!ss.endsWith("*")){
                        run = true;
                    }
                    length = et.getText().length();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prelength = et.getText().length();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (prelength>length){
                    et.removeTextChangedListener(this);
                    deletTextListener();
                }
                if (run) {
                    et.removeTextChangedListener(this);
                    et.append("*");
                    et.setSelection(length / 2 + 1);
                    addMyTextListener();
                }
            }
        });
    }

    private void initial(){
        al.add("*****");
        al.add("***");
        al.add("*");
    }

    public ArrayList<String> getAl(){
        return al;
    }

}