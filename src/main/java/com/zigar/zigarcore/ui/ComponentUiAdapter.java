package com.zigar.zigarcore.ui;

/**
 * @author Zigar
 * @createTime 2019-10-12 14:38:05
 * @description 用于支持前端componentUi
 */

public class ComponentUiAdapter extends UiAdapter {

    private static final String CURRENT_PAGE_PARAM = "page";
    private static final String CURRENT_PAGE_SIZE_PARAM = "pageSize";

    @Override
    public String getCurrentPageParam() {
        return CURRENT_PAGE_PARAM;
    }

    @Override
    public String getCurrentPageSizeParam() {
        return CURRENT_PAGE_SIZE_PARAM;
    }
}
