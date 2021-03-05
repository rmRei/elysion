<template>
  <div>
    {{ ("00" + Math.floor(timer / 60)).slice(-2) }}:{{
      ("00" + (timer % 60)).slice(-2)
    }}
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

export default defineComponent({
  name: "Timer",
  setup() {
    const timer = ref(0);
    let interval: number;

    const countup = () => {
      timer.value++;
    };

    const start = () => {
      interval = setInterval(countup, 1000);
    };

    const stop = () => {
      clearInterval(interval);
    };

    const reset = () => {
      timer.value = 0;
    };

    return {
      timer,
      start,
      stop,
      reset
    };
  }
});
</script>
