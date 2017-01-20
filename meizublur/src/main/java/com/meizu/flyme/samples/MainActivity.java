
package com.meizu.flyme.samples;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.ListActivity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.SimpleAdapter;

import com.meizu.flyme.blur.drawable.BlurDrawable;
import com.meizu.flyme.blur.view.BlurRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    private BlurDrawable mBlurDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);

        // 填充ListView数据
        List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 20;) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (i % 2 == 0) {
                map.put("ICON", R.drawable.jpeg6);
                map.put("TITLE", ++i + "  Test Title one");
                map.put("CONTENT", "Test Content one");
            } else {
                map.put("ICON", R.drawable.jpeg7);
                map.put("TITLE", ++i + "  Test Title two Title two");
                map.put("CONTENT", "Test Content two Test Content two");
            }
            contents.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, contents, R.layout.list_item_layout,
                new String[] { "ICON", "TITLE", "CONTENT" }, 
                new int[] { android.R.id.icon, android.R.id.text1, android.R.id.text2 });
        setListAdapter(adapter);
 
        ActionBar ab = getActionBar();
        ab.setBackgroundDrawable(getDrawable(R.drawable.mz_titlebar_background_bottom_blank));

        // 如果要对带毛玻璃的布局设置动画，那么此布局必须使用BlurFrameLayout、BlurLinearLayout、BlurRelativeLayout中的一个。
        // 如果使用了上述三种布局，那么GLBlurDrawable则不需要自己创建，只需要调用getBlurDrawable()获取对象即可
        // 下面是三种不同等级、不同颜色的毛玻璃，作为api示例
        BlurRelativeLayout blur = (BlurRelativeLayout) findViewById(R.id.blur_rect_view);
        mBlurDrawable = blur.getBlurDrawable();
        mBlurDrawable.setForce(true);
        mBlurDrawable.setBlurLevel(0.3f);

        BlurRelativeLayout blur1 = (BlurRelativeLayout) findViewById(R.id.blur_rect_view1);
        BlurDrawable blurDrawable1 = blur1.getBlurDrawable();
        blurDrawable1.setForce(true);
        blurDrawable1.setBlurLevel(0.6f);
        blurDrawable1.setColorFilter(Color.argb(0x88, 0xff, 0xff, 0));

        BlurRelativeLayout blur2 = (BlurRelativeLayout) findViewById(R.id.blur_rect_view2);
        BlurDrawable blurDrawable2 = blur2.getBlurDrawable();
        blurDrawable2.setForce(true);
        blurDrawable2.setBlurLevel(0.9f);
        blurDrawable2.setColorFilter(Color.argb(0x88, 0x0, 0xff, 0));
    }

    /**
     * 创建BlurDrawable，可以参考如何设置参数
     * @param background 毛玻璃颜色
     * @param divider 分隔线颜色
     * @param level 模糊等级
     * @param layerInset 分隔线区域
     * @return
     */
    private static Drawable createDrawable(int background, int divider, float level, Rect layerInset) {
        ColorDrawable dividerDrawable = new ColorDrawable(divider);
        BlurDrawable blurDrawable = new BlurDrawable();
        blurDrawable.setColorFilter(background);
        blurDrawable.setBlurLevel(level);
        blurDrawable.setForce(true);
        LayerDrawable ld = new LayerDrawable(new Drawable[]{blurDrawable, dividerDrawable});
        ld.setLayerInset(1, layerInset.left, layerInset.top, layerInset.right, layerInset.bottom);
        return ld;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu1: {
                // 用动画的方式演示：可以通过setBlurLevel设置模糊的等级
                ObjectAnimator anim = ObjectAnimator.ofFloat(mBlurDrawable,
                        "blurLevel", BlurDrawable.DEFAULT_BLUR_LEVEL, 0.0f, BlurDrawable.DEFAULT_BLUR_LEVEL);
                anim.setDuration(5000);
                anim.start();
            }
            break;
            case R.id.action_menu2: {
                // 用动画的方式演示：可以通过setAlpha设置模糊效果在原有图像上叠加时采用的透明度
                ObjectAnimator anim = ObjectAnimator.ofInt(mBlurDrawable, "alpha", 255, 0, 255);
                anim.setDuration(1000);
                anim.start();
            }
            break;
            case R.id.action_menu3: {
                // 可以通过setColorFilter设置在模糊效果基础上叠加的颜色效果
                // 默认颜色效果是BlurDrawable.DEFAULT_BLUR_COLOR和BlurDrawable.DEFAULT_BLUR_COLOR_MODE
                int r = (int)(Math.random() * 255);
                int g = (int)(Math.random() * 255);
                int b = (int)(Math.random() * 255);
                mBlurDrawable.setColorFilter(Color.argb(0x88, r, g, b), BlurDrawable.DEFAULT_BLUR_COLOR_MODE);
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

}
