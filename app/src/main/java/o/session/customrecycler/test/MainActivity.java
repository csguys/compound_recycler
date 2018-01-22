package o.session.customrecycler.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import demo.base.list_library.ProRecyclerView;
import o.session.customrecycler.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ProRecyclerView recyclerView;
    private List<String> data = new ArrayList<>();
    private TempAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter  = new TempAdapter();
        recyclerView.setAdapter(adapter);
        findViewById(R.id.b1).setOnClickListener(this);
        findViewById(R.id.b2).setOnClickListener(this);
        findViewById(R.id.b3).setOnClickListener(this);
        findViewById(R.id.b4).setOnClickListener(this);

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.b1:
                initData();
                adapter.notifyDataSetChanged();
                break;
            case R.id.b2:
                data.clear();
                adapter.notifyDataSetChanged();
                break;
            case R.id.b3:
                recyclerView.setSwipeEnable(true);
                recyclerView.setSwipeRefreshLayoutListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        timer();
                    }
                });
                break;
            case R.id.b4:
                break;
        }
    }

    private void timer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshing(false);
            }
        },2000);
    }

    private void initData(){
        data.addAll(Arrays.asList("a","b","c","d","e"));
    }

    class TempAdapter extends RecyclerView.Adapter<TempAdapter.TempHolder>{

        @Override
        public TempHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            TextView textView = new TextView(parent.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            return new TempHolder(textView);
        }

        @Override
        public void onBindViewHolder(final TempHolder holder, final int position) {
            holder.textView.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class TempHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public TempHolder(final View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }
    }
}
