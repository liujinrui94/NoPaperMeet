package com.bril.nopapermeet.dao;

import org.xutils.DbManager;

/**
 * 配置
 */

public class ConfigSetting {

    public static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig().setDbName("bril.db")
            .setDbVersion(1).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager dbManager, int i, int i1) {
                    //TODO 数据库升级
//                    Log.e("数据库升级", i + "----" + i1);
//                    if (i == 3) {
//                        try {
//                            dbManager.dropTable(Account.class);
//                        } catch (DbException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            });
}
