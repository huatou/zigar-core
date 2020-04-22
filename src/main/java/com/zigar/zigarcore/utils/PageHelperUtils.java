package com.zigar.zigarcore.utils;

import com.zigar.zigarcore.exception.BusinessLogicException;
import com.zigar.zigarcore.model.Page;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.ui.UiAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangzihua on 2019/3/26.
 */

@Component
public class PageHelperUtils {

    @Autowired
    private UiAdapter uiAdapter;


    //最大查询单页条数
    public static final Integer MAX_PAGE_SIZE = 100;
    public static final Integer DEFAULT_PAGE_INT = 1;
    public static final Integer DEFAULT_PAGE_SIZE_INT = 20;

    /**
     * 判断请求是否分页并且进行分页操作
     *
     * @return
     */
    public <T> Results<Page<T>> isPage(HttpServletRequest httpServletRequest) {


        Object pageObject = httpServletRequest.getParameter(uiAdapter.getCurrentPageParam());
        Object pageSizeObject = httpServletRequest.getParameter(uiAdapter.getCurrentPageSizeParam());

        if (pageObject != null && pageSizeObject != null) {

            String pageStr = pageObject.toString();
            String pageSizeStr = pageSizeObject.toString();

            Integer page = null;
            Integer pageSize = null;
            try {
                page = Integer.valueOf(pageStr);

            } catch (NumberFormatException e) {
                throw new BusinessLogicException(uiAdapter.getCurrentPageParam() + "必须为整数");
            }
            try {
                pageSize = Integer.valueOf(pageSizeStr);
            } catch (NumberFormatException e) {
                throw new BusinessLogicException(uiAdapter.getCurrentPageSizeParam() + "必须为整数");
            }
            if (page <= 0) {
                return Results.error("page必须大于0");
            }
            if (pageSize > MAX_PAGE_SIZE) {
                return Results.error("pageSize必须小于100");
            }
            return Results.succeed(new Page(page, pageSize));

        }
        return Results.error();
    }

}
