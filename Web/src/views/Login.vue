<script setup>

import {Lock, User} from "@element-plus/icons-vue"
import {ref} from "vue";
import {userLoginService, userRegisterService} from "@/api/user";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {useTokenStore} from "@/stores/token";

// Register page and Login page use the same view.
// By default, show login.
const isRegister = ref(false);
const registerData = ref({
  username: '',
  password: '',
  rePassword: ''
});

const checkRePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please confirm the password again'))
  } else if (value !== registerData.value.password) {
    callback(new Error('The two passwords do not match'))
  } else {
    callback()
  }
}
const rules = {
  username: [
    {required: true, message: 'Please enter a username', trigger: 'blur'},
    {min: 5, max: 16, message: 'Length must be 5-16 characters', trigger: 'blur'},
  ],
  password: [
    {required: true, message: 'Please enter a password', trigger: 'blur'},
    {min: 5, max: 16, message: 'Length must be 5-16 characters', trigger: 'blur'},
  ],
  rePassword: [
    {required: true, message: 'Please enter a password', trigger: 'blur'},
    {validator: checkRePassword, trigger: 'blur'},
  ]
}

const register = async () => {
  const valid = await form.value.validate();
  if (valid) {
    const result = await userRegisterService(registerData.value);
    ElMessage.success(result.message ? result.message : 'Registration successful')
  }
}

const router = useRouter()
const tokenStore = useTokenStore();
const form = ref(null)
const login = async () => {
  // HM
  const valid = await form.value.validate();
  if (valid) {
    const result = await userLoginService(registerData.value);
    ElMessage.success(result.message ? result.message : 'Login successful')

    tokenStore.setToken(result.data)
    router.push('/')
  }
}

const clearRegisterData = () => {
  registerData.value = {
    username: '',
    password: '',
    rePassword: ''
  }
}


</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- Registration form -->
      <el-form ref="form" size="large" autocomplete="on" v-if="isRegister" :model="registerData" :rules="rules">
        <el-form-item>
          <h1>Register</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="Please enter a username" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="Please enter a password"
                    v-model="registerData.password"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" type="password" placeholder="Please enter the password again"
                    v-model="registerData.rePassword"></el-input>
        </el-form-item>
        <!-- Register button -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="register">
            Register
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = false;clearRegisterData()">
            ← Back
          </el-link>
        </el-form-item>
      </el-form>

      <!-- Login form -->
      <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
        <el-form-item>
          <h1>Login</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="Please enter a username" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input name="password" :prefix-icon="Lock" type="password" placeholder="Please enter a password"
                    v-model="registerData.password"></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>Remember me</el-checkbox>
            <el-link type="primary" :underline="false">Forgot password?</el-link>
          </div>
        </el-form-item>
        <!-- Login button -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="login">
            Login
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = true;clearRegisterData()">
            Register →
          </el-link>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
/* Styles */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
    url('@/assets/login_bg.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>