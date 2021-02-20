package com.zigar.zigarcore.service;

import com.zigar.zigarcore.entity.FileEntity;
import com.zigar.zigarcore.entity.UserEntity;

import java.io.File;

public abstract class WeChatPublicHandler {

    public abstract String handleTextMsg(UserEntity userEntity, String text);

    public abstract String handleImageMsg(UserEntity userEntity, FileEntity imageFile);
}
