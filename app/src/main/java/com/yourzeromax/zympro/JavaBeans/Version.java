package com.yourzeromax.zympro.JavaBeans;

import org.litepal.crud.DataSupport;

/**
 * Created by yourzeromax on 2017/11/12.
 */

public class Version extends DataSupport{
    private float version;

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }
}
