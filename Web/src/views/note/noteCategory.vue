<script setup>
import { Delete, Edit } from "@element-plus/icons-vue";
import { ref } from "vue";
import {
  noteCategoryAddService,
  noteCategoryDeleteService,
  noteCategoryListService,
  noteCategoryUpdateService,
} from "@/api/note";
import { ElMessage, ElMessageBox } from "element-plus";

const categories = ref([
  // {
  //   "id": 3,
  //   "categoryName": "entertainment",
  //   "categoryAlias": "my",
  //   "createTime": "2023-09-02 12:06:59",
  //   "updateTime": "2023-09-02 12:06:59"
  // },
  // {
  //   "id": 4,
  //   "categoryName": "life hobby",
  //   "categoryAlias": "yl",
  //   "createTime": "2023-09-02 12:08:16",
  //   "updateTime": "2023-09-02 12:08:16"
  // },
  // {
  //   "id": 5,
  //   "categoryName": "study",
  //   "categoryAlias": "js",
  //   "createTime": "2023-09-02 12:08:33",
  //   "updateTime": "2023-09-02 12:08:33"
  // }
]);

const noteCategoryList = async () => {
  const result = await noteCategoryListService();
  categories.value = result.data;
};

noteCategoryList();

// Category dialog visibility
const dialogVisible = ref(false);

// Category data model
const categoryModel = ref({
  categoryName: "",
  categoryAlias: "",
});
// Form validation rules
const rules = {
  categoryName: [
    { required: true, message: "Please enter category name", trigger: "blur" },
  ],
  categoryAlias: [
    { required: true, message: "Please enter category alias", trigger: "blur" },
  ],
};
const title = ref("");

const form = ref(null);
const addCategory = async () => {
  const valid = await form.value.validate();
  if (valid) {
    await noteCategoryAddService(categoryModel.value);
    ElMessage.success("Added successfully");
    await noteCategoryList();
    dialogVisible.value = false;
  }
};

// Show Edit Dialog
const showDialog = (row) => {
  dialogVisible.value = true;
  title.value = "Edit category";
  categoryModel.value = {
    categoryName: row.categoryName,
    categoryAlias: row.categoryAlias,
    id: row.id,
  };
};

const updateCategory = async () => {
  const valid = await form.value.validate();
  if (valid) {
    await noteCategoryUpdateService(categoryModel.value);
    ElMessage.success("Edit successful");
    await noteCategoryList();
    dialogVisible.value = false;
  }
};

const clearData = () => {
  categoryModel.value = {
    categoryName: "",
    categoryAlias: "",
  };
};

const deleteCategory = (row) => {
  ElMessageBox.confirm(
    "Are you sure you want to delete this category?",
    "Friendly Reminder",
    {
      confirmButtonText: "Confirm",
      cancelButtonText: "Cancel",
      type: "warning",
    }
  ).then(async () => {
    await noteCategoryDeleteService(row.id);
    ElMessage.success("Deleted successfully");
    await noteCategoryList();
  });
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>Article Categories</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="
              dialogVisible = true;
              title = 'Add category';
              clearData();
            "
            >Add category</el-button
          >
        </div>
      </div>
    </template>
    <el-table :data="categories" style="width: 100%">
      <el-table-column label="Index" width="100" type="index"></el-table-column>
      <el-table-column
        label="Category Name"
        prop="categoryName"
      ></el-table-column>
      <el-table-column
        label="Category Alias"
        prop="categoryAlias"
      ></el-table-column>
      <el-table-column label="Actions" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="showDialog(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteCategory(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No data available" />
      </template>
    </el-table>

    <!-- Add Category Dialog -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form
        ref="form"
        :model="categoryModel"
        :rules="rules"
        label-width="100px"
        style="padding-right: 30px"
      >
        <el-form-item label="Category Name" prop="categoryName">
          <el-input
            v-model="categoryModel.categoryName"
            minlength="1"
            maxlength="10"
          ></el-input>
        </el-form-item>
        <el-form-item label="Category Alias" prop="categoryAlias">
          <el-input
            v-model="categoryModel.categoryAlias"
            minlength="1"
            maxlength="15"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">Cancel</el-button>
          <el-button
            type="primary"
            @click="title === 'Add category' ? addCategory() : updateCategory()"
          >
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>
