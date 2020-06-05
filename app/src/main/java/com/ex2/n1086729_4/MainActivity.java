package com.ex2.n1086729_4;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btn;
    ListView toolNumber;
    Company Company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Company = new Company();

        editText = (EditText)findViewById(R.id.editText);
        btn = (Button)findViewById(R.id.btn);
        toolNumber= (ListView) findViewById(R.id.toolNumber);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(editText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "請輸入次數", Toast.LENGTH_LONG).show();
                }
                else {
                    int count = Integer.parseInt(editText.getText().toString());
                    if(count == 0) {
                        Toast.makeText(MainActivity.this, "請輸入大於0的值", Toast.LENGTH_LONG).show();
                    }
                    else {
                        // int cost = 0;
                        String [] items = new String[count];
                        for(int i = 0; i < count; i++) {
                            int money = randomMoney();
                            rent_record = Company.rent(id, money);
                            // String transname = Company.rent(money);
                            // cost += money;
                            String item = "第" + (i + 1) + "次\n租金：" + money + "元，租到" + rent_record.transportation.name + "\n出勤率：" + Company.getRate(rent_record) + "%\n累積金額：" + rent_record.transportation.rent_price_sum + "元";
                            items[i] = item;
                        }
                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, items);
                        toolNumber.setAdapter(adapter);
                    }
                }
            }
        });
    }

    public int randomMoney() {
        return 100 + (int)(Math.random() * ((200000 - 100) + 1));
    }

    // 虛擬類別，所有交通工具的基底
    public abstract class Transportation {
        public String name;
        public int rent_times = 0; //紀錄這個交通工具一共出租幾次
        public int rent_price_sum = 0; //出租總金額
    }

    public class Bike extends Transportation {
        public Bike()
        {
            this.name = "腳踏車";
        }
    }

    public class Motorcycle extends Transportation {
        public Motorcycle()
        {
            this.name = "機車";
        }
    }

    public class Car extends Transportation {
        public Car()
        {
            this.name = "汽車";
        }
    }

    public class Airplane extends Transportation {
        public Airplane()
        {
            this.name = "飛機";
        }
    }
    
    public class Rocket extends Transportation {
        public Rocket()
        {
            this.name = "火箭";
        }
    }

    // 每一次借出的紀錄
    public class RentRecord {
        public int id; //流水號
        public Transportation transportation; //用哪個交通工具
        public int rent_price; //租金

        public RentRecord(int id, Transportation transportation, int rent_price)
        {
            this.id = id;
            this.transportation = transportation;
            this.rent_price = rent_price;

            //把這次的出租次數及金額加到該交通工具
            transportation.rent_times = transportation.rent_price + 1;
            transportation.rent_price_sum = transportation.rent_price_sum + rent_price;
        }
    }

    public class Company {
        public ArrayList<RentRecord> trans;

        public Bike bike; 
        public Motorcycle motorcycle;
        public Car car;
        public Airplane airplane;
        public Rocket rocket;

        public Company() {
            this.rent_records = new ArrayList<>();

            //初始化所有交通工具
            this.bike = new Bike;
            this.motorcycle = new Motorcycle;
            this.car = new Car;
            this.airplane = new Airplane;
            this.rocket = new Rocket;
        }

        public rent(int id, int offer_rent_price) {
            transportation = this.getTransportation(offer_rent_price);
            rent_record = RentRecord(id, transportation, rent_price);
            this.rent_records.add(rent_record);

            return rent_record;
        }

        // 從租金決定這次要用哪個交通工具
        public Transportation getTransportation(int offer_rent_price) {
            if(offer_rent_price >= 100 && offer_rent_price < 500) {
                return this.bike;
            }
            else if(offer_rent_price >= 500 && offer_rent_price < 2000) {
                return this.motorcycle;
            }
            else if(offer_rent_price >= 2000 && offer_rent_price < 30000) {
                return this.car;
            }
            else if(offer_rent_price >= 30000 && offer_rent_price < 100000) {
                return this.airplane;
            }
            else if(offer_rent_price >= 100000) {
                 return this.rocket;
            }
        }

        // 取得出勤率
        public float getRate(RentRecord rent_record) {
            //流水號可信, 流水號多少代表出租了幾次，可以直接拿來當分母
            return rent_record.transportation.rent_times / rent_record.id; 

            //流水號不可信，會跳號或其他原因
            //int rent_times_sum = this.bike.rent_times + this.motorcycle.rent_times + this.car.rent_times + this.airplane.rent_times + this.rocket.rent_times;
            //return transportation / rent_times_sum;
        }
    }
}
