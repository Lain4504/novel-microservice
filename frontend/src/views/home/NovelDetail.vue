<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import Comment from '@/components/home/CommentSection.vue';
import ReviewNovel from '@/components/home/ReviewNovelForm.vue';
import Ads from '@/components/home/Banner.vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';
import { getNovel } from '@/api/novel';
import { getVolumesByNovelId } from '@/api/volume';
import { getChapterByVolumeId } from '@/api/chapter';
import {followNovel, getReviewList, isFollowingNovel, unfollowNovel} from "@/api/user";
import store from "@/store";
import {
  createNovelComment,
  createNovelReply,
  getAllNovelComments,
  getAllNovelRepliesByCommentId
} from "@/api/novelcomment";

const route = useRoute();
const novelId = Array.isArray(route.params.id) ? route.params.id[0] : route.params.id;
const userId = store.getters.getUserId;

const novel = ref<Novel | Record<string, any>>({});
const comments = ref([]);
const reviews = ref([]);
const volumes = ref<Volume[]>([]);
const isCollected = ref(false);
const showRatingMenu = ref(false);
const currentTab = ref('Thảo Luận');
const tabs = ['Thảo Luận', 'Đánh Giá'];

interface Chapter {
  id: string;
  chapterTitle: string;
}

interface Category {
  id: string;
  name: string;
  description: string;
  createdDate: string;
  modifiedDate: string;
}

interface Novel {
  id: string;
  title: string;
  authorId: string;
  authorName: string;
  description: string;
  bookStatus: string;
  categories: Category[];
  coverPicture: string;
  image: {
    id: string;
    name: string;
    type: string;
    path: string;
  };
}

interface Volume {
  id: string;
  volumeName: string;
  showMore: boolean;
  expanded: boolean;
  chapters: Chapter[];
}

const fetchNovelData = async () => {
  try {
    const data = await getNovel(novelId);
    novel.value = {
      ...data,
      categories: data.categories.map((category: any) => ({
        id: category.id,
        name: category.name,
        description: category.description,
        createdDate: category.createdDate,
        modifiedDate: category.modifiedDate,
      })),
    };
  } catch (error) {
    console.error(error);
  }
};

const fetchComments = async () => {
  try {
    const result = await getAllNovelComments(novelId);
    comments.value = result.data;
  } catch (error) {
    console.error('Failed to fetch comments:', error);
  }
};

const fetchReviews = async () => {
  try {
    const result = await getReviewList(novelId, 1, 10);
    reviews.value = result.data;
  } catch (error) {
    console.error('Failed to fetch reviews:', error);
  }
};

const fetchVolumes = async (novelId: string) => {
  try {
    const data = await getVolumesByNovelId(novelId);
    volumes.value = data.map((volume: any) => ({
      ...volume,
      showMore: false,
      expanded: false,
      chapters: [],
    }));
  } catch (error) {
    console.error(error);
  }
};

const fetchChapters = async (volumeIndex: number, volumeId: string) => {
  try {
    const data = await getChapterByVolumeId(volumeId);
    volumes.value[volumeIndex].chapters = data;
  } catch (error) {
    console.error(error);
  }
};

const toggleVolume = (index: number, volumeId: string) => {
  volumes.value[index].expanded = !volumes.value[index].expanded;
  if (volumes.value[index].expanded && volumes.value[index].chapters.length === 0) {
    fetchChapters(index, volumeId);
  }
};

const toggleShowMore = (index: number) => {
  volumes.value[index].showMore = !volumes.value[index].showMore;
};

const checkFollowStatus = async () => {
  try {
    const response = await isFollowingNovel({ userId, novelId });
    isCollected.value = response;
  } catch (error) {
    console.error(error);
  }
};

const toggleCollect = async () => {
  try {
    if (isCollected.value) {
      await unfollowNovel({ userId, novelId });
    } else {
      await followNovel({ userId, novelId });
    }
    isCollected.value = !isCollected.value;
  } catch (error) {
    console.error(error);
  }
};

const handleReviewCreated = () => {
  fetchReviews();
};

const handleCommentAdded = () => {
  fetchComments();
};

const currentTabComponent = computed(() => {
  switch (currentTab.value) {
    case 'Thảo Luận':
      return Comment;
    case 'Đánh Giá':
      return ReviewNovel;
    default:
      return Comment;
  }
});

onMounted(() => {
  fetchNovelData();
  fetchComments();
  fetchReviews();
  fetchVolumes(novelId);
  checkFollowStatus();
});
</script>

<template>
  <div class="max-w-7xl mx-auto my-5">
    <Ads class="my-4" />
    <Breadcrumb :breadcrumbs="[
      { label: 'Home', to: { name: 'home' } },
      { label: novel.title, to: { name: 'noveldetail', params: { id: novel.id } }, isCurrent: true }
    ]" />
  </div>
  <div class="bg-gray-50 p-6 rounded-lg shadow-lg max-w-7xl mx-auto">
    <div class="flex flex-col lg:flex-row items-center space-y-6 lg:space-y-0 lg:space-x-8">
      <div class="w-full lg:w-1/4 flex justify-center">
        <img :src="novel.image?.path" class="w-56 h-72 object-cover rounded-lg shadow-lg" alt="Novel Cover" />
      </div>
      <div class="w-full lg:w-3/4 space-y-4 text-left lg:text-left">
        <h1 class="text-xl font-semibold text-gray-800 hover:text-[#728156]">{{ novel.title }}</h1>
        <div class="space-x-2">
          <span v-for="(category, index) in novel.categories" :key="index"
            class="inline-block py-1 px-3 text-sm font-semibold text-gray-700 bg-gray-200 rounded-full hover:bg-[#b6c99b]">
            {{ category.name }}
          </span>
        </div>
        <p class="text-gray-600 text-left">
          <span class="font-bold text-md">Author:</span> {{ novel.authorName }}
        </p>
        <p class="text-gray-600 text-left">
          <span class="font-bold text-md">Status:</span> Completed
        </p>
        <div class="grid grid-cols-3 lg:grid-cols-6 gap-4">
          <div class="flex justify-center items-center">
            <button id="collect" class="flex flex-col items-center text-center text-gray-700 cursor-pointer"
              @click="toggleCollect">
              <font-awesome-icon :icon="['fa', 'heart']" :class="isCollected ? 'text-red-700' : 'text-red-300'"
                class="text-xl transition-colors duration-200" />
              <span class="text-sm">{{ isCollected ? 'Following' : 'Follow' }}</span>
            </button>
          </div>
          <div class="flex justify-center items-center">
            <div class="flex flex-col items-center text-center text-gray-700 hover:text-blue-600 relative"
              @mouseover="showRatingMenu = true" @mouseleave="showRatingMenu = false">
              <a>
                <label for="open-rating" class="flex flex-col items-center">
                  <font-awesome-icon :icon="['far', 'star']" class="text-yellow-500" />
                  <span class="text-sm">Đánh giá</span>
                </label>
              </a>
              <div v-show="showRatingMenu"
                class="absolute top-full mt-2 bg-white border border-gray-200 rounded-lg shadow-lg p-2">
                <div class="flex space-x-1">
                  <font-awesome-icon v-for="n in 5" :key="n" :icon="['far', 'star']"
                    class="text-yellow-500 cursor-pointer" />
                </div>
              </div>
            </div>
          </div>
          <div class="flex justify-center items-center">
            <router-link to="#" class="flex flex-col items-center text-center text-gray-700 hover:text-blue-600">
              <font-awesome-icon :icon="['far', 'comments']" />
              <span class="text-sm">Bàn luận</span>
            </router-link>
          </div>
          <div class="flex justify-center items-center">
            <label for="open-sharing" class="flex flex-col items-center text-center text-gray-700 hover:text-blue-600">
              <font-awesome-icon :icon="['fas', 'share-nodes']" />
              <span class="text-sm">Chia sẻ</span>
            </label>
            <input type="checkbox" id="open-sharing" class="hidden" />
            <div class="sharing-box hidden mt-2">
              <a x-data="" href="#" class="sharing-item block text-sm text-blue-600 hover:text-blue-800">Link rút
                gọn</a>
              <a class="sharing-item block text-xl text-blue-600 hover:text-blue-800" href="#" target="_blank"><i
                  class="fab fa-facebook-f"></i></a>
              <a class="sharing-item block text-xl text-blue-600 hover:text-blue-800" href="#" target="_blank"><i
                  class="fab fa-twitter"></i></a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-10 text-left lg:text-left">
      <h3 class="font-bold text-md items-start justify-start flex">Description:</h3>
      <p v-html="novel.description" class="text-md text-gray-600 mt-4 text-left" />
    </div>
    <div class="w-full max-w-7xl mx-auto mt-10">
      <h2 class="text-xl font-semibold text-gray-800 mb-6">Chapter List</h2>
      <div class="space-y-6">
        <div v-for="(volume, volumeIndex) in volumes" :key="volumeIndex"
          class="border border-gray-200 rounded-lg overflow-hidden">
          <button @click="toggleVolume(volumeIndex, volume.id)"
            class="w-full px-4 py-3 bg-gray-50 flex items-center justify-between hover:bg-gray-100 transition-colors">
            <h3 class="text-lg font-medium text-gray-800">{{ volume.volumeName }}</h3>
            <font-awesome-icon :icon="volume.expanded ? 'fa-solid fa-sort-up' : 'fa-solid fa-sort-down'" />
          </button>
          <div v-show="volume.expanded" class="transition-all duration-300 ease-in-out">
            <div class="p-4">
              <div v-if="volume.chapters.length === 0" class="text-center text-gray-600">
                Không có chapter
              </div>
              <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                <router-link
                  v-for="(chapter, chapterIndex) in volume.chapters.slice(0, volume.showMore ? undefined : 6)"
                  :key="chapterIndex"
                  :to="{ name: 'chapter', params: { id: chapter.id } }"
                  class="group relative">
                  <div
                    class="p-3 bg-white rounded-lg shadow-sm hover:shadow-md transition-shadow border border-gray-100 hover:border-gray-200">
                    <span class="text-sm text-gray-700 truncate block">{{ chapter.chapterTitle }}</span>
                    <div
                      class="absolute left-1/2 transform -translate-x-1/2 bottom-full mb-2 hidden group-hover:block z-10 bg-black text-white text-xs py-1 px-2 rounded">
                      {{ chapter.chapterTitle }}
                    </div>
                  </div>
                </router-link>
              </div>
              <div v-if="volume.chapters.length > 6" class="mt-4 text-center">
                <button @click="toggleShowMore(volumeIndex)"
                  class="inline-flex items-center justify-center gap-2 px-4 py-2 text-sm text-gray-600 hover:text-gray-800 transition-colors">
                  <span>{{ volume.showMore ? 'Show Less' : 'Show More' }}</span>
                  <font-awesome-icon :icon="volume.showMore ? 'fa-solid fa-angles-up' : 'fa-solid fa-angles-down'" />
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container mx-auto p-4">
      <div class="flex space-x-4 border-b mb-4">
        <button v-for="(tab, index) in tabs" :key="index"
          :class="['px-4 py-2 text-sm font-semibold', currentTab === tab ? 'border-b-2 border-blue-500 text-blue-500' : 'text-gray-600']"
          @click="currentTab = tab">
          {{ tab }}
        </button>
      </div>
      <component :is="currentTabComponent" :itemId="novel.id" :reviews="reviews" @reviewCreated="handleReviewCreated"
                 :comments="comments" @commentAdded="handleCommentAdded"
                 :create-comment-api="createNovelComment"
                 :create-reply-api="createNovelReply"
                 :get-all-replies-api="getAllNovelRepliesByCommentId"
                 itemType="novel" :owner-id="novel.authorId" :item-name="novel.title" />
    </div>
  </div>
</template>