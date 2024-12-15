import axios from "@/utils/axiosInstance";
const POST_COMMENT_API = "/comment/post-comments";
interface PostComment {
    id?: string;
    postId: string;
    content: string;
    userId: string;
    replyCount?: number;
}

interface PostCommentReply {
    id?: string;
    commentId: string;
    parentReplyId?: string;
    userId: string;
    replyTo: string;
    content: string;
}

const getAllComments = (postId: string) => {
    return axios.get(`${POST_COMMENT_API}/${postId}`)
        .then(response => response.data);
};

const createComment = (data: PostComment) => {
    return axios.post(POST_COMMENT_API, data)
        .then(response => response.data);
};

const updateComment = (id: string, data: PostComment) => {
    return axios.put(`${POST_COMMENT_API}/${id}`, data)
        .then(response => response.data);
};

const deleteComment = (id: string) => {
    return axios.delete(`${POST_COMMENT_API}/${id}`)
        .then(response => response.data);
};

const getAllRepliesByCommentId = (commentId: string) => {
    return axios.get(`${POST_COMMENT_API}/replies/${commentId}`)
        .then(response => response.data);
};


const createReply = (data: PostCommentReply) => {
    return axios.post(`${POST_COMMENT_API}/replies`, data)
        .then(response => response.data);
};

const updateReply = (id: string, data: PostCommentReply) => {
    return axios.put(`${POST_COMMENT_API}/replies/${id}`, data)
        .then(response => response.data);
};

const deleteReply = (id: string) => {
    return axios.delete(`${POST_COMMENT_API}/replies/${id}`)
        .then(response => response.data);
};

export { getAllComments, createComment, updateComment, deleteComment, getAllRepliesByCommentId, createReply, updateReply, deleteReply };