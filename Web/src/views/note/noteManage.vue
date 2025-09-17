<script setup>
import { Delete, Edit, Plus } from "@element-plus/icons-vue";

import { ref } from "vue";
import {
  noteAddService,
  noteCategoryListService,
  noteDeleteService,
  noteListService,
  noteUpdateService,
} from "@/api/note";
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import { useTokenStore } from "@/stores/token";
import { ElMessage, ElMessageBox } from "element-plus";

// Article category data model
const categories = ref([
  {
    id: 3,
    categoryName: "Food",
    categoryAlias: "my",
    createTime: "2023-09-02 12:06:59",
    updateTime: "2023-09-02 12:06:59",
  },
  {
    id: 4,
    categoryName: "Entertainment",
    categoryAlias: "yl",
    createTime: "2023-09-02 12:08:16",
    updateTime: "2023-09-02 12:08:16",
  },
  {
    id: 5,
    categoryName: "Military",
    categoryAlias: "js",
    createTime: "2023-09-02 12:08:33",
    updateTime: "2023-09-02 12:08:33",
  },
]);

// Selected category ID during user search
const categoryId = ref("");

// Selected publication state during user search
const state = ref("");

// Article list data model
const notes = ref([
  // Example data
]);

// Pagination data model
const pageNum = ref(1); // Current page
const total = ref(20); // Total items
const pageSize = ref(3); // Items per page

// Called when items per page changes
const onSizeChange = (size) => {
  pageSize.value = size;
  noteList();
};
// Called when current page number changes
const onCurrentChange = (num) => {
  pageNum.value = num;
  noteList();
};

const noteCategoryList = async () => {
  const result = await noteCategoryListService();
  categories.value = result.data;
};
noteCategoryList();

const noteList = async () => {
  const params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    // If empty string, write as follows
    categoryId: categoryId.value ? categoryId.value : null,
    state: state.value ? state.value : null,
  };
  const result = await noteListService(params);
  notes.value = result.data.items;
  total.value = result.data.total;
  for (let i = 0; i < notes.value.length; i++) {
    const note = notes.value[i];
    for (let j = 0; j < categories.value.length; j++) {
      if (note.categoryId === categories.value[j].id) {
        note.categoryName = categories.value[j].categoryName;
      }
    }
  }
};
noteList();

// Control drawer visibility
const visibleDrawer = ref(false);
const drawerTitle = ref("");
// Add form data model
const noteModel = ref({
  title: "",
  categoryId: "",
  coverImg: "",
  content: "",
  state: "",
});

const tokenStore = useTokenStore();
const uploadSuccess = (result) => {
  noteModel.value.coverImg = result.data;
};

const addnote = async (clickState) => {
  noteModel.value.state = clickState;
  await noteAddService(noteModel.value);
  ElMessage.success("Added successfully");
  visibleDrawer.value = false;
  await noteList();
};

// Edit and delete
const showEditDialog = (row, title) => {
  visibleDrawer.value = true;
  drawerTitle.value = title;
  noteModel.value = {
    ...row,
  };
};

const showAddDialog = (title) => {
  clearData();
  drawerTitle.value = title;
  visibleDrawer.value = true;
};
const clearData = () => {
  noteModel.value = {
    title: "",
    categoryId: "",
    coverImg: "",
    content: "",
    state: "",
  };
};

const updatenote = async (clickState) => {
  noteModel.value.state = clickState;
  await noteUpdateService(noteModel.value);
  ElMessage.success("Updated successfully");
  visibleDrawer.value = false;
  await noteList();
};

const deletenote = (row) => {
  ElMessageBox.confirm("Are you sure you want to delete this article?", "Friendly Reminder", {
    confirmButtonText: "Confirm",
    cancelButtonText: "Cancel",
    type: "warning",
  }).then(async () => {
    await noteDeleteService(row.id);
    ElMessage.success("Deleted successfully");
    await noteList();
  });
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>Article Management</span>
        <div class="extra">
          <el-button type="primary" @click="showAddDialog('Add Article')"
            >Add Article</el-button
          >
        </div>
      </div>
    </template>
    <!-- Search form -->
    <el-form inline>
      <el-form-item label="Article Category:">
        <el-select placeholder="Please select" v-model="categoryId">
          <el-option
            v-for="c in categories"
            :key="c.id"
            :label="c.categoryName"
            :value="c.id"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="Publication State:">
        <el-select placeholder="Please select" v-model="state">
          <el-option label="Published" value="Published"></el-option>
          <el-option label="Draft" value="Draft"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="noteList">Search</el-button>
        <el-button
          @click="
            categoryId = '';
            state = '';
          "
          >Reset</el-button
        >
      </el-form-item>
    </el-form>
    <!-- Article list -->
    <el-table :data="notes" style="width: 100%">
      <el-table-column
        label="Article Title"
        width="400"
        prop="title"
      ></el-table-column>
      <el-table-column label="Category" prop="categoryName"></el-table-column>
      <el-table-column label="Publication Time" prop="createTime"></el-table-column>
      <el-table-column label="State" prop="state"></el-table-column>
      <el-table-column label="Actions" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="showEditDialog(row, 'Edit Article')"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deletenote(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No data available" />
      </template>
    </el-table>
    <!-- Pagination -->
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[3, 5, 10, 15]"
      layout="jumper, total, sizes, prev, pager, next"
      background
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />
  </el-card>

  <!-- Drawer -->
  <el-drawer
    v-model="visibleDrawer"
    :title="drawerTitle"
    direction="rtl"
    size="50%"
  >
    <!-- Add Article Form -->
    <el-form :model="noteModel" label-width="100px">
      <el-form-item label="Article Title">
        <el-input v-model="noteModel.title" placeholder="Please enter title"></el-input>
      </el-form-item>
      <el-form-item label="Article Category">
        <el-select placeholder="Please select" v-model="noteModel.categoryId">
          <el-option
            v-for="c in categories"
            :key="c.id"
            :label="c.categoryName"
            :value="c.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Article Cover">
        <el-upload
          class="avatar-uploader"
          :auto-upload="true"
          :show-file-list="false"
          action="/api/upload"
          name="file"
          :headers="{ Authorization: tokenStore.token }"
          :on-success="uploadSuccess"
        >
          <el-image
            v-if="noteModel.coverImg"
            :src="noteModel.coverImg"
            class="avatar"
          />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="Article Content">
        <div class="editor">
          <quill-editor
            theme="snow"
            v-model:content="noteModel.content"
            contentType="html"
          >
          </quill-editor>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="
            drawerTitle === 'Add Article'
              ? addnote('Published')
              : updatenote('Published')
          "
          >Publish
        </el-button>
        <el-button
          type="info"
          @click="
            drawerTitle === 'Add Article' ? addnote('Draft') : updatenote('Draft')
          "
          >Draft
        </el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
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

/* Drawer styles */
.avatar-uploader {
  :deep {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}

.editor {
  width: 100%;

  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>
