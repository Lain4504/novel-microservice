import axios from "@/utils/axiosInstance";
const POST_CATEGORY_API = "/posts/post-categories";

const createPostCategory = (data = {}) => {
  return axios.post(`${POST_CATEGORY_API}/create`, data)
      .then((response) => response.data.result);
}
const getPostCategories = () => {
    return axios.get(`${POST_CATEGORY_API}/all`)
        .then((response) => response.data.result);
}
const getPostCategory = (id) => {
    return axios.get(`${POST_CATEGORY_API}/${id}`)
        .then((response) => response.data.result);
}
const deletePostCategory = (id) => {
    return axios.delete(`${POST_CATEGORY_API}/delete/${id}`)
        .then((response) => response.data.result);
}
const updatePostCategory = (id, data = {}) => {
    return axios.put(`${POST_CATEGORY_API}/update/${id}`, data)
        .then((response) => response.data.result);
}
export { createPostCategory, getPostCategories, getPostCategory, deletePostCategory, updatePostCategory };