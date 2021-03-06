package com.android.tixade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private TextView tvClass, tvMovie, tvRate, tvJumlah, tvAdditional;
    private TextView tvSubTotalTicket, tvSubTotalAddtional, tvTotal;
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvClass = (TextView) findViewById(R.id.tv_class);
        tvMovie = (TextView) findViewById(R.id.tv_movie);
        tvRate = (TextView) findViewById(R.id.tv_rate);
        tvJumlah = (TextView) findViewById(R.id.tv_jumlah);
        tvAdditional = (TextView) findViewById(R.id.tv_additional);

        tvSubTotalTicket = (TextView) findViewById(R.id.tv_ticket_subtotal);
        tvSubTotalAddtional = (TextView) findViewById(R.id.tv_additional_subtotal);
        tvTotal = (TextView) findViewById(R.id.tv_total);

        btnShare = (Button) findViewById(R.id.btn_share);

        Intent intent = getIntent();

        final String classCinematix = intent.getStringExtra("classCinematix");
        tvClass.setText(classCinematix);

        final String movieCinematix = intent.getStringExtra("movieCinematix");
        tvMovie.setText(movieCinematix);
        int rate = getPriceBasedOnClass(classCinematix);
        tvRate.setText(convertMoney(rate));

        final String jumlah = intent.getStringExtra("jumlahCinematix");
        tvJumlah.setText(jumlah);

        final String additional = intent.getStringExtra("additionalCinematix");
        int priceAdditional = getPriceBasedOnAdditional(additional);

        tvAdditional.setText(additional + " : " + convertMoney(priceAdditional));

        int priceClass = getPriceBasedOnClass(classCinematix);

        int subTotalMovie = priceClass * Integer.valueOf(jumlah);
        tvSubTotalTicket.setText(convertMoney(subTotalMovie));

        int subTotalAddtional = priceAdditional * Integer.valueOf(jumlah);
        tvSubTotalAddtional.setText(convertMoney(subTotalAddtional));

        final int total = subTotalMovie + subTotalAddtional;
        tvTotal.setText(convertMoney(total));

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendText = "Memesan " + jumlah + " tiket " +
                        movieCinematix + ". Kelas " + classCinematix +
                        ", include popcorn dan soda : " + additional + ".\n" +
                        "Total Harga : " + convertMoney(total);

                Intent sendIntent = new Intent();
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, sendText);
                startActivity(Intent.createChooser(sendIntent, "Bagikan"));
            }
        });
    }

    public int getPriceBasedOnClass(String classCinema) {
        int price = 0;
        switch (classCinema) {
            case "Ekonomi":
                price = 20000;
                break;
            case "Regular":
                price = 30000;
                break;
            case "Eksekutif":
                price = 50000;
                break;
            default:
                price = 0;
                break;
        }
        return price;
    }

    public int getPriceBasedOnAdditional(String additional) {
        int price = 0;
        switch (additional) {
            case "Ya":
                price = 20000;
                break;
            case "Tidak":
            default:
                price = 0;
                break;
        }
        return price;
    }

    public String convertMoney(int money) {
        return NumberFormat.getNumberInstance(Locale.GERMANY).format(money);
    }
}
