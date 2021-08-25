package com.makertech.tnustudentapp.ui.timetable;

import android.content.Intent;
import android.util.Log;

import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.Gson;
import com.makertech.tnustudentapp.R;
import com.makertech.tnustudentapp.data.local.DailyTimeTable;
import com.makertech.tnustudentapp.data.local.TimetableDataSource;
import com.makertech.tnustudentapp.data.network.timetable.DailytimetableItem;
import com.makertech.tnustudentapp.data.network.timetable.Response;
import com.makertech.tnustudentapp.databinding.ActivityWorkAttendanceDailysubjectsBinding;
import com.makertech.tnustudentapp.ui.base.BaseActivity;
import com.makertech.tnustudentapp.utils.Utils;

import java.util.List;

public class TimetableFragment extends BaseActivity<ActivityWorkAttendanceDailysubjectsBinding,TimetableViewModel> {

    TimetableDataSource timetableDataSource;
    List<DailytimetableItem> dailytimetableItems;
    @Override
    protected void initView() {
        String str = Utils.getJsonFromAssets(getApplicationContext(),"timetablejson.json");
        Gson gson = new Gson();
        Response response = gson.fromJson(str,Response.class);
        Log.d(" json converted data ", response.toString());
        Intent init = getIntent();
        String day = init.getStringExtra("day");
        dailytimetableItems =  response.getDailytimetable();

        TimetableAdapter timetableAdapter = new TimetableAdapter(prepareData(day.toLowerCase()));
        getViewBinding().dailyroutineRecyclerView.setAdapter(timetableAdapter);
        getSupportActionBar().setTitle(day+" TimeTable");

    }

    @Override
    protected Integer getBindingVariable() {
        return BR._all;
    }

    @Override
    protected TimetableViewModel initialViewModel() {
        return new TimetableViewModel();
    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.activity_work_attendance_dailysubjects;
    }

    List<DailyTimeTable> prepareData(String key)
    {
      TimetableDataSource.addRoutineToMap();
     return TimetableDataSource.returnList(key);
    }
}
