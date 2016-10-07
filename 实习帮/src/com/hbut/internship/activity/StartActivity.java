package com.hbut.internship.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hbut.internship.R;
import com.hbut.internship.util.NetWorkUtil;
import com.hbut.internship.util.ToastUtil;

/**
 * 启动时的欢迎界面
 * 
 * @author Nie
 * 
 */
public class StartActivity extends BaseActivity {

	// 创建handler对象
	private Handler handler;
	// 创建接口Runnable
	private Runnable updateThread;
	private Dialog dialog;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		

		// 创建handler对象
		handler = new Handler();

		// 创建接口Runnable 线程
		updateThread = new Runnable() {
			public void run() {
				if (!NetWorkUtil.checkNetState(StartActivity.this)) {
					netDialog(StartActivity.this, R.layout.net_dialog);
					ToastUtil.showtoast("网络未连接，请检查网络");

				} else {
					Intent intent = new Intent(StartActivity.this,
							LoginActivity.class);
					// 要跳转的界面
					startActivity(intent);
					StartActivity.this.finish();

				}
				// 判断sdk版本，大于5才能用
				int version = Integer.valueOf(android.os.Build.VERSION.SDK);

				if (version >= 5) {
					// 自定义动画跳转
					// 淡入浅出
					overridePendingTransition(R.anim.in, R.anim.out);
					// 执行handler的postdelayed，放到线程中
				}

			}

		};
		handler.postDelayed(updateThread, 3000);

	}

	/* 自定义的网络Dialog */

	public Dialog netDialog(final Context context, int layoutId) {
		dialog = new Dialog(context, R.style.Dialog);
		View layout = LayoutInflater.from(context).inflate(layoutId, null);
		/* 网络设置 */
		Button netsetbutton = (Button) layout.findViewById(R.id.netsetButton);
		netsetbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到系统网络设置的界面
				Intent intent = new Intent();
				intent.setClassName("com.android.settings",
						"com.android.settings.Settings");
				startActivity(intent);
			}
		});
		/* 退出 */
		Button quitbutton = (Button) layout.findViewById(R.id.quit);
		quitbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				System.exit(0);
			}
		});
		dialog.setContentView(layout);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		return dialog;

	}

	// 按两次返回键退出
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog.dismiss();
			StartActivity.this.finish();
			System.exit(0);

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
