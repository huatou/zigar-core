const MiniCssExtractPlugin = require("mini-css-extract-plugin")
const assetsDir = 'webjars/zigar-core';
const projectName = process.env.PROJECT_NAME

module.exports = {
    publicPath: '/',//根路径
    outputDir: '../src/main/resources/META-INF/resources/',//打包的时候生成的一个文件名
    assetsDir: assetsDir,//静态资源目录(js,css,img,fonts)这些文件都可以写里面

    // 修改打包后js文件名
    configureWebpack: { // webpack 配置
        output: { // 输出重构  打包编译后的 文件名称  【模块名称.版本号.js】
            filename: `${assetsDir}/js/${projectName}.js`,
            chunkFilename: `${assetsDir}/js/${projectName}-chunk-vendors.js`,
        },
        // 修改打包后css文件名
        plugins: [
            new MiniCssExtractPlugin({
                filename: `${assetsDir}/css/${projectName}.css`,
                chunkFilename: `${assetsDir}/css/${projectName}-chunk-vendors.css`,
            })
        ]
    },

    chainWebpack: config => {

        //修改页面的title
        config.plugin('html').tap(args => {
            args[0].title = 'zigar-core';
            return args;
        });

    },
};
