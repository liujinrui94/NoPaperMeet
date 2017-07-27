package com.bril.nopapermeet;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.entity.DaoMaster;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.Lssue;
import com.bril.nopapermeet.entity.MeetFile;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.MeetListDao;
import com.bril.nopapermeet.entity.MeetPerson;
import com.bril.nopapermeet.entity.MeetPersonDao;
import com.bril.nopapermeet.entity.Notice;
import com.bril.nopapermeet.entity.Summary;
import com.bril.nopapermeet.entity.UnReadMsg;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * app
 */

public class App extends Application {

    private static App appInstance;
    private DaoMaster.DevOpenHelper mHelper;
    private Database db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private final String fileName = "AppFile";
    private final String keyIsFrist = "first";
    SharedPreferences sp;
    public static final String SAMPLE_FILE = "sample.pdf";
    public static final  String PPT="ppt.pptx";
    public static final  String Word="Word.docx";

    public static App getsInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        appInstance = this;
        setDatabase();
        sp = getSharedPreferences(fileName, MODE_PRIVATE);
    }

    /**
     * 初始化数据
     */
    public void initDate(final InitLinsner callBack) {
        if (sp.getBoolean(keyIsFrist, true)) {
            callBack.start();
            x.task().run(new Runnable() {
                @Override
                public void run() {
                    //人员初始化
                    List<Account> list = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        Account account = new Account();
                        account.loginName = "ceshi" + i;
                        account.nickName = "人员" + i;
                        account.phone = "18311077909";
                        account.departmentName = "办公室";
                        list.add(account);
                    }
                    mDaoSession.getAccountDao().saveInTx(list);
                    //会议初始化
                    List<Account> accounts = mDaoSession.getAccountDao().loadAll();
                    Random ramdom = new Random();
                    List<MeetList> meetLists = new ArrayList<>();
                    MeetListDao meetListDao = mDaoSession.getMeetListDao();
                    for (int i = 0; i < 100; i++) {
                        MeetList meetList = new MeetList();
                        meetList.title = "会议标题" + i;
                        meetList.msg = "会议内容" + i;
                        meetList.date = "2017-12-12 11:22";
                        meetList.hostId = accounts.get(i % accounts.size()).id;
                        meetList.place="xx公司五楼202室";
                        meetList.type="销售部门";
                        meetList.require="入场时间：按时到会\n参会着装：正装\n其他：会议期间不准喧哗、不准随意走动、自觉关闭手机\n备注";
                        meetList.placeRequire="干净整洁";
                        meetList.information="公司前一天所发文件";
                        meetList.people="销售部门和开发部门人员";
                        meetLists.add(meetList);
                    }
                    meetListDao.saveInTx(meetLists);
                    List<MeetList> meetListArray = meetListDao.loadAll();

                    MeetPersonDao meetPersonDao = mDaoSession.getMeetPersonDao();
                    for (int i = 0; i < meetListArray.size(); i++) {
                        //参会人
                        MeetList meetList = meetListArray.get(i);
                        Set<Long> meetPersonIds = new HashSet<>();
                        meetPersonIds.add(meetList.hostId);
                        for (int j = 0; j < 10; j++) {
                            meetPersonIds.add(accounts.get(ramdom.nextInt(accounts.size())).id);
                        }
                        Iterator<Long> ids = meetPersonIds.iterator();
                        List<MeetPerson> meePersonArray = new ArrayList<>();
                        Log.e("MeetId", "----" + meetList.id);
                        while (ids.hasNext()) {
                            Long id = ids.next();
                            MeetPerson meetPerson = new MeetPerson();
                            meetPerson.accountId = id;
                            meetPerson.meetId = meetList.id;
                            meePersonArray.add(meetPerson);
                        }
                        meetPersonDao.saveInTx(meePersonArray);
                        //议题列表
                        List<Lssue> lssues = new ArrayList<>();
                        for (int j = 0; j < 30; j++) {
                            if (j % 3 == 0) {
                                lssues.add(new Lssue(meetList.id, "合作方产品介绍" + meetList.hostId, 2, "2017年3月9日(星期四)上午10:00时", "张敏娟"));
                            } else if (j % 3 == 1) {
                                lssues.add(new Lssue(meetList.id, "无纸化方案讨论" + meetList.hostId, 2, "2017年3月9日(星期四)上午10:00时", "刘冬"));
                            } else {
                                lssues.add(new Lssue(meetList.id, "谈淘宝" + meetList.hostId, 2, "2017年3月9日(星期四)上午10:00时", "马云"));
                            }
                        }
                        mDaoSession.getLssueDao().saveInTx(lssues);

                        //会议纪要
                        Summary s=new Summary();
                        for (int j = 0; j < 100; j++) {
                            s.setContent("");
                            s.setMeetId(meetLists.get(i).id);
                        }
                        mDaoSession.getSummaryDao().saveInTx(s);
                        //通知通告
                        ArrayList<Notice> notices = new ArrayList<>();
                        for (int j = 0; j < 1; j++) {
                            Notice msg = new Notice();
                            msg.title = "关于参会要求的通知！" + j;
                            msg.msessage = " 一、未参加会议的乡镇\n" +
                                    "\n" +
                                    "    曲库乎乡、瓜什则乡\n" +
                                    "\n" +
                                    "    二、未参加会议的部门\n" +
                                    "\n" +
                                    "    县财政局、县教育科技局、县文化体育和广播电视局、县工商局、县城管局\n" +
                                    "\n" +
                                    "    对未参加会议的乡镇和部门提出批评。会议纪律事关会议的质量和效果，体现了领导干部的精神风貌、纪律意识和工作作风。当前，正值全县学习践行“三严三实”关键时期，请各乡镇、各部门引以为戒，进一步转变工作作风，加强工作纪律，确保县政府的各项决策部署落到实处。" + i;
                            msg.time = "2017-03-09 12:22";
                            msg.meetId = meetList.id;
                            notices.add(msg);
                        }
                        mDaoSession.getNoticeDao().saveInTx(notices);
                    }
                    //未读消息
                    List<UnReadMsg> listMsg = new ArrayList<>();
                    for (int i = 0; i < 25; i++) {
                        UnReadMsg unReadMsg = new UnReadMsg();
                        unReadMsg.setTitle("关于参会要求的通知！" + i);
                        unReadMsg.setTime("2017-03-09 12:22");
                        unReadMsg.setContent("暂定于2017年4月5日全体成员大会，要求各位携带笔记本、会议所需资料，准时参加。");
                        listMsg.add(unReadMsg);
                    }
                    mDaoSession.getUnReadMsgDao().saveInTx(listMsg);
                    //
                    sp.edit().putBoolean(keyIsFrist, false).commit();
                    x.task().post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess();
                        }
                    });
                }
            });
        } else {
            callBack.onSuccess();
        }

        CopyAssets( SAMPLE_FILE, Environment.getExternalStorageDirectory().getPath());
        CopyAssets( PPT, Environment.getExternalStorageDirectory().getPath());
        CopyAssets( Word, Environment.getExternalStorageDirectory().getPath());
    }

    public void CopyAssets( String assetDir, String dir) {
        File mWorkingPath = new File(dir);
        try {
            if (mWorkingPath.getParentFile().exists()) {
                mWorkingPath.getParentFile().delete();
            } else {
                mWorkingPath.createNewFile();
            }
            File outFile = new File(mWorkingPath, assetDir);
            InputStream in = getResources().getAssets().open(assetDir);
            OutputStream out = new FileOutputStream(outFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置greenDao
     */
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "noperpermeet-db");
        db = mHelper.getEncryptedWritableDb("baoyakun");
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        QueryBuilder.LOG_SQL = true;

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public Database getDb() {
        return db;
    }

    public interface InitLinsner {
        void start();

        void onSuccess();
    }
}
