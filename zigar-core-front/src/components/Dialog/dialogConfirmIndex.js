import vue from 'vue'
import DialogConfirm from "./DialogConfirm";
// import Dialog from './Dialog'

//使用vue的extend，以vue文件为基础组件，返回一个可以创建vue组件的特殊构造函数
const DialogConfirmConstructor = vue.extend(DialogConfirm);

function $confirm(msg, callback) {
    const toastDom = new DialogConfirmConstructor({
        el: document.createElement('div'),
        data() {
            return {
                msg: msg,
                callback: callback,
                visible:true
            }
        }
    });
    //在body中动态创建一个div元素，后面自动会把它替换成整个vue文件内的内容
    document.body.appendChild(toastDom.$el)

}

export default $confirm