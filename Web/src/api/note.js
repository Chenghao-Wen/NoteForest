import request from "@/utils/request";

export const noteCategoryListService = () => {
  return request.get("/category");
};

export const noteCategoryAddService = (categoryData) => {
  return request.post("/category", categoryData);
};

export const noteCategoryUpdateService = (categoryData) => {
  return request.put("/category", categoryData);
};

export const noteCategoryDeleteService = (id) => {
  return request.delete("/category?id=" + id);
};

export const noteListService = (params) => {
  return request.get("/note", { params });
};

export const noteAddService = (noteData) => {
  return request.post("/note", noteData);
};

export const noteUpdateService = (noteData) => {
  return request.put("/note", noteData);
};

export const noteDeleteService = (id) => {
  return request.delete("/note?id=" + id);
};
