<template>
  <el-dialog title="权限配置" :visible.sync="visible" v-if="row" top="10vh">
    <el-form :model="row" size="mini">
      <el-form-item label="用户" label-width="120px">
        <label style="line-height: 40px;" class="h3">{{ row.displayName + '(' + row.username + ')' }}</label>
      </el-form-item>
      <div style="border: 1px solid #cccccc;border-bottom: none" v-loading="tableIsLoading">
        <div style="border-bottom: 1px solid #cccccc;padding: 10px"
             v-for="(moduleItem) in list" :key="moduleItem.moduleId">
          <label class="moduleNameLabel">{{ moduleItem.name }}</label>
          <el-checkbox v-model="moduleItem.isChecked"></el-checkbox>
        </div>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dismiss()">取 消</el-button>
      <el-button type="primary" @click="submit()">确 定</el-button>
    </div>
  </el-dialog>

</template>

<script>
import BaseDialog from "../../../components/Dialog/BaseDialog";

export default {
  name: "DialogUserAuth",
  extends: BaseDialog,
  methods: {
    getData() {
      if (this.list) {
        this.list.forEach(item => {
          item.isChecked = false;
        })
      }
      this.tableIsLoading = true;
      let params = {userId: this.row.userId}
      this.request.axiosGetUser(this, params, (data) => {
        if (data) {
          this.getModuleList(data.menuList);
        } else {
          alert("获取用户权限失败")
        }
      });
    },
    getModuleList(userMenuList) {
      this.request.axiosGetModuleList(this, null, (moduleList) => {
        moduleList.forEach(moduleItem => {
          moduleItem.isChecked = false;
          userMenuList.forEach(userMenuItem => {
            if (moduleItem.moduleId == userMenuItem.moduleId) {
              moduleItem.isChecked = true;
            }
          })
        })
        this.list = this.jquery.extend([], moduleList);
        this.tableIsLoading = false;
      });
    },
    submit() {
      let submitList = [];
      this.list.forEach(listItem => {
        if (listItem.isChecked) {
          submitList.push(listItem);
        }
      });
      let prams = {
        userId: this.row.userId,
        privilegeList: submitList
      }
      this.request.axiosUserAuthPost(this, prams, () => {
        this.close();
      });
    }
  }
}
</script>

<style scoped>

.moduleNameLabel {
  font-size: 16px;
  font-weight: bold;
  line-height: 30px;
  width: 168px;
  margin-bottom: 0;
}

</style>
