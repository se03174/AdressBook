package com.example.adressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<item> item_list;
    Button add, del;
    EditText ed_name, ed_phonenumber, ed_email;
    ListView lv;
    myCustomAdapter av;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //추기,제거
        add = (Button) findViewById(R.id.add);
        del = (Button) findViewById(R.id.del);
       // 이름,번호,이메일
        ed_name = (EditText) findViewById(R.id.list_name);
        ed_phonenumber = (EditText) findViewById(R.id.list_number);
        ed_email = (EditText) findViewById(R.id.list_email);
        //빈 데이터 리스트 생성
        item_list = new ArrayList<item>();
        // onCreate에서 Adapter 생성
        av = new myCustomAdapter(this, R.layout.listlayout, item_list);
        //ca = new myCustomAdapter(this, android.R.layout.simple_list_item_multiple_choice, item_list);
        //리스트 뷰 참조 및 Adapter 설정
        lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(av);
        add.setOnClickListener(this);
        del.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add: //추가 버튼 누를때
                item item = new item(ed_name.getText().toString(), ed_phonenumber.getText().toString(), ed_email.getText().toString());
                item_list.add(item);
                av.notifyDataSetChanged();
                ed_name.setText("");
                ed_phonenumber.setText("");
                ed_email.setText("");
                break;
            case R.id.del: // 제거 버튼 누를때
                item_list.remove(item_list.size()-1);
                av.notifyDataSetChanged();


                break;
        }
    }

    public class myCustomAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        int layout;
        ArrayList<item> adresslist =  new ArrayList<item>(); // adapter에 추가된 데이터를 저장하기 위한 배열리스트


        public myCustomAdapter(Context context, int layout, ArrayList<item> alist) {
            this.context = context;
            this.layout = layout;
            this.adresslist = alist;
            inflater = LayoutInflater.from(context);
        }


        //BaseAdapter 상속 받으려면 BaseAdapter 내에 정의된 추상메서드를 구현해야함
        ////Adapter에 사용되는 데이터 갯수를 리턴함
        @Override
        public int getCount() { //리스트 객체 내 item 갯수 반환
            return adresslist.size();
        }

        //지정한 position 위치에 해당하는 리스트 객체의 item을 객체 형태로 반환
        @Override
        public Object getItem(int position) {
            return adresslist.get(position);
        }

        //지정한 position 위치에 해당하는 리스트 객체의 item rowID를 반환해주는 함수
        @Override
        public long getItemId(int position) {
            return position;
        }

        // position에 위치한 데이터를 화면에 출력하는데 사용될 view를 리턴.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) { //해당되는 항목의 adapter에서 위치값, 재사용할 항목의 view, 항목의 view들을 포함하고 있는 ListView
            final int pos= position;
            final Context context = parent.getContext();

            //convertview 참조 획득
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //convertView = inflater.inflate((XmlPullParser) alist, parent, false);
                convertView = inflater.inflate(layout, parent, false);
            }
            // 화면에 표시될 View(Layout)이 inflate된)로부터 위젯에 대한 참조 획득
            TextView tv_item_name = (TextView) convertView.findViewById(R.id.list_name);
            TextView tv_item_phonenumber = (TextView) convertView.findViewById(R.id.list_number);
            TextView tv_item_email = convertView.findViewById(R.id.list_email);
            LinearLayout item_layout = (LinearLayout) convertView.findViewById(R.id.list_layout);

            //Data set(item.java)에서 position에 위치한 데이터 참조 획득
            tv_item_name.setText(adresslist.get(position).getName());
            tv_item_email.setText(adresslist.get(position).getEmail());
            tv_item_phonenumber.setText(adresslist.get(position).getPhoneNum());




            return convertView;
        }
    }
}

