package com.example.moneydesk.ui.mainfragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.moneydesk.Client;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PieChartFragment extends Fragment {

    private PieChart pieChart;
    private HorizontalBarChart hbarChart;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie, container, false);
        pieChart = view.findViewById(R.id.chart_pie);
        hbarChart = view.findViewById(R.id.chart_category);
        spinner = view.findViewById(R.id.spinnerDate);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] items = {"Месяц","Неделя","Сегодня"};
        loadChartView();
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                pieChart.clear();
                hbarChart.clear();
                if (item == "Месяц") {
                    loadChartDataMonth();
                }
                else if (item == "Неделя") {
                    loadChartDataWeek();
                }
                else if (item == "Сегодня") {
                    loadChartDataToday();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadChartView() {
        pieChart.setDrawCenterText(true);
        pieChart.setNoDataText("Нет трат");
        pieChart.setCenterTextSize(16f);
        pieChart.setDrawHoleEnabled(true); // Рисовать ли круг в середине круговой диаграммы
        pieChart.setHoleColor(Color.WHITE); // Цвет отрисовки круга в середине круговой диаграммы
        pieChart.setHoleRadius(30f); // Радиус круга в середине круговой диаграммы
        pieChart.setTransparentCircleRadius(30f); // Устанавливаем радиус кольца
        pieChart.getLegend().setEnabled(false); //убрать легенду
        pieChart.getDescription().setEnabled(false); //убрать описание
        pieChart.animateY(1500, Easing.EaseInOutQuad);
        hbarChart.getLegend().setEnabled(false);
        hbarChart.getDescription().setEnabled(false);
        hbarChart.getAxisLeft().setEnabled(false);
        hbarChart.getAxisRight().setEnabled(false);
        hbarChart.animateXY(2000, 2000);
        hbarChart.setTouchEnabled(false);
    }

    public void setAxisLabels(ArrayList<String> values){
        XAxis xAxis = hbarChart.getXAxis();
        if (values.size() == 0)
            pieChart.setCenterText("Нет расходов за период");
        else
            pieChart.setCenterText("Расходы");

        if (values.size() <= 1)
            xAxis.setEnabled(false);
        else {
            xAxis.setEnabled(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12f);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setLabelCount(values.size());
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    if (((int) value) >= values.size())
                        return values.get(values.size()-1);
                    else
                        return values.get((int) value);
                }
            });
        }
    }

    private void loadChartDataMonth() {
        ArrayList<String> values = new ArrayList<>();
        ArrayList<PieEntry> list = new ArrayList<>();
        ArrayList<BarEntry> barlist = new ArrayList<>();
        Client client = new Client();
        String data = client.get_sumincome_month(Param.id_user);
        if (data != null) {
            try {
                JSONArray jsonArray = new JSONArray(data);
                for(int i = 0; i< jsonArray.length();i++) {
                    JSONObject rec = jsonArray.getJSONObject(i);
                    String category = rec.getString("category");
                    Float amount = (float)rec.getDouble("sum");
                    list.add(new PieEntry(amount, category));
                    barlist.add(new BarEntry(i,amount));
                    values.add(category);
                }
            }
            catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.COLORFUL_COLORS) {
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(list,"Категории");
        dataSet.setColors(colors);
        PieData piedata = new PieData(dataSet);
        piedata.setDrawValues(true);
        piedata.setValueFormatter(new PercentFormatter(pieChart));
        piedata.setValueTextSize(14f);
        piedata.setValueTextColor(Color.WHITE);
        pieChart.setData(piedata);
        pieChart.invalidate();
        setAxisLabels(values);
        BarDataSet barDataSet = new BarDataSet(barlist,"Категории");
        barDataSet.setColors(colors);
        BarData bardata = new BarData(barDataSet);
        bardata.setBarWidth(0.4f);
        hbarChart.setData(bardata);
        hbarChart.invalidate();
    }

    private void loadChartDataWeek() {
        ArrayList<String> values = new ArrayList<>();
        ArrayList<PieEntry> list = new ArrayList<>();
        ArrayList<BarEntry> barlist = new ArrayList<>();
        Client client = new Client();
        String data = client.get_sumincome_week(Param.id_user);
        if (data != null) {
            try {
                JSONArray jsonArray = new JSONArray(data);
                for(int i = 0; i< jsonArray.length();i++) {
                    JSONObject rec = jsonArray.getJSONObject(i);
                    String category = rec.getString("category");
                    Float amount = (float)rec.getDouble("sum");
                    list.add(new PieEntry(amount, category));
                    barlist.add(new BarEntry(i,amount));
                    values.add(category);
                }
            }
            catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.COLORFUL_COLORS) {
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(list,"Категории");
        dataSet.setColors(colors);
        PieData piedata = new PieData(dataSet);
        piedata.setDrawValues(true);
        piedata.setValueFormatter(new PercentFormatter(pieChart));
        piedata.setValueTextSize(14f);
        piedata.setValueTextColor(Color.WHITE);
        pieChart.setData(piedata);
        pieChart.invalidate();
        setAxisLabels(values);
        BarDataSet barDataSet = new BarDataSet(barlist,"Категории");
        barDataSet.setColors(colors);
        BarData bardata = new BarData(barDataSet);
        bardata.setBarWidth(0.4f);
        hbarChart.setData(bardata);
        hbarChart.invalidate();
    }

    private void loadChartDataToday() {
        ArrayList<String> values = new ArrayList<>();
        ArrayList<PieEntry> list = new ArrayList<>();
        ArrayList<BarEntry> barlist = new ArrayList<>();
        Client client = new Client();
        String data = client.get_sumincome_today(Param.id_user);
        if (data != null) {
            try {
                JSONArray jsonArray = new JSONArray(data);
                for(int i = 0; i< jsonArray.length();i++) {
                    JSONObject rec = jsonArray.getJSONObject(i);
                    String category = rec.getString("category");
                    Float amount = (float)rec.getDouble("sum");
                    list.add(new PieEntry(amount, category));
                    barlist.add(new BarEntry(i,amount));
                    values.add(category);
                }
            }
            catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.COLORFUL_COLORS) {
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(list,"Категории");
        dataSet.setColors(colors);
        PieData piedata = new PieData(dataSet);
        piedata.setDrawValues(true);
        piedata.setValueFormatter(new PercentFormatter(pieChart));
        piedata.setValueTextSize(14f);
        piedata.setValueTextColor(Color.WHITE);
        pieChart.setData(piedata);
        pieChart.invalidate();
        setAxisLabels(values);
        BarDataSet barDataSet = new BarDataSet(barlist,"Категории");
        barDataSet.setColors(colors);
        BarData bardata = new BarData(barDataSet);
        bardata.setBarWidth(0.4f);
        hbarChart.setData(bardata);
        hbarChart.invalidate();
    }
}