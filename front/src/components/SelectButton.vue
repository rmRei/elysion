<template>
  <button
    v-on:click="returnSelected(option)"
    v-for="option in optionList"
    :key="option"
  >
    {{ option }}
  </button>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "SelectButton",
  props: {
    optionList: []
  },
  setup(_, context) {
    const returnSelected = (selected: string) => {
      context.emit("result", selected);
    };

    return {
      returnSelected
    };
  }
});
</script>
