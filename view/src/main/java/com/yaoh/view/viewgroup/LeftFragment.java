package com.yaoh.view.viewgroup;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yaoh.view.R;

/**
 * Package com.yaoh.view.viewgroup.
 * Created by yaoh on 2017/01/04.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class LeftFragment extends Fragment implements View.OnClickListener{

    private TextView textView;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_app,null);
        View view = inflater.inflate(R.layout.include_run_first,null);
        textView = (TextView) view.findViewById(R.id.tv_run_totalcount);
        btn = (Button) view.findViewById(R.id.btn_start_run);
        btn.setOnClickListener(this);
        return view;
    }

    private int count = 0;
    @Override
    public void onClick(View view){
        count++;
        textView.setText("您点击了: " + count + "次");

        Vibrator vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设
    }
}
