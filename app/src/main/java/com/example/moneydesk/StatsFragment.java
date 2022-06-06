package com.example.moneydesk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class StatsFragment extends Fragment {

    private BarChart incomeChart;
    private BarChart expenseChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        incomeChart = view.findViewById(R.id.chart_income);
        expenseChart = view.findViewById(R.id.chart_expense);
        loadChartView();
        loadChartDataMonth();
        return view;
    }

    private void loadChartView()
    {
        incomeChart.getLegend().setEnabled(false);
        incomeChart.getDescription().setEnabled(false);
        incomeChart.getAxisRight().setEnabled(false);
        incomeChart.getAxisLeft().setAxisMinimum(0f);
        incomeChart.getAxisLeft().setDrawAxisLine(false);
        incomeChart.getAxisLeft().setDrawGridLines(false);
        incomeChart.getXAxis().setDrawGridLines(false);
        incomeChart.getXAxis().setGranularity(1f);
        incomeChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        incomeChart.animateXY(2000, 2000);
        expenseChart.getLegend().setEnabled(false);
        expenseChart.getDescription().setEnabled(false);
        expenseChart.getAxisRight().setEnabled(false);
        expenseChart.getAxisLeft().setAxisMinimum(0f);
        expenseChart.getAxisLeft().setDrawAxisLine(false);
        expenseChart.getAxisLeft().setDrawGridLines(false);
        expenseChart.getXAxis().setDrawGridLines(false);
        expenseChart.getXAxis().setGranularity(1f);
        expenseChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        expenseChart.animateXY(2000, 2000);

        ArrayList<String> values = new ArrayList<>();
        values.add("DEC");
        values.add("JAN");
        values.add("FEB");
        values.add("MAR");
        values.add("APR");
        values.add("MAY");
        values.add("JUN");
        values.add("JUL");
        values.add("AUG");
        values.add("SEP");
        values.add("OCT");
        values.add("NOV");
        XAxis xAxis = incomeChart.getXAxis();
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return values.get((int) value);
            }
        });
        XAxis xAxis2 = expenseChart.getXAxis();
        xAxis2.setLabelCount(12);
        xAxis2.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return values.get((int) value);
            }
        });
    }

    private void loadChartDataMonth() {
        ArrayList<BarEntry> incomes = new ArrayList<>();
        ArrayList<BarEntry> expenses = new ArrayList<>();
        Client client = new Client();
        String data_inc = client.get_totalincome_month(Param.id_user);
        try {
            JSONArray jsonArray = new JSONArray(data_inc);
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject rec = jsonArray.getJSONObject(i);
                Float month = (float)rec.getDouble("month");
                Float income = (float)rec.getDouble("sum");
                incomes.add(new BarEntry(month, income));
            }
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        String data_exp = client.get_totalexpense_month(Param.id_user);
        try {
            JSONArray jsonArray = new JSONArray(data_exp);
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject rec = jsonArray.getJSONObject(i);
                Float month = (float)rec.getDouble("month");
                Float expense = (float)rec.getDouble("sum");
                expenses.add(new BarEntry(month, expense));
            }
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        BarDataSet dataSet1 = new BarDataSet(incomes,"Расходы");
        BarDataSet dataSet2 = new BarDataSet(expenses,"Доходы");
        dataSet1.setColors(ColorTemplate.MATERIAL_COLORS[0]);
        dataSet2.setColors(ColorTemplate.MATERIAL_COLORS[1]);
        dataSet1.setValueTextSize(14f);
        dataSet2.setValueTextSize(14f);
        BarData incomedata = new BarData(dataSet1);
        BarData expensedata = new BarData(dataSet2);
        incomedata.setBarWidth(0.5f);
        expensedata.setBarWidth(0.5f);
        incomeChart.setData(incomedata);
        expenseChart.setData(expensedata);
        incomeChart.invalidate();
        expenseChart.invalidate();
    }
}