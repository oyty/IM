package com.iteacher.android.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.iteacher.android.R;
import com.iteacher.android.base.BaseActivity;
import com.iteacher.android.base.BaseApplication;
import com.iteacher.android.entities.Profile;
import com.iteacher.android.net.AppException;
import com.iteacher.android.net.Request;
import com.iteacher.android.net.Request.RequestMethod;
import com.iteacher.android.net.Request.RequestTool;
import com.iteacher.android.net.callback.JsonCallback;
import com.iteacher.android.ui.widgets.EditorView;
import com.iteacher.android.util.Constants;
import com.iteacher.android.util.PrefsAccessor;
import com.iteacher.android.util.TextUtil;
import com.iteacher.android.util.Trace;
import com.iteacher.android.util.UrlHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by oyty on 5/5/16.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditorView mLoginPwdEdt;
    private EditorView mLoginAccountEdt;
    private Button mLoginSubmitBtn;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mLoginAccountEdt = (EditorView) findViewById(R.id.mLoginAccountEdt);
        mLoginPwdEdt = (EditorView) findViewById(R.id.mLoginPwdEdt);
        mLoginSubmitBtn = (Button) findViewById(R.id.mLoginSubmitBtn);
    }

    @Override
    protected void initViewListener() {
        mLoginSubmitBtn.setOnClickListener(this);
    }

    @Override
    protected void process() {
        mLoginAccountEdt.setText(PrefsAccessor.getInstance(this).getString(Constants.KEY_ACCOUNT));
        mLoginPwdEdt.setText(PrefsAccessor.getInstance(this).getString(Constants.KEY_PASSWORD));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mLoginSubmitBtn:
                String account = mLoginAccountEdt.getText();
                String pwd = mLoginPwdEdt.getText();
                if (TextUtil.isValidate(account, pwd)) {
                    doLogin(account, pwd);
                }
                break;
        }
    }

    private void doLogin(final String account, final String pwd) {
        Request request = new Request(UrlHelper.loadLogin(), RequestMethod.POST, RequestTool.HTTPURLCONNECTION);
        JSONObject json = new JSONObject();
        try {
            json.put("account", account);
            json.put("password", pwd);
            json.put("clientId", "android");
            json.put("clientVersion", "1.0.0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addHeader("content-type", "application/json");
        request.postContent = json.toString();
        // request.entity = new StringEntity(json.toString());
        // request.setCallback(new StringCallback() {
        //
        // @Override
        // public void onSuccess(String result) {
        // Trace.d("resut:"+result);
        // }
        //
        // @Override
        // public void onFailure(AppException exception) {
        // exception.printStackTrace();
        // }
        // });

        request.setCallback(new JsonCallback<Profile>() {
            @Override
            public int retryCount() {
                return 3;
            }

            @Override
            public void onSuccess(Profile result) {
                Trace.d("login: " + result.toString());
                BaseApplication.setProfile(result);
                BaseApplication.mAppState = 1;
                PrefsAccessor.getInstance(LoginActivity.this).saveString(Constants.KEY_ACCOUNT, account);
                PrefsAccessor.getInstance(LoginActivity.this).saveString(Constants.KEY_PASSWORD, pwd);
                goHome();
            }

            @Override
            public void onFailure(AppException exception) {
                exception.printStackTrace();
            }
        });

        request.execute();
    }

    protected void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
