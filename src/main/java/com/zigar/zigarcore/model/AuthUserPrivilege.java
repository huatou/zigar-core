package com.zigar.zigarcore.model;

import com.zigar.zigarcore.action.RequestUpdateAction;
import com.zigar.zigarcore.entity.PrivilegeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuthUserPrivilege {

    @NotNull(groups = RequestUpdateAction.class, message = "userId不能为空")
    private String userId;
    private List<PrivilegeEntity> privilegeList;

}