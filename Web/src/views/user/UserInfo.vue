<script setup>
import {ref} from 'vue'
import {useUserInfoStore} from "@/stores/userInfo";
import {userInfoUpdateService} from "@/api/user";
import {ElMessage} from "element-plus";

const userInfoStore = useUserInfoStore();


const userInfo = ref({
  ...userInfoStore.userInfo
})
const rules = {
  nickname: [
    {required: true, message: 'Please enter a user nickname', trigger: 'blur'},
    {
      pattern: /^\S{2,10}$/,
      message: 'Nickname must be 2-10 non-whitespace characters',
      trigger: 'blur'
    }
  ],
  email: [
    {required: true, message: 'Please enter a user email', trigger: 'blur'},
    {type: 'email', message: 'Invalid email format', trigger: 'blur'}
  ]
}

const updateUserInfo = () => {
  userInfoUpdateService(userInfo.value);
  ElMessage.success('Update successful')
  userInfoStore.setUserInfo(userInfo.value)
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>Basic Information</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
          <el-form-item label="Login Name">
            <el-input v-model="userInfo.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="User Nickname" prop="nickname">
            <el-input v-model="userInfo.nickname"></el-input>
          </el-form-item>
          <el-form-item label="User Email" prop="email">
            <el-input v-model="userInfo.email"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateUserInfo">Submit Changes</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>
