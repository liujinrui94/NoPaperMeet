package com.bril.nopapermeet.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.artifex.mupdf.MuPDFCore;
import com.artifex.mupdf.MuPDFPageAdapter;
import com.artifex.mupdf.ReaderView;
import com.artifex.mupdf.SavePdf;
import com.artifex.mupdf.SignatureView;
import com.bril.nopapermeet.App;
import com.bril.nopapermeet.R;
import com.bril.nopapermeet.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;


/**
 * Created by LiuJinrui on 2017/3/29.
 */
@ContentView(R.layout.activity_pdf)
public class PdfActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.readerView)
    ReaderView readerView;
    @ViewInject(R.id.rl_back)
    RelativeLayout rlBack;

    @ViewInject(R.id.rl_sign)
    RelativeLayout rlSign;

    @ViewInject(R.id.rl_update)
    RelativeLayout rlUpdate;
    @ViewInject(R.id.rl_clear)
    RelativeLayout rlClear;
    @ViewInject(R.id.rl_save)
    RelativeLayout rlSave;
    @ViewInject(R.id.pdf_iv_back)
    ImageView iv_back;

    boolean isUpdate = false;
    String in_path;
    String out_path;
    String update_path;
    PopupWindow popupWindow;
    SignatureView signatureView;
    boolean iBack = false;
    float density; //屏幕分辨率密度
    int first = 1;
    int back_first = 0;
    String file_id;
    ProgressDialog progressDialog;
    MuPDFCore muPDFCore;
    Save_Pdf save_pdf;
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        x.view().inject(this);
        View screenView = this.getWindow().getDecorView();
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();

        //计算分辨率密度
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        density = metric.density;
        in_path = Environment.getExternalStorageDirectory().getPath() + "/sample.pdf";
        out_path = in_path.substring(0, in_path.length() - 4) + "1.pdf";
        try {
            muPDFCore = new MuPDFCore(in_path);//PDF的文件路径
            readerView.setAdapter(new MuPDFPageAdapter(this, muPDFCore));
            View mView = LayoutInflater.from(this).inflate(R.layout.signature_layout, null);
            signatureView = (SignatureView) mView.findViewById(R.id.qianming);
            readerView.setDisplayedViewIndex(0);
            popupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
            rlSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rlSave.getVisibility() == View.GONE) {
                        popupWindow.showAsDropDown(rlSign, 0, 0);
                        rlSave.setVisibility(View.VISIBLE);
                        rlClear.setVisibility(View.VISIBLE);
                    } else {
                        signatureView.clear();
                        popupWindow.dismiss();
                        rlSave.setVisibility(View.GONE);
                        rlClear.setVisibility(View.GONE);
                    }
                }
            });
            rlSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float scale = readerView.getmScale();///得到放大因子
                    SavePdf savePdf = new SavePdf(in_path, out_path);
                    savePdf.setScale(scale);
                    savePdf.setPageNum(readerView.getDisplayedViewIndex() + 1);

                    savePdf.setWidthScale(1.0f * readerView.scrollX / readerView.getDisplayedView().getWidth());//计算宽偏移的百分比
                    savePdf.setHeightScale(1.0f * readerView.scrollY / readerView.getDisplayedView().getHeight());//计算长偏移的百分比
                    savePdf.setDensity(density);
                    Bitmap bitmap = Bitmap.createBitmap(signatureView.getWidth(), signatureView.getHeight(),
                            Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    signatureView.draw(canvas);
                    savePdf.setBitmap(bitmap);
                    save_pdf = new Save_Pdf(savePdf);
                    save_pdf.execute();
                    popupWindow.dismiss();
                    iBack = true;
                    rlSave.setVisibility(View.GONE);
                    rlClear.setVisibility(View.GONE);
                    rlUpdate.setVisibility(View.GONE);
                    rlBack.setVisibility(View.VISIBLE);
                    ///显示隐藏probar
                    progressDialog = ProgressDialog.show(mContext, null, "正在存储...");
                    signatureView.clear();
                }
            });


            rlBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (iBack) {
                            if (back_first == 1) {
                                muPDFCore = new MuPDFCore(getIntent().getExtras().getString("inPath"));
                                first = 1;
                                in_path = getIntent().getExtras().getString("inPath");
                                out_path = in_path.substring(0, in_path.length() - 4) + "1.pdf";
                            } else {
                                muPDFCore = new MuPDFCore(out_path);
                                String temp = in_path;
                                in_path = out_path;
                                out_path = temp;
                            }
                            readerView.setmScale(1.0f);
                            readerView.setDisplayedViewIndex(readerView.getDisplayedViewIndex());
                            iBack = false;
                            if (rlSave.getVisibility() == View.VISIBLE) {
                                rlSave.setVisibility(View.GONE);
                                rlClear.setVisibility(View.GONE);
                                rlUpdate.setVisibility(View.GONE);
                                signatureView.clear();
                            }
                            rlBack.setVisibility(View.GONE);
                            back_first--;
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("无法返回");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            rlClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signatureView.clear();
                }
            });
            rlUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "这里没有用到", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pdf_iv_back:
                finish();
                break;
        }
    }


    /*
    * 用于存储的异步,并上传更新
    * */
    class Save_Pdf extends AsyncTask {

        SavePdf savePdf;

        public Save_Pdf(SavePdf savePdf) {
            this.savePdf = savePdf;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            savePdf.addText(isScreenChange());
            if (first == 1) {
                update_path = in_path.substring(0, in_path.length() - 4) + ".pdf";
                in_path = in_path.substring(0, in_path.length() - 4) + "2.pdf";
                first++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(mContext, "存储完成", Toast.LENGTH_SHORT).show();
            try {
                muPDFCore = new MuPDFCore(out_path);
                readerView.setAdapter(new MuPDFPageAdapter(mContext, muPDFCore));
                String temp = in_path;
                in_path = out_path;
                out_path = temp;
                readerView.setmScale(1.0f);
                readerView.setDisplayedViewIndex(readerView.getDisplayedViewIndex());
                progressDialog.dismiss();
                back_first++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

 /*   */

    /**
     * 返回按钮，退出时删除那两个文件
     */
    @Override
    public void onBackPressed() {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否退出？");
        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除缓冲的存储*/
        PdfActivity.this.finish();
      /*      }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
     /*   if (first != 1) {
            File file = new File(in_path);
            File file1 = new File(out_path);
            File file2 = new File(update_path);
            if (file.exists()) file.delete();
           *//* if (file1.exists()) file1.delete();
            if (file2.exists() && isUpdate) file2.delete();*//*
        }
//        if (save_pdf != null)
        save_pdf.cancel(false);*/
        if (first == 1) {
            File file = new File(in_path);
            File file1 = new File(out_path);
            File file2 = new File(update_path);
            if (file.exists()) file.delete();
            if (file1.exists()) file1.delete();
            if (file2.exists() && isUpdate) file2.delete();
        }
        save_pdf.cancel(true);
    }


    public boolean isScreenChange() {

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
//横屏
            return true;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
//竖屏
            return false;
        }
        return false;
    }
}
