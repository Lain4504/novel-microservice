<template>
  <div
      v-if="show"
      class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50 px-4 py-6 md:px-0"
  >
    <div class="bg-white p-6 rounded-lg shadow-lg w-full max-w-md mx-auto">
      <h4 class="text-lg md:text-xl font-semibold mb-4">{{ title }}</h4>
      <p class="text-sm md:text-base mb-4 text-gray-700">{{ message }}</p>
      <div class="flex flex-row justify-end space-x-4">
        <!-- Cancel Button -->
        <button
            @click="cancel"
            class="w-full sm:w-auto px-4 py-2 border border-gray-400 text-gray-700 rounded-md
                 hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-500
                 text-sm md:text-base"
        >
          Cancel
        </button>
        <!-- Confirm Button -->
        <button
            @click="confirm"
            class="w-full sm:w-auto px-4 py-2 border border-red-500 text-red-500 rounded-md
                 hover:bg-red-600 hover:text-white focus:outline-none focus:ring-2 focus:ring-red-500
                 text-sm md:text-base"
        >
          {{ confirmText }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  show: {
    type: Boolean,
    required: true,
  },
  title: {
    type: String,
    default: 'Are you sure?',
  },
  message: {
    type: String,
    default: 'Do you really want to delete this item?',
  },
  confirmText: {
    type: String,
    default: 'Delete',
  },
});

const emit = defineEmits(['confirm', 'cancel']);

const confirm = () => {
  emit('confirm');
};

const cancel = () => {
  emit('cancel');
};
</script>