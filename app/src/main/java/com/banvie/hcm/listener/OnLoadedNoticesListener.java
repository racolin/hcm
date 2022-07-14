package com.banvie.hcm.listener;

import com.banvie.hcm.model.policy.Notice;

import java.util.List;

public interface OnLoadedNoticesListener extends OnLoadImageListener{
    void setOnLoadedNotices(List<Notice> notices);
}
