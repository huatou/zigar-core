
const HOST_PATH = process.env.VUE_APP_API_URL;
const PROJECT_NAME = process.env.VUE_APP_PROJECT_NAME;
const API_PATH = HOST_PATH + "/api";
const FILE_UPLOAD_PATH = API_PATH + "/file/upload"
const FILE_PATH = API_PATH + "/file"

export default {
    HOST_PATH,
    PROJECT_NAME,
    API_PATH,
    FILE_PATH,
    FILE_UPLOAD_PATH
}



