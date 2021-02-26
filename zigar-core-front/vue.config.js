const projectName = process.env.PROJECT_NAME
const assetsDir = `webjars/${projectName}`;

module.exports = {

    publicPath: '/',//根路径
    outputDir: '../src/main/resources/META-INF/resources/',//打包的时候生成的一个文件名
    assetsDir: assetsDir,//静态资源目录(js,css,img,fonts)这些文件都可以写里面

};
